package com.bomwebportal.ims.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.ImsPaymentService;
import com.bomwebportal.ims.service.PTService;

public class GetSalesInfoController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsPaymentService imsPaymentService;
	private PTService PTS;

	public PTService getPTS() {
		return PTS;
	}

	public void setPTS(PTService pTS) {
		PTS = pTS;
	}

	public ImsPaymentService getImsPaymentService() {
		return imsPaymentService;
	}

	public void setImsPaymentService(ImsPaymentService imsPaymentService) {
		this.imsPaymentService = imsPaymentService;
	}
	
	

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		JSONArray jsonArray = new JSONArray();
    	
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		if(sessionOrder!=null){logger.info("ImsOrderID:"+sessionOrder.getOrderId());};
		//SalesmanDTO salesmanDTO = null;
		String salesCd = request.getParameter("salesCd");
		String ChannelCd = "";
		String salesName = "";
		String contactPhone = "";
    	
/*    	if ("".equals(sessionOrder.getSalesType()) || sessionOrder.getSalesType() == null){
			salesmanDTO = imsPaymentService.getSalesman("C", salesCd);
			salesName = salesmanDTO.getSalesName();		
			//sessionOrder.setSalesErrCode(salesmanDTO.getErrCode());
			//sessionOrder.setSalesErrCode(salesmanDTO.getErrMsg());
		} else if("".equals(sessionOrder.getSalesName()) || sessionOrder.getSalesName() == null){
			salesmanDTO = imsPaymentService.getSalesman(sessionOrder.getSalesType(), salesCd);
			salesName = salesmanDTO.getSalesName();		
			//sessionOrder.setSalesErrCode(salesmanDTO.getErrCode());
			//sessionOrder.setSalesErrCode(salesmanDTO.getErrMsg());
		}
*/
    	if(salesCd != null && !("").equals(salesCd)){
    		salesName = imsPaymentService.getStaffNameBySalesCd(salesCd);
    		ChannelCd = imsPaymentService.getStaffChannelCdBySalesCd(salesCd);
    		if ( ChannelCd == null || ChannelCd.isEmpty() )
    		{
    			ChannelCd = ((BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser")).getChannelCd();
    		}
    	}
    	contactPhone = imsPaymentService.getShopContact(ChannelCd);
    	
    	if ( contactPhone == null || "".equals(contactPhone))
		{
    		contactPhone = "1000";
		}
		
		int PTNum = PTS.getSalesNum(salesCd);
		if ( PTNum != 0 )
			contactPhone = String.valueOf(PTNum);
    	
    	if(salesName == null){
    		salesName = "";
    	}
    	
    	
    	jsonArray.add("{\"salesName\":\"" + salesName
    					+ "\",\"contactPhone\":\""	+ contactPhone
    					+ "\"}");
    	
		return new ModelAndView("ajax_getsalesnameshopcontact", "jsonArray", jsonArray);
	}
}
