package com.bomltsportal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis.utils.SessionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;//==PDPO==
import org.apache.commons.logging.LogFactory;//==PDPO==
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomltsportal.dto.BuildingMarkerDTO;
import com.bomltsportal.dto.FsaServiceAssgnDTO;
import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.interceptor.LtsCommonInterceptor;
import com.bomltsportal.service.CustomerDetailService;
import com.bomltsportal.service.ImsDetailService;
import com.bomltsportal.service.LivechatpathService;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.SessionHelper;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.service.bom.CustPdpoProfileService;//==PDPO==
import com.bomwebportal.lts.dto.profile.CustPdpoProfileDTO;//==PDPO==
import com.bomwebportal.service.CodeLkupCacheService;

public class AjaxProfileLookupController implements Controller {
	
	private CustomerDetailService customerDetailService;
	private ImsDetailService imsDetailService;
	private LivechatpathService livechatpathService;
	private CodeLkupCacheService specialHandleDocIdLkupCacheService;
	private CustPdpoProfileService custPdpoProfileService;//==PDPO==
	protected final Log logger = LogFactory.getLog(getClass());//==PDPO==
	
	final String[] wipInds = {"W", "P", "Y"};
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		String checkOpt =  request.getParameter("checkOpt");
		String first =  request.getParameter("first");
		String last =  request.getParameter("last");
		String isHKID = request.getParameter("isHKID");
		String docId =  request.getParameter("docId");
		boolean isWip = false, isSpecialHandleCust = false;
		CustomerDetailProfileLtsDTO custDetail = null;
		
		
		for(LookupItemDTO item: specialHandleDocIdLkupCacheService.getCodeLkupDAO().getCodeLkup()){
			if(StringUtils.equals(docId, item.getItemKey())){
				isSpecialHandleCust = true;
				jsonObject.put("sh_cust", true);
				break;
			}
		}
		
		
		if("DOC".equals(checkOpt)){
			String docType = null;// =  request.getParameter("docId");
			
			if (StringUtils.isBlank(isHKID) || StringUtils.isBlank(docId)) {
				orderCapture.setCustomerDetailProfile(null);
				orderCapture.setImsServiceProfile(null);
				orderCapture.setFsaServiceAssgn(null);
				jsonObject.put("cust_exist", false);	
			}
			
			if("true".equals(isHKID)){
				docType = "HKID";
			}else{
				docType = "PASS";
			}
			
			
			FsaServiceDetailOutputDTO sharedImsProfile = null;
			FsaServiceAssgnDTO fsaServiceAssgn = null;
			try{
				custDetail = customerDetailService.getLtsCustomerDetailByDocId(docId, docType);
				sharedImsProfile = imsDetailService.getFsaProfileToShare(docType, docId, orderCapture);
				fsaServiceAssgn = imsDetailService.getFsaServiceAssgn(orderCapture.getAddressRollout(), sharedImsProfile);
				orderCapture.setImsServiceProfile(sharedImsProfile);
				orderCapture.setFsaServiceAssgn(fsaServiceAssgn);
			}catch(AppRuntimeException are){
				are.printStackTrace();
			}
			
			jsonObject.element("name_match", false);
			jsonObject.element("wip", false);
			if(custDetail != null){
				orderCapture.setCustomerDetailProfile(custDetail);
				jsonObject.element("cust_exist", true);
				if(StringUtils.equalsIgnoreCase(custDetail.getFirstName(), first)
						&& 	StringUtils.equalsIgnoreCase(custDetail.getLastName(), last)){
					jsonObject.element("name_match", true);
				}
				if(ArrayUtils.contains(wipInds, custDetail.getWipInd())){
					jsonObject.element("wip", true);
					isWip = true;
				}else{
					isWip = false;
				}
				//==PDPO======================================================================
				try{			
				CustPdpoProfileDTO[] dataInfoList = custPdpoProfileService.getCustDataPrivacyInfo(custDetail.getCustNum(), BomLtsConstant.SYSTEM_ID_DRG);

				boolean showPdpoStatement = false;
				boolean isOptIndEmpty = true;
				String ltsStatus = "";
				String iddStatus = "";
				
				if(dataInfoList != null && dataInfoList.length > 0){
					for(CustPdpoProfileDTO dataInfo : dataInfoList){
						if(StringUtils.isNotBlank(dataInfo.getOptInd())){
							isOptIndEmpty = false;
						}
						
						if(StringUtils.isNotBlank(dataInfo.getOptInd()) 
								&& BomLtsConstant.DATA_PRIVACY_OPT_IND_OOA_CD.equals(dataInfo.getOptInd())){
							showPdpoStatement = true;
						}
						
						if(BomLtsConstant.DATA_PRIVACY_BOM_LOB_LTS.equals(dataInfo.getLob())){
							ltsStatus = BomLtsConstant.DATA_PRIVACY_STATUS_MAP.get(dataInfo.getOptInd());
						}
						
						if(BomLtsConstant.DATA_PRIVACY_BOM_LOB_IDD.equals(dataInfo.getLob())){
							iddStatus = BomLtsConstant.DATA_PRIVACY_STATUS_MAP.get(dataInfo.getOptInd());
						}
					}
					
					if(!showPdpoStatement && isOptIndEmpty){
						showPdpoStatement = true;
					}
					
					if(StringUtils.isBlank(ltsStatus) || StringUtils.isBlank(iddStatus)){
						showPdpoStatement = true;
					}
				}
				else{		// No PDPO records
					showPdpoStatement = true;
				}
				
				jsonObject.element("showPdpoStatement", showPdpoStatement);
				
			} catch (AppRuntimeException e) {
				logger.warn("Exception - getCustDataPrivacyInfo ", e);
			}
			//==PDPO end===============================================================
				
			}
			else{
				orderCapture.setCustomerDetailProfile(null);
//				orderCapture.setImsServiceProfile(null);
				jsonObject.element("cust_exist", false);
			}
		}else if("NAME".equals(checkOpt)){
			custDetail = orderCapture.getCustomerDetailProfile();
			if(custDetail != null){
				if(StringUtils.equalsIgnoreCase(custDetail.getFirstName(), first)
						&& 	StringUtils.equalsIgnoreCase(custDetail.getLastName(), last)){
					jsonObject.element("name_match", true);
				}else{
					jsonObject.element("name_match", false);
				}
			}
		}
		
