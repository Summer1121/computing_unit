package top.summer1121.elastic_computing.computing_unit.core;

import java.util.Map;

/**
 * 描述：类加载器持有者
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/16
 */
public interface IClassLoaderHolder {


	/**
	 * 根据类名获取类加载器
	 *
	 * @param className
	 * @return {@link java.lang.ClassLoader}
	 * @className IClassLoaderHolder
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/16
	 */
	ClassLoader get(String className);

	/**
	 * 根据类名存储类加载器
	 *
	 * @param className
	 * @param classLoader
	 * @return
	 * @className IClassLoaderHolder
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/16
	 */
	void put(String className, ClassLoader classLoader);

	/**
	 * 清空所有类加载器
	 *
	 * @param
	 * @return
	 * @className IClassLoaderHolder
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/16
	 */
	void clear();
}
