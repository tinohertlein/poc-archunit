package dev.hertlein.pocs.archunit.user.adapter.primary.api.mapper;

import dev.hertlein.pocs.archunit.user.adapter.primary.api.dto.UserDto;
import dev.hertlein.pocs.archunit.user.core.model.User;
import dev.hertlein.pocs.archunit.user.core.model.UserName;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDtoMapper {

    private final UserIdMapper userIdMapper;

    public User fromDto(UserDto dto) {
        return new User(userIdMapper.fromString(dto.getUserId()), new UserName(dto.getFirstName(), dto.getLastName()));
    }

    public UserDto toDto(User user) {
        return new UserDto(userIdMapper.toString(user.userId()), user.userName().firstName(), user.userName().lastName());
    }
}
