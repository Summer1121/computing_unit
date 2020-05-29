package top.summer1121.elastic_computing.client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import top.summer1121.elastic_computing.common.util.MqUtil;

@SpringBootApplication
@ComponentScan({"top.summer1121.elastic_computing.*"})
public class ElasticComputingClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticComputingClientApplication.class, args);
		MqUtil.createTaskExchange();
	}

}
