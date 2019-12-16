package com.bomwebportal.lts.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.openuri.www.CustomerBasicInfoDTO;
import org.openuri.www.ServiceLineDTO;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerInformationDTO;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.ApnSerialFileCaptureDTO;
import com.bomwebportal.lts.dto.LtsTeamFunctionDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.disconnect.TerminateOrderCaptureDTO;
import com.bomwebportal.lts.dto.form.LtsOrderAmendmentFormDTO;
import com.bomwebportal.lts.dto.form.qc.LtsDsQcProcessDetailFormDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.util.Utility;
import com.google.common.collect.Lists;

public class LtsSessionHelper {

	public static void clearOrderCapture(HttpServletRequest request) {
		request.getSession().removeAttribute(LtsConstant.SESSION_LTS_ORDER_CAPTURE);
	}
	
	public static void clearTerminateOrderCapture(HttpServletRequest request) {
		request.getSession().removeAttribute(LtsConstant.SESSION_LTS_TERMINATE_ORDER_CAPTURE);
	}
	
	public static OrderCaptureDTO getOrderCapture(HttpServletRequest request) {
		return (OrderCaptureDTO)request.getSession().getAttribute(LtsConstant.SESSION_LTS_ORDER_CAPTURE);
	}
	
	public static TerminateOrderCaptureDTO getTerminateOrderCapture(HttpServletRequest request) {
		return (TerminateOrderCaptureDTO)request.getSession().getAttribute(LtsConstant.SESSION_LTS_TERMINATE_ORDER_CAPTURE);
	}
	
