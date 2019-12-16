package com.bomwebportal.web;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dao.BomSubscriberDAO;
import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.OrderHsrmLogDTO;
import com.bomwebportal.dto.ResultDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.service.OrderHsrmService;
import com.bomwebportal.service.QueueRefService;
import com.bomwebportal.service.VasDetailService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hkcsl.hsrm.service.SQPCOrderDetailResponse;
import com.hkcsl.hsrm.service.SQPCService;

@Controller
public class QueueRefController {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private QueueRefService queueRefService;
	
	@Autowired
	private SQPCService sqpcService;
	
	@Autowired
	private OrderHsrmService orderHsrmService;
	
	@Autowired
	private BomSubscriberDAO bomSubscriberDAO;
	
	private VasDetailService vasDetailService;
	
	public QueueRefService getQueueRefService() {
		return queueRefService;
	}


	public void setQueueRefService(QueueRefService queueRefService) {
		this.queueRefService = queueRefService;
	}


	public SQPCService getSqpcService() {
		return sqpcService;
	}


	public void setSqpcService(SQPCService sqpcService) {
		this.sqpcService = sqpcService;
	}

	public OrderHsrmService getOrderHsrmService() {
		return orderHsrmService;
	}


	public void setOrderHsrmService(OrderHsrmService orderHsrmService) {
		this.orderHsrmService = orderHsrmService;
	}
	
