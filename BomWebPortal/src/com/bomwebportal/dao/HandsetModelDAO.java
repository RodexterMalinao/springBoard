package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BrandDTO;
import com.bomwebportal.dto.ColorDTO;
import com.bomwebportal.dto.CustomerInformationQuotaDTO;
import com.bomwebportal.dto.ModelDTO;
import com.bomwebportal.exception.DAOException;

public class HandsetModelDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());

	// for mobile detail and
	/*public List<BrandDTO> getBrandList(String locale, String channelId,
			String customerTier, String basketType) throws DAOException {
		List<BrandDTO> brandList = new ArrayList<BrandDTO>();
		
		 * String SQL ="SELECT id , description " +" FROM w_display_lkup "
		 * +" WHERE type = 'BRAND' " +" AND locale = ? "
		 * +" AND id in (SELECT DISTINCT idh.brand_id "
		 * +" FROM w_basket_item_assgn bia, w_item_dtl_hs idh "
		 * +" WHERE bia.item_id = idh.id )";
		 
		
		 * String SQL="SELECT id, description\n" + "  FROM w_display_lkup\n" +
		 * " WHERE type = 'BRAND'\n" + "   AND locale = ?\n" +
		 * "   AND id in (SELECT DISTINCT idh.brand_id\n" +
		 * "                FROM w_basket_item_assgn bia, w_item_dtl_hs idh,\n"
		 * + "                     w_channel_basket_assgn cba\n" +
		 * "               WHERE bia.item_id = idh.id\n" +
		 * "                 AND cba.basket_id = bia.basket_id\n" +
		 * "                    AND cba.channel_id = ?\n" +
		 * "                 AND cba.customer_tier = ?)";
		 

		String SQL = "SELECT id, description\n" + "  FROM w_display_lkup\n"
				+ " WHERE type = 'BRAND'\n" + "   AND locale = ?\n"
				+ "   AND id in (SELECT DISTINCT idh.brand_id\n"
				+ "                FROM w_basket_item_assgn    bia,\n"
				+ "                     w_item_dtl_hs          idh,\n"
				+ "                     w_channel_basket_assgn cba,\n"
				+ "                     w_basket               b\n"
				+ "               WHERE bia.item_id = idh.id\n"
				+ "                 AND cba.basket_id = bia.basket_id\n"
				+ "                 and b.id = cba.basket_id\n"
				+ "                 AND cba.channel_id = ?\n"
				+ "                 AND cba.customer_tier = ?\n"
				+ "                 and b.type = ?)";

		ParameterizedRowMapper<BrandDTO> mapper = new ParameterizedRowMapper<BrandDTO>() {

			// notice the return type with respect to Java 5 covariant return
			// types
			public BrandDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BrandDTO brand = new BrandDTO();
				brand.setBrandId(rs.getString("id"));
				brand.setBrandName(rs.getString("description"));

				return brand;
			}
		};

		try {
			logger.debug("getBrandList @ HandsetModelDTO: " + SQL);
			logger.info(brandList.size());
			brandList = simpleJdbcTemplate.query(SQL, mapper, locale,
					channelId, customerTier, basketType);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			brandList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getBrandList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return brandList;

	}*/
	 //new james
	public List<BrandDTO> getBrandList(String locale, String channelId,
			String customerTier, String basketType, String appDate, String mipBrand) throws DAOException {
		List<BrandDTO> brandList = new ArrayList<BrandDTO>();
		/*
		 * String SQL ="SELECT id , description " +" FROM w_display_lkup "
		 * +" WHERE type = 'BRAND' " +" AND locale = ? "
		 * +" AND id in (SELECT DISTINCT idh.brand_id "
		 * +" FROM w_basket_item_assgn bia, w_item_dtl_hs idh "
		 * +" WHERE bia.item_id = idh.id )";
		 */
		/*
		 * String SQL="SELECT id, description\n" + "  FROM w_display_lkup\n" +
		 * " WHERE type = 'BRAND'\n" + "   AND locale = ?\n" +
		 * "   AND id in (SELECT DISTINCT idh.brand_id\n" +
		 * "                FROM w_basket_item_assgn bia, w_item_dtl_hs idh,\n"
		 * + "                     w_channel_basket_assgn cba\n" +
		 * "               WHERE bia.item_id = idh.id\n" +
		 * "                 AND cba.basket_id = bia.basket_id\n" +
		 * "                    AND cba.channel_id = ?\n" +
		 * "                 AND cba.customer_tier = ?)";
		 */

		/*String SQL = "SELECT id, description\n" + "  FROM w_display_lkup\n"
				+ " WHERE type = 'BRAND'\n" + "   AND locale = ?\n"
				+ "   AND id in (SELECT DISTINCT idh.brand_id\n"
				+ "                FROM w_basket_item_assgn    bia,\n"
				+ "                     w_item_dtl_hs          idh,\n"
				+ "                     w_channel_basket_assgn cba,\n"
				+ "                     w_basket               b\n"
				+ "               WHERE bia.item_id = idh.id\n"
				+ "                 AND cba.basket_id = bia.basket_id\n"
				+ "                 and b.id = cba.basket_id\n"
				+ "                 AND cba.channel_id = ?\n"
				+ "                 AND cba.customer_tier = ?\n"
				+ "                 and b.type = ?)";*/
		
		String SQL = "SELECT id, description " +
					 "FROM w_display_lkup " +
					 "WHERE type = 'BRAND' and locale = ? " +
					 "AND id in (SELECT distinct brand_id FROM " +
					 "w_basket_attribute_mv mv, w_channel_basket_assgn cba " +
					 "WHERE mv.basket_id = cba.basket_id " +
					 "AND cba.channel_id = ? " +
					 "AND mv.customer_tier_id = ? " +
					 "AND mv.basket_type_id = ? " +
					 "AND NVL(mv.NATURE, 'ACQ') = 'ACQ' " +
					 "AND nvl(decode (mv.MIP_BRAND, '9', ?, mv.MIP_BRAND ), '1') = ? " +
					 "AND TO_DATE(?, 'DD/MM/YYYY') between " + 
					 "cba.eff_start_date and NVL(cba.eff_end_date, sysdate))";

		ParameterizedRowMapper<BrandDTO> mapper = new ParameterizedRowMapper<BrandDTO>() {

			// notice the return type with respect to Java 5 covariant return
			// types
			public BrandDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BrandDTO brand = new BrandDTO();
				brand.setBrandId(rs.getString("id"));
				brand.setBrandName(rs.getString("description"));

				return brand;
			}
		};

		try {
			logger.debug("getBrandList @ HandsetModelDTO: " + SQL);
			logger.info(brandList.size());
			brandList = simpleJdbcTemplate.query(SQL, mapper, locale,
					channelId, customerTier, basketType, mipBrand, mipBrand, appDate);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			brandList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getBrandList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return brandList;

	}

	
	//new james
	public List<ModelDTO> getModelList(String locale, String brandId,
			String channelId, String customerTier, String basketType, String appDate, String mipBrand)
			throws DAOException {
		List<ModelDTO> modelList = new ArrayList<ModelDTO>();
		
		/*
		 * String SQL
		 * ="SELECT brand_t.brand_id, brand_t.brand_name, model_t.model_id,"
		 * +"       model_t.model_name, brand_t.locale"
		 * +"  FROM (SELECT ID model_id, description model_name, locale"
		 * +"          FROM w_display_lkup"
		 * +"         WHERE TYPE = 'MODEL') model_t,"
		 * +"       (SELECT ID brand_id, description brand_name, locale"
		 * +"          FROM w_display_lkup"
		 * +"         WHERE TYPE = 'BRAND' AND locale = ?) brand_t,"
		 * +"       (SELECT DISTINCT idh.model_id, idh.brand_id"
		 * +"                   FROM w_basket_item_assgn bia, w_item_dtl_hs idh"
		 * +"                  WHERE bia.item_id = idh.ID) MAP"
		 * +" WHERE model_t.model_id = MAP.model_id"
		 * +"   AND brand_t.brand_id = MAP.brand_id"
		 * +"   AND brand_t.locale = model_t.locale"
		 * +"   AND brand_t.locale = ?"
		 * +"   AND brand_t.brand_id = NVL (?, brand_t.brand_id)";
		 */
		/*
		 * String SQL ="SELECT brand_t.brand_id,\n" +
		 * "       brand_t.brand_name,\n" + "       model_t.model_id,\n" +
		 * "       model_t.model_name,\n" + "       brand_t.locale\n" +
		 * "  FROM (SELECT ID model_id, description model_name, locale\n" +
		 * "          FROM w_display_lkup\n" +
		 * "         WHERE TYPE = 'MODEL') model_t,\n" +
		 * "       (SELECT ID brand_id, description brand_name, locale\n" +
		 * "          FROM w_display_lkup\n" + "         WHERE TYPE = 'BRAND'\n"
		 * + "           AND locale = ?) brand_t,\n" +
		 * "       (SELECT DISTINCT idh.model_id, idh.brand_id\n" +
		 * "          FROM w_basket_item_assgn    bia,\n" +
		 * "               w_item_dtl_hs          idh,\n" +
		 * "               w_channel_basket_assgn cba\n" +
		 * "         WHERE bia.item_id = idh.ID\n" +
		 * "           and cba.basket_id = bia.basket_id\n" +
		 * "           AND cba.channel_id = ?\n" +
		 * "           AND cba.customer_tier = ?) MAP\n" +
		 * " WHERE model_t.model_id = MAP.model_id\n" +
		 * "   AND brand_t.brand_id = MAP.brand_id\n" +
		 * "   AND brand_t.locale = model_t.locale\n" +
		 * "   AND brand_t.locale = ?\n" +
		 * "   AND brand_t.brand_id = NVL(?, brand_t.brand_id)";
		 */
		/*String SQL = "SELECT brand_t.brand_id,\n"
				+ "       brand_t.brand_name,\n"
				+ "       model_t.model_id,\n"
				+ "       model_t.model_name,\n"
				+ "       brand_t.locale\n"
				+ "  FROM (SELECT ID model_id, description model_name, locale\n"
				+ "          FROM w_display_lkup\n"
				+ "         WHERE TYPE = 'MODEL') model_t,\n"
				+ "       (SELECT ID brand_id, description brand_name, locale\n"
				+ "          FROM w_display_lkup\n"
				+ "         WHERE TYPE = 'BRAND'\n"
				+ "           AND locale = ?) brand_t,\n"
				+ "       (SELECT DISTINCT idh.model_id, idh.brand_id\n"
				+ "          FROM w_basket_item_assgn    bia,\n"
				+ "               w_item_dtl_hs          idh,\n"
				+ "               w_channel_basket_assgn cba,\n"
				+ "               w_basket               b\n"
				+ "         WHERE bia.item_id = idh.ID\n"
				+ "           and cba.basket_id = bia.basket_id\n"
				+ "           and b.id = cba.basket_id\n"
				+ "           AND cba.channel_id = ?\n"
				+ "           AND cba.customer_tier = ?\n"
				+ "           and b.type = ?) MAP\n"
				+ " WHERE model_t.model_id = MAP.model_id\n"
				+ "   AND brand_t.brand_id = MAP.brand_id\n"
				+ "   AND brand_t.locale = model_t.locale\n"
				+ "   AND brand_t.locale = ?\n"
				+ "   AND brand_t.brand_id = NVL(?, brand_t.brand_id)\n"
				+ "   order by brand_t.brand_id";*/
		
		String SQL = "SELECT DISTINCT brand.brand_id, model.model_id, brand.brand_name ||' '|| model.model_name model_name" +
					 " FROM (SELECT id model_id, description model_name, locale FROM w_display_lkup WHERE type = 'MODEL') model," +
					 " (SELECT id brand_id, description brand_name, locale FROM w_display_lkup WHERE type = 'BRAND') brand," +
					 " w_basket_attribute_mv mv, w_channel_basket_assgn cba" +
					 " WHERE model.model_id = mv.model_id" +
					 " AND mv.basket_id = cba.basket_id" +
					 " AND brand.brand_id = mv.brand_id" +
					 " AND cba.channel_id = ?" +
					 " AND mv.customer_tier_id = ?" +
					 " AND mv.basket_type_id = ?" +
					 " AND NVL(mv.NATURE, 'ACQ') = 'ACQ' "+
					 " AND model.locale = ?" +
					 " AND brand.locale = ?" +
					 " AND brand.brand_id = NVL(?, brand.brand_id)" +
					 " AND nvl(decode (mv.MIP_BRAND, '9', ?, mv.MIP_BRAND ), '1') = ?" +
					 " AND TO_DATE(?, 'DD/MM/YYYY') between" +
					 " cba.eff_start_date and NVL(cba.eff_end_date, sysdate)" +
					 " ORDER BY brand.brand_id";

		// System.out.println("brandId:"+brandId);

		try {
			logger.debug("getModelList @ HandsetModelDTO: " + SQL);
			logger.info(modelList.size());
			modelList = simpleJdbcTemplate.query(SQL,
					new ParameterizedRowMapper<ModelDTO>() {

						// notice the return type with respect to Java 5
						// covariant return types
						public ModelDTO mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							ModelDTO model = new ModelDTO();
							model.setBrandId(rs.getString("brand_id"));
							model.setModelId(rs.getString("model_id"));
							model.setModelName(rs.getString("model_name"));
							return model;
						}
					}, channelId, customerTier, basketType, locale, locale, brandId, mipBrand, mipBrand, appDate);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			modelList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getModelList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return modelList;

	}


	// need to join customer tier
	// first page(modellist) using,
	//add appdate 20120507
	public List<ModelDTO> getHandSetDisplayList(String locale, String brandId,
			String modelId, String channelId, String customerTier,
			String basketType, String appDate, String mipBrand) throws DAOException {
		List<ModelDTO> modelList = new ArrayList<ModelDTO>();
		/*
		 * String SQL
		 * =" SELECT brand_t.brand_id, brand_t.brand_name, model_t.model_id,"
		 * +"       model_t.model_name, model_html.image_path, model_html.html,"
		 * +"       model_t.locale"
		 * +"  FROM (SELECT ID model_id, description model_name, locale"
		 * +"         FROM w_display_lkup"
		 * +"         WHERE TYPE = 'MODEL') model_t,"
		 * +"      (SELECT ID brand_id, description brand_name, locale"
		 * +"          FROM w_display_lkup"
		 * +"        WHERE TYPE = 'BRAND') brand_t,"
		 * +"      (SELECT DISTINCT idh.model_id, idh.brand_id"
		 * +"                  FROM w_basket_item_assgn bia, w_item_dtl_hs idh"
		 * +"                 WHERE bia.item_id = idh.ID) MAP,"
		 * +"      (SELECT image_path, html, brand_id, model_id, locale"
		 * +"         FROM w_hs_display_lkup"
		 * +"        WHERE display_type = 'DETAIL') model_html"
		 * +" WHERE model_t.model_id = MAP.model_id"
		 * +"   AND brand_t.brand_id = MAP.brand_id"
		 * +"  AND model_html.model_id = MAP.model_id"
		 * +" AND model_html.brand_id = MAP.brand_id"
		 * +" AND model_t.locale = model_html.locale"
		 * +"  AND model_t.locale = brand_t.locale" +"  AND model_t.locale = ?"
		 * +"  AND MAP.brand_id = NVL (?, MAP.brand_id)"
		 * +"  AND MAP.model_id = NVL (?, MAP.model_id)";
		 */
		/*
		 * String SQL= "SELECT brand_t.brand_id,\n" +
		 * "       brand_t.brand_name,\n" + "       model_t.model_id,\n" +
		 * "       model_t.model_name,\n" + "       model_html.image_path,\n" +
		 * "       model_html.html,\n" + "       model_t.locale\n" +
		 * "  FROM (SELECT ID model_id, description model_name, locale\n" +
		 * "          FROM w_display_lkup\n" +
		 * "         WHERE TYPE = 'MODEL') model_t,\n" +
		 * "       (SELECT ID brand_id, description brand_name, locale\n" +
		 * "          FROM w_display_lkup\n" +
		 * "         WHERE TYPE = 'BRAND') brand_t,\n" +
		 * "       (SELECT DISTINCT idh.model_id, idh.brand_id\n" +
		 * "          FROM w_basket_item_assgn    bia,\n" +
		 * "               w_item_dtl_hs          idh,\n" +
		 * "               w_channel_basket_assgn cba\n" +
		 * "         WHERE bia.item_id = idh.ID\n" +
		 * "           and cba.basket_id = bia.basket_id\n" +
		 * "           AND cba.channel_id = ?\n" +
		 * "           AND cba.customer_tier = ?) MAP,\n" +
		 * "       (SELECT image_path, html, brand_id, model_id, locale\n" +
		 * "          FROM w_hs_display_lkup\n" +
		 * "         WHERE display_type = 'DETAIL') model_html\n" +
		 * " WHERE model_t.model_id = MAP.model_id\n" +
		 * "   AND brand_t.brand_id = MAP.brand_id\n" +
		 * "   AND model_html.model_id = MAP.model_id\n" +
		 * "   AND model_html.brand_id = MAP.brand_id\n" +
		 * "   AND model_t.locale = model_html.locale\n" +
		 * "   AND model_t.locale = brand_t.locale\n" +
		 * "   AND model_t.locale = ?\n" +
		 * "   AND MAP.brand_id = NVL(?, MAP.brand_id)\n" +
		 * "   AND MAP.model_id = NVL(?, MAP.model_id)";
		 */

		/*String SQL = "SELECT brand_t.brand_id,\n"
				+ "       brand_t.brand_name,\n"
				+ "       model_t.model_id,\n"
				+ "       brand_t.brand_name ||' '||model_t.model_name model_name,\n"
				+ "       model_html.image_path,\n"
				+ "       model_html.html,\n"
				+ "       model_t.locale\n"
				+ "  FROM (SELECT ID model_id, description model_name, locale\n"
				+ "          FROM w_display_lkup\n"
				+ "         WHERE TYPE = 'MODEL') model_t,\n"
				+ "       (SELECT ID brand_id, description brand_name, locale\n"
				+ "          FROM w_display_lkup\n"
				+ "         WHERE TYPE = 'BRAND') brand_t,\n"
				+ "       (SELECT DISTINCT idh.model_id, idh.brand_id\n"
				+ "          FROM w_basket_item_assgn    bia,\n"
				+ "               w_item_dtl_hs          idh,\n"
				+ "               w_channel_basket_assgn cba,\n"
				+ "               w_basket               b,\n"
				+ "               w_basket_attribute_mv  bam\n"
				+ "         WHERE bia.item_id = idh.ID\n"
				+ "           and cba.basket_id = bia.basket_id\n"
				+ "           and b.id = cba.basket_id\n"
				+ "           AND cba.channel_id = ?\n"
				+ "           AND cba.customer_tier = ?\n"
				+ "           and b.type = ? \n"
				+ "           AND bia.basket_id = bam.basket_id (+) \n"
				+ "           AND nvl(bam.nature, 'ACQ') = 'ACQ' \n"
				+ "           AND nvl(decode (bam.MIP_BRAND, '9', ?, bam.MIP_BRAND ), '1') = ? \n"
				+ "           AND TO_DATE(?, 'DD/MM/YYYY') between cba.eff_start_date and NVL(cba.eff_end_date, sysdate)) MAP,\n"
				+ "       (SELECT image_path, html, brand_id, model_id, locale, DISPLAY_SEQ\n"
				+ "          FROM w_hs_display_lkup\n"
				+ "         WHERE display_type = 'DETAIL') model_html\n"
				+ " WHERE model_t.model_id = MAP.model_id\n"
				+ "   AND brand_t.brand_id = MAP.brand_id\n"
				+ "   AND model_html.model_id = MAP.model_id\n"
				+ "   AND model_html.brand_id = MAP.brand_id\n"
				+ "   AND model_t.locale = model_html.locale\n"
				+ "   AND model_t.locale = brand_t.locale\n"
				+ "   AND model_t.locale = ?\n"
				+ "   AND MAP.brand_id = NVL(?, MAP.brand_id)\n"
				+ "   AND MAP.model_id = NVL(?, MAP.model_id)\n"
				+ "    order by MODEL_HTML.DISPLAY_SEQ";*/

		String SQL = "SELECT \n"
					+ " brand_t.brand_id, \n"
					+ " brand_t.brand_name, \n"
					+ " model_t.model_id, \n"
					+ " brand_t.brand_name ||' '||model_t.model_name model_name, \n"
					+ " model_html.image_path, \n"
					+ " model_html.html, \n"
					+ " model_t.locale \n"
					+ "FROM \n"
					+ " (SELECT ID model_id, description model_name, locale FROM w_display_lkup WHERE TYPE = 'MODEL') model_t, \n"
					+ " (SELECT ID brand_id, description brand_name, locale FROM w_display_lkup WHERE TYPE = 'BRAND') brand_t, \n"
					+ " (SELECT DISTINCT bam.model_id, bam.brand_id \n"
					+ "  FROM w_channel_basket_assgn cba, w_basket_attribute_mv bam \n"
					+ "  WHERE cba.channel_id = ? \n"
					+ "  AND cba.customer_tier = ? \n"
					+ "  AND TO_DATE(?, 'DD/MM/YYYY') BETWEEN cba.eff_start_date AND NVL(cba.eff_end_date, sysdate) \n"
					+ "  AND cba.basket_id = bam.basket_id \n"
					+ "  AND bam.basket_type_id = ? \n"
					+ "  AND NVL(bam.nature, 'ACQ') = 'ACQ' \n"
					+ "  AND NVL(DECODE(bam.MIP_BRAND, '9', ?, bam.MIP_BRAND), '1') = ?) MAP, \n"
					+ "   (SELECT image_path, html, brand_id, model_id, locale, display_seq FROM w_hs_display_lkup WHERE display_type = 'DETAIL') model_html \n"
					+ "WHERE \n"
					+ " model_t.model_id = MAP.model_id \n"
					+ " AND brand_t.brand_id = MAP.brand_id \n"
					+ " AND model_html.model_id = MAP.model_id \n"
					+ " AND model_html.brand_id = MAP.brand_id \n"
					+ " AND model_t.locale = model_html.locale \n"
					+ " AND model_t.locale = brand_t.locale \n"
					+ " AND model_t.locale = ? \n"
					+ " AND MAP.brand_id = NVL(?, MAP.brand_id) \n"
					+ " AND MAP.model_id = NVL(?, MAP.model_id) \n"
					+ "ORDER BY \n"
					+ " model_html.display_seq \n";
		
		ParameterizedRowMapper<ModelDTO> mapper = new ParameterizedRowMapper<ModelDTO>() {
			// notice the return type with respect to Java 5 covariant return
			// types
			public ModelDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ModelDTO model = new ModelDTO();
				model.setModelId(rs.getString("model_id"));
				model.setModelName(rs.getString("model_name"));
				model.setModelImagePath(rs.getString("image_path"));
				model.setModelHtml(rs.getString("html"));
				model.setBrandId(rs.getString("brand_id"));
				return model;
			}
		};

		try {
			logger.debug("getHandSetDisplayList @ HandsetModelDTO: " + SQL);
			logger.info(modelList.size());
			modelList = simpleJdbcTemplate.query(SQL, mapper, channelId, customerTier, appDate, basketType, mipBrand, mipBrand, locale, brandId, modelId);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			modelList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getHandSetDisplayList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return modelList;

	}

	// need to join customer tier
	// a model( mobile ) info with basket
	// get color with ns price
	public ModelDTO getMobileDetail(String locale, String brandId,
			String modelId , String appDate) throws DAOException {
		List<ModelDTO> modelList = new ArrayList<ModelDTO>();

		String SQLModel = "SELECT model_t.model_id, model_t.model_name, model_t.locale,\n"
				+ "       lkup.model_image_path, lkup.model_html\n"
				+ "  FROM (SELECT ID model_id, description model_name, locale\n"
				+ "          FROM w_display_lkup\n"
				+ "         WHERE TYPE = 'MODEL'\n"
				+ "         ) model_t,\n"
				+ "       (SELECT image_path model_image_path, html model_html, locale, brand_id,\n"
				+ "               model_id\n"
				+ "          FROM w_hs_display_lkup\n"
				+ "         WHERE display_type = 'DETAIL') lkup\n"
				+ " WHERE model_t.model_id = lkup.model_id\n"
				+ "   AND model_t.locale = lkup.locale\n"
				+ "   AND model_t.locale = ?\n"
				+ " --  AND lkup.brand_id = ?\n" 
				+ "   AND lkup.model_id = ?";

		ParameterizedRowMapper<ModelDTO> mapper = new ParameterizedRowMapper<ModelDTO>() {
			
			public ModelDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ModelDTO model = new ModelDTO();
				model.setModelId(rs.getString("model_id"));
				model.setModelName(rs.getString("model_name"));
				model.setModelImagePath(rs.getString("model_image_path"));
				model.setModelHtml(rs.getString("model_html"));

				return model;
			}
		};

		try {
			// by herbert 20111110 remove all unwanted logger
			logger.debug("getMobileDetail @ HandsetModelDTO: " + SQLModel);
			
			modelList = simpleJdbcTemplate.query(SQLModel, mapper, locale,
					modelId);
			// get color
			if (modelList != null && modelList.size() > 0) {
				modelList.get(0).setColorDto(
						getColorList(locale, brandId, modelList.get(0)
								.getModelId(), appDate));
			}

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			modelList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getColorList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return modelList.get(0);// only return the first one

	}

	// add app date and with NS price 
	public List<ColorDTO> getColorList(String locale, String brandId,
			String modelId, String appDate) throws DAOException {
		List<ColorDTO> colorList = new ArrayList<ColorDTO>();
		
		String SQL=
			"select distinct DL.ID,\n" +
			"                DL.DESCRIPTION ||\n" + 
			"                DECODE(HNP.NS_PRICE, null, null, ' ($' || HNP.NS_PRICE||')') DESCRIPTION,\n" + 
			"                HNP.POS_ITEM_CD,\n" + 
			"                HNP.NS_PRICE,\n" + 
			"                HNP.EFF_START_DATE,\n" + 
			"                HNP.EFF_END_DATE\n" + 
			"  from W_DISPLAY_LKUP DL,\n" + 
			"       W_ITEM_DTL_HS IDH,\n" + 
			"       W_ITEM_PRODUCT_POS_ASSGN IPPA,\n" + 
			"       (select *\n" + 
			"          from W_HANDSET_NS_PRICE\n" + 
			"         where TRUNC(to_date(?, 'dd/MM/yyyy')) between EFF_START_DATE and\n" + 
			"               TRUNC(NVL(EFF_END_DATE, sysdate))) HNP\n" + 
			" where IDH.COLOR_ID = DL.ID --COLOR ID mapping\n" + 
			"   and DL.TYPE = 'COLOR'\n" + 
			"   and DL.LOCALE = ?\n" + 
			"   and IDH.ID = IPPA.ITEM_ID(+)\n" + 
			"   and IDH.MODEL_ID = ? --25\n" + 
			"   and IPPA.POS_ITEM_CD = HNP.POS_ITEM_CD(+)";


		ParameterizedRowMapper<ColorDTO> mapper = new ParameterizedRowMapper<ColorDTO>() {
			public ColorDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ColorDTO color = new ColorDTO();
				color.setColorId(rs.getString("id"));
				color.setColorName(rs.getString("description"));
				return color;
			}
		};

		try {
			// by herbert 20111110 remove all unwanted logger
			logger.debug("getColorList @ HandsetModelDTO: " + SQL);
			colorList = simpleJdbcTemplate.query(SQL, mapper, appDate, locale, 
					modelId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			colorList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getColorList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return colorList;

	}

	// for mobiledetail
	public List<BasketDTO> getBasketList(String locale, String brandId,
			String modelId, String colorId, String channelId,
			String customerTier, String basketType, String appDate,final List<CustomerInformationQuotaDTO> existQuotaDtoList,
			String mipBrand, String mipSimType) throws DAOException {
		List<BasketDTO> basketList = new ArrayList<BasketDTO>();

		/*String SQL = "SELECT bhhv.basket_id, idl.HTML, bhhv.DISPLAY_TAB\n"
				+ "  FROM W_ITEM_DISPLAY_LKUP idl,\n"
				+ "       (SELECT distinct BASKET_ID, HSRB_ITEM_ID, CHANNEL_ID, PRIORITY, COLOR_ID, DISPLAY_TAB\n"
				+ "          FROM W_BASKET_HS_HSRB_MV, (SELECT sysdate EFF_DATE FROM DUAL) d\n"
				+ "         WHERE BRAND_ID = ?\n"
				+ "           AND MODEL_ID = ?\n"
				+ "           AND COLOR_ID = ?\n"
				+ "           AND (CHANNEL_ID = ? OR CHANNEL_ID IS NULL)\n"
				+ "           AND CUSTOMER_TIER = ? and BASKET_TYPE =?\n"
				+ // add BASKET_TYPE 20110109
				"           AND HS_EFF_START_DATE <= d.EFF_DATE\n"
				+ "           AND (HS_EFF_END_DATE >= d.EFF_DATE OR HS_EFF_END_DATE IS NULL)\n"
				+ "           AND HSRB_EFF_START_DATE <= d.EFF_DATE\n"
				+ "           AND (HSRB_EFF_END_DATE >= d.EFF_DATE OR HSRB_EFF_END_DATE IS NULL)) bhhv\n"
				+ " WHERE idl.locale = ?\n"
				+ "   AND idl.display_type = 'HSRB_PROMOT'\n"
				+ "   AND idl.item_id = bhhv.HSRB_ITEM_ID\n"
				+ " ORDER BY bhhv.DISPLAY_TAB, bhhv.PRIORITY DESC";*/
		

		String SQL ="select BHHMV.BASKET_ID,\n" +
		"       DECODE(BAMV.OFFER_TYPE_ID, '1', 'Y', 'N') PH_IND,\n" + 
		"       BAMV.CUSTOMER_TIER || decode(BAMV.OFFER_TYPE, null, '',' / ' || BAMV.OFFER_TYPE) customer_tier,\n" +  
		"       BAMV.CUSTOMER_TIER || decode(BAMV.OFFER_TYPE, null, '',' / ' || BAMV.OFFER_TYPE) || decode(BAMV.dummy_ind, null, '',' / ' || 'DUMMY') sort_desc,\n" +  
		"       BAMV.OFFER_TYPE, IDL.HTML,\n" + 
		"       NVL(BAMV.MIP_BRAND, '1') MIP_BRAND,\n" + 
		"       NVL(BAMV.MIP_SIM_TYPE, 'H') MIP_SIM_TYPE,\n" +  
		"       BHHMV.DISPLAY_TAB\n" + 
		"  from W_CHANNEL_BASKET_ASSGN CBA ,W_BASKET_HS_HSRB_MV BHHMV,\n" + 
		"       W_BASKET_ATTRIBUTE_MV  BAMV,\n" + 
		"       W_ITEM_DISPLAY_LKUP    IDL\n" + 
		" where CBA.BASKET_ID = BHHMV.BASKET_ID\n" + 
		"   and CBA.CHANNEL_ID = BHHMV.CHANNEL_ID\n" + 
		"   and BHHMV.HSRB_ITEM_ID = IDL.ITEM_ID\n" + 
		"   and BHHMV.BASKET_ID = BAMV.BASKET_ID\n" + 
		"   and IDL.LOCALE = ? --LOCALE--\n" + 
		"   and IDL.DISPLAY_TYPE = 'HSRB_PROMOT'\n" + 
		"   and TO_DATE(?, 'DD/MM/YYYY') between BHHMV.HS_EFF_START_DATE and\n" + 
		"       NVL(BHHMV.HS_EFF_END_DATE, sysdate)\n" + 
		"   and TO_DATE(?, 'DD/MM/YYYY') between BHHMV.HS_PRICE_EFF_START_DATE and\n" + 
		"       NVL(BHHMV.HS_PRICE_EFF_END_DATE, sysdate)\n" + 
		"   and TO_DATE(?, 'DD/MM/YYYY') between BHHMV.HSRB_EFF_START_DATE and\n" + 
		"       NVL(BHHMV.HSRB_EFF_END_DATE, sysdate)\n" + 
		"   and BAMV.BRAND_ID = ? --BRAND_ID--2--\n" + 
		"   and BAMV.MODEL_ID = ? --MODEL_ID--5--\n" + 
		"   and BAMV.COLOR_ID = ? --COLOR_ID--10012--\n" + 
		"   and BAMV.BASKET_TYPE_ID = ? --BASKET_TYPE_ID--4--\n" + 
		"   and NVL(BAMV.NATURE, 'ACQ') = 'ACQ' " + 
		"   and CBA.CHANNEL_ID = ? --CHANNEL_ID--2--\n" + 
		"   and BAMV.customer_tier_id = ? --customer_tier_id--\n" + 
		"   and TO_DATE(?, 'DD/MM/YYYY') between CBA.EFF_START_DATE and\n" + 
		"       NVL(CBA.EFF_END_DATE, sysdate)\n" + 
		"   and nvl(decode (BAMV.MIP_BRAND, '9', ?, BAMV.MIP_BRAND ), '1') = ? \n" +
		" order by CBA.DISPLAY_TAB, CBA.PRIORITY desc";


		ParameterizedRowMapper<BasketDTO> mapper = new ParameterizedRowMapper<BasketDTO>() {
			public BasketDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BasketDTO basket = new BasketDTO();
				basket.setBasketId(rs.getString("basket_id"));
				basket.setBasketHtml(rs.getString("HTML"));
				basket.setDisplayTab(rs.getString("DISPLAY_TAB"));
				basket.setPublicHouseBaksetInd(rs.getString("PH_IND"));
				basket.setBelowQuotaInd(checkQuota(rs.getString("basket_id"),existQuotaDtoList));
				basket.setCustomerTierDesc(rs.getString("customer_tier")); //show more info 
				basket.setSortDesc(rs.getString("sort_desc"));
				basket.setMipBrand(rs.getString("MIP_BRAND"));
				basket.setMipSimType(rs.getString("MIP_SIM_TYPE"));
				return basket;
			}
		};

		try {
			// by herbert 20111110 remove all unwanted logger
			logger.debug("getBasketList @ HandsetModelDTO: " + SQL);
			basketList = simpleJdbcTemplate.query(SQL, mapper, /*brandId,
					modelId, colorId, channelId, customerTier, basketType,
					locale*/
					/*locale, appDate, appDate,appDate, brandId,modelId, colorId,   channelId, appDate
					*/
					locale, appDate,appDate, appDate, brandId,modelId,colorId,  basketType, channelId, customerTier,appDate,
					mipBrand, mipBrand
			);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			basketList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getBasketList():", e);
			throw new DAOException(e.getMessage(), e);
		}

		return basketList;

	}

	public List<BasketDTO> getSimOnlyBasketList(String locale,
			String ratePlanType, String basketType, String customerTier, String channelId, String appDate,final List<CustomerInformationQuotaDTO> existQuotaDtoList,
			String mipBrand, String mipSimType)
			throws DAOException {
		List<BasketDTO> basketList = new ArrayList<BasketDTO>();

		String SQL=
			"select BIA.BASKET_ID,\n" +
			"       CBA.PRIORITY,\n" + 
			"       NVL(BDL.HTML, IDL.HTML) HTML,\n" + 
			"       CBA.DISPLAY_TAB,\n" + 
			"       DECODE(BAMV.OFFER_TYPE_ID, '1', 'Y', 'N') PH_IND,\n" + 
			"       BAMV.OFFER_TYPE,\n" + 
			"       BAMV.CUSTOMER_TIER || decode(BAMV.OFFER_TYPE, null, '',' / ' || BAMV.OFFER_TYPE) || decode(BAMV.dummy_ind, null, '',' / ' || 'DUMMY') sort_desc,\n" +
			"       BAMV.CUSTOMER_TIER || decode(BAMV.OFFER_TYPE, null, '','/' || BAMV.OFFER_TYPE) CUSTOMER_TIER,\n" +
			"       NVL(BAMV.MIP_BRAND, '1') MIP_BRAND,\n" + 
			"       NVL(BAMV.MIP_SIM_TYPE, 'H') MIP_SIM_TYPE\n" +  
			"  from W_CHANNEL_BASKET_ASSGN CBA,\n" + 
			"       W_BASKET_ATTRIBUTE_MV  BAMV,\n" + 
			"       W_BASKET_ITEM_ASSGN    BIA,\n" + 
			"       W_ITEM                 I,\n" + 
			"       W_ITEM_DISPLAY_LKUP    IDL,\n" + 
			"       W_BASKET_DISPLAY_LKUP  BDL\n" + 
			" where CBA.BASKET_ID = BIA.BASKET_ID\n" + 
			"   and CBA.BASKET_ID = BAMV.BASKET_ID\n" + 
			"   and BIA.ITEM_ID = I.ID\n" + 
			"   and I.TYPE = 'RP'\n" + 
			"   and I.ID = IDL.ITEM_ID\n" + 
			"   and IDL.LOCALE = ? --locale--\n" + 
			"   and IDL.DISPLAY_TYPE = 'RP_PROMOT'\n" + 
			"   and CBA.BASKET_ID = BDL.BASKET_ID(+)\n" + 
			"   and BDL.LOCALE(+) = ? --locale--\n" + 
			"   and BDL.DISPLAY_TYPE(+) = 'RP_PROMOT'\n" + 
			"   and BAMV.RP_TYPE_ID = ? --RP_TYPE_ID--\n" + 
			"   and BAMV.BASKET_TYPE_ID = ? --BASKET_TYPE_ID--\n" + 
			"   and NVL(BAMV.NATURE, 'ACQ') = 'ACQ' " + 
			"   and CBA.CHANNEL_ID = ? --CBA.CHANNEL_ID--\n" + 
			"   and CBA.CUSTOMER_TIER = ?--CBA.CUSTOMER_TIER--\n" + 
			"   and TO_DATE(?, 'DD/MM/YYYY') between CBA.EFF_START_DATE and--appDate\n" + 
			"       NVL(CBA.EFF_END_DATE, sysdate)\n" + 
			"   and nvl(decode (BAMV.MIP_BRAND, '9', ?, BAMV.MIP_BRAND ), '1') = ? \n" +
			"   order by CBA.DISPLAY_TAB, CBA.PRIORITY desc";


		ParameterizedRowMapper<BasketDTO> mapper = new ParameterizedRowMapper<BasketDTO>() {
			public BasketDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BasketDTO basket = new BasketDTO();
				basket.setBasketId(rs.getString("basket_id"));
				basket.setBasketHtml(rs.getString("HTML"));
				basket.setDisplayTab(rs.getString("display_tab"));
				basket.setPublicHouseBaksetInd(rs.getString("PH_IND"));
				basket.setBelowQuotaInd(checkQuota(rs.getString("basket_id"), existQuotaDtoList));
				basket.setCustomerTierDesc(rs.getString("customer_tier"));
				basket.setSortDesc(rs.getString("sort_desc"));
				basket.setMipBrand(rs.getString("MIP_BRAND"));
				basket.setMipSimType(rs.getString("MIP_SIM_TYPE"));
				return basket;
			}
		};

		try {

			logger.info("getSimOnlyBasketList @ HandsetModelDAO: ");
			logger.debug("getSimOnlyBasketList @ HandsetModelDAO: " + SQL);
			basketList = simpleJdbcTemplate.query(SQL, mapper, 
					  locale, locale,	ratePlanType, basketType, channelId, customerTier, appDate,
					  mipBrand, mipBrand
			);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			basketList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getSimOnlyBasketList():", e);
			throw new DAOException(e.getMessage(), e);
		}

		return basketList;

	}

	public String getSSFormDesc(String orderId) throws DAOException {

		List<String> ssFormDesc = new ArrayList<String>();

		String SQL = "SELECT IDH.SS_FORM_DESC " + "FROM W_ITEM_DTL_HS IDH, "
				+ "  BOMWEB_SUBSCRIBED_ITEM SI " + "WHERE IDH.ID    = SI.ID "
				+ "AND SI.TYPE     = 'HS' " + "AND SI.ORDER_ID = ?";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String result = "";
				result = rs.getString("ss_form_desc");

				return result;
			}
		};
		try {
			logger.debug("getSSFormDescList @ HandsetModelDTO: " + SQL);

			ssFormDesc = simpleJdbcTemplate.query(SQL, mapper, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			ssFormDesc = null;
		} catch (Exception e) {
			logger.error("Exception caught in getSSFormDescList()", e);
			throw new DAOException(e.getMessage(), e);
		}

		if (ssFormDesc == null || ssFormDesc.size() == 0) {
			ssFormDesc.add(0, "");
		}
		return ssFormDesc.get(0);

	}

	public List<BrandDTO> getCallListBrandList(String locale, String channelId,
			String jobList, String basketType, String rpType, String appDate)
			throws DAOException {
		List<BrandDTO> brandList = new ArrayList<BrandDTO>();
		if ("NONE".equals(rpType)) {
			rpType = null;
		}

		String SQL = "select distinct A.PARM_TYPE_VAL BRAND,\n"
				+ "                A.PARM_TYPE_ID  BRAND_ID\n"
				+
				// "                B.PARM_TYPE_VAL MODEL,\n" +
				// "                B.PARM_TYPE_ID  MODEL_ID\n" +
				"  from W_BASKET_PARM A, W_BASKET_PARM B\n"
				+ " where A.PARM_TYPE = 'BRAND'\n"
				+ "   and B.PARM_TYPE = 'MODEL'\n"
				+ "   and A.BASKET_ID = B.BASKET_ID\n"
				+ "   and A.BASKET_ID in\n"
				+ "       (select distinct BASKET_ID\n"
				+ "          from W_BASKET_PARM\n"
				+ "         where PARM_TYPE = 'RP_TYPE'\n"
				+ "           and PARM_TYPE_ID = nvl(?, PARM_TYPE_ID) --&RP_TYPE\n"
				+ "           and BASKET_ID in\n"
				+ "               (select distinct BASKET_ID\n"
				+ "                  from W_BASKET_PARM\n"
				+ "                 where PARM_TYPE = 'BASKET_TYPE'\n"
				+ "                   and PARM_TYPE_ID = ? --&BASKET_TYPE\n"
				+ "                   and BASKET_ID in\n"
				+ "                       (select distinct CBA.BASKET_ID\n"
				+ "                          from BOMWEB_JOBLIST_BASKET_ASSGN JBA,\n"
				+ "                               W_CHANNEL_BASKET_ASSGN      CBA\n"
				+ "                         where JBA.JOB_LIST = ? --'IBSP02'--'&job_list'\n"
				+ "                           and TO_DATE(?, 'DD/MM/YYYY') between --&date\n"
				+ "                               JBA.START_DATE and NVL(JBA.END_DATE, sysdate)\n"
				+ "                           and JBA.BASKET_ID = CBA.BASKET_ID\n"
				+ "                           and CBA.CHANNEL_ID = ?) -- Channel ID\n"
				+ "                ))";

		ParameterizedRowMapper<BrandDTO> mapper = new ParameterizedRowMapper<BrandDTO>() {

			// notice the return type with respect to Java 5 covariant return
			// types
			public BrandDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BrandDTO brand = new BrandDTO();
				brand.setBrandId(rs.getString("BRAND_ID"));
				brand.setBrandName(rs.getString("BRAND"));

				return brand;
			}
		};

		try {
			logger.debug("getBrandList @ HandsetModelDTO: " + SQL);
			logger.info(brandList.size());
			brandList = simpleJdbcTemplate.query(SQL, mapper,

			rpType, basketType, jobList, appDate, channelId
			// locale, channelId,customerTier , basketType
					);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			brandList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getBrandList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return brandList;

	}

	public ModelDTO getCallListMobileDetail(String locale, String channelId,
			String callList, String basketType, String rpType, String brandId,
			String modelId, String appDate) throws DAOException {
		List<ModelDTO> modelList = new ArrayList<ModelDTO>();

		String SQLModel = "SELECT model_t.model_id, model_t.model_name, model_t.locale,\n"
				+ "       lkup.model_image_path, lkup.model_html\n"
				+ "  FROM (SELECT ID model_id, description model_name, locale\n"
				+ "          FROM w_display_lkup\n"
				+ "         WHERE TYPE = 'MODEL'\n"
				+ "         ) model_t,\n"
				+ "       (SELECT image_path model_image_path, html model_html, locale, brand_id,\n"
				+ "               model_id\n"
				+ "          FROM w_hs_display_lkup\n"
				+ "         WHERE display_type = 'DETAIL') lkup\n"
				+ " WHERE model_t.model_id = lkup.model_id\n"
				+ "   AND model_t.locale = lkup.locale\n"
				+ "   AND model_t.locale = ?\n"
				+ " --  AND lkup.brand_id = ?\n" + "   AND lkup.model_id = ?";


		ParameterizedRowMapper<ModelDTO> mapper = new ParameterizedRowMapper<ModelDTO>() {
			
			public ModelDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ModelDTO model = new ModelDTO();
				model.setModelId(rs.getString("model_id"));
				model.setModelName(rs.getString("model_name"));
				model.setModelImagePath(rs.getString("model_image_path"));
				model.setModelHtml(rs.getString("model_html"));

				return model;
			}
		};

		try {
			// by herbert 20111110 remove all unwanted logger
			logger.debug("getMobileDetail @ HandsetModelDTO: " + SQLModel);

			modelList = simpleJdbcTemplate.query(SQLModel, mapper, locale,
					modelId);
			// get color
			if (modelList != null && modelList.size() > 0) {
				modelList.get(0).setColorDto(
						getColorList(locale, brandId, modelList.get(0)
								.getModelId(), appDate));//pass with appDate
			}

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			modelList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getColorList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return modelList.get(0);// only return the first one

	}
	
	public List<BasketDTO> getCallListBasketList(String locale, String brandId,
			String modelId, String colorId, String channelId,
			String customerTier, String basketType, String rpType,
			String callList, String appDate,
			final List<CustomerInformationQuotaDTO> existQuotaDtoList,
			String mipBrand, String mipSimType)
			throws DAOException {
		List<BasketDTO> basketList = new ArrayList<BasketDTO>();

		String SQL =
		"select BHHMV.BASKET_ID, DECODE(BAMV.OFFER_TYPE_ID, '1', 'Y', 'N') PH_IND, BAMV.CUSTOMER_TIER || decode(BAMV.OFFER_TYPE, null, '','/' || BAMV.OFFER_TYPE) CUSTOMER_TIER, IDL.HTML, BHHMV.DISPLAY_TAB,\n"
			+ "       BAMV.CUSTOMER_TIER || decode(BAMV.OFFER_TYPE, null, '',' / ' || BAMV.OFFER_TYPE) || decode(BAMV.dummy_ind, null, '',' / ' || 'DUMMY') sort_desc \n"   
			+ "       NVL(BAMV.MIP_BRAND, '1') MIP_BRAND,\n" 
			+ "       NVL(BAMV.MIP_SIM_TYPE, 'H') MIP_SIM_TYPE,\n"
			+ "  from W_BASKET_HS_HSRB_MV         BHHMV,\n"
				+ "       W_BASKET_ATTRIBUTE_MV       BAMV,\n"
				+ "       BOMWEB_JOBLIST_BASKET_ASSGN JBA,\n"
				+ "       W_ITEM_DISPLAY_LKUP         IDL\n"
				+ " where BHHMV.HSRB_ITEM_ID = IDL.ITEM_ID\n"
				+ "   and BHHMV.BASKET_ID = BAMV.BASKET_ID\n"
				+ "   and BHHMV.BASKET_ID = JBA.BASKET_ID\n"
				+ "   and IDL.LOCALE = ? --LOCALE--\n"
				+ "   and idl.display_type='HSRB_PROMOT' \n"
				+ "   and TO_DATE(?, 'DD/MM/YYYY') between JBA.START_DATE and\n"
				+ "       NVL(JBA.END_DATE, sysdate)\n"
				+ "   and TO_DATE(?, 'DD/MM/YYYY') between BHHMV.HS_EFF_START_DATE and\n"
				+ "       NVL(BHHMV.HS_EFF_END_DATE, sysdate)\n"
				+ "   and TO_DATE(?, 'DD/MM/YYYY') between BHHMV.HS_PRICE_EFF_START_DATE and\n"
				+ "       NVL(BHHMV.HS_PRICE_EFF_END_DATE, sysdate)\n"
				+ "   and TO_DATE(?, 'DD/MM/YYYY') between BHHMV.HSRB_EFF_START_DATE and\n"
				+ "       NVL(BHHMV.HSRB_EFF_END_DATE, sysdate)\n"
				+ "   and BAMV.BRAND_ID = ? --BRAND_ID--2--\n"
				+ "   and BAMV.MODEL_ID = ? --MODEL_ID--5--\n"
				+ "   and BAMV.COLOR_ID = ? --COLOR_ID--10012--\n"
				+ "   and BAMV.RP_TYPE_ID = NVL(?, BAMV.RP_TYPE_ID) --RP_TYPE_ID--\n"
				+ "   and BAMV.BASKET_TYPE_ID = ? --BASKET_TYPE_ID--4--\n"
				+ "   and NVL(BAMV.NATURE, 'ACQ') = 'ACQ' "
				+ "   and BHHMV.CHANNEL_ID = ? --CHANNEL_ID--2--\n"
				+ "   and JBA.JOB_LIST = ? --JOB_LIST--'IBSP02'--\n"
				+ "   and nvl(decode (BAMV.MIP_BRAND, '9', ?, BAMV.MIP_BRAND ), '1') = ? \n" 
				+ " order by BHHMV.DISPLAY_TAB, BHHMV.PRIORITY desc";

		ParameterizedRowMapper<BasketDTO> mapper = new ParameterizedRowMapper<BasketDTO>() {
			public BasketDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BasketDTO basket = new BasketDTO();
				basket.setBasketId(rs.getString("basket_id"));
				basket.setBasketHtml(rs.getString("HTML"));
				basket.setDisplayTab(rs.getString("DISPLAY_TAB"));
				basket.setBelowQuotaInd(checkQuota(rs.getString("basket_id"),
						existQuotaDtoList));
				basket.setCustomerTierDesc(rs.getString("CUSTOMER_TIER"));
				basket.setPublicHouseBaksetInd(rs.getString("PH_IND"));
				basket.setSortDesc(rs.getString("sort_desc"));
				basket.setMipBrand("MIP_BRAND");
				basket.setMipSimType("MIP_SIM_TYPE");
				return basket;
			}
		};

		try {
			logger.debug("getCallListBasketList @ HandsetModelDTO: " + SQL);

			basketList = simpleJdbcTemplate.query(SQL, mapper,
			locale, appDate, appDate, appDate, appDate, brandId, modelId,
					colorId, rpType, basketType, channelId, callList, 
					mipBrand, mipBrand);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getCallListBasketList EmptyResultDataAccessException");
			basketList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getCallListBasketList():", e);
			throw new DAOException(e.getMessage(), e);
		}

		return basketList;

	}

	// sql ,ok.
	public List<ModelDTO> getCallListModelList(String locale, String channelId,
			String jobList, String basketType, String rpType, String appDate)
			throws DAOException {
		List<ModelDTO> modelList = new ArrayList<ModelDTO>();
		if ("NONE".equals(rpType)) {
			rpType = null;
		}

		/*String SQL = "select distinct A.PARM_TYPE_VAL brand_name,\n"
				+ "                A.PARM_TYPE_ID  brand_id,\n"
				+ "                B.PARM_TYPE_VAL model_name,\n"
				+ "                B.PARM_TYPE_ID  model_id\n"
				+ "  from W_BASKET_PARM A, W_BASKET_PARM B\n"
				+ " where A.PARM_TYPE = 'BRAND'\n"
				+ "   and B.PARM_TYPE = 'MODEL'\n"
				+ "   and A.BASKET_ID = B.BASKET_ID\n"
				+ "   and A.BASKET_ID in\n"
				+ "       (select distinct BASKET_ID\n"
				+ "          from W_BASKET_PARM\n"
				+ "         where PARM_TYPE = 'RP_TYPE'\n"
				+ "           and PARM_TYPE_ID =  nvl(?, PARM_TYPE_ID) --&RP_TYPE\n"
				+ "           and BASKET_ID in\n"
				+ "               (select distinct BASKET_ID\n"
				+ "                  from W_BASKET_PARM\n"
				+ "                 where PARM_TYPE = 'BASKET_TYPE'\n"
				+ "                   and PARM_TYPE_ID = ? --&BASKET_TYPE\n"
				+ "                   and BASKET_ID in\n"
				+ "                       (select distinct CBA.BASKET_ID\n"
				+ "                          from BOMWEB_JOBLIST_BASKET_ASSGN JBA,\n"
				+ "                               W_CHANNEL_BASKET_ASSGN      CBA\n"
				+ "                         where JBA.JOB_LIST = ? --'IBSP02'--'&job_list'\n"
				+ "                           and TO_DATE(?, 'DD/MM/YYYY') between --&date\n"
				+ "                               JBA.START_DATE and NVL(JBA.END_DATE, sysdate)\n"
				+ "                           and JBA.BASKET_ID = CBA.BASKET_ID\n"
				+ "                           and CBA.CHANNEL_ID = ?) -- Channel ID\n"
				+ "                ))";*/
		
		String SQL=

			"select distinct A.PARM_TYPE_VAL BRAND_NAME,\n" +
			"                A.PARM_TYPE_ID  BRAND_ID,\n" + 
			"                B.PARM_TYPE_VAL MODEL_NAME,\n" + 
			"                B.PARM_TYPE_ID  MODEL_ID\n" + 
			"  from W_BASKET_PARM A, W_BASKET_PARM B\n" + 
			" where A.PARM_TYPE = 'BRAND'\n" + 
			"   and B.PARM_TYPE = 'MODEL'\n" + 
			"   and A.BASKET_ID = B.BASKET_ID\n" + 
			"   and A.BASKET_ID in\n" + 
			"       ( --basket filter base\n" + 
			"        select BHHMV.BASKET_ID\n" + 
			"          from W_BASKET_HS_HSRB_MV         BHHMV,\n" + 
			"                W_BASKET_ATTRIBUTE_MV       BAMV,\n" + 
			"                BOMWEB_JOBLIST_BASKET_ASSGN JBA\n" + 
			"         where BHHMV.BASKET_ID = BAMV.BASKET_ID\n" + 
			"           and BHHMV.BASKET_ID = JBA.BASKET_ID\n" + 
			"           and TO_DATE(?, 'DD/MM/YYYY') between BHHMV.HS_EFF_START_DATE and\n" + 
			"               NVL(BHHMV.HS_EFF_END_DATE, sysdate)\n" + 
			"           and TO_DATE(?, 'DD/MM/YYYY') between BHHMV.HS_PRICE_EFF_START_DATE and\n" + 
			"               NVL(BHHMV.HS_PRICE_EFF_END_DATE, sysdate)\n" + 
			"           and TO_DATE(?, 'DD/MM/YYYY') between BHHMV.HSRB_EFF_START_DATE and\n" + 
			"               NVL(BHHMV.HSRB_EFF_END_DATE, sysdate)\n" + 
			"           and TO_DATE(?, 'DD/MM/YYYY') between JBA.START_DATE and\n" + 
			"               NVL(JBA.END_DATE, sysdate)\n" + 
			"           and BAMV.RP_TYPE_ID = NVL(?, BAMV.RP_TYPE_ID) --RP_TYPE_ID--\n" + 
			"           and BAMV.BASKET_TYPE_ID = ? --BASKET_TYPE_ID--4--\n" +
			"   		and NVL(BAMV.NATURE, 'ACQ') = 'ACQ' " +
			"           and BHHMV.CHANNEL_ID = ? --CHANNEL_ID--2--\n" + 
			"           and JBA.JOB_LIST = ? --JOB_LIST--\n" + 
			"        )";




		try {
			logger.debug("getModelList @ HandsetModelDTO: " + SQL);
			logger.info(modelList.size());
			modelList = simpleJdbcTemplate.query(SQL,
					new ParameterizedRowMapper<ModelDTO>() {
						public ModelDTO mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							ModelDTO model = new ModelDTO();
							model.setModelId(rs.getString("model_id"));
							model.setModelName(rs.getString("model_name"));
							model.setBrandId(rs.getString("brand_id"));
							return model;
						}
					}, //RP_TYPE_ID, BASKET_TYPE_ID, CHANNEL_ID,JOB_LIST
					appDate,appDate,appDate,appDate,rpType,basketType,channelId,jobList
					//rpType, basketType, jobList, appDate, channelId
					);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			modelList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getModelList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return modelList;

	}

	public List<ModelDTO> getCallListHandSetDisplayList(String locale,
			String brandId, String modelId, String channelId,
			String customerTier, String basketType, String rpType,
			String callList, String appDate) throws DAOException {
		List<ModelDTO> modelList = new ArrayList<ModelDTO>();
		if ("NONE".equals(rpType)) {
			rpType = null;
		}
		String SQL = "SELECT brand_t.brand_id,\n"
				+ "       brand_t.brand_name,\n"
				+ "       model_t.model_id,\n"
				+ "       model_t.model_name,\n"
				+ "       model_html.image_path,\n"
				+ "       model_html.html,\n"
				+ "       model_t.locale\n"
				+ "  FROM (SELECT ID model_id, description model_name, locale\n"
				+ "          FROM w_display_lkup\n"
				+ "         WHERE TYPE = 'MODEL') model_t,\n"
				+ "       (SELECT ID brand_id, description brand_name, locale\n"
				+ "          FROM w_display_lkup\n"
				+ "         WHERE TYPE = 'BRAND') brand_t,\n"
				+ "       (SELECT DISTINCT idh.model_id, idh.brand_id\n"
				+ "          FROM w_basket_item_assgn    bia,\n"
				+ "               w_item_dtl_hs          idh,\n"
				+ "               w_channel_basket_assgn cba,\n"
				+ "               w_basket               b\n"
				+ "         WHERE bia.item_id = idh.ID\n"
				+ "           and cba.basket_id = bia.basket_id\n"
				+ "           and b.id = cba.basket_id\n"
				+ "           AND cba.channel_id = ?\n"
				+
				// "           AND cba.customer_tier = ?\n" +
				"           and b.type = ?) MAP,\n"
				+ "       (SELECT image_path, html, brand_id, model_id, locale, display_seq\n"
				+ "          FROM w_hs_display_lkup\n"
				+ "         WHERE display_type = 'DETAIL') model_html\n"
				+ " WHERE model_t.model_id = MAP.model_id\n"
				+ "   AND brand_t.brand_id = MAP.brand_id\n"
				+ "   AND model_html.model_id = MAP.model_id\n"
				+ "   AND model_html.brand_id = MAP.brand_id\n"
				+ "   AND model_t.locale = model_html.locale\n"
				+ "   AND model_t.locale = brand_t.locale\n"
				+ "   AND model_t.locale = ?\n"
				+ "   AND MAP.brand_id = NVL(?, MAP.brand_id)\n"
				+ "   AND MAP.model_id in ("
				+ "select distinct  B.PARM_TYPE_ID  model_id\n"
				+ "  from W_BASKET_PARM A, W_BASKET_PARM B\n"
				+ " where A.PARM_TYPE = 'BRAND'\n"
				+ "   and B.PARM_TYPE = 'MODEL'\n"
				+ "   and A.BASKET_ID = B.BASKET_ID and B.PARM_TYPE_ID =nvl(?, B.PARM_TYPE_ID)\n"
				+

				"   and A.BASKET_ID in\n"
				+ "       (select distinct BASKET_ID\n"
				+ "          from W_BASKET_PARM\n"
				+ "         where PARM_TYPE = 'RP_TYPE'\n"
				+ "           and PARM_TYPE_ID =  nvl(?, PARM_TYPE_ID) --&RP_TYPE\n"
				+ "           and BASKET_ID in\n"
				+ "               (select distinct BASKET_ID\n"
				+ "                  from W_BASKET_PARM\n"
				+ "                 where PARM_TYPE = 'BASKET_TYPE'\n"
				+ "                   and PARM_TYPE_ID = ? --&BASKET_TYPE\n"
				+ "                   and BASKET_ID in\n"
				+ "                       (select distinct CBA.BASKET_ID\n"
				+ "                          from BOMWEB_JOBLIST_BASKET_ASSGN JBA,\n"
				+ "                               W_CHANNEL_BASKET_ASSGN      CBA\n"
				+ "                         where JBA.JOB_LIST = ? --'IBSP02'--'&job_list'\n"
				+ "                           and TO_DATE(?, 'DD/MM/YYYY') between --&date\n"
				+ "                               JBA.START_DATE and NVL(JBA.END_DATE, sysdate)\n"
				+ "                           and JBA.BASKET_ID = CBA.BASKET_ID\n"
				+ "                           and CBA.CHANNEL_ID = ?) -- Channel ID\n"
				+ "                ))" + ")  order by MODEL_HTML.DISPLAY_SEQ";

		// System.out.println("locale2:"+locale);

		ParameterizedRowMapper<ModelDTO> mapper = new ParameterizedRowMapper<ModelDTO>() {
			// notice the return type with respect to Java 5 covariant return
			// types
			public ModelDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ModelDTO model = new ModelDTO();
				model.setModelId(rs.getString("model_id"));
				model.setModelName(rs.getString("model_name"));
				model.setModelImagePath(rs.getString("image_path"));
				model.setModelHtml(rs.getString("html"));
				model.setBrandId(rs.getString("brand_id"));
				return model;
			}
		};

		try {
			logger.debug("getHandSetDisplayList @ HandsetModelDTO: " + SQL);
			logger.info(modelList.size());
			modelList = simpleJdbcTemplate.query(SQL, mapper, channelId,
					basketType, locale, brandId, modelId, rpType, basketType,
					callList, appDate, channelId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			modelList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getHandSetDisplayList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return modelList;

	}

	public String checkQuota(String baksetId,
			List<CustomerInformationQuotaDTO> existQuotaDtoList) {
		StringBuffer SQLwhere = new StringBuffer();
		if (existQuotaDtoList == null || existQuotaDtoList.size() <= 0) {
			return "Y";
		} else {
			if (existQuotaDtoList != null && !existQuotaDtoList.isEmpty()) {
				SQLwhere.append("and BP.PARM_TYPE_VAL in (");

				for (int i = 0; i < existQuotaDtoList.size(); i++) {

					if (existQuotaDtoList.size() == 1
							|| existQuotaDtoList.size() == i + 1) {
						SQLwhere.append("'"
								+ existQuotaDtoList.get(i).getQuotaId() + "'");
					} else {
						SQLwhere.append("'"
								+ existQuotaDtoList.get(i).getQuotaId() + "',");
					}
				}

				SQLwhere.append(")");
			}

		}
		String countNum;
		String SQL = "select count(BP.BASKET_ID) COUNT_NUM\n"
				+ "  from W_BASKET_PARM BP\n"
				+ " where BP.PARM_TYPE = 'QUOTA'\n"
				+ "   and BP.BASKET_ID = ?\n" + SQLwhere.toString();

		try {
			countNum = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class, baksetId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			return "E";
		} catch (Exception e) {
			logger.error("Exception caught in checkQuota()", e);
			return "E2";

		}
		logger.info("checkQuota2 countNum: " + countNum);

		int i = Integer.parseInt(countNum);
		if (i > 0) {
			return "N"; // over
		} else {
			return "Y"; // below
		}

	}

	//sql ok. 20120118
	public List<BasketDTO> getCallListSimOnlyBasketList(String locale,
			String ratePlanType, String basketType, String callList,
			String channelId, String appDate,final List<CustomerInformationQuotaDTO> existQuotaDtoList,
			String mipBrand, String mipSimType) throws DAOException {
		List<BasketDTO> basketList = new ArrayList<BasketDTO>();

		/*String SQL = "select BIA.BASKET_ID,\n"
				+ "       CBA.PRIORITY,\n"
				+ "       NVL(BDL.HTML, IDL.HTML) HTML,\n"
				+ "       CBA.DISPLAY_TAB, DECODE(BAMV.OFFER_TYPE_ID, '1', 'Y', 'N') PH_IND, BAMV.OFFER_TYPE, bamv.customer_tier\n"
				+ "  from W_CHANNEL_BASKET_ASSGN CBA,\n"
				+ "       W_BASKET               B,\n"
				+ "       W_BASKET_ITEM_ASSGN    BIA,\n"
				+ "       W_ITEM                 I,\n"
				+ "       W_ITEM_DTL_RP_RB_VAS   IDRV,\n"
				+ "       W_ITEM_DISPLAY_LKUP    IDL,\n"
				+ "       W_BASKET_DISPLAY_LKUP  BDL, w_basket_attribute_mv bamv\n"
				+ " where B.ID = BIA.BASKET_ID and cba.basket_id=bamv.basket_id\n"
				+ "   and B.ID = CBA.BASKET_ID\n"
				+ "   and B.ID = BDL.BASKET_ID(+)\n"
				+ "   and NVL(bamv.NATURE, 'ACQ') = 'ACQ' \n"
				+ "   and BDL.LOCALE(+) = ? --locale------\n"
				+ "   and BDL.DISPLAY_TYPE(+) = 'RP_PROMOT'\n"
				+ "   and BIA.ITEM_ID = I.ID\n"
				+ "   and BIA.ITEM_ID = IDL.ITEM_ID\n"
				+ "   and BIA.ITEM_ID = IDRV.ID\n"
				+ "   and IDL.LOCALE = ? --locale------\n"
				+ "   and IDL.DISPLAY_TYPE = 'RP_PROMOT'\n"
				+ "   and I.TYPE = 'RP'\n"
				+ "   and IDRV.RP_TYPE = ? --ratePlanType----------\n"
				+ "   and B.TYPE = ? --basketType----------\n"
				+ "   and CBA.CHANNEL_ID = ? --CHANNEL_ID------\n"
				+ "   and CBA.BASKET_ID in\n"
				+ "       (select distinct CBA.BASKET_ID\n"
				+ "          from BOMWEB_JOBLIST_BASKET_ASSGN JBA\n"
				+ "         where JBA.JOB_LIST = ? --JOB_LIST------\n"
				+ "           and TO_DATE(?, 'DD/MM/YYYY') between JBA.START_DATE and --app_date------\n"
				+ "               NVL(JBA.END_DATE, sysdate))\n"
				+ " order by CBA.DISPLAY_TAB, CBA.PRIORITY desc";*/
		
		String SQL =
			"select BIA.BASKET_ID,\n" +
			"       CBA.PRIORITY,\n" + 
			"       NVL(BDL.HTML, IDL.HTML) HTML,\n" + 
			"       CBA.DISPLAY_TAB,\n" + 
			"       DECODE(BAMV.OFFER_TYPE_ID, '1', 'Y', 'N') PH_IND,\n" + 
			"       BAMV.OFFER_TYPE,\n" + 
			"       BAMV.CUSTOMER_TIER || decode(BAMV.OFFER_TYPE, null, '',' / ' || BAMV.OFFER_TYPE) || decode(BAMV.dummy_ind, null, '',' / ' || 'DUMMY') sort_desc,\n" + 
			"       BAMV.CUSTOMER_TIER || decode(BAMV.OFFER_TYPE, null, '','/' || BAMV.OFFER_TYPE) CUSTOMER_TIER\n" + 
			"       NVL(BAMV.MIP_BRAND, '1') MIP_BRAND,\n" +
			"       NVL(BAMV.MIP_SIM_TYPE, 'H') MIP_SIM_TYPE,\n" +
			"  from W_CHANNEL_BASKET_ASSGN      CBA,\n" + 
			"       W_BASKET_ATTRIBUTE_MV       BAMV,\n" + 
			"       W_BASKET_ITEM_ASSGN         BIA,\n" + 
			"       W_ITEM                      I,\n" + 
			"       W_ITEM_DISPLAY_LKUP         IDL,\n" + 
			"       W_BASKET_DISPLAY_LKUP       BDL,\n" + 
			"       BOMWEB_JOBLIST_BASKET_ASSGN JBA\n" + 
			" where CBA.BASKET_ID = BIA.BASKET_ID\n" + 
			"   and CBA.BASKET_ID = BAMV.BASKET_ID\n" + 
			"   and BIA.ITEM_ID = I.ID\n" + 
			"   and I.TYPE = 'RP'\n" + 
			"   and I.ID = IDL.ITEM_ID\n" + 
			"   and IDL.LOCALE = ? --locale--\n" + 
			"   and IDL.DISPLAY_TYPE = 'RP_PROMOT'\n" + 
			"   and CBA.BASKET_ID = BDL.BASKET_ID(+)\n" + 
			"   and BDL.LOCALE(+) = ? --locale--\n" + 
			"   and BDL.DISPLAY_TYPE(+) = 'RP_PROMOT'\n" + 
			"   and BAMV.RP_TYPE_ID = ? --RP_TYPE_ID--\n" + 
			"   and BAMV.BASKET_TYPE_ID = ? --BASKET_TYPE_ID--\n" +
			"   and NVL(BAMV.NATURE, 'ACQ') = 'ACQ'\n" +
			"   and CBA.CHANNEL_ID = ? --CBA.CHANNEL_ID--\n" + 
			"   and TO_DATE(?, 'DD/MM/YYYY') between CBA.EFF_START_DATE and --app_date--\n" + 
			"       NVL(CBA.EFF_END_DATE, sysdate)\n" + 
			"   and CBA.BASKET_ID = JBA.BASKET_ID\n" + 
			"   and JBA.JOB_LIST = ? --JOB_LIST--\n" + 
			"   and TO_DATE(?, 'DD/MM/YYYY') between JBA.START_DATE and --app_date--\n" + 
			"       NVL(JBA.END_DATE, sysdate)\n" + 
			"   and nvl(decode (BAMV.MIP_BRAND, '9', ?, BAMV.MIP_BRAND ), '1') = ? \n" +
			" order by CBA.DISPLAY_TAB, CBA.PRIORITY desc";


		ParameterizedRowMapper<BasketDTO> mapper = new ParameterizedRowMapper<BasketDTO>() {
			public BasketDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BasketDTO basket = new BasketDTO();
				basket.setBasketId(rs.getString("basket_id"));
				basket.setBasketHtml(rs.getString("HTML"));
				basket.setDisplayTab(rs.getString("display_tab"));
				basket.setPublicHouseBaksetInd(rs.getString("PH_IND"));
				basket.setBelowQuotaInd(checkQuota(rs.getString("basket_id"),
						existQuotaDtoList));
			
				basket.setCustomerTierDesc(rs.getString("customer_tier"));
				basket.setSortDesc(rs.getString("sort_desc"));
				basket.setMipBrand(rs.getString("MIP_BRAND"));
				basket.setMipSimType(rs.getString("MIP_SIM_TYPE"));
				return basket;
			}
		};

		try {
			logger.debug("getCallListSimOnlyBasketList @ HandsetModelDAO: "	+ SQL);
			basketList = simpleJdbcTemplate.query(SQL, mapper, /*locale, locale,
					ratePlanType, basketType, channelId, callList, appDate*/
					 locale, locale,
						ratePlanType, basketType, channelId, appDate, callList, appDate,
						mipBrand, mipBrand
					);

		} catch (EmptyResultDataAccessException e2) {
			logger.info("EmptyResultDataAccessException getCallListSimOnlyBasketList");
			basketList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getCallListSimOnlyBasketList():",	e);
			throw new DAOException(e.getMessage(), e);
		}
		return basketList;

	}
	
	
	public ModelDTO getMobileDetail(String locale, String basketId
			) throws DAOException {
		List<ModelDTO> modelList = new ArrayList<ModelDTO>();

		

	/*	String SQLOLD = "SELECT model_t.model_id, model_t.model_name, model_t.locale,\n"
				+ "       lkup.model_image_path, lkup.model_html\n"
				+ "  FROM (SELECT ID model_id, description model_name, locale\n"
				+ "          FROM w_display_lkup\n"
				+ "         WHERE TYPE = 'MODEL'\n"
				+ "         ) model_t,\n"
				+ "       (SELECT image_path model_image_path, html model_html, locale, brand_id,\n"
				+ "               model_id\n"
				+ "          FROM w_hs_display_lkup\n"
				+ "         WHERE display_type = 'DETAIL') lkup\n"
				+ " WHERE model_t.model_id = lkup.model_id\n"
				+ "   AND model_t.locale = lkup.locale\n"
				+ "   AND model_t.locale = ?\n"
				+ " --  AND lkup.brand_id = ?\n" 
				+ "   AND lkup.model_id = ?";*/
		
		
		String SQLModel=
			"select BAMV.BRAND_ID,\n" +
			"       BAMV.MODEL_ID,\n" + 
			"       BAMV.COLOR_ID,\n" + 
			"       (select BRAND_DL.DESCRIPTION\n" + 
			"          from W_DISPLAY_LKUP BRAND_DL\n" + 
			"         where BRAND_DL.ID = BAMV.BRAND_ID\n" + 
			"           and BRAND_DL.TYPE = 'BRAND'\n" + 
			"           and BRAND_DL.LOCALE = ?) BRAND_NAME, --LOCALE---\n" + 
			"       (select MODEL_DL.DESCRIPTION\n" + 
			"          from W_DISPLAY_LKUP MODEL_DL\n" + 
			"         where MODEL_DL.ID = BAMV.MODEL_ID\n" + 
			"           and MODEL_DL.TYPE = 'MODEL'\n" + 
			"           and MODEL_DL.LOCALE = ?) MODEL_NAME, --LOCALE---\n" + 
			"       (select COLOR_DL.DESCRIPTION\n" + 
			"          from W_DISPLAY_LKUP COLOR_DL\n" + 
			"         where COLOR_DL.ID = BAMV.COLOR_ID\n" + 
			"           and COLOR_DL.TYPE = 'COLOR'\n" + 
			"           and COLOR_DL.LOCALE = ?) COLOR_NAME, --LOCALE---\n" + 
			"       HDL.IMAGE_PATH MODEL_IMAGE_PATH,\n" + 
			"       HDL.HTML MODEL_HTML,\n" + 
			"       HDL.LOCALE\n" + 
			"  from W_BASKET_ATTRIBUTE_MV BAMV, W_HS_DISPLAY_LKUP HDL\n" + 
			" where BAMV.MODEL_ID = HDL.MODEL_ID\n" +
			"   AND NVL(BAMV.NATURE, 'ACQ') = 'ACQ'\n" + 
			"   and HDL.DISPLAY_TYPE = 'DETAIL'\n" + 
			"   and HDL.LOCALE = ? --LOCALE---\n" + 
			"   and BAMV.BASKET_ID = ? --BASKET_ID---";


		ParameterizedRowMapper<ModelDTO> mapper = new ParameterizedRowMapper<ModelDTO>() {
			// notice the return type with respect to Java 5 covariant return
			// types
			public ModelDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ModelDTO model = new ModelDTO();
				model.setModelId(rs.getString("model_id"));
				model.setModelName(rs.getString("model_name"));
				model.setBrandId(rs.getString("brand_id"));
				model.setModelImagePath(rs.getString("model_image_path"));
				model.setModelHtml(rs.getString("model_html"));
				List<ColorDTO> colorList = new ArrayList<ColorDTO>();
				ColorDTO colorDto = new ColorDTO();
				colorDto.setColorId(rs.getString("color_id"));
				colorDto.setColorName(rs.getString("color_name"));
				colorList.add(colorDto);
				model.setColorDto(colorList);

				return model;
			}
		};

		try {

			logger.debug("getMobileDetail @ HandsetModelDTO: " + SQLModel);
	
			modelList = simpleJdbcTemplate.query(SQLModel, mapper, locale,locale,locale,locale,
					basketId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			modelList = null;
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getColorList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (modelList.size()>0){
		return modelList.get(0);// only return the first one
		}else{
			
			return null;}

	}
	
	
	
	

}
