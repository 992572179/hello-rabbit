package com.example.studyrabbitmq.qos;

import com.example.studyrabbitmq.utils.RabbitFactoryBuilder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息提供者
 * 通道开启confirm机制，同时指定confirmListener
 */
public class ProviderWithQos {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitFactoryBuilder.getConnection();
        Channel channel = connection.createChannel();


        String exchange = "test_qos_exchange";
        String routingKey = "qos.svc";
        String msg = "hello rabbitMQ!";


        for (int i = 0; i < 5; i++) {
            channel.basicPublish(exchange, routingKey, true, null, msg.getBytes());
        }

    }
}
