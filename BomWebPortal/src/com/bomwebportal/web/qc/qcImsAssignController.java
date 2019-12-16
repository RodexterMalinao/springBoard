package com.bomwebportal.web.qc;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.constant.ImsConstants;
//import com.bomwebportal.ims.dto.ui.ImsDoneUI;
import com.bomwebportal.ims.dto.ImsAlertMsgDTO;
import com.bomwebportal.ims.dto.ui.DsQCImsOrderEnquiryUI;
import com.bomwebportal.ims.dto.ui.ImsSummaryUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.dto.ui.ImsQcAssignUI;
import com.bomwebportal.ims.service.BasketSummaryService;
import com.bomwebportal.ims.service.ImsReport2Service;
import com.bomwebportal.service.SupportDocService;
import com.bomwebportal.service.qc.ImsDSQCService;
import com.bomwebportal.web.ext.BomWebPortalApplicationFlow;
//import com.bomwebportal.service.OrderEsignatureService.EmailReqResult;

public class qcImsAssignController extends SimpleFormController{

    protected final Log logger = LogFactory.getLog(getClass());

    private ImsReport2Service imsReport2Service;
    private BasketSummaryService basketSummaryService;
	private SupportDocService supportDocService;
	 private ImsDSQCService imsDSQCService;
    
    public ImsReport2Service getImsReport2Service() {
		return imsReport2Service;
	}

	public void setImsReport2Service(ImsReport2Service imsReport2Service) {
		this.imsReport2Service = imsReport2Service;
	}

	public BasketSummaryService getBasketSummaryService() {
		return basketSummaryService;
	}

	public void setBasketSummaryService(BasketSummaryService basketSummaryService) {
		this.basketSummaryService = basketSummaryService;
	}
    
    private BomWebPortalApplicationFlow appFlow;
    
	public BomWebPortalApplicationFlow getAppFlow() {
		return appFlow;
	}

	public void setAppFlow(
			BomWebPortalApplicationFlow appFlow) {
		this.appFlow = appFlow;
	}
	
   
	
	public ImsDSQCService getImsDSQCService() {
		return imsDSQCService;
	}

	public void setImsDSQCService(ImsDSQCService imsDSQCService) {
		this.imsDSQCService = imsDSQCService;
	}
	

	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		logger.info("qcImsAssignController formBackingObject");
		
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		//BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		if(sessionOrder!=null){logger.info("ImsOrderID:"+sessionOrder.getOrderId());};
		
		ImsQcAssignUI imsAssignUI = (ImsQcAssignUI)request.getSession().getAttribute("ccltsimsorderenquiry");
		
		ImsQcAssignUI sessionDone = new ImsQcAssignUI();
		
		String orderId = (String) request.getParameter("orderId");
		String action = (String) request.getParameter("action");
		String sysf = (String) request.getParameter("sys_f");
		
		//String staffId = (String) request.getParameter("staffId");
		logger.info("qcImsAssignController orderId:"+orderId);
		logger.info("qcImsAssignController action:"+action);
		logger.info("qcImsAssignController sysf:"+sysf);
		
		request.setAttribute("orderids", orderId);
		request.setAttribute("action", action);
		request.setAttribute("sysf", sysf);
		//request.setAttribute("staffId", staffId);
		
//		request.getSession().setAttribute("orderId",orderId);
		
		
		List <String> qcStaffSkills = new ArrayList<String>();
		
		try {
			BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
			
			List<ImsQcAssignUI> qcStaffInfo = imsDSQCService.getQcStaffInfo(user.getChannelId());
			request.getSession().setAttribute("qcStaffInfo",qcStaffInfo);
			
			
			qcStaffSkills=imsDSQCService.getQcSkills(user.getUsername());
			logger.info("qcImsAssignController qcStaffSkills:"+user.getUsername() + "||" +StringUtils.join(qcStaffSkills, ','));
			
			request.getSession().setAttribute("qcStaffSkills", StringUtils.join(qcStaffSkills, ','));
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    
		return sessionDone;
	}	
	
	
	/*	
	public Map<String, Object> referenceData(HttpServletRequest request) {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		//eSignature CR by Eric Ng, for iPad Proof Capture
		String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
		logger.info("sessionId:"+sessionId);
		referenceData.put( "currentSessionId", sessionId);//for app
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		referenceData.put( "salesUserDto", salesUserDto);//for app

		//BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		Map<DocType, List<AllOrdDocWaiveDTO>> waiveReasons = new HashMap<DocType, List<AllOrdDocWaiveDTO>>();
		for (DocType docType : DocType.values()) {
			List<AllOrdDocWaiveDTO> reasons = this.supportDocService.getAllOrdDocWaiveDTOALL("IMS", docType);
			if (!this.isEmpty(reasons)) {
				waiveReasons.put(docType, reasons);
			}
		}
		referenceData.put("waiveReasons", waiveReasons);
		
		String emailReqResult = request.getParameter("emailReqResult");
		referenceData.put("emailReqResult", emailReqResult);
		
		String emailReqResultMsg = request.getParameter("emailReqResultMsg");
		referenceData.put("emailReqResultMsg", emailReqResultMsg);
		
		
	
		return referenceData;
	}

	private String getFirstLine(String input) {
		String output;
		
		if (input != null && input.length() > 0) {
			//logger.debug(">> getFirstLine:input ==" + input);
			if (input.indexOf(((char)10)+"") == -1) {
				output = input;
			} else {
				output = input.substring(0, input.indexOf(((char)10)+""));
			}
			//logger.debug(">> getFirstLine:output==" + output);
		} else {
			output = "";
		}
		
		return output;	
	}
*/
/*  
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println("ImsDoneController .handleRequest");
    	BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
    	if (user==null){
    		user = new BomSalesUserDTO();
    	}
    	logger.info("userId:" + user.getUsername());
    	
        return new ModelAndView("imsdone", "userId", user.getUsername());
    }
*/

	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}

	public SupportDocService getSupportDocService() {
		return supportDocService;
	}

	public void setSupportDocService(SupportDocService supportDocService) {
		this.supportDocService = supportDocService;
	}
}
