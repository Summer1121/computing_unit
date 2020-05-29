package top.summer1121.elastic_computing.client.core.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import top.summer1121.elastic_computing.common.entity.taskBeans.TaskBean;
import top.summer1121.elastic_computing.common.util.MqUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 描述：
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/29
 */

@SpringBootTest
@ComponentScan("top.summer1121.elastic_computing")
class TaskPusherTest {

	@Autowired
	TaskPusher taskPusher;

	@Test
	void createTaskBean() throws Exception {
		MqUtil.createTaskExchange();
		List<File> classFiles=new ArrayList<>();
		classFiles.add(new File("D:\\JAVA_Projects\\commonTest\\target\\classes\\Main.class"));
		TaskBean taskBean = taskPusher.createTaskBean(classFiles, Collections.emptyList(), null);
		taskPusher.pushTask(taskBean);
		taskPusher.pullResult(taskBean);
		MqUtil.deleteResultExchange(taskBean.getTaskId());
	}

	@Test
	void pushTask() {
	}

	@Test
	void pullResult() {
	}
}