package com.bomwebportal.mob.ccs.web;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.MobCcsPaymentNewTransUI;
import com.bomwebportal.mob.ccs.dto.MobCcsPaymentUpfrontDTO;
import com.bomwebportal.mob.ccs.service.MobCcsPaymentAdminService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.util.Utility;

public class MobCcsPaymentNewTransController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());
	private MobCcsPaymentAdminService mobCcsPaymentAdminService;

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		logger.info("MobCcsPaymentNewTransController formBackingObject called");

		MobCcsPaymentNewTransUI paymentNewTrans = (MobCcsPaymentNewTransUI) MobCcsSessionUtil
				.getSession(request, "paymentNewTrans");

		if (paymentNewTrans == null) {
			paymentNewTrans = new MobCcsPaymentNewTransUI();
			paymentNewTrans.setPayMethodList(mobCcsPaymentAdminService
					.getCodeLkupDTOALL("PAY_METHOD"));
			BomSalesUserDTO user = (BomSalesUserDTO) request.getSession()
					.getAttribute("bomsalesuser");
			if (user != null) {
				paymentNewTrans.setLastUpdateBy(user.getSalesCd());
			}
		} else {
			if ("QUERY".equalsIgnoreCase(paymentNewTrans.getActionType())) {
				if (paymentNewTrans.getOrder_id() != null
						&& paymentNewTrans.getOrder_id().trim().length() > 0) {
					refreshUpfrontPayment(paymentNewTrans);
					
				} else {
					paymentNewTrans.setMrt(null);
					paymentNewTrans.setCc_type(null);
					paymentNewTrans.setCc_num(null);
					paymentNewTrans.setCc_hold_name(null);
					paymentNewTrans.setCc_exp_date(null);
					paymentNewTrans.setPay_method(null);
					paymentNewTrans.setAd_amount(null);
					paymentNewTrans.setUp_amount(null);
					paymentNewTrans.setOut_amout(null);
					paymentNewTrans.setMsg("Order Id cannot be null");
				}

			} else if ("INSERT".equalsIgnoreCase(paymentNewTrans
					.getActionType())) {
				if (paymentNewTrans.getMrt() != null
						&& paymentNewTrans.getMrt().trim().length() > 0) {
					paymentNewTrans.setTransDate(Utility
							.string2Date(paymentNewTrans.getTransDateStr()));
					mobCcsPaymentAdminService
							.insertMobCcsPaymentNewTransUI(paymentNewTrans);
					refreshUpfrontPayment(paymentNewTrans);
					paymentNewTrans.setRef_no(null);
					paymentNewTrans.setTransDate(null);
					paymentNewTrans.setTransDateStr(null);
					paymentNewTrans.setPay_comb(null);
					paymentNewTrans.setPay_amount(null);
					BomSalesUserDTO user = (BomSalesUserDTO) request
							.getSession().getAttribute("bomsalesuser");
					if (user != null) {
						paymentNewTrans.setLastUpdateBy(user.getSalesCd());
					}
					paymentNewTrans.setMsg("Record saved successfully");
				} else {
					paymentNewTrans.setMsg("Please retrieve a record first");
				}
			}
		}

		MobCcsSessionUtil.setSession(request, "paymentNewTrans",
				paymentNewTrans);
		return paymentNewTrans;
	}
	
	private void refreshUpfrontPayment(MobCcsPaymentNewTransUI paymentNewTrans){
		List<MobCcsPaymentUpfrontDTO> upfrontPaymentList = mobCcsPaymentAdminService
				.getMobCcsUpfrontPaymentDTOList(paymentNewTrans
						.getOrder_id());

		if (upfrontPaymentList != null
				&& upfrontPaymentList.size() == 1) {
			paymentNewTrans.setMrt(upfrontPaymentList.get(0)
					.getMrt());
			paymentNewTrans.setCc_type(upfrontPaymentList.get(0)
					.getCc_type());
			paymentNewTrans.setCc_num(upfrontPaymentList.get(0)
					.getCc_num());
			paymentNewTrans.setCc_hold_name(upfrontPaymentList.get(
					0).getCc_hold_name());
			paymentNewTrans.setCc_exp_date(upfrontPaymentList
					.get(0).getCc_exp_date());
			paymentNewTrans.setPay_method(upfrontPaymentList.get(0)
					.getPay_method());
			paymentNewTrans.setAd_amount(upfrontPaymentList.get(0)
					.getAd_amount());
			paymentNewTrans.setUp_amount(upfrontPaymentList.get(0)
					.getUp_amount());
			paymentNewTrans.setOut_amout(upfrontPaymentList.get(0)
					.getOut_amout());
			paymentNewTrans.setMsg(null);			
		} else {
			paymentNewTrans.setMrt(null);
			paymentNewTrans.setCc_type(null);
			paymentNewTrans.setCc_num(null);
			paymentNewTrans.setCc_hold_name(null);
			paymentNewTrans.setCc_exp_date(null);
			paymentNewTrans.setPay_method(null);
			paymentNewTrans.setAd_amount(null);
			paymentNewTrans.setUp_amount(null);
			paymentNewTrans.setOut_amout(null);
			if (upfrontPaymentList != null
					&& upfrontPaymentList.size() > 1) {
				paymentNewTrans
						.setMsg("Error: No. of retrieved records is more than one");
			} else if (upfrontPaymentList == null
					|| upfrontPaymentList != null
					&& upfrontPaymentList.size() == 0) {
				paymentNewTrans.setMsg("No Record Found.");
			}
		}		
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {

		logger.info("MobCcsPaymentNewTransController is called!");

		return new ModelAndView(new RedirectView("mobccspaymentnewtrans.html"));
	}

	public MobCcsPaymentAdminService getMobCcsPaymentAdminService() {
		return mobCcsPaymentAdminService;
	}

	public void setMobCcsPaymentAdminService(
			MobCcsPaymentAdminService mobCcsPaymentAdminService) {
		this.mobCcsPaymentAdminService = mobCcsPaymentAdminService;
	}

}