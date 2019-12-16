package com.bomwebportal.ims.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.AddrInventoryDTO;
import com.bomwebportal.ims.dto.BandwidthDTO;
import com.bomwebportal.ims.dto.HousTypeOTChrgDTO;
import com.bomwebportal.ims.dto.ImsServiceSrdDTO;
import com.bomwebportal.ims.dto.SbRemarksDTO;
import com.bomwebportal.ims.dto.ui.AddressInfoUI;
import com.bomwebportal.ims.dto.ui.InstallAddrUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.CCSalesInfoService;
import com.bomwebportal.ims.service.ImsAddressInfoService;
import com.bomwebportal.ims.service.ImsAppointmentService;
import com.bomwebportal.service.ImsCommonService;
import com.bomwebportal.util.Utility;
import com.google.gson.Gson;

public class ImsAddressInfoLookupBySBController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());

	
    private ImsAddressInfoService service;
    private CCSalesInfoService ccsiService;
    private ImsAppointmentService imsAppointmentService;
    private ImsCommonService imsCommonService;
    private Gson gson = new Gson();
    
    
	public void setService(ImsAddressInfoService service) {
		this.service = service;
	}
	public ImsAddressInfoService getService() {
		return service;
	}
	public void setCcsiService(CCSalesInfoService ccsiService) {
		this.ccsiService = ccsiService;
	}
	public CCSalesInfoService getCcsiService() {
		return ccsiService;
	}
	public ImsAppointmentService getImsAppointmentService() {
		return imsAppointmentService;
	}
	public void setImsAppointmentService(ImsAppointmentService imsAppointmentService) {
		this.imsAppointmentService = imsAppointmentService;
	}
    
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		long startTime = System.currentTimeMillis();
		
		OrderImsUI order = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		String sbNum = request.getParameter("SB");
		String isBlacklistAddr = request.getParameter("BlAddr");
		String isBlacklistCust = request.getParameter("BlCust");
		
		String flat = request.getParameter("flat");
		String floor = request.getParameter("floor");
		
		logger.debug("SB=" + sbNum + ":BLADDR=" + isBlacklistAddr + ":BLCUST=" + isBlacklistCust + ":flat=" + flat + ":floor=" + floor);
		String i_has_bb_srv = "Y", i_has_nowtv_srv = "N", mismatch ="N", fsPrepay = "N", fsInd = "N";
		String isDS = (Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)?"Y":"N";
		
		ImsServiceSrdDTO addressInfo = new ImsServiceSrdDTO();
		ImsServiceSrdDTO addressInfo2 = new ImsServiceSrdDTO();
		
		List<SbRemarksDTO> sbRemarks = new ArrayList<SbRemarksDTO>();
		
		List<String> ponSBList = new ArrayList<String>();
		String ponSBStr = "";
		
		//nowTV sales no session fix
		String isPT, isCC;
		if (order!=null && order.getIsPT()!=null && order.getIsCC()!=null &&
				!"".equals(order.getIsPT()) && !"".equals(order.getIsCC())) {
			isPT = order.getIsPT();
			isCC = order.getIsCC();
		} else {
			if (user == null) {
				isPT = "N";
				isCC = "N";
				isDS = "Y";
			} else {
				isPT = user.getChannelId()==3?"Y":"N";
				isCC = user.getChannelId()==2||user.getChannelId()==3||user.getChannelId()==99?"Y":"N";
				isDS = user.getChannelId()==12||user.getChannelId()==13?"Y":"N";
			}
		}
		
		String orderType = request.getParameter("type");
		if (!("NTV-A".equals(orderType) || "NTVAO".equals(orderType) || "NTVUS".equals(orderType)|| "NTVRE".equals(orderType))) {
			orderType = "";
		} else {
			i_has_bb_srv = "N";
			i_has_nowtv_srv = "Y";
		}
		
		addressInfo = imsAppointmentService.getServiceSrdComparedWithBMO(isBlacklistAddr, isBlacklistCust, "N", null, sbNum, flat, floor, isCC, isPT, isDS, 
				i_has_bb_srv, i_has_nowtv_srv, mismatch, fsPrepay, fsInd, null, null, orderType, null);
		List<String> bomMsgList = service.getBrmVasMsg(sbNum);
		
		sbRemarks = service.getSbRemarks(sbNum);
		ponSBList = service.getPonSBList(sbNum);
		
		String comma = "";
		if(ponSBList!=null){
			for(String s:ponSBList){
				ponSBStr += comma + s;
				comma = ",";
			}
		}
		
		String hosTypeDesc = "";
		String phInd = "N";
		String hosType = "";
		String serbdyno = addressInfo.getServiceBoundary();
		System.out.println(serbdyno);
		for(int i=0; i < addressInfo.getHousingTypeList().size(); i++){
			if (addressInfo.getHousingTypeList().get(i).getHousingType().substring(0,2).equalsIgnoreCase("PH")) {
				phInd = "Y";
			}
			if (i==0) {
				
				hosTypeDesc = addressInfo.getHousingTypeDescList().get(i).getHousingTypeDesc();
			} else {
				
				hosTypeDesc = hosTypeDesc + ", " + addressInfo.getHousingTypeDescList().get(i).getHousingTypeDesc();
			}
			hosType = addressInfo.getHousingTypeList().get(i).getHousingType();
		}
		
		

		
		
		String tempHosType = hosType.trim();
		//By Kinman
		AddressInfoUI InstallAddr = new AddressInfoUI();
		System.out.println(tempHosType);
		InstallAddr = service.getHousTypeOTChrgList(tempHosType, serbdyno, floor, flat);
		List<HousTypeOTChrgDTO> housTypeOTChrgList1 = new ArrayList<HousTypeOTChrgDTO>();
		housTypeOTChrgList1 = InstallAddr.getHousTypeOTChrgList();
		
		String ponInstFee ="";
		String ponInstFeeComp ="";//gary	
		String ponOTChrgType="";		
		String vdslInstFee =""; 
		String vdslInstFeeComp ="";//gary
		String vdslOTChrgType =""; 
		String vectorInstFee =""; 
		String vectorInstFeeComp ="";
		String vectorOTChrgType =""; 
		String adslInstFee ="";
		String adslInstFeeComp ="";//gary
		String adslOTChrgType ="";
		String bomDesc ="";
		
		Boolean hasVDSL = false;

		for (HousTypeOTChrgDTO i:housTypeOTChrgList1 )
		{
			if (i.getOTChrgProdType().equalsIgnoreCase("PON"))
			{
				ponInstFee = i.getOTChrg();
				ponOTChrgType = i.getOTChrgType();
				ponInstFeeComp = i.getInstallOTDesc_en();
			}
			else if (i.getOTChrgProdType().equalsIgnoreCase("VDSL"))
			{
				vdslInstFee = i.getOTChrg();
				vdslOTChrgType = i.getOTChrgType();
				vdslInstFeeComp = i.getInstallOTDesc_en();
			}
			else if (i.getOTChrgProdType().equalsIgnoreCase("ADSL"))
			{
				adslInstFee = i.getOTChrg();
				adslOTChrgType = i.getOTChrgType();
				adslInstFeeComp = i.getInstallOTDesc_en();
			}
			else if (i.getOTChrgProdType().equalsIgnoreCase("Vectoring"))
			{
				vectorInstFee = i.getOTChrg();
				vectorOTChrgType = i.getOTChrgType();
				vectorInstFeeComp = i.getInstallOTDesc_en();
			}
		}
		
		

		String ponSrd = "";
		String adslSrd = "";
		String vdslSrd = "";
		String vectorSrd = "";
		String ponErrorMsg = "";
		String adslErrorMsg = "";
		String vdslErrorMsg = "";
		String vectorErrorMsg = "";
