package dev.hertlein.pocs.archunit.user.core.service;

import dev.hertlein.pocs.archunit.user.core.model.UserId;
import java.util.UUID;

public class UserIdService {

    UserId generateUserId() {
        return new UserId(UUID.randomUUID().toString());
    }
}
