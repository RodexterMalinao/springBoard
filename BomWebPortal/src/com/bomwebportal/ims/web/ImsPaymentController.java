package com.bomwebportal.ims.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.SalesmanDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.exception.ImsMobilePinException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.BasketDetailsDTO;
import com.bomwebportal.ims.dto.BomwebOTDTO;
import com.bomwebportal.ims.dto.DiscountedOtInstallChrgDTO;
import com.bomwebportal.ims.dto.ImsInstallationInstallmentPlanDTO;
import com.bomwebportal.ims.dto.ui.ImsPaymentUI;
import com.bomwebportal.ims.dto.ui.InstallFeeUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.dto.ui.SubscribedChannelUI;
import com.bomwebportal.ims.dto.ui.SubscribedItemUI;
import com.bomwebportal.ims.service.GetSourceCodeService;
import com.bomwebportal.ims.service.ImsOrderDetailService;
import com.bomwebportal.ims.service.ImsPaymentService;
import com.bomwebportal.ims.service.PTService;
import com.bomwebportal.ims.service.ReleaseLoginIDService;
import com.bomwebportal.service.ConstantLkupService;
import com.bomwebportal.service.LoginService;
import com.bomwebportal.service.MobilePINService;
import com.bomwebportal.service.MobilePINServiceImpl.MobileOffer;
import com.bomwebportal.util.Utility;
import com.bomwebportal.web.ext.BomWebPortalApplicationFlow;
import com.google.gson.Gson;
import com.pccw.util.search.Criteria.Order;
import com.bomwebportal.ims.dto.SalesReferrerDTO;

public class ImsPaymentController extends SimpleFormController{
    protected final Log logger = LogFactory.getLog(getClass());

    private ImsPaymentService imsPaymentService;
    private ReleaseLoginIDService releaseLoginIDService;
    private LoginService loginService;
    private ImsOrderDetailService imsOrderDetailService;
    private GetSourceCodeService getSourceCodeService;
    private PTService PTS;
    private MobilePINService mobilePINService;
    private ConstantLkupService constantLkupService;
	
    public MobilePINService getMobilePINService() {
		return mobilePINService;
	}

	public void setMobilePINService(MobilePINService mobilePINService) {
		this.mobilePINService = mobilePINService;
	}
    public PTService getPTS() {
		return PTS;
	}

	public void setPTS(PTService pTS) {
		PTS = pTS;
	}

	private Gson gson = new Gson();    
    
	public ImsPaymentService getImsPaymentService() {
		return imsPaymentService;
	}

	public void setImsPaymentService(ImsPaymentService imsPaymentService) {
		this.imsPaymentService = imsPaymentService;
	}

	public ReleaseLoginIDService getReleaseLoginIDService() {
		return releaseLoginIDService;
	}

