package dev.hertlein.pocs.archunit.user.adapter.primary.api;

import dev.hertlein.pocs.archunit.user.adapter.primary.api.dto.UserDto;
import dev.hertlein.pocs.archunit.user.adapter.primary.api.dto.UserNameDto;
import dev.hertlein.pocs.archunit.user.adapter.primary.api.mapper.UserDtoMapper;
import dev.hertlein.pocs.archunit.user.adapter.primary.api.mapper.UserIdMapper;
import dev.hertlein.pocs.archunit.user.adapter.primary.api.mapper.UserNameDtoMapper;
import dev.hertlein.pocs.archunit.user.core.port.primary.UserApiPort;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserApiAdapter {

    private final UserApiPort userApiPort;
    private final UserDtoMapper dtoMapper;
    private final UserNameDtoMapper nameDtoMapper;
    private final UserIdMapper idMapper;

    public UserDto create(UserNameDto user) {
        return dtoMapper.toDto(userApiPort.createUser(nameDtoMapper.fromDto(user)));
    }

    public void update(UserDto user) {
        userApiPort.updateUser(dtoMapper.fromDto(user));
    }

    public void delete(String userId) {
        userApiPort.deleteUser(idMapper.fromString(userId));
    }

    public void deleteAll() {
        userApiPort.deleteAllUsers();
    }

    public UserDto getAll(String userId) {
        return dtoMapper.toDto(userApiPort.getUser(idMapper.fromString(userId)));
    }

    public List<UserDto> getAll() {
        return userApiPort.getAllUsers().stream().map(dtoMapper::toDto).collect(Collectors.toList());
    }
}
