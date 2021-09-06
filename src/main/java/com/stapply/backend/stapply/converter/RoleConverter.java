package com.stapply.backend.stapply.converter;

import com.stapply.backend.stapply.enums.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RoleConverter implements AttributeConverter<Role, Short> {
    @Override
    public Short convertToDatabaseColumn(Role role) {
        return role == null ? Role.USER.getCode() : role.getCode();
    }

    @Override
    public Role convertToEntityAttribute(Short code) {
        return Role.getByCode(code);
    }
}
