package ynov.architecture.ds.userservice.chainOfResponsability;

import ynov.architecture.ds.userservice.entity.User;

public class MembershipValidator implements UserValidator {

    private UserValidator nextValidator;

    @Override
    public void setNextValidator(UserValidator nextValidator) {
        this.nextValidator = nextValidator;
    }

    @Override
    public void validate(User user) throws Exception {
        if (user.getMembershipType() == null) {
            throw new Exception("Membership type is required");
        }
        if (nextValidator != null) {
            nextValidator.validate(user);
        }
    }
}