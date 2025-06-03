package com.gs.stormtifao.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqConsumer {

    @RabbitListener(queues = "pessoa-perdida")
    public void listen(String json) {

        System.out.println("Nova pessoa perdida cadastrada (RabbitMQ): " + json);
    }
}
