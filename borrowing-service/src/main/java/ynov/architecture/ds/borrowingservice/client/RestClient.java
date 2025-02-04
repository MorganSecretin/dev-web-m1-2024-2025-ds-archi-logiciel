package ynov.architecture.ds.borrowingservice.client;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import ynov.architecture.ds.borrowingservice.dto.BookDto;
import ynov.architecture.ds.borrowingservice.dto.UserDto;

@Component
public class RestClient {
    @Autowired
    private RestTemplate restTemplate;

    private final String SERVICE_USER_URL = "http://localhost:8090/users";
    private final String SERVICE_BOOK_URL = "http://localhost:8090/books";

    public UserDto getUser(Long userId) {
        try {
            return restTemplate.getForObject(SERVICE_USER_URL + "/" + userId, UserDto.class);
        } catch (RestClientException e){
            throw new ResourceNotFoundException("User with id : " + userId + "not found");
        }
    }

    public BookDto getBook(Long bookId) {
        try {
            return restTemplate.getForObject(SERVICE_BOOK_URL + "/" + bookId, BookDto.class);
        } catch (RestClientException e){
            throw new ResourceNotFoundException("Book with id : " + bookId + "not found");
        }
    }
}
