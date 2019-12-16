package com.bomwebportal.lts.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO.FsaServiceType;
import com.bomwebportal.lts.dto.ModemTechnologyAissgnDTO;
import com.bomwebportal.lts.dto.NowTvServiceDetailDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.UpgradePcdSrvDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;
import com.bomwebportal.lts.dto.disconnect.TerminateOrderCaptureDTO;
import com.bomwebportal.lts.dto.profile.AddressDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.TenureDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.TechnologyDTO;
import com.bomwebportal.lts.service.bom.ImsProfileService;
import com.bomwebportal.lts.service.bom.ImsServiceProfileAccessService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.wsViQuadplayClient.SBGetProfileServiceResponse;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsRetrieveFsaServiceImpl implements LtsRetrieveFsaService {
	
	protected final Log logger = LogFactory.getLog(getClass());

	protected ImsServiceProfileAccessService imsServiceProfileAccessService;

	protected LtsViQuadplayService ltsViQuadplayService;
	
	protected UpgradePcdSrvService upgradePcdSrvService;
	
	private ImsProfileService imsProfileService;
	
	private CodeLkupCacheService ltsRentalRouterGrpCodeLkupCacheService;

	private CodeLkupCacheService ltsMeshRouterGrpCodeLkupCacheService;
	
	private CodeLkupCacheService ltsBrmAttbDescCacheService;
	
	
	public ImsServiceProfileAccessService getImsServiceProfileAccessService() {
		return imsServiceProfileAccessService;
	}

	public void setImsServiceProfileAccessService(
			ImsServiceProfileAccessService imsServiceProfileAccessService) {
		this.imsServiceProfileAccessService = imsServiceProfileAccessService;
	}
	
	public LtsViQuadplayService getLtsViQuadplayService() {
		return ltsViQuadplayService;
	}

	public void setLtsViQuadplayService(LtsViQuadplayService ltsViQuadplayService) {
		this.ltsViQuadplayService = ltsViQuadplayService;
	}
	
	public UpgradePcdSrvService getUpgradePcdSrvService() {
		return upgradePcdSrvService;
	}

	public void setUpgradePcdSrvService(UpgradePcdSrvService upgradePcdSrvService) {
		this.upgradePcdSrvService = upgradePcdSrvService;
	}

	public ImsProfileService getImsProfileService() {
		return imsProfileService;
	}

	public void setImsProfileService(ImsProfileService imsProfileService) {
		this.imsProfileService = imsProfileService;
	}

	public CodeLkupCacheService getLtsRentalRouterGrpCodeLkupCacheService() {
		return ltsRentalRouterGrpCodeLkupCacheService;
	}

	public void setLtsRentalRouterGrpCodeLkupCacheService(
			CodeLkupCacheService ltsRentalRouterGrpCodeLkupCacheService) {
		this.ltsRentalRouterGrpCodeLkupCacheService = ltsRentalRouterGrpCodeLkupCacheService;
	}

	public CodeLkupCacheService getLtsMeshRouterGrpCodeLkupCacheService() {
		return ltsMeshRouterGrpCodeLkupCacheService;
	}

	public void setLtsMeshRouterGrpCodeLkupCacheService(
			CodeLkupCacheService ltsMeshRouterGrpCodeLkupCacheService) {
		this.ltsMeshRouterGrpCodeLkupCacheService = ltsMeshRouterGrpCodeLkupCacheService;
	}

	public CodeLkupCacheService getLtsBrmAttbDescCacheService() {
		return ltsBrmAttbDescCacheService;
	}

	public void setLtsBrmAttbDescCacheService(CodeLkupCacheService ltsBrmAttbDescCacheService) {
		this.ltsBrmAttbDescCacheService = ltsBrmAttbDescCacheService;
	}

	public ValidationResultDTO retrieveFsaServiceByCustomer(String custNum, OrderCaptureDTO orderCapture, String pUser) {
		try {
			FsaServiceDetailOutputDTO[] fsaServiceDetailOutputs = imsServiceProfileAccessService.getServiceByCustomer(custNum, pUser);
			return createFsaDetailList(fsaServiceDetailOutputs, orderCapture);
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			List<String> errorMsgList = new ArrayList<String>();
			errorMsgList.add(e.getMessage());
			return new ValidationResultDTO(Status.INVAILD, errorMsgList, null);
		}
	}
	
	public ValidationResultDTO retrieveFsaServiceByTerminateCustomer(String custNum, TerminateOrderCaptureDTO orderCapture, String pUser) {
		try {
			FsaServiceDetailOutputDTO[] fsaServiceDetailOutputs = imsServiceProfileAccessService.getServiceByCustomer(custNum, pUser);
			return createTerminateFsaDetailList(fsaServiceDetailOutputs, orderCapture);
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			List<String> errorMsgList = new ArrayList<String>();
			errorMsgList.add(e.getMessage());
			return new ValidationResultDTO(Status.INVAILD, errorMsgList, null);
		}
	}

	public ValidationResultDTO retrieveFsaServiceByDocument(String docType, String docNum, OrderCaptureDTO orderCapture, String pUser) {
		try {
			//TODO GET ROUTER GROUP
			FsaServiceDetailOutputDTO[] fsaServiceDetailOutputs = imsServiceProfileAccessService.getServiceByDocument(docType, docNum, pUser);
			return createFsaDetailList(fsaServiceDetailOutputs, orderCapture);
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			List<String> errorMsgList = new ArrayList<String>();
			errorMsgList.add(e.getMessage());
			return new ValidationResultDTO(Status.INVAILD, errorMsgList, null);
		}
	}
	
	public ValidationResultDTO retrieveFsaServiceByLogin(String loginId, String domain, OrderCaptureDTO orderCapture, String pUser) {
		try {
			FsaServiceDetailOutputDTO[] fsaServiceDetailOutputs = imsServiceProfileAccessService.getServiceByLogin(loginId, domain, pUser);
			return createFsaDetailList(fsaServiceDetailOutputs, orderCapture);
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			List<String> errorMsgList = new ArrayList<String>();
			errorMsgList.add(e.getMessage());
			return new ValidationResultDTO(Status.INVAILD, errorMsgList, null);
		}
	}
	
	public ValidationResultDTO retrieveFsaServiceByFsa(String fsa, OrderCaptureDTO orderCapture, String pUser) {
		try {
			FsaServiceDetailOutputDTO fsaServiceDetailOutputs = imsServiceProfileAccessService.getServiceDetailByFSA(fsa, pUser);
			return createFsaDetailList(new FsaServiceDetailOutputDTO[] {fsaServiceDetailOutputs}, orderCapture);
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			List<String> errorMsgList = new ArrayList<String>();
			errorMsgList.add(e.getMessage());
			return new ValidationResultDTO(Status.INVAILD, errorMsgList, null);
		}
	}
	
	public ValidationResultDTO createFsaDetailList(FsaServiceDetailOutputDTO[] fsaServiceDetailOutputs, OrderCaptureDTO orderCapture) {
		
		List<String> errorMsgList = new ArrayList<String>();
		List<FsaDetailDTO> fsaDetailList = new ArrayList<FsaDetailDTO>();
		
		
			if (ArrayUtils.isEmpty(fsaServiceDetailOutputs)) {
				return new ValidationResultDTO(Status.VALID, errorMsgList, null);
			}
			
			for (FsaServiceDetailOutputDTO fsaServiceDetailOutput : fsaServiceDetailOutputs) {
				
				try {
					if (StringUtils.isNotEmpty(fsaServiceDetailOutput.getErrorCode()) && !StringUtils.equals("0", fsaServiceDetailOutput.getErrorCode())) {
						errorMsgList.add(fsaServiceDetailOutput.getFsa() + ":" + fsaServiceDetailOutput.getErrorDesc());
						continue;
					}
					
					FsaDetailDTO fsaDetail = new FsaDetailDTO();
					fsaDetail.setFsaProfile(fsaServiceDetailOutput);
					fsaDetail.setAddressDetail(fsaServiceDetailOutput.getAddressDtl());

					fsaServiceDetailOutput.getAddressDtl().setFullAddress(getFullAddress(fsaServiceDetailOutput.getAddressDtl()));
					
					fsaDetail.setCustFirstName(fsaServiceDetailOutput.getCustFirstName());
					fsaDetail.setCustLastName(fsaServiceDetailOutput.getCustLastName());
					fsaDetail.setDocNum(fsaServiceDetailOutput.getDocNum());
					fsaDetail.setDocType(fsaServiceDetailOutput.getDocType());
					
					fsaDetail.setExistingService(getExistSrvType(fsaServiceDetailOutput));
					fsaDetail.setBandwidth(fsaServiceDetailOutput.getBandwidth());

					if (StringUtils.equals("Y", fsaServiceDetailOutput.getIsEye())) {
						fsaDetail.setEyeService(true);
					}
					
					fsaDetail.setFsa(Integer.parseInt(fsaServiceDetailOutput.getFsa()));
					fsaDetail.setLoginId(fsaServiceDetailOutput.getLoginID());
					fsaDetail.setExistingModemArrange(StringUtils
								.isEmpty(fsaServiceDetailOutput.getExistModem()) ? LtsConstant.MODEM_TYPE_1L1B
								: fsaServiceDetailOutput.getExistModem());
					
					fsaDetail.setPendingOcid(fsaServiceDetailOutput.getPendingOcid());
					fsaDetail.setPendingOrderExist(!StringUtils.isEmpty(fsaServiceDetailOutput.getPendingOcid()));
					fsaDetail.setPendingOrderType(fsaServiceDetailOutput.getPendingOrderType());
					fsaDetail.setPendingOrderSrd(fsaServiceDetailOutput.getPendingOrderSrd());
					fsaDetail.setTos(StringUtils.equals("Y", fsaServiceDetailOutput.getIsTos()));
					fsaDetail.setTechnology(fsaServiceDetailOutput.getTechnology());
					fsaDetail.setExistMirrorInd(fsaServiceDetailOutput.getExistMirrorInd());
					
					boolean differentIa = isFsaDifferentIa(fsaServiceDetailOutput, orderCapture);
					fsaDetail.setDifferentIa(differentIa);
					if (differentIa) {
						fsaDetail.setAllowConfirmSameIa(isAllowConfirmSameIa(fsaServiceDetailOutput, orderCapture));
					}
					
					fsaDetail.setTenure(fsaServiceDetailOutput.getTenure());
					fsaDetail.setDeactNowTv(fsaServiceDetailOutput.getDeactNowtvInd());
	
					
					if (StringUtils.isNotEmpty(fsaServiceDetailOutput.getEffStratDate())) {
						Date effStartDate = DateUtils.parseDate(StringUtils.substring(fsaServiceDetailOutput.getEffStratDate(), 0, 10), new String[] {"yyyy-MM-dd"});
						fsaDetail.setEffectiveDate(DateUtil.formatDate(effStartDate, "dd/MM/yyyy"));
					}
					
					List<UpgradePcdSrvDTO> upgradePcdSrvList = setUpgradePcdSrv(fsaDetail, fsaServiceDetailOutput, orderCapture);
					setUpgradeBandwidth(fsaDetail, orderCapture);
					setNowTvProfile(fsaDetail, fsaServiceDetailOutput.getFsa(), errorMsgList);
					
					fsaDetail.setRouterGrp(getFsaRouterGrp(fsaServiceDetailOutput.getFsa()));
					fsaDetail.setMeshRouterGrp(checkMeshRouterGrp(fsaServiceDetailOutput.getFsa()));
					fsaDetail.setBrmWifiInd(checkBrmWifiOffer(fsaServiceDetailOutput.getFsa()));
				
					ValidationResultDTO validateAllowShareFsa = validateAllowShareFsa(fsaServiceDetailOutput, fsaDetail, upgradePcdSrvList, orderCapture);
						
					fsaDetail.setNotAllowToShare(Status.INVAILD == validateAllowShareFsa.getStatus());
					fsaDetail.setNotAllowToShareReason(validateAllowShareFsa.getMessageList() != null ? validateAllowShareFsa.getMessageList().toString() : null);
					fsaDetailList.add(fsaDetail);
				}
			catch (Exception e) {
				errorMsgList.add(fsaServiceDetailOutput.getFsa() + ":" + e);
			}
		}

		return new ValidationResultDTO(errorMsgList.isEmpty() ? Status.VALID : Status.INVAILD, errorMsgList, fsaDetailList);
		
	}
	
	public String getFsaRouterGrp(String fsa){
		
		try {
			LookupItemDTO[] grpCdLkups = ltsRentalRouterGrpCodeLkupCacheService.getCodeLkupDAO().getCodeLkup();
			HashMap<String, String> grpCdMap = new HashMap<String, String>();
			for(LookupItemDTO grpCd : grpCdLkups){
				grpCdMap.put(grpCd.getItemKey(), (String) grpCd.getItemValue());
			}

			for(String parmName: grpCdMap.keySet()){
				if("Y".equals(imsServiceProfileAccessService.checkProductParmByFsa(fsa, parmName))){
					return grpCdMap.get(parmName);						
				}
			}
			
		} catch (DAOException de) {
			throw new RuntimeException(de.getCustomMessage());
		}
		
		
		return null;
	}
	
	public boolean checkMeshRouterGrp(String fsa){		
		String meshRouterGrpCd = (String) ltsMeshRouterGrpCodeLkupCacheService.get(LtsConstant.ROUTER_MESH_ROUTER_GROUP_CODE);
		return "Y".equals(imsServiceProfileAccessService.checkProductParmByFsa(fsa, meshRouterGrpCd));
	}

	//TODO
	public boolean checkBrmWifiOffer(String fsa){
		
		try {
			LookupItemDTO[] brmAttbLkups = ltsBrmAttbDescCacheService.getCodeLkupDAO().getCodeLkup();
			for(LookupItemDTO brmAttbLkup: brmAttbLkups){
				String brmAttbDesc = (String) (brmAttbLkup.getItemValue());
				if("Y".equals(imsServiceProfileAccessService.checkProductParmByFsa(fsa, brmAttbDesc))){
					return true;
				}
			}
		} catch (DAOException de) {
			throw new RuntimeException(de.getCustomMessage());
		}
		
		return false;
//		return LtsConstant.FSA_ATTB_VALUE_BRM_WIFI.equals(imsServiceProfileAccessService.checkProductParmByFsa(fsa, LtsConstant.FSA_ATTB_NAME_COMPONENT_VAS_TYPE_WIRELESS_MODEM));
	}

	public ValidationResultDTO createTerminateFsaDetailList(FsaServiceDetailOutputDTO[] fsaServiceDetailOutputs, TerminateOrderCaptureDTO orderCapture) {
		
		List<String> errorMsgList = new ArrayList<String>();
		List<FsaDetailDTO> fsaDetailList = new ArrayList<FsaDetailDTO>();
		
		
			if (ArrayUtils.isEmpty(fsaServiceDetailOutputs)) {
				return new ValidationResultDTO(Status.VALID, errorMsgList, null);
			}
			
			for (FsaServiceDetailOutputDTO fsaServiceDetailOutput : fsaServiceDetailOutputs) {
				
				try {
					if (StringUtils.isNotEmpty(fsaServiceDetailOutput.getErrorCode()) && !StringUtils.equals("0", fsaServiceDetailOutput.getErrorCode())) {
						errorMsgList.add(fsaServiceDetailOutput.getFsa() + ":" + fsaServiceDetailOutput.getErrorDesc());
						continue;
					}
					
					FsaDetailDTO fsaDetail = new FsaDetailDTO();
					fsaDetail.setFsaProfile(fsaServiceDetailOutput);
					fsaDetail.setAddressDetail(fsaServiceDetailOutput.getAddressDtl());

					fsaServiceDetailOutput.getAddressDtl().setFullAddress(getFullAddress(fsaServiceDetailOutput.getAddressDtl()));
					
					fsaDetail.setCustFirstName(fsaServiceDetailOutput.getCustFirstName());
					fsaDetail.setCustLastName(fsaServiceDetailOutput.getCustLastName());
					fsaDetail.setDocNum(fsaServiceDetailOutput.getDocNum());
					fsaDetail.setDocType(fsaServiceDetailOutput.getDocType());
					
					fsaDetail.setExistingService(getExistSrvType(fsaServiceDetailOutput));
					fsaDetail.setBandwidth(fsaServiceDetailOutput.getBandwidth());

					if (StringUtils.equals("Y", fsaServiceDetailOutput.getIsEye())) {
						fsaDetail.setEyeService(true);
					}
					
					fsaDetail.setFsa(Integer.parseInt(fsaServiceDetailOutput.getFsa()));
					fsaDetail.setLoginId(fsaServiceDetailOutput.getLoginID());
					fsaDetail.setExistingModemArrange(StringUtils
								.isEmpty(fsaServiceDetailOutput.getExistModem()) ? LtsConstant.MODEM_TYPE_1L1B
								: fsaServiceDetailOutput.getExistModem());
					
					fsaDetail.setPendingOcid(fsaServiceDetailOutput.getPendingOcid());
					fsaDetail.setPendingOrderExist(!StringUtils.isEmpty(fsaServiceDetailOutput.getPendingOcid()));
					fsaDetail.setPendingOrderType(fsaServiceDetailOutput.getPendingOrderType());
					fsaDetail.setPendingOrderSrd(fsaServiceDetailOutput.getPendingOrderSrd());
					fsaDetail.setTos(StringUtils.equals("Y", fsaServiceDetailOutput.getIsTos()));
					fsaDetail.setTechnology(fsaServiceDetailOutput.getTechnology());
					fsaDetail.setExistMirrorInd(fsaServiceDetailOutput.getExistMirrorInd());
					
					boolean differentIa = TerminateisFsaDifferentIa(fsaServiceDetailOutput, orderCapture);
					fsaDetail.setDifferentIa(differentIa);
					if (differentIa) {
						fsaDetail.setAllowConfirmSameIa(isAllowConfirmSameIa(fsaServiceDetailOutput, orderCapture));
					}
					
					fsaDetail.setTenure(fsaServiceDetailOutput.getTenure());
					fsaDetail.setDeactNowTv(fsaServiceDetailOutput.getDeactNowtvInd());
	
					
					if (StringUtils.isNotEmpty(fsaServiceDetailOutput.getEffStratDate())) {
						Date effStartDate = DateUtils.parseDate(StringUtils.substring(fsaServiceDetailOutput.getEffStratDate(), 0, 10), new String[] {"yyyy-MM-dd"});
						fsaDetail.setEffectiveDate(DateUtil.formatDate(effStartDate, "dd/MM/yyyy"));
					}
					
					List<UpgradePcdSrvDTO> upgradePcdSrvList = setTerminatePcdSrv(fsaDetail, fsaServiceDetailOutput, orderCapture);
					setTerminateBandwidth(fsaDetail, orderCapture);
					setNowTvProfile(fsaDetail, fsaServiceDetailOutput.getFsa(), errorMsgList);

				
				ValidationResultDTO validateAllowShareFsa = validateAllowShareTerminateFsa(fsaServiceDetailOutput, upgradePcdSrvList, fsaDetail.getNowTvServiceDetail(), orderCapture);
					
				fsaDetail.setNotAllowToShare(Status.INVAILD == validateAllowShareFsa.getStatus());
				fsaDetail.setNotAllowToShareReason(validateAllowShareFsa.getMessageList() != null ? validateAllowShareFsa.getMessageList().toString() : null);
				fsaDetailList.add(fsaDetail);
				}
			catch (Exception e) {
				errorMsgList.add(fsaServiceDetailOutput.getFsa() + ":" + e);
			}
		}

		return new ValidationResultDTO(errorMsgList.isEmpty() ? Status.VALID : Status.INVAILD, errorMsgList, fsaDetailList);
		
	}
	
	public FsaServiceType getExistSrvType(FsaServiceDetailOutputDTO fsaServiceDetailOutput) {
		
		if (StringUtils.isBlank(fsaServiceDetailOutput.getSrvType())) {
			return FsaServiceType.WG;
		}
		
		boolean isStandaloneTv = imsProfileService.isStandaloneTv(fsaServiceDetailOutput.getFsa());
		
		FsaServiceType existSrvType = FsaServiceType.valueOf(StringUtils.replace(fsaServiceDetailOutput.getSrvType(), "+", "_"));
		
		if (!isStandaloneTv) {
			switch (existSrvType) {
			case PCD_HDTV:
			case PCD_SDTV:
				return FsaServiceType.PCD;
			case HDTV:
			case SDTV:
				return FsaServiceType.WG;
			}	
		}
		
		return existSrvType;
	}
	
	private void setNowTvProfile(FsaDetailDTO fsaDetail, String fsa, List<String> errorMsgList) {
			
		if (!(FsaServiceType.PCD_HDTV == fsaDetail.getExistingService() 
				|| FsaServiceType.HDTV == fsaDetail.getExistingService() 
				|| FsaServiceType.SDTV == fsaDetail.getExistingService() 
				|| FsaServiceType.PCD_SDTV == fsaDetail.getExistingService() 
				|| FsaServiceType.PCD_EW_SDTV == fsaDetail.getExistingService()
				|| FsaServiceType.PCD_EW_HDTV == fsaDetail.getExistingService())) {
			return;
		}
		
		try {
			SBGetProfileServiceResponse profileServiceResponse = ltsViQuadplayService.getProfileService(fsa);
			
			if (profileServiceResponse == null) {
				return;
			}
			NowTvServiceDetailDTO nowTvServiceDetail = new NowTvServiceDetailDTO();
			nowTvServiceDetail.setReceiveAdultContent(profileServiceResponse.isReceiveAdultContent());
			nowTvServiceDetail.setLineRate(profileServiceResponse.getLineRate());
			nowTvServiceDetail.setEye2Num(profileServiceResponse.getNoOfEye2());
			nowTvServiceDetail.setStatus(profileServiceResponse.getStatus());
			nowTvServiceDetail.setStbNum(profileServiceResponse.getNoOfSTB());
			nowTvServiceDetail.setTvType(profileServiceResponse.getTvType());
			nowTvServiceDetail.setVstbNum(profileServiceResponse.getNoOfVirtualSTB());
			nowTvServiceDetail.setVstbType(profileServiceResponse.getVirtualSTBType());
			nowTvServiceDetail.setArpu(ltsViQuadplayService.getARPU(fsa));
			nowTvServiceDetail.setTvbSubscriber(ltsViQuadplayService.isTVBSubscriber(fsa));
			fsaDetail.setNowTvServiceDetail(nowTvServiceDetail);
		} catch (Exception e) {
			logger.debug(ExceptionUtils.getFullStackTrace(e));
			errorMsgList.add( fsa + " Fail in calling VIM retrieve profile service");
		}
	}
	
	private boolean isAllowConfirmSameIa(FsaServiceDetailOutputDTO fsaServiceDetailOutput, OrderCaptureDTO orderCapture) {
		
		String targetDistrictCd = orderCapture.getLtsAddressRolloutForm()
				.isExternalRelocate() ? orderCapture.getLtsAddressRolloutForm()
				.getDistrictCode() : orderCapture.getLtsServiceProfile()
				.getAddress().getDistrictCd();
		String targetAreaCd = orderCapture.getLtsAddressRolloutForm()
				.isExternalRelocate() ? orderCapture.getLtsAddressRolloutForm()
				.getAreaCode() : orderCapture.getLtsServiceProfile()
				.getAddress().getAreaCd();
		
		if (StringUtils.equals(fsaServiceDetailOutput.getAddressDtl()
				.getDistrictCd(), targetDistrictCd)
				&& StringUtils.equals(fsaServiceDetailOutput.getAddressDtl()
						.getAreaCd(), targetAreaCd)) {
			return true;
		}
		return false;
	}
	
	private boolean isAllowConfirmSameIa(FsaServiceDetailOutputDTO fsaServiceDetailOutput, TerminateOrderCaptureDTO orderCapture) {
		
		return false;
	}
	
	private boolean isFsaDifferentIa(FsaServiceDetailOutputDTO fsaServiceDetailOutputDTO, OrderCaptureDTO orderCapture) {
		
		if (fsaServiceDetailOutputDTO.getAddressDtl() == null || orderCapture.getNewAddressRollout() == null) {
			return true;
		}
		
		if (isSameFlatUnit(fsaServiceDetailOutputDTO.getAddressDtl(), orderCapture.getNewAddressRollout()) 
				&& StringUtils.equalsIgnoreCase(StringUtils.defaultIfEmpty(fsaServiceDetailOutputDTO.getAddressDtl().getFloorNum(), null), StringUtils.defaultIfEmpty(orderCapture.getNewAddressRollout().getFloor(), null))
				&& StringUtils.equalsIgnoreCase(StringUtils.defaultIfEmpty(fsaServiceDetailOutputDTO.getAddressDtl().getSrvBdry(), null), StringUtils.defaultIfEmpty(orderCapture.getNewAddressRollout().getSrvBdary(), null))) {
			return false;
		}
		
		if (isSameFlatUnitSpeicalPattern(fsaServiceDetailOutputDTO.getAddressDtl(), orderCapture.getNewAddressRollout()) 
				&& StringUtils.equalsIgnoreCase(StringUtils.defaultIfEmpty(fsaServiceDetailOutputDTO.getAddressDtl().getSrvBdry(), null), StringUtils.defaultIfEmpty(orderCapture.getNewAddressRollout().getSrvBdary(), null))) {
			return false;
		}
		
		return true;
	}
	
	private boolean TerminateisFsaDifferentIa(FsaServiceDetailOutputDTO fsaServiceDetailOutputDTO, TerminateOrderCaptureDTO orderCapture) {
		
		if (fsaServiceDetailOutputDTO.getAddressDtl() == null || orderCapture.getNewAddressRollout() == null) {
			return true;
		}
		
		if (isSameFlatUnit(fsaServiceDetailOutputDTO.getAddressDtl(), orderCapture.getNewAddressRollout()) 
				&& StringUtils.equalsIgnoreCase(StringUtils.defaultIfEmpty(fsaServiceDetailOutputDTO.getAddressDtl().getFloorNum(), null), StringUtils.defaultIfEmpty(orderCapture.getNewAddressRollout().getFloor(), null))
				&& StringUtils.equalsIgnoreCase(StringUtils.defaultIfEmpty(fsaServiceDetailOutputDTO.getAddressDtl().getSrvBdry(), null), StringUtils.defaultIfEmpty(orderCapture.getNewAddressRollout().getSrvBdary(), null))) {
			return false;
		}
		
		if (isSameFlatUnitSpeicalPattern(fsaServiceDetailOutputDTO.getAddressDtl(), orderCapture.getNewAddressRollout()) 
				&& StringUtils.equalsIgnoreCase(StringUtils.defaultIfEmpty(fsaServiceDetailOutputDTO.getAddressDtl().getSrvBdry(), null), StringUtils.defaultIfEmpty(orderCapture.getNewAddressRollout().getSrvBdary(), null))) {
			return false;
		}
		
		return true;
	}
	
	private boolean isSameFlatUnitSpeicalPattern(AddressDetailProfileLtsDTO fsaProfileAddress, AddressRolloutDTO addressRollout) {

		if (StringUtils.equalsIgnoreCase(
				StringUtils.defaultIfEmpty(fsaProfileAddress.getUnitNum(), ""),
				StringUtils.defaultIfEmpty(addressRollout.getFloor(), "")
						+ StringUtils.defaultIfEmpty(addressRollout.getFlat(), ""))) {
			return true;
		}		
		
		if (StringUtils.equalsIgnoreCase(
				StringUtils.defaultIfEmpty(fsaProfileAddress.getUnitNum(), ""),
				StringUtils.defaultIfEmpty(addressRollout.getFloor(), "")
						+ "0"
						+ StringUtils.defaultIfEmpty(addressRollout.getFlat(), ""))) {
			return true;
		}
		
		if (StringUtils.equalsIgnoreCase(
				StringUtils.defaultIfEmpty(fsaProfileAddress.getFloorNum(), "")
						+ StringUtils.defaultIfEmpty(fsaProfileAddress.getUnitNum(), ""),
				StringUtils.defaultIfEmpty(addressRollout.getFlat(), ""))) {
			return true;
		}
		
		if (StringUtils.equalsIgnoreCase(
				StringUtils.defaultIfEmpty(fsaProfileAddress.getFloorNum(), "")
						+ "0"
						+ StringUtils.defaultIfEmpty(fsaProfileAddress.getUnitNum(), ""),
				StringUtils.defaultIfEmpty(addressRollout.getFlat(), ""))) {
			return true;
		}
		
		return false;
	}
	
	private boolean isSameFlatUnit(AddressDetailProfileLtsDTO fsaProfileAddress, AddressRolloutDTO addressRollout) {
		
		if (StringUtils.isBlank(fsaProfileAddress.getUnitNum()) && StringUtils.isNotBlank(addressRollout.getFlat())) {
			return false;
		}
		
		if (StringUtils.isNotBlank(fsaProfileAddress.getUnitNum()) && StringUtils.isBlank(addressRollout.getFlat())) {
			return false;
		}

		if (StringUtils.isBlank(fsaProfileAddress.getUnitNum()) && StringUtils.isBlank(addressRollout.getFlat())) {
			return true;
		}
		
		if (StringUtils.equalsIgnoreCase(
				StringUtils.removeStart(fsaProfileAddress.getUnitNum(), "0"), 
				StringUtils.removeStart(addressRollout.getFlat(), "0"))) {
			return true;
		}

		return false;
	}
	
	
	
	private boolean isFttcAdslOnly(AddressRolloutDTO addressRollout) {
		
		if (!addressRollout.isFttcInd() 
				|| (addressRollout.getTechnology() != null && addressRollout.getTechnology().length != 1)) {
			return false;
		}
		
		if (StringUtils.equals(LtsConstant.TECHNOLOGY_ADSL, addressRollout.getTechnology()[0].getTechnology())) {
			return true;
		}
		return false;
	}
	
	
	private ValidationResultDTO validateAllowShareFsa(FsaServiceDetailOutputDTO fsaServiceDetailOutput, FsaDetailDTO fsaDetail, 
			List<UpgradePcdSrvDTO> upgradePcdSrvList, OrderCaptureDTO orderCapture) {
		
		List<String> rejectReasonList = new ArrayList<String>();
		
		if (isFttcAdslOnly(orderCapture.getNewAddressRollout())) {
			rejectReasonList.add("Address Rollout [FTTC & ADSL]");
			return new ValidationResultDTO(Status.INVAILD, rejectReasonList, null);
		}

		// Bandwidth cannot cover
		if (upgradePcdSrvList == null || upgradePcdSrvList.isEmpty()) {
			rejectReasonList.add("No available bandwidth coverage");
			return new ValidationResultDTO(Status.INVAILD, rejectReasonList, null);
		}
		
//		if (nowTvServiceDetail != null && nowTvServiceDetail.isTvbSubscriber()) {
//			rejectReasonList.add("Subscribed TVB Offer");
//			return new ValidationResultDTO(Status.INVAILD, rejectReasonList, null);
//		}
		
		if (StringUtils.isNotBlank(fsaServiceDetailOutput.getPendingOcid()) && StringUtils.equals(fsaServiceDetailOutput.getPendingOrderType(), "D")) {
			rejectReasonList.add("Pending Disconnect Order");
			return new ValidationResultDTO(Status.INVAILD, rejectReasonList, null);
		}
//		
//		if (StringUtils.equals("Y", fsaServiceDetailOutput.getIsTos())) {
//			return true;
//		}
		
		if (StringUtils.equals("Y", fsaServiceDetailOutput.getIsEye())
				&& (orderCapture.getLtsServiceProfile().getSrvGrp() == null 
						|| !StringUtils.equals(fsaServiceDetailOutput.getFsa(), orderCapture.getLtsServiceProfile().getRelatedEyeFsa()))) {
			rejectReasonList.add("Subscribed eye Family Product");
			return new ValidationResultDTO(Status.INVAILD, rejectReasonList, null);
		}

//		
		// Not allow standalone EW (without PCD)
		if (StringUtils.equals(FsaServiceType.EW.getDesc(), fsaServiceDetailOutput.getSrvType())
				|| StringUtils.equals(FsaServiceType.EW_SDTV.getDesc(), fsaServiceDetailOutput.getSrvType())
				|| StringUtils.equals(FsaServiceType.EW_HDTV.getDesc(), fsaServiceDetailOutput.getSrvType())) {
			rejectReasonList.add("Standalone EasyWatch");
			return new ValidationResultDTO(Status.INVAILD, rejectReasonList, null);
		}
		
		// Not allow all PS3 Service
		if (StringUtils.equals(FsaServiceType.PS3.getDesc(), fsaServiceDetailOutput.getSrvType())
				|| StringUtils.equals(FsaServiceType.PCD_PS3.getDesc(), fsaServiceDetailOutput.getSrvType())
				|| StringUtils.equals(FsaServiceType.PCD_SDTV_PS3.getDesc(), fsaServiceDetailOutput.getSrvType())
				|| StringUtils.equals(FsaServiceType.PCD_HDTV_PS3.getDesc(), fsaServiceDetailOutput.getSrvType())
				|| StringUtils.equals(FsaServiceType.PCD_EW_SDTV_PS3.getDesc(), fsaServiceDetailOutput.getSrvType())
				|| StringUtils.equals(FsaServiceType.PCD_EW_HDTV_PS3.getDesc(), fsaServiceDetailOutput.getSrvType())
				|| StringUtils.equals(FsaServiceType.EW_SDTV_PS3.getDesc(), fsaServiceDetailOutput.getSrvType())
				|| StringUtils.equals(FsaServiceType.EW_HDTV_PS3.getDesc(), fsaServiceDetailOutput.getSrvType())
				|| StringUtils.equals(FsaServiceType.SDTV_PS3.getDesc(), fsaServiceDetailOutput.getSrvType())
				|| StringUtils.equals(FsaServiceType.HDTV_PS3.getDesc(), fsaServiceDetailOutput.getSrvType())) {
			rejectReasonList.add("Subscribed PS3 Offer");
			return new ValidationResultDTO(Status.INVAILD, rejectReasonList, null);
		}
		
		// Check if (a)Existing DEL, (b) Existing eye1, (c) Existing eye but not under upgrade eye group 
		
		if (StringUtils.isBlank(orderCapture.getLtsServiceProfile().getExistEyeType()) ||
				(orderCapture.getLtsServiceProfile().getSrvGrp() != null
						&& (StringUtils.equalsIgnoreCase(LtsConstant.EYE_TYPE_EYE1, orderCapture.getLtsServiceProfile().getExistEyeType())
								|| !StringUtils.equals(fsaServiceDetailOutput.getFsa(), orderCapture.getLtsServiceProfile().getRelatedEyeFsa())))) {
			
			if (StringUtils.equals(LtsConstant.MODEM_TYPE_1L2B, fsaServiceDetailOutput.getExistModem())
					|| StringUtils.equals(LtsConstant.MODEM_TYPE_nLnB, fsaServiceDetailOutput.getExistModem())) {
				rejectReasonList.add("Subscribed existing offer with 2 Set-Top Box");
				return new ValidationResultDTO(Status.INVAILD, rejectReasonList, null);	
			}	
		}
		
		// Disallow share while target is 2L2B
		if (LtsConstant.MODEM_TYPE_2L2B.equals(fsaDetail.getModemArrange())) {
			rejectReasonList.add("Target modem arrangement: 2L2B");
			return new ValidationResultDTO(Status.INVAILD, rejectReasonList, null);
		}
		
		
		if (StringUtils.equals("Y", fsaServiceDetailOutput.getIsEye()) && 
				!isEyeProfileFsaUnderSameCust(orderCapture, fsaServiceDetailOutput)) {
			rejectReasonList.add("eye FSA with different customer");
			return new ValidationResultDTO(Status.INVAILD, rejectReasonList, null);	
		}
		
		if (StringUtils.equals("Y", fsaServiceDetailOutput.getNoEyeInd())) {
			rejectReasonList.add("Subscried existing offer which cannot be shared with eye service");
			return new ValidationResultDTO(Status.INVAILD, rejectReasonList, null);
		}
		
		return new ValidationResultDTO(Status.VALID, rejectReasonList, null);
	}
	
	private ValidationResultDTO validateAllowShareTerminateFsa(FsaServiceDetailOutputDTO fsaServiceDetailOutput, 
			List<UpgradePcdSrvDTO> upgradePcdSrvList, NowTvServiceDetailDTO nowTvServiceDetail, TerminateOrderCaptureDTO orderCapture) {
		
		List<String> rejectReasonList = new ArrayList<String>();
		
		if (isFttcAdslOnly(orderCapture.getNewAddressRollout())) {
			rejectReasonList.add("Address Rollout [FTTC & ADSL]");
			return new ValidationResultDTO(Status.INVAILD, rejectReasonList, null);
		}

		// Bandwidth cannot cover
		if (upgradePcdSrvList == null || upgradePcdSrvList.isEmpty()) {
			rejectReasonList.add("No available bandwidth coverage");
			return new ValidationResultDTO(Status.INVAILD, rejectReasonList, null);
		}
		
//		if (nowTvServiceDetail != null && nowTvServiceDetail.isTvbSubscriber()) {
//			rejectReasonList.add("Subscribed TVB Offer");
//			return new ValidationResultDTO(Status.INVAILD, rejectReasonList, null);
//		}
		
//		if (StringUtils.isNotBlank(fsaServiceDetailOutput.getPendingOcid()) && StringUtils.equals(fsaServiceDetailOutput.getPendingOrderType(), "D")) {
//			rejectReasonList.add("Pending Disconnect Order");
//			return new ValidationResultDTO(Status.INVAILD, rejectReasonList, null);
//		}
//		
//		if (StringUtils.equals("Y", fsaServiceDetailOutput.getIsTos())) {
//			return true;
//		}
		
		if (StringUtils.equals("Y", fsaServiceDetailOutput.getIsEye())
				&& (orderCapture.getLtsServiceProfile().getSrvGrp() == null 
						|| !StringUtils.equals(fsaServiceDetailOutput.getFsa(), orderCapture.getLtsServiceProfile().getRelatedEyeFsa()))) {
			rejectReasonList.add("Subscribed eye Family Product");
			return new ValidationResultDTO(Status.INVAILD, rejectReasonList, null);
		}

//		
		// Not allow standalone EW (without PCD)
		if (StringUtils.equals(FsaServiceType.EW.getDesc(), fsaServiceDetailOutput.getSrvType())
				|| StringUtils.equals(FsaServiceType.EW_SDTV.getDesc(), fsaServiceDetailOutput.getSrvType())
				|| StringUtils.equals(FsaServiceType.EW_HDTV.getDesc(), fsaServiceDetailOutput.getSrvType())) {
			rejectReasonList.add("Standalone EasyWatch");
			return new ValidationResultDTO(Status.INVAILD, rejectReasonList, null);
		}
		
		// Not allow all PS3 Service
		if (StringUtils.equals(FsaServiceType.PS3.getDesc(), fsaServiceDetailOutput.getSrvType())
				|| StringUtils.equals(FsaServiceType.PCD_PS3.getDesc(), fsaServiceDetailOutput.getSrvType())
				|| StringUtils.equals(FsaServiceType.PCD_SDTV_PS3.getDesc(), fsaServiceDetailOutput.getSrvType())
				|| StringUtils.equals(FsaServiceType.PCD_HDTV_PS3.getDesc(), fsaServiceDetailOutput.getSrvType())
				|| StringUtils.equals(FsaServiceType.PCD_EW_SDTV_PS3.getDesc(), fsaServiceDetailOutput.getSrvType())
				|| StringUtils.equals(FsaServiceType.PCD_EW_HDTV_PS3.getDesc(), fsaServiceDetailOutput.getSrvType())
				|| StringUtils.equals(FsaServiceType.EW_SDTV_PS3.getDesc(), fsaServiceDetailOutput.getSrvType())
				|| StringUtils.equals(FsaServiceType.EW_HDTV_PS3.getDesc(), fsaServiceDetailOutput.getSrvType())
				|| StringUtils.equals(FsaServiceType.SDTV_PS3.getDesc(), fsaServiceDetailOutput.getSrvType())
				|| StringUtils.equals(FsaServiceType.HDTV_PS3.getDesc(), fsaServiceDetailOutput.getSrvType())) {
			rejectReasonList.add("Subscribed PS3 Offer");
			return new ValidationResultDTO(Status.INVAILD, rejectReasonList, null);
		}
		
		// Check if (a)Existing DEL, (b) Existing eye1, (c) Existing eye but not under upgrade eye group 
		
		if (StringUtils.isBlank(orderCapture.getLtsServiceProfile().getExistEyeType()) ||
				(orderCapture.getLtsServiceProfile().getSrvGrp() != null
						&& (StringUtils.equalsIgnoreCase(LtsConstant.EYE_TYPE_EYE1, orderCapture.getLtsServiceProfile().getExistEyeType())
								|| !StringUtils.equals(fsaServiceDetailOutput.getFsa(), orderCapture.getLtsServiceProfile().getRelatedEyeFsa())))) {
			
			if (StringUtils.equals(LtsConstant.MODEM_TYPE_1L2B, fsaServiceDetailOutput.getExistModem())
					|| StringUtils.equals(LtsConstant.MODEM_TYPE_nLnB, fsaServiceDetailOutput.getExistModem())) {
				rejectReasonList.add("Subscribed existing offer with 2 Set-Top Box");
				return new ValidationResultDTO(Status.INVAILD, rejectReasonList, null);	
			}	
		}
		
		
		
		if (StringUtils.equals("Y", fsaServiceDetailOutput.getIsEye()) && 
				!isEyeProfileFsaUnderSameTerminateCust(orderCapture, fsaServiceDetailOutput)) {
			rejectReasonList.add("eye FSA with different customer");
			return new ValidationResultDTO(Status.INVAILD, rejectReasonList, null);	
		}
		
		if (StringUtils.equals("Y", fsaServiceDetailOutput.getNoEyeInd())) {
			rejectReasonList.add("Subscried existing offer which cannot be shared with eye service");
			return new ValidationResultDTO(Status.INVAILD, rejectReasonList, null);
		}
		
		return new ValidationResultDTO(Status.VALID, rejectReasonList, null);
	}
	
	private boolean isEyeProfileFsaUnderSameCust(OrderCaptureDTO orderCapture, FsaServiceDetailOutputDTO fsaProfile) {

		if (!StringUtils.equals(fsaProfile.getFsa(), orderCapture.getLtsServiceProfile().getRelatedEyeFsa())) {
			return false;
		}
		FsaServiceDetailOutputDTO eyeFsaServiceProfile = imsProfileService.getServiceDetailByFSA(fsaProfile.getFsa());
		if (eyeFsaServiceProfile == null) {
			return false;
		}
		
		if (StringUtils.equalsIgnoreCase(eyeFsaServiceProfile.getDocType(), orderCapture.getLtsServiceProfile().getPrimaryCust().getDocType())
				&& StringUtils.equalsIgnoreCase(eyeFsaServiceProfile.getDocNum(), orderCapture.getLtsServiceProfile().getPrimaryCust().getDocNum())) {
			return true;
		}
		return false;
	}
	
	private boolean isEyeProfileFsaUnderSameTerminateCust(TerminateOrderCaptureDTO orderCapture, FsaServiceDetailOutputDTO fsaProfile) {

		if (!StringUtils.equals(fsaProfile.getFsa(), orderCapture.getLtsServiceProfile().getRelatedEyeFsa())) {
			return false;
		}
		FsaServiceDetailOutputDTO eyeFsaServiceProfile = imsProfileService.getServiceDetailByFSA(fsaProfile.getFsa());
		if (eyeFsaServiceProfile == null) {
			return false;
		}
		
		if (StringUtils.equalsIgnoreCase(eyeFsaServiceProfile.getDocType(), orderCapture.getLtsServiceProfile().getPrimaryCust().getDocType())
				&& StringUtils.equalsIgnoreCase(eyeFsaServiceProfile.getDocNum(), orderCapture.getLtsServiceProfile().getPrimaryCust().getDocNum())) {
			return true;
		}
		return false;
	}
	
	
	public boolean isOtherFsaExist(String flat, String floor, String serviceBoundary) {
		try {
			TenureDTO[] tenures = imsProfileService.getImsTenureByAddress(flat, floor, serviceBoundary);
			return (tenures != null && !ArrayUtils.isEmpty(tenures)) ; 
		}
		catch (Exception e) {
			throw new AppRuntimeException(ExceptionUtils.getMessage(e));
		}
	}
	
	private String getFullAddress(AddressDetailProfileLtsDTO addressDetail) {
		
		StringBuilder targetIa = new StringBuilder();
		if (StringUtils.isNotBlank(addressDetail.getLdlotNum())) {
			targetIa.append("HSE ").append(addressDetail.getLdlotNum()).append(", ");	
		}
		if (StringUtils.isNotBlank(addressDetail.getHlotNum())) {
			targetIa.append("LOT HSE NUM ").append(addressDetail.getHlotNum()).append(", ");	
		}
		if (StringUtils.isNotBlank(addressDetail.getUnitNum())) {
			targetIa.append("FLAT ").append(addressDetail.getUnitNum()).append(", ");	
		}
		if (StringUtils.isNotBlank(addressDetail.getFloorNum())) {
			targetIa.append(addressDetail.getFloorNum()).append("/FLOOR ").append(", ");	
		}
		if (StringUtils.isNotBlank(addressDetail.getBuildName())) {
			targetIa.append(addressDetail.getBuildName()).append(", ");	
		}
		if (StringUtils.isNotBlank(addressDetail.getStreetNum())) {
			targetIa.append(addressDetail.getStreetNum()).append(", ");	
		}
		if (StringUtils.isNotBlank(addressDetail.getStreetName())) {
			targetIa.append(addressDetail.getStreetName()).append(", ");	
		}
		if (StringUtils.isNotBlank(addressDetail.getStreetCat())) {
			targetIa.append(addressDetail.getStreetCat()).append(", ");	
		}
		if (StringUtils.isNotBlank(addressDetail.getSectDesc())) {
			targetIa.append(addressDetail.getSectDesc()).append(", ");	
		}
		if (StringUtils.isNotBlank(addressDetail.getDistrict())) {
			targetIa.append(addressDetail.getDistrict()).append(", ");	
		}
		if (StringUtils.isNotBlank(addressDetail.getArea())) {
			targetIa.append(addressDetail.getArea()).append(", ");	
		}
		
		return targetIa.substring(0, targetIa.length()-2).toString();
	}
	
	
	public void setUpgradeBandwidth(FsaDetailDTO fsaDetail, OrderCaptureDTO orderCapture) {
		
		if (!(fsaDetail.getExistingService() == FsaServiceType.WG
				|| fsaDetail.getExistingService() == FsaServiceType.PCD
				|| fsaDetail.getExistingService() == FsaServiceType.PCD_EW
				|| fsaDetail.getExistingService() == FsaServiceType.PCD_EW_HDTV
				|| fsaDetail.getExistingService() == FsaServiceType.PCD_EW_SDTV
				|| fsaDetail.getExistingService() == FsaServiceType.PCD_HDTV
				|| fsaDetail.getExistingService() == FsaServiceType.PCD_SDTV)) {
			return;
		}
		
		TechnologyDTO[] technologies = orderCapture.getNewAddressRollout().getTechnology();
		
		if (ArrayUtils.isEmpty(technologies)) {
			return;
		}
		
		Map<String, String> upgradeBandwidthMap = new HashMap<String, String>();
		boolean isPonCovered = false;
		for (TechnologyDTO technology : technologies) {
			if (StringUtils.equals(LtsConstant.TECHNOLOGY_PON, technology.getTechnology())) {
				isPonCovered = true;
			}
			if (technology.getBandwidthDTO() == null 
					|| ArrayUtils.isEmpty(technology.getBandwidthDTO().getBandwidth())) {
				continue;
			}
			for (String bandwidth : technology.getBandwidthDTO().getBandwidth()) {
				if (StringUtils.isEmpty(fsaDetail.getBandwidth())  
						|| StringUtils.isEmpty(bandwidth)) {
					continue;
				}
				
				if (Double.parseDouble(fsaDetail.getBandwidth()) < Double.parseDouble(bandwidth)) {
					if (!upgradeBandwidthMap.containsKey(bandwidth)) {
						upgradeBandwidthMap.put(bandwidth, bandwidth + "M");
					}
				}
			}
		}
		
		if (isPonCovered) {
			upgradeBandwidthMap.put(LtsConstant.TECHNOLOGY_PON, "PON");
		}
		
		
		if (upgradeBandwidthMap.size() == 0) {
			return;
		}

		
		List<Map.Entry<String, String>> list =
            new LinkedList<Map.Entry<String, String>>( upgradeBandwidthMap.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<String, String>>()
        {
            public int compare( Map.Entry<String, String> o1, Map.Entry<String, String> o2 )
            {
            	Double d1 = LtsConstant.TECHNOLOGY_PON.equals(o1.getKey()) ? new Double(1000) : Double.parseDouble(o1.getKey());
            	Double d2 = LtsConstant.TECHNOLOGY_PON.equals(o2.getKey()) ? new Double(1000) : Double.parseDouble(o2.getKey());
                return d1.compareTo( d2 );
            }
        } );

		Map<String, String> result = new LinkedHashMap<String, String>();
		for (Map.Entry<String, String> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
        
		fsaDetail.setUpgradeBandwidthMap(result);
	}
	
	public void setTerminateBandwidth(FsaDetailDTO fsaDetail, TerminateOrderCaptureDTO orderCapture) {
		
		if (!(fsaDetail.getExistingService() == FsaServiceType.WG
				|| fsaDetail.getExistingService() == FsaServiceType.PCD
				|| fsaDetail.getExistingService() == FsaServiceType.PCD_EW
				|| fsaDetail.getExistingService() == FsaServiceType.PCD_EW_HDTV
				|| fsaDetail.getExistingService() == FsaServiceType.PCD_EW_SDTV
				|| fsaDetail.getExistingService() == FsaServiceType.PCD_HDTV
				|| fsaDetail.getExistingService() == FsaServiceType.PCD_SDTV)) {
			return;
		}
		
		TechnologyDTO[] technologies = orderCapture.getNewAddressRollout().getTechnology();
		
		if (ArrayUtils.isEmpty(technologies)) {
			return;
		}
		
		Map<String, String> upgradeBandwidthMap = new HashMap<String, String>();
		boolean isPonCovered = false;
		for (TechnologyDTO technology : technologies) {
			if (StringUtils.equals(LtsConstant.TECHNOLOGY_PON, technology.getTechnology())) {
				isPonCovered = true;
			}
			if (technology.getBandwidthDTO() == null 
					|| ArrayUtils.isEmpty(technology.getBandwidthDTO().getBandwidth())) {
				continue;
			}
			for (String bandwidth : technology.getBandwidthDTO().getBandwidth()) {
				if (StringUtils.isEmpty(fsaDetail.getBandwidth())  
						|| StringUtils.isEmpty(bandwidth)) {
					continue;
				}
				
				if (Double.parseDouble(fsaDetail.getBandwidth()) < Double.parseDouble(bandwidth)) {
					if (!upgradeBandwidthMap.containsKey(bandwidth)) {
						upgradeBandwidthMap.put(bandwidth, bandwidth + "M");
					}
				}
			}
		}
		
		if (isPonCovered) {
			upgradeBandwidthMap.put(LtsConstant.TECHNOLOGY_PON, "PON");
		}
		
		
		if (upgradeBandwidthMap.size() == 0) {
			return;
		}

		
		List<Map.Entry<String, String>> list =
            new LinkedList<Map.Entry<String, String>>( upgradeBandwidthMap.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<String, String>>()
        {
            public int compare( Map.Entry<String, String> o1, Map.Entry<String, String> o2 )
            {
            	Double d1 = LtsConstant.TECHNOLOGY_PON.equals(o1.getKey()) ? new Double(1000) : Double.parseDouble(o1.getKey());
            	Double d2 = LtsConstant.TECHNOLOGY_PON.equals(o2.getKey()) ? new Double(1000) : Double.parseDouble(o2.getKey());
                return d1.compareTo( d2 );
            }
        } );

		Map<String, String> result = new LinkedHashMap<String, String>();
		for (Map.Entry<String, String> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
        
		fsaDetail.setUpgradeBandwidthMap(result);
	}
	
	public List<UpgradePcdSrvDTO> setUpgradePcdSrv(FsaDetailDTO fsaDetail, FsaServiceDetailOutputDTO fsaServiceDetailOutput, OrderCaptureDTO orderCapture) {
		String addressCoverage = orderCapture.getNewAddressRollout().getMaximumBandwidth();
		String imsExistingService = null; 
		
		// Assume PCD_EW == PCD in lookup table
		switch (fsaDetail.getExistingService()) {
		case PCD_EW:
			imsExistingService = FsaServiceType.PCD.getDesc();
			break;
		case PCD_EW_HDTV:
			imsExistingService = FsaServiceType.PCD_HDTV.getDesc();
			break;
		case PCD_EW_SDTV:
			imsExistingService = FsaServiceType.PCD_SDTV.getDesc();
			break;
		default:
			imsExistingService = fsaDetail.getExistingService().getDesc();
			break;
		}
		
		if (fsaServiceDetailOutput.isHdReady()) {
			imsExistingService = StringUtils.replace(imsExistingService, "SDTV", "HDTV");
		}
		
		List<UpgradePcdSrvDTO> upgradePcdSrvList = upgradePcdSrvService.getUpgradePcdSrv(addressCoverage, imsExistingService, null);

		if (upgradePcdSrvList == null || upgradePcdSrvList.isEmpty()) {
			return null;
		}
		List<FsaServiceType> futureFsaServiceList = new ArrayList<FsaDetailDTO.FsaServiceType>();
		
		for (UpgradePcdSrvDTO upgradePcdSrv : upgradePcdSrvList) {
			if (!StringUtils.equals(upgradePcdSrv.getImsNewSrv(), imsExistingService)) {
				futureFsaServiceList.add(FsaServiceType.valueOf(StringUtils.replaceChars(upgradePcdSrv.getImsNewSrv(), "+", "_")));
			}
			if (StringUtils.equals(upgradePcdSrv.getImsNewSrv(), imsExistingService)) {
				fsaDetail.setModemArrange(upgradePcdSrv.getModemArrangement());
			}
		}
		fsaDetail.setFutureFsaServiceList(futureFsaServiceList);
		return upgradePcdSrvList;
	}
	
	public ModemTechnologyAissgnDTO createModemTechnologyAissgn(String existingService, String newService,
			AddressRolloutDTO addressRollout, FsaDetailDTO selectedFsaDetail, boolean isPremierChannel, boolean isExternalRelocate) {

		List<UpgradePcdSrvDTO> upgradePcdSrvList = upgradePcdSrvService
				.getUpgradePcdSrv(addressRollout.getMaximumBandwidth(), existingService, newService);
		
		if (upgradePcdSrvList == null || upgradePcdSrvList.isEmpty()) {
			return null;
		}

		// Assume only one result record;
		String modemArrangment = upgradePcdSrvList.get(0).getModemArrangement();
		String targetBandwidth = StringUtils.equals(LtsConstant.MODEM_TYPE_2L2B, upgradePcdSrvList.get(0).getModemArrangement()) 
				? addressRollout.getMaximumBandwidth() : String.valueOf(upgradePcdSrvList.get(0).getMinBandwidth());
		
		ModemTechnologyAissgnDTO modemTechnologyAssign = new ModemTechnologyAissgnDTO();
		modemTechnologyAssign.setNewImsService(newService);
		modemTechnologyAssign.setModemArrangment(modemArrangment);
		modemTechnologyAssign.setImsOrderType(upgradePcdSrvList.get(0).getImsOrderType());
		
		List<TechnologyDTO> sortedTechnologyList = new ArrayList<TechnologyDTO>();
		
		// Share Existing FSA
		if (selectedFsaDetail != null) {
			// Existing PON
			if (StringUtils.equals(LtsConstant.TECHNOLOGY_PON, selectedFsaDetail.getTechnology())) {
				modemTechnologyAssign.setTechnology(LtsConstant.TECHNOLOGY_PON);
				modemTechnologyAssign.setBandwidth(selectedFsaDetail.getBandwidth());
				return modemTechnologyAssign;
			}
			
			// Upgrade to PON 
			if (StringUtils.equals(LtsConstant.TECHNOLOGY_PON, selectedFsaDetail.getUpgradeBandwidth())) {
				modemTechnologyAssign.setTechnology(LtsConstant.TECHNOLOGY_PON);
				modemTechnologyAssign.setBandwidth("1000");
				modemTechnologyAssign.setBbShortage(isPonShortage(addressRollout));
				return modemTechnologyAssign;
			}
			
			// Get target bandwidth
			if (StringUtils.isNotBlank(selectedFsaDetail.getUpgradeBandwidth()) && StringUtils.isNumeric(selectedFsaDetail.getUpgradeBandwidth()) 
					&& Double.parseDouble(selectedFsaDetail.getUpgradeBandwidth()) > Double.parseDouble(targetBandwidth) ) {
				targetBandwidth = selectedFsaDetail.getUpgradeBandwidth();
			}
			else if (Double.parseDouble(selectedFsaDetail.getBandwidth()) > Double.parseDouble(targetBandwidth) ) {
				targetBandwidth = selectedFsaDetail.getBandwidth();
			}
			
			// Existing ADSL
			if (StringUtils.equals(LtsConstant.TECHNOLOGY_ADSL, selectedFsaDetail.getTechnology())) {
				sortedTechnologyList.add(new TechnologyDTO(LtsConstant.TECHNOLOGY_ADSL));
				sortedTechnologyList.add(new TechnologyDTO(LtsConstant.TECHNOLOGY_VDSL));
			}
			
			// Existing VDSL
			if (StringUtils.equals(LtsConstant.TECHNOLOGY_VDSL, selectedFsaDetail.getTechnology())) {
				sortedTechnologyList.add(new TechnologyDTO(LtsConstant.TECHNOLOGY_VDSL));
			}
		}
		else {
			// New WG
			sortedTechnologyList.add(new TechnologyDTO(LtsConstant.TECHNOLOGY_ADSL));
			sortedTechnologyList.add(new TechnologyDTO(LtsConstant.TECHNOLOGY_VDSL));
		}
		
		boolean isPonCovered = false;
		String assignBandwidth = null;
		String assignTechnology = null;
		
		for (TechnologyDTO sortedTechnology : sortedTechnologyList) {
			for (TechnologyDTO rolloutTechnology : addressRollout.getTechnology()) {
				if (StringUtils.equals(LtsConstant.TECHNOLOGY_PON, rolloutTechnology.getTechnology())
						&& !isPremierChannel) {
					isPonCovered = true;
				}
				if (!StringUtils.equals(sortedTechnology.getTechnology(), rolloutTechnology.getTechnology())) {
					continue;
				}
				
				if (rolloutTechnology.getBandwidthDTO() == null 
						|| ArrayUtils.isEmpty(rolloutTechnology.getBandwidthDTO().getBandwidth())) {
					continue;
				}
				
				for (String rolloutBandwidth : rolloutTechnology.getBandwidthDTO().getBandwidth()) {
					if (Double.parseDouble(rolloutBandwidth) <  Double.parseDouble(targetBandwidth)) {
						continue;
					}
					
					// Share Existing FSA with same technology (No need to check resource shortage)
					if (selectedFsaDetail != null &&
							StringUtils.equals(rolloutTechnology.getTechnology(), selectedFsaDetail.getTechnology())
							&& !isExternalRelocate) {
						modemTechnologyAssign.setBandwidth(rolloutBandwidth);
						modemTechnologyAssign.setTechnology(rolloutTechnology.getTechnology());
						return modemTechnologyAssign;
					}
					
					if (StringUtils.equals(LtsConstant.TECHNOLOGY_ADSL, rolloutTechnology
							.getTechnology()) && addressRollout.isFttcInd()) {
						assignBandwidth = rolloutBandwidth;
						assignTechnology = LtsConstant.TECHNOLOGY_ADSL;
					}
					else if (!StringUtils.equals("Y", rolloutTechnology.getIsResrcShort())
								&& !StringUtils.equals("Y", rolloutTechnology.getIsDeadCase())) {
						modemTechnologyAssign.setBandwidth(rolloutBandwidth);
						modemTechnologyAssign.setTechnology(rolloutTechnology.getTechnology());
						return modemTechnologyAssign;
					}
					else if (StringUtils.isEmpty(assignTechnology) && StringUtils.isEmpty(assignBandwidth) ) {
							assignTechnology = rolloutTechnology.getTechnology();
							assignBandwidth = rolloutBandwidth;
						}
					}
			}
		}
		if (isPonCovered) {
			modemTechnologyAssign.setTechnology(LtsConstant.TECHNOLOGY_PON);
			modemTechnologyAssign.setBbShortage(isPonShortage(addressRollout) );
			return modemTechnologyAssign;
		}

		modemTechnologyAssign.setBandwidth(assignBandwidth);
		modemTechnologyAssign.setTechnology(assignTechnology);
		modemTechnologyAssign.setBbShortage(true);	
		return modemTechnologyAssign;
	}

	private boolean isPonShortage(AddressRolloutDTO addressRollout) {
		for (TechnologyDTO rolloutTechnology : addressRollout.getTechnology()) {
			if (StringUtils.equals(LtsConstant.TECHNOLOGY_PON, rolloutTechnology.getTechnology())) {
				if (StringUtils.equals("Y", rolloutTechnology.getIsResrcShort())
						|| StringUtils.equals("Y", rolloutTechnology.getIsDeadCase())) {
					return true;
				}	
			}
		}
		return false;
	}
	public List<UpgradePcdSrvDTO> setTerminatePcdSrv(FsaDetailDTO fsaDetail, FsaServiceDetailOutputDTO fsaServiceDetailOutput, TerminateOrderCaptureDTO orderCapture) {
		String addressCoverage = orderCapture.getNewAddressRollout().getMaximumBandwidth();
		String imsExistingService = null; 
		
		// Assume PCD_EW == PCD in lookup table
		switch (fsaDetail.getExistingService()) {
		case PCD_EW:
			imsExistingService = FsaServiceType.PCD.getDesc();
			break;
		case PCD_EW_HDTV:
			imsExistingService = FsaServiceType.PCD_HDTV.getDesc();
			break;
		case PCD_EW_SDTV:
			imsExistingService = FsaServiceType.PCD_SDTV.getDesc();
			break;
		default:
			imsExistingService = fsaDetail.getExistingService().getDesc();
			break;
		}
		
		if (fsaServiceDetailOutput.isHdReady()) {
			imsExistingService = StringUtils.replace(imsExistingService, "SDTV", "HDTV");
		}
		
		List<UpgradePcdSrvDTO> upgradePcdSrvList = upgradePcdSrvService.getUpgradePcdSrv(addressCoverage, imsExistingService, null);
		
		
		if (upgradePcdSrvList == null || upgradePcdSrvList.isEmpty()) {
			return null;
		}
		List<FsaServiceType> futureFsaServiceList = new ArrayList<FsaDetailDTO.FsaServiceType>();
		
		for (UpgradePcdSrvDTO upgradePcdSrv : upgradePcdSrvList) {
			if (!StringUtils.equals(upgradePcdSrv.getImsNewSrv(), imsExistingService)) {
				futureFsaServiceList.add(FsaServiceType.valueOf(StringUtils.replaceChars(upgradePcdSrv.getImsNewSrv(), "+", "_")));
			}
			if (StringUtils.equals(upgradePcdSrv.getImsNewSrv(), imsExistingService)) {
				fsaDetail.setModemArrange(upgradePcdSrv.getModemArrangement());
			}
		}
		fsaDetail.setFutureFsaServiceList(futureFsaServiceList);
		return upgradePcdSrvList;
	}

}
