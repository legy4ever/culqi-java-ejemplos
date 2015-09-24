package com.culqi.pruebas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Respuesta {

    @JsonProperty
    private String codigo_respuesta;

    @JsonProperty
    private String mensaje_respuesta;

    @JsonProperty
    private String codigo_comercio;

    @JsonProperty
    private String nro_pedido;

    @JsonProperty
    private String codigo_transaccion;

    public String getCodigo_respuesta() {
        return codigo_respuesta;
    }

    public String getMensaje_respuesta() {
        return mensaje_respuesta;
    }

    public String getCodigo_comercio() {
        return codigo_comercio;
    }

    public String getNro_pedido() {
        return nro_pedido;
    }

    public String getCodigo_transaccion() {
        return codigo_transaccion;
    }
}