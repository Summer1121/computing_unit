package top.summer1121.elastic_computing.computing_unit.core.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.summer1121.elastic_computing.common.entity.resourceBeans.ClassResourceBean;
import top.summer1121.elastic_computing.common.entity.resourceBeans.ResourceBean;
import top.summer1121.elastic_computing.common.entity.taskBeans.TaskBean;
import top.summer1121.elastic_computing.common.util.UuidUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * 描述：
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/16
 */
@ComponentScan("top.summer1121.elastic_computing.*")
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class TaskHandlerTest {

//	TaskHandler taskHandler;

	@Test
	void invoke() throws Exception {
		ResourceBean resource = new ClassResourceBean();
		resource.setName("Main");
		resource.setSign("0d3d18788b5d5445a3f964e96d2920e4");
		resource.setUrl("https://elastic-computing-data-center.oss-cn-beijing.aliyuncs.com/class/0d3d18788b5d5445a3f964e96d2920e4.class");

		TaskBean taskBean = new TaskBean();
		taskBean.setClassResource(new ArrayList<ClassResourceBean>() {
			{
				add((ClassResourceBean) resource);
			}
		});
		taskBean.setTaskId(UuidUtil.getUuid());
		TaskHandler taskHandler = new TaskHandler(taskBean);

		new Thread(() -> {
			InputStream in = new InputStream() {
				private byte[] bits = "10\n".getBytes();
				private int index = 0;

				@Override
				public int read() throws IOException {
					int clone = index++;
					if (index > bits.length) {
						index = 0;
						return '\n';
					} else {
						return bits[clone];
					}
				}
			};
			System.setIn(in);
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
		taskHandler.invoke();
	}
}