	public VasDetailService getVasDetailService() {
		return vasDetailService;
	}


	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}


	@RequestMapping(value="/queueRef/validQueueRef", method=RequestMethod.POST)
	public String validQueueRef(String orderId, String channelId, String appDateStr, String itemCode, String docType, String docId, String msisdn, String basketBrand ,String components, Model model) {
		try {
			Date convertedDate = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			convertedDate = dateFormat.parse(appDateStr);
			
			Type listType = new TypeToken<ArrayList<ComponentDTO>>() {}.getType();
			List<ComponentDTO> componentList = new Gson().fromJson(components, listType);
			
			String error = "N";
			String errMsg = null;
			
			if (components != null && componentList.size() > 0) {
				
				List<CodeLkupDTO> couponComponentList= LookupTableBean.getInstance().getCodeLkupList().get("COUPON_ATTB");
				if (CollectionUtils.isNotEmpty(couponComponentList)) {
					for (CodeLkupDTO dto: couponComponentList) {
						for (ComponentDTO compo : componentList){
							if (compo != null && compo.getCompAttbId() != null && compo.getCompAttbId().equalsIgnoreCase(dto.getCodeId())) {
								if ("BS".equals(docType) && !StringUtil.isBlank(docId) && !"2".equals(channelId)) {
									if (!msisdn.equals(compo.getCompAttbVal())) {
										int result = bomSubscriberDAO.getCouponAccountInfo(compo.getCompAttbVal(), docType, docId);
										if (result < 1) {
											model.addAttribute("couponErrMsg", "Coupon SMS No. [" + compo.getCompAttbVal() + "] is not matched with HKBR " + docId);
											model.addAttribute("error", "C");
											return "ajax_service";
										}
									}
								}
							}
						}
					}
				}
				
				List<CodeLkupDTO> preReigstrationComponentList= LookupTableBean.getInstance().getCodeLkupList().get("PRJ_7_ATTB");
				if (CollectionUtils.isNotEmpty(preReigstrationComponentList)){
					for (CodeLkupDTO dto: preReigstrationComponentList) {
						for (ComponentDTO compo : componentList){
							if (compo!=null && compo.getCompAttbId()!= null && compo.getCompAttbId().equalsIgnoreCase(dto.getCodeId())) {
								//Matched with pre-defined VIP number pattern?													
								//Matched with pre-defined Walk-in number pattern?							
								//Validation Pre-registration number status from HSRM
								OrderHsrmLogDTO existOrderConfirmedLogForOrder= null;
								OrderHsrmLogDTO existOrderCompletedLogForOrder= null;
								if ("2".equals(channelId) &&  StringUtils.isNotEmpty(orderId)){
									existOrderConfirmedLogForOrder = orderHsrmService.getOrderConfirmedHsrmLog(orderId);
								}
								
								if ( (!"2".equals(channelId)) &&  StringUtils.isNotEmpty(orderId)) {
									existOrderCompletedLogForOrder = orderHsrmService.getOrderCompletedHsrmLog(orderId);
								}
								
								//Matched with pre-defined VIP number pattern?													
								//Matched with pre-defined Walk-in number pattern?							
								//Validation Pre-registration number status from HSRM
								if ("2".equals(channelId) && existOrderConfirmedLogForOrder != null ){
									if (compo.getCompAttbVal()!=null && !compo.getCompAttbVal().equals(existOrderConfirmedLogForOrder.getRefId())){
										model.addAttribute("errMsg", "Pre-Registration Number ("+existOrderConfirmedLogForOrder.getRefId()+") has been attached to order. Not allowed to change.");
										model.addAttribute("error", "Y");
										return "ajax_service";
									}else if (!itemCode.equalsIgnoreCase(existOrderConfirmedLogForOrder.getItemCode())){
										String handsetDescription=vasDetailService.getHandsetDescriptionByItemCd(existOrderConfirmedLogForOrder.getItemCode());
										model.addAttribute("errMsg", "Hero Product Item Code :("+handsetDescription +") has been attached to order. Not allowed to change.");
										model.addAttribute("error", "Y");
										return "ajax_service";
									}
								}else if ( (!"2".equals(channelId)) && existOrderCompletedLogForOrder != null ){
									if (compo.getCompAttbVal()!=null && !compo.getCompAttbVal().equals(existOrderCompletedLogForOrder.getRefId())){
										model.addAttribute("errMsg", "Pre-Registration Number ("+existOrderCompletedLogForOrder.getRefId()+") has been attached to order and is in Completed Status. Not allowed to change.");
										model.addAttribute("error", "Y");
										return "ajax_service";
										
									}
								}else if (queueRefService.validateVipNumberPattern(compo.getCompAttbVal())){
									
								}else if (queueRefService.validateWalkInNumberPattern(compo.getCompAttbVal()) ){
									//verify Walk-in number plus item code bypass control
																	
									if (!queueRefService.verifyWalkInByPassControl(itemCode,compo.getCompAttbVal(),convertedDate)){
										model.addAttribute("errMsg", "This hero product CANNOT offer to walk-in customer");
										model.addAttribute("error", "Y");
										return "ajax_service";
									}
			
								}else{
									
									SQPCOrderDetailResponse sqpcOrder = null;
									
									try {
										sqpcOrder = sqpcService.getOrderDetailByRef(compo.getCompAttbVal());
									} catch (Exception e) {
										model.addAttribute("errMsg", "System cannot call \"HSRM Pre-reg number validation\" temporary. Please click \"Exit\" to proceed your order.");
										model.addAttribute("error", "W");
										return "ajax_service";
									}
									
									if (sqpcOrder == null || "-1".equals(sqpcOrder.getRefNo())){
										if (sqpcOrder == null){
											model.addAttribute("errMsg", "Pre-registration Number not found.");
											model.addAttribute("error", "Y");
										}else{
											if ("Pre-registration Number not found.".equalsIgnoreCase(sqpcOrder.getMessage())){
												model.addAttribute("errMsg", sqpcOrder.getMessage());
												model.addAttribute("error", "Y");
											}else{
												model.addAttribute("error", "W");
												if ("Exception found.".equalsIgnoreCase(sqpcOrder.getMessage())){
													model.addAttribute("errMsg", "System cannot call \"HSRM Pre-reg number validation\" temporary. Please click \"Exit\" to proceed your order.");
												}else{
													model.addAttribute("errMsg", sqpcOrder.getMessage());
												}
											}
											
										}
										
										return "ajax_service";
									}else{
										ResultDTO valiadteResult = queueRefService.validatePreRegStatusFromHSRM(sqpcOrder, compo.getCompAttbVal(), docId,basketBrand);
										if (!valiadteResult.getReturnBool()){
											model.addAttribute("errMsg",null);
											model.addAttribute("error", "Y");
											if (StringUtils.isEmpty(sqpcOrder.getMessage())){
												sqpcOrder.setMessage(valiadteResult.getReturnMessage());
											}
											
											if ("Brand not match".equalsIgnoreCase(valiadteResult.getReturnMessage())){
												model.addAttribute("error", "M");
												sqpcOrder.setMessage("Alert : Brand 1010 pre-registration number should not subscribed csl. basket !");
											}
											
											if ("Stock allocated status".equalsIgnoreCase(valiadteResult.getReturnMessage())){
												model.addAttribute("error", "M");
												sqpcOrder.setMessage("Alert: Pre-registration number status : "+ sqpcOrder.getQueueStatus() + ", invalid queue status !");
											}
											model.addAttribute("message", sqpcOrder.getMessage());
											model.addAttribute("refNo", sqpcOrder.getRefNo());
											model.addAttribute("docId", sqpcOrder.getDocId());
											model.addAttribute("status", sqpcOrder.getQueueStatus());
											return "ajax_service";
										}
									}
								}
							}
						}
					}
				}
			}
			
			model.addAttribute("error", error);
			
		} catch (Exception e) {
			model.addAttribute("error", "Y");
			model.addAttribute("errorMsg", e.getMessage());
		}
		return "ajax_service";
	}

}
