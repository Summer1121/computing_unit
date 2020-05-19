package com.summer1121.elastic_computing.computing_unit.core;


import com.summer1121.elastic_computing.computing_unit.entity.resourceBeans.ClassResourceBean;
import com.summer1121.elastic_computing.computing_unit.entity.resourceBeans.ResourceBean;
import com.summer1121.elastic_computing.computing_unit.util.FileUtil;
import org.springframework.beans.BeanUtils;

/**
 * 描述：自定义类加载器
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/16
 */
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
		String url = resource.getFileUrl();
		byte[] data = FileUtil.loadData(url);

		return defineClass(className, data, 0, data.length);
	}
}
