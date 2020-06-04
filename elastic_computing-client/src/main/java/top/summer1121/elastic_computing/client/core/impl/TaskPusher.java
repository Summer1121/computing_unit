package top.summer1121.elastic_computing.client.core.impl;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;
import top.summer1121.elastic_computing.client.core.ITaskPusher;
import top.summer1121.elastic_computing.common.constant.MqConstant;
import top.summer1121.elastic_computing.common.entity.resourceBeans.ClassResourceBean;
import top.summer1121.elastic_computing.common.entity.resourceBeans.DataResourceBean;
import top.summer1121.elastic_computing.common.entity.resourceBeans.ResourceBean;
import top.summer1121.elastic_computing.common.entity.taskBeans.TaskBean;
import top.summer1121.elastic_computing.common.entity.taskBeans.TaskResultBean;
import top.summer1121.elastic_computing.common.util.MqUtil;
import top.summer1121.elastic_computing.common.util.SpringUtil;
import top.summer1121.elastic_computing.common.util.UuidUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * 描述：项目发布者
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/29
 */
@Slf4j
@Component("TaskPusher")
public class TaskPusher implements ITaskPusher {

	@Override
	public TaskBean createTaskBean(List<File> classFiles, List<File> dataFiles, String taskId) throws Exception {
		if (classFiles == null) classFiles = Collections.emptyList();
		if (dataFiles == null) dataFiles = Collections.emptyList();

		//将classFiles上传oss并生成对应的资源bean
		List<ClassResourceBean> classList = new ArrayList<>();
		List<DataResourceBean> dataList = new ArrayList<>();
		try {
			for (File file : classFiles) {
				if (!file.exists() || file.length() <= 0) continue;
				//将本地资源上传并生成resourceBean
				classList.add(new ClassResourceBean().createResourceBean(file));
			}
		} catch (Exception e) {
			log.error("class上传失败", e);
			return null;
		}

		//将dataFiles上传oss并生成对应的资源bean
		try {
			for (File file : dataFiles) {
				if (!file.exists() || file.length() <= 0) continue;
				//将本地资源上传并生成resourceBean
				dataList.add(DataResourceBean.createResourceBean(file));
			}
		} catch (Exception e) {
			log.error("data上传失败", e);
			return null;
		}

		if (taskId == null || taskId.length() != 32) {
			log.warn("传入的taskId不符规格，自动生成uuid");
			taskId = UuidUtil.getUuid();
		}

		TaskBean taskBean = new TaskBean();
		taskBean.setClassResource(classList).setDataResource(dataList).setTaskId(taskId).setStepId(1l);
		return taskBean;
	}

	@Override
	public Boolean pushTask(TaskBean task) {
		try {
			//创建结果队列（之所以在发送之前创建，是因为节点运行速度有可能过快，这边还没及时创建，节点就已经开始写入了）
			MqUtil.createResultExchange(task.getTaskId());

			RabbitTemplate rabbitTemplate = (RabbitTemplate) SpringUtil.getBean("rabbitTemplate");
			//向消息队列发送一个任务信息
			rabbitTemplate.convertAndSend(MqConstant.TASK_EXCHANGE, MqConstant.TASK_QUEUE, task);
		} catch (AmqpException e) {
			log.error("发送消息队列失败", e);
			MqUtil.deleteResultExchange(task.getTaskId());
			return false;
		}
		return true;
	}

	@Override
	public TaskResultBean pullResult(TaskBean task) {
		RabbitTemplate rabbitTemplate = (RabbitTemplate) SpringUtil.getBean("rabbitTemplate");
		TaskResultBean result = (TaskResultBean) rabbitTemplate.receiveAndConvert(MqUtil.getResultQueueName(task.getTaskId()));
		return result;
	}
}
