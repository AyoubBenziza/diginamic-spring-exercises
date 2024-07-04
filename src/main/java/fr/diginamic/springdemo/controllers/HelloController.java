package fr.diginamic.springdemo.controllers;

import fr.diginamic.springdemo.services.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloController {

    @Autowired
    private HelloService service;

    @GetMapping
    public String helloWorld() {
        return service.salutations();
    }
}
