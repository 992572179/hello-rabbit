package com.example.studyrabbitmq.returnlistener;

import com.example.studyrabbitmq.utils.MyConsumer;
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

        String exchange = "test_return_exchange";
        String routingKey = "return.#";
        String queue = "return_queue";

        channel.exchangeDeclare(exchange, "topic", true, false, null);
        channel.queueDeclare(queue, true, false, false, null);
        //将队列和交换机绑定，并指定routingKey
        channel.queueBind(queue, exchange, routingKey);

        //方法的返回值为服务端自动生成的 consumerTag
        String s = channel.basicConsume(queue, true, new MyConsumer(channel));
        System.out.println(s);
    }
}
