package com.pccw.util.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.OracleSelectHelper;

public class ParameterizedRowMapperByFieldName<T> implements
		ParameterizedRowMapper<T> {

	private static TreeMap<String, ArrayList<String>> dtoPropertyMap = new TreeMap<String, ArrayList<String>>();
	
	private Class<T> elementClass;
	
	public ParameterizedRowMapperByFieldName (Class<T> pClass) {
		this.elementClass = pClass;	
	}
	
	@Override
	public T mapRow(ResultSet pRs, int pRowNum) throws SQLException {
		T rowObj = null;
		try {
			
			boolean isDao = false;

			try {
				isDao = (this.elementClass.newInstance() instanceof DaoBaseImpl);
			} catch (Exception ignore) {
			}

			ArrayList<String> dtoPropertyMappingList = null;
			
			if (isDao) {
				dtoPropertyMappingList = dtoPropertyMap.get(this.elementClass.toString());
			}
			
			if (dtoPropertyMappingList == null) {
				dtoPropertyMappingList = OracleSelectHelper.getColumnPropertyMappingList(pRs.getMetaData(), this.elementClass);
				if (isDao) {
					dtoPropertyMap.put(this.elementClass.toString(), dtoPropertyMappingList);
				}
			}
			
			rowObj = this.elementClass.newInstance();
            OracleSelectHelper.getResultSetRowObject(dtoPropertyMappingList, pRs, pRs.getMetaData(), rowObj);
		} catch (Exception e) {
			throw new SQLException(e);
		}
		
		return rowObj;
	}


}
