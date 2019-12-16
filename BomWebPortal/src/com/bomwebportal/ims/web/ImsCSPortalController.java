package com.bomwebportal.ims.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.SubscribedCslOfferDTO;
import com.bomwebportal.ims.dto.ui.ComponentUI;
import com.bomwebportal.ims.dto.ui.ImsLoginIDUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.dto.ui.SubscribedItemUI;
import com.bomwebportal.dto.ClubHktCheckIdDTO;
import com.bomwebportal.service.ClubHktService;
import com.bomwebportal.util.ImsUtil;

import com.google.gson.Gson;

public class ImsCSPortalController implements Controller{
	protected final Log logger = LogFactory.getLog(getClass());
	private final Gson gson = new Gson();
	
	private ClubHktService clubHktService;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		
		String[] resultFromAPI = new String[9];
		String isCsPortalReg = "false";
		String retrieveLoginId = null;
		String isTheClubReg = "false";
		String retrieveClubLoginId = null; //Tony
		String nloginResult = "NA";
		String sysGenEmail = "";
		String tmpEmail = "";
		String phantomInd = "N";
		
		String oCareApplyInd = "";
		String oCareOptOutInd = "";
		String oCareVisit = "";
		String oCareEnable = "";
		String oCareBip = "";
		ClubHktCheckIdDTO checkDto1 = new ClubHktCheckIdDTO();
		ClubHktCheckIdDTO checkDto2 = new ClubHktCheckIdDTO();
		ClubHktCheckIdDTO checkDto3 = new ClubHktCheckIdDTO();
		
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String strDate = sdf.format(today);
		
		ImsLoginIDUI imsLoginIdUi = new ImsLoginIDUI();
		
		String docType = request.getParameter("docType");
		//request.getSession().setAttribute("imsDocType", docType);
		imsLoginIdUi.setDocType(docType);
		String idDocNum = request.getParameter("idDocNum");
		//request.getSession().setAttribute("imsIdDocNum", idDocNum);
		imsLoginIdUi.setIdDocNum(idDocNum);
		String loginName = request.getParameter("loginName");
		//request.getSession().setAttribute("imsLoginName", loginName);
		imsLoginIdUi.setLoginName(loginName);

		String firstName = request.getParameter("custFirstName");
		String lastName = request.getParameter("custLastName");
		
		String reqType = request.getParameter("reqType");
		
		resultFromAPI[0] = "<APIFailed>";
		resultFromAPI[1] = "<APIFailed>";
		
		boolean isClubMemberIDValid = true;
		
		String[]invalidPassNoPatt = {"DUMMY","DUM","TEST","TEMP","#","@","*","?","!"};
		boolean invalidPassNo=false;
		for(int i=0;i<invalidPassNoPatt.length;i++){
			if(idDocNum.indexOf(invalidPassNoPatt[i])>=0){
				invalidPassNo=true;
				break;
			}
		}
		
		if (invalidPassNo==false && (Pattern.compile(".*[\\d].*[\\d].*[\\d].*[\\d].*[\\d].*[\\d].*")).matcher(idDocNum).find()){
			checkDto1 = clubHktService.checkId(docType, idDocNum, null, null, null, null);
			logger.info("check::: "+gson.toJson(checkDto1));
			resultFromAPI = ImsUtil.convertAPIReturnResult(checkDto1);
			logger.info(gson.toJson(resultFromAPI));
			
			isCsPortalReg ="true";
			isTheClubReg ="true";
		}
		
		phantomInd = resultFromAPI[4];
							
		if(resultFromAPI[0]=="not exist"){
			isCsPortalReg="false";
		}else if(resultFromAPI[0]=="<APIFailed>"){
			isCsPortalReg=null;
		}else{
			isCsPortalReg ="true";
			retrieveLoginId=resultFromAPI[0];
		}
							
		if(resultFromAPI[1]=="not exist"){
			isTheClubReg="false";
		}else if(resultFromAPI[1]=="<APIFailed>"){
			isTheClubReg=null;
		}else{
			isTheClubReg ="true";
			retrieveClubLoginId=resultFromAPI[1];
		}
		
