package com.bomwebportal.ims.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.utils.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.AllDocDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.AllDocDTO.Type;
import com.bomwebportal.exception.ImsMobilePinException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.AppointmentPermitPropertyDtlDTO;
import com.bomwebportal.ims.dto.AppointmentTimeSlotDTO;
import com.bomwebportal.ims.dto.ImsServiceSrdDTO;
import com.bomwebportal.ims.dto.ImsSupportDocDTO;
import com.bomwebportal.ims.dto.ui.AppointmentUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.ImsAppointmentService;
import com.bomwebportal.ims.service.ImsOrderDetailService;
import com.bomwebportal.service.OrdDocService;
import com.bomwebportal.service.SupportDocService;
import com.bomwebportal.util.DateUtils;
import com.bomwebportal.util.Utility;
import com.google.gson.Gson;
import com.bomwebportal.service.MobilePINServiceImpl.MobileOffer;
import com.bomwebportal.service.MobilePINService;
public class ImsAppointmentController extends SimpleFormController{
	
	private ImsAppointmentService appointmentService;
	private ImsOrderDetailService imsOrderDetailService;
	private Gson gson = new Gson();
	private MobilePINService mobilePINService;
	
    public MobilePINService getMobilePINService() {
		return mobilePINService;
	}

	public void setMobilePINService(MobilePINService mobilePINService) {
		this.mobilePINService = mobilePINService;
	}
	// add by steven
	private OrdDocService ordDocService;
	public void setOrdDocService(OrdDocService ordDocService) {
		this.ordDocService = ordDocService;
	}

	public OrdDocService getOrdDocService() {
		return ordDocService;
	}
	private SupportDocService supportDocService;
	public SupportDocService getSupportDocService() {
		return supportDocService;
	}
	public void setSupportDocService(SupportDocService supportDocService) {
		this.supportDocService = supportDocService;
	}
	// add by steven end
	
