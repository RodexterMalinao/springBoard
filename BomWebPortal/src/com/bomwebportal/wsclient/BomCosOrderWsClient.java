package com.bomwebportal.wsclient;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.bomwebportal.wsclient.exception.UnexpectedWsClientException;
import com.bomwebportal.wsclient.exception.WsClientException;
import com.pccw.schema.xsd.CancelCosOrderRequest;
import com.pccw.schema.xsd.CancelCosOrderResult;
import com.pccw.schema.xsd.CheckPposPreTransRequest;
import com.pccw.schema.xsd.CheckPposPreTransResult;
import com.pccw.schema.xsd.CreateCsimOrdRequest;
import com.pccw.schema.xsd.CreateCsimOrdResult;
import com.pccw.schema.xsd.CreateSmTenderRequest;
import com.pccw.schema.xsd.CreateSmTenderResult;
import com.pccw.schema.xsd.GetAcctPayMthdRequest;
import com.pccw.schema.xsd.GetAcctPayMthdResult;
import com.pccw.schema.xsd.GetActiveSubRequest;
import com.pccw.schema.xsd.GetActiveSubResult;
import com.pccw.schema.xsd.GetCosOrderImageRequest;
import com.pccw.schema.xsd.GetCosOrderImageResult;
import com.pccw.schema.xsd.GetDepositBySubRequest;
import com.pccw.schema.xsd.GetDepositBySubResult;
import com.pccw.schema.xsd.GetIDDRoamOptRequest;
import com.pccw.schema.xsd.GetIDDRoamOptResult;
import com.pccw.schema.xsd.GetPaymSummRequest;
import com.pccw.schema.xsd.GetPaymSummResult;
import com.pccw.schema.xsd.GetSubInfoRequest;
import com.pccw.schema.xsd.GetSubInfoResult;
import com.pccw.schema.xsd.GetSubSimInfoRequest;
import com.pccw.schema.xsd.GetSubSimInfoResult;
import com.pccw.schema.xsd.LockControlRequest;
import com.pccw.schema.xsd.LockControlResult;
import com.pccw.schema.xsd.UpdAcctPayMthdRequest;
import com.pccw.schema.xsd.UpdAcctPayMthdResult;
import com.pccw.schema.xsd.UpdCustRegAddrRequest;
import com.pccw.schema.xsd.UpdCustRegAddrResult;
import com.pccw.schema.xsd.UpdCustTierByCustNumRequest;
import com.pccw.schema.xsd.UpdCustTierByCustNumResult;
import com.pccw.schema.xsd.UpdateCOSOrderSRDRequest;
import com.pccw.schema.xsd.UpdateCOSOrderSRDResult;
import com.pccw.schema.xsd.ValCsimOptRequest;
import com.pccw.schema.xsd.ValCsimOptResult;
import com.pccw.services.CancelCosOrder;
import com.pccw.services.CancelCosOrderResponse;
import com.pccw.services.CreateBomSmTender;
import com.pccw.services.CreateBomSmTenderResponse;
import com.pccw.services.CreateCsimOrder;
import com.pccw.services.CreateCsimOrderResponse;
import com.pccw.services.GetAcctPayMthd;
import com.pccw.services.GetAcctPayMthdResponse;
import com.pccw.services.GetActiveSubscription;
import com.pccw.services.GetActiveSubscriptionResponse;
import com.pccw.services.GetCosOrderImage;
import com.pccw.services.GetCosOrderImageResponse;
import com.pccw.services.GetDepositBySub;
import com.pccw.services.GetDepositBySubResponse;
import com.pccw.services.GetIDDRoamOpt;
import com.pccw.services.GetIDDRoamOptResponse;
import com.pccw.services.GetPaymentSummary;
import com.pccw.services.GetPaymentSummaryResponse;
import com.pccw.services.GetSubInfo;
import com.pccw.services.GetSubInfoResponse;
import com.pccw.services.GetSubSimInfo;
import com.pccw.services.GetSubSimInfoResponse;
import com.pccw.services.LockControl;
import com.pccw.services.LockControlResponse;
import com.pccw.services.RunPposPreTranslation;
import com.pccw.services.RunPposPreTranslationResponse;
import com.pccw.services.UpdateAcctPayMthd;
import com.pccw.services.UpdateAcctPayMthdResponse;
import com.pccw.services.UpdateCOSOrderSRD;
import com.pccw.services.UpdateCOSOrderSRDResponse;
import com.pccw.services.UpdateCustRegAddr;
import com.pccw.services.UpdateCustRegAddrResponse;
import com.pccw.services.UpdateCustTierByCustNum;
import com.pccw.services.UpdateCustTierByCustNumResponse;
import com.pccw.services.ValidateCsimOpt;
import com.pccw.services.ValidateCsimOptResponse;

