package com.bomwebportal.lts.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.LtsHousingTypeDAO;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.srvAccess.AddrRolloutDTO;
import com.bomwebportal.lts.dto.srvAccess.TechnologyDTO;
import com.bomwebportal.lts.dto.srvAccess.UimBlockageDTO;
import com.bomwebportal.lts.service.bom.AddressDetailLtsService;
import com.bomwebportal.lts.service.bom.EyeProfileCountService;
import com.bomwebportal.lts.service.bom.ImsProfileService;
import com.bomwebportal.lts.util.LtsBackendConstant;

public class AddressRolloutServiceImpl implements AddressRolloutService {

	private AddressDetailLtsService addressDetailLtsService;
	private ImsProfileService imsProfileService;
	private EyeProfileCountService eyeProfileCountService;
	private LtsHousingTypeDAO ltsHousingTypeDao;
	
	private final Log logger = LogFactory.getLog(getClass());
	
	public enum TechnologyType {
		ADSL("A"), VDSL("V"), PON("P");
		
		public String desc;
		   private TechnologyType(String desc) {
		       this.desc = desc;
		   }
		   public String getDesc() {
		       return desc;
		   }
		   public String getName() {
		       return name();
		   } 
		   
		   @Override
		   public String toString () {
		       return getDesc();
		   } 
	}

