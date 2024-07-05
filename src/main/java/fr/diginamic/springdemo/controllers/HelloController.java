package fr.diginamic.springdemo.controllers;

import fr.diginamic.springdemo.services.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A controller for the Hello entity
 * @see HelloService
 * @see fr.diginamic.springdemo.services.HelloService
 * @author AyoubBenziza
 */
@RestController
@RequestMapping("/")
public class HelloController {

    /**
     * The HelloService instance
     * @see HelloService
     */
    @Autowired
    private HelloService service;

    /**
     * A simple hello world message
     * @return a string
     */
    @GetMapping
    public String helloWorld() {
        return service.salutations();
    }
}
