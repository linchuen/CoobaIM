package com.cooba.constant;

import com.cooba.entity.UserAuthority;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cooba.constant.AuthorityEnum.*;


@Getter
@RequiredArgsConstructor
public enum RoleEnum {
    USER("ROLE_USER", Set.of(CREATE.auth(), new UserAuthority("ROLE_USER"))),
    GUEST("ROLE_GUEST", Set.of(READ.auth(), new UserAuthority("ROLE_GUEST"))),
    AGENT("ROLE_AGENT", Set.of(CREATE.auth(), new UserAuthority("ROLE_AGENT"))),
    ADMIN("ROLE_ADMIN", Set.of(
            CREATE.auth(),
            READ.auth(),
            UPDATE.auth(),
            DELETE.auth(),
            new UserAuthority("ROLE_ADMIN")
    ));

    private final String role;
    private final Set<UserAuthority> authorities;

    private static final Map<String, Set<UserAuthority>> map = Arrays.stream(RoleEnum.values())
            .collect(Collectors.toMap(RoleEnum::getRole, RoleEnum::getAuthorities));

    public static Set<UserAuthority> getFromType(String type) {
        return map.getOrDefault(type, Collections.emptySet());
    }
}
