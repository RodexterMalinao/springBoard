package com.bomwebportal.mob.ccs.web;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.mob.ccs.dto.ApprovalLogDTO;
import com.bomwebportal.mob.ccs.dto.SalesInfoDTO;
import com.bomwebportal.mob.ccs.service.GoldenMrtAdminService;
import com.bomwebportal.mob.ccs.service.MobCcsApprovalLogService;
import com.bomwebportal.mob.ccs.service.MobCcsMrtService;
import com.bomwebportal.mob.ccs.service.MobCcsSalesInfoService;
import com.bomwebportal.mob.ccs.util.BomWebPortalCcsConstant;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.util.Utility;

public class MobCcsMrtAJAXController implements Controller{
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    private MobCcsMrtService mobCcsMrtService;
    private GoldenMrtAdminService goldenMrtAdminService;
    //add by Eliot 20120514
    private MobCcsSalesInfoService mobCcsSalesInfoService;
    private MobCcsApprovalLogService mobCcsApprovalLogService;

    public MobCcsMrtService getMobCcsMrtService() {
        return mobCcsMrtService;
    }

    public void setMobCcsMrtService(MobCcsMrtService mobCcsMrtService) {
        this.mobCcsMrtService = mobCcsMrtService;
    }
    
    
    public GoldenMrtAdminService getGoldenMrtAdminService() {
        return goldenMrtAdminService;
    }

    public void setGoldenMrtAdminService(GoldenMrtAdminService goldenMrtAdminService) {
        this.goldenMrtAdminService = goldenMrtAdminService;
    }

    public MobCcsSalesInfoService getMobCcsSalesInfoService() {
		return mobCcsSalesInfoService;
	}

	public void setMobCcsSalesInfoService(
			MobCcsSalesInfoService mobCcsSalesInfoService) {
		this.mobCcsSalesInfoService = mobCcsSalesInfoService;
	}

	public MobCcsApprovalLogService getMobCcsApprovalLogService() {
		return mobCcsApprovalLogService;
	}

