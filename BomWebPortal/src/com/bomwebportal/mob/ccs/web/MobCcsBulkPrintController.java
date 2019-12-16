package com.bomwebportal.mob.ccs.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsBulkPrintDTO;
import com.bomwebportal.mob.ccs.dto.ui.BulkPrintUI;
import com.bomwebportal.mob.ccs.service.MobCcsBulkPrintService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class MobCcsBulkPrintController extends SimpleFormController {
	private enum Status {
		DELIVERY_PENDING("01", "500", "Delivery (Pending)", null)
		, DELIVERY_READY("01", "599", "Delivery (Ready)", null)
		, DELIVERY_NOTE("99", "999", "Delivery (DOA)", "N005")
		;
		Status(String orderStatus, String checkPoint, String checkPointDesc, String reasonCd) {
			this.orderStatus = orderStatus;
			this.checkPoint = checkPoint;
			this.checkPointDesc = checkPointDesc;
			this.reasonCd = reasonCd;
		}
		public String getOrderStatus() {
			return orderStatus;
		}
		public String getCheckPoint() {
			return checkPoint;
		}
		public String getCheckPointDesc() {
			return checkPointDesc;
		}
		public String getReasonCd() {
			return reasonCd;
		}
		public static Status getByCheckPoint(String checkPoint) {
			for (Status c : Status.values()) {
				if (c.getCheckPoint().equals(checkPoint)) {
					return c;
				}
			}
			return null;
		}
		private String orderStatus;
		private String checkPoint;
		private String checkPointDesc;
		private String reasonCd;
	}
	private MobCcsBulkPrintService mobCcsBulkPrintService;

	public MobCcsBulkPrintService getMobCcsBulkPrintService() {
		return mobCcsBulkPrintService;
	}

	public void setMobCcsBulkPrintService(
			MobCcsBulkPrintService mobCcsBulkPrintService) {
		this.mobCcsBulkPrintService = mobCcsBulkPrintService;
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		logger.info("MobCcsBulkPrintController formBackingObject called");
		
		BulkPrintUI form = new BulkPrintUI();
		form.setProcessDateStr(Utility.date2String(new Date(), BomWebPortalConstant.DATE_FORMAT));
		
		return form;
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {
		logger.info("MobCcsBulkPrintController ReferenceData called");

		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		Map<String, String> checkPointALL = new LinkedHashMap<String, String>();
		for (Status status : Status.values()) {
			checkPointALL.put(status.getCheckPoint(), status.getCheckPointDesc());
		}
		referenceData.put("checkPointALL", checkPointALL);
		
		
		Map<String, List<CodeLkupDTO>> entityCodeMap = LookupTableBean.getInstance().getCodeLkupList();
		List<CodeLkupDTO> stockLocation = new ArrayList<CodeLkupDTO>();
		for (CodeLkupDTO dto : entityCodeMap.get("STOCK_LOC")) {
			stockLocation.add(dto);
		}
		referenceData.put("stockLocation", stockLocation);
		
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		Map model = errors.getModel();
		model.putAll(referenceData(request));
		BulkPrintUI form = (BulkPrintUI) command;
		Date now = new Date();

		ModelAndView modelAndView = new ModelAndView("mobccsbulkprint", model);
		
		Date processDate = Utility.string2Date(form.getProcessDateStr());
		
		List<Map<String, String>> statuses = new ArrayList<Map<String, String>>();
		if (StringUtils.isBlank(form.getCheckPoint())) {
			for (Status s : Status.values()) {
				Map<String, String> status = new HashMap<String, String>();
				status.put("orderStatus", s.getOrderStatus());
				status.put("checkPoint", s.getCheckPoint());
				status.put("reasonCd", s.getReasonCd());
				statuses.add(status);
			}
		} else {
			Status s = Status.getByCheckPoint(form.getCheckPoint());
			if (s != null) {
				Map<String, String> status = new HashMap<String, String>();
				status.put("orderStatus", s.getOrderStatus());
				status.put("checkPoint", s.getCheckPoint());
				status.put("reasonCd", s.getReasonCd());
				statuses.add(status);
			}
		}
		
		String location = form.getLocation();
		
		List<MobCcsBulkPrintDTO> resultList = this.mobCcsBulkPrintService.getBulkPrintDTOALLBySearch(processDate, statuses, location);
		modelAndView.addObject("searchLocation", location);
		modelAndView.addObject("resultList", resultList);
		
		return modelAndView;
	}
}
