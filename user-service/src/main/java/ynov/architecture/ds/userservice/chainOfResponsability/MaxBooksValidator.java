package ynov.architecture.ds.userservice.chainOfResponsability;

import ynov.architecture.ds.userservice.entity.User;
import ynov.architecture.ds.userservice.entity.enums.EMembershipType;

public class MaxBooksValidator implements UserValidator {

    private UserValidator nextValidator;

    @Override
    public void setNextValidator(UserValidator nextValidator) {
        this.nextValidator = nextValidator;
    }

    @Override
    public void validate(User user) throws Exception {
        if (user.getMembershipType() == EMembershipType.PREMIUM) {
            user.setNombreMaxEmprunt(7);
        } else {
            user.setNombreMaxEmprunt(5);
        }
        if (nextValidator != null) {
            nextValidator.validate(user);
        }
    }
}