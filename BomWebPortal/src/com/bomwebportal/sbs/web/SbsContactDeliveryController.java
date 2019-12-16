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
public class SbsContactDeliveryController {
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
	
	//@ModelAttribute("normalDateList")
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
	public SbsContactDeliveryForm getContactDeliveryInfo(String orderId) {
		SbsContactDeliveryForm form = new SbsContactDeliveryForm();
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
	
	@RequestMapping(value="/sbscontactdelivery", method=RequestMethod.GET)
	public String showDetail(String orderId,
			Boolean init,
			//@ModelAttribute("sbsContactDeliveryInfo") SbsContactDeliveryForm sbsContactDeliveryInfo,
			HttpSession session,
			ModelMap model) {
		
		SbsContactDeliveryForm sbsContactDeliveryInfo = (SbsContactDeliveryForm)session.getAttribute("sbsContactDeliveryInfo");
		
		if (StringUtils.isNotEmpty(orderId)) {
			if (Boolean.TRUE == init 
					|| sbsContactDeliveryInfo == null 
					|| !sbsContactDeliveryInfo.getOrderId().equals(orderId)) {
				sbsContactDeliveryInfo = getContactDeliveryInfo(orderId);
			}
		}
	    
		model.addAttribute("sbsContactDeliveryInfo", sbsContactDeliveryInfo);

		

		return "sbscontactdelivery";
	}
	
	@RequestMapping(value="/sbscontactdelivery", method=RequestMethod.POST)
	public String submit(
			@ModelAttribute("sbsContactDeliveryInfo") SbsContactDeliveryForm sbsContactDeliveryInfo,
			BindingResult result,
			SessionStatus status,
			HttpSession session,
			Model model) {
		
		new SbsCustomerProfileValidator().validate(sbsContactDeliveryInfo, result);
		
		OrderDTO order = (OrderDTO)model.asMap().get("order");
		SbsDeliveryInfoDTO delivery = sbsContactDeliveryInfo.getDeliveryInfo();
		if (delivery != null) {	
			validateDeliveryDate(delivery.getDeliveryDate(), sbsContactDeliveryInfo.getOrderId(), order.getAppInDate(), result);
		}
		
		if (result.hasErrors()) {
			return "sbscontactdelivery";
		}
		model.asMap().clear();
		return "redirect:sbscontactdeliveryreview.html?orderId="+sbsContactDeliveryInfo.getOrderId();
	}
	
	@RequestMapping(value="/sbscontactdeliveryreview")
	public String review(
			@ModelAttribute("sbsContactDeliveryInfo") SbsContactDeliveryForm sbsContactDeliveryInfo,
			BindingResult result,
			SessionStatus status,
			HttpSession session,
			Model model) {
		
	

		return "sbscontactdeliveryreview";
	}
	
	
	@RequestMapping(value="/sbscontactdeliveryreview", method=RequestMethod.POST)
	public String confirm(
			@ModelAttribute("sbsContactDeliveryInfo") SbsContactDeliveryForm sbsContactDeliveryInfo,
			BindingResult result,
			SessionStatus status,
			HttpSession session,
			Model model) {
		BomSalesUserDTO loginUserDto = (BomSalesUserDTO) session.getAttribute("bomsalesuser");

		sbsContactDeliveryInfo.setLastUpdBy(loginUserDto.getUsername());
		
		orderService.backupOrder(sbsContactDeliveryInfo.getOrderId());
		
		sbsOrderService.updateContactInfo(sbsContactDeliveryInfo.getContactInfo());
		if (sbsContactDeliveryInfo.getDeliveryInfo() != null) {
			sbsOrderService.updateDeliveryInfo(sbsContactDeliveryInfo.getDeliveryInfo());
		}
		orderService.updateCcsOrderStatus(sbsContactDeliveryInfo.getOrderId(), "01", "490", "", "", "", loginUserDto.getUsername());
		
		sbsOrderService.insertOrderRemark(sbsContactDeliveryInfo.getOrderId(),
				loginUserDto.getUsername() + " UPDATED CONTACT AND DELIVERY INFO",
				loginUserDto.getUsername());
		
		status.setComplete();
		model.asMap().clear();
		return "redirect:/sbsorderdetail_closedialog.html?orderId="+sbsContactDeliveryInfo.getOrderId();
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

}