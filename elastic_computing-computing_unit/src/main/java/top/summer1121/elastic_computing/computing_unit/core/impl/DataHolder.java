package top.summer1121.elastic_computing.computing_unit.core.impl;

import com.alibaba.fastjson.JSONObject;
import top.summer1121.elastic_computing.common.holder.IDataHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/25
 */

@Component("DataHolder")
public class DataHolder implements IDataHolder {

	static Map<String, JSONObject> map=new HashMap<>();

	@Override
	public JSONObject get(String dataName) {
		return map.get(dataName);
	}

	@Override
	public void put(String dataName, JSONObject jsonObject) {
		map.put(dataName, jsonObject);
	}

	@Override
	public void clear() {
		map.clear();
	}
}
