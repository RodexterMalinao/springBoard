package com.bomwebportal.ims.service;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bigmachines.soap.ActionType;
import com.bigmachines.soap.Add;
import com.bigmachines.soap.AddResponse;
import com.bigmachines.soap.AddUsers;
import com.bigmachines.soap.AddUsersResponse;
import com.bigmachines.soap.BooleanOrBlank;
import com.bigmachines.soap.CommerceServiceLocator;
import com.bigmachines.soap.CommerceServicePortType;
import com.bigmachines.soap.CriteriaType;
import com.bigmachines.soap.DataTablesDeployRequestType;
import com.bigmachines.soap.DataTablesRetrieveType;
import com.bigmachines.soap.DataTablesServiceLocator;
import com.bigmachines.soap.DataTablesServicePortType;
import com.bigmachines.soap.DataTablesType;
import com.bigmachines.soap.DataXmlType;
import com.bigmachines.soap.Delete;
import com.bigmachines.soap.DeleteResponse;
import com.bigmachines.soap.Deploy;
import com.bigmachines.soap.DeployResponse;
import com.bigmachines.soap.EachRecordType;
import com.bigmachines.soap.Fault;
import com.bigmachines.soap.Get;
import com.bigmachines.soap.GetResponse;
import com.bigmachines.soap.GetTransaction;
import com.bigmachines.soap.GetTransactionResponse;
import com.bigmachines.soap.GetTransactionType;
import com.bigmachines.soap.GetUser;
import com.bigmachines.soap.GetUserInfoType;
import com.bigmachines.soap.GetUserResponse;
import com.bigmachines.soap.GetUserType;
import com.bigmachines.soap.Login;
import com.bigmachines.soap.LoginRequestUserInfoType;
import com.bigmachines.soap.LoginResponse;
import com.bigmachines.soap.LogoutResponse;
import com.bigmachines.soap.SecurityServiceLocator;
import com.bigmachines.soap.SecurityServicePortType;
import com.bigmachines.soap.TransactionType;
import com.bigmachines.soap.CustomDoctransactionType;
import com.bigmachines.soap.Update;
import com.bigmachines.soap.UpdateResponse;
import com.bigmachines.soap.UpdateTransaction;
import com.bigmachines.soap.UpdateTransactionResponse;
import com.bigmachines.soap.UpdateUsers;
import com.bigmachines.soap.UpdateUsersResponse;
import com.bigmachines.soap.UserServiceLocator;
import com.bigmachines.soap.UserServicePortType;
import com.bigmachines.soap.UserType2;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dao.NowTVDAO;
import com.bomwebportal.ims.dto.CPQNTVInfo;
import com.bomwebportal.ims.dto.CPQNTVRetInfo;
import com.google.gson.Gson;
import com.jcraft.jsch.Logger;

public class CPQInterfacingWSServiceImpl implements CPQInterfacingWSService{
	private String desUri1;
	private String desUri2;
	private String desUri3;
	private String desUri4;
	private String desUri5;
	
	public void setDesUri5(String desUri5) {
		this.desUri5 = desUri5;
	}
	public String getDesUri5() {
		return desUri5;
	}
	public void setDesUri4(String desUri4) {
		this.desUri4 = desUri4;
	}
	public String getDesUri4() {
		return desUri4;
	}
	public void setDesUri3(String desUri3) {
		this.desUri3 = desUri3;
	}
	public String getDesUri3() {
		return desUri3;
	}
	public String getDesUri2() {
		return desUri2;
	}
	public void setDesUri2(String desUri2) {
		this.desUri2 = desUri2;
	}
	public String getDesUri1() {
		return desUri1;
	}
	public void setDesUri1(String desUri1) {
		this.desUri1 = desUri1;
	}
	
	private static Set<String> TRASACTION_LIST = Collections.synchronizedSet(new HashSet<String>());
	private int maxTransactionListSize = 100;
	
	
	public int getMaxTransactionListSize() {
		return maxTransactionListSize;
	}
	public void setMaxTransactionListSize(int maxTransactionListSize) {
		this.maxTransactionListSize = maxTransactionListSize;
	}

	public static final String CPQ_JSON_KEYWORD ="SB Transaction JSON";
	public static final String CPQ_PASSWORD ="sbims";
	protected final Log logger = LogFactory.getLog(getClass());	
	
	private Gson gson = new Gson();
	private NowTVDAO nowTVDAO;

	public void setNowTVDAO(NowTVDAO nowTVDAO) {
		this.nowTVDAO = nowTVDAO;
	}


	public NowTVDAO getNowTVDAO() {
		return nowTVDAO;
	}

	private SecurityServicePortType createSecurityService(String username, String pw) throws java.rmi.RemoteException, com.bigmachines.soap.Fault{
		SecurityServicePortType service;
		try { 
	
//			
//			System.getProperties().put("http.proxyHost", "proxy.pccw.com");
//			System.getProperties().put("http.proxyPort", "8080"); 
//			 
			System.out.println("desUri1:"+desUri1);
			System.out.println("desUri2:"+desUri2);
			SecurityServiceLocator ws = new SecurityServiceLocator();
			ws.setSecurityServicePortEndpointAddress(desUri1);
			
			service = ws.getSecurityServicePort();
			
			System.out.println("*********************** before call ******************");
			
			//WeeklyIndex i = locator.getMortgageIndexSoap().getCurrentMortgageIndexByWeekly();
			
			SOAPHeaderElement category = new SOAPHeaderElement("urn:soap.bigmachines.com", "category", "Security");
			
			SOAPHeaderElement xsdInfo = new SOAPHeaderElement("urn:soap.bigmachines.com", "xsdInfo");
			SOAPElement node = xsdInfo.addChildElement("schemaLocation");
			node.addTextNode(desUri2);

			category.appendChild(xsdInfo);
			 
			((Stub) service).setHeader(category);
			((Stub) service).setHeader("xsdInfo", "xsdInfo", xsdInfo);
		} catch (Exception e){
			System.out.println("*********************** Exception found ******************");
			System.out.println(e.getMessage());
			throw new AppRuntimeException(e);
		}
		System.out.println("*********************** WS End ******************");
	
		return service;
	};
	

	public LoginResponse login(String username, String pw) throws java.rmi.RemoteException, com.bigmachines.soap.Fault{
		SecurityServicePortType service=this.createSecurityService(username, pw);
		BooleanOrBlank b = new BooleanOrBlank();
		b.setValue(true);
		LoginRequestUserInfoType user = new LoginRequestUserInfoType(
				
				username,	//"superuser",	//username
				pw,			//"gHS2eEN_^E",	//pw
				//"",							//currency
				b							//sso_ind
		);
//		 
		Login l = new Login(user);
		LoginResponse _res = service.login(l);
		
		System.out.println("getSessionId " + _res.getUserInfo().getSessionId());  
		return _res;
		
	}

//	@Override
	public LogoutResponse logout(String username, String pw) throws RemoteException, Fault {
		SecurityServicePortType service=this.createSecurityService(username, pw);
		BooleanOrBlank b = new BooleanOrBlank();
		b.setValue(true);
		LoginRequestUserInfoType user = new LoginRequestUserInfoType(
				
				username,	//"superuser",	//username
				pw,			//"gHS2eEN_^E",	//pw
				//"",							//currency
				b							//sso_ind
		);
//		 
		Login l = new Login(user);
		LogoutResponse _res = service.logout(l);
		
		return _res;
		
	}


//	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public CPQNTVInfo saveCPQTransaction(String txnId, String orderActionInd, String amendSeq) throws Fault, RemoteException, ServiceException, SOAPException {
		
