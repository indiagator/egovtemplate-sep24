package com.egov.egovtemplate.personnel.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CredentialRepository extends JpaRepository<Credential, UUID>
{
    Credential findByUsername(String username);
}