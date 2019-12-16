package com.bomwebportal.ims.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.bomwebportal.dto.ClubHktCheckIdDTO;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.SubscribedCslOfferDTO;
import com.bomwebportal.ims.dto.ui.ComponentUI;
import com.bomwebportal.ims.dto.ui.ImsInstallationUI;
import com.bomwebportal.ims.dto.ui.ImsLoginIDUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.dto.ui.SubscribedItemUI;
import com.bomwebportal.ims.service.CheckLoginNameService;
import com.bomwebportal.ims.service.ReleaseLoginIDService;
import com.bomwebportal.ims.service.ReserveLoginIDService;
import com.bomwebportal.ims.service.SuggestLoginIDService;
import com.bomwebportal.ims.service.ValidateLoginIDService;
import com.bomwebportal.service.ClubHktService;
import com.bomwebportal.util.ImsUtil;
import com.google.gson.Gson;

public class CheckLoginNameController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private CheckLoginNameService checkLoginNameService;
	private ValidateLoginIDService validateLoginIDService;
	private ReserveLoginIDService reserveLoginIDService;
	private ReleaseLoginIDService releaseLoginIDService;
	private SuggestLoginIDService suggestLoginIDService;
	private ClubHktService clubHktService;
	
	private final Gson gson = new Gson();
	private MessageSource message;
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		logger.info("reserve login request"+"["+request.getParameter("loginName")+"]");
		
		ImsLoginIDUI imsLoginIdUi = new ImsLoginIDUI();
		
		int reserveResult = -99;
		int releaseResult = -99;
		
		
		if(request.getSession().getAttribute("imsLoginIdUi") != null){
			imsLoginIdUi = (ImsLoginIDUI)request.getSession().getAttribute("imsLoginIdUi");
			if((imsLoginIdUi.getDocType() != null && !imsLoginIdUi.getDocType().equals("")) && (imsLoginIdUi.getIdDocNum() != null && !imsLoginIdUi.getIdDocNum().equals(""))
					&& (imsLoginIdUi.getOldLoginName() != null && !("").equals(imsLoginIdUi.getOldLoginName()))){ // && !loginName.equals(imsLoginIdUi.getOldLoginName())
				releaseResult = releaseLoginIDService.releaseLoginID(imsLoginIdUi.getOldLoginName(), imsLoginIdUi.getIdDocNum(), imsLoginIdUi.getDocType());
				//oldLoginName = "";
				imsLoginIdUi.setOldLoginName("");
			}
		}
		
		String docType = request.getParameter("docType");
		//request.getSession().setAttribute("imsDocType", docType);
		imsLoginIdUi.setDocType(docType);
		String idDocNum = request.getParameter("idDocNum");
		//request.getSession().setAttribute("imsIdDocNum", idDocNum);
		imsLoginIdUi.setIdDocNum(idDocNum);
		String loginName = request.getParameter("loginName");
		//request.getSession().setAttribute("imsLoginName", loginName);
		imsLoginIdUi.setLoginName(loginName);
		//String oldLoginName = (String)request.getSession().getAttribute("imsOldLoginName");
		String oldLoginName = imsLoginIdUi.getOldLoginName();
		ImsInstallationUI result = new ImsInstallationUI();

		JSONArray jsonArray = new JSONArray();
		
		boolean valid = true;
		boolean isExist = true;
		String errorText = null;
		char theChar;
		//if((docType != null && !docType.equals("")) && (idDocNum != null && !idDocNum.equals(""))){
		if(loginName != null && !("").equals(loginName)){
			valid = validateLoginIDService.validateLoginID(loginName);
			isExist = checkLoginNameService.checkLoginName(loginName);
			
			if(valid == false){
				errorText = validateLoginIDService.validateLoginIDError(loginName);
			}
		 
			if(loginName.length() < 2 || loginName.length() > 15){
				valid = false;
//				errorText = "Incorrect login ID length";
				errorText = message.getMessage("ims.pcd.imsinstallation.msg.064", null, RequestContextUtils.getLocale(request));
			}
		 
			theChar = loginName.charAt(0);
			if(!Character.isLetter(theChar) && !Character.isDigit(theChar)){ // || !Character.isLowerCase(theChar)){
				valid = false;
//				errorText = "Login ID should contain numbers and letters only";
				errorText = message.getMessage("ims.pcd.imsinstallation.msg.061", null, RequestContextUtils.getLocale(request));
			}else if(!Character.isLetter(theChar) && Character.isDigit(theChar)){
				valid = false;
//				errorText = "LOGIN ID First character must be letters (a-z)";
				errorText = message.getMessage("ims.pcd.imsinstallation.msg.062", null, RequestContextUtils.getLocale(request));
			}else if(!Character.isLowerCase(theChar)){
				valid = false;
//				errorText = "LOGIN ID should be small letters only";
				errorText = message.getMessage("ims.pcd.imsinstallation.msg.063", null, RequestContextUtils.getLocale(request));
			}
		
			for(int idx = 1; idx < loginName.length(); ++idx){
				theChar = loginName.charAt(idx);
				logger.debug("theChar: " + theChar);
				if(Character.isLetter(theChar) && Character
						.isUpperCase(theChar)){
					valid = false;
//					errorText = "Login ID should be small letters only";
					errorText = message.getMessage("ims.pcd.imsinstallation.msg.063", null, RequestContextUtils.getLocale(request));
					break;
				}else if(!((Character.isLetter(theChar) && Character
						.isLowerCase(theChar)) || Character.isDigit(theChar))){
					valid = false;
//					errorText = "Login ID should contain numbers and letters only";
					errorText = message.getMessage("ims.pcd.imsinstallation.msg.061", null, RequestContextUtils.getLocale(request));
					break;
				}
			}
		}
