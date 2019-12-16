package com.bomwebportal.mob.ccs.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.ccs.dto.ui.PaymentUpFrontUI;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.DeliveryService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.sbs.dto.DeliveryDateRangeDTO;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.DateUtils;
import com.bomwebportal.util.Utility;

public class MobCcsDoaDeliveryController extends SimpleFormController{
	
	protected final Log logger = LogFactory.getLog(getClass());

	private DeliveryService deliveryService;

	private CodeLkupService codeLkupService;

	/**
	 * @return the deliveryService
	 */
	public DeliveryService getDeliveryService() {
		return deliveryService;
	}

	/**
	 * @param deliveryService the deliveryService to set
	 */
	public void setDeliveryService(DeliveryService deliveryService) {
		this.deliveryService = deliveryService;
	}

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}

	public MobCcsDoaDeliveryController() {
	}
	
	private void setDeliveryAddress (DeliveryUI delivery, CustomerProfileDTO customer) {
		delivery.getPrimaryContact().setTitle(customer.getTitle());
		delivery.getPrimaryContact().setContactName(customer.getContactName());
		delivery.getPrimaryContact().setContactPhone(customer.getContactPhone());
		delivery.getPrimaryContact().setOtherPhone(customer.getOtherContactPhone());
		delivery.getPrimaryContact().setIdDocType(customer.getIdDocType());
		delivery.getPrimaryContact().setIdDocNum(customer.getIdDocNum());
		
		//address copy
		delivery.setFlat(customer.getFlat());
		delivery.setFloor(customer.getFloor());
		delivery.setLotNum(customer.getLotNum());
		delivery.setBuildingName(customer.getBuildingName());
		delivery.setStreetNum(customer.getStreetNum());
		delivery.setStreetName(customer.getStreetName());
		delivery.setStreetCatgDesc(customer.getStreetCatgDesc());
		delivery.setStreetCatgCode(customer.getStreetCatgCode());
		delivery.setSectionDesc(customer.getSectionDesc());
		delivery.setSectionCode(customer.getSectionCode());
		delivery.setDistrictDesc(customer.getDistrictDesc());
		delivery.setDistrictCode(customer.getDistrictCode());
		delivery.setAreaDesc(customer.getAreaDesc());
		delivery.setAreaCode(customer.getAreaCode());
		delivery.setQuickSearch(customer.getQuickSearch());
		delivery.setCustAddressFlag(customer.isCustAddressFlag());
	}
	
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		logger.info("MobCcsDoaDeliveryController@formBackingObject called");
		DeliveryUI deliveryDto = (DeliveryUI) MobCcsSessionUtil
				.getSession(request, "delivery");//get session from hashMap session
		
		if (deliveryDto == null) {
			return new DeliveryUI();
		}
		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		if (salesUserDto != null) {
			deliveryDto.setLastUpdBy(salesUserDto.getUsername());
			deliveryDto.getPrimaryContact().setLastUpdBy(salesUserDto.getUsername());
		}
		
		String appDate = (String) request.getSession().getAttribute("appDate");
		if (appDate == null) {
			appDate = (String) MobCcsSessionUtil.getSession(request, "appDate");
		}
		
		deliveryDto.setValue("appDate", appDate);
		
		BasketDTO basketDTO = (BasketDTO) MobCcsSessionUtil.getSession(request, "basket");
		deliveryDto.setValue("basket", basketDTO);
		deliveryDto.setUrgentInd(false);
		deliveryDto.setRecieveByThirdPartyInd(false);
		
		return deliveryDto;
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {

		DeliveryUI deliveryDto = (DeliveryUI) command;
		
		deliveryDto.setDeliveryDate(Utility.string2Date(deliveryDto
				.getDeliveryDateStr()));
				
		MobCcsSessionUtil.setSession(request, "doaDelivery", deliveryDto);// save session to hashMap session
		ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccsdoaupdatemrt.html"));
		modelAndView.addObject("orderId", deliveryDto.getOrderId());
		return modelAndView;
	}

	protected Map referenceData(HttpServletRequest request) throws Exception {
		logger.info("ReferenceData called");
		Map referenceData = new HashMap<String, List<String>>();

		List<CodeLkupDTO> titleList = codeLkupService.getCodeLkupDTOALL("NAME_TITLE");
		referenceData.put("titleList", titleList);
		referenceData.put("titleList2", titleList);

		referenceData.put("streetCatgList", LookupTableBean.getInstance().getAddressCategoryList());

		List<CodeLkupDTO> pickUpCentreList = codeLkupService.getCodeLkupDTOALL("PICK_CENTRE");
		
		
		BasketDTO basketDTO = (BasketDTO) MobCcsSessionUtil.getSession(request, "basket");
		OrderDTO orderDTO = (OrderDTO) MobCcsSessionUtil.getSession(request, "orderDTO");
		PaymentUpFrontUI paymentupFront = (PaymentUpFrontUI) MobCcsSessionUtil.getSession(request, "paymentUpFront");
		
		String appDate = (String) request.getSession().getAttribute("appDate");
		if (appDate == null) {
			appDate = (String) MobCcsSessionUtil.getSession(request, "appDate");
		}
		referenceData.put("appDate", appDate);
		
		String orderId = "";
		if (orderDTO != null && orderDTO.getOrderId() != null ) {
			orderId = orderDTO.getOrderId();
		} 
		
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-M-d");
		List<String> normalDateList = new ArrayList<String>();
		//set normal date range
		/*DeliveryDateRangeDTO deliveryDateRangeDTO = deliveryService.normalDeliveryDateRange(orderId, paymentupFront.getPayMethodType(), 
				basketDTO.getHsPosItemCd(), Utility.string2Date(appDate));
		
		Date nStartDate = deliveryDateRangeDTO.getStartDate();
		Date nEndDate = deliveryDateRangeDTO.getEndDate();
		List<String> phList = new ArrayList<String>();
		String errorDate = null;
		if (deliveryDateRangeDTO.getPhDateString()!=null){
			String phDateString = deliveryDateRangeDTO.getPhDateString().substring(1, deliveryDateRangeDTO.getPhDateString().length()-1);		
			logger.info("phDateString:"+phDateString);
			if (StringUtils.isNotEmpty(phDateString)){
				String[] ary = phDateString.split(",");
				phList = Arrays.asList(ary);
			}
		}
		logger.info("phList.size():"+phList.size());
		System.out.println(phList.size());
		long i = DateUtils.daysDiffBetween (nEndDate, nStartDate);
		logger.info("daysDiffBetween:"+i);
		//Order out of delivery date range and have to force cancel
		if (i < 0) {
			errorDate = "This order is out of the available delivery date range. " +
					"Please cancel and re-create a new order.";
		} */
		//checking for available date pick. 
		//Date with PH date type will be disable for selection
		/*for (int j = 0; j < i+1; j++) {
			if (j == 0) {
				
				String date = Utility.date2String(nStartDate, BomWebPortalConstant.DATE_FORMAT);
				
				if (!phList.contains(date)){
					normalDateList.add(dt.format(nStartDate));
				}
				String dateType = deliveryService
						.getDateType(Utility.date2String(nStartDate,
								BomWebPortalConstant.DATE_FORMAT));
				
				if (!"PH".equalsIgnoreCase(dateType)) {
					normalDateList.add(dt.format(nStartDate));
				}
			} else {
				Date nextDay = DateUtils.dateAfterdays(nStartDate, j);
				String date = Utility.date2String(nextDay, BomWebPortalConstant.DATE_FORMAT);
				if (!phList.contains(date)){
					normalDateList.add(dt.format(nextDay));
				}
				
				Date nextDay = DateUtils.dateAfterdays(nStartDate, j);
				
				String dateType = deliveryService
						.getDateType(Utility.date2String(nextDay,
								BomWebPortalConstant.DATE_FORMAT));
				
				if (!"PH".equalsIgnoreCase(dateType)) {
					normalDateList.add(dt.format(nextDay));
				}
			}
			
		}*/
		
		MRTUI mrtui = (MRTUI) MobCcsSessionUtil.getSession(request, "mrt");
		
		referenceData.put("orderId", orderId);
		referenceData.put("hsItemCd", basketDTO.getHsPosItemCd());
		referenceData.put("hsInd", basketDTO.getBasketTypeId());
		referenceData.put("hsInd", basketDTO.getBasketTypeId());
		referenceData.put("mnpInd", mrtui.getMnpInd());
		
		/*referenceData.put("errorDate", errorDate);
		referenceData.put("maxDate", nEndDate);*/
		referenceData.put("normalDateList", normalDateList);
		
		return referenceData;
	}


}
