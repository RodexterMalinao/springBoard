package com.bomwebportal.ims.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.ims.dao.CSPortalDAO;
import com.google.gson.Gson;


public class CsPortalServiceImpl implements CsPortalService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private static SSLContext ctx;
	
	private static final String CS_SUCESS = "RC_SUCC";
	private static final String CS_ALREADY_REG = "RC_CUS_ALDY_REG";
	private static final String CS_ALREADY_LOGIN_REG = "RC_LOGIN_ID_EXIST";	
	private static final String CS_LESS_THAN_6_DIGITS = "RC_ARQ_IVDOCNUM6DIG";

	private static final String CS_NOT_REG = "RC_NO_CUSTOMER";
	private static final String CS_EXIST_CUST = "RC_CUST_EXIST";
	private static final String CS_IN_USE = "RC_IN_USE";
	
	private final Gson gson = new Gson();

	private String CsVerUrl;
	private String CsRegUrl;	
	private String CsRetrUrl;
	private String CsRegrProUrl;
	

	private String SysId;
	private String SysPwd;
	private static final String verApiTy = "CSLD_IDCK";
	private static final String regApiTy = "EMBO_REGR";
	private static final String inqApiTy = "EMBO_INQ";
	private static final String regrProApiTy = "CSLD_REG";
	
	private CSPortalDAO csDao;
	
	/*
	 * 	GDCN UAT: https://10.87.120.207:8143/donut/sa/emboRegr.sa
	 * 	Production: https://customerservices.pccw.com/cs/sa/emboRegr.sa

		GDCN UAT: https://10.87.120.207:8143/donut/sa/emboVerf.sa
		Production: https://customerservices.pccw.com/cs/sa/emboVerf.sa

	 */
	
	public static void main(String[] args){
		System.out.println("start");
//		try{
//			
//			EmboVerfArq rArq = new EmboVerfArq();
//			EmboVerfArq rRqt = rArq;
//			
//			rArq.apiTy = verApiTy;
//			rArq.sysId = "RAYH";
//			rArq.sysPwd = "uat1234";
//
//			rArq.iDocTy = "PASS";
//			rArq.iDocNum = "A12345";
//			rArq.iLoginId = "";						
//			
//			CsPortalServiceImpl testsrv = new CsPortalServiceImpl();
//			
//			rRqt = (EmboVerfArq)testsrv.callCsPortalApi(rArq, "https://10.87.120.207:8043/donut/sa/emboVerf.sa");
//			
//			System.out.println("reply:"+rRqt.reply);
//			
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//////////////
		CsPortalServiceImpl testsrv = new CsPortalServiceImpl();
		
		boolean isReg = false;
		
		try{
			EmboInqArq rRqt = null;
			
			try {
										
				EmboInqArq rArq = new EmboInqArq();
				rRqt = rArq;								
				
				rArq.apiTy = "EMBO_INQ";
				rArq.sysId = "RAYH";
				rArq.sysPwd = "uat1234";
	
				rArq.iDocTy = "PASS";
				rArq.iDocNum = "FH3111133123";
				//rArq.iLoginId = "";							
				
				rRqt = (EmboInqArq)testsrv.callCsPortalApi(rArq, "https://10.87.120.207:8043/donut/sa/emboInq.sa");
				
				System.out.println(rRqt.reply);
				System.out.println(rRqt.oLoginId);
				
				//osDao.insertCsPortalLog(orderId, rRqt.apiTy, rRqt.sysId, rRqt.reply, gson.toJson(rRqt));
				
			}catch(Exception e){
				e.printStackTrace();
			}
		
			
			
		}catch(Exception e){
			throw new AppRuntimeException(e); 
		}		
		//logger.info("isReg:" + 
		
		

	
		System.out.println("end");		
	}

	private EmboArq callCsPortalApi(EmboArq arq, String reqUrl) throws Exception{
		
		System.out.println( "url:"+reqUrl);
		System.out.println("input:"+gson.toJson(arq));
		
		URL rURL;
		URLConnection rConn;
		OutputStreamWriter rOSW;
		BufferedReader rBR;
		String rStr;
		StringBuffer rSB;		

		rBR = null;
		rOSW = null;
		
		try {

			// set dummy ssl trust manager			
			
			//Gson gson = new Gson();			

			rConn = getDummySSLConnection(reqUrl);
			//if(true)throw new Exception("testing");////////////
			rConn.setDoOutput(true);

			rOSW = new OutputStreamWriter(rConn.getOutputStream());
			rOSW.write(gson.toJson(arq));
			rOSW.flush();

			rBR = new BufferedReader(new InputStreamReader(
					rConn.getInputStream()));
			rSB = new StringBuffer();

			while ((rStr = rBR.readLine()) != null) {				
				logger.debug(rStr);
				rSB.append(rStr);
			}
			
			return gson.fromJson(rSB.toString(), arq.getClass());
			
			
		} finally {
			try {
				if (rBR != null)
					rBR.close();
				if (rOSW != null)
					rOSW.close();
			} catch (Exception rIgnEx) {
			}
		}
				
	}

	public String[] isCsPortalReg(String docType, String docNum, String hktId, String ClubId) {
		logger.info("isCsPortalReg is called");
		logger.info("docType:" + docType);
		logger.info("docNum:" + docNum);
		logger.info("hktId:" + hktId);
		logger.info("ClubId:" + ClubId);

		String retrieveLoginId = null;
		String retrieveClubLoginId = null;
		
		String reserveLoginId = null;
		String reserveClubLoginId = null;
		
		String[] result = new String[4];
		
		try{
			EmboVerfArq rRqt = null;
			
			try {
										
				EmboVerfArq rArq = new EmboVerfArq();
				rRqt = rArq;
				
				rArq.apiTy = verApiTy;
				rArq.sysId = SysId;
				rArq.sysPwd = SysPwd;
	
				rArq.iDocTy = docType;
				rArq.iDocNum = docNum;
				rArq.iLi4MyHkt = hktId;		
				rArq.iLi4Club = ClubId;
				
				rRqt = (EmboVerfArq)callCsPortalApi(rArq, CsVerUrl);
				logger.info("rRqt.oIdStatus"+rRqt.oIdStatus);
				logger.info("rRqt.oCorrMyHktLi"+rRqt.oCorrMyHktLi);
				logger.info("rRqt.oCorrClubLi"+rRqt.oCorrClubLi);
				logger.info("rRqt.oCorrClubMbrId"+rRqt.oCorrClubMbrId);
				
				if(CS_SUCESS.equals(rRqt.reply)){
					
					logger.info("call API success.");
				
					if(hktId == null && ClubId == null){
				
						if(CS_ALREADY_REG.equals(rRqt.oIdStatus)){
							retrieveLoginId = rRqt.oCorrMyHktLi;
						}else if(CS_NOT_REG.equals(rRqt.oIdStatus)||CS_EXIST_CUST.equals(rRqt.oIdStatus)){
							retrieveLoginId = "not exist";
						}else{
							retrieveLoginId = "<APIFailed>";
						}
				
						if(rRqt.oCorrClubLi==null||rRqt.oCorrClubLi.isEmpty()){
							retrieveClubLoginId = "not exist";
						}else if(rRqt.oCorrClubLi!=null&&!rRqt.oCorrClubLi.isEmpty()){
							retrieveClubLoginId = rRqt.oCorrClubLi;
						}else{
							retrieveClubLoginId = "<APIFailed>";
						}
				
						reserveLoginId = null;
						reserveClubLoginId = null;
				
					}else if(hktId != null && ClubId != null){
						if(CS_IN_USE.equals(rRqt.oMyHktLiStatus)){
							reserveLoginId = "fail";
						}else{
							reserveLoginId = "success";
						}
						if(CS_IN_USE.equals(rRqt.oClubLiStatus)){
							reserveClubLoginId = "fail";
						}else{
							reserveClubLoginId = "success";
						}
						retrieveLoginId = null;
						retrieveClubLoginId = null;
					}else if(hktId != null){
						if(CS_IN_USE.equals(rRqt.oMyHktLiStatus)){
							reserveLoginId = "fail";
						}else{
							reserveLoginId = "success";
						}
						reserveClubLoginId = null;
						retrieveLoginId = null;
						retrieveClubLoginId = null;
					}else if(ClubId != null){
						if(CS_IN_USE.equals(rRqt.oClubLiStatus)){
							reserveClubLoginId = "fail";
						}else{
							reserveClubLoginId = "success";
						}
						reserveLoginId = null;
						retrieveLoginId = null;
						retrieveClubLoginId = null;
					}
				}else{
					logger.info("call API failed.");
					retrieveLoginId = "<APIFailed>";
					retrieveClubLoginId = "<APIFailed>";
					reserveLoginId = "fail";
					reserveClubLoginId = "fail";
				}
					
				
				//tmp comment tony
//				if(CS_ALREADY_REG.equals(rRqt.reply)||CS_ALREADY_LOGIN_REG.equals(rRqt.reply)||CS_LESS_THAN_6_DIGITS.equals(rRqt.reply)){
//					
//					retrieveLoginId = RetrieveLoginId(docType, docNum);
//				}else
//					retrieveLoginId = "not exist";
				
				

				csDao.insertCsPortalLog("", rRqt.apiTy, rRqt.sysId, rRqt.reply, gson.toJson(rRqt));
				
				
			}catch(Exception e){
				rRqt.reply = "CALL_API_EXCEPTION";
				rRqt.oIdStatus = e.toString();
				csDao.insertCsPortalLog("", rRqt.apiTy, rRqt.sysId, rRqt.reply, gson.toJson(rRqt));
				logger.error("", e);
				retrieveLoginId = "<APIFailed>";
				retrieveClubLoginId = "<APIFailed>";
			}
		/* value to be returned
		 * 
		 * 1)any API failed  return "<APIFailed>"
		 * 2)existing myhkt cust return loginname
		 * 3)not existing myhkt cust return "not exist"
		 * 4)cannot retrieve for existing myhkt cust return null
		 */
			
			
		}catch(Exception e){
			throw new AppRuntimeException(e); 
		}		
		logger.info("retrieveLoginId:" + retrieveLoginId);
		logger.info("retrieveClubLoginId:" + retrieveClubLoginId);
		logger.info("reserveLoginId:" + reserveLoginId);
		logger.info("reserveClubLoginId:" + reserveClubLoginId);

		result[0] = retrieveLoginId;
		result[1] = retrieveClubLoginId;
		result[2] = reserveLoginId;
		result[3] = reserveClubLoginId;
		
		return result;

	}	
	
	

	public void RegCsPortalReg(String orderId, String docType, String docNum, String loginId, String mobileNum, String lang, String nickname) {
		
		try{
			
			EmboRegrArq rRqt = null;
			
			try {	
				
				EmboRegrArq rArq = new EmboRegrArq();
				rRqt = rArq;
				rArq.apiTy = regApiTy;
				rArq.sysId = SysId;
				rArq.sysPwd = SysPwd;

				rArq.iDoc_Ty = docType;
				rArq.iDoc_Num = docNum;
				rArq.iLogin_id = loginId.toLowerCase();
										
				//default input
				rArq.iPremier = "N";
				rArq.iAcptTnc = true;
				rArq.iCt_mob = mobileNum;
				rArq.iLang = lang;
				rArq.iNick_name = nickname;
				
				rRqt = (EmboRegrArq)callCsPortalApi(rArq, CsRegUrl);								
				
			}catch(Exception e){
				logger.error("", e);
			}
			
			csDao.insertCsPortalLog(orderId, rRqt.apiTy, rRqt.sysId, rRqt.reply, gson.toJson(rRqt));
			
		}catch(Exception e){
			throw new AppRuntimeException(e); 
		}
		
	}	
	