	public void setReleaseLoginIDService(ReleaseLoginIDService releaseLoginIDService) {
		this.releaseLoginIDService = releaseLoginIDService;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public ImsOrderDetailService getImsOrderDetailService() {
		return imsOrderDetailService;
	}

	public void setImsOrderDetailService(ImsOrderDetailService imsOrderDetailService) {
		this.imsOrderDetailService = imsOrderDetailService;
	}

	public GetSourceCodeService getGetSourceCodeService() {
		return getSourceCodeService;
	}

	public void setGetSourceCodeService(GetSourceCodeService getSourceCodeService) {
		this.getSourceCodeService = getSourceCodeService;
	}

	public void setConstantLkupService(ConstantLkupService constantLkupService) {
		this.constantLkupService = constantLkupService;
	}

	public ConstantLkupService getConstantLkupService() {
		return constantLkupService;
	}

	private BomWebPortalApplicationFlow appFlow;
    
	public BomWebPortalApplicationFlow getAppFlow() {
		return appFlow;
	}

	public void setAppFlow(
			BomWebPortalApplicationFlow appFlow) {
		this.appFlow = appFlow;
	}
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException{
//		logger.info("formBackingObject");
		Calendar today = Calendar.getInstance();
		logger.debug(today.get(Calendar.YEAR) + today.get(Calendar.MONTH)+1);
		String Locale = RequestContextUtils.getLocale(request).toString().contains("en")?"EN":"CHI";
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		
//		if(sessionOrder!=null){logger.info("ImsOrderID:"+sessionOrder.getOrderId());};
		ImsPaymentUI sessionPayment = new ImsPaymentUI();
		SalesmanDTO salesmanDTO = null;
		if(sessionOrder.getCustomer().getAccount().getPayment() != null){
			BeanUtils.copyProperties(sessionOrder.getCustomer().getAccount().getPayment(), sessionPayment);
		}
		String imsSubmitTag = "";
		if(request.getSession().getAttribute("imsSubmitTag") != null){
			imsSubmitTag = (String)request.getSession().getAttribute("imsSubmitTag");
		}
		if(imsSubmitTag.equals("S") || imsSubmitTag.equals("R") || imsSubmitTag.equals("C") || imsSubmitTag.equals("W")){
			String imsOrderId = (String)request.getSession().getAttribute("imsOrderId");
			sessionPayment.setOrderId(imsOrderId);
			logger.info("Order ID: " + imsOrderId);
			request.getSession().setAttribute("imsOrderId", null);
		}
		sessionPayment.setYear(Integer.toString(today.get(Calendar.YEAR)));
		sessionPayment.setMonth(Integer.toString(today.get(Calendar.MONTH)+1));
		sessionPayment.setBillMedia(sessionOrder.getCustomer().getAccount().getBillMedia());
		sessionPayment.setMode(sessionOrder.getMode());
//		logger.info(sessionOrder.getLoginId());
		if(sessionOrder.getCustomer().getAccount().getEmailAddr() == null){
			//sessionOrder.getCustomer().getAccount().setEmailAddr(sessionOrder.getLoginId()+"@netvigator.com");
			//sessionOrder.getCustomer().getAccount().setEmailAddr(payment.getEmailAddr());
			//sessionPayment.setEmailAddr(sessionOrder.getCustomer().getAccount().getEmailAddr());
			sessionPayment.setEmailAddr(sessionOrder.getLoginId()+"@netvigator.com");
		}else{
			sessionPayment.setEmailAddr(sessionOrder.getCustomer().getAccount().getEmailAddr());
		}
		//sessionPayment.setPrepayAmt(sessionOrder.getPrepayAmt());
		//sessionPayment.setOtInstallChrg(sessionOrder.getOtInstallChrg());  //TT
		String technology = sessionOrder.getInstallAddress().getAddrInventory().getTechnology();
	    String housingtype = sessionOrder.getInstallAddress().getHousingTypeList().get(0).getHousingType();
	    String serbdyno = sessionOrder.getInstallAddress().getSerbdyno();
	    String floor = sessionOrder.getInstallAddress().getFloorNo();
	    String flat = sessionOrder.getInstallAddress().getUnitNo();
	    System.out.println("serbdyno is: "+ serbdyno);
	    System.out.println("technology is: "+technology);		
		System.out.println("housing type is: "+housingtype);
		
		System.out.println("Order.getOtInstallChrg is: "+sessionOrder.getOtInstallChrg());	
		System.out.println("payment.getOtInstallChrg is: "+sessionPayment.getOtInstallChrg());	
		System.out.println("Order.getCompForOtInstallChrg is: "+sessionOrder.getInstallOTDesc_en());//Gary	
		System.out.println("payment.getCompForOtInstallChrg is: "+sessionPayment.getInstallOTDesc_en());	

		String fsPrepayCutOffDateStr = constantLkupService.getImsFsPrepayCutOff();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		Date fsPrepayCutOffDate = new Date();
		try {
			fsPrepayCutOffDate = formatter.parse(fsPrepayCutOffDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Date appDate = null;
		
		if(sessionOrder.getAppDate()!=null){
			appDate = sessionOrder.getAppDate();
		}else{
			appDate = new Date();
		}

		String fsPrepayCutOff = "Y";

	    if(appDate.before(fsPrepayCutOffDate)){
	    	fsPrepayCutOff = "N";
        }
	    
		request.getSession().setAttribute("fsPrepayCutOff", fsPrepayCutOff);
		

		InstallFeeUI InstallFeeNormal;
		InstallFeeUI InstallFeeSpecial;
		
		/*
		InstallFee = imsPaymentService.getOtAmount(technology, housingtype);
		sessionPayment.setOtInstallChrg(InstallFee.getOnetimeAmount());   //TT
		System.out.println("onetime charge: "+sessionPayment.getOtInstallChrg());
			
		sessionPayment.setInstallInstallmentAmt(""+InstallFee.getInstallInstalmentAmt());   //Kinman
		System.out.println("onetime charge install installment amount: "+sessionPayment.getInstallInstallmentAmt());
		String temp_amt = Integer.toString(InstallFee.getInstallInstalmentAmt());
		
		sessionPayment.setInstallInstallmentMth(""+InstallFee.getInstallInstalmentMth());   //Kinman
		System.out.println("onetime charge Monthly: "+sessionPayment.getInstallInstallmentMth());
		String temp_month = Integer.toString(InstallFee.getInstallInstalmentMth());
		
		*/
		String basket_id = sessionOrder.getSubscribedItems()[0].getBasketId();
		
		String[] item_id_str = new String[sessionOrder.getSubscribedItems().length];
		
		for(int i =0;i<sessionOrder.getSubscribedItems().length;i++){
			item_id_str[i] = sessionOrder.getSubscribedItems()[i].getId();			
		}
	
    
		logger.info(item_id_str);
		//InstallFee = imsPaymentService.getOTCByBasketId(basket_id);

		InstallFeeNormal = imsPaymentService.getImsInstallationInstallmentPlanList(technology, housingtype, serbdyno, floor, flat);
		InstallFeeSpecial = imsPaymentService.getSpecialOTC(basket_id,item_id_str);
		
		List<ImsInstallationInstallmentPlanDTO> imsInstallationInstallmentPlanList1 = new ArrayList<ImsInstallationInstallmentPlanDTO>();
		
		//kinman 2013
		if((InstallFeeSpecial == null || InstallFeeSpecial.getImsInstallationInstallmentPlanList() == null || InstallFeeSpecial.getImsInstallationInstallmentPlanList().size() == 0)
				|| ("B".equalsIgnoreCase(sessionOrder.getServiceWaiver())) || ("V".equalsIgnoreCase(sessionOrder.getServiceWaiver())) || ("G".equalsIgnoreCase(sessionOrder.getServiceWaiver())) || ("G".equalsIgnoreCase(sessionOrder.getServiceWaiverNowTVPage()))
				|| "0".equalsIgnoreCase(InstallFeeNormal.getImsInstallationInstallmentPlanList().get(0).getOneTimeFee())){
			imsInstallationInstallmentPlanList1 = InstallFeeNormal.getImsInstallationInstallmentPlanList();
			sessionPayment.setErrorCode(""+InstallFeeNormal.getErrorCode());
			sessionPayment.setErrorText(""+InstallFeeNormal.getErrorText());
		}else{
			imsInstallationInstallmentPlanList1 = InstallFeeSpecial.getImsInstallationInstallmentPlanList();
			sessionPayment.setErrorCode(""+InstallFeeSpecial.getErrorCode());
			sessionPayment.setErrorText(""+InstallFeeSpecial.getErrorText());
		}
		
		System.out.println("error code:"+sessionPayment.getErrorCode());
		
		System.out.println("error text:"+sessionPayment.getErrorText());
		

		String charge;
		String Month;
		
		if( imsInstallationInstallmentPlanList1!=null )
		{
			sessionPayment.setInstallInstallmentAmt(imsInstallationInstallmentPlanList1.get(0).getInstallmentPlanAmt());
			sessionPayment.setInstallInstallmentMth(imsInstallationInstallmentPlanList1.get(0).getInstallmentPlanMnth());
			sessionPayment.setOtChrgType(imsInstallationInstallmentPlanList1.get(0).getOtChrgType());
			if ( imsInstallationInstallmentPlanList1.size()== 1 )
			{  
				sessionPayment.setOnlyOneInstallInstallmentPlanInd("Y"); //kinman	 
				System.out.println("OnlyOneInstallInstallmentPlanInd: "+ sessionPayment.getOnlyOneInstallInstallmentPlanInd());
			}
			else
			{
				sessionPayment.setOnlyOneInstallInstallmentPlanInd("N"); //kinman
				System.out.println("OnlyOneInstallInstallmentPlanInd: "+ sessionPayment.getOnlyOneInstallInstallmentPlanInd());
				
				for( int i=0; i<imsInstallationInstallmentPlanList1.size(); i++ )
				{
					charge = imsInstallationInstallmentPlanList1.get(i).getInstallmentPlanAmt();
					Month = imsInstallationInstallmentPlanList1.get(i).getInstallmentPlanMnth();
					System.out.println("imsInstallationInstallmentPlanList1: "+ charge + "x"+ Month);
					System.out.println("imsInstallationInstallmentPlanList1: "+ imsInstallationInstallmentPlanList1.get(i).getInstallPlanDisplay());
					System.out.println("imsInstallationInstallmentPlanList1: "+ imsInstallationInstallmentPlanList1.get(i).getInstallPlanValue());
			//		System.out.println("imsInstallationInstallmentPlanList: "+ imsInstallationInstallmentPlanList.get(i));
				}
			}
		}
		
		sessionPayment.setOtInstallChrg(imsInstallationInstallmentPlanList1.get(0).getOneTimeFee());
		sessionPayment.setInstallOtCode(imsInstallationInstallmentPlanList1.get(0).getInstallOtCode());//Gary
		sessionPayment.setInstallOTDesc_en(imsInstallationInstallmentPlanList1.get(0).getInstallOTDesc_en());//Gary
		sessionPayment.setInstallOTDesc_zh(imsInstallationInstallmentPlanList1.get(0).getInstallOTDesc_zh());//Gary
		sessionPayment.setInstallmentCode(imsInstallationInstallmentPlanList1.get(0).getInstallmentCode());//Gary
		sessionPayment.setInstallOtQty(imsInstallationInstallmentPlanList1.get(0).getInstallOtQty());//set the ot qty by Tony
		request.setAttribute("imsInstallationInstallmentPlanList1", imsInstallationInstallmentPlanList1);
		sessionOrder.setOtChrgType(sessionPayment.getOtChrgType());
		
		
		//Add Kinman
		if (sessionPayment.getInstallInstallmentAmt()!=null){
			if(!sessionPayment.getInstallInstallmentAmt().equals("0")){
				sessionPayment.setIsValidForInstallInstallment("Y");

			}else {sessionPayment.setIsValidForInstallInstallment("N");
			}
		}else sessionPayment.setIsValidForInstallInstallment("N");
		
		//set original installment indicator
		if (sessionOrder.getInstallmentChrg()!=null){
			if(!sessionOrder.getInstallmentChrg().equals("0")){
				sessionPayment.setInstallInstalmentInd("Y");
			}else sessionPayment.setInstallInstalmentInd("N");
		}else sessionPayment.setInstallInstalmentInd("N");
		//
		
		//Tony otInstallChrg for amendment order
//		if ("Y".equals(sessionOrder.isSignoffed())
//				&&sessionOrder.getInstallAddress().getAddrInventory().getTechnology().equals(sessionOrder.getOrderImg().getInstallAddress().getAddrInventory().getTechnology())) {
//			
//			if(sessionOrder.getOrderImg().getOtInstallChrg().indexOf("-")>=0){
//				sessionPayment.setOtInstallChrg(sessionOrder.getOrderImg().getOtInstallChrg().substring(1));
//			}else{
//				sessionPayment.setOtInstallChrg(sessionOrder.getOrderImg().getOtInstallChrg());
//			}
//			sessionPayment.setInstallOtCode(sessionOrder.getOrderImg().getInstallOtCode());
//			sessionPayment.setInstallOTDesc_en(sessionOrder.getOrderImg().getInstallOTDesc_en());
//			sessionPayment.setInstallOTDesc_zh(sessionOrder.getOrderImg().getInstallOTDesc_zh());
//			sessionPayment.setInstallmentCode(sessionOrder.getOrderImg().getInstallmentCode());
//			
//			if(sessionOrder.getOrderImg().getInstallmentCode()!=null&&sessionOrder.getOrderImg().getInstallmentCode().length()>0){
//				sessionPayment.setInstallInstallmentAmt(sessionOrder.getOrderImg().getInstallmentChrg());
//				sessionPayment.setInstallInstallmentMth(sessionOrder.getOrderImg().getInstallmentMonth());
//				sessionPayment.setIsValidForInstallInstallment("Y");
//				sessionPayment.setInstallInstalmentInd("Y");
//				sessionPayment.setOnlyOneInstallInstallmentPlanInd("Y");
//			}else{
//				sessionPayment.setIsValidForInstallInstallment("N");
//			}
//		}
		
		
		
		//sessionPayment.setIsValidForInstallInstallment("Y"); //kinman testing
		System.out.println("IsValidForInstallInstallment:"+sessionPayment.getIsValidForInstallInstallment());
		
		String bandwidth = sessionOrder.getBandwidth();
		
		System.out.println("bandwidth is: "+ bandwidth);
		System.out.println("serbdyno is: "+ serbdyno);
		
		Date appdate = Calendar.getInstance().getTime();
		System.out.println("appdate: "+ appdate);	
		
		
		
		
		
		if(sessionOrder.getAppDate()!=null)
			appdate =sessionOrder.getAppDate();

		sessionPayment.setWaiveOtApproval(sessionOrder.getWaiveOtApprovalResult());
		sessionPayment.setIsValidForWaive(imsPaymentService.getIsValidForWaive(serbdyno, technology, bandwidth, housingtype, appdate)); //TT
		if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
			sessionPayment.setIsValidForWaive("N"); //jacky
			sessionPayment.setShopCd(sessionOrder.getChannelCd()); 
		}
		
		
		
		List<BasketDetailsDTO> basketInfoList = imsPaymentService.getBasketInfo(basket_id);
		
		String result = sessionPayment.getIsValidForWaive();
		String result2 = sessionOrder.getWaiveOtApprovalResult();
		String compForOtInstallChrg =sessionPayment.getInstallOTDesc_en();
		
		String otInstallChrgReq = basketInfoList.get(0).getOtInstallChrgReq();
		
		List<BomwebOTDTO> specialOTClist = imsPaymentService.getOverrideOTC(user.getUsername(), "ACQ", technology, housingtype, serbdyno, "I", null);

		List<BomwebOTDTO> removeList = new ArrayList<BomwebOTDTO>();
		
		//handle If INSTALL_WAIVE_IND is 'Y' and INSTALL_OT_AMT contain no value, the original ISF/ASF will be waived
		String waiveBySpecialOT = "N";
		String hideOriginalOTCInd = "N";
		for(BomwebOTDTO dto:specialOTClist){
			if(((dto.getInstallOTAmt()==null||dto.getInstallOTAmt().isEmpty())&&"Y".equalsIgnoreCase(dto.getInstallWaiveInd())&&"I".equals(sessionOrder.getOtChrgType()))
					||((dto.getActivateOTAmt()==null||dto.getActivateOTAmt().isEmpty())&&"Y".equalsIgnoreCase(dto.getActivateWaiveInd())&&"A".equals(sessionOrder.getOtChrgType()))){
				removeList.add(dto);
				waiveBySpecialOT = "Y";
			}
			if(dto.getHideOriginalFee()!=null&&"Y".equalsIgnoreCase(dto.getHideOriginalFee())){
				hideOriginalOTCInd = "Y";
			}
		}

		if(removeList!=null){
			for(int i =0;i<specialOTClist.size();i++){
				for(int j =0;j<removeList.size();j++){
					if(specialOTClist.get(i).getId()==(removeList.get(j).getId())){
						specialOTClist.remove(i);
					}
				}
			}
		}
		//handle If INSTALL_WAIVE_IND is 'Y' and INSTALL_OT_AMT contain no value, the original ISF/ASF will be waived (end)
		sessionPayment.setBomwebOTList(specialOTClist);
		sessionPayment.setHideOriginalOTCInd(hideOriginalOTCInd);
		
		if (result2==null){ 
			result2="N";
		};
		System.out.println("Is valid for waive?: "+ result);	
		System.out.println("Waive Ot Approved?: "+ result2);	
		if (result.equals("Y")||result2.equals("Y")||otInstallChrgReq.equals("N")||"Y".equalsIgnoreCase(sessionOrder.getMobileOfferInd())||sessionOrder.getServiceWaiver()!=null||sessionOrder.getServiceWaiverNowTVPage()!=null
				||"Y".equals(waiveBySpecialOT)){
			sessionPayment.setWaivedOtInstallChrg(sessionPayment.getOtInstallChrg()+"(Waived)");
			if(sessionOrder.getServiceWaiver()!=null){
				if("B".equalsIgnoreCase(sessionOrder.getServiceWaiver())){
					sessionPayment.setWaivedOtInstallChrg(sessionPayment.getOtInstallChrg()+"(Waived)");
				}
				if("V".equalsIgnoreCase(sessionOrder.getServiceWaiver())){
					sessionPayment.setWaivedOtInstallChrg(sessionPayment.getOtInstallChrg()+"(Waived)");
				}
				if("G".equalsIgnoreCase(sessionOrder.getServiceWaiver())){
					sessionPayment.setWaivedOtInstallChrg(sessionPayment.getOtInstallChrg()+"(Waived)");
				}
			}
			if(sessionOrder.getServiceWaiverNowTVPage()!=null){
				if("G".equalsIgnoreCase(sessionOrder.getServiceWaiver())){
					sessionPayment.setWaivedOtInstallChrg(sessionPayment.getOtInstallChrg()+"(Waived)");
				}
			}
			sessionPayment.setOtInstallChrg("-"+sessionPayment.getOtInstallChrg());	
			sessionPayment.setInstallInstalmentInd("N");//Tony bug fix
		}else if ("0".equalsIgnoreCase(imsInstallationInstallmentPlanList1.get(0).getOneTimeFee())){
			sessionPayment.setWaivedOtInstallChrg("Waived");
		}else{
			sessionPayment.setWaivedOtInstallChrg(sessionPayment.getOtInstallChrg());	
			if(compForOtInstallChrg!=null && !compForOtInstallChrg.equalsIgnoreCase("null")){//Gary
				sessionPayment.setWaivedOtInstallChrg(sessionPayment.getWaivedOtInstallChrg()+compForOtInstallChrg);
			}
		}
		
		if(otInstallChrgReq.equals("N")||"Y".equalsIgnoreCase(sessionOrder.getMobileOfferInd())||sessionOrder.getServiceWaiver()!=null||sessionOrder.getServiceWaiverNowTVPage()!=null){
			sessionPayment.setIsValidForWaive("Y"); 
		}
		
		//handle mass project install fee 'WAIVED' case
		if(sessionOrder.getInstallAddress().getAddrInventory().getBuildingType()!=null
				&&sessionOrder.getInstallAddress().getAddrInventory().getBuildingType().indexOf("MassProject")>-1
				&&sessionPayment.getOtInstallChrg()!=null&&sessionPayment.getOtInstallChrg().indexOf("WAIVED")>-1){
			sessionPayment.setOtInstallChrg(null);
			sessionPayment.setWaivedOtInstallChrg("Waived");
		}
		
		//initiate special otc string for recall order
		if(sessionPayment.getSpecialOTString()==null||sessionPayment.getSpecialOTString().isEmpty()){
			if(sessionOrder.getSpecialOTCId()!=null&&!sessionOrder.getSpecialOTCId().isEmpty()){
				for(BomwebOTDTO dto:sessionPayment.getBomwebOTList()){
					if (sessionOrder.getSpecialOTCId().equals(""+dto.getId())){
						if(sessionOrder.getInstallmentCode()!=null&&!sessionOrder.getInstallmentCode().isEmpty()){//installment 
							sessionPayment.setSpecialOTString("SOTCC_"+sessionOrder.getSpecialOTCId());
						}else{//not installment
							sessionPayment.setSpecialOTString("SOTC_"+sessionOrder.getSpecialOTCId());
						}
					}
				}
			}else if("Y".equalsIgnoreCase(hideOriginalOTCInd)){//if original otc is hidden, tick the first special otc
				if(sessionPayment.getBomwebOTList()!=null&&sessionPayment.getBomwebOTList().size()>0){
					sessionPayment.setSpecialOTString("SOTC_"+sessionPayment.getBomwebOTList().get(0).getId());
				}
			}
		}
		//initiate special otc wavied ind
		if(sessionOrder.getOtInstallChrg()!=null&&!sessionOrder.getOtInstallChrg().isEmpty()&&sessionOrder.getOtInstallChrg().indexOf("-")>-1){
			sessionPayment.setSpecialOTCWaivedInd("Y");
		}
		
		String basketTitle = basketInfoList.get(0).getTitle();
		basketTitle = basketTitle.indexOf("<br/>") > 0?basketTitle.substring(0, basketTitle.indexOf("<br/>")):basketTitle;

		String basketAmt = basketInfoList.get(0).getMthFixText();
		
		String idDocType = "BS".equals(sessionOrder.getCustomer().getIdDocType())?"HKBR": //HKBR = BS
			"PASS".equals(sessionOrder.getCustomer().getIdDocType())?"Passport":		  //Passport = PASS		
				sessionOrder.getCustomer().getIdDocType();								  //HKID = HKID
		
		String text = "Document Type: " + idDocType + ((char)10) +"Plan: "+ basketTitle+ ((char)10)+ "Plan Charge: "+ basketAmt + ((char)10) +"Installation/Activation Charge: "+ "$"+ sessionPayment.getOtInstallChrg() + ((char)10) + "Waive Reason: ";
		
		if ( "I".equals(sessionOrder.getOtChrgType()))
			text = "Document Type: " + idDocType + ((char)10) +"Plan: "+ basketTitle+ ((char)10)+ "Plan Charge: "+ basketAmt + ((char)10) +"Installation Charge: "+ "$"+ sessionPayment.getOtInstallChrg() + ((char)10) + "Waive Reason: ";
		else if ( "A".equals(sessionOrder.getOtChrgType()))
			text = "Document Type: " + idDocType + ((char)10) +"Plan: "+ basketTitle+ ((char)10)+ "Plan Charge: "+ basketAmt + ((char)10) +"Activation Charge: "+ "$"+ sessionPayment.getOtInstallChrg() + ((char)10) + "Waive Reason: ";
		
		sessionPayment.setTextAreaInfoWO(text);

		String text2 = "Document Type: " + sessionOrder.getCustomer().getIdDocType()+ ((char)10) +"Plan: "+ basketTitle+ ((char)10)+ "Plan Charge: "+ basketAmt + ((char)10) +"Prepayment: "+"$"+ sessionOrder.getPrepayCash() + ((char)10) + "Waive Reason: ";
		
		sessionPayment.setTextAreaInfoWP(text2);
		//steven added on 20130111
		String text3 = "Plan: "+ basketTitle+ ((char)10)+ "Plan Charge: "+ basketAmt + ((char)10)+"Waive Reason: ";
		sessionPayment.setTextAreaInfo(text3);
		//steven added on 20130111 end
		sessionPayment.setPrepayCc(sessionOrder.getPrepayCC());
		sessionPayment.setPrepayCash(sessionOrder.getPrepayCash());
		sessionPayment.setCashFsPrepay(sessionOrder.getCashFsPrepay());
		sessionPayment.setProcessCreditCard(sessionOrder.getProcessCreditCard());
		sessionPayment.setIsCcOffer("Y".equalsIgnoreCase(sessionOrder.getIsCreditCardOfferNowTVPage())?"Y":sessionOrder.getIsCreditCardOffer());
		sessionPayment.setIdDocType(sessionOrder.getCustomer().getIdDocType());
		sessionPayment.setNowTvFormType(sessionOrder.getNowTvFormType());
		sessionPayment.setRegisterName(sessionOrder.getCustomer().getLastName() + " " + sessionOrder.getCustomer().getFirstName());
		logger.info(sessionPayment.getRegisterName());
		
    	if(user == null){
    		user = new BomSalesUserDTO();
    	}
		logger.info("user.getShopCd():"+user.getShopCd()+" sessionPayment.getShopCd():"+sessionPayment.getShopCd()+" user.getUsername():"+user.getUsername());
		
		if(!(Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES))
			sessionPayment.setShopCd(user.getShopCd());
		
		if(sessionOrder.getSalesCd() != null && !("").equals(sessionOrder.getSalesCd()))
		{
			sessionPayment.setSalesCd(sessionOrder.getSalesCd());
		}
		else
		{
			sessionPayment.setSalesCd(imsPaymentService.getOrgSalesCd(user.getUsername()));
		}
		
		//salesmanDTO = imsPaymentService.getSalesman("S", sessionPayment.getSalesCd());
		if(sessionOrder.getSalesName() != null){
			sessionPayment.setSalesName(sessionOrder.getSalesName());
		}else{
			sessionPayment.setSalesName(imsPaymentService.getStaffNameByOrgSalesCd(user.getUsername()));
		}
		//sessionOrder.setSalesErrCode(salesmanDTO.getErrCode());
		//sessionOrder.setSalesErrCode(salesmanDTO.getErrMsg());
		//logger.info(salesmanDTO.getSalesName());
		if ( sessionOrder.getSalesContactNum() == null || sessionOrder.getSalesContactNum().isEmpty())
		{
				sessionPayment.setSalesContactNum(imsPaymentService.getShopContact(user.getShopCd()));
				
		}else{
			sessionPayment.setSalesContactNum(sessionOrder.getSalesContactNum());
		}
		if (StringUtils.isEmpty(sessionPayment.getSalesContactNum())) {
			sessionPayment.setSalesContactNum(imsPaymentService.getSalemanContactNumByStaffId(user.getUsername()));
		}
	   
		//set source code for DS
		if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
			if ( sessionOrder.getSourceCd() == null || sessionOrder.getSourceCd().isEmpty()){
				sessionPayment.setSourcecode(imsPaymentService.getDeflaultSourceCode(user.getUsername()));
			}else{
				sessionPayment.setSourcecode(sessionOrder.getSourceCd());
			}
			logger.info("DS-sessionPayment.getSourcecode():" + sessionPayment.getSourcecode());
		}
		
		logger.debug("getShopCd:"+sessionPayment.getShopCd()+
				"getSalesCd:"+sessionPayment.getSalesCd()+
				"getSalesName:"+sessionPayment.getSalesName()+
				"getSalesName:"+sessionPayment.getSalesContactNum());
		
		if(sessionOrder.getReasonCd() != null && sessionOrder.getReasonCd().substring(0, 1).equals("S")){
			sessionPayment.setsReasonCd(sessionOrder.getReasonCd());
		}
		if(sessionOrder.getReasonCd() != null && sessionOrder.getReasonCd().substring(0, 1).equals("C")){
			sessionPayment.setcReasonCd(sessionOrder.getReasonCd());
		}
		sessionPayment.setSubmitTag((String)request.getSession().getAttribute("imsSubmitTag"));
		
		sessionPayment.setOrderActionInd(sessionOrder.getOrderActionInd());
		sessionPayment.setOrderStatus(sessionOrder.getOrderStatus());

		Entry<String, String>[] slist;
		
		logger.info("sessionPayment");
		
		if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
			slist = imsOrderDetailService.getLookUpMapByLocale(LookupTableBean.getInstance().getImsDSSuspendLookupMap(),Locale).entrySet().toArray(new Entry[0]);
		}
		else if ( !"Y".equals(sessionOrder.getIsCC()) ||  !"Y".equals(sessionOrder.getIsPT()) ) 
		{
			slist = imsOrderDetailService.getLookUpMapByLocale(LookupTableBean.getInstance().getImsSuspendLookupMap(),Locale).entrySet().toArray(new Entry[0]);
		}
		else {
			slist = imsOrderDetailService.getLookUpMapByLocale(LookupTableBean.getInstance().getImsPTSuspendLookupMap(),Locale).entrySet().toArray(new Entry[0]);
		}
		
		for(int i=0; i<slist.length; i++){
			System.out.println(slist[i].getKey()+":"+slist[i].getValue());
		}
		Entry<String, String>[] clist = imsOrderDetailService.getLookUpMapByLocale(LookupTableBean.getInstance().getImsCancelLookupMap(),Locale).entrySet().toArray(new Entry[0]);
		if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
			clist = imsOrderDetailService.getLookUpMapByLocale(LookupTableBean.getInstance().getImsDSCancelLookupMap(),Locale).entrySet().toArray(new Entry[0]);
		}
		for(int i=0; i<clist.length; i++){
			System.out.println(clist[i].getKey()+":"+clist[i].getValue());
		}
		request.setAttribute("suspendList", slist);
		request.setAttribute("cancelList", clist);
		
		
		
		
		//sessionOrder.getAppDate()
				
