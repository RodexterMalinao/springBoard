/*
 * Created on Dec 02, 2011
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bomwebportal.lts.dao.bom;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.order.OrderStatusSynchDTO;

public class BomOrderStatusSynchDAO extends BaseDAO {
	
	private static final String SQL_GET_SB_BOM_STATUS =
		/* Parameters in sequence: (1) order_id, 
		                           (2) type_of_srv 
		                           (3) cc_service_id, 
		                           (4) cc_service_mem_num
		                           (5) type_of_srv
		                           (6) type_of_srv
		                           (7) srv_id
		                           (8) to_prod
		                           (9) srv_id
		                           (10)type_of_srv
		                           (11)order_id
		                           (12)oc_id
		                           (13)grp_id
		                           (14)order_id
		                           
           Ignore those with rea_cd = 3000 and cancelled as we will wait for bomweb_order_tmp to be updated	
           If the bols.last_hist_date is later than bwot.last_upd_date then ignore err_cd and err_msg	                           
		*/
		"SELECT * FROM " +  
		"(" +
		" Select * From" +
		" (" +
		"  select /*+ FIRST_ROWS */ bwot.order_id," +
        "         case " + 
        "          when bwot.last_update_date >= bols.last_hist_date THEN  "  +
        "             decode(bwot.ocid,to_char(bod.oc_id),bwot.err_cd,null) "  +
        "           else null "  + 
        "         end err_cd, "  +
        "         case  " +
        "           when bwot.last_update_date >= bols.last_hist_date THEN "   +
        "             decode(bwot.ocid,to_char(bod.oc_id),bwot.err_msg,null) " +
        "           else null  " +
        "         end err_msg, " +		
