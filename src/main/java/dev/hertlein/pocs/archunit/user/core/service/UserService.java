package dev.hertlein.pocs.archunit.user.core.service;

import dev.hertlein.pocs.archunit.user.core.model.User;
import dev.hertlein.pocs.archunit.user.core.model.UserId;
import dev.hertlein.pocs.archunit.user.core.model.UserName;
import dev.hertlein.pocs.archunit.user.core.port.primary.UserApiPort;
import dev.hertlein.pocs.archunit.user.core.port.secondary.UserPersistencePort;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService implements UserApiPort {

    private final UserValidator userValidator;
    private final UserFactory userFactory;
    private final UserIdService userIdService;
    private final UserPersistencePort userPersistence;

    @Override
    public User createUser(UserName userName) {
        userValidator.validateUserName(userName);
        UserId userId = userIdService.generateUserId();
        User user = userFactory.create(userId, userName);
        userPersistence.createUser(user);
        return user;
    }

    @Override
    public void updateUser(User user) {
        userValidator.validateUser(user);
        userPersistence.updateUser(user);
    }

    @Override
    public void deleteUser(UserId userId) {
        userPersistence.deleteUser(userId);
    }

    @Override
    public void deleteAllUsers() {
        userPersistence.deleteAllUsers();
    }

    @Override
    public User getUser(UserId userId) {
        return userPersistence.getUser(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userPersistence.getAllUsers();
    }
}
