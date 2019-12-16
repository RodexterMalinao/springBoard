package com.bomwebportal.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.EmailTemplateDTO;
import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.OrderMobDTO;
import com.bomwebportal.dto.SubscriberDTO;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.bomwebportal.dto.ui.OrderEsignatureUI;
import com.bomwebportal.dto.ui.OrderEsignatureUI.Action;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ui.AmendOrderImsUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.ImsOrderAmendService;
import com.bomwebportal.ims.service.ImsOrderService;
import com.bomwebportal.ims.service.ImsSMSService;
import com.bomwebportal.dto.ui.OrderEsignatureUI.Action;
import com.bomwebportal.ims.service.ImsSignOffLogService;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.service.OrdEmailReqService;
import com.bomwebportal.service.OrderEsignatureService;
import com.bomwebportal.service.OrderEsignatureService.EmailReqResult;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.ConfigProperties;
import com.bomwebportal.util.Utility;
import com.google.gson.Gson;
import com.pccw.wq.schema.dto.WorkQueueDTO;

public class OrderSendEmailHistController extends SimpleFormController {
	private OrdEmailReqService ordEmailReqService;
	private OrderEsignatureService orderEsignatureService;
	private OrderRetrieveLtsService orderRetrieveLtsService;
	private ImsSMSService imsSMSService;
	private ImsOrderAmendService imsOrderAmendservice;
	private OrderService orderService;
	private ImsSignOffLogService signOffLogService;
	
	private Gson gson = new Gson();
	private ImsOrderService orderservice;
	public ImsOrderService getOrderservice() {
		return orderservice;
	}

	public void setOrderservice(ImsOrderService orderservice) {
		this.orderservice = orderservice;
	}
	
	public OrderRetrieveLtsService getOrderRetrieveLtsService() {
		return orderRetrieveLtsService;
	}

	public void setOrderRetrieveLtsService(
			OrderRetrieveLtsService orderRetrieveLtsService) {
		this.orderRetrieveLtsService = orderRetrieveLtsService;
	}

	public OrdEmailReqService getOrdEmailReqService() {
		return ordEmailReqService;
	}

	public void setOrdEmailReqService(OrdEmailReqService ordEmailReqService) {
		this.ordEmailReqService = ordEmailReqService;
	}

	public OrderEsignatureService getOrderEsignatureService() {
		return orderEsignatureService;
	}

	public void setOrderEsignatureService(
			OrderEsignatureService orderEsignatureService) {
		this.orderEsignatureService = orderEsignatureService;
	}
	
	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public ImsSMSService getImsSMSService() {
		return imsSMSService;
	}

	public void setImsSMSService(ImsSMSService imsSMSService) {
		this.imsSMSService = imsSMSService;
	}

	public void setImsOrderAmendservice(ImsOrderAmendService imsOrderAmendservice) {
		this.imsOrderAmendservice = imsOrderAmendservice;
	}

	public ImsOrderAmendService getImsOrderAmendservice() {
		return imsOrderAmendservice;
	}
	
	public void setSignOffLogService(ImsSignOffLogService signOffLogService) {
		this.signOffLogService = signOffLogService;
	}
	public ImsSignOffLogService getSignOffLogService() {
		return signOffLogService;
	}

