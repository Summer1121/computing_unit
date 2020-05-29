package top.summer1121.elastic_computing.computing_unit;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import top.summer1121.elastic_computing.common.constant.MqConstant;
import top.summer1121.elastic_computing.common.entity.resourceBeans.ClassResourceBean;
import top.summer1121.elastic_computing.common.entity.resourceBeans.ResourceBean;
import top.summer1121.elastic_computing.common.entity.taskBeans.TaskBean;
import top.summer1121.elastic_computing.common.entity.taskBeans.TaskResultBean;
import top.summer1121.elastic_computing.common.util.MqUtil;
import top.summer1121.elastic_computing.common.util.SpringUtil;
import top.summer1121.elastic_computing.computing_unit.core.impl.TaskHandler;
import top.summer1121.elastic_computing.common.util.UuidUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@SpringBootApplication
@ComponentScan({"top.summer1121.elastic_computing.*"})
public class ComputingUnitApplication {

	@Autowired
	RabbitTemplate rabbitTemplate;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ComputingUnitApplication.class, args);
//		new ComputingUnitApplication().invoke();
	}

	@RabbitListener(queues = MqConstant.TASK_QUEUE)
	void ListenAndInvoke(TaskBean task) throws Exception {
		TaskHandler taskHandler = new TaskHandler(task);
		TaskResultBean result = taskHandler.invoke();

		//返回计算结果
		rabbitTemplate.convertAndSend(MqConstant.RESULT_EXCHANGE, MqUtil.getResultQueueName(task.getTaskId()), result);
		return ;
	}

//	void invoke() throws Exception {
//		ResourceBean resource = new ClassResourceBean();
//		resource.setName("Main");
//		resource.setSign("0d3d18788b5d5445a3f964e96d2920e4");
//		resource.setUrl("https://elastic-computing-data-center.oss-cn-beijing.aliyuncs.com/class/0d3d18788b5d5445a3f964e96d2920e4.class");
//
//		TaskBean taskBean = new TaskBean();
//		taskBean.setClassResource(new ArrayList<ResourceBean>() {
//			{
//				add(resource);
//			}
//		});
//		taskBean.setTaskId(UuidUtil.getUuid());
//		TaskHandler taskHandler = new TaskHandler(taskBean);
//
//		new Thread(() -> {
//			InputStream in = new InputStream() {
//				private byte[] bits = "10\n".getBytes();
//				private int index = 0;
//
//				@Override
//				public int read() throws IOException {
//					int clone = index++;
//					if (index > bits.length) {
//						index = 0;
//						return '\n';
//					} else {
//						return bits[clone];
//					}
//				}
//			};
//			System.setIn(in);
//			try {
//				in.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}).start();
//		taskHandler.invoke();
//	}

}
