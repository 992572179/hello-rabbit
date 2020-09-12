package com.example.studyrabbitmq.returnlistener;

import com.example.studyrabbitmq.utils.RabbitFactoryBuilder;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息提供者
 * 通道开启confirm机制，同时指定confirmListener
 */
public class ProviderWithReturnListener {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitFactoryBuilder.getConnection();
        Channel channel = connection.createChannel();


        String exchange = "test_return_exchange";
        String routingKey = "return.svc";
        String errorRoutingKey = "abc.def";
        String msg = "hello rabbitMQ!";

        //自定义一个ReturnListener
        channel.addReturnListener((replyCode, replyText, exchange1, routingKey1, properties, body) -> {
            System.err.println("---------handle  return----------");
            System.err.println("replyCode: " + replyCode);
            System.err.println("replyText: " + replyText);
            System.err.println("exchange: " + exchange1);
            System.err.println("routingKey: " + routingKey1);
            System.err.println("properties: " + properties);
            System.err.println("body: " + new String(body));
        });

        /**
         * @param mandatory 若设置为true，监听器在消息路由不可达的时候进行处理
         *                  若为false，则MQ会删除该消息
         */
        //channel.basicPublish(exchange, routingKey, true,null, msg.getBytes());
        channel.basicPublish(exchange, errorRoutingKey, true, null, msg.getBytes());

    }
}
