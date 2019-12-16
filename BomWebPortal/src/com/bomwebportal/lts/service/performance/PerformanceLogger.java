package com.bomwebportal.lts.service.performance;

import javax.servlet.http.HttpSession;

import com.bomwebportal.lts.dto.performance.BomwebPerformanceLogDTO;

public interface PerformanceLogger {
	public void logMyPerformance(
			BomwebPerformanceLogDTO pBomwebPerformanceLogDTO,HttpSession session) throws Exception;
}
