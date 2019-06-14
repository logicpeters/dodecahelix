package org.dmp.gwtpurdy.server;

import org.dmp.gwtpurdy.showcase.rpc.RpcService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 *  Stubbed for later usage
 * 
 */
@SuppressWarnings("serial")
public class RpcServiceImpl extends RemoteServiceServlet implements RpcService {

    public String greetServer(String input) throws IllegalArgumentException {
        return "Hello";
    }

}
