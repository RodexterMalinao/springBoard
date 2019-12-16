package com.bomwebportal.lts.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dao.CsPortalDAO;
import com.bomwebportal.lts.dto.CsPortalIdInqArqDTO;
import com.bomwebportal.lts.dto.CsPortalIdRegrArqDTO;
import com.bomwebportal.lts.dto.CsPortalResponseDTO;
import com.bomwebportal.lts.dto.CsPortalTxnDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsCsPortalBackendConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.google.gson.Gson;

public class CsPortalServiceImpl implements CsPortalService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private String systemId;
	private String systemPwd;
	private String verfUrl;
	private String regUrl;
	private String regProUrl;
	
	private String csIdCheckUrl;
	private String csIdRegrUrl;
	
	private CsPortalDAO csPortalDAO;
	private Gson gson = new Gson();
	
	public void createCsPortalTxn(CsPortalTxnDTO csPortalTxn, String userId) {
		try {
			csPortalDAO.createCsPortalTxn(csPortalTxn, userId);
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e);
		}
	}
	
	private EmboArq callCsPortalApi(EmboArq arq, String reqUrl) throws Exception{
		String res = callCsPortalApiFinal(gson.toJson(arq), reqUrl);
		return StringUtils.isNotBlank(res)? gson.fromJson(res, arq.getClass()) : arq;
	}
	
	private CsPortalIdInqArqDTO callCsPortalApi(CsPortalIdInqArqDTO arq, String reqUrl) throws Exception{
		String res = callCsPortalApiFinal(gson.toJson(arq), reqUrl);
		return StringUtils.isNotBlank(res)? gson.fromJson(res, arq.getClass()) : arq;
	}
	
	private CsPortalIdRegrArqDTO callCsPortalApi(CsPortalIdRegrArqDTO arq, String reqUrl) throws Exception{
		String res = callCsPortalApiFinal(gson.toJson(arq), reqUrl);
		return StringUtils.isNotBlank(res)? gson.fromJson(res, arq.getClass()) : arq;
	}
	
	private String callCsPortalApiFinal(String requestString, String reqUrl) throws Exception{
		
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
			HttpsURLConnection
				.setDefaultHostnameVerifier(new DummyHostnameVerifier());
			HttpsURLConnection.setDefaultSSLSocketFactory(sf);
			
	        rURL = new URL(reqUrl);
	        logger.warn("Connecting to " + reqUrl);
	        rConn = rURL.openConnection();
	        rConn.setDoOutput(true);
	        rConn.setDoInput(true);
	        
	        rOSW = new OutputStreamWriter(rConn.getOutputStream());
	        logger.warn("Request: " + requestString);
	        rOSW.write(requestString);
	        rOSW.flush();

			rBR = new BufferedReader(new InputStreamReader(rConn.getInputStream()));
			
	        rSB = new StringBuffer();
	        
	        while ( (rStr = rBR.readLine()) != null) {                
                rSB.append(rStr);
            }
	        
	        String responseString = rSB.toString();
	        logger.warn("Response: " + responseString);	   
	        
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
	
	public CsPortalRes checkCsPortalCustExist(String docType, String docNum,
			String loginId, String userId) {
		
		EmboVerfArq rArq = new EmboVerfArq();

		try{
			rArq = new EmboVerfArq();
			rArq.apiTy = LtsCsPortalBackendConstant.API_TYPE_VERF;
			rArq.sysId = StringUtils.strip(systemId);
			rArq.sysPwd = StringUtils.strip(systemPwd);
	        rArq.iDocTy = docType;
	        rArq.iDocNum = docNum;
	        rArq.iLoginId = StringUtils.lowerCase(loginId);

            rArq = (EmboVerfArq) callCsPortalApi(rArq, verfUrl);
            
            logger.info("RES="+rArq.reply);

		}catch (RuntimeException rEx) {
        	logger.error(ExceptionUtils.getFullStackTrace(rEx));
//            throw rEx;
        }
        catch (Exception rEx) {
        	logger.error(ExceptionUtils.getFullStackTrace(rEx));
//            throw new RuntimeException(rEx);
        }
		
		if(LtsCsPortalBackendConstant.CSP_REPLY_SUCCESS.equals(rArq.reply)){
			return CsPortalRes.SUCCESS;
		}else if(ArrayUtils.contains(LtsCsPortalBackendConstant.CSP_REPLY_EMAIL_FAIL, rArq.reply)){
			return CsPortalRes.EMAIL_ERROR;
		}else if(LtsCsPortalBackendConstant.CSP_REPLY_CUST_ALREADY_REGISTER.equals(rArq.reply)){
			return CsPortalRes.ALDY_REG;
		}else if(LtsCsPortalBackendConstant.CSP_REPLY_DOC_NUM_6_DIGIT.equals(rArq.reply)){
			return CsPortalRes.DOCNUM_6_DIGIT;
		}
		return CsPortalRes.SYSTEM_ERROR;
	}

	public CsPortalRes regCsPortal(SbOrderDTO sbOrder, String userId, boolean isPremier, boolean isPro){
		if(sbOrder.getCustomers() != null){
			ServiceDetailLtsDTO srvDtlLts = LtsSbHelper.getLtsService(sbOrder);
			CustomerDetailLtsDTO cust = LtsSbHelper.getLtsService(sbOrder).getAccount().getCustomer();
			String docType = cust.getIdDocType(); //srvDtlLts.getActualDocType(); //
			String docNum = cust.getIdDocNum(); //srvDtlLts.getActualDocId(); //
			if(srvDtlLts != null && "Y".equals(srvDtlLts.getRecontractInd()) && srvDtlLts.getRecontract() != null){
				docType = srvDtlLts.getRecontract().getIdDocType();
				docNum = srvDtlLts.getRecontract().getIdDocNum();
			}
			String email = cust.getCsPortalLogin();
			String premierInd = isPremier? "Y" : "N";
			String nickName = cust.getTitle()+ " " + sbOrder.getCustomers()[0].getLastName();
			String mobNum = cust.getCsPortalMobile(); //LtsSbHelper.getLtsService(sbOrder).getAppointmentDtl().getCustContactMobile();
			String lang = "ENG".equals(sbOrder.getLangPref())?"en":"zh";
			String orderId = sbOrder.getOrderId();
			
			if(!isPro){
				return regCsPortal(docType, docNum, email, premierInd, nickName, mobNum, lang, orderId, userId);
			}else{
				String salesChannel = sbOrder.getSalesChannel();
				String teamCd = sbOrder.getSalesTeam();
				return regCsPortalPro(docType, docNum, email, premierInd, nickName, mobNum, lang, orderId, userId, salesChannel, teamCd);
			}

		}else{
			return CsPortalRes.SYSTEM_ERROR;
		}
		
	}
			
	private CsPortalRes regCsPortal(String docType, String docNum,
			String email, String premierInd, String nickName, String mobNum, 
			String lang, String orderId, String userId){

    	EmboRegrArq regArq = new EmboRegrArq();
    	
        try{
        	regArq.apiTy = LtsCsPortalBackendConstant.API_TYPE_REGR;
        	regArq.sysId = StringUtils.strip(systemId);
        	regArq.sysPwd = StringUtils.strip(systemPwd);
    		regArq.iDoc_Ty = docType;
    		regArq.iDoc_Num = docNum;
    		regArq.iPremier = premierInd;
    		regArq.iLogin_id = StringUtils.lowerCase(email);
    		regArq.iNick_name = nickName;
    		regArq.iCt_mob = mobNum;
    		regArq.iLang = lang;
    		regArq.iSec_qus = 0;
    		regArq.iAcptTnc = true;
	        
	        regArq = (EmboRegrArq) callCsPortalApi(regArq, regUrl);
	        
            logger.info("RES="+regArq.reply);
    		
			CsPortalTxnDTO csPortalTxn = new CsPortalTxnDTO();
			csPortalTxn.setApiTy(regArq.apiTy);
			csPortalTxn.setOrderId(orderId);
			csPortalTxn.setReply(regArq.reply);
			csPortalTxn.setReqObj(gson.toJson(regArq));
			csPortalTxn.setSysId(regArq.sysId);
			this.createCsPortalTxn(csPortalTxn, userId);
			
        }catch (RuntimeException rEx) {
        	logger.error(ExceptionUtils.getFullStackTrace(rEx));
        }
        catch (Exception rEx) {
        	logger.error(ExceptionUtils.getFullStackTrace(rEx));
        }
        
		if(LtsCsPortalBackendConstant.CSP_REPLY_SUCCESS.equals(regArq.reply)){
			return CsPortalRes.SUCCESS;
		}else if(ArrayUtils.contains(LtsCsPortalBackendConstant.CSP_REPLY_EMAIL_FAIL, regArq.reply)){
			return CsPortalRes.EMAIL_ERROR;
		}else if(LtsCsPortalBackendConstant.CSP_REPLY_CUST_ALREADY_REGISTER.equals(regArq.reply)){
			return CsPortalRes.ALDY_REG;
		}else if(LtsCsPortalBackendConstant.CSP_REPLY_DOC_NUM_6_DIGIT.equals(regArq.reply)){
			return CsPortalRes.DOCNUM_6_DIGIT;
		}
		return CsPortalRes.SYSTEM_ERROR;
	}
	
	private CsPortalRes regCsPortalPro(String docType, String docNum,
			String email, String premierInd, String nickName, String mobNum, 
			String lang, String orderId, String userId, String salesChannel, String teamCd){

    	EmboRegrProArq regProArq = new EmboRegrProArq();
    	
        try{
        	regProArq.apiTy = LtsCsPortalBackendConstant.API_TYPE_REGR_PRO;
        	regProArq.sysId = StringUtils.strip(systemId);
    		regProArq.sysPwd = StringUtils.strip(systemPwd);   		
    		regProArq.iDoc_Ty = docType;
    		regProArq.iDoc_Num = docNum;
    		regProArq.iPremier = premierInd;
    		regProArq.iLogin_id = StringUtils.lowerCase(email);
    		regProArq.iNick_name = nickName;
    		regProArq.iCt_mob = mobNum;
    		regProArq.iLang = lang;
    		regProArq.iSec_qus = 0;
    		regProArq.iAcptTnc = true;
    		regProArq.iSalesChl = salesChannel;
    		regProArq.iTeamCd = teamCd;
    		regProArq.iStaffId = userId;

	        regProArq = (EmboRegrProArq) callCsPortalApi(regProArq, regProUrl);
	        
            logger.info("RES="+regProArq.reply);
    		
			CsPortalTxnDTO csPortalTxn = new CsPortalTxnDTO();
			csPortalTxn.setApiTy(regProArq.apiTy);
			csPortalTxn.setOrderId(orderId);
			csPortalTxn.setReply(regProArq.reply);
			csPortalTxn.setReqObj(gson.toJson(regProArq));
			csPortalTxn.setSysId(regProArq.sysId);
			this.createCsPortalTxn(csPortalTxn, userId);
			
        }catch (RuntimeException rEx) {
        	logger.error(ExceptionUtils.getFullStackTrace(rEx));
        }
        catch (Exception rEx) {
        	logger.error(ExceptionUtils.getFullStackTrace(rEx));
        }
        
		if(LtsCsPortalBackendConstant.CSP_REPLY_SUCCESS.equals(regProArq.reply)){
			return CsPortalRes.SUCCESS;
		}else if(ArrayUtils.contains(LtsCsPortalBackendConstant.CSP_REPLY_EMAIL_FAIL, regProArq.reply)){
			return CsPortalRes.EMAIL_ERROR;
		}else if(LtsCsPortalBackendConstant.CSP_REPLY_CUST_ALREADY_REGISTER.equals(regProArq.reply)){
			return CsPortalRes.ALDY_REG;
		}else if(LtsCsPortalBackendConstant.CSP_REPLY_DOC_NUM_6_DIGIT.equals(regProArq.reply)){
			return CsPortalRes.DOCNUM_6_DIGIT;
		}
		return CsPortalRes.SYSTEM_ERROR;
	}
	
	public CsPortalResponseDTO checkCsIdForTheClubAndHkt(String docType, String docNum, 
			String hktLoginId, String clubLoginId, String userId) {		
		CsPortalIdInqArqDTO arq = new CsPortalIdInqArqDTO();		
		// set up the request
		arq.setiDocTy(docType);
		arq.setiDocNum(docNum);
		arq.setiLi4MyHkt(StringUtils.lowerCase(hktLoginId));
		arq.setiLi4Club(StringUtils.lowerCase(clubLoginId));
		// call CS PORTAL here..
		return checkCsIdForTheClubAndHkt(arq, userId);		    
	}
	
	public CsPortalResponseDTO checkCsIdForTheClubAndHkt(CsPortalIdInqArqDTO arq, String userId) {
		try {
			// call CS PORTAL here..
			arq.setApiTy(LtsCsPortalBackendConstant.API_TYPE_CSID_CHECK);
			arq.setSysId(StringUtils.strip(systemId));
			arq.setSysPwd(StringUtils.strip(systemPwd));
			arq = (CsPortalIdInqArqDTO) callCsPortalApi(arq, csIdCheckUrl);
			arq.setRtnCd(LtsCsPortalBackendConstant.CSP_REPLY_SUCCESS.equals(arq.getReply())? 
					CsPortalResponseDTO.RETURN_SUCCESS : CsPortalResponseDTO.RETURN_ERROR);
			String rtnDesc = !StringUtils.isBlank(arq.getoIdStatus())?
					CsPortalResponseDTO.getReplyDesc(arq.getoIdStatus()):
						!StringUtils.isBlank(arq.getoMyHktLiStatus())?
							CsPortalResponseDTO.getReplyDesc(arq.getoMyHktLiStatus()):
								!StringUtils.isBlank(arq.getoClubLiStatus())?
										CsPortalResponseDTO.getReplyDesc(arq.getoClubLiStatus()):
											CsPortalResponseDTO.getReplyDesc(arq.getReply());									
			arq.setRtnDesc(!StringUtils.isBlank(rtnDesc)?rtnDesc:
				LtsCsPortalBackendConstant.CSP_REPLY_SUCCESS.equals(arq.getReply())? 
						CsPortalResponseDTO.RETURN_SUCCESS : CsPortalResponseDTO.RETURN_ERROR);
		} catch (RuntimeException rEx) {
			arq.setRtnCd(CsPortalResponseDTO.RETURN_FATAL);
			arq.setRtnDesc(rEx.getMessage());
			logger.error(ExceptionUtils.getFullStackTrace(rEx));			
		} catch (Exception rEx) {
			arq.setRtnCd(CsPortalResponseDTO.RETURN_FATAL);
			arq.setRtnDesc(rEx.getMessage());
			logger.error(ExceptionUtils.getFullStackTrace(rEx));
		}
		return arq;
	}
	
	/* targetAcct:
	 * LtsCsPortalBackendConstant.MY_HKT_AND_THE_CLUB_REGISTER = To register both my HKT and the Club
	 * LtsCsPortalBackendConstant.MY_HKT_REGISTER_ONLY = To register my HKT only
	 * LtsCsPortalBackendConstant.THE_CLUB_REGISTER_ONLY = To register the Club only 
	 */
	public CsPortalResponseDTO regCsIdForTheClubAndHkt(SbOrderDTO sbOrder, String userId, 
			String srcSys, boolean isPremier, String targetAcct) {
		CsPortalIdRegrArqDTO arq = new CsPortalIdRegrArqDTO();       
    	ServiceDetailLtsDTO srvDtlLts = LtsSbHelper.getLtsService(sbOrder);	
    	CustomerDetailLtsDTO cust = srvDtlLts.getAccount().getCustomer();
		// set up the request
		arq.setiDocTy("Y".equals(srvDtlLts.getRecontractInd()) && srvDtlLts.getRecontract() != null?
				srvDtlLts.getRecontract().getIdDocType() : cust.getIdDocType());
		arq.setiDocNum("Y".equals(srvDtlLts.getRecontractInd()) && srvDtlLts.getRecontract() != null?
				srvDtlLts.getRecontract().getIdDocNum() : cust.getIdDocNum());			
    	arq.setiPremier(isPremier? "Y" : "N");
    	arq.setiLob(LtsBackendConstant.LOB_LTS);
    	arq.setiSrvNum(srvDtlLts.getSrvNum());
    	arq.setiSalesChl(sbOrder.getSalesChannel());
        arq.setiTeamCd(sbOrder.getSalesTeam());
        arq.setiStaffId(userId);
    	arq.setiSrcSys(srcSys);
    	arq.setiOrderNo(sbOrder.getOrderId());
    	if (StringUtils.equals(LtsCsPortalBackendConstant.MY_HKT_AND_THE_CLUB_REGISTER, targetAcct)) {
    		arq.setiMyHktLi(StringUtils.lowerCase(cust.getCsPortalLogin()));
    		arq.setiCt_mob(cust.getCsPortalMobile());
    		arq.setiClubLi(StringUtils.lowerCase(cust.getTheClubLogin()));
    	}
    	if (StringUtils.equals(LtsCsPortalBackendConstant.MY_HKT_REGISTER_ONLY, targetAcct)) {
    		arq.setiMyHktLi(StringUtils.lowerCase(cust.getCsPortalLogin()));
    		arq.setiCt_mob(cust.getCsPortalMobile());
    	}
    	if (StringUtils.equals(LtsCsPortalBackendConstant.THE_CLUB_REGISTER_ONLY, targetAcct)) {
    		arq.setiClubLi(StringUtils.lowerCase(cust.getTheClubLogin()));
    		arq.setiCt_mob(cust.getTheClubMobile());
    	}
    	arq.setiNick_name(cust.getTitle()+ " " + cust.getLastName());
		arq.setiAcptTnc("Y");
		arq.setiLang(LtsBackendConstant.DISTRIBUTE_LANG_ENGLISH.equals(sbOrder.getLangPref())?"en":"zh");
		arq.setiClubOptOutPromo(srvDtlLts.getAccount().getCustomer().getClubOptOut());
		arq.setiClubOptOutRea(srvDtlLts.getAccount().getCustomer().getClubOptRea());
		arq.setiClubOptOutRem(srvDtlLts.getAccount().getCustomer().getClubOptRmk());
		arq.setiMyHktOptOutPm(srvDtlLts.getAccount().getCustomer().getHktOptOut());
		// call CS PORTAL here..
		return regCsIdForTheClubAndHkt(arq, sbOrder.getOrderId(), userId);
	}
	
	public CsPortalResponseDTO regCsIdForTheClubAndHkt(CsPortalIdRegrArqDTO arq, String sbOrderId, String userId) {
		try {
			// call CS PORTAL here..
			arq.setApiTy(LtsCsPortalBackendConstant.API_TYPE_CSID_REGISTER);
			arq.setSysId(StringUtils.strip(systemId));
			arq.setSysPwd(StringUtils.strip(systemPwd));
			arq = (CsPortalIdRegrArqDTO) callCsPortalApi(arq, csIdRegrUrl);
			// save records into W_ONLINE_CSPORTAL_TXN
	        CsPortalTxnDTO csPortalTxn = new CsPortalTxnDTO();
			csPortalTxn.setApiTy(arq.getApiTy());
			csPortalTxn.setOrderId(sbOrderId);
			csPortalTxn.setReply(arq.getReply());
			csPortalTxn.setReqObj(gson.toJson(arq));
			csPortalTxn.setSysId(arq.getSysId());
			csPortalTxn.setClubReply(arq.getoClubRes());
			csPortalTxn.setMyhktReply(arq.getoMyHktRes());
			this.createCsPortalTxn(csPortalTxn, userId);
			arq.setRtnCd(LtsCsPortalBackendConstant.CSP_REPLY_SUCCESS.equals(arq.getReply())? 
					CsPortalResponseDTO.RETURN_SUCCESS : CsPortalResponseDTO.RETURN_ERROR);			
			String rtnDesc = !StringUtils.isBlank(arq.getoMyHktResEnMsg())?arq.getoMyHktResEnMsg():
						!StringUtils.isBlank(arq.getoClubResMsg())?arq.getoClubResMsg():"";										
			arq.setRtnDesc(!StringUtils.isBlank(rtnDesc)?rtnDesc:
				LtsCsPortalBackendConstant.CSP_REPLY_SUCCESS.equals(arq.getReply())? 
						CsPortalResponseDTO.RETURN_SUCCESS : CsPortalResponseDTO.RETURN_ERROR);			
		} catch (RuntimeException rEx) {
			arq.setRtnCd(CsPortalResponseDTO.RETURN_FATAL);
			arq.setRtnDesc(rEx.getMessage());
			logger.error(ExceptionUtils.getFullStackTrace(rEx));			
		} catch (Exception rEx) {
			arq.setRtnCd(CsPortalResponseDTO.RETURN_FATAL);
			arq.setRtnDesc(rEx.getMessage());
			logger.error(ExceptionUtils.getFullStackTrace(rEx));
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
	
	public boolean isCsldReplyFail(String pReplyCd){
		
		Set<String> returnCodeSet = new HashSet<String>();
		returnCodeSet.add(LtsCsPortalBackendConstant.CSP_REPLY_BUSY);						
		returnCodeSet.add(LtsCsPortalBackendConstant.CSP_REPLY_GATE_BUSY);
		
		return returnCodeSet.contains(pReplyCd);
		
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
		public String iLoginId;

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
	
	public class EmboRegrProArq extends EmboRegrArq{
		public String iSalesChl;
		public String iTeamCd;
		public String iStaffId;
		public String oZhMsg;
		public String oEnMsg;
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

	public String getVerfUrl() {
		return verfUrl;
	}

	public void setVerfUrl(String verfUrl) {
		this.verfUrl = verfUrl;
	}

	public String getRegUrl() {
		return regUrl;
	}

	public void setRegUrl(String regUrl) {
		this.regUrl = regUrl;
	}

	public String getRegProUrl() {
		return regProUrl;
	}

	public void setRegProUrl(String regProUrl) {
		this.regProUrl = regProUrl;
	}

	public CsPortalDAO getCsPortalDAO() {
		return csPortalDAO;
	}

	public void setCsPortalDAO(CsPortalDAO csPortalDAO) {
		this.csPortalDAO = csPortalDAO;
	}

	/**
	 * @return the csIdCheckUrl
	 */
	public String getCsIdCheckUrl() {
		return csIdCheckUrl;
	}

	/**
	 * @param csIdCheckUrl the csIdCheckUrl to set
	 */
	public void setCsIdCheckUrl(String csIdCheckUrl) {
		this.csIdCheckUrl = csIdCheckUrl;
	}

	/**
	 * @return the csIdRegrUrl
	 */
	public String getCsIdRegrUrl() {
		return csIdRegrUrl;
	}

	/**
	 * @param csIdRegrUrl the csIdRegrUrl to set
	 */
	public void setCsIdRegrUrl(String csIdRegrUrl) {
		this.csIdRegrUrl = csIdRegrUrl;
	}

}
