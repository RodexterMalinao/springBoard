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
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.ApnSerialFileCaptureDTO;
import com.bomwebportal.lts.dto.apn.LtsApnFileDTO;
import com.bomwebportal.lts.dto.form.apn.LtsApnSerialFileSummaryFormDTO;
import com.bomwebportal.lts.dto.form.apn.LtsApnSerialFileUploadFormDTO;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsApnUploadSuccessController extends AbstractController {
	private final String viewName = "lts/apn/ltsapnuploadsuccess";
	private final String mainView = "ltsapnserialfilemain.html";
	//private final String dnListView = "ltsapnserialfilednlistinfo.html";
	private final String commandName = "ltsApnUploadSuccessCmd";
	
	public LtsApnUploadSuccessController() {
/*		setCommandClass(LtsApnSerialFileSummaryFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);*/
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

/*		ApnSerialFileCaptureDTO apnFileCaptureDTO = LtsSessionHelper.getApnSerialFileCapture(request);
		if (apnFileCaptureDTO == null) {
			return (new ModelAndView(new RedirectView(mainView)));
		}
		return super.handleRequestInternal(request, response);*/
		
		return (new ModelAndView(viewName));
	}
	
/*	@Override
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		return (new ModelAndView(new RedirectView(mainView)));
	}*/
	
/*	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {

		return new ModelAndView(new RedirectView(mainView));
	}
	
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		
		Map<String, Object> referenceData = new HashMap<String, Object>();

		return referenceData;
	}*/
}