	public void setMobCcsApprovalLogService(
			MobCcsApprovalLogService mobCcsApprovalLogService) {
		this.mobCcsApprovalLogService = mobCcsApprovalLogService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {

		String type = this.getStringValue(request, "type");
			
		JSONArray jsonArray = new JSONArray();
		
		BomSalesUserDTO bomsalesuser = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		String channelCd = null;
		if (bomsalesuser != null) {
			channelCd = bomsalesuser.getChannelCd();
		}
		
		CustomerProfileDTO sessionCustomer = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
		String numType = null;
		if (sessionCustomer != null) {
			numType = sessionCustomer.getNumType();
		}
		
		if("GetRandomMrtLvl".equals(type)){
		    
		    String msisdnLvl = mobCcsMrtService.getNewMRTMsisdnLvl(this.getStringValue(request, "msisdn"),
			    						       BomWebPortalCcsConstant.MRT_NEW_NUM_STATUS_FREE);
		    
		    jsonArray.add("{\"msisdnLvl\":\"" + msisdnLvl + "\"}");
		}
		
		if("PCCWUnicomMrtNum".equals(type)){
	
		    List<String[]> mrtNumList = mobCcsMrtService.getUnicomMrtNumAndLvl(this.getStringValue(request, "channelId"), 
			    						       this.getStringValue(request, "cityCd"));
		 
		    Iterator<String[]> itr = mrtNumList.iterator();
		    while(itr.hasNext()){
				String[] list = itr.next();
				jsonArray.add("{\"msisdn\":\""	+ list[0]
				    	+ "\",\"msisdnlvl\":\""	+ list[1] + "\"}");
		    }	  
		}
		
		if("NewMrtSearch".equals(type)){
		    
		    boolean valid = false;
		    
		    try{
		    	valid = mobCcsMrtService.newMRTSearch(this.getStringValue(request, "msisdn"), channelCd, numType);
		    	//Dennis MIP3
			
				if(valid){
				    jsonArray.add("{\"result\":\"success\"}");
				}else{
				    jsonArray.add("{\"result\":\"fail\"}");
				}
		    }catch(Exception e){
		    	logger.error(e);
		    	jsonArray.add("{\"result\":\"fail\"}");
		    }
		}
		
		if("goldenMRTSearch".equals(type)){
		    
		    try{
				/*List<String[]> lists =  mobCcsMrtService.getGoldenNumSearchList("0", 
												this.getStringValue(request, "period"), 
												this.getStringValue(request, "charge"),
												"%" + this.getStringValue(request, "searchMsisdn") + "%");*/
		    	
		    	//add 20141103
		    	String appDateStr = this.getStringValue(request, "appDate");
		    	Date appDate = StringUtils.isBlank(appDateStr) ? null : Utility.string2Date(appDateStr);
		    	logger.info("appDateStr : " + appDateStr);
		    	logger.info("appDate : " + appDate);
		    	
		    	List<String[]> lists =  mobCcsMrtService.getGoldenNumSearchList("0", 
						this.getStringValue(request, "period"), 
						this.getStringValue(request, "charge"),
						"%" + this.getStringValue(request, "searchMsisdn") + "%",
						this.getStringValue(request, "grossPlanFee"),
						appDate,
						sessionCustomer.getBrand()
						);
				
				if (lists == null) {
				    lists = Collections.emptyList();
				    jsonArray = JSONArray.fromObject(lists);
				}else{			
				    Iterator<String[]> itr = lists.iterator();
				    while (itr.hasNext()){
				    	String[] item = itr.next();
								    
						jsonArray.add("{\"msisdn\":\""	+ item[0]
							    	+ "\",\"msisdnlvl\":\""	+ item[1] + "\"}");
				    }
				}
		    }catch(Exception e){
		    	logger.error(e);
		    	jsonArray.add("{\"result\":\"fail\"}");
		    }
		}
		
		if("patternMRTSearch".equals(type)){
		    
		    try{
		    	String appDateStr = this.getStringValue(request, "appDate");
		    	Date appDate = StringUtils.isBlank(appDateStr) ? null : Utility.string2Date(appDateStr);
		    	logger.info("appDateStr : " + appDateStr);
		    	logger.info("appDate : " + appDate);
		    	
		    	
		    	String teamCdOrCenterCd = null;
		    	if("MDV".equalsIgnoreCase(bomsalesuser.getChannelCd())){
		    		teamCdOrCenterCd = bomsalesuser.getShopCd();
		    	}else{
		    		teamCdOrCenterCd = bomsalesuser.getAreaCd();
		    	}
		    	
		    	List<String[]> lists =  mobCcsMrtService.getPatternNumSearchList(bomsalesuser.getChannelCd(), 	
		    			teamCdOrCenterCd,
						 this.getStringValue(request, "searchMsisdn").replace('*', '%') ,
						 numType
						); //Dennis MIP3
				
				if (lists == null) {
				    lists = Collections.emptyList();
				    jsonArray = JSONArray.fromObject(lists);
				}else{			
				    Iterator<String[]> itr = lists.iterator();
				    while (itr.hasNext()){
				    	String[] item = itr.next();
								    
						jsonArray.add("{\"msisdn\":\""	+ item[0]
							    	+ "\",\"msisdnlvl\":\""	+ item[1] + "\"}");
				    }
				}
		    }catch(Exception e){
		    	logger.error(e);
		    	jsonArray.add("{\"result\":\"fail\"}");
		    }
		}
		//Ds Multi
			if("patternMRTSearchMul".equals(type)){
		    
		    try{
		    	String appDateStr = this.getStringValue(request, "appDate");
		    	Date appDate = StringUtils.isBlank(appDateStr) ? null : Utility.string2Date(appDateStr);
		    	logger.info("appDateStr : " + appDateStr);
		    	logger.info("appDate : " + appDate);
		    	
		    	
		    	String teamCdOrCenterCd = null;
		    	if("MDV".equalsIgnoreCase(bomsalesuser.getChannelCd())){
		    		teamCdOrCenterCd = bomsalesuser.getShopCd();
		    	}else{
		    		teamCdOrCenterCd = bomsalesuser.getAreaCd();
		    	}
		    	
		    	List<String[]> lists =  mobCcsMrtService.getPatternNumSearchList(bomsalesuser.getChannelCd(), 	
		    			teamCdOrCenterCd,
						 this.getStringValue(request, "searchMsisdn").replace('*', '%') ,
						 numType
						); //Dennis MIP3
				
				if (lists == null) {
				    lists = Collections.emptyList();
				    jsonArray = JSONArray.fromObject(lists);
				}else{			
				    Iterator<String[]> itr = lists.iterator();
				    while (itr.hasNext()){
				    	String[] item = itr.next();
								    
						jsonArray.add("{\"msisdn\":\""	+ item[0]
							    	+ "\",\"msisdnlvl\":\""	+ item[1] + "\"}");
				    }
				}
		    }catch(Exception e){
		    	logger.error(e);
		    	jsonArray.add("{\"result\":\"fail\"}");
		    }
		}
		
			if("patternMRTSearchCcs".equals(type)){
		    
		    try{
 	
		    	String channelShopCd = null;
		    	channelShopCd = bomsalesuser.getChannelCd();
		    		
		    	
		    	
		    	List<String[]> lists =  mobCcsMrtService.getPatternNumSearchListCcs(channelShopCd, 	
						this.getStringValue(request, "searchMsisdn").replace('*', '%') ,
						numType
						);
				
				if (lists == null) {
				    lists = Collections.emptyList();
				    jsonArray = JSONArray.fromObject(lists);
				}else{			
				    Iterator<String[]> itr = lists.iterator();
				    while (itr.hasNext()){
				    	String[] item = itr.next();
								    
						jsonArray.add("{\"msisdn\":\""	+ item[0]
							    	+ "\",\"msisdnlvl\":\""	+ item[1] + "\"}");
				    }
				}
		    }catch(Exception e){
		    	logger.error(e);
		    	jsonArray.add("{\"result\":\"fail\"}");
		    }
		}
			
			
			if("patternMRTSearchCcsMul".equals(type)){
			    
			    try{
	 	
			    	List<String[]> lists =  mobCcsMrtService.getPatternNumSearchListCcsMul(	
							 this.getStringValue(request, "searchMsisdn").replace('*', '%') 
							
							);
					
					if (lists == null) {
					    lists = Collections.emptyList();
					    jsonArray = JSONArray.fromObject(lists);
					}else{			
					    Iterator<String[]> itr = lists.iterator();
					    while (itr.hasNext()){
					    	String[] item = itr.next();
									    
							jsonArray.add("{\"msisdn\":\""	+ item[0]
								    	+ "\",\"msisdnlvl\":\""	+ item[1] + "\"}");
					    }
					}
			    }catch(Exception e){
			    	logger.error(e);
			    	jsonArray.add("{\"result\":\"fail\"}");
			    }
			}
		
		if("mnpOrderExistCancellingStatus".equals(type)){
		    
		    try{
		    	List<String> lists = null;
		    	if(BomWebPortalCcsConstant.RECALL_ORDER.equals(this.getStringValue(request, "orderStatus")))
		    		lists =  mobCcsMrtService.getOrderExistWithMsisdnOrderIdByPCancelling(this.getStringValue(request, "msisdn"),
		    																			  this.getStringValue(request, "orderId"));
		    	else
		    		lists =  mobCcsMrtService.getOrderExistWithMsisdnByPCancelling(this.getStringValue(request, "msisdn"));
			
		    	if (lists == null || lists.isEmpty()) {
		    		jsonArray.add("{\"orderIdExist\":\"no\",\"orderIdList\":\"\"}");
		    	}else{
		    		String tempOrderIdListStr = "";
		    		Iterator<String> itr = lists.iterator();
		    		while (itr.hasNext()){
		    			tempOrderIdListStr += itr.next() + ", ";
		    		}
		    		tempOrderIdListStr.substring(0, tempOrderIdListStr.length() - 2);
		    		
		    		jsonArray.add("{\"orderIdExist\":\"yes\",\"orderIdList\":\"" + tempOrderIdListStr + "\"}");
		    	}
		    	}catch(Exception e){
		    		logger.error(e);
		    		jsonArray.add("{\"result\":\"fail\"}");
		    	}
		}
		
		if("superValid".equals(type)){
			try{
				boolean userIsManager = false;
				
				BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
				
				List<SalesInfoDTO> managers = this.mobCcsSalesInfoService.getSalesInfoDTO(user.getChannelCd(), "MANAGER");
				SalesInfoDTO userManager = null;
				if (!managers.isEmpty()) {
					for (SalesInfoDTO manager : managers) {
						if (StringUtils.isNotBlank(this.getStringValue(request, "login")) && this.getStringValue(request, "login").equals(manager.getStaffId())) {
							userIsManager = true;
							userManager = manager;
							break;
						}
					}
				}
				
				if (userIsManager) {
					String authorizedId = mobCcsApprovalLogService.getNextAuthorizedId();
					
					if (StringUtils.isBlank(authorizedId)) {						
						jsonArray.add("{\"valid\":\"false\"}");
					} else {
						String orderId = (String) MobCcsSessionUtil.getSession(request,	"orderId");
						ApprovalLogDTO dto = new ApprovalLogDTO();
						dto.setAuthorizedId(authorizedId);
						dto.setOrderId(StringUtils.isBlank(orderId) ? null : orderId);
						dto.setAuthorizedBy(userManager.getStaffId());
						dto.setAuthorizedAction("AU05");
						dto.setShopCode(userManager.getChannelCd());
						dto.setLastUpdBy(userManager.getStaffId());
						mobCcsApprovalLogService.insertApprovalLogDTO(dto);
						
						/*
						 *  action: 
						 *  AU05 = special mrt number
						 */
						MobCcsSessionUtil.setSession(request, "AU05", authorizedId);
						jsonArray.add("{\"valid\":\"true\"}");
					}
				} else {					
					jsonArray.add("{\"valid\":\"false\"}");					
				}
			}catch(Exception e){
				logger.error(e);
	    		jsonArray.add("{\"valid\":\"false\"}");
			}
		}
		
		if("GetSpecialNum".equals(type)){
		    
			String msisdnLvl = mobCcsMrtService.getSpecialMsisdnLvl(this.getStringValue(request, "msisdn"),
			    						       BomWebPortalCcsConstant.MRT_NEW_NUM_STATUS_RESERVE, this.getStringValue(request, "approvalSerial"));
		    
		    if(StringUtils.isBlank(msisdnLvl)){
		    	jsonArray.add("{\"msisdnLvl\":\"empty\"}");
		    }else{
		    	jsonArray.add("{\"msisdnLvl\":\"" + msisdnLvl + "\"}");
		    }
		}
		
		if("GetOneCardTwoNumberByRandom".equals(type)){
		    		    
			try{
			 	
		    	List<String[]> lists =  mobCcsMrtService.getOneCardTwoNumberByRandom(this.getStringValue(request, "channelCd"),
						this.getStringValue(request, "cityCd"), this.getStringValue(request, "numType"));
				
				if (lists == null) {
				    lists = Collections.emptyList();
				    jsonArray = JSONArray.fromObject(lists);
				}else{			
				    Iterator<String[]> itr = lists.iterator();
				    while (itr.hasNext()){
				    	String[] item = itr.next();
								    
						jsonArray.add("{\"msisdn\":\""	+ item[0]
							    	+ "\",\"msisdnlvl\":\""	+ item[1] 
							    	+ "\",\"channelCd\":\""	+ item[2]
							    	+ "\",\"cityCd\":\""	+ item[3]
							    	+ "\",\"msisdnStatus\":\""	+ item[4]
							    	+ "\",\"numType\":\""	+ item[5]
							    	+ "\",\"srvNumType\":\""	+ item[6]
							    	+ "\"}");
				    }
				}
		    }catch(Exception e){
		    	logger.error(e);
		    	jsonArray.add("{\"result\":\"fail\"}");
		    }
		}
		
		if("GetOneCardTwoNumberByFullNumber".equals(type)){
		    
			try{
			 	
		    	List<String[]> lists =  mobCcsMrtService.getOneCardTwoNumberByFullNumber(this.getStringValue(request, "channelCd"),
						this.getStringValue(request, "searchMsisdn"), this.getStringValue(request, "numType"));
				
				if (lists == null) {
				    lists = Collections.emptyList();
				    jsonArray = JSONArray.fromObject(lists);
				}else{			
				    Iterator<String[]> itr = lists.iterator();
				    while (itr.hasNext()){
				    	String[] item = itr.next();
								    
						jsonArray.add("{\"msisdn\":\""	+ item[0]
							    	+ "\",\"msisdnlvl\":\""	+ item[1] 
							    	+ "\",\"channelCd\":\""	+ item[2]
							    	+ "\",\"cityCd\":\""	+ item[3]
							    	+ "\",\"msisdnStatus\":\""	+ item[4]
							    	+ "\",\"numType\":\""	+ item[5]
							    	+ "\",\"srvNumType\":\""	+ item[6]
							    	+ "\"}");
				    }
				}
		    }catch(Exception e){
		    	logger.error(e);
		    	jsonArray.add("{\"result\":\"fail\"}");
		    }
		}
	
		logger.info("jsonArray : " + jsonArray);
	
		return new ModelAndView("ajax_mobCcsCommonLookup", "jsonArray",	jsonArray);
	
    }
    
    private String getStringValue(HttpServletRequest request, String name) {
    	String value = "";
		try {
		    if(StringUtils.isNotBlank((String)request.getParameter(name))){
		    	value = new String(request.getParameter(name));
		    }	    
		} catch (NumberFormatException nfe) {}
			return value;
    }
}
