package com.activity.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import smartliving.backend.service.ActivityDocumentService;

import com.bomwebportal.dto.OrdDocDTO;
import com.bomwebportal.util.PdfUtil;
import com.pccw.util.FastByteArrayOutputStream;
import com.pccw.util.spring.SpringApplicationContext;


public class UploadActvDocumentServiceImpl implements UploadActvDocumentService {

	protected String saveDirectory;

	@Override
	public void uploadActvDocument(OrdDocDTO pOrdDocDTO, String pUsername) throws Exception {
		String orderId = pOrdDocDTO.getOrderId();
		String docType = pOrdDocDTO.getDocType();
		int seq = getNextSeqNum(orderId, docType);
		File uploadedFile = null;
		// Save source file to activity directory
		if (StringUtils.isNotBlank(pOrdDocDTO.getFilePathName())) {
			uploadedFile = this.processFile(pOrdDocDTO.getFilePathName(), orderId, docType, seq);
		} else if (pOrdDocDTO.getFileContent() != null) {
			uploadedFile = this.processFile(pOrdDocDTO.getFileContent(), orderId, docType, seq);
		}

		String fileName = null;
		if (uploadedFile != null){
			fileName= uploadedFile.getName();				
		}
		
		//Insert record to BOMWEB_ALL_ORD_DOC
		saveRecord(orderId, docType, seq, fileName, pUsername, null, null);	
		
	}

	private File processFile(String pFilePath, String pOrderId, String pDocType, int pSeq) throws Exception {
		File sourceFile = new File(pFilePath);
		if (!sourceFile.exists()) {
			sourceFile = new File(saveDirectory + "/" + pFilePath);
		}
		if (!sourceFile.exists()) {
			throw new IOException("SOURCE FILE [" + sourceFile.getPath() + "] NOT EXISTS");
		}
		File destFile = getFile(pOrderId, pDocType, pSeq, null, FILE_EXTENSION_PDF);
		FileUtils.copyFile(sourceFile, destFile);
		return destFile;		
	}
	
	private File processFile(byte[] pFileByteArray, String pOrderId, String pDocType, int pSeq) throws Exception {
		File destFile = getFile(pOrderId, pDocType, pSeq, null, FILE_EXTENSION_PDF);	
		FileUtils.writeByteArrayToFile(destFile, pFileByteArray);
		return destFile;		
	}
	
	private int getNextSeqNum(String orderId, String docType) {
		return SpringApplicationContext.getBean(ActivityDocumentService.class).getLastSeqNum(orderId, docType)+1;
	}
	
	private void saveRecord(String pOrderId, String pDocType, int pSeqNum, String pFileName, String pUsername, String pDocDesc, String pFaxSerial) {
		OrdDocDTO dto = new OrdDocDTO();
		dto.setOrderId(pOrderId);
		dto.setDocType(pDocType);
		dto.setSeqNum(pSeqNum);
		dto.setFilePathName(pFileName);
		dto.setCaptureBy(pUsername);
		dto.setSerial(pFaxSerial);
		SpringApplicationContext.getBean(ActivityDocumentService.class).insertOrdDoc(dto, pDocDesc);
	}
	
	@Override
	public String getOrdAllDocOrderId(String pActvId, String pTaskSeq) {
		return pActvId + StringUtils.defaultString(pTaskSeq);
	}

	private String composeFileName(String pOrderId, String pDocType, int pFileSeq, String pFileSuffix, String pExt) {
		return composeFileName(pOrderId, pDocType, String.valueOf(pFileSeq), pFileSuffix, pExt);
	}

	
	private String composeFileName(String pOrderId, String pDocType, String pFileSeq, String pFileSuffix, String pExt) {
		return pOrderId + '_' + pDocType + '_' + StringUtils.defaultString(pFileSeq) + StringUtils.defaultString(pFileSuffix) 
				+ (StringUtils.isBlank(pExt) ? "" : ".") + StringUtils.defaultString(pExt);
	}

	private File getOrderDirectory(String pOrderId) throws Exception {
		File orderDir = new File(saveDirectory + "/" + pOrderId);
		FileUtils.forceMkdir(orderDir);
		return orderDir;
	}

	public File getFile(OrdDocDTO pOrdDocDTO) {
      if(StringUtils.isNotBlank(pOrdDocDTO.getLinkedPath())){
    	  return new File(this.saveDirectory + "/" + pOrdDocDTO.getLinkedPath() + "//" + pOrdDocDTO.getFullPathName());
      }
		return new File(this.saveDirectory + "/" + pOrdDocDTO.getFullPathName());
	}
	
	@Override
	public File getFile(String pOrderId, String pDocType, int pFileSeq,
			String pFileSuffix, String pExt) throws Exception {
		return new File(getOrderDirectory(pOrderId), this.composeFileName(pOrderId, pDocType, pFileSeq, pFileSuffix, pExt));
	}
	
	@Override
	public File getFile(String pOrderId, String pDocType, String pFileSeq,
			String pFileSuffix, String pExt) throws Exception {
		return new File(getOrderDirectory(pOrderId), this.composeFileName(pOrderId, pDocType, pFileSeq, pFileSuffix, pExt));
	}

	@Override
	public File getFile(String pActvId, String pTaskSeq, String pDocType,
			String pFileSeq, String pFileSuffix, String pExt) throws Exception {
		return getFile(getOrdAllDocOrderId(pActvId, pTaskSeq), pDocType, pFileSeq, pFileSuffix, pExt);
	}
	
	@Override
	public File getWqFile(String pActvId, String pTaskSeq, String pDocType) throws Exception {
		return getFile(pActvId, pDocType, null, "WQ", UploadActvDocumentService.FILE_EXTENSION_PDF);
	}

	@Override
	public void addWaterMark(String pActvId, String pTaskSeq, String pDocType,
			String pFileSeq, String pFileSuffix, String pWaterMark)
			throws Exception {
		addWaterMark(getFile(pActvId, pTaskSeq, pDocType, pFileSeq, pFileSuffix, UploadActvDocumentService.FILE_EXTENSION_PDF), pWaterMark);
	}

	@Override
	public void addWqWaterMark(String pActvId, String pTaskSeq, String pDocType, String pWaterMark) throws Exception {
		addWaterMark(getWqFile(pActvId, pTaskSeq, pDocType), pWaterMark);
	}
	
	private void addWaterMark(File pFile, String pWaterMark) throws Exception {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(pFile);
			FastByteArrayOutputStream fbaos = new FastByteArrayOutputStream();
			PdfUtil.concatPDFs(Arrays.asList(new InputStream[] {fis}), fbaos, false, false, pWaterMark, null, null, null, null);
			fis.close();
			FileUtils.writeByteArrayToFile(
					pFile, 
					fbaos.getByteArray());
		} catch (Exception e) {
			throw e;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception ignore) {
				}
			}
		}
	}
	
	protected String getFileExtension(String filename) {
		if (StringUtils.isBlank(filename)) return "";
		String ext = "";
		int dot = filename.lastIndexOf('.');
		if (dot >= 0 && dot < filename.length())
			ext = filename.substring(dot);
		return StringUtils.lowerCase(ext);
	}

	@Override
	public String getSaveDirectory() {
		return saveDirectory;
	}

	public void setSaveDirectory(String pSaveDirectory) {
		saveDirectory = pSaveDirectory;
	}
}