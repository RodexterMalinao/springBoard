package com.bomwebportal.ims.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.ims.dao.ImsOrderDAO;
import com.bomwebportal.ims.dao.ReleaseLoginIDDAO;
import com.bomwebportal.ims.dto.NelgninvDTO;
import com.bomwebportal.ims.wsclient.ImsWSClient;

@Transactional(readOnly=true)
public class ReleaseLoginIDServiceImpl implements ReleaseLoginIDService{

	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsWSClient client;
	private ReleaseLoginIDDAO releaseLoginIDDao;
	private ImsOrderDAO imsOrderDAO;
	
	public ImsOrderDAO getImsOrderDAO() {
		return imsOrderDAO;
	}

	public void setImsOrderDAO(ImsOrderDAO imsOrderDAO) {
		this.imsOrderDAO = imsOrderDAO;
	}

	public ImsWSClient getClient() {
		return client;
	}

	public void setClient(ImsWSClient client) {
		//System.out.println("setClient");
		this.client = client;
	}

	public ReleaseLoginIDDAO getReleaseLoginIDDao() {
		return releaseLoginIDDao;
	}

	public void setReleaseLoginIDDao(ReleaseLoginIDDAO releaseLoginIDDao) {
		this.releaseLoginIDDao = releaseLoginIDDao;
	}

	public int releaseLoginID(String loginName, String idNo, String idType){
		try{
			//System.out.println("releaseLoginID");
/*			ImsLoginTransaction request = new ImsLoginTransaction();
			
			request.setDomain("N");
			request.setLoginID(loginName);
			request.setIdDocNo(idNo);
			request.setIdDocType(idType);
			
			ImsLoginTransaction response = client.getClient().releaseLoginId(request);
			
			int result = Integer.parseInt(response.getReturnCode());
*/
			//logger.info("releaseLoginID() is called in ReserveLoginIDServiceImpl.java");
			//return releaseLoginIDDao.releaseLoginID(loginName, idNo, idType);
			return client.releaseLoginID(loginName, idNo, idType);
		}catch(Exception e) {
			logger.error("Exception caught in releaseLoginID()", e);
			throw new AppRuntimeException(e);
		}
	}


	public void releaseUselessLoginID() {
		logger.info("releaseUselessLoginID");
		try{
			try{
				List<NelgninvDTO> uselessLoginIdDetail = releaseLoginIDDao.getUselessLoginId();
				String usedLoginId = "";
				for (NelgninvDTO login : uselessLoginIdDetail){	
					
					if (imsOrderDAO.isLoginIdUsed(login.getNETLOGID())){
//						logger.info("Login ID is used: " + login.getNETLOGID());
						usedLoginId += login.getNETLOGID() + ", ";
					}else{
						int retval = this.releaseLoginID(login.getNETLOGID(), login.getIDDOCNB(), login.getIDDOCTY());
						logger.info("Released login id : " + login.getNETLOGID());
						if(retval!=0){					
							throw new Exception("fail to release login "+login.getNETLOGID());
						}	
					}
				
				}
				logger.info("Used login id: " +  usedLoginId );
			}catch (Exception e){
				logger.error("Exception caught in releaseUselessLoginID()", e);
				throw new AppRuntimeException(e);
			}
		}catch (Exception e){
			logger.error("", e);
		}
		
		
	}
	
	
	
		
}
