package com.bomwebportal.wsclient;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.transport.http.HttpUrlConnectionMessageSender;

import com.bomwebportal.bean.SSLConfig;


public class TrustAllHttpsUrlConnectionMessageSender extends
		HttpUrlConnectionMessageSender {
	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	SSLConfig sslConfig;
	
	public TrustAllHttpsUrlConnectionMessageSender() {
		
	}
	
	
	protected void prepareConnection(final HttpURLConnection connection) throws IOException {
		super.prepareConnection(connection);
		if (connection instanceof HttpsURLConnection) {
			sslConfig.trustConnection((HttpsURLConnection)connection);
		}
	}
		
	
	
}
