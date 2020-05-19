package com.summer1121.elastic_computing.computing_unit.core.impl;

import com.summer1121.elastic_computing.computing_unit.core.ITaskHandler;
import com.summer1121.elastic_computing.computing_unit.entity.resourceBeans.ResourceBean;
import com.summer1121.elastic_computing.computing_unit.entity.taskBeans.TaskBean;
import com.summer1121.elastic_computing.computing_unit.util.SpringUtil;
import com.sun.org.apache.xml.internal.security.Init;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 描述：
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/16
 */

@Slf4j
@NoArgsConstructor
@Data
public class TaskHandler implements ITaskHandler {
	private TaskBean taskBean;


	public TaskHandler(TaskBean taskBean) {
		this.taskBean = taskBean;
		loadResource();
	}

//	public void init() {
//		loadResource();
//	}

	@Override
	public void loadResource() {
		//对任务中包含的类进行加载
		for (ResourceBean resource : taskBean.getClassResource()) {
			((ClassHandler) SpringUtil.getBean("ClassHandler")).loadClass(resource);
		}
		//todo 加载任务中包含的数据文件


	}

	@Override
	public void invoke() {
		try {
			//加载主类并执行主方法
			Class<?> clazz = ((ClassHandler) SpringUtil.getBean("ClassHandler")).get("Main");
			Object object = clazz.newInstance();
			Method mainMethod = clazz.getMethod("main", String[].class);
			mainMethod.invoke(object, (Object) new String[]{});
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			//任务结束
			//清空ClassHolder内的class对象
			((ClassHandler) SpringUtil.getBean("ClassHandler")).clear();
			//清空ClassLoaderHolder内的类加载器
			((ClassLoaderHolder) SpringUtil.getBean("ClassLoaderHolder")).clear();
		}
	}
}