	protected Object formBackingObject(HttpServletRequest request) throws ServletException{
		logger.debug("formBackingObject");
		String Locale = RequestContextUtils.getLocale(request).toString().contains("en")?"EN":"CHI";
		//temp use
//		if(request.getSession().getAttribute(ImsConstants.IMS_ORDER)==null){
//			OrderImsUI order =  DevService.getDummyOrder();
//			//order.getInstallAddress().setBlacklistInd("Y");
//			order.getCustomer().setBlacklistInd("N");
//			order.getInstallAddress().setSerbdyno("227790");	
//			//order.getInstallAddress().setSerbdyno("227464");
//			order.getInstallAddress().setAddrInventory(new AddrInventoryDTO());
//			order.getInstallAddress().getAddrInventory().setTechnology("ADSL");
//			//order.getInstallAddress().getAddrInventory().setResourceShortage("Y");
//			order.getInstallAddress().setAreaCd("NT");
//			order.getInstallAddress().setDistNo("222");			
//			SubscribedItemUI[] items = new SubscribedItemUI[1];
//			items[0] = new SubscribedItemUI();
//			items[0].setType(ImsConstants.IMS_PRE_INSTALL_ITEM_TYPE);
//			order.setSubscribedItems(items);
//			order.setShopCd("TP1");
//			order.setOrderStatus(null);
//			order.getAppointment().setSerialNum(null);
//			order.setFixedLineNo(null);
//			request.getSession().setAttribute(ImsConstants.IMS_ORDER, order);								
//		}				
		
		OrderImsUI order = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		
		logger.debug("order action:"+order.getOrderActionInd());
		logger.debug("sb:"+order.getInstallAddress().getSerbdyno());
		logger.debug("is blacklist addr:"+order.getInstallAddress().getBlacklistInd());
		logger.debug("is blacklist cust:"+order.getCustomer().getBlacklistInd());
		logger.debug("fix line:"+order.getFixedLineNo());
		logger.debug("technology:"+order.getInstallAddress().getAddrInventory().getTechnology());
		logger.debug("resource shortage:"+order.getInstallAddress().getAddrInventory().getResourceShortage());
		logger.debug("subscribed items count:"+order.getSubscribedItems().length);
		logger.debug("AppntStartDate:"+order.getAppointment().getAppntStartDate());
		logger.debug("AppntEndDate:"+order.getAppointment().getAppntEndDate());
		logger.debug("TargetInstallDate:"+order.getAppointment().getTargetInstallDate());
		logger.debug("TimeSlot:"+order.getAppointment().getTimeSlot());
		logger.debug("TimeSlotDisplay:"+order.getAppointment().getTimeSlotDisplay());
		
		String i_has_bb_srv = "Y", i_has_nowtv_srv = "N", mismatch ="N", fsPrepay = "N", fsInd = "N", i_ride_on_fsa = "";
		String isDS = (Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)?"Y":"N";
		
		if("Y".equals(order.getCustomer().getExistingCustomerConflictWithName())) mismatch = "Y"; 
		
		if(order.getcOrderDTO()!=null) i_ride_on_fsa = order.getcOrderDTO().getO_related_fsa();
		
		ImsServiceSrdDTO srdDto = appointmentService.getServiceSrdComparedWithBMO(
				order.getInstallAddress().getBlacklistInd(), 
				order.getCustomer().getBlacklistInd(), 
				(order.getFixedLineNo()!=null && !order.getFixedLineNo().equals(""))?"Y":"N", 
				null,
				order.getInstallAddress().getSerbdyno()
				,order.getInstallAddress().getUnitNo(), order.getInstallAddress().getFloorNo(), order.getIsCC(), order.getIsPT(), isDS, 
				i_has_bb_srv, i_has_nowtv_srv, mismatch, fsPrepay, fsInd, null, i_ride_on_fsa, "", order.getSupremeFsInd());

		logger.debug("srdDto: "+new Gson().toJson(srdDto));
		
//		Date EarliestSrd = appointmentService.getEstimatedSRD(
//				order.getInstallAddress().getBlacklistInd(),
//				order.getCustomer().getBlacklistInd(),
//				(order.getFixedLineNo()!=null && !order.getFixedLineNo().equals(""))?"Y":"N",
//				null, 
//				order.getInstallAddress().getSerbdyno(), 
//				order.getInstallAddress().getAddrInventory().getResourceShortage(),
//				order.getInstallAddress().getAddrInventory().getTechnology()
//				);
//		
		AppointmentUI appointment = order.getAppointment();
		/*
		appointment.setBmoRmk(srdDto.getBmoRmk());
		order.setBmoAlertRemarks(srdDto.getBmoAlert());
		*/
		//appointment.setAdditionRemarks(order.getRemarks()[0].getRmkDtl());
		appointment.setAdditionRemarks(order.getInstallationRemark());
		if("Y".equals(order.isHos()) || "Y".equals(order.isPh())){
			appointment.setHosOrPhInd("Y");
		}else{
			appointment.setHosOrPhInd("N");
		}
		
		if("Y".equals(order.isPrivateHousing())){
			appointment.setPrivateInd("Y");
		}else{
			appointment.setPrivateInd("N");
		}
		
		Date EarliestSrd = null;
		
		for (int j=0; j < srdDto.getServiceDetailList().size(); j++) {
			if (srdDto.getServiceDetailList().get(j).getTechnology().equals(
					order.getInstallAddress().getAddrInventory().getTechnology()) )  {
				if (srdDto.getServiceDetailList().get(j).getTechnologySupported().equals("Y") &&
						srdDto.getServiceDetailList().get(j).getIsDeadCase().equals("N")) {
					EarliestSrd = srdDto.getServiceDetailList().get(j).getEstEarliestSrd();
					appointment.setEstSrdReason(srdDto.getServiceDetailList().get(j).getRtnDesc());
					appointment.setBmoRmk(srdDto.getServiceDetailList().get(j).getBmoRmk());
					order.setBmoAlertRemarks(srdDto.getServiceDetailList().get(j).getBmoAlert());
					appointment.setBmoLeadDay(srdDto.getServiceDetailList().get(j).getBmoLeadDay());
					break;
				}				
			} 			
		}
				
		//System.out.println(order.getOrderStatus());//temp use
		if(ImsConstants.IMS_ORDER_STATUS_CANCELLED.equals(order.getOrderStatus())){
			appointment.setActionInd("R");
		}
		
//		if("Y".equals(order.getInstallAddress().getBlacklistInd()) ||
//				"Y".equals(order.getCustomer().getBlacklistInd()) ||
//				"Y".equals(order.getInstallAddress().getAddrInventory().getResourceShortage())){
//			appointment.setBookNotAllowed("Y");
//		}else{
//			appointment.setBookNotAllowed("N");
//		}
		
		appointment.setResourceInd(order.getInstallAddress().getAddrInventory().getResourceShortage());
		
		if("Y".equals(order.getInstallAddress().getBlacklistInd()) || "Y".equals(order.getCustomer().getBlacklistInd())){
			appointment.setBlackListInd("Y");
		}else{
			appointment.setBlackListInd("N");
		}
		/*
		if("Y".equals(appointment.getBlackListInd()) || "Y".equals(appointment.getResourceInd())){
			appointment.setBookNotAllowed("Y");
		}else{
			appointment.setBookNotAllowed("N");
		}*/
		appointment.setBookNotAllowed(order.isBookingNotAllowed());
		
//		if(order.isPreinstallation().equals("Y")){
		if("Y".equalsIgnoreCase(order.getPreInstallInd())||"Y".equalsIgnoreCase(order.getPreUseInd())){
			logger.debug("pre-install order detected");
			appointment.setPreInstOrder("Y");
		}else{
			appointment.setPreInstOrder("N");
			order.setTargetCommDate(null);
			request.getSession().setAttribute(ImsConstants.IMS_ORDER, order);	
		}		
		
		//temp use
		//Date EarliestSrd = new GregorianCalendar(2011,11,12).getTime();
		
		appointment.setEstimatedSrd(Utility.date2String(EarliestSrd,"yyyy/MM/dd"));
												
		//AppointmentTimeSlotDTO[] timeSlots = appointmentService.getTimeSlot(
		//		order.getInstallAddress(), EarliestSrd);

		if(appointment.getAppntStartDate()!=null){
			appointment.setTimeSlot(
				Utility.date2String(appointment.getAppntStartDate(), "HH:mm")+"-"+
				Utility.date2String(appointment.getAppntEndDate(), "HH:mm"));
			if(appointment.getBookNotAllowed().equals("Y")){
				appointment.setTargetInstallDate(
						Utility.date2String(order.getSrvReqDate(),"yyyy/MM/dd"));
			}else{
				appointment.setTargetInstallDate(
						Utility.date2String(appointment.getAppntStartDate(),"yyyy/MM/dd"));
			}
		}else{
			if(appointment.getTargetInstallDate()==null){
				appointment.setTargetInstallDate(appointment.getEstimatedSrd());
			}
		}

		if(order.getTargetCommDate()!=null){
			appointment.setTargetCommDate(
					Utility.date2String(order.getTargetCommDate(),"yyyy/MM/dd"));
		}
		
		GregorianCalendar _apptDate = new GregorianCalendar(
				Integer.parseInt(appointment.getTargetInstallDate().substring(0, 4)),
				Integer.parseInt(appointment.getTargetInstallDate().substring(5, 7))-1,
				Integer.parseInt(appointment.getTargetInstallDate().substring(8, 10)));
		
		AppointmentTimeSlotDTO[] timeSlots = appointmentService.getTimeSlot(
				order.getInstallAddress(), _apptDate.getTime(), order.getSupremeFsInd());
		
		for(int i=0; i<timeSlots.length; i++){
			if(timeSlots[i].getErrorMsg() == null){
				timeSlots[i].setErrorMsg("");
			}
			if(timeSlots[i].getErrorMsg() != null && !("").equals(timeSlots[i].getErrorMsg())){
				appointment.setErrorMsg(timeSlots[i].getErrorMsg());
			}
			logger.debug("time slot:"+timeSlots[i].getTimeSlot());
			logger.debug("errorMsg:"+timeSlots[i].getErrorMsg());
		}
		
		request.setAttribute("timeSlots", timeSlots);
		
		
		//if ( "Y".equals(order.getIsCC()) || user.getChannelId() == 12 || user.getChannelId() == 13 )
		//{
			if ( !"BS".equals(order.getCustomer().getIdDocType()) ){
				if(order.getAppointment().getInstContactName() == null || order.getAppointment().getInstContactName().isEmpty() )
				appointment.setInstContactName(order.getCustomer().getLastName() + " " + order.getCustomer().getFirstName());
			}
			else
				appointment.setInstContactName(order.getCustomer().getFirstName());
			
			if ( (order.getCustomer().getContact().getContactPhone() != null && !order.getCustomer().getContact().getContactPhone().isEmpty()) 
				 && 
				 ( (order.getAppointment().getInstSmsNum() == null || order.getAppointment().getInstSmsNum().isEmpty())	//1.update instSmsNum if it is null
				||
				(order.getOldMobileNum() != null && !order.getOldMobileNum().isEmpty() 			//2.update instSmsNum if it is the same as contactphone
				&& order.getAppointment().getInstSmsNum()!=null && !order.getAppointment().getInstSmsNum().isEmpty() 
				&& order.getOldMobileNum().equals(order.getAppointment().getInstSmsNum()))
				)){
				appointment.setInstSmsNum(
						order.getCustomer().getContact().getContactPhone().length() >= 8 ?
						order.getCustomer().getContact().getContactPhone().substring(0, 8):
						order.getCustomer().getContact().getContactPhone()
						);
				order.setOldMobileNum(
						order.getCustomer().getContact().getContactPhone().length() >= 8 ?
						order.getCustomer().getContact().getContactPhone().substring(0, 8):
						order.getCustomer().getContact().getContactPhone()
						);
			}
			
			if ( order.getCustomer().getContact().getOtherPhone() != null && !order.getCustomer().getContact().getOtherPhone().isEmpty()
					&& ( order.getAppointment().getInstContactNum() == null || order.getAppointment().getInstContactNum().isEmpty() )
			)
				appointment.setInstContactNum(
						order.getCustomer().getContact().getOtherPhone().length() >= 8 ?
						order.getCustomer().getContact().getOtherPhone().substring(0, 8):
						order.getCustomer().getContact().getOtherPhone()
						);
		//}
		
		
		//prepare lookup list
		Entry<String, String>[] slist;
		
		if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
			slist = imsOrderDetailService.getLookUpMapByLocale(LookupTableBean.getInstance().getImsDSSuspendLookupMap(),Locale).entrySet().toArray(new Entry[0]);
		}
		else if ( !"Y".equals(order.getIsPT())  ) 
		{
			slist = imsOrderDetailService.getLookUpMapByLocale(LookupTableBean.getInstance().getImsSuspendLookupMap(),Locale).entrySet().toArray(new Entry[0]);
		}
		else {
			slist = imsOrderDetailService.getLookUpMapByLocale(LookupTableBean.getInstance().getImsPTSuspendLookupMap(),Locale).entrySet().toArray(new Entry[0]);
		}
		
