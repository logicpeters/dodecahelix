package org.dmp.spring.rest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Greeting {
    
    private static final Logger logger = Logger.getLogger(GreeterRestService.class);

    public void greet() {
        logger.info("YAR!!");
    }
    
}
