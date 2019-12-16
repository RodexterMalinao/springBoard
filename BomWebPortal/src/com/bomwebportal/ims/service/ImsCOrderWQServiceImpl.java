package com.bomwebportal.ims.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.bomwebportal.ims.dao.ImsCOrderWQDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.pccw.wq.schema.dto.WorkQueueNatureDTO;


public class ImsCOrderWQServiceImpl implements ImsCOrderWQService {
	protected final Log logger = LogFactory.getLog(getClass());
	ImsCOrderWQDAO imsCOrderWQDao;
	public ImsCOrderWQDAO getImsCOrderWQDao() {
		return imsCOrderWQDao;
	}
	public void setImsCOrderWQDao(ImsCOrderWQDAO imsCOrderWQDao) {
		this.imsCOrderWQDao = imsCOrderWQDao;
	}
	public WorkQueueNatureDTO getTypeSubtypeByNatureID(String WQ_NATURE_ID) {
		WorkQueueNatureDTO wq = new WorkQueueNatureDTO();
		try {
			wq =  imsCOrderWQDao.get_TYPE_SUBTYPE_by_NATURE_ID(WQ_NATURE_ID);
		} catch (DAOException de) {
			throw new AppRuntimeException(de);
		}
		return wq;
	}
	public Boolean checkPendingAlertWQExist(String orderId) {
		try {
			return  imsCOrderWQDao.checkPendingAlertWQExist(orderId);
		} catch (DAOException de) {
			throw new AppRuntimeException(de);
		}
	}
	public void insertAlertWQRemark(String rmk, String orderId, String salesCd) {
		try {
			imsCOrderWQDao.insertAlertWQRemark(rmk, orderId, salesCd);
		} catch (DAOException de) {
			throw new AppRuntimeException(de);
		}
		
	}
	public String getChannelCd(String salesCd) {
		try {
			return imsCOrderWQDao.getChannelCd(salesCd);
		} catch (DAOException de) {
			throw new AppRuntimeException(de);
		}
	}
	public void updateStatusAlertWQ(String orderId, String assignee) {
		try {
			imsCOrderWQDao.updateStatusAlertWQ(orderId, assignee);
		} catch (DAOException de) {
			throw new AppRuntimeException(de);
		}
		
	}
	public String getSuspendReasonByCode(String code) {
		try {
			return imsCOrderWQDao.getSuspendReasonByCode(code);
		} catch (DAOException de) {
			throw new AppRuntimeException(de);
		}
	}

	public Boolean needSuspendWQ(String orderStatus, String createBy) {
		try {
			return  imsCOrderWQDao.needSuspendWQ(orderStatus,createBy);
		} catch (DAOException de) {
			throw new AppRuntimeException(de);
		}
	}
	public void insertBomwebRemark(String orderId, String remark, String createBy) {
		try {
			imsCOrderWQDao.insertBomwebRemark( orderId,  remark,  createBy);
		} catch (DAOException de) {
			throw new AppRuntimeException(de);
		}
	}

	public String getSupportDocFaxSerial(String orderId,String docType) {
		try {
			return imsCOrderWQDao.getSupportDocFaxSerial(orderId,docType);
		} catch (DAOException de) {
			throw new AppRuntimeException(de);
		}
	}
}
