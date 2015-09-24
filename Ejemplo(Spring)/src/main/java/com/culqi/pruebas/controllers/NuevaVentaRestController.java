
package com.culqi.pruebas.controllers;

import com.culqi.commons.utils.JsonUtils;
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

    @ResponseBody @RequestMapping(value = "/respuesta-demo-desarrollo", method = RequestMethod.POST)
    public String createSale(@RequestBody @Valid RespuestaCifrada respuestaCifrada, HttpServletRequest request) {

        Culqi.llaveSecreta = "zzmxZlgIJtKKy0F71DMsZPWnPVzow4S90abBScLDIrk=";
        Culqi.codigoComercio = "testc101";

       return Culqi.decifrar(respuestaCifrada.getRespuesta());
    }

    @ResponseBody @RequestMapping(value = "/respuesta-demo-integracion", method = RequestMethod.POST)
    public String descrifrar(@RequestBody @Valid RespuestaCifrada respuestaCifrada, HttpServletRequest request) {

        Culqi.llaveSecreta = "58+jmm+ObJJMhXC18orFu/sk/h5DyeSJZVpv6pFwH/M=";
        Culqi.codigoComercio = "analvarteprod";

        return Culqi.decifrar(respuestaCifrada.getRespuesta());
    }

    @ResponseBody @RequestMapping(value = "/respuesta-demo-produccion", method = RequestMethod.POST)
    public String descrifrarProd(@RequestBody @Valid RespuestaCifrada respuestaCifrada, HttpServletRequest request) {

        Culqi.llaveSecreta = "YdYEkTwabQK0YHwf02eZs0dkZSH3QHYzZVzjp5bs6F4=";
        Culqi.codigoComercio = "cismo";

        return Culqi.decifrar(respuestaCifrada.getRespuesta());
    }

    @ResponseBody @RequestMapping(value = "/anular-integracion", method = RequestMethod.POST)
    public String anularDev(@RequestBody @Valid VentaAnular ventaAnular, HttpServletRequest request) {

        Culqi.llaveSecreta = "58+jmm+ObJJMhXC18orFu/sk/h5DyeSJZVpv6pFwH/M=";
        Culqi.codigoComercio = "analvarteprod";

        Map<String, Object> anulacion = Pago.anular(ventaAnular.getId_transaccion());

        String respuesta = JsonUtils.toJson(anulacion);

        return respuesta;

    }

    @ResponseBody @RequestMapping(value = "/anular-produccion", method = RequestMethod.POST)
    public String anularProd(@RequestBody @Valid VentaAnular ventaAnular, HttpServletRequest request) {

        Culqi.llaveSecreta = "YdYEkTwabQK0YHwf02eZs0dkZSH3QHYzZVzjp5bs6F4=";
        Culqi.codigoComercio = "cismo";

        Map<String, Object> anulacion = Pago.anular(ventaAnular.getId_transaccion());

        String respuesta = JsonUtils.toJson(anulacion);

        return respuesta;

    }

}