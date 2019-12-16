package com.bomwebportal.lts.service.performance;


import javax.servlet.http.HttpSession;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dao.performance.BomwebPerformanceLogDAO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.performance.BomwebPerformanceLogDTO;
import com.pccw.util.spring.SpringApplicationContext;


public class PerformanceLoggerImpl implements PerformanceLogger {

	
	public void logMyPerformance(BomwebPerformanceLogDTO pBomwebPerformanceLogDTO,HttpSession pSession) throws Exception {	
		
		BomwebPerformanceLogDAO  bomwebPerformanceLogDAO = (BomwebPerformanceLogDAO) SpringApplicationContext.getBean("bomwebPerformanceLogDao");
		
		if (pSession != null) {
            bomwebPerformanceLogDAO.setSessionId(pSession.getId());
    		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)pSession.getAttribute("bomsalesuser");
    		if (bomSalesUserDTO != null) {
    			bomwebPerformanceLogDAO.setStaffId(bomSalesUserDTO.getUsername());
    		}
    		
    		OrderCaptureDTO orderCaptureDTO = (OrderCaptureDTO)pSession.getAttribute("sessionLtsOrderCapture");
    		if (orderCaptureDTO != null) {
    			bomwebPerformanceLogDAO.setOrderId(orderCaptureDTO.getSbOrder()!=null?orderCaptureDTO.getSbOrder().getOrderId():null);
    		}
    		
    		//AcqOrderCaptureDTO acqOrderCaptureDTO = (OrderCaptureDTO)pSession.getAttribute("sessionLtsOrderCapture");
		}
		
		bomwebPerformanceLogDAO.clearExcludeColumn();
		bomwebPerformanceLogDAO.addExcludeColumn(new String[] {	"LOG_ID", "DURATION_MS", "LAST_UPD_DATE", "LAST_UPD_BY","CREATE_DATE", "CREATE_BY" });		
				
		bomwebPerformanceLogDAO.setLob(pBomwebPerformanceLogDTO.getLob());
		bomwebPerformanceLogDAO.setClassName(pBomwebPerformanceLogDTO.getClassName());
		bomwebPerformanceLogDAO.setMethodName(pBomwebPerformanceLogDTO.getMethodName());
		bomwebPerformanceLogDAO.setStartTime(pBomwebPerformanceLogDTO.getStartTimeAsString());
		bomwebPerformanceLogDAO.setEndTime(pBomwebPerformanceLogDTO.getEndTimeAsString());


        bomwebPerformanceLogDAO.doInsert();
	}

}
