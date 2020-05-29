package top.summer1121.elastic_computing.computing_unit.core.impl;

import top.summer1121.elastic_computing.common.entity.resourceBeans.ResourceBean;
import top.summer1121.elastic_computing.computing_unit.core.IClassLoader;
import top.summer1121.elastic_computing.computing_unit.core.MyClassLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.MalformedURLException;

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
	public Class loadClass(ResourceBean resource) throws Exception {
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
			log.info("获取失败，从本地缓存获取classLoader,className:[{}],sign:[{}]", resource.getName(), resource.getSign());
			classLoader = getClassLoaderCache(resource);
		}
		//否则从oss获取
		if (classLoader == null) {
			log.info("获取失败，从中心仓库获取classLoader,className:[{}],sign:[{}]", resource.getName(), resource.getSign());
			classLoader = getClassLoaderRemote(resource);
		}

		if (classLoader == null) {
			log.error("无法获取classLoader,className:[{}],sign:[{}]", resource.getName(), resource.getSign());
			return null;
		}

		//开始类加载
		try {
			log.info("获取classLoader成功，classLoader,className:[{}],sign:[{}]", resource.getName(), resource.getSign());
			clazz = ((MyClassLoader) classLoader).loadClass(resource.getName());
			put(resource.getName(), clazz);
			return clazz;

		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
			log.info("加载[{}]类失败", resource.getName());
			return null;
		}
	}

	@Override
	public ClassLoader getClassLoaderRemote(ResourceBean resource) throws Exception {
		resource.setUrl(resource.getFileUrl());
		ClassLoader classLoader = new MyClassLoader(resource);
		classLoaderHolder.put(resource.getName(), classLoader);

		return classLoader;
	}

	@Override
	public ClassLoader getClassLoaderCache(ResourceBean resource) throws Exception {
		File localFile = new File(resource.getFilePath());
		//如果本地不存在，返回null
		if (!localFile.exists())
			return null;
		resource.setUrl(localFile.toURI().toURL().toString());
		ClassLoader classLoader = new MyClassLoader(resource);
		classLoaderHolder.put(resource.getName(), classLoader);
		return classLoader;
	}
}
