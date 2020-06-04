package top.summer1121.elastic_computing.computing_unit;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {"top.summer1121.elastic_computing.*"})
public class ComputingUnitApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ComputingUnitApplication.class, args);

//		MqListener mqListener = (MqListener) SpringUtil.getBean("MqListener");
//		Class mqListenerClazz = mqListener.getClass();
//		RabbitListener listener = (RabbitListener) mqListenerClazz.getAnnotation(RabbitListener.class);
//		System.out.println(listener.queues());
//		new ComputingUnitApplication().invoke();
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
