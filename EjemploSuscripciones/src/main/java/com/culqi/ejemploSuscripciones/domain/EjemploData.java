package com.culqi.ejemploSuscripciones.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by admin_InSolutions on 7/07/2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EjemploData {

    private static final Logger log = Logger.getLogger(EjemploData.class);

    @JsonProperty
    private String codigo_comercio;

    @JsonProperty
    private String codigo_pais;

    @JsonProperty
    private String direccion;

    @JsonProperty
    private String ciudad;

    @JsonProperty
    private String telefono;

    @JsonProperty
    private String nombre;

    @JsonProperty
    private String correo_electronico;

    @JsonProperty
    private String apellido;

    @JsonProperty
    private String usuario_id;

    @JsonProperty
    private String plan_id;

    @JsonProperty
    private String token;

    @JsonProperty
    private Boolean cargos_predefinidos;

    public String getCodigo_comercio() {
        return codigo_comercio;
    }

    public EjemploData setCodigo_comercio(String codigo_comercio) {
        this.codigo_comercio = codigo_comercio;
        return this;
    }

    public String getCodigo_pais() {
        return codigo_pais;
    }

    public EjemploData setCodigo_pais(String codigo_pais) {
        this.codigo_pais = codigo_pais;
        return this;
    }

    public String getDireccion() {
        return direccion;
    }

    public EjemploData setDireccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public String getCiudad() {
        return ciudad;
    }

    public EjemploData setCiudad(String ciudad) {
        this.ciudad = ciudad;
        return this;
    }

    public String getTelefono() {
        return telefono;
    }

    public EjemploData setTelefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public EjemploData setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public EjemploData setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
        return this;
    }

    public String getApellido() {
        return apellido;
    }

    public EjemploData setApellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public EjemploData setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
        return this;
    }

    public String getPlan_id() {
        return plan_id;
    }

    public EjemploData setPlan_id(String plan_id) {
        this.plan_id = plan_id;
        return this;
    }

    public String getToken() {
        return token;
    }

    public EjemploData setToken(String token) {
        this.token = token;
        return this;
    }

    public Boolean getCargos_predefinidos() {
        return cargos_predefinidos;
    }

    public EjemploData setCargos_predefinidos(Boolean cargos_predefinidos) {
        this.cargos_predefinidos = cargos_predefinidos;
        return this;
    }

    public static class CargosData {
        private Integer monto;
        private String fecha;

        public Integer getMonto() {
            return monto;
        }

        public CargosData setMonto(Integer monto) {
            this.monto = monto;
            return this;
        }

        public String getFecha() {
            return fecha;
        }

        public CargosData setFecha(String fecha) {
            this.fecha = fecha;
            return this;
        }
    }

    public List<CargosData> generarCargos(){
        List<CargosData> list = new ArrayList<>();
        Calendar fechaCalendar = new GregorianCalendar();
        SimpleDateFormat fecha1 = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha;
        int m, ms;
        for (int i=0; i < 2; i++){
            m = i*10+100;
            ms = i;
            CargosData cargosData = new CargosData();
            fechaCalendar.add(Calendar.MONTH, ms);
            fecha = fechaCalendar.getTime();
            String fechaAleatoria = fecha1.format(fecha);
            cargosData
                    .setMonto(m)
                    .setFecha(fechaAleatoria);
            list.add(cargosData);
            log.info(list.get(i).getFecha());
        }
        return list;
    }
}
