package com.bomwebportal.sbs.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.AddressCategoryDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.OrderMobDTO;
import com.bomwebportal.mob.ccs.service.DeliveryService;
import com.bomwebportal.sbs.dto.DeliveryDateRangeDTO;
import com.bomwebportal.sbs.dto.SbsContactInfoDTO;
import com.bomwebportal.sbs.dto.SbsDeliveryInfoDTO;
import com.bomwebportal.sbs.dto.form.SbsContactDeliveryForm;
import com.bomwebportal.sbs.service.SbsDeliveryService;
import com.bomwebportal.sbs.service.SbsOrderService;
import com.bomwebportal.sbs.validator.SbsCustomerProfileValidator;
import com.bomwebportal.service.CustomerProfileService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.DateUtils;
import com.bomwebportal.util.Utility;

@Controller
@SessionAttributes("sbsContactDeliveryInfo")
public class SbsDoaController {
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	@Autowired
	private CustomerProfileService customerProfileService;
	
	@Autowired
	private DeliveryService deliveryService;
	
	@Autowired
	private SbsOrderService sbsOrderService;
	@Autowired
	private SbsDeliveryService sbsDeliveryService;
	@Autowired
	private OrderService orderService;
	
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    
    @ModelAttribute
    public void referenceData(String orderId, Model model) {
   	
		List<String> titleList = new ArrayList<String>();
		titleList.add("Dr");
		titleList.add("Mdm");
		titleList.add("Miss");
		titleList.add("Mr");
		titleList.add("Mrs");
		titleList.add("Ms");
		titleList.add("Prof");
		titleList.add("Rev");
		model.addAttribute("titleList", titleList);
		
    	List<AddressCategoryDTO> streetCatList = LookupTableBean.getInstance().getAddressCategoryList();
    	model.addAttribute("streetCatList", streetCatList);
    
	   	OrderDTO orderDTO = orderService.getOrder(orderId);
    	model.addAttribute("order", orderDTO);
    	model.addAttribute("appDate", Utility.date2String(orderDTO.getAppInDate(), BomWebPortalConstant.DATE_FORMAT));
    	
    	List<String> normalDateList = getNormalDateList(orderId, orderDTO.getAppInDate());
    	model.addAttribute("normalDateList", normalDateList);
    }
	
    /*
	@ModelAttribute("normalDateList")
	public List<String> getNormalDateList()  {

		SimpleDateFormat dt = new SimpleDateFormat("yyyy-M-d");
		List<String> normalDateList = new ArrayList<String>();

		DeliveryDateRangeDTO deliveryDateRangeDTO = null;
	
		deliveryDateRangeDTO = sbsDeliveryService.getDeliveryDate(new Date());

		Calendar start = Calendar.getInstance();
		start.setTime(deliveryDateRangeDTO.getStartDate());
		Calendar end = Calendar.getInstance();
		end.setTime(deliveryDateRangeDTO.getEndDate());

		while (start.getTime().before(end.getTime())) {
			// do something
			if (StringUtils.equals(deliveryDateRangeDTO.getPhDateString(), Utility.date2String(start.getTime(), DATE_FORMAT))) {
				// return true;
			} else {
				normalDateList.add(dt.format(start.getTime()));
			}
			start.add(Calendar.DAY_OF_WEEK, 1);
		}

		return normalDateList;
	}
	*/
    
