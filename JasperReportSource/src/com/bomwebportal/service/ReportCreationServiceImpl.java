package com.bomwebportal.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.report.ReportOutputDTO;
import com.bomwebportal.dto.report.ReportSetDTO;
import com.bomwebportal.dto.report.ReportSetDetailDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.util.FastByteArrayOutputStream;
import com.bomwebportal.util.PdfUtil;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.service.ReportService;
import com.pccw.util.spring.SpringApplicationContext;

public abstract class ReportCreationServiceImpl implements ReportCreationService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	protected abstract void fillReportName(ReportSetDTO pReportSet, Map<String,Object> pParmMap);
	
	protected abstract void fillReportName(ReportSetDetailDTO pReportDtl, Map<String,Object> pParmMap);
	
	protected abstract String generateEncryptionPassword(ReportSetDTO pReportSet, Map<String,Object> pParmMap);
	
	protected abstract boolean isReportRequired(ReportSetDetailDTO pReportDtl, Map<String,Object> pParmMap);
	
	protected abstract String[] getReportNameRequired(ReportSetDetailDTO pReportDtl, Map<String,Object> pParmMap);
	
	public ReportOutputDTO generateReportWithWaterMark(ReportSetDTO pRptSet, Map<String,Object> pParmMap, String pWaterMark) {
		
		this.fillReportName(pRptSet, pParmMap);
		ReportOutputDTO rptOut = null;
		
		if (!pRptSet.isReGen()) {
			rptOut = this.openOutputFile(pRptSet);	
		}		
		if (rptOut != null) {
			if (pRptSet.isEncrypt()) {
				String password = this.generateEncryptionPassword(pRptSet, pParmMap);
				Integer permission = PdfUtil.ALLOW_SCREENREADERS | PdfUtil.ALLOW_PRINTING;
				FastByteArrayOutputStream outputStream = this.concatPdf(new InputStream[] {rptOut.getOutputFileStream().getInputStream()}, false, true, permission, password, password);
				rptOut.setOutputFileStream(outputStream);
			}
			return rptOut;
		}
		return this.createReportWithWaterMark(pRptSet, pParmMap, pWaterMark);
	}
	
	private ReportOutputDTO createReportWithWaterMark(ReportSetDTO pRptSet, Map<String,Object> pParmMap, String pWaterMark) {
		
		List<FastByteArrayOutputStream>[] outputList = this.createReport(pRptSet.getRptDtls(), pRptSet.getCopies(), pParmMap, pRptSet.isReGen());
		List<FastByteArrayOutputStream> mergeList = new ArrayList<FastByteArrayOutputStream>();
		mergeList.addAll(outputList[0]);
		mergeList.addAll(outputList[1]);
		InputStream[] inputStreams = new InputStream[mergeList.size()];
		FastByteArrayOutputStream outputStream = null;
		
		for (int i=0; i<mergeList.size(); ++i) {
			inputStreams[i] = mergeList.get(i).getInputStream();
		}
		String password = null;
		Integer permission = null;
		
		if (pRptSet.isEncrypt()) {
			password = this.generateEncryptionPassword(pRptSet, pParmMap);
			permission = PdfUtil.ALLOW_SCREENREADERS | PdfUtil.ALLOW_PRINTING;
		} else {
			password = null;
			
			if (StringUtils.equals(PERMISSION_SCREEN_READ, pRptSet.getPermission())) {
				permission = PdfUtil.ALLOW_SCREENREADERS;
			}
		}
		outputStream = this.concatPdfWithWaterMark(inputStreams, false, true, permission, password, password, pWaterMark);
		
		try {
			ReportOutputDTO rptOut = new ReportOutputDTO();
			rptOut.setFileStoragePath(this.saveOutputFile(pRptSet, outputStream));
			rptOut.setOutputFileStream(outputStream);
			return rptOut;
		} catch (Exception ex) {
			logger.error("Fail to create report " +  pRptSet.getFileName() + ": " + ExceptionUtils.getFullStackTrace(ex));
			throw new AppRuntimeException("Fail to create report " +  pRptSet.getFileName() , ex);
		}

	}
	

	public ReportOutputDTO generateReport(ReportSetDTO pRptSet, Map<String,Object> pParmMap) {
		
		this.fillReportName(pRptSet, pParmMap);
		ReportOutputDTO rptOut = null;
		
		if (!pRptSet.isReGen()) {
			rptOut = this.openOutputFile(pRptSet);	
		}		
		if (rptOut != null) {
			if (pRptSet.isEncrypt()) {
				String password = this.generateEncryptionPassword(pRptSet, pParmMap);
				Integer permission = PdfUtil.ALLOW_SCREENREADERS | PdfUtil.ALLOW_PRINTING;
				FastByteArrayOutputStream outputStream = this.concatPdf(new InputStream[] {rptOut.getOutputFileStream().getInputStream()}, false, true, permission, password, password);
				rptOut.setOutputFileStream(outputStream);
			}
			return rptOut;
		}
		return this.createReport(pRptSet, pParmMap);
	}
	
	private ReportOutputDTO createReport(ReportSetDTO pRptSet, Map<String,Object> pParmMap) {
		
		List<FastByteArrayOutputStream>[] outputList = this.createReport(pRptSet.getRptDtls(), pRptSet.getCopies(), pParmMap, pRptSet.isReGen());
		List<FastByteArrayOutputStream> mergeList = new ArrayList<FastByteArrayOutputStream>();
		mergeList.addAll(outputList[0]);
		mergeList.addAll(outputList[1]);
		InputStream[] inputStreams = new InputStream[mergeList.size()];
		FastByteArrayOutputStream outputStream = null;
		
		for (int i=0; i<mergeList.size(); ++i) {
			inputStreams[i] = mergeList.get(i).getInputStream();
		}
		String password = null;
		Integer permission = null;
		
		if (pRptSet.isEncrypt()) {
			password = this.generateEncryptionPassword(pRptSet, pParmMap);
			permission = PdfUtil.ALLOW_SCREENREADERS | PdfUtil.ALLOW_PRINTING;
		} else {
			password = null;
			
			if (StringUtils.equals(PERMISSION_SCREEN_READ, pRptSet.getPermission())) {
				permission = PdfUtil.ALLOW_SCREENREADERS;
			}
		}
		outputStream = this.concatPdf(inputStreams, false, true, permission, password, password);
		
		try {
			ReportOutputDTO rptOut = new ReportOutputDTO();
		
			if("N".equals(pParmMap.get("CONCAT"))) {
				rptOut.setFileStoragePath(pRptSet.getFileName());
			}else{
				rptOut.setFileStoragePath(this.saveOutputFile(pRptSet, outputStream));
			}
			
					
			
			
			rptOut.setOutputFileStream(outputStream);
			return rptOut;
		} catch (Exception ex) {
			logger.error("Fail to create report " +  pRptSet.getFileName() + ": " + ExceptionUtils.getFullStackTrace(ex));
			throw new AppRuntimeException("Fail to create report " +  pRptSet.getFileName() , ex);
		}

	}
	
	@SuppressWarnings("unchecked")
	private List<FastByteArrayOutputStream>[] createReport(ReportSetDetailDTO[] pReportDtls, String pCopy, Map<String,Object> pParmMap, boolean pIsReGenWholeSet) {
		
		List<FastByteArrayOutputStream> internalStreamList = new ArrayList<FastByteArrayOutputStream>();
		List<FastByteArrayOutputStream> custStreamList = new ArrayList<FastByteArrayOutputStream>();
		
		for (int i=0; pReportDtls!=null && i<pReportDtls.length; ++i) {
			if (ReportCreationServiceImpl.this.isReportRequired(pReportDtls[i], pParmMap)) {
				String[] reportNames = ReportCreationServiceImpl.this.getReportNameRequired(pReportDtls[i], pParmMap);
				
				for (int j=0; reportNames != null && j<reportNames.length; j++) {
					Map<String,Object> inputParmMap = new HashMap<String,Object>(pParmMap);
					inputParmMap.put(ReportCreationService.RPT_KEY_NAME, reportNames[j]);
					
					ReportCreationServiceImpl.this.fillReportName(pReportDtls[i], inputParmMap);
					ReportOutputDTO rptOut = null;
				
					if (!pIsReGenWholeSet && !pReportDtls[i].isReGen()) {
						rptOut = ReportCreationServiceImpl.this.openOutputFile(pReportDtls[i]);
					}
					
					if (rptOut != null) {
						internalStreamList.add(rptOut.getOutputFileStream());
					} else {
						FastByteArrayOutputStream[] outputStreams = ReportCreationServiceImpl.this.createReport(pReportDtls[i], pCopy, inputParmMap);
					
						if (outputStreams[0] != null) {
							internalStreamList.add(outputStreams[0]);
						}
						if (outputStreams[1] != null) {
							custStreamList.add(outputStreams[1]);
						}
					}	
				}
			}
		}
		
		return new List[] {internalStreamList, custStreamList};
	}
	