		sessionPayment.setCashApproval(sessionOrder.getCashApprovalResult());
		sessionPayment.setWaiveApproval(sessionOrder.getWaiveApprovalResult());
		sessionPayment.setPreInstallInd(sessionOrder.isPreinstallation());
		sessionPayment.setApprovalRequested(sessionOrder.getApprovalRequested());
		if ( sessionOrder.getOtChrgType() != null && !sessionOrder.getOtChrgType().isEmpty())
			sessionPayment.setOtChrgType(sessionOrder.getOtChrgType());
		request.getSession().setAttribute("payment", sessionPayment);
		sessionOrder.getInstallAddress().getAddrInventory().getTechnology();
		sessionOrder.getInstallAddress().getHousingTypeList();
		
		
		////////////////////////////////////////////////////////////////////////////////////////
		//--------------------------------------------------------------- Call Center arrangment
		////////////////////////////////////////////////////////////////////////////////////////
		
		if ( "Y".equals(sessionOrder.getIsCC()) )
		{
		
			//if ( user.getChannelId() == 2 )
			try {
				sessionPayment.setDiscountedOtInstallAmt(Double.valueOf(sessionPayment.getWaivedOtInstallChrg()));
			}catch( NumberFormatException nfe)
			{
				logger.info("Exception caught in setDiscountedOtInstallAmt()", nfe);
				sessionPayment.setDiscountedOtInstallAmt(0);
			}
			
			
			
			sessionPayment.setCallDate(Utility.date2String(sessionOrder.getCallDate(), "dd/MM/yyyy"));
			sessionPayment.setCallTime(Utility.date2String(sessionOrder.getCallDate(), "HH:mm"));
			sessionPayment.setAppMethod(sessionOrder.getAppMethod());
			sessionPayment.setSourcecode(sessionOrder.getSourceCd());
			sessionPayment.setPno(sessionOrder.getPositionNo());
			
			List<Map<String, String>> nowTVPTList =  PTS.getNowTVPTList();
			
			for (Map<String,String> i:nowTVPTList)
			{
				if(i.get("code").equalsIgnoreCase(sessionOrder.getNowTvFormType()))
				{
					sessionPayment.setIsCcOffer("Y");
				}
			}
			
			if ( sessionOrder.getSalesContactNum() == null || sessionOrder.getSalesContactNum().isEmpty())
			{
				sessionPayment.setSalesContactNum(imsPaymentService.getShopContact(user.getChannelCd()));
				
				logger.info("ChannelCd: " + user.getChannelCd()+ ", sessionPayment.getSalesContactNum():"+sessionPayment.getSalesContactNum());
				if ( sessionPayment.getSalesContactNum() == null || "".equals(sessionPayment.getSalesContactNum()))
				{
					sessionPayment.setSalesContactNum("1000");
				}
				
				int PTNum = PTS.getSalesNum(sessionPayment.getSalesCd());
				if ( PTNum != 0 )
					sessionPayment.setSalesContactNum(String.valueOf(PTNum));
			}
			else
			{
				sessionPayment.setSalesContactNum(sessionOrder.getSalesContactNum());
			}
			
			int discountedOtInstallChrg1 = 0;
			List<DiscountedOtInstallChrgDTO> DiscountedOtInstallChrgList = new ArrayList<DiscountedOtInstallChrgDTO>();
			DiscountedOtInstallChrgDTO DiscountedOtInstallChrg;
			
			if (sessionPayment.getDiscountedOtInstallAmt() <= 1000)
			{
				sessionPayment.setDiscountedOtInstallAmt(0);
			}
			else{
				if ( (int) (Math.floor(sessionPayment.getDiscountedOtInstallAmt()))%1000 > 0 )
				{
					discountedOtInstallChrg1 = (int) (Math.floor(sessionPayment.getDiscountedOtInstallAmt()/1000)*1000);
				}
				else
				{
					discountedOtInstallChrg1 = (int) (sessionPayment.getDiscountedOtInstallAmt() - 1000);
				}
				DiscountedOtInstallChrg = new DiscountedOtInstallChrgDTO();
				DiscountedOtInstallChrg.setDiscountedOtInstallChrgAmt(String.valueOf(discountedOtInstallChrg1));
				DiscountedOtInstallChrg.setDiscountedOtInstallChrgDisplay("$" + String.valueOf(discountedOtInstallChrg1));
				DiscountedOtInstallChrgList.add(DiscountedOtInstallChrg);
				
				if ( discountedOtInstallChrg1 - 1000 > 0)
				{
					DiscountedOtInstallChrg = new DiscountedOtInstallChrgDTO();
					DiscountedOtInstallChrg.setDiscountedOtInstallChrgAmt(String.valueOf(discountedOtInstallChrg1-1000));
					DiscountedOtInstallChrg.setDiscountedOtInstallChrgDisplay("$" + String.valueOf(discountedOtInstallChrg1-1000));
					DiscountedOtInstallChrgList.add(DiscountedOtInstallChrg);
				}
				
				
				if ( discountedOtInstallChrg1 - 2000 > 0)
				{
					DiscountedOtInstallChrg = new DiscountedOtInstallChrgDTO();
					DiscountedOtInstallChrg.setDiscountedOtInstallChrgAmt(String.valueOf(discountedOtInstallChrg1-2000));
					DiscountedOtInstallChrg.setDiscountedOtInstallChrgDisplay("$" + String.valueOf(discountedOtInstallChrg1-2000));
					DiscountedOtInstallChrgList.add(DiscountedOtInstallChrg);
				}
				
			}
			
			
			
			if ( DiscountedOtInstallChrgList.size() == 0)
				DiscountedOtInstallChrgList = null;
			
			sessionPayment.setDiscountedOtInstallChrgList(DiscountedOtInstallChrgList);
			
			//Tony add
			
			String result3 = sessionOrder.getOtDiscountApprovalResult();
			
			sessionPayment.setDiscountOTCApproval(result3);
			
			if (result3==null){
				result3="N";
			};
			logger.info("Discount approval result: "+ result3);	
	
			if (result3.equals("Y")){
				//sessionPayment.setWaivedOtInstallChrg(sessionPayment.getRequestSelectDiscountOTC()+"(Adjusted)");
				if (result2.equals("Y")){}
				else{
				sessionPayment.setWaivedOtInstallChrg(sessionOrder.getOtInstallChrg());
					if(compForOtInstallChrg!=null && !compForOtInstallChrg.equalsIgnoreCase("null")){//Gary
						sessionPayment.setWaivedOtInstallChrg(sessionPayment.getWaivedOtInstallChrg()+compForOtInstallChrg);
					}
				}
				
			}else{
//				sessionPayment.setWaivedOtInstallChrg(sessionPayment.getOtInstallChrg());
//				if(compForOtInstallChrg!=null && !compForOtInstallChrg.equalsIgnoreCase("null")){//Gary
//					sessionPayment.setWaivedOtInstallChrg(sessionPayment.getWaivedOtInstallChrg()+compForOtInstallChrg);
//				}
			}
			
			//Tony add end
			
			String text4 = "Document Type: " + idDocType + ((char)10) +"Plan: "+ basketTitle+ ((char)10)+ "Plan Charge: "+ basketAmt + ((char)10) +"Installation/Activation Charge: "+ "$"+ sessionPayment.getOtInstallChrg() + ((char)10) + "Request Reason: ";
			String text4Presrc = "Document Type: " + idDocType + ((char)10) +"Plan: "+ basketTitle+ ((char)10)+ "Plan Charge: "+ basketAmt + ((char)10) +"Installation/Activation Charge: "+ "$"+ sessionPayment.getOtInstallChrg() + ((char)10) + "Request for: $";
			
			if ( "I".equals(sessionOrder.getOtChrgType()))
			{
				text4 = "Document Type: " + idDocType + ((char)10) +"Plan: "+ basketTitle+ ((char)10)+ "Plan Charge: "+ basketAmt + ((char)10) +"Installation Charge: "+ "$"+ sessionPayment.getOtInstallChrg() + ((char)10) + "Request Reason: ";
				text4Presrc = "Document Type: " + idDocType + ((char)10) +"Plan: "+ basketTitle+ ((char)10)+ "Plan Charge: "+ basketAmt + ((char)10) +"Installation Charge: "+ "$"+ sessionPayment.getOtInstallChrg() + ((char)10) + "Request for: $";
			}
			else if ( "A".equals(sessionOrder.getOtChrgType()))
			{
				text4 = "Document Type: " + idDocType + ((char)10) +"Plan: "+ basketTitle+ ((char)10)+ "Plan Charge: "+ basketAmt + ((char)10) +"Activation Charge: "+ "$"+ sessionPayment.getOtInstallChrg() + ((char)10) + "Request Reason: ";
				text4Presrc = "Document Type: " + idDocType + ((char)10) +"Plan: "+ basketTitle+ ((char)10)+ "Plan Charge: "+ basketAmt + ((char)10) +"Activation Charge: "+ "$"+ sessionPayment.getOtInstallChrg() + ((char)10) + "Request for: $";
			}
			
			String text4Sufsrc = ((char)10) + "Request Reason: ";
			sessionPayment.setTextAreaInfoDO(text4);
			request.getSession().setAttribute("textAreaInfoDOPreSrc", text4Presrc);
			request.getSession().setAttribute("textAreaInfoDOSufSrc", text4Sufsrc);
			
			Calendar date = new GregorianCalendar();
	    	SimpleDateFormat date_format1 = new SimpleDateFormat("dd/MM/yyyy");
	    	SimpleDateFormat date_format2 = new SimpleDateFormat("HH:mm");
	    	
	    	sessionPayment.setCallDate(date_format1.format(date.getTime()));
	    	sessionPayment.setCallTime(date_format2.format(date.getTime()));
	    	
	    	
	    	
	    	if ( sessionPayment.getAppMethod() != null && !"".equalsIgnoreCase(sessionPayment.getAppMethod()))
	    	{
	    		//request.getSession().setAttribute("sourceCodeList", getSourceCodeService.getSourceCode(sessionPayment.getAppMethod()));
	    	}
	    	
	    	try {
	    		
	    		String channel = "CC";
	    		
	    		if("Y".equalsIgnoreCase(sessionOrder.getMobileOfferInd())){
	    			channel = channel + "_M";
	    		}
	    		
	    		List<Map<String,String>> map = getSourceCodeService.getSourceCode(channel);
	    		
//	    		List<SourceCodeDTO> list = new ArrayList<SourceCodeDTO>();
//	    		SourceCodeDTO k;
//	    		for ( Map<String,String> i:map)
//	    		{
//	    			k = new SourceCodeDTO();
//	    			k.setAppMethod(i.get("appMethod"));
//	    			k.setSourceCode(i.get("sourceCode"));
//	    			list.add(k);
//	    		}
	    		
			request.getSession().setAttribute("sourceCodeList", map);
			
			
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	sessionPayment.setIsPT(sessionOrder.getIsPT());
	    	sessionPayment.setIsCC(sessionOrder.getIsCC());
	    	
	    	//kinman 20140428			
			logger.info("sessionPayment.getAppMethod(): "+sessionPayment.getAppMethod());
			logger.info("sessionPayment.getSourcecode(): "+sessionPayment.getSourcecode());

			if( sessionPayment.getAppMethod() == null && sessionPayment.getSourcecode() == null){									
				sessionPayment.setSourceCodeDefault(imsPaymentService.getDeflaultSourceCode(user.getUsername()));			
				if(sessionPayment.getSourceCodeDefault()!= null && !"".equalsIgnoreCase(sessionPayment.getSourceCodeDefault())){			
					sessionPayment.setAppMethodCdDefault(getSourceCodeService.getDeflaultAppMethod(sessionPayment.getSourceCodeDefault()));					
					if(sessionPayment.getAppMethodCdDefault()== null){
						sessionPayment.setAppMethodCdDefault(getSourceCodeService.getDeflaultAppMethodRetry(sessionPayment.getSourceCodeDefault()));						
					 	}
					
					if (sessionPayment.getAppMethodCdDefault()!= null && !"".equalsIgnoreCase(sessionPayment.getAppMethodCdDefault())){				
						sessionPayment.setSourcecode(sessionPayment.getSourceCodeDefault());
						sessionPayment.setAppMethod(sessionPayment.getAppMethodCdDefault());					
					}
				}	
			}
			//end kinman 20140428
			
			
			
			//referral for Call center
			if(sessionOrder.getSalesReferrerDTO() != null){
				SalesReferrerDTO salesReferrerDTO = new SalesReferrerDTO();
				salesReferrerDTO.setReferrerAppMethod(sessionOrder.getSalesReferrerDTO().getReferrerAppMethod());
				salesReferrerDTO.setReferrerAppMethodDesc(sessionOrder.getSalesReferrerDTO().getReferrerAppMethodDesc());
				salesReferrerDTO.setReferrerSourcecode(sessionOrder.getSalesReferrerDTO().getReferrerSourcecode());
				salesReferrerDTO.setReferrerStaffNo(sessionOrder.getSalesReferrerDTO().getReferrerStaffNo());
				salesReferrerDTO.setReferrerStaffName(sessionOrder.getSalesReferrerDTO().getReferrerStaffName());
				sessionPayment.setSalesReferrerDTO(salesReferrerDTO);
			}
	    	
	    	///////////////////////////////////////////////////////////////////////////////////////
			//----------------------------------------------------- Call Center arrangment ( Ends )
			///////////////////////////////////////////////////////////////////////////////////////
		}
		

		
		
		
		
		sessionPayment.setIsDS(sessionOrder.getIsDS());
		sessionPayment.setMobileOfferInd(sessionOrder.getMobileOfferInd());
		
		request.getSession().setAttribute(ImsConstants.IMS_ORDER, sessionOrder);
		
		if ("Y".equals(sessionOrder.isSignoffed())) {
			this.setFormView("imsamendpayment");
			sessionPayment.setIsAmendOrder("Y");
		}
		else {
			this.setFormView("imspayment");
			sessionPayment.setIsAmendOrder("N"); 
		}
		
		logger.debug("sessionPayment : " + new Gson().toJson(sessionPayment));
		return sessionPayment;
	}

	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
		