//		String ponErrorCd = "";
//		String adslErrorCd = "";
//		String vdslErrorCd = "";
		
		for (int j=0; j < addressInfo.getServiceDetailList().size(); j++) {
			if (addressInfo.getServiceDetailList().get(j).getTechnology().equals("PON") )  
			{
				
				
				if (addressInfo.getServiceDetailList().get(j).getTechnologySupported().equals("Y") &&
					addressInfo.getServiceDetailList().get(j).getIsDeadCase().equals("N")) 
				{
					ponSrd = Utility.date2String(addressInfo.getServiceDetailList().get(j).getEstEarliestSrd(),"yyyy/MM/dd");
					ponSrd = ponSrd + " (Ref: " + addressInfo.getServiceDetailList().get(j).getRtnDesc() + ")";
				} else {
					logger.debug("PON not supported");
					ponSrd = "N/A";
				}
				
				if (addressInfo.getServiceDetailList().get(j).getTechnologySupported().equals("Y") &&
					(addressInfo.getServiceDetailList().get(j).getIsDeadCase().equals("Y") ||
					 addressInfo.getServiceDetailList().get(j).getIsResrcShort().equals("Y")		) ) 
				{
						ponErrorMsg = "<p>" + addressInfo.getServiceDetailList().get(j).getRtnCd() + ":" + addressInfo.getServiceDetailList().get(j).getRtnDesc() + ".</p>";
//						ponErrorCd = addressInfo.getServiceDetailList().get(j).getRtnCd();
				}
				
				if ( !("NTV-A".equals(orderType) || "NTVUS".equals(orderType)|| "NTVAO".equals(orderType) || "NTVRE".equals(orderType)) && ponInstFee.equalsIgnoreCase("TBC") )
				{
					addressInfo.getServiceDetailList().get(j).setTechnologySupported("N");
				}
			} 
			if (addressInfo.getServiceDetailList().get(j).getTechnology().equals("ADSL") )  
			{
						
				if (addressInfo.getServiceDetailList().get(j).getTechnologySupported().equals("Y") &&
					addressInfo.getServiceDetailList().get(j).getIsDeadCase().equals("N")) 
				{
						adslSrd = Utility.date2String(addressInfo.getServiceDetailList().get(j).getEstEarliestSrd(),"yyyy/MM/dd");
						adslSrd = adslSrd + " (Ref: " + addressInfo.getServiceDetailList().get(j).getRtnDesc() + ")";
				} else {
						logger.debug("ADSL not supported");
						adslSrd = "N/A";
				}
				
				if (addressInfo.getServiceDetailList().get(j).getTechnologySupported().equals("Y") &&
					(addressInfo.getServiceDetailList().get(j).getIsDeadCase().equals("Y") ||
					 addressInfo.getServiceDetailList().get(j).getIsResrcShort().equals("Y")		) ) 
				{
						adslErrorMsg = "<p>" + addressInfo.getServiceDetailList().get(j).getRtnCd() + ":" + addressInfo.getServiceDetailList().get(j).getRtnDesc() + ".</p>";
//						adslErrorCd = addressInfo.getServiceDetailList().get(j).getRtnCd();
				}
				
				if ( !("NTV-A".equals(orderType)|| "NTVRE".equals(orderType)  || "NTVAO".equals(orderType) || "NTVUS".equals(orderType)) && adslInstFee.equalsIgnoreCase("TBC") )
				{
					addressInfo.getServiceDetailList().get(j).setTechnologySupported("N");
				}
			}
			if (addressInfo.getServiceDetailList().get(j).getTechnology().equals("VDSL") )  
			{
						
				if (addressInfo.getServiceDetailList().get(j).getTechnologySupported().equals("Y") &&
					addressInfo.getServiceDetailList().get(j).getIsDeadCase().equals("N")) 
				{
						vdslSrd = Utility.date2String(addressInfo.getServiceDetailList().get(j).getEstEarliestSrd(),"yyyy/MM/dd");
						vdslSrd = vdslSrd + " (Ref: " + addressInfo.getServiceDetailList().get(j).getRtnDesc() + ")";
						hasVDSL = true;
				} else {
						logger.debug("VDSL not supported");
						vdslSrd = "N/A";
				}
				
				if (addressInfo.getServiceDetailList().get(j).getTechnologySupported().equals("Y") &&
					(addressInfo.getServiceDetailList().get(j).getIsDeadCase().equals("Y") ||
					 addressInfo.getServiceDetailList().get(j).getIsResrcShort().equals("Y")		) ) 
				{
						vdslErrorMsg = "<p>" + addressInfo.getServiceDetailList().get(j).getRtnCd() + ":" + addressInfo.getServiceDetailList().get(j).getRtnDesc() + ".</p>";
//						vdslErrorCd = addressInfo.getServiceDetailList().get(j).getRtnCd();
				}
				
				if ( !("NTV-A".equals(orderType) || "NTVRE".equals(orderType) || "NTVAO".equals(orderType) || "NTVUS".equals(orderType)) && vdslInstFee.equalsIgnoreCase("TBC") )
				{
					addressInfo.getServiceDetailList().get(j).setTechnologySupported("N");
				}
			}
			if (addressInfo.getServiceDetailList().get(j).getTechnology().equals("Vectoring") )  
			{
						
				if (addressInfo.getServiceDetailList().get(j).getTechnologySupported().equals("Y") &&
					addressInfo.getServiceDetailList().get(j).getIsDeadCase().equals("N")) 
				{
						vectorSrd = Utility.date2String(addressInfo.getServiceDetailList().get(j).getEstEarliestSrd(),"yyyy/MM/dd");
						vectorSrd = vectorSrd + " (Ref: " + addressInfo.getServiceDetailList().get(j).getRtnDesc() + ")";
				} else {
						logger.debug("Vectoring not supported");
						vectorSrd = "N/A";
				}
				
				if (addressInfo.getServiceDetailList().get(j).getTechnologySupported().equals("Y") &&
					(addressInfo.getServiceDetailList().get(j).getIsDeadCase().equals("Y") ||
					 addressInfo.getServiceDetailList().get(j).getIsResrcShort().equals("Y")		) ) 
				{
						vectorErrorMsg = "<p>" + addressInfo.getServiceDetailList().get(j).getRtnCd() + ":" + addressInfo.getServiceDetailList().get(j).getRtnDesc() + ".</p>";
//						vdslErrorCd = addressInfo.getServiceDetailList().get(j).getRtnCd();
				}
				
				if ( !("NTV-A".equals(orderType) || "NTVRE".equals(orderType)|| "NTVAO".equals(orderType) || "NTVUS".equals(orderType)) && vectorInstFee.equalsIgnoreCase("TBC") )
				{
					addressInfo.getServiceDetailList().get(j).setTechnologySupported("N");
				}
			}
		}
		
		

		String sbInsuffRscWarning1 = "";
		if (ponErrorMsg == "" && adslErrorMsg == "" && vdslErrorMsg == "" && vectorErrorMsg == "") {

		} else {
			sbInsuffRscWarning1 = ponErrorMsg + adslErrorMsg + vdslErrorMsg + vectorErrorMsg;
		}

		String sbOutServiceWarning2="";
		String txtWarning2_1 = "";
		String txtWarning2_2 = "";
		String noPon10G = "";
		
		if(ponSrd!="N/A"&&sbRemarks!=null&&sbRemarks.get(0).getHas_rbr()!=null&&"Y".equalsIgnoreCase(sbRemarks.get(0).getHas_rbr())){
			noPon10G = "No PON 10G";
		}
		
		if (addressInfo.getRfsIsFuture() != null && addressInfo.getRfsIsFuture().equals("Y")) {
			txtWarning2_1 = "<p>The Service Boundary is not in service yet.</p>";
		}
		if (addressInfo.getRfsIsNotAvailable() != null && addressInfo.getRfsIsNotAvailable().equals("Y")) {
			txtWarning2_2 = "<p>This Service Boundary is not available for use, cannot issue order for this address.</p>";
		}
		sbOutServiceWarning2 = txtWarning2_1 + txtWarning2_2;

		// update the below value into session for later use (Start)
		request.getSession().setAttribute("IMS_AddressInfotmp", null);
		AddressInfoUI tmpAddrInfo = new AddressInfoUI();
		
		InstallAddrUI installAddr = new InstallAddrUI();
		tmpAddrInfo.setInstallAddress(installAddr);
		
		//remove bandwidth=null case
		if (addressInfo.getBandwidthList().size() > 0) {
			Iterator<BandwidthDTO> iterator = addressInfo.getBandwidthList().iterator();
			while(iterator.hasNext()){
				BandwidthDTO bw = iterator.next();
				if(bw==null||bw.getBandwidth()==null||bw.getBandwidth().isEmpty()){
	                iterator.remove();
				}
			}
		}
		
		if (addressInfo.getBandwidthList().size() > 0) {
			tmpAddrInfo.getInstallAddress().setBandwidthList(addressInfo.getBandwidthList());
		}
		
		if (addressInfo.getHousingTypeList().size() > 0) {
			tmpAddrInfo.getInstallAddress().setHousingTypeList(addressInfo.getHousingTypeList());
		}
		if (addressInfo.getServiceDetailList().size() > 0) {
			tmpAddrInfo.getInstallAddress().setServiceDetailList(addressInfo.getServiceDetailList());
		}

		AddrInventoryDTO addrInventory = new AddrInventoryDTO();
		addrInventory.setN2BuildingInd(addressInfo.getIs2NBuilding());
		tmpAddrInfo.getInstallAddress().setAddrInventory(addrInventory);

		
		// update the below value into session for later use (End)
		
		
		
		
		if (ponInstFee == "" || ponSrd == "N/A"){
			ponInstFee = "N/A";
		}
		
		if (vdslInstFee == ""|| vdslSrd == "N/A"){
			vdslInstFee = "N/A";
		}
		
		if (vectorInstFee == ""|| vectorSrd == "N/A"){
			vectorInstFee = "N/A";
		}
		
		if (adslInstFee == ""|| adslSrd == "N/A"){
			adslInstFee = "N/A";
		}
		if (bomMsgList != null){
			for (int i=0; i < bomMsgList.size(); i++) {
				bomDesc = bomDesc + "<p>" + bomMsgList.get(i) + "</p>";
			}
		}
		
		
		
		
		
		
