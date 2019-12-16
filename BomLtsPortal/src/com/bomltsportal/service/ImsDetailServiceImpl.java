package com.bomltsportal.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.bomltsportal.dto.FsaServiceAssgnDTO;
import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.dto.form.BasketDetailFormDTO;
import com.bomltsportal.dto.form.VasDetailFormDTO;
import com.bomltsportal.util.BomLtsConstant;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.UpgradePcdSrvDTO;
import com.bomwebportal.lts.dto.profile.AddressDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.TechnologyDTO;
import com.bomwebportal.lts.service.UpgradePcdSrvService;
import com.bomwebportal.lts.service.bom.ImsProfileService;
import com.bomwebportal.lts.util.LtsBackendConstant;

public class ImsDetailServiceImpl implements ImsDetailService {

	private ImsProfileService imsProfileService;
	private UpgradePcdSrvService upgradePcdSrvService;
	
	public ImsProfileService getImsProfileService() {
		return imsProfileService;
	}

	public void setImsProfileService(ImsProfileService imsProfileService) {
		this.imsProfileService = imsProfileService;
	}

	public UpgradePcdSrvService getUpgradePcdSrvService() {
		return upgradePcdSrvService;
	}

	public void setUpgradePcdSrvService(UpgradePcdSrvService upgradePcdSrvService) {
		this.upgradePcdSrvService = upgradePcdSrvService;
	}

	public FsaServiceDetailOutputDTO getFsaProfileToShare(
			String idDocType, String idDocNum, OrderCaptureDTO orderCapture) {

		FsaServiceDetailOutputDTO[] fsaServiceDetails = imsProfileService
				.getServiceByDocument(idDocType, idDocNum);

		if (ArrayUtils.isEmpty(fsaServiceDetails)) {
			return null;
		}

		AddressRolloutDTO newAddressRollout = orderCapture.getAddressRollout();
		AddressDetailProfileLtsDTO profileAddress = null;
		for (FsaServiceDetailOutputDTO fsaServiceDetail : fsaServiceDetails) {
			profileAddress = fsaServiceDetail.getAddressDtl();
			if (profileAddress == null
					|| !isProfileFsaAllowShare(orderCapture, fsaServiceDetail)) {
				continue;
			}

			if (StringUtils.equalsIgnoreCase(newAddressRollout.getSrvBdary(),
					profileAddress.getSrvBdry())
					&& StringUtils.equalsIgnoreCase(StringUtils.trimToNull(newAddressRollout.getFlat()),
							StringUtils.trimToNull(profileAddress.getUnitNum()))
					&& StringUtils.equalsIgnoreCase(StringUtils.trimToNull(newAddressRollout.getFloor()),
							StringUtils.trimToNull(profileAddress.getFloorNum()))) {
				return fsaServiceDetail;
			}
		}
		return null;
	}

	private boolean isProfileFsaAllowShare(OrderCaptureDTO orderCapture,
			FsaServiceDetailOutputDTO fsaServiceDetail) {

		// Profile Error
		if (StringUtils.isNotEmpty(fsaServiceDetail.getErrorCode())
				&& !StringUtils.equals("0", fsaServiceDetail.getErrorCode())) {
			return false;
		}

		// Not allow to share with existing PS3 / Standalone EW
		if (StringUtils.equals(BomLtsConstant.FSA_SRV_TYPE_EW,
						fsaServiceDetail.getSrvType())
				|| StringUtils.equals(BomLtsConstant.FSA_SRV_TYPE_EW_SDTV,
						fsaServiceDetail.getSrvType())
				|| StringUtils.equals(BomLtsConstant.FSA_SRV_TYPE_EW_HDTV,
						fsaServiceDetail.getSrvType())
				|| StringUtils.equals(BomLtsConstant.FSA_SRV_TYPE_PCD_PS3,
						fsaServiceDetail.getSrvType())
				|| StringUtils.equals(BomLtsConstant.FSA_SRV_TYPE_PCD_SDTV_PS3,
						fsaServiceDetail.getSrvType())
				|| StringUtils.equals(BomLtsConstant.FSA_SRV_TYPE_PCD_HDTV_PS3,
						fsaServiceDetail.getSrvType())
				|| StringUtils.equals(BomLtsConstant.FSA_SRV_TYPE_EW_SDTV_PS3,
						fsaServiceDetail.getSrvType())
				|| StringUtils.equals(BomLtsConstant.FSA_SRV_TYPE_EW_HDTV_PS3,
						fsaServiceDetail.getSrvType())
				|| StringUtils.equals(BomLtsConstant.FSA_SRV_TYPE_SDTV_PS3,
						fsaServiceDetail.getSrvType())
				|| StringUtils.equals(BomLtsConstant.FSA_SRV_TYPE_HDTV_PS3,
						fsaServiceDetail.getSrvType())
				|| StringUtils.equals(BomLtsConstant.FSA_SRV_TYPE_PS3,
						fsaServiceDetail.getSrvType())) {
			return false;
		}
		
		// Not allow share standalone nowTV (since eye3 launch)
//		if (StringUtils.equals(BomLtsConstant.FSA_SRV_TYPE_SDTV, fsaServiceDetail.getSrvType())
//				|| StringUtils.equals(BomLtsConstant.FSA_SRV_TYPE_HDTV, fsaServiceDetail.getSrvType())) {
//			return false;
//		}
		
		
		// Not allow to share with existing eye
		if (StringUtils.equals("Y", fsaServiceDetail.getIsEye())) {
			return false;
		}
		
		// Not allow to share with pending order
		if (StringUtils.isNotBlank(fsaServiceDetail.getPendingOcid())) {
			return false;
		}
		
		if (isSelectedTvOffer(orderCapture) && isFsaSubscribedTvOffer(fsaServiceDetail)) {
			return false;
		}
		
		// Not allow to share with existing 1L2B case, (NOT Support 1L3B)
		if (StringUtils.equals(BomLtsConstant.MODEM_TYPE_1L2B, fsaServiceDetail.getExistModem())
				|| StringUtils.equals(BomLtsConstant.MODEM_TYPE_nLnB, fsaServiceDetail.getExistModem())) {
			return false;
		}
		
		return true;

	}

