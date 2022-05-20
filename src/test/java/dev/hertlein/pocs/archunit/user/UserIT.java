package dev.hertlein.pocs.archunit.user;

import dev.hertlein.pocs.archunit.user.adapter.primary.api.UserApiAdapter;
import dev.hertlein.pocs.archunit.user.adapter.primary.api.dto.UserDto;
import dev.hertlein.pocs.archunit.user.adapter.primary.api.dto.UserNameDto;
import dev.hertlein.pocs.archunit.user.configuration.UserModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserIT {

    private final static String A_FIRST_NAME = "John";
    private final static String ANOTHER_FIRST_NAME = "Jane";
    private final static String A_LAST_NAME = "Doe";

    private final UserApiAdapter userApiAdapter = UserModule.INSTANCE.module();

    @AfterEach
    void afterEach() {
        userApiAdapter.deleteAll();
    }

    @Test
    void shouldCreateUser() {
        assertThat(userApiAdapter.getAll()).isEmpty();
        UserNameDto toBeCreatedUser = new UserNameDto(A_FIRST_NAME, A_LAST_NAME);

        UserDto createdUser = userApiAdapter.create(toBeCreatedUser);

        assertThat(createdUser.getUserId()).isNotNull();
        assertThat(createdUser.getFirstName()).isEqualTo(toBeCreatedUser.getFirstName());
        assertThat(createdUser.getLastName()).isEqualTo(toBeCreatedUser.getLastName());
    }

    @Test
    void shouldUpdateUser() {
        assertThat(userApiAdapter.getAll()).isEmpty();
        UserNameDto toBeCreatedUser = new UserNameDto(A_FIRST_NAME, A_LAST_NAME);
        UserDto user = userApiAdapter.create(toBeCreatedUser);
        user.setFirstName(ANOTHER_FIRST_NAME);
        userApiAdapter.update(user);

        UserDto updatedUser = userApiAdapter.getAll(user.getUserId());

        assertThat(updatedUser).isEqualTo(user);
    }

    @Test
    void shouldDeleteUser() {
        assertThat(userApiAdapter.getAll()).isEmpty();
        UserNameDto toBeCreatedUser = new UserNameDto(A_FIRST_NAME, A_LAST_NAME);
        UserDto createdUser = userApiAdapter.create(toBeCreatedUser);
        assertThat(userApiAdapter.getAll()).isNotEmpty();

        userApiAdapter.delete(createdUser.getUserId());

        assertThat(userApiAdapter.getAll()).isEmpty();
    }

    @Test
    void shouldGetUser() {
        assertThat(userApiAdapter.getAll()).isEmpty();
        UserNameDto toBeCreatedUser = new UserNameDto(A_FIRST_NAME, A_LAST_NAME);
        UserDto createdUser = userApiAdapter.create(toBeCreatedUser);

        UserDto gotUser = userApiAdapter.getAll(createdUser.getUserId());

        assertThat(gotUser).isEqualTo(createdUser);
    }
}