import bom.mob.schema.javabean.si.springboard.xsd.CreateSmReqDTO;
import bom.mob.schema.javabean.si.springboard.xsd.GetAcctPayMthdDTO;
import bom.mob.schema.javabean.si.springboard.xsd.GetDepositBySubResultDTO;
import bom.mob.schema.javabean.si.springboard.xsd.LockControlResultDTO;
import bom.mob.schema.javabean.si.springboard.xsd.NewAddrDTO;
import bom.mob.schema.javabean.si.springboard.xsd.NewSimDTO;
import bom.mob.schema.javabean.si.springboard.xsd.OptSetDTO;
import bom.mob.schema.javabean.si.springboard.xsd.OrderDTO;
import bom.mob.schema.javabean.si.springboard.xsd.PaymentDTO;
import bom.mob.schema.javabean.si.springboard.xsd.PposPreTransDTO;
import bom.mob.schema.javabean.si.springboard.xsd.ResultDTO;
import bom.mob.schema.javabean.si.springboard.xsd.SubDepositDTO;
import bom.mob.schema.javabean.si.springboard.xsd.SubInfoDTO;
import bom.mob.schema.javabean.si.springboard.xsd.SubSimInfoDTO;
import bom.mob.schema.javabean.si.springboard.xsd.SubscriptionDTO;
import bom.mob.schema.javabean.si.springboard.xsd.UpdCOSSRDResultDTO;
import bom.mob.schema.javabean.si.xsd.OperInfoDTO;

@Service
public class BomCosOrderWsClient {

	protected final Log logger = LogFactory.getLog(getClass());

	private WebServiceTemplate bomCosWsTemplate;
	

	private final OperInfoDTO operInfoDTO;
	
	BomCosOrderWsClient() {
		operInfoDTO = new OperInfoDTO();
		operInfoDTO.setChannel("EAI");
		operInfoDTO.setOperId("SPB");
	}
	
	public WebServiceTemplate getBomCosWsTemplate() {
		return bomCosWsTemplate;
	}

	public void setBomCosWsTemplate(WebServiceTemplate bomCosWsTemplate) {
		this.bomCosWsTemplate = bomCosWsTemplate;
	}
	
