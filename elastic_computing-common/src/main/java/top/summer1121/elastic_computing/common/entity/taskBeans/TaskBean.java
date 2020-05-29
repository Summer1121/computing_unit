package top.summer1121.elastic_computing.common.entity.taskBeans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.summer1121.elastic_computing.common.entity.resourceBeans.ClassResourceBean;
import top.summer1121.elastic_computing.common.entity.resourceBeans.DataResourceBean;
import top.summer1121.elastic_computing.common.entity.resourceBeans.ResourceBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author xtysummer1121@foxmail.com
 * @description 任务包装类
 * @date 2020/4/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TaskBean implements Serializable {

	/**
	 * 任务唯一id，在客户端生成唯一id
	 */
	String taskId;
	/**
	 * 任务中当前步骤id，由计算中心统一发放
	 */
	Long stepId;
	/**
	 * 本次任务需要加载的类信息
	 */
	List<ClassResourceBean> classResource = Collections.emptyList();
	/**
	 * 本次任务需要加载的数据集信息
	 */
	List<DataResourceBean> dataResource = Collections.emptyList();
}
