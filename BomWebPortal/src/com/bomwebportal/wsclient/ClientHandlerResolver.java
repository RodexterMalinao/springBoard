package com.bomwebportal.wsclient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

public class ClientHandlerResolver implements HandlerResolver {

	List<Handler> handlerChain = new ArrayList<Handler>();
	
	public ClientHandlerResolver() {}
	
	public ClientHandlerResolver(Handler ... handlers) {
		if (handlers != null && handlers.length > 0) {
			handlerChain.addAll(Arrays.asList(handlers));
		}
	}
	
	public void addHandler(Handler handler) {
		handlerChain.add(handler);
	}

    @SuppressWarnings("rawtypes")
    public List<Handler> getHandlerChain(PortInfo portInfo) {
       
        return handlerChain;
    }
}