package com.summer1121.elastic_computing.computing_unit.core.impl;

import com.summer1121.elastic_computing.computing_unit.core.IClassLoader;
import com.summer1121.elastic_computing.computing_unit.core.MyClassLoader;
import com.summer1121.elastic_computing.computing_unit.entity.resourceBeans.ResourceBean;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/6
 */
@Slf4j
@Component("ClassHandler")
public class ClassHandler extends ClassHolder implements IClassLoader {

	@Autowired
	ClassLoaderHolder classLoaderHolder;

	@Override
	public Class loadClass(ResourceBean resource) {
		Class clazz = null;
		clazz = get(resource.getName());
		if (clazz != null) {
			return clazz;
		}

		//从classLoaderHolder获取class文件
		log.info("从classLoaderHolder获取classLoader,className:[{}],sign:[{}]", resource.getName(), resource.getSign());
		ClassLoader classLoader = classLoaderHolder.get(resource.getName());
		//从缓存获取class文件
		if (classLoader == null) {
			classLoader = getClassLoaderCache(resource);
			log.info("获取失败，从本地缓存获取classLoader,className:[{}],sign:[{}]", resource.getName(), resource.getSign());
		}
		//否则从oss获取
		if (classLoader == null) {
			classLoader = getClassLoaderRemote(resource);
			log.info("获取失败，从中心仓库获取classLoader,className:[{}],sign:[{}]", resource.getName(), resource.getSign());
		}

		if (classLoader == null) {
			log.error("无法获取classLoader,className:[{}],sign:[{}]", resource.getName(), resource.getSign());
			return null;
		}

		//开始类加载
		try {
			clazz = classLoader.loadClass(resource.getName());
			put(resource.getName(), clazz);
			return clazz;

		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
			log.info("加载[{}]类失败", resource.getName());
			return null;
		}
	}

	/**
	 * 可根据resource来获取class对象
	 *
	 * @param resource
	 * @return {@link Class}
	 * @className ClassHandler
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/16
	 */
	public Class get(ResourceBean resource) {
		return loadClass(resource);
	}

	@Override
	public ClassLoader getClassLoaderRemote(ResourceBean resource) {
		ClassLoader classLoader = new MyClassLoader(resource);
		classLoaderHolder.put(resource.getName(), classLoader);
		return classLoader;
	}

	@Override
	public ClassLoader getClassLoaderCache(ResourceBean resource) {
		return null;
	}
}
