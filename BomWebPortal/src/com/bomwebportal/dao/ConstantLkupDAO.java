package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.AddressAreaDTO;
import com.bomwebportal.dto.AddressCategoryDTO;
import com.bomwebportal.dto.AddressDistrictDTO;
import com.bomwebportal.dto.AddressSectionDTO;
import com.bomwebportal.dto.BankBranchDTO;
import com.bomwebportal.dto.CreditCardTypeDTO;
import com.bomwebportal.dto.IssueBankDTO;
import com.bomwebportal.dto.ItemFuncAssgnMobDTO;
import com.bomwebportal.dto.MobBillMediaDTO;
import com.bomwebportal.dto.report.MobAppFormDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;

public class ConstantLkupDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());

	public List<IssueBankDTO> getIssueBankList() throws DAOException {
		List<IssueBankDTO> issueBankList = new ArrayList<IssueBankDTO>();

		String SQL = "SELECT BANK_CODE, BANK_NAME FROM W_ISSUEBANKLKUP ORDER BY BANK_NAME";

		ParameterizedRowMapper<IssueBankDTO> mapper = new ParameterizedRowMapper<IssueBankDTO>() {

			public IssueBankDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				IssueBankDTO issueBankDto = new IssueBankDTO();
				issueBankDto.setBankCd(rs.getString("BANK_CODE"));
				issueBankDto.setBankName(rs.getString("BANK_NAME"));
				return issueBankDto;
			}
		};

		try {
			issueBankList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getIssueBankList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return issueBankList;
	}

	public List<Integer> getBillPeriod() throws DAOException {

		List<Integer> billPeriodList = new ArrayList<Integer>();

		String SQL = "SELECT bill_period from w_billperiod_lkup ORDER BY bill_period";

		ParameterizedRowMapper<Integer> mapper = new ParameterizedRowMapper<Integer>() {

			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt("bill_period");
			}
		};

		try {
			billPeriodList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getBillPeriod()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return billPeriodList;
	}
	
	public List<Integer> getBillPeriodByBrand(String brand) throws DAOException {

		List<Integer> billPeriodList = new ArrayList<Integer>();

		String SQL = "select cl.code_id from bomweb_code_lkup cl where code_type = ? ";

		ParameterizedRowMapper<Integer> mapper = new ParameterizedRowMapper<Integer>() {

			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt("code_id");
			}
		};

		try {
			billPeriodList = simpleJdbcTemplate.query(SQL, mapper, brand);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getBillPeriodByBrand()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return billPeriodList;
	}

	public List<AddressAreaDTO> getAddressAreaList() throws DAOException {
		List<AddressAreaDTO> addressAreaList = new ArrayList<AddressAreaDTO>();

		String SQL = "SELECT CODE, DESCRIPTION FROM W_ADDRLKUP_AREA ORDER BY CODE";

		ParameterizedRowMapper<AddressAreaDTO> mapper = new ParameterizedRowMapper<AddressAreaDTO>() {

			public AddressAreaDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				AddressAreaDTO addressAreaDto = new AddressAreaDTO();
				addressAreaDto.setAreaCode(rs.getString("CODE"));
				addressAreaDto.setAreaDescription(rs.getString("DESCRIPTION"));
				return addressAreaDto;
			}
		};

		try {
			addressAreaList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getAddressAreaList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return addressAreaList;
	}

	public List<AddressCategoryDTO> getAddressCategoryList()
			throws DAOException {
		List<AddressCategoryDTO> addressCategoryList = new ArrayList<AddressCategoryDTO>();

		String SQL = "SELECT STCATGCD, STCATDSC FROM W_ADDRCATEGORY ORDER BY STCATGCD";

		ParameterizedRowMapper<AddressCategoryDTO> mapper = new ParameterizedRowMapper<AddressCategoryDTO>() {

			public AddressCategoryDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				AddressCategoryDTO addressCategoryDto = new AddressCategoryDTO();
				addressCategoryDto.setCategoryCode(rs.getString("STCATGCD"));
				addressCategoryDto.setCategoryDesc(rs.getString("STCATDSC"));
				return addressCategoryDto;
			}
		};

		try {
			addressCategoryList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getAddressCategoryList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return addressCategoryList;
	}

	public List<AddressDistrictDTO> getAddressDistrictList()
			throws DAOException {
		List<AddressDistrictDTO> addressDistrictList = new ArrayList<AddressDistrictDTO>();
		
		String SQL = "select ad.code,ad.areacd, ad.DISTDSC   from W_ADDRLKUP_DISTRICT ad order by ad.DISTDSC";

		ParameterizedRowMapper<AddressDistrictDTO> mapper = new ParameterizedRowMapper<AddressDistrictDTO>() {

			public AddressDistrictDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				AddressDistrictDTO AddressDistrictDTO = new AddressDistrictDTO();
				AddressDistrictDTO.setDistrictCode(rs.getString("CODE"));
				AddressDistrictDTO.setDistrictDescription(rs
						.getString("DISTDSC"));
				AddressDistrictDTO.setAreaCode(rs.getString("AREACD"));
				return AddressDistrictDTO;
			}
		};

		try {
			addressDistrictList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getAddressDistrictList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return addressDistrictList;
	}

	public List<AddressSectionDTO> getAddressSectionList() throws DAOException {
		List<AddressSectionDTO> addressSectionList = new ArrayList<AddressSectionDTO>();
        // edit sql 20110316, add where code !='ZZZZ'
		//SELECT CODE, decode(CODE,'ZZZZ', null ,SECT_DESC) SECT_DESC, DISTRNUM FROM W_ADDRLKUP_SECTION  ORDER BY CODE
		//String SQL = "SELECT CODE, SECT_DESC, DISTRNUM FROM W_ADDRLKUP_SECTION  ORDER BY CODE";
		String SQL ="SELECT CODE,  SECT_DESC, DISTRNUM FROM W_ADDRLKUP_SECTION  ORDER BY CODE";
		ParameterizedRowMapper<AddressSectionDTO> mapper = new ParameterizedRowMapper<AddressSectionDTO>() {

			public AddressSectionDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				AddressSectionDTO AddressSectionDTO = new AddressSectionDTO();
				AddressSectionDTO.setSectionCode(rs.getString("CODE"));
				AddressSectionDTO.setSectionDescription(rs.getString("SECT_DESC"));
				AddressSectionDTO.setDistrictCode(rs.getString("DISTRNUM"));
				return AddressSectionDTO;
			}
		};

		try {
			addressSectionList = simpleJdbcTemplate.query(SQL, mapper);
			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getAddressSectionList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return addressSectionList;
	}

	public List<BankBranchDTO> getBankBranchList() throws DAOException {
		List<BankBranchDTO> bankBranchList = new ArrayList<BankBranchDTO>();

		String SQL = "SELECT BANK_CODE, BRANCH_CODE, BRANCH_NAME FROM W_BANKBRANCHLKUP ORDER BY BANK_CODE";

		ParameterizedRowMapper<BankBranchDTO> mapper = new ParameterizedRowMapper<BankBranchDTO>() {

			public BankBranchDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BankBranchDTO BankBranchDTO = new BankBranchDTO();
				BankBranchDTO.setBankCode(rs.getString("BANK_CODE"));
				BankBranchDTO.setBranchCode(rs.getString("BRANCH_CODE"));
				BankBranchDTO.setBranchName(rs.getString("BRANCH_NAME"));
				return BankBranchDTO;
			}
		};

		try {
			bankBranchList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getBankBranchList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return bankBranchList;
	}

	public List<CreditCardTypeDTO> getCreditCardTypeList() throws DAOException {
		List<CreditCardTypeDTO> creditCardTypeList = new ArrayList<CreditCardTypeDTO>();

		String SQL = "SELECT BOM_GRP_ID, BOM_CODE, BOM_DESC FROM W_CREDITCARDTYPE ORDER BY BOM_CODE";

		ParameterizedRowMapper<CreditCardTypeDTO> mapper = new ParameterizedRowMapper<CreditCardTypeDTO>() {

			public CreditCardTypeDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				CreditCardTypeDTO CreditCardTypeDTO = new CreditCardTypeDTO();
				CreditCardTypeDTO.setBomGrpId(rs.getString("BOM_GRP_ID"));
				CreditCardTypeDTO.setBomCode(rs.getString("BOM_CODE"));
				CreditCardTypeDTO.setBomDesc(rs.getString("BOM_DESC"));
				return CreditCardTypeDTO;
			}
		};

		try {
			creditCardTypeList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getCreditCardTypeList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return creditCardTypeList;
	}
	
	public List<IssueBankDTO> getAutopayIssueBankList() throws DAOException {
		List<IssueBankDTO> issueBankList = new ArrayList<IssueBankDTO>();

		String SQL = "SELECT BANK_CODE, BANK_NAME  || '(' ||BANK_CODE ||')' BANK_NAME FROM W_AP_ISSUEBANKLKUP ORDER BY BANK_code";

		ParameterizedRowMapper<IssueBankDTO> mapper = new ParameterizedRowMapper<IssueBankDTO>() {

			public IssueBankDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				IssueBankDTO issueBankDto = new IssueBankDTO();
				issueBankDto.setBankCd(rs.getString("BANK_CODE"));
				issueBankDto.setBankName(rs.getString("BANK_NAME"));
				return issueBankDto;
			}
		};

		try {
			issueBankList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getAutopayIssueBankList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return issueBankList;
	}
	
	public List<BankBranchDTO> getAutopayBankBranchList() throws DAOException {
		List<BankBranchDTO> bankBranchList = new ArrayList<BankBranchDTO>();

		String SQL ="select ab.bank_code, ab.bank_name, abb.branch_code, abb.branch_name || '(' ||abb.branch_code ||')' branch_name\n" +
		"  from w_ap_issuebanklkup ab, w_ap_bankbranchlkup abb\n" + 
		" where ab.bank_code = abb.bank_code  \n" + 
		" order by ab.bank_code, abb.branch_code";

		ParameterizedRowMapper<BankBranchDTO> mapper = new ParameterizedRowMapper<BankBranchDTO>() {

			public BankBranchDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BankBranchDTO BankBranchDTO = new BankBranchDTO();
				BankBranchDTO.setBankCode(rs.getString("BANK_CODE"));
				BankBranchDTO.setBankCode(rs.getString("BANK_NAME"));
				BankBranchDTO.setBranchCode(rs.getString("BRANCH_CODE"));
				BankBranchDTO.setBranchName(rs.getString("BRANCH_NAME"));
				return BankBranchDTO;
			}
		};

		try {
			bankBranchList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getAutopayBankBranchList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return bankBranchList;
	}
	
	public List<Map<String, String>> getImsLookUpCode(String GrpId) throws DAOException{
		logger.debug("getImsLookUpCode:"+GrpId);
		String SQL = "	SELECT   grp_id, code, description	"+
		"	    FROM w_code_lkup	"+
		"	   WHERE grp_id = ?	"+
		"	ORDER BY code ";

		
		ParameterizedRowMapper<Map<String, String>> mapper = new ParameterizedRowMapper<Map<String, String>>() {

			public Map<String, String> mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				HashMap<String, String> map = new HashMap<String, String>();
				
				map.put("grp_id", rs.getString("grp_id"));
				map.put("code", rs.getString("code"));
				map.put("description", rs.getString("description"));
				
				return map;
			}
		};
		
		try {
//			logger.debug("getImsLookUpCode @ OrderDAO: " + SQL);
			List<Map<String, String>> map = simpleJdbcTemplate
					.query(SQL, mapper, GrpId);
			
			return map;
		
		}catch (EmptyResultDataAccessException erdae) {
			return null;
		}catch (Exception e) {
			logger.error("Exception caught in getImsLookUpCode()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
 	
	public List<Map<String, String>> getImsDisPlayLkup(String type, String locale) throws DAOException{
		logger.info("getImsLookUpCode");
		String SQL = "select type, id, description from w_display_lkup where type = ? and locale = ? order by id asc";

		
		ParameterizedRowMapper<Map<String, String>> mapper = new ParameterizedRowMapper<Map<String, String>>() {

			public Map<String, String> mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				HashMap<String, String> map = new HashMap<String, String>();
				
				map.put("type", rs.getString("type"));
				map.put("id", rs.getString("id"));
				map.put("description", rs.getString("description"));
				
				return map;
			}
		};
		
		try {
			logger.debug("getImsDisPlayLkup @ OrderDAO: " + SQL);
			List<Map<String, String>> map = simpleJdbcTemplate
					.query(SQL, mapper, type, locale);
			
			return map;
		
		}catch (EmptyResultDataAccessException erdae) {
			return null;
		}catch (Exception e) {
			logger.error("Exception caught in getImsDisPlayLkup()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public List<Map<String, String>> getImsDisplayLkupAllLang(String type) throws DAOException{
		logger.info("getImsDisplayLkupAllLang");
		String SQL = "select locale, type, id, description from w_display_lkup where type like ? order by type, id";

		
		ParameterizedRowMapper<Map<String, String>> mapper = new ParameterizedRowMapper<Map<String, String>>() {

			public Map<String, String> mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				HashMap<String, String> map = new HashMap<String, String>();
				
				map.put("locale", rs.getString("locale"));	
				map.put("type", rs.getString("type"));
				map.put("id", rs.getString("id"));
				map.put("description", rs.getString("description"));
				
				return map;
			}
		};
		
		try {
			logger.debug("getImsDisplayLkupAllLang @ OrderDAO: " + SQL);
			List<Map<String, String>> map = simpleJdbcTemplate
					.query(SQL, mapper, type);
			
			return map;
		
		}catch (EmptyResultDataAccessException erdae) {
			return null;
		}catch (Exception e) {
			logger.error("Exception caught in getImsDisPlayLkupAllLang()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public String getSendSMSorNot() throws DAOException {
		
		String SQL = "select DESCRIPTION  \n"
			+" from w_code_lkup \n"
			+" where grp_id = 'IMS_OS_PARM'" 
			+" and code = ?";
		String SmsOrNot;
		try {
//			logger.debug("getSendSMSorNot @ ConstantLkupDAO: \n" + SQL);
			SmsOrNot = simpleJdbcTemplate.queryForObject(SQL, String.class, "CONF_CC_SMS");;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			SmsOrNot = null;
			return SmsOrNot;
		} catch (Exception e) {
			logger.error("Exception caught in getSendSMSorNot()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return SmsOrNot;	
	}
	
	public String getSendNowRetSmsOrNot() throws DAOException {
		
		String SQL = "select DESCRIPTION  \n"
			+" from w_code_lkup \n"
			+" where grp_id = 'IMS_OS_PARM'" 
			+" and code = ?";
		String SmsOrNot;
		try {
//			logger.debug("getSendNowRetSmsOrNot @ ConstantLkupDAO: \n" + SQL);
			SmsOrNot = simpleJdbcTemplate.queryForObject(SQL, String.class, "CONF_NR_SMS");;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			SmsOrNot = null;
			return SmsOrNot;
		} catch (Exception e) {
			logger.error("Exception caught in getSendNowRetSmsOrNot()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return SmsOrNot;
	}
	
	public List<CodeLkupDTO> getAllCodeLkup() throws DAOException {
			String sql =	"SELECT \n" 
				  		+ 	"	CODE_TYPE \n" 
				  		+	"	,CODE_ID \n" 
				  		+	"	,CODE_DESC \n" 
				  		+	"	,CREATE_BY \n" 
				  		+	"	,CREATE_DATE \n" 
				  		+	"	,LAST_UPD_BY \n" 
				  		+	"	,LAST_UPD_DATE \n" 
				  		+	"FROM \n"
				  		+	"	BOMWEB_CODE_LKUP \n" 
				  		+	"WHERE \n"
				  		+	"	CODE_TYPE NOT IN \n" 
				  		+	"	(SELECT CLI.CODE_ID \n" 
				  		+	"	FROM BOMWEB_CODE_LKUP CLI \n"
				  		+	"	WHERE CLI.CODE_TYPE = 'BOMWEBPORTAL_EXCLUDE_LKUP_TYPE') \n"
				  		+	"ORDER BY \n" 
				  		+	"	CODE_TYPE \n"
				  		+	"	,CODE_ID \n";
		
		ParameterizedRowMapper<CodeLkupDTO> mapper = new ParameterizedRowMapper<CodeLkupDTO>() {
			public CodeLkupDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				CodeLkupDTO dto = new CodeLkupDTO();
				dto.setCodeType(rs.getString("CODE_TYPE"));
				dto.setCodeId(rs.getString("CODE_ID"));
				dto.setCodeDesc(rs.getString("CODE_DESC"));
				return dto;
			}
		};
		
		try {
			logger.debug("getAllCodeLkup() @ CodeLkupDTO:"+  sql);
			
			return simpleJdbcTemplate.query(sql, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getAllCodeLkup()");
		} catch (Exception e) {
			logger.info("Exception caught in getCodeLkupDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;// only return the first one
	}
	public List<MobBillMediaDTO> getBillMediaOption() throws DAOException {//paper bill Athena 20130925

		List<MobBillMediaDTO> billOptionList = new ArrayList<MobBillMediaDTO>();
		String SQL = "SELECT A.CODE_TYPE, " 
				+"  A.CODE_ID, " 
				+"  A.CODE_DESC, " 
				+"  DECODE(B.CODE_ID, NULL , 'N', 'Y') AUTHORIZE_IND, " 
				+"  (SELECT DL.DESCRIPTION " 
				+"  FROM W_DISPLAY_LKUP DL " 
				+"  WHERE DL.TYPE = 'BILL_MEDIA' " 
				+"  AND DL.LOCALE = 'en' " 
				+"  AND DL.SYS_DISPLAY = A.CODE_ID " 
				+"  ) ENG_DESC , " 
				+"  (SELECT DL.DESCRIPTION " 
				+"  FROM W_DISPLAY_LKUP DL " 
				+"  WHERE DL.TYPE  = 'BILL_MEDIA' " 
				+"  AND DL.LOCALE  = 'zh_HK' " 
				+"  AND DL.SYS_DISPLAY = A.CODE_ID " 
				+"  ) CHI_DESC " 
				+"FROM " 
				+"  (SELECT * FROM BOMWEB_CODE_LKUP CLM WHERE CLM.CODE_TYPE = 'BIL_MED_TYPE' " 
				+"  ) A , " 
				+"  (SELECT * " 
				+"  FROM BOMWEB_CODE_LKUP CLMA " 
				+"  WHERE CLMA.CODE_TYPE = 'BIL_MED_AUTHORIZE' " 
				+"  ) B " 
				+"WHERE A.CODE_ID = B.CODE_ID(+)";


		ParameterizedRowMapper<MobBillMediaDTO> mapper = new ParameterizedRowMapper<MobBillMediaDTO>() {//paper bill Athena 20130925

			public MobBillMediaDTO mapRow(ResultSet rs, int rowNum)//paper bill Athena 20130925
					throws SQLException {
				MobBillMediaDTO mobBillMediaDTO = new MobBillMediaDTO();
				mobBillMediaDTO.setCodeType(rs.getString("CODE_TYPE"));
				mobBillMediaDTO.setCodeId(rs.getString("CODE_ID"));
				mobBillMediaDTO.setCodeDesc(rs.getString("CODE_DESC"));
				mobBillMediaDTO.setAuthorizeInd(rs.getString("AUTHORIZE_IND"));
				mobBillMediaDTO.setEngDesc(rs.getString("ENG_DESC"));
				mobBillMediaDTO.setChiDesc(rs.getString("CHI_DESC"));
				return mobBillMediaDTO;
			}
		};

		try {
			billOptionList = simpleJdbcTemplate.query(SQL, mapper);		
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getBillMediaOption()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return billOptionList;
	}
	
	public List<MobAppFormDTO> getMobAppFormList(int channelId, String nature) throws DAOException {
		
		List<MobAppFormDTO> mobAppFormList = new ArrayList<MobAppFormDTO>();
		String sql = "select MA.FORM_ID, MA.FORM_DESC, MA.JASPER_TEMPLATE_NAME, MCA.CHANNEL_ID, MCA.MO_IND, MCA.SEQ_NO, MA.NATURE, " +
					 " NVL(MA.COMPANY_COPY_IND, 'N') COMPANY_COPY_IND, " +
					 " NVL(MA.COP_IND, 'N') COP_IND, " +
					 " NVL(MA.DEL_IND, 'N') DEL_IND, " +
					 " NVL(MA.DMS_IND, 'N') DMS_IND " + 
				     " from BOMWEB_MOB_APPFORM MA, BOMWEB_MOB_CHANNEL_APPFORM MCA " + 
				     " where MA.form_id = MCA.form_id and MCA.channel_id = ? " +
				     " and MA.nature = decode(MA.nature,'ALL','ALL',?)  "+
				     " order by MCA.SEQ_NO ";
		
		ParameterizedRowMapper<MobAppFormDTO> mapper = new ParameterizedRowMapper<MobAppFormDTO>() {
			public MobAppFormDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MobAppFormDTO mobAppForm = new MobAppFormDTO();
				mobAppForm.setAppFormId(rs.getString("FORM_ID"));
				mobAppForm.setAppFormDesc(rs.getString("FORM_DESC"));
				mobAppForm.setJasperTemplateName(rs.getString("JASPER_TEMPLATE_NAME"));
				mobAppForm.setChannelId(rs.getString("CHANNEL_ID"));
				mobAppForm.setNature(rs.getString("NATURE"));
				mobAppForm.setCompanyCopyInd(rs.getString("COMPANY_COPY_IND"));
				mobAppForm.setCopInd(rs.getString("COP_IND"));
				mobAppForm.setDelInd(rs.getString("DEL_IND"));
				mobAppForm.setDmsInd(rs.getString("DMS_IND"));
				
				if("M".equalsIgnoreCase(rs.getString("MO_IND"))){
					mobAppForm.setMandatory(true);
				}else{
					mobAppForm.setMandatory(false);
				}
				mobAppForm.setReportSeq(rs.getInt("SEQ_NO"));
	
				
				return mobAppForm;
			}
		};

		try {			
			mobAppFormList = simpleJdbcTemplate.query(sql, mapper, channelId, nature);
			return mobAppFormList;
			
		} catch (EmptyResultDataAccessException erdae) {
			erdae.printStackTrace();
			logger.info("EmptyResultDataAccessException in getMobAppFormDTO()");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Exception caught in getMobAppFormDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;
	}
	
	public List<String> getAsciiReplaceList() throws DAOException {
		// martin
		List<String> asciiReplaceList = new ArrayList<String>();
		String sql = "SELECT CODE FROM W_CODE_LKUP WHERE GRP_ID = 'ASCII_REPLACE'";
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("CODE");
			}
		};

		try {
			asciiReplaceList = simpleJdbcTemplate.query(sql, mapper);
			return asciiReplaceList;
		} catch (EmptyResultDataAccessException erdae) {
			erdae.printStackTrace();
			logger.info("EmptyResultDataAccessException in getAsciiReplaceList()");
		} catch (Exception e) {
			logger.error("Exception caught in getAsciiReplaceList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;
	}
	
	
	public boolean testConnection() throws DAOException {
		String sql = "select 1 from dual";
		logger.info("Testing connection - " + sql);

		try {
			simpleJdbcTemplate.queryForObject(sql, Integer.class, new HashMap<String,Object>());
			logger.info("Testing connection ok");
			return true;
		} catch (Exception e) {
			logger.error("Error while testing db connection");
			throw new DAOException("Test Connection Failed - " + e.getMessage(), e);
		}
	}
	
	//kinman new nowtv
	public String getNewTvPriceCutOff() throws DAOException{
		String sql = "select decode(SIGN(trunc(sysdate) - to_date(description, 'dd/MM/yyyy')),1,'Y','N') tv_cutoff from  w_code_lkup where grp_id = 'NEW_TV_PRICE_RETL'";
	
		List<String> cList = new ArrayList<String>();
		String cutOff ="";
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("tv_cutoff");
			}
		};

		try {
			cList = simpleJdbcTemplate.query(sql, mapper);
			if(cList!=null&&cList.size()>0)
				cutOff=cList.get(0);			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getNewTvPriceCutOff()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return cutOff;
	}
	
	public String getImsClubCutOff() throws DAOException{
		String sql = "select description club_cutoff from  w_code_lkup where grp_id = 'IMS_CLUB_CUTOFF'";
	
		List<String> cList = new ArrayList<String>();
		String cutOff ="";
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("club_cutoff");
			}
		};

		try {
			cList = simpleJdbcTemplate.query(sql, mapper);
			if(cList!=null&&cList.size()>0)
				cutOff=cList.get(0);			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getImsClubCutOff()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return cutOff;
	}
	
	public String getImsFsPrepayCutOff() throws DAOException{
		String sql = "select description fs_prepay_cutoff from  w_code_lkup where grp_id = 'IMS_FS_PREPAY_CUTOFF'";
	
		List<String> cList = new ArrayList<String>();
		String cutOff ="";
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("fs_prepay_cutoff");
			}
		};

		try {
			cList = simpleJdbcTemplate.query(sql, mapper);
			if(cList!=null&&cList.size()>0)
				cutOff=cList.get(0);			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getImsFsPrepayCutOff()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return cutOff;
	}
	
	public String getImsNTVClubCutOff() throws DAOException{
		String sql = "select description club_cutoff from  w_code_lkup where grp_id = 'IMS_NTV_CLUB_CUTOFF'";
	
		List<String> cList = new ArrayList<String>();
		String cutOff ="";
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("club_cutoff");
			}
		};

		try {
			cList = simpleJdbcTemplate.query(sql, mapper);
			if(cList!=null&&cList.size()>0)
				cutOff=cList.get(0);			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getImsClubCutOff()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return cutOff;
	}
	
	public String getImsNTVFsPrepayCutOff() throws DAOException{
		String sql = "select description fs_prepay_cutoff from  w_code_lkup where grp_id = 'IMS_NTV_FS_PREPAY_CUTOFF'";
	
		List<String> cList = new ArrayList<String>();
		String cutOff ="";
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("fs_prepay_cutoff");
			}
		};

		try {
			cList = simpleJdbcTemplate.query(sql, mapper);
			if(cList!=null&&cList.size()>0)
				cutOff=cList.get(0);			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getImsNTVFsPrepayCutOff()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return cutOff;
	}
	
	public String getDisableWQInd() throws DAOException{
		String sql = "select description ind from  w_code_lkup where grp_id = 'IMS_DISABLE_WQ'";
	
		List<String> cList = new ArrayList<String>();
		String ind ="";
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("ind");
			}
		};

		try {
			cList = simpleJdbcTemplate.query(sql, mapper);
			if(cList!=null&&cList.size()>0)
				ind=cList.get(0);			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getDisableWQInd()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return ind;
	}
	
	public List<String[]> getMobSimTypeMap() throws DAOException {

		String SQL = "select bcl.code_id, bcl.code_desc\n"
				+ "  from bomweb_code_lkup bcl\n"
				+ " where bcl.code_type = 'SIM_BRAND_PREFIX'\n";
		
		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
			public String[] mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String[] result = new String[2];
				result[0] = rs.getString("code_id");
				result[1] = rs.getString("code_desc");
				return result;
			}
		};
		
		try {
			List<String[]> result = simpleJdbcTemplate.query(SQL, mapper);
			if (CollectionUtils.isEmpty(result)) {
				return null;
			} else {
				return result;
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in getMobSimTypeMap()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;
	}
	
	public Date getSysDate() throws DAOException {
		String sql = "select sysdate from dual";
		logger.info("Getting db sysdate - " + sql);

		try {
			return simpleJdbcTemplate.queryForObject(sql, Timestamp.class, new HashMap<String,Object>());
		} catch (Exception e) {
			logger.error("Error while getting db sysdate");
			throw new DAOException(e.getMessage(), e);
		}
	}
	public String getEnableIOExclusiveCheckDesc() throws DAOException{
		
		String sql = "select description from w_code_lkup where grp_id='IMS_VASSELECT_VALIDA' and code='CHECK_BOM_EXCLUSIVE'  ";
	
		List<String> excluDescList = new ArrayList<String>();
		String excluDesc ="";
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("DESCRIPTION");
			}
		};

		try {
			excluDescList = simpleJdbcTemplate.query(sql, mapper);
			if(excluDescList!=null&&excluDescList.size()>0)
				excluDesc=excluDescList.get(0);			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getEnableIOExclusiveCheckDesc()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return excluDesc;
	}
	
	
	
	
	public List<ItemFuncAssgnMobDTO> getItemFuncAssgnMobListAll() throws DAOException {
		
		String sql =  "SELECT "
				+ " F.ITEM_ID, "
				+ " F.FUNC_DESC, "
				+ " F.FUNC_ID, "
				+ " F.EFF_START_DATE, "
				+ " F.EFF_END_DATE, "
				+ " C.CODE_DESC AS EXTRA_INFO "
				+ " FROM "
				+ " W_ITEM_FUNC_ASSGN_MOB_VIEW F,  "
				+ " BOMWEB_CODE_LKUP C "
				+ " WHERE "	
				+ " F.FUNC_ID=C.CODE_ID(+) "
				+ " AND C.CODE_TYPE(+)='EXTRA_INFO_FUNC'"
				+ " ORDER BY F.ITEM_ID "
				;
		
		logger.info("findItemFuncAssgnMobDTO is called");

		if (logger.isDebugEnabled()) {
			logger.debug(sql);
		}
		try {
			List<ItemFuncAssgnMobDTO> list = this.simpleJdbcTemplate.query(sql,
					ParameterizedBeanPropertyRowMapper.newInstance(ItemFuncAssgnMobDTO.class));
			return list;
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getItemFuncAssgnMobListAll()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getItemFuncAssgnMobListAll():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public List<String[]> getPreInstallParameter() throws DAOException {

		String SQL = "select code, description\n"
				+ "  from w_code_lkup \n"
				+ " where grp_id = 'IMS_ACQ_PREINST_PARA'\n";
		
		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
			public String[] mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String[] result = new String[2];
				result[0] = rs.getString("code");
				result[1] = rs.getString("description");
				return result;
			}
		};
		
		try {
			List<String[]> result = simpleJdbcTemplate.query(SQL, mapper);
			if (CollectionUtils.isEmpty(result)) {
				return null;
			} else {
				return result;
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in getMobSimTypeMap()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;
	}
	
	
	public List<Map<String, String>> getImsThirdPartyRelationshipLookUpCode() throws DAOException{
		logger.info("getImsLookUpCode");
		String SQL = "SELECT a.locale, b.code, a.description FROM  w_display_lkup a JOIN w_code_lkup b ON a.ID = b.description AND a.TYPE = b.grp_id WHERE a.TYPE = 'NTV_THIRD_PARTY_REL' ORDER BY a.ID";

		
		ParameterizedRowMapper<Map<String, String>> mapper = new ParameterizedRowMapper<Map<String, String>>() {

			public Map<String, String> mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				HashMap<String, String> map = new HashMap<String, String>();
				
				map.put("code", rs.getString("code"));
				map.put("locale", rs.getString("locale"));
				map.put("description", rs.getString("description"));
				
				return map;
			}
		};
		
		try {
			logger.debug("getImsThirdPartyRelationshipLookUpCode @ sql: " + SQL);
			List<Map<String, String>> map = simpleJdbcTemplate
					.query(SQL, mapper);
			
			return map;
		
		}catch (EmptyResultDataAccessException erdae) {
			return null;
		}catch (Exception e) {
			logger.error("Exception caught in getImsThirdPartyRelationshipLookUpCode()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	public List<Map<String, String>> getImsThirdPartyRelationshipChannel13LookUpCode() throws DAOException{
		logger.info("getImsLookUpCode");
		String SQL = "SELECT a.locale, b.code, a.description FROM  w_display_lkup a JOIN w_code_lkup b ON a.ID = b.description AND a.TYPE = b.grp_id WHERE a.TYPE = 'NTV_THIRD_REL_CH_13' ORDER BY a.ID";

		
		ParameterizedRowMapper<Map<String, String>> mapper = new ParameterizedRowMapper<Map<String, String>>() {

			public Map<String, String> mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				HashMap<String, String> map = new HashMap<String, String>();
				
				map.put("code", rs.getString("code"));
				map.put("locale", rs.getString("locale"));
				map.put("description", rs.getString("description"));
				
				return map;
			}
		};
		
		try {
			logger.debug("getImsThirdPartyRelationshipLookUpCode @ sql: " + SQL);
			List<Map<String, String>> map = simpleJdbcTemplate
					.query(SQL, mapper);
			
			return map;
		
		}catch (EmptyResultDataAccessException erdae) {
			return null;
		}catch (Exception e) {
			logger.error("Exception caught in getImsThirdPartyRelationshipLookUpCode()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public List<Map<String, String>> getSpecialRequest() throws DAOException{
		logger.info("getSpecialRequest");
		String SQL = "SELECT a.locale, b.code, a.description FROM  w_display_lkup a JOIN w_code_lkup b ON a.ID = b.description AND a.TYPE = b.grp_id WHERE a.TYPE = 'SB_IMS_NTV_SPEC_REQ' ORDER BY a.ID";

		
		ParameterizedRowMapper<Map<String, String>> mapper = new ParameterizedRowMapper<Map<String, String>>() {

			public Map<String, String> mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				HashMap<String, String> map = new HashMap<String, String>();
				
				map.put("code", rs.getString("code"));
				map.put("locale", rs.getString("locale"));
				map.put("description", rs.getString("description"));
				
				return map;
			}
		};
		
		try {
			logger.debug("getSpecialRequest @ sql: " + SQL);
			List<Map<String, String>> map = simpleJdbcTemplate
					.query(SQL, mapper);
			
			return map;
		
		}catch (EmptyResultDataAccessException erdae) {
			return null;
		}catch (Exception e) {
			logger.error("Exception caught in getSpecialRequest()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public List<Map<String, String>> getImsLookUpCodeMultiLang(String type) throws DAOException{
		logger.info("getImsLookUpCodeMultiLang");
		String SQL = "SELECT a.locale, b.code, a.description FROM  w_display_lkup a JOIN w_code_lkup b ON a.ID = b.description AND a.TYPE = b.grp_id WHERE a.TYPE = ? ORDER BY a.ID";

		
		ParameterizedRowMapper<Map<String, String>> mapper = new ParameterizedRowMapper<Map<String, String>>() {

			public Map<String, String> mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				HashMap<String, String> map = new HashMap<String, String>();
				
				map.put("code", rs.getString("code"));
				map.put("locale", rs.getString("locale"));
				map.put("description", rs.getString("description"));
				
				return map;
			}
		};
		
		try {
			logger.debug("getSpecialRequest @ sql: " + SQL);
			List<Map<String, String>> map = simpleJdbcTemplate
					.query(SQL, mapper, type);
			
			return map;
		
		}catch (EmptyResultDataAccessException erdae) {
			return null;
		}catch (Exception e) {
			logger.error("Exception caught in getImsLookUpCodeMultiLang()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public String getCareCashVisitDate() throws DAOException {

		String sql = "select description from w_code_lkup where grp_id='IMS_ACQ_CARECASH' and code='VISITDATE'  ";
	
		List<String> resultList = new ArrayList<String>();
		String result ="";
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("DESCRIPTION");
			}
		};

		try {
			resultList = simpleJdbcTemplate.query(sql, mapper);
			if(resultList!=null&&resultList.size()>0)
				result=resultList.get(0);			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getEnableIOExclusiveCheckDesc()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return result;
		
	}
	
	public String getCareCashShowInd(String code) throws DAOException {

		if (StringUtils.isEmpty(code)) {
			code = "IMS_SHOW_CARECASH";
		}
		
		String sql = "select description from w_code_lkup where grp_id='IMS_SHOW_CARECASH' and code=?  ";
	
		List<String> resultList = new ArrayList<String>();
		String result ="";
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("DESCRIPTION");
			}
		};

		try {
			resultList = simpleJdbcTemplate.query(sql, mapper, code);
			if(resultList!=null&&resultList.size()>0)
				result=resultList.get(0);			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getCareCashShowInd()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return result;
		
	}
	
	public Map<String, String> getImsServPlanStaticReportWords(String locale, String rptName, String attribute) 
		throws DAOException {
		if (StringUtils.isEmpty(rptName)) {
			rptName = "ims/BomWebPortal/SectionJ%";
		}
		if (StringUtils.isEmpty(attribute)) {
			attribute = "servPlan%";
		}
		List<String[]> tempResult = new ArrayList<String[]>();
		Map<String, String> result = new HashMap<String, String>();
		
		String sql = "  SELECT ATTRIBUTE, CONTENTS" +
				"  FROM bomweb_rpt_template" +
				"  WHERE rpt_name LIKE ?" +
				"  AND attribute LIKE ?" +
				"  AND language = ?";
		
		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
			public String[] mapRow(ResultSet rs, int rownum) throws SQLException {
				String[] temp2DStringArray;
				temp2DStringArray = new String[2]; 
				temp2DStringArray[0] = (String) rs.getString("ATTRIBUTE");
				temp2DStringArray[1] = (String) rs.getString("CONTENTS");
				return temp2DStringArray;
			}
		};
		
		try {
			tempResult = simpleJdbcTemplate.query(sql, mapper, rptName, attribute, locale);
			if (tempResult != null && tempResult.size() > 0) {
				for (int i = 0; i < tempResult.size(); i++) {
					String temp[] = tempResult.get(i);
					result.put(temp[0], temp[1]);
				}
			}
		} catch (EmptyResultDataAccessException ee) {
			logger.debug("EmptyResultDataAccessException @getImsServPlanStaticReportWords");
			result = null;
		} catch (Exception e) {
			logger.debug("Exception @getImsServPlanStaticReportWords", e);
			result = null;
			throw new DAOException(e.getMessage(),e);
		}
		
		return result;
	}
	
	public List<Map<String, String>> getImsNTVRetSellingSegmentLookUpCode() throws DAOException{
		logger.info("getImsNTVRetSellingSegmentLookUpCode");
		String SQL = "	select pcm_name, locale, description from w_display_lkup	"+
		"	where type like 'IMS_NTV_SELLSEG'	"+
		"	order by id, locale	";

		
		ParameterizedRowMapper<Map<String, String>> mapper = new ParameterizedRowMapper<Map<String, String>>() {

			public Map<String, String> mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				HashMap<String, String> map = new HashMap<String, String>();
				
				map.put("pcm_name", rs.getString("pcm_name"));
				map.put("locale", rs.getString("locale"));
				map.put("description", rs.getString("description"));
				
				return map;
			}
		};
		
		try {
			logger.debug("getImsNTVRetSellingSegment @ sql: " + SQL);
			List<Map<String, String>> map = simpleJdbcTemplate
					.query(SQL, mapper);
			
			return map;
		
		}catch (EmptyResultDataAccessException erdae) {
			return null;
		}catch (Exception e) {
			logger.error("Exception caught in getImsNTVRetSellingSegment()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public String getSellingSegmentShowInd(String code) throws DAOException {

		if (StringUtils.isEmpty(code)) {
			code = "NTVRET_SHOW_SELLSEG";
		}
		
		String sql = "select description from w_code_lkup where grp_id='IMS_SHOW_SELLSEG' and code=?  ";
	
		List<String> resultList = new ArrayList<String>();
		String result ="";
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("DESCRIPTION");
			}
		};

		try {
			resultList = simpleJdbcTemplate.query(sql, mapper, code);
			if(resultList!=null&&resultList.size()>0)
				result=resultList.get(0);			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getSellingSegmentShowInd()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return result;
		
	}
	
	public String[] getImsNTVCPQProxySettings() throws DAOException {
		String[] settings = {"", "", ""};
		
		String grp_id = "SBIMS_NTV_PROXY_SETTING";
		String[] codes = {"USE_PROXY", "PROXY_URL", "PROXY_PORT"};
		
		String sql = "SELECT code, description FROM w_code_lkup WHERE grp_id = 'SBIMS_NTV_PROXY_SETTING'";
		
		ParameterizedRowMapper<Map<String, String>> mapper = new ParameterizedRowMapper<Map<String, String>>() {
			public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(rs.getString("code"), rs.getString("description"));
				return map;
			}
		};
		
		try {
			List<Map<String, String>> map = simpleJdbcTemplate.query(sql, mapper);
			
			for (int i = 0; i < codes.length; i++) {
				for (int j = 0; j < map.size(); j++) {
					if (map.get(j).get(codes[i]) != null) {
						settings[i] = map.get(j).get(codes[i]);
						break;
					}
				}
			}
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getImsNTVCPQProxySettings()", e);
			throw new DAOException(e.getMessage(), e);
		}

		
		return settings;
	}
		

}