		ImsPaymentUI payment = (ImsPaymentUI)command;

		logger.info("getAppMethodDesc:"+payment.getAppMethodDesc());
		
		
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		if(sessionOrder!=null)
		{
			logger.info("ImsOrderID:"+sessionOrder.getOrderId());
		}
		
		BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
    	if(user == null){
    		user = new BomSalesUserDTO();
    	}
		
		//Add by Tony 
		//Make CC required document null
		if ( "Y".equals(sessionOrder.getIsCC())){
			sessionOrder.setAllOrdDocAssgnDTOs(null);
			sessionOrder.setImsCollectDocDtoList(null);
		}
		//End
		
		String fsPrepayCutOff = (String) request.getSession().getAttribute("fsPrepayCutOff");
		if(payment.getPayMtdType().equals("M")){
			if("Y".equalsIgnoreCase(fsPrepayCutOff)){
				payment.setThirdPartyInd("");
				payment.setCcHoldName("");
				payment.setCcIdDocType("");
				payment.setCcIdDocNo("");
				payment.setCcType("");
				payment.setCcNum("");
				payment.setCcExpDate("");
				payment.setCcVerified("");
				payment.setCashFsPrepay("");
				payment.setWaivedPrepay("Y");
				payment.setProcessCreditCard("");
				sessionOrder.setPrepayAmt("0");
			}else{
			payment.setThirdPartyInd("");
			payment.setCcHoldName("");
			payment.setCcIdDocType("");
			payment.setCcIdDocNo("");
			payment.setCcType("");
			payment.setCcNum("");
			payment.setCcExpDate("");
			payment.setCcVerified("");
			payment.setProcessCreditCard("");
			sessionOrder.setPrepayAmt(sessionOrder.getPrepayCash());
			}
		}else if(payment.getPayMtdType().equals("C")){
			sessionOrder.setCashFsPrepay("");
			payment.setCashFsPrepay("");
			sessionOrder.setPrepayAmt(sessionOrder.getPrepayCC());
			if ( "Y".equals(sessionOrder.getIsCC()) && !"R".equalsIgnoreCase(sessionOrder.getMode()) )
				payment.setCcVerified("N");
		}
		
