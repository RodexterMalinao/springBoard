package com.bomwebportal.ims.web;


import java.lang.reflect.InvocationTargetException;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.bind.ServletRequestUtils;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.EmailTemplateDTO;
import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.bomwebportal.dto.ui.OrderEsignatureUI.Action;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dao.ImsEmailParamHelperDAO;
import com.bomwebportal.ims.dto.ui.AmendOrderImsUI;
import com.bomwebportal.ims.dto.ui.DSOrderSendEmailHistUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.ImsOrderAmendService;
import com.bomwebportal.ims.service.ImsOrderService;
import com.bomwebportal.ims.service.ImsSMSService;
import com.bomwebportal.ims.service.ImsSignOffLogService;
import com.bomwebportal.service.OrdEmailReqService;
import com.bomwebportal.service.OrderEsignatureService;
import com.bomwebportal.service.OrderEsignatureService.EmailReqResult;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.ConfigProperties;
import com.bomwebportal.util.Utility;
import com.bomwebportal.util.uENC;
import com.google.gson.Gson;
import com.pccw.wq.schema.dto.WorkQueueDTO;
public class DSOrderSendEmailHistController extends SimpleFormController {
	private OrdEmailReqService ordEmailReqService;
	private OrderEsignatureService orderEsignatureService;
	private ImsSMSService imsSMSService;
	private ImsOrderAmendService imsOrderAmendservice;
	private ImsOrderService orderservice;
	private ImsSignOffLogService signOffLogService;
	public ImsOrderService getOrderservice() {
		return orderservice;
	}

	public void setOrderservice(ImsOrderService orderservice) {
		this.orderservice = orderservice;
	}


	private ImsEmailParamHelperDAO imsEmailParamHelperDAO;
	
	public ImsEmailParamHelperDAO getImsEmailParamHelperDAO() {
		return imsEmailParamHelperDAO;
	}

	public void setImsEmailParamHelperDAO(
			ImsEmailParamHelperDAO imsEmailParamHelperDAO) {
		this.imsEmailParamHelperDAO = imsEmailParamHelperDAO;
	}


	private Gson gson = new Gson();

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
	
	public void setSignOffLogService(ImsSignOffLogService signOffLogService) {
		this.signOffLogService = signOffLogService;
	}
	public ImsSignOffLogService getSignOffLogService() {
		return signOffLogService;
	}


