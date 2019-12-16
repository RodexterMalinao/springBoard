package com.bomwebportal.ims.web;

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
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.EmailTemplateDTO;
import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.dto.ui.OrderEsignatureUI.Action;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dao.ImsEmailParamHelperDAO;
import com.bomwebportal.ims.dto.ui.AmendOrderImsUI;
import com.bomwebportal.ims.dto.ui.CCOrderSendEmailHistUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.CCSalesInfoService;
import com.bomwebportal.ims.service.ImsOLOrderSearchService;
import com.bomwebportal.ims.service.ImsOrderAmendService;
import com.bomwebportal.ims.service.ImsOrderService;
import com.bomwebportal.ims.service.ImsSMSService;
import com.bomwebportal.ims.service.ImsSignOffLogService;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.service.OrdEmailReqService;
import com.bomwebportal.service.OrderEsignatureService;
import com.bomwebportal.service.OrderEsignatureService.EmailReqResult;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.ConfigProperties;
import com.bomwebportal.util.Utility;
import com.google.gson.Gson;
import com.pccw.wq.schema.dto.WorkQueueDTO;

public class CCOrderSendEmailHistController extends SimpleFormController {
	private OrdEmailReqService ordEmailReqService;
	private OrderEsignatureService orderEsignatureService;
	private OrderRetrieveLtsService orderRetrieveLtsService;
	private ImsSMSService imsSMSService;
	private ImsOrderAmendService imsOrderAmendservice;
	private CCSalesInfoService ccsiService;
	private ImsOLOrderSearchService imsOlOrderSearchService;
	private ImsEmailParamHelperDAO imsEmailParamHelperDAO;
	private ImsSignOffLogService signOffLogService;
	
	private Gson gson = new Gson();
	
	public ImsOLOrderSearchService getImsOlOrderSearchService() {
		return imsOlOrderSearchService;
	}

