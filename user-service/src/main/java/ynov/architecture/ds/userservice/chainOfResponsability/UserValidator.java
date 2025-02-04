package ynov.architecture.ds.userservice.chainOfResponsability;

import ynov.architecture.ds.userservice.entity.User;

public interface UserValidator {
    void setNextValidator(UserValidator nextValidator);
    void validate(User user) throws Exception;
}
