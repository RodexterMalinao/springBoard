package com.bomwebportal.lts.theclub.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.form.NotificationFormDTO;
import com.bomwebportal.lts.notification.dto.BomNotificationConstants;
import com.bomwebportal.lts.notification.dto.LtsNotification;
import com.bomwebportal.lts.notification.service.LtsNotificationService;
import com.bomwebportal.lts.theclub.dto.LtsTheClubRequestFormDTO;
import com.bomwebportal.lts.theclub.dto.LtsTheClubResponseFormDTO;
import com.bomwebportal.lts.theclub.dto.LtsTheClubResponseFormDTO.PointQuarter;
import com.bomwebportal.lts.theclub.dto.BLtsTheClubTxnDTO;
import com.bomwebportal.lts.theclub.service.LtsTheClubPointConstant;
import com.bomwebportal.lts.theclub.service.LtsTheClubPointService;
import com.bomwebportal.lts.theclub.service.LtsTheClubPointUtil;
import com.bomwebportal.offercode.web.OfferCodeReqController.Form;

@Controller
public class TheClubPointServiceController {
	private static final Logger logger = LoggerFactory.getLogger(TheClubPointServiceController.class);

	public Validator getValidator() {
		return validator;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	Validator validator;

	@Autowired
	LtsTheClubPointService ltsTheClubPointService;

	public LtsTheClubPointService getLtsTheClubPointService() {
		return ltsTheClubPointService;
	}

	public void setLtsTheClubPointService(LtsTheClubPointService ltsTheClubPointService) {
		this.ltsTheClubPointService = ltsTheClubPointService;
	}

	@RequestMapping(value = "/pd/getMemberBasicInfoWithMaskedID", method = RequestMethod.GET)
	public ModelAndView getMemberBasicInfoWithMaskedID(@ModelAttribute LtsTheClubRequestFormDTO form,
			BindingResult result, Locale locale, HttpServletRequest request, HttpServletResponse response,
			Model model) {

		String idDocType = form.getIdDocType();
		String idDocNum = form.getIdDocNum();
		String searchingType = form.getSearchType();
		String memberId = form.getMemberId();
		String loginId = form.getLoginId();
		ModelAndView ret = new ModelAndView("json_clubpoint");
		LtsTheClubResponseFormDTO responseDTO = null;

		try {

			responseDTO = this.ltsTheClubPointService.getTheClubMembershipInfo(searchingType, memberId, loginId,
					idDocType, idDocNum);
			model.addAttribute("result", responseDTO);
		} catch (Exception e) {
			responseDTO.setRtnCode("Err_lts");
			responseDTO.setRtnMsg("Error:"+e.getMessage());
			e.printStackTrace();
		}

		return ret;
	}

	// (@ModelAttribute Form form, BindingResult result, Model model, Locale
	// locale, HttpServletRequest request)
	// (HttpServletRequest request, HttpServletResponse response, Model model)
	@RequestMapping(value = "/pd/doInstantEarnPoint", method = RequestMethod.GET)
	public ModelAndView doInstantEarnPoint(@ModelAttribute LtsTheClubRequestFormDTO form, BindingResult result,
			Locale locale, HttpServletRequest request, HttpServletResponse response, Model model) {
		this.validator.validate(form, result);
		LtsTheClubResponseFormDTO responseDTO = new LtsTheClubResponseFormDTO();

		Model responseModel = new ExtendedModelMap();
		responseModel.addAttribute(responseDTO);
		ModelAndView ret = new ModelAndView("json_clubpoint");
		// ret.addObject("result",responseModel);

		if (result.hasErrors()) {

		} else {
			String theClubOrderId = form.getTheClubOrderId();
			String custNum = form.getCustNum();
			String memberId = form.getMemberId();
			String idDocType = form.getIdDocType();
			String idDocNum = form.getIdDocNum();
			Integer ocId = form.getOcId();
			Integer dtlId = form.getDtlId();
			Integer dtlSeq = form.getDtlSeq();
			Integer itemId = form.getItemId();
			Integer offerId = form.getOfferId();
			String psefCd = form.getPsefCd();
			String packageCode = form.getPackageCode();
			Integer clubPoints = form.getClubPoints();
			String agreementNum = form.getAgreementNum();
			String channel = form.getChannel();
            boolean verify = form.isVerify();
            boolean test = form.isTest();
			try {
				responseDTO = this.ltsTheClubPointService.earnClubPoint(theClubOrderId, custNum, memberId, idDocType, idDocNum, ocId, dtlId,
						dtlSeq, itemId, offerId, psefCd, packageCode, clubPoints, agreementNum, channel, verify, test);
			} catch(AppRuntimeException are) {
				responseDTO.setRtnCode("Err_lts");
				responseDTO.setRtnMsg("Error:"+are.getCustomMessage());
				are.printStackTrace();
			}  catch (Exception e) {// handle unexpected exception and return error 
				responseDTO.setRtnCode("Err_lts");
				responseDTO.setRtnMsg("Error:"+e.getMessage());
				e.printStackTrace();
			}
		}

		model.addAttribute("result", responseDTO);
		return ret;
	}

	@RequestMapping(value = "/pd/doInstantEarnReversePoint", method = RequestMethod.GET)
	public ModelAndView doInstantEarnReversePoint(@ModelAttribute LtsTheClubRequestFormDTO form, BindingResult result,
			Locale locale, HttpServletRequest request, HttpServletResponse response, Model model) {
		this.validator.validate(form, result);
		LtsTheClubResponseFormDTO responseDTO = new LtsTheClubResponseFormDTO();

		Model responseModel = new ExtendedModelMap();
		responseModel.addAttribute(responseDTO);
		ModelAndView ret = new ModelAndView("json_clubpoint");
		// ret.addObject("result",responseModel);

		if (result.hasErrors()) {

		} else {
			String theClubOrderId = form.getTheClubOrderId();
			String custNum = form.getCustNum();
			String memberId = form.getMemberId();
			String idDocType = form.getIdDocType();
			String idDocNum = form.getIdDocNum();
			Integer ocId = form.getOcId();
			Integer dtlId = form.getDtlId();
			Integer dtlSeq = form.getDtlSeq();
			Integer itemId = form.getItemId();
			Integer offerId = form.getOfferId();
			String psefCd = form.getPsefCd();
			String packageCode = form.getPackageCode();
			Integer clubPoints = form.getClubPoints();
			String agreementNum = form.getAgreementNum();
			String channel = form.getChannel();
			String reverseTransId = form.getReverseTransId();
            boolean verify = form.isVerify();
            boolean test = form.isTest();
			try {
				responseDTO = this.ltsTheClubPointService.reverseClubPoint(theClubOrderId, custNum, memberId, idDocType, idDocNum, ocId,
						dtlId, dtlSeq, itemId, offerId, psefCd, packageCode, reverseTransId, clubPoints, agreementNum, channel, verify, test);
			} catch(AppRuntimeException are) {
				responseDTO.setRtnCode("Err_lts");
				responseDTO.setRtnMsg("Error:"+are.getCustomMessage());
				are.printStackTrace();
			} catch (Exception e) {// handle unexpected exception and return error 
				responseDTO.setRtnCode("Err_lts");
				responseDTO.setRtnMsg("Error:"+e.getMessage());
				e.printStackTrace();
			} 
		}

		model.addAttribute("result", responseDTO);
		return ret;
	}

	@RequestMapping(value = "/pd/processRequest", method = RequestMethod.GET)
	public ModelAndView processPendingRequest(@ModelAttribute LtsTheClubRequestFormDTO form, BindingResult result,
			Locale locale, HttpServletRequest request, HttpServletResponse response, Model model) {
		ModelAndView ret = new ModelAndView("json_clubpoint");
		LtsTheClubResponseFormDTO responseDTO = new LtsTheClubResponseFormDTO();
		if (form.getTransStatus() != null) {
			try {
				this.ltsTheClubPointService.processRequest(form.getTransStatus(), form.isVerify(), form.isTest());
			}catch(AppRuntimeException are){
				responseDTO.setRtnCode("Err_lts");
				responseDTO.setRtnMsg("Error:"+are.getCustomMessage());
				are.printStackTrace();
			}
		}
		model.addAttribute("result", responseDTO);
		return ret;
	}
}
