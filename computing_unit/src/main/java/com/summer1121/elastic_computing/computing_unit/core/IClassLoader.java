package com.summer1121.elastic_computing.computing_unit.core;

import com.summer1121.elastic_computing.computing_unit.entity.resourceBeans.ResourceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.net.MalformedURLException;

/**
 * 描述：单元核心，用于用于获取类加载器，加载任务的class文件，并返回class对象
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/4/27
 */
public interface IClassLoader {

	/**
	 * 根据resource信息来加载目标类
	 *
	 * @param resource 包含class的sign和className
	 * @return 目标对象的class
	 * @className ItaskClassLoader
	 * @author xtysummer1121@foxmail.com
	 * @methodName loadClass
	 * @date 2020/4/27
	 */
	Class loadClass(ResourceBean resource);

	/**
	 * 根据url获取对应的类加载器
	 * (优先根据sign从缓存中获取对应的class文件
	 * {@link IClassLoader#getClassLoaderCache(com.summer1121.elastic_computing.computing_unit.entity.resourceBeans.ResourceBean)})
	 *
	 * @param resource 资源文件信息
	 * @return url对应的类加载器
	 * @className ItaskClassLoader
	 * @author xtysummer1121@foxmail.com
	 * @methodName loadClassFrom
	 * @date 2020/4/27
	 */
	ClassLoader getClassLoaderRemote(ResourceBean resource);

	/**
	 * 根据签名获取缓存中的类加载器
	 *
	 * @param resource 资源文件信息
	 * @return 签名对应的类加载器
	 * @className ItaskClassLoader
	 * @author xtysummer1121@foxmail.com
	 * @methodName loadClassCache
	 * @date 2020/4/27
	 */
	ClassLoader getClassLoaderCache(ResourceBean resource);
}
