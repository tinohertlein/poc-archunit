package dev.hertlein.pocs.archunit.user.adapter.primary.api.mapper;

import dev.hertlein.pocs.archunit.user.core.model.UserId;

public class UserIdMapper {

    public UserId fromString(String userId) {
        return new UserId(userId);
    }

    public String toString(UserId userId) {
        return userId.userId();
    }
}
