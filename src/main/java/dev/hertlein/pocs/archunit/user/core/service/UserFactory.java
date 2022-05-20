package dev.hertlein.pocs.archunit.user.core.service;

import dev.hertlein.pocs.archunit.user.core.model.User;
import dev.hertlein.pocs.archunit.user.core.model.UserId;
import dev.hertlein.pocs.archunit.user.core.model.UserName;

public class UserFactory {

    User create(UserId userId, UserName userName) {
        return new User(userId, userName);
    }
}