	public List<UimBlockageDTO> getUimBlockageList(String flat, String floor, String sb) {
		try {
			return imsProfileService.getUimBlockageList(flat, floor, sb);	
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e);
		}
	}
	
	
	// edited by Markball on 25-02-2016
	public AddressRolloutDTO getAddressRollout (String serviceBoundary, String flat, String floor) {
		
		if (StringUtils.isEmpty(serviceBoundary)) {
			return null;
		}
		
		AddrRolloutDTO rolloutResult = this.imsProfileService.getAddrRolloutDtl(flat, floor, serviceBoundary);

		if (rolloutResult == null) {
			return null;
		}

		filterPonWhenPonVillaExist(rolloutResult);
		
		AddressRolloutDTO addressRollout = new AddressRolloutDTO();
		
		addressRollout.setFlat(flat);
		addressRollout.setFloor(floor);
		addressRollout.setSrvBdary(serviceBoundary);
		addressRollout.setEyeCoverage(StringUtils.equals(rolloutResult.getoCoverEyex(), "Y"));
		addressRollout.setPeCoverage(StringUtils.equals(rolloutResult.getoCoverPe(), "Y"));
		addressRollout.setFiberBlockageInd(StringUtils.equals(rolloutResult.getoIsFiberBlockage(), "Y"));
		addressRollout.setFieldWorkPermit(rolloutResult.getoFieldPermitDay());
		addressRollout.setFttcInd(StringUtils.equals("Y", rolloutResult.getoIsFttcInd()));
		//addressRollout.setHousingType(StringUtils.equals(rolloutResult.getoIsPh(), "Y") ? LtsBackendConstant.HOUSE_TYPE_PUBLIC_HSE : LtsBackendConstant.HOUSE_TYPE_NON_PUBLIC_HSE);
		addressRollout.setHousingType(rolloutResult.getoHousingType());
		addressRollout.setHousingTypeCd(rolloutResult.getoHousingTypeCd());
		addressRollout.setHousingTypeDesc(rolloutResult.getoHousingTypeDesc());
		String n2BuildingInd = rolloutResult.getoN2Building();
		if (StringUtils.isNotBlank(n2BuildingInd) && !StringUtils.equalsIgnoreCase(n2BuildingInd, "NULL")) {
			addressRollout.setIs2nBuilding(true);
			addressRollout.setN2BuildingInd(n2BuildingInd);
		}
		
		addressRollout.setTechnology(rolloutResult.getTechnologyDTO());
		addressRollout.setPcdResourceShortage(getResourceShortageMsg(rolloutResult.getTechnologyDTO()));
		addressRollout.setMaximumTechnology(getMaxTechnology(rolloutResult.getTechnologyDTO()));
		addressRollout.setPonVilla(StringUtils.equals(rolloutResult.getoPonVillaInd(), "Y"));
		addressRollout.setAvailableBandwidth(getAvailableBandwidth(rolloutResult.getTechnologyDTO()));
		String hktPremier = rolloutResult.getoAddrTagCd();
		addressRollout.setHktPremier(hktPremier);
		
		addressRollout.setNumOfEyeProfile(eyeProfileCountService.getEyeProfileCountByAddr(flat, floor, serviceBoundary, true));
		
		if (!StringUtils.equals(TechnologyType.PON.getName(), addressRollout.getMaximumTechnology())
				// Ignore PON for PT address
				|| StringUtils.isNotBlank(hktPremier)
				// Ignore PON if blockage
				|| addressRollout.isFiberBlockageInd()) {
			addressRollout.setMaximumBandwidth(getMaxBandwidth(rolloutResult.getTechnologyDTO()));
		}
		
		addressRollout.setImsAddressBlacklist(StringUtils.equals(rolloutResult.getoIsBlacklistAddr(), "Y") );
		addressRollout.setLtsAddressBlacklist(addressDetailLtsService.isBlacklistAddress(serviceBoundary, flat, floor));
		
		List<UimBlockageDTO> uimBlockageList = imsProfileService.getUimBlockageList(flat, floor, serviceBoundary);
		addressRollout.setUimBlockage(uimBlockageList == null || uimBlockageList.isEmpty() ? null : uimBlockageList.toArray(new UimBlockageDTO[uimBlockageList.size()]));
		
		try {
			addressRollout = ltsHousingTypeDao.getLtsHousingType(addressRollout, serviceBoundary);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return addressRollout;
	}
	
	private void filterPonWhenPonVillaExist(AddrRolloutDTO rolloutResult) {
		
		if (!StringUtils.equals("Y", rolloutResult.getoPonVillaInd()) || rolloutResult.getTechnologyDTO() == null) {
			return;
		}
		List<TechnologyDTO> technologyList = new ArrayList<TechnologyDTO>();
		
		for (TechnologyDTO technology : rolloutResult.getTechnologyDTO()) {
			if (!StringUtils.equalsIgnoreCase(technology.getTechnology(), LtsBackendConstant.TECHNOLOGY_PON)) {
				technologyList.add(technology);
			}
		}
		
		rolloutResult.setTechnologyDTO(technologyList.toArray(new TechnologyDTO[technologyList.size()]));
	}
	
	private String getResourceShortageMsg(TechnologyDTO[] technologies) {
		
		if (ArrayUtils.isEmpty(technologies)) {
			return null;
		}
		StringBuilder msg = new StringBuilder(); 
		
		for (TechnologyDTO technology : technologies) {
			if (StringUtils.equals(technology.getIsDeadCase(), "Y")
					|| StringUtils.equals(technology.getIsResrcShort(), "Y")) {
				msg.append(" ");
				for (TechnologyType technologyType : TechnologyType.values()) {
					if (StringUtils.equals(technology.getTechnology(), technologyType.getDesc())){
						msg.append(technologyType.getName());
						break;
					}
				}
			}
		}
		if (msg.length() == 0) {
			return null;
		}
		return msg.toString();
	}
	
	private String getMaxTechnology(TechnologyDTO[] technologies) {
		
		if (ArrayUtils.isEmpty(technologies)) {
			return null;
		}
		TechnologyType[] maxOrderTechnologies = new TechnologyType[] {TechnologyType.PON, TechnologyType.VDSL, TechnologyType.ADSL};
		String maxTechnologyName = null;
		
		for (TechnologyType technologyType : maxOrderTechnologies) {
			for (TechnologyDTO technology : technologies) {
				if (StringUtils.equals(technologyType.getDesc(), technology.getTechnology())) {
					if (StringUtils.isBlank(maxTechnologyName)) {
						maxTechnologyName = technologyType.getName();
					}
					
					if (!StringUtils.equals("Y", technology.getIsResrcShort())
							&& !StringUtils.equals("Y", technology.getIsDeadCase())) {
						return technologyType.getName();	
					}
				}
			}
		}
		return maxTechnologyName;
	}
	
	private String getAvailableBandwidth(TechnologyDTO[] technologies) {
		
		StringBuilder bandwidthSb = new StringBuilder();	
		TechnologyType[] orderTechnologies = new TechnologyType[] {TechnologyType.ADSL, TechnologyType.VDSL, TechnologyType.PON};
		
		for (TechnologyType technologyType : orderTechnologies) {
			for (TechnologyDTO technology : technologies) {
				if (StringUtils.equals(technologyType.getDesc(), technology.getTechnology())) {
					
					bandwidthSb.append(technologyType.getName());
					
					if (technology.getBandwidthDTO().getBandwidth() != null
							&& ArrayUtils.isNotEmpty(technology.getBandwidthDTO().getBandwidth()) ) {
						bandwidthSb.append(" ( ");
						for (String bandwidth : technology.getBandwidthDTO().getBandwidth()) {
							bandwidthSb.append(bandwidth).append("M ");
						}
						bandwidthSb.append(")");
					}
					bandwidthSb.append("<br>");
				}
			}
		}
		return bandwidthSb.toString();
	}
	
	private String getMaxBandwidth(TechnologyDTO[] technologies) {
		
		TechnologyType[] maxOrderTechnologies = new TechnologyType[] {TechnologyType.VDSL, TechnologyType.ADSL};
		String maxBandwidth = null;
		for (TechnologyType technologyType : maxOrderTechnologies) {
			for (TechnologyDTO technology : technologies) {
				if (StringUtils.equals(technologyType.getDesc(), technology.getTechnology()) && technology.getBandwidthDTO() != null) {
					
					if (ArrayUtils.isNotEmpty(technology.getBandwidthDTO().getBandwidth())) {
						for (String bw : technology.getBandwidthDTO().getBandwidth()) {
							if (StringUtils.isEmpty(maxBandwidth)
									|| Double.parseDouble(bw) > Double.parseDouble(maxBandwidth)) {
								maxBandwidth = bw;
							}
						}
					}
				}
			}
		}
		return maxBandwidth;
	}
	
	public AddressDetailLtsService getAddressDetailLtsService() {
		return addressDetailLtsService;
	}

	public void setAddressDetailLtsService(
			AddressDetailLtsService addressDetailLtsService) {
		this.addressDetailLtsService = addressDetailLtsService;
	}

	public ImsProfileService getImsProfileService() {
		return imsProfileService;
	}

	public void setImsProfileService(ImsProfileService imsProfileService) {
		this.imsProfileService = imsProfileService;
	}

	public EyeProfileCountService getEyeProfileCountService() {
		return eyeProfileCountService;
	}

	public void setEyeProfileCountService(EyeProfileCountService eyeProfileCountService) {
		this.eyeProfileCountService = eyeProfileCountService;
	}
	
	public LtsHousingTypeDAO getLtsHousingTypeDao() {
		return ltsHousingTypeDao;
	}

	public void setLtsHousingTypeDao(LtsHousingTypeDAO ltsHousingTypeDao) {
		this.ltsHousingTypeDao = ltsHousingTypeDao;
	}
}
