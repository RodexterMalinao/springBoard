package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.EligibilityDTO;
import com.bomwebportal.exception.DAOException;

public class ServiceSelectionDAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	//getBasketFilterList
	public List<String[]> getSelectList (String locale, String type)throws DAOException {
		List<String[]> selectList = new ArrayList<String[]>();
		String SQL ="";

			SQL ="SELECT id , description, type "
				  +" FROM w_display_lkup "
				  +" WHERE type = ? "//'CUSTOMER_TIER', 'BASKET_TYPE', 'RP_TYPE'
				  +" AND locale = ? ";

	        ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
			    
		      	    public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
	 	        	String[] brand = new String[3];
	 	        	brand[0]=rs.getString("id");
	 	        	brand[1]=rs.getString("description");
	 	        	brand[2]=rs.getString("type");
	 	            return brand;
	 	        }
		    };

			try {
				//herbert 20111110 - remove useless SQL logger
				logger.debug("getCustomerTierList @ ServiceSelectionDAO: " + SQL);
				selectList = simpleJdbcTemplate.query(SQL,mapper,type, locale);

			} catch (EmptyResultDataAccessException erdae) {
				logger.info("EmptyResultDataAccessException");
				selectList = null;
			} catch (Exception e) {
				logger.error("Exception caught in getCustomerTierList()", e);
				throw new DAOException(e.getMessage(), e);
			}
			return selectList;

	}
	
	public List<String[]> getCallList (String appDate, String username) throws DAOException {
		List<String[]> callList = new ArrayList<String[]>();
		
		/*String sql = "SELECT distinct J.JOB_LIST, J.JOB_LIST_DESC " +
					 "from BOMWEB_JOBLIST_TEAM_ASSGN JTA, " +
					 "BOMWEB_SALES_ASSIGNMENT SA, " +
					 "BOMWEB_JOBLIST J " +
					 "where SA.STAFF_ID = ? " +
					 "and JTA.JOB_LIST = J.JOB_LIST " + 
					 "and to_date(?, 'DD/MM/YYYY') between JTA.START_DATE and NVL(JTA.END_DATE, sysdate) " + 
					 "and JTA.TEAM_CD = SA.TEAM_CD and JTA.CENTRE_CD = SA.CENTRE_CD and JTA.CHANNEL_CD = SA.CHANNEL_CD " + 
					 "and to_date(?, 'DD/MM/YYYY') between SA.START_DATE and NVL(SA.END_DATE, sysdate) ";*/
		String sql=
			"select distinct J.JOB_LIST, J.JOB_LIST_DESC\n" +
			"    from BOMWEB_JOBLIST_TEAM_ASSGN JTA,\n" + 
			"         BOMWEB_SALES_ASSIGNMENT   SA,\n" + 
			"         BOMWEB_JOBLIST            J\n" + 
			"   where SA.STAFF_ID = ?\n" + 
			"     and JTA.JOB_LIST = J.JOB_LIST\n" + 
			"     and TO_DATE(?, 'DD/MM/YYYY') between JTA.START_DATE and\n" + 
			"         NVL(JTA.END_DATE, sysdate)\n" + 
			"     and DECODE(JTA.TEAM_CD, 'ALL', SA.TEAM_CD, JTA.TEAM_CD) = SA.TEAM_CD\n" + 
			"     and DECODE(JTA.CENTRE_CD, 'ALL', SA.CENTRE_CD, JTA.CENTRE_CD) = SA.CENTRE_CD\n" + 
			"     and JTA.CHANNEL_CD = SA.CHANNEL_CD\n" + 
			"     and TO_DATE(?, 'DD/MM/YYYY') between SA.START_DATE and NVL(SA.END_DATE, sysdate) " ;
			//"     and TO_DATE(?, 'DD/MM/YYYY') between J.START_DATE and NVL(J.END_DATE, sysdate)";
		
		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
		    
      	    public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	String[] params = new String[2];
	        	params[0]=rs.getString("job_list");
	        	params[1]=rs.getString("job_list_desc");
	            return params;
	        }
		};
		try {
			logger.debug("getCallList @ ServiceSelectionDAO: " + sql);
			callList = simpleJdbcTemplate.query(sql,mapper,username, appDate, appDate);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			callList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getCallList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return callList;
	}

	public List<String[]> getRatePlan2 (String jobList, String appDate) throws DAOException {
		List<String[]> ratePlanList = new ArrayList<String[]>();
		
		String sql = "select distinct B.BASKET_TYPE, B.BASKET_TYPE_ID, A.RP_TYPE, A.RP_TYPE_ID " + 
				 "from (select distinct BASKET_ID, PARM_TYPE_VAL RP_TYPE, PARM_TYPE_ID  RP_TYPE_ID " +
				 "from W_BASKET_PARM " +
				 "where BASKET_ID in " + 
				 "(select distinct BASKET_ID " +
				 "from BOMWEB_JOBLIST_BASKET_ASSGN " +
				 "where JOB_LIST = ? and to_date(?, 'DD/MM/YYYY') between START_DATE and NVL(END_DATE, sysdate)) " +
				 "and PARM_TYPE in ('RP_TYPE')) A, " + 
				 "(select distinct BASKET_ID, PARM_TYPE_VAL BASKET_TYPE, PARM_TYPE_ID  BASKET_TYPE_ID " +
				 "from W_BASKET_PARM where BASKET_ID in " +
				 "(select distinct BASKET_ID " +
				 "from BOMWEB_JOBLIST_BASKET_ASSGN " +
				 "where JOB_LIST = ? " +
				 "and to_date(?, 'DD/MM/YYYY') between START_DATE and NVL(END_DATE, sysdate)) " +
				 "and PARM_TYPE in ('BASKET_TYPE')) B " +
				 "where A.BASKET_ID = B.BASKET_ID";
		           
		
		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
		    
      	    public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	String[] params = new String[4];
	        	params[0]=rs.getString("basket_type");
	        	params[1]=rs.getString("basket_type_id");
	        	params[2]=rs.getString("rp_type");
	        	params[3]=rs.getString("rp_type_id");
	            return params;
	        }
		};
		try {
			logger.debug("getRatePlan2 @ ServiceSelectionDAO: " + sql);
			ratePlanList = simpleJdbcTemplate.query(sql,mapper, jobList, appDate, jobList, appDate);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			ratePlanList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getRatePlan2()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return ratePlanList;
	}
	
	public List<String[]> getEligibilityCustomerTierList (String locale, List<EligibilityDTO> eligibilityDto, int channelId)throws DAOException {
		List<String[]> selectList = new ArrayList<String[]>();

//convert input eligibilityDto list to  sql 

			StringBuffer SQL = new StringBuffer("select DL.ID, DL.DESCRIPTION ");
			SQL.append("from W_CUSTOMER_TIER_LKUP CTL, W_DISPLAY_LKUP DL ");
			SQL.append("where CTL.CUSTOMER_TIER_ID = DL.ID ");
			SQL.append("and DL.TYPE = 'CUSTOMER_TIER' ");
			SQL.append("and CTL.CHANNEL_ID = ? ");
			SQL.append("and CTL.DEFAULT_IND = 'Y' ");
			
			
			if (eligibilityDto != null && !eligibilityDto.isEmpty()) {
				SQL.append("union ");
				SQL.append("select DL.ID, DL.DESCRIPTION ");
				SQL.append("from W_DISPLAY_LKUP DL ");
				SQL.append("where DL.TYPE = 'CUSTOMER_TIER' ");
				SQL.append("and DL.ID in (");
				
				for (int i = 0; i < eligibilityDto.size(); i++) {
					
					if (eligibilityDto.size() == 1 || eligibilityDto.size() == i + 1) {
						SQL.append("'" + eligibilityDto.get(i).getCustomerTierId() + "'");
					} else {
						SQL.append("'" + eligibilityDto.get(i).getCustomerTierId() + "',");
					}
				}
				
				SQL.append(")");
			}
			

	        ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
	        	public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
	 	        	String[] brand = new String[2];
	 	        	brand[0]=rs.getString("id");
	 	        	brand[1]=rs.getString("description");
	 	        
	 	            return brand;
	 	        }
		    };

			try {
				logger.info("getCustomerTierList @ ServiceSelectionDAO: " );
				logger.debug("getCustomerTierList @ ServiceSelectionDAO: " + SQL.toString());
				selectList = simpleJdbcTemplate.query(SQL.toString(),mapper, channelId);

			} catch (EmptyResultDataAccessException erdae) {
				logger.info("EmptyResultDataAccessException");
				selectList = null;
			} catch (Exception e) {
				logger.error("Exception caught in getEligibilityCustomerTierList()", e);
				throw new DAOException(e.getMessage(), e);
			}
			return selectList;

	}
	
	//add by wilson 20120309
	public String getChannelBasketStatus(String channelId, String basketId, String appDate) throws DAOException {
		String status = "";

		String SQL = 
			"select DECODE(count(*), 0, 'N', 'Y') STATUS\n" +
			"  from W_CHANNEL_BASKET_ASSGN CBA\n" + 
			" where TO_DATE(?, 'DD/MM/YYYY') between CBA.EFF_START_DATE and\n" + 
			"       NVL(CBA.EFF_END_DATE, sysdate)\n" + 
			"   and CBA.CHANNEL_ID = ?\n" + 
			"   and CBA.BASKET_ID = ?";

		try {
			logger.debug("getChannelBasketStatus @ VasDetailDAO: " + SQL);
			status = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class,appDate, channelId, basketId );

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			status = "";
		} catch (Exception e) {
			logger.error("Exception caught in getChannelBasketStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return status;

	}
	
	public List<String[]> getEligibilityCustomerTierListByPeriorty (String locale, List<EligibilityDTO> eligibilityDto, int channelId)throws DAOException {
		List<String[]> selectList = new ArrayList<String[]>();

//convert input eligibilityDto list to  sql 

			StringBuffer SQL_default = new StringBuffer("select DL.ID, DL.DESCRIPTION ");
			SQL_default.append("from W_CUSTOMER_TIER_LKUP CTL, W_DISPLAY_LKUP DL ");
			SQL_default.append("where CTL.CUSTOMER_TIER_ID = DL.ID ");
			SQL_default.append("and DL.TYPE = 'CUSTOMER_TIER' ");
			SQL_default.append("and CTL.CHANNEL_ID = ? ");
			SQL_default.append("and CTL.DEFAULT_IND = 'Y' ");
			
			
			String SQL="";
			if (eligibilityDto != null && !eligibilityDto.isEmpty()) {
				

				String  sub_SQL="";
				for (int i = 0; i < eligibilityDto.size(); i++) {
					
					if (eligibilityDto.size() == 1 || eligibilityDto.size() == i + 1) {
						sub_SQL+="'" + eligibilityDto.get(i).getCustomerTierId() + "'";
					} else {
						sub_SQL+="'" + eligibilityDto.get(i).getCustomerTierId() + "',";
					}
				}
				
				sub_SQL+=")";
				
				
				SQL="select DL.ID, DL.DESCRIPTION\n" +
				"  from (select CTL.CUSTOMER_TIER_ID, CTL.PRIORITY\n" + 
				"          from W_CUSTOMER_TIER_LKUP CTL\n" + 
				"         where CTL.CHANNEL_ID = ?\n" + 
				"           and CTL.DEFAULT_IND = 'Y'\n" + 
				"        union\n" + 
				"        select CTL.CUSTOMER_TIER_ID, CTL.PRIORITY\n" + 
				"          from W_CUSTOMER_TIER_LKUP CTL\n" + 
				"         where CTL.CHANNEL_ID = ?\n" + 
				"           and CTL.PRIORITY <= nvl((select max(C.PRIORITY)\n" + 
				"                                  from W_CUSTOMER_TIER_LKUP C\n" + 
				"                                 where C.CUSTOMER_TIER_ID in (" + sub_SQL +" \n"+
				"                                   and C.CHANNEL_ID = ?\n" + 
				"                                ),0)) A,\n" + 
				"       W_DISPLAY_LKUP DL\n" + 
				" where A.CUSTOMER_TIER_ID = DL.ID\n" + 
				"   and DL.TYPE = 'CUSTOMER_TIER'\n" + 
				" order by A.PRIORITY asc, DL.DESCRIPTION asc";
			}
			

	        ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
	        	public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
	 	        	String[] tier = new String[2];
	 	        	tier[0]=rs.getString("id");
	 	        	tier[1]=rs.getString("description");
	 	        
	 	            return tier;
	 	        }
		    };

			try {
				logger.debug("getEligibilityCustomerTierListByPeriorty @ ServiceSelectionDAO: " + SQL.toString());
				if ("".equals(SQL)){
					selectList = simpleJdbcTemplate.query(SQL_default.toString(),mapper, channelId);
				}else{
					selectList = simpleJdbcTemplate.query(SQL,mapper, channelId, channelId, channelId);
				}

			} catch (EmptyResultDataAccessException erdae) {
				logger.info("EmptyResultDataAccessException");
				selectList = null;
			} catch (Exception e) {
				logger.error("Exception caught in getEligibilityCustomerTierListByPeriorty()", e);
				throw new DAOException(e.getMessage(), e);
			}
			return selectList;

	}

	
}