	public SubInfoDTO getSubInfo(String serviceId) throws WsClientException {
		logger.debug("getSubInfo()");
		GetSubInfoRequest req = new GetSubInfoRequest();
		req.setCcServiceId(serviceId);
		req.setOperInfoDTO(operInfoDTO);
		
		GetSubInfo op = new GetSubInfo();
		op.setGetSubInfoRequest(req);
		
		GetSubInfoResponse resp = null;
		
		try {
			resp = (GetSubInfoResponse)bomCosWsTemplate.marshalSendAndReceive(op);
			GetSubInfoResult ret = resp.getReturn();
			
			String errCode = ret.getResultDTO().getErrCd();
			String errMsg = ret.getResultDTO().getErrDesc();
			
			logger.debug("getSubInfo - errCode: " + errCode + ", errMsg: " + errMsg);

			if (!"0".equals(errCode)) {
				WsClientException ex = new WsClientException(errCode, errMsg);
				throw ex;
			}
			
			return ret.getSubInfoDTO();
			
		} catch (WsClientException e) {
			logger.error("getSubInfo() failed", e);
			throw e;
		} catch (Exception e) {
			logger.error("getSubInfo() failed", e);
			throw new UnexpectedWsClientException(e);
		}
	}
	
	
	public SubscriptionDTO getActiveSubscription(String serviceId, String applnDate) throws WsClientException {
		logger.debug("getActiveSubscription()");
		
		GetActiveSubRequest req = new GetActiveSubRequest();
		req.setCcServiceId(serviceId);
		req.setApplnDate(applnDate);
		req.setOperInfoDTO(operInfoDTO);
		
		GetActiveSubscription op = new GetActiveSubscription();
		op.setGetActiveSubRequest(req);
		
		GetActiveSubscriptionResponse resp = null;
		
		try {
			resp = (GetActiveSubscriptionResponse)bomCosWsTemplate.marshalSendAndReceive(op);
		
			GetActiveSubResult ret = resp.getReturn();
			SubscriptionDTO dto = ret.getSubscriptionDTO();
			
			String errCode = ret.getResultDTO().getErrCd();
			String errMsg = ret.getResultDTO().getErrDesc();
			
			logger.debug("getActiveSubscription - errCode: " + errCode + ", errMsg: " + errMsg);

			if (!"0".equals(errCode)) {
				WsClientException ex = new WsClientException(errCode, errMsg);
				throw ex;
			}
			
			return dto;
			
		} catch (WsClientException e) {
			logger.error("getActiveSubscription() failed", e);
			throw e;
		} catch (Exception e) {
			logger.error("getActiveSubscription() failed", e);
			throw new UnexpectedWsClientException(e);
		}
	}
	
	
	public OrderDTO getCosOrderImage(String serviceId, String applnDate, String shopCd, String salesCd) throws WsClientException {
		logger.debug("getCosOrderImage");
		
		GetCosOrderImageRequest req = new GetCosOrderImageRequest();
		req.setShopCd(shopCd);
		req.setApplnDate(applnDate);
		req.setCcServiceId(serviceId);
		req.setSalesCd(salesCd);

		req.setOperInfoDTO(operInfoDTO);
	
		GetCosOrderImage op = new GetCosOrderImage();
		op.setGetCosOrderImageRequest(req);
		GetCosOrderImageResponse resp = null;
		try {
			resp = (GetCosOrderImageResponse)bomCosWsTemplate.marshalSendAndReceive(op);
			GetCosOrderImageResult ret = resp.getReturn();
			String errCode = ret.getResultDTO().getErrCd();
			String errMsg = ret.getResultDTO().getErrDesc();
			
			logger.debug("getCosOrderImage - errCode: " + errCode + ", errMsg: " + errMsg);

			if (!"0".equals(errCode)) {
				WsClientException ex = new WsClientException(errCode, errMsg);
				throw ex;
			}
			
			return ret.getOrderDTO();
			
		} catch (WsClientException e) {
			logger.error("getCosOrderImage() failed", e);
			throw e;
		} catch (Exception e) {
			logger.error("getCosOrderImage() failed", e);
			throw new UnexpectedWsClientException(e);
		}
		
	}

	
	public void lockControl(String lockType, String lockActionInd, String ocid, String serviceId,
			String shopCd, String salesCd) throws WsClientException {
		logger.debug("lockControl()");

		LockControlRequest req = new LockControlRequest();
		req.setLockType(lockType);
		req.setLockActionInd(lockActionInd);
		req.setOcid(ocid);
		req.setCcServiceId(serviceId);
		req.setSalesCd(salesCd);
		req.setShopCd(shopCd);
		req.setOperInfo(operInfoDTO);
		
		LockControl op = new LockControl();
		op.setReq(req);
		LockControlResponse resp = null;
		try {
			resp = (LockControlResponse)bomCosWsTemplate.marshalSendAndReceive(op);
			LockControlResult ret = resp.getReturn();
			LockControlResultDTO dto = ret.getLockControlResultDTO();
			
			String errCode = dto.getResultDTO().getErrCd();
			String errMsg = dto.getResultDTO().getErrDesc();
			
			logger.debug("lockControl - errCode: " + errCode + ", errMsg: " + errMsg);

			if (!"0".equals(errCode)) {
				WsClientException ex = new WsClientException(errCode, errMsg);
				throw ex;
			}
			
						
		} catch (WsClientException e) {
			logger.error("lockControl() failed", e);
			throw e;
		} catch (Exception e) {
			logger.error("lockControl() failed", e);
			throw new UnexpectedWsClientException(e);
		}

	}
	
	public void cancelCosOrder(String ocid, String reasonCd, String salesCd) throws WsClientException {
		logger.debug("canceling COS Order - " + ocid);
		 
		CancelCosOrderRequest req = new CancelCosOrderRequest();
		req.setOcid(ocid);
		req.setCancelReasonCd(reasonCd);
		req.setSalesCd(salesCd);
		
		
		req.setOperInfoDTO(operInfoDTO);
		CancelCosOrder op = new CancelCosOrder();
		op.setCancelMobOrderRequest(req);

		CancelCosOrderResponse resp = null; 
		
		try {
			resp = (CancelCosOrderResponse)bomCosWsTemplate.marshalSendAndReceive(op);
			
			CancelCosOrderResult ret = resp.getReturn();
			
			String errCode = ret.getResultDTO().getErrCd();
			String errMsg = ret.getResultDTO().getErrDesc();
			
			logger.debug("cancelCosOrder - errCode: " + errCode + ", errMsg: " + errMsg);

			if (!"0".equals(errCode)) {
				WsClientException ex = new WsClientException(errCode, errMsg);
				throw ex;
			}
			
						
		} catch (WsClientException e) {
			logger.error("cancelCosOrder() failed", e);
			throw e;
		} catch (Exception e) {
			logger.error("cancelCosOrder() failed", e);
			throw new UnexpectedWsClientException(e);
		}
		

	}
	
	
	
	
	
