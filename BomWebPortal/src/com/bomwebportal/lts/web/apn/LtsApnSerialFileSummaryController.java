package com.bomwebportal.lts.web.apn;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
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

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.ApnSerialFileCaptureDTO;
import com.bomwebportal.lts.dto.apn.LtsApnFileDTO;
import com.bomwebportal.lts.dto.form.apn.LtsApnSerialFileSummaryFormDTO;
import com.bomwebportal.lts.dto.form.apn.LtsApnSerialFileUploadFormDTO;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsApnSerialFileSummaryController extends SimpleFormController {
	private final String viewName = "lts/apn/ltsapnserialfilesummary";
	private final String mainView = "ltsapnserialfilemain.html";
	private final String dnListView = "ltsapnserialfilednlistinfo.html";
	private final String commandName = "ltsApnSerialFileSummaryCmd";
	
	public static final String COL_DOC_TYPE = "A";
	public static final String COL_DN = "B";
	public static final String COL_GATEWAY_NUM = "C";
	
	protected static final int CELL_DOC_TYPE = CellReference.convertColStringToIndex(COL_DOC_TYPE);
	protected static final int CELL_DN = CellReference.convertColStringToIndex(COL_DN);
	protected static final int CELL_GATEWAY_NUM = CellReference.convertColStringToIndex(COL_GATEWAY_NUM);
	
	public LtsApnSerialFileSummaryController() {
		setCommandClass(LtsApnSerialFileSummaryFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ApnSerialFileCaptureDTO apnFileCaptureDTO = LtsSessionHelper.getApnSerialFileCapture(request);
		if (apnFileCaptureDTO == null) {
			return (new ModelAndView(new RedirectView(mainView)));
		}
		return super.handleRequestInternal(request, response);
	}
	
	@Override
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		ApnSerialFileCaptureDTO file = LtsSessionHelper.getApnSerialFileCapture(request);
		
		LtsApnSerialFileSummaryFormDTO form = new LtsApnSerialFileSummaryFormDTO();
		if(file == null || file.getFileUploadInfo() == null){
			return (new ModelAndView(new RedirectView(mainView)));
		}		
		
		LtsApnFileDTO apnFile = file.getFileUploadInfo();
		form.setApnFileSummary(apnFile);
		form.setTotalNumOfRecords(String.valueOf(apnFile.getAllDnRecord().size()));
		form.setNumDnMatchedWithoutProblem(String.valueOf(apnFile.getDnMatch().size()));
		form.setNumDnMatchedWithProblem(String.valueOf(apnFile.getDnMatchWithProblem().size()));
		form.setNumDnNotMatchedButNN(String.valueOf(apnFile.getDnNotMatchNnMatch().size()));
		form.setNumIgnored(String.valueOf(apnFile.getDnIgnored().size()));
		return form;
	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		ApnSerialFileCaptureDTO file = LtsSessionHelper.getApnSerialFileCapture(request);
		LtsApnSerialFileSummaryFormDTO form = (LtsApnSerialFileSummaryFormDTO) command;
		if(form == null){
			return new ModelAndView(new RedirectView(mainView));
		}
		
		switch (form.getFormAction()) {
			case RETURN:
				file.setFileUploadInfo(null);		
				return new ModelAndView(new RedirectView(mainView));
			case MATCH:
				file.setDnList(form.getApnFileSummary().getDnMatch());
				return new ModelAndView(new RedirectView(dnListView));
			case PROBLEM:	
				file.setDnList(form.getApnFileSummary().getDnMatchWithProblem());
				return new ModelAndView(new RedirectView(dnListView));
			case NOTMATCH:
				file.setDnList(form.getApnFileSummary().getDnNotMatchNnMatch());
				return new ModelAndView(new RedirectView(dnListView));
			case IGNORE:
				file.setDnList(form.getApnFileSummary().getDnIgnored());
				return new ModelAndView(new RedirectView(dnListView));
			case ALL:
				file.setDnList(form.getApnFileSummary().getAllDnRecord());
				return new ModelAndView(new RedirectView(dnListView));
		}
		return new ModelAndView(new RedirectView(mainView));
	}
	
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		
		Map<String, Object> referenceData = new HashMap<String, Object>();

		return referenceData;
	}
}
