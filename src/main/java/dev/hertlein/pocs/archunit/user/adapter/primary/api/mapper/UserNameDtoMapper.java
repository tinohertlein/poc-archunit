package dev.hertlein.pocs.archunit.user.adapter.primary.api.mapper;

import dev.hertlein.pocs.archunit.user.adapter.primary.api.dto.UserNameDto;
import dev.hertlein.pocs.archunit.user.core.model.UserName;

public class UserNameDtoMapper {

    public  UserName fromDto(UserNameDto dto) {
        return new UserName(dto.getFirstName(), dto.getLastName());
    }

    public  UserNameDto toDto(UserName user) {
        return new UserNameDto(user.firstName(), user.lastName());
    }
}
