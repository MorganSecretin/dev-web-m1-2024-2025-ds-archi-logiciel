package ynov.architecture.ds.bookservice.kafka;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookKafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(BookKafkaProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic.book:book-events}")
    private String topic;

    public BookKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBookUpdatedEvent(Long bookId) {
        String event = String.format("{\"event\":\"BOOK_UPDATED\",\"bookId\":\"%s\"}", bookId);
        logger.info("Producing book updated event: {}", event);
        kafkaTemplate.send(topic, event);
    }

    public void sendBookDeletedEvent(Long bookId) {
        String event = String.format("{\"event\":\"BOOK_DELETED\",\"bookId\":\"%s\"}", bookId);
        logger.info("Producing book deleted event: {}", event);
        kafkaTemplate.send(topic, event);
    }
}

