package com.bomwebportal.lts.service.bom;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.bom.ImsServiceProfileAccessDAO;
import com.bomwebportal.lts.dto.profile.AddressDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.PendingOrdStatusDetailDTO;
import com.bomwebportal.lts.dto.profile.TenureDTO;
import com.bomwebportal.lts.dto.srvAccess.AddrRolloutDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.UimBlockageDTO;

public class ImsProfileServiceImpl implements ImsProfileService {

    private final Log logger = LogFactory.getLog(getClass());
	
	private ImsServiceProfileAccessDAO imsServiceProfileAccessDAO;
	
	private void setHDReady(FsaServiceDetailOutputDTO fsaServiceDetailOutput) {
		final String HD_READY_SRV_TYPE = "(HDR)";
		if (StringUtils.contains(fsaServiceDetailOutput.getExistingSrv(), HD_READY_SRV_TYPE)) {
			fsaServiceDetailOutput.setHdReady(true);
			fsaServiceDetailOutput.setExistingSrv(StringUtils.replace(fsaServiceDetailOutput.getExistingSrv(), HD_READY_SRV_TYPE, ""));
		}
		if (StringUtils.contains(fsaServiceDetailOutput.getSrvType(), HD_READY_SRV_TYPE)) {
			fsaServiceDetailOutput.setHdReady(true);
			fsaServiceDetailOutput.setSrvType(StringUtils.replace(fsaServiceDetailOutput.getSrvType(), HD_READY_SRV_TYPE, ""));
		}
	}

	public FsaServiceDetailOutputDTO[] getServiceByCustomer(String pCustNum) {
		
		try {
			FsaServiceDetailOutputDTO[] fsas = this.imsServiceProfileAccessDAO.getServiceByCustomer(pCustNum);
			
			for (int i=0; fsas!=null && i<fsas.length; ++i) {
				fsas[i].setSrvType(this.imsServiceProfileAccessDAO.getServiceMiscByFSA(fsas[i].getFsa()));
				setHDReady(fsas[i]);
			}
			return fsas;
		} catch (DAOException de) {
			logger.error("Fail in ImsServiceProfileAccessDAO.getServiceByCustomer()", de);
			throw new AppRuntimeException(de);
		}
	}

	public FsaServiceDetailOutputDTO getServiceDetailByFSA(String pFsa) {
		
		try {
			FsaServiceDetailOutputDTO fsa = this.imsServiceProfileAccessDAO.getServiceDetailByFSA(pFsa);
			
			if (fsa != null) {
				fsa.setSrvType(this.imsServiceProfileAccessDAO.getServiceMiscByFSA(fsa.getFsa()));	
				setHDReady(fsa);
			}
			return fsa;
		} catch (DAOException de) {
			logger.error("Fail in ImsServiceProfileAccessDAO.getServiceDetailByFSA()", de);
			throw new AppRuntimeException(de);
		}
	}

	public FsaServiceDetailOutputDTO[] getServiceByDocument(String pDocType, String pDocNum) {
		
		try {
			FsaServiceDetailOutputDTO[] fsas = this.imsServiceProfileAccessDAO.getServiceByDocument(pDocType, pDocNum);
			
			for (int i=0; fsas!=null && i<fsas.length; ++i) {
				fsas[i].setSrvType(this.imsServiceProfileAccessDAO.getServiceMiscByFSA(fsas[i].getFsa()));
				setHDReady(fsas[i]);
			}
			return fsas;
		} catch (DAOException de) {
			logger.error("Fail in ImsServiceProfileAccessDAO.getServiceByDocument()", de);
			throw new AppRuntimeException(de);
		}
	}

	public FsaServiceDetailOutputDTO[] getServiceByLogin(String pLogin, String pDomainType) {
		
		try {
			FsaServiceDetailOutputDTO[] fsas = this.imsServiceProfileAccessDAO.getServiceByLogin(pLogin, pDomainType);
			
			for (int i=0; fsas!=null && i<fsas.length; ++i) {
				fsas[i].setSrvType(this.imsServiceProfileAccessDAO.getServiceMiscByFSA(fsas[i].getFsa()));
				setHDReady(fsas[i]);
			}
			return fsas;
		} catch (DAOException de) {
			logger.error("Fail in ImsServiceProfileAccessDAO.getServiceByLogin()", de);
			throw new AppRuntimeException(de);
		}
	}

	public AddressDetailProfileLtsDTO getAddressDTLByAddressID(String pAddress) {
		
		try {
			return this.imsServiceProfileAccessDAO.getAddressDTLByAddressID(pAddress);
		} catch (DAOException de) {
			logger.error("Fail in ImsServiceProfileAccessDAO.getAddressDTLByAddressID()", de);
			throw new AppRuntimeException(de);
		}
	}

	public TenureDTO[] getImsTenureByAddress(String pFlat, String pFloor, String pSb) {
		
		try {
			return this.imsServiceProfileAccessDAO.getImsTenureByAddress(pFlat, pFloor, pSb);
		} catch (DAOException de) {
			logger.error("Fail in ImsServiceProfileAccessDAO.getServiceByAddress()", de);
			throw new AppRuntimeException(de);
		}
	}

	public AddrRolloutDTO getAddrRolloutDtl(String pIFlat, String pIFloor, String pIServiceBoundary) {
		
		try {
			return this.imsServiceProfileAccessDAO.getAddrRolloutDtl(pIFlat, pIFloor, pIServiceBoundary);
		} catch (DAOException de) {
			logger.error("Fail in ImsServiceProfileAccessDAO.getAddrRolloutDtl()", de);
			return null;
		}
	}

	public CustomerDetailProfileLtsDTO getImsCustomerDetailByFsa(String pFsa) {
		
		try {
			return this.imsServiceProfileAccessDAO.getImsCustomerDetailByFsa(pFsa);
		} catch (DAOException de) {
			logger.error("Fail in ImsServiceProfileAccessDAO.getImsCustomerDetailByFsa()", de);
			return null;
		}
	}

	public PendingOrdStatusDetailDTO getPendingOrder(String pFsa) {
		
		try {
			return this.imsServiceProfileAccessDAO.getPendingOrder(pFsa);
		} catch (DAOException de) {
			logger.error("Fail in ImsServiceProfileAccessDAO.getPendingOrderByFsa()", de); 
			return null;
		}
	}

	public boolean isStandaloneTv(String pFsa) {
		try {
			return StringUtils.equals("Y", this.imsServiceProfileAccessDAO.getIsTvServiceByFSA(pFsa));
		}
		catch (DAOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getCause());
		}
	}
	
	public List<UimBlockageDTO> getUimBlockageList(String flat, String floor, String sb) {
		try{
			return imsServiceProfileAccessDAO.getUimBlockageList(flat, floor, sb);
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getCause());
		}
	}
	
	public String[] getImsCustDocByParentCust(String pParentCustNum){
		try{
			return imsServiceProfileAccessDAO.getImsCustDocByParentCust(pParentCustNum);
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getCause());
		}
	}
	
	
	public String checkProductParmByFsa(String fsa, String parmName)  {
		try{
			return imsServiceProfileAccessDAO.checkProductParmByFsa(fsa, parmName);
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getCause());
		}
	}
	
	public ImsServiceProfileAccessDAO getImsServiceProfileAccessDAO() {
		return this.imsServiceProfileAccessDAO;
	}

	public void setImsServiceProfileAccessDAO(ImsServiceProfileAccessDAO pImsServiceProfileAccessDAO) {
		this.imsServiceProfileAccessDAO = pImsServiceProfileAccessDAO;
	}
}
