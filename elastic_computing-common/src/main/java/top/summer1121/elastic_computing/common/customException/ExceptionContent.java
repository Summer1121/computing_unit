package top.summer1121.elastic_computing.common.customException;

/**
 * 异常内容
 */
public enum ExceptionContent {
	/**
	 * 服务器内部异常
	 */
	InternalException("服务器内部错误", "500"),
	DataCenterNetException("数据中心访问异常", "500"),
	JsonFormatException("JSON格式转换异常", "500");

	String msg;
	String code;

	ExceptionContent(String msg, String code) {
		this.msg = msg;
		this.msg = code;
	}
}