package com.stapply.backend.stapply.converter;

import com.stapply.backend.stapply.enums.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RoleCodeConverter implements AttributeConverter<Role, Short> {

    @Override
    public Short convertToDatabaseColumn(Role attribute) {
        return attribute == null ? Role.USER.getCode() : attribute.getCode();
    }

    @Override
    public Role convertToEntityAttribute(Short i) {
        return Role.getByCode(i);
    }
}