//	@SuppressWarnings("unchecked")
//	private List<FastByteArrayOutputStream>[] createReport(final ReportSetDetailDTO[] pReportDtls, final String pCopy, final Map<String,Object> pParmMap, final boolean pIsReGenWholeSet) {
//		
//		final FastByteArrayOutputStream[] internalCopyStreams = new FastByteArrayOutputStream[pReportDtls.length];
//		final FastByteArrayOutputStream[] custCopyStreams = new FastByteArrayOutputStream[pReportDtls.length];
//		Thread[] threads = new Thread[Integer.parseInt(this.numThreads)];
//		
//		for (int i=0; i<threads.length; ++i) {
//			final int threadCtr = i;
//			
//			threads[i] = new Thread(new Runnable() {
//				public void run() {
//					int index = threadCtr;
//					
//					while (pReportDtls.length > index) {
//						if (ReportCreationServiceImpl.this.isReportRequired(pReportDtls[index], pParmMap)) {
//							
//							Map<String,Object> inputParmMap = new HashMap<String,Object>(pParmMap);
//							ReportCreationServiceImpl.this.fillReportName(pReportDtls[index], inputParmMap);
//							FastByteArrayOutputStream rptOutputStream = null;
//						
//							if (!pIsReGenWholeSet && !pReportDtls[index].isReGen()) {
//								rptOutputStream = ReportCreationServiceImpl.this.openOutputFile(pReportDtls[index]);
//							}
//							if (rptOutputStream != null) {
//								internalCopyStreams[pReportDtls[index].getRptSeq()] = rptOutputStream;
//							} else {
//								FastByteArrayOutputStream[] outputStreams = ReportCreationServiceImpl.this.createReport(pReportDtls[index], pCopy, inputParmMap);
//							
//								if (outputStreams[0] != null) {
//									internalCopyStreams[pReportDtls[index].getRptSeq()] = outputStreams[0];
//								}
//								if (outputStreams[1] != null) {
//									custCopyStreams[pReportDtls[index].getRptSeq()] = outputStreams[1];
//								}
//							}
//						}
//						index = index + Integer.parseInt(numThreads);
//					}
//				}});
//			threads[i].start();	
//		}
//		for (int i=0; i<threads.length; i++) {
//			try {
//				if (threads[i] != null) {
//					threads[i].join();
//				}
//			} catch (InterruptedException ignore) {}
//		}
//		List<FastByteArrayOutputStream> internalStreamList = new ArrayList<FastByteArrayOutputStream>();
//		List<FastByteArrayOutputStream> custStreamList = new ArrayList<FastByteArrayOutputStream>();
//		
//		for (int i=0; i<internalCopyStreams.length; ++i) {
//			if (internalCopyStreams[i] != null) {
//				internalStreamList.add(internalCopyStreams[i]);
//			}
//		}
//		for (int i=0; i<custCopyStreams.length; ++i) {
//			if (custCopyStreams[i] != null) {
//				custStreamList.add(custCopyStreams[i]);
//			}
//		}
//		return new List[] {internalStreamList, custStreamList};
//	}
	
	private FastByteArrayOutputStream[] createReport(ReportSetDetailDTO pReportDtl, String pCopy, Map<String,Object> pParmMap) {

		FastByteArrayOutputStream internalCopyOutStream = null;
		FastByteArrayOutputStream custCopyOutStream = null;
		
		try {
			FastByteArrayOutputStream rptOutputStream = new FastByteArrayOutputStream();
			ReportDTO rptDTO = SpringApplicationContext.getBean(ReportService.class).generateReport(pReportDtl.getRptName(), (String)pParmMap.get(PARM_LANG), pParmMap, rptOutputStream, ReportService.EXPORT_TYPE_PDF);
			internalCopyOutStream = this.concatPdf(new InputStream[] {rptOutputStream.getInputStream()}, true, true, null, null, null);			
			this.saveOutputFile(pReportDtl, internalCopyOutStream);
					
			if (StringUtils.equals(COPY_BOTH, pCopy)) {
				BeanUtils.setProperty(rptDTO, "customerCopy", true);
				rptOutputStream = new FastByteArrayOutputStream();
				SpringApplicationContext.getBean(ReportService.class).reGenerateReport(pReportDtl.getRptName(), (String)pParmMap.get(PARM_LANG), pParmMap, rptDTO, rptOutputStream, ReportService.EXPORT_TYPE_PDF);
				custCopyOutStream = this.concatPdf(new InputStream[] {rptOutputStream.getInputStream()}, true, true, null, null, null);
			}
		} catch (Exception ex) {
			logger.error("Fail to create report " +  pReportDtl.getRptName() + ": " + ExceptionUtils.getFullStackTrace(ex));
			throw new AppRuntimeException("Fail to create report " +  pReportDtl.getRptName() , ex);
		}
		return new FastByteArrayOutputStream[] {internalCopyOutStream, custCopyOutStream};
	}
	
	private String saveOutputFile(ReportSetDTO pReportSet, FastByteArrayOutputStream pOutputStream) throws FileNotFoundException, IOException {
		return this.saveOutputFile(pOutputStream.getByteArray(), pReportSet.getStorePath(), pReportSet.getFileName());
	}
	
	private String saveOutputFile(ReportSetDetailDTO pReportDtl, FastByteArrayOutputStream pOutputStream) throws FileNotFoundException, IOException {
		return this.saveOutputFile(pOutputStream.getByteArray(), pReportDtl.getStorePath(), pReportDtl.getFileName());
	}
	
	protected String saveOutputFile(byte[] pFileBytes, String pStorePath, String pFileName) throws FileNotFoundException, IOException {
		logger.debug("saveOutputFile begin\n");
		if (StringUtils.isBlank(pStorePath) || StringUtils.isBlank(pFileName)) {
			return null;
		}
		File directory = new File(pStorePath);
		
		if (!directory.exists()) {
			directory.mkdir();
			logger.debug("Create directory: " + pStorePath);
		}
		File f = new File(directory, pFileName);
		
		if (f.exists()) {
			logger.debug("File: " + f.getAbsoluteFile() + " existed\n");
			int fileCnt = 1;
			boolean fileRename = false;
			String nFileName ;
            
			while (!fileRename) {
				//nFileName = new StringBuilder(pFileName).insert(pFileName.indexOf("."), "_"+fileCnt).toString();
				nFileName = new StringBuilder(pFileName).append("_"+fileCnt).toString();
				fileRename = f.renameTo(new File(directory, nFileName));
				fileCnt++;
			}
			f = new File(directory, pFileName);
		}
		logger.debug("Start create: " + f.getAbsoluteFile() + "\n");
		FileOutputStream fout = new FileOutputStream(f);
		fout.write(pFileBytes);
		fout.flush();
		fout.close();
		logger.debug("File: " + f.getAbsoluteFile() + "saved\n");
		return f.getAbsolutePath();
	}
	
	public FastByteArrayOutputStream concatPdf(InputStream[] pInputStreams, boolean pIsAddPageNum, boolean pIsMergePageNum, Integer pPdfPermission, String pUserPin, String pOwnerPin) {
		
		List<InputStream> inStreamList = new ArrayList<InputStream>();
		
		for (int i=0; pInputStreams!=null && i<pInputStreams.length; ++i) {
			inStreamList.add(pInputStreams[i]);	
		}
		FastByteArrayOutputStream outputStream = new FastByteArrayOutputStream();
		PdfUtil.concatPDFs(inStreamList, outputStream, pIsAddPageNum, pIsMergePageNum, null, pPdfPermission, pUserPin, pOwnerPin);
		return outputStream;
	}
	
	public FastByteArrayOutputStream concatPdfWithWaterMark(InputStream[] pInputStreams, boolean pIsAddPageNum, boolean pIsMergePageNum, Integer pPdfPermission, String pUserPin, String pOwnerPin, String pWaterMark) {
		
		List<InputStream> inStreamList = new ArrayList<InputStream>();
		
		for (int i=0; pInputStreams!=null && i<pInputStreams.length; ++i) {
			inStreamList.add(pInputStreams[i]);	
		}
		FastByteArrayOutputStream outputStream = new FastByteArrayOutputStream();
		PdfUtil.concatPDFs(inStreamList, outputStream, pIsAddPageNum, pIsMergePageNum, pWaterMark, null, pPdfPermission, pUserPin, pOwnerPin);
		return outputStream;
	}
	
	
	private ReportOutputDTO openOutputFile(ReportSetDTO pReportSet) {
		return this.openOutputFile(pReportSet.getSrcStorePath(), pReportSet.getSrcFileName());
	}
	
	private ReportOutputDTO openOutputFile(ReportSetDetailDTO pRptDtl) {
		return this.openOutputFile(pRptDtl.getSrcStorePath(), pRptDtl.getSrcFileName());
	}
	
	public ReportOutputDTO openOutputFile(String pStorePath, String pFileName) {
		
		if (StringUtils.isBlank(pStorePath) || StringUtils.isBlank(pFileName)) {
			return null;
		}
		try {
			File directory = new File(pStorePath);
			
			if (!directory.exists()) {
				directory.mkdir();
			}
			File f = new File(directory, pFileName);
			
			if (!f.exists()) {
				return null;
			}
			InputStream inputStream = new FileInputStream(f);
			FastByteArrayOutputStream outStream = new FastByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int len;
			
			while ((len = inputStream.read(buf)) > 0){
				outStream.write(buf, 0, len);
			}
			inputStream.close();
			ReportOutputDTO rptOut = new ReportOutputDTO();
			rptOut.setFileStoragePath(f.getAbsolutePath());
			rptOut.setOutputFileStream(outStream);
	        return rptOut;
		} catch (FileNotFoundException e) {
			return null;
		} catch(IOException e) {
			return null;
		}
	}
}
