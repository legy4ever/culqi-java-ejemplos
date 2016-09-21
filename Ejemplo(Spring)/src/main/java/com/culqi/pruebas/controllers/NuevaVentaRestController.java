
package com.culqi.pruebas.controllers;

import com.culqi.pruebas.domain.*;
import com.culqi.pruebas.domain.Respuesta;
import com.culqi.sdk.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class NuevaVentaRestController {

    @Value("${culqi.url.mod_pago}")
    private String URLModuloPago;

    @Value("${culqi.config.llaveSecreta}")
    private String llaveSecreta;

    @Value("${culqi.config.codigoComercio}")
    private String codigoComercio;

    @ResponseBody @RequestMapping(value = "/respuesta-demo-integracion", method = RequestMethod.POST)
    public String descrifrar(@RequestBody @Valid RespuestaCifrada respuestaCifrada, HttpServletRequest request) {

        Culqi.llaveSecreta = llaveSecreta;
        Culqi.codigoComercio = codigoComercio;

        return Culqi.decifrar(respuestaCifrada.getRespuesta());
    }

    @ResponseBody @RequestMapping(value = "/anular-integracion", method = RequestMethod.POST)
    public String anularDev(@RequestBody @Valid VentaAnular ventaAnular, HttpServletRequest request) {

        return "";

    }

}