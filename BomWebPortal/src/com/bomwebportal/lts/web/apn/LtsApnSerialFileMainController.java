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
import com.bomwebportal.lts.dto.form.apn.LtsApnSerialFileMainFormDTO;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsApnSerialFileMainController extends SimpleFormController {
	private final String viewName = "lts/apn/ltsapnserialfilemain";
	private final String nextViewFileUpload = "ltsapnserialfileupload.html";
	private final String nextViewFileEnquiry = "ltsapnserialfileenquiry.html";
	private final String currentView = "ltsapnserialfilemain.html";
	private final String commandName = "ltsApnSerialFileMainCmd";
	
	public LtsApnSerialFileMainController() {
		setCommandClass(LtsApnSerialFileMainFormDTO.class);
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
/*		ApnSerialFileCaptureDTO file = LtsSessionHelper.getApnSerialFileCapture(request);
		
		LtsApnSerialFileUploadFormDTO form = file.getLtsApnSerialFileUploadForm();
		if(form == null){
			form = new LtsApnSerialFileUploadFormDTO();
		}*/
		
		return new LtsApnSerialFileMainFormDTO();
	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		LtsApnSerialFileMainFormDTO form = (LtsApnSerialFileMainFormDTO) command;

		if(form != null || form.getFormAction() != null){
			switch (form.getFormAction()) {
				case UPLOAD:
					return new ModelAndView(new RedirectView(nextViewFileUpload));
				case ENQUIRY:
					return new ModelAndView(new RedirectView(nextViewFileEnquiry));
			}
		}
		
		return new ModelAndView(new RedirectView(currentView));
	}
	
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		
		Map<String, Object> referenceData = new HashMap<String, Object>();

		return referenceData;
	}
}
