package com.bomwebportal.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import com.bomwebportal.dao.BomSubscriberDAO;
import com.bomwebportal.dao.MultiSimInfoDAO;
import com.bomwebportal.dao.OrderDAO;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomMupInfoDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoMemberDTO;
import com.bomwebportal.dto.SimDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsOrderDAO;
import com.bomwebportal.mob.ccs.dto.SbOrderAmendDTO;
import com.bomwebportal.mob.ccs.service.MobCcsSbOrderAmendService;
import com.bomwebportal.wsclient.exception.WsClientException;
import com.bomwebportal.dto.BomOrderDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.util.Utility;
import com.bomwebportal.wsclient.BomCosOrderWsClient;
import bom.mob.schema.javabean.si.springboard.xsd.SubActiveContract;

import com.bomwebportal.dto.SubscriberDTO;

public class MultiSimInfoServiceImpl implements MultiSimInfoService {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	private MultiSimInfoDAO multiSimInfoDAO;
	private VasDetailService vasDetailService;
	private MobCcsOrderDAO mobCcsOrderDAO;
	private OrderDAO orderDAO;
	private MobCcsSbOrderAmendService amendService;
	private BomSubscriberDAO bomSubscriberDAO;

	public BomSubscriberDAO getBomSubscriberDAO() {
		return bomSubscriberDAO;
	}

	public void setBomSubscriberDAO(BomSubscriberDAO bomSubscriberDAO) {
		this.bomSubscriberDAO = bomSubscriberDAO;
	}

