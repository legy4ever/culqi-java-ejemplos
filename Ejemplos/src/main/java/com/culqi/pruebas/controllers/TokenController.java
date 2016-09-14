package com.culqi.pruebas.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by admin_InSolutions on 14/09/2016.
 */
@Controller
@RequestMapping
public class TokenController {

    private static final Logger log = Logger.getLogger(TokenController.class);

    @Value("${culqi.config.codigoComercio}")
    private String Codigo_Comercio;

    @Value("${culqi.config.llaveSecreta}")
    private String Llave_secreta;

    @Value("${culqi.config.servidorBase}")
    private String Servidor_Base;

    @RequestMapping(value = "/demo-ejemplo-token")
    public String Token(Model model){
        model.addAttribute("llave_secreta", Llave_secreta);
        model.addAttribute("codigo_comercio", Codigo_Comercio);
        model.addAttribute("servidorBase", Servidor_Base);

        return "demo-token.html";
    }
}
