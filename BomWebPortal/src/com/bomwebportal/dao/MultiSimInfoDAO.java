package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.ActualUserDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoMemberDTO;
import com.bomwebportal.dto.SimDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class MultiSimInfoDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<MultiSimInfoMemberDTO> getMultiSimInfoMemberDTO(String orderId) throws DAOException{
		logger.debug("getMultiSimInfoMemberDTO() is called");

		List<MultiSimInfoMemberDTO> multiSimInfoMemberList = new ArrayList<MultiSimInfoMemberDTO>();

		/*String SQL = "select bomm.parent_order_id, bomm.member_num, bomm.member_order_id, "
				+ "bomm.msisdn, bomm.msisdnlvl, bomm.ocid, bomm.member_status, "
				+ "bomm.err_msg, bomm.err_cd, bomm.MEMBER_ORDER_TYPE, "
				+ "bomm.iccid, bomm.imsi, bomm.puk1, bomm.puk2, bomm.item_code, bomm.sim_type, "
				+ "bomm.mnp_ind, bomm.mnp_no, bomm.mnp_cut_over_date, bomm.mnp_cut_over_time, "
				+ "bomm.mnp_rno, bomm.mnp_dno, bomm.act_dno, bomm.mnp_cust_name, bomm.mnp_doc_no, "
				+ "bomm.mnp_ticket_num, bomm.prepaid_sim_doc_ind, "
				+ "bomm.csim_ind, bomm.cur_sim_iccid, "
				+ "bomm.cur_first_name, bomm.cur_last_name, bomm.cur_id_doc_type, bomm.cur_id_doc_num, "
				+ "bomm.num_type, "
				+ "bomm.same_as_cust_ind, "
				+ "bommv.basket_id, bommv.item_id sim_item_id, "
				+ "(select code_id from bomweb_code_lkup "
				+ "    where code_type = 'MULTISIM_ITEM_BASKET' "
				+ "    and code_desc = bommv.basket_id "
				+ "    and code_id in (select id from bomweb_subscribed_item "
				+ "        where type = 'VAS' "
				+ "        and order_id = ?)) item_id "
				+ "from bomweb_ord_mob_member bomm "
				+ "left join bomweb_ord_mob_member_vas bommv "
				+ "	 on bommv.parent_order_id = bomm.parent_order_id and bommv.member_num=bomm.member_num "
				+ "where bomm.parent_order_id = ? "
				+ "and bommv.item_type = 'SIM' "
				+ "order by basket_id, bomm.member_num";*/
		
		String SQL = "SELECT bomm.parent_order_id , " +
		"  bomm.member_num , " +
		"  bomm.member_order_id , " +
		"  bomm.msisdn , " +
		"  bomm.msisdnlvl , " +
		"  bomm.ocid , " +
		"  bomm.member_status , " +
		"  bomm.err_msg , " +
		"  bomm.err_cd , " +
		"  bomm.member_order_type , " +
		"  bomm.auto_ind , " +
		"  bomm.cos_ind , " +
		"  bomm.too1_ind , " +
		"  bomm.cmn_ind , " +
		"  bomm.csim_ind , " +
		"  bomm.brm_ind , " +
		"  bomm.iccid , " +
		"  bomm.imsi , " +
		"  bomm.puk1 , " +
		"  bomm.puk2 , " +
		"  bomm.item_code , " +
		"  bomm.sim_type , " +
		"  bomm.mnp_ind , " +
		"  bomm.mnp_no , " +
		"  bomm.mnp_cut_over_date , " +
		"  bomm.mnp_cut_over_time , " +
		"  bomm.mnp_rno , " +
		"  bomm.mnp_dno , " +
		"  bomm.act_dno , " +
		"  bomm.mnp_cust_name , " +
		"  bomm.mnp_doc_no , " +
		"  bomm.mnp_ticket_num , " +
		"  bomm.prepaid_sim_doc_ind , " +
		"  bomm.csim_ind , " +
		"  bomm.cur_sim_iccid , " +
		"  bomm.cur_first_name , " +
		"  bomm.cur_last_name , " +
		"  bomm.cur_id_doc_type , " +
		"  bomm.cur_id_doc_num , " +
		"  bomm.num_type , " +
		"  bomm.same_as_cust_ind , " +
		"  temp.basket_id , " +
		"  temp.sim_item_id sim_item_id , " +
		"  temp.item_id , " +
		"  bomm.cur_title , " +
		"  bomm.cur_cust_no , " +
		"  bomm.cur_acct_no , " +
		"  bomm.cur_sim_item_desc , " +
		"  bomm.cur_sim_item_cd, " +
		"  bomm.cur_brand , " +
		"  bomm.cur_sim_type , " +
		"  bomm.too1_admin_charge ," +
		"  bomm.too1_waive_reason_cd ," +
		"  bomm.opss_ind , " +
		"  bomm.starter_pack , " +
		"  bomm.brm_chg_sim_ind  " +
		"FROM bomweb_ord_mob_member bomm , " +
		"  (SELECT a.parent_order_id , " +
		"    a.member_num , " +
		"    b.basket_id , " +
		"    b.item_id rp_item_id , " +
		"    c.item_id sim_item_id , " +
		"    (SELECT code_id " +
		"    FROM bomweb_code_lkup " +
		"    WHERE code_type = 'MULTISIM_ITEM_BASKET' " +
		"    AND code_desc   = b.basket_id " +
		"    AND code_id    IN " +
		"      (SELECT id " +
		"      FROM bomweb_subscribed_item " +
		"      WHERE type   = 'VAS' " +
		"      AND order_id = a.parent_order_id " +
		"      ) " +
		"    ) item_id " +
		"  FROM bomweb_ord_mob_member a , " +
		"    bomweb_ord_mob_member_vas b , " +
		"    bomweb_ord_mob_member_vas c " +
		"  WHERE a.parent_order_id = b.parent_order_id " +
		"  AND a.member_num        = b.member_num " +
		"  AND b.item_type         = 'RP' " +
		"  AND a.parent_order_id   = c.parent_order_id(+) " +
		"  AND a.member_num        = c.member_num(+) " +
		"  AND c.item_type(+)      = 'SIM' " +
		"  AND a.parent_order_id   = ? " +
		"  ) temp " +
		"WHERE bomm.parent_order_id = ? " +
		"AND bomm.parent_order_id   = temp.parent_order_id(+) " +
		"AND bomm.member_num        = temp.member_num(+) " +
		"ORDER BY bomm.member_num, " +
		"  temp.basket_id";
		
		ParameterizedRowMapper<MultiSimInfoMemberDTO> mapper = new ParameterizedRowMapper<MultiSimInfoMemberDTO>() {
			public MultiSimInfoMemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				MultiSimInfoMemberDTO member = new MultiSimInfoMemberDTO();
				
				member.setBasketId(rs.getString("basket_id"));
				member.setItemId(rs.getString("item_id"));
				
				member.setParentOrderId(rs.getString("parent_order_id"));
				member.setMemberNum(rs.getString("member_num"));
				member.setMemberOrderId(rs.getString("member_order_id"));
				member.setMsisdn(rs.getString("msisdn"));
				member.setMsisdnlvl(rs.getString("msisdnlvl"));
				member.setOcid(rs.getString("ocid"));
				member.setMemberStatus(rs.getString("member_status"));
				member.setErrMsg(rs.getString("err_msg"));
				member.setErrCd(rs.getString("err_cd"));
				member.setSelectedSimItemId(rs.getString("sim_item_id"));
				member.setMemberOrderType(rs.getString("member_order_type"));
				member.setAutoInd(rs.getString("auto_ind"));
				member.setCosInd(rs.getString("cos_ind"));
				member.setToo1Ind(rs.getString("too1_ind"));
				member.setCmnInd(rs.getString("cmn_ind"));
				member.setCsimInd(rs.getString("csim_ind"));
				member.setBrmInd(rs.getString("brm_ind"));
				member.setCsimInd(rs.getString("csim_ind"));
				member.setCurSimIccid(rs.getString("cur_sim_iccid"));
				member.setCurFirstName(rs.getString("cur_first_name"));
				member.setCurLastName(rs.getString("cur_last_name"));
				member.setCurIdDocType(rs.getString("cur_id_doc_type"));
				member.setCurIdDocNum(rs.getString("cur_id_doc_num"));
				member.setNumType(rs.getString("num_type"));
				member.setSameAsCustInd(rs.getString("same_as_cust_ind"));
				member.setBrmChgSimInd(rs.getString("brm_chg_sim_ind"));
				
				SimDTO sim = new SimDTO();
				sim.setIccid(rs.getString("iccid"));
				sim.setImsi(rs.getString("imsi"));
				sim.setPuk1(rs.getString("puk1"));
				sim.setPuk2(rs.getString("puk2"));
				sim.setItemCode(rs.getString("item_code"));
				sim.setSimType(rs.getString("sim_type"));
				member.setSim(sim);
				
				member.setMnpInd(rs.getString("mnp_ind"));
				member.setMnpRno(rs.getString("mnp_rno")); //DENNIS MIP3
				
				if ("A".equals(member.getMnpInd()) || "MUPS05".equals(member.getMemberOrderType())) {
					member.setMnpNumber(rs.getString("mnp_no"));
					member.setMnpCutOverDate(Utility.date2String(rs.getTimestamp("MNP_CUT_OVER_DATE"), "dd/MM/yyyy"));
					member.setMnpCutOverTime(rs.getString("mnp_cut_over_time"));
					//member.setMnpRno(rs.getString("mnp_rno"));
					member.setMnpDno(rs.getString("mnp_dno"));
					member.setActualDno(rs.getString("act_dno"));
					member.setMnpCustName(rs.getString("mnp_cust_name"));
					member.setMnpDocNo(rs.getString("mnp_doc_no"));
					member.setMnpTicketNum(rs.getString("mnp_ticket_num"));
					member.setPrePaidSimDocInd(rs.getString("prepaid_sim_doc_ind"));
					member.setOpssInd(rs.getString("opss_ind"));
					member.setStarterPack(rs.getString("starter_pack"));
				}
				
				if ("MUPS04".equals(member.getMemberOrderType())) {
					member.setCurTitle(rs.getString("cur_title"));
					member.setCurCustNo(rs.getString("cur_cust_no"));
					member.setCurAcctNo(rs.getString("cur_acct_no"));
					member.setCurSimItemDesc(rs.getString("cur_sim_item_desc"));
					member.setCurSimItemCd(rs.getString("cur_sim_item_cd"));
					member.setSubBrand(rs.getString("cur_brand"));
					member.setSubSimType(rs.getString("cur_sim_type"));
					member.setToo1AdminCharge(rs.getString("too1_admin_charge"));
					member.setToo1WaiveReasonCd(rs.getString("too1_waive_reason_cd"));
					if ("".equalsIgnoreCase(member.getToo1WaiveReasonCd())) {
						member.setToo1AdminChargeInd("Charge");
					} else {
						member.setToo1AdminChargeInd("Waive");
					}
					member.setMnpCutOverDate(Utility.date2String(rs.getTimestamp("MNP_CUT_OVER_DATE"), "dd/MM/yyyy"));
					member.setMnpCutOverTime(rs.getString("mnp_cut_over_time"));
				}
				
				return member;
			}
		};

		try {
			multiSimInfoMemberList = simpleJdbcTemplate.query(SQL, mapper, orderId, orderId);

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Exception caught in getMultiSimInfoMemberDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return multiSimInfoMemberList;
	}
	
	public List<String> getSelectedVASList(String orderId, String memberNum) throws DAOException {
		logger.debug("getSelectedVASList is called");
		List<String> selectedVASList = new ArrayList();
		String SQL = "select item_id from bomweb_ord_mob_member_vas "
				+ "where parent_order_id = ? "
				+ "and member_num = ? "
				+ "and item_type = 'VAS'";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String itemId = new String();
				itemId = rs.getString("item_id");
				return itemId;
			}
		};
		try {
			selectedVASList = simpleJdbcTemplate.query(SQL, mapper, orderId, memberNum);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getBundleVASList() getSelectedVASList");
			selectedVASList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getSelectedVASList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return selectedVASList;
	}
	
	public void updateMultiSimMemberStatus(String parentOrderId, String memberOrderId, String status, String errCode, String errString , String lastUpdateBy)
			throws DAOException {
		logger.debug("updateMultiSimMemberStatus is called");
		logger.info("update orderId : " + memberOrderId);
		logger.info("update status : " + status);

		String SQL = "UPDATE bomweb_ord_mob_member " +
				"SET member_status     = :status ," +
				" err_cd = :errCode, " +
				" err_msg = :errString, " +
				" last_upd_by = :lastUpdateBy, " +
				" last_upd_date = sysdate " +
				//"WHERE parent_order_id = nvl(:parentOrderId,parent_order_id) " +
				"WHERE parent_order_id = :parentOrderId " +
				"AND member_order_id = decode(:memberOrderId,'ALL',member_order_id,:memberOrderId) ";
		logger.debug("SQL : " + SQL);
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();	
			params.addValue("parentOrderId", parentOrderId);
			params.addValue("memberOrderId", memberOrderId);
			params.addValue("status", status);
			params.addValue("errCode", errCode);
			params.addValue("errString", errString);
			params.addValue("lastUpdateBy", lastUpdateBy);
			simpleJdbcTemplate.update(SQL, params);

		} catch (Exception e) {
			logger.error("Exception caught in updateMultiSimMemberStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public void cancelMultiSimMember(String parentOrderId, String lastUpdateBy)
			throws DAOException {
		logger.debug("cancelMultiSimMember is called");

		String SQL = "UPDATE bomweb_ord_mob_member a " +
				"SET a.member_status     = decode(a.ocid,null,'"+BomWebPortalConstant.BWP_MULTISIM_ORDER_CANCELLED+"','"+BomWebPortalConstant.BWP_MULTISIM_ORDER_CANCELLING+"') ," +
				" last_upd_by = :lastUpdateBy, " +
				" last_upd_date = sysdate " +
				"WHERE parent_order_id = :parentOrderId";
		
		logger.debug("SQL : " + SQL);
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();	
			params.addValue("parentOrderId", parentOrderId);
			params.addValue("lastUpdateBy", lastUpdateBy);
			simpleJdbcTemplate.update(SQL, params);

		} catch (Exception e) {
			logger.error("Exception caught in cancelMultiSimMember()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public String validateSBPendingOrder(List<String> mrtList, String orderId) {
		logger.debug("validateSBPendingOrder is called");
		List<String> orderIdList = new ArrayList<String>();
		String SQL = "select order_id from bomweb_order o where order_id in "
				+ "(select order_id from bomweb_mrt m "
				+ "where m.msisdn in :mrtList"
				+ "	UNION "
				+ "select parent_order_id from bomweb_ord_mob_member omm "
				+ "where  omm.msisdn in :mrtList) "
				+ "and o.order_status not in ('VOID','SUCCESS','CANCELLED', '02', '04') ";
		MapSqlParameterSource params = new MapSqlParameterSource();	
		params.addValue("mrtList", mrtList);
		if (orderId != null) {
				SQL += "and o.order_id != :orderId";
				params.addValue("orderId", orderId);
		}
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String orderId = rs.getString("order_id");
				return orderId;
			}
		};
		
		try {
			orderIdList = simpleJdbcTemplate.query(SQL, mapper, params);
			if (orderIdList != null && orderIdList.size() > 0) {
				return orderIdList.get(0);
			} else {
				return null;
			}
		} catch (DataAccessException e) {
			logger.error("Exception caught in validateSBPendingOrder()", e);
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	public List<String> getDBSecondaryMRTs(String orderId) {
		logger.debug("getDBSecondaryMRTs is called");
		List<String> mrtList = new ArrayList<String>();
		String SQL = "select msisdn from bomweb_ord_mob_member where parent_order_id = :orderId";
		MapSqlParameterSource params = new MapSqlParameterSource();	
		params.addValue("orderId", orderId);
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String msisdn = rs.getString("msisdn");
				return msisdn;
			}
		};
		
		try {
			mrtList = simpleJdbcTemplate.query(SQL, mapper, params);
			return mrtList;
		} catch (DataAccessException e) {
			logger.error("Exception caught in validateSBPendingOrder()", e);
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	public List<MultiSimInfoMemberDTO> getRetMultiSimInfoMemberDTO(String orderId) throws DAOException{
		logger.debug("getMultiSimInfoMemberDTO() is called");

		List<MultiSimInfoMemberDTO> multiSimInfoMemberList = new ArrayList<MultiSimInfoMemberDTO>();

		String SQL = "select temp.parent_order_id\n" +
				",temp.member_num\n" +
				",temp.member_order_id\n" +
				",temp.msisdn\n" +
				",temp.msisdnlvl\n" +
				",temp.ocid\n" +
				",temp.member_status\n" +
				",temp.err_msg\n" +
				",temp.err_cd\n" +
				",temp.member_order_type\n" +
				",temp.iccid\n" +
				",temp.imsi\n" +
				",temp.puk1\n" +
				",temp.puk2\n" +
				",temp.item_code\n" +
				",temp.sim_type\n" +
				",temp.mnp_ind\n" +
				",temp.mnp_no\n" +
				",temp.mnp_cut_over_date\n" +
				",temp.mnp_cut_over_time\n" +
				",temp.mnp_rno\n" +
				",temp.mnp_dno\n" +
				",temp.act_dno\n" +
				",temp.mnp_cust_name\n" +
				",temp.mnp_doc_no\n" +
				",temp.mnp_ticket_num\n" +
				",temp.prepaid_sim_doc_ind\n" + 
				",temp.num_type\n" +
				",temp.basket_id\n" +
				",temp.nfc_ind " +
				",temp.same_as_cust_ind " +
				",temp.brm_chg_sim_ind " + 
				"      ,(select code_id\n" +
				"        from bomweb_code_lkup\n" +
				"        where code_type = 'MULTISIM_ITEM_BASKET'\n" +
				"        and code_desc = temp.basket_id\n" +
				"        and code_id in (select id\n" +
				"           from bomweb_subscribed_item\n" +
				"           where type = 'VAS'\n" +
				"           and order_id = ? )) item_id\n" +
				",temp.csim_ind, temp.cur_sim_iccid, temp.cur_first_name, temp.cur_last_name, temp.cur_id_doc_type, temp.cur_id_doc_num\n " + 
				",  temp.opss_ind " +
				",  temp.starter_pack  " + 
				"from (select bomm.parent_order_id\n" +
				",bomm.member_num\n" +
				",bomm.member_order_id\n" +
				",bomm.msisdn\n" +
				",bomm.msisdnlvl\n" +
				",bomm.ocid\n" +
				",bomm.member_status\n" +
				",bomm.err_msg\n" +
				",bomm.err_cd\n" +
				",bomm.member_order_type\n" +
				",bomm.iccid\n" +
				",bomm.imsi\n" +
				",bomm.puk1\n" +
				",bomm.puk2\n" +
				",bomm.item_code\n" +
				",bomm.mnp_ind\n" +
				",bomm.mnp_no\n" +
				",bomm.mnp_cut_over_date\n" +
				",bomm.mnp_cut_over_time\n" +
				",bomm.mnp_rno\n" +
				",bomm.mnp_dno\n" +
				",bomm.act_dno\n" +
				",bomm.mnp_cust_name\n" +
				",bomm.mnp_doc_no\n" +
				",bomm.mnp_ticket_num\n" +
				",bomm.prepaid_sim_doc_ind\n" +
				",bomm.num_type\n" +
				",bomm.sim_type\n" +
				",bomm.same_as_cust_ind\n" +
				",bomm.csim_ind, bomm.cur_sim_iccid " +
				",bomm.cur_first_name, bomm.cur_last_name, bomm.cur_id_doc_type, bomm.cur_id_doc_num, bomm.nfc_ind " + 
				",bomm.opss_ind " +
				",bomm.starter_pack " + 
				",bomm.brm_chg_sim_ind " + 
				",(select bommv.basket_id\n" +
				"  from bomweb_ord_mob_member_vas bommv\n" +
				"  where bommv.basket_id is not null\n" +
				"  and bommv.parent_order_id = bomm.parent_order_id\n" +
				"  and bommv.member_num = bomm.member_num\n" +
				"  and rownum = 1) basket_id\n" +
				"      from bomweb_ord_mob_member bomm\n" +
				"      where bomm.parent_order_id = ? " +
				"      and bomm.member_order_type !='MUPS99') temp\n" +

				"order by basket_id, member_num";

				;


		ParameterizedRowMapper<MultiSimInfoMemberDTO> mapper = new ParameterizedRowMapper<MultiSimInfoMemberDTO>() {
			public MultiSimInfoMemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				MultiSimInfoMemberDTO member = new MultiSimInfoMemberDTO();
				member.setBasketId(rs.getString("basket_id"));
				member.setItemId(rs.getString("item_id"));	
				member.setParentOrderId(rs.getString("parent_order_id"));
				member.setMemberNum(rs.getString("member_num"));
				member.setMemberOrderId(rs.getString("member_order_id"));
				member.setMsisdn(rs.getString("msisdn"));
				member.setMsisdnlvl(rs.getString("msisdnlvl"));
				member.setOcid(rs.getString("ocid"));
				member.setMemberStatus(rs.getString("member_status"));
				member.setErrMsg(rs.getString("err_msg"));
				member.setErrCd(rs.getString("err_cd"));
				member.setMemberOrderType(rs.getString("MEMBER_ORDER_TYPE"));
				member.setCsimInd(rs.getString("csim_ind"));
				member.setCurSimIccid(rs.getString("cur_sim_iccid"));
				member.setCurFirstName(rs.getString("cur_first_name"));
				member.setCurLastName(rs.getString("cur_last_name"));
				member.setCurIdDocType(rs.getString("cur_id_doc_type"));
				member.setCurIdDocNum(rs.getString("cur_id_doc_num"));
				member.setNfcInd(rs.getString("nfc_ind"));
				member.setNumType(rs.getString("num_type"));
				member.setSameAsCustInd(rs.getString("same_as_cust_ind"));
				member.setBrmChgSimInd(rs.getString("brm_chg_sim_ind"));
				
				SimDTO sim = new SimDTO();
				sim.setIccid(rs.getString("iccid"));
				sim.setImsi(rs.getString("imsi"));
				sim.setPuk1(rs.getString("puk1"));
				sim.setPuk2(rs.getString("puk2"));
				sim.setItemCode(rs.getString("item_code"));
				sim.setSimType(rs.getString("sim_type"));
				member.setSim(sim);
				
				member.setMnpInd(rs.getString("mnp_ind"));
				
				member.setMnpRno(rs.getString("mnp_rno")); //DENNIS MIP3
				
				if ("A".equals(member.getMnpInd()) || "MUPS05".equals(member.getMemberOrderType())) {
					member.setMnpNumber(rs.getString("mnp_no"));
					member.setMnpCutOverDate(Utility.date2String(rs.getTimestamp("MNP_CUT_OVER_DATE"), "dd/MM/yyyy"));
					member.setMnpCutOverTime(rs.getString("mnp_cut_over_time"));
					//member.setMnpRno(rs.getString("mnp_rno")); //DENNIS MIP3
					member.setMnpDno(rs.getString("mnp_dno"));
					member.setActualDno(rs.getString("act_dno"));
					member.setMnpCustName(rs.getString("mnp_cust_name"));
					member.setMnpDocNo(rs.getString("mnp_doc_no"));
					member.setMnpTicketNum(rs.getString("mnp_ticket_num"));
					member.setPrePaidSimDocInd(rs.getString("prepaid_sim_doc_ind"));
					member.setOpssInd(rs.getString("opss_ind"));
					member.setStarterPack(rs.getString("starter_pack"));
				}
				
				return member;
			}
		};

		try {
			multiSimInfoMemberList = simpleJdbcTemplate.query(SQL, mapper, orderId, orderId);

		} catch (Exception e) {
			logger.info("Exception caught in getMultiSimInfoMemberDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}

		return multiSimInfoMemberList;
	}
	
	public ActualUserDTO getActualUser(String orderId, String memberNum)
			throws DAOException {
			logger.debug("getActualUser() is called");
			List<ActualUserDTO> actualUserList = new ArrayList<ActualUserDTO>();
			
				String SQL =
						"select A.ORDER_ID\n" +
						"      ,A.ORDER_TYPE\n" + 
						"      ,A.MEMBER_NUM\n" + 
						"      ,A.SUB_DOC_NUM\n" + 
						"      ,A.SUB_DOC_TYPE\n" + 
						"      ,A.SUB_COMPANY_NAME\n" + 
						"      ,A.SUB_TITLE\n" + 
						"      ,A.SUB_LAST_NAME\n" + 
						"      ,A.SUB_FIRST_NAME\n" + 
						"      ,A.SUB_CONTACT_TEL\n" + 
						"      ,A.SUB_EMAIL_ADDR\n" + 
						"      ,trunc(A.SUB_DATE_OF_BIRTH) SUB_DATE_OF_BIRTH\n" + 
						"from BOMWEB_MOB_ACTUAL_USER  A\n" + 
						"where A.ORDER_TYPE = 'M'\n" + 
						"and A.ORDER_ID = ?\n" +
						"and A.MEMBER_NUM = ?\n";
	
			ParameterizedRowMapper<ActualUserDTO> mapper = new ParameterizedRowMapper<ActualUserDTO>() {
				public ActualUserDTO mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					ActualUserDTO actualUser = new ActualUserDTO();
			
					actualUser.setOrderId(rs.getString("ORDER_ID"));
					actualUser.setOrderType(rs.getString("ORDER_TYPE"));
					actualUser.setMemberNum(rs.getString("MEMBER_NUM"));
					actualUser.setSubDocNum(rs.getString("SUB_DOC_NUM"));
					actualUser.setSubDocType(rs.getString("SUB_DOC_TYPE"));
					actualUser.setSubCompanyName(rs.getString("SUB_COMPANY_NAME"));
					actualUser.setSubTitle(rs.getString("SUB_TITLE"));
					actualUser.setSubLastName(rs.getString("SUB_LAST_NAME"));
					actualUser.setSubFirstName(rs.getString("SUB_FIRST_NAME"));
					actualUser.setSubContactTel(rs.getString("SUB_CONTACT_TEL"));
					actualUser.setSubEmailAddr(rs.getString("SUB_EMAIL_ADDR"));			
					actualUser.setSubDateOfBirthStr(Utility.date2String(rs.getDate("SUB_DATE_OF_BIRTH"), "dd/MM/yyyy"));

					return actualUser;
				}
			};
			
			try {
				logger.debug("getActualUser() @ CustomerProfileDAO: " + SQL);
				actualUserList = simpleJdbcTemplate.query(SQL, mapper, orderId, memberNum);
			
				if (actualUserList == null || actualUserList.isEmpty()) {
					return null;
				}
				
			} catch (EmptyResultDataAccessException erdae) {
				logger.info("EmptyResultDataAccessException");
			
				actualUserList = null;
			} catch (Exception e) {
				logger.info("Exception caught in getCosCustomerProfile():", e);
				throw new DAOException(e.getMessage(), e);
			}
			
			return actualUserList.get(0);// only return the first one
			
	}
	
	public SimDTO getBrmChgSimInfo(String orderId) throws DAOException {
		logger.debug("getBrmChgSimInfo() is called");
		List<SimDTO> simList = new ArrayList<SimDTO>();
		String SQL = "select sim_iccid, sim_item_cd from bomweb_ord_mob_chg_sim_txn where order_id = :orderId and mark_del_ind <> 'Y'";
		ParameterizedRowMapper<SimDTO> mapper = new ParameterizedRowMapper<SimDTO>() {
			public SimDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				SimDTO sim = new SimDTO();
				sim.setIccid(rs.getString("sim_iccid"));
				sim.setSimType(rs.getString("sim_item_cd"));
				return sim;
			}
		};
		try {
			logger.debug("getBrmChgSimInfo(): " + SQL);
			simList = simpleJdbcTemplate.query(SQL, mapper, orderId);
			if (simList == null || simList.isEmpty()) {
				return null;
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			simList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getBrmChgSimInfo():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return simList.get(0);
	}
}
