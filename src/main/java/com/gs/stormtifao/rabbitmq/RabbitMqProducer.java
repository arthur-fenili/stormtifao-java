package com.gs.stormtifao.rabbitmq;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqProducer {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMqProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendPessoaPerdidaJson(String json) {
        rabbitTemplate.convertAndSend("pessoa-perdida", json);
    }
}
