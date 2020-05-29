package top.summer1121.elastic_computing.common.util;

import java.util.Collection;
import java.util.Map;

/**
 * 描述：字符串工具类
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/6
 */
public class ParamUtil {
	/**
	 * 非null判断
	 *
	 * @param params
	 * @return {@link Boolean}
	 * @className ParamValidatedUtil
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/12
	 */
	public static Boolean isNotNull(Object... params) {
		for (Object var : params) {
			if (var == null) return false;
		}
		return true;
	}

	/**
	 * 非空判断
	 *
	 * @param params
	 * @return 所有参数都不为空，则返回true
	 * @className ParamValidatedUtil
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/12
	 */
	public static Boolean isNotBlank(Object... params) {
		for (Object var : params) {
			if (var == null) return false;
			if (var instanceof String) {
				if (((String) var).trim().isEmpty()) return false;
			} else if (var instanceof Collection) {
				if (((Collection) var).isEmpty()) return false;
			} else if (var instanceof Map) {
				if ((((Map) var).isEmpty())) return false;
			}
		}
		return true;
	}
}
