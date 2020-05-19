package com.summer1121.elastic_computing.computing_unit.core;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;

/**
 * 描述：用于加载数据集
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/4/27
 */
public interface IDataLoader {
	/**
	 * 根据url获取数据集
	 * （优先根据sign从缓存中获取对应的数据）
	 *
	 * @param url  对象存储的url
	 * @param sign 数据集签名
	 * @return json字符串
	 * @className IDataLoader
	 * @author xtysummer1121@foxmail.com
	 * @methodName loadJsonData
	 * @date 2020/4/27
	 */
	String loadDataAsJson(URL url, String sign);


}
