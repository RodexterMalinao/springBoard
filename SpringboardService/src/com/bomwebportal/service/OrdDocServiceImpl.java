package com.bomwebportal.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.bomwebportal.dao.AllOrdDocAssgnDAO;
import com.bomwebportal.dao.AllOrdDocAssgnWriteDAO;
import com.bomwebportal.dao.AllOrdDocWriteDAO;
import com.bomwebportal.dao.BaseOrderDAO;
import com.bomwebportal.dao.OrdDocDAO;
import com.bomwebportal.dto.AllDocDTO.DocType;
import com.bomwebportal.dto.AllOrdDocAssgnDTO;
import com.bomwebportal.dto.AllOrdDocAssgnDTO.CollectedInd;
import com.bomwebportal.dto.AllOrdDocDTO;
import com.bomwebportal.dto.DocTypeDTO;
import com.bomwebportal.dto.OrdDocAssgnDTO;
import com.bomwebportal.dto.OrdDocDTO;
import com.bomwebportal.dto.OrdDocDTO.VerifyInd;
import com.bomwebportal.exception.AppRuntimeException;

@Transactional(readOnly = true)
public class OrdDocServiceImpl implements OrdDocService {

	private OrdDocDAO ordDocDAO;
	private AllOrdDocWriteDAO allOrdDocWriteDAO;
	private BaseOrderDAO orderDAO;
	private AllOrdDocAssgnDAO allOrdDocAssgnDAO;
	private AllOrdDocAssgnWriteDAO allOrdDocAssgnWriteDAO;

	public void setOrdDocDAO(OrdDocDAO ordDocDAO) {
		this.ordDocDAO = ordDocDAO;
	}

	public AllOrdDocWriteDAO getAllOrdDocWriteDAO() {
		return allOrdDocWriteDAO;
	}


	public void setAllOrdDocWriteDAO(AllOrdDocWriteDAO allOrdDocWriteDAO) {
		this.allOrdDocWriteDAO = allOrdDocWriteDAO;
	}
	
	
	public BaseOrderDAO getOrderDAO() {
		return orderDAO;
	}

	public void setOrderDAO(BaseOrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	public AllOrdDocAssgnDAO getAllOrdDocAssgnDAO() {
		return allOrdDocAssgnDAO;
	}

	public void setAllOrdDocAssgnDAO(AllOrdDocAssgnDAO allOrdDocAssgnDAO) {
		this.allOrdDocAssgnDAO = allOrdDocAssgnDAO;
	}

	public AllOrdDocAssgnWriteDAO getAllOrdDocAssgnWriteDAO() {
		return allOrdDocAssgnWriteDAO;
	}

	public void setAllOrdDocAssgnWriteDAO(
			AllOrdDocAssgnWriteDAO allOrdDocAssgnWriteDAO) {
		this.allOrdDocAssgnWriteDAO = allOrdDocAssgnWriteDAO;
	}

	public DocTypeDTO getDocType(String docType, String lob) {
		try {
			return ordDocDAO.getDocType(docType, lob);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}

	public DocTypeDTO getDocTypeForOrder(String docType, String orderId) {
		try {
			return ordDocDAO.getDocTypeForOrder(docType, orderId);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}
	
	public List<OrdDocDTO> getOrdDoc(String orderId) {
		
		try {
			return ordDocDAO.getOrdDoc(orderId);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}


	public List<OrdDocDTO> getImsOrdDoc(String orderId) {
		
		try {
			return ordDocDAO.getImsOrdDoc(orderId);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}

	public List<OrdDocAssgnDTO> getRequiredDoc(String orderId) {
		try {
			return ordDocDAO.getRequiredDoc(orderId);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}

	public int getLastSeqNum(String orderId, String docType) {
		try {
			return ordDocDAO.getLastSeqNum(orderId, docType);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}
	
//steven 20121025
	public int getImsNumberOfCollectedDoc(String orderId, String docType) {
		try {
			return ordDocDAO.getImsNumberOfCollectedDoc(orderId, docType);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}


	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void insertOrdDoc(OrdDocDTO ordDoc) {
		try {
			ordDocDAO.insertOrdDoc(ordDoc);
			ordDocDAO.markOrdDocCollected(ordDoc.getOrderId(), ordDoc.getDocType(), "Y", ordDoc.getCaptureBy());
			orderDAO.updateDmsInd(ordDoc.getOrderId(), "N", ordDoc.getCaptureBy());
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
		
	}

	// add by Joyce 20120816
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void insertAllOrderDocDTO(AllOrdDocDTO dto) {
		try {
			allOrdDocWriteDAO.insertAllOrderDocDTO(dto);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
		
	}

	// add by Steven 20121024
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateAllOrderDocDTOOutdatedInd(String Order_id, String doc_type) {
		try {
			allOrdDocWriteDAO.updateAllOrdDocAssgnDTOOutdatedInd( Order_id,  doc_type);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
		
	}

	public List<OrdDocDTO> getOrdDoc(String orderId, String docType) {
		try {
			return this.ordDocDAO.getOrdDoc(orderId, docType);
		} catch (Exception e) { 
			throw new AppRuntimeException(e);
		}
	}

	public OrdDocDTO getOrdDoc(String orderId, String docType, int seqNum) {
		try {
			return this.ordDocDAO.getOrdDoc(orderId, docType, seqNum);
		} catch (Exception e) { 
			throw new AppRuntimeException(e);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateVerifyInd(String orderId, String docType, int seqNum, VerifyInd verifyInd, String username) {
		try {
			this.insertAuditLog(orderId, docType, seqNum, AuditLogActionCd.AD03, username);
			int count = this.ordDocDAO.updateVerifyInd(orderId, docType, seqNum, verifyInd, username);
			// update collected_ind = 'N' when
			// 1) doc_type + all seq_num with verify_ind = N
			List<OrdDocDTO> list = this.getOrdDoc(orderId, docType);
			if (!CollectionUtils.isEmpty(list)) {
				boolean allVerifiedDocRejected = true;
				for (OrdDocDTO dto : list) {
					if (!VerifyInd.N.equals(dto.getVerifyInd())) {
						// can be verified_ind = 'Y' or null
						allVerifiedDocRejected = false;
						break;
					}
				}
				// only handle DocType defined in com.bomwebportal.dto.AllDocDTO
				AllOrdDocAssgnDTO dto = this.allOrdDocAssgnDAO.getInUseAllOrdDocAssgnDTO(orderId, docType/*DocType.valueOf(docType)*/);
				if (dto != null) {
					dto.setCollectedInd(allVerifiedDocRejected ? CollectedInd.N : CollectedInd.Y);
					dto.setLastUpdBy(username);
					dto.setLastUpdDate(new Date());
					this.allOrdDocAssgnWriteDAO.updateAllOrdDocAssgnDTO(dto);
				}
			}
			return count;
		} catch (Exception e) { 
			e.printStackTrace();
			throw new AppRuntimeException(e);
		}
	}

	public int insertAuditLog(String orderId, String docType, Integer seqNum, AuditLogActionCd actionCd, String username) {
		try {
			return this.ordDocDAO.insertAuditLog(orderId, docType, seqNum, actionCd, username);
		} catch (Exception e) { 
			throw new AppRuntimeException(e);
		}
	}


}
