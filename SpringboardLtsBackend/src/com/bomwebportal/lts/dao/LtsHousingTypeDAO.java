package com.bomwebportal.lts.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.LtsHousingTypeDTO;

public class LtsHousingTypeDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());
	
	public AddressRolloutDTO getLtsHousingType(AddressRolloutDTO pRolloutResult, String pSbNum) throws DAOException{
		
		AddressRolloutDTO result = new AddressRolloutDTO();
		
		StringBuffer sb = new StringBuffer();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		MapSqlParameterSource paramSource1 = new MapSqlParameterSource();
		StringBuffer sb1 = new StringBuffer();
		List<LtsHousingTypeDTO> tempLtsHousingTypeList = new ArrayList<LtsHousingTypeDTO>();
		LtsHousingTypeDTO tempLtsHousingType = new LtsHousingTypeDTO();
		
		boolean noResult1 = false;
		boolean noResult2 = false;
		boolean noResult3 = false;
        
		MapSqlParameterSource paramSource2 = new MapSqlParameterSource();
		StringBuffer sb2 = new StringBuffer();
        
		MapSqlParameterSource paramSource3 = new MapSqlParameterSource();
		StringBuffer sb3 = new StringBuffer();	
		
		String ltsHousingCatCd = "";
		String ltsHousingCatDesc = "";
		String ltsHousingTypeDesc = "";

		try {
			
			sb1.append("select HOUSING_TYPE_CD, HOUSING_TYPE_DESC ");
			sb1.append("from B_LTS_HOUSING_TYPE ");
			sb1.append("where (srvbdry_num = ltrim(:srvbdry_num1, '0') or srvbdry_num = LPAD(:srvbdry_num2, 6, '0')) ");
			
			paramSource1.addValue("srvbdry_num1", pSbNum);
			paramSource1.addValue("srvbdry_num2", pSbNum);
			
			ParameterizedRowMapper<LtsHousingTypeDTO> mapper1 = new ParameterizedRowMapper<LtsHousingTypeDTO>() {
				public LtsHousingTypeDTO mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					LtsHousingTypeDTO dto = new LtsHousingTypeDTO();
					dto.setLtsHousingTypeCd(rs.getString("HOUSING_TYPE_CD"));
					dto.setLtsHousingTypeDesc(rs.getString("HOUSING_TYPE_DESC"));
					return dto;
				}
			};
		
			sb = sb1;
			paramSource = paramSource1;
			
			tempLtsHousingTypeList = simpleJdbcTemplate.query(sb1.toString(), mapper1, paramSource1);
			if(tempLtsHousingTypeList != null)
			{
				if(tempLtsHousingTypeList.size() > 0)
				{
					tempLtsHousingType = tempLtsHousingTypeList.get(0);
				}
				else
				{
					noResult1 = true;
				}
			}
			else
			{
				noResult1 = true;
			}
			
			if(noResult1)
			{
				sb2.append("select HOUSING_TYPE_CD, HOUSING_TYPE_DESC ");
				sb2.append("from B_LTS_HOUSING_TYPE ");
				sb2.append("where LPAD(srvbdry_num, 6, '0') in (select LPAD(primary_sb, 6, '0') ");
				sb2.append("          from B_SB_RELATED_SB where related_sb = LPAD(:srvbdry_num, 6, '0') and obsolete = 'N') ");
				
				paramSource2.addValue("srvbdry_num", pSbNum);
				
				sb = sb2;
				paramSource = paramSource2;
				
				tempLtsHousingTypeList = simpleJdbcTemplate.query(sb2.toString(), mapper1, paramSource2);
				if(tempLtsHousingTypeList != null)
				{
					if(tempLtsHousingTypeList.size() > 0)
					{
						tempLtsHousingType = tempLtsHousingTypeList.get(0);
					}
					else
					{
						noResult2 = true;
					}
				}
				else
				{
					noResult2 = true;
				}
			}
			
			if(noResult2)
			{
				result = pRolloutResult;
				result.setLtsHousingCatDesc("Not yet defined");
				result.setLtsHousingCatCd("NOT_YET_DEFINED");
				result.setLtsHousingType("");
				
				return result;
			}
					
			sb3.append("select HOUSING_CAT_CD, HOUSING_CAT_DESC ");
			sb3.append("from B_LTS_HOUSING_CAT ");
			sb3.append("where HOUSING_TYPE_CODE = :housingType ");
			sb3.append("and EFF_START_DATE <= sysdate ");
			
			paramSource3.addValue("housingType", tempLtsHousingType.getLtsHousingTypeCd());
			
			sb = sb3;
			paramSource = paramSource3;
			
			ParameterizedRowMapper<LtsHousingTypeDTO> mapper3 = new ParameterizedRowMapper<LtsHousingTypeDTO>() {
				public LtsHousingTypeDTO mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					LtsHousingTypeDTO dto = new LtsHousingTypeDTO();
					dto.setLtsHousingCatCd(rs.getString("HOUSING_CAT_CD"));
					dto.setLtsHousingCatDesc(rs.getString("HOUSING_CAT_DESC"));
					return dto;
				}
			};
			
			tempLtsHousingTypeList = simpleJdbcTemplate.query(sb3.toString(), mapper3, paramSource3);
			if(tempLtsHousingTypeList != null)
			{
				if(tempLtsHousingTypeList.size() > 0)
				{
					tempLtsHousingType.setLtsHousingCatCd(tempLtsHousingTypeList.get(0).getLtsHousingCatCd());
					tempLtsHousingType.setLtsHousingCatDesc(tempLtsHousingTypeList.get(0).getLtsHousingCatDesc());
				}
				else
				{
					noResult3 = true;
				}
			}
			else
			{
				noResult3 = true;
			}
			
			if(noResult3){
				result = pRolloutResult;
				result.setLtsHousingCatDesc("Not yet defined");
				result.setLtsHousingCatCd("NOT_YET_DEFINED");
				result.setLtsHousingType("");
				
				return result;
			}
			else
			{
				result = pRolloutResult;
				result.setLtsHousingCatDesc(tempLtsHousingType.getLtsHousingCatDesc());
				result.setLtsHousingCatCd(tempLtsHousingType.getLtsHousingCatCd());
				result.setLtsHousingType(tempLtsHousingType.getLtsHousingTypeDesc());
				return result;
			}			

		} catch (EmptyResultDataAccessException erdae) {
			logger.error("Exception caught in getLtsHousingType()\n - sql: " + sb.toString() + "\n - value:" + paramSource.getValues(), erdae);
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getLtsHousingType()\n - sql: " + sb.toString() + "\n - value:" + paramSource.getValues(), e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
