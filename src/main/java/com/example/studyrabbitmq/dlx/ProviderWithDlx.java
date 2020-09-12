package com.example.studyrabbitmq.dlx;

import com.example.studyrabbitmq.utils.RabbitFactoryBuilder;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息提供者
 * 先启动生产者，发送消息到交换机上，此时没有consumer消费消息，
 * 到了过期时间，消息将自动移入死信队列中
 */
public class ProviderWithDlx {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitFactoryBuilder.getConnection();
        Channel channel = connection.createChannel();

        String exchange = "test_dlx_exchange";
        String routingKey = "dlx.svc";
        String msg = "hello rabbitMQ!";

        for (int i = 0; i < 1; i++) {
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .deliveryMode(2)
                    .contentEncoding("UTF-8")
                    .expiration("10000")
                    .build();
            /**
             * @param mandatory 若设置为true，监听器在消息路由不可达的时候进行处理
             *                  若为false，则MQ会删除该消息
             */
            channel.basicPublish(exchange, routingKey, true, properties, msg.getBytes());


        }
    }
}
