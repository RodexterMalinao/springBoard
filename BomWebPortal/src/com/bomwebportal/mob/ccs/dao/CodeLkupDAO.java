package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;

public class CodeLkupDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());

	/*
	 * private static String getCodeLkupDTOSQL = "SELECT \n" +
	 * "     CODE_TYPE,\n" + "     CODE_ID,\n" + "     CODE_DESC,\n" +
	 * "     CREATE_BY,\n" + "     CREATE_DATE,\n" + "     LAST_UPD_BY,\n" +
	 * "     LAST_UPD_DATE\n" + " from BOMWEB_CODE_LKUP Where CODE_ID=? \n";
	 * 
	 * public CodeLkupDTO getCodeLkupDTO(String codeId) throws DAOException {
	 * logger.info(" getCodeLkupDTO is called"); List<CodeLkupDTO> itemList =
	 * new ArrayList<CodeLkupDTO>();
	 *//**** ==ParameterizedRowMapper start== *********************************************************/
	/*
	 * ParameterizedRowMapper<CodeLkupDTO> mapper = new
	 * ParameterizedRowMapper<CodeLkupDTO>() { public CodeLkupDTO
	 * mapRow(ResultSet rs, int rowNum) throws SQLException { CodeLkupDTO dto =
	 * new CodeLkupDTO(); dto.setCodeType(rs.getString("CODE_TYPE"));
	 * dto.setCodeId(rs.getString("CODE_ID"));
	 * dto.setCodeDesc(rs.getString("CODE_DESC"));
	 * dto.setCreateBy(rs.getString("CREATE_BY"));
	 * dto.setCreateDate(rs.getDate("CREATE_DATE"));
	 * dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
	 * dto.setLastUpdDate(rs.getDate("LAST_UPD_DATE")); return dto; } };
	 *//**** ==ParameterizedRowMapper end== *********************************************************/
	/*
	 * try { logger.info("getCodeLkupDTO() @ CodeLkupDTO: " +
	 * getCodeLkupDTOSQL); itemList =
	 * simpleJdbcTemplate.query(getCodeLkupDTOSQL, mapper, codeId); } catch
	 * (EmptyResultDataAccessException erdae) {
	 * logger.info("EmptyResultDataAccessException in getCodeLkupDTO()");
	 * 
	 * itemList = null; } catch (Exception e) {
	 * logger.info("Exception caught in getCodeLkupDTO():", e);
	 * 
	 * throw new DAOException(e.getMessage(), e); } return itemList.get(0);//
	 * only return the first one }
	 */

	private static String getCodeLkupDTOAllSQL = "SELECT \n"
			+ "     CODE_TYPE,\n" + "     CODE_ID,\n" + "     CODE_DESC,\n"
			+ "     CREATE_BY,\n" + "     CREATE_DATE,\n"
			+ "     LAST_UPD_BY,\n" + "     LAST_UPD_DATE\n"
			+ " from BOMWEB_CODE_LKUP where CODE_TYPE =? order by CODE_ID\n";

	public List<CodeLkupDTO> getCodeLkupDTOALL(String codeType)
			throws DAOException {
		logger.info("getCodeLkupDTO is called, codeType:"+codeType);
		List<CodeLkupDTO> itemList = new ArrayList<CodeLkupDTO>();
		/**** ==ParameterizedRowMapper start== *********************************************************/
		ParameterizedRowMapper<CodeLkupDTO> mapper = new ParameterizedRowMapper<CodeLkupDTO>() {
			public CodeLkupDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				CodeLkupDTO dto = new CodeLkupDTO();
				dto.setCodeType(rs.getString("CODE_TYPE"));
				dto.setCodeId(rs.getString("CODE_ID"));
				dto.setCodeDesc(rs.getString("CODE_DESC"));
				//dto.setCreateBy(rs.getString("CREATE_BY"));
				//dto.setCreateDate(rs.getDate("CREATE_DATE"));
				//dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				//dto.setLastUpdDate(rs.getDate("LAST_UPD_DATE"));
				return dto;
			}
		};
		/**** ==ParameterizedRowMapper end== *********************************************************/
		try {
			logger.debug("getCodeLkupDTO() @ CodeLkupDTO:"+ getCodeLkupDTOAllSQL);
			
			itemList = simpleJdbcTemplate.query(getCodeLkupDTOAllSQL, mapper,
					codeType);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getCodeLkupDTO()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getCodeLkupDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;// only return the first one
	}
	
	
	private static String getCodeLkupDTOAllSQLDesc = "SELECT \n"
			+ "     CODE_TYPE,\n" + "     CODE_ID,\n" + "     CODE_DESC,\n"
			+ "     CREATE_BY,\n" + "     CREATE_DATE,\n"
			+ "     LAST_UPD_BY,\n" + "     LAST_UPD_DATE\n"
			+ " from BOMWEB_CODE_LKUP where CODE_TYPE =? order by CODE_ID Desc\n";

	public List<CodeLkupDTO> getCodeLkupDTOALLDesc(String codeType)
			throws DAOException {
		logger.info("getCodeLkupDTO is called, codeType:"+codeType);
		List<CodeLkupDTO> itemList = new ArrayList<CodeLkupDTO>();
		/**** ==ParameterizedRowMapper start== *********************************************************/
		ParameterizedRowMapper<CodeLkupDTO> mapper = new ParameterizedRowMapper<CodeLkupDTO>() {
			public CodeLkupDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				CodeLkupDTO dto = new CodeLkupDTO();
				dto.setCodeType(rs.getString("CODE_TYPE"));
				dto.setCodeId(rs.getString("CODE_ID"));
				dto.setCodeDesc(rs.getString("CODE_DESC"));
				//dto.setCreateBy(rs.getString("CREATE_BY"));
				//dto.setCreateDate(rs.getDate("CREATE_DATE"));
				//dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				//dto.setLastUpdDate(rs.getDate("LAST_UPD_DATE"));
				return dto;
			}
		};
		/**** ==ParameterizedRowMapper end== *********************************************************/
		try {
			logger.debug("getCodeLkupDTO() @ CodeLkupDTO:"+ getCodeLkupDTOAllSQLDesc);
			
			itemList = simpleJdbcTemplate.query(getCodeLkupDTOAllSQLDesc, mapper,
					codeType);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getCodeLkupDTO()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getCodeLkupDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;// only return the first one
	}
	
	//modify Eliot 20120308
	public String getCodeDesc (String codeType, String codeId) throws DAOException {
		String sql = "SELECT code_desc FROM bomweb_code_lkup" +
					 " WHERE code_type = ? and code_id = ?";
		
		String result = "";
		
		try {
		    logger.debug("getCodeDesc() @ CodeLkupDTO:"+ sql);			
		    result = (String)simpleJdbcTemplate.queryForObject(sql, String.class, codeType, codeId);
		    return result;
			
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getCodeLkupDTO()");
		    return result;
		} catch (Exception e) {
		    logger.info("Exception caught in getCodeLkupDTO():", e);
		    throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	/*
	 * // === Start of INSERT function === private static final String
	 * insertCodeLkupSQL = " INSERT INTO BOMWEB_CODE_LKUP ( \n" +
	 * "    CODE_TYPE,  \n" + "    CODE_ID,  \n" + "    CODE_DESC,  \n" +
	 * "    CREATE_BY,  \n" + "    CREATE_DATE,  \n" + "    LAST_UPD_BY,  \n" +
	 * "    LAST_UPD_DATE  \n" + ") VALUES ( \n" + "? , \n" + "? , \n" +
	 * "? , \n" + "? , \n" + "? , \n" + "? , \n" + "?  \n" + " ) \n";
	 * 
	 * public int insertCodeLkup(CodeLkupDTO dto) throws DAOException {
	 * logger.info("insertCodeLkup is called");
	 * 
	 * try { logger.info("insertCodeLkup() @ CodeLkupDAO: " +
	 * insertCodeLkupSQL); return simpleJdbcTemplate.update( insertCodeLkupSQL,
	 * // start sql mapping dto.getCodeType(), dto.getCodeId(),
	 * dto.getCodeDesc(), dto.getCreateBy(), dto.getCreateDate(),
	 * dto.getLastUpdBy(), dto.getLastUpdDate() // end sql mapping ); } catch
	 * (Exception e) { logger.error("Exception caught in insertCodeLkup()", e);
	 * throw new DAOException(e.getMessage(), e); } }
	 */

	/*
	 * // === End of INSERT function ===
	 * 
	 * // === Start of UPDATE function === private static final String
	 * updateCodeLkupSQL = " UPDATE BOMWEB_CODE_LKUP SET \n" +
	 * "     CODE_TYPE = ? ,\n" + "     CODE_ID = ? ,\n" +
	 * "     CODE_DESC = ? ,\n" + "     CREATE_BY = ? ,\n" +
	 * "     CREATE_DATE = ? ,\n" + "     LAST_UPD_BY = ? ,\n" +
	 * "     LAST_UPD_DATE = ? \n" + " WHERE  CODE_ID = ? \n";
	 * 
	 * public int updateCodeLkup(CodeLkupDTO dto) throws DAOException {
	 * logger.info("updateCodeLkup is called");
	 * 
	 * try { logger.info("updateCodeLkup() @ CodeLkupDAO: " +
	 * updateCodeLkupSQL); return simpleJdbcTemplate.update( updateCodeLkupSQL,
	 * // start sql mapping dto.getCodeType(), dto.getCodeId(),
	 * dto.getCodeDesc(), dto.getCreateBy(), dto.getCreateDate(),
	 * dto.getLastUpdBy(), dto.getLastUpdDate(), dto.getCodeId());
	 * 
	 * } catch (Exception e) {
	 * logger.error("Exception caught in updateCodeLkup()", e); throw new
	 * DAOException(e.getMessage(), e); }
	 * 
	 * }
	 */

	/*
	 * // === End of UPDATE function === // === Start of DELETE function ===
	 * private static final String deleteCodeLkupSQL =
	 * "  delete from BOMWEB_CODE_LKUP where CODE_ID=?";
	 * 
	 * public int deleteCodeLkup(CodeLkupDTO dto) throws DAOException {
	 * logger.info("deleteCodeLkup is called");
	 * 
	 * try { logger.info("deleteCodeLkup() @ CodeLkupDAO: " +
	 * deleteCodeLkupSQL); return simpleJdbcTemplate .update(deleteCodeLkupSQL,
	 * dto.getCodeId()); } catch (Exception e) {
	 * logger.error("Exception caught in deleteCodeLkup()", e); throw new
	 * DAOException(e.getMessage(), e); } } // === End of DELETE function ===
	 */
	

	//add by Herbert 20120607
	private static String getCodeTypeByIdSQL = "SELECT" +
			"  code_type " +
			" FROM bomweb_code_lkup" +
			" WHERE code_type IN (:codeTypes)" +
			" AND code_id = :codeId ";
	public String getCodeTypeById(List<String> codeTypes, String codeId) throws DAOException {
		List<String> itemList = Collections.emptyList();
		String result = "";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("codeTypes", codeTypes);
			params.addValue("codeId", codeId);
			if (logger.isInfoEnabled()) {
				logger.info("getCodeTypeById() @ CodeLkupDAO: " + getCodeTypeByIdSQL);
			}
			itemList = this.simpleJdbcTemplate.query(getCodeTypeByIdSQL, this.getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getCodeTypeById()");
			}
			itemList = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getCodeTypeById():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		if (!itemList.isEmpty()){
			result = itemList.get(0);
		} 
		return result;
	}
	
	private ParameterizedRowMapper<String> getRowMapper() {
		return new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("code_type");
			}
		};
	}
	
	private static String getPayMethodCodeLkup = 
			"select CL.CODE_TYPE, CL.CODE_ID, CL.CODE_DESC\n" +
					"from BOMWEB_CODE_LKUP CL\n" + 
					"where CL.CODE_TYPE = 'PAY_METHOD'\n" + 
					"order by DECODE(CL.CODE_ID, 'NP', 1, 'FA', 2, 'OP', 3, CL.CODE_ID) asc";

	public List<CodeLkupDTO> getPayMethodCodeLkup() throws DAOException {
		logger.info("getPayMethodCodeLkup is called");
		List<CodeLkupDTO> itemList = new ArrayList<CodeLkupDTO>();

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
			logger.debug("getPayMethodCodeLkup():"
					+ getCodeLkupDTOAllSQLDesc);

			itemList = simpleJdbcTemplate.query(getPayMethodCodeLkup, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getPayMethodCodeLkup()");
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getPayMethodCodeLkup():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}

	public List<CodeLkupDTO> findActiveReasonCodeLkupByType(String type, Date appDate) throws DAOException {
		String sql = "select REASON_TYPE, REASON_CD, REASON_DESC\n"
				+ "from W_WAIVE_LKUP\n"
				+ "where REASON_TYPE = :V_REASON_TYPE\n"
				+ "and TRUNC(NVL(:V_APP_DATE, sysdate)) between TRUNC(START_DATE) and\n"
				+ "      TRUNC(NVL(END_DATE, sysdate))\n"
				+ "order by REASON_DESC, REASON_CD";

		ParameterizedRowMapper<CodeLkupDTO> mapper = new ParameterizedRowMapper<CodeLkupDTO>() {
			public CodeLkupDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				CodeLkupDTO dto = new CodeLkupDTO();
				dto.setCodeType(rs.getString("REASON_TYPE"));
				dto.setCodeId(rs.getString("REASON_CD"));
				dto.setCodeDesc(rs.getString("REASON_DESC"));
				return dto;
			}
		};

		try {
			logger.debug("findActiveReasonCodeLkupByType() @ CodeLkupDAO:" + sql);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("V_REASON_TYPE", type);
			params.addValue("V_APP_DATE", appDate);

			return simpleJdbcTemplate.query(sql, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in findActiveReasonCodeLkupByType()");
		} catch (Exception e) {
			logger.info("Exception caught in findActiveReasonCodeLkupByType():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;
	}
	
	
	public List<CodeLkupDTO> findAttbLkup( String locale) throws DAOException {
		String sql = 
				"select 'ATTB_LKUP' CODE_TYPE\n" +
						"      ,AL.ATTB_ID CODE_ID\n" + 
						"      ,DL.DESCRIPTION ATTB_DESC\n" + 
						"      ,AL.DESCRIPTION DESP_DESC\n" + 
						"      ,NVL(DL.DESCRIPTION, AL.DESCRIPTION) CODE_DESC\n" + 
						"from W_ATTB_LKUP AL\n" + 
						"left join W_DISPLAY_LKUP DL\n" + 
						"on (AL.ATTB_ID = DL.ID and AL.LOCALE = DL.LOCALE and DL.TYPE = 'ATTB_DESC')\n" + 
						"where AL.LOCALE = :locale";


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
			logger.debug("findAttbLkup() @ CodeLkupDTO:" + sql);
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			params.addValue("locale", locale);
			

			return simpleJdbcTemplate.query(sql, mapper,params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in findAttbLkup()");
		} catch (Exception e) {
			logger.info("Exception caught in findAttbLkup():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;

	}
	
	public List<CodeLkupDTO> findAttbInfoLkup( String locale) throws DAOException {
		String sql ="select 'ATTB_INFO_DIC' CODE_TYPE\n" +
				"      ,AL.ATTB_ID || 'ZZZ' || AID.ATTB_VALUE CODE_ID\n" + 
				"      ,AID.ATTB_VALUE_DESC CODE_DESC\n" + 
				"from W_ATTB_LKUP AL, W_ATTB_INFO_DIC AID\n" + 
				"where AL.ATTB_INFO_KEY = AID.ATTB_INFO_KEY\n" + 
				"and AL.LOCALE = AID.LOCALE\n" + 
				"and AL.LOCALE = :locale"; 
				


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
			logger.debug("findAttbInfoLkup() @ CodeLkupDTO:" + sql);
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			params.addValue("locale", locale);
			

			return simpleJdbcTemplate.query(sql, mapper,params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in findAttbInfoLkup()");
		} catch (Exception e) {
			logger.info("Exception caught in findAttbInfoLkup():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;

	}
	
	public List<CodeLkupDTO> findCodeLkupByType(String type) throws DAOException {
		String sql = "SELECT \n" + "     CODE_TYPE,\n" + "     CODE_ID,\n"
				+ "     CODE_DESC,\n" + "     CREATE_BY,\n"
				+ "     CREATE_DATE,\n" + "     LAST_UPD_BY,\n"
				+ "     LAST_UPD_DATE\n" + " from BOMWEB_CODE_LKUP "
				+ " where CODE_TYPE=:codeType "
				+ " order by CODE_TYPE, CODE_ID\n"; 

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
			logger.debug("findCodeLkupByType() @ CodeLkupDAO:" + sql);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("codeType", type);

			return simpleJdbcTemplate.query(sql, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in findCodeLkupByType()");
		} catch (Exception e) {
			logger.info("Exception caught in findCodeLkupByType():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;

	}
	
}