		String url = generateLiveChatUrl(request, first, last, isWip, isSpecialHandleCust);
		jsonObject.element("live_chat_url", url);
		
		return new ModelAndView("ajax_ajaxprofilelookup", jsonObject);
	}

	private String generateLiveChatUrl(HttpServletRequest request, String firstName, String lastName, boolean isWip, boolean isSpecialHandleCust){
		final String LIVE_CHAT_URL = "live_chat_url";
		final String LIVE_CHAT_SRV_TYPE = "srv_type";
		
		OrderCaptureDTO order = SessionHelper.getOrderCapture(request);
		
		BuildingMarkerDTO marker 	= order.getAddressLookupForm().getBuildingMarker();
		String channel 				= order.getChannelId();
		boolean isChi				= BomLtsConstant.LOCALE_CHI.equals(order.getLang());
		String nickName				= firstName + " " + lastName;
		String srv_Id				= order.getServiceTypeInd();
		String addressEn 			= marker.getAddressEn();
		String addressCh 			= marker.getAddressCh();
		String reqPage				= "applicantInfo";
		String flat					= order.getAddressRolloutForm().getFlat();
		String floor				= order.getAddressRolloutForm().getFloor();
		
		String url = livechatpathService.generateLiveChatUrl(marker, channel, isChi, srv_Id, addressEn, addressCh, reqPage, flat, floor, isWip, isSpecialHandleCust);
		
		request.getSession().setAttribute(LIVE_CHAT_URL, url);	
		request.getSession().setAttribute(LIVE_CHAT_SRV_TYPE, srv_Id);
		
		return url;
	}
	
	
	public ImsDetailService getImsDetailService() {
		return imsDetailService;
	}

	public void setImsDetailService(ImsDetailService imsDetailService) {
		this.imsDetailService = imsDetailService;
	}

	public CustomerDetailService getCustomerDetailService() {
		return customerDetailService;
	}

	public void setCustomerDetailService(CustomerDetailService customerDetailService) {
		this.customerDetailService = customerDetailService;
	}

	public LivechatpathService getLivechatpathService() {
		return livechatpathService;
	}

	public void setLivechatpathService(LivechatpathService livechatpathService) {
		this.livechatpathService = livechatpathService;
	}

	public CodeLkupCacheService getSpecialHandleDocIdLkupCacheService() {
		return specialHandleDocIdLkupCacheService;
	}

	public void setSpecialHandleDocIdLkupCacheService(
			CodeLkupCacheService specialHandleDocIdLkupCacheService) {
		this.specialHandleDocIdLkupCacheService = specialHandleDocIdLkupCacheService;
	}

	//== PDPO ==
	public CustPdpoProfileService getCustPdpoProfileService() {
		return custPdpoProfileService;
	}

	public void setCustPdpoProfileService(CustPdpoProfileService custPdpoProfileService) {
		this.custPdpoProfileService = custPdpoProfileService;
	}
	//== PDPO end ==

}
