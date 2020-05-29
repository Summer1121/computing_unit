package top.summer1121.elastic_computing.common.constant;

/**
 * 描述：消息队列常量
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/29
 */
public class MqConstant {
	/**
	 * 任务发布交换器
	 */
	public static final String TASK_EXCHANGE = "elastic.taskExchange";
	/**
	 * 任务发布队列
	 */
	public static final String TASK_QUEUE = "elastic.taskQueue";

	/**
	 * 任务结果交换器
	 */
	public static final String RESULT_EXCHANGE = "elastic.resultExchange";
	/**
	 * 任务结果队列前缀
	 */
	public static final String RESULT_QUEUE = "elastic.resultQueue.";

}
