package com.gs.stormtifao.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Bean
    public Queue pessoaPerdidaQueue() {
        return new Queue("pessoa-perdida", false);
    }
}