	public Object formBackingObject(HttpServletRequest request) throws Exception {
		DSOrderSendEmailHistUI form = new DSOrderSendEmailHistUI();

		Action action = Action.SEARCH;
		try {
			action = Action.valueOf(request.getParameter("action"));
		} catch (Exception e) {}
		form.setAction(action);
		
		switch (action) {
		case PREVIEW:
			form.setOrderId(ServletRequestUtils.getRequiredStringParameter(request, "orderId"));
			List<OrdEmailReqDTO> list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderIdIMS(form.getOrderId(),null);
			form.setEmailAddr(request.getParameter("emailAddr"));
			form.setSMSno(request.getParameter("SMSno"));
			form.setTemplateId(request.getParameter("templateId"));
			
			BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
			OrderImsUI imsOrder = new OrderImsUI();
			imsOrder.setOrderId(form.getOrderId());
			imsOrder.setImsOrderType("DS");
			imsOrder.setCreateBy(salesUserDto.getUsername());
			signOffLogService.signOffOrderLog(imsOrder, "ResendAF", null);
            
			int seq;
			try {
				seq = Integer.parseInt(request.getParameter("seqNo"));
			} catch (Exception e) {
				seq = 1;
			}
			form.setSeqNo(seq);			
			
			if (!isEmpty(list)) {
				for(OrdEmailReqDTO t:list){
					if(t.getSeqNo() == seq) { 
						if(DisMode.E.equals(t.getMethod())) form.setEmailAddr(t.getEmailAddr());
						else if(DisMode.S.equals(t.getMethod())) form.setSMSno(t.getSMSno());
					}
				}
				
			}
			logger.info("form : " + new Gson().toJson(form));
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
		Map<Object, Object> referenceData = new HashMap<Object, Object>();
		
		Action action = Action.SEARCH;
		try {
			action = Action.valueOf(request.getParameter("action"));
		} catch (Exception e) {}
		
		switch (action) {
		case PREVIEW:
			String orderId = ServletRequestUtils.getRequiredStringParameter(request, "orderId");
			String templateId = ServletRequestUtils.getRequiredStringParameter(request, "templateId");
			
			OrderImsUI order = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
			if(order==null){
				order = orderservice.getBomWebImsOrder(orderId); 
			}
			
			List<OrdEmailReqDTO> list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderIdIMS(orderId,null);
			referenceData.put("list", list);
			
			if (!isEmpty(list)) {
				OrdEmailReqDTO ordEmailReqDto = list.get(0);
				
				EmailTemplateDTO emailTemplateDto = null;
				EmailTemplateDTO SMSTemplateDto = null;
				
				String pcdOrder = "Y";
			
					String templateIdE="";
					String templateIdS="";
//					for(OrdEmailReqDTO dto: list){
//						if(dto.getMethod()== DisMode.E){
//							templateIdE = dto.getTemplateId();
//						}else if (dto.getMethod()== DisMode.S){
//							templateIdS = dto.getTemplateId();
//						}
//					}
					
					if(order.isOrderTypeNowRet()){
						pcdOrder = "N";
						SMSTemplateDto = this.orderEsignatureService.getEmailTemplateDTO  ("NRSUR"+salesUserDto.getChannelId(), ordEmailReqDto.getLob(), ordEmailReqDto.getEsigEmailLang());
						emailTemplateDto = this.orderEsignatureService.getEmailTemplateDTO("NREUR"+salesUserDto.getChannelId(), ordEmailReqDto.getLob(), ordEmailReqDto.getEsigEmailLang());
					}else{
					if(templateId.indexOf("E_")>=0){
						templateIdE = templateId.substring(templateId.indexOf("E_")+2, templateId.indexOf("E_")+7);
					}
					if(templateId.indexOf("S_")>=0){
						templateIdS = templateId.substring(templateId.indexOf("S_")+2, templateId.indexOf("S_")+7);
					}
					
					if(!templateIdE.isEmpty()){
						if(!"RT034".equalsIgnoreCase(templateIdE)&&!"RT035".equalsIgnoreCase(templateIdE)){
							templateIdE=templateIdE.substring(0,2)+"1"+templateIdE.substring(3);
						}
					}
					else {											// NTV ORDER ONLY
						String mapStr= imsEmailParamHelperDAO.getDSTemplateMap(templateIdS.substring(0,2)+"0"+templateIdS.substring(3));
						templateIdE = imsEmailParamHelperDAO.getDSTemplateId(mapStr.substring(0,2), "NTV-A", "E", mapStr.substring(6));
					}
					if(!templateIdS.isEmpty()){
						templateIdS=templateIdS.substring(0,2)+"1"+templateIdS.substring(3);
					}
					
					emailTemplateDto = this.orderEsignatureService.getEmailTemplateDTO(templateIdE, ordEmailReqDto.getLob(), ordEmailReqDto.getEsigEmailLang());
					if(!templateIdS.isEmpty()){
						SMSTemplateDto = this.orderEsignatureService.getEmailTemplateDTO(templateIdS, ordEmailReqDto.getLob(), ordEmailReqDto.getEsigEmailLang());			
					}
					}
				
				referenceData.put("emailTemplateDto", emailTemplateDto);
				referenceData.put("pcdOrder", pcdOrder);
				
				try {
					
					String emailSubject = this.orderEsignatureService.getEmailSubject(emailTemplateDto, ordEmailReqDto, null);
					String emailContent = this.orderEsignatureService.getEmailContent(emailTemplateDto, ordEmailReqDto, null);
					
					String SMSSubject = "";
					String SMSContent = "";
					String SMSInd = "N";
					
					if(SMSTemplateDto!=null){
						SMSSubject = this.orderEsignatureService.getEmailSubject(SMSTemplateDto, ordEmailReqDto, null);
						SMSContent = this.orderEsignatureService.getEmailContent(SMSTemplateDto, ordEmailReqDto, null);	
						SMSInd = "Y";
					}
					
					referenceData.put("emailSubject", emailSubject);
					referenceData.put("emailContent", emailContent);
					
					referenceData.put("SMSSubject", SMSSubject);
					referenceData.put("SMSContent", SMSContent);
					
					referenceData.put("SMSInd", SMSInd);
					
				} catch (Exception e) {
					if (logger.isDebugEnabled()) {
						logger.debug("Exception during prepare email preview", e);
					}
					referenceData.put("emailException", e);
				}
			}
			List<String> tlist = new ArrayList<String>();
			tlist.add(orderId);
			tlist = (List<String>)(imsOrderAmendservice.getPrivilegedOrderIdList(tlist, salesUserDto, "IMS_ROLE_AF", "IMS_ROLE_AF_FUNC"));
			String SaleResendEmailAllowed = tlist!=null && tlist.size()>0?"Y":"N";
			referenceData.put("SaleResendEmailAllowed", SaleResendEmailAllowed);

			for(OrdEmailReqDTO dto: list){
				if(dto.getMethod()== DisMode.E){
					dto.setTemplateId("E_"+dto.getTemplateId());
				}else if (dto.getMethod()== DisMode.S){
					dto.setTemplateId("S_"+dto.getTemplateId());
				}
			}
			
			referenceData.put("list2", getPreviewShowList(list)); 
			
			break;
		case SEND:
			// should be reached by POST method
			break;
		case SEARCH:
		default:
		}

		Map<String, String> teamCds = new LinkedHashMap<String, String>();		
		List<String> list = ordEmailReqService.getTeamCdsByRoleandChannelCd(salesUserDto);
		if ( list == null || list.size() == 0)
			if (salesUserDto.getShopCd() != null)
			teamCds.put(salesUserDto.getShopCd(), salesUserDto.getShopCd());
			else
			teamCds.put("","");
		else
		{
			for(String item:list)
			{
				if ( item != null && !"".equals(item))
					teamCds.put(item, item);
			}
		}
		logger.info("teamcds: "+ teamCds);
			
		referenceData.put("teamCds", teamCds);
		
		Map<String, String> lobs = new LinkedHashMap<String, String>();
		lobs.put("", "All");
		lobs.put("LTS", "LTS");
		lobs.put("IMS", "IMS");
		referenceData.put("lobs", lobs);
		
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors) throws Exception {
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		ModelAndView modelAndView;
		DSOrderSendEmailHistUI form = (DSOrderSendEmailHistUI) command;
		
		logger.info("###Send email"+form.getAction());
		
		switch (form.getAction()) {
		case SEARCH_SUBMIT:
			modelAndView = new ModelAndView("dsordersendemailhist", this.getModel(request, errors));
			Date requestDate = null;
			if (StringUtils.isNotBlank(form.getRequestDateStr())) {
				requestDate = Utility.string2Date(form.getRequestDateStr());
			}

			List<OrdEmailReqDTO> olist = this.ordEmailReqService.getOrdEmailReqDTOALLBySearchIMS(form.getOrderId(), requestDate,salesUserDto,form.getTeamCd(),form.getOrderType());
			if(form.getReset().equalsIgnoreCase("Y")){
				olist = new ArrayList<OrdEmailReqDTO>();
			}
			modelAndView.addObject("list", olist);
			for(OrdEmailReqDTO dto: olist){
				if(dto.getMethod()== DisMode.E){
					dto.setTemplateId("E_"+dto.getTemplateId());
				}else if (dto.getMethod()== DisMode.S){
					dto.setTemplateId("S_"+dto.getTemplateId());
				}
			}
			modelAndView.addObject("list3", getSearchShowList(olist)); 
			
			break;
		case SEND:
			OrderImsUI order = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
			if(order==null){
				order = orderservice.getBomWebImsOrder(form.getOrderId()); 
			}
			
			modelAndView = new ModelAndView(new RedirectView("dsordersendemailhist.html?dM=Y&seqNo="+form.getSeqNo()+1));

			modelAndView.addObject("orderId", form.getOrderId());
			modelAndView.addObject("templateId", form.getTemplateId());
			modelAndView.addObject("action", Action.PREVIEW.toString());
			modelAndView.addObject("emailSendConfirm", form.isEmailSendConfirm());
			logger.info("form:"+gson.toJson(form));
			
//			logger.info("Send email "+form.getMethod()+" "+form.getOrderId());
			List<OrdEmailReqDTO> listE = new ArrayList<OrdEmailReqDTO>();
			List<OrdEmailReqDTO> listS = new ArrayList<OrdEmailReqDTO>();
			if (form.isEmailSendConfirm()) {
				
				String oldTemplateIdE = "";
				String oldTemplateIdS = "";

				String formTemplateId = form.getTemplateId();
				
				if(order.isOrderTypeNowRet()){
					if(formTemplateId.indexOf("E_")>=0){
						oldTemplateIdE = oldTemplateIdE.replace("E_", "");
					}
					if(formTemplateId.indexOf("S_")>=0){
						oldTemplateIdS =oldTemplateIdE.replace("S_", "");
					}
				}else{
				if(formTemplateId.indexOf("E_")>=0){
					oldTemplateIdE = formTemplateId.substring(formTemplateId.indexOf("E_")+2, formTemplateId.indexOf("E_")+7);
				}
				if(formTemplateId.indexOf("S_")>=0){
					oldTemplateIdS = formTemplateId.substring(formTemplateId.indexOf("S_")+2, formTemplateId.indexOf("S_")+7);
				}
				}
				
				listE = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderIdIMS(form.getOrderId(),oldTemplateIdE);
				if(!oldTemplateIdS.isEmpty()){
					listS = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderIdIMS(form.getOrderId(),oldTemplateIdS);
				}
				EmailReqResult result = null;
				EmailReqResult result2 = null;
				if (!isEmpty(listE)) {
					OrdEmailReqDTO ordEmailReqDto = listE.get(listE.size()-1); 
					if(order.isOrderTypeNowRet()){			
						if(ordEmailReqDto.getFilePathName1()==null||"".equals(ordEmailReqDto.getFilePathName1())){
							String filePath = "";
							for(OrdEmailReqDTO a:listS){
								if(a.getFilePathName1()!=null&&!"".equals(a.getFilePathName1())){
									filePath=a.getFilePathName1();
								}
							}
							ordEmailReqDto.setFilePathName1(filePath);
						}
					}
					String templateIdE="";
					String templateIdS="";
//					for(OrdEmailReqDTO dto: list){
//						if(dto.getMethod()== DisMode.E){
//							templateIdE = dto.getTemplateId();
//						}else if (dto.getMethod()== DisMode.S){
//							templateIdS = dto.getTemplateId();
//
//						}
//					}

					String shortenUrl = imsSMSService.getShortenUrl(ordEmailReqDto.getOrderId());
					String locale = EsigEmailLang.ENG.equals(order.getEsigEmailLang())?"ENG":"CHN";
					if(order.isOrderTypeNowRet()){
						templateIdE="NREUR"+order.getCreateByUser().getChannelId();
						templateIdS="NRSUR"+order.getCreateByUser().getChannelId();
					}else{
					if(!oldTemplateIdE.isEmpty()){
						if(!"RT034".equalsIgnoreCase(oldTemplateIdE)&&!"RT035".equalsIgnoreCase(oldTemplateIdE)){
							templateIdE=oldTemplateIdE.substring(0,2)+"1"+oldTemplateIdE.substring(3);
						}else{
							templateIdE=oldTemplateIdE;
						}
					}
					else {											// NTV ORDER ONLY
						String mapStr= imsEmailParamHelperDAO.getDSTemplateMap(templateIdS.substring(0,2)+"0"+templateIdS.substring(3));
						templateIdE = imsEmailParamHelperDAO.getDSTemplateId(mapStr.substring(0,2), "NTV-A", "E", mapStr.substring(6));
					}
					if(!oldTemplateIdS.isEmpty()){
						templateIdS=oldTemplateIdS.substring(0,2)+"1"+oldTemplateIdS.substring(3);
					}
					}

					logger.info("form.getMethod() :"+form.getMethod() +"  listS.size():"+listS.size() );
					if(form.getMethod() == DisMode.E){
						logger.info("form.getMethod E ");
						result = this.orderEsignatureService.resendEmailReqIMS(
								ordEmailReqDto.getOrderId()
								, templateIdE
								, ordEmailReqDto.getFilePathName1()
								, ordEmailReqDto.getFilePathName2()
								, ordEmailReqDto.getFilePathName3()
								, form.getEmailAddr()
								, salesUserDto.getUsername() 
						);
					}else if(form.getMethod() == DisMode.S && (listS.size()>0||order.isOrderTypeNowRet())){
						logger.info("form.getMethod S ");
						 if(order.isOrderTypeNowRet()){							 
							 result = sendNowRetSms(salesUserDto,  form, order,ordEmailReqDto, shortenUrl, locale);
							 this.imsSMSService.addSendNowRetSmsRecord(ordEmailReqDto.getOrderId(), templateIdS, form.getSMSno(), salesUserDto.getUsername(),  result, listS.get(listS.size()-1).getSeqNo()+1);
						 }else{						
							 result = this.imsSMSService.resendSMSReq(ordEmailReqDto.getOrderId(),templateIdS, form.getSMSno(), salesUserDto.getUsername(), listS.get(listS.size()-1).getSeqNo()+1);
						 }
					}else if(form.getMethod() == DisMode.DS && (listS.size()>0||order.isOrderTypeNowRet())){
						logger.info("form.getMethod DS ");
						result = this.orderEsignatureService.resendEmailReqIMS(
								ordEmailReqDto.getOrderId()
								, templateIdE
								, ordEmailReqDto.getFilePathName1()
								, ordEmailReqDto.getFilePathName2()
								, ordEmailReqDto.getFilePathName3()
								, form.getEmailAddr()
								, salesUserDto.getUsername() 
						);

						int smsSeqNo = 0;
						try {
							if(templateIdS.contains(ImsConstants.IMS_AMENTMENT_NOTIFICATION)){
								smsSeqNo = this.ordEmailReqService.getNextAmendNoteSeqNoIMS(form.getOrderId());
							}//celia ims 20141125	
							else
								smsSeqNo = this.ordEmailReqService.getNextSeqIMS(form.getOrderId());
							
						} catch (Exception e) {
							logger.warn("Exception in insertOrdEmailReq", e);
							throw new AppRuntimeException(e == null ? "" : e.getMessage());
						}
						

						 if(order.isOrderTypeNowRet()){				
							 result2 = sendNowRetSms(salesUserDto,  form, order,ordEmailReqDto, shortenUrl, locale);
							 int smsSeq= 0;
							 if(listS!=null&&listS.size()>0){
								 smsSeq=listS.get(listS.size()-1).getSeqNo()+1;
							 }
							 this.imsSMSService.addSendNowRetSmsRecord(ordEmailReqDto.getOrderId(), templateIdS, form.getSMSno(), salesUserDto.getUsername(),  result2, smsSeq);
						 }else{
							 result2 = this.imsSMSService.resendSMSReq(ordEmailReqDto.getOrderId(), templateIdS, form.getSMSno(), salesUserDto.getUsername(), smsSeqNo-1);
						 }
						 
					}
				
				modelAndView.addObject("result", result.toString());
				if(result!=null){
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
			else{
				
			}
			break;
		case SEARCH:
		case PREVIEW:{
			
		}
		default:
			modelAndView = new ModelAndView(new RedirectView("dsordersendemailhist.html"));
		}
		
		if (StringUtils.isNotBlank(request.getParameter("hideToolbar"))) {
			modelAndView.addObject("hideToolbar", request.getParameter("hideToolbar"));
		}
		return modelAndView;
	}

	public EmailReqResult sendNowRetSms(BomSalesUserDTO salesUserDto,
			DSOrderSendEmailHistUI form,
			OrderImsUI order, OrdEmailReqDTO ordEmailReqDto, String shortenUrl,
			String locale) {
		try{
			String rs = imsSMSService.sendNowRetSms(form.getSMSno(),"NRSUR"+salesUserDto.getChannelId(), ordEmailReqDto.getOrderId(), locale, order.getSignOffDate(), shortenUrl);
			if(rs.equals("Error")){
				return EmailReqResult.SMS_FAIL;
			}else{
				return EmailReqResult.SUCCESS;
			}
		}catch (Exception e){
			logger.error("Exception caught in sendNowRetSms()", e);
		}
		return EmailReqResult.SMS_FAIL;
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
				if(DisMode.E == t.getMethod() && DisMode.S == dto.getMethod()){
					t.setMethod(DisMode.DS);
					t.setSMSno(dto.getSMSno());
					t.setErrMsg((t.getSentDate()==null?(t.getErrMsg()==null?"":t.getErrMsg()):"Send on "+dt.format(t.getSentDate()))+System.getProperty("line.separator")+(dto.getSentDate()==null?(dto.getErrMsg()==null?"":dto.getErrMsg()):"Send on "+dt.format(dto.getSentDate())));
//					t.setErrMsg((t.getErrMsg()==null?"":t.getErrMsg())+"\n"+(dto.getErrMsg()==null?"":dto.getErrMsg()));
					t.setTemplateId(t.getTemplateId()+dto.getTemplateId());//Tony
					
					m.remove(dto.getSMSno());
					m.put(dto.getSeqNo(), t);
				} else if(DisMode.S == t.getMethod() && DisMode.E == dto.getMethod()){ 
					t.setMethod(DisMode.DS);
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

}

