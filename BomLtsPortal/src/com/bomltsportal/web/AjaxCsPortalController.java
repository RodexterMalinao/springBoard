package com.bomltsportal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomltsportal.util.BomLtsConstant;
import com.bomwebportal.lts.dto.CsPortalIdInqArqDTO;
import com.bomwebportal.lts.service.CsPortalService;


public class AjaxCsPortalController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());
	private CsPortalService csPortalService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("handleRequest() called"); 
		JSONObject jsonObject = new JSONObject();
		String docType =  request.getParameter("docType");
		String docNum =  request.getParameter("docNum");
		String email =  request.getParameter("email");
		logger.info("docType: " + docType + ", docNum: " + docNum + ", email: " + email); 

		CsPortalIdInqArqDTO res = null;
		try{
			for(int i = 0; i < 3; i++){
				logger.info("trial: " + (i+1));
				res = (CsPortalIdInqArqDTO) csPortalService.checkCsIdForTheClubAndHkt(docType, docNum, email, email, BomLtsConstant.USER_ID);
				if(res != null && !res.isReturnError() && !res.isSystemBusy() ){
					break;
				}
			}
			
			if (res == null || res.isReturnError() || res.isSystemBusy()) {
				jsonObject.put("sys_error", true);
				return new ModelAndView("ajax_ajaxcsportal", jsonObject);
			}
			logger.info("docType: " + docType + ", docNum: " + docNum + ", email: " + email + "res:" + res.toString()); 
			
			jsonObject.put("isDocValid", res.isDocValid());
			jsonObject.put("isCustAldyMyHkt", res.isCustAldyMyHkt());
			jsonObject.put("isCustAldyTheClub", res.isCustAldyTheClub());
			jsonObject.put("isClubLiInUse", res.isClubLiInUse());
			jsonObject.put("isHktLiInUse", res.isHktLiInUse());
			jsonObject.put("isLiInUse", res.isLiInUse());
			
//			if(CsPortalRes.SUCCESS == res){
//				jsonObject.put("isCsPortalCusExist", false);
//			}else if(CsPortalRes.FAILED == res){
//				jsonObject.put("isCsPortalCusExist", true);
//			}else if(CsPortalRes.EMAIL_ERROR == res){
//				jsonObject.put("email_error", true);
//			}else if(CsPortalRes.DOCNUM_6_DIGIT == res){
//				jsonObject.put("six_digit_error", true);
//			}else {
//				jsonObject.put("sys_error", true);
//			}
		}catch(RuntimeException re){
			jsonObject.put("sys_error", true);
		}
		
		return new ModelAndView("ajax_ajaxcsportal", jsonObject);
	}

	public CsPortalService getCsPortalService() {
		return csPortalService;
	}

	public void setCsPortalService(CsPortalService csPortalService) {
		this.csPortalService = csPortalService;
	}

}
