
package com.bootme.app;


import com.bootme.app.util.redis.RedisUtil;
import org.apache.shiro.util.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestActiveMq1 {

    private static Logger logger = LoggerFactory.getLogger(TestActiveMq1.class);

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testRedis(){
        String key="R_KEY_20190211_TEMP";
        redisUtil.set(key,"xxxx");
        String value = (String)redisUtil.get(key);
        logger.debug("value="+value);

        Assert.hasText(value);
    }
/*
    @Autowired
    private QueueProducer producer;

    @Test
    public void contextLoads() throws InterruptedException {
        Destination destination = new ActiveMQQueue("mytest.queue");

        for (int i = 0; i < 100; i++) {
            producer.sendMessage(destination, "myname1111 is axxxxx!!!");
        }
    }
*/

}
