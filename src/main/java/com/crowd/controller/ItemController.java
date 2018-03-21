package com.crowd.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by marce on 3/21/18.
 */
@RestController
@RequestMapping("/api/v1")
public class ItemController {

    @RequestMapping(value = "/item", method = RequestMethod.GET)
    public String procesando() {
        return "hola";
    }
}
