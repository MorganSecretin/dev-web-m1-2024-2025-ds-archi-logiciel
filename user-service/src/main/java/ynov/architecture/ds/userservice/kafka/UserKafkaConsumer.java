package ynov.architecture.ds.userservice.kafka;

import org.springframework.stereotype.Service;

@Service
public class UserKafkaConsumer {

    // private static final Logger logger = LoggerFactory.getLogger(
    //         UserKafkaConsumer.class);

    // @Autowired
    // private UserService userService;

    // @KafkaListener(topics = "user-events", groupId = "user-group")
    // public void consumeUserEvents(String message) {
    //     System.out.println("Consuming user event");
    //     logger.info("Consumed message: {}", message);
    //     try {
    //         ObjectMapper objectMapper = new ObjectMapper();
    //         JsonNode jsonNode = objectMapper.readTree(message);

    //         if (jsonNode.get("event").asText().equals("USER_CREATED")) {
    //             Long userId = jsonNode.get("userId").asLong();
    //             userService.processBorrowingCreated(userId);
    //         }
    //         if (jsonNode.get("event").asText().equals("USER_UPDATED")) {
    //             Long userId = jsonNode.get("userId").asLong();
    //             userService.processUserUpdated(userId);
    //         }
    //     } catch (JsonProcessingException e) {
    //         logger.error("Error processing message", e);
    //     }
    // }
}
