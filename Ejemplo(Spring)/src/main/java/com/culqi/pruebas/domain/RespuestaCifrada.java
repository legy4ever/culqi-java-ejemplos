package com.culqi.pruebas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespuestaCifrada {

    @JsonProperty
    private String respuesta;

    public String getRespuesta() {
        return respuesta;
    }
}