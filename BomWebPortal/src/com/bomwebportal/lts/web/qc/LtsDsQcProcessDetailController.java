package com.bomwebportal.lts.web.qc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.lts.dto.form.qc.LtsDsQcProcessDetailFormDTO;
import com.bomwebportal.lts.dto.form.qc.LtsDsQcProcessDetailFormDTO.QcProcessAction;
import com.bomwebportal.lts.dto.qc.LtsDsQcProcessDTO;
import com.bomwebportal.lts.service.qc.LtsDsQcService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsDsQcProcessDetailController extends SimpleFormController {
	
	private final String successRedirectView = "blank.jsp";
	private final String passMustQcView = "ltsorder.html?action="+LtsConstant.REQUEST_PARAM_ORDER_ACTION_RECALL_N_UPDATE_APPNT+"&orderId=";
	
	private LtsDsQcService ltsDsQcService;
	
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
//		LtsDsQcProcessDetailFormDTO form = LtsSessionHelper.getLtsDsQcProcessDetail(request);
		String orderId = request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ID);
		
//		if(form == null || !StringUtils.equals(orderId, form.getOrderId())){
//			form = initForm(orderId);
//		}
		
		return initForm(orderId);
	}
	
	private LtsDsQcProcessDetailFormDTO initForm(String orderId){
		LtsDsQcProcessDTO qcProcessInfo = ltsDsQcService.getLtsDsQcProcessInfo(orderId);
		
		LtsDsQcProcessDetailFormDTO form = new LtsDsQcProcessDetailFormDTO();
		form.setLob(LtsConstant.LOB_LTS);
		form.setOrderId(orderId);
		form.setQcDateTime(DateTime.now().toString(LtsDsQcProcessDetailFormDTO.DATETIME_FORMATTER));
		
		if(qcProcessInfo != null){
			form.setAssignDate(qcProcessInfo.getAssignDate());
			form.setAssignee(qcProcessInfo.getAssignee());
			form.setQcCallTime(qcProcessInfo.getQcCallTime());
			form.setOrderPlace(qcProcessInfo.getOrderPlace());
			form.setOrderDistrict(qcProcessInfo.getOrderDistrict());
//			form.setQcFindingDetail(qcProcessInfo.getQcRemarks());
			form.setMustQc(StringUtils.equals(qcProcessInfo.getMustQc(), "Y"));
			form.setQcStatus(qcProcessInfo.getQcStatus());
			form.setSbStatus(qcProcessInfo.getSbStatus());
		}
		return form;
	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
			throws ServletException {
		LtsDsQcProcessDetailFormDTO form = (LtsDsQcProcessDetailFormDTO) command;
		BomSalesUserDTO userInfo = LtsSessionHelper.getBomSalesUser(request);
		String reasonCode = null;
		String reasonDesc = null;
		String qcStatus = form.getAction().getCode();
		switch(form.getAction()){
			case CANNOT_QC: {
				reasonCode = form.getQcCannotQcReason();
				reasonDesc = getLookupDesc(reasonCode, ltsDsQcService.getCannotQcReasonList());
				if(StringUtils.equals(reasonCode, LtsConstant.QC_CANNOT_QC_REASON_SPECIAL_WAIVED)){
					qcStatus = LtsConstant.QC_STATUS_SPECIAL_WAIVED;
				}
				break;
			}
			case FAILED: {
				reasonCode = form.getQcFailedReason();
				reasonDesc = getLookupDesc(reasonCode, ltsDsQcService.getFailedReasonList());
				break;
			}
		}
		
		
		updateQc(form, userInfo, reasonCode, reasonDesc, qcStatus);
		/*if(form.getAction() == QcProcessAction.PASS && !StringUtils.equals(form.getQcStatus(),QcProcessAction.PASS.getCode())){
			updateQc(form, userInfo, reasonCode, reasonDesc, qcStatus);
		}*/
	
				
		if(form.getAction() == QcProcessAction.PASS_APPT || form.getAction() == QcProcessAction.APPT ){ //&& form.isMustQc()){
			return new ModelAndView(new RedirectView(passMustQcView + form.getOrderId()));
		}
		
		
		
		
		return new ModelAndView(new RedirectView(successRedirectView));
	}
	
	private void updateQc(LtsDsQcProcessDetailFormDTO form, BomSalesUserDTO userInfo, String reasonCode, String reasonDesc, 
			String qcStatus){
		String orderId = form.getOrderId();
		String qcDateTime = form.getQcDateTime();
		String qcFindings = form.getQcFindingDetail();
		String district = form.getOrderDistrict();
		String place = form.getOrderPlace();
		String user = userInfo.getUsername();
		String action = form.getAction().toString();
		
		if(ltsDsQcService.isQcProcessExist(orderId)){
			ltsDsQcService.updateQcProcess(orderId, qcDateTime, qcFindings, district, place, qcStatus, reasonCode, reasonDesc, user, action);
		}else{
			ltsDsQcService.insertQcProcess(orderId, qcDateTime, qcFindings, district, place, qcStatus, reasonCode, reasonDesc, user, action);
		}
	}
	

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		referenceData.put("cannotQcReasonList", ltsDsQcService.getCannotQcReasonList());
		referenceData.put("failedReasonList", ltsDsQcService.getFailedReasonList());
		return referenceData;
	}
	
	private String getLookupDesc(String code, LookupItemDTO[] lkups){
		for(LookupItemDTO lkup: lkups){
			if(StringUtils.equals(lkup.getItemKey(), code)){
				return (String)lkup.getItemValue();
			}
		}
		return null;
	}

	public LtsDsQcService getLtsDsQcService() {
		return ltsDsQcService;
	}

	public void setLtsDsQcService(LtsDsQcService ltsDsQcService) {
		this.ltsDsQcService = ltsDsQcService;
	}
	
}
