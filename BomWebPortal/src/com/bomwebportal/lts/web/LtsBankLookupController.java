package com.bomwebportal.lts.web;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.lts.service.LtsPaymentService;

public class LtsBankLookupController implements Controller {
	private LtsPaymentService ltsPaymentService;
	
	public LtsPaymentService getLtsPaymentService() {
		return ltsPaymentService;
	}

	public void setLtsPaymentService(LtsPaymentService ltsPaymentService) {
		this.ltsPaymentService = ltsPaymentService;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String bankCode = request.getParameter("parm");
		Comparator<LookupItemDTO> c = new Comparator<LookupItemDTO>(){
			public int compare(LookupItemDTO o1, LookupItemDTO o2) {
				return (Integer.parseInt(o1.getItemKey()) - Integer.parseInt(o2.getItemKey()));
			}
		};
		List<LookupItemDTO> branchCodeList = ltsPaymentService.getBranchCode(bankCode);
		Collections.sort(branchCodeList, c);
		
		for(int i = 0; i < branchCodeList.size(); i ++){
			jsonObject.put(branchCodeList.get(i).getItemKey(), branchCodeList.get(i).getItemValue());
		}
		request.setAttribute("branchList", jsonObject);
		return new ModelAndView("ajax_ltsbanklookup", jsonObject);
	}


}
