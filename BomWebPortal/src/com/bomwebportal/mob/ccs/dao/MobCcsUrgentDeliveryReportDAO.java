package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.UrgentDeliveryReportDTO;
public class MobCcsUrgentDeliveryReportDAO extends BaseDAO {
    protected final Log logger = LogFactory.getLog(getClass());
    
	
   /* private static final String getUrgentDeliveryReportDTOALLSQL = "SELECT sl.courier, " +
    		"  a.process_date, " +
    		"  a.order_id, " +
    		"  b.ocid, " +
    		"  a.msisdn, " +
    		"  a.cust_name, " +
    		"  a.staff_id, " +
    		"  a.delivery_type, " +
    		"  a.delivery_date, " +
    		"  a.delivery_time_slot, " +
    		"  a.payment_method, " +
    		"  a.payment_amt, " +
    		"  a.contact_name, " +
    		"  a.contact_num_1, " +
    		"  a.contact_num_2, " +
    		"  a.delivery_address, " +
    		"  a.batch_no, " +
    		"  d.code_desc item_type, " +
    		"  c.item_code, " +
    		"  c.item_desc, " +
    		"  c.item_serial, " +
    		"  c.doa_item_serial, " +
    		"  e.attb_value yahoo_coupon, " +
    		"  NVL( " +
    		"  (SELECT comp.attb_value " +
    		"  FROM bomweb_subscribed_item bsi " +
    		"  JOIN w_item_attb_assgn wiaa " +
    		"  ON bsi.id = wiaa.item_id " +
    		"  JOIN bomweb_code_lkup tng " +
    		"  ON tng.code_type = 'TNG_CARD' " +
    		"  AND wiaa.item_id = tng.code_id " +
    		"  JOIN bomweb_component comp " +
    		"  ON bsi.order_id  = comp.order_id " +
    		"  AND wiaa.attb_id = comp.attb_id " +
    		"  AND bsi.order_id = :orderId " +
    		"  ),'N') TNG_CARD , " +
    		"  (SELECT DECODE(COUNT(*),0,NULL,'Y') tng_sim " +
    		"  FROM bomweb_subscribed_item bsi " +
    		"  JOIN bomweb_code_lkup tng " +
    		"  ON tng.code_type = 'TNG_SIM' " +
    		"  AND bsi.id       = tng.code_id " +
    		"  AND bsi.order_id = :orderId " +
    		"  ) TNG_SIM " +
    		"FROM bomweb_ccsmob_order_delivery a, " +
    		"  bomweb_order b, " +
    		"  (SELECT DISTINCT code_id, " +
    		"    code_desc courier " +
    		"  FROM bomweb_code_lkup " +
    		"  WHERE code_type='STOCK_LOC' " +
    		"  ) sl, " +
    		"  bomweb_order_delivery_item c, " +
    		"  (SELECT code_id,code_desc FROM bomweb_code_lkup WHERE code_type='STOCK_TYPE' " +
    		"  ) d, " +
    		"  (SELECT bsi.order_id, " +
    		"    comp.attb_value " +
    		"  FROM bomweb_subscribed_item bsi " +
    		"  JOIN w_item_offer_product_assgn prod " +
    		"  ON bsi.id = prod.item_id " +
    		"  JOIN bomweb_code_lkup yahoo " +
    		"  ON yahoo.code_type  = 'YAHOO_COUPON_PROD' " +
    		"  AND prod.product_id = yahoo.code_id " +
    		"  JOIN w_item_attb_assgn attb " +
    		"  ON bsi.id = attb.item_id " +
    		"  JOIN bomweb_component comp " +
    		"  ON bsi.order_id  = comp.order_id " +
    		"  AND attb.attb_id = comp.attb_id " +
    		"  ) e " +
    		"WHERE a.process_date = TRUNC(:processingDate) " +
    		"AND a.order_id       = :orderId " +
    		"AND a.check_point    ='500' " +
    		"AND a.order_id       =b.order_id " +
    		"AND a.location       =sl.code_id " +
    		"AND c.process_date   = a.process_date " +
    		"AND c.order_id       = a.order_id " +
    		"AND c.batch_no       = a.batch_no " +
    		"AND c.item_type      = d.code_id " +
    		"AND a.order_id       = e.order_id(+) " +
    		"ORDER BY DECODE(d.code_desc,'SIM',1,'HANDSET',2,'TABLET',3,'ANS',4,'GIFT-PC',5,'GIFT_MISC',6) ASC ";
    */
    private static final String getUrgentDeliveryReportDTOALLSQL="SELECT sl.courier, " +
    		"  a.process_date, " +
    		"  a.order_id, " +
    		"  b.ocid, " +
    		"  a.msisdn, " +
    		"  a.cust_name, " +
    		"  a.staff_id, " +
    		"  a.delivery_type, " +
    		"  a.delivery_date, " +
    		"  a.delivery_time_slot, " +
    		"  a.payment_method, " +
    		"  a.payment_amt, " +
    		"  a.contact_name, " +
    		"  a.contact_num_1, " +
    		"  a.contact_num_2, " +
    		"  a.delivery_address, " +
    		"  a.batch_no, " +
    		"  c.item_type, " +
    		"  c.item_code, " +
    		"  c.item_desc, " +
    		"  c.item_serial, " +
    		"  c.doa_item_serial, " +
    		"  e.attb_value yahoo_coupon, " +
    		"  NVL( " +
    		"  (SELECT comp.attb_value " +
    		"  FROM bomweb_subscribed_item bsi " +
    		"  JOIN w_item_attb_assgn wiaa " +
    		"  ON bsi.id = wiaa.item_id " +
    		"  JOIN bomweb_code_lkup tng " +
    		"  ON tng.code_type = 'TNG_CARD' " +
    		"  AND wiaa.item_id = tng.code_id " +
    		"  JOIN bomweb_component comp " +
    		"  ON bsi.order_id  = comp.order_id " +
    		"  AND wiaa.attb_id = comp.attb_id " +
    		"  AND bsi.order_id = :orderId " +
    		"  ),'N') TNG_CARD , " +
    		"  (SELECT DECODE(COUNT(*),0,NULL,'Y') tng_sim " +
    		"  FROM bomweb_subscribed_item bsi " +
    		"  JOIN bomweb_code_lkup tng " +
    		"  ON tng.code_type = 'TNG_SIM' " +
    		"  AND bsi.id       = tng.code_id " +
    		"  AND bsi.order_id = :orderId " +
    		"  ) TNG_SIM " +
    		"FROM bomweb_ccsmob_order_delivery a, " +
    		"  bomweb_order b, " +
    		"  (SELECT DISTINCT code_id, " +
    		"    code_desc courier " +
    		"  FROM bomweb_code_lkup " +
    		"  WHERE code_type='STOCK_LOC' " +
    		"  ) sl, " +
    		"  (SELECT bodi.process_date, " +
    		"    bodi.order_id, " +
    		"    bodi.batch_no, " +
    		"    bodi.item_code, " +
    		"    bodi.item_desc, " +
    		"    bodi.item_serial, " +
    		"    bodi.doa_item_serial, " +
    		"    st.code_desc item_type " +
    		"  FROM bomweb_order_delivery_item bodi, " +
    		"    bomweb_code_lkup st " +
    		"  WHERE bodi.order_id= :orderId " +
    		"  AND st.code_type   ='STOCK_TYPE' " +
    		"  AND st.code_id     = bodi.item_type " +
    		"  ) c, " +
    		"  (SELECT bsi.order_id, " +
    		"    comp.attb_value " +
    		"  FROM bomweb_subscribed_item bsi " +
    		"  JOIN w_item_offer_product_assgn prod " +
    		"  ON bsi.id = prod.item_id " +
    		"  JOIN bomweb_code_lkup yahoo " +
    		"  ON yahoo.code_type  = 'YAHOO_COUPON_PROD' " +
    		"  AND prod.product_id = yahoo.code_id " +
    		"  JOIN w_item_attb_assgn attb " +
    		"  ON bsi.id = attb.item_id " +
    		"  JOIN bomweb_component comp " +
    		"  ON bsi.order_id  = comp.order_id " +
    		"  AND attb.attb_id = comp.attb_id " +
    		"  ) e " +
    		"WHERE a.process_date = TRUNC(:processingDate) " +
    		"AND a.order_id       = :orderId " +
    		"AND a.check_point    ='500' " +
    		"AND a.order_id       =b.order_id " +
    		"AND a.location       =sl.code_id " +
    		"AND a.process_date   = c.process_date (+) " +
    		"AND a.order_id       = c.order_id (+) " +
    		"AND a.batch_no       = c.batch_no (+) " +
    		"AND a.order_id       = e.order_id(+) " +
    		"ORDER BY DECODE(c.item_type,'SIM',1,'HANDSET',2,'TABLET',3,'ANS',4,'GIFT-PC',5,'GIFT_MISC',6) ASC ";

