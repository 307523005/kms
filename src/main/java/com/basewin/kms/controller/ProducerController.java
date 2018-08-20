package com.basewin.kms.controller;

import com.basewin.kms.servlet.redis.RedisUtils;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jms.Destination;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * mq消息推送12
 */
@Controller
@RequestMapping("/ProducerController")
public class ProducerController {
    // @Autowired
    // private Producer alarmProducer;

    @Autowired
    private RedisUtils redisUtils;


    @RequestMapping("/sendMsg")
    @ResponseBody
    public void send(String msg)throws Exception {
        redisUtils.set("niuhao", "1", 10, TimeUnit.SECONDS);

    }

}