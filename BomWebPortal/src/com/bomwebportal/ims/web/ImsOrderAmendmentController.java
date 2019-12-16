package com.bomwebportal.ims.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.AllDocDTO.DocType;
import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.exception.ImsAlreadySignOffException;
import com.bomwebportal.exception.ImsCannotAmendException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.AmendWqDTO;
import com.bomwebportal.ims.dto.AmendmentModeDTO;
import com.bomwebportal.ims.dto.AppointmentPermitPropertyDtlDTO;
import com.bomwebportal.ims.dto.AppointmentTimeSlotDTO;
import com.bomwebportal.ims.dto.DelayReasonDTO;
import com.bomwebportal.ims.dto.GetCOrderDTO;
import com.bomwebportal.ims.dto.HousingTypeDTO;
import com.bomwebportal.ims.dto.ImsAllOrdDocWaiveDTO;
import com.bomwebportal.ims.dto.ImsServiceSrdDTO;
import com.bomwebportal.ims.dto.NtvReplaceSelfPickHddDTO;
import com.bomwebportal.ims.dto.ui.AmendOrderImsUI;
import com.bomwebportal.ims.dto.ui.AppointmentUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.dto.ui.RegAmendSRDUI;
import com.bomwebportal.ims.dto.ui.SubscribedItemUI;
import com.bomwebportal.ims.service.COrderService;
import com.bomwebportal.ims.service.ImsAppointmentService;
import com.bomwebportal.ims.service.ImsOrderAmendService;
import com.bomwebportal.ims.service.ImsOrderDetailService;
import com.bomwebportal.ims.service.ImsOrderService;
import com.bomwebportal.ims.service.ImsSMSService;
import com.bomwebportal.service.ConstantLkupService;
import com.bomwebportal.service.ImsCommonService;
import com.bomwebportal.service.OrdEmailReqService;
import com.bomwebportal.service.OrderEsignatureService;
import com.bomwebportal.service.OrderEsignatureService.EmailReqResult;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;
import com.bomwebportal.util.uENC;
import com.google.gson.Gson;
import com.pccw.util.spring.SpringApplicationContext;
import com.pccw.wq.schema.dto.WorkQueueDTO;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.validation.BindException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;


/* ims steven created this file*/
public class ImsOrderAmendmentController extends SimpleFormController {

	
	protected final Log logger = LogFactory.getLog(getClass());
	private Gson gson = new Gson();
	private ImsOrderDetailService imsOrderDetailService;
	private ImsAppointmentService appointmentService;
	private ImsOrderAmendService imsOrderAmendservice;
	private OrdEmailReqService ordEmailReqService;
	private OrderEsignatureService orderEsignatureService;
    private ImsSMSService imsSMSService;
	private ImsOrderService orderService;
	private String ntvDomain;
	private COrderService cOrderService; //Tony added
    private ConstantLkupService constantLkupService;
	
	public ImsOrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(ImsOrderService orderService) {
		this.orderService = orderService;
	}

	public OrdEmailReqService getOrdEmailReqService() {
		return ordEmailReqService;
	}

	public void setOrdEmailReqService(OrdEmailReqService ordEmailReqService) {
		this.ordEmailReqService = ordEmailReqService;
	}
	public OrderEsignatureService getOrderEsignatureService() {
		return orderEsignatureService;
	}
	public void setOrderEsignatureService(
			OrderEsignatureService orderEsignatureService) {
		this.orderEsignatureService = orderEsignatureService;
	}
	public void setImsSMSService(ImsSMSService imsSMSService) {
		this.imsSMSService = imsSMSService;
	}
	public ImsSMSService getImsSMSService() {
		return imsSMSService;
	}
	public ImsOrderDetailService getImsOrderDetailService() {
		return imsOrderDetailService;
	}

	public void setImsOrderDetailService(
			ImsOrderDetailService imsOrderDetailService) {
		this.imsOrderDetailService = imsOrderDetailService;
	}

	public ImsAppointmentService getAppointmentService() {
		return appointmentService;
	}

	public void setAppointmentService(ImsAppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}
	public void setImsOrderAmendservice(ImsOrderAmendService imsOrderAmendservice) {
		this.imsOrderAmendservice = imsOrderAmendservice;
	}
	public ImsOrderAmendService getImsOrderAmendservice() {
		return imsOrderAmendservice;
	}
	
	public String getNtvDomain() {
		return ntvDomain;
	}

	public void setNtvDomain(String ntvDomain) {
		this.ntvDomain = ntvDomain;
	}

	public void setcOrderService(COrderService cOrderService) {
		this.cOrderService = cOrderService;
	}

	public COrderService getcOrderService() {
		return cOrderService;
	}

	public void setConstantLkupService(ConstantLkupService constantLkupService) {
		this.constantLkupService = constantLkupService;
	}

