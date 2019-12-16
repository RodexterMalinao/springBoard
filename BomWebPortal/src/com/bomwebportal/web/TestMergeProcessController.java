package com.bomwebportal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.bomwebportal.quartz.MergingDocAutoProcess;

public class TestMergeProcessController extends AbstractController {

	private MergingDocAutoProcess process;
	
	public MergingDocAutoProcess getProcess() {
		return process;
	}

	public void setProcess(MergingDocAutoProcess process) {
		this.process = process;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		process.execute();
		return null;
	}

}