	public Object formBackingObject(HttpServletRequest request) throws Exception {
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		OrderEsignatureUI form = new OrderEsignatureUI();
		Action action = Action.SEARCH;
		try {
			action = Action.valueOf(request.getParameter("action"));
		} catch (Exception e) {}
		form.setAction(action);
		switch (action) {
		case PREVIEW:
			form.setOrderId(ServletRequestUtils.getRequiredStringParameter(request, "orderId"));
			form.setTemplateId(ServletRequestUtils.getRequiredStringParameter(request, "templateId"));
			List<OrdEmailReqDTO> list = new ArrayList<OrdEmailReqDTO>();
			/*if (form.getOrderId().substring(0,1).equals("C") ){
				List<OrdEmailReqDTO> list= ordEmailReqService.getCCSOrdEmailReqDTOALLBySearch(form.getOrderId());
			}*/
			OrderDTO orderDto = orderService.getOrder(form.getOrderId());
			if (orderDto!=null && form.getOrderId().substring(0,1).equals("C")&& "MOB".equals(orderDto.getOrderSumLob())){
					 SubscriberDTO sub = orderService.getBomWebSub(form.getOrderId());
					if("00".equals(sub.getSmsLang())){
					list= ordEmailReqService.getCcsOrdEmailReqDTOALLByOrderId(form.getOrderId(),form.getTemplateId(),"CHN");
					}else{
					list= ordEmailReqService.getCcsOrdEmailReqDTOALLByOrderId(form.getOrderId(),form.getTemplateId(),"ENG");
					}
			}else{
			list= this.ordEmailReqService.getOrdEmailReqDTOALLByOrderId(form.getOrderId(), form.getTemplateId());
			}
			if (!isEmpty(list) && StringUtils.equals("IMS", list.get(0).getLob()) && form.getTemplateId().equalsIgnoreCase("RT001")){
				OrderImsUI imsOrder = new OrderImsUI();
				imsOrder.setOrderId(form.getOrderId());
				imsOrder.setCreateBy(salesUserDto.getUsername());
				signOffLogService.signOffOrderLog(imsOrder, "ResendAF", null);
				list = getSearchShowList(this.ordEmailReqService.getOrdEmailReqDTOALLByOrderId(form.getOrderId(), ""));
			}
			if (!isEmpty(list)) {
				
				form.setEmailAddr(list.get(0).getEsigEmailAddr());
				if(list.get(0).getSMSno()!=null&&!list.get(0).getSMSno().isEmpty()){
					form.setSMSno(list.get(0).getSMSno());
				}
				if (StringUtils.isEmpty(form.getEmailAddr())){
					form.setEmailAddr(list.get(0).getEmailAddr());
				}
				if (form.getTemplateId().equalsIgnoreCase("RT034")||form.getTemplateId().equalsIgnoreCase("RT035")){
					form.setEmailAddr(list.get(0).getEmailAddr());
				}
				if (form.getTemplateId().equalsIgnoreCase("RT018") && "MOB".equals(orderDto.getOrderSumLob())){
					String iGuardCareCashEmail = ConfigProperties.getPropertyByEnvironment("careCashIGuardEmailAddr");
					form.setEmailAddr(iGuardCareCashEmail);
				}
				if (form.getTemplateId().equalsIgnoreCase("RT003")){
					String iGuardemail = ConfigProperties.getPropertyByEnvironment("iGuardEmailAddr");
					form.setEmailAddr(iGuardemail);
				}else{
				form.setOrigEmailAddr(form.getEmailAddr());
				}
			}
			break;
		case SEARCH:
			form.setShopCd("TTW".equals(salesUserDto.getBomShopCd()) ? "" : salesUserDto.getBomShopCd());
			form.setLob("");
			form.setRequestDateStr(Utility.date2String(new Date(), BomWebPortalConstant.DATE_FORMAT));
			break;
		case SEND:
		default:
		}
		return form;
	}
	