	public PaymentDTO getAcctPayMthd(String acctNum) throws WsClientException {
		logger.debug("getAcctPayMthd()");
		
		GetAcctPayMthdRequest req = new GetAcctPayMthdRequest();
		req.setAcctNum(acctNum);
		req.setOperInfoDTO(operInfoDTO);
		
		GetAcctPayMthd op = new GetAcctPayMthd();
		op.setReq(req);
		
		GetAcctPayMthdResponse resp = null;
		
		try {
			resp = (GetAcctPayMthdResponse)bomCosWsTemplate.marshalSendAndReceive(op);
			GetAcctPayMthdResult ret = resp.getReturn();
			GetAcctPayMthdDTO dto = ret.getGetAcctPayMthdDTO();
			String errCode = dto.getResultDTO().getErrCd();
			String errMsg = dto.getResultDTO().getErrDesc();
			
			logger.debug("getAcctPayMthd - errCode: " + errCode + ", errMsg: " + errMsg);

			if (!"0".equals(errCode)) {
				WsClientException ex = new WsClientException(errCode, errMsg);
				throw ex;
			}
			
			return dto.getPaymentDTO();
			
		} catch (WsClientException e) {
			logger.error("getAcctPayMthd() failed", e);
			throw e;
		} catch (Exception e) {
			logger.error("getAcctPayMthd() failed", e);
			throw new UnexpectedWsClientException(e);
		}
	}
	
	
	public void updateAcctPayMthd(String acctNum, String serviceId, PaymentDTO paymentDTO, String salesCd) throws WsClientException {
		logger.debug("updateAcctPayMthd()");
		
		UpdAcctPayMthdRequest req = new UpdAcctPayMthdRequest();
		req.setAcctNum(acctNum);
		req.setServiceID(serviceId);
		req.setPaymentDTO(paymentDTO);
		req.setSalesCd(salesCd);
		req.setOperInfoDTO(operInfoDTO);
		
		UpdateAcctPayMthd op = new UpdateAcctPayMthd();
		op.setUpdAcctPayMthdRequest(req);
		
		UpdateAcctPayMthdResponse resp = null;
		
		try {
			resp = (UpdateAcctPayMthdResponse)bomCosWsTemplate.marshalSendAndReceive(op);
			UpdAcctPayMthdResult ret = resp.getReturn();

			String errCode = ret.getResultDTO().getErrCd();
			String errMsg = ret.getResultDTO().getErrDesc();
			
			logger.debug("updateAcctPayMthd - errCode: " + errCode + ", errMsg: " + errMsg);

			if (!"0".equals(errCode)) {
				WsClientException ex = new WsClientException(errCode, errMsg);
				throw ex;
			}
						
		} catch (WsClientException e) {
			logger.error("updateAcctPayMthd() failed", e);
			throw e;
		} catch (Exception e) {
			logger.error("updateAcctPayMthd() failed", e);
			throw new UnexpectedWsClientException(e);
		}
	}
	
	
	
	public List<SubDepositDTO> getDepositBySub(String serviceId) throws WsClientException {
		logger.debug("getDepositBySub()");
		
		GetDepositBySubRequest req = new GetDepositBySubRequest();
		req.setCcServiceId(serviceId);
		req.setOperInfo(operInfoDTO);
		
		GetDepositBySub op = new GetDepositBySub();
		op.setReq(req);
		
		GetDepositBySubResponse resp = null;
		
		try {
			resp = (GetDepositBySubResponse)bomCosWsTemplate.marshalSendAndReceive(op);
			GetDepositBySubResult ret = resp.getReturn();

			GetDepositBySubResultDTO dto = ret.getGetDepositBySubResultDTO();
			
			String errCode = dto.getResultDTO().getErrCd();
			String errMsg = dto.getResultDTO().getErrDesc();
			
			logger.debug("getDepositBySub - errCode: " + errCode + ", errMsg: " + errMsg);

			if (!"0".equals(errCode)) {
				WsClientException ex = new WsClientException(errCode, errMsg);
				throw ex;
			}
			
			return dto.getSubDepositDTO();
			
		} catch (WsClientException e) {
			logger.error("getDepositBySub() failed", e);
			throw e;
		} catch (Exception e) {
			logger.error("getDepositBySub() failed", e);
			throw new UnexpectedWsClientException(e);
		}
	}
	
	
	
