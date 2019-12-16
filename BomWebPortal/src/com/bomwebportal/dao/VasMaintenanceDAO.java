package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;


import com.bomwebportal.dto.VasMaintenanceDTO;
import com.bomwebportal.exception.DAOException;


public class VasMaintenanceDAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<VasMaintenanceDTO> getVasMaintenanceDTO(String offerCdSearch) throws DAOException {
		
		String sql = "select" +
				" distinct oa.offer_cd batch_no, pa.prod_desc display_chi, pa.prod_desc display_eng," +
				" oa.offer_type, oa.offer_id, oa.offer_cd, oa.offer_desc," +
				" oaa.offer_sub_id, pa.prod_type, pa.prod_id, pa.prod_cd, pa.prod_desc,ica.item_cd pos_item_cd,oa.brand,oa.sim_type,pa.brand,pa.sim_type" +
				" from B_OFFER_PRODUCT_ASSGN_A opa, B_OFFER_A oa, B_OFFER_ASSGN_A oaa, b_product_a pa, b_product_compt_assgn_a pca, b_component_a ca, b_item_code_assgn_a icaa, b_item_code_a ica" +
				" where 1 = 1" +
				" and oa.offer_cd = (:offerCdSearch)" +
				//" and pa.prod_cd = (:offerCdSearch)" +
				" and oaa.offer_sub_id = opa.offer_sub_id" +
				" and oa.offer_id = oaa.offer_id" +
				" and opa.prod_id = pa.prod_id" +
				" and pa.prod_id = pca.prod_id" +
				" and pca.compt_id = ca.compt_id" +
				" and ca.compt_id = icaa.lvl_id (+)" +
				" and icaa.lvl_ind (+) = 'C'" +
				" and icaa.item_cd_id = ica.item_cd_id (+)" +
				" and nvl(oa.eff_end_date, to_date('20991231','yyyymmdd')) >= trunc(sysdate)" +
				" and nvl(opa.eff_end_date, to_date('20991231','yyyymmdd')) >= trunc(sysdate)" +
				" and nvl(pa.eff_end_date, to_date('20991231','yyyymmdd')) >= trunc(sysdate)" +
				" and nvl(pca.eff_end_date, to_date('20991231','yyyymmdd')) >= trunc(sysdate)" +
				" and nvl(ca.eff_end_date, to_date('20991231','yyyymmdd')) >= trunc(sysdate)";
		
