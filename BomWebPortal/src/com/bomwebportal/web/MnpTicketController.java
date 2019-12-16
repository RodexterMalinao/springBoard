package com.bomwebportal.web;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bomwebportal.util.Utility;
import com.pccw.cts.service.CtsService;

@Controller
public class MnpTicketController {

	@Autowired
	private CtsService mnpTicketService ;
	
	
	
	public CtsService getMnpTicketService() {
		return mnpTicketService;
	}



	public void setMnpTicketService(CtsService mnpTicketService) {
		this.mnpTicketService = mnpTicketService;
	}



	@RequestMapping(value="/mnp/ticket", method=RequestMethod.POST)
	public String getTicket(String ac, String pin, String dn , String date /*dd/MM/yyyy*/, String session, Model model) {
		try {
			// check T+1/T+2,  sessions , 30 days  ...
			// as in mnp validator
			Calendar calnow = Calendar.getInstance();
			Calendar calnow0 = DateUtils.truncate(calnow, Calendar.DATE);
			Date now = calnow.getTime();
			
			Date mnpDate0 = Utility.string2Date(date);
			if (mnpDate0 == null) {
				throw new IllegalArgumentException("Either invalid cutover date/time or MNP ticket can be obtained within 30 days only.");
			}
			
			Date now0 = calnow0.getTime();
			
			Date tPlus1Date = DateUtils.addDays(now0, 1);
			Date tPlus2Date = DateUtils.addDays(now0, 2);
			Date tPlus30Date = DateUtils.addDays(now0, 30);
		
			Calendar cal1145 = DateUtils.truncate(calnow, Calendar.DATE);
			cal1145.set(Calendar.HOUR_OF_DAY, 11);
			cal1145.set(Calendar.MINUTE, 45);
			Date t1145 = cal1145.getTime();
			
			Calendar cal1745 = DateUtils.truncate(calnow, Calendar.DATE);
			cal1745.set(Calendar.HOUR_OF_DAY, 17);
			cal1745.set(Calendar.MINUTE, 45);
			Date t1745 = cal1745.getTime();
		
			if (mnpDate0.before(tPlus1Date)) {
				throw new IllegalArgumentException("Either invalid cutover date/time or MNP ticket can be obtained within 30 days only.");
			}
			
			if (mnpDate0.equals(tPlus1Date)) {
				if (  ! ( now.before(t1145) && "1".equals(session) ) ) {
					throw new IllegalArgumentException("Either invalid cutover date/time or MNP ticket can be obtained within 30 days only.");
				}
			}
			
			if (mnpDate0.equals(tPlus2Date)) {
				if (  ! ( now.before(t1745) && "1".equals(session) ) ) {
					throw new IllegalArgumentException("Either invalid cutover date/time or MNP ticket can be obtained within 30 days only.");
				}
			}
			
			if ( mnpDate0.after(tPlus30Date)) {
				throw new IllegalArgumentException("Either invalid cutover date/time or MNP ticket can be obtained within 30 days only.”");
			}



			String mmdd = DateFormatUtils.format(mnpDate0, "MMdd");
			//System.out.println("mmdd="+mmdd);
			
			String ticket = mnpTicketService.getTicket(ac, pin, dn, mmdd, session);
			model.addAttribute("ticket", ticket);
		} catch (Exception e) {
			//e.printStackTrace();
			model.addAttribute("error", e.getMessage());
		}
		return "ajax_service";
	}

}
