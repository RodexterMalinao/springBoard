package com.bomwebportal.mob.ds.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.DepositDTO;
import com.bomwebportal.dto.IssueBankDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.ui.DepositUI;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.mob.ds.dto.MobDsPaymentTransDTO;
import com.bomwebportal.mob.ds.dto.MobDsPaymentUpfrontDTO;
import com.bomwebportal.service.PaymentService;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class MobDsPaymentUpfrontController extends SimpleFormController{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private PaymentService service;
	private VasDetailService vasService;
	
	public PaymentService getService() {
		return service;
	}

	public void setService(PaymentService service) {
		this.service = service;
	}

	public VasDetailService getVasService(){
		return vasService;
	}
	
	public void setVasService(VasDetailService vasService) {
		this.vasService = vasService;
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		
		BasketDTO basketDTO = (BasketDTO) MobCcsSessionUtil.getSession(request,	"basket");
		String [] selectedItemList = (String [])request.getSession().getAttribute("selectedVasItemList");
		MobDsPaymentUpfrontDTO currentPaymentUpfrontDTO = (MobDsPaymentUpfrontDTO) request.getSession().getAttribute("currentPaymentUpfront");
		DepositUI depositUI = (DepositUI) MobCcsSessionUtil.getSession(request, "depositInfo");
		MobileSimInfoDTO mobileSimInfo = (MobileSimInfoDTO) request.getSession().getAttribute("MobileSimInfo");
		List<DepositDTO> depositList = depositUI.getDepositDTOList();
		if (currentPaymentUpfrontDTO != null) {
			currentPaymentUpfrontDTO.setCeksSubmit("");
			request.getSession().removeAttribute("currentPaymentUpfront");
			return currentPaymentUpfrontDTO;
		} else {
			MobDsPaymentUpfrontDTO paymentDTO = (MobDsPaymentUpfrontDTO) request.getSession().getAttribute("paymentUpfront");
			OrderDTO orderDto = (OrderDTO) request.getSession().getAttribute("orderDtoOriginal");
			Date appDate = new Date();
			if (orderDto != null) {
				appDate = orderDto.getAppInDate();
			}
			
			
			
			double totalUpfrontAmt = Double.parseDouble(basketDTO.getPrePaymentAmt()) 
					+ Double.parseDouble(basketDTO.getUpfrontAmt()) 
					+ vasService.getVasAmt(selectedItemList, Utility.date2String(appDate, BomWebPortalConstant.DATE_FORMAT))
					+ (Double)computeDepositAmount(depositList).doubleValue();
			double totalHsUpfrontAmt = Double.parseDouble(basketDTO.getUpfrontAmt())
					+ vasService.getVasHSAmt(selectedItemList, Utility.date2String(appDate, BomWebPortalConstant.DATE_FORMAT));
			
			if (mobileSimInfo != null 
					&& (mobileSimInfo.getSimWaiveReason() == null ||  mobileSimInfo.getSimWaiveReason().length() == 0)) {
				totalUpfrontAmt += mobileSimInfo.getSimCharge();
			}
			
			if (mobileSimInfo != null && (StringUtils.isNotEmpty(mobileSimInfo.getRpWaiveReason()))) {
				logger.info("mobileSimInfo.getRpCharge(): "+mobileSimInfo.getRpCharge());
				totalUpfrontAmt -= mobileSimInfo.getRpCharge();
			}
			
			if (paymentDTO == null) {
				paymentDTO = new MobDsPaymentUpfrontDTO();				
				List<MobDsPaymentTransDTO> paymentTransList = new ArrayList<MobDsPaymentTransDTO>();
				paymentDTO.setPaymentTransList(paymentTransList);
				paymentDTO.setTotalPaidAmount(0);
			} 
			paymentDTO.setHsUpfrontAmount(totalHsUpfrontAmt);
			paymentDTO.setTotalUpfrontAmount(totalUpfrontAmt);
			return paymentDTO;
		}
		
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException {
		String nextView = (String) request.getAttribute("nextView");
		MobDsPaymentUpfrontDTO paymentUpfront = (MobDsPaymentUpfrontDTO) command;
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		CustomerProfileDTO customerProfile = (CustomerProfileDTO)request.getSession().getAttribute("customer");
		String actionType = paymentUpfront.getActionType();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String currentDateStr = sdf.format(Calendar.getInstance().getTime());
		
		if ("ceksSubmit".equalsIgnoreCase(actionType) && paymentUpfront != null && !"".equals(paymentUpfront.getCeksSubmit().trim())) {
			String uid=request.getParameter("sbuid");
			paymentUpfront.setUid(uid);//set uid
			
			request.getSession().setAttribute("currentPaymentUpfront", paymentUpfront);

			//for UAT/PRD
			String sCeksUrl = service.initialCeksAccess(user.getUsername(), request);
			request.getSession().setAttribute("sCeksUrl", sCeksUrl);
			nextView = sCeksUrl;
			
			// for local testing on UAT (hardcode)
			//nextView = "ceks.html?v1=&v2=&v3=&v5=411111AAAABm1111"; 
		} else if ("AddCash".equalsIgnoreCase(actionType)) {
			MobDsPaymentTransDTO dummyCashPaymentTrans = new MobDsPaymentTransDTO();
			dummyCashPaymentTrans.setPaymentAmount(0);
			dummyCashPaymentTrans.setPaymentType("M");
			dummyCashPaymentTrans.setTransDate(currentDateStr);
			if ("SIS".equals(user.getChannelCd())) {
				dummyCashPaymentTrans.setStoreCd(user.getAreaCd());
			}
			paymentUpfront.getPaymentTransList().add(dummyCashPaymentTrans);
			
			request.getSession().setAttribute("paymentUpfront", paymentUpfront);
			nextView = "mobdspaymentupfront.html";
		} else if ("AddCC".equalsIgnoreCase(actionType)) {
			MobDsPaymentTransDTO dummyCCPaymentTrans = new MobDsPaymentTransDTO();
			dummyCCPaymentTrans.setPaymentAmount(0);
			dummyCCPaymentTrans.setPaymentType("C");
			dummyCCPaymentTrans.setTransDate(currentDateStr);
			dummyCCPaymentTrans.setCcHolderName(customerProfile.getCustLastName()+" "+customerProfile.getCustFirstName());
			if ("SIS".equals(user.getChannelCd())) {
				dummyCCPaymentTrans.setStoreCd(user.getAreaCd());
			}
			paymentUpfront.getPaymentTransList().add(dummyCCPaymentTrans);
			
			request.getSession().setAttribute("paymentUpfront", paymentUpfront);
			nextView = "mobdspaymentupfront.html";
		} else if ("AddCCInstallment".equalsIgnoreCase(actionType)) {
			MobDsPaymentTransDTO dummyCCInstallmentPaymentTrans = new MobDsPaymentTransDTO();
			dummyCCInstallmentPaymentTrans.setPaymentAmount(0);
			dummyCCInstallmentPaymentTrans.setPaymentType("I");
			dummyCCInstallmentPaymentTrans.setTransDate(currentDateStr);
			dummyCCInstallmentPaymentTrans.setCcHolderName(customerProfile.getCustLastName()+" "+customerProfile.getCustFirstName());
			if ("SIS".equals(user.getChannelCd())) {
				dummyCCInstallmentPaymentTrans.setStoreCd(user.getAreaCd());
			}
			paymentUpfront.getPaymentTransList().add(dummyCCInstallmentPaymentTrans);
			
			request.getSession().setAttribute("paymentUpfront", paymentUpfront);
			nextView = "mobdspaymentupfront.html";
		} else if ("DELETEROW".equalsIgnoreCase(actionType)) {
			try {
				int row = Integer.parseInt(paymentUpfront.getCeksSubmit());
				paymentUpfront.getPaymentTransList().remove(row);
				paymentUpfront.setCeksSubmit("");
				request.getSession().setAttribute("paymentUpfront", paymentUpfront);
				nextView = "mobdspaymentupfront.html";
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		} else if ("AMEND".equalsIgnoreCase(actionType)) {
			request.getSession().setAttribute("paymentUpfront", paymentUpfront);
			nextView = "mobilesiminfo.html";
		}else {
			request.getSession().setAttribute("paymentUpfront", paymentUpfront);
		}

		String attrUid=(String)request.getParameter("sbuid");
		ModelAndView modelAndView =  new ModelAndView(new RedirectView(nextView));
		modelAndView.addObject("sbuid", attrUid);
		
		return modelAndView;
	}
	
	protected Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		List<IssueBankDTO> issueBankList  = service.getIssueBankList();
		referenceData.put("issueBankList", issueBankList);
		
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		String salesId = user.getUsername();
		OrderDTO orderDto = (OrderDTO) request.getSession().getAttribute("orderDtoOriginal");
		Date appDate = new Date();
		if (orderDto != null) {
			salesId = orderDto.getSalesCd();
			appDate = orderDto.getAppInDate();
		}
		String salesChannelCd = service.getSalesChannelCd(salesId, appDate);
		referenceData.put("salesChannelCd", salesChannelCd);
		//referenceData.put("totalUpfrontAmount", totalUpfrontAmt);
		//referenceData.put("hsUpfrontAmount", totalHsUpfrontAmt);
		
		return referenceData;
	}
	
	private BigDecimal computeDepositAmount(List<DepositDTO> depositList) {
		BigDecimal amount = BigDecimal.ZERO;
		if (depositList == null) 
			return amount;
		
		for (DepositDTO deposit: depositList) {
			BigDecimal itemAmt = deposit.getDepositAmount();
			if (!"Y".equals(deposit.getWaiveInd())) {
				amount = amount.add(itemAmt);
			}
		}
		return amount;
	}
	/*
	private double calculateTotalPaid(List<MobDsPaymentTransDTO> payment) {
		double totalPaid = 0;
		for (int i = 0; i < payment.size(); i++) {
			totalPaid += payment.get(i).getPaymentAmount();
		}
		return totalPaid;
	} */
}
