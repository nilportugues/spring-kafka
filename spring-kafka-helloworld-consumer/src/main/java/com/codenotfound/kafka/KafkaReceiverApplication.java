package com.codenotfound.kafka;

import com.codenotfound.kafka.consumer.Receiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class KafkaReceiverApplication implements ApplicationRunner {

    @Autowired
    private final Receiver receiver;

    public KafkaReceiverApplication(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        receiver.getLatch();
    }
}
