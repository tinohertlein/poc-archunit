package dev.hertlein.pocs.archunit.user.adapter.primary.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

    private String userId;
    private String firstName;
    private String lastName;
}
