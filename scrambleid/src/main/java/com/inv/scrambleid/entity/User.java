package com.inv.scrambleid.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String userName;
    private String givenName;
    private String familyName;

    @Column(unique = true)
    private String email;

    private boolean active;
    private String role;

    // password for auth
    private String password;

    // Scramble extension
    private Boolean desktopAppEnabled;
    private Boolean mobileAppEnabled;

    // Ops
    private Boolean sendActivation;
    private Boolean sendDesktopActivation;

    @Column(name = "scramble_user_id", unique = true)
    private String scrambleUserId;

}
