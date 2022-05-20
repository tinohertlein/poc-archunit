package dev.hertlein.pocs.archunit.user.adapter.secondary.persistence.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserEntity {

    private String id;
    private String firstName;
    private String lastName;
}
