package com.pccw.util.db.stringOracleType;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.DaoHelper;
import com.pccw.util.db.OracleSelectHelper;
import com.pccw.util.db.DaoHelper.DaoProperty;
import com.pccw.util.db.DaoHelper.OracleColumn;

public class OraNumberInsertValueFromSelectMax extends OraNumberInsertValueFromSelect {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8432270629970362060L;

	private String selectMaxFieldName;

	private String selectMaxTableName;

	private String[] whereBindingField;
	
	public OraNumberInsertValueFromSelectMax(String pSelectMaxFieldName,
			String pSelectMaxTableName, String[] pWhereBindingField) {
		super(null);
		this.selectMaxFieldName = pSelectMaxFieldName;
		this.selectMaxTableName = pSelectMaxTableName;
		this.whereBindingField = pWhereBindingField;
	}

	public String[] getWhereBindingField() {
		return this.whereBindingField;
	}

	@Override
	public String getInsertValue(DaoBaseImpl pDao) throws Exception {
		Map<String, DaoProperty> daoPropertyMap = pDao.getPropertyMap();
		DaoProperty selectDaoProperty = DaoHelper.getDaoProperty(daoPropertyMap, selectMaxFieldName);
		String maxDbColumnName = this.selectMaxFieldName;
		if (selectDaoProperty != null) {
			maxDbColumnName = DaoHelper.getOracleColumn(pDao, selectDaoProperty).getOracleFieldName();
		}
		StringBuilder sql = new StringBuilder("SELECT TO_CHAR(NVL(MAX(")
				.append(maxDbColumnName).append("), 0) + 1)").append(
						'"' + this.selectMaxFieldName + '"').append(" FROM ")
				.append(this.selectMaxTableName);
		ArrayList<String> bindingValueList = new ArrayList<String>();
		if (!ArrayUtils.isEmpty(this.whereBindingField)) {
			sql.append(" WHERE ");
			
			OracleColumn whereColumn = DaoHelper.getOracleColumn(pDao, DaoHelper.getDaoProperty(daoPropertyMap, this.whereBindingField[0]));
			sql.append(whereColumn.getOracleFieldName());
			sql.append(" = ");
			sql.append(whereColumn.getOracleInsUpdValueClause());
			if (whereColumn.getOracleInsUpdValueClause().indexOf("?") != -1) {
				bindingValueList.add(whereColumn.getColumnValue());
			}
			
			for (int i = 1; i < this.whereBindingField.length; i++) {
				sql.append(" AND ");
				whereColumn = DaoHelper.getOracleColumn(pDao, DaoHelper.getDaoProperty(daoPropertyMap, this.whereBindingField[i]));
				sql.append(whereColumn.getOracleFieldName());
				sql.append(" = ");
				sql.append(whereColumn.getOracleInsUpdValueClause());
				if (whereColumn.getOracleInsUpdValueClause().indexOf("?") != -1) {
					bindingValueList.add(whereColumn.getColumnValue());
				}
			}
		}
		return OracleSelectHelper.getSqlFirstRowColumnString(
				pDao.getDataSource(), 
				sql.toString(), bindingValueList.toArray());
	}
}