		for(int i=0; i<slist.length; i++){
			System.out.println(slist[i].getKey()+":"+slist[i].getValue());
		}
		Entry<String, String>[] clist = imsOrderDetailService.getLookUpMapByLocale(LookupTableBean.getInstance().getImsCancelLookupMap(), Locale).entrySet().toArray(new Entry[0]);
		if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
			clist = imsOrderDetailService.getLookUpMapByLocale(LookupTableBean.getInstance().getImsDSCancelLookupMap(),Locale).entrySet().toArray(new Entry[0]);
		}
		for(int i=0; i<clist.length; i++){
			System.out.println(clist[i].getKey()+":"+clist[i].getValue());
		}
		
		request.setAttribute("suspendlist", slist);
		request.setAttribute("cancellist", clist);
		request.setAttribute("isCC", order.getIsCC());
		if(request.getSession().getAttribute("imsSubmitTag")!=null){
			request.setAttribute("imsSubmitTag", request.getSession().getAttribute("imsSubmitTag"));
			request.setAttribute("imsOrderId", request.getSession().getAttribute("imsOrderId"));
			request.getSession().setAttribute("imsSubmitTag", null);
			request.getSession().setAttribute("imsOrderId", null);
		}				
		
		AppointmentPermitPropertyDtlDTO appointmentPermitPropertyDtl = new AppointmentPermitPropertyDtlDTO();
		appointmentPermitPropertyDtl = appointmentService.getPermitPropertyDtl(order.getInstallAddress(), _apptDate.getTime(), order.getSupremeFsInd());
		logger.debug("earliestApptDay:"+appointmentPermitPropertyDtl.getEarliestApptDate());
		logger.debug("permitLeadDays:"+appointmentPermitPropertyDtl.getPermitLeadDays());
		
		logger.debug("UI Object:" + gson.toJson(appointment));

		
		String minDate = null;
		String maxMonth = null;
		
		if("Y".equalsIgnoreCase(order.getPreInstallInd())||"Y".equalsIgnoreCase(order.getPreUseInd())){
			List<String[]> para = appointmentService.getPreInstallParameter();
			
			
			for (String[] result : para){
				if ("MINDATE".equals(result[0])){
					minDate = result[1];
				}else if ("MAXMONTH".equals(result[0])){
					maxMonth = result[1];
				}
			}
		}
		
		logger.debug("minDate:: "+minDate);
		logger.debug("maxMonth:: "+maxMonth);

		request.setAttribute("minDate", minDate);
		request.setAttribute("maxMonth", maxMonth);
		
		//ims direct sales
		if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
			Calendar c = Calendar.getInstance(); 
			c.setTime(order.getAppDate()); 
			c.add(Calendar.DATE, 7);
			request.setAttribute("esrd7", (new SimpleDateFormat("yyyy/MM/dd")).format(c.getTime())) ;
			if(!StringUtils.isEmpty(order.getDsWaiveCoolOff()))
			appointment.setWaiveCoolingOffPeriod(order.getDsWaiveCoolOff());
		}
		request.setAttribute("appdate", (new SimpleDateFormat("yyyy/MM/dd")).format(order.getAppDate())) ;
		
		appointment.setGiftCommDate(order.getGiftInstDate());
		
		return appointment;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) 
	throws Exception {
		
		logger.debug("onSubmit is called");

		request.getSession().setAttribute(ImsConstants.IMS_CUST_SIGN, null);// make all sign null
		request.getSession().setAttribute(ImsConstants.IMS_CREDIT_CARD_SIGN, null);// make all sign null
		request.getSession().setAttribute(ImsConstants.IMS_3_PARTY_SIGN, null);// make all sign null
		request.getSession().setAttribute(ImsConstants.IMS_TDO_MOOV_SIGN, null);// make all sign null
		request.getSession().setAttribute(ImsConstants.IMS_TDO_TV_SIGN, null);// make all sign null
		request.getSession().setAttribute(ImsConstants.IMS_TDO_PCD_SIGN, null);// make all sign null

		//steven added
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		if(sessionOrder!=null){logger.debug("ImsOrderID:"+sessionOrder.getOrderId());};
		List<AllDocDTO> allDocDTOs = supportDocService.getAllDocDTOByTypeAndAppDate("IMS",Type.I,sessionOrder.getAppDate()); 

		ImsSupportDocDTO SupportDoc = imsOrderDetailService.get_PayMtd_ccNum_DisMode(sessionOrder.getOrderId());
		if(SupportDoc!=null){
			System.out.println("In summary formbackingobj, from db, getCreditCardNum: "+SupportDoc.getCreditCardNum());
			System.out.println("In summary formbackingobj, from db, getDis_Mode: "+SupportDoc.getDis_Mode());
			System.out.println("In summary formbackingobj, from db, getPay_Mtd_Type: "+SupportDoc.getPay_Mtd_Type());
			System.out.println("In summary formbackingobj, from db, getTHIRD_PARTY_IND: "+SupportDoc.getTHIRD_PARTY_IND());
			System.out.println("In summary formbackingobj, from db, getTHIRD_PARTY_IND: "+SupportDoc.getCC_HOLD_NAME());
			System.out.println("In summary formbackingobj, from db, getCC_EXP_DATE: "+SupportDoc.getCC_EXP_DATE());
			if (sessionOrder != null && SupportDoc.getPay_Mtd_Type()!= null) {// check if payment method changed - Credit Card in DB and now use cash
				if ("M".equals(sessionOrder.getCustomer().getAccount().getPayment().getPayMtdType()) && "C".equals(SupportDoc.getPay_Mtd_Type())){// C - Credit Card, M - Cash
					System.out.println("Update outdated-Ind case 3, Pay_Mtd_Type changed  ");
					markAllUploadedSupDocOutdate(allDocDTOs, sessionOrder.getOrderId(), request);
				}
			}
			if (sessionOrder != null && SupportDoc.getPay_Mtd_Type()!= null) {// check if payment method changed - Cash in DB and now use Credit Card
				if ("C".equals(sessionOrder.getCustomer().getAccount().getPayment().getPayMtdType()) && "M".equals(SupportDoc.getPay_Mtd_Type())){// C - Credit Card, M - Cash
					System.out.println("Update outdated-Ind case 4, Pay_Mtd_Type changed  ");
					markAllUploadedSupDocOutdate(allDocDTOs, sessionOrder.getOrderId(), request);
				}
			}
			if (sessionOrder != null && SupportDoc.getCreditCardNum()!= null) {// check if credit card number equal
				if (!sessionOrder.getCustomer().getAccount().getPayment().getCcNum().equals(SupportDoc.getCreditCardNum())){
					System.out.println("Update outdated-Ind case 5, CreditCardNum changed ");
					markAllUploadedSupDocOutdate(allDocDTOs, sessionOrder.getOrderId(), request);
				}
			}
			if (sessionOrder != null && SupportDoc.getTHIRD_PARTY_IND()!= null) {
				if (!sessionOrder.getCustomer().getAccount().getPayment().getThirdPartyInd().equals(SupportDoc.getTHIRD_PARTY_IND())){
					System.out.println("Update outdated-Ind case 6, THIRD_PARTY_IND changed ");
					markAllUploadedSupDocOutdate(allDocDTOs, sessionOrder.getOrderId(), request);
				}
			}else if(SupportDoc.getTHIRD_PARTY_IND()== null && sessionOrder.getCustomer().getAccount().getPayment().getThirdPartyInd()!= null){
				System.out.println("Update outdated-Ind case 7, THIRD_PARTY_IND changed");
				markAllUploadedSupDocOutdate(allDocDTOs, sessionOrder.getOrderId(), request);
			}
			if (sessionOrder != null && SupportDoc.getCC_HOLD_NAME()!= null) {
				if (!sessionOrder.getCustomer().getAccount().getPayment().getCcHoldName().equals(SupportDoc.getCC_HOLD_NAME())){
					System.out.println("Update outdated-Ind case 8, CC_HOLD_NAME changed ");
					markAllUploadedSupDocOutdate(allDocDTOs, sessionOrder.getOrderId(), request);
				}
			}
			if (sessionOrder != null && SupportDoc.getCC_EXP_DATE()!= null) {
				if (!sessionOrder.getCustomer().getAccount().getPayment().getCcExpDate().equals(SupportDoc.getCC_EXP_DATE())){
					System.out.println("Update outdated-Ind case 9, EXP_DATE changed ");
					markAllUploadedSupDocOutdate(allDocDTOs, sessionOrder.getOrderId(), request);
				}
			}
		}
		//steven added
		AppointmentUI appointment = (AppointmentUI) command;
		
		logger.debug("est srd date:"+appointment.getEstimatedSrd());
		logger.debug("serial no:"+appointment.getSerialNum());
		logger.debug("target inst date:"+appointment.getTargetInstallDate());
		logger.debug("time slot:"+appointment.getTimeSlot());
		logger.debug("time slot(display):"+appointment.getTimeSlotDisplay());
		logger.debug("remarks:"+appointment.getAdditionRemarks());	
		
		OrderImsUI order = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);	
		order.setAppointment(appointment);
		//order.getRemarks()[0].setRmkType("IA");
		//order.getRemarks()[0].setRmkDtl(appointment.getAdditionRemarks());			
		
		//order.setInstallationRemark(appointment.getAdditionRemarks());		
		String encodeRmk = appointment.getAdditionRemarks();
		encodeRmk = new String(encodeRmk.getBytes("ISO-8859-1"),"UTF-8");
		order.setInstallationRemark(encodeRmk);
		
		
		if("Y".equalsIgnoreCase(order.getPreInstallInd())||"Y".equalsIgnoreCase(order.getPreUseInd())){
			if(appointment.getTargetCommDate()!=null && !appointment.getTargetCommDate().equals("")){
				Calendar commDate = new GregorianCalendar(
						Integer.parseInt(appointment.getTargetCommDate().substring(0,4)),
						Integer.parseInt(appointment.getTargetCommDate().substring(5,7))-1,
						Integer.parseInt(appointment.getTargetCommDate().substring(8,10)));
				
				order.setTargetCommDate(commDate.getTime());
			}
		}else{
			order.setTargetCommDate(null);
		}
		
		if(appointment.getTargetInstallDate()!=null && !appointment.getTargetInstallDate().equals("")){
			getAppntStartEndTime(appointment);
			order.setSrvReqDate(appointment.getSrvReqDate());

			if("Y".equals(appointment.getResourceInd())){
				/*order.setSrvReqDate(appointmentService.getResourceShortageSRD(
						order.getInstallAddress().getSerbdyno(), 
						order.getInstallAddress().getAddrInventory().getTechnology(), 
						(order.getFixedLineNo()!=null && !order.getFixedLineNo().equals(""))?"Y":"N"));*/
				Calendar _appntdate = Calendar.getInstance();
				_appntdate.setTime(appointmentService.getResourceShortageSRD(
						order.getInstallAddress().getSerbdyno(), 
						order.getInstallAddress().getAddrInventory().getTechnology(), 
						(order.getFixedLineNo()!=null && !order.getFixedLineNo().equals(""))?"Y":"N"));
				Calendar _appntstart = new GregorianCalendar(
						_appntdate.get(Calendar.YEAR),
						_appntdate.get(Calendar.MONTH),
						_appntdate.get(Calendar.DATE),
						9,0);
				Calendar _appntend = new GregorianCalendar(
						_appntdate.get(Calendar.YEAR),
						_appntdate.get(Calendar.MONTH),
						_appntdate.get(Calendar.DATE),
						18,0);
				appointment.setAppntStartDate(_appntstart.getTime());
				//appointment.setAppntStartDateDisplay(_appntstart.getTime());
				appointment.setAppntEndDate(_appntend.getTime());
				//appointment.setAppntEndDateDisplay(_appntend.getTime());
				
			}
		};				
		
		//ims direct sales
		if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
			order.setDsWaiveCoolOff(appointment.getWaiveCoolingOffPeriod());  
		}
		
		//temp use
		System.out.println("start:"+appointment.getAppntStartDate());
		System.out.println("end:"+appointment.getAppntEndDate());
		System.out.println("start display:"+appointment.getAppntStartDateDisplay());
		System.out.println("end display:"+appointment.getAppntEndDateDisplay());
		System.out.println("srd:"+order.getSrvReqDate());
		System.out.println("comm date:"+order.getTargetCommDate());
		
		if("C".equals(appointment.getSubmitTag())){
			
			imsOrderDetailService.cancelOrder(order, appointment.getCancelCd());//, user.getUsername());
			request.getSession().setAttribute("imsSubmitTag", appointment.getSubmitTag());
			request.getSession().setAttribute("imsOrderId", order.getOrderId());
			//System.out.println(order.getOrderStatus());//temp use
			
			MobileOffer imsMobileOfferUi = new MobileOffer();
			List<MobileOffer> imsMobileOfferUiList = (List<MobileOffer>) request.getSession().getAttribute("imsMobileOfferUiList");
			
			logger.debug("imsappointment Release MobilePIN*** (List<MobileOffer>) request.getSession().getAttribute"+"(imsMobileOfferUiList):"+ gson.toJson((List<MobileOffer>) request.getSession().getAttribute("imsMobileOfferUiList")));
			
			if (imsMobileOfferUiList != null){
				
				for(int i= 0; i<imsMobileOfferUiList.size(); i++){
				
					if(sessionOrder != null && !("").equals(sessionOrder)){
						try{
							imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(imsMobileOfferUiList.get(i).mrt, imsMobileOfferUiList.get(i).pin, sessionOrder.getCreateBy(), sessionOrder.getOrderId(), "NONONLINE");
						}catch(Exception e){
							logger.error("Exception caught in releaseMobilePIN()", e);
							throw new ImsMobilePinException();
						}
						logger.debug("release old number: " + imsMobileOfferUiList.get(i).mrt + " , old pin: " + imsMobileOfferUiList.get(i).pin);
					}else{
						try{
							imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(imsMobileOfferUiList.get(i).mrt, imsMobileOfferUiList.get(i).pin);
						}catch(Exception e){
							logger.error("Exception caught in releaseMobilePIN()", e);
							throw new ImsMobilePinException();
						}
						logger.debug("release old number: " + imsMobileOfferUiList.get(i).mrt + " , old pin: " + imsMobileOfferUiList.get(i).pin);
					}
				
					logger.debug(gson.toJson(imsMobileOfferUi));
				}
			}
			request.getSession().setAttribute("imsMobileOfferUiList", null);
			
			
			return new ModelAndView(new RedirectView(getFormView()+".html"));
			
		}else if("S".equals(appointment.getSubmitTag())){
			
			imsOrderDetailService.suspendOrder(order, appointment.getSuspendCd());//, user.getUsername());
			request.getSession().setAttribute("imsSubmitTag", appointment.getSubmitTag());
			request.getSession().setAttribute("imsOrderId", order.getOrderId());
			request.getSession().setAttribute("imsLoginIdUi", null);
			request.getSession().setAttribute(ImsConstants.IMS_APPOINTMENT_SERIAL, null);
			request.getSession().setAttribute("imsMobileOfferUiList", null);
			
			return new ModelAndView(new RedirectView(getFormView()+".html"));
			
		}else{			
			request.getSession().setAttribute(ImsConstants.IMS_ORDER, order);			
			return new ModelAndView(new RedirectView(getSuccessView()));			
		}												
				
	}		
