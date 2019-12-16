package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.ShopDTO;
import com.bomwebportal.dto.report.MobPreActReqDTO;
import com.bomwebportal.exception.DAOException;

public class MobPreActReqDAO extends BaseDAO {
	private static String getProcessActivationLetterDetailSQL = 
			"SELECT \n"
			+ "  msisdn \n"
			+ ", activation_code \n"
			+ ", cust_name \n"
			+ ", addrln1 \n"
			+ ", addrln2 \n"
			+ ", addrln3 \n"
			+ ", addrln4 \n"
			+ ", addrln5 \n"
			+ ", template_type \n"
			+ ", agreement_num \n"
			+ ", srd_date \n"
			+ ", sms_lang \n"
			+ ", brand \n"
			+ ", shop_cd \n"
			+ ", req_file_name \n"
		    + "FROM w_pre_act_req_work \n"
		    + "WHERE msisdn = ? ";

    public MobPreActReqDTO getProcessActivationLetterDetail(String msisdn)
	    throws DAOException {
	logger.info("getProcessActivationLetterDetail @ MobPreActReqDAO is called");
	List<MobPreActReqDTO> result = new ArrayList<MobPreActReqDTO>();

	ParameterizedRowMapper<MobPreActReqDTO> mapper = new ParameterizedRowMapper<MobPreActReqDTO>() {
	    public MobPreActReqDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	MobPreActReqDTO tempDto = new MobPreActReqDTO();
	    	tempDto.setMsisdn(rs.getString("msisdn"));
	    	tempDto.setActivationCode(rs.getString("activation_code"));
	    	tempDto.setCustName(rs.getString("cust_name"));
	    	tempDto.setAddrLn1(rs.getString("addrln1"));
	    	tempDto.setAddrLn2(rs.getString("addrln2"));
	    	tempDto.setAddrLn3(rs.getString("addrln3"));
	    	tempDto.setAddrLn4(rs.getString("addrln4"));
	    	tempDto.setAddrLn5(rs.getString("addrln5"));
	    	tempDto.setTemplateType(rs.getString("template_type"));
	    	tempDto.setAgreementNum(rs.getString("agreement_num"));
	    	tempDto.setSrdDate(rs.getString("srd_date"));
	    	tempDto.setSmsLang(rs.getString("sms_lang"));
	    	tempDto.setBrand(rs.getString("brand"));
	    	tempDto.setShopCd(rs.getString("shop_cd"));
	    	tempDto.setReqFilename(rs.getString("req_file_name"));
	    	return tempDto;
	    }
	};

	try {
	    logger.info("getProcessActivationLetterDetail() @ MobPreActReqDAO: "
		    + getProcessActivationLetterDetailSQL);

	    result = simpleJdbcTemplate.query(getProcessActivationLetterDetailSQL, mapper, msisdn);
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getProcessActivationLetterDetail()");

	    result = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getProcessActivationLetterDetail():", e);

	    throw new DAOException(e.getMessage(), e);
	}
		return (result == null || result.isEmpty()) ? null : result.get(0);
    }
    
    private static String getShopDetailSQL = 
    		"select \n" +
			"     S.SHOP_CD \n" +
			"    ,S.EMAIL_ADDR \n" +
			"    ,S.CONTACT_PHONE \n" +
			"    ,S.BRAND \n" +
			"    ,S.HOTLINE \n" +
			"    ,SI.ENG_DESC \n" +
			"    ,SI.CHI_DESC \n" +
			"    ,SI.ENG_ADDR_1 \n" +
			"    ,SI.ENG_ADDR_2 \n" +
			"    ,SI.ENG_ADDR_3 \n" +
			"    ,SI.ENG_ADDR_4 \n" +
			"    ,SI.CHI_ADDR_1 \n" +
			"    ,SI.CHI_ADDR_2 \n" +
			"    ,SI.CHI_ADDR_3 \n" +
			"    ,SI.CHI_ADDR_4 \n" +
			"    ,SI.CREATE_BY \n" +
			"    ,SI.CREATE_DATE \n" +
			"    ,SI.LAST_UPD_BY \n" +
			"    ,SI.LAST_UPD_DATE \n" +
			"from BOMWEB_SHOP S \n" +
			"left join BOMWEB_SHOP_INFO SI ON S.SHOP_CD = SI.SHOP_CD \n" +
			"where S.SHOP_CD = ? \n";

    public ShopDTO getShopDetail(String shopCd)
	    throws DAOException {
	logger.info("getShopDetail @ MobPreActReqDAO is called");
	List<ShopDTO> result = new ArrayList<ShopDTO>();

	ParameterizedRowMapper<ShopDTO> mapper = new ParameterizedRowMapper<ShopDTO>() {
	    public ShopDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	ShopDTO dto = new ShopDTO();
	    	dto.setShopCd(rs.getString("SHOP_CD"));
	    	dto.setEmailAddr(rs.getString("EMAIL_ADDR"));
	    	dto.setContactPhone(rs.getString("CONTACT_PHONE"));
	    	dto.setBrand(rs.getString("BRAND"));
	    	dto.setHotline(rs.getString("HOTLINE"));
	    	dto.setEngDesc(rs.getString("ENG_DESC"));
	    	dto.setEngAddr1(rs.getString("ENG_ADDR_1"));
	    	dto.setEngAddr2(rs.getString("ENG_ADDR_2"));
	    	dto.setEngAddr3(rs.getString("ENG_ADDR_3"));
	    	dto.setEngAddr4(rs.getString("ENG_ADDR_4"));
	    	dto.setChiDesc(rs.getString("CHI_DESC"));
	    	dto.setChiAddr1(rs.getString("CHI_ADDR_1"));
	    	dto.setChiAddr2(rs.getString("CHI_ADDR_2"));
	    	dto.setChiAddr3(rs.getString("CHI_ADDR_3"));
	    	dto.setChiAddr4(rs.getString("CHI_ADDR_4"));
	    	dto.setCreateBy(rs.getString("CREATE_BY"));
	    	dto.setCreateDate(rs.getDate("CREATE_DATE"));
	    	dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
	    	dto.setLastUpdDate(rs.getDate("LAST_UPD_DATE"));
	    	return dto;
	    }
	};

	try {
	    logger.info("getShopDetail() @ MobPreActReqDAO: " + getShopDetailSQL);

	    result = simpleJdbcTemplate.query(getShopDetailSQL, mapper, shopCd);
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getShopDetail()");

	    result = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getShopDetail():", e);

	    throw new DAOException(e.getMessage(), e);
	}
		return (result == null || result.isEmpty()) ? null : result.get(0);
    }
}
