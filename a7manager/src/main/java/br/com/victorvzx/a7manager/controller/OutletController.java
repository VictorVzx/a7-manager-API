package br.com.victorvzx.a7manager.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class OutletController {
    @GetMapping
    public String test() {
        return "Inicio";
    }
}
