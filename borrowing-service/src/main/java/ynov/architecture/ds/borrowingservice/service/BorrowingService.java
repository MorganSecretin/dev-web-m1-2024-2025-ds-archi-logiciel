package ynov.architecture.ds.borrowingservice.service;

import ynov.architecture.ds.borrowingservice.entity.Borrowing;
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

    // private static final Logger logger = LoggerFactory.getLogger(BorrowingService.class);

    @Autowired
    private BorrowingRepository borrowingRepository;

    public List<Borrowing> getAllBorrowings() {
        return borrowingRepository.findAll();
    }

    public Borrowing getBorrowingById(Long id) {
        return borrowingRepository.findById(id).orElseThrow(() -> new RuntimeException("Borrowing not found"));
    }

    public Borrowing createBorrowing(Borrowing borrowing) {
        return this.borrowingRepository.save(borrowing);
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
