
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
public class Venta {

    @Value("${culqi.url.mod_pago}")
    private String URLModuloPago;

    @ResponseBody @RequestMapping(value = "/respuesta", method = RequestMethod.POST)
    public String createSale(@RequestBody @Valid RespuestaCifrada respuestaCifrada, HttpServletRequest request) {

       Culqi.llaveSecreta = "zzmxZlgIJtKKy0F71DMsZPWnPVzow4S90abBScLDIrk=";
       Culqi.codigoComercio = "testc101";

       return Culqi.decifrar(respuestaCifrada.getRespuesta());
    }


   @ResponseBody @RequestMapping(value = "/anular", method = RequestMethod.POST)
   public String anularVenta(@RequestBody @Valid VentaAnular ventaAnular, HttpServletRequest request) {

       Culqi.llaveSecreta = "zzmxZlgIJtKKy0F71DMsZPWnPVzow4S90abBScLDIrk=";
       Culqi.codigoComercio = "testc101";
       Culqi.servidorBase = URLModuloPago;

       Map<String, Object> anulacion = Pago.anular(ventaAnular.getId_transaccion());


       try {
           return new ObjectMapper().writeValueAsString(anulacion);
       } catch (JsonProcessingException e) {

           throw new RuntimeException(e);

       }

   }

    @RequestMapping(value = "/venta", method = RequestMethod.GET)
    public String generarVenta(Model model) throws IOException {

        Culqi.llaveSecreta = "zzmxZlgIJtKKy0F71DMsZPWnPVzow4S90abBScLDIrk=";
        Culqi.codigoComercio = "testc105";
        Culqi.servidorBase = "https://integ-pago.culqi.com";

        String orden = (new Date()).toString();

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Pago.PARAM_NUMERO_PEDIDO, orden);
        params.put(Pago.PARAM_MONEDA, "PEN");
        params.put(Pago.PARAM_MONTO, 8700);
        params.put(Pago.PARAM_DESCRIPCION, "Venta de pruebas.");

        Map<String, Object> respuesta = com.culqi.sdk.Pago.crearDatosPago(params);

        if (respuesta.get("codigo_respuesta").equals("OK") ) {

            return respuesta.get("codigo_respuesta").toString();

        } else {

            return "error_response";
        }

    }

}