package com.bomwebportal.wsclient;

import java.util.Set;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class UsernameTokenHeaderHandler implements SOAPHandler<SOAPMessageContext> {
	
    // WS-Security header values
    public static final String SECURITY = "Security";
    public static final String USERNAME_TOKEN = "UsernameToken";
    public static final String USERNAME = "Username";
    public static final String PASSWORD = "Password";
	public static final String WS_SECURITY_NAMESPACE = 
		       "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
	// prefixes
    public static final String WSSE_PREFIX = "wsse"; // ws service security
    
	private String username;
	private String password;
	
	public UsernameTokenHeaderHandler(String username, String password) {
		this.username = username;
		this.password = password;
	}
    public boolean handleMessage(SOAPMessageContext smc) {
        Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outboundProperty.booleanValue()) {
            SOAPMessage message = smc.getMessage();
            try {
                SOAPEnvelope envelope = smc.getMessage().getSOAPPart().getEnvelope();
                SOAPHeader header = envelope.getHeader();
                if (header == null) header = envelope.addHeader();
                SOAPElement security = header.addChildElement(SECURITY, WSSE_PREFIX, WS_SECURITY_NAMESPACE);
                SOAPElement usernameToken = security.addChildElement(USERNAME_TOKEN, WSSE_PREFIX);
                usernameToken.addAttribute(envelope.createName("xmlns:wsu"), "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
                SOAPElement username = usernameToken.addChildElement(USERNAME, WSSE_PREFIX);
                username.addTextNode(this.username);
                SOAPElement password = usernameToken.addChildElement(PASSWORD, WSSE_PREFIX);
                password.setAttribute("Type", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
                password.addTextNode(this.password);
                // Print out the outbound SOAP message to System.out
                ///message.writeTo(System.out);
                //System.out.println("");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {
                // This handler does nothing with the response from the Web
                // Service so
                // we just print out the SOAP message.
                //SOAPMessage message = smc.getMessage();
                ///message.writeTo(System.out);
                //System.out.println("");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return outboundProperty;

    }


    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Set getHeaders() {
        // throw new UnsupportedOperationException("Not supported yet.");
        return null;
    }

    public boolean handleFault(SOAPMessageContext context) {
        // throw new UnsupportedOperationException("Not supported yet.");
        return true;
    }

    public void close(MessageContext context) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }
}