	public Map referenceData(HttpServletRequest request) throws Exception {
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		Map<Object, Object> referenceData = new HashMap<Object, Object>();
		
		Action action = Action.SEARCH;
		try {
			action = Action.valueOf(request.getParameter("action"));
		} catch (Exception e) {}
		
		switch (action) {
		case PREVIEW:
			String orderId = ServletRequestUtils.getRequiredStringParameter(request, "orderId");
			String templateId = ServletRequestUtils.getRequiredStringParameter(request, "templateId");
			List<OrdEmailReqDTO> list = new ArrayList<OrdEmailReqDTO>();
			OrderDTO orderDto = orderService.getOrder(orderId);
			OrderImsUI imsOrder=null;
			if (StringUtils.equals("IMS", orderDto.getOrderSumLob())) {
				imsOrder = orderservice.getBomWebImsOrder(orderId); 
				logger.debug("referenceData:"+orderId+" templateId:"+templateId);					
			}			
			if (orderDto!=null && orderId.substring(0,1).equals("C") && "MOB".equals(orderDto.getOrderSumLob())){
					list = new ArrayList<OrdEmailReqDTO>();
					SubscriberDTO sub = orderService.getBomWebSub(orderId);
						if ("00".equals(sub.getSmsLang())) {
							list = ordEmailReqService.getCcsOrdEmailReqDTOALLByOrderId(orderId, templateId, "CHN");
						} else {
							list = ordEmailReqService.getCcsOrdEmailReqDTOALLByOrderId(orderId, templateId, "ENG");
						}
						if (list.get(0).getEsigEmailLang() == null) {
							if ("00".equals(sub.getSmsLang())) {
								list.get(0).setEsigEmailLang(EsigEmailLang.CHN);
							} else {
								list.get(0).setEsigEmailLang(EsigEmailLang.ENG);
							}
						}
						referenceData.put("list",list);
			}else if(StringUtils.equals("IMS", orderDto.getOrderSumLob())&&imsOrder!=null&&!imsOrder.isOrderTypeNowRet()){
				list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderId(orderId, "");
				referenceData.put("list", getSearchShowList(list));
			}else{
			list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderId(orderId, templateId);
			referenceData.put("list", list);
			}
			if (!isEmpty(list)) {
				OrdEmailReqDTO ordEmailReqDto = list.get(0);
//				EmailTemplateDTO emailTemplateDto = this.orderEsignatureService.getEmailTemplateDTO(ordEmailReqDto.getTemplateId(), ordEmailReqDto.getLob(), ordEmailReqDto.getEsigEmailLang());
				EmailTemplateDTO emailTemplateDto = null;
				if(imsOrder!=null&&imsOrder.isOrderTypeNowRet()){
					emailTemplateDto = this.orderEsignatureService.getEmailTemplateDTO("NREUR1", ordEmailReqDto.getLob(), ordEmailReqDto.getEsigEmailLang());
				} else if (imsOrder!=null&&"NTV-A".equals(imsOrder.getOrderType())) {
					for (int i=0; i<list.size(); i++) {
						if (list.get(i).getMethod().equals(DisMode.E) && 
								!list.get(i).getTemplateId().contains(ImsConstants.IMS_AMENTMENT_NOTIFICATION)) {
							ordEmailReqDto = list.get(i);
							break;
						}
					}
					String templateIdE = ordEmailReqDto.getTemplateId();
					templateIdE=templateIdE.substring(0,2)+"1"+templateIdE.substring(3);
					emailTemplateDto = this.orderEsignatureService.getEmailTemplateDTO(templateIdE, ordEmailReqDto.getLob(), ordEmailReqDto.getEsigEmailLang());
				} else{
					emailTemplateDto = this.orderEsignatureService.getEmailTemplateDTO(ordEmailReqDto.getTemplateId(), ordEmailReqDto.getLob(), ordEmailReqDto.getEsigEmailLang());
				}
				referenceData.put("emailTemplateDto", emailTemplateDto);
				
				SbOrderDTO ltsSbOrder = null;
				
				if (StringUtils.equals(LtsConstant.LOB_LTS, ordEmailReqDto.getLob())) {
					ltsSbOrder = orderRetrieveLtsService.retrieveSbOrder(ordEmailReqDto.getOrderId(), true);
					referenceData.put("isLtsOnlineAcqOrder", LtsSbHelper.isOnlineAcquistionOrder(ltsSbOrder));
				}
				
				
				if (StringUtils.equals("MOB", ordEmailReqDto.getLob())) {
					String orderBrand = orderService.getOrderBrand(orderId);
					if ("0".equals(orderBrand)){
						ordEmailReqDto.setOrderBrand("1010");
					}else{
						ordEmailReqDto.setOrderBrand("csl");
					}
					referenceData.put("isMobOrder",true);
					referenceData.put("ccsOrderId",orderId);
				}
				
				String SMSSubject = "";
				String SMSContent = "";
				
				String SMSInd = "N";
				
				if (StringUtils.equals("IMS", ordEmailReqDto.getLob())) {
					EmailTemplateDTO SMSTemplateDto = new EmailTemplateDTO();
					SMSTemplateDto = this.orderEsignatureService.getEmailTemplateDTO("RT046", ordEmailReqDto.getLob(), ordEmailReqDto.getEsigEmailLang());
					
					if(!"RT034".equalsIgnoreCase(ordEmailReqDto.getTemplateId())&&imsOrder!=null&&!imsOrder.isOrderTypeNowRet()){
						SMSInd = "Y";
					}
					if ("NTV-A".equals(imsOrder.getOrderType())) {
						String templateIdS = "";
						SMSInd = "N";
						for (int i = 0; i < list.size(); i++) {
							if (list.get(i).getMethod().equals(DisMode.S) && !"RT034".equalsIgnoreCase(ordEmailReqDto.getTemplateId()) && 
									!list.get(i).getTemplateId().contains(ImsConstants.IMS_AMENTMENT_NOTIFICATION)) {
								SMSInd = "Y";
								templateIdS = list.get(i).getTemplateId();
								break;
							}
						}
						if (templateIdS!=null&&!"".equals(templateIdS)) {
							SMSTemplateDto = this.orderEsignatureService.getEmailTemplateDTO(templateIdS, ordEmailReqDto.getLob(), ordEmailReqDto.getEsigEmailLang());
						}
					}
					
					try {
						SMSSubject = this.orderEsignatureService.getEmailSubject(SMSTemplateDto, ordEmailReqDto, null);
						SMSContent = this.orderEsignatureService.getEmailContent(SMSTemplateDto, ordEmailReqDto, null);
						referenceData.put("SMSSubject", SMSSubject);
						referenceData.put("SMSContent", SMSContent);
					} catch (Exception e) {
						if (logger.isDebugEnabled()) {
							logger.debug("Exception during prepare email preview", e);
						}
						referenceData.put("emailException", e);
					}
				}

				referenceData.put("SMSInd", SMSInd);
				referenceData.put("LOB", ordEmailReqDto.getLob());
				
				
				try {
					String emailSubject = this.orderEsignatureService.getEmailSubject(emailTemplateDto, ordEmailReqDto, ltsSbOrder);
					String emailContent = this.orderEsignatureService.getEmailContent(emailTemplateDto, ordEmailReqDto, ltsSbOrder);
					referenceData.put("emailSubject", emailSubject);
					referenceData.put("emailContent", emailContent);
				} catch (Exception e) {
					if (logger.isDebugEnabled()) {
						logger.debug("Exception during prepare email preview", e);
					}
					referenceData.put("emailException", e);
				}
			}
			break;
		/*case SEARCH_SUBMIT:
			orderId = request.getParameter("orderId");
			if (StringUtils.isNotEmpty(orderId) && orderId.substring(0,1).equals("C")){
				List<OrdEmailReqDTO>  ordEmailReqDTO= ordEmailReqService.getCCSOrdEmailReqDTOALLBySearch(orderId);
			referenceData.put("list",ordEmailReqDTO);
			}
			else if (StringUtils.isNotEmpty(orderId) && !orderId.substring(0,1).equals("C")){
				List<OrdEmailReqDTO>  ordEmailReqDTO=ordEmailReqService.getOrdEmailReqDTOALLBySearch(orderId,null,null,null,null);
				referenceData.put("list",ordEmailReqDTO);
			}
			break;*/
		case SEND:
			// should be reached by POST method
			break;
		case SEARCH:
		default:
		}

		Map<String, String> shopCds = new LinkedHashMap<String, String>();
		if ("TTW".equals(salesUserDto.getBomShopCd())) {
			shopCds.put("", "All");
		}
		shopCds.put(salesUserDto.getBomShopCd(), salesUserDto.getBomShopCd());
		referenceData.put("shopCds", shopCds);
		
		Map<String, String> lobs = new LinkedHashMap<String, String>();
		lobs.put("", "All");
		lobs.put("MOB", "MOB");
		lobs.put("LTS", "LTS");
		lobs.put("IMS", "IMS");
		referenceData.put("lobs", lobs);
		
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors) throws Exception {
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		ModelAndView modelAndView;
		OrderEsignatureUI form = (OrderEsignatureUI) command;

		switch (form.getAction()) {
		case SEARCH_SUBMIT:
			modelAndView = new ModelAndView("ordersendemailhist", this.getModel(request, errors));
			Date requestDate = null;
			if (StringUtils.isNotBlank(form.getRequestDateStr())) {
				requestDate = Utility.string2Date(form.getRequestDateStr());
			}
			if(form.getReset().equalsIgnoreCase("Y"))
				modelAndView.addObject("list", new ArrayList<OrdEmailReqDTO>());
			else{
				List<OrdEmailReqDTO> list = this.ordEmailReqService.getOrdEmailReqDTOALLBySearch(form.getOrderId(), form.getShopCd(), form.getLob(), requestDate, form.getTemplateId(),form.getOrderType());
				modelAndView.addObject("list", getSearchShowList(list));
			}
			break;
		case SEND:
			OrderDTO orderDto = orderService.getOrder(form.getOrderId());
			OrderImsUI imsOrder = null;
			if("IMS".equals(orderDto.getOrderSumLob())){
				imsOrder = orderservice.getBomWebImsOrder(form.getOrderId()); 
			}
			modelAndView = new ModelAndView(new RedirectView("ordersendemailhist.html"));
			modelAndView.addObject("orderId", form.getOrderId());
			modelAndView.addObject("templateId", form.getTemplateId());
			modelAndView.addObject("action", Action.PREVIEW.toString());
			modelAndView.addObject("emailSendConfirm", form.isEmailSendConfirm());
			List<OrdEmailReqDTO> list = new ArrayList<OrdEmailReqDTO>();
			if (form.isEmailSendConfirm()) {
				if(imsOrder!=null&&imsOrder.isOrderTypeNowRet()){
					list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderId(form.getOrderId(), form.getTemplateId());
					if (!isEmpty(list)) {
						OrdEmailReqDTO ordEmailReqDto = list.get(0);
						EmailReqResult result = this.orderEsignatureService.createEmailReq(ordEmailReqDto.getOrderId(), "NREUR1"
								, new Date(), ordEmailReqDto.getFilePathName1(), ordEmailReqDto.getFilePathName2(), ordEmailReqDto.getFilePathName3()
								, form.getEmailAddr(), salesUserDto.getUsername());
						modelAndView.addObject("result", result.toString());
					}
				} else {
				if (form.getOrderId().substring(0,1).equals("C") && "MOB".equals(orderDto.getOrderSumLob())){
					list = new ArrayList<OrdEmailReqDTO>();
					SubscriberDTO sub = orderService.getBomWebSub(form.getOrderId());
						if ("00".equals(sub.getSmsLang())) {
							list = ordEmailReqService.getCcsOrdEmailReqDTOALLByOrderId(form.getOrderId(), form.getTemplateId(), "CHN");
						} else {
							list = ordEmailReqService.getCcsOrdEmailReqDTOALLByOrderId(form.getOrderId(), form.getTemplateId(), "ENG");
						}
						if (list.get(0).getEsigEmailLang() == null) {
							if ("00".equals(sub.getSmsLang())) {
								list.get(0).setEsigEmailLang(EsigEmailLang.CHN);
							} else {
								list.get(0).setEsigEmailLang(EsigEmailLang.ENG);
							}
						}
				}else{
				 list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderId(form.getOrderId(), form.getTemplateId());
				}
				if (!isEmpty(list)) {
					if (form.getTemplateId().equalsIgnoreCase("RT003")){
						OrdEmailReqDTO ordEmailReqDto = list.get(0);
						EmailReqResult result = this.orderEsignatureService.createEmailReq(ordEmailReqDto.getOrderId(), ordEmailReqDto.getTemplateId()
								, new Date(), ordEmailReqDto.getFilePathName1(), ordEmailReqDto.getFilePathName2(), ordEmailReqDto.getFilePathName3()
								, form.getEmailAddr(), salesUserDto.getUsername());
						modelAndView.addObject("result", result.toString());
						
						if (EmailReqResult.SUCCESS.equals(result)) {
							List<OrdEmailReqDTO> latestList = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderId(form.getOrderId(), form.getTemplateId());
							if (!isEmpty(latestList)) {
								modelAndView.addObject("sentDate", Utility.date2String(latestList.get(latestList.size() - 1).getSentDate(), BomWebPortalConstant.DATE_FORMAT + " HH:mm:ss"));
							}
						}
					}else{
						OrdEmailReqDTO ordEmailReqDto = list.get(0);
						EmailReqResult result = null;
						if (StringUtils.equals("IMS", ordEmailReqDto.getLob()) && imsOrder != null && "NTV-A".equals(imsOrder.getOrderType())) {
							OrdEmailReqDTO ntvOrdEmailReqDto = null;
							for (int i=0; i<list.size(); i++) {
								if (list.get(i).getMethod().equals(DisMode.E) && 
										!list.get(i).getTemplateId().contains(ImsConstants.IMS_AMENTMENT_NOTIFICATION)) {
									ntvOrdEmailReqDto = list.get(i);
									break;
								}
							}
							String templateIdE = "";
							if (ntvOrdEmailReqDto == null) {
								templateIdE = ordEmailReqDto.getTemplateId();
							} else {
								templateIdE = ntvOrdEmailReqDto.getTemplateId();
								templateIdE = templateIdE.substring(0,2)+"1"+templateIdE.substring(3);
							}
							
							result = this.orderEsignatureService.resendEmailReq(ordEmailReqDto.getOrderId(), templateIdE
									, ordEmailReqDto.getFilePathName1(), ordEmailReqDto.getFilePathName2(), ordEmailReqDto.getFilePathName3()
									, form.getEmailAddr(), salesUserDto.getUsername());
						} else {
							result = this.orderEsignatureService.resendEmailReq(ordEmailReqDto.getOrderId(), ordEmailReqDto.getTemplateId()
									, ordEmailReqDto.getFilePathName1(), ordEmailReqDto.getFilePathName2(), ordEmailReqDto.getFilePathName3()
									, form.getEmailAddr(), salesUserDto.getUsername());
						}
						modelAndView.addObject("result", result.toString());
						
						if (EmailReqResult.SUCCESS.equals(result)) {
							List<OrdEmailReqDTO> latestList = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderId(form.getOrderId(), form.getTemplateId());
							if (!isEmpty(latestList)) {
								modelAndView.addObject("sentDate", Utility.date2String(latestList.get(latestList.size() - 1).getSentDate(), BomWebPortalConstant.DATE_FORMAT + " HH:mm:ss"));
							}
						}
						
						if(StringUtils.equals("IMS", ordEmailReqDto.getLob())&&!"RT034".equalsIgnoreCase(ordEmailReqDto.getTemplateId())&&imsOrder!=null&&!imsOrder.isOrderTypeNowRet()){
							int smsSeqNo = 0;
							try {
								smsSeqNo = this.ordEmailReqService.getNextSeqIMS(form.getOrderId());
							} catch (Exception e) {
								logger.warn("Exception in insertOrdEmailReq", e);
								throw new AppRuntimeException(e == null ? "" : e.getMessage());
							} 
							EmailReqResult result2 = this.imsSMSService.resendSMSReq(ordEmailReqDto.getOrderId(), "RT046", form.getSMSno(), salesUserDto.getUsername(), smsSeqNo-1);

							if(result2!=null){
								modelAndView.addObject("result2", result2.toString());
								
								if (EmailReqResult.SUCCESS.equals(result2)) 
								{
									List<OrdEmailReqDTO> latestList = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderIdIMS(form.getOrderId(),null);
									if (!isEmpty(latestList)) 
										modelAndView.addObject("sentDate", Utility.date2String(latestList.get(latestList.size() - 1).getSentDate(), BomWebPortalConstant.DATE_FORMAT + " HH:mm:ss"));					
								}
							}
							
							if("Y".equalsIgnoreCase(form.getUpdateBOMInd())){
							
								AmendOrderImsUI amend = new AmendOrderImsUI();
								
								amend.setIsAppointmentEnabled("N");
								amend.setIsCancelOrderEnabled("N");
								amend.setIsChangeProgOfferEnabled("N");
								amend.setIsFsAmendEnabled("N");
								amend.setIsContactEmailEnabled("Y");
								amend.setContactEmail(form.getEmailAddr());
								amend.setIsContactMobileEnabled("Y");
								amend.setContactMobile(form.getSMSno());
								amend.setOrderImsUI(imsOrder);
								amend.setCreatedBy(salesUserDto.getUsername());
								amend.setBomSalesUserDTO(salesUserDto);
								amend.setBomSRD(null);
								amend.printAmend();
								imsOrderAmendservice.insertBomwebAmendCategory(amend, imsOrderAmendservice.createWqCombinedCoverSheetRemark(amend, true), "N"); 
								
								List<WorkQueueDTO> remarkForAmendInsert= null;
								try {
									remarkForAmendInsert=imsOrderAmendservice.imsAmendWorkQueue(amend);
								}catch (Exception e) {
									try {
										imsOrderAmendservice.updateBomwebAmendCategoryMarkX(amend);
									} catch (DAOException e1) {
										logger.error("updateBomwebAmendCategoryMarkX  error"+ExceptionUtils.getFullStackTrace(e1));	
									}			
									throw new AppRuntimeException("", new Exception("create WQ failed, please retry, if retry fail, please report to helpdesk"));
								}
								
								try {
									imsOrderAmendservice.updateBomwebAmendCategory(amend);
								} catch (DAOException e1) {
									logger.error("updateBomwebAmendCategory  error"+ExceptionUtils.getFullStackTrace(e1));			
								}
							}
							
						}
					}
				}
			}
		}
			break;
		case SEARCH:
		case PREVIEW:
		default:
			modelAndView = new ModelAndView(new RedirectView("ordersendemailhist.html"));
		}
		
		if (StringUtils.isNotBlank(request.getParameter("hideToolbar"))) {
			modelAndView.addObject("hideToolbar", request.getParameter("hideToolbar"));
		}
		return modelAndView;
	}
	
