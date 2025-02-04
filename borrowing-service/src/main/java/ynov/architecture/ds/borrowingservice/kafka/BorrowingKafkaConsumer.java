package ynov.architecture.ds.borrowingservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ynov.architecture.ds.borrowingservice.service.BorrowingService;

@Service
public class BorrowingKafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(BorrowingKafkaConsumer.class);

    @Autowired
    private BorrowingService borrowingService;

    @KafkaListener(topics = "user-events", groupId = "borrowing-group")
    public void consumeUsersEvents(String message) {
        System.out.println("Consuming borrowing event");
        logger.info("Consumed message: {}", message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(message);

            if (jsonNode.get("event").asText().equals("USER_DELETED")) {
                Long userId = jsonNode.get("userId").asLong();
                borrowingService.processUserDeleted(userId);
            }
        } catch (JsonProcessingException e) {
            logger.error("Error processing message", e);
        }
    }

    @KafkaListener(topics = "book-events", groupId = "borrowing-group")
    public void consumeBooksEvents(String message) {
        System.out.println("Consuming borrowing event");
        logger.info("Consumed message: {}", message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(message);

            if (jsonNode.get("event").asText().equals("BOOK_DELETED")) {
                Long bookId = jsonNode.get("bookId").asLong();
                borrowingService.processBookDeleted(bookId);
            }
        } catch (JsonProcessingException e) {
            logger.error("Error processing message", e);
        }
    }
}