	public void setImsOlOrderSearchService(
			ImsOLOrderSearchService imsOlOrderSearchService) {
		this.imsOlOrderSearchService = imsOlOrderSearchService;
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

	public void setImsOrderAmendservice(ImsOrderAmendService imsOrderAmendservice) {
		this.imsOrderAmendservice = imsOrderAmendservice;
	}

	public ImsOrderAmendService getImsOrderAmendservice() {
		return imsOrderAmendservice;
	}

	public ImsSMSService getImsSMSService() {
		return imsSMSService;
	}

	public void setImsSMSService(ImsSMSService imsSMSService) {
		this.imsSMSService = imsSMSService;
	}

	public CCSalesInfoService getCcsiService() {
		return ccsiService;
	}

	public void setCcsiService(CCSalesInfoService ccsiService) {
		this.ccsiService = ccsiService;
	}

	public void setOrderEsignatureService(
			OrderEsignatureService orderEsignatureService) {
		this.orderEsignatureService = orderEsignatureService;
	}
	
	private ImsOrderService orderservice;
	public ImsOrderService getOrderservice() {
		return orderservice;
	}

	public void setOrderservice(ImsOrderService orderservice) {
		this.orderservice = orderservice;
	}

	public ImsEmailParamHelperDAO getImsEmailParamHelperDAO() {
		return imsEmailParamHelperDAO;
	}

	public void setImsEmailParamHelperDAO(
			ImsEmailParamHelperDAO imsEmailParamHelperDAO) {
		this.imsEmailParamHelperDAO = imsEmailParamHelperDAO;
	}
	
	public void setSignOffLogService(ImsSignOffLogService signOffLogService) {
		this.signOffLogService = signOffLogService;
	}
	public ImsSignOffLogService getSignOffLogService() {
		return signOffLogService;
	}

	public Object formBackingObject(HttpServletRequest request) throws Exception {
		CCOrderSendEmailHistUI form = new CCOrderSendEmailHistUI();

		Action action = Action.SEARCH;
		try {
			action = Action.valueOf(request.getParameter("action"));
		} catch (Exception e) {}
		form.setAction(action);
		
		switch (action) {
		case PREVIEW:
			//form.setOrderId(ServletRequestUtils.getRequiredStringParameter(request, "orderId"));
			//form.setTemplateId(ServletRequestUtils.getRequiredStringParameter(request, "templateId"));
			//List<OrdEmailReqDTO> list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderIdIMS(form.getOrderId());
			BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
			form.setOrderId(request.getParameter("orderId"));
			form.setTemplateId(request.getParameter("templateId"));
			OrderImsUI imsOrder = new OrderImsUI();
			imsOrder.setOrderId(form.getOrderId());
			if(form.getOrderId().subSequence(0, 1).equals("P")){
				imsOrder.setIsPT("Y");
			}else if(form.getOrderId().subSequence(0, 1).equals("C")){
				imsOrder.setIsCC("Y");
			}
			imsOrder.setCreateBy(salesUserDto.getUsername());
			signOffLogService.signOffOrderLog(imsOrder, "ResendAF", null);
			break;
		case SEARCH:
			form.setLob("");
			form.setRequestDateStr(Utility.date2String(new Date(), BomWebPortalConstant.DATE_FORMAT));
			break;
		case SEND:
		default:
		}
		return form;
	}
	
	public Map<Object, Object> referenceData(HttpServletRequest request) throws Exception {
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		String PT = salesUserDto.getChannelId()==3?"Y":"N";
		Map<Object, Object> referenceData = new HashMap<Object, Object>();

		String pcdOrder = "Y";
		Action action = Action.SEARCH;
		try {
			action = Action.valueOf(request.getParameter("action"));
		} catch (Exception e) {}
		
		switch (action) {
		case PREVIEW:
			OrderImsUI order = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
			if(order==null){
				order = orderservice.getBomWebImsOrder(request.getParameter("orderId")); 
			}
			String orderId = request.getParameter("orderId");
			String templateId =  request.getParameter("templateId");
			List<OrdEmailReqDTO> list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderIdIMS(orderId, null);
			referenceData.put("list", getPreviewShowList(list));
			if (!isEmpty(list)) {
				OrdEmailReqDTO ordEmailReqDto = list.get(0);
				
				EmailTemplateDTO emailTemplateDto = new EmailTemplateDTO();
				EmailTemplateDTO SMSTemplateDto = new EmailTemplateDTO();
				
				String SMSInd = "Y";
				String templateIdE="";
				String templateIdS="";
				
				if(order.isOrderTypeNowRet()||(order.getOrderType() != null && !"".equals(order.getOrderType()) && "NTV-A".equals(order.getOrderType()))){
					pcdOrder = "N";
				}
				
				if(order.isOrderTypeNowRet()){
					String nowRetEmailTemp = getEmailSmsTemplateForNowRet(order.getOrderType(), order.getCreateByUser().getChannelId(),"E");
					String nowRetSMSTemp = getEmailSmsTemplateForNowRet(order.getOrderType(), order.getCreateByUser().getChannelId(),"S");
					emailTemplateDto = this.orderEsignatureService.getEmailTemplateDTO(nowRetEmailTemp, ordEmailReqDto.getLob(), ordEmailReqDto.getEsigEmailLang());
					SMSTemplateDto = this.orderEsignatureService.getEmailTemplateDTO(nowRetSMSTemp, ordEmailReqDto.getLob(), ordEmailReqDto.getEsigEmailLang());
					logger.debug("emailTemplateDto: " + gson.toJson(emailTemplateDto));
					logger.debug("SMSTemplateDto: " + gson.toJson(SMSTemplateDto));
					referenceData.put("emailTemplateDto", emailTemplateDto);
					try {						
						String SMSSubject = this.orderEsignatureService.getEmailSubject(SMSTemplateDto, ordEmailReqDto, null);
						String SMSContent = this.orderEsignatureService.getEmailContent(SMSTemplateDto, ordEmailReqDto, null);	
						logger.debug("SMSSubject: " + SMSSubject);
						logger.debug("SMSContent: " + SMSContent);
						referenceData.put("SMSSubject", SMSSubject);
						referenceData.put("SMSContent", SMSContent);
						String emailSubject = this.orderEsignatureService.getEmailSubject(emailTemplateDto, ordEmailReqDto, null);
						String emailContent = this.orderEsignatureService.getEmailContent(emailTemplateDto, ordEmailReqDto, null);	
						referenceData.put("emailSubject", emailSubject);
						referenceData.put("emailContent", emailContent);
						referenceData.put("SMSInd", "Y");
						referenceData.put("nowRet", "Y");
					} catch (Exception e) {
						logger.error("Exception during prepare email preview", e);
						referenceData.put("emailException", e);
					}
				}else{
					if(templateId.indexOf("I_")>=0){
						templateIdE = templateId.substring(templateId.indexOf("I_")+2, templateId.indexOf("I_")+7);
					}
					if(templateId.indexOf("S_")>=0){
						templateIdS = templateId.substring(templateId.indexOf("S_")+2, templateId.indexOf("S_")+7);
					}
				
					if("RT034".equalsIgnoreCase(templateIdE)){
						emailTemplateDto = this.orderEsignatureService.getEmailTemplateDTO("RT034", ordEmailReqDto.getLob(), ordEmailReqDto.getEsigEmailLang());
						SMSInd = "N";
					}else if("RT035".equalsIgnoreCase(templateIdE)){
						emailTemplateDto = this.orderEsignatureService.getEmailTemplateDTO("RT035", ordEmailReqDto.getLob(), ordEmailReqDto.getEsigEmailLang());
						SMSInd = "N";
					}else if (order.getOrderType() != null && !"".equals(order.getOrderType()) && "NTV-A".equals(order.getOrderType()) &&
							!"RT034".equalsIgnoreCase(templateIdE) && !"RT035".equalsIgnoreCase(templateIdE)) {
						//NTV-A case
						String mapStr = "";
						try{
							if (templateIdE!=null && !"".equals(templateIdE)) {
								mapStr = imsEmailParamHelperDAO.getDSTemplateMap(templateIdE.substring(0,2)+"0"+templateIdE.substring(3));
							} else {
								mapStr = imsEmailParamHelperDAO.getDSTemplateMap(templateIdS.substring(0,2)+"0"+templateIdS.substring(3));
							}
						}catch (Exception e){
							logger.error("mapStr templateId error",e);
							mapStr= imsEmailParamHelperDAO.getDSTemplateMap(templateId.substring(0,2)+"0"+templateId.substring(3));
						}
						templateIdE = imsEmailParamHelperDAO.getDSTemplateId(mapStr.substring(0,2), "NTV-A", "E", mapStr.substring(6));
						templateIdS = imsEmailParamHelperDAO.getDSTemplateId(mapStr.substring(0,2), "NTV-A", "S", mapStr.substring(6));
						templateIdE = templateIdE.substring(0,2)+"1"+templateIdE.substring(3);
						templateIdS = templateIdS.substring(0,2)+"1"+templateIdS.substring(3);
						emailTemplateDto = this.orderEsignatureService.getEmailTemplateDTO(templateIdE, ordEmailReqDto.getLob(), ordEmailReqDto.getEsigEmailLang());
						SMSTemplateDto = this.orderEsignatureService.getEmailTemplateDTO(templateIdS, ordEmailReqDto.getLob(), ordEmailReqDto.getEsigEmailLang());
					} else if ("Y".equals(PT)){ 
						emailTemplateDto = this.orderEsignatureService.getEmailTemplateDTO("RT113", ordEmailReqDto.getLob(), ordEmailReqDto.getEsigEmailLang());
						SMSTemplateDto = this.orderEsignatureService.getEmailTemplateDTO("RT115", ordEmailReqDto.getLob(), ordEmailReqDto.getEsigEmailLang());
					}else {
						emailTemplateDto = this.orderEsignatureService.getEmailTemplateDTO("RT112", ordEmailReqDto.getLob(), ordEmailReqDto.getEsigEmailLang());
						SMSTemplateDto = this.orderEsignatureService.getEmailTemplateDTO("RT114", ordEmailReqDto.getLob(), ordEmailReqDto.getEsigEmailLang());
					}
					
					referenceData.put("emailTemplateDto", emailTemplateDto);
					referenceData.put("SMSInd", SMSInd);
					
					try {
						String emailSubject = this.orderEsignatureService.getEmailSubject(emailTemplateDto, ordEmailReqDto, null);
						String emailContent = this.orderEsignatureService.getEmailContent(emailTemplateDto, ordEmailReqDto, null);
						
						String SMSSubject = "";
						String SMSContent = "";
						
						if("Y".equalsIgnoreCase(SMSInd)){
							SMSSubject = this.orderEsignatureService.getEmailSubject(SMSTemplateDto, ordEmailReqDto, null);
							SMSContent = this.orderEsignatureService.getEmailContent(SMSTemplateDto, ordEmailReqDto, null);
						}
						
						referenceData.put("emailSubject", emailSubject);
						referenceData.put("emailContent", emailContent);
						
						referenceData.put("SMSSubject", SMSSubject);
						referenceData.put("SMSContent", SMSContent);
						
						
					} catch (Exception e) {
						if (logger.isDebugEnabled()) {
							logger.debug("Exception during prepare email preview", e);
						}
						referenceData.put("emailException", e);
					}
				}
			}
			referenceData.put("pcdOrder", pcdOrder);
			List<String> tlist = new ArrayList<String>();
			tlist.add(orderId);
			tlist = (List<String>)(imsOrderAmendservice.getPrivilegedOrderIdList(tlist, salesUserDto, "IMS_ROLE_AF", "IMS_ROLE_AF_FUNC"));
			String SaleResendEmailAllowed = tlist!=null && tlist.size()>0?"Y":"N";
			referenceData.put("SaleResendEmailAllowed", SaleResendEmailAllowed);
			break;
		case SEND:
			// should be reached by POST method
			break;
		case SEARCH:
		default:
		}

		Map<String, String> teamCds = new LinkedHashMap<String, String>();		
		List<String> list = ccsiService.getCCManagerTeamCds(salesUserDto.getUsername());
		
		if ( list == null || list.size() == 0)
			teamCds.put(salesUserDto.getShopCd(), salesUserDto.getShopCd());
		else
		{
			for(String item:list)
			{
				if ( item != null && !"".equals(item))
					teamCds.put(item, item);
			}
		}
			
		referenceData.put("teamCds", teamCds);
		
		Map<String, String> lobs = new LinkedHashMap<String, String>();
		lobs.put("", "All");
		lobs.put("LTS", "LTS");
		lobs.put("IMS", "IMS");
		referenceData.put("lobs", lobs);
		
		logger.debug("referenceData:"+gson.toJson(referenceData));
		
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors) throws Exception {
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		ModelAndView modelAndView;
		CCOrderSendEmailHistUI form = (CCOrderSendEmailHistUI) command;

		String PT = salesUserDto.getChannelId()== 3?"Y":"N";
		
		switch (form.getAction()) {
		case SEARCH_SUBMIT:
			modelAndView = new ModelAndView("ccordersendemailhist", this.getModel(request, errors));
			Date requestDate = null;
			if (StringUtils.isNotBlank(form.getRequestDateStr())) {
				requestDate = Utility.string2Date(form.getRequestDateStr());
			}
			List<OrdEmailReqDTO> olist = this.ordEmailReqService.getOrdEmailReqDTOLTSIMSBySearch(form.getOrderId(), salesUserDto.getChannelId(), requestDate, "", form.getTeamCd());
			

			
			
			modelAndView.addObject("list", olist);
			for(OrdEmailReqDTO dto: olist){
				if(dto.getMethod()== DisMode.I){
					dto.setTemplateId("I_"+dto.getTemplateId());
				}else if (dto.getMethod()== DisMode.S){
					dto.setTemplateId("S_"+dto.getTemplateId());
				}else if (dto.getMethod()== DisMode.B){
					if(dto.getEmailAddr()!=null&&!dto.getEmailAddr().isEmpty()){
						dto.setTemplateId("I_"+dto.getTemplateId());
					}else{
						dto.setTemplateId("S_"+dto.getTemplateId());
					}
				}
			}
			modelAndView.addObject("list3", getSearchShowList(olist));
			
			break;
		case SEND:

			OrderImsUI order = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
			if(order==null){
				order = orderservice.getBomWebImsOrder(form.getOrderId());  
			}
			modelAndView = new ModelAndView(new RedirectView("ccordersendemailhist.html?dM=Y"));
			modelAndView.addObject("orderId", form.getOrderId());
			modelAndView.addObject("templateId", form.getTemplateId());
			modelAndView.addObject("action", Action.PREVIEW.toString());
			modelAndView.addObject("emailSendConfirm", form.isEmailSendConfirm());
						
			if(order.isOrderTypeNowRet()){
				String nowRetRmailTemp = getEmailSmsTemplateForNowRet(order.getOrderType(), order.getCreateByUser().getChannelId(),"E");
				String locale = RequestContextUtils.getLocale(request).toString();
				locale = locale!=null?locale.indexOf("en")!=-1?"ENG":"CHN":"ENG";
				String shortenUrl = imsSMSService.getShortenUrl(form.getOrderId());
				if (form.isEmailSendConfirm()) {
					List<OrdEmailReqDTO> list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderIdIMS(form.getOrderId(),null);
					if (!isEmpty(list)) {
						
						OrdEmailReqDTO ordEmailReqDto = list.get(list.size()-1);							
						try{
							String result = imsSMSService.sendNowRetSms(form.getSMSno(),nowRetRmailTemp, ordEmailReqDto.getOrderId(), locale, order.getSignOffDate(), shortenUrl);
							if(result.equals("Error")){
								modelAndView.addObject("result", "Cannot send out SMS.");
							}else{
								modelAndView.addObject("result", "SUCCESS");
							}
						}catch (Exception e){
							logger.error("Exception caught in sendNowRetSms()", e);
						}						
						List<OrdEmailReqDTO> latestList = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderIdIMS(form.getOrderId(),null);
						if (!isEmpty(latestList)) {
							modelAndView.addObject("sentDate", Utility.date2String(latestList.get(latestList.size() - 1).getSentDate(), BomWebPortalConstant.DATE_FORMAT + " HH:mm:ss"));
						}
					}
				}
			} else if ( form.getMethod() == DisMode.I){
				
				if (form.isEmailSendConfirm()) {
					String oldTemplateIdE = "";
					String oldTemplateIdS = "";
					String formTemplateId = form.getTemplateId();
					if(formTemplateId.indexOf("I_")>=0){
						oldTemplateIdE = formTemplateId.substring(formTemplateId.indexOf("I_")+2, formTemplateId.indexOf("I_")+7);
					}
					if(formTemplateId.indexOf("S_")>=0){
						oldTemplateIdS = formTemplateId.substring(formTemplateId.indexOf("S_")+2, formTemplateId.indexOf("S_")+7);
					}
					List<OrdEmailReqDTO> list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderIdIMS(form.getOrderId(),oldTemplateIdE);
					if (isEmpty(list) && "NTV-A".equals(order.getOrderType())) {
						list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderIdIMS(form.getOrderId(),oldTemplateIdS);
					}
					
					if (!isEmpty(list)) {
						
						String finaltempId = "";
						
						if("RT034".equalsIgnoreCase(oldTemplateIdE)||"RT035".equalsIgnoreCase(oldTemplateIdE)){
							finaltempId = form.getTemplateId();
						}else if (order.getOrderType()!=null && "NTV-A".equals(order.getOrderType())) {
							String mapStr = "";
							if (oldTemplateIdS!=null && !"".equals(oldTemplateIdS)) {
								mapStr = imsEmailParamHelperDAO.getDSTemplateMap(oldTemplateIdS.substring(0,2)+"0"+oldTemplateIdS.substring(3));
							} else {
								mapStr = imsEmailParamHelperDAO.getDSTemplateMap(oldTemplateIdE.substring(0,2)+"0"+oldTemplateIdE.substring(3));
							}
							String templateIdE = imsEmailParamHelperDAO.getDSTemplateId(mapStr.substring(0,2), "NTV-A", "E", mapStr.substring(6));
							templateIdE = templateIdE.substring(0,2)+"1"+templateIdE.substring(3);
							finaltempId = templateIdE; 
						}else if("Y".equals(PT)){
							finaltempId = "RT113";
						}else{
							finaltempId = "RT112";
						}
						
						OrdEmailReqDTO ordEmailReqDto = list.get(list.size()-1);
						EmailReqResult result = this.orderEsignatureService.resendEmailReqIMS(
								ordEmailReqDto.getOrderId()
								, finaltempId
								, ordEmailReqDto.getFilePathName1()
								, ordEmailReqDto.getFilePathName2()
								, ordEmailReqDto.getFilePathName3()
								, form.getEmailAddr()
								, salesUserDto.getUsername() 
								);
						modelAndView.addObject("result", result.toString());
						
						if (EmailReqResult.SUCCESS.equals(result)) 
						{
							List<OrdEmailReqDTO> latestList = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderIdIMS(form.getOrderId(),null);
							if (!isEmpty(latestList)) 
							{
								modelAndView.addObject("sentDate", Utility.date2String(latestList.get(latestList.size() - 1).getSentDate(), BomWebPortalConstant.DATE_FORMAT + " HH:mm:ss"));
							}
						}
					}
				}
			}else if ( form.getMethod() == DisMode.S )
			{
				if (form.isEmailSendConfirm()) {
					String oldTemplateIdE = "";
					String oldTemplateIdS = "";
					String formTemplateId = form.getTemplateId();
					if(formTemplateId.indexOf("I_")>=0){
						oldTemplateIdE = formTemplateId.substring(formTemplateId.indexOf("I_")+2, formTemplateId.indexOf("I_")+7);
					}
					if(formTemplateId.indexOf("S_")>=0){
						oldTemplateIdS = formTemplateId.substring(formTemplateId.indexOf("S_")+2, formTemplateId.indexOf("S_")+7);
					}
					List<OrdEmailReqDTO> list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderIdIMS(form.getOrderId(),oldTemplateIdS);
					if (isEmpty(list) && "NTV-A".equals(order.getOrderType())) {
						list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderIdIMS(form.getOrderId(),oldTemplateIdE);
					}
					
					if (!isEmpty(list)) {
						
						String finaltempId = "";
						
						if (order.getOrderType()!=null && "NTV-A".equals(order.getOrderType())) {
							String mapStr = "";
							if (oldTemplateIdE!=null &&!"".equals(oldTemplateIdE)) {
								mapStr = imsEmailParamHelperDAO.getDSTemplateMap(oldTemplateIdE.substring(0,2)+"0"+oldTemplateIdE.substring(3));
							} else {
								mapStr = imsEmailParamHelperDAO.getDSTemplateMap(oldTemplateIdS.substring(0,2)+"0"+oldTemplateIdS.substring(3));
							}
							String templateIdS = imsEmailParamHelperDAO.getDSTemplateId(mapStr.substring(0,2), "NTV-A", "S", mapStr.substring(6));
							templateIdS = templateIdS.substring(0,2)+"1"+templateIdS.substring(3);
							finaltempId = templateIdS;
						} else if ("Y".equals(PT)){
							finaltempId = "RT115";
						} else {
							finaltempId = "RT114";
						}
						
						OrdEmailReqDTO ordEmailReqDto = list.get(list.size()-1);
						EmailReqResult result = this.imsSMSService.resendSMSReq(ordEmailReqDto.getOrderId(), finaltempId
							, form.getSMSno(), salesUserDto.getUsername() );
						modelAndView.addObject("result", result.toString());
						
						if (EmailReqResult.SUCCESS.equals(result)) 
						{
							List<OrdEmailReqDTO> latestList = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderIdIMS(form.getOrderId(),null);
							if (!isEmpty(latestList)) {
								modelAndView.addObject("sentDate", Utility.date2String(latestList.get(latestList.size() - 1).getSentDate(), BomWebPortalConstant.DATE_FORMAT + " HH:mm:ss"));
							}
						}
					}
				}
			}else if ( form.getMethod() == DisMode.IS )
			{
				if (form.isEmailSendConfirm()) {
					
					String oldTemplateIdE = "";
					String oldTemplateIdS = "";

					String formTemplateId = form.getTemplateId();
					
					if(formTemplateId.indexOf("I_")>=0){
						oldTemplateIdE = formTemplateId.substring(formTemplateId.indexOf("I_")+2, formTemplateId.indexOf("I_")+7);
					}
					if(formTemplateId.indexOf("S_")>=0){
						oldTemplateIdS = formTemplateId.substring(formTemplateId.indexOf("S_")+2, formTemplateId.indexOf("S_")+7);
					}
					
					List<OrdEmailReqDTO> list = new ArrayList<OrdEmailReqDTO>();
					
					if(!oldTemplateIdE.isEmpty()){
						list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderIdIMS(form.getOrderId(),oldTemplateIdE);
					}else if(!oldTemplateIdS.isEmpty()) {
						list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderIdIMS(form.getOrderId(),oldTemplateIdS);
					}
					if (!isEmpty(list)) {
						OrdEmailReqDTO ordEmailReqDto = list.get(list.size()-1); 
						
						String finaltempIdE = "";
						
						if("RT034".equalsIgnoreCase(oldTemplateIdE)||"RT035".equalsIgnoreCase(oldTemplateIdE)){
							finaltempIdE = oldTemplateIdE;
						}else if (order.getOrderType()!=null && "NTV-A".equals(order.getOrderType())&&!oldTemplateIdE.isEmpty()) {//???
							String mapStr= imsEmailParamHelperDAO.getDSTemplateMap(oldTemplateIdE.substring(0,2)+"0"+oldTemplateIdE.substring(3));
							String templateIdE = imsEmailParamHelperDAO.getDSTemplateId(mapStr.substring(0,2), "NTV-A", "E", mapStr.substring(6));
							templateIdE = templateIdE.substring(0,2)+"1"+templateIdE.substring(3);
							finaltempIdE = templateIdE; 
						}else if("Y".equals(PT)){
							finaltempIdE = "RT113";
						}else{
							finaltempIdE = "RT112";
						}
						
						EmailReqResult result = this.orderEsignatureService.resendEmailReqIMS(
								ordEmailReqDto.getOrderId()
								, finaltempIdE
								, ordEmailReqDto.getFilePathName1()
								, ordEmailReqDto.getFilePathName2()
								, ordEmailReqDto.getFilePathName3()
								, form.getEmailAddr()
								, salesUserDto.getUsername() 
								);

						int smsSeqNo = 0;
						try {
							smsSeqNo = this.ordEmailReqService.getNextSeqIMS(form.getOrderId());
						} catch (Exception e) {
							logger.warn("Exception in insertOrdEmailReq", e);
							throw new AppRuntimeException(e == null ? "" : e.getMessage());
						} 
						String finaltempIdS = "";
						if (order.getOrderType()!=null && "NTV-A".equals(order.getOrderType())&&!oldTemplateIdS.isEmpty()) {//???
							String mapStr= imsEmailParamHelperDAO.getDSTemplateMap(oldTemplateIdS.substring(0,2)+"0"+oldTemplateIdS.substring(3));
							String templateIdS = imsEmailParamHelperDAO.getDSTemplateId(mapStr.substring(0,2), "NTV-A", "S", mapStr.substring(6));
							templateIdS = templateIdS.substring(0,2)+"1"+templateIdS.substring(3);
							finaltempIdS = templateIdS;
						} else if ("Y".equals(PT)){
							finaltempIdS = "RT115";
						} else {
							finaltempIdS = "RT114";
						}
						
						EmailReqResult result2 = this.imsSMSService.resendSMSReq(ordEmailReqDto.getOrderId(), finaltempIdS, form.getSMSno(), salesUserDto.getUsername(), smsSeqNo-1);
						
						
						if(result!=null){
							modelAndView.addObject("result", result.toString());
							if (EmailReqResult.SUCCESS.equals(result)) 
							{
								List<OrdEmailReqDTO> latestList = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderIdIMS(form.getOrderId(),null);
								if (!isEmpty(latestList)) 
									modelAndView.addObject("sentDate", Utility.date2String(latestList.get(latestList.size() - 1).getSentDate(), BomWebPortalConstant.DATE_FORMAT + " HH:mm:ss"));
							}
						}
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
							amend.setOrderImsUI(order);
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
			break;
		case SEARCH:
		case PREVIEW:
		default:
			modelAndView = new ModelAndView(new RedirectView("ccordersendemailhist.html"));
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
			if(t !=null){
				if(DisMode.I == t.getMethod() && DisMode.S == dto.getMethod()){
					t.setMethod(DisMode.IS);
					t.setSMSno(dto.getSMSno());
					t.setErrMsg((t.getSentDate()==null?(t.getErrMsg()==null?"":t.getErrMsg()):"Send on "+dt.format(t.getSentDate()))+System.getProperty("line.separator")+(dto.getSentDate()==null?(dto.getErrMsg()==null?"":dto.getErrMsg()):"Send on "+dt.format(dto.getSentDate())));
//					t.setErrMsg((t.getErrMsg()==null?"":t.getErrMsg())+"\n"+(dto.getErrMsg()==null?"":dto.getErrMsg()));
					t.setTemplateId(t.getTemplateId()+dto.getTemplateId());//Tony
					
					m.remove(dto.getSMSno());
					m.put(dto.getSeqNo(), t);
				} else if(DisMode.S == t.getMethod() && DisMode.I == dto.getMethod()){ 
					t.setMethod(DisMode.IS);
					t.setEmailAddr(dto.getEmailAddr());
					t.setErrMsg((dto.getSentDate()==null?(dto.getErrMsg()==null?"":dto.getErrMsg()):"Send on "+dt.format(dto.getSentDate()))+System.getProperty("line.separator")+(t.getSentDate()==null?(t.getErrMsg()==null?"":t.getErrMsg()):"Send on "+dt.format(t.getSentDate())));
//					t.setErrMsg((dto.getErrMsg()==null?"":dto.getErrMsg())+"\n"+(t.getErrMsg()==null?"":t.getErrMsg()));
					t.setTemplateId(t.getTemplateId()+dto.getTemplateId());//Tony
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
	

	private String getEmailSmsTemplateForNowRet(String orderType, int userChannelId, String emailOrSms){
		if("S".equals(emailOrSms) && userChannelId==1){
			return "";//Retail has no sms
		}
		if(userChannelId==3){
			userChannelId=2;//pt 3 use the same template with 2
		}
		String result="NR"+emailOrSms;
		/*
		if("NTVRE".equals(orderType)&&(userChannelId==12||userChannelId==13)){
			result+="R";
		}else{
			result+="U";			
		}
		*/
		result+="UO";
		result+=userChannelId;
		return result;
	}
}
