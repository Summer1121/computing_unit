package top.summer1121.elastic_computing.computing_unit.core;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import top.summer1121.elastic_computing.common.config.BaseConfig;
import top.summer1121.elastic_computing.common.entity.resourceBeans.ClassResourceBean;
import top.summer1121.elastic_computing.common.entity.resourceBeans.ResourceBean;
import top.summer1121.elastic_computing.common.util.FileUtil;
import top.summer1121.elastic_computing.common.util.SpringUtil;

/**
 * 描述：自定义类加载器
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/16
 */
@Slf4j
public class MyClassLoader extends ClassLoader {

	private String className;
	private ResourceBean resource;

	/**
	 * 默认构造器
	 *
	 * @param resource 类名name!=null， 签名sign!=null
	 * @return {@link null}
	 * @className MyClassLoader
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/16
	 */
	public MyClassLoader(ResourceBean resource) {
		super();//调用父类的无参构造方法，创建一个类加载器，并让其父加载器默认设置为appClassLoader
		this.className = resource.getName();
		this.resource = new ClassResourceBean();
		BeanUtils.copyProperties(resource, this.resource);
	}

	public MyClassLoader(ResourceBean resource, ClassLoader parendClassLoader) {
		super(parendClassLoader);//代用父类的有参构造方法，创建一个类加载器，并让其父加载器设置为指定的类加载器
		this.className = resource.getName();
		this.resource = new ClassResourceBean();
		BeanUtils.copyProperties(resource, this.resource);
	}

	@Override
	public String toString() {
		return "[" + this.className + "]";
	}

	@Override
	protected Class<?> findClass(String className) {
		String url = null;

		//没有提前处理存储路径时，默认使用oss路径
		if (resource.getUrl() != null)
			url = resource.getUrl();
		else {
			try {
				url = resource.getFilePath();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		byte[] data = FileUtil.loadData(url);
		//将获取到的class文件，缓存到本地
		try {
			FileUtil.writeFileByBytes(data, ((BaseConfig) SpringUtil.getBean("BaseConfig")).getClassCachePath(), resource.getSign() + ".class",
					false);
		} catch (Exception e) {
			log.error("缓存class文件异常", e);
		}

		return defineClass(className, data, 0, data.length);
	}
}
