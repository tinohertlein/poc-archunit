package dev.hertlein.pocs.archunit.user.core.port.primary;

import dev.hertlein.pocs.archunit.user.core.model.User;
import dev.hertlein.pocs.archunit.user.core.model.UserId;
import dev.hertlein.pocs.archunit.user.core.model.UserName;
import java.util.List;

public interface UserApiPort {

    User createUser(UserName userName);

    void updateUser(User user);

    void deleteUser(UserId userId);

    void deleteAllUsers();

    User getUser(UserId userId);

    List<User> getAllUsers();
}