	private List<String> getNormalDateList(String orderId, Date appDate)  {

		SimpleDateFormat dt = new SimpleDateFormat("yyyy-M-d");
		List<String> normalDateList = new ArrayList<String>();

		if (appDate == null) appDate = new Date();
		
		DeliveryDateRangeDTO deliveryDateRangeDTO = deliveryService.normalDeliveryDateRange(orderId, "C", 
				"", appDate);
		Date nStartDate = deliveryDateRangeDTO.getStartDate();
		Date nEndDate = deliveryDateRangeDTO.getEndDate();
		List<String> phList = new ArrayList<String>();
		String errorDate = null;
		if (deliveryDateRangeDTO.getPhDateString()!=null){
			String phDateString = deliveryDateRangeDTO.getPhDateString().substring(1, deliveryDateRangeDTO.getPhDateString().length()-1);		
			if (StringUtils.isNotEmpty(phDateString)){
				String[] ary = phDateString.split(",");
				phList = Arrays.asList(ary);
			}
		}

		System.out.println(phList.size());
		long i = DateUtils.daysDiffBetween (nEndDate, nStartDate);

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

		return normalDateList;
	}
    
    
	//@ModelAttribute("sbsContactDeliveryInfo")
	public DoaContactDeliveryForm getContactDeliveryInfo(String orderId) {
		DoaContactDeliveryForm form = new DoaContactDeliveryForm();
		form.setOrderId(orderId);
		SbsContactInfoDTO contactInfo = sbsOrderService.getContactInfo(orderId);
		if (contactInfo == null) contactInfo = new SbsContactInfoDTO();
		form.setContactInfo(contactInfo);

		
		SbsDeliveryInfoDTO deliveryInfo = sbsOrderService.getDeliveryInfo(orderId);
		//if (deliveryInfo == null) deliveryInfo = new SbsDeliveryInfoDTO();
		form.setDeliveryInfo(deliveryInfo);

		if (deliveryInfo != null && StringUtils.isNotEmpty(deliveryInfo.getDeliveryTimeSlot())) {
			String timeSlotDesc = sbsDeliveryService.getTimeSlotDesc(deliveryInfo.getDeliveryTimeSlot());
			deliveryInfo.setDeliveryTimeSlotDesc(timeSlotDesc);
		}
		
		OrderMobDTO orderMobDTO = orderService.getOrderMobDTO(orderId);
		form.setOrderSubType(orderMobDTO.getOrderSubType());
		
		return form;
	}
	
	@RequestMapping(value="/sbsdoa", method=RequestMethod.GET)
	public String showForm(String orderId,
			Boolean init,
			//@ModelAttribute("sbsContactDeliveryInfo") SbsContactDeliveryForm sbsContactDeliveryInfo,
			HttpSession session,
			ModelMap model) {
		
		DoaContactDeliveryForm sbsContactDeliveryInfo = null;
		if (session.getAttribute("sbsContactDeliveryInfo") instanceof DoaContactDeliveryForm) {
			sbsContactDeliveryInfo = (DoaContactDeliveryForm)session.getAttribute("sbsContactDeliveryInfo");
		}
		
		if (StringUtils.isNotEmpty(orderId)) {
			if (Boolean.TRUE == init 
					|| sbsContactDeliveryInfo == null 
					|| !sbsContactDeliveryInfo.getOrderId().equals(orderId)) {
				sbsContactDeliveryInfo = getContactDeliveryInfo(orderId);
			}
		}

		OrderDTO orderDTO = (OrderDTO)model.get("order");
		if ("N002".equals(orderDTO.getReasonCd()) || "N003".equals(orderDTO.getReasonCd())) {
			sbsContactDeliveryInfo.setDoa(true);
		} else if ("G002".equals(orderDTO.getReasonCd()) || "G003".equals(orderDTO.getReasonCd())) {
			sbsContactDeliveryInfo.setOnsiteDoa(true);
		}

	   	//OrderDTO orderDTO = orderService.getOrder(sbsContactDeliveryInfo.getOrderId());
	    
		model.addAttribute("sbsContactDeliveryInfo", sbsContactDeliveryInfo);
    	//model.addAttribute("order", orderDTO);

		
		//System.out.println(model.asMap());

		return "sbsdoa";
	}
	
	@RequestMapping(value="/sbsdoa", method=RequestMethod.POST)
	public String submit(
			@ModelAttribute("sbsContactDeliveryInfo") SbsContactDeliveryForm sbsContactDeliveryInfo,
			BindingResult result,
			SessionStatus status,
			HttpSession session,
			Model model) {

		new SbsCustomerProfileValidator().validate(sbsContactDeliveryInfo, result);
		SbsDeliveryInfoDTO delivery = sbsContactDeliveryInfo.getDeliveryInfo();
		OrderDTO order = (OrderDTO)model.asMap().get("order");
		if (delivery != null) {	
			validateDeliveryDate(delivery.getDeliveryDate(), sbsContactDeliveryInfo.getOrderId(), order.getAppInDate(), result);
		}
		if (result.hasErrors()) {
			return "sbsdoa";
		}
		model.asMap().clear();
		return "redirect:sbsdoareview.html?orderId="+sbsContactDeliveryInfo.getOrderId();
	}
	
