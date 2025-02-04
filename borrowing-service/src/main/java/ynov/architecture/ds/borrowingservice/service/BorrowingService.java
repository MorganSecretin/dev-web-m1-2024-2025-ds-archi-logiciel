package ynov.architecture.ds.borrowingservice.service;

import ynov.architecture.ds.borrowingservice.client.RestClient;
import ynov.architecture.ds.borrowingservice.dto.BookDto;
import ynov.architecture.ds.borrowingservice.dto.UserDto;
import ynov.architecture.ds.borrowingservice.entity.Borrowing;
import ynov.architecture.ds.borrowingservice.kafka.BorrowingKafkaProducer;
import ynov.architecture.ds.borrowingservice.repository.BorrowingRepository;
import ynov.architecture.ds.borrowingservice.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class BorrowingService {
    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private BorrowingKafkaProducer borrowingKafkaProducer;

    @Autowired
    private RestClient restClient;

    public List<Borrowing> getAllBorrowings() {
        return borrowingRepository.findAll();
    }

    public Borrowing getBorrowingById(Long id) {
        return borrowingRepository.findById(id).orElseThrow(() -> new RuntimeException("Borrowing not found"));
    }

    public Borrowing createBorrowing(Borrowing borrowing) {
        // Verifier que c'est possible de créer un emprunt
        UserDto user = restClient.getUser(borrowing.getUserId());
        System.out.println("User found: " + user);

        BookDto book = restClient.getBook(borrowing.getBookId());
        System.out.println("Book found: " + book);

        if (book.isAvailable()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book " + book.getId() + " is not available");
        }

        Borrowing newBorrowing = this.borrowingRepository.save(borrowing);
        borrowingKafkaProducer.sendBorrowingCreateEvent(newBorrowing.getBookId(), newBorrowing.getUserId());
        return null;
    }

    public Borrowing updateBorrowing(Long id, Borrowing borrowing) {
        Borrowing existingBorrowing = borrowingRepository.findById(id).orElse(null);
        if (existingBorrowing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Borrowing " + id + " not found");
        }

        copyNonNullProperties(borrowing, existingBorrowing);

        return borrowingRepository.save(existingBorrowing);
    }

    public Borrowing saveBorrowing(Borrowing borrowing) {
        return borrowingRepository.save(borrowing);
    }

    public void deleteBorrowing(Long id) {
        borrowingRepository.deleteById(id);
    }

    private void copyNonNullProperties(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }

        for (Field field : source.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(source);
                if (value != null) {
                    field.set(target, value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error copying properties", e);
            }
        }
    }
}
