package com.imertyildiz.grpcserver.controller;

import com.imertyildiz.grpcserver.Service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class GreeterController {

    @Autowired(required = false)
    private RestService restService;

    @PostMapping("/send-message-rest")
    public void greetFromRest(@RequestBody String msg){
        restService.greet(msg);
    }
}