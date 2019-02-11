package com.bootme.app.mq.activemq;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;


public class QueueConsumer {

    private static Logger logger = LoggerFactory.getLogger(QueueConsumer.class);

    // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
    @JmsListener(destination = "mytest.queue",concurrency = "3")
    public void receiveQueue(String text) {
        logger.debug("Consumer收到的报文为:" + text);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Consumer收到的报文为:" + text );
    }
}