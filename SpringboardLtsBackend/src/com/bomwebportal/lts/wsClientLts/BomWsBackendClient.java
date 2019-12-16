package com.bomwebportal.lts.wsClientLts;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.pccw.appendOrdRmk.AppendOrdRmk;
import com.pccw.appendOrdRmk.AppendOrdRmkResponse;
import com.pccw.appendOrdRmk.OrderRemarkDTO;
import com.pccw.cancelOrder.Cancel;
import com.pccw.cancelOrder.CancelOrder;
import com.pccw.cancelOrder.CancelResponse;
import com.pccw.custProfileGateway.custProfile.InvPreassgnJunctionMsgUpdResponse;
import com.pccw.custProfileGateway.custProfile.UpdateInvPreassgnJunctionMsg;
import com.pccw.custProfileGateway.custProfile.UpdateInvPreassgnJunctionMsgResponse;
import com.pccw.custProfileGateway.serviceProf.DNAssignmentResultDTO;
import com.pccw.custProfileGateway.serviceProf.DnAssignReq;
import com.pccw.custProfileGateway.serviceProf.DnAssignReqResponse;
import com.pccw.custProfileGateway.serviceProf.GetDNAssignmentResult;
import com.pccw.custProfileGateway.serviceProf.GetDNAssignmentResultResponse;
import com.pccw.custProfileGateway.serviceProf.GetDrgSvcDetail;
import com.pccw.custProfileGateway.serviceProf.GetDrgSvcDetailResponse;
import com.pccw.dwfmGateway.orderInformation.GetOrderInformation;
import com.pccw.dwfmGateway.orderInformation.GetOrderInformationResponse;
import com.pccw.dwfmGateway.orderInformation.OrderInformationInput;
import com.pccw.updateSRD.ServiceResponseDTO;
import com.pccw.updateSRD.SrdDTO;
import com.pccw.updateSRD.UpdateSRD;
import com.pccw.updateSRD.UpdateSRDResponse;

public class BomWsBackendClient {
	
	private WebServiceTemplate serviceProfileTemplate;
	private WebServiceTemplate custProfileTemplate;
	private WebServiceTemplate orderInformationTemplate;
	private WebServiceTemplate updateSRDTemplate;
	private WebServiceTemplate cancelOrderTemplate;
	private WebServiceTemplate appendOrdRmkTemplate;

	private final Log logger = LogFactory.getLog(getClass());

	//Modified by Max.R.Meng
	public ServiceResponseDTO updateSRD(SrdDTO pSrdDTO, String withAppintment){
		try{
			UpdateSRD request = new UpdateSRD();
			request.setPWithAppointment(withAppintment);
			if(StringUtils.isEmpty(pSrdDTO.getToApptType())){
				pSrdDTO.setToApptType(LtsBackendConstant.APPOINTMENT_TIMESLOT_TYPE_EVENING);
				pSrdDTO.setToStartTime("0000");
				pSrdDTO.setToEndTime("0000");
			}
			request.setPSrdDTO(pSrdDTO);
			UpdateSRDResponse response = (UpdateSRDResponse) updateSRDTemplate.marshalSendAndReceive(request);
			return response.getUpdateSRDResult();
		}
		catch(Exception ex){
			logger.error("Error in calling WS updateSRD to Bom OCID [" + pSrdDTO.getOcId() + "] :" + ex.getMessage());
			throw new AppRuntimeException(ex);
		}		
	}
	
	public com.pccw.cancelOrder.ServiceResponseDTO cancelSRD(String ocId, String srvNum, String srvType, String userId, String boc, String reasonCd, String cancelRmk){
		try{
			Cancel request = new Cancel();
			request.setPOcId(Long.parseLong(ocId));
			request.setPUserId(userId);
			request.setPBoc(boc);
			request.setPCancelReasonCode(reasonCd);
			request.setPSrvId(srvNum);
			request.setPTypeOfSrv(srvType);
			request.setPCancelRemark(cancelRmk);
			
			CancelResponse response = (CancelResponse) cancelOrderTemplate.marshalSendAndReceive(request);
			return response.getCancelResult();
		}
		catch(Exception ex){
			logger.error("Error in calling WS cancelOrder to Bom OCID [" + ocId + "] :" + ex.getMessage());
			throw new AppRuntimeException(ex);
		}
	}
	//append Remark to BOM ---Max.r.meng
	
