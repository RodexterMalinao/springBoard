package com.bomwebportal.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bomwebportal.mob.ccs.service.DeliveryService;
import com.bomwebportal.sbs.dto.CcsDeliveryDateRangeDTO;
import com.bomwebportal.sbs.dto.CcsDeliveryTimeSlotDTO;
import com.bomwebportal.util.Utility;

import net.sf.json.JSONArray;

@Controller
public class CcsDeliveryController {
	
	private DeliveryService deliveryService;
	
	public DeliveryService getDeliveryService() {
		return deliveryService;
	}

	public void setDeliveryService(DeliveryService deliveryService) {
		this.deliveryService = deliveryService;
	}
	
	@RequestMapping(value="/getCcsDeliveryDateRange", method=RequestMethod.GET)
	String getCcsDeliveryDateRange(
			ModelMap model
			, @RequestParam("orderType") String orderType
			, @RequestParam("delMode") String delMode
			, @RequestParam("delInd") String delInd
			, @RequestParam("hsInd") String hsInd
			, @RequestParam("hsItemCd") String hsItemCd
			, @RequestParam("payMthd") String payMthd
			, @RequestParam("fsInd") String fsInd
			, @RequestParam("mnpInd") String mnpInd
			, @RequestParam("orderId") String orderId
			, @RequestParam("appDate") String appDate) {
		Date formattedAppDate;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat();
			sdf.applyPattern("dd-MM-yyyy HH:mm:ss");
			formattedAppDate = sdf.parse(appDate);
		} catch (ParseException e) {
			formattedAppDate = null;
		}
		if ("NONE".equals(hsItemCd)) {
			hsItemCd = null;
		}
		if ("NONE".equals(payMthd)) {
			payMthd = null;
		}
		if ("NONE".equals(orderId)) {
			orderId = null;
		}
		CcsDeliveryDateRangeDTO ccsDeliveryDateRangeDTO = deliveryService.getCcsDeliveryDateRange(orderType, delMode, delInd, hsInd, hsItemCd, payMthd, fsInd, mnpInd, orderId, formattedAppDate);
		JSONArray jsonArray = new JSONArray();
		if (ccsDeliveryDateRangeDTO != null) {
			String minDate = Utility.date2String(ccsDeliveryDateRangeDTO.getStartDate());
			String maxDate = Utility.date2String(ccsDeliveryDateRangeDTO.getEndDate());
			String phDateString = null;
			String minDateStr =null;
			if (ccsDeliveryDateRangeDTO.getPhDateString() != null) {
				phDateString = ccsDeliveryDateRangeDTO.getPhDateString().substring(1, ccsDeliveryDateRangeDTO.getPhDateString().length() - 1);		
			}
			String startDateString = ccsDeliveryDateRangeDTO.getStartDateString();
			String endDateString = ccsDeliveryDateRangeDTO.getEndDateString();
			String timeslot = ccsDeliveryDateRangeDTO.getTimeSlot();
			Integer ruleNo = ccsDeliveryDateRangeDTO.getRuleNo();
			Integer retValue = ccsDeliveryDateRangeDTO.getRetValue();
			Integer errCode = ccsDeliveryDateRangeDTO.getRetValue();
			String errText = ccsDeliveryDateRangeDTO.getErrText();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			if (ccsDeliveryDateRangeDTO.getStartDate()!=null) {
				minDateStr = formatter.format(ccsDeliveryDateRangeDTO.getStartDate());
			}
			jsonArray.add("{\"minDate\":\"" + minDate
						+ "\",\"maxDate\":\""	+ maxDate
						+ "\",\"minDateStr\":\""	+ minDateStr
						+ "\",\"phDateString\":\""	+ phDateString
						+ "\",\"startDateString\":\""	+ startDateString
						+ "\",\"endDateString\":\""	+ endDateString
						+ "\",\"timeslot\":\""	+ timeslot
						+ "\",\"ruleNo\":\""	+ ruleNo
						+ "\",\"retValue\":\""	+ retValue
						+ "\",\"errCode\":\""	+ errCode
						+ "\",\"errText\":\""	+ errText
						+ "\"}");
		}
		model.put("jsonArray", jsonArray);
		return "ajax_view";
	}
	
	@RequestMapping(value="/getCcsDeliveryTimeslot", method=RequestMethod.GET)
	String getCcsDeliveryTimeslot(
			ModelMap model
			, @RequestParam("delMode") String delMode
			, @RequestParam("delInd") String delInd
			, @RequestParam("delDate") String delDate
			, @RequestParam("distNo") String distNo
			, @RequestParam("minDate") String minDate
			, @RequestParam("minTimeslot") String minTimeslot) {
		Date formattedDelDate;
		Date formattedMinDate;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat();
			sdf.applyPattern("dd-MM-yyyy");
			formattedDelDate = sdf.parse(delDate);
		} catch (ParseException e) {
			formattedDelDate = null;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat();
			sdf.applyPattern("dd-MM-yyyy");
			formattedMinDate = sdf.parse(minDate);
		} catch (ParseException e) {
			formattedMinDate = null;
		}
		List<CcsDeliveryTimeSlotDTO> ccsDeliveryTimeSlotDTOList = deliveryService.getCcsDeliveryTimeslot(delMode, delInd, formattedDelDate, distNo, formattedMinDate, minTimeslot);
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(ccsDeliveryTimeSlotDTOList);
		model.put("jsonArray", jsonArray);
		return "ajax_view";
	}

}
