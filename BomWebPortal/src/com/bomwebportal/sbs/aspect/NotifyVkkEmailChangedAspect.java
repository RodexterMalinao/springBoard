package com.bomwebportal.sbs.aspect;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.sbs.service.VirtualPrepaidService;
import com.bomwebportal.service.OrdEmailReqService;
import com.bomwebportal.service.OrderEsignatureService.EmailReqResult;
import com.bomwebportal.service.OrderService;

@Aspect
public class NotifyVkkEmailChangedAspect {

	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private OrdEmailReqService ordEmailReqService;
	@Autowired
	private VirtualPrepaidService virtualPrepaidService;
	@Autowired
	private OrderService orderService;

	
	
	@Pointcut("execution(* com.bomwebportal.service.OrderEsignatureService+.resendEmailReq(String, String, .. , String )) && args(orderId,templateId, .. , createBy)")
	public void resendEmail(String orderId, String templateId, String createBy){}
	

	//@Around("resendEmail(orderId, templateId, createBy)")
	//public Object notifyVkkIfNeeded(ProceedingJoinPoint pjp, String orderId, String templateId) throws Throwable {
	@AfterReturning(pointcut="resendEmail(orderId, templateId, createBy)", returning="result")
	public void notifyVkkIfNeeded(String orderId, String templateId, String createBy, EmailReqResult result) throws Throwable {
		try {
			
			
			
			if ((result == EmailReqResult.SUCCESS && "RT010".equals(templateId))  ) {
				List<OrdEmailReqDTO> list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderId(orderId, templateId);
				OrdEmailReqDTO req = list.get(list.size()-1);
				OrdEmailReqDTO oldreq = list.get(list.size()-2); 
				String addr = req.getEmailAddr();
				String oldaddr = oldreq.getEmailAddr();
				
				if (! StringUtils.equalsIgnoreCase(addr, oldaddr)) {
					updateVkk(orderId, addr, createBy);
				}
			}
		}  catch (Throwable e) {
			logger.error("Failed to update virtual prepaid service after email resend", e);
		}
	}
	
	private void updateVkk(String orderId, String email, String createBy) {
		OrderDTO orderDTO = orderService.getOrder(orderId);
		String mrt = orderDTO.getMsisdn();
		virtualPrepaidService.updateEmail(orderId, mrt, email, createBy);
	}
	
}