/*		"         decode(bwot.ocid,to_char(bod.oc_id),bwot.err_cd,null)  err_cd," +
		"         decode(bwot.ocid,to_char(bod.oc_id),bwot.err_msg,null) err_msg," + */
		"   	  bwot.auto_complete_ind," +
		"   	  bwot.back_date_ind," +		
		"   	  to_char(bod.oc_id) oc_id," +
		"         bod.dtl_id," +
		"         bos.srv_id," +
		"         bod.cc_service_id," +
		"         bos.cc_service_mem_num," +
		"         bod.type_of_srv," +
		"         bod.srv_req_date," +		
		"         bols.bom_status," +
		"         bols.status," +
		"         bols.rea_cd," +
		"         bols.last_hist_date," +
		"         blod.l1_ord_status," +
		"         blod.l1_rea_cd," +
		"         bl.bom_desc l1_rea_desc," +
		"         bog.grp_id," +
		"         bog.grp_type," +
		"         big.io_ind grp_io_ind," +
        "         bip.srv_nn," +
        "         boa.legacy_ord_num," +
        "         Boa.Legacy_Actv_Seq " +		
		"  from   bomweb_order_tmp bwot," +
		"         b_ord_latest_status bols," +
		"         b_ord_srv bos," +
		"         b_ord_dtl bod," +
		"         b_legacy_ord_dtl blod," +
        "         b_inv_preassgn bip," +
        "         b_ord_actv boa," +
		"         b_lookup bl," +
		"         b_ord_grp bog," +
		"         b_inst_grp big" +
		"  where  bwot.order_id   = ?" +
		"     and bod.type_of_srv = ?" +
		"     and (bod.ord_type in ('C','D') and  bod.cc_service_id = ? and (bos.cc_service_mem_num = ? or bos.cc_service_mem_num is null and ? = 'IMS')" +
		"         or bod.ord_type = 'I' and bos.srv_id = nvl(decode(?,'IMS',null,?),bos.srv_id)  " +
		"         or bod.ord_type = 'C' and ? = 'PORT-LATER'   and bos.srv_id = ? and ? = 'TEL'  " +
	    "         )" +
		"     and (bod.agreement_num  = ? or bod.oc_id = to_number(?) and bog.grp_id = ? and bog.grp_type like 'EYE%')" +
		"     and NOT (bols.rea_cd = '3000' and bols.bom_status = '07') " +
		"     and bos.oc_id       =  bols.oc_id" +
		"	  and bos.dtl_id      =  bols.dtl_id" +
		"     and bod.oc_id       =  bos.oc_id" +
		"     and bod.dtl_id      =  bos.dtl_id" +
        "     and bip.oc_id(+)    = bols.oc_id" +
        "     and bip.dtl_id(+)   = bols.dtl_id" +
        "     and bip.dtl_seq(+)  = bols.dtl_seq" +
        "     and boa.oc_id(+)    = bols.oc_id" +
        "     and boa.dtl_id(+)   = bols.dtl_id" +
        "     and boa.dtl_seq(+)  = bols.dtl_seq" +        
		"     and blod.oc_id(+)   =  bols.oc_id" +
		"     and blod.dtl_id(+)  =  bols.dtl_id" +
		"     and bl.bom_grp_id(+)=  'L1_REASON_CODE'" +
		"     and bl.bom_code(+)  =  blod.l1_rea_cd" +
		"     and big.oc_id(+)    =  bos.oc_id" +
		"     and big.dtl_id(+)   =  bos.dtl_id" +
		"     and bog.oc_id(+)    =  big.oc_id" +
		"     and bog.ord_grp_seq(+) = big.ord_grp_seq" +
		"     and nvl(bog.mark_del_ind(+),'N')  = 'N'" +
		"     and nvl(big.mark_del_ind(+),'N')  = 'N'" +
		" UNION ALL" +
		"  select bwot.order_id," +
		"         bwot.err_cd," +
		"         bwot.err_msg," +
		"   	  bwot.auto_complete_ind," +		
		"   	  bwot.back_date_ind," +		
		"         bwot.ocid oc_id," +
		"         null dtl_id," +
		"         null srv_id," +
		"         null cc_service_id," +
		"         null cc_service_mem_num," +
		"         null type_of_srv," +
		"         null srv_req_date," +			
		"         null bom_status," +
		"         null status," +
		"         null rea_cd," +		
		"         last_update_date last_hist_date," +
		"         null l1_ord_status," +
		"         null l1_rea_cd," +
		"         null l1_rea_desc," +
		"         null grp_id," +
		"         null grp_type," +
		"         null grp_io_ind," +
        "         null srv_nn," +
        "         null legacy_ord_num," +
        "         null Legacy_Actv_Seq" +    		
		"  from   bomweb_order_tmp bwot" +
		"  where  bwot.order_id = ?" +
		"   and   err_cd is not null" +
		"  ) Order by nvl(last_hist_date,to_date('19900101','YYYYMMDD')) desc, decode(grp_io_ind,'I',2,'O',0,1) desc" +
		") WHERE ROWNUM < 2";
			
	public OrderStatusSynchDTO[] getStatus(String pOrderId, String pTypeOfSrv, String pSrvNum, String pCCServiceId, String pCCServiceMemNum, String pOcId, String pGrpId, String pToProd) throws DAOException {
		
		ParameterizedRowMapper<OrderStatusSynchDTO> mapper = new ParameterizedRowMapper<OrderStatusSynchDTO>() {
			public OrderStatusSynchDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				OrderStatusSynchDTO status = new OrderStatusSynchDTO();
				status.setOrderId(rs.getString("ORDER_ID"));
				status.setOcId(rs.getString("OC_ID"));
				status.setBomDtlId(rs.getString("DTL_ID"));
				status.setSrvNum(rs.getString("SRV_ID"));
				status.setCcServiceId(rs.getString("CC_SERVICE_ID"));
				status.setCcServiceMemNum(rs.getString("CC_SERVICE_MEM_NUM"));
				status.setTypeOfSrv(rs.getString("TYPE_OF_SRV"));
				status.setBomStatus(rs.getString("BOM_STATUS"));
				status.setBomReaCd(rs.getString("REA_CD"));
				status.setBomLegacyStatus(rs.getString("STATUS"));
				status.setLastHistDate(rs.getTimestamp("LAST_HIST_DATE"));
				status.setSrvReqDate(rs.getTimestamp("SRV_REQ_DATE"));
				status.setL1ReaCd(rs.getString("L1_REA_CD"));
				status.setL1OrdStatus(rs.getString("L1_ORD_STATUS"));
				status.setErrCd(rs.getString("ERR_CD"));
				status.setErrMsg(rs.getString("ERR_MSG"));
				status.setL1ReaDesc(rs.getString("L1_REA_DESC"));
				status.setGrpId(rs.getString("GRP_ID"));
				status.setGrpType(rs.getString("GRP_TYPE"));
				status.setGrpIoInd(rs.getString("GRP_IO_IND"));
				status.setAutoCompleteInd(rs.getString("AUTO_COMPLETE_IND"));
				status.setBackDateInd(rs.getString("BACK_DATE_IND"));
				status.setSrvNN(rs.getLong("SRV_NN"));
				status.setLegacyOrdNum(rs.getString("LEGACY_ORD_NUM"));
				status.setLegacyActvSeq(rs.getInt("LEGACY_ACTV_SEQ"));
				return status;
			}
		};

		try {
			return (OrderStatusSynchDTO[])simpleJdbcTemplate.query(SQL_GET_SB_BOM_STATUS, mapper, 
					                                               pOrderId, 
					                                               pTypeOfSrv, 
					                                               pCCServiceId, 
					                                               pCCServiceMemNum, 
					                                               pTypeOfSrv,
					                                               pTypeOfSrv,
					                                               pSrvNum, 
					                                               pToProd,
					                                               pSrvNum,
					                                               pTypeOfSrv,
					                                               pOrderId,
					                                               pOcId,
					                                               pGrpId,
					                                               pOrderId).toArray(new OrderStatusSynchDTO[0]);
		} catch (Exception e) {
			logger.error("Error in getStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
