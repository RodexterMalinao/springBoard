package com.bomwebportal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SSLUtil {

	private final static Log logger = LogFactory.getLog(SSLUtil.class);

	// default within mobcos
	private static SSLSocketFactory defaultSSLSocketFactory = null;
	

	public static SSLSocketFactory getDefaultSSLSocketFactory() {
		if (defaultSSLSocketFactory == null) {
			defaultSSLSocketFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
		}
		return defaultSSLSocketFactory;
	}
	
	public static void setDefaultSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
		if (sslSocketFactory == null) {
			throw new IllegalArgumentException("no default SSLSocketFactory specified");
		}
		logger.info("Setting default SSLSocketFactory");
		defaultSSLSocketFactory = sslSocketFactory;
	}
	
	
	public static SSLSocketFactory initDefaultTrustAll(String keyStore, String keyStorePassword) throws GeneralSecurityException {
		SSLSocketFactory sslSocketFactory = createTrustAllSSLSocketFactory(keyStore, keyStorePassword);
		setDefaultSSLSocketFactory(sslSocketFactory);
		return sslSocketFactory;
	}
	
	public static SSLSocketFactory createTrustAllSSLSocketFactory() throws GeneralSecurityException {
		return createTrustAllSSLSocketFactory((File)null, null);
	}
	
	public static SSLSocketFactory createTrustAllSSLSocketFactory(String keyStorePath, String keyStorePassword) throws GeneralSecurityException {
		SSLContext sslContext = createTrustAllSSLContext(keyStorePath, keyStorePassword);
		return sslContext.getSocketFactory();
	}
	
	public static SSLSocketFactory createTrustAllSSLSocketFactory(File keyStore, String keyStorePassword) throws GeneralSecurityException {
		SSLContext sslContext = createTrustAllSSLContext(keyStore, keyStorePassword);
		return sslContext.getSocketFactory();
	}
	
	public static SSLContext createTrustAllSSLContext(String keyStorePath, String keyStorePassword) throws GeneralSecurityException  {
		File ks = null;
		if (StringUtils.isNotEmpty(keyStorePath)) {
			ks = new File(keyStorePath);
		}
		return createTrustAllSSLContext(ks, keyStorePassword);
	}
	
	

	public static SSLContext createTrustAllSSLContext(File keyStore, String keyStorePassword) throws GeneralSecurityException  {

		KeyManager[] km = null;
		
		if (keyStore != null) {
			logger.info("Initializing KeyManager ...");
			km = getKeyManagers(keyStore, keyStorePassword);
		}
		
		logger.info("Initializing TrustManager ...");
		TrustManager[] trustManagers = {new NaiveX509TrustManager() };
		SSLContext sslContext = SSLContext.getInstance("TLSv1");
		sslContext.init(km, trustManagers, new SecureRandom());

		return sslContext;
	}
	
	

	
	private static KeyManager[] getKeyManagers(File keyStore, String keyStorePassword) {
		KeyStore ks = getKeyStore(keyStore, keyStorePassword);
		if (ks == null) return null;
		try {
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(ks, keyStorePassword.toCharArray());
			return kmf.getKeyManagers();
		} catch (Exception e) {
			logger.error("Exception while initializing KeyManagers", e);
		}
		return null;
	}
	
	private static KeyStore getKeyStore(File keystore, String password)  {
		KeyStore ks = null;
		FileInputStream is = null;
		try {
			ks = KeyStore.getInstance(KeyStore.getDefaultType());
			if (keystore != null) {
				is =  new FileInputStream(keystore);
			}
			ks.load(is, password == null ? null : password.toCharArray());
			return ks;
		} catch (IOException e) {
            logger.error("KeyStore IOException while initializing TrustManagerFactory ", e);
            ks = null;
        } catch (CertificateException e) {
            logger.error("KeyStore CertificateException while initializing TrustManagerFactory ", e);
            ks = null;
        } catch (NoSuchAlgorithmException e) {
        	logger.error("KeyStore NoSuchAlgorithmException while initializing TrustManagerFactory ", e);
        	ks = null;
		} catch (KeyStoreException e) {
        	logger.error("KeyStore KeyStoreException while initializing TrustManagerFactory ", e);
        	ks = null;
		} finally {
			IOUtils.closeQuietly(is);
		}
		return ks;
	}
	
	/*
	
	public static HttpClient createTrustAllHttpClient() {
		SSLContextBuilder builder = SSLContexts.custom();
		builder.loadTrustMaterial(null, new TrustStrategy() {

			@Override
			public boolean isTrusted(X509Certificate[] arg0, String arg1)
					throws CertificateException {
				return true;
			}
			
		});
		builder.loadKeyMaterial(ks, keyStorePassword.toCharArray());
		
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(), SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		HttpClientBuilder httpClientBuilder = HttpClients.custom().setSSLSocketFactory(sslsf);
		if (!isBlank(this.httpProxyHost)) {
			HttpHost proxy = this.httpProxyPort == null ? new HttpHost(this.httpProxyHost) : new HttpHost(this.httpProxyHost, this.httpProxyPort);
			httpClientBuilder.setRoutePlanner(new DefaultProxyRoutePlanner(proxy));
		}
		CloseableHttpClient httpClient = httpClientBuilder.build();
	}
	*/
	
	
	private static class NaiveX509TrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] chain, String authType)
        throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
        throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
	

}
