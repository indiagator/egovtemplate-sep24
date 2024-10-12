package com.egov.egovtemplate.operations;

import java.io.Serializable;

public class SagaStatus implements Serializable
{

    public String status;
    public String sagaId;
    public String sagaResponse;

    public String getSagaId() {
        return sagaId;
    }

    public String getSagaResponse() {
        return sagaResponse;
    }

    public String getStatus() {
        return status;
    }

    public void setSagaId(String sagaId) {
        this.sagaId = sagaId;
    }

    public void setSagaResponse(String sagaResponse) {
        this.sagaResponse = sagaResponse;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
