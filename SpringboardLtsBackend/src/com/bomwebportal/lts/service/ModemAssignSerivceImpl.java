package com.bomwebportal.lts.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.ModemAssignDTO;
import com.bomwebportal.lts.dto.UpgradePcdSrvDTO;
import com.bomwebportal.lts.dto.srvAccess.BandwidthDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.TechnologyDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.service.CodeLkupCacheService;

public class ModemAssignSerivceImpl implements ModemAssignService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	protected UpgradePcdSrvService upgradePcdSrvService;
	protected CodeLkupCacheService techAssgnSeqLkupCacheService;
	protected CodeLkupCacheService autoUpgradeTechLkupCacheService;
	
	public ModemAssignDTO createModemAssign(AddressRolloutDTO addressRollout,
			FsaServiceDetailOutputDTO selectedFsa, String existService, String newService, String newBandWidth, boolean isExternalRelocate, String selectedLineType) {
		
		try {
			
			String rolloutExistService = existService;
			String rolloutNewService = newService;
			if (selectedFsa != null && selectedFsa.isHdReady()) {
				if (StringUtils.isNotBlank(existService)) {
					rolloutExistService = StringUtils.replace(new String(existService), "SDTV", "HDTV");	
				}
				if (StringUtils.isNotBlank(newService)) {
					rolloutNewService = StringUtils.replace(new String(newService), "SDTV", "HDTV");	
				}
			}

			String maxBandwidth = addressRollout.getMaximumBandwidth();
			for(TechnologyDTO technology : addressRollout.getTechnology()){
				if(LtsBackendConstant.TECHNOLOGY_PON.equals(technology.getTechnology())){
					maxBandwidth = "1000";
					break;
				}
			}
			
			List<UpgradePcdSrvDTO> upgradeInfoList = 
				upgradePcdSrvService.getUpgradePcdSrv(maxBandwidth, rolloutExistService, rolloutNewService);
			
			if (upgradeInfoList == null || upgradeInfoList.size() != 1) {
				throw new AppRuntimeException(
						"No record found from w_upgrade_pcd_srv_lkup [pAddrCoverage:"
								+ addressRollout.getMaximumBandwidth()
								+ " | pImsExistSrv:" + existService
								+ " | pImsNewSrv:" + newService);
			}
			
			UpgradePcdSrvDTO upgradeInfo = upgradeInfoList.get(0);
			String targetBandwidth = calculateTargetBandwidth(upgradeInfo, addressRollout, newBandWidth);
			
			ModemAssignDTO modemAssign = assignModemByPreDefinedRule(
					upgradeInfo, addressRollout, selectedFsa, newService,
					newBandWidth, targetBandwidth, isExternalRelocate,
					selectedLineType);
			
			if (modemAssign == null) {
				modemAssign = assignModemBySortedTechnology(upgradeInfo, addressRollout,
						selectedFsa, targetBandwidth, newService, isExternalRelocate);
			}
			// Oct 2016. Auto change technology from ADSL to VDSL.
			autoUpgradeVDSL(selectedFsa, addressRollout, modemAssign, targetBandwidth);
			return modemAssign;	
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e);
		}
	}

	public ModemAssignDTO createRenewalModemAssign(AddressRolloutDTO addressRollout,
			FsaServiceDetailOutputDTO selectedFsa, String newService) {
		ModemAssignDTO modemAssign = new ModemAssignDTO();
		
		modemAssign.setBandwidth(selectedFsa.getBandwidth());
		modemAssign.setBbShortage(false);
		modemAssign.setImsOrderType(LtsBackendConstant.ORDER_TYPE_CHANGE);
		modemAssign.setModemArrangment(selectedFsa.getExistModem());
		modemAssign.setNewImsService(newService);
		modemAssign.setTechnology(selectedFsa.getTechnology());
		return modemAssign;
	}
	
	
	
	/**
	 * @param selectedFsa
	 * @param addressRollout
	 * @param modemAssign
	 * @param targetBandwidth
	 * 
	 * Auto upgrade to VDSL if :
	 * 1) Share with ADSL FSA.
	 * 2) The address rollout is not under 2N building.
	 * 3) VDSL is available (Enough bandwidth, not shortage and dead case)
	 * 
	 */
	
	public void autoUpgradeVDSL(FsaServiceDetailOutputDTO selectedFsa, AddressRolloutDTO addressRollout, ModemAssignDTO modemAssign, String targetBandwidth) {
		String serviceOn = (String)autoUpgradeTechLkupCacheService.get(LtsBackendConstant.TECHNOLOGY_VDSL);
		
		if (!StringUtils.equalsIgnoreCase("Y", serviceOn)) {
			return;
		}
		
		if (addressRollout.isIs2nBuilding()
				|| selectedFsa == null 
				|| !LtsBackendConstant.TECHNOLOGY_ADSL.equals(selectedFsa.getTechnology())
				|| !LtsBackendConstant.TECHNOLOGY_ADSL.equals(modemAssign.getTechnology())) {
			return;
		}

		TechnologyDTO[] technologies = addressRollout.getTechnology();
		if (ArrayUtils.isEmpty(technologies)) {
			return;
		}
		
		for (TechnologyDTO technology : technologies) {
			if (LtsBackendConstant.TECHNOLOGY_VDSL.equals(technology.getTechnology())
					&& !StringUtils.equals("Y", technology.getIsResrcShort())
					&& !StringUtils.equals("Y", technology.getIsDeadCase())) {
				BandwidthDTO availableBandwidth = technology.getBandwidthDTO();
				if (availableBandwidth != null && ArrayUtils.isNotEmpty(availableBandwidth.getBandwidth())) {
					for (String bandwidth : availableBandwidth.getBandwidth()) {
						if (Double.parseDouble(bandwidth) >=  Double.parseDouble(targetBandwidth)) {
							modemAssign.setBandwidth(bandwidth);
							modemAssign.setTechnology(technology.getTechnology());
							modemAssign.setAutoUpgraded(true);                                                                                                                                                                                                                                                                                                                                                                                                                                                             
							return;
						}
					}
				}
			}
		}
	}
	
	private ModemAssignDTO assignModemBySortedTechnology(UpgradePcdSrvDTO upgradeInfo, AddressRolloutDTO addressRollout, 
			FsaServiceDetailOutputDTO selectedFsa, String targetBandwidth, String newService, boolean isExternalRelocate) {
		
		List<TechnologyDTO> sortedTechnologyList = getTechAssgnSeqList();
		
		String assignedBandwidth = null;
		String assignedTechnology = null;
		
		for (TechnologyDTO sortedTechnology : sortedTechnologyList) {
			for (TechnologyDTO rolloutTechnology : addressRollout.getTechnology()) {
				
				if (!StringUtils.equals(sortedTechnology.getTechnology(), rolloutTechnology.getTechnology())) {
					continue;
				}
				
				// PON (PT not allow assign PON.)
				if (LtsBackendConstant.TECHNOLOGY_PON.equals(rolloutTechnology.getTechnology())
						&& StringUtils.isEmpty(addressRollout.getHktPremier())
//						&& ArrayUtils.isEmpty(addressRollout.getUimBlockage())
						&& !addressRollout.isFiberBlockageInd()) {
					if (isRolloutShortage(selectedFsa, addressRollout, rolloutTechnology, isExternalRelocate)) {
						assignedBandwidth = StringUtils.isEmpty(assignedBandwidth) ? "1000" : assignedBandwidth;
						assignedTechnology = StringUtils.isEmpty(assignedTechnology) ? rolloutTechnology.getTechnology() : assignedTechnology;	
					}
					else {
						return new ModemAssignDTO(newService, upgradeInfo.getModemArrangement(),
								rolloutTechnology.getTechnology(), null, upgradeInfo.getImsOrderType(), false);	
					}
				}
				
				// ADSL or VDSL
				if (rolloutTechnology.getBandwidthDTO() == null 
						|| ArrayUtils.isEmpty(rolloutTechnology.getBandwidthDTO().getBandwidth())) {
					continue;
				}
				
				for (String rolloutBandwidth : rolloutTechnology.getBandwidthDTO().getBandwidth()) {
					if (Double.parseDouble(rolloutBandwidth) <  Double.parseDouble(targetBandwidth)) {
						continue;
					}
					
					if (isRolloutShortage(selectedFsa, addressRollout, rolloutTechnology, isExternalRelocate)) {
						// Assign the minimum bandwidth and technology if all resource shortage.  
						assignedBandwidth = StringUtils.isEmpty(assignedBandwidth) ? rolloutBandwidth : assignedBandwidth;
						assignedTechnology = StringUtils.isEmpty(assignedTechnology) ? rolloutTechnology.getTechnology() : assignedTechnology;	
					}
					else {
						return new ModemAssignDTO(newService, upgradeInfo.getModemArrangement(),
								rolloutTechnology.getTechnology(), rolloutBandwidth, upgradeInfo.getImsOrderType(), false);	
					}
				}
			}
		}
		return new ModemAssignDTO(newService, upgradeInfo.getModemArrangement(), assignedTechnology, 
				(StringUtils.equals(assignedBandwidth, "1000") ? null : assignedBandwidth), upgradeInfo.getImsOrderType(), true);	
	}
	
	private ModemAssignDTO assignModemByPreDefinedRule(UpgradePcdSrvDTO upgradeInfo,
			AddressRolloutDTO addressRollout, FsaServiceDetailOutputDTO selectedFsa, String newService,
			String newBandWidth, String targetBandwidth, boolean isExternalRelocate, String selectedLineType) {
		
		// User choose upgrade to PON 
		if (LtsBackendConstant.TECHNOLOGY_PON.equals(newBandWidth)) {
			for (TechnologyDTO rolloutTechnology : addressRollout.getTechnology()) {
				if (LtsBackendConstant.TECHNOLOGY_PON.equals(rolloutTechnology.getTechnology())) {
					return new ModemAssignDTO(newService, upgradeInfo.getModemArrangement(),
							LtsBackendConstant.TECHNOLOGY_PON, "1000", upgradeInfo.getImsOrderType(), 
							isRolloutShortage(selectedFsa, addressRollout, rolloutTechnology, isExternalRelocate));				
				}
			}
		}
		
		// Existing bandwidth already enough to share with eye
		if (isExistBandwidthCovered(selectedFsa, targetBandwidth)) {
			return new ModemAssignDTO(newService, upgradeInfo.getModemArrangement(),
					selectedFsa.getTechnology(), selectedFsa.getBandwidth(), upgradeInfo.getImsOrderType(), false);
		}
		
		if (StringUtils.isNotBlank(selectedLineType)) {
			ModemAssignDTO modemAssign = new ModemAssignDTO();
			String assignedBandwidth = targetBandwidth;
			for(TechnologyDTO technology : addressRollout.getTechnology()) {
				if (selectedLineType.equals(technology.getTechnology())) {
					if (LtsBackendConstant.TECHNOLOGY_PON.equals(selectedLineType)) {
						assignedBandwidth = null;
					}
					else {
						for (String bandwidth : technology.getBandwidthDTO().getBandwidth()) {
							if (Double.parseDouble(bandwidth) <  Double.parseDouble(targetBandwidth)) {
								continue;
							}
							else {
								assignedBandwidth = bandwidth;
								break;
							}
						}
					}
					modemAssign.setBandwidth(assignedBandwidth);
					modemAssign.setTechnology(selectedLineType);
					modemAssign.setBbShortage(isRolloutShortage(selectedFsa, addressRollout, technology, isExternalRelocate));
					modemAssign.setImsOrderType(upgradeInfo.getImsOrderType());
					modemAssign.setModemArrangment(upgradeInfo.getModemArrangement());
					modemAssign.setNewImsService(newService);
					return modemAssign;
				}
			}
		}

		return null;
	}
	
	private List<TechnologyDTO> getTechAssgnSeqList() {
		
		List<TechnologyDTO> sortedTechnologyList = new ArrayList<TechnologyDTO>();
		
		try {
			LookupItemDTO[] techAssgnSeqItems = techAssgnSeqLkupCacheService.getCodeLkupDAO().getCodeLkup();
			if (ArrayUtils.isEmpty(techAssgnSeqItems)) {
				return sortedTechnologyList;
			}
			
			Arrays.sort(techAssgnSeqItems, new Comparator<LookupItemDTO>() {
				public int compare(LookupItemDTO item1, LookupItemDTO item2) {
				      //ascending order
				      return item1.getItemKey().compareTo(item2.getItemKey());
				    }
			});
			
			for (LookupItemDTO item : techAssgnSeqItems) {
				sortedTechnologyList.add(new TechnologyDTO((String)item.getItemValue()));
			}
		}
		catch (Exception e) {
			logger.error("Cannot retrieve technology seq.");
			logger.error(ExceptionUtils.getFullStackTrace(e));
			return sortedTechnologyList;
		}
		
		return sortedTechnologyList;
	}
	
	private boolean isRolloutShortage(FsaServiceDetailOutputDTO selectedFsa,
			AddressRolloutDTO addressRollout, TechnologyDTO rolloutTechnology,
			boolean isExternalRelocate) {
		// Share Existing FSA with same technology (No need to check resource shortage)
		if (selectedFsa != null && !isExternalRelocate
				&&  StringUtils.equals(rolloutTechnology.getTechnology(), selectedFsa.getTechnology())) {
			return false;
		}
		if (LtsBackendConstant.TECHNOLOGY_ADSL.equals(rolloutTechnology.getTechnology()) 
				&& addressRollout.isFttcInd()) {
			return true;
		}
		if (StringUtils.equals("Y", rolloutTechnology.getIsResrcShort())
				|| StringUtils.equals("Y", rolloutTechnology.getIsDeadCase())) {
			return true;
		}

		return false;
	}
	
	private boolean isExistBandwidthCovered(FsaServiceDetailOutputDTO selectedFsa, String targetBandwidth) {
		if (selectedFsa == null) {
			return false;
		}
		if (LtsBackendConstant.TECHNOLOGY_PON.equals(selectedFsa.getTechnology())
				|| Double.parseDouble(selectedFsa.getBandwidth()) >= Double.parseDouble(targetBandwidth)) {
			return true;
		}
		return false;
	}
	
	private String calculateTargetBandwidth(UpgradePcdSrvDTO upgradeInfo, AddressRolloutDTO addressRollout, 
			String newBandWidth){
		
		String targetBandwidth = LtsBackendConstant.MODEM_TYPE_2L2B.equals(upgradeInfo.getModemArrangement()) 
			? addressRollout.getMaximumBandwidth() : String.valueOf(upgradeInfo.getMinBandwidth());
		
		if (StringUtils.isNotBlank(newBandWidth) && StringUtils.isNumeric(newBandWidth) 
				&& Double.parseDouble(newBandWidth) > Double.parseDouble(targetBandwidth) ) {
			targetBandwidth = newBandWidth;
		}
		
		return targetBandwidth;
	}

	
	public UpgradePcdSrvService getUpgradePcdSrvService() {
		return upgradePcdSrvService;
	}

	public void setUpgradePcdSrvService(UpgradePcdSrvService upgradePcdSrvService) {
		this.upgradePcdSrvService = upgradePcdSrvService;
	}

	public CodeLkupCacheService getTechAssgnSeqLkupCacheService() {
		return techAssgnSeqLkupCacheService;
	}

	public void setTechAssgnSeqLkupCacheService(
			CodeLkupCacheService techAssgnSeqLkupCacheService) {
		this.techAssgnSeqLkupCacheService = techAssgnSeqLkupCacheService;
	}

	public CodeLkupCacheService getAutoUpgradeTechLkupCacheService() {
		return autoUpgradeTechLkupCacheService;
	}

	public void setAutoUpgradeTechLkupCacheService(
			CodeLkupCacheService autoUpgradeTechLkupCacheService) {
		this.autoUpgradeTechLkupCacheService = autoUpgradeTechLkupCacheService;
	}
	
}
