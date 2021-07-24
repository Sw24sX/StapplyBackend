package com.stapply.backend.stapply.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "Comment")
public class Comment extends BaseEntity{
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "text")
    private String text;

    @Column(name = "rate")
    private Integer rate;

    @ManyToOne
    @JoinColumn(name = "app_id")
    private App app;
}
