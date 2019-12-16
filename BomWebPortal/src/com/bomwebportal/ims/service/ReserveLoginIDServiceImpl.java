package com.bomwebportal.ims.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.ims.dao.ReserveLoginIDDAO;
import com.bomwebportal.ims.wsclient.ImsWSClient;

@Transactional(readOnly=true)
public class ReserveLoginIDServiceImpl implements ReserveLoginIDService{

	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsWSClient client;
	private ReserveLoginIDDAO reserveLoginIDDao;
	
	public ImsWSClient getClient() {
		return client;
	}

	public void setClient(ImsWSClient client) {
		this.client = client;
	}

	public ReserveLoginIDDAO getReserveLoginIDDao() {
		return reserveLoginIDDao;
	}

	public void setReserveLoginIDDao(ReserveLoginIDDAO reserveLoginIDDao) {
		this.reserveLoginIDDao = reserveLoginIDDao;
	}

	public int reserveLoginID(String loginName, String idNo, String idType){
		try{
/*			ImsLoginTransaction request = new ImsLoginTransaction();
			
			request.setDomain("N");
			request.setLoginID(loginName);
			request.setIdDocNo(idNo);
			request.setIdDocType(idType);
			
			ImsLoginTransaction response = client.getClient().reserveLoginId(request);
			
			int result = Integer.parseInt(response.getReturnCode());
*/			
			//logger.info("reserveLoginID() is called in ReserveLoginIDServiceImpl.java");
			//return reserveLoginIDDao.reserveLoginID(loginName, idNo, idType);
			return client.reserveLoginID(loginName, idNo, idType);
		}catch(Exception e) {
			logger.error("Exception caught in reserveLoginID()", e);
			throw new AppRuntimeException(e);
		}
	}
		
}
