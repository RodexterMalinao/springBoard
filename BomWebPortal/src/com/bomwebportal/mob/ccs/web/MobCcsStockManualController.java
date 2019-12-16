package com.bomwebportal.mob.ccs.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.StockManualDTO;
import com.bomwebportal.mob.ccs.dto.ui.StockManualUI;
import com.bomwebportal.mob.ccs.service.StockManualService;

public class MobCcsStockManualController extends SimpleFormController {
	
	private StockManualService stockManualService;

	public void setStockManualService(StockManualService stockManualService) {
		this.stockManualService = stockManualService;
	}
	public StockManualService getStockManualService() {
		return stockManualService;
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		
		return new StockManualUI();

	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		return new ModelAndView(new RedirectView("mobccsstockmanual.html"));
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {
		logger.info("ReferenceData called");

		Map referenceData = new HashMap<String, List<String>>();

		List<CodeLkupDTO> orderStatus = new ArrayList<CodeLkupDTO>(); 
		Map<String, List<CodeLkupDTO>> entityCodeMap = LookupTableBean.getInstance().getCodeLkupList();
		
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		if (user.getChannelId() == 10 || user.getChannelId() == 11) {
			for (CodeLkupDTO dto : entityCodeMap.get("DS_ORD_STATUS")) {
					orderStatus.add(dto);
			}
		} else {

			for (CodeLkupDTO dto : entityCodeMap.get("ORD_FALLOUT_CODE")) {
				if ("A001".equalsIgnoreCase(dto.getCodeId())
						|| "A002".equalsIgnoreCase(dto.getCodeId())
						|| "A003".equalsIgnoreCase(dto.getCodeId())
						|| "N002".equalsIgnoreCase(dto.getCodeId())
						|| "N004".equalsIgnoreCase(dto.getCodeId())
						|| "G002".equalsIgnoreCase(dto.getCodeId())
						|| "G004".equalsIgnoreCase(dto.getCodeId())
						|| "M001".equalsIgnoreCase(dto.getCodeId())) {
					orderStatus.add(dto);
				}
			}
		}
		
		CodeLkupDTO initValue = new CodeLkupDTO();
		initValue.setCodeId("ALL");
		initValue.setCodeDesc("----ALL----");
		orderStatus.add(0, initValue);
		
		referenceData.put("orderStatus", orderStatus);

		return referenceData;
	}	
	
}