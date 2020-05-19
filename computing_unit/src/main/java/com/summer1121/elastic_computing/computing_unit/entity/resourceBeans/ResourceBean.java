package com.summer1121.elastic_computing.computing_unit.entity.resourceBeans;

import lombok.Data;

import java.net.URL;

/**
 * @author xtysummer1121@foxmail.com
 * @description 资源包装类
 * @date 2020/4/27
 */
@Data
public abstract class ResourceBean {
	/**
	 * 访问路径
	 */
	URL url;
	/**
	 * 签名
	 *
	 */
	String sign;

	/**
	 * 资源名称
	 */
	String name;

	/**
	 * 根据sign获取访问路径
	 *
	 * @return {@link String}
	 * @className ResourceBean
	 * @author xtysummer1121@foxmail.com
	 * @methodName getFileUrl
	 * @date 2020/5/6
	 */
	abstract public String getFileUrl();
}
