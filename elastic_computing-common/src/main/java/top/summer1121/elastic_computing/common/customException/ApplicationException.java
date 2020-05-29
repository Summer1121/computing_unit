package top.summer1121.elastic_computing.common.customException;

/**
 * 描述：程序通用错误信息
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/25
 */
public class ApplicationException extends Exception {
	String msg;
	String code;

	public ApplicationException(ExceptionContent e) {
		this.msg = e.msg;
		this.code = e.code;
	}

	public ApplicationException(String msg, String code) {
		this.msg = msg;
		this.msg = code;
	}
}
