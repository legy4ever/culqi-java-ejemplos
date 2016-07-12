package com.culqi.ejemploSuscripciones.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by admin_InSolutions on 11/07/2016.
 */
@Controller
public class ComercioSuscripcionController {

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public String demoSuscripcion(Model model){
        model.addAttribute("codigo_comercio", "demo");
        return "demo-suscripcion.html";
    }
}
