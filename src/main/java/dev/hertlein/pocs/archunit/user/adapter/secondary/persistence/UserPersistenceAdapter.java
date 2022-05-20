package dev.hertlein.pocs.archunit.user.adapter.secondary.persistence;

import dev.hertlein.pocs.archunit.user.adapter.secondary.persistence.entity.UserEntity;
import dev.hertlein.pocs.archunit.user.adapter.secondary.persistence.mapper.UserEntityMapper;
import dev.hertlein.pocs.archunit.user.core.model.User;
import dev.hertlein.pocs.archunit.user.core.model.UserId;
import dev.hertlein.pocs.archunit.user.core.port.secondary.UserPersistencePort;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

    private final Map<String, UserEntity> repository = new HashMap<>();

    private final UserEntityMapper entityMapper;

    @Override
    public void createUser(User user) {
        UserEntity userEntity = entityMapper.toEntity(user);
        repository.put(userEntity.getId(), userEntity);
    }

    @Override
    public void updateUser(User user) {
        UserEntity userEntity = entityMapper.toEntity(user);
        repository.put(userEntity.getId(), userEntity);
    }

    @Override
    public void deleteUser(UserId userId) {
        repository.remove(userId.userId());
    }

    @Override
    public void deleteAllUsers() {
        repository.clear();
    }

    @Override
    public User getUser(UserId userId) {
        return entityMapper.fromEntity(repository.get(userId.userId()));
    }

    @Override
    public List<User> getAllUsers() {
        return repository.values().stream().map(entityMapper::fromEntity).collect(Collectors.toList());
    }
}
