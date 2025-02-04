package ynov.architecture.ds.borrowingservice.kafka;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BorrowingKafkaProducer {
    private static final Logger logger = LoggerFactory.getLogger(BorrowingKafkaProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic.borrowing:borrowing-events}")
    private String topic;

    public BorrowingKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBorrowingCreateEvent(Long bookId, Long userId) {
        System.out.println("Sending borrowing create event");
        String event = String.format("{\"event\":\"BORROWING_CREATED\",\"bookId\":\"%s\", \"userId\":\"%s\"}", bookId, userId);
        logger.info("Producing borrowing created event: {}", event);
        kafkaTemplate.send(topic, event);
    }

    public void sendBorrowingReturnEvent(Long bookId, Long userId) {
        String event = String.format("{\"event\":\"BORROWING_RETURNED\",\"bookId\":\"%s\", \"userId\":\"%s\"}", bookId, userId);
        logger.info("Producing borrowing returned event: {}", event);
        kafkaTemplate.send(topic, event);
    }
}