		if("Y".equalsIgnoreCase(sessionOrder.getMobileOfferInd())){
			payment.setCashFsPrepay("");
			payment.setWaivedPrepay("Y");
			payment.setProcessCreditCard("");
			sessionOrder.setPrepayAmt("0");
		}
		
		System.out.println(gson.toJson(sessionOrder));
		System.out.println(gson.toJson(payment));
		//sessionOrder.setOtInstallChrg(imsPaymentService.getOtAmount(sessionOrder.getInstallAddress().getAddrInventory().getTechnology(), sessionOrder.getInstallAddress().getHousingTypeList().get(0).getHousingType()));   //TT
		
		sessionOrder.getCustomer().getAccount().setPayment(payment);
		sessionOrder.setCashFsPrepay(payment.getCashFsPrepay());
		sessionOrder.setProcessCreditCard(payment.getProcessCreditCard());
		sessionOrder.getCustomer().getAccount().setBillMedia(payment.getBillMedia());
		sessionOrder.getCustomer().getAccount().setEmailAddr(payment.getEmailAddr());
		sessionOrder.setShopCd(payment.getShopCd());
		sessionOrder.setSalesCd(payment.getSalesCd());
		sessionOrder.setSalesName(payment.getSalesName());
		sessionOrder.setSalesContactNum(payment.getSalesContactNum());
		sessionOrder.setWaivedPrepay(payment.getWaivedPrepay());	
		if ("Y".equalsIgnoreCase(sessionOrder.getIsCC())&&"Y".equalsIgnoreCase(sessionOrder.getIsPT())){
			if(!"Y".equalsIgnoreCase(sessionOrder.getOtDiscountApprovalResult())){
				sessionOrder.setOtInstallChrg(payment.getOtInstallChrg());
				sessionOrder.setInstallOtCode(payment.getInstallOtCode());//Gary
				sessionOrder.setInstallOTDesc_en(payment.getInstallOTDesc_en());//Gary
				sessionOrder.setInstallOTDesc_zh(payment.getInstallOTDesc_zh());//Gary
				sessionOrder.setInstallOtQty(payment.getInstallOtQty());//Tony
			}
		}else{
			sessionOrder.setOtInstallChrg(payment.getOtInstallChrg());
			sessionOrder.setInstallOtCode(payment.getInstallOtCode());//Gary
			sessionOrder.setInstallOTDesc_en(payment.getInstallOTDesc_en());//Gary
			sessionOrder.setInstallOTDesc_zh(payment.getInstallOTDesc_zh());//Gary
//			sessionOrder.setInstallmentCode(payment.getInstallmentCode());//Gary
			sessionOrder.setInstallOtQty(payment.getInstallOtQty());//Tony
		}
		
