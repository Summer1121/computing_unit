package top.summer1121.elastic_computing.computing_unit.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.summer1121.elastic_computing.common.constant.MqConstant;
import top.summer1121.elastic_computing.common.entity.taskBeans.TaskBean;
import top.summer1121.elastic_computing.common.entity.taskBeans.TaskResultBean;
import top.summer1121.elastic_computing.common.util.MqUtil;
import top.summer1121.elastic_computing.computing_unit.core.impl.TaskHandler;

import java.util.concurrent.CountDownLatch;

import static top.summer1121.elastic_computing.common.constant.MqConstant.TASK_QUEUE;

/**
 * 描述：
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/6/3
 */
@Component("MqListener")
@Slf4j
public class MqListener {
	@Autowired
	RabbitTemplate rabbitTemplate;

	@RabbitListener(queues = {TASK_QUEUE})
	public void ListenAndInvoke(TaskBean task) {
		try {
			TaskHandler taskHandler = null;
			try {
				taskHandler = new TaskHandler(task);
			} catch (Exception e) {
				log.error("加载资源失败", e);
			}
			TaskResultBean result = null;

			try {
				result = taskHandler.invoke();
			} catch (Exception e) {
				log.error("运行失败", e);
			}

			//返回计算结果
			rabbitTemplate.convertAndSend(MqConstant.RESULT_EXCHANGE, MqUtil.getResultQueueName(task.getTaskId()), result);
			log.info("发送任务[{}]计算结果",task.getTaskId());
		} catch (Exception e) {
			log.error("运行失败", e);
			TaskResultBean result = new TaskResultBean().error();
			rabbitTemplate.convertAndSend(MqConstant.RESULT_EXCHANGE, MqUtil.getResultQueueName(task.getTaskId()), result);
		}

	}
}
