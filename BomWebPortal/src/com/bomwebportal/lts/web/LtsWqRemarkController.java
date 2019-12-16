package com.bomwebportal.lts.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.form.LtsWqRemarkFormDTO;
import com.bomwebportal.lts.dto.order.RemarkDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSbRemarkHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.ServiceActionBase;

public class LtsWqRemarkController extends SimpleFormController {

	private ServiceActionBase remarkLtsService;
	private Locale locale;
	private MessageSource messageSource;
	
	private final String commandName = "ltsWqRemarkCmd";
	private final String viewName = "ltswqremark";
	private final String nextView = "ltswqremark.html?submit=true";
	
	public LtsWqRemarkController() {
		setCommandClass(LtsWqRemarkFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		setLocale(RequestContextUtils.getLocale(request));
		if (orderCapture == null) {
			orderCapture = LtsSessionHelper.getTerminateOrderCapture(request);
			if(orderCapture == null){
				return new ModelAndView(LtsConstant.ERROR_VIEW);
			}
		}
		return super.handleRequestInternal(request, response);
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		if(orderCapture == null){
			orderCapture = LtsSessionHelper.getTerminateOrderCapture(request);
		}
		LtsWqRemarkFormDTO form = orderCapture.getLtsWqRemarkForm();
		
		if (form == null || StringUtils.isBlank(form.getWqRemark())) {
			form = new LtsWqRemarkFormDTO();
			form.setWqRemark(initWqRemark(orderCapture));
			
			if(StringUtils.equals(orderCapture.getOrderType(), LtsConstant.ORDER_TYPE_SB_DISCONNECT)){
				if(orderCapture.getSbOrder() != null){
					ServiceDetailLtsDTO coreService = LtsSbOrderHelper.getLtsService(orderCapture.getSbOrder());
					if(coreService.getRemarks() != null && coreService.getRemarks().length > 0){
						StringBuilder sb = new StringBuilder();
						for(RemarkDetailLtsDTO remarkDtl: coreService.getRemarks()){
							if(StringUtils.equals(remarkDtl.getRmkType(), LtsConstant.REMARK_TYPE_APPROVL_REMARK)){
								sb.append(remarkDtl.getRmkDtl());
							}
						}
						if(sb.length() > 0){
							form.setWqRemark(sb.toString());
						}
					}
				}
			}
		}
		
		return form;
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		LtsWqRemarkFormDTO form = (LtsWqRemarkFormDTO) command;
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		if(orderCapture == null){
			orderCapture = LtsSessionHelper.getTerminateOrderCapture(request);
			orderCapture.setLtsWqRemarkForm(form);
			submitDisconnectWqRemarks(orderCapture.getSbOrder(), form, LtsSessionHelper.getBomSalesUser(request).getUsername());
			return new ModelAndView(new RedirectView(nextView));
		}
		orderCapture.setLtsWqRemarkForm(form);
		
		return new ModelAndView(new RedirectView(nextView)); 
	}
	
	private void submitDisconnectWqRemarks(SbOrderDTO sbOrder, LtsWqRemarkFormDTO form, String user){
		ServiceDetailLtsDTO coreService = LtsSbOrderHelper.getLtsService(sbOrder);
		String orderId = sbOrder.getOrderId();
		String dtlId = coreService.getDtlId();
				
		List<RemarkDetailLtsDTO> retainRemarkDtlList = new ArrayList<RemarkDetailLtsDTO>(); 
		List<RemarkDetailLtsDTO> oldApprovalRemarkDtlList = new ArrayList<RemarkDetailLtsDTO>(); 
		List<RemarkDetailLtsDTO> newApprovalRemarkDtlList = new ArrayList<RemarkDetailLtsDTO>(); 
		
		if(coreService.getRemarks() != null && coreService.getRemarks().length > 0){
			for(RemarkDetailLtsDTO remarkDtl: coreService.getRemarks()){
				if(StringUtils.equals(remarkDtl.getRmkType(), LtsConstant.REMARK_TYPE_APPROVL_REMARK)){
					oldApprovalRemarkDtlList.add(remarkDtl);
				}else{
					retainRemarkDtlList.add(remarkDtl);
				}
			}
		}
		
		for(RemarkDetailLtsDTO remarkDtl: oldApprovalRemarkDtlList){
			remarkDtl.setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
			remarkLtsService.doSave(remarkDtl, user, orderId, dtlId);
		}
		
		RemarkDetailLtsDTO[] approvalRemarkDtls = LtsSbRemarkHelper.createRemarkDetails(form.getWqRemark(), LtsConstant.REMARK_TYPE_APPROVL_REMARK);
		if(approvalRemarkDtls != null && approvalRemarkDtls.length > 0){
			for(RemarkDetailLtsDTO remarkDtl: approvalRemarkDtls){
				remarkDtl.setObjectAction(ObjectActionBaseDTO.ACTION_ADD);
				remarkLtsService.doSave(remarkDtl, user, orderId, dtlId);
				newApprovalRemarkDtlList.add(remarkDtl);
			}
		}
		
		retainRemarkDtlList.addAll(newApprovalRemarkDtlList);
		coreService.setRemarks(retainRemarkDtlList.toArray(new RemarkDetailLtsDTO[0]));
	}
	
	private String initWqRemark(OrderCaptureDTO orderCapture) {
		StringBuilder wqRemark = new StringBuilder();
		
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())) {
			wqRemark.append(messageSource.getMessage("lts.ltsWqRmk.eyeRet", new Object[] {}, this.locale)); // : e.g. DEL Acq / DEL Ret / eye Acq / eye Ret)
		}else if(LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(orderCapture.getOrderType())){
			if(StringUtils.isNotBlank(orderCapture.getLtsServiceProfile().getExistEyeType())){
				wqRemark.append(messageSource.getMessage("lts.ltsWqRmk.eyeDis", new Object[] {}, this.locale)); 
			}else{
				wqRemark.append(messageSource.getMessage("lts.ltsWqRmk.eyeDis", new Object[] {}, this.locale));
			}
		}else {
			wqRemark.append(messageSource.getMessage("lts.ltsWqRmk.eyeAcq", new Object[] {}, this.locale)); // : e.g. DEL Acq / DEL Ret / eye Acq / eye Ret)	
		}
		
