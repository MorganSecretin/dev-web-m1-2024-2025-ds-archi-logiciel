package ynov.architecture.ds.userservice.chainOfResponsability;

import ynov.architecture.ds.userservice.entity.User;

public class EmailValidator implements UserValidator {

    private UserValidator nextValidator;

    @Override
    public void setNextValidator(UserValidator nextValidator) {
        this.nextValidator = nextValidator;
    }

    @Override
    public void validate(User user) throws Exception {
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new Exception("Invalid email");
        }
        if (nextValidator != null) {
            nextValidator.validate(user);
        }
    }
}
