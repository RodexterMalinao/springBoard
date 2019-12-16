package com.bomwebportal.ims.service;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.CSPortalDAO;
import com.bomwebportal.ims.dao.ImsSignOffLogDAO;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.service.ImsCommonService;
import com.google.gson.Gson;
import com.pccw.util.spring.SpringApplicationContext;


public class ImsSignOffLogServiceImpl implements ImsSignOffLogService {

	protected final Log logger = LogFactory.getLog(getClass());
	private final Gson gson = new Gson();
	private ImsSignOffLogDAO logDao;
	public ImsSignOffLogDAO getLogDao() {
		return logDao;
	}

	public void setLogDao(ImsSignOffLogDAO logDao) {
		this.logDao = logDao;
	}

	public void signOffOrderLog(OrderImsUI order, String action, String errMsg) {
		try {
			logDao.signOffOrderLog(order, action, errMsg);
		} catch (Exception e) {
			String orderId="orderIsNull";
			if(order!=null){
				orderId=order.getOrderId();
			}
			logger.error("signOffOrderLog error:"+orderId, e);
		}
	}	

	public void signOffOrderLog(OrderImsUI order, String action, String errMsg, String lastUpdBy) {
		
		if(order==null){
			order = new OrderImsUI();
			order.setOrderId("StevenTesting");
			order.setImsOrderType("setImsOrderType");
			order.setCreateBy("StevenTest");
		}
		logger.info("signOffOrderLog orderId:"+order.getOrderId()+" action:"+action);
		String systemFlow = order.getImsOrderType();
		if(systemFlow==null||"".equals(systemFlow)){
			systemFlow=order.getOrderType();
		}else{
			if(order.getOrderType()!=null&&!"".equals(order.getOrderType())){
				systemFlow=systemFlow+"-"+order.getOrderType();
			}			
		}
		SpringApplicationContext.getBean(ImsCommonService.class).
		insertSignOffLog(order.getOrderId(), systemFlow, action, order.getCreateBy(), lastUpdBy, errMsg);
	}	


	public void signOffOrderLogException(OrderImsUI order, String action, Exception e) {
		try {
			action=action.length()>15 ? action.substring(0, 14) : action;
			String errMsg = ExceptionUtils.getStackTrace(e);
			errMsg=errMsg.length()>4000 ? errMsg.substring(0, 4000) : errMsg;
			logDao.signOffOrderLog(order, action, errMsg);
		} catch (Exception e2) {
			String orderId="orderIsNull";
			if(order!=null){
				orderId=order.getOrderId();
			}
			logger.error("signOffOrderLog error:"+orderId, e);
		}		
	}
	
}