	public void updateCOSOrderSRD(String ocid,
			String srvReqDate,
			String onHoldInd,
			String reasonCd,
			String salesCd) throws WsClientException {
		logger.debug("updateCosOrderSRD()");
		
		UpdateCOSOrderSRDRequest req = new UpdateCOSOrderSRDRequest();
		req.setOcid(ocid);
		req.setSrvReqDate(srvReqDate);
		req.setOnHoldInd(onHoldInd);
		req.setOnHoldReasonCd(reasonCd);
		req.setSalesCd(salesCd);

		req.setOperInfo(operInfoDTO);
		
		UpdateCOSOrderSRD op = new UpdateCOSOrderSRD();
		op.setReq(req);
		
		UpdateCOSOrderSRDResponse resp = null;
		
		try {
			resp = (UpdateCOSOrderSRDResponse)bomCosWsTemplate.marshalSendAndReceive(op);
			UpdateCOSOrderSRDResult ret = resp.getReturn();
			
			UpdCOSSRDResultDTO dto = ret.getUpdCOSSRDResultDTO();
			
			String errCode = dto.getResultDTO().getErrCd();
			String errMsg = dto.getResultDTO().getErrDesc();
			
			logger.debug("updateCosOrderSRD - errCode: " + errCode + ", errMsg: " + errMsg);

			if (!"0".equals(errCode)) {
				WsClientException ex = new WsClientException(errCode, errMsg);
				throw ex;
			}
						
		} catch (WsClientException e) {
			logger.error("updateCosOrderSRD() failed", e);
			throw e;
		} catch (Exception e) {
			logger.error("updateCosOrderSRD() failed", e);
			throw new UnexpectedWsClientException(e);
		}
	}
	
	//@Retryable(maxRetries=2, timeout=1000, retryOn={UnexpectedWsClientException.class})
	public List<OptSetDTO> getIDDRoamOpt(String tmplGrp, String featureOfferGrpCd, String offerOfferGrpCd)
			throws WsClientException {
		
		logger.debug("getIDDRoamOpt()");
		GetIDDRoamOptRequest req = new GetIDDRoamOptRequest();
		req.setFeatureOfferGrpCd(featureOfferGrpCd);
		req.setOfferOfferGrpCd(offerOfferGrpCd);
		req.setTmplGrp(tmplGrp);
		req.setOperInfoDTO(operInfoDTO);
		
		GetIDDRoamOpt op = new GetIDDRoamOpt();
		op.setGetIDDRoamOptRequest(req);
		
		GetIDDRoamOptResponse resp = null;
		try {
			resp = (GetIDDRoamOptResponse)bomCosWsTemplate.marshalSendAndReceive(op);
			GetIDDRoamOptResult result = resp.getReturn();
			
			ResultDTO err = result.getResultDTO();			
			String errCode = err.getErrCd();
			String errMsg = err.getErrDesc();
			
			logger.debug("getIDDRoamOpt - errCode: " + errCode + ", errMsg: " + errMsg);

			if (!"0".equals(errCode)) {
				WsClientException ex = new WsClientException(errCode, errMsg);
				throw ex;
			}
			return result.getOptSetDTO();
			
		} catch (WsClientException e) {
			logger.error("getIDDRoamOpt() failed", e);
			throw e;
		} catch (Throwable e) {
			logger.error("getIDDRoamOpt() failed", e);
			throw new UnexpectedWsClientException(e);
		}
	}

	
	public SubSimInfoDTO getSubSimInfo(String ccServiceId) throws WsClientException {
		logger.debug("getSubSimInfo()");
		GetSubSimInfoRequest req = new GetSubSimInfoRequest();
		req.setCcServiceId(ccServiceId);
		req.setOperInfoDTO(operInfoDTO);
		GetSubSimInfo op = new GetSubSimInfo();
		op.setGetSubSimInfoRequest(req);

		
		GetSubSimInfoResponse resp = null;
		try {
			resp = (GetSubSimInfoResponse)bomCosWsTemplate.marshalSendAndReceive(op);
			GetSubSimInfoResult result = resp.getReturn();
			
			ResultDTO err = result.getResultDTO();			
			String errCode = err.getErrCd();
			String errMsg = err.getErrDesc();
			
			logger.debug("getSubSimInfo - errCode: " + errCode + ", errMsg: " + errMsg);

			if (!"0".equals(errCode)) {
				WsClientException ex = new WsClientException(errCode, errMsg);
				throw ex;
			}
			return result.getSubSimInfoDTO();
			
		} catch (WsClientException e) {
			logger.error("getSubSimInfo() failed", e);
			throw e;
		} catch (Throwable e) {
			logger.error("getSubSimInfo() failed", e);
			throw new UnexpectedWsClientException(e);
		}		
	}
	
	
	
