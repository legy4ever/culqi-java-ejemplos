package com.culqi.pruebas.controllers;

import com.culqi.sdk.Culqi;
import com.culqi.sdk.Pago;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping
public class ComercioCertificacionController {

    @Value("${culqi.url.mod_pago}")
    private String URLModuloPago;

    static {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ComercioCertificacionController.class);

    private SecureRandom random = new SecureRandom();

    public String randomOrder() {
        return new BigInteger(16, random).toString(4);
    }

    @RequestMapping(value = "/ejemplo-desarrollo", method = RequestMethod.GET)
    public String demo(Model model) throws IOException {

        Culqi.llaveSecreta = "zzmxZlgIJtKKy0F71DMsZPWnPVzow4S90abBScLDIrk=";
        Culqi.codigoComercio = "testc101";
        Culqi.servidorBase = "https://devpago.culqi.com";

        model.addAttribute("URLModuloPago", URLModuloPago);

        model.addAttribute("llave_secreta",Culqi.llaveSecreta );
        model.addAttribute("codigo_comercio_info",Culqi.codigoComercio );
        model.addAttribute("servidorBase",Culqi.servidorBase );

        String orden = randomOrder();

        String moneda = "PEN";

        Integer monto = random.nextInt(99900)+100;

        Float montoFlotante = (float)monto / 100;
        String montoFormateado = new DecimalFormat("0.00").format(montoFlotante);

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Pago.PARAM_NUMERO_PEDIDO, orden);
        params.put("correo_electronico", "wmuro@me.com");
        params.put("nombres", "William");
        params.put("apellidos", "Muro");
        params.put("id_usuario_comercio", "123456");
        params.put(Pago.PARAM_MONEDA, moneda);
        params.put(Pago.PARAM_MONTO, 123);
        params.put(Pago.PARAM_DESCRIPCION, "Venta de prueba.");
        params.put(Pago.PARAM_CIUDAD, "Lima");
        params.put(Pago.PARAM_COD_PAIS, "PE");
        params.put(Pago.PARAM_DIRECCION, "Avenida Lima 1234");
        params.put(Pago.PARAM_NUM_TEL, "12345");

        LOGGER.info("Información enviada.{}", params.toString() );

        model.addAttribute("informacion_transaccion_descifrada", params);

        model.addAttribute("numero_pedido", orden);
        model.addAttribute("moneda", moneda);
        model.addAttribute("monto", 1.00);
        model.addAttribute("descripción", "Venta de prueba.");
        model.addAttribute("codigoComercio", "testc101");

        Map<String, Object> respuesta = com.culqi.sdk.Pago.crearDatosPago(params);

