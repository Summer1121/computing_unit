package top.summer1121.elastic_computing.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import top.summer1121.elastic_computing.common.constant.MqConstant;

/**
 * 描述：用于消息队列操作的工具类
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/29
 */
@Slf4j
public class MqUtil {
	/**
	 * 创建任务交换器和任务主队列
	 *
	 * @param
	 * @return
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/29
	 */
	public static void createTaskExchange() {
		AmqpAdmin amqpAdmin = (AmqpAdmin) SpringUtil.getBean("amqpAdmin");

		// 创建Exchange 交换器(不会重复创建)
		amqpAdmin.declareExchange(new DirectExchange(MqConstant.TASK_EXCHANGE));
		log.info("创建Exchange完成");
		// 创建Queue 队列，持久化(不会重复创建)
		amqpAdmin.declareQueue(new Queue(MqConstant.TASK_QUEUE, true));
		log.info("创建Queue完成");
		// 创建绑定规则
		amqpAdmin.declareBinding(new Binding(MqConstant.TASK_QUEUE, Binding.DestinationType.QUEUE,
				MqConstant.TASK_EXCHANGE, MqConstant.TASK_QUEUE, null));
		log.info("创建绑定规则完成");
	}

	/**
	 * 根据任务id，创建运行结果队列
	 *
	 * @param taskId
	 * @return queueName 队列名称
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/29
	 */
	public static String createResultExchange(String taskId) {
		AmqpAdmin amqpAdmin = (AmqpAdmin) SpringUtil.getBean("amqpAdmin");

		// 创建Exchange 交换器(不会重复创建)  所有答案都通过这个交换器
		amqpAdmin.declareExchange(new TopicExchange(MqConstant.RESULT_EXCHANGE));
		log.info("创建Exchange完成");
		String queueName = MqConstant.RESULT_QUEUE + taskId;
		// 创建Queue 队列，持久化(不会重复创建)
		amqpAdmin.declareQueue(new Queue(queueName, true));
		log.info("创建Queue完成");
		// 创建绑定规则  根据elastic.result.*的路由键
		amqpAdmin.declareBinding(new Binding(queueName, Binding.DestinationType.QUEUE,
				MqConstant.RESULT_EXCHANGE, MqConstant.RESULT_QUEUE + "*", null));
		log.info("创建绑定规则完成");
		return queueName;
	}

	public static String getResultQueueName(String taskId) {
		String queueName = MqConstant.RESULT_QUEUE + taskId;
		return queueName;
	}

	/**
	 * 运行结束，删除任务对应的队列
	 *
	 * @param taskId
	 * @return queueName 队列名称
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/29
	 */
	public static String deleteResultExchange(String taskId) {
		AmqpAdmin amqpAdmin = (AmqpAdmin) SpringUtil.getBean("amqpAdmin");
		String queueName = MqConstant.RESULT_QUEUE + taskId;
		amqpAdmin.deleteQueue(queueName);
		return queueName;
	}
}
