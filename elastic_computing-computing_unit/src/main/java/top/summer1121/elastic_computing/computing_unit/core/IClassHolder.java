package top.summer1121.elastic_computing.computing_unit.core;

/**
 * 描述：Class对象持有者,根据类名返回对应的class对象
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/16
 */
public interface IClassHolder {

	/**
	 * 根据类名获取class对象，未加载对象会返回null
	 *
	 * @param className
	 * @return {@link Class}
	 * @className IClassHolder
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/16
	 */
	Class get(String className);

	/**
	 * 存入新的class对象
	 *
	 * @param className
	 * @param clazz
	 * @className IClassHolder
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/16
	 */
	void put(String className, Class clazz);

	/**
	 * 清空class对象持有者内的所有class对象
	 *
	 * @param
	 * @className IClassHolder
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/16
	 */
	void clear();
}
