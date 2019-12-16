package com.bomwebportal.lts.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dao.CsPortalDAO;
import com.bomwebportal.lts.dto.CareCashOptInArqDTO;
import com.bomwebportal.lts.dto.CsPortalTxnDTO;
import com.google.gson.Gson;

public class CustomerIguardApiServiceImpl implements CustomerIguardApiService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private String systemId;
	private String systemPwd;
	
	private String apiProxy;
	
	private int apiPort;
	
	private String custIguardUrl;
	
	private CsPortalDAO csPortalDAO;
	
	private Gson gson = new Gson();
			
	private CareCashOptInArqDTO callCareCashApi(CareCashOptInArqDTO arq, String reqUrl) throws Exception{
		String res = callCareCashApiFinal(gson.toJson(arq), reqUrl);
		return StringUtils.isNotBlank(res)? gson.fromJson(res, arq.getClass()) : arq;
	}
	
	public void createCsPortalTxn(CsPortalTxnDTO csPortalTxn, String userId) {
		try {
			csPortalDAO.createCsPortalTxn(csPortalTxn, userId);
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e);
		}
	}
	
	private String callCareCashApiFinal(String requestString, String reqUrl) throws Exception{
		
		URL rURL;
		URLConnection rConn;
		OutputStreamWriter rOSW;
		BufferedReader rBR;
		String rStr;
		StringBuffer rSB;
		
		rBR = null;
		rOSW = null;
		
		try{
			// set dummy ssl trust manager
			SSLContext ctx = SSLContext.getInstance("SSL");
			ctx.init(new KeyManager[0],
					new TrustManager[] { new DefaultTrustManager() },
					new SecureRandom());
			SSLSocketFactory sf = ctx.getSocketFactory();
			
			Proxy proxy = null;
			if(!apiProxy.trim().equalsIgnoreCase("passby"))
			{
				proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(apiProxy.trim(), apiPort));
				System.out.println("CareCashApi apiProxy:" + apiProxy);
			}
				
			
			HttpsURLConnection
				.setDefaultHostnameVerifier(new DummyHostnameVerifier());
			HttpsURLConnection.setDefaultSSLSocketFactory(sf);
			
	        rURL = new URL(reqUrl);
	        logger.warn("CareCashApi Connecting to " + reqUrl);
	        System.out.println("CareCashApi Connecting to " + reqUrl);
	        	        
	        if(apiProxy.trim().equalsIgnoreCase("passby"))
	        {
	        	System.out.println("CareCashApi passby Proxy ");
	        	rConn = rURL.openConnection();
	        }	        	
	        else
	        {
	        	System.out.println("CareCashApi using Proxy ");
	        	rConn = rURL.openConnection(proxy);
	        }	        	
	        
	        rConn.setDoOutput(true);
	        rConn.setDoInput(true);
	        
	        rOSW = new OutputStreamWriter(rConn.getOutputStream());
	        logger.warn("CareCashApi Request: " + requestString);
	        System.out.println("CareCashApi Request: " + requestString);
	        rOSW.write(requestString);
	        rOSW.flush();

			rBR = new BufferedReader(new InputStreamReader(rConn.getInputStream()));
			
	        rSB = new StringBuffer();
	        
	        while ( (rStr = rBR.readLine()) != null) {                
                rSB.append(rStr);
            }
	        
	        String responseString = rSB.toString();
	        logger.warn("CareCashApi Response: " + responseString);	   
	        System.out.println("CareCashApi Response: " + responseString);
	        
	        return responseString;
	        
		}catch (RuntimeException rEx) {
        	logger.error(ExceptionUtils.getFullStackTrace(rEx));
//            throw rEx;
        }
        catch (Exception rEx) {
        	logger.error(ExceptionUtils.getFullStackTrace(rEx));
//            throw new RuntimeException(rEx);
        }
        finally {
            try {
                if (rBR != null) rBR.close();
                if (rOSW != null) rOSW.close();
            }
            catch (Exception rIgnEx)
            {
            }
        }
		return null;
				
	}
				
	public CareCashOptInArqDTO regCustomerIguardCareCash(CareCashOptInArqDTO arq) {
		try {
			String orderid = arq.getiFormNum();
			
			if(!arq.getiOptFor().equalsIgnoreCase("I"))
			{
				arq.setiFormNum("");
			}
			
			// call CS PORTAL here..
			arq.setApiTy("CARE_CASH_OPTIN");
			arq.setSysId(StringUtils.strip(systemId));
			arq.setSysPwd(StringUtils.strip(systemPwd));
			arq.setUserId("");
			arq.setPsnTy("");
			arq.setPhylum("");
			arq.setClnVer("");
			arq.setReply("");
			
			arq = callCareCashApi(arq, custIguardUrl);
			
			// save records into W_ONLINE_CSPORTAL_TXN
	        CsPortalTxnDTO csPortalTxn = new CsPortalTxnDTO();
			csPortalTxn.setApiTy(arq.getApiTy());
			csPortalTxn.setOrderId(orderid);
			csPortalTxn.setReply(arq.getReply());
			csPortalTxn.setReqObj(gson.toJson(arq));
			csPortalTxn.setSysId(arq.getSysId());
			csPortalTxn.setClubReply("");
			csPortalTxn.setMyhktReply("");
			this.createCsPortalTxn(csPortalTxn, arq.getUserId());
			
			if(StringUtils.isBlank(arq.getReply()))
			{
				arq.setReply("api has no reply");
			}
			
		} catch (RuntimeException rEx) {
			logger.error(ExceptionUtils.getFullStackTrace(rEx));
			if(rEx.getMessage().length()>4000)
			{
				arq.setReply(rEx.getMessage().substring(0, 4000));	
			}
			else
			{
				arq.setReply(rEx.getMessage());
			}
			
		} catch (Exception rEx) {
			logger.error(ExceptionUtils.getFullStackTrace(rEx));
			if(rEx.getMessage().length()>4000)
			{
				arq.setReply(rEx.getMessage().substring(0, 4000));	
			}
			else
			{
				arq.setReply(rEx.getMessage());
			}
		}		
		return arq;
	}
	
	static class DummyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String urlHostName, String certHostName) {
			return true;
		}

		public boolean verify(String urlHost, SSLSession sslSession) {
			return true;
		}
	}

	static class DefaultTrustManager implements X509TrustManager {

		public void checkClientTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws java.security.cert.CertificateException {
			// TODO Auto-generated method stub
		}

		public void checkServerTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws java.security.cert.CertificateException {
			// TODO Auto-generated method stub
		}

		public X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return null;
		}

	}
	
	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getSystemPwd() {
		return systemPwd;
	}

	public void setSystemPwd(String systemPwd) {
		this.systemPwd = systemPwd;
	}

	public String getCustIguardUrl() {
		return custIguardUrl;
	}

	public void setCustIguardUrl(String custIguardUrl) {
		this.custIguardUrl = custIguardUrl;
	}

	public String getApiProxy() {
		return apiProxy;
	}

	public void setApiProxy(String apiProxy) {
		this.apiProxy = apiProxy;
	}

	public int getApiPort() {
		return apiPort;
	}

	public void setApiPort(int apiPort) {
		this.apiPort = apiPort;
	}

	public CsPortalDAO getCsPortalDAO() {
		return csPortalDAO;
	}

	public void setCsPortalDAO(CsPortalDAO csPortalDAO) {
		this.csPortalDAO = csPortalDAO;
	}
	
	
	
}