		if(reqType.equalsIgnoreCase("nlogin")){
		
			String nlogin = loginName + "@netvigator.com";
			
			nlogin = nlogin.length() > 60 ? nlogin.substring(nlogin.length() - 60) : nlogin;
			
			String[] resultFornlogin = new String[5];
			
			checkDto2 = clubHktService.checkId(docType, idDocNum, nlogin, nlogin, null, null);
			resultFornlogin = ImsUtil.convertAPIReturnResult(checkDto2);
			logger.info(gson.toJson(resultFornlogin));

			if(resultFromAPI[0]=="not exist"&&resultFromAPI[1]=="not exist"){
				if(resultFornlogin[2]!="success"||resultFornlogin[3]!="success"){
					nloginResult = "NA";
				}else{
					nloginResult = nlogin;
				}
			}else if(resultFromAPI[0]=="not exist"){
				if(resultFornlogin[2]!="success"){
					nloginResult = "NA";
				}else{
					nloginResult = nlogin;
				}
			}else if(resultFromAPI[1]=="not exist"){
				if(resultFornlogin[3]!="success"){
					nloginResult = "NA";
				}else{
					nloginResult = nlogin;
				}
			}else{
				nloginResult = "NA";
			}

			phantomInd = resultFornlogin[4];
			
		}
		
		if(reqType.equalsIgnoreCase("sysgen")){
			
			int maxIdLength = 30;
			
			tmpEmail = (lastName + firstName).toLowerCase().trim().replaceAll("\\s",""); 
			
			tmpEmail = tmpEmail.length() > maxIdLength ? tmpEmail.substring(0, maxIdLength) : tmpEmail;
			
			tmpEmail = tmpEmail + "@theclub.com.hk";
			
			String[] resultForSysGenEmail = new String[5];
			
			// @theclub.com.hk is only available for no email case
			// thus myHKT is not need to check.
			checkDto3 = clubHktService.checkId(docType, idDocNum, null, tmpEmail, null, null);
			resultForSysGenEmail = ImsUtil.convertAPIReturnResult(checkDto3);
			logger.info(gson.toJson(resultForSysGenEmail));
			
			maxIdLength -= 6;
			
			
			if("not exist".equals(resultFromAPI[1])){
				if(!"success".equals(resultForSysGenEmail[3])){
					tmpEmail = (lastName + firstName).toLowerCase().trim().replaceAll("\\s","");
					tmpEmail = tmpEmail.length() > maxIdLength ? tmpEmail.substring(0, maxIdLength) : tmpEmail;
					tmpEmail = (tmpEmail + strDate).toLowerCase().trim().replaceAll("\\s","");
					tmpEmail = tmpEmail + "@theclub.com.hk";
					sysGenEmail = tmpEmail;
				}else{
					sysGenEmail = tmpEmail;
				}
			}
			
			phantomInd = resultForSysGenEmail[4];
			
		}
		
		// martin, IMSCARECASH, for NTV-A, without checkLoginName function
		oCareVisit = resultFromAPI[6];
		oCareEnable = resultFromAPI[7];
		oCareBip = resultFromAPI[8];
//		if(StringUtils.isNotBlank(resultFromAPI[7])&& resultFromAPI[7].equals("Y")){
			if(StringUtils.isNotBlank(resultFromAPI[6])){ //-pending, customer to be decide
				oCareApplyInd = "P";
				oCareOptOutInd = "I";
			}else if(StringUtils.isBlank(resultFromAPI[5])|| resultFromAPI[5]=="N"){
				oCareApplyInd = "I";  //- customer want to enroll
				oCareOptOutInd = "I";
			}else if (resultFromAPI[5]=="I"){
				oCareApplyInd = "A"; // - customer already enrolled
				oCareOptOutInd = "I";
			}else{//-customer already choose to opt out
				oCareApplyInd = "O";
				oCareOptOutInd = "I";
			}
//		}
			
		

			
			List<String> inputClubMemberIdList = new ArrayList<String>();
				
			OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
			
			if(sessionOrder!=null){
				if(sessionOrder.getSubscribedImsVasParm()!=null&&sessionOrder.getSubscribedImsVasParm().length>0){
					for(SubscribedCslOfferDTO dto:sessionOrder.getSubscribedImsVasParm()){
						if("CLUB_PT_REDM".equalsIgnoreCase(dto.getItem_type())||"CLUB_PT_REDM_ISF".equalsIgnoreCase(dto.getItem_type())){
							inputClubMemberIdList.add(dto.getVas_parm_a());
						}
					}
				}
				if(sessionOrder.getComponents()!=null&&sessionOrder.getComponents().length>0){
					for(SubscribedItemUI sui:sessionOrder.getSubscribedItems()){
						if("CLUB_PT_REDM_GIFT".equalsIgnoreCase(sui.getType())){
							for(ComponentUI cui:sessionOrder.getComponents()){
								if(cui.getItemId().equalsIgnoreCase(sui.getId())){
									inputClubMemberIdList.add(cui.getAttbValue());
								}
							}
						}
					}
				}
			}
			
