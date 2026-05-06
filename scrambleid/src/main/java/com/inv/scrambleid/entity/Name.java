package com.inv.scrambleid.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Name {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer nameId;
    private String givenName;
    private String familyName;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