        if (respuesta.get("tipo_respuesta").equals("validacion_exitosa") ) {

            model.addAttribute("respuesta", respuesta);

            model.addAttribute("informacion_venta", respuesta.get("informacion_venta"));
            model.addAttribute("codigo_comercio", respuesta.get("codigo_comercio"));

            return "demo-desarrollo.html";

        } else {

            return "demo-desarrollo-error.html";
        }

    }

    @RequestMapping(value = "/ejemplo-integracion", method = RequestMethod.GET)
    public String demoIntegracion(Model model) throws IOException {

        Culqi.llaveSecreta = "58+jmm+ObJJMhXC18orFu/sk/h5DyeSJZVpv6pFwH/M=";
        Culqi.codigoComercio = "analvarteprod";
        Culqi.servidorBase = "https://integ-pago.culqi.com";

        model.addAttribute("URLModuloPago", URLModuloPago);

        model.addAttribute("llave_secreta",Culqi.llaveSecreta );
        model.addAttribute("codigo_comercio_info",Culqi.codigoComercio );
        model.addAttribute("servidorBase",Culqi.servidorBase );

        String orden = randomOrder();

        String moneda = "PEN";

        Integer monto = random.nextInt(99900)+100;

        Float montoFlotante = (float)monto / 100;
        String montoFormateado = new DecimalFormat("0.00").format(montoFlotante);

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Pago.PARAM_NUMERO_PEDIDO, orden);
        params.put("correo_electronico", "wmuro@me.com");
        params.put("nombres", "William");
        params.put("apellidos", "Muro");
        params.put("id_usuario_comercio", "123456");
        params.put(Pago.PARAM_MONEDA, moneda);
        params.put(Pago.PARAM_MONTO, 123);
        params.put(Pago.PARAM_DESCRIPCION, "Venta de prueba.");
        params.put(Pago.PARAM_CIUDAD, "Lima");
        params.put(Pago.PARAM_COD_PAIS, "PE");
        params.put(Pago.PARAM_DIRECCION, "Avenida Lima 1234");
        params.put(Pago.PARAM_NUM_TEL, "12345");

        LOGGER.info("Información enviada.{}", params.toString() );

        model.addAttribute("informacion_transaccion_descifrada", params);

        model.addAttribute("numero_pedido", orden);
        model.addAttribute("moneda", moneda);
        model.addAttribute("monto", 123);
        model.addAttribute("descripción", "Venta de prueba.");
        model.addAttribute("codigoComercio", "analvarteprod");

        Map<String, Object> respuesta = com.culqi.sdk.Pago.crearDatosPago(params);

        if (respuesta.get("tipo_respuesta").equals("validacion_exitosa") ) {

            model.addAttribute("respuesta", respuesta);

            model.addAttribute("informacion_venta", respuesta.get("informacion_venta"));
            model.addAttribute("codigo_comercio", respuesta.get("codigo_comercio"));

            return "demo-integracion.html";

        } else {

            return "demo-integracion-error.html";
        }

    }

    @RequestMapping(value = "/ejemplo-produccion", method = RequestMethod.GET)
    public String demoProduccion(Model model) throws IOException {

        Culqi.llaveSecreta = "YdYEkTwabQK0YHwf02eZs0dkZSH3QHYzZVzjp5bs6F4=";
        Culqi.codigoComercio = "cismo";
        Culqi.servidorBase = "https://pago.culqi.com";

        model.addAttribute("URLModuloPago", URLModuloPago);

        model.addAttribute("llave_secreta",Culqi.llaveSecreta );
        model.addAttribute("codigo_comercio_info",Culqi.codigoComercio );
        model.addAttribute("servidorBase",Culqi.servidorBase );

        String orden = randomOrder();

        String moneda = "PEN";

        Integer monto = random.nextInt(99900)+100;

        Float montoFlotante = (float)monto / 100;
        String montoFormateado = new DecimalFormat("0.00").format(montoFlotante);

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Pago.PARAM_NUMERO_PEDIDO, orden);
        params.put("correo_electronico", "wmuro@me.com");
        params.put("nombres", "William");
        params.put("apellidos", "Muro");
        params.put("id_usuario_comercio", "123456");
        params.put(Pago.PARAM_MONEDA, moneda);
        params.put(Pago.PARAM_MONTO, 123);
        params.put(Pago.PARAM_DESCRIPCION, "Venta de prueba.");
        params.put(Pago.PARAM_CIUDAD, "Lima");
        params.put(Pago.PARAM_COD_PAIS, "PE");
        params.put(Pago.PARAM_DIRECCION, "Avenida Lima 1234");
        params.put(Pago.PARAM_NUM_TEL, "12345");

        LOGGER.info("Información enviada.{}", params.toString() );

        model.addAttribute("informacion_transaccion_descifrada", params);

        model.addAttribute("numero_pedido", orden);
        model.addAttribute("moneda", moneda);
        model.addAttribute("monto", 123);
        model.addAttribute("descripción", "Venta de prueba.");
        model.addAttribute("codigoComercio", "cismo");

        Map<String, Object> respuesta = com.culqi.sdk.Pago.crearDatosPago(params);

        if (respuesta.get("tipo_respuesta").equals("validacion_exitosa") ) {

            model.addAttribute("respuesta", respuesta);

            model.addAttribute("informacion_venta", respuesta.get("informacion_venta"));
            model.addAttribute("codigo_comercio", respuesta.get("codigo_comercio"));

            return "demo-produccion.html";

        } else {

            return "demo-produccion-error.html";
        }

    }


}