public String RetrieveLoginId(String docType, String docNum) {
		
	String retrieveResult = null;
	try{
		EmboInqArq rRqt = null;
		
		try {
									
			EmboInqArq rArq = new EmboInqArq();
			rRqt = rArq;
			
			rArq.apiTy = inqApiTy;
			rArq.sysId = SysId;
			rArq.sysPwd = SysPwd;

			rArq.iDocTy = docType;
			logger.info("docNum123: "+docNum);
			rArq.iDocNum = docNum;						
			logger.info(CsRetrUrl);
			rRqt = (EmboInqArq)callCsPortalApi(rArq,CsRetrUrl);
			
			logger.info("reply: " + rRqt.reply);
			logger.info("login ID: "+ rRqt.oLoginId);
			retrieveResult = rRqt.oLoginId;
			//osDao.insertCsPortalLog(orderId, rRqt.apiTy, rRqt.sysId, rRqt.reply, gson.toJson(rRqt));
			
		}catch(Exception e){
			e.printStackTrace();
			retrieveResult = "<APIFailed>";
		}
	}catch(Exception e){
		throw new AppRuntimeException(e); 
	}
	logger.info(retrieveResult);
	return retrieveResult;
}	
	
public void RegMyHKTClubReg(String orderId, String docType, String docNum, String hktId, String clubId, String mobileNum, String lang, String nickname, String salesChannel, String shopCd, String staffNum) {
	
	try{
		EmboRegrPro rRqt = null;
		
		try {

			logger.info("orderId: "+orderId);
			logger.info("docType"+docType);
			logger.info("docNum: "+docNum);
			logger.info("hktId: "+hktId);
			logger.info("clubId: "+clubId);
			logger.info("mobileNum: "+mobileNum);
			logger.info("lang: "+lang);
			logger.info("nickname: "+nickname);
			logger.info("salesChannel: "+salesChannel);
			logger.info("shopCd: "+shopCd);
			logger.info("staffNum: "+staffNum);
			
			EmboRegrPro rArq = new EmboRegrPro();
			rRqt = rArq;
			
			rArq.apiTy = regrProApiTy;
			rArq.sysId = SysId;
			rArq.sysPwd = SysPwd;

			rArq.iDocTy = docType;
			rArq.iDocNum = docNum;
			if(hktId!=null&&hktId.length()>0){
				rArq.iMyHktLi = hktId.toLowerCase();
			}else{
				rArq.iMyHktLi = "";
			}
			if(clubId!=null&&clubId.length()>0){
				rArq.iClubLi = clubId.toLowerCase();
			}else{
				rArq.iClubLi = "";
			}
									
			//default input
			rArq.iSrcSys = "SB";
			rArq.iLob = "IMS";
			rArq.iPremier = "N";
			rArq.iAcptTnc = "Y";
			rArq.iCt_mob = mobileNum;
			rArq.iLang = lang;
			rArq.iNick_name = nickname;
			
			rArq.iSalesChl = salesChannel;
			rArq.iTeamCd = shopCd;
			rArq.iStaffId = staffNum;

			if(hktId!=null&&hktId.length()>0&&clubId!=null&&clubId.length()>0){
				rArq.iSrvNum = hktId.toLowerCase().substring(0, hktId.toLowerCase().indexOf('@'));
			}else if(clubId!=null&&clubId.length()>0){
				rArq.iSrvNum = clubId.toLowerCase().substring(0, clubId.toLowerCase().indexOf('@'));
			}else{
				rArq.iSrvNum = "";
			}
			
			rArq.iOrderNo = orderId;
			
			rRqt = (EmboRegrPro)callCsPortalApi(rArq,CsRegrProUrl);
											
			
		}catch(Exception e){
			logger.error("", e);
		}
		
		csDao.insertCsPortalLog(orderId, rRqt.apiTy, rRqt.sysId, rRqt.reply, gson.toJson(rRqt));
		
	}catch(Exception e){
		throw new AppRuntimeException(e); 
	}
	
}	
	
