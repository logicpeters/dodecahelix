package org.dmp.gwtpurdy.showcase.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RpcServiceAsync {

    void greetServer(String input, AsyncCallback<String> callback)
            throws IllegalArgumentException;
    
}
