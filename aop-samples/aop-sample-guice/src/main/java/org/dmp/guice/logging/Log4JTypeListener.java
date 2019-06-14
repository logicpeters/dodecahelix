package org.dmp.guice.logging;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

public class Log4JTypeListener implements TypeListener {

    // This type of notation will go away with @Inject Logger logger
    private static final Logger logger = Logger.getLogger(Log4JTypeListener.class);

    public <T> void hear(TypeLiteral<T> typeLiteral, TypeEncounter<T> typeEncounter) {
        for (Field field : typeLiteral.getRawType().getDeclaredFields()) {
            if (field.getType() == Logger.class 
                    && field.isAnnotationPresent(InjectLogger.class)) {
                
                logger.debug("Registering logger for class " + field.getDeclaringClass());
                typeEncounter.register(new Log4JMembersInjector<T>(field));
            }
        }
    }
}
