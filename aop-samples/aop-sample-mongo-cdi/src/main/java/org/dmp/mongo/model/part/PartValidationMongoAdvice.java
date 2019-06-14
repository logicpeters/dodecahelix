package org.dmp.mongo.model.part;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.validation.ValidationException;

import org.apache.log4j.Logger;
import org.dmp.mongo.db.MongoWithMongojackMapping;
import org.dmp.mongo.db.PartDAO;

/**
 *   This is an example AOP aspect that checks for uniqueness of the part number argument and throws an exception if the Part number is already being used
 *   
 * @author david.peters
 *
 */
@PartValidation @Interceptor
public class PartValidationMongoAdvice {

    @Inject
    private transient Logger logger;
    
    @Inject @MongoWithMongojackMapping
    private PartDAO mongo;
    
    @AroundInvoke
    public Object validatePart(InvocationContext joinPoint) throws Exception {
        Object returnObj = null;
        
        try {
            String partNumber = (String) joinPoint.getParameters()[0];
            logger.debug("Validating uniqueness of part number " + partNumber);
            
            Part part = mongo.retrieveByPartNumber(partNumber);
            if (part!=null) {
                throw new ValidationException("Part being added is not unique.  Number = " + partNumber);
            }
            
            if (partNumber.length()<3 || partNumber.length()>12) {
                throw new ValidationException("Part number is invalid: " + partNumber + ".  Must be between 3 and 12 characters in length.");
            }
            
            returnObj = joinPoint.proceed();
            return returnObj;
        } finally {    
        }

    }
    
}
