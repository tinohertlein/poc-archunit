package dev.hertlein.pocs.archunit.user.core.service;

import com.google.common.base.Strings;
import dev.hertlein.pocs.archunit.user.core.model.User;
import dev.hertlein.pocs.archunit.user.core.model.UserName;

public class UserValidator {

    private static final String EXCEPTION_TEMPLATE = "%s must neither be null nor empty!";

    void validateUserName(UserName userName) {
        validateString(userName.firstName(), "FirstName");
        validateString(userName.lastName(), "LastName");
    }

    void validateUser(User user) {
        final String USER_ID = "UserId";

        if (user.userId() == null) {
            throw new IllegalArgumentException(EXCEPTION_TEMPLATE.formatted(USER_ID));
        }

        validateString(user.userId().userId(), USER_ID);
        validateUserName(user.userName());
    }

    private void validateString(String toValidate, String identifierForException) {
        if (Strings.isNullOrEmpty(toValidate)) {
            throw new IllegalArgumentException(EXCEPTION_TEMPLATE.formatted(identifierForException));
        }
    }
}