//steven added
	private void markAllUploadedSupDocOutdate(List<AllDocDTO> allDocDTOs, String order_id, HttpServletRequest request){
		if (this.isEmpty(allDocDTOs)) {
			return;
		}
		for (AllDocDTO dto : allDocDTOs) {
			ordDocService.updateAllOrderDocDTOOutdatedInd(order_id, dto.getDocType().toString());
		}
		request.getSession().setAttribute(ImsConstants.IMS_CUST_SIGN, null);// make all sign null
		request.getSession().setAttribute(ImsConstants.IMS_CREDIT_CARD_SIGN, null);// make all sign null
		request.getSession().setAttribute(ImsConstants.IMS_3_PARTY_SIGN, null);// make all sign null
		request.getSession().setAttribute(ImsConstants.IMS_TDO_MOOV_SIGN, null);// make all sign null
		request.getSession().setAttribute(ImsConstants.IMS_TDO_TV_SIGN, null);// make all sign null
		request.getSession().setAttribute(ImsConstants.IMS_TDO_PCD_SIGN, null);// make all sign null
	}

	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	//steven added
	private void getAppntStartEndTime(AppointmentUI appointment){
		Calendar appntStartDate = null;
		Calendar appntEndDate = null;
		if(appointment.getTimeSlot()==null || appointment.getTimeSlot().equals("")){
			appntStartDate = new GregorianCalendar(
					Integer.parseInt(appointment.getTargetInstallDate().substring(0,4)),
					Integer.parseInt(appointment.getTargetInstallDate().substring(5,7))-1,
					Integer.parseInt(appointment.getTargetInstallDate().substring(8,10)),
					9, 0);
			
			appntEndDate = new GregorianCalendar(
					Integer.parseInt(appointment.getTargetInstallDate().substring(0,4)),
					Integer.parseInt(appointment.getTargetInstallDate().substring(5,7))-1,
					Integer.parseInt(appointment.getTargetInstallDate().substring(8,10)),
					18, 0);			
		}else{
			appntStartDate = new GregorianCalendar(
					Integer.parseInt(appointment.getTargetInstallDate().substring(0,4)),
					Integer.parseInt(appointment.getTargetInstallDate().substring(5,7))-1,
					Integer.parseInt(appointment.getTargetInstallDate().substring(8,10)),
					Integer.parseInt(appointment.getTimeSlot().substring(0, 2)),
					Integer.parseInt(appointment.getTimeSlot().substring(3, 5)));
			
			appntEndDate = new GregorianCalendar(
					Integer.parseInt(appointment.getTargetInstallDate().substring(0,4)),
					Integer.parseInt(appointment.getTargetInstallDate().substring(5,7))-1,
					Integer.parseInt(appointment.getTargetInstallDate().substring(8,10)),
					Integer.parseInt(appointment.getTimeSlot().substring(6, 8)),
					Integer.parseInt(appointment.getTimeSlot().substring(9, 11)));
			
		}
		
		appointment.setAppntStartDate(appntStartDate.getTime());
		appointment.setAppntEndDate(appntEndDate.getTime());

		Calendar appntStartDateDisplay = null;
		Calendar appntEndDateDisplay = null;
		if(appointment.getTimeSlotDisplay()==null || appointment.getTimeSlotDisplay().equals("")){
			appntStartDateDisplay = new GregorianCalendar(
					Integer.parseInt(appointment.getTargetInstallDate().substring(0,4)),
					Integer.parseInt(appointment.getTargetInstallDate().substring(5,7))-1,
					Integer.parseInt(appointment.getTargetInstallDate().substring(8,10)),
					9,0);
			
			appntEndDateDisplay = new GregorianCalendar(
					Integer.parseInt(appointment.getTargetInstallDate().substring(0,4)),
					Integer.parseInt(appointment.getTargetInstallDate().substring(5,7))-1,
					Integer.parseInt(appointment.getTargetInstallDate().substring(8,10)),
					18,0);			
		}else{
			appntStartDateDisplay = new GregorianCalendar(
					Integer.parseInt(appointment.getTargetInstallDate().substring(0,4)),
					Integer.parseInt(appointment.getTargetInstallDate().substring(5,7))-1,
					Integer.parseInt(appointment.getTargetInstallDate().substring(8,10)),
					Integer.parseInt(appointment.getTimeSlotDisplay().substring(0, 2)),
					Integer.parseInt(appointment.getTimeSlotDisplay().substring(3, 5)));
			
			appntEndDateDisplay = new GregorianCalendar(
					Integer.parseInt(appointment.getTargetInstallDate().substring(0,4)),
					Integer.parseInt(appointment.getTargetInstallDate().substring(5,7))-1,
					Integer.parseInt(appointment.getTargetInstallDate().substring(8,10)),
					Integer.parseInt(appointment.getTimeSlotDisplay().substring(6, 8)),
					Integer.parseInt(appointment.getTimeSlotDisplay().substring(9, 11)));
			
		}
		
		appointment.setAppntStartDateDisplay(appntStartDateDisplay.getTime());
		appointment.setAppntEndDateDisplay(appntEndDateDisplay.getTime());
		
		Calendar srd = Calendar.getInstance();
		//srd.setTime(appointment.getAppntStartDate());
		if("Y".equals(appointment.getBlackListInd())){
			srd.setTime(appointment.getAppntStartDate());
			appointment.setAppntStartDate(null);
			//appointment.setAppntStartDateDisplay(null);
			appointment.setAppntEndDate(null);
			//appointment.setAppntEndDateDisplay(null);
		}/*else if("Y".equals(appointment.getResourceInd())){
			srd.add(Calendar.DAY_OF_YEAR, 7);
			Calendar _appntstart = new GregorianCalendar(
					srd.get(Calendar.YEAR),
					srd.get(Calendar.MONTH),
					srd.get(Calendar.DATE),
					9, 0);
			
			Calendar _appntend = new GregorianCalendar(
					srd.get(Calendar.YEAR),
					srd.get(Calendar.MONTH),
					srd.get(Calendar.DATE),
					18, 0);
			
			srd.setTime(appointment.getAppntStartDate());
			
			appointment.setAppntStartDate(_appntstart.getTime());
			appointment.setAppntStartDateDisplay(_appntstart.getTime());
			appointment.setAppntEndDate(_appntend.getTime());
			appointment.setAppntEndDateDisplay(_appntend.getTime());
			
		}*/else{
			srd.setTime(appointment.getAppntStartDate());
		}
		
		srd.set(Calendar.HOUR_OF_DAY, 0);
		srd.set(Calendar.MINUTE, 0);
		appointment.setSrvReqDate(srd.getTime());
				
	}	

	public ImsAppointmentService getAppointmentService() {
		return appointmentService;
	}

	public void setAppointmentService(ImsAppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}

	public ImsOrderDetailService getImsOrderDetailService() {
		return imsOrderDetailService;
	}

	public void setImsOrderDetailService(ImsOrderDetailService imsOrderDetailService) {
		this.imsOrderDetailService = imsOrderDetailService;
	}

}
