/**
 * 
 */
package com.bomwebportal.web.qc;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.ims.dto.ui.ImsDsQcProcessDetailUI;
import com.bomwebportal.service.qc.ImsDSQCService;

/**
 * @author 01397165
 *
 */
	public class qcImsAmendRemarksController extends SimpleFormController  {
		
		private ImsDSQCService imsDSQCService;
		
		public Object formBackingObject(HttpServletRequest request)	throws ServletException {
			
			logger.debug("qcImsAmendRemarksController formBackingObject called");
			String orderId = (String) request.getParameter("orderId");
			
			ImsDsQcProcessDetailUI uiObject = new ImsDsQcProcessDetailUI();
			
			uiObject.setQcFinding(imsDSQCService.getAmendRemarks(orderId));
			
			//request.getSession().setAttribute("amendmentRemark", remark); 
			
			logger.debug("qcImsAmendRemarksController formBackingObject orderID:" + orderId);
			
			return uiObject;
		}

		public void setImsDSQCService(ImsDSQCService imsDSQCService) {
			this.imsDSQCService = imsDSQCService;
		}

		public ImsDSQCService getImsDSQCService() {
			return imsDSQCService;
		}
					
	}
