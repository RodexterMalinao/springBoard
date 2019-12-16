package com.bomwebportal.lts.service.performance;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bomwebportal.lts.dto.performance.BomwebPerformanceLogDTO;

@Aspect
public class PerformanceLoggerAspect {
	
	 protected final Log logger = LogFactory.getLog(getClass());	
     private PerformanceLogger performanceLogger;
     private TaskExecutor taskExecutor;
     
	 @Pointcut("execution(* com.bomwebportal.lts.service.*.*(..)) && !execution (* com.bomwebportal.lts.service.LtsJobsImpl.*(..)) && !execution (* com.bomwebportal.lts.service.LtsSynchOrderStatusServiceImpl.*(..)) && !execution (* com.bomwebportal.lts.service.LtsWqTransServiceImpl.*(..)) "
	 		   + "|| execution(* com.bomwebportal.lts.wsClientLts.*.*(..)) "
	 		   + "|| execution(* com.bomwebportal.lts.web.*.*(..)) "
	 		   + "|| execution(* com.bomwebportal.lts.service.order.*.*(..)) "
	 		   + "|| execution(* com.bomwebportal.lts.service.order.acq.*.*(..)) "
	 		   + "|| execution(* com.bomwebportal.lts.service.acq.*.*(..)) ")
     public void ltsService(){}
     
     @Around("ltsService()")
     public Object logAround(ProceedingJoinPoint pJoinPoint) throws Throwable {
    	 return logAroundCommon(pJoinPoint);
     }
     
     private Object logAroundCommon(final ProceedingJoinPoint pJoinPoint) throws Throwable {
        final BomwebPerformanceLogDTO bomwebPerformanceLogDTO = new BomwebPerformanceLogDTO();
         

		bomwebPerformanceLogDTO.setStartTime(new Timestamp(System.currentTimeMillis()));
		logger.info("Pointcutting start...." + pJoinPoint.getSignature().getName() + "-" + bomwebPerformanceLogDTO.getStartTimeAsString());
		
		
		Object retVal = pJoinPoint.proceed();

		bomwebPerformanceLogDTO.setEndTime(new Timestamp(System.currentTimeMillis()));
		logger.info("Pointcutting end...." + pJoinPoint.getSignature().getName() + "-" + bomwebPerformanceLogDTO.getEndTimeAsString());

		ServletRequestAttributes attr = null;
		try {
			 attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		} catch (Exception e) {
			logger.info("[Error found! Ignoring]..." + e.getMessage());
		}		

		final HttpSession session  = (attr==null?null:attr.getRequest().getSession());
				
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					bomwebPerformanceLogDTO.setClassName(pJoinPoint.getTarget().getClass().getName());
					bomwebPerformanceLogDTO.setMethodName(pJoinPoint.getSignature().getName());					
					performanceLogger.logMyPerformance(bomwebPerformanceLogDTO, session);
				} catch (Exception e) {
					logger.info("[Error found! Ignoring]..." + e.getMessage());
				}
			}
		});

		return retVal;
         
     }

	public PerformanceLogger getPerformanceLogger() {
		return performanceLogger;
	}

	public void setPerformanceLogger(PerformanceLogger performanceLogger) {
		this.performanceLogger = performanceLogger;
	}

	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
}