		// NOWTVSALES - System Finding
		if (user.getChannelId() == 13) {
			imsOrderDetailService.getSystemFinding(sessionOrder, user.getUsername(), user.getChannelId(), Utility.date2String(sessionOrder.getCustomer().getDob(), "MM/dd/yyyy"));
			logger.info("Order: " + gson.toJson(sessionOrder));
		}
		// NOWTVSALES - System Finding (end)
		
		GregorianCalendar callDate = null;
		if ( payment.getCallDate() != null && !"".equals(payment.getCallDate())
				&& payment.getCallTime() != null && !"".equals(payment.getCallTime()) )
		{
			callDate =  new GregorianCalendar(
					Integer.parseInt(payment.getCallDate().substring(6, 10)),
					Integer.parseInt(payment.getCallDate().substring(3, 5))-1,
					Integer.parseInt(payment.getCallDate().substring(0, 2)),
					Integer.parseInt(payment.getCallTime().substring(0, 2)),
					Integer.parseInt(payment.getCallTime().substring(3,5))
				);
			sessionOrder.setCallDate(callDate.getTime());
			//sessionOrder.setCallTime(payment.getCallTime());
		}
		
		if (StringUtils.isEmpty(payment.getAppMethod())) {
			if ("Y".equals(sessionOrder.getIsCC()) ||  "Y".equals(sessionOrder.getIsDS()) ||"Y".equals(sessionOrder.getIsPT())){
				//getSourceCodeService.getDirectSalesAppMethod(payment, "Y".equals(payment.getIsCC())||"Y".equals(sessionOrder.getIsDS())?payment.getSourcecode():payment.getShopCd());
				getSourceCodeService.getDeflaultAppMethod("Y".equals(payment.getIsCC())||"Y".equals(sessionOrder.getIsDS())?payment.getSourcecode():payment.getShopCd());
			}else getSourceCodeService.getRetailAppMethod(payment.getShopCd());
		}
		
		
		sessionOrder.setAppMethod(payment.getAppMethod());
		sessionOrder.setAppMethodDesc(payment.getAppMethodDesc());
		sessionOrder.setSourceCd(payment.getSourcecode());
		sessionOrder.setPositionNo(payment.getPno());
		
