package dev.hertlein.pocs.archunit.user.adapter.primary.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserNameDto {

    private String firstName;
    private String lastName;
}
