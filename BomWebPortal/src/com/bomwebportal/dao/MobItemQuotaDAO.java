package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.bomwebportal.dto.MobItemQuotaDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.exception.DAOException;

@Repository
public class MobItemQuotaDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());

	private static String getMobItemQuotaDTOALLSQL = 
						"--item lvl\n" +
						"select i.id              item_id\n" + 
						"      ,mqa.lvl_ind\n" + 
						"      ,mqa.lvl_id\n" + 
						"      ,mq.quota_id\n" + 
						"      ,mq.quota_eng_desc\n" + 
						"      ,mq.quota_chi_desc\n" + 
						"      ,mq.ceil_cnt\n" + 
						"      ,mq.eff_start_date\n" + 
						"      ,mq.eff_end_date\n" + 
						"      ,mqa.eff_start_date quota_assign_eff_start_date\n" + 
						"      ,mqa.eff_end_date quota_assign_eff_end_date\n" + 
						"      ,ippa.pos_item_cd  lvl_cd\n" + 
						"      ,i.description     lvl_desc\n" + 
						"from w_mob_quota              mq\n" + 
						"    ,w_mob_quota_assgn        mqa\n" + 
						"    ,w_item                   i\n" + 
						"    ,w_item_product_pos_assgn ippa\n" + 
						"where mq.quota_id = mqa.quota_id\n" + 
						"and mqa.lvl_ind = 'I'\n" + 
						"and mqa.lvl_id = ippa.pos_item_cd\n" + 
						"and ippa.item_id = i.id\n" + 
						"and trunc(nvl(mq.eff_end_date, sysdate)) >= trunc(sysdate - 90)\n" + 
						"and trunc(nvl(mqa.eff_end_date, sysdate)) >= trunc(sysdate - 90)\n" + 
						"union\n" + 
						"--prod lvl\n" + 
						"select i.id              item_id\n" + 
						"      ,mqa.lvl_ind\n" + 
						"      ,mqa.lvl_id\n" + 
						"      ,mq.quota_id\n" + 
						"      ,mq.quota_eng_desc\n" + 
						"      ,mq.quota_chi_desc\n" + 
						"      ,mq.ceil_cnt\n" + 
						"      ,mq.eff_start_date\n" + 
						"      ,mq.eff_end_date\n" + 
						"      ,mqa.eff_start_date quota_assign_eff_start_date\n" + 
						"      ,mqa.eff_end_date quota_assign_eff_end_date\n" + 
						"      ,iopa.prod_cd      lvl_cd\n" + 
						"      ,iopa.prod_desc    lvl_desc\n" + 
						"from w_mob_quota                mq\n" + 
						"    ,w_mob_quota_assgn          mqa\n" + 
						"    ,w_item                     i\n" + 
						"    ,w_item_offer_product_assgn iopa\n" + 
						"where mq.quota_id = mqa.quota_id\n" + 
						"and mqa.lvl_ind = 'P'\n" + 
						"and mqa.lvl_id = iopa.product_id\n" + 
						"and iopa.item_id = i.id\n" + 
						"and trunc(nvl(mq.eff_end_date, sysdate)) >= trunc(sysdate - 90)\n" + 
						"and trunc(nvl(mqa.eff_end_date, sysdate)) >= trunc(sysdate - 90)\n" + 
						"union\n" + 
						"--offer lvl\n" + 
						"select i.id              item_id\n" + 
						"      ,mqa.lvl_ind\n" + 
						"      ,mqa.lvl_id\n" + 
						"      ,mq.quota_id\n" + 
						"      ,mq.quota_eng_desc\n" + 
						"      ,mq.quota_chi_desc\n" + 
						"      ,mq.ceil_cnt\n" + 
						"      ,mq.eff_start_date\n" + 
						"      ,mq.eff_end_date\n" + 
						"      ,mqa.eff_start_date quota_assign_eff_start_date\n" + 
						"      ,mqa.eff_end_date quota_assign_eff_end_date\n" + 
						"      ,ioa.offer_cd      lvl_cd\n" + 
						"      ,ioa.offer_desc    lvl_desc\n" + 
						"from w_mob_quota        mq\n" + 
						"    ,w_mob_quota_assgn  mqa\n" + 
						"    ,w_item             i\n" + 
						"    ,w_item_offer_assgn ioa\n" + 
						"where mq.quota_id = mqa.quota_id\n" + 
						"and mqa.lvl_ind = 'O'\n" + 
						"and mqa.lvl_id = ioa.offer_id\n" + 
						"and ioa.item_id = i.id\n" + 
						"and trunc(nvl(mq.eff_end_date, sysdate)) >= trunc(sysdate - 90)\n"+ 
						"and trunc(nvl(mqa.eff_end_date, sysdate)) >= trunc(sysdate - 90)";



	public List<MobItemQuotaDTO> getMobItemQuotaDTOALL() throws DAOException {
		logger.info(" getMobQuotaAuthLog is called");
		List<MobItemQuotaDTO> itemList = new ArrayList<MobItemQuotaDTO>();
		/**** ==ParameterizedRowMapper start== *********************************************************/
		ParameterizedRowMapper<MobItemQuotaDTO> mapper = new ParameterizedRowMapper<MobItemQuotaDTO>() {
			public MobItemQuotaDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MobItemQuotaDTO dto = new MobItemQuotaDTO();

				dto.setItemId(rs.getString("item_id"));
				dto.setLvlInd(rs.getString("lvl_ind"));
				dto.setLvlId(rs.getString("lvl_id"));
				dto.setQuotaId(rs.getString("quota_id"));

				dto.setQuotaEngDesc(rs.getString("quota_eng_desc"));
				dto.setQuotaChiDesc(rs.getString("quota_chi_desc"));

				dto.setCeilCnt(rs.getInt("ceil_cnt"));

				dto.setEffStartDate(rs.getDate("eff_start_date"));
				dto.setEffEndDate(rs.getDate("eff_end_date"));

				dto.setLvlCd(rs.getString("lvl_cd"));
				dto.setLvlDesc(rs.getString("lvl_desc"));
				dto.setQuotaAssignEffStartDate(rs.getDate("quota_assign_eff_start_date"));
				dto.setQuotaAssignEffEndDate(rs.getDate("quota_assign_eff_end_date"));
				return dto;
			}
		};

		try {
			logger.info("getMobItemQuotaDTOALL() @ MobItemQuotaDAO: "
					+ getMobItemQuotaDTOALLSQL);
			itemList = simpleJdbcTemplate.query(getMobItemQuotaDTOALLSQL,
					mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getMobItemQuotaDTOALL()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getMobItemQuotaDTOALL():", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	public List<VasDetailDTO> getEffectiveBasketItems(String basketId, String appDate) throws DAOException {

		String sql = "select bia.item_id, i.type item_type\n"
				+ "from w_basket_item_assgn bia, w_item i\n"
				+ "where bia.item_id = i.id\n"
				+ "and to_date(:v_app_date, 'DD/MM/YYYY') between trunc(bia.eff_start_date) and\n"
				+ "      trunc(nvl(bia.eff_end_date, sysdate))\n"
				+ "and bia.basket_id = :v_basket_id";

		ParameterizedRowMapper<VasDetailDTO> mapper = new ParameterizedRowMapper<VasDetailDTO>() {
			public VasDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VasDetailDTO dto = new VasDetailDTO();
				dto.setItemId(rs.getString("item_id"));
				dto.setItemType(rs.getString("item_type"));
				return dto;
			}
		};

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("v_app_date", appDate);
		params.addValue("v_basket_id", basketId);

		try {
			logger.info("getEffevtiveBasketItems : " + sql);
			List<VasDetailDTO> result = simpleJdbcTemplate.query(sql, mapper, params);
			return result;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException", erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getEffevtiveBasketItems()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;

	}

}
