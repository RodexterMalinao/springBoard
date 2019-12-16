package com.bomwebportal.mob.ds.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.service.MnpService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class MobDsApproveController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());
	private OrderService orderService;
	private MnpService mnpService;
	
	public OrderService getOrderService() {
		return orderService;
	}
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	public MnpService getMnpService() {
		return mnpService;
	}
	public void setMnpService(MnpService mnpService) {
		this.mnpService = mnpService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		MnpDTO mnpDto = (MnpDTO)request.getSession().getAttribute("orderdetailMNP");
		if (mnpDto == null) {
			return new ModelAndView("ajax_mobDsApprove", "dsApproveMessage", "No MRT information");
		}
		
		String approveMessage = null;
		
		//Validate Service Request Date / MNP Cutover Date
		if ("Y".equals(mnpDto.getMnp()) || "A".equals(mnpDto.getMnp())) {
			//Check the year range
			Calendar currentDate = Calendar.getInstance();
			Calendar compareDate = Calendar.getInstance();

			compareDate.setTime(Utility.string2Date(mnpDto.getCutoverDateStr()));
			compareDate.add(Calendar.YEAR, -100);
			if(compareDate.after(currentDate)){
				approveMessage = "Invalid MNP Cutover Date";
			}
			
			List<String> results = new ArrayList<String>();
			try {
				 results = orderService.getFrozenWindow(mnpDto.getCutoverDateStr());
				
			} catch (Exception e) {
				logger.error("getFrozenWindow:", e);
			}
			
			if (results != null) {
				for (String s : results) {
					if (s.equals(mnpDto.getCutoverTime())) {
						approveMessage = "This Cut Over Time is frozen";
					}
				}
			}
			
			int currentTime = currentDate.get(Calendar.HOUR) * 100
					+ currentDate.get(Calendar.MINUTE);
		
			Calendar cutoverDate = Calendar.getInstance();
			cutoverDate.setTime(Utility.string2Date(mnpDto.getCutoverDateStr()));

			Calendar tPlus1Date = Calendar.getInstance();
			tPlus1Date.set(currentDate.get(Calendar.YEAR),
					currentDate.get(Calendar.MONTH),
					currentDate.get(Calendar.DATE) + 1);

			Calendar tPlus2Date = Calendar.getInstance();
			tPlus2Date.set(currentDate.get(Calendar.YEAR),
					currentDate.get(Calendar.MONTH),
					currentDate.get(Calendar.DATE) + 2);
			
			Calendar tPlus30Date = Calendar.getInstance();
			tPlus30Date.set(currentDate.get(Calendar.YEAR),
					currentDate.get(Calendar.MONTH),
					currentDate.get(Calendar.DATE));
			tPlus30Date.add(Calendar.DATE, 30);

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date cutover = java.sql.Date.valueOf(df.format(cutoverDate.getTime()));
			Date tPlus1 = java.sql.Date.valueOf(df.format(tPlus1Date.getTime()));
			Date tPlus2 = java.sql.Date.valueOf(df.format(tPlus2Date.getTime()));
			
			if (cutover.before(tPlus1)) {
				//System.out.println("cutover date before T + 1==>A");
				approveMessage = "Invalid MNP Cutover Date (submission time must at least before MNP date -1 11:45). \nNot allow to approve, please modify the MNP date first.";
			} else if (cutover.equals(tPlus1)) {
				if ((Calendar.AM == currentDate.get(Calendar.AM_PM) && currentTime < 1145)
						&& "0".equals(mnpDto.getCutoverTime())) {
					approveMessage = "Invalid MNP Cutover Date (submission time must before MNP date -2 17:45). \nNot allow to approve, please modify the MNP date first.";
				}
				if ((Calendar.AM == currentDate.get(Calendar.AM_PM) && currentTime >= 1145)
						|| Calendar.PM == currentDate.get(Calendar.AM_PM)) {
					if ("0".equals(mnpDto.getCutoverTime())) {
						approveMessage = "Invalid MNP Cutover Date (submission time must before MNP date -2 17:45). \nNot allow to approve, please modify the MNP date first.";
					} else {
						approveMessage = "Invalid MNP Cutover Date (submission time must before MNP date -1 11:45). \nNot allow to approve, please modify the MNP date first.";
					}
				}
			} else if (cutover.equals(tPlus2)) {
				if ((Calendar.PM == currentDate.get(Calendar.AM_PM) && currentTime >= 545)
						&& "0".equals(mnpDto.getCutoverTime())) {
					approveMessage = "Invalid MNP Cutover Date (submission time must before MNP date -2 17:45). \nNot allow to approve, please modify the MNP date first.";
				}
			}
		} else {
			Calendar currentDate = Calendar.getInstance();
			Calendar compareDate = Calendar.getInstance();
			Calendar serviceReqDate = Calendar.getInstance();
			serviceReqDate.setTime(Utility.string2Date(mnpDto.getServiceReqDateStr()));
			compareDate.set(serviceReqDate.get(Calendar.YEAR),
					serviceReqDate.get(Calendar.MONTH),
					serviceReqDate.get(Calendar.DATE));				
			if (compareDate.before(currentDate)) {
				approveMessage = "SRD passed. Not allow to approve. \nPlease modify a new SRD first.";
			} else {
				compareDate.add(Calendar.YEAR, -100);
				if(compareDate.after(currentDate)){
					approveMessage = "Invalid SRD Date.";
				}
			}
			
			//Validate New Number Status
			MnpDTO outMnpDto = mnpService.validateCnmMsindn(mnpDto.getNewMsisdn(), mnpDto.getShopCd());
			if (BomWebPortalConstant.CNM_STATUS_NORMAL == outMnpDto.getCnmStatus()) {
				if (StringUtils.isNotBlank(mnpDto.getReserveId())) {
					approveMessage = "Mobile number reserve status expired.";
				}
			} else if (BomWebPortalConstant.CNM_STATUS_RESERVE == outMnpDto.getCnmStatus()) {
				if (StringUtils.isBlank(outMnpDto.getReserveId()) ||
						StringUtils.isBlank(mnpDto.getReserveId()) ||
						!outMnpDto.getReserveId().equals(mnpDto.getReserveId())) {
					approveMessage = "Invalid Reserve ID.";
				}
			} else {
				approveMessage = "The mobile number is invalid.";
			}
		}
		
		return new ModelAndView("ajax_mobDsApprove", "dsApproveMessage", approveMessage);
	}
}
