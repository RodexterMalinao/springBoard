package com.bomwebportal.ims.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.Ims1AMSFSAInfoDTO;
import com.bomwebportal.ims.dto.Ims1AMSFSAInfoListDTO;
import com.bomwebportal.ims.dto.Ims1AMSInfoWithoutPendingDTO;
import com.google.gson.Gson;

import com.bomwebportal.ims.dao.Ims1AMSEnquiryDAO;



public class Ims1AMSEnquiryServiceImpl implements Ims1AMSEnquiryService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	private Gson gson = new Gson();
	private Ims1AMSEnquiryDAO ims1AMSEnquiryDAO;	
	
	public Ims1AMSEnquiryDAO getIms1AMSEnquiryDAO() {
		return ims1AMSEnquiryDAO;
	}

	public void setIms1AMSEnquiryDAO(Ims1AMSEnquiryDAO ims1amsEnquiryDAO) {
		ims1AMSEnquiryDAO = ims1amsEnquiryDAO;
	}

	public List<Ims1AMSFSAInfoDTO> getIms1AMSFSAInfoList2(String i_serbdyno, String i_flat, String i_floor, String i_h_lot_no){

		List<Ims1AMSFSAInfoDTO> outList = new ArrayList<Ims1AMSFSAInfoDTO>();
		try{	
	
			Ims1AMSFSAInfoListDTO ims1AMSFSAInfoListDTO = ims1AMSEnquiryDAO.getIms1AMSFSAInfoList2(i_serbdyno, i_flat, i_floor, i_h_lot_no);
			
			for(int j=0; j<ims1AMSFSAInfoListDTO.getIms1AMSFSAInfoList().size(); j++)
				outList.add(ims1AMSFSAInfoListDTO.getIms1AMSFSAInfoList().get(j));

			logger.debug("getIms1AMSFSAInfoList2 return:"+outList);			
			return outList;	
		}
		catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}
	}	
		
	public Ims1AMSInfoWithoutPendingDTO getIms1AMSInfoWithoutPending(String in_FSA){
			logger.info("getIms1AMSInfoWithoutPending is called");				

			try{	
							
				Ims1AMSInfoWithoutPendingDTO ims1AMSInfoWithoutPendingDTO = ims1AMSEnquiryDAO.getIms1AMSInfoWithoutPending(in_FSA);
							
				return ims1AMSInfoWithoutPendingDTO;
			}catch (DAOException de) {			
				throw new AppRuntimeException(de);
			}
	}
		
	public String getIms1AMSBandwidth(String in_pid){
			logger.info("getRet1AMSBandwidth is called");				

			try{	
							
				String o_bandwith= ims1AMSEnquiryDAO.getIms1AMSBandwidth(in_pid);
							
				return o_bandwith;
			}catch (DAOException de) {			
				throw new AppRuntimeException(de);
			}
			
	}

	public boolean checkDs1D1ISimilarAddress(String i_serbdyno, String i_flat,
			String i_floor, String i_h_lot_no) {
		    
		try{	
			
			boolean isSimilar = ims1AMSEnquiryDAO.checkDs1D1ISimilarAddress(i_serbdyno,i_flat,i_floor,i_h_lot_no);
						
			return isSimilar;
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}
	}
}

