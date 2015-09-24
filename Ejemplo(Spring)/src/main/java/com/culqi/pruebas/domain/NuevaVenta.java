package com.culqi.pruebas.domain;

        import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
        import com.fasterxml.jackson.annotation.JsonInclude;
        import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by William on 6/23/15.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NuevaVenta {

    @JsonProperty
    private String llave;

    @JsonProperty
    private String codigoComercio;

    @JsonProperty
    private String numero_orden;

    @JsonProperty
    private String moneda;

    @JsonProperty
    private Long monto;

    @JsonProperty
    private String descripcion;


    public String getLlave() {
        return llave;
    }

    public NuevaVenta setLlave(String llave) {
        this.llave = llave;
        return this;
    }

    public String getCodigoComercio() {
        return codigoComercio;
    }

    public NuevaVenta setCodigoComercio(String codigoComercio) {
        this.codigoComercio = codigoComercio;
        return this;
    }

    public String getNumero_orden() {
        return numero_orden;
    }

    public NuevaVenta setNumero_orden(String numero_orden) {
        this.numero_orden = numero_orden;
        return this;
    }

    public String getMoneda() {
        return moneda;
    }

    public NuevaVenta setMoneda(String moneda) {
        this.moneda = moneda;
        return this;
    }

    public Long getMonto() {
        return monto;
    }

    public NuevaVenta setMonto(Long monto) {
        this.monto = monto;
        return this;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public NuevaVenta setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }
}
