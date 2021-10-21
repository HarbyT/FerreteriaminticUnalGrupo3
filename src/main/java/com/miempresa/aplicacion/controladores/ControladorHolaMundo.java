package com.miempresa.aplicacion.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControladorHolaMundo {
    
    @GetMapping("/HolaMundo") //path del controlador
    public String holaMundo(){
        return "vistaHolaMundo";
    }
}
