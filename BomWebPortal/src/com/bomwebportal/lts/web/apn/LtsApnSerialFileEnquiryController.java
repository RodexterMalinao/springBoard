package com.bomwebportal.lts.web.apn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.ApnSerialFileCaptureDTO;
import com.bomwebportal.lts.dto.apn.LtsApnFileDTO;
import com.bomwebportal.lts.dto.form.apn.LtsApnSerialFileEnquiryFormDTO;
import com.bomwebportal.lts.dto.form.apn.LtsApnSerialFileUploadFormDTO;
import com.bomwebportal.lts.service.apn.LtsApnSerialFileService;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsApnSerialFileEnquiryController extends SimpleFormController {
	private final String viewName = "lts/apn/ltsapnserialfileenquiry";
	private final String nextView = "ltsapnserialfilemain.html";
	private final String currentView = "ltsapnserialfileenquiry.html";
	private final String commandName = "ltsApnSerialFileEnquiryCmd";
	private final String dnListView = "ltsapnserialfilednlistinfo.html";
	
	private LtsApnSerialFileService ltsApnSerialFileService;
	
	public LtsApnSerialFileEnquiryController() {
		setCommandClass(LtsApnSerialFileEnquiryFormDTO.class);
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
		ApnSerialFileCaptureDTO file = LtsSessionHelper.getApnSerialFileCapture(request);
		
		LtsApnSerialFileEnquiryFormDTO form = file.getLtsApnSerialFileEnquiryForm();
		if(form == null){
			form = new LtsApnSerialFileEnquiryFormDTO();
		}	
		
		return form;
	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		LtsApnSerialFileEnquiryFormDTO form = (LtsApnSerialFileEnquiryFormDTO) command;
		ApnSerialFileCaptureDTO file = LtsSessionHelper.getApnSerialFileCapture(request);

		if(form != null){
			String fileListNum = form.getSearchFileDnDtlList();
			switch (form.getFormAction()) {
				case SEARCH:			
					List<LtsApnFileDTO> searchResult = ltsApnSerialFileService.searchForApnSerialFiles(form.getSearchUploadDateFrom(), form.getSearchUploadDateTo(), form.getSearchByStatus());
					form.setSearchResult(searchResult);
					if(searchResult != null){
						form.setTotalNumOfResults(String.valueOf(searchResult.size()));
					}
					form.setSearchPerformed(true);		
					file.setLtsApnSerialFileEnquiryForm(form);
					return new ModelAndView(new RedirectView(currentView));
				case UPDATE:
					
				case RETURN:	
					file.setLtsApnSerialFileEnquiryForm(null);
					return new ModelAndView(new RedirectView(nextView));
				case MATCH:
					if(StringUtils.isNotBlank(fileListNum) && form.getSearchResult().get(Integer.parseInt(fileListNum)) != null){
						file.setDnList(form.getSearchResult().get(Integer.parseInt(fileListNum)).getDnMatch());
						return new ModelAndView(new RedirectView(dnListView));	
					}
				case PROBLEM:	
					if(StringUtils.isNotBlank(fileListNum) && form.getSearchResult().get(Integer.parseInt(fileListNum)) != null){
						file.setDnList(form.getSearchResult().get(Integer.parseInt(fileListNum)).getDnMatchWithProblem());
						return new ModelAndView(new RedirectView(dnListView));	
					}
				case NOTMATCH:
					if(StringUtils.isNotBlank(fileListNum) && form.getSearchResult().get(Integer.parseInt(fileListNum)) != null){
						file.setDnList(form.getSearchResult().get(Integer.parseInt(fileListNum)).getDnNotMatchNnMatch());
						return new ModelAndView(new RedirectView(dnListView));	
					}
				case IGNORE:
					if(StringUtils.isNotBlank(fileListNum) && form.getSearchResult().get(Integer.parseInt(fileListNum)) != null){
						file.setDnList(form.getSearchResult().get(Integer.parseInt(fileListNum)).getDnIgnored());
						return new ModelAndView(new RedirectView(dnListView));	
					}
				case ALL:
					if(StringUtils.isNotBlank(fileListNum) && form.getSearchResult().get(Integer.parseInt(fileListNum)) != null){
						file.setDnList(form.getSearchResult().get(Integer.parseInt(fileListNum)).getAllDnRecord());
						return new ModelAndView(new RedirectView(dnListView));	
					}					
			}
		}
		
		return new ModelAndView(new RedirectView(currentView));
	}
	
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		
		Map<String, Object> referenceData = new HashMap<String, Object>();

		return referenceData;
	}

	public LtsApnSerialFileService getLtsApnSerialFileService() {
		return ltsApnSerialFileService;
	}

	public void setLtsApnSerialFileService(
			LtsApnSerialFileService ltsApnSerialFileService) {
		this.ltsApnSerialFileService = ltsApnSerialFileService;
	}
	
	
}
