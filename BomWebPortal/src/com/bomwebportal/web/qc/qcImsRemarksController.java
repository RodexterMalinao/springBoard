/**
 * 
 */
package com.bomwebportal.web.qc;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.ui.ImsDsQcProcessDetailUI;
import com.bomwebportal.service.qc.ImsDSQCService;

/**
 * @author 01397165
 *
 */
	public class qcImsRemarksController extends SimpleFormController  {
		
		private ImsDSQCService imsDSQCService;
		
		public ImsDSQCService getImsDSQCService() {
			return imsDSQCService;
		}

		public void setImsDSQCService(ImsDSQCService imsDSQCService) {
			this.imsDSQCService = imsDSQCService;
		}

		
		public Object formBackingObject(HttpServletRequest request)	throws ServletException {
	
			logger.debug("qcImsRemarksController formBackingObject called");
			ImsDsQcProcessDetailUI enquiry = new ImsDsQcProcessDetailUI(); 
			
			String orderID =  request.getParameter("orderId");
			
			try {
				List<ImsDsQcProcessDetailUI> qcRemarks = imsDSQCService.getQcRemark(orderID);
				if (qcRemarks.size()>0)
					enquiry.setQcFinding(qcRemarks.get(0).getQcFinding());
				
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			logger.debug("qcImsRemarksController orderid : " + orderID);
			
			return enquiry;
		}
			
	}