    public com.pccw.appendOrdRmk.ServiceResponseDTO appendOrdRmk(OrderRemarkDTO orderRemark){
    	
    	try{
        	AppendOrdRmk request = new AppendOrdRmk();
        	request.setPOrderRemarkDTO(orderRemark);
        	AppendOrdRmkResponse response = (AppendOrdRmkResponse) appendOrdRmkTemplate.marshalSendAndReceive(request);
        	return response.getAppendOrdRmkResult();
    	}
    	catch(Exception ex){
    		logger.error("Error in calling WS appendOrdRmk to Bom OCID [" + orderRemark.getOcId() + "] :" + ex.getMessage());
			throw new AppRuntimeException(ex);
    	}

    }
	
	public List<DNAssignmentResultDTO> getDNAssignment(String srvNum, String acctCd, String boc, String projectCd, String specialSrvGrp, String specialSrvName) {
		try {			
			GetDNAssignmentResult request = new GetDNAssignmentResult();
			request.setSrvNum(srvNum);
			request.setAccountCd(acctCd);
			request.setBoc(boc);
			request.setProjectCd(projectCd);
			request.setSpecialSrvGrp(specialSrvGrp);
			request.setSpecialSrvName(specialSrvName);
			// default values
			request.setSrvType(LtsBackendConstant.SERVICE_TYPE_TEL);
			request.setDatCd(LtsBackendConstant.DAT_CD_DEL);
			//
			GetDNAssignmentResultResponse response = (GetDNAssignmentResultResponse) serviceProfileTemplate.marshalSendAndReceive(request);
			return response.getGetDNAssignmentResultResult().getDNAssignmentResultDTO();			
		} catch (Exception ex) {
			logger.error("Error in calling WS to Bom");
			throw new AppRuntimeException(ex);
		}
	}
	
	public DnAssignReqResponse dnAssignReq(String srvNum, String inventStsCd, String action, String gatewayNum) {
		try {
			DnAssignReq request = new DnAssignReq();
			request.setSrvNum(srvNum);			
			request.setAction(action);
			if (StringUtils.equals(LtsBackendConstant.DEFAULT_WS_ASSIGN_DN_RELEASE, action)) {
				if (StringUtils.isBlank(inventStsCd) || StringUtils.isBlank(gatewayNum)) {
					throw new Exception("DN status and gateway number can not be null");
				}
				request.setInventStsCd(inventStsCd);
				request.setGatewayNum(StringUtils.leftPad(gatewayNum, 12, "0"));
			} else if (StringUtils.equals(LtsBackendConstant.DEFAULT_WS_ASSIGN_DN_PRE_ASSIGN, action)) {
				request.setInventStsCd("");
				request.setGatewayNum("");
			}
			// default values
			request.setSrvType(LtsBackendConstant.SERVICE_TYPE_TEL);
			//
			DnAssignReqResponse response = (DnAssignReqResponse) serviceProfileTemplate.marshalSendAndReceive(request);
			return response;
		} catch (Exception ex) {
			logger.error("Error in calling WS to Bom");
			throw new AppRuntimeException(ex);
		}		
	}
	
	public GetOrderInformationResponse getOrderInformation(String orderId, int contActvSeqNum) {
		try {
			GetOrderInformation request = new GetOrderInformation();
			OrderInformationInput orderInput = new OrderInformationInput();
			
			orderInput.setOrderId(orderId);
			orderInput.setContActrmkSeqNum(contActvSeqNum);
			orderInput.setGetVoiceInd("Y"); //For get NN
			
			// default values
			orderInput.setActivityLimit(1);
			orderInput.setGetActInd("N");
			orderInput.setGetPsefInd("N");
			orderInput.setGetOrdrmkInd("N");
			orderInput.setGetJunrmkInd("N");
			orderInput.setGetActrmkInd("N");
			orderInput.setGetImsInd("N");
			orderInput.setTwoNTieInd("N");
			orderInput.setPsefLimit(1);
			orderInput.setOrdrmkLimit(1);
			orderInput.setJunrmkLimit(1);
			orderInput.setActrmkLimit(1);
			orderInput.setVoiceLimit(1);
			orderInput.setImsLimit(1);
			orderInput.setContPsefSeqNum(1);
			orderInput.setContPsefInd("S");
			

			
			request.setPOrderInfo(orderInput);
			//
			GetOrderInformationResponse response = (GetOrderInformationResponse) orderInformationTemplate.marshalSendAndReceive(request);
			return response;
		} catch (Exception ex) {
			logger.error("Error in calling WS to Bom");
			throw new AppRuntimeException(ex);
		}		
	}
	
