package top.summer1121.elastic_computing.common.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
 
    /**
     * 用json方式替换默认的序列化
     * @return
     */
    @Bean
    public MessageConverter messageConverter()
    {
        return  new Jackson2JsonMessageConverter();
    }
}