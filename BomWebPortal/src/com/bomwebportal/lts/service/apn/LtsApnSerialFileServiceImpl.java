package com.bomwebportal.lts.service.apn;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.apn.BomwebPipbApnDtlDAO;
import com.bomwebportal.lts.dao.apn.BomwebPipbApnFileDAO;
import com.bomwebportal.lts.dao.apn.LtsApnSerialFileDAO;
import com.bomwebportal.lts.dao.apn.LtsApnSerialRecordDtlDAO;
import com.bomwebportal.lts.dto.apn.LtsApnDnDTO;
import com.bomwebportal.lts.dto.apn.LtsApnFileDTO;
import com.bomwebportal.lts.dto.apn.LtsApnResultDTO;
import com.bomwebportal.lts.dto.apn.LtsApnSerialOrderInfoDTO;
import com.pccw.util.db.OracleSelectHelper;
import com.pccw.util.spring.SpringApplicationContext;

public class LtsApnSerialFileServiceImpl implements LtsApnSerialFileService{
	
	private LtsApnSerialFileDAO ltsApnSerialFileDAO;
	private LtsApnSerialRecordDtlDAO ltsApnSerialRecordDtlDAO;
	
	public List<LtsApnFileDTO> searchForApnSerialFiles(String pFrom, String pTo, String pStatus){
		SimpleDateFormat sbDf = new SimpleDateFormat("dd/MM/yyyy HHmmss");
		SimpleDateFormat sqlDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fromDate = "";
		String toDate = "";
		try{			
			if(StringUtils.isNotBlank(pFrom)){
				Date fromD = sbDf.parse(pFrom + " 000000");
				fromDate = sqlDf.format(fromD);
			}
			if(StringUtils.isNotBlank(pTo)){
				Date toD = sbDf.parse(pTo  + " 235959");
				toDate = sqlDf.format(toD);
			}

		} catch (Exception e){
			fromDate = "";
			toDate = "";
		}		
		
		List<LtsApnFileDTO> returnResult = new ArrayList<LtsApnFileDTO>();
		try {	
			List<LtsApnFileDTO> searchResult = new ArrayList<LtsApnFileDTO>();
			searchResult = ltsApnSerialFileDAO.getApnSerialFileResults(fromDate, toDate, pStatus);
			if(searchResult != null){
				for(LtsApnFileDTO info : searchResult){
					if(StringUtils.isNotBlank(info.getBatchSeq())){
						info = getApnSerialFileInfo(info.getBatchSeq());
					}
					returnResult.add(info);
				}
			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnResult;
	}
	
	public LtsApnFileDTO getApnSerialFileInfo(String pBatchSeq) {
		try {
			LtsApnFileDTO info = ltsApnSerialFileDAO.getApnSerialFileInfo(pBatchSeq);			
			List<LtsApnDnDTO> dnMatch = new ArrayList<LtsApnDnDTO>();
			List<LtsApnDnDTO> dnMatchWithProblem = new ArrayList<LtsApnDnDTO>();
			List<LtsApnDnDTO> dnNotMatchNnMatch = new ArrayList<LtsApnDnDTO>();
			List<LtsApnDnDTO> dnIgnored = new ArrayList<LtsApnDnDTO>();
			
			if(info != null){
				List<LtsApnDnDTO> dnList = ltsApnSerialFileDAO.getApnDtlStatusInfo(pBatchSeq, "");
				if(dnList != null){
					for(LtsApnDnDTO dn : dnList){
						// DN Matched (Without Problem) Case
						if("Y".equals(dn.getIsDnMatch())
								&& ("Y".equals(dn.getIsNnMatch()) || "NA".equals(dn.getIsNnMatch()))
								&& "Y".equals(dn.getIsDateTimeMatch())){
							dnMatch.add(dn);
						}
						
						// DN Matched (WITH Problem) Case
						if("Y".equals(dn.getIsDnMatch())
								&& ("N".equals(dn.getIsNnMatch()) || "N".equals(dn.getIsDateTimeMatch()))){
							dnMatchWithProblem.add(dn);
						}
						
						// DN not matched but NN matched
						if("N".equals(dn.getIsDnMatch())
								&& "Y".equals(dn.getIsNnMatch())){
							dnNotMatchNnMatch.add(dn);
						}
						
						// DN ignored
						if("I".equals(dn.getStatus())){
							dnIgnored.add(dn);
						}
						
					}
					info.setAllDnRecord(dnList);
					info.setDnMatch(dnMatch);
					info.setDnMatchWithProblem(dnMatchWithProblem);
					info.setDnNotMatchNnMatch(dnNotMatchNnMatch);
					info.setDnIgnored(dnIgnored);					
				}
			}
			return info;
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<LtsApnDnDTO> getApnDtlStatusInfo(String pBatchSeq, String pSrvNum){
		try{	
			return ltsApnSerialFileDAO.getApnDtlStatusInfo(pBatchSeq, pSrvNum);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}
	
	
	public void inputApnSerialFile(String pBatchSeq, String pFileName, String pFileStatus, String pFailReason, byte[] pFile, String pUser) {
		// TODO Auto-generated method stub
		if(StringUtils.isNotBlank(pFileName) && StringUtils.isNotBlank(pBatchSeq)){
			try {
				BomwebPipbApnFileDAO bomwebPipbApnFileDAO = SpringApplicationContext.getBean(BomwebPipbApnFileDAO.class);	
				bomwebPipbApnFileDAO.setBatchSeq(pBatchSeq);
				bomwebPipbApnFileDAO.setFileName(pFileName);
				bomwebPipbApnFileDAO.doSelect();
				
				bomwebPipbApnFileDAO.setFileStatus(pFileStatus);
				bomwebPipbApnFileDAO.setFailedReason(pFailReason);				
				bomwebPipbApnFileDAO.setSerialFile(pFile);
				bomwebPipbApnFileDAO.setLastUpdBy(pUser);
				bomwebPipbApnFileDAO.setCreateBy(pUser);
				
				if(StringUtils.isBlank(bomwebPipbApnFileDAO.getOracleRowID())){
					bomwebPipbApnFileDAO.doInsert();	
				}
				else{
					bomwebPipbApnFileDAO.doUpdate();
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public boolean isFileNameAvailable(String pFileName){
		// TODO Auto-generated method stub
		if(StringUtils.isNotBlank(pFileName)){
			try {
				BomwebPipbApnFileDAO criteria = SpringApplicationContext.getBean(BomwebPipbApnFileDAO.class);	
				criteria.setSearchKey("fileName", pFileName);
				BomwebPipbApnFileDAO[] bomwebPipbApnFileDAOs =  (BomwebPipbApnFileDAO[]) criteria.doSelect(null, null);

				if(bomwebPipbApnFileDAOs == null || bomwebPipbApnFileDAOs.length == 0){
					return true;
				}
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public String assignBatchSeq(){
		try {
			BomwebPipbApnFileDAO bomwebPipbApnFileDAO = SpringApplicationContext.getBean(BomwebPipbApnFileDAO.class);	
			return OracleSelectHelper.getSqlFirstRowColumnString(bomwebPipbApnFileDAO.getDataSource(), "SELECT BOMWEB_PIPB_APN_FILE_SEQ.NEXTVAL FROM DUAL");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void inputApnSerialDtlRecord(LtsApnDnDTO pInfo, String pUser) {
		// TODO Auto-generated method stub
		if(pInfo != null){
			
			try {
				BomwebPipbApnDtlDAO bomwebPipbApnDtlDAO = SpringApplicationContext.getBean(BomwebPipbApnDtlDAO.class);
				bomwebPipbApnDtlDAO.setBatchSeq(pInfo.getBatchSeq());
				bomwebPipbApnDtlDAO.setDtlSeq(pInfo.getDtlSeq());
				bomwebPipbApnDtlDAO.doSelect();
				
				BeanUtils.copyProperties(pInfo, bomwebPipbApnDtlDAO);				
				bomwebPipbApnDtlDAO.setLastUpdBy(pUser);
				if(StringUtils.isBlank(bomwebPipbApnDtlDAO.getOracleRowID())){
					bomwebPipbApnDtlDAO.setCreateBy(pUser);
					bomwebPipbApnDtlDAO.doInsert();
				}
				else{
					bomwebPipbApnDtlDAO.doUpdate();
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private LtsApnResultDTO getApnDtlResultInfo(String pSrvNum, String pOrderId, String pDtlId){
		try {
			List<LtsApnResultDTO> result = ltsApnSerialRecordDtlDAO.getApnDtlResultInfo(pSrvNum, pOrderId, pDtlId);
			
			if(result != null){
				return result.get(0);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public LtsApnDnDTO getApnDtlInfo(String pSrvNum, String pSrvNn, String pCounter, String pBatchSeq, Date pStartTime, Date pEndTime){
		try {
			LtsApnDnDTO info = new LtsApnDnDTO();
			info.setSrvNum(pSrvNum);
			info.setSrvNn(pSrvNn);
			info.setDtlSeq(pCounter);
			info.setBatchSeq(pBatchSeq);
			
			LtsApnSerialOrderInfoDTO orderInfo = ltsApnSerialRecordDtlDAO.getApnDtlOrderInfo(pSrvNum,pSrvNn);
			
			if(orderInfo != null && StringUtils.isNotBlank(orderInfo.getOrderId()) && StringUtils.isNotBlank(orderInfo.getDtlId())){
				info.setDuplexAction(orderInfo.getDuplexAction());
				
				if("".equals(orderInfo.getIsPortBack()) || "N".equals(orderInfo.getIsPortBack())){
					if(StringUtils.isNotBlank(pSrvNn) && pSrvNn.equals(orderInfo.getSrvNn())){ 
						info.setIsNnMatch("Y");
					}
					else{
						info.setIsNnMatch("N");
					}
				}
				else{
					info.setIsNnMatch("NA");
				}				
				
				LtsApnResultDTO result = getApnDtlResultInfo(pSrvNum, orderInfo.getOrderId(), orderInfo.getDtlId());
				if(result != null && orderInfo.getOrderId().equals(result.getOrderId())){
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					info.setIsDnMatch("Y");			
					info.setOrderId(result.getOrderId());
					info.setDtlId(result.getDtlId());		
					if(StringUtils.isNotBlank(result.getCutOverEndDate()) && StringUtils.isNotBlank(result.getCutOverStartDate())){
						Date startDate = df.parse(result.getCutOverStartDate());
						Date endDate = df.parse(result.getCutOverEndDate());
						if(((pStartTime.after(startDate) || pStartTime.equals(startDate)) && pStartTime.before(pEndTime))
								&& ((pEndTime.before(endDate) || pEndTime.equals(endDate)))){
							info.setIsDateTimeMatch("Y");
						}
						else{
							info.setIsDateTimeMatch("N");
						}
					}					
				}
				else{
					info.setIsDnMatch("N");
					info.setIsDateTimeMatch("N");
				}
				return info;
				
			}
						
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean isApnExist(String orderId){
		try{
			return ltsApnSerialRecordDtlDAO.isApnExist(orderId);
		} catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean isDuplexCompleted(String pOrderId){
		return ltsApnSerialRecordDtlDAO.isDuplexCompleted(pOrderId);
	}

	public LtsApnSerialFileDAO getLtsApnSerialFileDAO() {
		return ltsApnSerialFileDAO;
	}

	public void setLtsApnSerialFileDAO(LtsApnSerialFileDAO ltsApnSerialFileDAO) {
		this.ltsApnSerialFileDAO = ltsApnSerialFileDAO;
	}

	public LtsApnSerialRecordDtlDAO getLtsApnSerialRecordDtlDAO() {
		return ltsApnSerialRecordDtlDAO;
	}

	public void setLtsApnSerialRecordDtlDAO(
			LtsApnSerialRecordDtlDAO ltsApnSerialRecordDtlDAO) {
		this.ltsApnSerialRecordDtlDAO = ltsApnSerialRecordDtlDAO;
	}
}