	public InvPreassgnJunctionMsgUpdResponse updateInvPreassgnJunctionMsg(
			String srvNum, String sbOrderId, String lastUpdBy) {		
		try {
			UpdateInvPreassgnJunctionMsg request = new UpdateInvPreassgnJunctionMsg();
			request.setSrvNum(srvNum);
			request.setSbOrderId(sbOrderId);
			request.setLastUpdBy(lastUpdBy);
			
			UpdateInvPreassgnJunctionMsgResponse response = 
				(UpdateInvPreassgnJunctionMsgResponse) custProfileTemplate.marshalSendAndReceive(request);
			
			if (!"0".equals(response.getUpdateInvPreassgnJunctionMsgResult().getErrorSeverity())) {
				  throw new Exception();
			}
			
			return response.getUpdateInvPreassgnJunctionMsgResult();
			
		} catch (Exception ex) {
			logger.error("Error in calling WS to Bom");
			throw new AppRuntimeException(ex);
		}
	}
	
	public GetDrgSvcDetailResponse getDrgSvcDetail(String srvNum, String srvType) {
		try {
			GetDrgSvcDetail request = new GetDrgSvcDetail();
			request.setSrvNum(srvNum);
			// default values
			request.setSrvType(srvType);
			//
			GetDrgSvcDetailResponse response = (GetDrgSvcDetailResponse) serviceProfileTemplate.marshalSendAndReceive(request);
			return response;			
		} catch (Exception ex) {
			logger.error("Error in calling WS to Bom");
			throw new AppRuntimeException(ex);
		}
	}

	/**
	 * @return the serviceProfileTemplate
	 */
	public WebServiceTemplate getServiceProfileTemplate() {
		return serviceProfileTemplate;
	}

	/**
	 * @param serviceProfileTemplate the serviceProfileTemplate to set
	 */
	public void setServiceProfileTemplate(WebServiceTemplate serviceProfileTemplate) {
		this.serviceProfileTemplate = serviceProfileTemplate;
	}

	/**
	 * @return the custProfileTemplate
	 */
	public WebServiceTemplate getCustProfileTemplate() {
		return custProfileTemplate;
	}

	/**
	 * @param custProfileTemplate the custProfileTemplate to set
	 */
	public void setCustProfileTemplate(WebServiceTemplate custProfileTemplate) {
		this.custProfileTemplate = custProfileTemplate;
	}

	public WebServiceTemplate getOrderInformationTemplate() {
		return orderInformationTemplate;
	}

	public void setOrderInformationTemplate(
			WebServiceTemplate orderInformationTemplate) {
		this.orderInformationTemplate = orderInformationTemplate;
	}

	public WebServiceTemplate getUpdateSRDTemplate() {
		return updateSRDTemplate;
	}

	public void setUpdateSRDTemplate(WebServiceTemplate updateSRDTemplate) {
		this.updateSRDTemplate = updateSRDTemplate;
	}

	public WebServiceTemplate getCancelOrderTemplate() {
		return cancelOrderTemplate;
	}

	public void setCancelOrderTemplate(WebServiceTemplate cancelOrderTemplate) {
		this.cancelOrderTemplate = cancelOrderTemplate;
	}

	public WebServiceTemplate getAppendOrdRmkTemplate() {
		return appendOrdRmkTemplate;
	}

	public void setAppendOrdRmkTemplate(WebServiceTemplate appendOrdRmkTemplate) {
		this.appendOrdRmkTemplate = appendOrdRmkTemplate;
	}
	
	

}
