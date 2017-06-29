package com.codenotfound.kafka;

import com.codenotfound.kafka.producer.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerApplication implements ApplicationRunner {

    @Autowired
    private Sender sender;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        int i = 1;
        while(true) {
            System.out.println(i);
            sender.send("hello_world_topic", "THIS IS A TESTTT");
            Thread.sleep(1);
            i++;
        }
    }
}
