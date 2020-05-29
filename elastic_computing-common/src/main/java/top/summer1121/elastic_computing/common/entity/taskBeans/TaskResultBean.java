package top.summer1121.elastic_computing.common.entity.taskBeans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述：任务结果包装类
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TaskResultBean extends TaskBean implements Serializable {
	/**
	 * 计算结果
	 */
	Object result;

	/**
	 * 起始时间
	 */
	Date startTime;

	/**
	 * 结束时间
	 */
	Date endTime;

	/**
	 * 成功标志
	 */
	Boolean success = true;

	public TaskResultBean(Object result, Date startTime, Date endTime) {
		this.result = result;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public TaskResultBean error() {
		this.success = false;
		return this;
	}
}
