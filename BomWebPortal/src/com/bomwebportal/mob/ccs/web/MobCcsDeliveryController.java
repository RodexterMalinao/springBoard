package com.bomwebportal.mob.ccs.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.SuperOrderDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.ccs.dto.ui.PaymentUpFrontUI;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.DeliveryService;
import com.bomwebportal.mob.ccs.util.BomWebPortalCcsConstant;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.sbs.dto.DeliveryDateRangeDTO;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.DateUtils;
import com.bomwebportal.util.Utility;

public class MobCcsDeliveryController extends SimpleFormController {
	protected final Log logger = LogFactory.getLog(getClass());

	private CodeLkupService codeLkupService;
	private VasDetailService vasDetailService;

	public VasDetailService getVasDetailService() {
		return vasDetailService;
	}

	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}

	DeliveryService deliveryService;

	/**
	 * @return the deliveryService
	 */
	public DeliveryService getDeliveryService() {
		return deliveryService;
	}

	/**
	 * @param deliveryService
	 *            the deliveryService to set
	 */
	public void setDeliveryService(DeliveryService deliveryService) {
		this.deliveryService = deliveryService;
	}
	
	public MobCcsDeliveryController() {
	}
	
	private void setDeliveryAddress (DeliveryUI delivery, CustomerProfileDTO customer) {
		delivery.getPrimaryContact().setTitle(customer.getTitle());
		delivery.getPrimaryContact().setContactName(customer.getContactName());
		delivery.getPrimaryContact().setContactPhone(customer.getContactPhone());
		delivery.getPrimaryContact().setOtherPhone(customer.getOtherContactPhone());
		delivery.getPrimaryContact().setIdDocType(customer.getIdDocType());
		delivery.getPrimaryContact().setIdDocNum(customer.getIdDocNum());
		
		//address copy
		delivery.setAddress1(customer.getAddress1());
		delivery.setAddress2(customer.getAddress2());
		delivery.setAddress3(customer.getAddress3());
		
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
		delivery.setCustAddressFlag2(customer.isCustAddressFlag2());
		delivery.setUnlinkSectionFlag(customer.isUnlinkSectionFlag());
	}
	
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		logger.info("MobCcsDeliveryController@formBackingObject called");
		DeliveryUI sessionDeliveryDto = (DeliveryUI) MobCcsSessionUtil
				.getSession(request, "delivery");//get session from hashMap session
		
		CustomerProfileDTO sessionCustomer = (CustomerProfileDTO)MobCcsSessionUtil.getSession(request, "customer");
		
		if (sessionDeliveryDto == null) {
			logger.info("MobCcsDeliveryController@formBackingObject called, , sessionDeliveryDto is null");
			sessionDeliveryDto = new DeliveryUI();
			sessionDeliveryDto.setDeliveryInd("D");// set default = "D", for jsp page
			
			//Copy CustomerProfileDTO session to DeliveryUI
			if(sessionCustomer!=null){
				setDeliveryAddress(sessionDeliveryDto, sessionCustomer);				
			}
			
		}
		
		//decleared for validation purpose
		MRTUI mrtui = (MRTUI) MobCcsSessionUtil.getSession(request, "mrt");
		
		if (mrtui != null) {
			if (mrtui.getCutOverDateStr() != null && !mrtui.getCutOverDateStr().isEmpty()) {
				sessionDeliveryDto.setServiceDate(Utility.string2Date(mrtui.getCutOverDateStr()));
			} else if (mrtui.getServiceReqDateStr() != null && !mrtui.getServiceReqDateStr().isEmpty())
			sessionDeliveryDto.setServiceDate(Utility.string2Date(mrtui.getServiceReqDateStr()));
			
			if (mrtui.getMnpInd() != null) {
				sessionDeliveryDto.setMnp(mrtui.getMnpInd().equalsIgnoreCase("Y") ? true : false);
			}
			sessionDeliveryDto.setValue("mrt", mrtui);
		}
		//decleared for validation purpose
		PaymentUpFrontUI upFront = (PaymentUpFrontUI) MobCcsSessionUtil.getSession(request, "paymentUpFront");
		if (upFront != null) {
			if (upFront.getPayMethodType() != null) {
				sessionDeliveryDto.setPaymentMethod(upFront.getPayMethodType());
			}
		}
		
		String appDate = (String) request.getSession().getAttribute("appDate");
		if (appDate == null) {
			appDate = (String) MobCcsSessionUtil.getSession(request, "appDate");
		}
		
		sessionDeliveryDto.setValue("appDate", appDate);
		
		OrderDTO orderDTO = (OrderDTO) MobCcsSessionUtil.getSession(request, "orderDTO");
		
		if (orderDTO != null && BomWebPortalCcsConstant.ORD_CCS_TYPE_DRAF.equalsIgnoreCase(orderDTO.getBusTxnType())) {
			setDeliveryAddress(sessionDeliveryDto, sessionCustomer);
		}
		
		//initiate Delivery method 
		if (sessionDeliveryDto.getDeliveryInd() == null) {
			sessionDeliveryDto.setDeliveryInd("D");
		}
		
		BasketDTO basketDTO = (BasketDTO) MobCcsSessionUtil.getSession(request, "basket");
		sessionDeliveryDto.setValue("basketDTO", basketDTO);
		
		List<MultiSimInfoDTO> multiSimInfoDTOs = (List<MultiSimInfoDTO>) request.getSession().getAttribute("MultiSimInfoList");
		String[] vasItemArray = (String[]) request.getSession().getAttribute("selectedVasItemList");
		
		sessionDeliveryDto.setAllowUrgentDelivery(true);
		if (multiSimInfoDTOs != null) {
			List<String> vasItemList = vasDetailService.getSubscribedVASList(basketDTO.getBasketId(), vasItemArray, sessionCustomer.getBrand(), sessionCustomer.getSimType());
			for (MultiSimInfoDTO msi: multiSimInfoDTOs) {
				if (vasItemList.contains(msi.getItemId())) {
					sessionDeliveryDto.setAllowUrgentDelivery(false);
					break;
				}
			}
		}
		
		return sessionDeliveryDto;
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {

		String nextView = (String) request.getAttribute("nextView");
		logger.info("Next View: " + nextView);
		DeliveryUI deliveryDto = (DeliveryUI) command;

		if (!deliveryDto.isDummyDeliveryDateInd()) {
			deliveryDto.setDeliveryDate(Utility.string2Date(deliveryDto.getDeliveryDateStr()));
		} else {
			deliveryDto.setDeliveryDate(null);
		}
		
		//James 20120307 #set time slot into deliveryUI
		if ("D".equalsIgnoreCase(deliveryDto.getDeliveryInd()) && !deliveryDto.isDummyDeliveryDateInd()) {
			String timeSlot = request.getParameter("deliveryTimeSlotText");
			if (timeSlot != null) {
				int pivot = timeSlot.indexOf("-");
				deliveryDto.setTimeSlotFrom(timeSlot.substring(0, pivot));
				deliveryDto.setTimeSlotTo(timeSlot.substring(pivot + 1));
			}
			deliveryDto.setDmId("HD");
		} else {
			deliveryDto.setDeliveryTimeSlot(null);
		}
		
				
		MobCcsSessionUtil.setSession(request, "delivery", deliveryDto);// save session to hashMap session
		return new ModelAndView(new RedirectView(nextView));

	}

	protected Map referenceData(HttpServletRequest request) throws Exception {
		logger.info("ReferenceData called");
		Map referenceData = new HashMap<String, List<String>>();

		List<CodeLkupDTO> titleList = codeLkupService.getCodeLkupDTOALL("NAME_TITLE");
		referenceData.put("titleList", titleList);
		referenceData.put("titleList2", titleList);

		referenceData.put("streetCatgList", LookupTableBean.getInstance().getAddressCategoryList());

		List<CodeLkupDTO> pickUpCentreList = codeLkupService.getCodeLkupDTOALL("PICK_CENTRE");
		
		PaymentUpFrontUI paymentupFront = (PaymentUpFrontUI) MobCcsSessionUtil.getSession(request, "paymentUpFront");
		BasketDTO basketDTO = (BasketDTO) MobCcsSessionUtil.getSession(request, "basket");
		
		if (basketDTO != null){
			referenceData.put("basketType", basketDTO.getBasketTypeId());
		}
		referenceData.put("pickUpCentreList", pickUpCentreList);
		if (paymentupFront != null) {
			referenceData.put("payMethodType", paymentupFront.getPayMethodType());
		}
		
		OrderDTO orderDTO = (OrderDTO) MobCcsSessionUtil.getSession(request, "orderDTO");
		referenceData.put("orderDTO", orderDTO);
		referenceData.put("workStatus", MobCcsSessionUtil.getSession(request, "workStatus"));
		
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
		
		DeliveryDateRangeDTO deliveryDateRangeDTO = deliveryService.normalDeliveryDateRange(orderId, paymentupFront.getPayMethodType(), 
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
		long i = DateUtils.daysDiffBetween (nEndDate, nStartDate);
		logger.info("daysDiffBetween:"+i);
		//Order out of delivery date range and have to force cancel
		if (i < 0) {
			errorDate = "This order is out of the available delivery date range. " +
					"Please cancel and re-create a new order.";
		} 
		//checking for available date pick. 
		//Date with PH date type will be disable for selection
		for (int j = 0; j < i+1; j++) {
			if (j == 0) {
				
				String date = Utility.date2String(nStartDate, BomWebPortalConstant.DATE_FORMAT);
				
				if (!phList.contains(date)){
					normalDateList.add(dt.format(nStartDate));
				}
				/*String dateType = deliveryService
						.getDateType(Utility.date2String(nStartDate,
								BomWebPortalConstant.DATE_FORMAT));
				
				if (!"PH".equalsIgnoreCase(dateType)) {
					normalDateList.add(dt.format(nStartDate));
				}*/
			} else {
				Date nextDay = DateUtils.dateAfterdays(nStartDate, j);
				String date = Utility.date2String(nextDay, BomWebPortalConstant.DATE_FORMAT);
				if (!phList.contains(date)){
					normalDateList.add(dt.format(nextDay));
				}
				
				/*Date nextDay = DateUtils.dateAfterdays(nStartDate, j);
				
				String dateType = deliveryService
						.getDateType(Utility.date2String(nextDay,
								BomWebPortalConstant.DATE_FORMAT));
				
				if (!"PH".equalsIgnoreCase(dateType)) {
					normalDateList.add(dt.format(nextDay));
				}*/
			}
			
		}
		
		MRTUI mrtui = (MRTUI) MobCcsSessionUtil.getSession(request, "mrt");
		
		referenceData.put("orderId", orderId);
		referenceData.put("hsItemCd", basketDTO.getHsPosItemCd());
		referenceData.put("hsInd", basketDTO.getBasketTypeId());
		referenceData.put("mnpInd", mrtui.getMnpInd());
		
		referenceData.put("errorDate", errorDate);
		referenceData.put("maxDate", nEndDate);
		referenceData.put("normalDateList", normalDateList);
				
		//set urgent date range
		Calendar calendar = new GregorianCalendar();
		
		Date urgentStartDt = new Date();
		Date urgentEndDt = new Date();
		List<String> urgentDateList = new ArrayList<String>();
						
		if (calendar.get(Calendar.HOUR) >= 5 && calendar.get(Calendar.AM_PM) == 1) {
			urgentStartDt = DateUtils.dateAfterdays(urgentStartDt, 1);
		} 
		
		String currentDayDateType = deliveryService
				.getDateType(Utility.date2String(urgentStartDt,
						BomWebPortalConstant.DATE_FORMAT));
		
		if ("PH".equalsIgnoreCase(currentDayDateType) || 
				"SUNDAY".equalsIgnoreCase(currentDayDateType)) {
			urgentStartDt = toNextWorkDay(urgentStartDt);
		}
		
		urgentEndDt = deliveryService.getNextNDeliveryDay(urgentStartDt, 1);
						
		urgentDateList.add(dt.format(urgentStartDt));
		urgentDateList.add(dt.format(urgentEndDt));
		
		referenceData.put("urgentDateList", urgentDateList);
		
		CustomerProfileDTO sessionCustomer = (CustomerProfileDTO)MobCcsSessionUtil.getSession(request, "customer");
		if ("BS".equals(sessionCustomer.getIdDocType())){//when "BS" order not allow receive by third party
			referenceData.put("showReceiveByThirdPartyInd", "N");
			
		}
		
		return referenceData;
	}
	
	
	private Date toNextWorkDay(Date inDate) {

		String dateType = deliveryService.getDateType(Utility.date2String(
				inDate, BomWebPortalConstant.DATE_FORMAT));

		if ("WD".equalsIgnoreCase(dateType)) {
			return inDate;
		} else {
			return toNextWorkDay(DateUtils.dateAfterdays(inDate, 1));
		}
	}
	
}
