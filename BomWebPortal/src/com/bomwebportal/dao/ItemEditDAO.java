package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.*;
import com.bomwebportal.exception.*;


public class ItemEditDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());
	
	public String getItemId(String type, String env){
		String itemId;
		String SQL="";
		//String env="prd";
		env=env.toLowerCase();
		
		if (env.equals("dev") || env.equals("uat") ){
			SQL="SELECT MAX (ID) + 1 new_id FROM w_item  WHERE TYPE = ? and ID < 100000000";
		}
		else if (env.equals("prd")){
			
			if ("HSRB".equals(type)){
				
				SQL="select max(id)+1 from w_item where type='HSRB'and  id < 104000000";
			}else{
				SQL="SELECT MAX (ID) + 1 new_id FROM w_item  WHERE TYPE = ? and ID >100000000";
				
			}
		}

		try {

			itemId = (String)	simpleJdbcTemplate.queryForObject(SQL,  String.class, type );
		} catch (EmptyResultDataAccessException erdae) {
			itemId = null;
		} 
		logger.info("getItemId: " + itemId);
		return itemId;
	}
	

	public ItemEditDTO getItem(int itemId) throws DAOException {
		logger.info("getItem() is called");

		List<ItemEditDTO> itemList = new ArrayList<ItemEditDTO>();

		/*StringBuilder SQL= new StringBuilder();
		SQL.append(" SELECT I.ID,  I.LOB,  I.TYPE,  I.DESCRIPTION,   \n");
		SQL.append(" I.CREATE_BY,  I.CREATE_DATE,  I.LAST_UPD_BY,  I.LAST_UPD_DATE, \n");
		SQL.append("   --IDH.ID, \n");
		SQL.append("   IDH.BRAND_ID,  IDH.MODEL_ID,  IDH.COLOR_ID,  IDH.CONTRACT_PERIOD HS_CONTRACT_PERIOD, \n");
		SQL.append("   --IDRRV.ID, \n");
		SQL.append("   IDRRV.RP_TYPE,  IDRRV.CONTRACT_PERIOD RP_RB_VAS_CONTRACT_PERIOD,  IDRRV.REBATE_PERIOD, \n");
		SQL.append("   IDRRV.REBATE_SCHEDULE,  IDRRV.REBATE_AMT,  IDRRV.PENALTY_TYPE,  IDRRV.PENALTY_AMT \n");
		SQL.append(" FROM W_ITEM I,  W_ITEM_DTL_HS IDH,  W_ITEM_DTL_RP_RB_VAS IDRRV \n");
		SQL.append(" WHERE I.ID = IDH.ID(+) \n");
		SQL.append(" AND I.ID   = IDRRV.ID(+)  \n");
		SQL.append(" AND I.ID = ? \n");*/
		//id:102000001
		StringBuilder SQL= new StringBuilder();
		SQL.append(" SELECT i.ID, i.LOB, i.TYPE, i.description, i.create_by, \n");
		SQL.append("        TO_CHAR (i.create_date, 'yyyy/mm/dd hh24:mi:ss') create_date, \n");
		SQL.append("        i.last_upd_by, \n");
		SQL.append("        TO_CHAR (i.last_upd_date, 'yyyy/mm/dd hh24:mi:ss') last_upd_date, \n");
		SQL.append("        --IDH.ID, \n");
		SQL.append("        idh.brand_id, \n");
		SQL.append("        (SELECT description \n");
		SQL.append("           FROM w_display_lkup \n");
		SQL.append("          WHERE TYPE = 'BRAND' \n");
		SQL.append("            AND locale = 'en' \n");
		SQL.append("            AND ID = idh.brand_id) brand_name, \n");
		SQL.append("        idh.model_id, \n");
		SQL.append("        (SELECT description \n");
		SQL.append("           FROM w_display_lkup \n");
		SQL.append("          WHERE TYPE = 'MODEL' \n");
		SQL.append("            AND locale = 'en' \n");
		SQL.append("            AND ID = idh.model_id) model_name, \n");
		SQL.append("        idh.color_id, \n");
		SQL.append("        (SELECT description \n");
		SQL.append("           FROM w_display_lkup \n");
		SQL.append("          WHERE TYPE = 'COLOR' \n");
		SQL.append("            AND locale = 'en' \n");
		SQL.append("            AND ID = idh.color_id) color_name, \n");
		SQL.append("        idh.contract_period hs_contract_period, \n");
		SQL.append("        --IDRRV.ID, \n");
		SQL.append("        idrrv.rp_type, \n");
		SQL.append("        idrrv.contract_period rp_rb_vas_contract_period, idrrv.rebate_period, \n");
		SQL.append("        idrrv.rebate_schedule, idrrv.rebate_amt, idrrv.penalty_type, \n");
		SQL.append("        idrrv.penalty_amt, \n");
		SQL.append("        --pricing \n");
		SQL.append("        TO_CHAR (pricing_t.eff_start_date,'yyyy/mm/dd') eff_start_date, \n");
		SQL.append("        TO_CHAR (pricing_t.eff_end_date, 'yyyy/mm/dd') eff_end_date, \n");
		SQL.append("        pricing_t.onetime_type, pricing_t.onetime_amt, pricing_t.recurrent_amt \n");
		SQL.append("   FROM w_item i, \n");
		SQL.append("        w_item_dtl_hs idh, \n");
		SQL.append("        w_item_dtl_rp_rb_vas idrrv, \n");
		SQL.append("        (SELECT ip.ID, ip.eff_start_date, ip.eff_end_date, ip.onetime_type, \n");
		SQL.append("                ip.onetime_amt, ip.recurrent_amt \n");
		SQL.append("           FROM ops$cnm.w_item_pricing ip \n");
		SQL.append("          WHERE ip.ID = ? \n");
		SQL.append("            AND ip.eff_start_date = (SELECT MAX (ip2.eff_start_date) \n");
		SQL.append("                                       FROM w_item_pricing ip2 \n");
		SQL.append("                                      WHERE ip2.ID = ?)) pricing_t \n");
		SQL.append("  WHERE i.ID = pricing_t.ID(+) AND i.ID = idh.ID(+) AND i.ID = idrrv.ID(+) \n");
		SQL.append("        AND i.ID = ? \n");


		ParameterizedRowMapper<ItemEditDTO> mapper = new ParameterizedRowMapper<ItemEditDTO>() {
			public ItemEditDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ItemEditDTO dto = new ItemEditDTO();
				dto.setId(rs.getInt("ID"));
				dto.setLob(rs.getString("LOB"));
				dto.setType(rs.getString("TYPE"));
				dto.setDescription(rs.getString("DESCRIPTION"));
				dto.setCreateBy(rs.getString("CREATE_BY"));
				dto.setCreateDate(rs.getString("CREATE_DATE"));
				dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				dto.setLastUpdDate(rs.getString("LAST_UPD_DATE"));

				dto.setBrandId(rs.getString("BRAND_ID"));
				dto.setModelId(rs.getString("MODEL_ID"));
				dto.setColorId(rs.getString("COLOR_ID"));
			//	dto.setBrandName(rs.getString("BRAND_NAME"));
				//dto.setModelName(rs.getString("MODEL_NAME"));
				//dto.setColorName(rs.getString("COLOR_NAME"));
				dto.setHsContractPeriod(rs.getString("HS_CONTRACT_PERIOD"));

				dto.setRpType(rs.getString("RP_TYPE"));
				dto.setRpRbVasContractPeriod(rs.getString("RP_RB_VAS_CONTRACT_PERIOD"));
				dto.setRebatePeriod(rs.getString("REBATE_PERIOD"));
				dto.setRebateSchedule(rs.getString("REBATE_SCHEDULE"));
				dto.setRebateAmt(rs.getString("REBATE_AMT"));
				dto.setPenaltyType(rs.getString("PENALTY_TYPE"));
				dto.setPenaltyAmt(rs.getString("PENALTY_AMT"));
				
				
				dto.setEffStartDate(rs.getString("EFF_START_DATE"));
				dto.setEffEndDate(rs.getString("EFF_END_DATE"));
				dto.setOnetimeType(rs.getString("ONETIME_TYPE"));
				dto.setOnetimeAmt(rs.getString("ONETIME_AMT"));
				dto.setRecurrentAmt(rs.getString("RECURRENT_AMT"));
				
				dto.setEffStartDateOriginal(rs.getString("EFF_START_DATE"));
				dto.setEffEndDateOriginal(rs.getString("EFF_END_DATE"));
				dto.setOnetimeTypeOriginal(rs.getString("ONETIME_TYPE"));
				dto.setOnetimeAmtOriginal(rs.getString("ONETIME_AMT"));
				dto.setRecurrentAmtOriginal(rs.getString("RECURRENT_AMT"));
				

				return dto;
			}
		};

		try {
			//herbert 20111110 - remove useless SQL logger
			logger.info("getItem() @ ItemEditDAO: " );
			logger.debug("getItem() @ ItemEditDAO: " + SQL);
			itemList = simpleJdbcTemplate.query(SQL.toString(), mapper, itemId,itemId,itemId);
			if (itemList != null){
				logger.info("getItemPricing in getItem()");
				itemList.get(0).setItemPricing(getItemPricing(itemId));
				
			}

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getItem()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getItem():", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList.get(0);// only return the first one
	}
	
	
	public List<ItemPricingDTO> getItemPricing(int itemId) throws DAOException {
		logger.info("getItemPricing() is called");

		List<ItemPricingDTO> itemList = new ArrayList<ItemPricingDTO>();

		StringBuilder SQL= new StringBuilder();
		SQL.append(" SELECT ID, EFF_START_DATE, EFF_END_DATE,  \n");
		SQL.append("    ONETIME_TYPE, ONETIME_AMT, RECURRENT_AMT  \n");
		SQL.append(" FROM OPS$CNM.W_ITEM_PRICING  \n");
		SQL.append(" where id=? \n");



		ParameterizedRowMapper<ItemPricingDTO> mapper = new ParameterizedRowMapper<ItemPricingDTO>() {
			public ItemPricingDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ItemPricingDTO dto = new ItemPricingDTO();
				
				dto.setId(rs.getInt("ID"));
				dto.setEffStartDate(rs.getString("EFF_START_DATE"));
				dto.setEffEndDate(rs.getString("EFF_END_DATE"));
				dto.setOnetimeType(rs.getString("ONETIME_TYPE"));
				dto.setOnetimeAmt(rs.getString("ONETIME_AMT"));
				dto.setRecurrentAmt(rs.getString("RECURRENT_AMT"));
				//dto.setRebateAmt(rs.getString("REBATE_AMT"));

				return dto;
			}
		};

		try {
			//herbert 20111110 - remove useless SQL logger
			logger.info("getItem() @ ItemPricingDTO: " );
			logger.debug("getItem() @ ItemPricingDTO: " + SQL);
			itemList = simpleJdbcTemplate.query(SQL.toString(), mapper, itemId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getItem()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getItem():", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;// only return the first one
	}
	
	/*
	 * UPDATE W_ITEM
	 * */
	public int updateItem(ItemEditDTO dto) throws DAOException {
		logger.info("updateItem is called");
		
		StringBuilder SQL= new StringBuilder();
		SQL.append(" UPDATE W_ITEM \n");
		SQL.append(" SET    LOB           = ?, \n");
		SQL.append("        TYPE          = ?, \n");
		SQL.append("        DESCRIPTION   = ?, \n");
		SQL.append("        LAST_UPD_BY   = ?, \n");//'WEB'||to_char(sysdate, 'YYMMDD')
		SQL.append("        LAST_UPD_DATE = sysdate \n");
		SQL.append(" WHERE  ID            = ? \n");

		try {
			return simpleJdbcTemplate.update(SQL.toString(), dto.getLob(), dto.getType(), dto.getDescription(),dto.getLastUpdBy(), dto.getId());

		} catch (Exception e) {
			logger.error("Exception caught in updateItem()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	/*
	 * UPDATE W_ITEM_DTL_HS
	 * */
	public int updateItemDtlHs(ItemEditDTO dto) throws DAOException {
		logger.info("updateItemDtlHs is called");
		
		StringBuilder SQL= new StringBuilder();
		SQL.append(" UPDATE OPS$CNM.W_ITEM_DTL_HS \n");
		SQL.append(" SET    BRAND_ID        = ?, \n");
		SQL.append("        MODEL_ID        = ?, \n");
		SQL.append("        COLOR_ID        = ?, \n");
		SQL.append("        CONTRACT_PERIOD = ?, \n");
		SQL.append("        LAST_UPD_BY   = ?, \n");
		SQL.append("        LAST_UPD_DATE = sysdate \n");
		SQL.append(" WHERE  ID              = ? \n");

		try {
			return simpleJdbcTemplate.update(SQL.toString(), dto.getBrandId(), dto.getModelId(), dto.getColorId(), dto.getHsContractPeriod() ,dto.getLastUpdBy(),dto.getId());

		} catch (Exception e) {
			logger.error("Exception caught in updateItemDtlHs()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	/*
	 * UPDATE W_ITEM_DTL_RP_RB_VAS
	 * */
	public int updateItemDtlRpRbVas(ItemEditDTO dto) throws DAOException {
		logger.info("updateItemDtlHs is called");
		
		StringBuilder SQL= new StringBuilder();
		SQL.append(" UPDATE OPS$CNM.W_ITEM_DTL_RP_RB_VAS \n");
		SQL.append(" SET    RP_TYPE         = ?, \n");
		SQL.append("        CONTRACT_PERIOD = ?, \n");
		SQL.append("        REBATE_PERIOD   = ?, \n");
		SQL.append("        REBATE_SCHEDULE = ?, \n");
		SQL.append("        REBATE_AMT      = ?, \n");
		SQL.append("        PENALTY_TYPE    = ?, \n");
		SQL.append("        PENALTY_AMT     = ?, \n");
		SQL.append("        LAST_UPD_BY   = ?, \n");
		SQL.append("        LAST_UPD_DATE = sysdate \n");
		SQL.append(" WHERE  ID              = ? \n");


		try {
			return simpleJdbcTemplate.update(SQL.toString(), dto.getRpType(),
					dto.getRpRbVasContractPeriod(),
					dto.getRebatePeriod(),
					dto.getRebateSchedule(),
					dto.getRebateAmt(),
					dto.getPenaltyType(),
					dto.getPenaltyAmt(), 
					dto.getLastUpdBy(),
					dto.getId());

		} catch (Exception e) {
			logger.error("Exception caught in updateItemDtlHs()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	/*
	 * UPDATE W_ITEM_DTL_RP_RB_VAS
	 */
	public int updateItemPricingEffEndDate(ItemEditDTO dto) throws DAOException {
		logger.info("updateItemPricing is called");
		
		
		StringBuilder SQL= new StringBuilder();
		SQL.append(" UPDATE OPS$CNM.W_ITEM_PRICING \n");
		SQL.append(" 		 SET    EFF_END_DATE   = to_date(?,'YYYY/MM/DD')-1, \n");
		SQL.append(" 		        LAST_UPD_BY   = ?, \n");
		SQL.append(" 		        LAST_UPD_DATE = sysdate \n");
		SQL.append(" 		 WHERE  ID             = ? \n");
		SQL.append(" 		 AND    EFF_START_DATE = (SELECT MAX (ip2.eff_start_date) \n");
		SQL.append(" 		                                       FROM w_item_pricing ip2 \n");
		SQL.append(" 		                                      WHERE ip2.ID = ?) \n");
		SQL.append(" 											  and EFF_START_DATE <=to_date(?,'YYYY/MM/DD')-1 \n");



		try {
			return simpleJdbcTemplate.update(SQL.toString(), dto.getEffStartDate(),dto.getLastUpdBy(),dto.getId(),dto.getId(), dto.getEffStartDate());

		} catch (Exception e) {
			logger.error("Exception caught in updateItemPricing()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int updateItemPricing(ItemEditDTO dto) throws DAOException {
		logger.info("updateItemPricing is called");
		
		
		StringBuilder SQL= new StringBuilder();
		SQL.append("UPDATE OPS$CNM.W_ITEM_PRICING \n");
		SQL.append(" SET    EFF_END_DATE   = to_date(?,'YYYY/MM/DD'), \n");
		SQL.append("        ONETIME_TYPE   = ?, \n");
		SQL.append("        ONETIME_AMT    = ?, \n");
		SQL.append("        RECURRENT_AMT  = ?, \n");
	//	SQL.append("        REBATE_AMT     = ?, \n");
		SQL.append("        LAST_UPD_BY    = ?, \n");
		SQL.append("        LAST_UPD_DATE  = sysdate \n");
		SQL.append(" WHERE  ID             = ? \n");
		SQL.append(" AND    EFF_START_DATE = to_date(?,'YYYY/MM/DD') \n");

		try {
			return simpleJdbcTemplate.update(SQL.toString(), 
					dto.getEffEndDate(),
					dto.getOnetimeType(),
					dto.getOnetimeAmt(),
					dto.getRecurrentAmt(),
					//dto.getRebateAmt(),
					dto.getLastUpdBy(),
					dto.getId(),
					dto.getEffStartDate()
);

		} catch (Exception e) {
			logger.error("Exception caught in updateItemPricing()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	
	
	
	public int insertItem(ItemEditDTO dto) throws DAOException {
		logger.info("insertItem is called");

		StringBuilder SQL= new StringBuilder();
		SQL.append(" INSERT INTO ops$cnm.w_item \n");
		SQL.append("             (ID, LOB, TYPE, description, \n");
		SQL.append("              create_by, create_date, \n");
		SQL.append("              last_upd_by, last_upd_date \n");
		SQL.append("             ) \n");
		SQL.append("      VALUES (?, '3G', ?, ?, \n");
		SQL.append("              'WEB' || TO_CHAR (SYSDATE, 'YYMMDD'), SYSDATE, \n");
		SQL.append("              ?, SYSDATE) \n");

		try {
			//Comment & modify by herbert 20110822, standardize the log
			/*
			 * System.out.println("dto.getId():"+dto.getId());
			 * System.out.println("dto.getType():"+dto.getType());
			 * System.out.println("dto.getDescription():"+dto.getDescription());
			 */
			logger.debug("dto.getId():"+dto.getId());
			logger.debug("dto.getType():"+dto.getType());
			logger.debug("dto.getDescription():"+dto.getDescription());

			return simpleJdbcTemplate.update(SQL.toString(), dto.getId(), dto.getType(), dto.getDescription(), dto.getLastUpdBy() );
		}catch(Exception e){
			logger.error("Exception caught in insertItem()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public int insertItemDtlHs(ItemEditDTO dto) throws DAOException {
		logger.info("insertItemDtlHs is called");

		StringBuilder SQL= new StringBuilder();
		SQL.append(" INSERT INTO OPS$CNM.W_ITEM_DTL_HS ( \n");
		SQL.append("    ID, BRAND_ID, MODEL_ID,  \n");
		SQL.append("    COLOR_ID, CONTRACT_PERIOD, CREATE_BY,  \n");
		SQL.append("    CREATE_DATE, LAST_UPD_BY, LAST_UPD_DATE)  \n");
		SQL.append(" VALUES ( ?, ?, ?, \n");
		SQL.append("    ? , ?, 'WEB' || TO_CHAR (SYSDATE, 'YYMMDD'), \n");
		SQL.append("    sysdate ,? ,sysdate ) \n");

	

		try {
			return simpleJdbcTemplate.update(SQL.toString(),dto.getId(),
					dto.getBrandId(),
					dto.getModelId(),
					dto.getColorId(),
					dto.getHsContractPeriod() ,dto.getLastUpdBy());
		}catch(Exception e){
			logger.error("Exception caught in insertItemDtlHs()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public int insertItemDtlRpRbVas(ItemEditDTO dto) throws DAOException {
		logger.info("insertItemDtlRpRbVas is called");

		StringBuilder SQL= new StringBuilder();
		
		SQL.append(" INSERT INTO OPS$CNM.W_ITEM_DTL_RP_RB_VAS ( \n");
		SQL.append("    ID, RP_TYPE, CONTRACT_PERIOD,  \n");
		SQL.append("    REBATE_PERIOD, REBATE_SCHEDULE, REBATE_AMT,  \n");
		SQL.append("    PENALTY_TYPE, PENALTY_AMT, CREATE_BY,  \n");
		SQL.append("    CREATE_DATE, LAST_UPD_BY, LAST_UPD_DATE)  \n");
		SQL.append(" VALUES ( ?,? ,? , \n");
		SQL.append("     ?, ?, ?, \n");
		SQL.append("     ?, ?, 'WEB' || TO_CHAR (SYSDATE, 'YYMMDD'), \n");
		SQL.append("     sysdate, ?, sysdate) \n");

		

		try {
			return simpleJdbcTemplate.update(SQL.toString(),dto.getId(),
					dto.getRpType(),
					dto.getRpRbVasContractPeriod(),
					dto.getRebatePeriod(),
					dto.getRebateSchedule(),
					dto.getRebateAmt(),
					dto.getPenaltyType(),
					dto.getPenaltyAmt(),
					dto.getLastUpdBy()  );
		}catch(Exception e){
			logger.error("Exception caught in insertItemDtlRpRbVas()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int insertItemPricing(ItemEditDTO dto) throws DAOException {
		logger.info("insertItemPricing is called");

		StringBuilder SQL= new StringBuilder();
		SQL.append(" INSERT INTO OPS$CNM.W_ITEM_PRICING ( \n");
		SQL.append("    ID, EFF_START_DATE, EFF_END_DATE,  \n");
		SQL.append("    ONETIME_TYPE, ONETIME_AMT, RECURRENT_AMT,  \n");
		SQL.append("    CREATE_BY, CREATE_DATE,  \n");
		SQL.append("    LAST_UPD_BY, LAST_UPD_DATE)  \n");
		SQL.append(" VALUES (? ,to_date(?,'yyyy/mm/dd') , to_date(?,'yyyy/mm/dd'), \n");
		SQL.append("     ?, ?, ?, \n");
		SQL.append("     'WEB' || TO_CHAR (SYSDATE, 'YYMMDD'),sysdate , \n");
		SQL.append("     ?,sysdate ) \n");

		
		
		
		try {
			return simpleJdbcTemplate.update(SQL.toString(),dto.getId(),
					dto.getEffStartDate(),
					dto.getEffEndDate(),
					dto.getOnetimeType(),
					dto.getOnetimeAmt(),
					dto.getRecurrentAmt(),
				
					dto.getLastUpdBy());
		
		}catch(Exception e){
			logger.error("Exception caught in insertItemPricing()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public int deleteItem(int itemId) throws DAOException {
		logger.info("deleteItem is called");
		

		String SQL="delete from W_ITEM where id=?  ";

		try {
			return simpleJdbcTemplate.update(SQL, itemId);
		}catch (Exception e) {
			logger.error("Exception caught in deleteItem()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int deleteItemDtlHs(int itemId) throws DAOException {
		logger.info("deleteItem is called");
		

		String SQL="delete from W_ITEM_DTL_HS where id=?  ";

		try {
			return simpleJdbcTemplate.update(SQL, itemId);
		}catch (Exception e) {
			logger.error("Exception caught in deleteItem()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int deleteItemDtlRpRbVas(int itemId) throws DAOException {
		logger.info("deleteItem is called");
		

		String SQL="delete from W_ITEM_DTL_RP_RB_VAS where id=?  ";

		try {
			return simpleJdbcTemplate.update(SQL, itemId);
		}catch (Exception e) {
			logger.error("Exception caught in deleteItem()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int deleteItemPricing(int itemId) throws DAOException {
		logger.info("deleteItem is called");
		

		String SQL="delete from W_ITEM_PRICING where id=?  ";

		try {
			return simpleJdbcTemplate.update(SQL, itemId);
		}catch (Exception e) {
			logger.error("Exception caught in deleteItem()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public int deleteItemOfferAssign(int itemId) throws DAOException {
		logger.info("deleteItem is called");
		

		String SQL="delete from W_ITEM_OFFER_ASSGN where item_id=?  ";

		try {
			return simpleJdbcTemplate.update(SQL, itemId);
		}catch (Exception e) {
			logger.error("Exception caught in deleteItem()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int deleteItemOfferProductAssign(int itemId) throws DAOException {
		logger.info("deleteItem is called");
		

		String SQL="delete from W_ITEM_OFFER_PRODUCT_ASSGN where item_id=?  ";

		try {
			return simpleJdbcTemplate.update(SQL, itemId);
		}catch (Exception e) {
			logger.error("Exception caught in deleteItem()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	//add 20110331
	private static final String insertItemOfferAssignSQL=" INSERT INTO OPS$CNM.W_ITEM_OFFER_ASSGN ( \n"+
	"    ITEM_ID, ITEM_OFFER_SEQ, OFFER_ID,  \n"+
	"    OFFER_SUB_ID, OFFER_TYPE, SELECT_QTY,  \n"+
	"    CREATE_BY, CREATE_DATE)  \n"+
	" VALUES (? ,? ,? , \n"+
	"    ? ,? , ?, \n"+
	"    'WEB' || TO_CHAR (SYSDATE, 'YYMMDD') , sysdate) \n";
	
	public int insertItemOfferAssign(ItemOfferDTO dto) throws DAOException {
		logger.info("insertItemOfferAssign is called");
		try {

			return simpleJdbcTemplate.update(insertItemOfferAssignSQL, dto.getItemId(),
					dto.getItemOfferSeq(),
					dto.getOfferId(),
					dto.getOfferSubId(),
					dto.getOfferType(),
					dto.getSelectQty()
			);
		}catch(Exception e){
			logger.error("Exception caught in insertItemOfferAssign()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	private static final String  insertItemOfferProductAssignSQL=" INSERT INTO OPS$CNM.W_ITEM_OFFER_PRODUCT_ASSGN ( \n"+
	"    ITEM_ID, ITEM_OFFER_SEQ, ITEM_PRODUCT_SEQ,  \n"+
	"    PRODUCT_ID, PRODUCT_TYPE, SELECT_QTY,  \n"+
	"    CREATE_BY, CREATE_DATE)  \n"+
	" VALUES ( ?, ?,?, \n"+
	"     ?, ?, ?, \n"+
	"     'WEB' || TO_CHAR (SYSDATE, 'YYMMDD') , sysdate ) \n";

	
	public int insertItemOfferProductAssign(ItemOfferDTO dto) throws DAOException {
		logger.info("insertItemOfferProductAssign() is called");
		try {

			return simpleJdbcTemplate.update(insertItemOfferProductAssignSQL, dto.getItemId(),
					dto.getItemOfferSeq(),
					dto.getItemProductSeq(),
					dto.getProductId(),
					dto.getProductType(),
					dto.getSelectQty()

			);
		}catch(Exception e){
			logger.error("Exception caught in insertItemOfferProductAssign()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	private static final String getItemOfferSQL=" SELECT IOA.ITEM_ID, IOA.ITEM_OFFER_SEQ, IOA.OFFER_ID, IOA.OFFER_SUB_ID, \n"+
	"        IOA.OFFER_TYPE, IOA.SELECT_QTY, IOA.CREATE_BY, IOA.CREATE_DATE, \n"+
	"        --IOPA \n"+
	"        IOPA.ITEM_PRODUCT_SEQ, IOPA.PRODUCT_ID, IOPA.PRODUCT_TYPE, \n"+
	"        IOPA.SELECT_QTY IOPA_SELECT_QTY \n"+
	"   FROM W_ITEM_OFFER_ASSGN IOA, W_ITEM_OFFER_PRODUCT_ASSGN IOPA \n"+
	"  WHERE IOA.ITEM_ID = IOPA.ITEM_ID \n"+
	"    AND IOA.ITEM_OFFER_SEQ = IOPA.ITEM_OFFER_SEQ \n"+
	"    AND IOA.ITEM_ID = ? \n";
	
	public List<ItemOfferDTO> getItemOffer(int itemId) throws DAOException {
		logger.info("getItemOffer() is called");

		List<ItemOfferDTO> itemList = new ArrayList<ItemOfferDTO>();

		ParameterizedRowMapper<ItemOfferDTO> mapper = new ParameterizedRowMapper<ItemOfferDTO>() {
			public ItemOfferDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ItemOfferDTO dto = new ItemOfferDTO();
				
				dto.setItemId(rs.getInt("ITEM_ID"));
				dto.setItemOfferSeq(rs.getInt("ITEM_OFFER_SEQ"));
				dto.setOfferId(rs.getInt("OFFER_ID"));
				dto.setOfferSubId(rs.getInt("OFFER_SUB_ID"));
				dto.setOfferType(rs.getString("OFFER_TYPE"));
				dto.setSelectQty(rs.getInt("SELECT_QTY"));
				//dto.setCreateBy(rs.getString("CREATE_BY"));
				//dto.setCreateDate(rs.getString("CREATE_DATE"));

				//dto.setItemId(rs.getString("ITEM_ID"));
				//dto.setItemOfferSeq(rs.getString("ITEM_OFFER_SEQ"));
				dto.setItemProductSeq(rs.getInt("ITEM_PRODUCT_SEQ"));
				dto.setProductId(rs.getInt("PRODUCT_ID"));
				dto.setProductType(rs.getString("PRODUCT_TYPE"));
				dto.setSelectQty(rs.getInt("SELECT_QTY"));
				//dto.setCreateBy(rs.getString("CREATE_BY"));
			//	dto.setCreateDate(rs.getString("CREATE_DATE"));


				return dto;
			}
		};

		try {
			logger.info("getItemOffer() @ ItemOfferDTO: " + getItemOfferSQL);
			itemList = simpleJdbcTemplate.query(getItemOfferSQL, mapper, itemId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getItemOffer()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getItemOffer():", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	
	
	

}
