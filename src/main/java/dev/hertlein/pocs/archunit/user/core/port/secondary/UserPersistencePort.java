package dev.hertlein.pocs.archunit.user.core.port.secondary;

import dev.hertlein.pocs.archunit.user.core.model.User;
import dev.hertlein.pocs.archunit.user.core.model.UserId;

import java.util.List;

public interface UserPersistencePort {
    void createUser(User user);

    void updateUser(User user);

    void deleteUser(UserId userId);

    void deleteAllUsers();

    User getUser(UserId userId);

    List<User> getAllUsers();
}
