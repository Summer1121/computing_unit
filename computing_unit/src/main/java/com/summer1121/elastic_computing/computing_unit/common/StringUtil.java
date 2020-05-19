package com.summer1121.elastic_computing.computing_unit.common;

/**
 * 描述：字符串工具类
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/6
 */
public class StringUtil {
	/**
	 * 判断字符串是否为空
	 *
	 * @param str
	 * @return {@link boolean}
	 * @className StringUtil
	 * @author xtysummer1121@foxmail.com
	 * @methodName isBlack
	 * @date 2020/5/6
	 */
	public static boolean isBlack(String str) {
		if (str == null || str.trim().isEmpty()) return false;
		return true;
	}
}
