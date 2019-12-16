package com.bomwebportal.wsclient;

import java.rmi.RemoteException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bom.mob.webservice.huawei.INVInterfaceServicePortType;
import bom.mob.webservice.huawei.INVInterfaceService_Impl;

import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.exception.WebServiceException;
import com.bomwebportal.util.BomWebPortalConstant;
import com.huawei.oss.inventory.webservice.SIMAnonType;
import com.huawei.oss.inventory.webservice.SIMInputValue;
import com.huawei.oss.inventory.webservice.SIMDealAnonType;
import com.huawei.oss.inventory.webservice.UpdateSIMInputValue;

public class HwInvClient {

	protected final Log logger = LogFactory.getLog(getClass());

	private String wsdl;

	public String getWsdl() {
		return wsdl;
	}

	public void setWsdl(String wsdl) {
		this.wsdl = wsdl;
	}

	public MobileSimInfoDTO checkIccid(MobileSimInfoDTO mobileSimInfoDTO)
			throws WebServiceException {
		SIMInputValue inputValue = new SIMInputValue();
		MobileSimInfoDTO result = new MobileSimInfoDTO();

		try {
			logger.info("HWInvService wsdl = " + wsdl);

			inputValue.setIccid(mobileSimInfoDTO.getIccid());
			inputValue.setStatus(BomWebPortalConstant.HWINV_DEFAULT_STATUS);
			inputValue.setOperCode(BomWebPortalConstant.HWINV_OPERCODE);

			INVInterfaceServicePortType hwInvService = null;
			String hwWsdl = wsdl;

			if (hwWsdl != null) {
				hwInvService = new INVInterfaceService_Impl(hwWsdl)
						.getINVInterfaceServiceSOAP11port_http();
			} else {
				logger.error("Failure in loading WSDL of HuaWei web services.");
				throw new WebServiceException(
						"Failure in loading WSDL of HuaWei web services.");
			}

			SIMAnonType[] simAnonTypeList = hwInvService.getSIMs(inputValue);
			if (simAnonTypeList != null && simAnonTypeList.length > 0) {
				logger.info("simAnonTypeList length = "
						+ simAnonTypeList.length);

				SIMAnonType simAnonType = simAnonTypeList[0];

				if (simAnonType != null) {
					logger
							.info("simAnonType Iccid = "
									+ simAnonType.getIccid());

					logger.debug("simAnonType Status = "
							+ simAnonType.getStatus());

					result.setIccid(simAnonType.getIccid());
					result.setHwInvStatus(simAnonType.getStatus());
					result.setItemCd(simAnonType.getItemCode());
					result.setImsi(simAnonType.getImsi());
					result.setPuk1(simAnonType.getPuk1());
					result.setPuk2(simAnonType.getPuk2());
					result.setShopCd(simAnonType.getDepartmentCode());
				}
			}

			return result;

		} catch (Exception e) {
			logger.error("Exception caught in checkIccid()", e);
			throw new WebServiceException(e.getMessage(), e);
		}
	}

	public MobileSimInfoDTO checkIccid(String iccid)
			throws WebServiceException {
		SIMInputValue inputValue = new SIMInputValue();
		MobileSimInfoDTO result = new MobileSimInfoDTO();

		try {
			logger.info("HWInvService wsdl = " + wsdl);

			inputValue.setIccid(iccid);
			inputValue.setStatus(BomWebPortalConstant.HWINV_DEFAULT_STATUS);
			inputValue.setOperCode(BomWebPortalConstant.HWINV_OPERCODE);

			INVInterfaceServicePortType hwInvService = null;
			String hwWsdl = wsdl;

			if (hwWsdl != null) {
				hwInvService = new INVInterfaceService_Impl(hwWsdl)
						.getINVInterfaceServiceSOAP11port_http();
			} else {
				logger.error("Failure in loading WSDL of HuaWei web services.");
				throw new WebServiceException(
						"Failure in loading WSDL of HuaWei web services.");
			}

			SIMAnonType[] simAnonTypeList = hwInvService.getSIMs(inputValue);
			if (simAnonTypeList != null && simAnonTypeList.length > 0) {
				logger.info("simAnonTypeList length = "
						+ simAnonTypeList.length);

				SIMAnonType simAnonType = simAnonTypeList[0];

				if (simAnonType != null) {
					logger
							.info("simAnonType Iccid = "
									+ simAnonType.getIccid());

					logger.debug("simAnonType Status = "
							+ simAnonType.getStatus());

					result.setIccid(simAnonType.getIccid());
					result.setHwInvStatus(simAnonType.getStatus());
					result.setItemCd(simAnonType.getItemCode());
					result.setImsi(simAnonType.getImsi());
					result.setPuk1(simAnonType.getPuk1());
					result.setPuk2(simAnonType.getPuk2());
					result.setShopCd(simAnonType.getDepartmentCode());
				}
			}

			return result;

		} catch (Exception e) {
			logger.error("Exception caught in checkIccid()", e);
			throw new WebServiceException(e.getMessage(), e);
		}
	}
	
	public boolean updateSIM(String fromICCID, // msisdn
			String toICCID, // same msisdn
			int originalStatus, int newStatus, String[] iccidList, // null
			String operatorCode) throws Exception {
		try {

			INVInterfaceServicePortType inter = null;

			UpdateSIMInputValue inputValue = new UpdateSIMInputValue();

			inputValue.setFromICCID(fromICCID);
			inputValue.setToICCID(toICCID);
			inputValue.setOriginalStatus(originalStatus);
			inputValue.setNewStatus(newStatus);
			inputValue.setIccidList(iccidList);
			inputValue.setOperCode(operatorCode);

			//String hwWSDL = "http://10.87.120.197:7060/axis2/services/INVInterfaceService?wsdl";
			/*************************************/
			String hwWSDL = wsdl;
			/*************************************/
			if (hwWSDL != null) {
				try {
					inter = new INVInterfaceService_Impl(hwWSDL)
							.getINVInterfaceServiceSOAP11port_http();

				} catch (Exception e) {
					logger.error("Failure in connecting HuaWei web services.");
					throw new Exception(
							"Failure in connecting HuaWei web services.");
				}
			} else {
				logger.error("Failure in loading WSDL of HuaWei web services.");
				throw new Exception(
						"Failure in loading WSDL of HuaWei web services.");
			}

			SIMDealAnonType[] simDealAnonTypes = inter.updateSIM(inputValue);

			if (simDealAnonTypes != null && simDealAnonTypes.length > 0) {
				return simDealAnonTypes[0].getIsSuccess();
			} else
				return false;

		} catch (RemoteException e) {
			e.printStackTrace();
			throw new Exception("Exception in HwInvClient.updateSIM(): "
					+ e.getMessage(), e);
		}
	}
}
