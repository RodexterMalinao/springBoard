package com.bomwebportal.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.AllDocDAO;
import com.bomwebportal.dao.AllOrdDocAssgnDAO;
import com.bomwebportal.dao.AllOrdDocAssgnWriteDAO;
import com.bomwebportal.dao.AllOrdDocDAO;
import com.bomwebportal.dao.AllOrdDocWaiveDAO;
import com.bomwebportal.dto.AllDocDTO;
import com.bomwebportal.dto.AllDocDTO.DocType;
import com.bomwebportal.dto.AllDocDTO.Type;
import com.bomwebportal.dto.AllOrdDocAssgnDTO;
import com.bomwebportal.dto.AllOrdDocAssgnDTO.CollectedInd;
import com.bomwebportal.dto.AllOrdDocDTO;
import com.bomwebportal.dto.AllOrdDocTempDTO;
import com.bomwebportal.dto.AllOrdDocWaiveDTO;
import com.bomwebportal.dto.SignCaptureAllOrdDocDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

@Transactional(readOnly = true)
public class SupportDocServiceImpl implements SupportDocService {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private AllDocDAO allDocDAO;
	private AllOrdDocDAO allOrdDocDAO;
	private AllOrdDocAssgnDAO allOrdDocAssgnDAO;
	private AllOrdDocAssgnWriteDAO allOrdDocAssgnWriteDAO;
	private AllOrdDocWaiveDAO allOrdDocWaiveDAO;
	
	public AllDocDAO getAllDocDAO() {
		return allDocDAO;
	}

	public void setAllDocDAO(AllDocDAO allDocDAO) {
		this.allDocDAO = allDocDAO;
	}

	public AllOrdDocDAO getAllOrdDocDAO() {
		return allOrdDocDAO;
	}

