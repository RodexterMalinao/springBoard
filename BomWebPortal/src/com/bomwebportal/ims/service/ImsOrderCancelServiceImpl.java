package com.bomwebportal.ims.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.ImsOrderDAO;
import com.bomwebportal.ims.dto.CancelOrderDTO;


@Transactional(readOnly = true)
public class ImsOrderCancelServiceImpl implements ImsOrderCancelService{
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsOrderDAO orderDao;
	private ReleaseLoginIDService reLoginSrv;
	
	public List<CancelOrderDTO> getPendingToCancelOrder(){
		logger.info("getPendingToCancelOrder");		
		try{
			return orderDao.getCancelOrderDetail();			
		}catch (DAOException de) {
			logger.error("Exception caught in getOcPendingOrder()", de);
			throw new AppRuntimeException(de);
		}catch (Exception e){
			logger.error("Exception caught in getOcPendingOrder()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void cancelOrder(CancelOrderDTO order){
		logger.info("cancelOrder id: "+order.getOrderId());
		try{
			
			try{
				if(order.getLoginId()!=null){				
					int retval = reLoginSrv.releaseLoginID(
							order.getLoginId(), order.getIdDocNum(), order.getIdDocType());				
					if(retval!=0){					
						orderDao.updateCanceledOrderFailStatus(order.getOrderId());
						throw new Exception("fail to release login "+order.getLoginId()+
								" during canceling order "+order.getOrderId());
					}			
				}
				orderDao.updateCanceledOrderStatus(order.getOrderId());			
			}catch (DAOException de) {
				//logger.error("Exception caught in updateCancelOrderStatus()", de);
				throw new AppRuntimeException(de);
			}catch (Exception e){
				//logger.error("Exception caught in updateCancelOrderStatus()", e);
				throw new AppRuntimeException(e);
			}
			
		}catch(Exception e){
			logger.error("", e);
		}
	}

	public ImsOrderDAO getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(ImsOrderDAO orderDao) {
		this.orderDao = orderDao;
	}

	public ReleaseLoginIDService getReLoginSrv() {
		return reLoginSrv;
	}

	public void setReLoginSrv(ReleaseLoginIDService reLoginSrv) {
		this.reLoginSrv = reLoginSrv;
	}

	

	
}
