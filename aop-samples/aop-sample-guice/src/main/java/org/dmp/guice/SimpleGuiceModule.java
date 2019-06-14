package org.dmp.guice;

import org.dmp.guice.aop.Audited;
import org.dmp.guice.aop.Auditor;
import org.dmp.guice.rest.GreeterRestResource;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.matcher.Matchers;

public class SimpleGuiceModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(GreeterRestResource.class);
        binder.bind(Auditor.class);
        
        //binder.bindListener(Matchers.any(), new Log4JTypeListener());
        binder.bindInterceptor(Matchers.any(), Matchers.annotatedWith(Audited.class), new Auditor());
    }

}
