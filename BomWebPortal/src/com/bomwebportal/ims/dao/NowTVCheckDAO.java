package com.bomwebportal.ims.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.BasketDetailsDTO;
import com.bomwebportal.ims.dto.NowTVCheckDTO;


public class NowTVCheckDAO extends BaseDAO{
protected final Log logger = LogFactory.getLog(getClass());
	
	public List<NowTVCheckDTO> getNowTVCheckList (String Serbdyno, String tech)throws DAOException {
		List<NowTVCheckDTO> NowTVCheckList = new ArrayList<NowTVCheckDTO>();

		String SQL=


			/*"SELECT NVL(MAX_BB, '0') MAX_BB,					\n"+
			"(SELECT DECODE(COUNT(*),0,'N','Y') FROM B_SERVICE_ROLLOUT SR2 WHERE SR2.SERBDYNO=V_SERBDYNO AND \n"+
			"		NVL(SR2.OBSOLETE,'N')='N' AND \n"+
			"		SR2.SERVICE_CODE IN (SELECT L.BOM_CODE FROM B_LOOKUP L WHERE L.BOM_GRP_ID='SBIMS_NTV_SRV_CD')) NTV_SRV_CD_FOUND   \n"+
			"		FROM				\n"+
			"		(					\n"+
			"				SELECT MAX(BANDWIDTH) MAX_BB,SR.SERBDYNO V_SERBDYNO	\n"+
			"				FROM B_SERVICE_ROLLOUT SR, B_SRVCODE_LKUP SL 				\n"+
			"				WHERE SR.SERBDYNO=?				\n"+
			"				AND SR.SERVICE_CODE=SL.SERVICE_CODE				\n"+
			"				AND NVL(SR.OBSOLETE,'N')='N'				\n"+
			"					AND SL.DOMAIN_TYPE='P' AND SL.SPRINGBOARD_IMS_ACQ='Y' GROUP BY SR.SERBDYNO				\n"+
			"		) TEMP1					";*/
			"select max_bb, ntv_srv_cd_found from ( 					"+
			"select max_bb, 'Y' ntv_srv_cd_found,             "+
			"decode(pon, 'Y', 'PON',                          "+
			"decode(vector, 'Y', 'Vectoring',                       "+
			"decode(vdsl1, 'Y', 'VDSL',                       "+
			"decode(vdsl2, 'Y', 'VDSL', 'ADSL'          "+
			")))) tech                                        "+   
			"from b_service_rollout sr, b_srvcode_lkup sl 		"+		  
			"where sr.serbdyno= ?				                      "+
			"and sr.service_code=sl.service_code				      "+      
			"and nvl(sr.obsolete,'N')='N'				              "+      
			"and sl.domain_type='P' 		                      "+
			"and sdtv= 'Y'                                    "+
			") where tech = ?                                 ";
//        "select max_bb, sdtv ntv_srv_cd_found                   "+
//        "from b_srvcode_lkup b, b_service_rollout a     "+
//        "where a.service_code = b.service_code          "+
//        "and a.serbdyno =  ?                            "+
//        "and b.sdtv = 'Y'                               "; 

		
		ParameterizedRowMapper<NowTVCheckDTO> mapper = new ParameterizedRowMapper<NowTVCheckDTO>() {
	        public NowTVCheckDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	NowTVCheckDTO NowTVCheck = new NowTVCheckDTO();
	        	NowTVCheck.setMax_BB(rs.getString("MAX_BB"));
	        	NowTVCheck.setNTV_srv_cd_found(rs.getString("NTV_SRV_CD_FOUND"));
	            return NowTVCheck;
	        }
	    };

		try {
			logger.debug("getNowTVCheckList @ NowTVCheckDAO: " + SQL);
			NowTVCheckList = simpleJdbcTemplate.query(SQL, mapper,Serbdyno, tech); 
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			NowTVCheckList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getNowTVCheckList():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return NowTVCheckList;
	}
	//Celia 20150731
	public List<String> getPayChannelList(String planCdStr){
		List<String> rtnList = new ArrayList<String>();
		
		String SQL = "select distinct plancode from vi_plan_sponsor where fintxncd in ('VISP0001','VISP0024') and plancode in ("+planCdStr+")";  
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String aa = rs.getString("plancode");
				return aa;
			}
		};
		
		rtnList  = simpleJdbcTemplate.query(SQL, mapper);
		
		
		return rtnList;
	}
	public List<String> getEntChannelList(String campaignCdStr){
		List<String> rtnList = new ArrayList<String>();
		
		String SQL = "select distinct campaign_code from VI_HARD_BUNDLE_CAMPAIGN where campaign_code in ("+campaignCdStr+")"; 
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String aa = rs.getString("campaign_code");
				return aa;
			}
		};
		
		rtnList  = simpleJdbcTemplate.query(SQL, mapper);
		
		
		return rtnList;
	}	
	public Map<String,List<String>> getSB_HD_BUNDLE_PLAN() throws DAOException {
		Map<String,List<String>> sbHdBundlePlan = new HashMap<String, List<String>>();

		String SQL =
		"select CODE_DESC description "+ 
		"from bomweb_code_lkup "+
		"where CODE_TYPE='SB_HD_BUNDLE_PLAN'";

		try {
			List<Map<String, Object>> rows = simpleJdbcTemplate.queryForList(SQL);
			for (Map row : rows) {
				String[] s = ((String)row.get("description")).split("___");
				if(s!=null && s.length==2){
					if(sbHdBundlePlan.get(s[0])==null){
						List<String> sL= new ArrayList<String>();
						sL.add(s[1]);
						sbHdBundlePlan.put(s[0],sL);
					}
					else{
						List<String> sL=sbHdBundlePlan.get(s[0]);
						sL.add(s[1]);
						sbHdBundlePlan.put(s[0],sL);
					}
				}
			}
		
		}catch (Exception e) {
			 e.printStackTrace();
		}
		return sbHdBundlePlan;
	}
			
}