	private boolean isFsaSubscribedTvOffer(FsaServiceDetailOutputDTO fsaServiceDetail) {
		if (StringUtils.equals(BomLtsConstant.FSA_SRV_TYPE_HDTV, fsaServiceDetail.getSrvType())
				|| StringUtils.equals(BomLtsConstant.FSA_SRV_TYPE_SDTV, fsaServiceDetail.getSrvType())
				|| StringUtils.equals(BomLtsConstant.FSA_SRV_TYPE_PCD_HDTV, fsaServiceDetail.getSrvType())
				|| StringUtils.equals(BomLtsConstant.FSA_SRV_TYPE_PCD_SDTV, fsaServiceDetail.getSrvType())) {
			return true;
		}
		return false;
	}
	
	private boolean isSelectedTvOffer(OrderCaptureDTO orderCapture) {
		
		BasketDetailFormDTO basketDetailForm = orderCapture.getBasketDetailForm();
		VasDetailFormDTO vasDetailForm = orderCapture.getVasDetailForm();
		
		if (basketDetailForm.getContItemSetList() != null) {
			for (ItemSetDetailDTO itemSetDetail : basketDetailForm.getContItemSetList()) {
				if (ArrayUtils.isNotEmpty(itemSetDetail.getItemDetails())) {
					for (ItemDetailDTO itemDetail : itemSetDetail.getItemDetails()) {
						if (StringUtils.equals(BomLtsConstant.ITEM_TYPE_NOWTV_PAY, itemDetail.getItemType())
								|| StringUtils.equals(BomLtsConstant.ITEM_TYPE_NOWTV_SPEC, itemDetail.getItemType())) {
							if (itemDetail.isSelected()) {
								return true;
							}
						}
					}
				}
			}
		}
		
		
		if (vasDetailForm.getNowTvPayItems() != null) {
			for (ItemDetailDTO itemDetail : vasDetailForm.getNowTvPayItems()) {
				if (StringUtils.equals(BomLtsConstant.ITEM_TYPE_NOWTV_PAY, itemDetail.getItemType()) &&
						itemDetail.isSelected()) {
					return true;
				}
			}
		}
		
		if (vasDetailForm.getNowTvSpecItems() != null) {
			for (ItemDetailDTO itemDetail : vasDetailForm.getNowTvSpecItems()) {
				if (StringUtils.equals(BomLtsConstant.ITEM_TYPE_NOWTV_SPEC,
						itemDetail.getItemType()) && itemDetail.isSelected()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	public FsaServiceAssgnDTO getFsaServiceAssgn(
			AddressRolloutDTO addressRollout,
			FsaServiceDetailOutputDTO profileFsaService) {

		FsaServiceAssgnDTO serviceAssgn = new FsaServiceAssgnDTO();

		String existingService = profileFsaService != null ? profileFsaService
				.getSrvType() : null;
		String newService = profileFsaService != null ? profileFsaService
				.getSrvType() : LtsBackendConstant.FSA_SRV_TYPE_PCD;

		List<UpgradePcdSrvDTO> upgradePcdSrvList = upgradePcdSrvService
				.getUpgradePcdSrv(addressRollout.getMaximumBandwidth(),
						existingService, newService);

		if (upgradePcdSrvList == null || upgradePcdSrvList.isEmpty()) {
			return serviceAssgn;
		}

		String targetModem = upgradePcdSrvList.get(0).getModemArrangement();
		serviceAssgn.setModem(targetModem);

		String targetBandwidth = StringUtils.equals(
				LtsBackendConstant.MODEM_TYPE_2L2B, targetModem) ? addressRollout
				.getMaximumBandwidth() : String.valueOf(upgradePcdSrvList
				.get(0).getMinBandwidth());

		String imsOrderType = upgradePcdSrvList.get(0).getImsOrderType();
		serviceAssgn.setImsOrderType(imsOrderType);

		List<TechnologyDTO> sortedTechnologyList = new ArrayList<TechnologyDTO>();

		// Share Existing FSA
		if (profileFsaService != null) {
			// Existing PON
			if (StringUtils.equals(LtsBackendConstant.TECHNOLOGY_PON,
					profileFsaService.getTechnology())) {
				serviceAssgn.setTechnology(LtsBackendConstant.TECHNOLOGY_PON);
				serviceAssgn.setBandwidth(profileFsaService.getBandwidth());
				return serviceAssgn;
			}

			// Get target bandwidth
			if (Double.parseDouble(profileFsaService.getBandwidth()) > Double
					.parseDouble(targetBandwidth)) {
				targetBandwidth = profileFsaService.getBandwidth();
			}

			// Existing ADSL
			if (StringUtils.equals(LtsBackendConstant.TECHNOLOGY_ADSL,
					profileFsaService.getTechnology())) {
				sortedTechnologyList.add(new TechnologyDTO(
						LtsBackendConstant.TECHNOLOGY_ADSL));
				sortedTechnologyList.add(new TechnologyDTO(
						LtsBackendConstant.TECHNOLOGY_VDSL));
			}

			// Existing VDSL
			if (StringUtils.equals(LtsBackendConstant.TECHNOLOGY_VDSL,
					profileFsaService.getTechnology())) {
				sortedTechnologyList.add(new TechnologyDTO(
						LtsBackendConstant.TECHNOLOGY_VDSL));
			}
		} else {
			// New WG
			sortedTechnologyList.add(new TechnologyDTO(
					LtsBackendConstant.TECHNOLOGY_ADSL));
			sortedTechnologyList.add(new TechnologyDTO(
					LtsBackendConstant.TECHNOLOGY_VDSL));
		}

		boolean isPonCovered = false;
		String assignBandwidth = null;
		String assignTechnology = null;

		for (TechnologyDTO sortedTechnology : sortedTechnologyList) {
			for (TechnologyDTO rolloutTechnology : addressRollout
					.getTechnology()) {
				if (StringUtils.equals(LtsBackendConstant.TECHNOLOGY_PON,
						rolloutTechnology.getTechnology())) {
					isPonCovered = true;
				}
				if (!StringUtils.equals(sortedTechnology.getTechnology(),
						rolloutTechnology.getTechnology())) {
					continue;
				}

				if (rolloutTechnology.getBandwidthDTO() == null
						|| ArrayUtils.isEmpty(rolloutTechnology
								.getBandwidthDTO().getBandwidth())) {
					continue;
				}

				for (String rolloutBandwidth : rolloutTechnology
						.getBandwidthDTO().getBandwidth()) {
					if (Double.parseDouble(rolloutBandwidth) < Double
							.parseDouble(targetBandwidth)) {
						continue;
					}

					// Share Existing FSA with same technology (No need to check
					// resource shortage)
					if (profileFsaService != null
							&& StringUtils.equals(
									rolloutTechnology.getTechnology(),
									profileFsaService.getTechnology())) {
						serviceAssgn.setBandwidth(rolloutBandwidth);
						serviceAssgn.setTechnology(rolloutTechnology
								.getTechnology());
						return serviceAssgn;
					}

					if (StringUtils.equals(LtsBackendConstant.TECHNOLOGY_ADSL,
							rolloutTechnology.getTechnology())
							&& addressRollout.isFttcInd()) {
						assignBandwidth = rolloutBandwidth;
						assignTechnology = LtsBackendConstant.TECHNOLOGY_ADSL;
					} else if (!StringUtils.equals("Y",
							rolloutTechnology.getIsResrcShort())
							&& !StringUtils.equals("Y",
									rolloutTechnology.getIsDeadCase())) {
						serviceAssgn.setBandwidth(rolloutBandwidth);
						serviceAssgn.setTechnology(rolloutTechnology
								.getTechnology());
						return serviceAssgn;
					} else if (StringUtils.isEmpty(assignTechnology)
							&& StringUtils.isEmpty(assignBandwidth)) {
						assignTechnology = rolloutTechnology.getTechnology();
						assignBandwidth = rolloutBandwidth;
					}
				}
			}
		}

		if (isPonCovered) {
			serviceAssgn.setTechnology(LtsBackendConstant.TECHNOLOGY_PON);
			serviceAssgn.setBbShortage(isPonShortage(addressRollout));
			return serviceAssgn;
		}

		serviceAssgn.setBandwidth(assignBandwidth);
		serviceAssgn.setTechnology(assignTechnology);
		serviceAssgn.setBbShortage(true);
		return serviceAssgn;
	}

	private boolean isPonShortage(AddressRolloutDTO addressRollout) {
		for (TechnologyDTO rolloutTechnology : addressRollout.getTechnology()) {
			if (StringUtils.equals(LtsBackendConstant.TECHNOLOGY_PON,
					rolloutTechnology.getTechnology())) {
				if (StringUtils
						.equals("Y", rolloutTechnology.getIsResrcShort())
						|| StringUtils.equals("Y",
								rolloutTechnology.getIsDeadCase())) {
					return true;
				}
			}
		}
		return false;
	}
}
