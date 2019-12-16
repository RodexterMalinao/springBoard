package com.bomwebportal.web;

import java.io.File;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.service.MobCcsOrderRemarkService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.service.ReportService.Form;
import com.bomwebportal.sig.dto.SignCaptureDTO;
import com.bomwebportal.sig.service.SignCaptureService;
import com.bomwebportal.web.util.GenericReportHelper;
import com.bomwebportal.web.util.ProjectEagleReportHelper;
import com.bomwebportal.web.util.ReportRepository;

public class ReportDownloadController implements Controller {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ReportRepository reportRepository;
	private OrderService orderService;
	private GenericReportHelper genericReportHelper;
	private SignCaptureService signCaptureService;
	private MobCcsOrderRemarkService mobCcsOrderRemarkService;
	
	public ReportRepository getReportRepository() {
		return reportRepository;
	}
	public void setReportRepository(ReportRepository reportRepository) {
		this.reportRepository = reportRepository;
	}
	
	public OrderService getOrderService() {
		return orderService;
	}
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	public GenericReportHelper getGenericReportHelper() {
		return genericReportHelper;
	}
	public void setGenericReportHelper(GenericReportHelper genericReportHelper) {
		this.genericReportHelper = genericReportHelper;
	}
	
	public SignCaptureService getSignCaptureService() {
		return signCaptureService;
	}
	public void setSignCaptureService(SignCaptureService signCaptureService) {
		this.signCaptureService = signCaptureService;
	}
	
	public MobCcsOrderRemarkService getMobCcsOrderRemarkService() {
		return mobCcsOrderRemarkService;
	}
	public void setMobCcsOrderRemarkService(MobCcsOrderRemarkService mobCcsOrderRemarkService) {
		this.mobCcsOrderRemarkService = mobCcsOrderRemarkService;
	}
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String orderId = request.getParameter("orderId");
		String filePath = request.getParameter("filePath");
		String directFrom = request.getParameter("directFrom");
		String inputSales = request.getParameter("sales");
		
		if (StringUtils.equalsIgnoreCase(directFrom, "signcapture")) {
			String reqId = request.getParameter("reqId");
			SignCaptureDTO signCaptureDTO = signCaptureService.getSignCaptureDTO(reqId, orderId);
			if (signCaptureDTO == null) {
				ModelAndView view = new ModelAndView("requiredproofdownload");
				view.addObject("INVALID_PARAMETER", true);
				return view;
			}
		}
		
		logger.info("Requesting a signed-forms download.");
		String nfcInd = request.getParameter("nfcInd");
		String pLang = request.getParameter("pLang");
		String genericFormName = request.getParameter("genericForm");
		String formName = request.getParameter("formName");
		
		Form form = null;

		if ("LDS".equalsIgnoreCase(request.getParameter("iGuardType"))) {
			form = Form.IGUARD_LDS;
		}else if ("AD".equalsIgnoreCase(request.getParameter("iGuardType"))) {
			form = Form.IGUARD_AD;
		}else if ("CARECASH".equalsIgnoreCase(request.getParameter("iGuardType"))) {
			form = Form.IGUARD_CARECASH;
		}else if ("UAD".equalsIgnoreCase(request.getParameter("iGuardType"))) {
			form = Form.IGUARD_UAD;
		}else if ("Y".equalsIgnoreCase(request.getParameter("mobileSafetyPhone"))) {
			form = Form.MOBILE_SAFETY_PHONE;
		} else if ("Y".equalsIgnoreCase(request.getParameter("nfcSim"))) {
			form = Form.NFC_SIM;
		} else if ("Y".equalsIgnoreCase(request.getParameter("octopusSim"))) {
			form = Form.OCTOPUS_SIM;
		}  else if (StringUtils.isNotBlank(nfcInd) && !"0".equals(nfcInd)) {
			form = Form.NFC_SIM_SET;
		} else if ("Y".equalsIgnoreCase(request.getParameter("authorizedLetter"))) {
			form = Form.AUTH_LETTER;
		} else if (StringUtils.isNotBlank(genericFormName)) {
			form = genericReportHelper.getGenericForm(genericFormName);
		} else if (StringUtils.equalsIgnoreCase(formName, ProjectEagleReportHelper.FORM_NAME)) {
			form = Form.PROJECT_EAGLE_FORM;
		}
		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		
		OrderDTO orderDto = null;
		String appMode = (String)request.getSession().getAttribute("appMode");
		