	public List<UrgentDeliveryReportDTO> getUrgentDeliveryReportDTOALL(String orderId, Date processingDate) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("getUrgentDeliveryReportDTOALL() is called");
		}
		List<UrgentDeliveryReportDTO> itemList = null;
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("processingDate", processingDate);
			if (logger.isInfoEnabled()) {
				logger.info("getUrgentDeliveryReportDTOALL() @ UrgentDeliveryReportDTO: " + getUrgentDeliveryReportDTOALLSQL);
			}
			itemList = this.simpleJdbcTemplate.query(getUrgentDeliveryReportDTOALLSQL, this.getRowMapper(), params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getUrgentDeliveryReportDTOALL()");
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getUrgentDeliveryReportDTOALL1+2():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
		
	
	
	public ParameterizedRowMapper<UrgentDeliveryReportDTO> getRowMapper() {
		return new ParameterizedRowMapper<UrgentDeliveryReportDTO>() {
			public UrgentDeliveryReportDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				UrgentDeliveryReportDTO dto = new UrgentDeliveryReportDTO();
				dto.setOrderId(rs.getString("order_id"));
				dto.setOcid(rs.getString("ocid"));
				dto.setCourier(rs.getString("courier"));
				dto.setProcessDate(rs.getDate("process_date"));
				dto.setMsisdn(rs.getString("msisdn"));
				dto.setCustName(rs.getString("cust_name"));
				dto.setStaffId(rs.getString("staff_id"));
				dto.setDeliveryType(rs.getString("delivery_type"));
				dto.setDeliveryDate(rs.getDate("delivery_date"));
				dto.setDeliveryTimeSlot(rs.getString("delivery_time_slot"));
				dto.setPaymentMethod(rs.getString("payment_method"));
				dto.setPaymentAmt(rs.getBigDecimal("payment_amt"));
				dto.setContactName(rs.getString("contact_name"));
				dto.setContactNum1(rs.getString("contact_num_1"));
				dto.setContactNum2(rs.getString("contact_num_2"));
				dto.setDeliveryAddress(rs.getString("delivery_address"));
				dto.setBatchNo(rs.getInt("batch_no"));
				dto.setItemType(rs.getString("item_type"));
				dto.setItemCode(rs.getString("item_code"));
				dto.setItemDesc(rs.getString("item_desc"));
				dto.setItemSerial(rs.getString("item_serial"));
				dto.setDoaitemSerial(rs.getString("doa_item_serial"));
				dto.setYahooCoupon(rs.getString("yahoo_coupon"));
				dto.setTngSim(rs.getString("TNG_SIM"));
				dto.setTngCard(rs.getString("TNG_CARD"));
				
				
				return dto;
			}
		};
	}
	
	

}
