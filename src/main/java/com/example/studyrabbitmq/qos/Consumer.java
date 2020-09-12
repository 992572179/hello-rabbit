package com.example.studyrabbitmq.qos;

import com.example.studyrabbitmq.utils.RabbitFactoryBuilder;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息的消费者
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {

        Channel channel = RabbitFactoryBuilder.getConnection().createChannel();

        String exchange = "test_qos_exchange";
        String routingKey = "qos.#";
        String queue = "qos_queue";

        channel.exchangeDeclare(exchange, "topic", true, false, null);
        channel.queueDeclare(queue, true, false, false, null);

        channel.queueBind(queue, exchange, routingKey);

        channel.basicQos(0, 2, false);

        //关闭自动ack
        channel.basicConsume(queue, false, new MyConsumerNack(channel));
    }
}
