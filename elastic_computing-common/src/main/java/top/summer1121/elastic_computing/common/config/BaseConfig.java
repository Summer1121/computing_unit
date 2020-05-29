package top.summer1121.elastic_computing.common.config;

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
@Component("BaseConfig")
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
	 * class文件缓存路径
	 */
	@Value("${class.cache.path}")
	String classCachePath;


	/**
	 * class文件最大存储数量
	 */
	@Value("${class.cache.max.count}")
	String classCacheMaxCount;

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

	/**
	 * data文件缓存路径
	 */
	@Value("${data.cache.path}")
	String dataCachePath;


	/**
	 * data文件最大存储数量
	 */
	@Value("${data.cache.max.count}")
	String dataCacheMaxCount;
}
