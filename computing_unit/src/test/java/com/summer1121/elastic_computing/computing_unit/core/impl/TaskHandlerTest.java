package com.summer1121.elastic_computing.computing_unit.core.impl;

import com.summer1121.elastic_computing.computing_unit.entity.resourceBeans.ClassResourceBean;
import com.summer1121.elastic_computing.computing_unit.entity.resourceBeans.ResourceBean;
import com.summer1121.elastic_computing.computing_unit.entity.taskBeans.TaskBean;
import com.summer1121.elastic_computing.computing_unit.util.UuidUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 描述：
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/16
 */
@ComponentScan("com.summer1121.elastic_computing.computing_unit.core")
class TaskHandlerTest {

//	TaskHandler taskHandler;

	@Test
	void invoke() throws MalformedURLException {
		ResourceBean resource = new ClassResourceBean();
		resource.setName("Main");
		resource.setSign("0d3d18788b5d5445a3f964e96d2920e4");
		resource.setUrl(new URL("https://elastic-computing-data-center.oss-cn-beijing.aliyuncs.com/class/0d3d18788b5d5445a3f964e96d2920e4.class"));

		TaskBean taskBean = new TaskBean();
		taskBean.setClassResource(new ArrayList<ResourceBean>() {
			{
				add(resource);
			}
		});
		taskBean.setTaskId(UuidUtil.getUuid());
		TaskHandler taskHandler = new TaskHandler(taskBean);
		taskHandler.invoke();
	}
}