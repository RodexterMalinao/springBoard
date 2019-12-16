package com.bomwebportal.lts.web.apn;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.springframework.validation.BindException;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.ApnSerialFileCaptureDTO;
import com.bomwebportal.lts.dto.apn.LtsApnDnDTO;
import com.bomwebportal.lts.dto.apn.LtsApnFileDTO;
import com.bomwebportal.lts.dto.form.apn.LtsApnSerialFileUploadFormDTO;
import com.bomwebportal.lts.service.PipbActvLtsService;
import com.bomwebportal.lts.service.apn.LtsApnSerialFileService;
import com.bomwebportal.lts.util.LtsActvBackendConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.lts.util.apn.ExcelLoaderHelper;
import com.pccw.util.FastByteArrayInputStream;
import org.springframework.core.task.TaskExecutor;

public class LtsApnSerialFileUploadController extends SimpleFormController {
	private final String viewName = "lts/apn/ltsapnserialfileupload";
	//private final String nextView = "ltsapnserialfilesummary.html";
	private final String nextView = "ltsapnuploadsuccess.html";
	private final String currentView = "ltsapnserialfileupload.html";
	private final String returnView = "ltsapnserialfilemain.html";
	private final String commandName = "ltsApnSerialFileUploadCmd";
	
	private final int WK_SHEET_APN_FILE = 1;	
	public static final String DOC_TYPE_APN = "APN";
	public static final String PIPB_ACTION_PORT_IN_TGT = "PORT_IN_TOGETHER";
	
	public static final String COL_PRINT_MKR = "A";
	public static final String COL_RC_VR = "B";
	public static final String COL_DOC_TYPE = "C";
	public static final String COL_NPR_COUNT = "D";
	public static final String COL_APP_DATE = "E";
	public static final String COL_BATCH_DATE = "F";
	public static final String COL_REF_SERIAL_NUM = "G";
	public static final String COL_DN = "H";
	public static final String COL_GATEWAY_NUM = "I";
	public static final String COL_REQ_ID = "J";
	public static final String COL_RNO = "K";
	public static final String COL_DNO = "L";
	public static final String COL_ORG_DNO = "M";
	public static final String COL_ORG_TP = "N";
	public static final String COL_EXIST_TP = "O";
	public static final String COL_CHGOVER_DATE = "P";
	public static final String COL_CO_S_TIME = "Q";
	public static final String COL_CO_E_TIME = "R";
	public static final String COL_NUM_OF_DOC = "S";
	public static final String COL_CUST_NAME = "T";
	public static final String COL_ID_TYPE = "U";
	public static final String COL_ID_NUM = "V";
	public static final String COL_LAL_BW_ORD_NUM = "W";
	public static final String COL_NUM_OF_ASST_NUM = "X";
	public static final String COL_COMMENT = "Y";
	public static final String COL_REJECT_CODE = "Z";
	public static final String COL_ASSO_TERM_1 = "AA";
	public static final String COL_ASSO_TERM_2 = "AB";
	public static final String COL_ASSO_TERM_3 = "AC";
	public static final String COL_ASSO_TERM_4 = "AD";
	public static final String COL_ASSO_TERM_5 = "AE";
	public static final String COL_ASSO_TERM_6 = "AF";
	
	
	protected static final int CELL_DOC_TYPE = CellReference.convertColStringToIndex(COL_DOC_TYPE);
	protected static final int CELL_DN = CellReference.convertColStringToIndex(COL_DN);
	protected static final int CELL_GATEWAY_NUM = CellReference.convertColStringToIndex(COL_GATEWAY_NUM);
	protected static final int CELL_CHGOVER_DATE = CellReference.convertColStringToIndex(COL_CHGOVER_DATE);
	protected static final int CELL_CO_S_TIME = CellReference.convertColStringToIndex(COL_CO_S_TIME);
	protected static final int CELL_CO_E_TIME = CellReference.convertColStringToIndex(COL_CO_E_TIME);
	protected static final int CELL_APP_DATE = CellReference.convertColStringToIndex(COL_APP_DATE);
	protected static final int CELL_BATCH_DATE = CellReference.convertColStringToIndex(COL_BATCH_DATE);
	protected static final int CELL_REF_SERIAL_NUM = CellReference.convertColStringToIndex(COL_REF_SERIAL_NUM);
	
