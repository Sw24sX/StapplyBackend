package com.stapply.backend.stapply.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@MappedSuperclass
@EqualsAndHashCode(of = {"id"})
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    private Long id;
}
