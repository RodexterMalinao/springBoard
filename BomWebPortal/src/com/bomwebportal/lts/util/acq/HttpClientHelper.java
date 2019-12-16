package com.bomwebportal.lts.util.acq;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.bomwebportal.lts.util.LtsConstant;



public class HttpClientHelper {
	public static HttpClient getHttpClient(String pEnvironment)
			throws Exception {
		String clientCertLoc = LtsConstant.getConstant(pEnvironment,
				LtsConstant.SB_CLIENT_CERT_LOCATION);
		if (StringUtils.isBlank(clientCertLoc)) {
			PoolingClientConnectionManager mgr = new PoolingClientConnectionManager();
			return new DefaultHttpClient(mgr);
		}
		return getHttpClient(clientCertLoc, LtsConstant.getConstant(
				pEnvironment, LtsConstant.SB_CLIENT_CERT_PWD));
	}

	private static HttpClient getHttpClient(String pClientCertLocation,
			String pClientCertPassword) throws Exception {
		// read in the keystore from the filesystem, this should contain a
		// single keypair
		KeyStore clientKeyStore = KeyStore.getInstance("JKS");
		clientKeyStore.load(
				pClientCertLocation.contains("classpath:") ? ClassLoader.class
						.getResourceAsStream(pClientCertLocation
								.substring("classpath:".length()))
						: new FileInputStream(pClientCertLocation),
				pClientCertPassword.toCharArray());

		// set up the socketfactory, to use our keystore for client
		// authentication.
		SSLSocketFactory socketFactory = new SSLSocketFactory(
				SSLSocketFactory.TLS,
				clientKeyStore,
				pClientCertPassword,
				null,
				null,
				null,
				(X509HostnameVerifier) SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

		// create and configure scheme registry
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("https", 443, socketFactory));

		// create a client connection manager to use in creating httpclients
		PoolingClientConnectionManager mgr = new PoolingClientConnectionManager(
				registry);

		// create the client based on the manager, and use it to make the call
		return wrapHttpClient(new DefaultHttpClient(mgr));

	}

	public static HttpClient wrapHttpClient(HttpClient pHttpClient) {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {

				public void checkClientTrusted(X509Certificate[] xcs,
						String string) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] xcs,
						String string) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = pHttpClient.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", 443, ssf));
			
			final HttpParams httpParams = new BasicHttpParams();
		    HttpConnectionParams.setConnectionTimeout(httpParams, 60000); // 60 sec
		    HttpConnectionParams.setSoTimeout(httpParams, 60000); // 60 sec
		    DefaultHttpClient httpClient = (DefaultHttpClient)pHttpClient;
		    httpClient.setParams(httpParams);
		    
			return new DefaultHttpClient(ccm, httpClient.getParams());
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
