package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.dto.IssueBankDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.CreditCardInstDTO;
import com.bomwebportal.mob.ccs.dto.ui.PaymentUpFrontUI;


public class PaymentUpfrontDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());
	
	// === Start of INSERT function ===
	private static final String insertBomwebUpfrontPaymentSQL = " INSERT INTO BOMWEB_UPFRONT_PAYMENT ( \n"
			+ "    ORDER_ID,  \n"
			+ "    PAY_MTD_TYPE,  \n"
			+ "    THIRD_PARTY_IND,  \n"
			+ "    CC_TYPE,  \n"
			+ "    CC_NUM,  \n"
			+ "    CC_HOLD_NAME,  \n"
			+ "    CC_EXP_DATE,  \n"
			+ "    CC_ISSUE_BANK,  \n"
			//+ "    CC_ID_DOC_TYPE,  \n"
			//+ "    CC_ID_DOC_NO,  \n"
			//+ "    CC_INST_IND,  \n"
			//+ "    CC_VERIFIED_IND,  \n"
			+ "    PAY_METHOD,  \n"
			+ "    PAY_AMT,  \n"
			//+ "    LOCK_IND,  \n"
			+ "    CREATE_BY,  \n"
			//+ "    CREATE_DATE,  \n"
			+ "    LAST_UPD_BY,  \n"
			+ "    CC_VERIFIED_IND,  \n"
			+ "    CC_INST_SCHEDULE,  \n"
			+ "   BYPASS_IND   \n"
			+ ") VALUES ( \n"
			+ "? , \n"
			+ "? , \n"
			+ "? , \n"
			+ "? , \n"
			+ "? , \n"
			+ "? , \n"
			+ "? , \n"
			+ "? , \n"
			+ "? , \n"
			+ "? , \n"
			+ "? , \n"
			+ "? , \n"
			+ "? , \n"
			+ "? , \n"
			+ "?  \n" + " ) \n";

	public int insertBomwebUpfrontPayment(PaymentUpFrontUI dto) throws DAOException {
		logger.info("insertBomwebUpfrontPayment is called");

		try {
			//String ccInstInd;
			//String paymthType;
			String ccExpDate;
			String ccInstSchedule = dto.getCcInstSchedule();
			if ("I".equalsIgnoreCase(dto.getPayMethodType())) {
				//ccInstInd = "Y";
				//paymthType = "C";
				ccExpDate = dto.getCreditExpiryMonth() + "/" + dto.getCreditExpiryYear();
			} else if ("C".equalsIgnoreCase(dto.getPayMethodType())){
				//ccInstInd = "N";
				//paymthType = "C";
				ccInstSchedule = "";
				ccExpDate = dto.getCreditExpiryMonth() + "/" + dto.getCreditExpiryYear();
			} else {
				//ccInstInd = "";
				ccExpDate = "";
				ccInstSchedule = "";
				//paymthType = "M";
			}
			logger.debug("insertBomwebUpfrontPayment() @ PaymentUpfrontDAO: "
					+ insertBomwebUpfrontPaymentSQL);
			return simpleJdbcTemplate.update(
					insertBomwebUpfrontPaymentSQL,
					// start sql mapping
					dto.getOrderId(), dto.getPayMethodType(), dto.getThirdPartyInd() ,
					dto.getCreditCardType(), dto.getCreditCardNum(),
					dto.getCreditCardHolderName(), ccExpDate,
					dto.getCreditCardIssueBankCd(), //ccInstInd,
					dto.getPaymentCombination(),dto.getInAdvanceAmount(),
					dto.getUsername(), dto.getUsername(), dto.getCreditCardVerifiedInd(), ccInstSchedule,
					(dto.isByPassValidation() == true ? "Y" : "N")
			// end sql mapping
					);
		} catch (Exception e) {
			logger.error("Exception caught in insertBomwebUpfrontPayment()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	// === End of INSERT function ===
	
	private final String getBomwebUpfrontPaymentSQL = "select up.ORDER_ID, \n"
							+ "up.PAY_MTD_TYPE,			\n"
							+ "up.THIRD_PARTY_IND,		\n"
							+ "up.CC_TYPE,		\n"
							+ "up.CC_NUM,		\n"
							+ "up.CC_HOLD_NAME,		\n"
							+ "up.CC_EXP_DATE,		\n"
							+ "up.CC_ISSUE_BANK,		\n"
							+ "up.PAY_METHOD,		\n"
							+ "up.PAY_AMT,			\n"
							+ "up.CREATE_BY,			\n"
							+ "up.CC_INST_SCHEDULE,				\n" 
							+ "up.BYPASS_IND,				\n"
							+ "up.CC_VERIFIED_IND,				\n"
							+ "  (select cb.bank_name from W_ISSUEBANKLKUP cb\n"
							+ "      where cb.bank_code = up.CC_ISSUE_BANK) CC_ISSUE_BANK_NAME \n"
							+ "from BOMWEB_UPFRONT_PAYMENT up		\n"
							+ "where up.ORDER_ID = ?    \n";

	public PaymentUpFrontUI getBomwebUpfrontPayment(String orderId) throws DAOException {
		logger.info(" getBomwebUpfrontPayment is called");

		List<PaymentUpFrontUI> resultList = new ArrayList<PaymentUpFrontUI>();

		ParameterizedRowMapper<PaymentUpFrontUI> mapper = new ParameterizedRowMapper<PaymentUpFrontUI>() {

			public PaymentUpFrontUI mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				PaymentUpFrontUI dto = new PaymentUpFrontUI();
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setPayMethodType(rs.getString("PAY_MTD_TYPE"));
				dto.setThirdPartyInd(rs.getString("THIRD_PARTY_IND"));
				dto.setCreditCardType(rs.getString("CC_TYPE"));
				dto.setCreditCardNum(rs.getString("CC_NUM"));
				dto.setCreditCardHolderName(rs.getString("CC_HOLD_NAME"));
				
				if (rs.getString("CC_EXP_DATE") != null) {
					dto.setCreditExpiryMonth(rs.getString("CC_EXP_DATE").split("/")[0]);
					dto.setCreditExpiryYear(rs.getString("CC_EXP_DATE").split("/")[1]);
				}
				
				dto.setCreditCardIssueBankCd(rs.getString("CC_ISSUE_BANK"));
				dto.setPaymentCombination(rs.getString("PAY_METHOD"));
				dto.setInAdvanceAmount(rs.getString("PAY_AMT"));
				dto.setUsername(rs.getString("CREATE_BY"));
				dto.setCcInstSchedule(rs.getString("CC_INST_SCHEDULE"));
				dto.setByPassValidation("Y".equalsIgnoreCase(rs.getString("BYPASS_IND")));
				dto.setCreditCardIssueBankName(rs.getString("CC_ISSUE_BANK_NAME"));
				dto.setCreditCardVerifiedInd(rs.getString("CC_VERIFIED_IND"));
				return dto;
			}
		};

		try {
			logger.debug("getBomwebUpfrontPaymentSQL() @ PaymentUpfrontDAO: "
					+ getBomwebUpfrontPaymentSQL);
			
			resultList = simpleJdbcTemplate.query(getBomwebUpfrontPaymentSQL, mapper, orderId);
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBomwebUpfrontPayment()");
		} catch (Exception e) {
			logger.info("Exception caught in getBomwebUpfrontPayment():",e);
			throw new DAOException(e.getMessage(), e);
		}

		if (resultList != null && !resultList.isEmpty()) {
			return resultList.get(0);
		} else {
			return null;
		}
	}

	private final String getCreditCardInstListSQL = "SELECT a.bank_code, \n"
					+ "a.bank_name, \n" 
					+ "b.min_inst_amt, \n" 
					+ "b.INST_SCHEDULE, \n"
					+ "b.start_date, \n"
					+ "b.end_date \n"
					+ "FROM w_inst_schedule b, w_inst_issuebanklkup a \n"
					+ "WHERE a.bank_code=b.bank_code \n"
					+ "AND ? >=b.MIN_INST_AMT \n"
					+ "AND SYSDATE BETWEEN b.start_date AND NVL(b.end_date,SYSDATE)\n"
					+ "AND b.BANK_CODE = ? \n"
					+ "AND b.channel_id= ? \n";
	
	public List<CreditCardInstDTO>  getCreditCardInstList(String bankCode, String upfrontAmt, int channelId) throws DAOException {
		logger.info("getCreditCardInstList is called");
		logger.info("bankCode:"+bankCode+", upfrontAmt"+upfrontAmt);
		
		List<CreditCardInstDTO>  resultList = new ArrayList<CreditCardInstDTO>();

		ParameterizedRowMapper<CreditCardInstDTO> mapper = new ParameterizedRowMapper<CreditCardInstDTO>() {

			public CreditCardInstDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				CreditCardInstDTO dto = new CreditCardInstDTO();
				dto.setBankCode(rs.getString("bank_code"));
				dto.setBankName(rs.getString("bank_name"));
				dto.setMinInstAmt(rs.getString("min_inst_amt"));
				dto.setInstSchedule(rs.getString("INST_SCHEDULE"));
				dto.setStartDate(rs.getString("start_date"));
				dto.setEndDate(rs.getString("end_date"));
				return dto;
			}
		};

		try {
			logger.debug("getCreditCardInstListSQL() @ PaymentUpfrontDAO: "
					+ getCreditCardInstListSQL);
			
			resultList = simpleJdbcTemplate.query(getCreditCardInstListSQL, mapper, upfrontAmt, bankCode, channelId);
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getCreditCardInstList()");
		} catch (Exception e) {
			logger.info("Exception caught in getCreditCardInstList():",e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return resultList;
		
	}
	
	private final String getCreditCardInstBankListSQL = "SELECT distinct a.bank_code, \n"
			+ "a.bank_name \n" 
			+ "FROM w_inst_schedule b, w_inst_issuebanklkup a \n"
			+ "WHERE a.bank_code=b.bank_code \n"
			+ "AND ? >=b.MIN_INST_AMT \n"
			+ "AND SYSDATE BETWEEN b.start_date AND NVL(b.end_date,SYSDATE)\n"
			+ "AND b.channel_id= ? \n";

	public List<IssueBankDTO>  getCreditCardInstBankList(String upfrontAmt, int channelId) throws DAOException {
	logger.info("getCreditCardInstBankList is called");
	logger.info("upfrontAmt:"+upfrontAmt);
	
	List<IssueBankDTO>  resultList = new ArrayList<IssueBankDTO>();
	
	ParameterizedRowMapper<IssueBankDTO> mapper = new ParameterizedRowMapper<IssueBankDTO>() {
	
		public IssueBankDTO mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			IssueBankDTO dto = new IssueBankDTO();
			dto.setBankCd(rs.getString("bank_code"));
			dto.setBankName(rs.getString("bank_name"));
			return dto;
		}
	};
	
	try {
		logger.debug("getCreditCardInstBankListSQL() @ PaymentUpfrontDAO: "
				+ getCreditCardInstBankListSQL);
		
		resultList = simpleJdbcTemplate.query(getCreditCardInstBankListSQL, mapper, upfrontAmt, channelId);
		
	} catch (EmptyResultDataAccessException erdae) {
		logger.info("EmptyResultDataAccessException in getCreditCardInstBankList()");
	} catch (Exception e) {
		logger.info("Exception caught in getCreditCardInstBankList():",e);
		throw new DAOException(e.getMessage(), e);
	}
	
	return resultList;
	
	}
	
	public String[] getInstallBankInfo(String orderId) throws DAOException {
		
		String sql = "SELECT BANK_NAME_CHI, NO_OF_SIGNATURE " 
				+"FROM W_INST_ISSUEBANKLKUP " 
				+"WHERE BANK_CODE IN " 
				+"  (SELECT CC_ISSUE_BANK " 
				+"  FROM BOMWEB_UPFRONT_PAYMENT " 
				+"  WHERE ORDER_ID      = :orderId " 
				+"  AND PAY_MTD_TYPE    = 'I' " 
				+"  AND THIRD_PARTY_IND = 'N' " 
				+"  AND CC_ISSUE_BANK IN " 
				+"    (SELECT DISTINCT CODE_ID " 
				+"    FROM BOMWEB_CODE_LKUP " 
				+"    WHERE CODE_TYPE = 'INST_FORM_BANK' " 
				+"    ) " 
				+"  )"; 

		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
			public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
				String[] temp = new String[2];
				temp[0] = rs.getString("BANK_NAME_CHI");
				temp[1] = rs.getString("NO_OF_SIGNATURE");
				return temp;
			}
		};
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);

		try {
			logger.debug("getInstallBankInfo() @ PaymentUpfrontDAO:" + sql);
			String[] result = {"", ""};
			List<String[]> tempResult = simpleJdbcTemplate.query(sql, mapper, params);
			if (tempResult != null && tempResult.size() > 0) {
				result = tempResult.get(0);
			}
			return result;
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getInstallBankInfo()");
			
		} catch (Exception e) {
			logger.info("Exception caught in getInstallBankInfo():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return new String[]{"", ""};
	}
	
	public String getStandaloneHandSetPaymentUpfront(String itemId, Date appDate) throws DAOException {
		logger.info(" getStandaloneHandSetPaymentUpfront is called");
		String standaloneHandsetPaymentUpfrontSQL =	"SELECT NVL(onetime_amt, 0) HS_ONETIME " +
													"FROM w_item i " +
													"JOIN w_item_pricing p ON i.id = p.id " +
													"WHERE i.id = :itemId " +
													"AND p.payment_group = 'HANDSET' " +
													"AND NVL(p.oneTime_type,'N') != 'A' " +
													"AND TRUNC(:appDate) BETWEEN p.eff_start_date AND NVL(p.eff_end_date, sysdate)";
		
		String upfrontAmount = "";
		
		try {
			logger.debug("getStandaloneHandSetPaymentUpfront() @ PaymentUpfrontDAO: " + standaloneHandsetPaymentUpfrontSQL);
			upfrontAmount = simpleJdbcTemplate.queryForObject(standaloneHandsetPaymentUpfrontSQL, String.class, itemId);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getStandaloneHandSetPaymentUpfront()");
		} catch (Exception e) {
			logger.info("Exception caught in getStandaloneHandSetPaymentUpfront():",e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return upfrontAmount;
		
	}
	
}