	public void setAllOrdDocDAO(AllOrdDocDAO allOrdDocDAO) {
		this.allOrdDocDAO = allOrdDocDAO;
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

	public AllOrdDocWaiveDAO getAllOrdDocWaiveDAO() {
		return allOrdDocWaiveDAO;
	}

	public void setAllOrdDocWaiveDAO(AllOrdDocWaiveDAO allOrdDocWaiveDAO) {
		this.allOrdDocWaiveDAO = allOrdDocWaiveDAO;
	}

	@Deprecated
	public List<AllDocDTO> getAllDocDTOByType(String lob, Type type) {
		try {
			return this.allDocDAO.getAllDocDTOByType(lob, type);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	public List<AllDocDTO> getAllDocDTOByTypeAndAppDate(String lob, Type type, Date appDate) {
		try {
			return this.allDocDAO.getAllDocDTOByTypeAndAppDate(lob, type, appDate);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	public List<AllDocDTO> getAllDocDTOKnownByTypeAndAppDate(String lob, Type type, Date appDate) {
		try {
			return this.allDocDAO.getAllDocDTOKnownByTypeAndAppDate(lob, type, appDate);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<AllOrdDocTempDTO> getAllOrdDocDTOALL(String orderId) {
		try {
			return this.allOrdDocDAO.getAllOrdDocDTOALL(orderId);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<AllOrdDocAssgnDTO> getAllOrdDocAssgnDTOALL(String orderId) {
		try {
			return this.allOrdDocAssgnDAO.getAllOrdDocAssgnDTOALL(orderId);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<AllOrdDocAssgnDTO> getInUseAllOrdDocAssgnDTOALL(String orderId) {
		try {
			return this.allOrdDocAssgnDAO.getInUseAllOrdDocAssgnDTOALL(orderId);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	public List<AllOrdDocWaiveDTO> getAllOrdDocWaiveDTOALL(String lob, DocType docType) {
		try {
			return this.allOrdDocWaiveDAO.getAllOrdDocWaiveDTOALL(lob, ((docType == null) ? null : docType.toString()));
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<AllOrdDocWaiveDTO> getAllOrdDocWaiveDTOALL(String lob, String docType) {
		try {
			return this.allOrdDocWaiveDAO.getAllOrdDocWaiveDTOALL(lob, docType);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void insertAllOrdDocAssgnDTOALL(String orderId, List<AllOrdDocAssgnDTO> list) {
		if (this.isEmpty(list)) {
			return;
		}
		
		try {
			List<AllOrdDocAssgnDTO> currentAllOrdDocAssgnDTOs = this.getInUseAllOrdDocAssgnDTOALL(orderId);
			for (AllOrdDocAssgnDTO inUseDTO : currentAllOrdDocAssgnDTOs) {
				boolean markDel = true;
				for (AllOrdDocAssgnDTO dto : list) {
					if (inUseDTO.getDocTypeMob().equalsIgnoreCase(dto.getDocTypeMob())) {
						markDel = false;
					}
				}
				if (markDel) {
					AllOrdDocAssgnDTO currentDto = this.allOrdDocAssgnDAO.getInUseAllOrdDocAssgnDTO(inUseDTO.getOrderId(), inUseDTO.getDocTypeMob());
					allOrdDocAssgnWriteDAO.updateMarkDelInd(currentDto.getRowId(), AllOrdDocAssgnDTO.MarkDelInd.Y, inUseDTO.getLastUpdBy());
				}
			}
			
			for (AllOrdDocAssgnDTO dto : list) {
				AllOrdDocAssgnDTO currentDto = this.allOrdDocAssgnDAO.getInUseAllOrdDocAssgnDTO(dto.getOrderId(), dto.getDocTypeMob());
				if (currentDto == null) {
					this.allOrdDocAssgnWriteDAO.insertAllOrdDocAssgnDTO(dto);
				} else {
					dto.setRowId(currentDto.getRowId());
					// not allow from Y > N without waive reason
					if (CollectedInd.Y.equals(currentDto.getCollectedInd()) && CollectedInd.N.equals(dto.getCollectedInd()) && StringUtils.isBlank(dto.getWaiveReason())) {
						dto.setCollectedInd(CollectedInd.Y);
						dto.setWaiveReason(null);
						dto.setWaivedBy(null);
					}
					this.allOrdDocAssgnWriteDAO.updateAllOrdDocAssgnDTO(dto);
				}
			}
			
			
			
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void insertImsAllOrdDocAssgnDTOALL(List<AllOrdDocAssgnDTO> list) {
		try {
			for (AllOrdDocAssgnDTO dto : list) {
				AllOrdDocAssgnDTO currentDto = this.allOrdDocAssgnDAO.getImsInUseAllOrdDocAssgnDTO(dto.getOrderId(), dto.getDocType());
				if (currentDto == null) {
					this.allOrdDocAssgnWriteDAO.insertAllOrdDocAssgnDTO(dto);
				} else {
					dto.setRowId(currentDto.getRowId());
					// not allow collectedInd from Y > N without waive reason
//					if (CollectedInd.Y.equals(currentDto.getCollectedInd()) && CollectedInd.N.equals(dto.getCollectedInd()) && StringUtils.isBlank(dto.getWaiveReason())) {
//						dto.setCollectedInd(CollectedInd.Y);
//						dto.setWaiveReason(null);
//						dto.setWaivedBy(null);
//					}
					this.allOrdDocAssgnWriteDAO.updateAllOrdDocAssgnDTO(dto);
				}
			}
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void ims_mark_all_delete(String order_id) {
		try {

			List<AllOrdDocAssgnDTO> currentDto = this.allOrdDocAssgnDAO.getAllOrdDocAssgnDTOALL(order_id);
			if(currentDto!=null){
				this.allOrdDocAssgnWriteDAO.ims_mark_all_delete_dao(order_id);
				System.out.println("ims_mark_all_delete @ SupportDocServiceImpl is called");
			}
		} catch (DAOException de) {
			logger.error(de);
			//throw new AppRuntimeException(de);
		}
	}

	public boolean isSupportDocRequired(String docType, String basketID, String[] vasItemList, String channel) {
		try {
			return allDocDAO.isSupportDocRequired( docType, basketID, vasItemList, channel);
		} catch (DAOException de) {
			logger.error(de);
			return false;
		}
	}

	public List<String> getMissingDocReasonList() {
		try {
			List<String> reasonList = this.allDocDAO.getMissingDocReasonList();
			if(reasonList != null)
				return reasonList;
		} catch (DAOException de) {
			logger.error(de);
			//throw new AppRuntimeException(de);
		}
		return null;
	}
	
	/**
	 * 
	 * @param orderId
	 * @param appDate
	 */
	public List<SignCaptureAllOrdDocDTO> retrieveAllOrdDocDTOList(String orderId, Date appDate, boolean isTemp) {
		
		if (isTemp) {
			return getSignCaptureAllOrdDocDTO(orderId, appDate);
		}
		
		List<SignCaptureAllOrdDocDTO> signCaptureAllOrdDocDTOList = new ArrayList<SignCaptureAllOrdDocDTO>();
		
		List<AllOrdDocTempDTO> allOrdDocDTOList = getAllOrdDocDTOALL(orderId);
		List<AllDocDTO> allDocDTOList = getAllDocDTOByTypeAndAppDate("MOB", Type.O, (appDate == null)? new Date() : appDate);
		
		for (AllOrdDocTempDTO allOrdDocDTO : allOrdDocDTOList) {
			
			SignCaptureAllOrdDocDTO signCaptureAllOrdDocDTO = new SignCaptureAllOrdDocDTO();
			signCaptureAllOrdDocDTO.setOrderId(orderId);
			signCaptureAllOrdDocDTO.setUrl(allOrdDocDTO.getFilePathName());
			signCaptureAllOrdDocDTO.setDocType(allOrdDocDTO.getDocType());
			
			for (AllDocDTO allDocDTO : allDocDTOList) {
				if (StringUtils.equalsIgnoreCase(allOrdDocDTO.getDocType(), allDocDTO.getDocTypeMob())) {
					signCaptureAllOrdDocDTO.setDocName(allDocDTO.getDocName());
					break;
				}
			}
			
			signCaptureAllOrdDocDTOList.add(signCaptureAllOrdDocDTO);
		}
		
		return signCaptureAllOrdDocDTOList;
	}
	
	public List<SignCaptureAllOrdDocDTO> getSignCaptureAllOrdDocDTO(String orderId, Date appDate) {
		try {
			return allOrdDocDAO.getSignCaptureAllOrdDocDTO(orderId, appDate);
		} catch (DAOException e) {
			logger.error("[" + orderId + "]" + e.getStackTrace());
		}
		return null;
	}
	
	public void deleteAllOrderDocTempDTO(String orderId) {
		try {
			allOrdDocDAO.deleteAllOrderDocTempDTO(orderId);
		} catch (DAOException e) {
			logger.error("[" + orderId + "]" + e.getStackTrace());
		}
	}
	
	public void insertAllOrderDocTempDTO(AllOrdDocDTO dto) {
		try {
			allOrdDocDAO.insertAllOrderDocTempDTO(dto);
		} catch (DAOException e) {
			logger.error("[" + dto.getOrderId() + "]" + e.getStackTrace());
		}
	}
	
}