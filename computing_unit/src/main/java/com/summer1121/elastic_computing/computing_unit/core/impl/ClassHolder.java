package com.summer1121.elastic_computing.computing_unit.core.impl;

import com.summer1121.elastic_computing.computing_unit.core.IClassHolder;
import com.summer1121.elastic_computing.computing_unit.entity.resourceBeans.ResourceBean;
import org.springframework.context.annotation.Bean;
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
	Map<String, Class> map;

	public ClassHolder() {
		map = new HashMap<>();
	}

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
