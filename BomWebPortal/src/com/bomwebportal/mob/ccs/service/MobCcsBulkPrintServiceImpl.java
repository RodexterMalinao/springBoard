package com.bomwebportal.mob.ccs.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsBulkPrintDAO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsBulkPrintDTO;

@Transactional(readOnly=true)
public class MobCcsBulkPrintServiceImpl implements MobCcsBulkPrintService {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private MobCcsBulkPrintDAO mobCcsBulkPrintDAO;
	
	public MobCcsBulkPrintDAO getMobCcsBulkPrintDAO() {
		return mobCcsBulkPrintDAO;
	}

	public void setMobCcsBulkPrintDAO(MobCcsBulkPrintDAO mobCcsBulkPrintDAO) {
		this.mobCcsBulkPrintDAO = mobCcsBulkPrintDAO;
	}

	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public Date getNextWorkingDay(Date jobDate) {
		try {
			logger.info("getNextWorkingDay() is called in MobCcsBulkPrintServiceImpl");
			return this.mobCcsBulkPrintDAO.getNextNWorkingDay(jobDate, 1);
		} catch (DAOException de) {
			logger.error("Exception caught in getNextWorkingDay()", de);
			throw new AppRuntimeException(de);
		}
	}
	
    @Transactional(readOnly=true, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<MobCcsBulkPrintDTO> getBulkPrintDTOALLBySearch(
			Date processDate
			, List<Map<String, String>> statuses, String location) {
		try {
			logger.info("getBulkPrintDTOALLBySearch() is called in MobCcsBulkPrintServiceImpl");
			List<MobCcsBulkPrintDTO> list = this.mobCcsBulkPrintDAO.getMobCcsBulkPrintDTOALLBySearch(processDate, statuses, location);
			this.updateSalesFallout(list);
			return list;
		} catch (DAOException de) {
			logger.error("Exception caught in getBulkPrintDTOALLBySearch()", de);
			throw new AppRuntimeException(de);
		}
	}

	private void updateSalesFallout(List<MobCcsBulkPrintDTO> bulkPrintDtos) {
		if (this.isEmpty(bulkPrintDtos)) {
			return;
		}
		Map<String, List<CodeLkupDTO>> entityCodeMap = LookupTableBean.getInstance().getCodeLkupList();
		List<CodeLkupDTO> salesFalloutCds = entityCodeMap.get("MOB_SALES_FALLOUT_CD");
		for (MobCcsBulkPrintDTO bulkPrintDto : bulkPrintDtos) {
			if (StringUtils.isNotBlank(bulkPrintDto.getReasonCd())) {
				for (CodeLkupDTO salesFalloutCd : salesFalloutCds) {
					if (bulkPrintDto.getReasonCd().equals(salesFalloutCd.getCodeId())) {
						bulkPrintDto.setSalesFalloutFlag(true);
						break;
					}
				}
			}
		}
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
}
