package com.summer1121.elastic_computing.computing_unit.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 描述：
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/6
 */
@Data
@PropertySource(value = {"classpath:config/base.config.properties"})
@Component
public class BaseConfig {
	/**
	 * class文件所在oss存储根路径
	 */
	@Value("${class.oss.base.url}")
	String classOssBaseUrl;

	/**
	 * class文件存储路径
	 */
	@Value("${class.store.url.prefix}")
	String classStoreUrlPrefix;

	/**
	 * class文件过期时间(秒)
	 */
	@Value("${class.store.url.timeout}")
	String classStoreUrlTimeout;


	/**
	 * data文件所在oss存储根路径
	 */
	@Value("${data.oss.base.url}")
	String dataOssBaseUrl;

	/**
	 * data文件存储路径
	 */
	@Value("${data.store.url.prefix}")
	String dataStoreUrlPrefix;

	/**
	 * data文件过期时间(秒)
	 */
	@Value("${data.store.url.timeout}")
	String dataStoreUrlTimeout;

}
