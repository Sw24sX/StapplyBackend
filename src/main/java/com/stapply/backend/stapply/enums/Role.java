package com.stapply.backend.stapply.enums;

import java.util.Arrays;

public enum Role {
    USER(1),
    ADMIN(2);

    private final Short code;

    Role(int code) {
        this.code = (short) code;
    }

    public Short getCode() {
        return code;
    }

    public static Role getByCode(Short code) {
        if(code == null) {
            return USER;
        }
        return Arrays.stream(values())
                .filter(value -> value.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Code not found"));
    }
}
