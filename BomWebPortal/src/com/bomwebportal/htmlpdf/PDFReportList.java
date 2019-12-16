package com.bomwebportal.htmlpdf;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public abstract class PDFReportList implements IPDFReport {

	public final String getView(HttpServletRequest request) {
		return null;
	}

	public final void fillModel(HttpServletRequest request, Map<String, Object> model) {
		

	}
	
	public abstract List<String> getReports(HttpServletRequest request);

}
