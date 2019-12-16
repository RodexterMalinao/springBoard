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
import com.bomwebportal.lts.dao.AddressRolloutDAO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.TenureDTO;
import com.bomwebportal.lts.service.bom.ImsProfileService;

public class LtsAddressRolloutServiceImpl implements LtsAddressRolloutService{

	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsProfileService imsProfileService;
	private AddressRolloutDAO addressRolloutDAO;
	
	public ImsProfileService getImsProfileService() {
		return imsProfileService;
	}

	public void setImsProfileService(ImsProfileService imsProfileService) {
		this.imsProfileService = imsProfileService;
	}

	public AddressRolloutDAO getAddressRolloutDAO() {
		return addressRolloutDAO;
	}

	public void setAddressRolloutDAO(AddressRolloutDAO addressRolloutDAO) {
		this.addressRolloutDAO = addressRolloutDAO;
	}

	public List<String> getTeamByPremierAddr(String premierAddr, String activationFee) {
		try {
			return addressRolloutDAO.getTeamByPremierAddr(premierAddr, activationFee);
		}
		catch (DAOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getMessage());
		}
	}
	
	public boolean isDiffCustFsaExist(String[] fsas, String docType, String docNum) {
		
		if (ArrayUtils.isEmpty(fsas)) {
			return false;
		}
		CustomerDetailProfileLtsDTO custDtl = null;
		
		for (int i=0; i < fsas.length; ++i) {
			custDtl = this.imsProfileService.getImsCustomerDetailByFsa(fsas[i]);
			
			if (custDtl == null) {
				continue;
			}
			if (!StringUtils.equals(custDtl.getDocNum(), docNum) || !StringUtils.equals(custDtl.getDocType(), docType)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isDiffCustFsaExist(String serviceBoundary, String flat, String floor, String docType, String docNum) {
		
		try {
			TenureDTO[] imsTenures = imsProfileService.getImsTenureByAddress(flat, floor, serviceBoundary);
			
			if (ArrayUtils.isEmpty(imsTenures)) {
				imsTenures = imsProfileService.getImsTenureByAddress(floor + flat, "", serviceBoundary);
			}
			if (ArrayUtils.isEmpty(imsTenures)) {
				return false;
			}
			List<String> fsaList = new ArrayList<String>();
			
			for (int i=0; i < imsTenures.length; ++i) {
				if (StringUtils.isNotEmpty(imsTenures[i].getServiceId())) {
					fsaList.add(imsTenures[i].getServiceId());
				}
			}
			return this.isDiffCustFsaExist(fsaList.toArray(new String[0]), docType, docNum);
		} catch (Exception e) {
			logger.error("isDiffCustFsaExist" + ExceptionUtils.getFullStackTrace(e));
			return false;
		}
	}
}
