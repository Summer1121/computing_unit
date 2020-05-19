package com.summer1121.elastic_computing.computing_unit.util;

import java.util.UUID;

/**
 * 描述：uuid生成工具
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/9
 */
public class UuidUtil {
	/**
	 * 返回32位uuid
	 *
	 * @param
	 * @return {@link String}
	 * @className UuidUtil
	 * @author xtysummer1121@foxmail.com
	 * @methodName getUuid
	 * @date 2020/5/9
	 */
	public static String getUuid() {
		String str = UUID.randomUUID().toString().replace("-", "");
		return str;
	}
}
