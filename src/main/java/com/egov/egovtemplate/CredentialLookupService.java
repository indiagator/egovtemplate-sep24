package com.egov.egovtemplate;

import com.egov.egovtemplate.personnel.auth.Credential;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

@Service
public abstract class CredentialLookupService {

    @Lookup
    public abstract Credential getDummyCredential();

}