	public ConstantLkupService getConstantLkupService() {
		return constantLkupService;
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		

		logger.info("ImsOrderAmendmentController formBackingObject is called");
		AmendOrderImsUI amend = new AmendOrderImsUI();
		amend.setImsAmendJustDone((String) request.getSession().getAttribute("ImsAmendJustDone"));
		OrderImsUI orderImsUi = null;
		
//		if("Y".equals(amend.getImsAmendJustDone())){
//			if(request.getSession().getAttribute("OnceIsEnough")!=null&&
//					"Y".equals((String) request.getSession().getAttribute("OnceIsEnough"))){
//				request.getSession().setAttribute("ImsAmendJustDone", "N");
//				request.getSession().setAttribute("OnceIsEnough", "N"); 
//				amend.setImsAmendJustDone("N");
//			}else{
//				request.getSession().setAttribute("OnceIsEnough", "Y");				
//			}		
//		}
		
		
		
		if(request.getParameter("OrderIdForAmend")!=null){
			if(!imsOrderAmendservice.isOrderStatusOkayForAmend(request.getParameter("OrderIdForAmend"))){
				request.getSession().setAttribute("imsAmendOrderId", request.getParameter("OrderIdForAmend"));
				request.getSession().setAttribute("imsAmendOrderStatus", imsOrderAmendservice.getOrderStatusDesc(request.getParameter("OrderIdForAmend")));
				throw new ImsCannotAmendException();
			}else{
				orderImsUi = imsOrderDetailService.getOrder(request.getParameter("OrderIdForAmend"));
			}
		}else{
			return amend;
		}

		//20161212 Summer
		String autoTempCamp = imsOrderAmendservice.auto_term_camp_order_amend(orderImsUi.getOrderId());
		logger.info("1ST AllowAmendStateAppear():" + gson.toJson(autoTempCamp));
		amend.setAllowAmendStateAppear(null);
		if(autoTempCamp!=null&&!"".equals(autoTempCamp)){
			logger.info("2ND AllowAmendStateAppear():" + gson.toJson(autoTempCamp));
			amend.setAllowAmendStateAppear("An auto-term campaign ("+autoTempCamp+") exists, the pending auto-term order MUST be handled by ORDER TEAM manually in SOPHIE");
		}
		
		try{
			if(orderImsUi.getCreateByUser().getChannelId()==12||orderImsUi.getCreateByUser().getChannelId()==13){
				logger.info("is ds getCreateByUser():" + gson.toJson(orderImsUi.getCreateByUser()));
				request.getSession().setAttribute(ImsConstants.IMS_DIRECT_SALES,true);
			}else{
				logger.info("not ds getCreateByUser():" + gson.toJson(orderImsUi.getCreateByUser()));
				request.getSession().setAttribute(ImsConstants.IMS_DIRECT_SALES,false);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		
		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
//		logger.debug("Order():" + gson.toJson(orderImsUi));
		logger.info("OrderId():" + orderImsUi.getOrderId() +"userId:" + bomSalesUserDTO.getUsername());
		amend.setBomSalesUserDTO(bomSalesUserDTO);
		amend.setCreatedBy(bomSalesUserDTO.getUsername());
		logger.info("login user:" + gson.toJson(bomSalesUserDTO));
		if((bomSalesUserDTO.getChannelId()==1 && "SUPPORT".equals(bomSalesUserDTO.getCategory()))||
		   (bomSalesUserDTO.getChannelId()==12 && "DS4".equals(bomSalesUserDTO.getChannelCd()))	||
		   (bomSalesUserDTO.getChannelId()==13 && "VQA".equals(bomSalesUserDTO.getChannelCd()))	
				){
			amend.setIsDsSupport("Y");
		}else{
			amend.setIsDsSupport("N");
		}
		
		request.getSession().setAttribute(ImsConstants.IMS_IS_USER_CHANNEL_ONE, bomSalesUserDTO.getChannelId()==1?true:false);
		if(bomSalesUserDTO.getChannelId()==1?true:false){
			logger.info("channel id is 1"+" OrderId():" + orderImsUi.getOrderId() +"userId:" + bomSalesUserDTO.getUsername());
		}else{
			logger.info("channel id is not 1"+" OrderId():" + orderImsUi.getOrderId() +"userId:" + bomSalesUserDTO.getUsername());			
		}
//		if(imsOrderAmendservice.isPaymentMethodIsCC(orderImsUi.getOrderId())){
//			amend.setIsPaymentMethodIsCC("Y");
//		}else{
//			amend.setIsPaymentMethodIsCC("N");
//		}
//		logger.info("getIsPaymentMethodIsCC:" + amend.getIsPaymentMethodIsCC());

		if(imsOrderAmendservice.isEyeGroupAttach(orderImsUi.getOrderId())){
			request.getSession().setAttribute("eyeGroupAttach", "Y");
		}else{
			request.getSession().setAttribute("eyeGroupAttach", "N");
		}

		Date EarliestSrd = null;
		//get tomorrow date, T+1
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);  
        Date tmw = c.getTime(); 
		AppointmentUI appointment = orderImsUi.getAppointment();
		if(ImsConstants.Termination.equals(orderImsUi.getImsOrderType())&&"Y".equals(orderImsUi.isDorder())){
			orderImsUi.setIsTermDOrderSelfPickTargetCust(this.checkIfTermSelfPickTargetCust(orderImsUi));
		}
		this.enableSelfPick(orderImsUi);
		
		Boolean needAppointment=false;
		if("P_R_T".equals(orderImsUi.getImsOrderType())||"PCD_V".equals(orderImsUi.getImsOrderType())){
			 needAppointment=false;
			logger.debug(orderImsUi.getOrderId()+" case 1 pcd ret only tv / only vas, needAppointment:"+needAppointment);
		}else if(orderImsUi.isOrderTypeNowRet()){
			needAppointment=imsOrderAmendservice.isNowRetNeedAppointment(orderImsUi.getOrderId());
			logger.debug(orderImsUi.getOrderId()+" case 3 now ret,  needAppointment:"+needAppointment);
		}else if(imsOrderAmendservice.isRemakeAppointmentNeeded(orderImsUi.getOrderId())){
			needAppointment = true;
			logger.debug(orderImsUi.getOrderId()+" case 4 isRemakeAppointmentNeeded, needAppointment:"+needAppointment);
		}else{
			needAppointment=imsOrderAmendservice.checkIfNeedAppointment(orderImsUi.getOrderId());
			logger.debug(orderImsUi.getOrderId()+" case 5 checkIfNeedAppointment, needAppointment:"+needAppointment);
		}

		if(needAppointment){
			request.getSession().setAttribute("CanAmendAppointment", "Y");
			amend.setCanAmendAppointment("Y");
	//		from ImsAppointmentController , get EarliestSrd start
			logger.info("bomSalesUserDTO. getChannelId():"+bomSalesUserDTO. getChannelId());
			ImsServiceSrdDTO addressInfo = null;
			
			Boolean isShortage=imsOrderAmendservice.isShortage(orderImsUi.getOrderId());
			Boolean L1Distributed=imsOrderAmendservice.isL1Distributed(orderImsUi.getOrderId());
			logger.info("amend order "+
					orderImsUi.getOrderId()+" "+
					(isShortage?"is":"is not") +" Shortage, "+ (L1Distributed?"is":"is not") +" L1 Distributed");


			String newTech = orderImsUi.getInstallAddress().getAddrInventory().getTechnology();
			String sbType="ACQ";
			if(ImsConstants.Termination.equals(orderImsUi.getImsOrderType())){
				sbType="TERM";
			}else if(ImsConstants.Retention.equals(orderImsUi.getImsOrderType())){
				sbType="RET";
			}
			String bomLastestTech = imsOrderAmendservice.getBomLastestTechBySbId(orderImsUi.getOrderId(), sbType);
			if(!"".equals(bomLastestTech)){
				newTech = bomLastestTech;
			}
			String bomLastestSB = imsOrderAmendservice.getBomSB(orderImsUi.getOrderId());
			if(bomLastestSB==null||"".equals(bomLastestSB)){
				bomLastestSB=orderImsUi.getInstallAddress().getSerbdyno();
			}
			
			String blackListCustomer = orderImsUi.getCustomer().getBlacklistInd();
			if(imsOrderAmendservice.isBomOrderSuspended(orderImsUi.getOrderId()) && "Y".equals(blackListCustomer)){
				blackListCustomer = "Y";
			}else{
				blackListCustomer = "N";
			}
			
			//get EarliestSrd
			if(ImsConstants.Termination.equals(orderImsUi.getImsOrderType())){
				//ret / temp flow 
				logger.info("order_amend_order_id:"+orderImsUi.getOrderId()+" Temp_flow");

				addressInfo = appointmentService.getServiceSrdComparedWithBMO(						
						orderImsUi.getInstallAddress().getBlacklistInd(), 
						blackListCustomer, 
					(orderImsUi.getFixedLineNo()!=null && !orderImsUi.getFixedLineNo().equals(""))?"Y":"N", 
					null,
					bomLastestSB
					,orderImsUi.getInstallAddress().getUnitNo(), orderImsUi.getInstallAddress().getFloorNo()
					, bomSalesUserDTO.getChannelId()==2||bomSalesUserDTO.getChannelId()==3?"Y":"N"
						, bomSalesUserDTO.getChannelId()==3?"Y":"N", orderImsUi,newTech);	
				

				EarliestSrd = handleShortageAndL1Distributed(orderImsUi,
						EarliestSrd, tmw, appointment, addressInfo, isShortage,
						L1Distributed, newTech);		


					for (int j=0; j < addressInfo.getServiceDetailList().size(); j++) {
						if(orderImsUi.getInstallAddress().getAddrInventory()!=null){
							logger.info("app getTechnology():"+addressInfo.getServiceDetailList().get(j).getTechnology() +
									"    now cust want term old tech:"+orderImsUi.getServiceIms().getLineType());
							if (addressInfo.getServiceDetailList().get(j).getTechnology().equals(
									orderImsUi.getServiceIms().getLineType()) )  {
								logger.info("  app getTechnologySupported():"+addressInfo.getServiceDetailList().get(j).getTechnologySupported()+
											"\tapp getIsDeadCase():"+addressInfo.getServiceDetailList().get(j).getIsDeadCase());
								if (addressInfo.getServiceDetailList().get(j).getTechnologySupported().equals("Y") &&
										addressInfo.getServiceDetailList().get(j).getIsDeadCase().equals("N")) {
									EarliestSrd = addressInfo.getServiceDetailList().get(j).getEstEarliestSrd();
									appointment.setEstSrdReason(addressInfo.getServiceDetailList().get(j).getRtnDesc());
									appointment.setBmoRmk(addressInfo.getServiceDetailList().get(j).getBmoRmk());
									orderImsUi.setBmoAlertRemarks(addressInfo.getServiceDetailList().get(j).getBmoAlert());
									appointment.setBmoLeadDay(addressInfo.getServiceDetailList().get(j).getBmoLeadDay());
									break;
								}				
							} 		
						}
					}
					logger.info("Temp EarliestSrd:"+EarliestSrd);
					
					
			}else if(ImsConstants.Retention.  equals(orderImsUi.getImsOrderType())||orderImsUi.isOrderTypeNowRet()){
				//ret flow
				logger.info("order_amend_order_id:"+orderImsUi.getOrderId()+" Ret_flow");

				String oldTech = orderImsUi.getServiceIms().getLineType();
				addressInfo = appointmentService.getServiceSrdComparedWithBMO(
						orderImsUi.getInstallAddress().getBlacklistInd(),  
						blackListCustomer, 
						(orderImsUi.getFixedLineNo()!=null && !orderImsUi.getFixedLineNo().equals(""))?"Y":"N", 
						null,
						bomLastestSB
						,orderImsUi.getInstallAddress().getUnitNo(), orderImsUi.getInstallAddress().getFloorNo()
						, bomSalesUserDTO.getChannelId()==2||bomSalesUserDTO.getChannelId()==3?"Y":"N"
							, bomSalesUserDTO.getChannelId()==3?"Y":"N", orderImsUi,newTech);	

				EarliestSrd = handleShortageAndL1Distributed(orderImsUi,
						EarliestSrd, tmw, appointment, addressInfo, isShortage,
						L1Distributed,newTech);
				
				for (int j=0; j < addressInfo.getServiceDetailList().size(); j++) {
					if(orderImsUi.getInstallAddress().getAddrInventory()!=null){
						logger.info("addr Tech:"+addressInfo.getServiceDetailList().get(j).getTechnology() +	"    new tech:"+newTech);
						if (addressInfo.getServiceDetailList().get(j).getTechnology().equals(newTech) )  {
							logger.info("addr TechSupported:"+addressInfo.getServiceDetailList().get(j).getTechnologySupported()+
										"\taddr DeadCase:"+addressInfo.getServiceDetailList().get(j).getIsDeadCase());
							if (addressInfo.getServiceDetailList().get(j).getTechnologySupported().equals("Y") &&
									addressInfo.getServiceDetailList().get(j).getIsDeadCase().equals("N")) {
								EarliestSrd = addressInfo.getServiceDetailList().get(j).getEstEarliestSrd();
								logger.info("EarliestSrd:"+EarliestSrd);
								if(!oldTech.equals(newTech)){
									orderImsUi.getInstallAddress().getAddrInventory().setResourceShortage(addressInfo.getServiceDetailList().get(j).getIsResrcShort());
								}
								appointment.setEstSrdReason(addressInfo.getServiceDetailList().get(j).getRtnDesc());
								appointment.setBmoRmk(addressInfo.getServiceDetailList().get(j).getBmoRmk());
								orderImsUi.setBmoAlertRemarks(addressInfo.getServiceDetailList().get(j).getBmoAlert());
								appointment.setBmoLeadDay(addressInfo.getServiceDetailList().get(j).getBmoLeadDay());
								break;
							}				
						} 		
					}
				}
				logger.info("Ret EarliestSrd:"+EarliestSrd);
				
								
				
				

				//steven, PON + 2X1000G + service code = T+21 start, 20170927
				Calendar c2 = Calendar.getInstance();
				c.setTime(orderImsUi.getAppDate()); 	//app date, by Backy email Wed 9/27/2017 2:58 PM			
				c2.add(Calendar.DATE, 21);  // add 21 day, for PON to 2X1000M with special service code
				Date t21 = 	c2.getTime();
				if(EarliestSrd.before(t21)){
					if(imsOrderAmendservice.isPonTo1G1GProfilePonAndNot1G1G(orderImsUi.getOrderId())){
						if(imsOrderAmendservice.isPonTo1G1GNewBuyBasket1G1G(orderImsUi.getOrderId())){
							if(imsOrderAmendservice.isPonTo1G1GSpecialServiceCode(orderImsUi.getOrderId())){
								EarliestSrd = t21;
								appointment.setEstSrdReason("PON to 2X1000M with special service code, T+21");		
							}else{
								logger.debug(orderImsUi.getOrderId()+" Serbdyno no service code for T21");							
							}
						}else{
							logger.debug(orderImsUi.getOrderId()+" not but 1g1g");					
						}
					}else{
						logger.debug(orderImsUi.getOrderId()+" oldTech is not pon");
					}
				}else{
					logger.debug(orderImsUi.getOrderId()+" SRD later than T+21 pon");
				}
				//steven, PON + 2X1000G + service code = T+21 end, 20170920
				
				
			}else{//ACQ flow 
				String i_has_bb_srv = "Y", i_has_nowtv_srv = "N", mismatch ="N", fsPrepay = "N", fsInd = "N", i_ride_on_fsa = "";
				String isDS = (Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)?"Y":"N";
				
				String isNowTV = "N";
				if(orderImsUi.getProcessVim()!=null){
					 isNowTV="Y";
				}else{
					 isNowTV="N";
				}
				
				if("CO".equals(orderImsUi.getSrc())){
					if(orderImsUi.getCustomer()!=null&&orderImsUi.getCustomer().getServiceNum()!=null&&!"".equals(orderImsUi.getCustomer().getServiceNum())){
						i_ride_on_fsa=orderImsUi.getCustomer().getServiceNum();
					}else{
						GetCOrderDTO cOrderDTO = cOrderService.getCOrder("Y", isNowTV, orderImsUi.getInstallAddress().getAddrInventory().getTechnology(), orderImsUi.getCustomer().getCustNo(), orderImsUi.getInstallAddress().getSerbdyno(), orderImsUi.getInstallAddress().getUnitNo(), orderImsUi.getInstallAddress().getFloorNo());
						logger.info("create_c_order:"+cOrderDTO.getO_create_c_order()+"  reason:"+cOrderDTO.getO_reason()+"  related_fsa:"+cOrderDTO.getO_related_fsa());
						i_ride_on_fsa = cOrderDTO.getO_related_fsa();
					}
			    }else{
			    	i_ride_on_fsa="";
		    	}
				
				if ("NTV-A".equals(orderImsUi.getOrderType()) || orderImsUi.isOrderTypeNowRet()) {
					i_has_bb_srv = "N";
					i_has_nowtv_srv = "Y";
					logger.info("order_amend_order_id:"+orderImsUi.getOrderId()+" NTV-ACQ/RET_flow");
				} else {
					logger.info("order_amend_order_id:"+orderImsUi.getOrderId()+" PCD-ACQ_flow");
				}
				addressInfo = appointmentService.getServiceSrd(
						"DS".equals(orderImsUi.getImsOrderType())?"N":orderImsUi.getInstallAddress().getBlacklistInd(),  
								blackListCustomer, 
						(orderImsUi.getFixedLineNo()!=null && !orderImsUi.getFixedLineNo().equals(""))?"Y":"N",  
								orderImsUi.getSignOffDate(), 
								bomLastestSB, 
								orderImsUi.getInstallAddress().getUnitNo(), 
								orderImsUi.getInstallAddress().getFloorNo(), 
								bomSalesUserDTO.getChannelId()==2||bomSalesUserDTO.getChannelId()==3?"Y":"N",
								bomSalesUserDTO.getChannelId()==3?"Y":"N", isDS, 
								i_has_bb_srv, i_has_nowtv_srv, mismatch, fsPrepay, fsInd, i_ride_on_fsa,
								orderImsUi.getOrderType());
				
				EarliestSrd = handleShortageAndL1Distributed(orderImsUi,
						EarliestSrd, tmw, appointment, addressInfo, isShortage,
						L1Distributed,newTech);

				AppointmentPermitPropertyDtlDTO bmoFTHIInfo = null;
				AppointmentPermitPropertyDtlDTO bmoPCDIInfo = null;
		        Date today = new Date();  
		        
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");  
		        dateFormat.format(today);
				bmoFTHIInfo = appointmentService.getPermitPropertyDtl(bomLastestSB, "FTHI", Utility.date2String(today, "yyyyMMdd"));
				bmoPCDIInfo = appointmentService.getPermitPropertyDtl(bomLastestSB, "PCDI", Utility.date2String(today, "yyyyMMdd"));
				
				for(int i=0; i<addressInfo.getServiceDetailList().size(); i++){
					if(addressInfo.getServiceDetailList().get(i).getEstEarliestSrd()!=null){
						if(addressInfo.getServiceDetailList().get(i).getTechnology().equalsIgnoreCase("PON")){		
							logger.info("PON date:"+Utility.string2Date(bmoFTHIInfo.getEarliestApptDate().substring(0, 4), bmoFTHIInfo.getEarliestApptDate().substring(4, 6), bmoFTHIInfo.getEarliestApptDate().substring(6, 8)));
							if(addressInfo.getServiceDetailList().get(i).getEstEarliestSrd().before(Utility.string2Date(bmoFTHIInfo.getEarliestApptDate().substring(0, 4), bmoFTHIInfo.getEarliestApptDate().substring(4, 6), bmoFTHIInfo.getEarliestApptDate().substring(6, 8)))){
								addressInfo.getServiceDetailList().get(i).setEstEarliestSrd(Utility.string2Date(bmoFTHIInfo.getEarliestApptDate().substring(0, 4), bmoFTHIInfo.getEarliestApptDate().substring(4, 6), bmoFTHIInfo.getEarliestApptDate().substring(6, 8)));
								addressInfo.getServiceDetailList().get(i).setLeadTime(Integer.parseInt(bmoFTHIInfo.getPermitLeadDays()));
								addressInfo.getServiceDetailList().get(i).setRtnDesc("PON BMO, T+"+addressInfo.getServiceDetailList().get(i).getLeadTime());
								addressInfo.getServiceDetailList().get(i).setBmoAlert(bmoFTHIInfo.getAlertMsg());
								addressInfo.getServiceDetailList().get(i).setBmoRmk(bmoFTHIInfo.getBmoRemark());
							}
							addressInfo.getServiceDetailList().get(i).setBmoLeadDay(bmoFTHIInfo.getPermitLeadDays());
						}else if(addressInfo.getServiceDetailList().get(i).getTechnology().equalsIgnoreCase("ADSL")){		
							logger.info("ADSL date:"+Utility.string2Date(bmoPCDIInfo.getEarliestApptDate().substring(0, 4), bmoPCDIInfo.getEarliestApptDate().substring(4, 6), bmoPCDIInfo.getEarliestApptDate().substring(6, 8)));
							if(addressInfo.getServiceDetailList().get(i).getEstEarliestSrd().before(Utility.string2Date(bmoPCDIInfo.getEarliestApptDate().substring(0, 4), bmoPCDIInfo.getEarliestApptDate().substring(4, 6), bmoPCDIInfo.getEarliestApptDate().substring(6, 8)))){
								addressInfo.getServiceDetailList().get(i).setEstEarliestSrd(Utility.string2Date(bmoPCDIInfo.getEarliestApptDate().substring(0, 4), bmoPCDIInfo.getEarliestApptDate().substring(4, 6), bmoPCDIInfo.getEarliestApptDate().substring(6, 8)));
								addressInfo.getServiceDetailList().get(i).setLeadTime(Integer.parseInt(bmoPCDIInfo.getPermitLeadDays()));
								addressInfo.getServiceDetailList().get(i).setRtnDesc("ADSL BMO, T+"+addressInfo.getServiceDetailList().get(i).getLeadTime());
								addressInfo.getServiceDetailList().get(i).setBmoAlert(bmoPCDIInfo.getAlertMsg());
								addressInfo.getServiceDetailList().get(i).setBmoRmk(bmoPCDIInfo.getBmoRemark());
							}
							addressInfo.getServiceDetailList().get(i).setBmoLeadDay(bmoPCDIInfo.getPermitLeadDays());
						}else if(addressInfo.getServiceDetailList().get(i).getTechnology().equalsIgnoreCase("VDSL")){				
							logger.info("VDSL date:"+Utility.string2Date(bmoPCDIInfo.getEarliestApptDate().substring(0, 4), bmoPCDIInfo.getEarliestApptDate().substring(4, 6), bmoPCDIInfo.getEarliestApptDate().substring(6, 8)));		
							if(addressInfo.getServiceDetailList().get(i).getEstEarliestSrd().before(Utility.string2Date(bmoPCDIInfo.getEarliestApptDate().substring(0, 4), bmoPCDIInfo.getEarliestApptDate().substring(4, 6), bmoPCDIInfo.getEarliestApptDate().substring(6, 8)))){
								addressInfo.getServiceDetailList().get(i).setEstEarliestSrd(Utility.string2Date(bmoPCDIInfo.getEarliestApptDate().substring(0, 4), bmoPCDIInfo.getEarliestApptDate().substring(4, 6), bmoPCDIInfo.getEarliestApptDate().substring(6, 8)));
								addressInfo.getServiceDetailList().get(i).setLeadTime(Integer.parseInt(bmoPCDIInfo.getPermitLeadDays()));
								addressInfo.getServiceDetailList().get(i).setRtnDesc("VDSL BMO, T+"+addressInfo.getServiceDetailList().get(i).getLeadTime());
								addressInfo.getServiceDetailList().get(i).setBmoAlert(bmoPCDIInfo.getAlertMsg());
								addressInfo.getServiceDetailList().get(i).setBmoRmk(bmoPCDIInfo.getBmoRemark());
							}
							addressInfo.getServiceDetailList().get(i).setBmoLeadDay(bmoPCDIInfo.getPermitLeadDays());
						}else if(addressInfo.getServiceDetailList().get(i).getTechnology().equalsIgnoreCase("Vectoring")){				
							logger.info("Vectoring date:"+Utility.string2Date(bmoPCDIInfo.getEarliestApptDate().substring(0, 4), bmoPCDIInfo.getEarliestApptDate().substring(4, 6), bmoPCDIInfo.getEarliestApptDate().substring(6, 8)));		
							if(addressInfo.getServiceDetailList().get(i).getEstEarliestSrd().before(Utility.string2Date(bmoPCDIInfo.getEarliestApptDate().substring(0, 4), bmoPCDIInfo.getEarliestApptDate().substring(4, 6), bmoPCDIInfo.getEarliestApptDate().substring(6, 8)))){
								addressInfo.getServiceDetailList().get(i).setEstEarliestSrd(Utility.string2Date(bmoPCDIInfo.getEarliestApptDate().substring(0, 4), bmoPCDIInfo.getEarliestApptDate().substring(4, 6), bmoPCDIInfo.getEarliestApptDate().substring(6, 8)));
								addressInfo.getServiceDetailList().get(i).setLeadTime(Integer.parseInt(bmoPCDIInfo.getPermitLeadDays()));
								addressInfo.getServiceDetailList().get(i).setRtnDesc("Vectoring BMO, T+"+addressInfo.getServiceDetailList().get(i).getLeadTime());
								addressInfo.getServiceDetailList().get(i).setBmoAlert(bmoPCDIInfo.getAlertMsg());
								addressInfo.getServiceDetailList().get(i).setBmoRmk(bmoPCDIInfo.getBmoRemark());
							}
							addressInfo.getServiceDetailList().get(i).setBmoLeadDay(bmoPCDIInfo.getPermitLeadDays());
						}
					}				
				}
				for (int j=0; j < addressInfo.getServiceDetailList().size(); j++) {
					if (addressInfo.getServiceDetailList().get(j).getTechnology().equals(newTech)) {
						if (addressInfo.getServiceDetailList().get(j).getTechnologySupported().equals("Y") &&
								addressInfo.getServiceDetailList().get(j).getIsDeadCase().equals("N")) {
							EarliestSrd = addressInfo.getServiceDetailList().get(j).getEstEarliestSrd();
							logger.info("BMO>0 final tech:"+newTech+" EarliestSrd:"+EarliestSrd);
							appointment.setEstSrdReason(addressInfo.getServiceDetailList().get(j).getRtnDesc());
							appointment.setBmoRmk(addressInfo.getServiceDetailList().get(j).getBmoRmk());
							orderImsUi.setBmoAlertRemarks(addressInfo.getServiceDetailList().get(j).getBmoAlert());
							break;
						}				
					} 			
				}
		
				logger.info("ACQ EarliestSrd:"+EarliestSrd);
				//from ImsAppointmentController , get EarliestSrd end
				
			}//ACQ flow - get EarliestSrd

			
			// get hosuing type for error
			orderImsUi.getInstallAddress().setHousingTypeList(imsOrderAmendservice.getHousingTypeByOrderID(orderImsUi.getOrderId()));
		}else{
			request.getSession().setAttribute("CanAmendAppointment", "N");
			amend.setCanAmendAppointment("N");
			EarliestSrd=tmw;
		}
//		request.getSession().setAttribute("eyeGroupAttach", "Y");
//		request.getSession().setAttribute("CanAmendAppointment", "N");

        //earliest SRD must >= sysdate + 1
		if(EarliestSrd==null){
			throw new AppRuntimeException("", new Exception("cannot find Earliest Srd"));
		}
		if(EarliestSrd.before(tmw)){
			EarliestSrd=tmw;
		}
		logger.info("final EarliestSrd:"+EarliestSrd);
		appointment.setEstimatedSrd(Utility.date2String(EarliestSrd,"yyyy/MM/dd"));

		
		if("Y".equalsIgnoreCase(orderImsUi.getPreInstallInd())||"Y".equalsIgnoreCase(orderImsUi.getPreUseInd())
				||"Y".equalsIgnoreCase(imsOrderAmendservice.getIsRetPreRenew(orderImsUi.getOrderId()))){// 20170424 raymond said preRenew = IsPreInstOrder
			request.getSession().setAttribute("IsPreInstOrder", "Y");
		}else{
			request.getSession().setAttribute("IsPreInstOrder", "N");
		}
		
		String disableWqInd = "N";
		
		if("Y".equalsIgnoreCase(orderImsUi.getMobileOfferInd()))
		{
			disableWqInd = constantLkupService.getDisableWQInd();
		}
		
		request.getSession().setAttribute("disableWqInd", disableWqInd);
		
		orderImsUi.setIsCC(bomSalesUserDTO.getChannelId()==2||bomSalesUserDTO.getChannelId()==3||bomSalesUserDTO.getChannelId()==99?"Y":"N");
		orderImsUi.setIsPT(bomSalesUserDTO.getChannelId()==3?"Y":"N");
		orderImsUi.setIsCcTv(bomSalesUserDTO.getChannelId()==99?"Y":"N");
		
		String IsPreInstThirdOrder = "N";
		
		AmendmentModeDTO amendMode = new AmendmentModeDTO();
		
		if("Y".equalsIgnoreCase(orderImsUi.getPreInstallInd())||"Y".equalsIgnoreCase(imsOrderAmendservice.getIsRetPreRenew(orderImsUi.getOrderId()))){
			amendMode = imsOrderAmendservice.getPreInstallAmendMode(orderImsUi.getOrderId());
			if(!(("Y".equalsIgnoreCase(orderImsUi.getIsCcTv())||"Y".equalsIgnoreCase(orderImsUi.getIsCC())||"Y".equalsIgnoreCase(orderImsUi.getIsPT())||(Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES))
					&&(!"Y".equalsIgnoreCase(orderImsUi.getMobileOfferInd())||("Y".equalsIgnoreCase(orderImsUi.getMobileOfferInd())&&"N".equalsIgnoreCase(disableWqInd))))){
				amendMode.setUpdateCreditCard("N");
				amendMode.setFsAmendment("N");
			}
			if(!("Y".equalsIgnoreCase(orderImsUi.getIsCcTv())||"Y".equalsIgnoreCase(orderImsUi.getIsCC())||"Y".equalsIgnoreCase(orderImsUi.getIsPT()))){
				amendMode.setChangeLoginId("N");
				amendMode.setUpdateContactEmail("N");
			}
			
			if("Y".equalsIgnoreCase(amendMode.getChangeSRD())&&"N".equalsIgnoreCase(amendMode.getChangeCommDate())){
				IsPreInstThirdOrder = "Y";
			}
			
		}else{
			amendMode.setCancelOrder("Y");
			amendMode.setAmendAppointment("Y");
			if(("Y".equalsIgnoreCase(orderImsUi.getIsCcTv())||"Y".equalsIgnoreCase(orderImsUi.getIsCC())||"Y".equalsIgnoreCase(orderImsUi.getIsPT())||(Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES))
					&&(!"Y".equalsIgnoreCase(orderImsUi.getMobileOfferInd())||("Y".equalsIgnoreCase(orderImsUi.getMobileOfferInd())&&"N".equalsIgnoreCase(disableWqInd)))){
				amendMode.setUpdateCreditCard("Y");
				amendMode.setFsAmendment("Y");
			}else{
				amendMode.setUpdateCreditCard("N");
				amendMode.setFsAmendment("N");
			}
			if("Y".equalsIgnoreCase(orderImsUi.getIsCcTv())||"Y".equalsIgnoreCase(orderImsUi.getIsCC())||"Y".equalsIgnoreCase(orderImsUi.getIsPT())){
				amendMode.setChangeLoginId("Y");
				amendMode.setUpdateContactEmail("Y");
			}else{
				amendMode.setChangeLoginId("N");
				amendMode.setUpdateContactEmail("N");
			}
		}
		
		amend.setAmendModeDto(amendMode);
		
		request.getSession().setAttribute("IsPreInstThirdOrder", IsPreInstThirdOrder);
		
		String minDate = null;
		String maxMonth = null;
		
		if("Y".equalsIgnoreCase(orderImsUi.getPreInstallInd())||"Y".equalsIgnoreCase(orderImsUi.getPreUseInd())){
			List<String[]> para = appointmentService.getPreInstallParameter();
			
			
			for (String[] result : para){
				if ("MINDATE".equals(result[0])){
					minDate = result[1];
				}else if ("MAXMONTH".equals(result[0])){
					maxMonth = result[1];
				}
			}
		}
		
		logger.info("minDate:: "+minDate);
		logger.info("maxMonth:: "+maxMonth);

		request.setAttribute("minDate", minDate);
		request.setAttribute("maxMonth", maxMonth);
		
		
		
		amend.setRegisterName(orderImsUi.getCustomer().getLastName() + " " + orderImsUi.getCustomer().getFirstName());
		amend.setOrderImsUI(orderImsUi);
		amend.setThirdPartyInd("N");
		
		
	//Celia  -  Amendment Notification Distribution Mode	
		
		List<OrdEmailReqDTO> list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderIdIMS(orderImsUi.getOrderId(), null);
		String emailAddress = "";
		String SMSno = "";	
		if(list!=null && list.size()!=0){
			if(list.size()==1 ||(list.size()>1 && list.get(list.size()-1).getSeqNo() != list.get(list.size()-2).getSeqNo())){
				emailAddress = list.get(list.size()-1).getEmailAddr();
				SMSno = list.get(list.size()-1).getSMSno();
			}else {
				emailAddress = StringUtils.isBlank(list.get(list.size()-1).getEmailAddr())?list.get(list.size()-2).getEmailAddr():list.get(list.size()-1).getEmailAddr();
				SMSno = StringUtils.isBlank(list.get(list.size()-1).getSMSno())?list.get(list.size()-2).getSMSno():list.get(list.size()-1).getSMSno();
			}			
		}
		if(SMSno==null||"".equals(SMSno)){
			SMSno=orderImsUi.getSMSno();
		}
		amend.setEmailAddr(emailAddress);        	
		amend.setSMSno(SMSno);	
		
		if(ImsConstants.Retention.equals(orderImsUi.getImsOrderType()) || ImsConstants.Termination.equals(orderImsUi.getImsOrderType())) {
			amend.setAllowProgOfferChange("N");
		} else amend.setAllowProgOfferChange("Y");
		
		if("NTV-A".equals(orderImsUi.getOrderType()) || orderImsUi.isOrderTypeNowRet()){
			amend.setAllowTvContentChange("Y");			
			logger.info("setAllowTvContentChange(Y):");
		}else{
			amend.setAllowTvContentChange("N");		
			logger.info("setAllowTvContentChange(N):");
		}
		
		// self pick Hdd to FS Hdd start, by steven, 20171127
		List<NtvReplaceSelfPickHddDTO> replaceListOfSelfPickHdd = imsOrderDetailService.getReplaceSelfPickHddLkup();
		List<List<Object>>  logOfReplaceSelfHdd = new ArrayList<List<Object>>();
		List <Object> selfHdd = null;
		if(orderImsUi.getSubscribedItems()!=null){
			ArrayList<SubscribedItemUI> itemList = new ArrayList<SubscribedItemUI>(Arrays.asList(orderImsUi.getSubscribedItems()));
			for(int i=0;i<itemList.size();i++){
				for(NtvReplaceSelfPickHddDTO dto :replaceListOfSelfPickHdd ){
					if(dto.getId().equals(itemList.get(i).getId())){
						selfHdd = new ArrayList<Object>();
						selfHdd.add("non self pick item:"+itemList.get(i).getId());
						selfHdd.add(itemList.get(i));
						selfHdd.add(" is replaced by: "+dto.getReplaceId());
						selfHdd.add(dto);
						logOfReplaceSelfHdd.add(selfHdd);
						itemList.get(i).setId(dto.getReplaceId());	
						itemList.get(i).setType(dto.getReplaceType());	
						itemList.get(i).setBandwidth(Integer.parseInt(dto.getReplaceBw()));	
						itemList.get(i).setProdCd(dto.getReplaceProdCd());	
						itemList.get(i).setOfferCode(dto.getReplaceOfferCode());
						orderImsUi.setShowSelfPickHddForNtvRet("Y");
					}
				}
			}
			orderImsUi.setSubscribedItems(itemList.toArray((new SubscribedItemUI[itemList.size()])));	
			logger.info(orderImsUi.getCpqTxnId()+" logOfReplaceSelfHdd:"+gson.toJson(logOfReplaceSelfHdd));
		}else{		
			logger.info(orderImsUi.getCpqTxnId()+" logOfReplaceSelfHdd:non self pick item cannot be replaced since getSubscribedItems is null");							
		}
		// self pick Hdd to FS Hdd end, by steven, 20171127
		
		
		request.getSession().setAttribute(ImsConstants.IMS_ORDER, orderImsUi);
		
		if(!ImsConstants.Retention.equals(orderImsUi.getImsOrderType()) && 
		   !ImsConstants.Termination.equals(orderImsUi.getImsOrderType())){
			if(amend.getOrderImsUI().getCustomer().getIdDocType().equalsIgnoreCase("BS"))
				amend.setApplicantName(amend.getOrderImsUI().getCustomer().getCompanyName());
			else
				amend.setApplicantName(amend.getOrderImsUI().getCustomer().getContact().getContactName());
			String contacPhone = amend.getOrderImsUI().getCustomer().getContact().getContactPhone();
			if(StringUtils.isNotBlank(contacPhone)&& contacPhone.length()>8)
				contacPhone = contacPhone.substring(0,8);
			amend.setApplicantContactNo(contacPhone);
			amend.setApplicantRelationship("Sub");
//			logger.info("getApplicantName:" + amend.getApplicantName());
//			logger.info("getApplicantContactNo:" + amend.getApplicantContactNo());
//			logger.info("getApplicantRelationship:" + amend.getApplicantRelationship());
		}
		
		if("DS".equals(amend.getOrderImsUI().getImsOrderType())  ||
				(Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
			amend.setThirdPartyInd("N");
		}
		
		request.getSession().removeAttribute("order_for_amendment");
		
		if(bomSalesUserDTO!=null && bomSalesUserDTO.getUsername()!=null 
				&& "Y".equals(this.checkIfSuitableCategory(bomSalesUserDTO.getUsername()))){
			amend.setIsQualifiedCategory("Y");
		}else{
			amend.setIsQualifiedCategory("N");
		}
		amend.setIsDorder(orderImsUi.isDorder());
		
		if("Y".equals(orderImsUi.isDorder())){
			if("self".equalsIgnoreCase(imsOrderAmendservice.getEqtReturnMethod(orderImsUi.getOcId(), orderImsUi.getServiceIms().getFsa()))){
				amend.setTermDorderSelfReturn("Y");
			}else{
				amend.setTermDorderSelfReturn("N");				
			}
		}
		logger.debug(amend.getOrderImsUI().getOrderId()+" TermDorderSelfReturn: " + amend.getTermDorderSelfReturn());
//		amend.setIsDorder("N");
		logger.debug(amend.getOrderImsUI().getOrderId()+" orderImsUi.isDorder(): " + orderImsUi.isDorder());
		logger.debug("eddie orderImsUi: " + gson.toJson(orderImsUi));
		
		logger.info("amend:" + gson.toJson(amend));
		return amend;
	}

	public Date handleShortageAndL1Distributed(OrderImsUI orderImsUi,
			Date EarliestSrd, Date tmw, AppointmentUI appointment,
			ImsServiceSrdDTO addressInfo, Boolean isShortage,
			Boolean L1Distributed, String newTech) {
		for (int j=0; j < addressInfo.getServiceDetailList().size(); j++) {
					logger.info("BMO<0 Tech:"+addressInfo.getServiceDetailList().get(j).getTechnology()+
					" Earliest SRD:"+addressInfo.getServiceDetailList().get(j).getEstEarliestSrd());
			if (addressInfo.getServiceDetailList().get(j).getTechnology().equals(newTech) )  {
				if (addressInfo.getServiceDetailList().get(j).getTechnologySupported().equals("Y")) {
					
					if(!isShortage){
						if("Y".equals(addressInfo.getServiceDetailList().get(j).getIsDeadCase())||
						   "Y".equals(addressInfo.getServiceDetailList().get(j).getIsResrcShort())){
							logger.info("amend order not Shortage and (srd DeadCase or srd ResrcShort)");
							addressInfo.getServiceDetailList().get(j).setEstEarliestSrd(tmw);
							addressInfo.getServiceDetailList().get(j).setRtnDesc(null);
						}
					}else{
						if(L1Distributed&&!("Y".equalsIgnoreCase(addressInfo.getIs2NBuilding()))){
							logger.info("amend order is Shortage, and L1Distributed");
							addressInfo.getServiceDetailList().get(j).setEstEarliestSrd(tmw);
							addressInfo.getServiceDetailList().get(j).setRtnDesc(null);
						}
					}
					EarliestSrd = addressInfo.getServiceDetailList().get(j).getEstEarliestSrd();
					logger.info("BMO<0 final tech:"+newTech+" EarliestSrd:"+EarliestSrd);
					appointment.setEstSrdReason(addressInfo.getServiceDetailList().get(j).getRtnDesc());
					appointment.setBmoRmk(addressInfo.getServiceDetailList().get(j).getBmoRmk());
					orderImsUi.setBmoAlertRemarks(addressInfo.getServiceDetailList().get(j).getBmoAlert());
					break;
				}				
			} 			
		}
		return EarliestSrd;
	}

	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {		
		AmendOrderImsUI amend = (AmendOrderImsUI)command;
		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		logger.info("amend:"+gson.toJson(amend));
		
		if("Y".equalsIgnoreCase(amend.getIsAppointmentEnabled())
				&&(!"Y".equalsIgnoreCase(amend.getOrderImsUI().getPreInstallInd())&&!"Y".equalsIgnoreCase(amend.getOrderImsUI().getPreUseInd())
						||(("Y".equalsIgnoreCase(amend.getOrderImsUI().getPreInstallInd())||"Y".equalsIgnoreCase(amend.getOrderImsUI().getPreUseInd()))
						&&"Y".equalsIgnoreCase(amend.getIsPreInstApptEnabled())))){
			logger.info("set ImsConstants.IMS_AMEND_TIMESLOTTYPE:"+(String)request.getSession().getAttribute(ImsConstants.IMS_AMEND_TIMESLOTTYPE));
			amend.setTimeSlotType((String)request.getSession().getAttribute(ImsConstants.IMS_AMEND_TIMESLOTTYPE));
		}else{
			logger.info("set ImsConstants.IMS_AMEND_TIMESLOTTYPE null");
			request.getSession().setAttribute(ImsConstants.IMS_AMEND_TIMESLOTTYPE,null);
		}
		
		if(imsOrderAmendservice.isRmkEmpty(amend)){
			throw new AppRuntimeException("", new Exception("network error, plz try again"));
		}		
		
		Boolean isTvGuidedFlow=false;
		if(amend.getWqNatureIDList()!=null){
			for(String a:amend.getWqNatureIDList()){
				if(ImsConstants.IMS_AMEND_WQ_NATURE_TV_CONTENT_CHANGE.equals(a)){
					isTvGuidedFlow=true;
				}
			}
		}
		
		String isNowRetAmendTvOnly = "N";
		if(amend.getOrderImsUI().isOrderTypeNowRet() && "Y".equals(orderService.isNowRetAmendTvOnly(amend.getOrderImsUI())) && isTvGuidedFlow){
			isNowRetAmendTvOnly="Y";
		}
//		isNowRetAmendTvOnly="N";//next Phase, by steven 20160905//open on 20160920, asked by raymond
		
		logger.info(amend.getOrderImsUI().getOrderId()+" insertBomwebAmendCategory, isNowRetAmendTvOnly:"+isNowRetAmendTvOnly);
		try {
			imsOrderAmendservice.insertBomwebAmendCategory(amend, imsOrderAmendservice.createWqCombinedCoverSheetRemark(amend, true), isNowRetAmendTvOnly); 
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException("", new Exception("insert BOMWEB_AMEND_CATEGORY fail, please retry, if retry fail, please report to helpdesk"));
		}
		
		List<WorkQueueDTO> remarkForAmendInsert= null;
		try {
			remarkForAmendInsert=imsOrderAmendservice.imsAmendWorkQueue(amend);
		}catch (Exception e) {
			try {
				imsOrderAmendservice.updateBomwebAmendCategoryMarkX(amend);
			} catch (DAOException e1) {
				logger.error("updateBomwebAmendCategoryMarkX  error"+ExceptionUtils.getFullStackTrace(e1));	
			}			
			throw new AppRuntimeException("", new Exception("create WQ failed, please retry, if retry fail, please report to helpdesk"));
		}
		
		try {
			imsOrderAmendservice.updateBomwebAmendCategory(amend);
		} catch (DAOException e1) {
			logger.error("updateBomwebAmendCategory  error"+ExceptionUtils.getFullStackTrace(e1));			
		}
		
		try{
			if("Y".equals(isNowRetAmendTvOnly)){
				imsOrderAmendservice.updateNowRetAmendTvOnlyToAuto(amend);				
			}else if(("DS".equals(amend.getOrderImsUI().getImsOrderType())||amend.getOrderImsUI().isDs())
				&&"Y".equals(amend.isFS())){
				logger.info("go dummy, order_id:"+amend.getOrderImsUI().getOrderId());
				imsOrderAmendservice.updateFsAmendToDummy(amend);
			}else{
				logger.info("not dummy, order_id:"+amend.getOrderImsUI().getOrderId()+
						" type:"+amend.getOrderImsUI().getImsOrderType()+" isFS:"+amend.isFS());
			}
			if(amend.getWqNatureIDList().contains("362") || amend.getWqNatureIDList().contains("368")) {
				OrderImsUI order = (OrderImsUI) request.getSession().getAttribute("order_for_amendment");
				String isNtvOrder = amend.getWqNatureIDList().contains("368")?"Y":"N";
				orderService.insertBomwebAmendTables(order, remarkForAmendInsert.get(0).getCreatedBatchId(), isNtvOrder);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}

		//Celia  -  Amendment Notification Distribution Mode
		String orderId = amend.getOrderImsUI().getOrderId();
		String orderType = amend.getOrderImsUI().getOrderType();
		
		//NOWTV SALES 
		if(amend.getOrderImsUI().isOrderTypeNowRet()){	
			logger.debug("isOrderTypeNowRet:"+amend.getOrderImsUI().getCreateByUser().getChannelId());
			if("99".equals(String.valueOf(amend.getOrderImsUI().getCreateByUser().getChannelId()))){
				orderEsignatureService.createEmailReq(orderId, "AMNNRE99",new Date(), null , null, null , amend.getEmailAddr(), bomSalesUserDTO.getUsername() );
				List<OrdEmailReqDTO> list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderId(orderId,"AMNNRS99");
				imsSMSService.createSMSReq(orderId, new Date(), amend.getSMSno(),  bomSalesUserDTO.getUsername() , "AMNNRS99", ((list.size()==0)?1:list.get(list.size()-1).getSeqNo()+1));
			}else if("12".equals(String.valueOf(amend.getOrderImsUI().getCreateByUser().getChannelId()))){
				orderEsignatureService.createEmailReq(orderId, "AMNNRE12",new Date(), null , null, null , amend.getEmailAddr(), bomSalesUserDTO.getUsername() );
				List<OrdEmailReqDTO> list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderId(orderId,"AMNNRS12");
				imsSMSService.createSMSReq(orderId, new Date(), amend.getSMSno(),  bomSalesUserDTO.getUsername() , "AMNNRS12", ((list.size()==0)?1:list.get(list.size()-1).getSeqNo()+1));
			}else if("13".equals(String.valueOf(amend.getOrderImsUI().getCreateByUser().getChannelId()))){
				orderEsignatureService.createEmailReq(orderId, "AMNNRE13",new Date(), null , null, null , amend.getEmailAddr(), bomSalesUserDTO.getUsername() );
				List<OrdEmailReqDTO> list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderId(orderId,"AMNNRS13");
				imsSMSService.createSMSReq(orderId, new Date(), amend.getSMSno(),  bomSalesUserDTO.getUsername() , "AMNNRS13", ((list.size()==0)?1:list.get(list.size()-1).getSeqNo()+1));
			}else if("1".equals(String.valueOf(amend.getOrderImsUI().getCreateByUser().getChannelId()))){
				orderEsignatureService.createEmailReq(orderId, "AMNNRE1",new Date(), null , null, null , amend.getEmailAddr(), bomSalesUserDTO.getUsername() );
			}else if("2".equals(String.valueOf(amend.getOrderImsUI().getCreateByUser().getChannelId()))){
				//orderEsignatureService.createEmailReq(orderId, "NREUA2",new Date(), null , null, null , amend.getEmailAddr(), bomSalesUserDTO.getUsername() );
			}else{
				//PT
			}
		}else{
			if(orderType!=null && orderType.equalsIgnoreCase("NTV-A")){
				if("99".equals(String.valueOf(amend.getOrderImsUI().getCreateByUser().getChannelId()))){
					//Can amend but no email/sms
					//if(StringUtils.isNotBlank(amend.getEmailAddr())) {
					//	orderEsignatureService.createEmailReq(orderId, "AMNNAE99",new Date(), null , null, null , amend.getEmailAddr(), bomSalesUserDTO.getUsername() );
					//}
					//if (StringUtils.isNotBlank(amend.getSMSno())) {
					//	List<OrdEmailReqDTO> list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderId(orderId,"AMNNAS99");
					//	imsSMSService.createSMSReq(orderId, new Date(), amend.getSMSno(),  bomSalesUserDTO.getUsername() , "AMNNAS99", ((list.size()==0)?1:list.get(list.size()-1).getSeqNo()+1));
					//}
				}else if("12".equals(String.valueOf(amend.getOrderImsUI().getCreateByUser().getChannelId()))){
					if(StringUtils.isNotBlank(amend.getEmailAddr())) {
						orderEsignatureService.createEmailReq(orderId, "AMNNAE12",new Date(), null , null, null , amend.getEmailAddr(), bomSalesUserDTO.getUsername() );
					}
					if (StringUtils.isNotBlank(amend.getSMSno())) {
						List<OrdEmailReqDTO> list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderId(orderId,"AMNNAS12");
						imsSMSService.createSMSReq(orderId, new Date(), amend.getSMSno(),  bomSalesUserDTO.getUsername() , "AMNNAS12", ((list.size()==0)?1:list.get(list.size()-1).getSeqNo()+1));
					}
				}else if("13".equals(String.valueOf(amend.getOrderImsUI().getCreateByUser().getChannelId()))){
					if(StringUtils.isNotBlank(amend.getEmailAddr())) {
						orderEsignatureService.createEmailReq(orderId, "AMNNAE13",new Date(), null , null, null , amend.getEmailAddr(), bomSalesUserDTO.getUsername() );
					}
					if (StringUtils.isNotBlank(amend.getSMSno())) {
						List<OrdEmailReqDTO> list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderId(orderId,"AMNNAS13");
						imsSMSService.createSMSReq(orderId, new Date(), amend.getSMSno(),  bomSalesUserDTO.getUsername() , "AMNNAS13", ((list.size()==0)?1:list.get(list.size()-1).getSeqNo()+1));
					}
				}else if("1".equals(String.valueOf(amend.getOrderImsUI().getCreateByUser().getChannelId()))){
					if(StringUtils.isNotBlank(amend.getEmailAddr())) {
						orderEsignatureService.createEmailReq(orderId, "AMNNAE1",new Date(), null , null, null , amend.getEmailAddr(), bomSalesUserDTO.getUsername() );
					}
					//no sms
					//List<OrdEmailReqDTO> list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderId(orderId,"AMNNAS1");
					//imsSMSService.createSMSReq(orderId, new Date(), amend.getSMSno(),  bomSalesUserDTO.getUsername() , "AMNNAS1", ((list.size()==0)?1:list.get(list.size()-1).getSeqNo()+1));
				}else if("2".equals(String.valueOf(amend.getOrderImsUI().getCreateByUser().getChannelId()))){
					//Can amend but no email/sms
				}else{
					//PT
					//Can amend but no email/sms
				}
			}	
					
			//Ims Direct Sales (Consumer)
			else if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
				
			    if (bomSalesUserDTO.getChannelId()==12){
			    	orderEsignatureService.createEmailReq(orderId, "AMNT001",new Date(), null , null, null , amend.getEmailAddr(), bomSalesUserDTO.getUsername() );
					List<OrdEmailReqDTO> list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderId(orderId,"AMNT001");	  	    
			    	imsSMSService.createSMSReq(orderId, new Date(), amend.getSMSno(),  bomSalesUserDTO.getUsername() , "AMNT002", ((list.size()==0)?1:list.get(list.size()-1).getSeqNo()));    	   
			   
			    }else if (bomSalesUserDTO.getChannelId()==13){
					orderEsignatureService.createEmailReq(orderId, "AMNT003",new Date(), null , null, null , amend.getEmailAddr(), bomSalesUserDTO.getUsername() );
					List<OrdEmailReqDTO> list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderId(orderId,"AMNT003");			      
				    imsSMSService.createSMSReq(orderId, new Date(), amend.getSMSno(),  bomSalesUserDTO.getUsername() , "AMNT004", ((list.size()==0)?1:list.get(list.size()-1).getSeqNo()));    	   
			    	}
		    }
			//Ims Call Center
			else if (amend.getOrderImsUI().getIsCC().equals("Y") || amend.getOrderImsUI().getIsPT().equals("Y")){ //commented by jacky 20150109 
				/*logger.info("return amend.getOrderImsUI().getIsCC() list : " + new Gson().toJson(amend)); 
				
		    	if (amend.getNotificationSendConfirmInd().equals("Y")){
		    		if ( amend.getDisMode() == DisMode.I ){	
		    			if ("Y".equals(amend.getOrderImsUI().getIsPT()))				
		    				orderEsignatureService.createEmailReqIMS(orderId, "AMNT005",new Date(), null , null, null, amend.getEmailAddr(),  bomSalesUserDTO.getUsername());				
		    			else if ("Y".equals(amend.getOrderImsUI().getIsCC()))	
		    				orderEsignatureService.createEmailReqIMS(orderId, "AMNT006",new Date(), null , null, null, amend.getEmailAddr(),  bomSalesUserDTO.getUsername());
		    		}else if ( amend.getDisMode() == DisMode.S )
		    		{		
		    			imsSMSService.createSMSReq(orderId, new Date(), amend.getSMSno(),  bomSalesUserDTO.getUsername(), "Y".equals(amend.getOrderImsUI().getIsPT())?"AMNT008":"AMNT009");
		    		}
		    	}*/	    		
		    }
			//Ims Retail
			else {
		    	if (StringUtils.isNotBlank( amend.getEmailAddr())){
		    		orderEsignatureService.createEmailReq(orderId, "AMNT007",	new Date(), null , null, null, amend.getEmailAddr(),  bomSalesUserDTO.getUsername());
		    	}
		    }
		}
		
		request.getSession().setAttribute("ImsAmendJustDone", "Y");
		RedirectView nextView = new RedirectView("imsorderamendment.html?OrderIdForAmend="+amend.getOrderImsUI().getOrderId());
//		if(amend.getOrderImsUI().isOrderTypeNowRet()&&"Y".equals(amend.getImsAmendJustDone())){
//			logger.info("Y amend.getImsAmendJustDone():"+amend.getImsAmendJustDone());
//			logger.info("Y getOrderType():"+amend.getOrderImsUI().getOrderType());
//			nextView = new RedirectView("orderamend.html?sbuid=&orderId="+amend.getOrderImsUI().getOrderId());
//		}else{
//			logger.info("N amend.getImsAmendJustDone():"+amend.getImsAmendJustDone());
//			logger.info("N getOrderType():"+amend.getOrderImsUI().getOrderType());			
//		}

		return new ModelAndView(nextView);
	}

	public void sendNowRetSms(AmendOrderImsUI amend, 
			String locale, String shortenUrl, String smsTemplateId) {
		try{
			imsSMSService.sendNowRetSms(amend.getSMSno(),smsTemplateId, amend.getOrderImsUI().getOrderId(), locale, amend.getOrderImsUI().getSignOffDate(), shortenUrl);
		}catch (Exception e){
			logger.error("Exception caught in sendNowRetSms()", e);
		}
	}
	
	
	
	public Map<String, Object> referenceData(HttpServletRequest request) {
		Map<String, Object> referenceData = new HashMap<String, Object>();

//		OrderImsUI orderImsUi = null;
		List <DelayReasonDTO> cancelReasons = new ArrayList<DelayReasonDTO>();
		cancelReasons=imsOrderAmendservice.getCancelReason();
		referenceData.put("cancelReasons", cancelReasons);
		OrderImsUI orderImsUi = imsOrderDetailService.getOrder(request.getParameter("OrderIdForAmend"));
		
		List <AmendWqDTO> amendNatures = null;
		
		if(ImsConstants.Retention.equals(orderImsUi.getImsOrderType()) || ImsConstants.Termination.equals(orderImsUi.getImsOrderType())) {
			amendNatures=imsOrderAmendservice.getAmendNature("");
		}else{
			amendNatures=imsOrderAmendservice.getAmendNature("ACQ");
		}
		
//		if(request.getParameter("OrderIdForAmend")!=null){
//			orderImsUi = imsOrderDetailService.getOrder(request.getParameter("OrderIdForAmend"));
//			if(!imsOrderAmendservice.checkIfNeedAppointment(orderImsUi.getOrderId())){
//				for(int i=0; i<amendNatures.size(); i++){
//					if("346".equals(amendNatures.get(i).getWqNatureId())){
//						amendNatures.remove(i);
//						i--;
//					}
//				}
//			}
//		}
		referenceData.put("amendNatures", amendNatures);
		
		List <DelayReasonDTO> delayReasons = new ArrayList<DelayReasonDTO>();
		delayReasons=imsOrderAmendservice.getDelayReasons("abc");
		referenceData.put("delayReasons", delayReasons);
		
		List<String> asciiReplaceList = LookupTableBean.getInstance().getAsciiReplaceList();
		referenceData.put("asciiReplaceList", asciiReplaceList);
		
		referenceData.put("ntvdomain", this.getNtvDomain());
		
		return referenceData;
	}
	
	public String checkIfSuitableCategory(String staff_id) {		
		String enableSuitableCategory = "N";
		try{
			
			String input_staff_id= "'"+staff_id+"'";
			
			String sql = " SELECT DECODE(COUNT(*),0,'N','Y') ALLOW_SELECT_NO_CHARGE " +
			" FROM  W_IMS_SB_LKUP a, bomweb_sales_lkup_v b " + 
			" WHERE GRP_ID = 'TERM_D_O_FS_WO_C' " +
			" and (a.channel_id = b.channel_id or  a.channel_id = '*') " +
			" and (a.channel_cd = b.channel_cd or  A.channel_cd = '*') " +
			" and (a.centre_cd  = b.centre_cd  or  A.centre_cd = '*') " +
			" and (a.team_cd    = b.team_cd    or  A.team_cd = '*') " +
			" and (a.BASKET_GROUP   = b.category   or  A.BASKET_GROUP = '*') " +
			" and (staff_id = :staffId OR ORG_staff_id = :staffId) ";

			MapSqlParameterSource params =new MapSqlParameterSource();
			params.addValue("staffId", staff_id);
			List<Map<String, Object>> result = SpringApplicationContext.getBean(ImsCommonService.class).sb(sql,params);
			List<String> urls = new ArrayList<String>();
			for (Map<String, Object> row : result) {
				urls.add((String)row.get("ALLOW_SELECT_NO_CHARGE"));							
			}
			logger.info("enable SuitableCategory :"+gson.toJson(urls));
			if(urls!=null && urls.size()>0){
				for(String a:urls){
					if("Y".equals(a)){
						enableSuitableCategory="Y";
						logger.debug("Staff id: " + input_staff_id + " can see tick box");
					}		
				}
			}
			if("Y".equals(enableSuitableCategory)){
				logger.info(input_staff_id+" enable SuitableCategory");
			}else{
				logger.info(input_staff_id+" disable SuitableCategory");
			}
		}catch (Exception e){
			logger.error("enableSuitableCategory  error:",e);
		}
		return enableSuitableCategory;
	}
	
	
	

	public String checkIfTermSelfPickTargetCust(OrderImsUI order) {		
/*
 * Raymond said, if match below condition, show self return
 * 
 * Bandwidth – All line type 
 * Now TV - No
 * Router / Mesh Router = Yes        (in case, logistic flow is still complicated, we will remove this item from Phase 1). 
 * Housing Type  = Remain unchanged. 
 * Standalone VI  = No
 * Standalone eye  = No
 */
		String isTargetCust = "N";
		try{
			String sql = "select code from w_code_lkup where grp_id = 'PCD_T_SELF_PICK_VI'";
			List<Map<String, Object>> result = SpringApplicationContext.getBean(ImsCommonService.class).sb(sql);
			List<String> dbList = new ArrayList<String>();
			for (Map<String, Object> row : result) {
				dbList.add((String)row.get("code"));							
			}
			logger.info(order.getServiceIms().getFsa()+" order.getServiceIms().getNowTvInd():"
					+order.getServiceIms().getNowTvInd()
					+",  VI:"+gson.toJson(dbList));
			if(dbList.contains(order.getServiceIms().getNowTvInd())){//condition 1, VI
				isTargetCust="Y";							
			}				
			
			
			if("Y".equals(isTargetCust)){//condition 2, tech
				 sql = "select code from w_code_lkup where grp_id = 'PCD_T_SELF_PICK_TECH'";
				 result = SpringApplicationContext.getBean(ImsCommonService.class).sb(sql);
				 dbList = new ArrayList<String>();
				for (Map<String, Object> row : result) {
					dbList.add((String)row.get("code"));							
				}
				logger.info(order.getServiceIms().getFsa()+" order.getServiceIms().getAppLineType():"+order.getServiceIms().getAppLineType()
						+",  TECH:"+gson.toJson(dbList)	);
				if(dbList.contains(order.getServiceIms().getAppLineType())){
					isTargetCust="Y";					
				}else{
					isTargetCust="N";					
				}
			}
				
			if("Y".equals(isTargetCust)){//condition 3, housing type
				sql = "select code from w_code_lkup where grp_id = 'PCD_T_SELF_PICK_HOUSING_T'";
				result = SpringApplicationContext.getBean(ImsCommonService.class).sb(sql);
				dbList = new ArrayList<String>();
				for (Map<String, Object> row : result) {
					dbList.add((String)row.get("code"));							
				}				
				logger.info(order.getServiceIms().getFsa()
						+" order.getInstallAddress().getAddrInventory().getBuildingType():"
						+order.getInstallAddress().getAddrInventory().getBuildingType()
						+",  housingType:"+gson.toJson(dbList));
				if(dbList.contains(order.getInstallAddress().getAddrInventory().getBuildingType())){
					isTargetCust="Y";					
				}else{
					isTargetCust="N";					
				}
			}
						
			if("Y".equals(isTargetCust)){//condition 4, profile have to have at least one element of the parm list		
				sql = "select code from w_code_lkup where grp_id = 'PCD_T_SELF_PICK_PARM'";
				result = SpringApplicationContext.getBean(ImsCommonService.class).sb(sql);
				dbList = new ArrayList<String>();
				for (Map<String, Object> row : result) {
					dbList.add((String)row.get("code"));							
				}				
				List<String> profileParmList =new ArrayList<String>();

				String sql2 = "select *" +
						" from ims_parm a, ims_prdassoc b, b_product_a c , b_subc_prod d" +
						" where a.prd_id = b.prd_id " +
						" and d.SUBSCRIBER_NUM = :fsa " +
						" and c.prod_id = d.prod_id" +
						" and lpad(c.prod_id, 7, '0') = b.rel_prd_id" +
						" AND parm_val='Y'" +
						" and d.eff_start_date < sysdate and (sysdate < d.eff_end_date or d.eff_end_date is null)" +
						" and c.eff_start_date < sysdate and (sysdate < c.eff_end_date or c.eff_end_date is null)";
				MapSqlParameterSource params = new MapSqlParameterSource();
				params.addValue("fsa", order.getServiceIms().getFsa());
				List<Map<String, Object>> profileParmListResult = 
					SpringApplicationContext.getBean(ImsCommonService.class).bom(sql2,params);
				
				for (Map<String, Object> row : profileParmListResult) {
					profileParmList.add((String)row.get("PARM_DESC"));							
				}				
				logger.info(order.getServiceIms().getFsa()
						+" getProfileParmList:"	+profileParmList
						+",  DB PARM:"+gson.toJson(dbList));
				if(dbList.size()>0){
					if(profileParmList.size()>0){
						isTargetCust="N";//assume profile list has no element from parm list
						for(String a:dbList){
							for(String b:profileParmList){
								if(a.equals(b)){//profile list has as least one element from parm list  
									isTargetCust="Y";										
								}
							}
						}
					}else{
						isTargetCust="N";//db parm list has value but profile has no value				
					}
				}else{
					isTargetCust="Y";//db parm list has no value
				}
			}
			
		}catch (Exception e){
			logger.error("checkIfTermSelfPickTargetCust  error:",e);
		}
		logger.info(order.getServiceIms().getFsa()+" checkIfTermSelfPickTargetCust: "+isTargetCust);
		return isTargetCust;
	}
	
	
	public void enableSelfPick(OrderImsUI order) {		
		String enable= "N";
		try{			
			String sql = " SELECT CODE FROM w_code_lkup where grp_id = 'PCD_T_SELF_PICK_AMEND_ENA' ";
			List<Map<String, Object>> result = SpringApplicationContext.getBean(ImsCommonService.class).sb(sql);
			List<String> urls = new ArrayList<String>();
			for (Map<String, Object> row : result) {
				urls.add((String)row.get("CODE"));							
			}
			logger.info(order.getOrderId()+"enableSelfPick  :"+gson.toJson(urls));
			if(urls!=null && urls.size()>0){
				for(String a:urls){
					if("Y".equals(a)){
						enable="Y";
					}		
				}
			}
			if("Y".equals(enable)){
				logger.info(order.getOrderId()+"  enableSelfPick");
			}else{
				order.setIsTermDOrderSelfPickTargetCust("N");//disable
				logger.info(order.getOrderId()+"  disable SelfPick");
			}
		}catch (Exception e){
			logger.error("enableSelfPick  error:",e);
		}
	}
}
