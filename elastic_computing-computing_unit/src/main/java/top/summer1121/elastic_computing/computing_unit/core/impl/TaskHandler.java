package top.summer1121.elastic_computing.computing_unit.core.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import top.summer1121.elastic_computing.common.config.BaseConfig;
import top.summer1121.elastic_computing.common.entity.resourceBeans.DataResourceBean;
import top.summer1121.elastic_computing.common.entity.resourceBeans.ResourceBean;
import top.summer1121.elastic_computing.common.entity.taskBeans.TaskBean;
import top.summer1121.elastic_computing.common.entity.taskBeans.TaskResultBean;
import top.summer1121.elastic_computing.common.util.FileUtil;
import top.summer1121.elastic_computing.common.util.SpringUtil;
import top.summer1121.elastic_computing.computing_unit.core.ITaskHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;

/**
 * 描述：保持一个计算任务，应保持全局唯一
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/16
 */

@Slf4j
@NoArgsConstructor
@Data
public class TaskHandler implements ITaskHandler {

	@Autowired
	private BaseConfig baseConfig;

	/**
	 * 计算任务信息
	 */
	private static TaskBean taskBean;


	public TaskHandler(TaskBean taskBean) throws Exception {
		this.taskBean = taskBean;
		loadResource();
	}

	/**
	 * 获取任务信息
	 */
	public static TaskBean getTaskBean() {
		return taskBean;
	}


	@Override
	public void loadResource() throws Exception {
		//对任务中包含的类进行加载
		for (ResourceBean resource : taskBean.getClassResource()) {
			((ClassHandler) SpringUtil.getBean("ClassHandler")).loadClass(resource);
		}
		//加载任务中要用到的数据文件
		for (ResourceBean resource : taskBean.getDataResource()) {
			((DataHolder) SpringUtil.getBean("DataHolder")).put(resource.getName(), FileUtil.loadAndCacheData((DataResourceBean) resource));
		}


	}

	@Override
	public TaskResultBean invoke() {
		try {
			Date startTime = new Date();

			//加载主类并执行invoke方法
			Class<?> clazz = ((ClassHandler) SpringUtil.getBean("ClassHandler")).get("Main");

			Method mainMethod = clazz.getMethod("invoke", JSONObject.class);
			Object result = mainMethod.invoke(null,((DataHolder) SpringUtil.getBean("DataHolder")).get("data"));

			Date endTime = new Date();

			//封装为计算结果
			TaskResultBean resultBean = new TaskResultBean(result, startTime, endTime);
			BeanUtils.copyProperties(taskBean, resultBean);
			log.info("计算任务[{}]计算完毕",taskBean.getTaskId());
			//计算任务清空
			taskBean = null;
			return resultBean;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			//任务结束
			//清空ClassHolder内的class对象
			((ClassHandler) SpringUtil.getBean("ClassHandler")).clear();
			//清空ClassLoaderHolder内的类加载器
			((ClassLoaderHolder) SpringUtil.getBean("ClassLoaderHolder")).clear();
			//清空DataHolder内的数据缓存
			((DataHolder) SpringUtil.getBean("DataHolder")).clear();

			//清除本地缓存空间中较久的数据
			//类信息
			FileUtil.clearLocalDir(((BaseConfig) SpringUtil.getBean("BaseConfig")).getClassCachePath(),
					Integer.valueOf(((BaseConfig) SpringUtil.getBean("BaseConfig")).getClassCacheMaxCount()));
			//数据信息
			FileUtil.clearLocalDir(((BaseConfig) SpringUtil.getBean("BaseConfig")).getDataCachePath(),
					Integer.valueOf(((BaseConfig) SpringUtil.getBean("BaseConfig")).getDataCacheMaxCount()));

		}
		TaskResultBean resultBean = new TaskResultBean().error();
		BeanUtils.copyProperties(taskBean, resultBean);
		log.info("计算任务[{}]发生错误",taskBean.getTaskId());
		return resultBean;
	}
}
