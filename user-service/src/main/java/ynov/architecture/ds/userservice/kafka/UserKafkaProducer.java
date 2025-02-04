package ynov.architecture.ds.userservice.kafka;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserKafkaProducer {
    private static final Logger logger = LoggerFactory.getLogger(UserKafkaProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic.user:user-events}")
    private String topic;

    public UserKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserDeletedEvent(Long userId) {
        String event = String.format("{\"event\":\"USER_DELETED\",\"userId\":\"%s\"}", userId);
        logger.info("Producing user deleted event: {}", event);
        kafkaTemplate.send(topic, event);
    }
}

