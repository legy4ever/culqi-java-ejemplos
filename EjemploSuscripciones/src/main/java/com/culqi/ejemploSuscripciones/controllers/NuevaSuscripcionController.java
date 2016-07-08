package com.culqi.ejemploSuscripciones.controllers;

import com.culqi.ejemploSuscripciones.domain.EjemploData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created by admin_InSolutions on 7/07/2016.
 */
@RestController
@RequestMapping
public class NuevaSuscripcionController {

    private static final Logger log = Logger.getLogger(NuevaSuscripcionController.class);

    @Value("${culqi.url.mod_pago}")
    private String URLModuloPago;

    @RequestMapping(value = "/ejemplo-suscripcion", method = RequestMethod.POST)
    public ResponseEntity<String> demoSuscripcion(@Validated @RequestBody EjemploData ejemploData){

        RestTemplate restTemplateSuscripcion = new RestTemplate();

        JSONObject requestSuscripcion = new JSONObject();
        requestSuscripcion.put("codigo_comercio", ejemploData.getCodigo_comercio());
        requestSuscripcion.put("codigo_pais", ejemploData.getCodigo_pais());
        requestSuscripcion.put("direccion", ejemploData.getDireccion());
        requestSuscripcion.put("ciudad", ejemploData.getCiudad());
        requestSuscripcion.put("telefono", ejemploData.getTelefono());
        requestSuscripcion.put("nombre", ejemploData.getNombre());
        requestSuscripcion.put("correo_electronico", ejemploData.getCorreo_electronico());
        requestSuscripcion.put("apellido", ejemploData.getApellido());
        requestSuscripcion.put("usuario_id", ejemploData.getUsuario_id());
        requestSuscripcion.put("plan_id", ejemploData.getPlan_id());
        requestSuscripcion.put("token", ejemploData.getToken());
        if (ejemploData.getCargos_predefinidos()){
            requestSuscripcion.put("cargos", ejemploData.generarCargos());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestSuscripcion.toString(), headers);

        ResponseEntity<String> responseSuscripcion;

        try {

            responseSuscripcion = restTemplateSuscripcion.exchange(URLModuloPago, HttpMethod.POST, entity, String.class);

            log.info("Respuesta de la creación de la Suscripcion: " + responseSuscripcion.toString());

            HashMap<String, Object> respuestaPlan = new HashMap<>();
            respuestaPlan.put("respuesta", "Suscripcion creada exitosamente.");

            ObjectMapper objectMapper = new ObjectMapper();
            String test = "";
            try {
                test = objectMapper.writeValueAsString(respuestaPlan);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            };

            return ResponseEntity.ok(test);

        } catch (HttpClientErrorException e) {

            log.info("Respuesta de la creación de la suscripcion: " + e.getResponseBodyAsString());

            HashMap<String, Object> respuestaSuscripcion = new HashMap<>();
            respuestaSuscripcion.put("error", "Error al crear la suscripcion.");

            ObjectMapper objectMapper = new ObjectMapper();
            String test = "";
            try {
                test = objectMapper.writeValueAsString(respuestaSuscripcion);
            } catch (JsonProcessingException x) {
                x.printStackTrace();
            };
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(test);
        }
    }
}
