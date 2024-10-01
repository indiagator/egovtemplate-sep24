package com.egov.egovtemplate;


import com.egov.egovtemplate.personnel.auth.Credential;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Bean
    @Scope("prototype")
    public Credential DummyCredential() {
        Credential credential = new Credential();
        credential.setId(java.util.UUID.randomUUID());
        credential.setPassword("dummyPassword");
        credential.setUsername("dummyUsername");
        return credential;
    }


}
