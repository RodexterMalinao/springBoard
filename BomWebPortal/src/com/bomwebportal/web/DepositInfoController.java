package com.bomwebportal.web;

//Created on Oct 15: to add deposit controller 

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.DepositDTO;
import com.bomwebportal.dto.DepositLkupDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.WaiveLkupDTO;
import com.bomwebportal.dto.ui.DepositUI;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.UserTimeoutException;
import com.bomwebportal.mob.ccs.dto.ui.PaymentMonthyUI;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.DepositService;
import com.bomwebportal.service.WaiveService;
import com.bomwebportal.util.Utility;

public class DepositInfoController extends SimpleFormController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	// to declare instances for DepositService
	private DepositService depositService;

	public DepositService getDepositService() {
		return depositService;
	}

	public void setDepositService(DepositService depositService) {
		this.depositService = depositService;
	}

	// to declare instances for WaiveService
	private WaiveService waiveService;

	public WaiveService getWaiveService() {
		return waiveService;
	}

	public void setWaiveService(WaiveService waiveService) {
		this.waiveService = waiveService;
	}

	@Override
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException, UserTimeoutException {
		Date appDate = getAppDate(request);

		String paymentMethod = getPaymentMethod(request);
		boolean studentPlanSubInd = getStudentPlanSubInd(request);
		boolean autowaiveCustomer = autoSystemWaiveIddRoamingCustomer(request,studentPlanSubInd);
		boolean autopayWaiveCustomer= autoPayWaiveIddRoamingCustomer(request);
		
		// to get the depositDTOList from the session, if not exists, create a
		// new one
		
		DepositUI depositUI = (DepositUI) MobCcsSessionUtil.getSession(request, "depositInfo");
		if (depositUI == null) {

			depositUI = new DepositUI();
		}
		
		List<DepositLkupDTO> requiredDeposits = getRequiredDepositItems(request);
		List<DepositDTO> prevList = depositUI.getDepositDTOList();
		List<DepositDTO> newList = new ArrayList<DepositDTO>();
		for (DepositLkupDTO lkup: requiredDeposits) {
			DepositDTO depositDto = null;
			if (prevList != null) {
				for (DepositDTO dto : prevList) {
					if (lkup.getDepositId().equals(dto.getDepositId())) {
						depositDto = dto;
						break;
					}
				}
			}
			if (depositDto == null) {
				depositDto = createDepositDTO(lkup, paymentMethod, studentPlanSubInd, autowaiveCustomer,autopayWaiveCustomer);
			}
			
			refereshAutopayWaiveData(depositDto, paymentMethod, studentPlanSubInd, autowaiveCustomer,autopayWaiveCustomer);
			
			newList.add(depositDto);
		}
		depositUI.setDepositDTOList(newList);
		



		// save the list DepositDTO into the session with the name
		// DepositDTOList
		//request.getSession().setAttribute("depositDTOList", depositDTOList);
		return depositUI;
	}



	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		String nextView = (String) request.getAttribute("nextView");
		logger.info("Next View: " + nextView);

		DepositUI depositUI = (DepositUI) command;
		MobCcsSessionUtil.setSession(request, "depositInfo", depositUI);
		Date appDate = getAppDate(request);

		List<WaiveLkupDTO> waiveReasonList = waiveService
				.findWaiveLkupByReasonType("WAIVE_DEP", appDate);
		
		updateDepositWaiveData(depositUI.getDepositDTOList(), waiveReasonList);


		ModelAndView modelAndView = new ModelAndView(
				new RedirectView(nextView));

		return modelAndView;
	}

	@Override
	public Map referenceData(HttpServletRequest request) throws Exception {


		
		// obtain the date (see if it exists)
		Date appDate = getAppDate(request);
		
		Map referenceData = new HashMap<String, List<Object>>();
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		//String channelId = String.valueOf(user.getChannelId());
		referenceData.put("user", user);

		List<WaiveLkupDTO> waiveReasonList = waiveService
				.findWaiveLkupByReasonType("WAIVE_DEP", appDate);
		
		OrderDTO orderDTO = getSessionOrderDTO(request);
		
		referenceData.put("orderDTO", orderDTO);
		referenceData.put("waiveReasons", waiveReasonList);
		return referenceData;
	}
	
	private OrderDTO getSessionOrderDTO(HttpServletRequest req) {
		
		OrderDTO orderDTO = (OrderDTO)req.getSession().getAttribute("OrderDTO");
		
		return orderDTO != null ? orderDTO : (OrderDTO)MobCcsSessionUtil.getSession(req, "orderDTO");

	}

	private Date getAppDate(HttpServletRequest request) {
		Date appDate = null;
		if (request.getSession().getAttribute("OrderDTO") instanceof OrderDTO) {
			appDate = ((OrderDTO) request.getSession().getAttribute("OrderDTO")).getAppInDate();
		} else if (MobCcsSessionUtil.getSession(request, "orderDTO") instanceof OrderDTO) {
			appDate = ((OrderDTO)MobCcsSessionUtil.getSession(request, "orderDTO")).getAppInDate();
		}
		if (appDate == null) appDate = new Date();
		
		return appDate;
	}

	private String getPaymentMethod(HttpServletRequest request) {
		String paymentType = "";
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");

		/*
		int channelId=user.getChannelId();
		if (channelId == 1) {
			PaymentDTO paymentDto = (PaymentDTO)request.getSession().getAttribute("payment");
			paymentType = paymentDto.getPayMethodType();
		} else if (channelId == 2) {
			PaymentMonthyUI paymentMonthly = (PaymentMonthyUI) MobCcsSessionUtil.getSession(request, "paymentMonthy");
			paymentType = paymentMonthly.getPayMethodType();
		}
		*/
		
		PaymentMonthyUI paymentMonthly = (PaymentMonthyUI) MobCcsSessionUtil.getSession(request, "paymentMonthy");
		PaymentDTO paymentDto = (PaymentDTO)request.getSession().getAttribute("payment");

		if (paymentMonthly != null) {
			paymentType = paymentMonthly.getPayMethodType();
		} else if (paymentDto != null) {
			paymentType = paymentDto.getPayMethodType();
		}
		
		return paymentType;
	}
	
	
	private boolean getStudentPlanSubInd(HttpServletRequest request) {
		String appMode = (String) request.getSession().getAttribute("appMode");
		// two session for mob/mobccs
		CustomerProfileDTO sessionCustomer = (CustomerProfileDTO) request
				.getSession().getAttribute("customer");

		if ("mobccs".equals(appMode)) {
			sessionCustomer = (CustomerProfileDTO) MobCcsSessionUtil
					.getSession(request, "customer");
		}
		/*BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		
		CustomerProfileDTO sessionCustomer = null;
		if (user.getChannelId() == 2) {
			sessionCustomer = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
		} else {
			sessionCustomer = (CustomerProfileDTO) request.getSession().getAttribute("customer");
		}			*/
		
		return sessionCustomer.isStudentPlanSubInd();
	}
	
	private List<DepositLkupDTO> getRequiredDepositItems(HttpServletRequest request) {

		Date appDate = getAppDate(request);
		List<DepositLkupDTO> depositLkupList = new ArrayList<DepositLkupDTO>();
		
		// idDocType and addrProofInd from customerprofile
		// to handle the case of DEP0007 and DEP0001
		CustomerProfileDTO customer = (CustomerProfileDTO) request.getSession()
				.getAttribute("customer");
		if (customer == null) {
			customer = (CustomerProfileDTO)MobCcsSessionUtil.getSession(request, "customer");
		}
		if (customer != null) {
			if ("PASS".equals(customer.getIdDocType())) {
				List<DepositLkupDTO> lkupList1 = depositService
						.findDepositLkupByDepositCd("DEP0007", appDate);
				if (lkupList1 != null && lkupList1.size() > 0) {
					//DepositDTO dto = createDepositDTO(lkupList1.get(0));
					depositLkupList.add(lkupList1.get(0));
					//depositUI.getDepositLkupList().add(lkupList1.get(0));	
				}	
			}
		
			
			if ("0".equals(customer.getAddrProofInd())) {
				List<DepositLkupDTO> lkupList2 = depositService
						.findDepositLkupByDepositCd("DEP0001", appDate);
				if (lkupList2 != null && lkupList2.size() > 0) {
					depositLkupList.add(lkupList2.get(0));
					//DepositDTO dto2 = createDepositDTO(lkupList2.get(0));
					//depositUI.getDepositDTOList().add(dto2);
					//depositUI.getDepositLkupList().add(lkupList2.get(0));
				
			}

		}
		}
		// for the product-related handling
		// obtain basketId
		BasketDTO basketDTO = (BasketDTO) MobCcsSessionUtil.getSession(request,
				"basket");
		String basketId = basketDTO.getBasketId();
		// obtain selectedItemList
		String selectedItemList[] = (String[]) request.getSession()
				.getAttribute("selectedVasItemList");
		List<DepositLkupDTO> prodDepositLkupList = depositService
				.findDepositLkupByItemIds(basketId, appDate,
						selectedItemList, customer.getBrand(), customer.getSimType());
		if (prodDepositLkupList != null) {
			depositLkupList.addAll(prodDepositLkupList);
		}
		return depositLkupList;
	}
	
	
	// define the method createDepositDTO
	private DepositDTO createDepositDTO(DepositLkupDTO lkup, String paymentMethod, boolean studentPlanSubInd, boolean autowaiveCustomer, boolean autopayWaiveCustomer) {
		

		
		DepositDTO dto = new DepositDTO();
		// copy from lkup to dto
		dto.setDepositId(lkup.getDepositId());
		dto.setDepositAmount(lkup.getDepositAmount());
		dto.setDepositCd(lkup.getDepositCd());
		dto.setDepositDesc(lkup.getDepositDesc());
		dto.setItemCd(lkup.getItemCd());
		dto.setWaivable(lkup.getWaivable());
		dto.setWaiveOnAutopay(lkup.getWaiveOnAutopay());
		dto.setDepositLevel(lkup.getDepositLevel());
		/*
		if ("Y".equals(dto.getWaivable()) 
				&& "Y".equals(dto.getWaiveOnAutopay())
				&& "C".equals(paymentMethod)) {
			dto.setWaiveInd("Y");
			dto.setReasonCd("DEP0090");
		}
		*/
		refereshAutopayWaiveData(dto, paymentMethod, studentPlanSubInd, autowaiveCustomer,autopayWaiveCustomer);
		
		return dto;
	}	
	
	private DepositDTO refereshAutopayWaiveData(DepositDTO dto, String paymentMethod, boolean studentPlanSubInd,  boolean autowaiveCustomer, boolean autopayWaiveCustomer) {	
		
		if ("C".equals(paymentMethod)) {
			if ("Y".equals(dto.getWaivable()) 
					&& "Y".equals(dto.getWaiveOnAutopay())) {
				dto.setWaiveInd("Y");
				dto.setReasonCd("DEP0090");
			}
		} else {
			if ("Y".equals(dto.getWaiveInd()) && "DEP0090".equals(dto.getReasonCd())) {
				dto.setWaiveInd("N");
				dto.setReasonCd(null);
				dto.setReasonDesc(null);
			}
		}
		
		if (autowaiveCustomer) {
			if ("DEP0002".equals(dto.getDepositCd()) || "DEP0003".equals(dto.getDepositCd())) {
				dto.setWaiveInd("Y");
				dto.setReasonCd("DEP0091");
			}
		}else if (autopayWaiveCustomer) {
			
		}else{
			if ("DEP0002".equals(dto.getDepositCd()) || "DEP0003".equals(dto.getDepositCd())) {
				dto.setWaiveInd("N");
				dto.setReasonCd(null);
			}
		}
		
		//Student Plan - Deposit Waive Address Proof
		if ("DEP0001".equals(dto.getDepositCd())){
			if (studentPlanSubInd){
				//if ("Y".equals(dto.getWaivable())) {
					dto.setWaiveInd("Y");
					dto.setReasonCd("DEP0095");
				//}
			}else{
				if ("Y".equals(dto.getWaiveInd()) && "DEP0095".equals(dto.getReasonCd())) {
					dto.setWaiveInd("N");
					dto.setReasonCd(null);
					dto.setReasonDesc(null);
				}
			}
		}
		
		return dto;
	}
	
	private void updateDepositWaiveData(List<DepositDTO> depositList, List<WaiveLkupDTO> waiveReasons) {
		if (depositList == null || waiveReasons == null) return;
		
		for (DepositDTO deposit: depositList) {
			
			String reasonCd = deposit.getReasonCd();
			if (StringUtils.isEmpty(reasonCd)) {
				deposit.setWaiveInd("N");
				deposit.setReasonDesc(null);
			} else {
				deposit.setWaiveInd("Y");
				deposit.setReasonDesc(reasonCd);
				for (WaiveLkupDTO waive: waiveReasons) {
					if (reasonCd.equals(waive.getReasonCd())) {
						deposit.setReasonDesc(waive.getReasonDesc());
						break;
					}
				}
			}
		}
		
	}
	
	private boolean autoSystemWaiveIddRoamingCustomer(HttpServletRequest request, boolean studentPlanSubInd) {
		boolean autowaiveCustomer = false;

		CustomerProfileDTO customer = (CustomerProfileDTO) request.getSession().getAttribute("customer");
		if (customer == null) {
			customer = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
		}
		if (("HKID".equals(customer.getIdDocType()) && !customer.getIdDocNum().startsWith("W")) 
				|| ("BS".equals(customer.getIdDocType())) || studentPlanSubInd) {
			autowaiveCustomer = true;
		}
		return autowaiveCustomer;
	}
	
	private boolean autoPayWaiveIddRoamingCustomer(HttpServletRequest request) {
		boolean autoPaywaiveCustomer = false;

		CustomerProfileDTO customer = (CustomerProfileDTO) request.getSession().getAttribute("customer");
		PaymentMonthyUI paymentMonthly = (PaymentMonthyUI) MobCcsSessionUtil.getSession(request, "paymentMonthy");
		PaymentDTO paymentDto = (PaymentDTO)request.getSession().getAttribute("payment");
		if (customer == null) {
			customer = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
		}
		if (paymentMonthly !=null){
			if ("HKID".equals(customer.getIdDocType()) && customer.getIdDocNum().startsWith("W") && "C".equalsIgnoreCase(paymentMonthly.getPayMethodType()) && "N".equalsIgnoreCase(paymentMonthly.getThirdPartyInd()) ) {
				autoPaywaiveCustomer = true;
			}
		}
		
		if (paymentDto != null){
			if ("HKID".equals(customer.getIdDocType()) && customer.getIdDocNum().startsWith("W") && "C".equalsIgnoreCase(paymentDto.getPayMethodType()) && "N".equalsIgnoreCase(paymentDto.getThirdPartyInd()) ) {
				autoPaywaiveCustomer = true;
			}
		}
		
		return autoPaywaiveCustomer;
	}
	
}