		List<VasMaintenanceDTO> list = null;
		try {
			logger.info("sql: " + sql);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("offerCdSearch", offerCdSearch);
			list = simpleJdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(VasMaintenanceDTO.class), params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("empty result", erdae);
		}
		return list;
				
	}
	
	
	
	public VasMaintenanceDTO getUniqueVasMaintenanceDTO(String offerCd, String prodCd, String prodId, String offerId, String offerSubId ) throws DAOException {
		
		String sql = "select" +
				" distinct oa.offer_cd batch_no, pa.prod_desc display_chi, pa.prod_desc display_eng," +
				" oa.offer_type, oa.offer_id, oa.offer_cd, oa.offer_desc," +
				" oaa.offer_sub_id, pa.prod_type, pa.prod_id, pa.prod_cd, pa.prod_desc,ica.item_cd pos_item_cd,oa.brand as offer_brand, oa.sim_type as offer_sim_type,pa.brand, pa.sim_type" +
				" from B_OFFER_PRODUCT_ASSGN_A opa, B_OFFER_A oa, B_OFFER_ASSGN_A oaa, b_product_a pa, b_product_compt_assgn_a pca, b_component_a ca, b_item_code_assgn_a icaa, b_item_code_a ica" +
				" where 1 = 1" +
				" and oa.offer_cd = (:offerCd)" +
				" and pa.prod_cd = (:prodCd)" +
				" and oaa.offer_sub_id = (:offerSubId)" +
				" and oa.offer_id = (:offerId)" +
				" and opa.prod_id = (:prodId)"+
				" and oaa.offer_sub_id = opa.offer_sub_id" +
				" and oa.offer_id = oaa.offer_id" +
				" and opa.prod_id = pa.prod_id" +
				" and pa.prod_id = pca.prod_id" +
				" and pca.compt_id = ca.compt_id" +
				" and ca.compt_id = icaa.lvl_id (+)" +
				" and icaa.lvl_ind (+) = 'C'" +
				" and icaa.item_cd_id = ica.item_cd_id (+)" +
				" and nvl(oa.eff_end_date, to_date('20991231', 'yyyymmdd')) >= trunc(sysdate)" +
				" and nvl(pa.eff_end_date, to_date('20991231', 'yyyymmdd')) >= trunc(sysdate)" +
				" and nvl(opa.eff_end_date, to_date('20991231', 'yyyymmdd')) >= trunc(sysdate)" +
				" and nvl(pca.eff_end_date, to_date('20991231', 'yyyymmdd')) >= trunc(sysdate)" +
				" and nvl(ca.eff_end_date, to_date('20991231', 'yyyymmdd')) >= trunc(sysdate)" +
				" and rownum = 1";

		try {
			logger.info("sql: " + sql);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("offerCd", offerCd);
			params.addValue("prodCd", prodCd);
			params.addValue("prodId", prodId);
			params.addValue("offerId", offerId);
			params.addValue("offerSubId", offerSubId);
			return simpleJdbcTemplate.queryForObject(sql, ParameterizedBeanPropertyRowMapper.newInstance(VasMaintenanceDTO.class), params);
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("empty result", erdae);
			return null;
		}
				
	}
	
	public List<VasMaintenanceDTO> getRecurringAmt(String prodId) throws DAOException {
		
			String sql = "select pt.chrg_amt" +
				" from b_product_a p, b_pricing_assgn_a pa, b_pricing_scheme_a ps, b_pricing_tier_a pt" +
				" where p.prod_id = pa.item_id" +
				" and pa.item_type = 'P'" +
				" and pa.prc_sch_id = ps.prc_sch_id" +
				" and ps.prc_sch_id = pt.prc_sch_id" +
				" and ps.eff_start_date = pt.eff_Start_date" +
				" and nvl(ps.eff_end_date,to_date('20991231','yyyymmdd')) > sysdate" +
				" and ps.prc_type in ('01')" +
				" and p.prod_id = (:prodId)";
			List<VasMaintenanceDTO> list = null;
			try{
				logger.info("sql: " + sql);
				MapSqlParameterSource params = new MapSqlParameterSource();
				params.addValue("prodId", prodId);
				list = simpleJdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(VasMaintenanceDTO.class), params);
			} catch (EmptyResultDataAccessException erdae) {
				logger.debug("empty result", erdae);
			}
			
			return list;
		
	}
	
	public List<VasMaintenanceDTO> getOneTimeAmt(String prodId) throws DAOException {
		
		String sql = "select pt.chrg_amt" +
			" from b_product_a p, b_pricing_assgn_a pa, b_pricing_scheme_a ps, b_pricing_tier_a pt" +
			" where p.prod_id = pa.item_id" +
			" and pa.item_type = 'P'" +
			" and pa.prc_sch_id = ps.prc_sch_id" +
			" and ps.prc_sch_id = pt.prc_sch_id" +
			" and ps.eff_start_date = pt.eff_Start_date" +
			" and nvl(ps.eff_end_date,to_date('20991231','yyyymmdd')) > sysdate" +
			" and ps.prc_type in ('02')" +
			" and p.prod_id = (:prodId)";
		List<VasMaintenanceDTO> list = null;
		try{
			logger.info("sql: " + sql);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("prodId", prodId);
			list = simpleJdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(VasMaintenanceDTO.class), params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("empty result", erdae);
		}
		return list;
	
}
	//vincy
			public List<VasMaintenanceDTO> getOneTimeRecurringAmt(String offerCd) throws DAOException {
				
				
				
				
				String sql = "select prc.offer_id, prc.offer_cd, prc.prc_type prod_type, prc.chrg_amt "
						+ "from ( "
						+ "select o.offer_id, o.offer_cd, decode(ps.prc_type,'01','Recurring', '02','Onetime') prc_type, pt.chrg_amt "
						+ "from b_offer_a o, b_offer_assgn_a oa, b_offer_product_assgn_a opa, b_product_a p, b_pricing_assgn_a pa, b_pricing_scheme_a ps, b_pricing_tier_a pt "
						+ "where o.offer_id = oa.offer_id "
						+ "and o.std_offer_ind = 'Y' "
						+ "and oa.offer_sub_id = opa.offer_sub_id "
						+ "and opa.prod_id = p.prod_id  "
						+ "and p.prod_id = pa.item_id "
						+ "and pa.item_type = 'P' "
						+ "and pa.prc_sch_id = ps.prc_sch_id "
						+ "and ps.prc_sch_id = pt.prc_sch_id "
						+ "and ps.eff_start_date = pt.eff_Start_date " 
						+ "and nvl(ps.eff_end_date,to_date('20991231','yyyymmdd')) > sysdate "
						+ "and ps.prc_type in ('01','02') "
						+ " union "
						+ "select o.offer_id, o.offer_cd, decode(ps.prc_type,'01','Recurring', '02','Onetime') prc_type, pt.chrg_amt "
						+ "from b_offer_a o, b_pricing_assgn_a pa, b_pricing_scheme_a ps, b_pricing_tier_a pt "
						+ "where o.offer_id = pa.item_id "
						+ "and pa.item_type = 'O'"
						+ "and pa.prc_sch_id = ps.prc_sch_id "
						+ "and ps.prc_sch_id = pt.prc_sch_id "
						+ "and ps.eff_start_date = pt.eff_Start_date "
						+ "and nvl(ps.eff_end_date,to_date('20991231','yyyymmdd')) > sysdate " 
						+ "and ps.prc_type in ('01','02') "
						+ ") prc "
						+ "where prc.offer_cd = (:offer_cd) ";
				List<VasMaintenanceDTO> list = null;
				try{
					logger.info("sql: " + sql);
					logger.info("getOneTimeRecurringAmt() is called ");
					MapSqlParameterSource params = new MapSqlParameterSource();
					params.addValue("offer_cd", offerCd);
					list = simpleJdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(VasMaintenanceDTO.class), params);
				} catch (EmptyResultDataAccessException erdae) {
					logger.debug("empty result", erdae);
				}
				return list;
			
			}
			
			
	
			public List<VasMaintenanceDTO> getParamsAlert(String prodId) throws DAOException {
				
				String sql = "select pa.prod_id, pa.prod_cd, pa.prod_desc, pa.eff_start_date prod_start_date, pa.eff_end_date prod_end_date " +
					" from b_product_a pa, b_product_compt_assgn_a pca, b_component_a ca, b_component_attb_a caa, b_attb_a aa, b_attb_type_a ata, b_info_dic_a ida " +
					" where 1=1 " +
					" and pa.prod_id = (:prodId) " +
					" and pa.prod_id = pca.prod_id " +
					" and pca.compt_id = ca.compt_id " +
					" and ca.compt_id = caa.compt_id " +
					" and caa.compt_attb_id = aa.attb_id " +
					" and aa.attb_type_id = ata.attb_type_id " +
					" and ata.man_ind = 'Y' " +
					" and ata.vis_ind = 'Y' " +
					" and ata.info_key = ida.info_key (+) " +
					" order by pa.prod_id, ca.compt_id, aa.attb_id, ata.attb_type_id ";
				
				
				List<VasMaintenanceDTO> list = null;
				try{
					logger.info("sql: " + sql);
					logger.info("getParamsAlert() is called ");
					MapSqlParameterSource params = new MapSqlParameterSource();
					params.addValue("prodId", prodId);
					list = simpleJdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(VasMaintenanceDTO.class), params);
				} catch (EmptyResultDataAccessException erdae) {
					logger.debug("empty result", erdae);
				}
				return list;
			
		}
			
			
	/*
	private ParameterizedRowMapper<OfferProductAssgnDTO> getRowMapper() {
		return new ParameterizedRowMapper<OfferProductAssgnDTO>() {
			
			public OfferProductAssgnDTO mapRow(ResultSet rs, int rowNum)
				throws SQLException {
				
				OfferProductAssgnDTO dto = new OfferProductAssgnDTO();
				
				dto.setOfferCd(rs.getString("offerCd"));
				dto.setOfferDesc(rs.getString("offerDesc"));
				dto.setPosItemCd(rs.getString("posItemCd"));
				dto.setProdCd(rs.getString("prodCd"));
				dto.setProdDesc(rs.getString("prodDesc"));
				
				return dto;
				
				
				
				
			}
		};
	}
	*/
	

}