	@RequestMapping(value="/sbsdoareview")
	public String review(
			@ModelAttribute("sbsContactDeliveryInfo") SbsContactDeliveryForm sbsContactDeliveryInfo,
			BindingResult result,
			SessionStatus status,
			HttpSession session,
			Model model) {
		
		


		return "sbsdoareview";
	}
	
	
	@RequestMapping(value="/sbsdoareview", method=RequestMethod.POST)
	public String confirm(
			@ModelAttribute("sbsContactDeliveryInfo") DoaContactDeliveryForm sbsContactDeliveryInfo,
			BindingResult result,
			SessionStatus status,
			HttpSession session,
			Model model) {
		BomSalesUserDTO loginUserDto = (BomSalesUserDTO) session.getAttribute("bomsalesuser");
		
		String username = loginUserDto.getUsername();
		String orderId = sbsContactDeliveryInfo.getOrderId();
		
		sbsContactDeliveryInfo.setLastUpdBy(loginUserDto.getUsername());
		
		orderService.backupOrder(orderId);

		sbsOrderService.updateContactInfo(sbsContactDeliveryInfo.getContactInfo());
		if (sbsContactDeliveryInfo.getDeliveryInfo() != null) {
			sbsOrderService.updateDeliveryInfo(sbsContactDeliveryInfo.getDeliveryInfo());
		}

		
		if (sbsContactDeliveryInfo.isDoa()) {
			orderService.updateCcsOrderStatus(orderId, "99", "999", "N003", "", "", username);
			orderService.insertOrderFallout(
					orderId,
					username, "DOA", "N003", "");
			sbsOrderService.changeDoaRequestStatus(orderId, "N003", "N002", username);
			sbsOrderService.insertOrderRemark(orderId, username + " DOA", username);
			

		} else if (sbsContactDeliveryInfo.isOnsiteDoa()) {
			orderService.updateCcsOrderStatus(
					orderId, "99", "999", "G003", "", "", username);
			orderService.insertOrderFallout(
					orderId,
					username, "ON_DOA", "G003", "");
			sbsOrderService.changeDoaRequestStatus(orderId, "G003", "G002", username);
			sbsOrderService.insertOrderRemark(orderId, username + " ONSITE DOA", username);

		}
		status.setComplete();
		model.asMap().clear();
		return "redirect:/sbsorderdetail_closedialog.html?orderId="+orderId;
	}
	
	
	
	
	
	private void validateDeliveryDate(Date deliveryDate, String orderId, Date appDate, Errors errors) {
		if (deliveryDate == null) return;
		if (appDate == null) appDate = new Date();
		
		
		DeliveryDateRangeDTO dateRange = deliveryService.normalDeliveryDateRange(orderId, "C", 
				"", appDate);
		

		//check valid delivery date range
		if (dateRange == null)  {
			errors.rejectValue("deliveryInfo.deliveryDate", "deliveryDateStr.error",
					"System eror. Please try again in a few minutes later");
		} else {
			Date startDate = dateRange.getStartDate();
			Date endDate = dateRange.getEndDate();
			if (DateUtils.daysDiffBetween(deliveryDate, startDate) < 0 || DateUtils.daysDiffBetween(deliveryDate, endDate) > 0) {
				errors.rejectValue("deliveryInfo.deliveryDate", "deliveryDateStr.error",
						"Delivery Date must be in between "
								+ Utility.date2String(startDate,
										BomWebPortalConstant.DATE_FORMAT) + " and " 
								+ Utility.date2String(endDate,
												BomWebPortalConstant.DATE_FORMAT));
			}
		}

	}
	
	
	
	
	
	
	
	
	public static class DoaContactDeliveryForm extends SbsContactDeliveryForm {
		boolean doa;
		boolean onsiteDoa;
		public boolean isDoa() {
			return doa;
		}
		public void setDoa(boolean doa) {
			this.doa = doa;
		}
		public boolean isOnsiteDoa() {
			return onsiteDoa;
		}
		public void setOnsiteDoa(boolean onsiteDoa) {
			this.onsiteDoa = onsiteDoa;
		}
		
		
	}

}