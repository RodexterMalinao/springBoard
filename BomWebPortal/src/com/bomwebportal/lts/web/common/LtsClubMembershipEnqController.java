package com.bomwebportal.lts.web.common;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.service.LtsClubMembershipService;
import com.bomwebportal.lts.theclub.dto.LtsTheClubResponseFormDTO;
import com.bomwebportal.lts.theclub.service.LtsTheClubPointConstant;
import com.bomwebportal.lts.theclub.service.LtsTheClubPointService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsClubMembershipEnqController extends SimpleFormController {

	private final String viewName = "lts/common/ltsclubmembershipenq";
	
	private LtsTheClubPointService ltsTheClubPointService;
	private LtsClubMembershipService ltsClubMembershipService;
	private Locale locale;
	private MessageSource messageSource;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setLocale(RequestContextUtils.getLocale(request));
		
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

		ModelAndView mav = new ModelAndView(viewName);
		setLocale(RequestContextUtils.getLocale(request));
		OrderCaptureDTO order = LtsSessionHelper.getOrderCapture(request);
		AcqOrderCaptureDTO acqOrder = LtsSessionHelper.getAcqOrderCapture(request);
		
		if(order == null && acqOrder == null){
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}

		String docType = (String) request.getParameter("docType");
		String docNum = (String) request.getParameter("docNum");
		
		/*Obtain Doc Type & Doc Num accordingly*/
		if(StringUtils.isBlank(docType) || StringUtils.isNotBlank(docNum)){
			if(order != null){
				if(order.getLtsServiceProfile() != null){
					docType = order.getLtsServiceProfile().getPrimaryCust().getDocType();
					docNum = order.getLtsServiceProfile().getPrimaryCust().getDocNum();
				}
				if(order.getLtsRecontractForm() != null ){
					docType = order.getLtsRecontractForm().getDocType();
					docNum = order.getLtsRecontractForm().getDocId();
				}
			}else if(acqOrder != null){
				if(acqOrder.getCustomerDetailProfileLtsDTO() != null){
					docType = acqOrder.getCustomerDetailProfileLtsDTO().getDocType();
					docNum = acqOrder.getCustomerDetailProfileLtsDTO().getDocNum();
				}else if(acqOrder.getLtsAcqPersonalInfoForm() != null){
					docType = acqOrder.getLtsAcqPersonalInfoForm().getDocumentType();
					docType = acqOrder.getLtsAcqPersonalInfoForm().getDocNum();
				}
			}
		}

		/*Call the club membership API*/
		LtsTheClubResponseFormDTO theClubInfo = null;
		if(StringUtils.isNotBlank(docType) && StringUtils.isNotBlank(docNum)){
			try{
				theClubInfo = ltsTheClubPointService.getTheClubMembershipInfo(LtsTheClubPointConstant.MEMBER_SEARCH_TYPE_DOCUMENT, null, null, docType, docNum);
				logger.info("Calling ltsTheClubPointService.getTheClubMembershipInfo, docType: " + docType + ", docNum: " + docNum);
			}catch(Exception e){
				theClubInfo = new LtsTheClubResponseFormDTO();
				theClubInfo.setRtnCode("ERROR");
				theClubInfo.setRtnMsg(messageSource.getMessage("lts.acq.clubMemEnq.retrieveClubMemInfoFail", new Object[] {}, this.locale) + "\nError: " + e.getMessage());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			};
		}else{
			theClubInfo = new LtsTheClubResponseFormDTO();
			theClubInfo.setRtnCode("ERROR");
			if(acqOrder != null){
				theClubInfo.setRtnMsg(messageSource.getMessage("lts.acq.clubMemEnq.compPersInfoToRetrieve", new Object[] {}, this.locale));
			}else{
				theClubInfo.setRtnMsg(messageSource.getMessage("lts.acq.clubMemEnq.missDocInfo", new Object[] {}, this.locale));
			}
		}
		mav.addObject("theClubInfo", theClubInfo);
		mav.addObject("docType", docType);
		mav.addObject("docNum", docNum);
		
		mav.addObject("theClubCatalogueUrl", ltsClubMembershipService.getTheClubCatalogueUrl(this.locale.toString()));
		
		return mav;
		
	}

	public LtsTheClubPointService getLtsTheClubPointService() {
		return ltsTheClubPointService;
	}

	public void setLtsTheClubPointService(
			LtsTheClubPointService ltsTheClubPointService) {
		this.ltsTheClubPointService = ltsTheClubPointService;
	}
	
    public LtsClubMembershipService getLtsClubMembershipService() {
		return ltsClubMembershipService;
	}

	public void setLtsClubMembershipService(LtsClubMembershipService ltsClubMembershipService) {
		this.ltsClubMembershipService = ltsClubMembershipService;
	}

	public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
}
