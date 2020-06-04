package top.summer1121.elastic_computing.computing_unit.core.impl;

import top.summer1121.elastic_computing.common.holder.IClassHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/16
 */
@Component("ClassHolder")
public class ClassHolder implements IClassHolder {
	static Map<String, Class> map = new HashMap<>();

	@Override
	public Class get(String className) {
		return map.get(className);
	}

	@Override
	public void put(String className, Class clazz) {
		map.put(className, clazz);
	}

	@Override
	public void clear() {
		map.clear();
	}
}
