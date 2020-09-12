package com.example.studyrabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 返回ConnectionFactory和Connection实例
 */
public class RabbitFactoryBuilder {

    private static class BasicConfig {

        private static final String USER_NAME = "admin";
        private static final String PASSWORD = "123456";
        private static final String HOST = "127.0.0.1";
        private static final String VIRTUAL_HOST = "/";
        private static final int PORT = 5672;
    }


    private static ConnectionFactory buildConnectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername(BasicConfig.USER_NAME);
        connectionFactory.setPassword(BasicConfig.PASSWORD);
        connectionFactory.setHost(BasicConfig.HOST);
        connectionFactory.setPort(BasicConfig.PORT);
        connectionFactory.setVirtualHost(BasicConfig.VIRTUAL_HOST);
        return connectionFactory;
    }

    public static Connection getConnection() throws IOException, TimeoutException {
        return buildConnectionFactory().newConnection();
    }

}
