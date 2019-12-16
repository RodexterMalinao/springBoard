package com.bomwebportal.sbs.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.OrderMobDTO;
import com.bomwebportal.mob.ccs.dto.OrderRemarkDTO;
import com.bomwebportal.mob.ccs.dto.ui.PaymentUpFrontUI;
import com.bomwebportal.mob.ccs.service.DeliveryService;
import com.bomwebportal.mob.ccs.service.MobCcsOrderRemarkService;
import com.bomwebportal.mob.ccs.service.MobCcsPaymentUpfrontService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.sbs.SbsFunction;
import com.bomwebportal.sbs.dto.SbsContactInfoDTO;
import com.bomwebportal.sbs.dto.SbsDeliveryInfoDTO;
import com.bomwebportal.sbs.dto.StOrderPosSmDTO;
import com.bomwebportal.sbs.dto.StSubscribedItemDTO;
import com.bomwebportal.sbs.dto.form.SbsContactDeliveryForm;
import com.bomwebportal.sbs.service.SbsDeliveryService;
import com.bomwebportal.sbs.service.SbsOrderService;
import com.bomwebportal.service.CustomerProfileService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.web.util.ReportRepository;

@Controller
public class SbsOrderDetailController {

	@Autowired
	private CustomerProfileService customerProfileService;

	@Autowired
	private DeliveryService deliveryService;
	@Autowired
	private OrderService orderService;

	@Autowired
	private SbsOrderService sbsOrderService;
	@Autowired
	private SbsDeliveryService sbsDeliveryService;
	@Autowired
	private MobCcsPaymentUpfrontService mobCcsPaymentUpfrontService;
	@Autowired
	private MobCcsOrderRemarkService mobCcsOrderRemarkService;
	@Autowired
	private ReportRepository reportRepository;

	@ModelAttribute
	public void referenceData(String orderId, HttpServletRequest request, HttpSession session, Model model) {
		BomSalesUserDTO loginUserDto = (BomSalesUserDTO) session.getAttribute("bomsalesuser");

		OrderDTO orderDTO = orderService.getOrderWithPaidAmount(orderId);

		PaymentUpFrontUI paymentUpfront = mobCcsPaymentUpfrontService.getPaymentUpfront(orderId);
		BigDecimal upfrontAmount = BigDecimal.ZERO;
		List<? extends StSubscribedItemDTO> subscribedItems = sbsOrderService.getSubscribedItems(orderId);
		if (subscribedItems != null) {
			for (StSubscribedItemDTO si : subscribedItems) {
				upfrontAmount = (si.getOnetimeAmt() != null) ? upfrontAmount.add(si.getOnetimeAmt()) : upfrontAmount;
			}
		}
		List<StOrderPosSmDTO> posSms = sbsOrderService.getOrderPosSms(orderId);

		// List<OrderRemarkDTO> orderRemarkResultList =
		// this.mobCcsOrderRemarkService.getOrderRemarkDTOALL(orderId);

		OrderMobDTO orderMobDTO = orderService.getOrderMobDTO(orderId);

		HashMap<String, Boolean> functions = new HashMap<String, Boolean>();

		if (orderMobDTO == null || !"2".equals(orderMobDTO.getOrderSubType())) {
			Set<String> allowed = SbsFunction.allowedFunctions(loginUserDto, orderDTO.getOrderStatus(),
					orderDTO.getCheckPoint(), orderDTO.getReasonCd());
			for (String fn : allowed) {
				functions.put(fn, true);
			}
		}
		
		

		// boolean isFormExists = reportRepository.isSignedFromsExist(orderId);
		// for reportdownload.html
		MobCcsSessionUtil.setSession(request, "orderDTO", orderDTO);
		session.setAttribute("orderdetailOrderDto", orderDTO);

		model.addAttribute("order", orderDTO);
		model.addAttribute("payment", paymentUpfront);
		model.addAttribute("upfrontAmount", upfrontAmount);
		model.addAttribute("subscribedItems", subscribedItems);
		model.addAttribute("posSms", posSms);
		// model.addAttribute("orderRemarkResultList", orderRemarkResultList);
		// // loaded in showForm
		model.addAttribute("allowed", functions);
		model.addAttribute("orderMob", orderMobDTO);
		// model.addAttribute("isFormExists", isFormExists);

	}

	@ModelAttribute("sbsContactDeliveryInfo")
	public SbsContactDeliveryForm getContactDeliveryInfo(String orderId) {
		SbsContactDeliveryForm form = new SbsContactDeliveryForm();
		form.setOrderId(orderId);
		SbsContactInfoDTO contactInfo = sbsOrderService.getContactInfo(orderId);
		if (contactInfo != null) {
			form.setContactInfo(contactInfo);
		} else {
			form.setContactInfo(new SbsContactInfoDTO());
		}

		// null if no delivery info ... virtual kk...
		SbsDeliveryInfoDTO deliveryInfo = sbsOrderService.getDeliveryInfo(orderId);
		form.setDeliveryInfo(deliveryInfo);
		/*
		 * if (deliveryInfo != null) { form.setDeliveryInfo(deliveryInfo); }
		 * else { form.setDeliveryInfo(new SbsDeliveryInfoDTO()); }
		 */
		if (deliveryInfo != null && StringUtils.isNotEmpty(deliveryInfo.getDeliveryTimeSlot())) {
			String timeSlotDesc = sbsDeliveryService.getTimeSlotDesc(deliveryInfo.getDeliveryTimeSlot());
			deliveryInfo.setDeliveryTimeSlotDesc(timeSlotDesc);
		}
		return form;
	}

	@RequestMapping(value = "/sbsorderdetail", method = RequestMethod.GET)
	public String showForm(String orderId, HttpServletRequest request, HttpSession session, Model model) {
		BomSalesUserDTO loginUserDto = (BomSalesUserDTO) session.getAttribute("bomsalesuser");
		String user = loginUserDto.getUsername();
		String lastOrderId = (String) MobCcsSessionUtil.getSession(request, "lastOrderId");
		MobCcsSessionUtil.setSession(request, "lastOrderId", orderId);

		if (!StringUtils.equals(orderId, lastOrderId)) {
			sbsOrderService.insertOrderRemark(orderId, user + " ENQUIRY ORDER", user);
		}

		List<OrderRemarkDTO> orderRemarkResultList = this.mobCcsOrderRemarkService.getOrderRemarkDTOALL(orderId);
		model.addAttribute("orderRemarkResultList", orderRemarkResultList);
		


		// System.out.println(model.asMap());
		return "sbsorderdetail";
	}

	@RequestMapping(value = "/sbsorderdetail_closedialog", method = RequestMethod.GET)
	public String closeDialog() {

		return "sbsorderdetail_closedialog";
	}

}