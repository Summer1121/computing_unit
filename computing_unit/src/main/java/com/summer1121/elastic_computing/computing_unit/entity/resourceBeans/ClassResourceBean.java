package com.summer1121.elastic_computing.computing_unit.entity.resourceBeans;

import com.summer1121.elastic_computing.computing_unit.common.StringUtil;
import com.summer1121.elastic_computing.computing_unit.config.BaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述：用于标识class资源
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/6
 */
@Component
public class ClassResourceBean extends ResourceBean {

	@Autowired
	BaseConfig baseConfig;

	@Override

	public String getFileUrl() {
		if (StringUtil.isBlack(sign)) return null;

		StringBuilder sb = new StringBuilder(64);
		sb.append(baseConfig.getClassStoreUrlPrefix());
		sb.append("&");
		sb.append(this.sign);
		sb.append(".class");
		return sb.toString();
	}
}
