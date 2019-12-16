package com.bomltsportal.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.LocaleUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomltsportal.dao.MessageDAO;
import com.bomltsportal.dto.BuildingMarkerDTO;
import com.bomltsportal.dto.OnlineSalesRequestDTO;
import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.dto.form.SalesLeadFormDTO;
import com.bomltsportal.service.OnlineAddressRolloutService;
import com.bomltsportal.service.OnlineSalesRequestService;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.SearchHelper;
import com.bomltsportal.util.SessionHelper;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.AddressDetailDTO;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.service.bom.AddressDetailLtsService;
import com.pccw.util.spring.SpringApplicationContext;
import java.util.HashMap;


public class SalesLeadFormController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());
	private OnlineAddressRolloutService onlineAddressRolloutService;
	private AddressDetailLtsService addressDetailLtsService;
	private OnlineSalesRequestService onlineSalesRequestService;
	private SearchHelper searchHelper;
	private MessageSource messageSource;
	//Promotion and contact message
	private static final String HOUSING_TYPE = "HOUSING_TYPE";
	private static final String ORDER_TYPE = "ORDER_TYPE";
	private static final String SERVICE_TYPE = "SERVICE_TYPE";
	private static final String LANG = "LANG";
	//Promotion and contact message(end)

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(SessionHelper.getOrderCapture(request) == null){
			return SessionHelper.getSessionTimeOutView();
		}
		/*if(StringUtils.isBlank(request.getParameter("submitted")) 
				&& StringUtils.isBlank(request.getParameter("marker_idx"))){
			return SessionHelper.getSessionTimeOutView();
		}*/
		return super.handleRequestInternal(request, response);
	}
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		OrderCaptureDTO order = SessionHelper.getOrderCapture(request);
		SalesLeadFormDTO form = new SalesLeadFormDTO();

		String submitted = request.getParameter("submitted");
		if(StringUtils.equals(submitted, "true") 
				&& order != null && order.getSalesLeadForm() != null){
			form = order.getSalesLeadForm();
		}else {
			String salesLeadReason = request.getParameter("reason");
			form.setMarker_idx(request.getParameter("marker_idx"));
			form.setS_flat(request.getParameter("flat"));
			form.setS_floor(request.getParameter("floor"));
			String service = "";
			try {
				service = new String(request.getParameter("service").getBytes("iso-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				service = request.getParameter("service");
			} catch (NullPointerException npe){
				service = messageSource.getMessage("map."+ order.getServiceTypeInd().toLowerCase() +".srvtype1", new Object[] {}, LocaleUtils.toLocale(order.getLang())) ;
			}
			BuildingMarkerDTO marker = null;
			if(StringUtils.isBlank(form.getMarker_idx())){
				marker = order.getAddressLookupForm() != null? order.getAddressLookupForm().getBuildingMarker() : null;
			}else{
				marker = searchHelper.getBuildingMarkerDetailByKey(form.getMarker_idx());
			}
			form.setMarkerDTO(marker);
			if(marker != null){
				if(BomLtsConstant.LOCALE_CHI.equals(order.getLang())){
					form.setInstallAddress(marker.getAddressCh());
				}else{
					form.setInstallAddress(marker.getAddressEn());
				}
			}
			form.setService(service);
			form.setReason(salesLeadReason);
			
			//promotion and contact message
			String[] srvBrdys = onlineAddressRolloutService.getConsumerEyeSrvBdryByBuildCoordinate(
					null, null, marker.getBuildXy(), order.getServiceTypeInd());
			//String remark = null;
			if (ArrayUtils.isNotEmpty(srvBrdys)) {
				
				//if (StringUtils.equals(BomLtsConstant.FAIL_TO_GET_SRVBDRY_IND, srvBrdys[0])) {
				//	remark = srvBrdys[1];
				//}
				//else {
					
					try {
						AddressRolloutDTO addressRollout = onlineAddressRolloutService.getAddressRollout(srvBrdys, null, null, order.getServiceTypeInd());
						String pHousingType	= addressRollout == null  
								? "MASS" 
								: getStringMapperValue(StringUtils.substring(addressRollout.getHousingTypeCd(), 0, 3), HOUSING_TYPE);
						//System.out.println(addressRollout.getHousingTypeCd());
						//System.out.println(pHousingType);
						form.setHousingType(pHousingType);
						String pOrderType = getStringMapperValue(salesLeadReason, ORDER_TYPE);
						String pServiceType = getStringMapperValue(order.getServiceTypeInd(), SERVICE_TYPE);
						String pLang = getStringMapperValue(order.getLang(), LANG);
						String promotionMsg = SpringApplicationContext.getBean(MessageDAO.class).getPromotionMsg(pHousingType, pOrderType, pServiceType, pLang);
						form.setPromotionMsg(promotionMsg);
						String contactMsg = SpringApplicationContext.getBean(MessageDAO.class).getContactMsg(pHousingType, pOrderType, pServiceType, pLang);
						form.setContactMsg(contactMsg);
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				//}
			} else {
				String pHousingType = getStringMapperValue(null, HOUSING_TYPE);
				form.setHousingType(pHousingType);
			}
			//Promotion and contact message(End)
		}
		
		return form;
	}
	
	//promotion and contact message
	private String getStringMapperValue(String pKey, String pConvertType){
		//HashMap<String, HashMap<String, String>> allDataMap = new HashMap<String, HashMap<String, String>>();
		//e.g. pKey = "PT2" --> then return P2
		HashMap<String, String> convertMap = null;
		
		if(StringUtils.equals(pConvertType, HOUSING_TYPE)){
			convertMap = new HashMap<String, String>();
			convertMap.put("PT2", "P2");
			convertMap.put("PT1", "P1");
			convertMap.put("PT0", "P0");
			
			if(convertMap != null && StringUtils.isNotBlank(pKey)){
			return (String)convertMap.get(pKey) == null  ? "MASS" : (String)convertMap.get(pKey);
			}else{
				return "MASS";				
			}
					
		}else if(StringUtils.equals(pConvertType, ORDER_TYPE)){
			convertMap = new HashMap<String, String>();
			convertMap.put("PORTINGDN_EYE", "PIPB");
			convertMap.put("PORTINGDN_DEL", "PIPB");		
			convertMap.put("UPGRADE_EYE", "UPG");
			
			if(convertMap != null && StringUtils.isNotBlank(pKey)){
				return (String)convertMap.get(pKey) == null ? "ACQ" : (String)convertMap.get(pKey);
			}else{
				return "ACQ";
			}
			
		}else if(StringUtils.equals(pConvertType, SERVICE_TYPE)){
			convertMap = new HashMap<String, String>();
			convertMap.put("EYE", "EYE");
			convertMap.put("DEL", "DEL");
		}else if(StringUtils.equals(pConvertType, LANG)){
			convertMap = new HashMap<String, String>();
			convertMap.put("en", "E");
			convertMap.put("zh_HK", "C");
		}

		if(convertMap != null && StringUtils.isNotBlank(pKey)){
			return (String)convertMap.get(pKey);
		}else{
			return null;
		}
	}
	//promotion and contact message(End)
	
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		SalesLeadFormDTO form = (SalesLeadFormDTO) command;
		BuildingMarkerDTO buildMarker = form.getMarkerDTO();
		if(buildMarker == null){
			buildMarker = searchHelper.getBuildingMarkerDetailByKey(form.getMarker_idx());
		}
		String floor = form.getS_floor();
		String flat = StringUtils.isBlank(form.getS_flat())?" ":form.getS_flat();
		String[] srvBrdys = onlineAddressRolloutService
				.getConsumerEyeSrvBdryByBuildCoordinate(flat, floor,
						buildMarker.getBuildXy(),
						orderCapture.getServiceTypeInd());
		String remark = null;
		if (ArrayUtils.isNotEmpty(srvBrdys)) {
			
			if (StringUtils.equals(BomLtsConstant.FAIL_TO_GET_SRVBDRY_IND, srvBrdys[0])) {
				remark = srvBrdys[1];
			}
			else {
				AddressRolloutDTO addressRollout = onlineAddressRolloutService.getAddressRollout(srvBrdys, flat, floor, orderCapture.getServiceTypeInd());
				AddressDetailDTO addressDetail = addressDetailLtsService.getAddressDetail(addressRollout.getSrvBdary());
				
				orderCapture.setAddressRollout(addressRollout);
				orderCapture.setAddressDetail(addressDetail);	
			}
		}
		
		form.setSubmitted(true);
		orderCapture.setSalesLeadForm(form);
		
		OnlineSalesRequestDTO onlineSalesRequest = createRequestDTO(orderCapture, remark);
		onlineSalesRequestService.insertOnlineSalesRequest(onlineSalesRequest);
			
		return new ModelAndView(new RedirectView("salesleadform.html?submitted=true"));
//		return new ModelAndView("message", "msgCode", "sales.info.eye");
	}
	
	private OnlineSalesRequestDTO createRequestDTO(OrderCaptureDTO orderCapture, String remark){
		OnlineSalesRequestDTO request = new OnlineSalesRequestDTO();
		SalesLeadFormDTO form = orderCapture.getSalesLeadForm();
		BuildingMarkerDTO marker = form.getMarkerDTO();
		
		request.setAreaDescCh(marker.getAreaDescCh());
		request.setAreaDescEn(marker.getAreaDescEn());
		request.setBuildXy(marker.getBuildXy());
		request.setChannel(orderCapture.getChannelId());
		request.setContactNum(form.getContactNum());
		request.setDistrictDescCh(marker.getDistDescCh());
		request.setDistrictDescEn(marker.getDistDescEn());
		request.setEmail(form.getEmailAddr());
		request.setServiceDN(form.getServiceDN());
		request.setFlat(form.getS_flat());
		request.setFloor(form.getS_floor());
		request.setHousingAddrCh(marker.getHousingAddrCh());
		request.setHousingAddrEn(marker.getHousingAddrEn());
		request.setLangPref(orderCapture.getLang());
		request.setName(form.getName());
		request.setPremier(StringUtils.defaultIfEmpty(orderCapture.getAddressRollout() != null ? orderCapture.getAddressRollout().getHktPremier() : "", "N"));
		request.setRequestCd(BomLtsConstant.ONLINE_SALES_REQ_CD_LTS);
		request.setSectionDescCh(marker.getSectDescCh());
		request.setSectionDescEn(marker.getSectDescEn());
		request.setTitle(form.getTitle());
//		request.setRemarkEn(remark);

		
		if(BomLtsConstant.ONLINE_SALES_REQ_TYPE_PREMIER.equals(form.getReason())){
			request.setItemId(BomLtsConstant.ONLINE_SALES_REQ_ITEM_ID_PREMIER);
			request.setRequestType(BomLtsConstant.ONLINE_SALES_REQ_TYPE_PREMIER);
		}else if(BomLtsConstant.ONLINE_SALES_REQ_TYPE_UPGRADE_EYE.equals(form.getReason())){
			request.setItemId(BomLtsConstant.ONLINE_SALES_REQ_ITEM_ID_UPGRADE_EYE);
			request.setRequestType(BomLtsConstant.ONLINE_SALES_REQ_TYPE_UPGRADE_EYE);
		}else if(BomLtsConstant.ONLINE_SALES_REQ_TYPE_PORTINGDN_EYE.equals(form.getReason())){
			request.setItemId(BomLtsConstant.ONLINE_SALES_REQ_ITEM_ID_PORTINGDN_EYE);
			request.setRequestType(BomLtsConstant.ONLINE_SALES_REQ_TYPE_PORTINGDN_EYE);
		}else if(BomLtsConstant.ONLINE_SALES_REQ_TYPE_PORTINGDN_DEL.equals(form.getReason())){
			request.setItemId(BomLtsConstant.ONLINE_SALES_REQ_ITEM_ID_PORTINGDN_DEL);
			request.setRequestType(BomLtsConstant.ONLINE_SALES_REQ_TYPE_PORTINGDN_DEL);
		}else if(BomLtsConstant.ONLINE_SALES_REQ_TYPE_NOT_CLEANED_VILLAGE.equals(form.getReason())){
			request.setItemId(BomLtsConstant.ONLINE_SALES_REQ_ITEM_ID_NOT_CLEANED_VILLAGE);
			request.setRequestType(BomLtsConstant.ONLINE_SALES_REQ_TYPE_NOT_CLEANED_VILLAGE);
		}
		else if(BomLtsConstant.ONLINE_SALES_REQ_TYPE_PCD_SHORTAGE.equals(form.getReason())){
			request.setItemId(BomLtsConstant.ONLINE_SALES_REQ_ITEM_ID_PCD_SHORTAGE);
			request.setRequestType(BomLtsConstant.ONLINE_SALES_REQ_TYPE_PCD_SHORTAGE);
		}
		else if(BomLtsConstant.ONLINE_SALES_REQ_TYPE_SYSTEM_ERROR.equals(form.getReason())){
			request.setItemId(BomLtsConstant.ONLINE_SALES_REQ_ITEM_ID_SYSTEM_ERROR);
			request.setRequestType(BomLtsConstant.ONLINE_SALES_REQ_TYPE_SYSTEM_ERROR);
		}
		else if (orderCapture.getAddressRollout() != null
				&& ArrayUtils.isEmpty(orderCapture.getAddressRollout()
						.getTechnology())
				&& orderCapture.getAddressRollout().isPonVilla()) {
			request.setItemId(BomLtsConstant.ONLINE_SALES_REQ_ITEM_ID_PON_VILLA);
			request.setRequestType(BomLtsConstant.ONLINE_SALES_REQ_TYPE_PON_VILLA);
		}

		if (StringUtils.isNotBlank(remark)) {
			request.setItemId(BomLtsConstant.ONLINE_SALES_REQ_ITEM_ID_PCD_ROLLOUT_RETURN_ERROR);
			request.setRemarkEn(remark);
		}
		
		return request;
	}
	
	
	public SearchHelper getSearchHelper() {
		return searchHelper;
	}

	public void setSearchHelper(SearchHelper searchHelper) {
		this.searchHelper = searchHelper;
	}

	public OnlineAddressRolloutService getOnlineAddressRolloutService() {
		return onlineAddressRolloutService;
	}

	public void setOnlineAddressRolloutService(
			OnlineAddressRolloutService onlineAddressRolloutService) {
		this.onlineAddressRolloutService = onlineAddressRolloutService;
	}

	public AddressDetailLtsService getAddressDetailLtsService() {
		return addressDetailLtsService;
	}

	public void setAddressDetailLtsService(
			AddressDetailLtsService addressDetailLtsService) {
		this.addressDetailLtsService = addressDetailLtsService;
	}

	public OnlineSalesRequestService getOnlineSalesRequestService() {
		return onlineSalesRequestService;
	}

	public void setOnlineSalesRequestService(OnlineSalesRequestService onlineSalesRequestService) {
		this.onlineSalesRequestService = onlineSalesRequestService;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
