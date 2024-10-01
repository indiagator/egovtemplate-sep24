package com.egov.egovtemplate.personnel.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "credentials")
public class Credential {
    @Id
    @Column(name = "citizenid", nullable = false)
    private UUID id;

    @Column(name = "password", length = 50)
    private String password;

    @Column(name = "username", length = 50)
    private String username;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}