public void RegrPro(String orderId, String docType, String docNum, String loginId, String mobileNum, String lang, String nickname, String salesChannel, String shopCd, String staffNum) {
	
	try{
		EmboRegrPro rRqt = null;
		
		try {
									
			EmboRegrPro rArq = new EmboRegrPro();
			rRqt = rArq;
			
			rArq.apiTy = regrProApiTy;
			rArq.sysId = SysId;
			rArq.sysPwd = SysPwd;

			rArq.iDocTy = docType;
			rArq.iDocNum = docNum;
//			rArq.iLogin_id = loginId.toLowerCase();
									
			//default input
			rArq.iPremier = "N";
			rArq.iAcptTnc = "Y";
			rArq.iCt_mob = mobileNum;
			rArq.iLang = lang;
			//rArq.iNick_name = nickname;
			
			rArq.iSalesChl = salesChannel;
			rArq.iTeamCd = shopCd;
			rArq.iStaffId = staffNum;

			
			
			rRqt = (EmboRegrPro)callCsPortalApi(rArq,CsRegrProUrl);
											
			
		}catch(Exception e){
			logger.error("", e);
		}
		
		csDao.insertCsPortalLog(orderId, rRqt.apiTy, rRqt.sysId, rRqt.reply, gson.toJson(rRqt));
		
	}catch(Exception e){
		throw new AppRuntimeException(e); 
	}
	
}	

	static class EmboArq{
		
		public String apiTy;
		public String reply;
		public String clnVer;
		public String sysId;
		public String sysPwd;
		public String userId;
		public String psnTy;
		
	}

	static class EmboVerfArq extends EmboArq{
		
		public String iDocTy;
		public String iDocNum;
		public String iLi4MyHkt;
	    //Tony
		public String iLi4Club;
	    public String oIdStatus;
	    public String oCorrMyHktLi;
	    public String oCorrClubLi;
	    public String oCorrClubMbrId;
	    public String oMyHktLiStatus;
	    public String oClubLiStatus;

	}

	static class EmboInqArq extends EmboArq{
   	    
	    public String iDocTy;
	    public String iDocNum;
	    public String oLoginId;
	}
	
