package com.bomwebportal.wsclient;

import java.io.IOException;
import java.util.Hashtable;

import org.apache.axis.components.net.JSSESocketFactory;

import com.bomwebportal.util.SSLUtil;

public class TrustAllAxisJSSESocketFactory extends JSSESocketFactory {

	public TrustAllAxisJSSESocketFactory(Hashtable attributes) {
		super(attributes);
	}
	
	protected void initFactory() throws IOException {
		this.sslFactory = SSLUtil.getDefaultSSLSocketFactory();
	}
}
