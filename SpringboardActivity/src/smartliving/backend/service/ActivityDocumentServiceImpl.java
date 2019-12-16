package smartliving.backend.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import smartliving.backend.dao.ActivityDocumentDAO;
import smartliving.backend.dto.DocWaiveReasonDTO;
import smartliving.backend.dto.OrdDocSLVDTO;
import smartliving.backend.dto.OrderDocDTO;

import com.activity.dto.DocumentDTO;
import com.activity.service.UploadActvDocumentService;
import com.bomwebportal.dto.OrdDocAssgnDTO;
import com.bomwebportal.dto.OrdDocDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.pccw.util.spring.SpringApplicationContext;

public class ActivityDocumentServiceImpl implements ActivityDocumentService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	protected ActivityDocumentDAO activityDocumentDAO;
	
	public List<OrdDocAssgnDTO> getRequiredDoc(String pActvIdTaskSeq, String pSrvType) {
		try {  
			return activityDocumentDAO.getRequiredDoc(pActvIdTaskSeq, pSrvType); 
		} catch (DAOException de) {
			//logger.error(de);
			return null;
		}
	}
	
	@Override
	public List<OrdDocAssgnDTO> getRequiredDocByActv(String pActvIdTaskSeq) {
		try {  
			return activityDocumentDAO.getRequiredDocByActv(pActvIdTaskSeq); 
		} catch (DAOException de) {
			//logger.error(de);
			return null;
		}
	}
	
	@Override
	public List<OrderDocDTO> getRequiredDocByActv(String pActvIdTaskSeq, Map<String, String> pMdoKeyValueMap) {
		try {  
			return activityDocumentDAO.getRequiredDocByActv(pActvIdTaskSeq, pMdoKeyValueMap); 
		} catch (DAOException de) {
			//logger.error(de);
			return null;
		}
	}
	
	
	@Override
	public int getLastSeqNum(String orderId, String docType){
		try {
			return activityDocumentDAO.getLastSeqNum(orderId, docType);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}
	
	@Override
	public void insertOrdDoc(OrdDocDTO pOrdDoc, String pDocDesc){ 
		try {
			activityDocumentDAO.insertOrdDoc(pOrdDoc, pDocDesc);
			activityDocumentDAO.markOrdDocCollected(pOrdDoc.getOrderId(), pOrdDoc.getDocType(), "Y", pOrdDoc.getCaptureBy());
			//orderDAO.updateDmsInd(pOrdDoc.getOrderId(), "N", pOrdDoc.getCaptureBy());
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}

	public void updateWaiveReason(String pActvId, String pTaskSeq, DocumentDTO pDoc, String pUser) { 
		try {			
			activityDocumentDAO.updateWaiveReason(pActvId+pTaskSeq, pDoc.getDocType(), pDoc.getWaiveReason(), pUser);
			//orderDAO.updateDmsInd(pOrdDoc.getOrderId(), "N", pOrdDoc.getCaptureBy());
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}

	@Override
	public List<OrdDocSLVDTO> getCapturedOrdDoc(String pActvId, String pTaskId) { 
		return getCapturedOrdDoc(SpringApplicationContext.getBean(UploadActvDocumentService.class).getOrdAllDocOrderId(pActvId, pTaskId));
	}
	
	
	@Override
	public List<OrdDocSLVDTO> getCapturedOrdDoc(String pOrderId) { 
		try {
			return activityDocumentDAO.getCapturedOrdDoc(pOrderId);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}
	
	@Override
	public List<DocWaiveReasonDTO> getWaiveReasonList(String docType) {
		try {
			return this.activityDocumentDAO.getWaiveReasonList(docType);
		} catch (DAOException de) {
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public OrderDocDTO getOrderDoc(String pDocType){
		try {
			OrderDocDTO orderDoc = activityDocumentDAO.getOrderDoc(pDocType);
			if (orderDoc != null) {
				List<DocWaiveReasonDTO> waiveReasonList = this.getWaiveReasonList(pDocType);
				orderDoc.setWaiveReasonList(waiveReasonList);
			}	
			return orderDoc;
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}
	
	@Override
	public OrderDocDTO getOrderDocByMdoKey(String pDocType, Map<String, String> pMdoKeyValueMap){
		try {
			OrderDocDTO orderDoc = activityDocumentDAO.getOrderDocByMdoKey(pDocType, pMdoKeyValueMap);
			if (orderDoc != null) {
				List<DocWaiveReasonDTO> waiveReasonList = this.getWaiveReasonList(pDocType);
				orderDoc.setWaiveReasonList(waiveReasonList);
			}	
			return orderDoc;
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}
	
	public ActivityDocumentDAO getActivityDocumentDAO() {
		return this.activityDocumentDAO;
	}

	public void setActivityDocumentDAO(ActivityDocumentDAO pActivityDocumentDAO) {
		this.activityDocumentDAO = pActivityDocumentDAO;
	}

	@Override
	public String[] saveAutoUploadSalesMemo(byte[] pMemo, String pFileName, String pServerFilePath,String pDocType) {
		// path = actvId+taskSeq
		String memoPath = activityDocumentDAO.getMemoPath(pFileName.substring(0, 13));
		int seq = SpringApplicationContext.getBean(ActivityDocumentService.class).getLastSeqNum(memoPath, pDocType)+1;
		
		try {
			if (memoPath == null) {
				return null;
			}
			FileOutputStream fos;
			File file = new File(pServerFilePath + memoPath + File.separator + insertSeqInFileName(pFileName, seq));

			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
				fos = new FileOutputStream(file);
				fos.write(pMemo);
				fos.close();
			} else
			{//rewrite
				fos = new FileOutputStream(file,false);
				fos.write(pMemo);
				fos.close();
			}
				
		} catch (Exception e) {
			return null;
		}
		
		return new String[]{memoPath,insertSeqInFileName(pFileName, seq),String.valueOf(seq)};
	}
	
	private String insertSeqInFileName(String fileName,int seq)
	{
		String[] temp = fileName.split("\\.");
		return temp[0]+"_"+String.valueOf(seq)+'.'+temp[1];
	}

	@Override
	public List<OrderDocDTO> getRequiredSupportDoc(String pSrvType,String pChannel) {
		try {  
			return activityDocumentDAO.getRequiredSupportDoc(pSrvType,pChannel); 
		} catch (DAOException de) {
			//logger.error(de);
			return null;
		}
	}
	
	@Override
	public List<OrderDocDTO> getRequiredSupportDocByActvTaskType(String pActvType, String pTaskType, String pChannelId) {
		try {  
			return activityDocumentDAO.getRequiredSupportDocByActvTaskType(pActvType, pTaskType, pChannelId); 
		} catch (DAOException de) {
			logger.error("Exception caught in getRequiredSupportDocByActvTaskType(): " + de);
			return null;
		}
	}

}
