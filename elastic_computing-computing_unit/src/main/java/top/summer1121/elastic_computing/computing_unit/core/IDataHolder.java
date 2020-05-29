package top.summer1121.elastic_computing.computing_unit.core;

import com.alibaba.fastjson.JSONObject;

/**
 * 描述：data对象持有者,根据文件名返回对应的JsonObject对象
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/16
 */
public interface IDataHolder {

	/**
	 * 根据类名获取JsonObject对象，未加载对象会返回null
	 *
	 * @param dataName
	 * @return {@link Class}
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/16
	 */
	JSONObject get(String dataName);

	/**
	 * 存入新的JsonObject对象
	 *
	 * @param dataName
	 * @param jsonObject
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/16
	 */
	void put(String dataName, JSONObject jsonObject);

	/**
	 * 清空data对象持有者内的所有JsonObject对象
	 *
	 * @param
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/16
	 */
	void clear();
}
