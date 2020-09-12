package com.example.studyrabbitmq.confirm;

import com.example.studyrabbitmq.utils.RabbitFactoryBuilder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息提供者
 * 通道开启confirm机制，同时指定confirmListener
 */
public class ProviderWithConfirm {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitFactoryBuilder.getConnection();
        Channel channel = connection.createChannel();

        //开启消息的confirm机制
        channel.confirmSelect();

        String exchange = "test_confirm_exchange";
        String routingKey = "confirm.svc";
        String msg = "hello rabbitMQ!";

        for (int i = 0; i < 5; i++) {
            //使用通道发送消息，需指定 exchange routingKey等基本信息
            channel.basicPublish(exchange, routingKey, null, msg.getBytes());

            //确认监听
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) {
                    System.out.println("ack....");
                }

                @Override
                public void handleNack(long deliveryTag, boolean multiple) {
                    System.out.println("no ack....");
                }
            });
        }

        channel.close();
        connection.close();
    }
}