		String _session_id = login("sbims", "sbims").getUserInfo().getSessionId();
		long _txn_id = Long.parseLong(txnId);
		 
		BooleanOrBlank b = new BooleanOrBlank();
		b.setValue(true);
		CommerceServiceLocator ws = new CommerceServiceLocator(); 
	    ws.setCommerceServicePortEndpointAddress(desUri1);
	    CommerceServicePortType service = ws.getCommerceServicePort();
	    SOAPHeaderElement category = new SOAPHeaderElement("urn:soap.bigmachines.com", "category", "Commerce");
		
		SOAPHeaderElement xsdInfo = new SOAPHeaderElement("urn:soap.bigmachines.com", "xsdInfo");
		SOAPElement node = xsdInfo.addChildElement("schemaLocation");
		
		node.addTextNode(desUri3);

		SOAPHeaderElement userInfo = new SOAPHeaderElement("urn:soap.bigmachines.com", "userInfo");
		SOAPElement node2 = userInfo.addChildElement("sessionId");
		node2.addTextNode(_session_id);

		((Stub) service).setHeader(userInfo);
		((Stub) service).setHeader(category);
		((Stub) service).setHeader(xsdInfo);
		
		GetTransactionType txnType = new GetTransactionType(
				_txn_id,
				null
		);
		
		GetTransaction txn = new GetTransaction(txnType);
		
		logger.info("############ start txnId : " + _txn_id + " ########################");				
		
			GetTransactionResponse _res = service.getTransaction(txn);
			
			String selectedLine = _res.getTransaction().getData_xml().getTransaction().getSelectOfferCombo_cquote();
			String xxx = _res.getTransaction().getData_xml().getTransaction().getSub_documents()[Integer.valueOf(selectedLine)-1].get_config_attr_info();
			
			String[] rec_list = xxx.split("~");
			String jsonStr="";
			
			for (int i=0;i<rec_list.length;i++){
				if (CPQ_JSON_KEYWORD.equals(rec_list[i])) 
					jsonStr = rec_list[i+1];
			}			
			logger.info("jsonStr = " + jsonStr);
			logger.info("############ start txnId : " + _txn_id + " (end)########################");
			
			CPQNTVInfo cpqNTVInfo = new CPQNTVInfo();
				
			cpqNTVInfo=	gson.fromJson(jsonStr, cpqNTVInfo.getClass());
			
			logger.info("cpqNTVInfo: "+gson.toJson(cpqNTVInfo));
		
		if (!"W".equals(orderActionInd)) {
			try {
				nowTVDAO.saveCPQTransaction(cpqNTVInfo, txnId,"SBIMS");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new AppRuntimeException(e);
			}
		} else {
			if (!StringUtils.isEmpty(amendSeq)) {
				try {
					nowTVDAO.saveCPQTransactionforAmend(cpqNTVInfo, txnId, amendSeq);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new AppRuntimeException(e.getMessage(),e);
				}
			} else {
				throw new AppRuntimeException("orderId is empty");
			}
		}
		
		return cpqNTVInfo;
			
	}
	