//Gary modified
		String[] resultFromAPI = new String[9];
		String isCsPortalReg = "false";
		String retrieveLoginId = null;
		String isTheClubReg = "false";
		String retrieveClubLoginId = null; //Tony
		String nloginResult = "NA";
		ClubHktCheckIdDTO checkDto1 = new ClubHktCheckIdDTO();
		ClubHktCheckIdDTO checkDto2 = new ClubHktCheckIdDTO();
		String phantomInd = "N";
		
		String oCareApplyInd = "";
		String oCareOptOutInd = "";
		String oCareVisit = "";
		String oCareEnable = "";
		String oCareBip = "";
		resultFromAPI[0] = "<APIFailed>";
		resultFromAPI[1] = "<APIFailed>";
		
		boolean isClubMemberIDValid = true;
		
		if((docType != null && !docType.equals("")) && (idDocNum != null && !idDocNum.equals(""))
				&& (oldLoginName != null && !("").equals(oldLoginName)) && !loginName.equals(oldLoginName)){
			releaseResult = releaseLoginIDService.releaseLoginID(oldLoginName, idDocNum, docType);
			//oldLoginName = "";
			imsLoginIdUi.setOldLoginName("");
		}
		
		if((docType != null && !docType.equals("")) && (idDocNum != null && !idDocNum.equals(""))
				&& valid == true && isExist == false && !loginName.equals(oldLoginName)){
			reserveResult = reserveLoginIDService.reserveLoginID(loginName, idDocNum, docType);
			if(reserveResult == -7){
				//valid = false;
//				errorText = "Cannot Reserve ILRC Login ID";
				errorText = message.getMessage("ims.pcd.imsinstallation.msg.064", null, RequestContextUtils.getLocale(request));
			}
			if(reserveResult == 0){
				//request.getSession().setAttribute("imsOldLoginName", loginName);
				imsLoginIdUi.setOldLoginName(loginName);
				logger.info("docType"+docType);
				logger.info("idDocNum"+idDocNum);
				logger.info("loginName"+loginName);
				//("HKID".equalsIgnoreCase(docType) || "PASS".equalsIgnoreCase(docType) &&
				String[]invalidPassNoPatt = {"DUMMY","DUM","TEST","TEMP","#","@","*","?","!","ILRC","BGCA"};
				boolean invalidPassNo=false;
				for(int i=0;i<invalidPassNoPatt.length;i++){
					if(idDocNum.indexOf(invalidPassNoPatt[i])>=0){
						invalidPassNo=true;
						break;
					}
				}
				
				String nlogin = loginName + "@netvigator.com";
				
				nlogin = nlogin.length() > 60 ? nlogin.substring(nlogin.length() - 60) : nlogin;
				
				String[] resultFornlogin = new String[5];
				
				checkDto1 = clubHktService.checkId(docType, idDocNum, nlogin, nlogin, null, null);
				resultFornlogin = ImsUtil.convertAPIReturnResult(checkDto1);
				logger.info(gson.toJson(resultFornlogin));

				if (!invalidPassNo && (Pattern.compile(".*[\\d].*[\\d].*[\\d].*[\\d].*[\\d].*[\\d].*")).matcher(idDocNum).find()){
					logger.info("in csportal checking");
					checkDto2 = clubHktService.checkId(docType, idDocNum, null, null, null, null);
					resultFromAPI = ImsUtil.convertAPIReturnResult(checkDto2);
					logger.info(gson.toJson(resultFromAPI));
					isCsPortalReg ="true";
					isTheClubReg ="true";
				}
				
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
				
				phantomInd = resultFromAPI[4];
				
				if(resultFromAPI[0]=="not exist"&&resultFromAPI[1]=="not exist"){
					if(resultFornlogin[2]!="success"||resultFornlogin[3]!="success"){
						nloginResult = "NA";
					}else{
						nloginResult = nlogin;
						phantomInd = resultFornlogin[4];
					}
				}else if(resultFromAPI[0]=="not exist"){
					if(resultFornlogin[2]!="success"){
						nloginResult = "NA";
					}else{
						nloginResult = nlogin;
						phantomInd = resultFornlogin[4];
					}
				}else if(resultFromAPI[1]=="not exist"){
					if(resultFornlogin[3]!="success"){
						nloginResult = "NA";
					}else{
						nloginResult = nlogin;
						phantomInd = resultFornlogin[4];
					}
				}else{
					nloginResult = "NA";
				}
				
//				if(StringUtils.isNotBlank(resultFromAPI[7])&& resultFromAPI[7].equals("Y")){
				oCareVisit = resultFromAPI[6];
				oCareEnable = resultFromAPI[7];
				oCareBip = resultFromAPI[8];
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
//				}
//				else{
//					oCareEnable = "N";
//				}
				
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
				
				
				
					
			}
			if(reserveResult != 0){
//				errorText="fail to reserve login";
				errorText = message.getMessage("ims.pcd.imsinstallation.msg.065", null, RequestContextUtils.getLocale(request));
				valid=false;
			}
		}
		
		if(valid == true && isExist == true){
			result = suggestLoginIDService.suggestLoginID(loginName);
		}

		jsonArray.add("{\"valid\":" + valid
					 + ",\"isExist\":" + isExist
					 + ", \"hintOne\":\"" + result.getHintOne()
					 + "\", \"hintSecond\":\"" + result.getHintSecond()
					 + "\", \"hintThird\":\"" + result.getHintThird()
					 + "\", \"errorText\":\"" + errorText
					 + "\", \"loginName\":\"" + loginName
					 + "\", \"oldLoginName\":\"" + oldLoginName
					 + "\",\"reserveResult\":\"" + reserveResult
					 + "\",\"isCsPortalReg\":\"" + isCsPortalReg
					 + "\",\"retrieveLoginId\":\"" + retrieveLoginId
					 + "\",\"isTheClubReg\":\"" + isTheClubReg
					 + "\",\"retrieveClubLoginId\":\"" + retrieveClubLoginId
					 + "\",\"nloginResult\":\"" + nloginResult
					 + "\",\"phantomInd\":\"" + phantomInd
					 + "\",\"oCareApplyInd\":\"" + oCareApplyInd
					 + "\",\"oCareOptOutInd\":\"" + oCareOptOutInd
					 + "\",\"oCareVisit\":\"" + oCareVisit
					 + "\",\"oCareEnable\":\"" + oCareEnable
					 + "\",\"oCareBip\":\"" + oCareBip
					 + "\",\"isClubMemberIDValid\":\"" + isClubMemberIDValid
					 + "\"}");
		
