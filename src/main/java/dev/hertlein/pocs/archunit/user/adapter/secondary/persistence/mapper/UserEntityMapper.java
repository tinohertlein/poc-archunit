package dev.hertlein.pocs.archunit.user.adapter.secondary.persistence.mapper;

import dev.hertlein.pocs.archunit.user.adapter.secondary.persistence.entity.UserEntity;
import dev.hertlein.pocs.archunit.user.core.model.User;
import dev.hertlein.pocs.archunit.user.core.model.UserId;
import dev.hertlein.pocs.archunit.user.core.model.UserName;

public class UserEntityMapper {

    public User fromEntity(UserEntity entity) {
        return new User(new UserId(entity.getId()), new UserName(entity.getFirstName(), entity.getLastName()));
    }

    public UserEntity toEntity(User user) {
        return new UserEntity(user.userId().userId(), user.userName().firstName(), user.userName().lastName());
    }
}
