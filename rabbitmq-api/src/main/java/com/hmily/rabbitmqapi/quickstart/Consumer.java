package com.hmily.rabbitmqapi.quickstart;

import com.hmily.rabbitmqapi.common.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import lombok.extern.slf4j.Slf4j;
import com.rabbitmq.client.QueueingConsumer.Delivery;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 快速开始：消费者
 */
@Slf4j
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(RabbitMQConfig.RABBITMQ_HOST);
        connectionFactory.setPort(RabbitMQConfig.RABBITMQ_PORT);
        connectionFactory.setVirtualHost(RabbitMQConfig.RABBITMQ_DEFAULT_VIRTUAL_HOST);

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
//          queueName， durable, exclusive, autoDelete, arguments
        channel.queueDeclare("test1001", true, false, false, null);
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
//         queueName,  autoAck, Consumer callback
        channel.basicConsume("test1001", true, queueingConsumer);
        log.info("消费端启动啦~");
        while (true) {
            Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());
            log.info("消费端接收到：{}", msg);
        }
    }

}
