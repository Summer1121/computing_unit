package top.summer1121.elastic_computing.client.core;

import top.summer1121.elastic_computing.common.entity.taskBeans.TaskBean;
import top.summer1121.elastic_computing.common.entity.taskBeans.TaskResultBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * 描述：项目发布者
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/29
 */
public interface ITaskPusher {

	/**
	 * 将本地文件对象生成任务对象
	 *
	 * @param classFiles 要用到的Class列表
	 * @param dataFiles  要用到的Data列表
	 * @param taskId     32位uuid的任务id
	 * @return {@link TaskBean}生成的任务对象
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/29
	 */
	TaskBean createTaskBean(List<File> classFiles, List<File> dataFiles, String taskId) throws Exception;

	/**
	 * 将任务推送至分布式系统中的消息队列总线中
	 * 同时会创建一个新根据taskId区分的消息队列用来接收本次任务的结果
	 *
	 * @param task
	 * @return {@link java.lang.Boolean}
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/29
	 */
	Boolean pushTask(TaskBean task);

	/**
	 * 监听消息队列，并将消息结果返回
	 *
	 * @param task 包含任务id
	 * @return {@link TaskResultBean} 消息结果
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/29
	 */
	TaskResultBean pullResult(TaskBean task);
}
