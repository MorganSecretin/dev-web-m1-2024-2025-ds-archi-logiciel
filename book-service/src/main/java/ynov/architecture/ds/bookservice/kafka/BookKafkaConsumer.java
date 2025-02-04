package ynov.architecture.ds.bookservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ynov.architecture.ds.bookservice.service.BookService;

@Service
public class BookKafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(BookKafkaConsumer.class);

    @Autowired
    private BookService bookService;

    @KafkaListener(topics = "borrowing-events", groupId = "book-group")
    public void consumeBorrowingEvents(String message) {
        System.out.println("Consuming borrowing event");
        logger.info("Consumed message: {}", message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(message);

            if (jsonNode.get("event").asText().equals("BORROWING_CREATED")) {
                Long bookId = jsonNode.get("bookId").asLong();
                bookService.processBorrowingCreated(bookId);
            }
            if (jsonNode.get("event").asText().equals("BORROWING_RETURNED")) {
                Long bookId = jsonNode.get("bookId").asLong();
                bookService.processBorrowingEnding(bookId);
            }
        } catch (JsonProcessingException e) {
            logger.error("Error processing message", e);
        }
    }
}
