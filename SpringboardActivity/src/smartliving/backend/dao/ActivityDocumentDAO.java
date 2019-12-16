package smartliving.backend.dao;

import java.util.List;
import java.util.Map;

import smartliving.backend.dto.DocWaiveReasonDTO;
import smartliving.backend.dto.OrdDocSLVDTO;
import smartliving.backend.dto.OrderDocDTO;

import com.bomwebportal.dto.OrdDocAssgnDTO;
import com.bomwebportal.dto.OrdDocDTO;
import com.bomwebportal.exception.DAOException;

public interface ActivityDocumentDAO {

	public abstract List<OrdDocAssgnDTO> getRequiredDoc(String pActvIdTaskSeq, String pSrvType) throws DAOException;
	
	public abstract List<OrderDocDTO> getRequiredSupportDoc(String pSrvType,String pChannel) throws DAOException;
	public abstract List<OrderDocDTO> getRequiredSupportDocByActvTaskType(String pActvType, String pTaskType, String pChannelId) throws DAOException;
	
	public abstract List<OrdDocAssgnDTO> getRequiredDocByActv(String pSrvType) throws DAOException;
	
	public int getLastSeqNum(String orderId, String docType) throws DAOException;
	
	public void insertOrdDoc(OrdDocDTO pDto, String pDocDesc) throws DAOException;
	
	public void updateWaiveReason(String pOrderId, String pDocType, String pWaiveReason, String pUsername) throws DAOException;
	
	public void markOrdDocCollected(String pOrderId, String pDocType, String pCollectedInd, String pUsername) throws DAOException;
	
	public List<OrdDocSLVDTO> getCapturedOrdDoc(String pOrderId) throws DAOException;
	
	public List<DocWaiveReasonDTO> getWaiveReasonList(String docType) throws DAOException;
	
	public OrderDocDTO getOrderDoc(String pDocType) throws DAOException;
	
	public OrderDocDTO getOrderDocByMdoKey(String pDocType, Map<String, String> pMdoKeyValueMap) throws DAOException;	
	
	public String getMemoPath(String salesMemoNum);

	List<OrderDocDTO> getRequiredDocByActv(String pActvIdTaskSeq, Map<String, String> pMdoKeyValueMap) throws DAOException;
	

}