//		logger.info("oldLoginName: " + oldLoginName);
		//logger.info(imsLoginIdUi.getOldLoginName());
		logger.info(" docType: " + docType+
				" idDocNum: " + idDocNum+
				" releaseResult: " + releaseResult);
//		logger.info("reserveResult: " + reserveResult);
//		logger.info("loginName: " + loginName);
//		logger.info("valid: " + valid);
//		logger.info("isExist: " + isExist);
//		logger.info("hintOne: " + result.getHintOne());
//		logger.info("hintSecond: " + result.getHintSecond());
//		logger.info("hintThird: " + result.getHintThird());
//		logger.info("isClubMemberIDValid: " + isClubMemberIDValid);
		logger.info(jsonArray);
		imsLoginIdUi.setValid(valid);
		imsLoginIdUi.setExist(isExist);
		request.getSession().setAttribute("imsLoginIdUi", imsLoginIdUi);
		
		return new ModelAndView("ajax_checkloginname", "jsonArray", jsonArray);
	}

	public CheckLoginNameService getCheckLoginNameService() {
		return checkLoginNameService;
	}

	public void setCheckLoginNameService(CheckLoginNameService checkLoginNameService) {
		this.checkLoginNameService = checkLoginNameService;
	}

	public ValidateLoginIDService getValidateLoginIDService() {
		return validateLoginIDService;
	}

	public void setValidateLoginIDService(
			ValidateLoginIDService validateLoginIDService) {
		this.validateLoginIDService = validateLoginIDService;
	}

	public ReserveLoginIDService getReserveLoginIDService() {
		return reserveLoginIDService;
	}

	public void setReserveLoginIDService(ReserveLoginIDService reserveLoginIDService) {
		this.reserveLoginIDService = reserveLoginIDService;
	}

	public ReleaseLoginIDService getReleaseLoginIDService() {
		return releaseLoginIDService;
	}

	public void setReleaseLoginIDService(ReleaseLoginIDService releaseLoginIDService) {
		this.releaseLoginIDService = releaseLoginIDService;
	}

	public SuggestLoginIDService getSuggestLoginIDService() {
		return suggestLoginIDService;
	}

	public void setSuggestLoginIDService(SuggestLoginIDService suggestLoginIDService) {
		this.suggestLoginIDService = suggestLoginIDService;
	}

	public void setClubHktService(ClubHktService clubHktService) {
		this.clubHktService = clubHktService;
	}

	public ClubHktService getClubHktService() {
		return clubHktService;
	}

	public void setMessage(MessageSource message) {
		this.message = message;
	}

	public MessageSource getMessage() {
		return message;
	}
}