			if(inputClubMemberIdList!=null&&inputClubMemberIdList.size()>0){
				String clubMemberId = clubHktService.getClubMemberIdbyDoc(docType,idDocNum);
				if(clubMemberId==null||clubMemberId.isEmpty()){
					isClubMemberIDValid = false;
				}else{
					for(int i=0; i<inputClubMemberIdList.size();i++){
						if(!clubMemberId.equalsIgnoreCase(inputClubMemberIdList.get(i))){
							isClubMemberIDValid = false;
						}
					}
				}
			}
		
			
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		jsonObject = new JSONObject();
		String regClubHktCase = request.getParameter("regClubHktCase");
		if("BOTH".equals(regClubHktCase)){
            logger.debug("CASE 1 BOTH registered");	
			isCsPortalReg="true"; 
			isTheClubReg="true";
			retrieveLoginId="stevenHkt@hkt.com";
			retrieveClubLoginId="stevenClub@Club.com";
			sysGenEmail="";
		}else if("CLUB".equals(regClubHktCase)){
            logger.debug("CASE 2 CLUB registered");	
			isCsPortalReg="false";
			isTheClubReg="true";	
			retrieveClubLoginId="stevenClub@Club.com";	
			retrieveLoginId="";
			phantomInd="Y";
			sysGenEmail="stevenTestSysGenEmail@theClub.Com.hk";
		}else if("HKT".equals(regClubHktCase)){		
            logger.debug("CASE 3 HKT registered");	
			isCsPortalReg="true";
			retrieveLoginId="stevenHkt@hkt.com";
			retrieveClubLoginId="";	
			isTheClubReg="false";				
			sysGenEmail="stevenTestSysGenEmail@theClub.Com.hk";
		}else if("NONE".equals(regClubHktCase)){
            logger.debug("CASE 4 NONE registered");		
			isCsPortalReg="false";
			isTheClubReg="false";		
			retrieveClubLoginId="";		
			retrieveLoginId="";	
			sysGenEmail="stevenTestSysGenEmail@theClub.Com.hk";
		}else if("BS".equals(regClubHktCase)){
            logger.debug("CASE 5 BS");		
			isCsPortalReg="null";
			isTheClubReg="null";
			retrieveClubLoginId="";	
			retrieveLoginId="";
			sysGenEmail="";
		}
		jsonObject.put("isCsPortalReg", isCsPortalReg);
		jsonObject.put("retrieveLoginId", retrieveLoginId);
		jsonObject.put("isTheClubReg", isTheClubReg);
		jsonObject.put("retrieveClubLoginId", retrieveClubLoginId);
		jsonObject.put("nloginResult", nloginResult);
		jsonObject.put("sysGenEmail", sysGenEmail);
		jsonObject.put("phantomInd", phantomInd);
		jsonObject.put("oCareEnable", oCareEnable);
		jsonObject.put("oCareVisit", oCareVisit);
		jsonObject.put("oCareBip", oCareBip);
		jsonObject.put("isClubMemberIDValid", isClubMemberIDValid);
		if (loginName == null) {
			// martin, IMSCARECASH, for NTV-A, without checkLoginName function
			jsonObject.put("oCareApplyInd", oCareApplyInd);
			jsonObject.put("oCareOptOutInd", oCareOptOutInd);
		}
//		logger.debug("ajax return: " + new Gson().toJson(jsonArray));
		jsonArray.element(jsonObject);
		
		Map<String, Object> input = new HashMap<String, Object>();
		input.put("docType", docType);
		input.put("idDocNum", idDocNum);
		input.put("loginName", loginName);
		input.put("isCsPortalReg", isCsPortalReg);
		input.put("retrieveLoginId", retrieveLoginId);
		input.put("isTheClubReg", isTheClubReg);
		input.put("retrieveClubLoginId", retrieveClubLoginId);
		input.put("jsonArray", jsonArray);
		logger.debug("ImsCSPortalController: " + new Gson().toJson(input));
		
//		logger.info("docType: " + docType);
//		logger.info("idDocNum: " + idDocNum);
//		logger.info("loginName: " + loginName);
//		logger.info("isCsPortalReg: " + isCsPortalReg);
//		logger.info("retrieveLoginId: " + retrieveLoginId);
//		logger.info("isTheClubReg: " + isTheClubReg);
//		logger.info("retrieveClubLoginId: " + retrieveClubLoginId);
//		logger.info(jsonArray);
		
		return new ModelAndView("ajax_csportal", "jsonArray", jsonArray);
				
		
	}

	public void setClubHktService(ClubHktService clubHktService) {
		this.clubHktService = clubHktService;
	}

	public ClubHktService getClubHktService() {
		return clubHktService;
	}

}

