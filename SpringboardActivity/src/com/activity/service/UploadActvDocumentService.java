package com.activity.service;

import java.io.File;

import com.bomwebportal.dto.OrdDocDTO;

public interface UploadActvDocumentService {
	
	public static final String FILE_EXTENSION_PDF = "pdf";
	
	public void uploadActvDocument(OrdDocDTO pOrdDocDTO, String pUsername) throws Exception;
	
	public String getSaveDirectory();
	
	public String getOrdAllDocOrderId(String pActvId, String pTaskId);
	
	public File getFile(OrdDocDTO pOrdDocSLVDTO);
	
	public File getFile(String pOrderId, String pDocType, int pFileSeq,	String pFileSuffix, String pExt) throws Exception;

	public File getFile(String pOrderId, String pDocType, String pSeqNum, String pFileSuffix, String pExt) throws Exception;

	public File getFile(String pActvId, String pTaskSeq, String pDocType, String pSeqNum, String pFileSuffix, String pExt) throws Exception;
	
	public File getWqFile(String pActvId, String pTaskSeq, String pDocType) throws Exception;
	
	public void addWaterMark(String pActvId, String pTaskSeq, String pDocType,
			String pFileSeq, String pFileSuffix, String pWaterMark)
			throws Exception;
	
	public void addWqWaterMark(String pActvId, String pTaskSeq, String pDocType, String pWaterMark) throws Exception;
}