package com.bomwebportal.lts.dao;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.pccw.util.db.OracleSelectHelper;

public class PipbLtsServiceDAO extends BaseDAO {
	
	DsServiceLtsDAO dsService;
	
	private static final String SQL_INSERT_B_INV_PREASSGN = 
			"insert into B_INV_PREASSGN " +
			"(OC_ID, DTL_ID, DTL_SEQ, NEW_SRV_NUM, JUNCTION_MESSAGE) " +
			"values (?,1,1,?,?) ";
	
	public void insertDummyForPipbDnAndReservedDn(SbOrderDTO pSbOrder) throws DAOException {
		try {
			if(dsService != null){
				String dummyOcId = OracleSelectHelper.getSqlFirstRowColumnString(dsService.getDataSource(), 
					"SELECT PREASSGN_DUMMEY_OC_ID_SEQ.NEXTVAL FROM DUAL");

				ServiceDetailLtsDTO coreLtsService = LtsSbHelper.getLtsService(pSbOrder);
				if(StringUtils.isNotBlank(dummyOcId) && coreLtsService != null){
					logger.info("PipbLtsServiceDAO.insertDummyForPipbDnAndReservedDn: DUMMY_OC_ID:" + dummyOcId + "  NEW_SRV_NUM:" + coreLtsService.getSrvNum() 
							+ "  Order ID:" + pSbOrder.getOrderId());
					simpleJdbcTemplate.update(SQL_INSERT_B_INV_PREASSGN, dummyOcId, coreLtsService.getSrvNum(), pSbOrder.getOrderId());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public DsServiceLtsDAO getDsService() {
		return dsService;
	}

	public void setDsService(DsServiceLtsDAO dsService) {
		this.dsService = dsService;
	}
	
}
