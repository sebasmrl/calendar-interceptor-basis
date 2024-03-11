package com.sebasmrl.calendar.interceptor.calendar.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class AppController {

    
    @GetMapping("/foo")
    public ResponseEntity<?> foo(HttpServletRequest request){
        
        Map<String, Object> data =  new HashMap<>();
        data.put("title", "Bienvenidos al sistema de atención!");
        data.put("time", new Date());
        data.put("message", request.getAttribute("message"));
        return ResponseEntity.status(HttpStatus.OK.value()).body(data);
    }
}
