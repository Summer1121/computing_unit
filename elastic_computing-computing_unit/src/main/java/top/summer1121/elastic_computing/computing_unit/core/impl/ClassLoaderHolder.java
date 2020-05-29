package top.summer1121.elastic_computing.computing_unit.core.impl;

import top.summer1121.elastic_computing.computing_unit.core.IClassLoaderHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：使用map来存储已获取的类加载器，可以按照类名来获取类加载器
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/16
 */
@Component("ClassLoaderHolder")
public class ClassLoaderHolder implements IClassLoaderHolder {

	Map<String, ClassLoader> classLoaders = null;

	/**
	 * 初始化
	 *
	 * @param
	 * @return {@link null}
	 * @className ClassLoaderHolder
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/16
	 */
	ClassLoaderHolder() {
		classLoaders = new HashMap<>();
	}

	@Override

	public ClassLoader get(String className) {
		return classLoaders.get(className);
	}

	@Override
	public void put(String className, ClassLoader classLoader) {
		classLoaders.put(className, classLoader);
	}

	@Override
	public void clear() {
		classLoaders.clear();
	}
}
