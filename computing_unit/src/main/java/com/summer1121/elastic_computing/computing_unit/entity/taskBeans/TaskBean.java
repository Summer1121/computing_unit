package com.summer1121.elastic_computing.computing_unit.entity.taskBeans;

import com.summer1121.elastic_computing.computing_unit.entity.resourceBeans.ResourceBean;
import com.google.common.primitives.UnsignedLong;
import lombok.Data;

import java.util.List;

/**
 * @author xtysummer1121@foxmail.com
 * @description 任务包装类
 * @date 2020/4/27
 */
@Data
public class TaskBean {

	/**
	 * 任务唯一id，在客户端生成唯一id
	 */
	String taskId;
	/**
	 * 任务中当前步骤id，由计算中心统一发放
	 */
	UnsignedLong stepId;
	/**
	 * 本次任务需要加载的类信息
	 */
	List<ResourceBean> classResource;
	/**
	 * 本次任务需要加载的数据集信息
	 */
	java.util.List<ResourceBean> dataResource;
}