	public ValResultDTO validateCsimOpt(String ccServiceId, String applnDate,
			String newSimOfferId, String newSimIccid, String newSimImsi, String newSimItemCd)
			throws WsClientException {
		
		logger.debug("validateCsimOpt()");
		ValCsimOptRequest req = new ValCsimOptRequest();
		
		req.setCcServiceId(ccServiceId);
		req.setApplnDate(applnDate);
		req.setNewSimOfferId(newSimOfferId);
		req.setNewSimIccid(newSimIccid);
		req.setNewSimImsi(newSimImsi);
		req.setNewSimItemCd(newSimItemCd);
		
		req.setOperInfoDTO(operInfoDTO);
		ValidateCsimOpt op = new ValidateCsimOpt();
		op.setValCsimOptRequest(req);

		
		ValidateCsimOptResponse resp = null;
		try {
			resp = (ValidateCsimOptResponse)bomCosWsTemplate.marshalSendAndReceive(op);
			ValCsimOptResult result = resp.getReturn();
			
			ResultDTO err = result.getResultDTO();			
			String errCode = err.getErrCd();
			String errMsg = err.getErrDesc();
			
			logger.debug("validateCsimOpt - errCode: " + errCode + ", errMsg: " + errMsg);

			if (!"0".equals(errCode)) {
				if (StringUtils.isBlank(result.getErrType())) {				
					WsClientException ex = new WsClientException(errCode, errMsg);
					throw ex;
				}
			}
			
			ValResultDTO valResult = new ValResultDTO();
			valResult.setErrType(result.getErrType());
			valResult.setErrMsg(result.getErrMsg());
			
			return valResult;
			
		} catch (WsClientException e) {
			logger.error("validateCsimOpt() failed", e);
			throw e;
		} catch (Throwable e) {
			logger.error("validateCsimOpt() failed", e);
			throw new UnexpectedWsClientException(e);
		}		
	}
	
	
	public String createCsimOrder(String ccServiceId, String sbOrderId, String applnDate /*yyyymmdd*/,
			String salesCd, String shopCd, String srvReqDate /*yyyymmdd*/,
			NewSimDTO newSimDTO) throws WsClientException {
		
		logger.debug("createCsimOrder()");
		CreateCsimOrdRequest req = new CreateCsimOrdRequest();
		
		req.setCcServiceId(ccServiceId);
		req.setSbOrderId(sbOrderId);
		req.setApplnDate(applnDate);
		req.setSalesCd(salesCd);
		req.setShopCd(shopCd);
		req.setSrvReqDate(srvReqDate);
		req.setNewSimDTO(newSimDTO);
		req.setOperInfoDTO(operInfoDTO);
		
		CreateCsimOrder op = new CreateCsimOrder();
		
		op.setCreateCsimOrdRequest(req);

		
		CreateCsimOrderResponse resp = null;
		
		try {
			resp = (CreateCsimOrderResponse)bomCosWsTemplate.marshalSendAndReceive(op);
			CreateCsimOrdResult result = resp.getReturn();
			
			ResultDTO err = result.getResultDTO();			
			String errCode = err.getErrCd();
			String errMsg = err.getErrDesc();
			
			logger.debug("createCsimOrder - errCode: " + errCode + ", errMsg: " + errMsg);

			if (!"0".equals(errCode)) {
				WsClientException ex = new WsClientException(errCode, errMsg);
				throw ex;
			}
			return result.getOcid();
			
		} catch (WsClientException e) {
			logger.error("createCsimOrder() failed", e);
			throw e;
		} catch (Throwable e) {
			logger.error("createCsimOrder() failed", e);
			throw new UnexpectedWsClientException(e);
		}			
		
	}