		wqRemark.append("\n");
		wqRemark.append(messageSource.getMessage("lts.ltsWqRmk.salesChannel", new Object[] {}, this.locale)); //: e.g. Call Centre / DS / Retail
		
		if (orderCapture.getLtsSalesInfoForm() != null) {
			wqRemark.append(orderCapture.getLtsSalesInfoForm().getSalesChannel());	
		}
		
		wqRemark.append("\n");
		wqRemark.append(messageSource.getMessage("lts.ltsWqRmk.salesTeam", new Object[] {}, this.locale)); // e.g. XXXX (Salesman code / Shop code / Source code)
		
		if (orderCapture.getLtsSalesInfoForm() != null) {
			wqRemark.append(orderCapture.getLtsSalesInfoForm().getSalesTeam());
		}
		
		wqRemark.append("\n");
		wqRemark.append(messageSource.getMessage("lts.ltsWqRmk.nameOfSales", new Object[] {}, this.locale)); //: e.g. Chan Tai Man
		
		if (orderCapture.getLtsSalesInfoForm() != null) {
			wqRemark.append(orderCapture.getLtsSalesInfoForm().getStaffName());
		}
		
		wqRemark.append("\n");
		wqRemark.append(messageSource.getMessage("lts.ltsWqRmk.compDate", new Object[] {}, this.locale)); 
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
		wqRemark.append(dateFormat.format(new Date()));
		
		wqRemark.append("\n");
		wqRemark.append("DN: "); //: e.g. 2XXX XXXX
		wqRemark.append(StringUtils.removeStart(orderCapture.getLtsServiceProfile().getSrvNum(), "0000"));
		wqRemark.append("\n");
		wqRemark.append(messageSource.getMessage("lts.ltsWqRmk.custName", new Object[] {}, this.locale)); //: e.g. Lee Mei Wah
		wqRemark.append(orderCapture.getLtsServiceProfile().getPrimaryCust().getTitle() + " " +
				orderCapture.getLtsServiceProfile().getPrimaryCust().getLastName() + " " +  
				orderCapture.getLtsServiceProfile().getPrimaryCust().getFirstName());
		wqRemark.append("\n");
		wqRemark.append(messageSource.getMessage("lts.ltsWqRmk.curPlanDtl", new Object[] {}, this.locale));
		wqRemark.append("\n");
		wqRemark.append(messageSource.getMessage("lts.ltsWqRmk.totalAPRU", new Object[] {}, this.locale)); // : e.g. $164(Term Plan) + $27(Voice Pkg) + $18(Lifestyle Pack) + $18(1TV) = $227
		wqRemark.append("\n");
		wqRemark.append(messageSource.getMessage("lts.ltsWqRmk.delEyeTermPlan", new Object[] {}, this.locale)); // : e.g. 1N4I(eye2A Basic 1C-100mins) x 24M (TP Period: YYYYMMDD - YYYYMMDD) 
		wqRemark.append("\n");
		wqRemark.append(messageSource.getMessage("lts.ltsWqRmk.vasTermPlan", new Object[] {}, this.locale)); 
		wqRemark.append("\n");
		// : e.g. 1LAD(Voice Pkg) x 18M (TP Period: YYYYMMDD - YYYYMMDD) 
		// : e.g. 1N13(Lifestyle Pack) x 24M (TP Period: YYYYMMDD - YYYYMMDD) 
		// e.g. 1N24(1TV) x 24M (TP Period: YYYYMMDD - YYYYMMDD)
		wqRemark.append(messageSource.getMessage("lts.ltsWqRmk.yearOfTenure", new Object[] {}, this.locale)); // : e.g. 22 months
		wqRemark.append(orderCapture.getLtsServiceProfile().getLtsSrvTenure() + " months");
		wqRemark.append("\n");
		wqRemark.append(messageSource.getMessage("lts.ltsWqRmk.custReq", new Object[] {}, this.locale)); // : e.g. fallback to DEL, waive penalty, etc
		wqRemark.append("\n"); 
		wqRemark.append(messageSource.getMessage("lts.ltsWqRmk.salesInvReq", new Object[] {}, this.locale)); //: XXXXX (for Call Centre, should provide call log together)
		wqRemark.append("\n");		 
		wqRemark.append(messageSource.getMessage("lts.ltsWqRmk.fieldSrvInv", new Object[] {}, this.locale)); //: XXXXX (for product related queries, should attach FS email as support doc)
		wqRemark.append("\n");
		wqRemark.append(messageSource.getMessage("lts.ltsWqRmk.otherSupDoc", new Object[] {}, this.locale)); //: e.g. death certificate
		return wqRemark.toString();
	}

	public ServiceActionBase getRemarkLtsService() {
		return remarkLtsService;
	}

	public void setRemarkLtsService(ServiceActionBase remarkLtsService) {
		this.remarkLtsService = remarkLtsService;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
