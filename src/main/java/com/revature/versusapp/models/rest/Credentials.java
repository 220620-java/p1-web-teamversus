package com.revature.versusapp.models.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Credentials {
    @JsonProperty("versus-api-key")
    private String versusApiKey;

    public String getVersusApiKey() {
        return versusApiKey;
    }

    public void setVersusApiKey(String versusApiKey) {
        this.versusApiKey = versusApiKey;
    }
}
