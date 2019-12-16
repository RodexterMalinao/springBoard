package smartliving.backend.service;

import java.util.List;
import java.util.Map;

import smartliving.backend.dto.DocWaiveReasonDTO;
import smartliving.backend.dto.OrdDocSLVDTO;
import smartliving.backend.dto.OrderDocDTO;

import com.activity.dto.DocumentDTO;
import com.bomwebportal.dto.OrdDocAssgnDTO;
import com.bomwebportal.dto.OrdDocDTO;

//import com.smartliving.dto.ProductLineDTO;

public interface ActivityDocumentService {

	List<OrdDocAssgnDTO> getRequiredDoc(String pActvIdTaskSeq, String pSrvType);
	
	List<OrderDocDTO> getRequiredSupportDoc(String pSrvType, String pChannel);
	List<OrderDocDTO> getRequiredSupportDocByActvTaskType(String pActvType, String pTaskType, String pChannelId);	
	
	List<OrdDocAssgnDTO> getRequiredDocByActv(String pActvIdTaskSeq);
		
	int getLastSeqNum(String orderId, String docType);
	
	void insertOrdDoc(OrdDocDTO pDto, String pDocDesc);
 
	void updateWaiveReason(String pActvId, String pTaskSeq, DocumentDTO pDoc, String pUser);

	List<OrdDocSLVDTO> getCapturedOrdDoc(String pActivityId, String pTaskId);

	List<OrdDocSLVDTO> getCapturedOrdDoc(String pOrderId);
	
	List<DocWaiveReasonDTO> getWaiveReasonList(String pDocType);
	
	OrderDocDTO getOrderDoc(String pDocType);
	
	String[] saveAutoUploadSalesMemo(byte[] memo,String fileName, String serverFilePath,String docType);

	OrderDocDTO getOrderDocByMdoKey(String pDocType, Map<String, String> pMdoKeyValueMap);

	List<OrderDocDTO> getRequiredDocByActv(String pActvIdTaskSeq, Map<String, String> pMdoKeyValueMap);

	
}