	private byte[] file;
	private String fileName;
	private String username;
	
	private LtsApnSerialFileService ltsApnSerialFileService;
	private TaskExecutor apnTaskExecutor;
	private PipbActvLtsService pipbActvLtsService;
	
	public LtsApnSerialFileUploadController() {
		setCommandClass(LtsApnSerialFileUploadFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ApnSerialFileCaptureDTO apnFileCaptureDTO = LtsSessionHelper.getApnSerialFileCapture(request);
		if (apnFileCaptureDTO == null) {
			LtsSessionHelper.setApnSerialFileCapture(request);
		}
		return super.handleRequestInternal(request, response);
	}
	
	@Override
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		/*ApnSerialFileCaptureDTO file = LtsSessionHelper.getApnSerialFileCapture(request);
		
		LtsApnSerialFileUploadFormDTO form = file.getLtsApnSerialFileUploadForm();
		if(form == null){
			form = new LtsApnSerialFileUploadFormDTO();
		}
		*/
		
		return new LtsApnSerialFileUploadFormDTO();
	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		LtsApnSerialFileUploadFormDTO form = (LtsApnSerialFileUploadFormDTO) command;
		ApnSerialFileCaptureDTO file = LtsSessionHelper.getApnSerialFileCapture(request);
		BomSalesUserDTO bomSalesUser = LtsSessionHelper.getBomSalesUser(request);
		
		if(form == null || form.getBatchFile() == null){
			return new ModelAndView(new RedirectView(currentView));
		}
		
		if(form != null || form.getFormAction() != null){
			switch (form.getFormAction()) {
				case UPLOAD:
					try {
						setFile(form.getBatchFile().getBytes());
						setFileName(form.getBatchFile().getOriginalFilename());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					setUsername(bomSalesUser.getUsername());
					apnTaskExecutor.execute(new Runnable() {
						public void run() {
							try {
								//LtsApnFileDTO uploadFileInfo = processApnSerialFile(getFile(), getUsername());	
								processApnSerialFile(getFile(), getFileName(), getUsername());
							} catch (Exception ignore) {
								logger.error(ExceptionUtils.getFullStackTrace(ignore));
							}
						}
					});
					file.setLtsApnSerialFileUploadForm(form);
					//file.setFileUploadInfo(uploadFileInfo);					
					return new ModelAndView(new RedirectView(nextView));
				case RETURN:
					return new ModelAndView(new RedirectView(returnView));
			}
		}
		
		return new ModelAndView(new RedirectView(currentView));
	}
	
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		
		Map<String, Object> referenceData = new HashMap<String, Object>();

		return referenceData;
	}
	
