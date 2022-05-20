package dev.hertlein.pocs.archunit.user.core.service;

import dev.hertlein.pocs.archunit.user.core.model.User;
import dev.hertlein.pocs.archunit.user.core.model.UserId;
import dev.hertlein.pocs.archunit.user.core.model.UserName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

class UserValidatorTest {

    private static final UserName A_VALID_USER_NAME = new UserName("a-first-name", "a-last-name");
    private static final User A_VALID_USER = new User(new UserId("an-id"), A_VALID_USER_NAME);

    private final UserValidator userValidator = new UserValidator();

    @Nested
    class ValidateUserNameShould {

        @Test
        void notThrowExceptionIfIsValid() {
            assertThatNoException().isThrownBy(() ->
                    userValidator.validateUserName(A_VALID_USER_NAME)
            );
        }

        @ParameterizedTest
        @NullAndEmptySource
        void throwExceptionIfFirstNameIsInvalid(String firstName) {
            UserName invalidUserName = new UserName(firstName, A_VALID_USER_NAME.lastName());

            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> userValidator.validateUserName(invalidUserName))
                    .withMessageContaining("FirstName");
        }

        @ParameterizedTest
        @NullAndEmptySource
        void throwExceptionIfLastNameIsInvalid(String lastName) {
            UserName invalidUserName = new UserName(A_VALID_USER_NAME.firstName(), lastName);

            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> userValidator.validateUserName(invalidUserName))
                    .withMessageContaining("LastName");
        }
    }

    @Nested
    class ValidateUserShould {

        @Test
        void notThrowExceptionIfIsValid() {
            assertThatNoException().isThrownBy(() ->
                    userValidator.validateUser(A_VALID_USER)
            );
        }

        @ParameterizedTest
        @NullAndEmptySource
        void throwExceptionIfUserIdInUserIdIsInvalid(String userId) {
            User invalidUser = new User(new UserId(userId), A_VALID_USER_NAME);

            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> userValidator.validateUser(invalidUser))
                    .withMessageContaining("UserId");
        }

        @ParameterizedTest
        @NullSource
        void throwExceptionIfUserIdIsInvalid(UserId userId) {
            User invalidUser = new User(userId, A_VALID_USER_NAME);

            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> userValidator.validateUser(invalidUser))
                    .withMessageContaining("UserId");
        }
    }
}