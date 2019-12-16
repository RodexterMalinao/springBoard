package com.bomwebportal.ims.listener;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dao.ReleaseLoginIDDAO;
import com.bomwebportal.ims.dto.ui.ImsLoginIDUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.ImsAppointmentService;
import com.bomwebportal.ims.service.ImsOrderService;
import com.bomwebportal.ims.service.ReleaseLoginIDServiceImpl;
import com.bomwebportal.ims.wsclient.ImsWSClient;
import com.bomwebportal.service.MobilePINServiceImpl.MobileOffer;


import com.bomwebportal.service.MobilePINService;



public class SessionTimeoutListener implements HttpSessionListener {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ReleaseLoginIDServiceImpl releaseLoginIDService;
	private ImsWSClient client;
	private ReleaseLoginIDDAO releaseLoginIDDao;
	private ImsAppointmentService appointmentService;
	private MobilePINService mobilePINService;
	private ImsOrderService imsOrderService;
	


	public SessionTimeoutListener(){
		super();
		releaseLoginIDService = new ReleaseLoginIDServiceImpl();
	}
	
	public ReleaseLoginIDServiceImpl getReleaseLoginIDService() {
		return releaseLoginIDService;
	}

	public void setReleaseLoginIDService(
			ReleaseLoginIDServiceImpl releaseLoginIDService) {
		this.releaseLoginIDService = releaseLoginIDService;
	}
	
	public ImsWSClient getClient() {
		return client;
	}

	public void setClient(ImsWSClient client) {
		this.client = client;
	}

	public ReleaseLoginIDDAO getReleaseLoginIDDao() {
		return releaseLoginIDDao;
	}

	public void setReleaseLoginIDDao(ReleaseLoginIDDAO releaseLoginIDDao) {
		this.releaseLoginIDDao = releaseLoginIDDao;
	}

	private void obtainDAO(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
		//releaseLoginIDDao = (ReleaseLoginIDDAO) ctx.getBean("releaseLoginIDDao");
		client = (ImsWSClient)ctx.getBean("bomWsClientIms");
/*		if(releaseLoginIDDao == null){
			System.out.println("releaseLoginIDDao Not Found");
		}
*/
		if(client == null){
			System.out.println("Client Not Found");
		}
	}
	
	private void getAppointmentSerivce(HttpSessionEvent event){
		HttpSession session = event.getSession();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
		appointmentService = (ImsAppointmentService)ctx.getBean("appointmentImsService");
	}

	
	private void getMobilePINService(HttpSessionEvent event){
		HttpSession session = event.getSession();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
		mobilePINService = (MobilePINService)ctx.getBean("mobilePINService");
	}
	
	private void getImsOrderService(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
		imsOrderService = (ImsOrderService) ctx.getBean("imsOrderService");
	}
	
	//@Override	
	public void sessionCreated(HttpSessionEvent arg0) {
		/*if (releaseLoginIDDao == null)
			obtainDAO(arg0);
		releaseLoginIDService.setReleaseLoginIDDao(releaseLoginIDDao);
		System.out.println("sessionCreated - add one session into counter");*/
	}
	 
	//@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		System.out.println("sessionDestroyed");
/*		if (releaseLoginIDDao == null)
			obtainDAO(arg0);
		releaseLoginIDService.setReleaseLoginIDDao(releaseLoginIDDao);*/
		if (client == null)
			obtainDAO(arg0);
		releaseLoginIDService.setClient(client);
		//System.out.println("sessionCreated - add one session into counter");
/*		String docType = (String)arg0.getSession().getAttribute("imsDocType");
		String idDocNum = (String)arg0.getSession().getAttribute("imsIdDocNum");
		String loginName = (String)arg0.getSession().getAttribute("imsLoginName");
		String oldLoginName = (String)arg0.getSession().getAttribute("imsOldLoginName");
		String submitTag = "";
		if(arg0.getSession().getAttribute("imsSubmitTag") != null){
			submitTag = (String)arg0.getSession().getAttribute("imsSubmitTag");
		}
*/
		int releaseResult = -1;
		
/*		logger.info(docType);
		logger.info(idDocNum);
		logger.info(loginName);
*/
//		if((docType != null && !docType.equals("")) && (idDocNum != null && !idDocNum.equals(""))){
//			if(!submitTag.equals("SO") && !submitTag.equals("S") && !submitTag.equals("R")){
		
		if(arg0.getSession().getAttribute("imsLoginIdUi") != null){
			ImsLoginIDUI imsLoginIdUi = (ImsLoginIDUI)arg0.getSession().getAttribute("imsLoginIdUi");
			if((imsLoginIdUi.getLoginName() != null && !("").equals(imsLoginIdUi.getLoginName()))
					&& (imsLoginIdUi.getIdDocNum() != null && !("").equals(imsLoginIdUi.getIdDocNum()))
							&& (imsLoginIdUi.getDocType() != null && !("").equals(imsLoginIdUi.getDocType()))){
						releaseResult = client.releaseLoginID(imsLoginIdUi.getLoginName(), imsLoginIdUi.getIdDocNum(), imsLoginIdUi.getDocType());
			}
		}
		
		//release appointment booking if any
		if(arg0.getSession().getAttribute(ImsConstants.IMS_APPOINTMENT_SERIAL)!= null){
			logger.info("find not yet released appointment detail in session");
			if(appointmentService==null){
				getAppointmentSerivce(arg0);
			}
			appointmentService.cancelReservedTimeSlot(
					(String)arg0.getSession().getAttribute(ImsConstants.IMS_APPOINTMENT_SERIAL));
		}
		
		if(arg0.getSession().getAttribute("imsMobileOfferUiList") != null){
			List<MobileOffer> imsMobileOfferUiList = (List<MobileOffer>) arg0.getSession().getAttribute("imsMobileOfferUiList");
			if(mobilePINService==null){
				getMobilePINService(arg0);
			}
			MobileOffer imsMobileOfferUi = new MobileOffer();
			if(arg0.getSession().getAttribute(ImsConstants.IMS_ORDER) != null){
				OrderImsUI sessionOrder = (OrderImsUI)arg0.getSession().getAttribute(ImsConstants.IMS_ORDER);
				if (imsMobileOfferUiList != null){
					for(int i= 0; i<imsMobileOfferUiList.size(); i++){
						imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(imsMobileOfferUiList.get(i).mrt, imsMobileOfferUiList.get(i).pin, sessionOrder.getCreateBy(), sessionOrder.getOrderId(), "NONONLINE");
						logger.info("release old number: " + imsMobileOfferUiList.get(i).mrt + " , old pin: " + imsMobileOfferUiList.get(i).pin);
					}
				}else{
					for(int i= 0; i<imsMobileOfferUiList.size(); i++){
						imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(imsMobileOfferUiList.get(i).mrt, imsMobileOfferUiList.get(i).pin);
						logger.info("release old number: " + imsMobileOfferUiList.get(i).mrt + " , old pin: " + imsMobileOfferUiList.get(i).pin);
					}
				}
			}
		}
		
		if (arg0.getSession().getAttribute(ImsConstants.IMS_ORDER) != null) {
			OrderImsUI sessionOrder = (OrderImsUI)arg0.getSession().getAttribute(ImsConstants.IMS_ORDER);
			if ("NTV-A".equals(sessionOrder.getOrderType()) && "08".equals(sessionOrder.getOrderStatus())) {
				if (imsOrderService == null) {
					getImsOrderService(arg0);
				}
				boolean updateSuccess = imsOrderService.updateOrderReasonCd_R008(sessionOrder.getOrderId(), "IMSAUTO");
			}
		}
	}
}