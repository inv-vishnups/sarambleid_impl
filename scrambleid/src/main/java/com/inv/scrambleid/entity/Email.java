package com.inv.scrambleid.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer emailId;
    @Column(name = "is_primary")
    private String primary;
    private String value;
    private String type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
