package ynov.architecture.ds.userservice.service;

import ynov.architecture.ds.userservice.chainOfResponsability.EmailValidator;
import ynov.architecture.ds.userservice.chainOfResponsability.MaxBooksValidator;
import ynov.architecture.ds.userservice.chainOfResponsability.MembershipValidator;
import ynov.architecture.ds.userservice.chainOfResponsability.UserValidator;
import ynov.architecture.ds.userservice.entity.User;
import ynov.architecture.ds.userservice.repository.UserRepository;
import ynov.architecture.ds.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class UserService {

    // private static final Logger logger =
    // LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User createUser(User user) {
        try {
            UserValidator emailValidator = new EmailValidator();
            UserValidator membershipValidator = new MembershipValidator();
            UserValidator maxBooksValidator = new MaxBooksValidator();
            emailValidator.setNextValidator(membershipValidator);
            membershipValidator.setNextValidator(maxBooksValidator);

            emailValidator.validate(user);

            return this.userRepository.save(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + id + " not found");
        }

        copyNonNullProperties(user, existingUser);

        return userRepository.save(existingUser);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
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

    // public void processBorrowingCreated(Long userId) {
    // User user = userRepository.findById(userId).orElse(null);
    // if (user == null) {
    // throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + userId + "
    // not found");
    // }
    // this.userRepository.save(user);
    // }

    // public void processBorrowingEnding(Long userId) {
    // User user = userRepository.findById(userId).orElse(null);
    // if (user == null) {
    // throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + userId + "
    // not found");
    // }

    // this.userRepository.save(user);
    // }
}
