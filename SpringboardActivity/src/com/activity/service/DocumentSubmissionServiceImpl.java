package com.activity.service;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.activity.dto.DocumentDTO;
import com.activity.dto.DocumentDetailDTO;
import com.activity.util.ActivityIdFactory;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.service.ServiceActionBase;

public class DocumentSubmissionServiceImpl implements DocumentSubmissionService  {

	private ServiceActionBase documentService = null;
	private ServiceActionBase documentDetailService = null;
	private ActivityIdFactory activityIdFactory = null;
	
	
	public DocumentDTO createDocument(String pId, String pDocType, String pUser) {
		
		DocumentDTO doc = new DocumentDTO();
		doc.setDocType(pDocType);
		this.documentService.doSave(doc, pUser, pId);
		return doc;
	}

	@Transactional
	public void submitDocument(String pId, DocumentDTO pDoc, String pUser) {
		
		DocumentDetailDTO[] docDtls = pDoc.getDocDetails();
		
		if (ArrayUtils.isEmpty(docDtls)) {
			return;
		}
		int maxDocSeq = this.activityIdFactory.getMaxDocumentSeq(pId, pDoc.getDocType());
		
		for (int i=0; i<docDtls.length; ++i) {
			if (StringUtils.isEmpty(docDtls[i].getSeqNum())) {
				maxDocSeq++;
				docDtls[i].setSeqNum(String.valueOf(maxDocSeq));
			}
			this.documentDetailService.doSave(docDtls[i], pUser, pId);
		}
		if (!StringUtils.equals("Y", pDoc.getCollectedInd())) {
			pDoc.setCollectedInd("Y");
			pDoc.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
			this.documentService.doSave(pDoc, pUser, pId);
		}
	}

	@Transactional
	public void clearDocument(String pId, DocumentDTO pDoc, String pUser) {
		
		DocumentDetailDTO[] docDtls = pDoc.getDocDetails();
		
		for (int i=0; docDtls!=null && i<docDtls.length; ++i) {
			docDtls[i].setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
			this.documentDetailService.doSave(docDtls[i], pUser, pId);
		}
		pDoc.setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
		this.documentService.doSave(pDoc, pUser, pId);
	}

	public ServiceActionBase getDocumentService() {
		return documentService;
	}

	public void setDocumentService(ServiceActionBase documentService) {
		this.documentService = documentService;
	}

	public ServiceActionBase getDocumentDetailService() {
		return documentDetailService;
	}

	public void setDocumentDetailService(ServiceActionBase documentDetailService) {
		this.documentDetailService = documentDetailService;
	}

	public ActivityIdFactory getActivityIdFactory() {
		return activityIdFactory;
	}

	public void setActivityIdFactory(ActivityIdFactory activityIdFactory) {
		this.activityIdFactory = activityIdFactory;
	}
}