	/*
	public String updateCsimOrderSRD(String ocid, String srvReqDate ,
			String onHoldInd, String onHoldReqsonCd, String salesCd) throws WsClientException {
		
		logger.debug("updateCsimOrderSRD()");
		CreateCsimOrdRequest req = new CreateCsimOrdRequest();
		
		req.setCcServiceId(ccServiceId);
		req.setSbOrderId(sbOrderId);
		req.setApplnDate(applnDate);
		req.setSalesCd(salesCd);
		req.setShopCd(shopCd);
		req.setSrvReqDate(srvReqDate);
		req.setNewSimDTO(newSimDTO);
		req.setOperInfoDTO(operInfoDTO);
		
		CreateCsimOrder op = new CreateCsimOrder();
		
		op.setCreateCsimOrdRequest(req);

		
		CreateCsimOrderResponse resp = null;
		
		try {
			resp = (CreateCsimOrderResponse)bomCosWsTemplate.marshalSendAndReceive(op);
			CreateCsimOrdResult result = resp.getReturn();
			
			ResultDTO err = result.getResultDTO();			
			String errCode = err.getErrCd();
			String errMsg = err.getErrDesc();
			
			logger.debug("createCsimOrder - errCode: " + errCode + ", errMsg: " + errMsg);

			if (!"0".equals(errCode)) {
				WsClientException ex = new WsClientException(errCode, errMsg);
				throw ex;
			}
			return result.getOcid();
			
		} catch (WsClientException e) {
			logger.error("validateCsimOpt() failed", e);
			throw e;
		} catch (Throwable e) {
			logger.error("validateCsimOpt() failed", e);
			throw new UnexpectedWsClientException(e);
		}			
		
	}
	*/

	
	public void updateCustRegAddr(String custNum, NewAddrDTO newAddrDTO) throws WsClientException {
		
		logger.debug("updateCustRegAddr()");
		UpdCustRegAddrRequest req = new UpdCustRegAddrRequest();
		
		req.setCustNum(custNum);
		req.setNewAddrDTO(newAddrDTO);
		
		req.setOperInfoDTO(operInfoDTO);
		
		UpdateCustRegAddr op = new UpdateCustRegAddr();
		
		op.setUpdCustRegAddrRequest(req);

		
		UpdateCustRegAddrResponse resp = null;
		
		try {
			resp = (UpdateCustRegAddrResponse)bomCosWsTemplate.marshalSendAndReceive(op);
			UpdCustRegAddrResult result = resp.getReturn();
			
			ResultDTO err = result.getResultDTO();			
			String errCode = err.getErrCd();
			String errMsg = err.getErrDesc();
			
			logger.debug("updateCustRegAddr - errCode: " + errCode + ", errMsg: " + errMsg);

			if (!"0".equals(errCode)) {
				WsClientException ex = new WsClientException(errCode, errMsg);
				throw ex;
			}
			
			
		} catch (WsClientException e) {
			logger.error("updateCustRegAddr() failed", e);
			throw e;
		} catch (Throwable e) {
			logger.error("updateCustRegAddr() failed", e);
			throw new UnexpectedWsClientException(e);
		}			
		
	}
	
	
	public PposPreTransDTO runPposPreTranslation(String ocid) throws WsClientException {
		CheckPposPreTransRequest req = new CheckPposPreTransRequest();
		
		req.setOperInfoDTO(operInfoDTO);
		req.setOcid(ocid);
		RunPposPreTranslation op = new RunPposPreTranslation();
		op.setCheckPposPreTransRequest(req);
		

		
		RunPposPreTranslationResponse resp = null;
		try {
			resp = (RunPposPreTranslationResponse)bomCosWsTemplate.marshalSendAndReceive(op);
			
			CheckPposPreTransResult result = resp.getReturn();
			PposPreTransDTO dto = result.getPposPreTransDTO();
			
			ResultDTO err = result.getResultDTO();			
			String errCode = err.getErrCd();
			String errMsg = err.getErrDesc();
			
			logger.debug("runPposPreTranslation - errCode: " + errCode + ", errMsg: " + errMsg);

			if (!"0".equals(errCode)) {
				WsClientException ex = new WsClientException(errCode, errMsg);
				throw ex;
			}

			
			return dto;
			
		} catch (WsClientException e) {
			logger.error("runPposPreTranslation() failed", e);
			throw e;
		} catch (Throwable e) {
			logger.error("runPposPreTranslation() failed", e);
			throw new UnexpectedWsClientException(e);
		}		
	}
	
