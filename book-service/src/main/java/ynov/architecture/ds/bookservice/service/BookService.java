package ynov.architecture.ds.bookservice.service;

import ynov.architecture.ds.bookservice.entity.Book;
import ynov.architecture.ds.bookservice.kafka.BookKafkaProducer;
import ynov.architecture.ds.bookservice.repository.BookRepository;
import ynov.architecture.ds.bookservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class BookService {

    // private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookKafkaProducer bookKafkaProducer;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public Book createBook(Book book) {
        return this.bookRepository.save(book);
    }

    public Book updateBook(Long id, Book book) {
        Book existingBook = bookRepository.findById(id).orElse(null);
        if (existingBook == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book " + id + " not found");
        }

        copyNonNullProperties(book, existingBook);

        Book updatedBook = bookRepository.save(existingBook);
        bookKafkaProducer.sendBookUpdatedEvent(id);
        return updatedBook;
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
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
