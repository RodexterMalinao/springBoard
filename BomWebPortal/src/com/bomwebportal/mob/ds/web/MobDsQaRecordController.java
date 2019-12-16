package com.bomwebportal.mob.ds.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.mob.ds.dto.MobDsQaRecordDTO;
import com.bomwebportal.mob.ds.service.MobDsQaRecordService;
import com.bomwebportal.mob.ds.ui.MobDsQaRecordUI;
import com.bomwebportal.util.BomWebPortalConstant;

public class MobDsQaRecordController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private MobDsQaRecordService mobDsQaRecordService;
	
	public MobDsQaRecordService getMobDsQaRecordService() {
		return mobDsQaRecordService;
	}
	public void setMobDsQaRecordService(MobDsQaRecordService mobDsQaRecordService) {
		this.mobDsQaRecordService = mobDsQaRecordService;
	}
	
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {

		logger.info("MobDsQaRecordController@formBackingObject called");

		MobDsQaRecordUI ui = new MobDsQaRecordUI(0, null);
		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		String orderId = ServletRequestUtils.getRequiredStringParameter(request, "orderId");
		List<MobDsQaRecordDTO> recordList = mobDsQaRecordService.getQARecord(orderId);
		ui.setOrderId(orderId);
		ui.setStaffId(salesUserDto.getUsername());
		if (recordList == null || recordList.isEmpty()) {
			ui.setSeqNo(1);
		} else {
			ui.setSeqNo(recordList.get(0).getSeqNo()+1);
		}
		
		return ui;
	}
	
	protected ModelAndView onSubmit(
			HttpServletRequest request,	HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		String orderId = ServletRequestUtils.getRequiredStringParameter(request, "orderId");
		
		MobDsQaRecordUI ui = (MobDsQaRecordUI) command;
		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		ModelAndView modelAndView = new ModelAndView("mobdsqarecord");
		//ModelAndView modelAndView = new ModelAndView(new RedirectView("mobdsqarecord.html"));
		String createInd = request.getParameter("createInd");
		List<MobDsQaRecordDTO> recordList = mobDsQaRecordService.getQARecord(orderId);
		modelAndView.addObject("recordList", recordList);
		if ("CREATE".equalsIgnoreCase(ui.getActionType())){
			ui.setActionType("CREATE");
		} else if ("SAVE".equalsIgnoreCase(ui.getActionType())){
			if (ui.getQaOption() == 0) {
				request.getSession().setAttribute("error", "qaOptionError");
				ui.setActionType("CREATE");
			} else {
				int isSuccess = mobDsQaRecordService.insertQARecord(ui.getOrderId(), ui.getSeqNo(), ui.getMustQ(),
				ui.getQaOption(), ui.getRemark(), salesUserDto.getUsername());
				if (isSuccess == 1) {
					int updateSuccess = 1;
					if ("Y".equalsIgnoreCase(ui.getMustQ())) {
						updateSuccess = mobDsQaRecordService.setMustQStatus(ui.getOrderId(), salesUserDto.getUsername());
					} else if ("QA TEAM".equalsIgnoreCase(salesUserDto.getCategory()) || "QAT".equalsIgnoreCase(salesUserDto.getAreaCd())) {
						if (ui.getQaOption() == 3) {
							updateSuccess = mobDsQaRecordService.updateQaOrderStatus(ui.getOrderId(), BomWebPortalConstant.BWP_ORDER_REVIEWING, salesUserDto.getUsername());
						}
					}
					if (updateSuccess == 1) {
						ui.setActionType("success");
						if ("Y".equalsIgnoreCase(ui.getMustQ()) || ui.getQaOption() == 3) {
							createInd = "N";
						}
						modelAndView = new ModelAndView(new RedirectView("mobdsqarecord.html?orderId="+orderId));
						request.getSession().setAttribute("error", "NONE");
					} else {
						ui.setActionType("CREATE");
						//modelAndView = new ModelAndView("mobdsqarecord.html?orderId="+orderId);
					}
				}
			}
		} else if ("QUIT".equalsIgnoreCase(ui.getActionType())) {
			ui.setActionType(null);
			request.getSession().setAttribute("error", null);
		}
		
		request.getSession().setAttribute("actionType", ui.getActionType());

		request.getSession().setAttribute("recordList", recordList);
		modelAndView.addObject("qaRecord", ui);
		modelAndView.addObject("createInd", createInd);
		request.getSession().setAttribute("qaRecord", ui);

		return modelAndView;
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {

		logger.info("MobccsstockController ReferenceData called");
		
		Map referenceData = new HashMap<String, List<String>>();
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		request.getSession().setAttribute("user", salesUserDto);
		String createInd = request.getParameter("createInd");
		referenceData.put("createInd", createInd);
		
		List<MobDsQaRecordUI> optionList = new ArrayList<MobDsQaRecordUI>();
		//if (user is mobile support team
		if ("ORDER SUPPORT".equalsIgnoreCase(salesUserDto.getCategory())) {
			optionList.add(new MobDsQaRecordUI(1, "CROSS OUT"));
		} else /*if (salesUserDto.getCategory().indexOf("QA") > 0)*/{
			optionList.add(new MobDsQaRecordUI(2, "RNA"));
			optionList.add(new MobDsQaRecordUI(3, "PASS QC"));
			optionList.add(new MobDsQaRecordUI(4, "RE-QC"));
			optionList.add(new MobDsQaRecordUI(5, "RE-VERIFICATION"));
			optionList.add(new MobDsQaRecordUI(6, "QA PROBLEM"));
			optionList.add(new MobDsQaRecordUI(7, "WRONG INFO"));
		}
		request.getSession().setAttribute("optionList", optionList);
		referenceData.put("optionList", optionList);
		
		String orderId = ServletRequestUtils.getRequiredStringParameter(request, "orderId");
		List<MobDsQaRecordDTO> recordList = mobDsQaRecordService.getQARecord(orderId);
		referenceData.put("recordList", recordList);

		if ("success".equalsIgnoreCase((String)request.getSession().getAttribute("actionType")) &&
			request.getSession().getAttribute("error") != null) {
			//keep and display success message once
			request.getSession().setAttribute("error", null);
		} else {
			//reset session
			request.getSession().setAttribute("actionType", null);
			request.getSession().setAttribute("error", null);
		}
		
		return referenceData;
	}
}