	public GetPaymSummResult getPaymentSummary(String ocid) throws WsClientException {
		GetPaymSummRequest req = new GetPaymSummRequest();
		
		req.setOperInfoDTO(operInfoDTO);
		req.setOcid(ocid);
		GetPaymentSummary op = new GetPaymentSummary();
		op.setGetPaymSummRequest(req);

		

		
		GetPaymentSummaryResponse resp = null;
		try {
			resp = (GetPaymentSummaryResponse)bomCosWsTemplate.marshalSendAndReceive(op);
			GetPaymSummResult result = resp.getReturn();
			//PaymentSummaryDTO dto = result.getPaymentSummaryDTO();
			
			ResultDTO err = result.getResultDTO();			
			String errCode = err.getErrCd();
			String errMsg = err.getErrDesc();
			
			logger.debug("getPaymentSummary - errCode: " + errCode + ", errMsg: " + errMsg);

			if (!"0".equals(errCode)) {
				WsClientException ex = new WsClientException(errCode, errMsg);
				throw ex;
			}


			
			return result;
			
		} catch (WsClientException e) {
			logger.error("getPaymentSummary() failed", e);
			throw e;
		} catch (Throwable e) {
			logger.error("getPaymentSummary() failed", e);
			throw new UnexpectedWsClientException(e);
		}		
	}
	
	public String createBomSmTender(String refNum, String ocid, String smType, String loginId, CreateSmReqDTO smReq) throws WsClientException {
		
		CreateSmTenderRequest req = new CreateSmTenderRequest();
		
		req.setOperInfoDTO(operInfoDTO);
		req.setRefNum(refNum);
		req.setOcid(ocid);
		req.setSmType(smType);
		req.setLoginID(loginId);
		req.setCreateSmReqDTO(smReq);
		
		
		CreateBomSmTender op = new CreateBomSmTender();
		op.setCreateSmTenderRequest(req);
		
		CreateBomSmTenderResponse resp = null;
		try {
			resp = (CreateBomSmTenderResponse)bomCosWsTemplate.marshalSendAndReceive(op);
			CreateSmTenderResult result = resp.getReturn();
			
			ResultDTO err = result.getResultDTO();			
			String errCode = err.getErrCd();
			String errMsg = err.getErrDesc();
			
			logger.debug("createBomSmTender - errCode: " + errCode + ", errMsg: " + errMsg);

			if (!"0".equals(errCode)) {
				WsClientException ex = new WsClientException(errCode, errMsg);
				throw ex;
			}

			return result.getSmNum();
			
		} catch (WsClientException e) {
			logger.error("createBomSmTender() failed", e);
			throw e;
		} catch (Throwable e) {
			logger.error("createBomSmTender() failed", e);
			throw new UnexpectedWsClientException(e);
		}		
	}
	
	
	public void updateCustTierByCustNum(String custNum, String custTier) throws WsClientException {
		logger.debug("updateCustTierByCustNum - " + custNum + custTier);
		 
		UpdCustTierByCustNumRequest  req = new UpdCustTierByCustNumRequest();
		req.setCustNum(custNum);
		req.setCustTier(custTier);
		
		req.setOperInfoDTO(operInfoDTO);
		UpdateCustTierByCustNum op = new UpdateCustTierByCustNum();
		op.setUpdateCustTierByCustNumRequest(req);

		UpdateCustTierByCustNumResponse resp = null; 
		
		try {
			resp = (UpdateCustTierByCustNumResponse)bomCosWsTemplate.marshalSendAndReceive(op);
			
			UpdCustTierByCustNumResult ret = resp.getReturn();
			
			String errCode = ret.getErrCd();
			String errMsg = ret.getErrDesc();
			
			logger.debug("updateCustTierByCustNum - errCode: " + errCode + ", errMsg: " + errMsg);

			if (!"0".equals(errCode)) {
				WsClientException ex = new WsClientException(errCode, errMsg);
				throw ex;
			}
			
						
		} catch (WsClientException e) {
			logger.error("updateCustTierByCustNum() failed", e);
			throw e;
		} catch (Exception e) {
			logger.error("updateCustTierByCustNum() failed", e);
			throw new UnexpectedWsClientException(e);
		}
		

	}
	
	
	
	//////////////////////////////////////////////////////
	
	
	public static class ValResultDTO {
		private String errType;
		private String errMsg;
		public String getErrType() {
			return errType;
		}
		public void setErrType(String errType) {
			this.errType = errType;
		}
		public String getErrMsg() {
			return errMsg;
		}
		public void setErrMsg(String errMsg) {
			this.errMsg = errMsg;
		}
		
		
	}
}