//		for(int i=0; i < housTypeOTChrgList1.size(); i++){
//			if (housTypeOTChrgList1.get(i).getOTChrgProdType().equalsIgnoreCase("PON")) 
//			{
//				ponInstFee = housTypeOTChrgList1.get(i).getOTChrg();
//				ponOTChrgType = housTypeOTChrgList1.get(i)
//			}
//			else if (housTypeOTChrgList1.get(i).getOTChrgProdType().equalsIgnoreCase("VDSL")) 
//			{
//				vdslInstFee = housTypeOTChrgList1.get(i).getOTChrg();
//			} 
//			else if (housTypeOTChrgList1.get(i).getOTChrgProdType().equalsIgnoreCase("ADSL")) 
//			{
//				adslInstFee = housTypeOTChrgList1.get(i).getOTChrg();
//			}
//		}
		
		
		
		/*
		
		for(int i=0; i < housTypeOTChrgList1.size(); i++){
			if (housTypeOTChrgList1.get(i).getOTChrgProdType().equalsIgnoreCase("PON")) {
				ponInstFee = housTypeOTChrgList1.get(i).getOTChrg();
			}else {ponInstFee = "N/A";}
		}
		for(int i=0; i < housTypeOTChrgList1.size(); i++){
			if (housTypeOTChrgList1.get(i).getOTChrgProdType().equalsIgnoreCase("VDSL")) {
				vdslInstFee = housTypeOTChrgList1.get(i).getOTChrg();
			} else {vdslInstFee = "N/A";}
		}
		for(int i=0; i < housTypeOTChrgList1.size(); i++){
			if (housTypeOTChrgList1.get(i).getOTChrgProdType().equalsIgnoreCase("ADSL")) {
				adslInstFee = housTypeOTChrgList1.get(i).getOTChrg();
			}else {adslInstFee = "N/A";}
		}
		*/
		
		
		
		
		String sbDeadCaseError1 = "";
		logger.debug("IsAllowed ===" + addressInfo.getIsAllowed());
		
		
		
		
		
		
		if ( addressInfo.getIsAllowed().equals("N") ) 
		{
			if (addressInfo.getRejectCd() != null && addressInfo.getRejectCd().equalsIgnoreCase("R01")) 
			{
				sbDeadCaseError1 = "Installation Address under Project Villa - Please refer to Hotline to handle.";
			} 
			else if (addressInfo.getRejectCd() != null && addressInfo.getRejectCd().equalsIgnoreCase("R02")) 
			{ 
				sbDeadCaseError1 = "Installation Address under Project Yacht - Please refer to Hotline to handle.";
			} 
			else 
			{
				sbDeadCaseError1 = addressInfo.getRejectDesc();
			}
		}
		
		if ( "".equals(sbDeadCaseError1) && ( 
				   !"N/A".equals(ponSrd) && "N/A".equals(ponInstFee)
				|| !"N/A".equals(vdslSrd) && "N/A".equals(vdslInstFee)
				|| !"N/A".equals(adslSrd) && "N/A".equals(adslInstFee)
				|| !"N/A".equals(vectorSrd) && "N/A".equals(vectorInstFee)
//				|| "TBC".equals(ponInstFee)
//				|| "TBC".equals(vdslInstFee)
//				|| "TBC".equals(adslInstFee)
		))
		{
			sbDeadCaseError1 = "Invalid Installation / Activation fee, or No Corresponding Installation / Activation fee";
		}
		
		/*
		List<String> teamCdList = null;
		String concatString = "";
		try {
			teamCdList = ccsiService.getCCManagerTeamCds(user.getUsername());
			for (int i=0; i<teamCdList.size(); i++ )
			{
				concatString += teamCdList.get(i);
				if ( i < teamCdList.size() - 1)
				{
					concatString += "','";
				}
			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		
		
		//logger.info("concatString of teamCdList:" + concatString);
		
		if (user!=null && "Y".equals(isPT) && !addressInfo.getIsAllowed().equals("N") && "".equals(sbDeadCaseError1) )
		{
			addressInfo2 = ccsiService.getPTServiceByStaffId(
					user.getUsername(), 
					hosType, 
					"N/A".equals(ponInstFee)||"TBC".equalsIgnoreCase(ponInstFee)?"0":
					"waived".equalsIgnoreCase(ponInstFee)?"-1":ponInstFee, 
					ponOTChrgType);
			
			logger.info(gson.toJson(addressInfo2));
			if ( addressInfo2 == null )
			{
				addressInfo2 = new ImsServiceSrdDTO();
			}
			if ( !"Y".equals(addressInfo2.getIsAllowed())  )
			{
				sbDeadCaseError1 = addressInfo2.getRejectDesc();
			}
		}
		
		//check if the staff is eligible for Mass project
		String allowMassProj = "N";
		if(!"Y".equalsIgnoreCase(addressInfo.getIsAllowed()) && user != null){
			allowMassProj = ccsiService.getAllowMassprojByStaff(user.getUsername(), hosType);
		}
		
		if("Y".equalsIgnoreCase(allowMassProj)){
			addressInfo.setIsAllowed("Y");
			sbDeadCaseError1 = "";
		}
		//check if the staff is eligible for Mass project (end)
		
		String isPonForVillage  = null; 
		isPonForVillage = imsCommonService.sbValidate("PONVILL", serbdyno)?"Y":"N";
		
		JSONArray jsonArray = new JSONArray();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("hosType", hosTypeDesc.trim());
		jsonObject.put("phInd", phInd.trim());
		jsonObject.put("ponSrd", ponSrd.trim());
		jsonObject.put("adslSrd", adslSrd.trim());
		jsonObject.put("vdslSrd", vdslSrd.trim());
		jsonObject.put("vectorSrd", vectorSrd.trim());
		
		//jsonObject.put("OtChrgType", addressInfo.getOtChrgType().trim());
		
		jsonObject.put("sbDeadCaseError1", sbDeadCaseError1);
		jsonObject.put("sbInsuffRscWarning1", sbInsuffRscWarning1);
		jsonObject.put("sbOutServiceWarning2", sbOutServiceWarning2);
		
		jsonObject.put("noPon10G", noPon10G);
		jsonObject.put("ponSBStr", ponSBStr);
		
		jsonObject.put("isPonForVillage", isPonForVillage);
		if (order != null) {
			order.setPreRegInd(isPonForVillage);
		}
		//gary
		logger.debug("ponInstFee: "+ponInstFee);
		if(!ponInstFee.equalsIgnoreCase("TBC") && !"N/A".equals(ponInstFee) && ponInstFeeComp!=null && !ponInstFeeComp.isEmpty()){
			ponInstFee+=ponInstFeeComp;
		}		
		jsonObject.put("ponInstFee", ponInstFee.trim());
		
		if(!vdslInstFee.equalsIgnoreCase("TBC") && !"N/A".equals(vdslInstFee) && vdslInstFeeComp!=null && !vdslInstFeeComp.isEmpty()){
			vdslInstFee+=vdslInstFeeComp;
		}
		jsonObject.put("vdslInstFee", vdslInstFee.trim());
		
		if(!vectorInstFee.equalsIgnoreCase("TBC") && !"N/A".equals(vectorInstFee) && vectorInstFeeComp!=null && !vectorInstFeeComp.isEmpty()){
			vectorInstFee+=vectorInstFeeComp;
		}
		jsonObject.put("vectorInstFee", vectorInstFee.trim());
		
		if(!adslInstFee.equalsIgnoreCase("TBC") && !"N/A".equals(adslInstFee) && adslInstFeeComp!=null && !adslInstFeeComp.isEmpty()){
			adslInstFee+=adslInstFeeComp;
		}
		jsonObject.put("adslInstFee", adslInstFee.trim());
		jsonObject.put("ponOTChrgType", ponOTChrgType.trim());
		jsonObject.put("vdslOTChrgType", vdslOTChrgType.trim());
		jsonObject.put("vectorOTChrgType", vectorOTChrgType.trim());
		jsonObject.put("adslOTChrgType", adslOTChrgType.trim());
		jsonObject.put("bomWarning", bomDesc.trim());
		
		if (order != null) {
			order.setPonOTChrgType(ponOTChrgType.trim());
			order.setAdslOTChrgType(adslOTChrgType.trim());
			order.setVdslOTChrgType(vdslOTChrgType.trim());
			order.setVectorOTChrgType(vectorOTChrgType.trim());
		}
		
		List<String> sbPopUpMsg = new ArrayList<String>();
		List<String> sbPopUpMsgHTML = new ArrayList<String>();
		if (sbNum != null && !"".equals(sbNum)) {
			sbPopUpMsg = imsAppointmentService.getSbPopUp(sbNum);
		}
		for(String s:sbPopUpMsg){
			s=s.trim();
		}
		for(String s:sbPopUpMsg){
			String i = "";
			i=s.replace("\n", "<br/>");
			sbPopUpMsgHTML.add(i);
		}
		jsonObject.put("sbPopUpJS", sbPopUpMsg);
		jsonObject.put("sbPopUpHTML", sbPopUpMsgHTML);
		
		//ims ds ntv 201508
		jsonObject.put("hasVDSL",hasVDSL?"Y":"N");
		jsonObject.put("hasADSL6",addressInfo.hasBandwidth("6")?"Y":"N");
		jsonObject.put("hasADSL8",addressInfo.hasBandwidth("8")?"Y":"N");
		jsonObject.put("hasADSL18",addressInfo.hasBandwidth("18")?"Y":"N");
		jsonObject.put("ntvCoverage",addressInfo.getNtvCoverage());
		jsonObject.put("submitAllowed",addressInfo.getSubmitAllowed());
		String cpqHosType;
		if(hosType.contains("PT"))
			cpqHosType = "PT";
		else if (hosType.contains("PH")||hosType.contains("HOS"))
			cpqHosType = "PH";
		else 
			cpqHosType = "Mass";
		jsonObject.put("cpqHosType",cpqHosType);
		
		/*
		jsonArray.add(
				"{\"hosType\":\""	+ hosTypeDesc.trim() +
				"\",\"phInd\":\""	+ phInd.trim() + 
				"\",\"ponSrd\":\""	+ ponSrd.trim() + 
				"\",\"adslSrd\":\""	+ adslSrd.trim() + 
				"\",\"vdslSrd\":\""	+ vdslSrd.trim() + 
				"\",\"sbDeadCaseError1\":\""	+ sbDeadCaseError1 + 
				"\",\"sbInsuffRscWarning1\":\""	+ sbInsuffRscWarning1 + 
				"\",\"sbOutServiceWarning2\":\""	+ sbOutServiceWarning2 + 
				"\"}");
		*/
		
		jsonArray.element(jsonObject);
		logger.info("jsonArray : " + jsonArray);
		request.getSession().setAttribute(ImsConstants.IMS_ORDER, order);
		request.getSession().setAttribute(ImsConstants.IMS_NOW_RET_FLOW_SB_INFO, jsonArray);
		request.getSession().setAttribute("IMS_AddressInfotmp", tmpAddrInfo);
		
		long endTime = System.currentTimeMillis();
		long timeElapsed = endTime-startTime;
		logger.debug("imsaddressinfolookupbysb SB="+sbNum+" flat="+flat+" floor="+floor+" timeElapsed="+timeElapsed/1000+"s");
		if (timeElapsed >= 25000) {
			logger.error("imsaddressinfolookupbysb timeElapsed= "+timeElapsed/1000+"s >= 25s");
		}
		
		return new ModelAndView("ajax_addrInfoLookup", "jsonArray",	jsonArray);
	}
	public void setImsCommonService(ImsCommonService imsCommonService) {
		this.imsCommonService = imsCommonService;
	}
	public ImsCommonService getImsCommonService() {
		return imsCommonService;
	}
}