	public VasDetailService getVasDetailService() {
		return vasDetailService;
	}

	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}

	public MobCcsOrderDAO getMobCcsOrderDAO() {
		return mobCcsOrderDAO;
	}

	public void setMobCcsOrderDAO(MobCcsOrderDAO mobCcsOrderDAO) {
		this.mobCcsOrderDAO = mobCcsOrderDAO;
	}

	public OrderDAO getOrderDAO() {
		return orderDAO;
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	public MobCcsSbOrderAmendService getAmendService() {
		return amendService;
	}

	public void setAmendService(MobCcsSbOrderAmendService amendService) {
		this.amendService = amendService;
	}

	public MultiSimInfoDAO getMultiSimInfoDAO() {
		return multiSimInfoDAO;
	}

	public void setMultiSimInfoDAO(MultiSimInfoDAO multiSimInfoDAO) {
		this.multiSimInfoDAO = multiSimInfoDAO;
	}

	public List<MultiSimInfoDTO> getMultiSimInfoDTO(String orderId, String locale, String appDate, String channelId, String channelCd, String mipBrand, String mipSimType) {
		try {
			List<MultiSimInfoMemberDTO> multiSimInfoMemberList = new ArrayList<MultiSimInfoMemberDTO>();
			if (!"ACQ".equalsIgnoreCase(orderDAO.getOrderNature(orderId))){
				multiSimInfoMemberList = multiSimInfoDAO.getRetMultiSimInfoMemberDTO(orderId);
			} else{
				multiSimInfoMemberList = multiSimInfoDAO.getMultiSimInfoMemberDTO(orderId);
			}
			if (multiSimInfoMemberList != null) {
				List<MultiSimInfoDTO> multiSimInfoDTOs = new ArrayList<MultiSimInfoDTO>();
				MultiSimInfoDTO currentMSI = null;
				for (MultiSimInfoMemberDTO member: multiSimInfoMemberList) {
					
					if (currentMSI == null||
							(member.getItemId() != null &&
							currentMSI.getItemId() != null&&
							!currentMSI.getItemId().equals(member.getItemId()))) {
						
						currentMSI = new MultiSimInfoDTO();
						currentMSI.setItemId(member.getItemId());
						currentMSI.setMembers(new ArrayList<MultiSimInfoMemberDTO>());

						String nature = vasDetailService.getBasketAttribute(member.getBasketId(), appDate).getNature();
						currentMSI.setBundleItemList(vasDetailService.getRPHSRPList(locale, member.getBasketId(), appDate, channelCd, mipBrand, mipSimType, nature));
						
						currentMSI.setSimItemList(vasDetailService.getSimTypeSelection(locale, appDate, member.getBasketId(), "", channelCd, mipSimType, mipBrand, nature));
						
						currentMSI.setOptionalItemList(vasDetailService.getVasDetailList(channelId ,locale, member.getBasketId(), null, "ITEM_SELECT" , appDate, channelCd, mipBrand, mipSimType, nature));

						currentMSI.setBasket(vasDetailService.getBasketAttribute(member.getBasketId(), appDate));

						//currentMSI.setBasket(basket);
						currentMSI.setAmend(false);
						currentMSI.setAmendSim(false);
						multiSimInfoDTOs.add(currentMSI);
					}
					member.setSelectedVasItemList(multiSimInfoDAO.getSelectedVASList(member.getParentOrderId(), member.getMemberNum()));
					member.setOriginalMsisdn(member.getMsisdn());
					member.setOriginalNewMsisdn(member.getMsisdn());
					member.setOriginalSimICCID(member.getSim().getIccid());
					if (StringUtils.isNotEmpty(member.getSameAsCustInd()) && !member.isSameAsCust()){
						member.setActualUserDTO(multiSimInfoDAO.getActualUser(orderId, member.getMemberNum()));
					}
					currentMSI.getMembers().add(member);
				}
				return multiSimInfoDTOs;
			} else {
				return null;
			}
		} catch (DAOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void orderHistoryProcess(String orderId) {
		try {
			mobCcsOrderDAO.orderHistoryProcess(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	public void updateBomWebMultiSimMNP(MultiSimInfoDTO msi, String salesId) {
		try {
			for (MultiSimInfoMemberDTO msim: msi.getMembers()) {
				orderDAO.updateBomWebMemberMNP(msim, salesId);
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateBomWebMUP(String orderId, String salesId) {
		try {
			orderDAO.updateBomWebMUP(orderId, salesId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	public void insertSbOrderAmendDTO(MultiSimInfoDTO msi, String salesId) {
		for (MultiSimInfoMemberDTO msim: msi.getMembers()) {
			if (msim.isAmendMNP()){
				SbOrderAmendDTO dto = new SbOrderAmendDTO();
				dto.setCreateBy(salesId);
				dto.setLastUpdBy(salesId);
				dto.setOrderAmendType("OA03");
				dto.setOrderId(msim.getParentOrderId());
				dto.setMemberNum(msim.getMemberNum());
				amendService.insertSbOrderAmendDTO(dto);
			}	
		}
		/*SbOrderAmendDTO dto = new SbOrderAmendDTO();
		dto.setCreateBy(salesId);
		dto.setLastUpdBy(salesId);
		dto.setOrderAmendType("OA03");
		for (MultiSimInfoMemberDTO msim: msi.getMembers()) {
			dto.setOrderId(msim.getParentOrderId());
		}
		amendService.insertSbOrderAmendDTO(dto);*/
	}

	public String validateSBPendingOrder(String mrt, String orderId) {
		List<String> mrtList = new ArrayList<String>();
		mrtList.add(mrt);
		return multiSimInfoDAO.validateSBPendingOrder(mrtList, orderId);
	}

	public List<String> getDBSecondaryMRTs(String orderId) {
		List<String> mrtList = multiSimInfoDAO.getDBSecondaryMRTs(orderId);
		if (mrtList == null) { 
			return new ArrayList<String>();
		} else {
			return mrtList;
		}
	}
	
	public List<BomMupInfoDTO> getBomMupInfoDTOList(String srvNum) {
		try {
			return bomSubscriberDAO.getBomMupInfoDTOList(srvNum);

		} catch (DAOException de) {
			throw new AppRuntimeException(de);
		}
	}
	
	public String getBomServiceId(String msisdn) {
		try {
			return bomSubscriberDAO.getBomServiceId(msisdn);

		} catch (DAOException de) {
			throw new AppRuntimeException(de);
		}
	}
	
	public String getItemCodeLkup(String itemCd) {
		try {
			return bomSubscriberDAO.getItemCodeLkup(itemCd);

		} catch (DAOException de) {
			throw new AppRuntimeException(de);
		}
	}
	
	public String getBomPendingOrderListString(String serviceId, String channelId) {
		String result = "";
		try {
			logger.info("getSbPendingOrderListString() is called in multiSimInfoService");
			List<BomOrderDTO> bomPendingOrderList = bomSubscriberDAO.getBomPendingOrder(serviceId);
			if ("1".equals(channelId)) {
				if (CollectionUtils.isNotEmpty(bomPendingOrderList)) {
					result += "Pending order in BOM.";
					for (BomOrderDTO dto : bomPendingOrderList) {
						result += "OCID: " + dto.getOcid() + ", Type: " +dto.getBusTxnType() + ", SRD: " + dto.getSrvReqDate() + ", Shop Cd: "+ dto.getShopCode();
					}
				}
			} else if ("2".equals(channelId)) {
				if (CollectionUtils.isNotEmpty(bomPendingOrderList)) {
					for (BomOrderDTO dto : bomPendingOrderList) {
						result += "OCID: " + dto.getOcid() + ", Type: " + dto.getBusTxnType() + ", SRD: " + dto.getSrvReqDate() + ", Shop Cd: " + dto.getShopCode();
					}
				}
			}
		} catch (DAOException de) {
			logger.error("Exception caught in getBomPendingOrderListString()", de);
			throw new AppRuntimeException(de);
		}
		return result;
	}
	
	public String getAutoInd(MultiSimInfoMemberDTO msim) {
		String autoInd = "N";
		if ("MUPS01".equals(msim.getMemberOrderType()) || "MUPS02".equals(msim.getMemberOrderType())
				|| "MUPS05".equals(msim.getMemberOrderType())) {
			autoInd = "Y";
		}
		return autoInd;
	}
	
	public String getCosInd(MultiSimInfoMemberDTO msim) {
		String cosInd = "N";
		if (("MUPS03".equals(msim.getMemberOrderType()) || "MUPS04".equals(msim.getMemberOrderType())
				|| "MUPS06".equals(msim.getMemberOrderType()) || "MUPS07".equals(msim.getMemberOrderType()))
				&& !"Y".equals(msim.getBrmInd()) && !"Y".equals(msim.getToo1Ind())) {
			cosInd = "Y";
		}
		return cosInd;
	}
	
	public String getToo1Ind(MultiSimInfoMemberDTO msim, CustomerProfileDTO customerProfileDTO) {
		String too1Ind = "N";
		if (("MUPS03".equals(msim.getMemberOrderType()) || "MUPS04".equals(msim.getMemberOrderType()))
				&& customerProfileDTO.getBrand().equals(msim.getSubBrand())) {
			too1Ind = "Y";
		}
		return too1Ind;
	}
	
	public String getBrmInd(MultiSimInfoMemberDTO msim, CustomerProfileDTO customerProfileDTO) {
		String brmInd = "N"; 
		if (("MUPS03".equals(msim.getMemberOrderType()) || "MUPS04".equals(msim.getMemberOrderType()))
				&& !customerProfileDTO.getBrand().equals(msim.getSubBrand())) {
			brmInd = "Y";
		}
		return brmInd;
	}
	
	public String getCmnInd(MultiSimInfoMemberDTO msim) {
		String cmnInd = "N";
		if ("A".equals(msim.getMnpInd())) {
			cmnInd = "Y";
		}
		return cmnInd;
	}
	
	public String getCsimInd(MultiSimInfoMemberDTO msim) {
		String csimInd = "N";
		if (("MUPS03".equals(msim.getMemberOrderType()) || "MUPS04".equals(msim.getMemberOrderType()))
				&& "BRM".equals(msim.getToo1BrmOrder())) {
			csimInd = "Y";
		}
		return csimInd;
	}
	
	public SimDTO getBrmChgSimInfo(String memberOrderId) {
		SimDTO simDto = new SimDTO();
		try {
			simDto = multiSimInfoDAO.getBrmChgSimInfo(memberOrderId);
		} catch (DAOException e) {
			e.printStackTrace();
			return null;
		}
		return simDto;
	}
	
	public boolean checkOneNumber(String serviceId) {
		int result = 0;
		try {
			result = bomSubscriberDAO.checkOneNumber(serviceId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}
}
