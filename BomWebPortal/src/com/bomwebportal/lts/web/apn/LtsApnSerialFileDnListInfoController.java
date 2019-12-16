package com.bomwebportal.lts.web.apn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.ApnSerialFileCaptureDTO;
import com.bomwebportal.lts.dto.apn.LtsApnDnDTO;
import com.bomwebportal.lts.dto.form.apn.LtsApnSerialFileDnListInfoFormDTO;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsApnSerialFileDnListInfoController extends SimpleFormController {
	private final String viewName = "lts/apn/ltsapnserialfilednlistinfo";
	private final String errorView = "ltsapnserialfileerror.html";
	private final String currentView = "ltsapnserialfilednlistinfo.html";
	private final String commandName = "ltsApnSerialFileDnListInfoCmd";
	public static final String DOC_TYPE_APN = "APN";
	
	public LtsApnSerialFileDnListInfoController() {
		setCommandClass(LtsApnSerialFileDnListInfoFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ApnSerialFileCaptureDTO apnFileCaptureDTO = LtsSessionHelper.getApnSerialFileCapture(request);
		if (apnFileCaptureDTO == null) {
			return new ModelAndView(new RedirectView(errorView));
		}
		
		return super.handleRequestInternal(request, response);
	}
	
	@Override
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		ApnSerialFileCaptureDTO file = LtsSessionHelper.getApnSerialFileCapture(request);
		
		LtsApnSerialFileDnListInfoFormDTO form = new LtsApnSerialFileDnListInfoFormDTO();
		List<LtsApnDnDTO> dnList = new ArrayList<LtsApnDnDTO>();
		
		for(LtsApnDnDTO info : file.getDnList()){
			if(DOC_TYPE_APN.equals(info.getTypeOfDoc())){
				dnList.add(info);
			}
		}
		
		Collections.sort(dnList, new Comparator<LtsApnDnDTO>(){
			public int compare(LtsApnDnDTO o1, LtsApnDnDTO o2) {
				return o1.getApnSerial().compareTo(o2.getApnSerial());
			}

		});

		form.setDnList(dnList);
		return form;
	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {

		return new ModelAndView(new RedirectView(currentView));
	}
	
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		
		Map<String, Object> referenceData = new HashMap<String, Object>();

		return referenceData;
	}
}