	private Map getModel(HttpServletRequest request, BindException errors) throws Exception {
		Map model = errors.getModel();
		model.putAll(referenceData(request));
		return model;
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	private List<OrdEmailReqDTO> getPreviewShowList(List<OrdEmailReqDTO> list){

		List<OrdEmailReqDTO> rtnList = new ArrayList<OrdEmailReqDTO>();
		Map<Integer, OrdEmailReqDTO> m = new HashMap<Integer, OrdEmailReqDTO>();		
		SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
		for(OrdEmailReqDTO dto: list){
			OrdEmailReqDTO t = m.get(dto.getSeqNo());
			if(t !=null && StringUtils.equals("IMS", t.getLob())){
				if(DisMode.E == t.getMethod() && DisMode.S == dto.getMethod()){
					t.setMethod(DisMode.E);
					t.setSMSno(dto.getSMSno());
					t.setErrMsg((t.getSentDate()==null?(t.getErrMsg()==null?"":t.getErrMsg()):"Send on "+dt.format(t.getSentDate()))+System.getProperty("line.separator")+(dto.getSentDate()==null?(dto.getErrMsg()==null?"":dto.getErrMsg()):"Send on "+dt.format(dto.getSentDate())));
//					t.setErrMsg((t.getErrMsg()==null?"":t.getErrMsg())+"\n"+(dto.getErrMsg()==null?"":dto.getErrMsg()));
					t.setTemplateId(t.getTemplateId());//Tony
					
					m.remove(dto.getSMSno());
					m.put(dto.getSeqNo(), t);
				} else if(DisMode.S == t.getMethod() && DisMode.E == dto.getMethod()){ 
					t.setMethod(DisMode.E);
					t.setEmailAddr(dto.getEmailAddr());
					t.setErrMsg((dto.getSentDate()==null?(dto.getErrMsg()==null?"":dto.getErrMsg()):"Send on "+dt.format(dto.getSentDate()))+System.getProperty("line.separator")+(t.getSentDate()==null?(t.getErrMsg()==null?"":t.getErrMsg()):"Send on "+dt.format(t.getSentDate())));
//					t.setErrMsg((dto.getErrMsg()==null?"":dto.getErrMsg())+"\n"+(t.getErrMsg()==null?"":t.getErrMsg()));
					t.setTemplateId(dto.getTemplateId());//Tony
					m.remove(dto.getSMSno());
					m.put(dto.getSeqNo(), t);
				} else {
					continue; 
				}
			} else {
				try {
					OrdEmailReqDTO tmp = (OrdEmailReqDTO) BeanUtils.cloneBean(dto);
					rtnList.add(tmp); 
					m.put(dto.getSeqNo(), tmp);
				} catch (Exception e) {
					//do nth
				} 
			}
		}	
		return rtnList;
	}
	
	private List<OrdEmailReqDTO> getSearchShowList(List<OrdEmailReqDTO> list){
		List<OrdEmailReqDTO> rtnList = new ArrayList<OrdEmailReqDTO>();
		List<OrdEmailReqDTO> tmp = new ArrayList<OrdEmailReqDTO>();
	    String lastOrderId = null;
	    logger.info("list " + new Gson().toJson(list));
		for(OrdEmailReqDTO dto: list){
			if (list.indexOf(dto)==0){
				if (list.size()==1)
					return list;
				else {
				lastOrderId = dto.getOrderId();				
				tmp.add(dto);				
				}
			}
			else if (dto.getOrderId().equals(lastOrderId)){
				tmp.add(dto);
				if (list.indexOf(dto)+1 == list.size())			
				rtnList.addAll(getPreviewShowList(tmp));
			}
			else 				
			{   
				if (list.indexOf(dto)!= list.size())
				{
				lastOrderId = dto.getOrderId();
				rtnList.addAll(getPreviewShowList(tmp));				
				tmp.clear();
				tmp.add(dto);}
							
			}
		}			
		return rtnList;
	}
	
}