//	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public CPQNTVRetInfo saveCPQTransactionRet(String txnId, String orderActionInd, String amendSeq, String createBy, String cpqEnv) throws Fault, RemoteException, ServiceException, SOAPException {
		if (TRASACTION_LIST.size()< maxTransactionListSize && !TRASACTION_LIST.contains(txnId))
		{
			try{
				TRASACTION_LIST.add(txnId);
				logger.info("Set transaction lock: "+txnId);
				CPQNTVRetInfo ret = new CPQNTVRetInfo();
				String jsonStr="";
				if(true){
				String _session_id = login("sbims", "sbims").getUserInfo().getSessionId();
				long _txn_id = Long.parseLong(txnId);
				 
				BooleanOrBlank b = new BooleanOrBlank();
				b.setValue(true);
				CommerceServiceLocator ws = new CommerceServiceLocator(); 
			    ws.setCommerceServicePortEndpointAddress(desUri1);
			    CommerceServicePortType service = ws.getCommerceServicePort();
			    SOAPHeaderElement category = new SOAPHeaderElement("urn:soap.bigmachines.com", "category", "Commerce");
				
				SOAPHeaderElement xsdInfo = new SOAPHeaderElement("urn:soap.bigmachines.com", "xsdInfo");
				SOAPElement node = xsdInfo.addChildElement("schemaLocation");
				
				node.addTextNode(desUri3);
		
				SOAPHeaderElement userInfo = new SOAPHeaderElement("urn:soap.bigmachines.com", "userInfo");
				SOAPElement node2 = userInfo.addChildElement("sessionId");
				node2.addTextNode(_session_id);
		
				((Stub) service).setHeader(userInfo);
				((Stub) service).setHeader(category);
				((Stub) service).setHeader(xsdInfo);
				
				GetTransactionType txnType = new GetTransactionType(
						_txn_id,
						null
				);
				
				GetTransaction txn = new GetTransaction(txnType);
				
				logger.info("############ start txnId : " + _txn_id + " ########################");
		//		try {
					
					GetTransactionResponse _res = service.getTransaction(txn);
					
					String selectedLine = _res.getTransaction().getData_xml().getTransaction().getSelectOfferCombo_cquote();
					String xxx = _res.getTransaction().getData_xml().getTransaction().getSub_documents()[Integer.valueOf(selectedLine)-1].get_config_attr_info();
					
					String[] rec_list = xxx.split("~");
					
					
					for (int i=0;i<rec_list.length;i++){
						if (CPQ_JSON_KEYWORD.equals(rec_list[i])) 
							jsonStr = rec_list[i+1];
					}
					logger.info("jsonStr = " + jsonStr);
					try {
						nowTVDAO.insertGsonTxn(new Long(_txn_id).toString(), jsonStr, createBy, cpqEnv);
					} catch (DAOException e) {
						logger.error("insertGsonTxn error:"+_txn_id,e);
					}
					logger.info("############ start txnId : " + _txn_id + " (end)########################");
					
					ret=gson.fromJson(jsonStr, ret.getClass());
					CPQNTVInfo acq = new CPQNTVInfo();			
		//			acq=gson.fromJson(jsonStr, acq.getClass());		
					logger.info("cpqNTVInfo: "+gson.toJson(ret));
					
					//steven wrong
			//			jsonStr="{\"IN\":{\"campaigns\":[null,{\"campaign_id\":\"CPKAL24MF0D88MOPA\",\"pack_id\":[\"Packs_Channel_Movie1\"],\"gift_id\":[],\"channel_gift_id\":[]},{\"campaign_id\":\"CPKAL24MF0D88MTPA\",\"pack_id\":[\"Packs_Channel_Movie2\"],\"gift_id\":[],\"channel_gift_id\":[]}],\"vas\":[\"ConnServ_HD_24M\",\"STB_BAU\"],\"paymentMethod\":[\"CreditCard\"],\"service\":{\"ID_DOC_TYPE\":\"HKID\",\"ID_DOC_NUM\":\"X123456(7)\",\"FSA\":\"3432423\"}},\"OUT\":{\"campaigns\":[{\"campaign_id\":\"XCBPH24MF0ENSD99A\",\"pack_id\":[\"Packs_Channel_TVB\",\"Packs_Channel_Ent1\"],\"gift_id\":[],\"channel_gift_id\":[]},{\"campaign_id\":\"CPKAL24MF0AddD50EAPO\",\"pack_id\":[\"Packs_Channel_AsiaEnt\"],\"gift_id\":[],\"channel_gift_id\":[\"101Chair\"]},{\"campaign_id\":\"PRCAL24MF0D148SP1A\",\"pack_id\":[\"Sports_Channel_Sports\"],\"gift_id\":[],\"channel_gift_id\":[]}],\"vas\":[\"ConnServ_HD_nowOne\",\"STB_AIO_RENTAL_24\"]},\"KEEP\":{\"campaigns\":[null,{\"campaign_id\":\"CPKAL24MF0D88MCPA\",\"pack_id\":[\"Packs_Channel_ChiMovies\"],\"gift_id\":[],\"channel_gift_id\":[]},{\"campaign_id\":\"CPKAL24MF0D68KNA\",\"pack_id\":[\"Packs_Channel_Knowledge\"],\"gift_id\":[],\"channel_gift_id\":[]},{\"campaign_id\":\"FTA_Pack_Mass\",\"pack_id\":[\"Starter_Pack\"],\"gift_id\":[],\"channel_gift_id\":[]},{\"campaign_id\":\"BONAL000000MASS\",\"pack_id\":[\"Special_Bonus\"],\"gift_id\":[],\"channel_gift_id\":[]}],\"vas\":[\"Acq50nowdollar\"]}}";
					//peter excel
			
			//		jsonStr="{\"IN\":{\"campaigns\":[{\"campaign_id\":\"FCBAL18MF01298SOCAA\",\"pack_id\":[\"Packs_Channel_AsiaEnt\",\"Sports_Channel_Soccer\",\"Packs_Channel_TVB\"],\"gift_id\":[\"Michi\"],\"channel_gift_id\":[\"101Chair\",\"101Chair\"]},{\"campaign_id\":\"FTA_Pack_Mass\",\"pack_id\":[\"Starter_Pack\"],\"gift_id\":[],\"channel_gift_id\":[]},{\"campaign_id\":\"BONAL000000MASS\",\"pack_id\":[\"Special_Bonus\"],\"gift_id\":[],\"channel_gift_id\":[]}],\"vas\":[\"ConnServ_HD_18M_PH\",\"Acq50nowdollar\",\"NowDollar_50D_V2MFree\",\"STB_BAU\"],\"paymentMethod\":[\"CreditCard\"],\"service\":{\"ID_DOC_TYPE\":\"HKID\",\"ID_DOC_NUM\":\"X123456(7)\",\"FSA\":\"3432423\"}},\"OUT\":{\"campaigns\":[{\"campaign_id\":\"camp3\",\"pack_id\":[\"a4\",\"a5\",\"a6\"]},{\"campaign_id\":\"camp4\",\"pack_id\":[\"b2\"]}],\"vas\":[\"vas3\",\"vas4\"]},\"KEEP\":{\"campaigns\":[{\"campaign_id\":\"camp1\",\"pack_id\":[\"a1\",\"a2\",\"a3\"]},{\"campaign_id\":\"camp2\",\"pack_id\":[\"b1\"]}],\"vas\":[\"vas1\",\"vas2\"]}}";
			
			//		if (!StringUtils.isEmpty(amendSeq)) {
			//		jsonStr="{\"IN\":{\"campaigns\":[{\"campaign_id\":\"FCBAL18MF01298SOCAA\",\"pack_id\":[\"Packs_Channel_AsiaEnt\",\"Sports_Channel_Soccer\",\"Packs_Channel_TVB\"],\"gift_id\":[\"Michi\"],\"channel_gift_id\":[\"101Chair\",\"101Chair\"]},{\"campaign_id\":\"FTA_Pack_Mass\",\"pack_id\":[\"Starter_Pack\"],\"gift_id\":[],\"channel_gift_id\":[]},{\"campaign_id\":\"BONAL000000MASS\",\"pack_id\":[\"Special_Bonus\"],\"gift_id\":[],\"channel_gift_id\":[]}],\"vas\":[\"ConnServ_HD_18M_PH\",\"Acq50nowdollar\",\"NowDollar_50D_V2MFree\",\"STB_BAU\"],\"paymentMethod\":[\"CreditCard\"],\"service\":{\"ID_DOC_TYPE\":\"HKID\",\"ID_DOC_NUM\":\"X123456(7)\",\"FSA\":\"3432423\"}},\"OUT\":{\"campaigns\":[{\"campaign_id\":\"camp1\",\"pack_id\":[\"a1\",\"a2\",\"a3\"]},{\"campaign_id\":\"camp2\",\"pack_id\":[\"b1\"]}],\"vas\":[\"vas1\",\"vas2\"]},\"KEEP\":{\"campaigns\":[{\"campaign_id\":\"camp3\",\"pack_id\":[\"d1\",\"d2\",\"d3\"]},{\"campaign_id\":\"camp4\",\"pack_id\":[\"e1\"]}],\"vas\":[\"vas3\",\"vas4\"]}}";
			//		}
					
					//remove "IN" in hard code
					//jsonStr="{\"IN\":{},\"OUT\":{\"campaigns\":[{\"campaign_id\":\"camp1\",\"pack_id\":[\"a1\",\"a2\",\"a3\"]},{\"campaign_id\":\"camp2\",\"pack_id\":[\"b1\"]}],\"vas\":[\"vas1\",\"vas2\"]},\"KEEP\":{\"campaigns\":[{\"campaign_id\":\"camp3\",\"pack_id\":[\"d1\",\"d2\",\"d3\"]},{\"campaign_id\":\"camp4\",\"pack_id\":[\"e1\"]}],\"vas\":[\"vas3\",\"vas4\"]}}";
					
					//very simple, out 1 keep 3
		//			jsonStr="{\"IN\":{},\"OUT\":{\"vas\":[\"vas1\"]},\"KEEP\":{\"vas\":[\"vas3\"]}}";
					
					//very simple reverse, in 3 keep 1
		//			if (!StringUtils.isEmpty(amendSeq)) {
		//			jsonStr="{\"IN\":{},\"OUT\":{\"vas\":[\"vas3\"]},\"KEEP\":{\"vas\":[\"vas1\"]}}";
		//			}
		//			logger.info("hard code jsonStr = " + jsonStr);
		//			ret=gson.fromJson(jsonStr, ret.getClass());
		//			logger.info("hardcode: "+gson.toJson(ret));			
		//			ret.IN=acq;
		//			logger.info("merge Ret: "+gson.toJson(ret));	
		//		} catch (Exception e) {
		//			logger.error("CPQ error",e);
		//			logger.error("use hard code Dummy object");
		//			jsonStr="{\"IN\":{\"campaigns\":[{\"campaign_id\":\"FCBAL18MF01298SOCAA\",\"pack_id\":[\"Packs_Channel_AsiaEnt\",\"Sports_Channel_Soccer\",\"Packs_Channel_TVB\"],\"gift_id\":[\"Michi\"],\"channel_gift_id\":[\"101Chair\",\"101Chair\"]},{\"campaign_id\":\"FTA_Pack_Mass\",\"pack_id\":[\"Starter_Pack\"],\"gift_id\":[],\"channel_gift_id\":[]},{\"campaign_id\":\"BONAL000000MASS\",\"pack_id\":[\"Special_Bonus\"],\"gift_id\":[],\"channel_gift_id\":[]}],\"vas\":[\"ConnServ_HD_18M_PH\",\"Acq50nowdollar\",\"NowDollar_50D_V2MFree\",\"STB_BAU\"],\"paymentMethod\":[\"CreditCard\"],\"service\":{\"ID_DOC_TYPE\":\"HKID\",\"ID_DOC_NUM\":\"X123456(7)\",\"FSA\":\"3432423\"}},\"OUT\":{\"campaigns\":[{\"campaign_id\":\"camp3\",\"pack_id\":[\"a4\",\"a5\",\"a6\"]},{\"campaign_id\":\"camp4\",\"pack_id\":[\"b2\"]}],\"vas\":[\"vas3\",\"vas4\"]},\"KEEP\":{\"campaigns\":[{\"campaign_id\":\"camp1\",\"pack_id\":[\"a1\",\"a2\",\"a3\"]},{\"campaign_id\":\"camp2\",\"pack_id\":[\"b1\"]}],\"vas\":[\"vas1\",\"vas2\"]}}";
		//			jsonStr="{\"IN\":{\"campaigns\":[{\"campaign_id\":\"FCBAL18MF01298SOCAA\",\"pack_id\":[\"Packs_Channel_AsiaEnt\",\"Sports_Channel_Soccer\",\"Packs_Channel_TVB\"],\"gift_id\":[\"Michi\"],\"channel_gift_id\":[\"101Chair\",\"101Chair\"]},{\"campaign_id\":\"FTA_Pack_Mass\",\"pack_id\":[\"Starter_Pack\"],\"gift_id\":[],\"channel_gift_id\":[]},{\"campaign_id\":\"BONAL000000MASS\",\"pack_id\":[\"Special_Bonus\"],\"gift_id\":[],\"channel_gift_id\":[]}],\"vas\":[\"ConnServ_HD_18M_PH\",\"Acq50nowdollar\",\"NowDollar_50D_V2MFree\",\"STB_BAU\"],\"paymentMethod\":[\"CreditCard\"],\"service\":{\"ID_DOC_TYPE\":\"HKID\",\"ID_DOC_NUM\":\"X123456(7)\",\"FSA\":\"3432423\"}, \"order_type\":\"NTVRET\", \"action_type\":\"UPGRADE\" ,\"camp_nature\":\"UPSELL\"},\"OUT\":{\"campaigns\":[{\"campaign_id\":\"camp3\",\"pack_id\":[\"a4\",\"a5\",\"a6\"]},{\"campaign_id\":\"camp4\",\"pack_id\":[\"b2\"]}],\"vas\":[\"vas3\",\"vas4\"]},\"KEEP\":{\"campaigns\":[{\"campaign_id\":\"camp1\",\"pack_id\":[\"a1\",\"a2\",\"a3\"]},{\"campaign_id\":\"camp2\",\"pack_id\":[\"b1\"]}],\"vas\":[\"vas1\",\"vas2\"]}}";
		//			jsonStr="{\"IN\":{\"campaigns\":[{\"campaign_id\":\"FCBAL18MF01298SOCAA\",\"pack_id\":[\"Packs_Channel_AsiaEnt\",\"Sports_Channel_Soccer\",\"Packs_Channel_TVB\"],\"gift_id\":[\"Michi\"],\"channel_gift_id\":[\"101Chair\",\"101Chair\"]},{\"campaign_id\":\"FTA_Pack_Mass\",\"pack_id\":[\"Starter_Pack\"],\"gift_id\":[],\"channel_gift_id\":[]},{\"campaign_id\":\"BONAL000000MASS\",\"pack_id\":[\"Special_Bonus\"],\"gift_id\":[],\"channel_gift_id\":[]}],\"vas\":[\"ConnServ_HD_18M_PH\",\"Acq50nowdollar\",\"NowDollar_50D_V2MFree\",\"STB_BAU\"],\"paymentMethod\":[\"CreditCard\"],\"service\":{\"ID_DOC_TYPE\":\"HKID\",\"ID_DOC_NUM\":\"X123456(7)\",\"FSA\":\"3432423\"}, \"order_type\":\"NTVRET\", \"action_type\":\"UPGRADE\" ,\"camp_nature\":\"UPSELL\"},\"OUT\":{\"campaigns\":[{\"campaign_id\":\"camp3\",\"pack_id\":[\"a4\",\"a5\",\"a6\"]},{\"campaign_id\":\"camp4\",\"pack_id\":[\"b2\"]}],\"vas\":[\"VCFC000053\",\"VCPC000566\",\"VCPC000647\",\"VCFC000302\",\"VCPC240007\",\"VCPC240007\",\"VCFC000029\",\"VCPC000624\"]},\"KEEP\":{\"campaigns\":[{\"campaign_id\":\"camp1\",\"pack_id\":[\"a1\",\"a2\",\"a3\"]},{\"campaign_id\":\"camp2\",\"pack_id\":[\"b1\"]}],\"vas\":[\"VCFC000277\",\"VCPC120231\",\"VCFC000058\",\"VCFC000010\",\"VCPC010024\",\"MO00000452\",\"MO00000412\"]}}";
		//			ret=gson.fromJson(jsonStr, ret.getClass());
		//		}	
				}else{
					//below is new Ret
					jsonStr="{\"IN\":{\"campaigns\":[{\"campaign_id\":\"FCBAL18MF01298SOCAA\",\"pack_id\":[\"Packs_Channel_AsiaEnt\",\"Sports_Channel_Soccer\",\"Packs_Channel_TVB\"],\"gift_id\":[\"Michi\"],\"channel_gift_id\":[\"101Chair\",\"101Chair\"]},{\"campaign_id\":\"FTA_Pack_Mass\",\"pack_id\":[\"Starter_Pack\"],\"gift_id\":[],\"channel_gift_id\":[]},{\"campaign_id\":\"BONAL000000MASS\",\"pack_id\":[\"Special_Bonus\"],\"gift_id\":[],\"channel_gift_id\":[]}],\"vas\":[\"ConnServ_HD_18M_PH\",\"Acq50nowdollar\",\"NowDollar_50D_V2MFree\",\"STB_BAU\"],\"paymentMethod\":[\"CreditCard\"],\"service\":{\"ID_DOC_TYPE\":\"HKID\",\"ID_DOC_NUM\":\"X123456(7)\",\"FSA\":\"3432423\"}, \"order_type\":\"NTVRET\", \"action_type\":\"UPGRADE\" ,\"camp_nature\":\"UPSELL\"},\"OUT\":{\"campaigns\":[{\"campaign_id\":\"camp3\",\"pack_id\":[\"a4\",\"a5\",\"a6\"]},{\"campaign_id\":\"camp4\",\"pack_id\":[\"b2\"]}],\"vas\":[\"VCFC000053\",\"VCPC000566\",\"VCPC000647\",\"VCFC000302\",\"VCPC240007\",\"VCPC240007\",\"VCFC000029\",\"VCPC000624\"]},\"KEEP\":{\"campaigns\":[{\"campaign_id\":\"camp1\",\"pack_id\":[\"a1\",\"a2\",\"a3\"]},{\"campaign_id\":\"camp2\",\"pack_id\":[\"b1\"]}],\"vas\":[\"VCFC000277\",\"VCPC120231\",\"VCFC000058\",\"VCFC000010\",\"VCPC010024\",\"MO00000452\",\"MO00000412\"]}}";
					//below is new ACQ
		//			jsonStr="{\"IN\":{\"campaigns\":[{\"campaign_id\":\"XCBAL24MF2ENTD238A \",\"pack_id\":[\"Packs_Channel_Junior\",\"Packs_Channel_ChiMovies\",\"Packs_Channel_News\"],\"gift_id\":[],\"channel_gift_id\":[]},{\"campaign_id\":\"2016SeasonFree\",\"pack_id\":[\"Sports_Season_2016\"],\"gift_id\":[],\"channel_gift_id\":[]},{\"campaign_id\":\"FTA_Pack_Mass\",\"pack_id\":[\"Starter_Pack\"],\"gift_id\":[],\"channel_gift_id\":[]},{\"campaign_id\":\"BONAL000000MASS\",\"pack_id\":[\"Special_Bonus\"],\"gift_id\":[],\"channel_gift_id\":[]}],\"vas\":[\"ConnServ_HD_24M_PH\",\"STB_BAU\",\"Acq50nowdollar\"],\"paymentMethod\":[\"CreditCard\"],\"IA\":{\"sb\":\"228670\",\"flr\":\"1\",\"flt\":\"1\"},\"order_type\":\"NTVACQ\",\"action_type\":\"\"},\"OUT\":{},\"KEEP\":{}}";
					//below is new ACQ 20160702
					jsonStr="{\"IN\":{\"campaigns\":[{\"campaign_id\":\"XCBAL24MF2ENTD238A\",\"pack_id\":[\"Packs_Channel_Junior\",\"Packs_Channel_ChiMovies\",\"Packs_Channel_News\"],\"gift_id\":[],\"channel_gift_id\":[]},{\"campaign_id\":\"2016SeasonFree\",\"pack_id\":[\"Sports_Season_2016\"],\"gift_id\":[],\"channel_gift_id\":[]}],\"vas\":[\"ConnServ_HD_24M_PH\",\"STB_BAU\",\"Acq50nowdollar\"],\"order_type\":\"NTVACQ\",\"action_type\":\"\",\"paymentMethod\":[\"CreditCard\"],\"IA\":{\"sb\":\"228670\",\"flr\":\"1\",\"flt\":\"1\"}},\"OUT\":{},\"KEEP\":{}}";
					//below is new ret 20160704
					jsonStr="{\"IN\":{\"campaigns\":[{\"campaign_id\":\"XCBAL18MF1SCPD298A\",\"pack_id\":[\"Packs_Channel_Movie1\",\"Packs_Channel_ChiMovies\",\"Sports_Channel_Soccer\"],\"gift_id\":[],\"channel_gift_id\":[]},{\"campaign_id\":\"CPKAL18MF0D68NEWA\",\"pack_id\":[\"Packs_Channel_News\"],\"gift_id\":[],\"channel_gift_id\":[]}],\"vas\":[\"ConnServ_HD_18M\",\"STB_BAU\",\"Acq50nowdollar\"],\"order_type\":\"NTVRET\",\"action_type\":\"UPGRADE\",\"paymentMethod\":[\"CreditCard\"],\"IA\":{\"sb\":\"228670\",\"flr\":\"98\",\"flt\":\"Q\"}},\"OUT\":{\"campaigns\":[{\"campaign_id\":\"CPKAL24MF0D68KNA\",\"pack_id\":[\"NOWKN1ALL\"]},{\"campaign_id\":\"CPKAL24MF0D88MTPA\",\"pack_id\":[\"NOWMT1ALL\"]}],\"vas\":[]},\"KEEP\":{\"campaigns\":[{\"campaign_id\":\"BONAL000000MASS\",\"pack_id\":[\"NOWMASSTAR\"]},{\"campaign_id\":\"FTA_Pack_Mass\",\"pack_id\":[\"NOWMAFFTA\"]}],\"vas\":[]}}";
					ret=gson.fromJson(jsonStr, ret.getClass());
				}
		
				if("NTVACQ".equalsIgnoreCase(ret.IN.order_type)){
					if (!"W".equals(orderActionInd)) {
						try {
							nowTVDAO.saveCPQTransaction(ret.IN, txnId, createBy);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							throw new AppRuntimeException(e);
						}
					} else {
						if (!StringUtils.isEmpty(amendSeq)) {
							try {
								nowTVDAO.saveCPQTransactionforAmend(ret.IN, txnId, amendSeq);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								throw new AppRuntimeException(e.getMessage(),e);
							}
						} else {
							throw new AppRuntimeException("orderId is empty");
						}
					}
				}else{
					if (!"W".equals(orderActionInd)) {
						try {
							nowTVDAO.saveCPQTransactionRet(ret, txnId,createBy);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							throw new AppRuntimeException(e);
						}
					} else {
						if (!StringUtils.isEmpty(amendSeq)) {
							try {
								nowTVDAO.saveCPQTransactionforAmendRet(ret, txnId, amendSeq);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								throw new AppRuntimeException(e.getMessage(),e);
							}
						} else {
							throw new AppRuntimeException("orderId is empty");
						}
					}
				}
					
				
				
				return ret;
			}catch(AppRuntimeException e){
				throw e;
			}
			finally{
				TRASACTION_LIST.remove(txnId);
				logger.info("Release transaction lock: "+txnId);
			}
		}else{
			logger.error("Duplicate txn id: "+txnId);
			throw new AppRuntimeException("", new Exception("Duplicate txn id: "+txnId));
		}
			
	}
	
	public void clearTransationList()
	{
		TRASACTION_LIST.clear();
	}
	
	public String updateCPQTransaction(String txnId,String orderId, String cpqStatus) throws Fault, RemoteException, ServiceException, SOAPException{
		String _session_id = login("sbims", "sbims").getUserInfo().getSessionId();
		long _txn_id = Long.parseLong(txnId);
		
		BooleanOrBlank b = new BooleanOrBlank();
		b.setValue(true);
		CommerceServiceLocator ws = new CommerceServiceLocator(); 
	    ws.setCommerceServicePortEndpointAddress(desUri1);
	    CommerceServicePortType service = ws.getCommerceServicePort();
	    SOAPHeaderElement category = new SOAPHeaderElement("urn:soap.bigmachines.com", "category", "Commerce");
		
		SOAPHeaderElement xsdInfo = new SOAPHeaderElement("urn:soap.bigmachines.com", "xsdInfo");
		SOAPElement node = xsdInfo.addChildElement("schemaLocation");
		
		node.addTextNode(desUri3);

		SOAPHeaderElement userInfo = new SOAPHeaderElement("urn:soap.bigmachines.com", "userInfo");
		SOAPElement node2 = userInfo.addChildElement("sessionId");
		node2.addTextNode(_session_id);

		((Stub) service).setHeader(userInfo);
		((Stub) service).setHeader(category);
		((Stub) service).setHeader(xsdInfo);
		
		TransactionType txnType = new TransactionType();
		
		txnType.setId(_txn_id);
		txnType.setAction_data(new ActionType());
		txnType.getAction_data().setAction_var_name("update");
		txnType.setData_xml(new DataXmlType());
		txnType.getData_xml().setTransaction(new CustomDoctransactionType());
		txnType.getData_xml().getTransaction().setSpringboardOrderNumber_cquote(orderId);
		txnType.getData_xml().getTransaction().setStatus_cquote(cpqStatus);
		txnType.getData_xml().getTransaction().set_document_number("1");
		
		UpdateTransaction txn = new UpdateTransaction(txnType);
		
		UpdateTransactionResponse _res = service.updateTransaction(txn);	
		
		logger.info("_res.getStatus().getRecords_updated()"+_res.getStatus().getRecords_updated());
		logger.info("_res.getStatus().getSuccess()"+_res.getStatus().getSuccess()+" "+(_res.getStatus().getMessage()== null?"":_res.getStatus().getMessage()));
		
		return _res.getStatus().getSuccess();
	}
	
	public String addCPQUsers(String userName) throws Fault, RemoteException, ServiceException, SOAPException{

		UserType2[] userType2 = null;
		try {
			userType2 = nowTVDAO.getCPQUserList("add");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(userType2 == null || userType2.length == 0){
			return "add record not found";
		}else{		

			String _session_id = login(userName, CPQ_PASSWORD).getUserInfo().getSessionId();

			BooleanOrBlank b = new BooleanOrBlank();
			b.setValue(true);

			UserServiceLocator ws = new UserServiceLocator(); 
			ws.setUserServicePortEndpointAddress(desUri1);

			UserServicePortType service = ws.getUserServicePort();

			System.out.println("*********************** UserServiceLocator before call ******************");

			SOAPHeaderElement category = new SOAPHeaderElement("urn:soap.bigmachines.com", "category", "Users");

			SOAPHeaderElement xsdInfo = new SOAPHeaderElement("urn:soap.bigmachines.com", "xsdInfo");
			SOAPElement node = xsdInfo.addChildElement("schemaLocation");
			node.addTextNode(desUri4);

			SOAPHeaderElement userInfo = new SOAPHeaderElement("urn:soap.bigmachines.com", "userInfo");
			SOAPElement node2 = userInfo.addChildElement("sessionId");
			node2.addTextNode(_session_id);

			((Stub) service).setHeader(userInfo);
			((Stub) service).setHeader(category);
			((Stub) service).setHeader(xsdInfo);		

			AddUsers users = new AddUsers(new GetUserType(userType2));

			AddUsersResponse _res = null;
			try{
				_res =service.addUsers(users);
			}catch(Exception e){
				if (e instanceof Fault) {
					logger.info("############### "+((Fault)e).getExceptionCode());
					logger.info("############### "+((Fault)e).getExceptionMessage());
					logger.info("############### "+((Fault)e).getExceptionDescription());
				} else e.printStackTrace();
			}
			if(_res!=null){
				logger.info("get user status "+_res.getStatus().getSuccess()+" "+_res.getStatus().getMessage());
				logger.info("get user detail "+_res.getDetailedStatus().getRecord(0).isSuccessful()+" "+((_res.getDetailedStatus().getRecord(0).getMessage()==null)?"":_res.getDetailedStatus().getRecord(0).getMessage()));

				if(_res.getStatus().getSuccess().equalsIgnoreCase("true")){
					try {
						nowTVDAO.updateCPQUser("add");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
			}
			return gson.toJson(_res==null?"add not success":_res);
		}		
	}
	public String updateCPQUsers(String userName) throws Fault, RemoteException, ServiceException, SOAPException{
		UserType2[] userType2 = null;
		try {
			userType2 = nowTVDAO.getCPQUserList("update");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(userType2 == null || userType2.length == 0){
			return "update record not found";
		}else{		

			String _session_id = login(userName, CPQ_PASSWORD).getUserInfo().getSessionId();

			BooleanOrBlank b = new BooleanOrBlank();
			b.setValue(true);

			UserServiceLocator ws = new UserServiceLocator(); 
			ws.setUserServicePortEndpointAddress(desUri1);

			UserServicePortType service = ws.getUserServicePort();

			System.out.println("*********************** UserServiceLocator before call ******************");

			SOAPHeaderElement category = new SOAPHeaderElement("urn:soap.bigmachines.com", "category", "Users");

			SOAPHeaderElement xsdInfo = new SOAPHeaderElement("urn:soap.bigmachines.com", "xsdInfo");
			SOAPElement node = xsdInfo.addChildElement("schemaLocation");
			node.addTextNode(desUri4);

			SOAPHeaderElement userInfo = new SOAPHeaderElement("urn:soap.bigmachines.com", "userInfo");
			SOAPElement node2 = userInfo.addChildElement("sessionId");
			node2.addTextNode(_session_id);

			((Stub) service).setHeader(userInfo);
			((Stub) service).setHeader(category);
			((Stub) service).setHeader(xsdInfo);

			UpdateUsers users = new UpdateUsers(new GetUserType(userType2));
			UpdateUsersResponse _res = null;
			try{
				_res = service.updateUsers(users);

			}catch (Exception e) {
				if (e instanceof Fault) {
					logger.info("############### "+((Fault)e).getExceptionCode());
					logger.info("############### "+((Fault)e).getExceptionMessage());
					logger.info("############### "+((Fault)e).getExceptionDescription());
				} else e.printStackTrace();
			}
			if(_res!=null){
				logger.info("get user status "+_res.getStatus().getSuccess()+" "+_res.getStatus().getMessage());
				logger.info("get user detail "+_res.getDetailedStatus().getRecord(0).isSuccessful()+" "+((_res.getDetailedStatus().getRecord(0).getMessage()==null)?"":_res.getDetailedStatus().getRecord(0).getMessage()));
				if(_res.getStatus().getSuccess().equalsIgnoreCase("true")){
					try {
						nowTVDAO.updateCPQUser("update");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}		
			}
			return gson.toJson(_res==null?"update not success":_res);
		}
	}

	public String addCPQUserTeam(String userName)throws Fault, RemoteException, ServiceException, SOAPException{
		EachRecordType[] records = null;
		try {
			records = nowTVDAO.getCPQUserTeamList("add");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(records == null || records.length == 0){
			return "add record not found";
		}else{
			try{
				String _session_id = login(userName, CPQ_PASSWORD).getUserInfo().getSessionId();

				BooleanOrBlank b = new BooleanOrBlank();
				b.setValue(true);
				
				DataTablesServiceLocator ws = new DataTablesServiceLocator(); 
				ws.setDataTablesServicePortEndpointAddress(desUri1);
				
				DataTablesServicePortType service = ws.getDataTablesServicePort();
				
				System.out.println("*********************** DataTablesServiceLocator before call ******************");
				
				SOAPHeaderElement category = new SOAPHeaderElement("urn:soap.bigmachines.com", "category", "Data Tables");
				
				SOAPHeaderElement xsdInfo = new SOAPHeaderElement("urn:soap.bigmachines.com", "xsdInfo");
				SOAPElement node = xsdInfo.addChildElement("schemaLocation");
				node.addTextNode(desUri5);

				SOAPHeaderElement userInfo = new SOAPHeaderElement("urn:soap.bigmachines.com", "userInfo");
				SOAPElement node2 = userInfo.addChildElement("sessionId");
				node2.addTextNode(_session_id);

				((Stub) service).setHeader(userInfo);
				((Stub) service).setHeader(category);
				((Stub) service).setHeader(xsdInfo);
				
				DataTablesType dataTablesType = new DataTablesType();
				
				dataTablesType.setTable_name("cpq_user_team");
				
				dataTablesType.setEach_record(records);
				
				Add addRecord = new Add(dataTablesType);
				
				AddResponse _res = service.add(addRecord);
				
				if(_res!=null){
					logger.info("get user team status "+_res.getStatus().getSuccess()+" "+_res.getStatus().getMessage()==null?"null":_res.getStatus().getMessage());
					if(_res.getStatus().getSuccess().equalsIgnoreCase("true")){
						try {
							nowTVDAO.updateCPQUserTeam("add");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				return gson.toJson(_res==null?"add not success":_res);
			}catch(Exception e){
				if (e instanceof Fault) {
					logger.info("############### "+((Fault)e).getExceptionCode());
					logger.info("############### "+((Fault)e).getExceptionMessage());
					logger.info("############### "+((Fault)e).getExceptionDescription());
				} else e.printStackTrace();
			}
			
			return "add not success";
		}
	}	
	
	public String updateCPQUserTeam(String userName)throws Fault, RemoteException, ServiceException, SOAPException{
		EachRecordType[] records = null;
		try {
			records = nowTVDAO.getCPQUserTeamList("update");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(records == null || records.length == 0){
			return "update record not found";
		}else{
			try{
				String _session_id = login(userName, CPQ_PASSWORD).getUserInfo().getSessionId();

				BooleanOrBlank b = new BooleanOrBlank();
				b.setValue(true);
				
				DataTablesServiceLocator ws = new DataTablesServiceLocator(); 
				ws.setDataTablesServicePortEndpointAddress(desUri1);
				
				DataTablesServicePortType service = ws.getDataTablesServicePort();
				
				System.out.println("*********************** DataTablesServiceLocator before call ******************");
				
				SOAPHeaderElement category = new SOAPHeaderElement("urn:soap.bigmachines.com", "category", "Data Tables");
				
				SOAPHeaderElement xsdInfo = new SOAPHeaderElement("urn:soap.bigmachines.com", "xsdInfo");
				SOAPElement node = xsdInfo.addChildElement("schemaLocation");
				node.addTextNode(desUri5);

				SOAPHeaderElement userInfo = new SOAPHeaderElement("urn:soap.bigmachines.com", "userInfo");
				SOAPElement node2 = userInfo.addChildElement("sessionId");
				node2.addTextNode(_session_id);

				((Stub) service).setHeader(userInfo);
				((Stub) service).setHeader(category);
				((Stub) service).setHeader(xsdInfo);
				
				DataTablesType dataTablesType = new DataTablesType();
				
				dataTablesType.setTable_name("cpq_user_team");
				
				dataTablesType.setEach_record(records);
				
				Update updRecord = new Update(dataTablesType);
				
				UpdateResponse _res = service.update(updRecord);
				
				if(_res!=null){
					logger.info("get user team status "+_res.getStatus().getSuccess()+" "+_res.getStatus().getMessage()==null?"null":_res.getStatus().getMessage());
					if(_res.getStatus().getSuccess().equalsIgnoreCase("true")){
						try {
							nowTVDAO.updateCPQUserTeam("update");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				return gson.toJson(_res==null?"update not success":_res);
			}catch(Exception e){
				if (e instanceof Fault) {
					logger.info("############### "+((Fault)e).getExceptionCode());
					logger.info("############### "+((Fault)e).getExceptionMessage());
					logger.info("############### "+((Fault)e).getExceptionDescription());
				} else e.printStackTrace();
			}
			
			return "update not success";
		}
	}	
	public String getCPQUserTeam(String userName)throws Fault, RemoteException, ServiceException, SOAPException{
		EachRecordType[] records = null;
		try {
			records = nowTVDAO.getCPQUserTeamList("get");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(records == null || records.length == 0){
			return "get record not found";
		}else{
			try{
				String _session_id = login(userName, CPQ_PASSWORD).getUserInfo().getSessionId();

				BooleanOrBlank b = new BooleanOrBlank();
				b.setValue(true);
				
				DataTablesServiceLocator ws = new DataTablesServiceLocator(); 
				ws.setDataTablesServicePortEndpointAddress(desUri1);
				
				DataTablesServicePortType service = ws.getDataTablesServicePort();
				
				System.out.println("*********************** DataTablesServiceLocator before call ******************");
				
				SOAPHeaderElement category = new SOAPHeaderElement("urn:soap.bigmachines.com", "category", "Data Tables");
				
				SOAPHeaderElement xsdInfo = new SOAPHeaderElement("urn:soap.bigmachines.com", "xsdInfo");
				SOAPElement node = xsdInfo.addChildElement("schemaLocation");
				node.addTextNode(desUri5);

				SOAPHeaderElement userInfo = new SOAPHeaderElement("urn:soap.bigmachines.com", "userInfo");
				SOAPElement node2 = userInfo.addChildElement("sessionId");
				node2.addTextNode(_session_id);

				((Stub) service).setHeader(userInfo);
				((Stub) service).setHeader(category);
				((Stub) service).setHeader(xsdInfo);
				
				DataTablesRetrieveType dataTablesType = new DataTablesRetrieveType();
				
				dataTablesType.setTable_name("cpq_user_team");
				
				CriteriaType[] criteria= new CriteriaType[4];
				criteria[0]=new CriteriaType();
				criteria[0].setField("login");
				criteria[1]=new CriteriaType();
				criteria[1].setField("sales_channel");
				criteria[2]=new CriteriaType();
				criteria[2].setField("sales_team");
				criteria[3]=new CriteriaType();
				criteria[3].setField("user_group");
				String[] tmp0 = new String[records.length];
				String[] tmp1 = new String[records.length];
				String[] tmp2 = new String[records.length];
				String[] tmp3 = new String[records.length];
				for(int i=0; i<records.length; i++){
					tmp0[i]=records[i].getLogin();
					tmp1[i]=records[i].getSales_channel();
					tmp2[i]=records[i].getSales_team();
					tmp3[i]=records[i].getUser_group();
				}
				
				criteria[0].setValue(tmp0);
				criteria[0].setComparator("=");
				criteria[1].setValue(tmp1);
				criteria[1].setComparator("=");
				criteria[2].setValue(tmp2);
				criteria[2].setComparator("=");
				criteria[3].setValue(tmp3);
				criteria[3].setComparator("=");
				
				dataTablesType.setCriteria(criteria);
				
				Get getRecord = new Get(dataTablesType);
			
				GetResponse _res = service.get(getRecord);
				
				if(_res!=null){
					logger.info("get user team status "+_res.getStatus().getSuccess()+" "+_res.getStatus().getMessage()==null?"null":_res.getStatus().getMessage());
					if(_res.getStatus().getSuccess().equalsIgnoreCase("true")){
						try {
							nowTVDAO.updateCPQUserTeam("get");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				return gson.toJson(_res==null?"get not success":_res);
			}catch(Exception e){
				if (e instanceof Fault) {
					logger.info("############### "+((Fault)e).getExceptionCode());
					logger.info("############### "+((Fault)e).getExceptionMessage());
					logger.info("############### "+((Fault)e).getExceptionDescription());
				} else e.printStackTrace();
			}
			
			return "get not success";
		}
	}	
	public String deleteCPQUserTeam(String userName)throws Fault, RemoteException, ServiceException, SOAPException{
		EachRecordType[] records = null;
		try {
			records = nowTVDAO.getCPQUserTeamList("delete");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(records == null || records.length == 0){
			return "delete record not found";
		}else{
			try{
				String _session_id = login(userName, CPQ_PASSWORD).getUserInfo().getSessionId();

				BooleanOrBlank b = new BooleanOrBlank();
				b.setValue(true);
				
				DataTablesServiceLocator ws = new DataTablesServiceLocator(); 
				ws.setDataTablesServicePortEndpointAddress(desUri1);
				
				DataTablesServicePortType service = ws.getDataTablesServicePort();
				
				System.out.println("*********************** DataTablesServiceLocator before call ******************");
				
				SOAPHeaderElement category = new SOAPHeaderElement("urn:soap.bigmachines.com", "category", "Data Tables");
				
				SOAPHeaderElement xsdInfo = new SOAPHeaderElement("urn:soap.bigmachines.com", "xsdInfo");
				SOAPElement node = xsdInfo.addChildElement("schemaLocation");
				node.addTextNode(desUri5);

				SOAPHeaderElement userInfo = new SOAPHeaderElement("urn:soap.bigmachines.com", "userInfo");
				SOAPElement node2 = userInfo.addChildElement("sessionId");
				node2.addTextNode(_session_id);

				((Stub) service).setHeader(userInfo);
				((Stub) service).setHeader(category);
				((Stub) service).setHeader(xsdInfo);
				
				DataTablesRetrieveType dataTablesType = new DataTablesRetrieveType();
				
				dataTablesType.setTable_name("cpq_user_team");
				
				CriteriaType[] criteria= new CriteriaType[4];
				criteria[0]=new CriteriaType();
				criteria[0].setField("login");
				criteria[1]=new CriteriaType();
				criteria[1].setField("sales_channel");
				criteria[2]=new CriteriaType();
				criteria[2].setField("sales_team");
				criteria[3]=new CriteriaType();
				criteria[3].setField("user_group");
				String[] tmp0 = new String[records.length];
				String[] tmp1 = new String[records.length];
				String[] tmp2 = new String[records.length];
				String[] tmp3 = new String[records.length];
				for(int i=0; i<records.length; i++){
					tmp0[i]=records[i].getLogin();
					tmp1[i]=records[i].getSales_channel();
					tmp2[i]=records[i].getSales_team();
					tmp3[i]=records[i].getUser_group();
				}
				
				criteria[0].setValue(tmp0);
				criteria[0].setComparator("=");
				criteria[1].setValue(tmp1);
				criteria[1].setComparator("=");
				criteria[2].setValue(tmp2);
				criteria[2].setComparator("=");
				criteria[3].setValue(tmp3);
				criteria[3].setComparator("=");
				
				dataTablesType.setCriteria(criteria);
				
				Delete dtlRecord = new Delete(dataTablesType);
			
				DeleteResponse _res = service.delete(dtlRecord);
				
				if(_res!=null){
					logger.info("delete user team status "+_res.getStatus().getSuccess()+" "+_res.getStatus().getMessage()==null?"null":_res.getStatus().getMessage());
					if(_res.getStatus().getSuccess().equalsIgnoreCase("true")){
						try {
							nowTVDAO.updateCPQUserTeam("delete");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				return gson.toJson(_res==null?"delete not success":_res);
			}catch(Exception e){
				if (e instanceof Fault) {
					logger.info("############### "+((Fault)e).getExceptionCode());
					logger.info("############### "+((Fault)e).getExceptionMessage());
					logger.info("############### "+((Fault)e).getExceptionDescription());
				} else e.printStackTrace();
			}
			
			return "delete not success";
		}
	}
	public String deployCPQUserTeam(String userName)throws Fault, RemoteException, ServiceException, SOAPException{

		try{
			String _session_id = login(userName, CPQ_PASSWORD).getUserInfo().getSessionId();

			BooleanOrBlank b = new BooleanOrBlank();
			b.setValue(true);

			DataTablesServiceLocator ws = new DataTablesServiceLocator(); 
			ws.setDataTablesServicePortEndpointAddress(desUri1);

			DataTablesServicePortType service = ws.getDataTablesServicePort();

			System.out.println("*********************** DataTablesServiceLocator before call ******************");

			SOAPHeaderElement category = new SOAPHeaderElement("urn:soap.bigmachines.com", "category", "Data Tables");

			SOAPHeaderElement xsdInfo = new SOAPHeaderElement("urn:soap.bigmachines.com", "xsdInfo");
			SOAPElement node = xsdInfo.addChildElement("schemaLocation");
			node.addTextNode(desUri5);

			SOAPHeaderElement userInfo = new SOAPHeaderElement("urn:soap.bigmachines.com", "userInfo");
			SOAPElement node2 = userInfo.addChildElement("sessionId");
			node2.addTextNode(_session_id);

			((Stub) service).setHeader(userInfo);
			((Stub) service).setHeader(category);
			((Stub) service).setHeader(xsdInfo);

			DataTablesDeployRequestType dataTablesType = new DataTablesDeployRequestType();

			dataTablesType.setTable_name("cpq_user_team");

			Deploy dpyRecord = new Deploy(dataTablesType);

			DeployResponse _res = service.deploy(dpyRecord);

			if(_res!=null){
				logger.info("deploy user team status "+_res.getStatus().getSuccess()+" "+_res.getStatus().getMessage()==null?"null":_res.getStatus().getMessage());					
			}
			return gson.toJson(_res==null?"deploy not success":_res);
		}catch(Exception e){
			if (e instanceof Fault) {
				logger.info("############### "+((Fault)e).getExceptionCode());
				logger.info("############### "+((Fault)e).getExceptionMessage());
				logger.info("############### "+((Fault)e).getExceptionDescription());
			} else e.printStackTrace();
		}

		return "deploy not success";
	}
	public String getCpqJsonRecord(String txnId) {		
		return nowTVDAO.getCpqJsonRecord(txnId);
	}
//	public String getCpqJsonRecordForTest(String testingOrderType) {		
//		return nowTVDAO.getCpqJsonRecordForTest(testingOrderType);
//	}
	public List<Map<String, String>> getCpqJsonRecordForTest(String testingOrderType){
		try {
			return nowTVDAO.getCpqJsonRecordForTest(testingOrderType);
		} catch (DAOException e) {
			logger.error("",e);
		}
		return null;		
	}

}