	public static void setOrderCapture(HttpServletRequest request, OrderCaptureDTO orderCapture) {
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		orderCapture.setChannelCs(bomSalesUser.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_CS));
		orderCapture.setChannelPremier(bomSalesUser.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_PREMIER));
		orderCapture.setChannelRetail(bomSalesUser.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_RETAIL));
		orderCapture.setBasketChannelId(LtsSbOrderHelper.getBasketChannelId(bomSalesUser, orderCapture));

		request.getSession().setAttribute(LtsConstant.SESSION_LTS_ORDER_CAPTURE, orderCapture);
		setSessionUid(request);
	}
	
	public static void setTerminateOrderCapture(HttpServletRequest request, TerminateOrderCaptureDTO orderCapture) {
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		orderCapture.setChannelCs(bomSalesUser.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_CS));
		orderCapture.setChannelPremier(bomSalesUser.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_PREMIER));
		orderCapture.setChannelRetail(bomSalesUser.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_RETAIL));
		orderCapture.setOrderType(LtsConstant.ORDER_TYPE_SB_DISCONNECT);
		request.getSession().setAttribute(LtsConstant.SESSION_LTS_TERMINATE_ORDER_CAPTURE, orderCapture);
		setSessionUid(request);
	}
	
	public static void setLtsTeamFunction(HttpServletRequest request, LtsTeamFunctionDTO ltsTeamFunction) {
		request.getSession().setAttribute(LtsConstant.SESSION_LTS_TEAM_FUNCTION, ltsTeamFunction);
	}

	public static LtsTeamFunctionDTO getLtsTeamFunction(HttpServletRequest request) {
		return (LtsTeamFunctionDTO) request.getSession().getAttribute(LtsConstant.SESSION_LTS_TEAM_FUNCTION);
	}
	
	public static BomSalesUserDTO getBomSalesUser(HttpServletRequest request) {
		return (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
	}
	
	public static List<CustomerBasicInfoDTO> getCustomerSearchResultList(HttpServletRequest request) {
		return (List<CustomerBasicInfoDTO>) request.getSession().getAttribute(LtsConstant.SESSION_CUST_SERACH_RESULT_LIST);
	}
	
	public static CustomerInformationDTO getCustomerInformationForm(HttpServletRequest request) {
		return (CustomerInformationDTO) request.getSession().getAttribute(LtsConstant.SESSION_CUST_INFORMATION_FORM);
	}
	
	public static List<ServiceLineDTO> getCustomerServiceInUse(HttpServletRequest request) {
		return (List<ServiceLineDTO>) request.getSession().getAttribute(LtsConstant.SESSION_CUST_SRV_IN_USE_LIST);
	}
	
	public static void clearAllSessionAttb(HttpServletRequest request) {
		request.getSession().removeAttribute(LtsConstant.SESSION_LTS_ORDER_CAPTURE);
		request.getSession().removeAttribute(LtsConstant.SESSION_LTS_ACQ_ORDER_CAPTURE);
		request.getSession().removeAttribute(LtsConstant.SESSION_LTS_TERMINATE_ORDER_CAPTURE);
		request.getSession().removeAttribute(LtsConstant.SESSION_BASKET_SELECTION_INFO);
		request.getSession().removeAttribute(LtsConstant.SESSION_LTS_ORDER_INFO_MSG);
		request.getSession().removeAttribute(LtsConstant.SESSION_LTS_SRV_PROFILE_MSG);
		request.getSession().removeAttribute(LtsConstant.SESSION_LTS_DUMMY_SB_ORDER);
		request.getSession().removeAttribute(LtsConstant.SESSION_LTS_ORDER_AMEND);
	}
	
	public static void setSessionUid(HttpServletRequest request) {
		request.getSession().setAttribute(LtsConstant.SESSION_SBUID, Utility.getUid());
	}
	
	public static String getSessionUid(HttpServletRequest request) {
		return (String)request.getSession().getAttribute(LtsConstant.SESSION_SBUID);
	}

	public static boolean isChannelCs(HttpServletRequest request){
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		return Lists.newArrayList(LtsConstant.CHANNEL_ID_ALL_CC).contains(Integer.toString(bomSalesUser.getChannelId()));
	}	
	
	public static void clearAllTerminateOrderSessionAttb(HttpServletRequest request) {
		request.getSession().removeAttribute(LtsConstant.SESSION_LTS_TERMINATE_ORDER_CAPTURE);
		request.getSession().removeAttribute(LtsConstant.SESSION_LTS_SRV_PROFILE_MSG);
	}
	
	public static AcqOrderCaptureDTO getAcqOrderCapture(HttpServletRequest request) {
		return (AcqOrderCaptureDTO)request.getSession().getAttribute(LtsConstant.SESSION_LTS_ACQ_ORDER_CAPTURE);
	}
	
	public static ApnSerialFileCaptureDTO getApnSerialFileCapture(HttpServletRequest request) {
		return (ApnSerialFileCaptureDTO)request.getSession().getAttribute(LtsConstant.SESSION_LTS_APN_SERIAL_FILE_CAPTURE);
	}
	
	public static void setApnSerialFileCapture(HttpServletRequest request) {
		request.getSession().setAttribute(LtsConstant.SESSION_LTS_APN_SERIAL_FILE_CAPTURE, new ApnSerialFileCaptureDTO());		
	}
	
	public static boolean isChannelDirectSales(HttpServletRequest request){
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		return isChannelDirectSales(bomSalesUser.getChannelId());
	}
	
	public static boolean isChannelDirectSales(int channelId){
		return Lists.newArrayList(LtsConstant.CHANNEL_ID_ALL_DIRECT_SALES).contains(Integer.toString(channelId));
	}
	
	public static boolean isDirectSalesSupport(HttpServletRequest request){
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		return isDirectSalesSupport(bomSalesUser.getChannelId(), bomSalesUser.getChannelCd());
	}

	public static boolean isDirectSalesSupport(int channelId, String channelCode){
		if(LtsConstant.CHANNEL_ID_DIRECT_SALES.equals(Integer.toString(channelId))
				&& LtsConstant.CHANNEL_CD_DIRECT_SALES_ORDER_SUPPORT.equals(channelCode)){
			return true;
		}
		return false;
	}
	
	public static void setAcqOrderCapture(HttpServletRequest request, AcqOrderCaptureDTO orderCapture) {
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		request.getSession().setAttribute(LtsConstant.SESSION_LTS_ACQ_ORDER_CAPTURE, orderCapture);
		orderCapture.setChannelCs(bomSalesUser.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_CS));
		orderCapture.setChannelPremier(bomSalesUser.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_PREMIER));
		orderCapture.setChannelRetail(bomSalesUser.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_RETAIL));
		orderCapture.setChannelDirectSales(isChannelDirectSales(bomSalesUser.getChannelId()));
		
		if(orderCapture.getCustomerDetailProfileLtsDTO() != null && StringUtils.isNotBlank(orderCapture.getCustomerDetailProfileLtsDTO().getCustNum())){
			orderCapture.setDefinedCustNum(true);
		}
		
		setSessionUid(request);
	}
	
	public static void clearAcqOrderCapture(HttpServletRequest request) {
		request.getSession().removeAttribute(LtsConstant.SESSION_LTS_ACQ_ORDER_CAPTURE);
	}	
	
	public static void setDummySbOrder(HttpServletRequest request, SbOrderDTO sbOrder){
		request.getSession().setAttribute(LtsConstant.SESSION_LTS_DUMMY_SB_ORDER, sbOrder);
		setSessionUid(request);
	}
	
	public static SbOrderDTO getDummySbOrder(HttpServletRequest request){
		return (SbOrderDTO)request.getSession().getAttribute(LtsConstant.SESSION_LTS_DUMMY_SB_ORDER);
	}
	
	public static void setOrderAmendForm(HttpServletRequest request, LtsOrderAmendmentFormDTO orderAmendDTO) {
		request.getSession().setAttribute(LtsConstant.SESSION_LTS_ORDER_AMEND, orderAmendDTO);
	}
	
	public static LtsOrderAmendmentFormDTO getOrderAmendForm(HttpServletRequest request){
		return (LtsOrderAmendmentFormDTO)request.getSession().getAttribute(LtsConstant.SESSION_LTS_ORDER_AMEND);
	}
	
	public static void setLtsDsQcProcessDetail(HttpServletRequest request, LtsDsQcProcessDetailFormDTO detailDTO){
		request.getSession().setAttribute(LtsConstant.SESSION_LTS_DS_QC_PROCESS_DETAIL, detailDTO);
	}
	
	public static LtsDsQcProcessDetailFormDTO getLtsDsQcProcessDetail(HttpServletRequest request){
		return (LtsDsQcProcessDetailFormDTO)request.getSession().getAttribute(LtsConstant.SESSION_LTS_DS_QC_PROCESS_DETAIL);
	}
	
	public static boolean isBackdoorMode(HttpServletRequest request)  {
		return LtsConstant.ORDER_MODE_BACKDOOR.equals(request.getSession().getAttribute(LtsConstant.SESSION_LTS_ORDER_MODE));
	}
}
