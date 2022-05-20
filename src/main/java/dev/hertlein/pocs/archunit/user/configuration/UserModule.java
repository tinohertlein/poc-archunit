package dev.hertlein.pocs.archunit.user.configuration;

import dev.hertlein.pocs.archunit.user.adapter.primary.api.UserApiAdapter;
import dev.hertlein.pocs.archunit.user.adapter.primary.api.mapper.UserDtoMapper;
import dev.hertlein.pocs.archunit.user.adapter.primary.api.mapper.UserIdMapper;
import dev.hertlein.pocs.archunit.user.adapter.primary.api.mapper.UserNameDtoMapper;
import dev.hertlein.pocs.archunit.user.adapter.secondary.persistence.UserPersistenceAdapter;
import dev.hertlein.pocs.archunit.user.adapter.secondary.persistence.mapper.UserEntityMapper;
import dev.hertlein.pocs.archunit.user.core.port.primary.UserApiPort;
import dev.hertlein.pocs.archunit.user.core.port.secondary.UserPersistencePort;
import dev.hertlein.pocs.archunit.user.core.service.UserFactory;
import dev.hertlein.pocs.archunit.user.core.service.UserIdService;
import dev.hertlein.pocs.archunit.user.core.service.UserService;
import dev.hertlein.pocs.archunit.user.core.service.UserValidator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserModule {

    public static UserModule INSTANCE = new UserModule();

    private final UserApiAdapter module = createModule();

    public UserApiAdapter module() {
        return module;
    }

    private UserApiAdapter createModule() {
        UserIdMapper userIdMapper = new UserIdMapper();
        UserNameDtoMapper userNameDtoMapper = new UserNameDtoMapper();
        UserDtoMapper userDtoMapper = new UserDtoMapper(userIdMapper);

        return new UserApiAdapter(provideUserApiPort(), userDtoMapper, userNameDtoMapper, userIdMapper);
    }

    private UserApiPort provideUserApiPort() {
        UserValidator userValidator = new UserValidator();
        UserFactory userFactory = new UserFactory();
        UserIdService userIdService = new UserIdService();

        return new UserService(userValidator, userFactory, userIdService, provideUserPersistencePort());
    }

    private UserPersistencePort provideUserPersistencePort() {
        UserEntityMapper userEntityMapper = new UserEntityMapper();

        return new UserPersistenceAdapter(userEntityMapper);
    }
}

