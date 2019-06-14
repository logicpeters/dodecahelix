package org.dmp.mongo.logging;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.log4j.Logger;

/**
 *   Since Logger is not a managed bean, we need to create a producer for it.
 *   
 * @author david.peters
 *
 */
public class LoggerFactory {

    @Produces 
    public Logger createLogger(InjectionPoint injectionPoint) {
        String name = injectionPoint.getMember().getDeclaringClass().getName();
        return Logger.getLogger(name);
    }
    
}