static class EmboRegrPro extends EmboArq{
   	    
	public String iDocTy;
    public String iDocNum;
    public String iPremier;
    public String iLob;
    public String iSrvNum;
    public String iSalesChl;
    public String iTeamCd;
    public String iStaffId;
    public String iSrcSys;
    public String iOrderNo;
    
    
    public String iMyHktLi;
    public String iClubLi;
//    public String iPwd;     
    public String iNick_name;
//    public String iCt_mail; 
    public String iCt_mob;  
    public String iAcptTnc;  
    public String iLang;     
//    public int    iSec_qus;  
//    public String iSec_ans;  
    
    public String oMyHktRes;
    public String oClubRes;
	}

	
	public static class DefaultTrustManager implements X509TrustManager {

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
	
	public static URLConnection getDummySSLConnection(String url) throws Exception{
		
		
		if (ctx==null){
			ctx = SSLContext.getInstance("SSL");
			ctx.init(new KeyManager[0],
					new TrustManager[] { new DefaultTrustManager() },
					new SecureRandom());
			SSLSocketFactory sf = ctx.getSocketFactory();
			HttpsURLConnection
					.setDefaultHostnameVerifier(new DummyHostnameVerifier());
			HttpsURLConnection.setDefaultSSLSocketFactory(sf);
		}
		
		return new URL(url).openConnection();
			
					
	}
	
	public static class DummyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String urlHostName, String certHostName) {
			return true;
		}

		public boolean verify(String urlHost, SSLSession sslSession) {
			return true;
		}
	}
	
	public class EmboRegrArq extends EmboArq{
				
		public String iDoc_Ty;
		public String iDoc_Num;
		public String iPremier;
		public String iLogin_id;
		public String iPwd;
		public String iNick_name;
		public String iCt_mail;
		public String iCt_mob;
		public String iLang;
		public int iSec_qus;
		public String iSec_ans;
		public boolean iAcptTnc;
		
	}	
	
	
	public String getCsVerUrl() {
		return CsVerUrl;
	}

	public void setCsVerUrl(String csVerUrl) {
		CsVerUrl = csVerUrl;
	}

	public String getCsRegUrl() {
		return CsRegUrl;
	}

	public void setCsRegUrl(String csRegUrl) {
		CsRegUrl = csRegUrl;
	}

	public String getSysId() {
		return SysId;
	}

	public void setSysId(String sysId) {
		SysId = sysId;
	}

	public String getSysPwd() {
		return SysPwd;
	}

	public void setSysPwd(String sysPwd) {
		SysPwd = sysPwd;
	}

	public CSPortalDAO getCsDao() {
		return csDao;
	}

	public void setCsDao(CSPortalDAO csDao) {
		this.csDao = csDao;
	}

	public void setCsRetrUrl(String csRetrUrl) {
		CsRetrUrl = csRetrUrl;
	}

	public String getCsRetrUrl() {
		return CsRetrUrl;
	}
	public String getCsRegrProUrl() {
		return CsRegrProUrl;
	}
	public void setCsRegrProUrl(String csRegrProUrl) {
		CsRegrProUrl = csRegrProUrl;
	}

}
