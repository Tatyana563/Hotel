package com.hotel.model.entity;

import com.hotel.validation.password.ValidPassword;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "users")
public class User extends BaseEntity implements Serializable {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column()
    private String tokenReset;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_fk_id")
    private Role role;

}
