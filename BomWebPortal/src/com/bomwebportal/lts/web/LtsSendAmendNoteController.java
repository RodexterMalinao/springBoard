package com.bomwebportal.lts.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.lts.service.LtsEmailService;
import com.bomwebportal.lts.service.sms.LtsSmsService;
import com.bomwebportal.lts.util.LtsConstant;

public class LtsSendAmendNoteController implements Controller {
	
	private LtsEmailService ltsEmailService;
	private LtsSmsService ltsSmsService;
	
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {

		JSONArray jsonArray = new JSONArray();
		
		//GET SMS/EMAIL REQUEST
		List<OrdEmailReqDTO> requestList = ltsEmailService.getOrdEmailReqDTOLTS(null, LtsConstant.LOB_LTS, LtsConstant.LTS_AMEND_NOTICE_TEMPLATE_IDS);//new ArrayList<OrdEmailReqDTO>();
		
		for (OrdEmailReqDTO insertedDto:requestList){
			String mtd = null;
			String target = null;
			String returnMsg = null;
			if(insertedDto.getMethod()==DisMode.S){
				//SMS
				mtd = "SMS";
				target = insertedDto.getSMSno();
				returnMsg = ltsSmsService.sendAmendmentNoticeSMS(insertedDto);
			}else if (insertedDto.getMethod()==DisMode.E || insertedDto.getMethod()==DisMode.I){
				//EMAIL
				mtd = "EMAIL";
				target = insertedDto.getEmailAddr();
				returnMsg = ltsEmailService.sendOrderEmail(insertedDto);
			}

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("ORDER", insertedDto.getOrderId());
			jsonObject.put("METHOD", mtd);
			jsonObject.put("TARGET", target);
			jsonObject.put("RESULT", returnMsg);
			jsonArray.add(jsonObject);
		}
				
		return new ModelAndView("ajax_view", "jsonArray", jsonArray);
	}

	public LtsEmailService getLtsEmailService() {
		return ltsEmailService;
	}

	public void setLtsEmailService(LtsEmailService ltsEmailService) {
		this.ltsEmailService = ltsEmailService;
	}

	public LtsSmsService getLtsSmsService() {
		return ltsSmsService;
	}

	public void setLtsSmsService(LtsSmsService ltsSmsService) {
		this.ltsSmsService = ltsSmsService;
	}

}
