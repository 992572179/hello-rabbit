package com.example.studyrabbitmq.dlx;

import com.example.studyrabbitmq.utils.MyConsumer;
import com.example.studyrabbitmq.utils.RabbitFactoryBuilder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 消息的消费者
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = RabbitFactoryBuilder.getConnection();
        Channel channel = connection.createChannel();

        String exchange = "test_dlx_exchange";
        String routingKey = "dlx.#";
        String queue = "dlx_queue";

        channel.exchangeDeclare(exchange, "topic", true, false, null);
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "real_dlx_exchange");
        //arguments，要设置到声明队列上
        channel.queueDeclare(queue, true, false, false, arguments);
        channel.queueBind(queue, exchange, routingKey);

        //要进行死信队列的声明:
        channel.exchangeDeclare("real_dlx_exchange", "topic", true, false, null);
        channel.queueDeclare("real_dlx_queue", true, false, false, null);
        channel.queueBind("real_dlx_queue", "real_dlx_exchange", "#");

        //方法的返回值为服务端自动生成的 consumerTag
        channel.basicConsume(queue, true, new MyConsumer(channel));
    }
}
