package com.Poseidon_Capital_Solutions.Trading.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", columnDefinition = "tinyint(4)")
    private Integer id;

    @Column(name = "username", length = 125)
    private String username;

    @Column(name = "password", length = 125)
    private String password;

    @Column(name = "fullname", length = 125)
    private String fullname;

    @Column(name = "role", length = 125)
    private String role;
}