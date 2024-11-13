package com.imertyildiz.grpcserver.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RestService {
    public void greet(String msg){
        log.info("Message from REST: " +msg);
    }
}