		System.out.println("payment.getOtInstallChrg(): "+ payment.getOtInstallChrg());	
		System.out.println("payment.getInstallInstalmentInd(): "+ payment.getInstallInstalmentInd());	
		
		
		//kinman Mar 2013
		
		String tempInstallmentPlanAmt =""; 
		String tempInstallmentPlanMnth ="";
		
		String TempinstallmentplanMultiple = payment.getTempinstallmentplan();
		System.out.println("TempinstallmentplanMultiple"+ TempinstallmentplanMultiple);	
		
		if (payment.getInstallInstalmentInd().equals("Y") && payment.getOnlyOneInstallInstallmentPlanInd().equals("N") ){
		System.out.println("payment.getTempinstallmentplan(): "+ payment.getTempinstallmentplan());	
		request.getSession().setAttribute("tempinstallmentplan", payment.getTempinstallmentplan());

		String selectedInstallmentPlan = payment.getTempinstallmentplan();
		String[] parts = selectedInstallmentPlan.split(",");
		tempInstallmentPlanAmt = parts[0]; 
		tempInstallmentPlanMnth = parts[1]; 
		}
		
		if(payment.getInstallInstalmentInd().equals("Y")){
			//sessionOrder.setInstallmentChrg(payment.getInstallInstallmentAmt());
			//sessionOrder.setInstallmentMonth(payment.getInstallInstallmentMth());
			if (payment.getOnlyOneInstallInstallmentPlanInd().equals("N")){
			sessionOrder.setInstallmentChrg(tempInstallmentPlanAmt);
			sessionOrder.setInstallmentMonth(tempInstallmentPlanMnth);
			sessionOrder.setInstallmentCode(payment.getInstallmentCode());//Gary
			}
			else
			{
			sessionOrder.setInstallmentChrg(payment.getInstallInstallmentAmt());
			sessionOrder.setInstallmentMonth(payment.getInstallInstallmentMth());
			sessionOrder.setInstallmentCode(payment.getInstallmentCode());//Gary
			}
		}else{
			sessionOrder.setInstallmentChrg("0");
			sessionOrder.setInstallmentMonth("0");
			sessionOrder.setInstallmentCode(null);//Gary
		}

//		System.out.println("abb: "+ sessionOrder.getInstallmentChrg());	
//		System.out.println("cdd: "+ sessionOrder.getInstallmentMonth());	

		
		//override ot install charge in order object if special otc selected
		sessionOrder.setSpecialOTCId("");
		if(payment.getSpecialOTString()!=null&&!payment.getSpecialOTString().isEmpty()){
			String s = payment.getSpecialOTString();
			//SOTCC->special installment otc selected
			if(s.indexOf("SOTCC")>-1){
				String[] parts = s.split("_");
				String specialOTCId = parts[1];
				for(BomwebOTDTO dto:payment.getBomwebOTList()){
					if (specialOTCId.equals(""+dto.getId())){
						if("I".equals(sessionOrder.getOtChrgType())){
							sessionOrder.setOtInstallChrg(dto.getInstallOTAmt());
							sessionOrder.setInstallOtCode(dto.getInstallOTCode());
							sessionOrder.setInstallOTDesc_en(dto.getInstallChrgDescEn());
							sessionOrder.setInstallOTDesc_zh(dto.getInstallChrgDescCn());
							sessionOrder.setInstallOtQty(dto.getInstallOTQty());
							sessionOrder.setInstallmentChrg(dto.getInstallInstalmentAmt());
							sessionOrder.setInstallmentMonth(dto.getInstallInstalmentMth());
							sessionOrder.setInstallmentCode(dto.getInstallInstalmentCode());
						}else if("A".equals(sessionOrder.getOtChrgType())){
							sessionOrder.setOtInstallChrg(dto.getActivateOTAmt());
							sessionOrder.setInstallOtCode(dto.getActivateOTCode());
							sessionOrder.setInstallOTDesc_en(dto.getInstallChrgDescEn());
							sessionOrder.setInstallOTDesc_zh(dto.getInstallChrgDescCn());
							sessionOrder.setInstallOtQty(dto.getActivateOTQty());
							sessionOrder.setInstallmentChrg(dto.getInstallInstalmentAmt());//???
							sessionOrder.setInstallmentMonth(dto.getInstallInstalmentMth());//???
							sessionOrder.setInstallmentCode(dto.getActivateInstalmentCode());
						}
						
					}
				}
				sessionOrder.setSpecialOTCId(specialOTCId);
			}
			//SOTC->special otc selected
			else if(s.indexOf("SOTC")>-1){
				String[] parts = s.split("_");
				String specialOTCId = parts[1];
				for(BomwebOTDTO dto:payment.getBomwebOTList()){
					if (specialOTCId.equals(""+dto.getId())){
						if("I".equals(sessionOrder.getOtChrgType())){
							if("Y".equalsIgnoreCase(payment.getSpecialOTCWaivedInd())){
								sessionOrder.setOtInstallChrg("-"+dto.getInstallOTAmt());
							}else{
								sessionOrder.setOtInstallChrg(dto.getInstallOTAmt());
							}
							sessionOrder.setInstallOtCode(dto.getInstallOTCode());
							sessionOrder.setInstallOTDesc_en(dto.getInstallChrgDescEn());
							sessionOrder.setInstallOTDesc_zh(dto.getInstallChrgDescCn());
							sessionOrder.setInstallOtQty(dto.getInstallOTQty());
						}else if("A".equals(sessionOrder.getOtChrgType())){
							if("Y".equalsIgnoreCase(payment.getSpecialOTCWaivedInd())){
								sessionOrder.setOtInstallChrg("-"+dto.getActivateOTAmt());
							}else{
								sessionOrder.setOtInstallChrg(dto.getActivateOTAmt());
							}
							sessionOrder.setInstallOtCode(dto.getActivateOTCode());
							sessionOrder.setInstallOTDesc_en(dto.getInstallChrgDescEn());
							sessionOrder.setInstallOTDesc_zh(dto.getInstallChrgDescCn());
							sessionOrder.setInstallOtQty(dto.getActivateOTQty());
						}
						sessionOrder.setInstallmentChrg("0");
						sessionOrder.setInstallmentMonth("0");
						sessionOrder.setInstallmentCode(null);
						
					}
				}
				sessionOrder.setSpecialOTCId(specialOTCId);
			}
		}
		//override ot install charge in order object if special otc selected
		
		
		
/*
		if(payment.getReasonCd() != null){
			sessionOrder.setReasonCd(payment.getReasonCd());
		}
*/		
		if ( "Y".equals(sessionOrder.getIsCC()) &&  payment.getSalesReferrerDTO() != null ){
			SalesReferrerDTO salesReferrerDTO = new SalesReferrerDTO();
			salesReferrerDTO.setReferrerAppMethod(payment.getSalesReferrerDTO().getReferrerAppMethod());
			salesReferrerDTO.setReferrerAppMethodDesc(payment.getSalesReferrerDTO().getReferrerAppMethodDesc());
			salesReferrerDTO.setReferrerSourcecode(payment.getSalesReferrerDTO().getReferrerSourcecode());
			salesReferrerDTO.setReferrerStaffNo(payment.getSalesReferrerDTO().getReferrerStaffNo());
			salesReferrerDTO.setReferrerStaffName(payment.getSalesReferrerDTO().getReferrerStaffName());
			sessionOrder.setSalesReferrerDTO(salesReferrerDTO);
		}
		
		logger.debug("sessionOrder@end of Imspayment:" + gson.toJson(sessionOrder));
		
		request.getSession().setAttribute(ImsConstants.IMS_ORDER, sessionOrder);
    	
    	if(payment.getSubmitTag().equals("U")){					
			String currentView = "imspayment.html";									
			return new ModelAndView(new RedirectView(currentView));
		}
    	
		if(payment.getsReasonCd() != null && !("").equals(payment.getsReasonCd())
				&& payment.getSubmitTag().equals("S")){
				//&& (payment.getcReasonCd() == null || ("").equals(payment.getcReasonCd()))){
			imsOrderDetailService.suspendOrder(sessionOrder, payment.getsReasonCd());
			String currentView = "imspayment.html";
			
			request.getSession().setAttribute("imsSubmitTag", payment.getSubmitTag());
			request.getSession().setAttribute("imsOrderId", sessionOrder.getOrderId());
			request.getSession().setAttribute("imsLoginIdUi", null);
			request.getSession().setAttribute(ImsConstants.IMS_APPOINTMENT_SERIAL, null);
			request.getSession().setAttribute("imsMobileOfferUiList", null);
			
			return new ModelAndView(new RedirectView(currentView));
		}
		
