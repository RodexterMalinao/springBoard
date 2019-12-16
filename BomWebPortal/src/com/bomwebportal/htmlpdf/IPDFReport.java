package com.bomwebportal.htmlpdf;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface IPDFReport {

	String getView(HttpServletRequest request);
	void fillModel(HttpServletRequest request, Map<String,Object> model);
}
