package com.summer1121.elastic_computing.computing_unit.core;

import com.summer1121.elastic_computing.computing_unit.entity.taskBeans.TaskBean;

/**
 * 描述：单元核心，计算任务处理器
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/4/27
 */
public interface ITaskHandler {

	/**
	 * 根据当前任务信息，加载所需资源
	 *
	 * @return
	 * @className ITaskHolder
	 * @author xtysummer1121@foxmail.com
	 * @methodName loadResource
	 * @date 2020/4/27
	 */
	void loadResource();

	/**
	 * 执行当前task
	 *
	 * @param
	 * @return
	 * @className ITaskHolder
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/16
	 */
	void invoke();

}