		if(payment.getSubmitTag().equals("R")){
				//&& (payment.getcReasonCd() == null || ("").equals(payment.getcReasonCd()))){
			imsOrderDetailService.suspendOrder(sessionOrder, ImsConstants.IMS_ORDER_CASH_REQ);
			String currentView = "imspayment.html";
			
			request.getSession().setAttribute("imsSubmitTag", payment.getSubmitTag());
			request.getSession().setAttribute("imsOrderId", sessionOrder.getOrderId());
			request.getSession().setAttribute("imsLoginIdUi", null);
			request.getSession().setAttribute(ImsConstants.IMS_APPOINTMENT_SERIAL, null);
			request.getSession().setAttribute("imsMobileOfferUiList", null);
			
			return new ModelAndView(new RedirectView(currentView));
		}
		
		if(payment.getSubmitTag().equals("W")){
			//&& (payment.getcReasonCd() == null || ("").equals(payment.getcReasonCd()))){
			imsOrderDetailService.suspendOrder(sessionOrder, ImsConstants.IMS_ORDER_WAIVE_PREPAY);
			String currentView = "imspayment.html";
			
			request.getSession().setAttribute("imsSubmitTag", payment.getSubmitTag());
			request.getSession().setAttribute("imsOrderId", sessionOrder.getOrderId());
			request.getSession().setAttribute("imsLoginIdUi", null);
			request.getSession().setAttribute(ImsConstants.IMS_APPOINTMENT_SERIAL, null);
			request.getSession().setAttribute("imsMobileOfferUiList", null);
			
			return new ModelAndView(new RedirectView(currentView));
		}		
		
		if(payment.getSubmitTag().equals("WO")){
			//&& (payment.getcReasonCd() == null || ("").equals(payment.getcReasonCd()))){
			imsOrderDetailService.suspendOrder(sessionOrder, ImsConstants.IMS_ORDER_WAIVE_OTC);
			String currentView = "imspayment.html";
			
			request.getSession().setAttribute("imsSubmitTag", payment.getSubmitTag());
			request.getSession().setAttribute("imsOrderId", sessionOrder.getOrderId());
			request.getSession().setAttribute("imsLoginIdUi", null);
			request.getSession().setAttribute(ImsConstants.IMS_APPOINTMENT_SERIAL, null);
			request.getSession().setAttribute("imsMobileOfferUiList", null);
			
			return new ModelAndView(new RedirectView(currentView));
		}
		
		if(payment.getSubmitTag().equals("DO")){			
			if(!"Y".equalsIgnoreCase(sessionOrder.getOtDiscountApprovalResult())){
				if("N".equalsIgnoreCase(sessionOrder.getOtDiscountApprovalResult())){
					sessionOrder.setOtInstallChrg(payment.getOtInstallChrg());
					sessionOrder.setInstallOtCode(payment.getInstallOtCode());//Gary
					sessionOrder.setInstallOTDesc_en(payment.getInstallOTDesc_en());//Gary
					sessionOrder.setInstallOTDesc_zh(payment.getInstallOTDesc_zh());//Gary
					sessionOrder.setInstallmentCode(payment.getInstallmentCode());//Gary
				}else{
					sessionOrder.setOtInstallChrg(payment.getRequestSelectDiscountOTC());
				}
			}
			//&& (payment.getcReasonCd() == null || ("").equals(payment.getcReasonCd()))){
			imsOrderDetailService.suspendOrder(sessionOrder, ImsConstants.IMS_ORDER_DISCOUNTED_OTC);
			String currentView = "imspayment.html";
			
			request.getSession().setAttribute("imsSubmitTag", payment.getSubmitTag());
			request.getSession().setAttribute("imsOrderId", sessionOrder.getOrderId());
			request.getSession().setAttribute("imsLoginIdUi", null);
			request.getSession().setAttribute(ImsConstants.IMS_APPOINTMENT_SERIAL, null);
			request.getSession().setAttribute("imsMobileOfferUiList", null);
			
			return new ModelAndView(new RedirectView(currentView));
		}
		
		if(payment.getcReasonCd() != null && !("").equals(payment.getcReasonCd())
				&& payment.getSubmitTag().equals("C")){
			int releaseResult = -99;
			releaseResult = releaseLoginIDService.releaseLoginID(sessionOrder.getLoginId(), sessionOrder.getCustomer().getIdDocNum(), sessionOrder.getCustomer().getIdDocType());
			logger.info(releaseResult);
			imsOrderDetailService.cancelOrder(sessionOrder, payment.getcReasonCd());
			String currentView = "imspayment.html";
			
			request.getSession().setAttribute("imsSubmitTag", payment.getSubmitTag());
			request.getSession().setAttribute("imsOrderId", sessionOrder.getOrderId());
			request.getSession().setAttribute("imsLoginIdUi", null);
			request.getSession().setAttribute(ImsConstants.IMS_APPOINTMENT_SERIAL, null);
			MobileOffer imsMobileOfferUi = new MobileOffer();
			List<MobileOffer> imsMobileOfferUiList = (List<MobileOffer>) request.getSession().getAttribute("imsMobileOfferUiList");
			
			if (imsMobileOfferUiList != null){
				
				for(int i= 0; i<imsMobileOfferUiList.size(); i++){
				
					if(sessionOrder != null && !("").equals(sessionOrder)){
						try{
							imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(imsMobileOfferUiList.get(i).mrt, imsMobileOfferUiList.get(i).pin, sessionOrder.getCreateBy(), sessionOrder.getOrderId(), "NONONLINE");
						}catch(Exception e){
							logger.error("Exception caught in releaseMobilePIN()", e);
							throw new ImsMobilePinException();
						}
						logger.info("release old number: " + imsMobileOfferUiList.get(i).mrt + " , old pin: " + imsMobileOfferUiList.get(i).pin);
						
					}else{
						try{
							imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(imsMobileOfferUiList.get(i).mrt, imsMobileOfferUiList.get(i).pin);
						}catch(Exception e){
							logger.error("Exception caught in releaseMobilePIN()", e);
							throw new ImsMobilePinException();
						}
						logger.info("release old number: " + imsMobileOfferUiList.get(i).mrt + " , old pin: " + imsMobileOfferUiList.get(i).pin);
					}
				
					logger.info(gson.toJson(imsMobileOfferUi));
				}
			}
			request.getSession().setAttribute("imsMobileOfferUiList", null);
			
			return new ModelAndView(new RedirectView(currentView));
		}

		request.getSession().setAttribute("imsSubmitTag", payment.getSubmitTag());
		logger.info(payment.getcReasonCd());
		logger.info("onSubmit is called");
		
		if ("Y".equals(sessionOrder.isSignoffed())) return new ModelAndView(new RedirectView("imsamendprogoffer.html"));
		else return new ModelAndView(new RedirectView(getSuccessView()));

	}

	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		logger.info("ReferenceData called"); 
		
		Map<String, Object> referenceData = new HashMap<String, Object>();

		//shopCode
		
		List<String> shopCdList = new ArrayList<String>();
		shopCdList = loginService.getShopList();
		
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		
		if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){

			BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
			shopCdList.clear();
			shopCdList.add(user.getChannelCd());
		}
		
		referenceData.put("shopCdList", shopCdList);
		
		Map<String,String> AppMethodList = new HashMap<String,String>();
		Map<String,String> ReferrerAppMethodList = new HashMap<String,String>();
		//AppMethodList.add("CSS");
		//AppMethodList.add("CFO");
		//AppMethodList.add("HPM");
		//AppMethodList.put("CSS", "30");
		//AppMethodList.put("HPM", "72");
		//AppMethodList.put("CFO", "8");
		String channel = "CC";
		
		if("Y".equalsIgnoreCase(sessionOrder.getMobileOfferInd())){
			channel = channel + "_M";
		}
		
		
		List<Map<String,String>> map = getSourceCodeService.getSourceCode(channel);
		for (Map<String, String> entry:map )
		{
			if (!AppMethodList.containsKey(entry.get("appMethod")))
			{
				AppMethodList.put(entry.get("appMethod"), entry.get("appMethodCode"));
				
				entry.put("referrerAppMethod", entry.remove("appMethod"));
				entry.put("referrerAppMethodCode", entry.remove("appMethodCode"));
				ReferrerAppMethodList.put(entry.get("referrerAppMethod"), entry.get("referrerAppMethodCode"));
				
				
			}
		}
		
		referenceData.put("AppMethodList", AppMethodList.entrySet().toArray(new Entry[0]));
		referenceData.put("ReferrerAppMethodList",ReferrerAppMethodList.entrySet().toArray(new Entry[0]));
		
		
		return referenceData;
	}

	/*
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println("ImsPaymentController .handleRequest");
    	BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
    	if (user==null){
    		user = new BomSalesUserDTO();
    	}
    	logger.info("userId:" + user.getUsername());
    	
        return new ModelAndView("imspayment", "userId", user.getUsername());
    }
    */
}