		if (form != Form.AUTH_LETTER && StringUtils.isBlank(filePath)) {
			if ("shop".equals(appMode) || "directsales".equals(appMode)) {
				orderDto=(OrderDTO)request.getSession().getAttribute("orderdetailOrderDto");
			}else{//CCS
				orderDto = (OrderDTO) MobCcsSessionUtil.getSession(request, "orderDTO");
			}
			
			if (orderDto == null || salesUserDto == null) {
				logger.error("Sessions orderDto or salesUserDto is null");
				ModelAndView view = new ModelAndView("requiredproofdownload");
				view.addObject("INVALID_PARAMETER", true);
				return view;
			}
	    	
			DisMode disMode = orderDto.getDisMode();
			//String orderStatus = orderDto.getOrderStatus();
	    	orderId = orderDto.getOrderId();
	    	if (orderDto.isRetail()){//only retail check DisMode =E
				if (!(DisMode.E.equals(disMode))) {
					logger.debug("Invalid order state - Order should be a e-signature order and signed off");
					throw new AppRuntimeException("Invalid order state - Order should be a e-signature order and signed off");
				}
			}
		}
		
		if (orderDto == null) {
			orderDto = orderService.getOrder(orderId);
			if (orderDto == null) {
				logger.error("Sessions orderDto is null");
				ModelAndView view = new ModelAndView("requiredproofdownload");
				view.addObject("INVALID_PARAMETER", true);
				return view;
			}
		}
		
    	File signedForms = null;
    			
    	if (StringUtils.isBlank(filePath)) {
	    	if (form == null) {
	    		signedForms = reportRepository.getSignedForms(orderId);
	    	} else {
	    		switch (form) {
		    		case IGUARD_LDS:
		        		signedForms = reportRepository.getIGuardSignedForms(orderId, orderDto.getMsisdn(), orderDto.getiGuardSerialNo(),"LDS",null,null);
		    			break;
		    		case IGUARD_AD:
		        		signedForms = reportRepository.getIGuardSignedForms(orderId, orderDto.getMsisdn(), orderDto.getiGuardSerialNo(),"AD",null,null);
		    			break;
		    		case IGUARD_UAD:
		        		signedForms = reportRepository.getIGuardSignedForms(orderId, orderDto.getMsisdn(), orderDto.getiGuardSerialNo(),"UAD",null,null);
		    			break;
		    		case IGUARD_CARECASH:
		    			signedForms =  reportRepository.getIGuardSignedForms(orderId, orderDto.getMsisdn(), orderDto.getiGuardSerialNo(),"IGUARD_CARECASH",orderDto.getEsigEmailLang(),pLang);
		    			break;
		    		case MOBILE_SAFETY_PHONE:
		    			signedForms = this.reportRepository.getMobileSafetyPhoneSignedForm(orderId);
		    			break;
		    		case NFC_SIM:
					case NFC_SIM_SET:
						signedForms = this.reportRepository.getNFCSimSignedForm(orderId);
						break;
					case OCTOPUS_SIM:
						signedForms = this.reportRepository.getOctopusSimSignedForm(orderId);
						break;
					case AUTH_LETTER:
						signedForms = this.reportRepository.getAuthLetter();
						break;
					default:
						String pdfFileName = genericReportHelper.getGenericFormFileName(orderId, orderDto.getMsisdn(), form);
						signedForms = this.reportRepository.getHKTCareSignedForms(orderId, pdfFileName);
						break;
				}
	    		/*case NFC_SIM:
					signedForms = this.reportRepository.getNFCSimSignedForm(orderId);
					break;
	    		case OCTOPUS_SIM:
					signedForms = this.reportRepository.getOctopusSimSignedForm(orderId);
					break;
	    		}*/
	    	}
    	} else {
    		signedForms = reportRepository.getFormByFilePath(orderId, filePath);
    	}
    	
    	System.out.println("Signed Form " +signedForms);
    	if (signedForms == null) {
    		logger.debug("No report file found ...");
			ModelAndView view = new ModelAndView("requiredproofdownload");
			view.addObject("FILE_NOT_FOUND", true);
			return view;
    	}
    	
    	OutputStream out = response.getOutputStream();
    	response.setContentType("application/pdf");
    	response.setHeader("Content-Disposition", "attachment; filename=\""+signedForms.getName()+"\"");
    	FileUtils.copyFile(signedForms, out);
    	
    	logger.info("Returning file: " + signedForms);
    	
    	if (salesUserDto != null && StringUtils.isNotBlank(salesUserDto.getUsername())
    			&& StringUtils.isBlank(inputSales)) {
    		inputSales = salesUserDto.getUsername();
    	}
    	mobCcsOrderRemarkService.insertOrderRemark(inputSales, orderId, "REPORT DOWNLOAD - REPORT: " + signedForms.getName());
    	
		return null;
	}

}
