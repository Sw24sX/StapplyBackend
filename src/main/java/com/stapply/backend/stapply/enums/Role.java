package com.stapply.backend.stapply.enums;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;

public enum Role implements GrantedAuthority {
    USER(1),
    ADMIN(2);

    private final short code;

    Role(Integer code) {
        this.code = code.shortValue();
    }

    public short getCode() {
        return code;
    }

    public static Role getByCode(short code) {
        return Arrays.stream(values())
                .filter(value -> value.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Wrong code = %d", code)));
    }

    @Override
    public String getAuthority() {
        return name();
    }
}