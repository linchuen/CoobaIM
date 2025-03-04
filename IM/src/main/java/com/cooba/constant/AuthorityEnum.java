package com.cooba.constant;

import com.cooba.entity.UserAuthority;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthorityEnum {
    CREATE(new UserAuthority("CREATE")),
    READ(new UserAuthority("READ")),
    UPDATE(new UserAuthority("UPDATE")),
    DELETE(new UserAuthority("DELETE")),
    ;

    private final UserAuthority authority;

    public UserAuthority auth(){
        return authority;
    }
}
