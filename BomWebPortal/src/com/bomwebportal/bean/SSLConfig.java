package com.bomwebportal.bean;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import javax.annotation.PostConstruct;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.bomwebportal.util.SSLUtil;

@Component("sslConfig")
public class SSLConfig {
	private final static Log logger = LogFactory.getLog(SSLConfig.class);

	
	//@Value("${https.keyStore:#{null}}")
	private Resource keyStore;
	
	//@Value("${https.keyStorePassword:#{null}}")
	private String keyStorePassword;
	
	private SSLContext sslContext;
	private SSLSocketFactory sslSocketFactory;
	private HostnameVerifier hostnameVerifier = new DummyHostnameVerifier();
	
	
	private  SSLConfig() {}
	
	public Resource getKeyStore() {
		return keyStore;
	}

	public void setKeyStore(Resource keyStore) {
		this.keyStore = keyStore;
	}

	public String getKeyStorePassword() {
		return keyStorePassword;
	}

	public void setKeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}
	
	
	@PostConstruct
	public void initJsse() throws Exception {
		
		logger.info("Initializing SSLSocketFactory ...");
		/*
		Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
		BouncyCastleProvider bcp = new BouncyCastleProvider();
		Security.addProvider(bcp);
		*/
		sslContext = SSLUtil.createTrustAllSSLContext(
				keyStore == null ? null : keyStore.getFile(),
				keyStorePassword);
		sslSocketFactory = sslContext.getSocketFactory();
		
		SSLUtil.setDefaultSSLSocketFactory(sslSocketFactory);
		
	}
	
	public SSLContext getSSLContext() {
		return sslContext;
	}
	
	public SSLSocketFactory getSSLSocketFactory() {
		return sslSocketFactory;
	}
	
	public HostnameVerifier getHostnameVerifier() {
		return hostnameVerifier;
	}
	
	public URLConnection openUrlConnection(String httpsUrl) throws MalformedURLException, IOException {
		URLConnection conn = new URL(httpsUrl).openConnection();
		if (conn instanceof HttpsURLConnection) {
			HttpsURLConnection httpsConn = (HttpsURLConnection)conn;
			httpsConn.setSSLSocketFactory(sslSocketFactory);
			httpsConn.setHostnameVerifier(hostnameVerifier);
		}
		return conn;
	}
	
	public URLConnection openUrlConnection(String httpsUrl, Proxy proxy) throws MalformedURLException, IOException {
		URL url = new URL(httpsUrl);
		URLConnection conn ;
		if (proxy != null) {
			conn = url.openConnection(proxy);
		} else {
			conn = url.openConnection();
		}
		if (conn instanceof HttpsURLConnection) {
			HttpsURLConnection httpsConn = (HttpsURLConnection)conn;
			httpsConn.setSSLSocketFactory(sslSocketFactory);
			httpsConn.setHostnameVerifier(hostnameVerifier);
		}
		return conn;
	}
	
	public HttpsURLConnection trustConnection(HttpsURLConnection conn) {
		conn.setSSLSocketFactory(sslSocketFactory);
		conn.setHostnameVerifier(hostnameVerifier);
		return conn;
	}
	
	
	private static class DummyHostnameVerifier implements HostnameVerifier {

		public boolean verify(String urlHost, SSLSession sslSession) {
			return true;
		}
	}
	
	public static Proxy createProxy(String host, int port) {
		return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
	}
	
	
	public SSLConfig(String keystore, String password) {
		System.out.println("========== constructor for testing only ==========");
		this.keyStore = new FileSystemResource(new File(keystore));
		this.keyStorePassword = password;
		try {
			initJsse();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
