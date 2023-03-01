package com.gsd.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("hello")
    @PreAuthorize("hasAuthority('system:post:list')")
    public  Object hello(){
        return  "hello";
    }
}