	private LtsApnFileDTO processApnSerialFile(byte[] pFile, String pFileName, String pUser){
		String batchSeq = ltsApnSerialFileService.assignBatchSeq();
		List<LtsApnDnDTO> insertedInfo = new ArrayList<LtsApnDnDTO>();	
		if(StringUtils.isNotBlank(batchSeq)){
			try {
				InputStream fbais = new FastByteArrayInputStream(pFile, pFile.length);
				HSSFWorkbook workbook = new HSSFWorkbook(fbais);
				FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
				HSSFSheet worksheet = workbook.getSheetAt(0);
				HSSFRow row = null;
				int counter = 1;
				Iterator<Row> rows = worksheet.rowIterator();
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				DateFormat df2 = new SimpleDateFormat("yyyy/MM/dd");
				DateFormat sqlForm = new SimpleDateFormat("yyyyMMddHHmmss");

				if(ltsApnSerialFileService.isFileNameAvailable(pFileName)){
					
					ltsApnSerialFileService.inputApnSerialFile(batchSeq, pFileName, "P", "Upload in Progress", pFile, pUser);
					
					while (rows.hasNext()) {
						row = (HSSFRow) rows.next();	
						if (row.getRowNum() < WK_SHEET_APN_FILE) {
							continue;
						}
						
						String srvNum = ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_DN); 
						int charIndex = 0;
						while(ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_GATEWAY_NUM).charAt(charIndex) == '0'){ //REMOVING left-padded "0" from NN service number	
							charIndex++;
						}
						String srvNn = ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_GATEWAY_NUM).substring(charIndex);	
						
						if (StringUtils.isNotBlank(ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_DOC_TYPE))
							&& DOC_TYPE_APN.equals(ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_DOC_TYPE))
							&& ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_DOC_TYPE).toUpperCase().indexOf("X") == -1) {
							
							String startDate = ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_CHGOVER_DATE) + " " + ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_CO_S_TIME);
							String endDate = ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_CHGOVER_DATE) + " " + ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_CO_E_TIME);						
							LtsApnDnDTO info = ltsApnSerialFileService.getApnDtlInfo(srvNum, srvNn, String.valueOf(counter), batchSeq, df.parse(startDate), df.parse(endDate));
							
							if(info == null){	// Ignore
								info = new LtsApnDnDTO();
								info.setSrvNum(srvNum);
								info.setSrvNn(srvNn);
								info.setStatus("I");
								info.setStatusMessage("APN information not found");							
							}
							else if("Y".equals(info.getIsNnMatch()) && !"Y".equals(info.getIsDnMatch())){
								info.setStatus("F");
								info.setStatusMessage("Mismatch DN record found");
							}
							else{
								info.setStatus("P");
								info.setStatusMessage("");
							}
							
							if(StringUtils.isNotBlank(ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_BATCH_DATE))){
								Date batchDate = df.parse(ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_BATCH_DATE));
								info.setBatchDate(sqlForm.format(batchDate));
							}
							
							if(StringUtils.isNotBlank(ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_APP_DATE))){
								Date appDate = df2.parse(ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_APP_DATE));
								info.setAppDate(sqlForm.format(appDate));
							}					
							
							info.setBatchSeq(batchSeq);
							info.setChgoverStartTime(sqlForm.format(df.parse(startDate)));
							info.setChgoverEndTime(sqlForm.format(df.parse(endDate)));
							info.setApnSerial(ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_REF_SERIAL_NUM));
							info.setDtlSeq(String.valueOf(counter));
							info.setTypeOfDoc(ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_DOC_TYPE));
							
							ltsApnSerialFileService.inputApnSerialDtlRecord(info, pUser);
							
							if(PIPB_ACTION_PORT_IN_TGT.equals(info.getDuplexAction())){
								if(ltsApnSerialFileService.isDuplexCompleted(info.getOrderId()) && info.getStatus().equals("P")){
									try{
										pipbActvLtsService.updatePipbActivityStatus(info.getOrderId(), pUser, LtsActvBackendConstant.ACTV_STATUS_UPLOAD_SUCCESS, LtsActvBackendConstant.ACTV_ACTION_APN_UPLOAD);
										info.setWqStatus(LtsActvBackendConstant.WQ_STATUS_ACKNOWLEDGE);
										ltsApnSerialFileService.inputApnSerialDtlRecord(info, pUser);
									}catch (Exception e) {
										info.setStatusMessage(e.toString());
										info.setWqFailInd("Y");
										ltsApnSerialFileService.inputApnSerialDtlRecord(info, pUser);
									}
								}
							}
							else{
								if(info.getStatus().equals("P")){
									try{
										pipbActvLtsService.updatePipbActivityStatus(info.getOrderId(), pUser, LtsActvBackendConstant.ACTV_STATUS_UPLOAD_SUCCESS, LtsActvBackendConstant.ACTV_ACTION_APN_UPLOAD);
										info.setWqStatus(LtsActvBackendConstant.WQ_STATUS_ACKNOWLEDGE);
										ltsApnSerialFileService.inputApnSerialDtlRecord(info, pUser);
									}catch (Exception e) {
										info.setStatusMessage(e.toString());
										info.setWqFailInd("Y");
										ltsApnSerialFileService.inputApnSerialDtlRecord(info, pUser);
									}
								}
							}
						}			
						else{
							LtsApnDnDTO info = new LtsApnDnDTO();						
							if (StringUtils.isNotBlank(ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_DOC_TYPE))
									&& !DOC_TYPE_APN.equals(ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_DOC_TYPE))){
								info.setStatus("I");
								info.setStatusMessage("Ignored");
							}
							else{
								info.setStatus("F");
								info.setStatusMessage("Missing document type");
							}
							info.setBatchSeq(batchSeq);
							info.setSrvNum(srvNum);
							info.setSrvNn(srvNn);
							info.setDtlSeq(String.valueOf(counter));
							
							if(StringUtils.isNotBlank(ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_BATCH_DATE))){
								Date batchDate = df.parse(ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_BATCH_DATE));
								info.setBatchDate(sqlForm.format(batchDate));
							}
							
							if(StringUtils.isNotBlank(ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_APP_DATE))){
								Date appDate = df2.parse(ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_APP_DATE));
								info.setAppDate(sqlForm.format(appDate));
							}		
							
							String startDate = ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_CHGOVER_DATE) + " " + ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_CO_S_TIME);
							String endDate = ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_CHGOVER_DATE) + " " + ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_CO_E_TIME);
							info.setChgoverStartTime(sqlForm.format(df.parse(startDate)));
							info.setChgoverEndTime(sqlForm.format(df.parse(endDate)));
							info.setTypeOfDoc(ExcelLoaderHelper.getStringCellValue(formulaEvaluator, row, CELL_DOC_TYPE));
	
							ltsApnSerialFileService.inputApnSerialDtlRecord(info, pUser);
							insertedInfo.add(info); // Safe guard in case when the system is down during the process, already inserted records will be marked as 'F' to avoid duplicated requests.
						}
						counter++;	
					}
					ltsApnSerialFileService.inputApnSerialFile(batchSeq, pFileName, "C", "Upload Completed", pFile, pUser);
					
				}
				else{
					ltsApnSerialFileService.inputApnSerialFile(batchSeq, pFileName, "R", "File with the same file name exists", pFile, pUser);
				}
			} catch (Exception e) {
				ltsApnSerialFileService.inputApnSerialFile(batchSeq, pFileName, "R", e.toString(), null, pUser);
				
				// Restore all the inserted data to "Failed" to avoid duplicated requests by re-uploading the APN document
				for(LtsApnDnDTO info : insertedInfo){
					info.setStatus("F");
					info.setStatusMessage("Rejected APN file");
					ltsApnSerialFileService.inputApnSerialDtlRecord(info, pUser);
				}
			}
		}
		
		return ltsApnSerialFileService.getApnSerialFileInfo(batchSeq);
	}

	public LtsApnSerialFileService getLtsApnSerialFileService() {
		return ltsApnSerialFileService;
	}

	public void setLtsApnSerialFileService(LtsApnSerialFileService ltsApnSerialFileService) {
		this.ltsApnSerialFileService = ltsApnSerialFileService;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public TaskExecutor getApnTaskExecutor() {
		return apnTaskExecutor;
	}

	public void setApnTaskExecutor(TaskExecutor apnTaskExecutor) {
		this.apnTaskExecutor = apnTaskExecutor;
	}

	public PipbActvLtsService getPipbActvLtsService() {
		return pipbActvLtsService;
	}

	public void setPipbActvLtsService(PipbActvLtsService pipbActvLtsService) {
		this.pipbActvLtsService = pipbActvLtsService;
	}
}
