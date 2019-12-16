package com.pccw.util.db;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;

import com.pccw.util.db.stringOracleType.OraBLOB;
import com.pccw.util.db.stringOracleType.OraCLOB;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateTimestamp;
import com.pccw.util.db.stringOracleType.OraNumber;
import com.pccw.util.db.stringOracleType.OraNumberInsertValueFromSelect;
import com.pccw.util.spring.SpringApplicationContext;

/**
 * <h4>Purpose:</h4>
 * <blockquote>
 * <p>
 * 
 * </p>
 * </blockquote>
 * <p>
 * <b>Version: </b> 1.0
 * </p>
 * <p>
 * <b>System Name: </b> BOM
 * </p>
 * <p>
 * <b>Module Name: bom.util.db.DaoHelper </b>
 * </p>
 * <p>
 * <b>Author: Raymond Wong KH </b>
 * </p>
 * <p>
 * <b>Created Date: </b>
 * </p>
 * <h4>Change Log:</h4>
 * <blockquote><table width="100%" border="0" cellspacing="0" cellpadding="0">
 * <tr>
 * <td width="20%"><b>Date </b></td>
 * <td width="30%"><b>Modify By </b></td>
 * <td width="50%"><b>Description </b></td>
 * </tr>
 * <tr>
 * <td>Oct 26, 2005</td>
 * <td>Raymond Wong KH</td>
 * <td>Create</td>
 * </tr>
 * </table> </blockquote>
 */

public class DaoHelper {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DaoHelper.class);
	
	private static final String ORACLE_SELECT_CLAUSE = "OracleSelectClause";
	
	public static final String ORACLE_FIELD_NAME = "OracleFieldName";
	
	

	@SuppressWarnings("rawtypes")
	private static HashMap<Class, Map<String, DaoProperty>> classPropertyMap = new HashMap<Class, Map<String, DaoProperty>>();

	/**
	 * Converts a Hungarian notation string to a Column name e.g. BomCode -->
	 * BOM_CODE
	 * 
	 * @param pHungarianName
	 *            the attribute name in Hungarian notation that will be
	 *            converted to a column name
	 * @return the column name
	 */
	public static String hungarianToOracleName(String pHungarianName) {
		char[] c = pHungarianName.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < c.length; i++) {
			if (Character.isUpperCase(c[i])) {
				if (i > 0) {
					sb.append('_');
				}
			}
			sb.append(Character.toUpperCase(c[i]));
		}
		return sb.toString();
	}

	public synchronized static Map<String, DaoProperty> getPropertyMap(@SuppressWarnings("rawtypes") Class pClass) {
		Map<String, DaoProperty> propertyMap;
		synchronized (classPropertyMap) {
			propertyMap = classPropertyMap.get(pClass);
			if (propertyMap != null) {
				return propertyMap;
			}
		}
		propertyMap = new TreeMap<String, DaoProperty>();

		Field[] daoFields = getAllFields(pClass, null).toArray(new Field[0]);
		
		DaoProperty daoProperty = null;
		for (int i = 0; i < daoFields.length; i++) {
			if ("class$0".equalsIgnoreCase(daoFields[i].getName())
					|| "serialVersionUID".equalsIgnoreCase(daoFields[i].getName())
					) {
				continue;
			}
			daoProperty = new DaoProperty(pClass, daoFields[i].getName(), 
								hungarianToOracleName(daoFields[i].getName()),
								daoFields[i].getType());
			propertyMap.put(daoFields[i].getName(), daoProperty);
		}
		synchronized (classPropertyMap) {
			classPropertyMap.put(pClass, propertyMap);
		}
		return propertyMap;
	}
	
	
	private static ArrayList<Field> getAllFields(@SuppressWarnings("rawtypes") Class pClass, ArrayList<Field> pAllFields){
		ArrayList<Field> allFields = pAllFields;
		if (allFields == null){
			allFields = new ArrayList<Field>(); 
		}
		Field[] declaredFields = pClass.getDeclaredFields();
		if (ArrayUtils.isNotEmpty(declaredFields)){
			allFields.addAll(Arrays.asList(declaredFields));
		}		
		if (pClass.getSuperclass() != null && !pClass.getSuperclass().equals(DaoBaseImpl.class)){
			getAllFields(pClass.getSuperclass(), allFields);
		}
		
		return allFields;
	}

	public static String getClobFieldName(Map<String, DaoProperty> pDaoPropertyMap) {
		for (Entry<String, DaoProperty> entry : pDaoPropertyMap.entrySet()) {
			if (entry.getValue().isClob()) {
				return entry.getKey();
			}
		}
		return null;
	}

	public static String getBlobFieldName(Map<String, DaoProperty> pDaoPropertyMap) {
		for (Entry<String, DaoProperty> entry : pDaoPropertyMap.entrySet()) {
			if (entry.getValue().isBlob()) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	public static DaoProperty getDaoProperty(Map<String, DaoProperty> pDaoPropertyMap,
			String pPropertyName) {
		DaoProperty daoProperty = pDaoPropertyMap.get(pPropertyName);
		if (daoProperty == null
				&& Character.isUpperCase(pPropertyName.charAt(0))) {
			pPropertyName = pPropertyName.substring(0, 1).toLowerCase()
					+ pPropertyName.substring(1);
			daoProperty = (DaoProperty) pDaoPropertyMap.get(pPropertyName);
		}
		return daoProperty;
	}
	
	public static boolean isDaoContainsProperty(Set<String> pDaoPropertySet,
			String pPropertyName) {
		boolean rtnValue = pDaoPropertySet.contains(pPropertyName);
		if (!rtnValue
				&& Character.isUpperCase(pPropertyName.charAt(0))) {
			pPropertyName = pPropertyName.substring(0, 1).toLowerCase()
					+ pPropertyName.substring(1);
			rtnValue = pDaoPropertySet.contains(pPropertyName);
		}
		return rtnValue;
	}

	public static boolean daoDoUpdate(DaoBaseImpl pDao, 
			ArrayList<String> pWhereColumns) throws Exception {
		DaoSql daoSql = buildUpdateStatement(pDao, pWhereColumns); 
		return executeUpdateDelete(pDao, daoSql);
	} 

	private static boolean executeUpdateDelete(DaoBaseImpl pDao, final DaoSql pDaoSql) throws Exception {
		try {
			if (pDaoSql.getClobBindingValuesIndex() != -1 || pDaoSql.getBlobBindingValuesIndex() != -1) {
				pDao.getJdbcTemplate().execute(
						pDaoSql.getSql(),
						new AbstractLobCreatingPreparedStatementCallback(new DefaultLobHandler()) {
							protected void setValues(PreparedStatement pPs,
									LobCreator pLobCreator) throws SQLException {
								for (int i = 0; i < pDaoSql.getBindingValues().length; i++) {
									if (i == pDaoSql.getClobBindingValuesIndex()) {
										pLobCreator.setClobAsString(pPs, i + 1, pDaoSql.getBindingValues()[i]);
										continue;
									} else if (i == pDaoSql.getBlobBindingValuesIndex()) {
										pLobCreator.setBlobAsBytes(pPs, i + 1, pDaoSql.getBlobBindingValue());
										continue;
									}

									pPs.setString(i + 1, pDaoSql.getBindingValues()[i]);
								}
							}
						});
				return true;
			}

			if (pDaoSql.isCheckOraRowscn() && (pDaoSql.isUpdateByRowId() || pDaoSql.isUpdateByPrimaryKey())) {
				Map<String, DaoProperty> propertyMap = pDao.getPropertyMap();
				StringBuilder sbSql = new StringBuilder("SELECT TO_CHAR(ORA_ROWSCN) FROM ").append(pDao.getTableName()).append(" ").append(pDaoSql.getSqlWhere());
				String dbOraRowscn = OracleSelectHelper.getSqlFirstRowColumnString(
											pDao.getDataSource(), 
											sbSql.toString(), (Object[]) pDaoSql.getWhereBindingValues());
				String daoOraRowscn = getOracleColumn(pDao, propertyMap.get("oraRowscn")).getColumnValue();
				if (!StringUtils.equals(dbOraRowscn, daoOraRowscn)) {
					pDao.setErrMsg("RECORD IS UPDATED BY ANOTHER USER SINCE LAST FETCH - DAO ORA_ROWSCN / DB ORA_ROWSCN: " + daoOraRowscn + " / " + dbOraRowscn);
					if ( propertyMap.containsKey("lastUpdBy")) {
						sbSql = new StringBuilder("SELECT LAST_UPD_BY FROM ").append(pDao.getTableName()).append(" ").append(pDaoSql.getSqlWhere());
						pDao.setErrMsg(StringUtils.replace(pDao.getErrMsg(), "ANOTHER USER", 
								OracleSelectHelper.getSqlFirstRowColumnString(
										pDao.getDataSource(), 
										sbSql.toString(), (Object[]) pDaoSql.getWhereBindingValues())));
					}
					logger.error(pDao.getErrMsg());
					return false;
				}
			}
			

			int numOfRow = pDao.getJdbcTemplate().update(pDaoSql.getSql(), (Object[]) pDaoSql.getBindingValues());
			if (pDaoSql.isUpdateByRowId() || pDaoSql.isUpdateByPrimaryKey()) {
				if (numOfRow != 1) {
					pDao.setErrMsg((numOfRow < 1 ? "NO" : "MORE THAN ONE") + " RECORD FOUND FOR UPDATE/DELETE\nSQL: " + pDaoSql.getSql() + "\n" + 
							pDao.getClass().getName() + " / " + pDao.getPrimaryKeyString());
					return false;
				}
				return true;
			}
			if (numOfRow < 1) {
				pDao.setErrMsg("NO RECORD FOUND FOR UPDATE\nSQL: " + pDaoSql.getSql() + "\n" + 
						pDao.getClass().getName() + " / " + pDao.getPrimaryKeyString());
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error("daoDoUpdate() - SQL: " + pDaoSql.getDebugSql() + "\nException - "
					+ ExceptionUtils.getFullStackTrace(e));
			pDao.setErrMsg("SQL: " + pDaoSql.getDebugSql() + "\nException:"
					+ ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}
	
	public static DaoSql buildUpdateStatement(DaoBaseImpl pDao,
			ArrayList<String> pWhereColumns) throws Exception {

		DaoSql daoSql = new DaoSql();
		ArrayList<OracleColumn> oraColumnList = getOracleColumnList(pDao);

		daoSql.setCheckOraRowscn(pDao.getPropertyMap().containsKey("oraRowscn"));
		
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(getUpdateSql(pDao, oraColumnList, false));
		
		ArrayList<OracleColumn> oraWhereColumnList = new ArrayList<OracleColumn>();
		oraWhereColumnList = getSelUpdWhereFieldList(pDao, pWhereColumns, daoSql);
		StringBuffer sbSqlWhere = new StringBuffer();

		OracleColumn whereColumn = null;
		if (oraWhereColumnList.size() > 0) {
			sbSqlWhere.append(" WHERE ");

			whereColumn = (OracleColumn) oraWhereColumnList.get(0);
			insertWhereField(whereColumn, sbSqlWhere);

			for (int i = 1; i < oraWhereColumnList.size(); i++) {
				whereColumn = (OracleColumn) oraWhereColumnList.get(i);
				sbSqlWhere.append(" AND ");
				insertWhereField(whereColumn, sbSqlWhere);
			}
			if(isExistMarkDelInd(pDao)){
				sbSqlWhere.append(" AND (MARK_DEL_IND != 'Y' OR MARK_DEL_IND IS NULL)");
            }
			daoSql.setSqlWhere(sbSqlWhere.toString());
		}

		sbSql.append(sbSqlWhere.toString());

		ArrayList<String> bindingValueList = new ArrayList<String>();
		ArrayList<String> whereBindingValueList = new ArrayList<String>();
		for (OracleColumn oraColumn : oraColumnList) {
			if (!("ROWID".equals(oraColumn.getOracleFieldName()) || "ORA_ROWSCN".equals(oraColumn.getOracleFieldName()))
					&& oraColumn.getOracleInsUpdValueClause().indexOf("?") != -1) {
				bindingValueList.add(oraColumn.getColumnValue());
			}
		}
		
		for (OracleColumn oraColumn : oraWhereColumnList) {
			if (oraColumn.getOracleInsUpdValueClause().indexOf("?") != -1) {
				if (OraBLOB.class.equals(oraColumn.getPropertyClass())) {
					daoSql.setBlobBindingValuesIndex(bindingValueList.size());
					daoSql.setBlobBindingValue(oraColumn.getColumnBlobValue());
				} else if (OraCLOB.class.equals(oraColumn.getPropertyClass())) {
					daoSql.setClobBindingValuesIndex(bindingValueList.size());
				}
				if (StringUtils.isNotEmpty(oraColumn.getColumnValue())) {
					bindingValueList.add(oraColumn.getColumnValue());
					whereBindingValueList.add(oraColumn.getColumnValue());
				}
			}
		}

		daoSql.setSql(sbSql.toString());
		daoSql.setBindingValues(bindingValueList);
		daoSql.setWhereBindingValues(whereBindingValueList);
		return daoSql;
	}

	private static void insertWhereField(OracleColumn pWhereColumn, StringBuffer pSqlWhere) {
		pSqlWhere.append(pWhereColumn.getOracleFieldName());
		if (StringUtils.isEmpty(pWhereColumn.getColumnValue())) {
			pSqlWhere.append(" IS NULL ");
		} else {
			pSqlWhere.append(" = ");
			pSqlWhere.append(pWhereColumn.getOracleInsUpdValueClause());
		}
	}
	
	private static void insertWhereField(OracleColumn pWhereColumn, StringBuilder pSqlWhere) {
		pSqlWhere.append(pWhereColumn.getOracleFieldName());
		if (StringUtils.isEmpty(pWhereColumn.getColumnValue())) {
			pSqlWhere.append(" IS NULL ");
		} else {
			pSqlWhere.append(" = ");
			pSqlWhere.append(pWhereColumn.getOracleInsUpdValueClause());
		}
	}
	
	public static boolean isSupportsGetGeneratedKeys(Connection pConnection) {
		return false;
	}

	public static boolean daoDoInsert(DaoBaseImpl pDao) throws Exception {
		
		final DaoSql daoSql = buildInsertStatement(pDao);
		
		try {
			if (daoSql.getClobBindingValuesIndex() != -1 || daoSql.getBlobBindingValuesIndex() != -1) {
				pDao.getJdbcTemplate().execute(
						daoSql.getSql(),
						new AbstractLobCreatingPreparedStatementCallback(new DefaultLobHandler()) {
							protected void setValues(PreparedStatement pPs,
									LobCreator pLobCreator) throws SQLException {
								for (int i = 0; i < daoSql.getBindingValues().length; i++) {
									if (i == daoSql.getClobBindingValuesIndex()) {
										pLobCreator.setClobAsString(pPs, i + 1, daoSql.getBindingValues()[i]);
										continue;
									} else if (i == daoSql.getBlobBindingValuesIndex()) {
										pLobCreator.setBlobAsBytes(pPs, i + 1, daoSql.getBlobBindingValue());
										continue;
									}
									pPs.setString(i + 1, daoSql.getBindingValues()[i]);
								}
							}
						});
				return true;
			}

			
			int numOfRow = pDao.getJdbcTemplate().update(daoSql.getSql(), (Object[]) daoSql.getBindingValues());
			
			boolean rtnValue = (numOfRow == 1);
			if (!rtnValue) {
				pDao.setErrMsg("NO ROW INSERTED\nSQL: " + daoSql.getSql() + "\n" + 
						pDao.getClass().getName() + " / " + pDao.getPrimaryKeyString());
			}
			return rtnValue;
		} catch (Exception e) {
			logger.error("daoDoInsert() - SQL: " + daoSql.getDebugSql() + "\nException - "
					+ ExceptionUtils.getFullStackTrace(e));
			pDao.setErrMsg("SQL: " + daoSql.getDebugSql() + "\nException:"
					+ ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}

	public static DaoSql buildInsertStatement(DaoBaseImpl pDao) throws Exception {
		DaoSql daoSql = new DaoSql();
		ArrayList<OracleColumn> oraColumnList = getOracleColumnList(pDao);
		daoSql.setSql(getInsertSql(pDao, oraColumnList, false));
		ArrayList<String> bindingValueList = new ArrayList<String>(oraColumnList.size());
		Map<String, DaoProperty> daoPropertyMap = pDao.getPropertyMap();
		DaoProperty daoProperty = null;
		OraNumberInsertValueFromSelect oraNumberInsertValueFromSelect;
		
		for (OracleColumn oraColumn : oraColumnList) {
			if (OraNumberInsertValueFromSelect.class.equals(oraColumn.getPropertyClass())) {
				daoProperty = DaoHelper.getDaoProperty(daoPropertyMap, oraColumn.getDaoFieldName());
				oraNumberInsertValueFromSelect = (OraNumberInsertValueFromSelect) daoProperty.getPropertyOracleGetter().invoke(pDao, (Object[]) null);
				oraNumberInsertValueFromSelect.setValue(oraNumberInsertValueFromSelect.getInsertValue(pDao));
				oraColumn.setColumnValue(oraNumberInsertValueFromSelect.getValue());
			}
			if (!("ROWID".equals(oraColumn.getOracleFieldName()) || "ORA_ROWSCN".equals(oraColumn.getOracleFieldName()))
					&& oraColumn.getOracleInsUpdValueClause().indexOf("?") != -1) {
				if (OraBLOB.class.equals(oraColumn.getPropertyClass())) {
					daoSql.setBlobBindingValuesIndex(bindingValueList.size());
					daoSql.setBlobBindingValue(oraColumn.getColumnBlobValue());
				} else if (OraCLOB.class.equals(oraColumn.getPropertyClass())) {
					daoSql.setClobBindingValuesIndex(bindingValueList.size());
				}
				bindingValueList.add(oraColumn.getColumnValue());
			}
		}
		daoSql.setBindingValues(bindingValueList);
		return daoSql;
	}

	public static boolean daoDoSelect(DaoBaseImpl pDao) throws Exception {
		return daoDoSelect(pDao, null);
	}

	public static DaoBase[] daoDoSelect(DaoBaseImpl pDao, 
			ArrayList<String> pColumnsToRetrieve, ArrayList<String> pWhereCondition,
			String pAdditionWhere) throws Exception {
		return daoDoSelect(pDao, pColumnsToRetrieve, pWhereCondition,
				pAdditionWhere, null);
	}
	
	@SuppressWarnings("unchecked")
	public static DaoBase[] daoDoSelect(DaoBaseImpl pDao,
			ArrayList<String> pColumnsToRetrieve,
			ArrayList<String> pWhereCondition, String pAdditionWhere,
			String pOrderBy) throws Exception {
		
		DaoSql daoSql = buildSelectStatement(pDao, pColumnsToRetrieve,
				pWhereCondition, pAdditionWhere, pOrderBy);
		
		try {
			@SuppressWarnings("rawtypes")
			Class tmpClass = pDao.getClass();
			for (@SuppressWarnings("rawtypes") Class daoInterface : pDao.getClass().getInterfaces()) {
				if ("DAO".equals(StringUtils.right(daoInterface.getSimpleName(), 3))) {
					tmpClass = daoInterface;
					break;
				}
			}
			@SuppressWarnings("rawtypes")
			final Class daoClass = tmpClass;
			@SuppressWarnings("rawtypes")
			List resultObjList = pDao.getJdbcTemplate().query(daoSql.getSql(), daoSql.getBindingValues(), new RowMapper() {

				private ArrayList columnPropertyMappingList = null;

				@Override
				public Object mapRow(ResultSet pRs, int pRowNum)
						throws SQLException {
	                Object rowObj = null;
	                try {
						rowObj = SpringApplicationContext.getBean(daoClass);
					} catch (Exception e) {
						throw new SQLException("ERROR CREATING OBJECT: " + daoClass.getName() + "\nException:" + ExceptionUtils.getFullStackTrace(e));
					}

					if (this.columnPropertyMappingList == null) {
						this.columnPropertyMappingList = OracleSelectHelper.getColumnPropertyMappingList(pRs.getMetaData(), rowObj.getClass());
					}

					try {
		                OracleSelectHelper.getResultSetRowObject(columnPropertyMappingList, pRs, pRs.getMetaData(), rowObj);
					} catch (Exception e) {
						throw new SQLException(e);
					}
					
	                return rowObj;
				}
			});
			Object[] rtnArray = (Object[]) Array.newInstance(pDao.getClass(), resultObjList.size());
		    return (DaoBase[]) resultObjList.toArray(rtnArray);

		} catch (Exception e) {
			logger.error("daoDoSelect() - SQL: " + daoSql.getDebugSql() + "\nException - ", e);
			pDao.setErrMsg("SQL: " + daoSql.getDebugSql() + "\nException:"
					+ ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}

	public static boolean daoDoSelect(DaoBaseImpl pDao,
			ArrayList<String> pColumnsToRetrieve) throws Exception {

		DaoSql daoSql = buildSelectStatement(pDao, pColumnsToRetrieve,
				null, null, null);

		try {
			final DaoBase dao = pDao;
			pDao.getJdbcTemplate().queryForObject(daoSql.getSql(), daoSql.getBindingValues(), new RowMapper() {

				@Override
				public Object mapRow(ResultSet pRs, int pRowNum)
						throws SQLException {
					try {
						OracleSelectHelper.getResultSetObject(pRs, pRs.getMetaData(), dao);
					} catch (Exception e) {
						throw new SQLException(e);
					}
	                return dao;
				}
			});

			return true;
		} catch (EmptyResultDataAccessException ignore) {
			return true;
		} catch (Exception e) {
			logger.error("daoDoSelect() - SQL: " + daoSql.getDebugSql() + "\nException - ", e);
			pDao.setErrMsg("SQL: " + daoSql.getDebugSql() + "\nException:"
					+ ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}

	public static String getWhereClause(DaoBaseImpl pDao,
			ArrayList<OracleColumn> pOraWhereColumnList, String pAdditionWhere) {
		
		StringBuffer sbSql = new StringBuffer();
		
		boolean whereAdded = false;
		OracleColumn whereColumn = null;
		if (pOraWhereColumnList.size() > 0) {
			whereAdded = true;
			sbSql.append(" WHERE ");

			whereColumn = (OracleColumn) pOraWhereColumnList.get(0);
			sbSql.append(whereColumn.getOracleFieldName());
			sbSql.append(" = ");
			sbSql.append(whereColumn.getOracleInsUpdValueClause());

			for (int i = 1; i < pOraWhereColumnList.size(); i++) {
				whereColumn = (OracleColumn) pOraWhereColumnList.get(i);
				sbSql.append(" AND ");
				sbSql.append(whereColumn.getOracleFieldName());
				sbSql.append(" = ");
				sbSql.append(whereColumn.getOracleInsUpdValueClause());
			}
			if (isExistMarkDelInd(pDao) && !pDao.isSelectMarkDel()) {
				sbSql.append(" AND (MARK_DEL_IND = 'N' or MARK_DEL_IND IS NULL)");
			}
		} else if (isExistMarkDelInd(pDao) && !pDao.isSelectMarkDel()) {
			whereAdded = true;
			sbSql.append(" WHERE (MARK_DEL_IND = 'N' or MARK_DEL_IND IS NULL)");
		}
		if (StringUtils.isNotBlank(pAdditionWhere)) {
			String trimAdditionWhere = pAdditionWhere.trim();

			if (!whereAdded) {
				sbSql.append(" WHERE ");
				if (trimAdditionWhere.toUpperCase().indexOf("AND") == 0) {
					sbSql.append(trimAdditionWhere.substring(3));
				} else {
					sbSql.append(trimAdditionWhere);
				}
			} else {
				if (trimAdditionWhere.toUpperCase().indexOf("AND") != 0) {
					sbSql.append(" AND ");
				}
				sbSql.append(pAdditionWhere);
			}
		}
		
		return sbSql.toString();
	}
	
	public static DaoSql buildSelectStatement(DaoBaseImpl pDao,
			ArrayList<String> pColumnsToRetrieve,
			ArrayList<String> pWhereColumns, String pAdditionWhere, String pOrderBy) throws Exception {

		DaoSql daoSql = new DaoSql();
		StringBuilder sbSql = new StringBuilder();
		StringBuilder additionWhereSb = new StringBuilder(
				pAdditionWhere == null ? (pDao.getAdditionWhere() == null ? ""
						: pDao.getAdditionWhere()) : pAdditionWhere);
		
		ArrayList<String> searchKeyInList = pDao.getSearchKeyInList();
		for (String searchKeyIn : searchKeyInList) {
			if (additionWhereSb.length() > 0) {
				additionWhereSb.append(" AND ");
			}
			additionWhereSb.append(searchKeyIn);			
		}
		
		String additionWhere = additionWhereSb.toString();
		
		sbSql.append(getSelectClause(pDao, pColumnsToRetrieve));

		ArrayList<OracleColumn> oraWhereColumnList = new ArrayList<OracleColumn>();
		oraWhereColumnList = getSelUpdWhereFieldList(pDao, pWhereColumns == null ? pDao.getSearchKeyList(): pWhereColumns, daoSql);

		sbSql.append(getWhereClause(pDao,oraWhereColumnList,additionWhere));
		
		if (StringUtils.isNotBlank(pOrderBy)) {
			sbSql.append(" ORDER BY ");
			sbSql.append(pOrderBy);
		}
		
		ArrayList<OracleColumn> bindOracleColumnList = new ArrayList<OracleColumn>();
		DaoHelper.addExtraBinds(bindOracleColumnList, pDao, pDao.getTableName(), sbSql);
		if (oraWhereColumnList != null && oraWhereColumnList.size() > 0) {
		   bindOracleColumnList.addAll(oraWhereColumnList);
		}
		DaoHelper.addExtraBinds(bindOracleColumnList, pDao, additionWhere, sbSql);
		
		ArrayList<String> bindingValueList = new ArrayList<String>();
		
/*		for (OracleColumn oraColumn : oraWhereColumnList) {
			if (oraColumn.getOracleInsUpdValueClause().indexOf("?") != -1) {
				bindingValueList.add(oraColumn.getColumnValue());
			}
		}*/
		
		for (OracleColumn oraColumn : bindOracleColumnList) {
			if (oraColumn.getOracleInsUpdValueClause().indexOf("?") != -1) {
				bindingValueList.add(oraColumn.getColumnValue());
			}
		}		

		daoSql.setSql(sbSql.toString());
		daoSql.setBindingValues(bindingValueList);
		return daoSql;
	}

	// Used in conjunction with setSearchKey and SetExtraBind
	private static void addExtraBinds(ArrayList<OracleColumn> pBindOracleColumnList,
			DaoBaseImpl pDao, String pExtraBinds, StringBuilder pSbSql)
			throws Exception {
		
		int startPos = 0;
		String fieldName = null;
		OracleColumn oraColumn = null;
		
		if (StringUtils.indexOf(pExtraBinds,":")!=-1) {			
			String [] tokens = StringUtils.split(pExtraBinds);	
			for (int i = 0; i < tokens.length;i++) {
				if (StringUtils.contains(tokens[i],":")) {
					fieldName = getBindFieldName(tokens[i]);
					oraColumn = new OracleColumn();
					oraColumn.setDaoFieldName(fieldName);
					oraColumn.setOracleInsUpdValueClause("?");
					oraColumn.setOracleFieldName(hungarianToOracleName(fieldName));
					oraColumn.setColumnValue(pDao.getBindingValue(fieldName));					
					pBindOracleColumnList.add(oraColumn);
					
					startPos = pSbSql.indexOf(":" + fieldName);
					if (startPos != -1) {
						pSbSql.replace(startPos, startPos + fieldName.length() + 1, "?");
					} else {
					   throw new Exception("Extra bind " + ":" + fieldName + " is not in the sql " + pSbSql);
					}
				}
			}
		}
	}
	
	private static String getBindFieldName(String pToken) {
		StringBuffer sb = new StringBuffer();
		char[] tokenChars = pToken.toCharArray();
		for (int i = 0; i < tokenChars.length; i++) {
			if (':' == tokenChars[i]) {
				sb.append(':');
			} else if (sb.length() > 0) {
				if (Character.isLetterOrDigit(tokenChars[i])) {
					sb.append(tokenChars[i]);
				} else {
					break;
				}
			}
		}
		return (sb.length() > 0 ? sb.toString().substring(1) : pToken);
	}
	
	public static ArrayList<OracleColumn> getSelUpdWhereFieldList(DaoBaseImpl pDao,
			ArrayList<String> pWhereColumns, DaoSql pDaoSql) throws Exception {
		ArrayList<OracleColumn> whereFieldList = new ArrayList<OracleColumn>();
		Map<String, DaoProperty> daoPropertyMap = pDao.getPropertyMap();
		DaoProperty daoProperty = null;
		if (pWhereColumns != null && pWhereColumns.size() > 0) {
			for (String propertyName : pWhereColumns) {
				daoProperty = getDaoProperty(daoPropertyMap, propertyName);
				if (daoProperty == null) {
					logger.warn(propertyName + " NOT FOUND in "
							+ pDao.getClass().getName());
					continue;
				}
				whereFieldList.add(getOracleColumn(pDao, daoProperty));
			}
		} else if (pDao.getAdditionWhere() != null || pDao.getSearchKeyInList().size() > 0) {
			//additionWhere or searchKeyIn is first priority over rowid and primary key fields
		} else if (!StringUtils.isEmpty(pDao.getOracleRowID())) {
			OracleColumn oraColumn = new OracleColumn();
			oraColumn.setDaoFieldName("oracleRowID");
			oraColumn.setOracleFieldName("ROWID");
			oraColumn.setColumnValue(pDao.getOracleRowID());
			oraColumn.setOracleInsUpdValueClause("CHARTOROWID(?)");
			oraColumn.setOracleSelectClause("ROWIDTOCHAR(ROWID) \"oracleRowID\"");
			whereFieldList.add(oraColumn);
			if (pDaoSql != null) {
				pDaoSql.setUpdateByRowId(true);
			}
		} else if (!ArrayUtils.isEmpty(pDao.getPrimaryKeyFields())) {
			daoPropertyMap = pDao.getPropertyMap();
			for (String propertyName : pDao.getPrimaryKeyFields()) {
				daoProperty = getDaoProperty(daoPropertyMap, propertyName);
				if (daoProperty == null) {
					logger.warn(propertyName + "NOT FOUND in "
							+ pDao.getClass().getName());
					continue;
				}

				OracleColumn oraColumn = getOracleColumn(pDao, daoProperty);
				whereFieldList.add(oraColumn);
			}
			if (pDaoSql != null) {
				pDaoSql.setUpdateByPrimaryKey(true);
			}
		}
		return whereFieldList;
	}

	public static ArrayList<String> getAllFieldList(DaoBaseImpl pDao, Map<String, DaoProperty> pDaoPropertyMap)
			throws Exception {
		
		ArrayList<String> rtnList = new ArrayList<String>();
		if (pDaoPropertyMap.size() > 0) {
			String propertyName = null;
			for (Iterator<String> it = pDaoPropertyMap.keySet().iterator(); it.hasNext(); ) {
				propertyName = it.next();
				if (!pDao.isIncludeColumn(propertyName) || pDao.isExcludeColumn(propertyName)) {
					continue;
				}
				rtnList.add(propertyName);
			}
		}
		return rtnList;
	}

//	public static String getSelectSqlFields(DaoBaseImpl pDao,
//			ArrayList<String> pColumnsToRetrieve) throws Exception {
//
//		StringBuilder sbRtnSql = new StringBuilder();
//		sbRtnSql.append("SELECT ");
//
//		if (StringUtils.isNotBlank(pDao.getOracleHints())) {
//			if (pDao.getOracleHints().indexOf("/*+") != 0) {
//				sbRtnSql.append("/*+");
//			}
//			sbRtnSql.append("pDao.getOracleHints()");
//			if (pDao.getOracleHints().indexOf("*/") == -1) {
//				sbRtnSql.append("*/");
//			}
//		}
//		
//		if (pDao.isDistinctResult()){
//			sbRtnSql.append("DISTINCT ");
//		}
//		
//		String primaryRowId = null;
//		if (pDao.isIncludeColumn("oracleRowID") && !pDao.isExcludeColumn("oracleRowID")) {
//			primaryRowId = pDao.getPrimaryRowId();
//			if (primaryRowId == null) { 
//				primaryRowId = "ROWID"; 
//			} 
//			sbRtnSql.append(" ROWIDTOCHAR(" + primaryRowId + ") \"oracleRowID\"");
//		} else {
//			sbRtnSql.append(" NULL \"oracleRowID\"");
//		}
//		
//		String columnsToRetrieve = (String)getColumnsToSelect(pDao, pColumnsToRetrieve).get(ORACLE_SELECT_CLAUSE);
//		if (StringUtils.isNotBlank(columnsToRetrieve)) {
//			sbRtnSql.append(", ");
//			sbRtnSql.append(columnsToRetrieve);
//		}
//		
//		sbRtnSql.append(" FROM ");
//		sbRtnSql.append(getTableName(pDao));
//
//		
//		sbRtnSql.setLength(sbRtnSql.length() - 2);
//		sbRtnSql.append(" FROM ");
//		sbRtnSql.append(pDao.getTableName());
//		return sbRtnSql.toString();
//	}

	public static Map<String, String> getColumnsToSelect(DaoBaseImpl pDao,
			ArrayList<String> pColumnsToRetrieve) throws Exception {
		
		HashMap<String, String> returnMap = new HashMap<String, String>();
		StringBuffer sbSelectClause     = new StringBuffer();
		StringBuffer sbOracleFieldNames = new StringBuffer();
		StringBuffer sbDaoFieldNames    = new StringBuffer();
		
		Map<String, DaoProperty> daoPropertyMap = pDao.getPropertyMap();

		if (pColumnsToRetrieve == null || pColumnsToRetrieve.size() == 0) {
			pColumnsToRetrieve = getAllFieldList(pDao, daoPropertyMap);
		}

		DaoProperty daoProperty = null;
		OracleColumn oraColumn = null;
		
		for (String propertyName : pColumnsToRetrieve) {
			if ("oracleRowID".equals(propertyName)
					&& pDao.getPrimaryRowId() != null) {
				continue;
			}
							
			daoProperty = getDaoProperty(daoPropertyMap, propertyName);

			if (daoProperty == null) {
				logger.warn(propertyName + "NOT FOUND in "
						+ pDao.getClass().getName());
				continue;
			}

			oraColumn = getOracleColumn(pDao, daoProperty);

			sbSelectClause.append(oraColumn.getOracleSelectClause());
			sbOracleFieldNames.append(oraColumn.getOracleFieldName() );
			sbDaoFieldNames.append("\"" + oraColumn.getDaoFieldName()+ "\"");

			sbSelectClause.append(", ");
			sbOracleFieldNames.append(", ");
			sbDaoFieldNames.append(", ");
		}		

		if (pColumnsToRetrieve.size() > 0) {
			// REMOVE THE LAST ", "
			sbSelectClause.setLength(sbSelectClause.length() - 2);
			sbOracleFieldNames.setLength(sbOracleFieldNames.length() - 2);
			sbDaoFieldNames.setLength(sbDaoFieldNames.length() - 2);
		}
		
		returnMap.put(ORACLE_SELECT_CLAUSE, sbSelectClause.toString());
		returnMap.put(ORACLE_FIELD_NAME, sbOracleFieldNames.toString());
		returnMap.put("DaoFieldName", sbDaoFieldNames.toString());
		
		return returnMap;
	}
	
	public static String getSelectClause(DaoBaseImpl pDao,
			ArrayList<String> pColumnsToRetrieve) throws Exception {

		String primaryRowId = null;
		StringBuffer sbRtnSql = new StringBuffer();

		sbRtnSql.append("SELECT ");
		if (StringUtils.isNotBlank(pDao.getOracleHints())) {
			if (pDao.getOracleHints().indexOf("/*+") != 0) {
				sbRtnSql.append("/*+");
			}
			sbRtnSql.append("pDao.getOracleHints()");
			if (pDao.getOracleHints().indexOf("*/") == -1) {
				sbRtnSql.append("*/");
			}
		}
		
		if (pDao.isDistinctResult()){
			sbRtnSql.append("DISTINCT ");
		}
		
		if (pDao.isIncludeColumn("oracleRowID") && !pDao.isExcludeColumn("oracleRowID")) {
			primaryRowId = pDao.getPrimaryRowId();
			if (primaryRowId == null) { 
				primaryRowId = "ROWID"; 
			} 
			sbRtnSql.append(" ROWIDTOCHAR(" + primaryRowId + ") \"oracleRowID\"");
		} else {
			sbRtnSql.append(" NULL \"oracleRowID\"");
		}

		String columnsToRetrieve = (String)getColumnsToSelect(pDao,pColumnsToRetrieve).get(ORACLE_SELECT_CLAUSE);
		if (StringUtils.isNotBlank(columnsToRetrieve)) {
			sbRtnSql.append(", ");
			sbRtnSql.append(columnsToRetrieve);
		}
		
		sbRtnSql.append(" FROM ");
		sbRtnSql.append(getTableName(pDao));

		return sbRtnSql.toString();
	}
	
	public static String getUpdateSql(DaoBase pDao, ArrayList<OracleColumn> pOraColumnList,
			boolean pDebugMode) {
		StringBuilder sbRtnSql = new StringBuilder();

		sbRtnSql.append("UPDATE ");
		sbRtnSql.append(pDao.getTableName());
		sbRtnSql.append(" SET ");

		//		sbValues.append("VALUES (");

		boolean firstFieldAdded = false;

		for (OracleColumn oraColumn : pOraColumnList) {
			if (!("ROWID".equals(oraColumn.getOracleFieldName()) || "ORA_ROWSCN".equals(oraColumn.getOracleFieldName()))) {
				if (firstFieldAdded) {
					sbRtnSql.append(", ");
				}

				sbRtnSql.append(oraColumn.getOracleFieldName());
				sbRtnSql.append(" = ");
				if (pDebugMode) {
					if (oraColumn.getOracleInsUpdValueClause().indexOf("?") != -1) {
						sbRtnSql.append(oraColumn.getColumnValue() == null ? "null"
										: "'" + oraColumn.getColumnValue() + "'");
					} else {
						sbRtnSql.append(oraColumn.getOracleInsUpdValueClause());
					}
				} else {
					sbRtnSql.append(oraColumn.getOracleInsUpdValueClause());
				}

				firstFieldAdded = true;
			}
		}

		//		sbRtnSql.append(") ");
		//		sbValues.append(")");
		//		sbRtnSql.append(sbValues.toString());

		logger.debug("getUpdateSql - pDao.class = " + pDao.getClass().getName()
				+ " Update SQL: " + sbRtnSql.toString());

		return sbRtnSql.toString();
	}

	public static String getInsertSql(DaoBase pDao, ArrayList<OracleColumn> pOraColumnList,
			boolean pDebugMode) {
		StringBuilder sbRtnSql = new StringBuilder();
		StringBuilder sbValues = new StringBuilder();

		sbRtnSql.append("INSERT INTO ");
		sbRtnSql.append(pDao.getTableName());
		sbRtnSql.append(" (");

		sbValues.append("VALUES (");

		boolean firstFieldAdded = false;

		for (OracleColumn oraColumn : pOraColumnList) {
			if (!("ROWID".equals(oraColumn.getOracleFieldName()) || "ORA_ROWSCN".equals(oraColumn.getOracleFieldName()))) {
				if (firstFieldAdded) {
					sbRtnSql.append(", ");
					sbValues.append(", ");
				}

				sbRtnSql.append(oraColumn.getOracleFieldName());
				if (pDebugMode) {
					if (oraColumn.getOracleInsUpdValueClause().indexOf("?") != -1) {
						sbValues.append(oraColumn.getColumnValue() == null ? 
								"null" : "'" + oraColumn.getColumnValue() + "'");
					} else {
						sbValues.append(oraColumn.getOracleInsUpdValueClause());
					}
				} else {
					sbValues.append(oraColumn.getOracleInsUpdValueClause());
				}

				firstFieldAdded = true;
			}
		}

		sbRtnSql.append(") ");
		sbValues.append(")");
		sbRtnSql.append(sbValues.toString());

		logger.debug("getInsertSql - pDao.class = " + pDao.getClass().getName()
				+ " Insert SQL: " + sbRtnSql.toString());

		return sbRtnSql.toString();
	}

	public static ArrayList<OracleColumn> getOracleColumnList(DaoBaseImpl pDao) {
		ArrayList<OracleColumn> oraColumnList = new ArrayList<OracleColumn>();
		OracleColumn oracleColumn;

		try {
			for (DaoProperty daoProperty : pDao.getPropertyMap().values()) {
				oracleColumn = getOracleColumn(pDao, daoProperty);
				if (!pDao.isIncludeColumn(oracleColumn.getOracleFieldName())
						|| pDao.isExcludeColumn(oracleColumn
								.getOracleFieldName())) {
					continue;
				} else {
					oraColumnList.add(oracleColumn);
				}
				
			}
		} catch (Exception e) {
			logger.error("getOracleColumnList() - Exception" + ExceptionUtils.getFullStackTrace(e));
		}
		return oraColumnList;
	}

	public static OracleColumn getOracleColumn(DaoBase pDao,
			DaoProperty pDaoProperty) throws Exception {
		try {
			OracleColumn oraColumn = new OracleColumn();
			oraColumn.setDaoFieldName(pDaoProperty.getPropertyName());
			oraColumn.setOracleFieldName(pDaoProperty.getOracleFieldName());
			oraColumn.setPropertyClass(pDaoProperty.getPropertyClass());
			if (pDaoProperty.getPropertyGetter() != null) {
				Object tmp = pDaoProperty.getPropertyGetter().invoke(pDao, (Object[]) null);
				if (tmp instanceof String) {
					oraColumn.setColumnValue((String) tmp);
				} else if (tmp instanceof byte[]) {
					oraColumn.setColumnBlobValue((byte[]) tmp);
				}
			}
			if (pDaoProperty.getPropertyClass().equals(String.class)
					|| pDaoProperty.getPropertyClass().equals(OraNumber.class)
					|| pDaoProperty.getPropertyClass().equals(OraNumberInsertValueFromSelect.class)
					|| pDaoProperty.getPropertyClass().equals(OraBLOB.class)
					|| pDaoProperty.getPropertyClass().equals(OraCLOB.class)) {
				oraColumn.setOracleSelectClause(pDaoProperty.getOracleSelectClause());
				oraColumn.setOracleInsUpdValueClause(pDaoProperty.getOracleInsertUpdateClause());
			} else if (pDaoProperty.getPropertyOracleGetter() != null) {
				Object tmp = pDaoProperty.getPropertyOracleGetter().invoke(pDao, (Object[]) null);
				if (tmp instanceof OraDate) {
					OraDate oraDate = (OraDate) tmp;
					oraColumn.setOracleSelectClause(pDaoProperty.getOracleSelectClause(oraDate));
					oraColumn.setOracleInsUpdValueClause(DaoProperty.getOracleInsertUpdateClause(oraDate));
				}
			}
			return oraColumn;
		} catch (IllegalArgumentException e) {
			logger.error("getOracleColumnList() - " + pDao.getClass().getName()
					+ " - " + pDaoProperty == null ? "NULL" : pDaoProperty
					.getPropertyName()
					+ " - IllegalArgumentException: ", e);
			logger.error("getOracleColumnList() - " + pDao.getClass().getName()
					+ " - " + pDaoProperty == null ? "NULL" : pDaoProperty
					.getPropertyName()
					+ " - IllegalArgumentException: "
					+ ExceptionUtils.getFullStackTrace(e));
			throw e;
		} catch (IllegalAccessException e) {
			logger.error("getOracleColumnList() - " + pDao.getClass().getName()
					+ " - " + pDaoProperty == null ? "NULL" : pDaoProperty
					.getPropertyName()
					+ " - IllegalAccessException: ", e);
			logger.error("getOracleColumnList() - " + pDao.getClass().getName()
					+ " - " + pDaoProperty == null ? "NULL" : pDaoProperty
					.getPropertyName()
					+ " - IllegalAccessException: "
					+ ExceptionUtils.getFullStackTrace(e));
			throw e;
		} catch (InvocationTargetException e) {
			logger.error("getOracleColumnList() - " + pDao.getClass().getName()
					+ " - " + pDaoProperty == null ? "NULL" : pDaoProperty
					.getPropertyName()
					+ " - InvocationTargetException: ", e);
			logger.error("getOracleColumnList() - " + pDao.getClass().getName()
					+ " - " + pDaoProperty == null ? "NULL" : pDaoProperty
					.getPropertyName()
					+ " - InvocationTargetException: "
					+ ExceptionUtils.getFullStackTrace(e));
			throw e;
		}

	}

	public static DaoProperty getDaoPropertyRowID() {
		return new DaoProperty(DaoBaseImpl.class, "oracleRowID", "ROWID",
				String.class);
	}

	public static class DaoProperty {
		/**
		 * Logger for this class
		 */
		private static final Logger logger = Logger
				.getLogger(DaoProperty.class);

		private boolean clob;

		private boolean blob;
		
		private String propertyName;

		@SuppressWarnings("rawtypes")
		private Class propertyClass;

		private Method propertyGetter;

		private Method propertySetter;

		private Method propertyOracleGetter;

		private String oracleFieldName;

		private String oracleSelectClause;

		private String oracleInsertUpdateClause;

		/**
		 * @param pPropertyName
		 * @param pPropertyClass
		 */
		@SuppressWarnings("rawtypes")
		public DaoProperty(Class pDaoClass, String pPropertyName,
				String pOracleFieldName, Class pPropertyClass) {
			super();
			this.setPropertyName(pPropertyName);
			this.setOracleFieldName(pOracleFieldName);
			this.setPropertyClass(pPropertyClass);
			this.propertyGetter = findGetterMethod(pDaoClass, pPropertyName);
			if (OraBLOB.class.equals(pPropertyClass)) {
				this.propertySetter = findBlobSetterMethod(pDaoClass, pPropertyName);
			} else {
				this.propertySetter = findSetterMethod(pDaoClass, pPropertyName);
			}
			if (pPropertyClass.getName().indexOf("OraDate") != -1
					|| pPropertyClass.getName().indexOf("OraNumberInsertValueFromSelect") != -1) {
				this.propertyOracleGetter = findGetterMethod(pDaoClass,	pPropertyName + "ORACLE");
			}
			this.clob = (pPropertyClass.getName().indexOf("OraCLOB") != -1);
			this.blob = (pPropertyClass.getName().indexOf("OraBLOB") != -1);
		}

		/**
		 * @return Returns the oracleFieldName.
		 */
		public String getOracleFieldName() {
			return this.oracleFieldName;
		}

		/**
		 * @param pOracleFieldName
		 *            The oracleFieldName to set.
		 */
		public void setOracleFieldName(String pOracleFieldName) {
			this.oracleFieldName = pOracleFieldName;
		}

		/**
		 * @return Returns the oracleInsertUpdateClause.
		 */
		public String getOracleInsertUpdateClause() {
			return this.oracleInsertUpdateClause;
		}

		/**
		 * @param pOraDate
		 *            The OraDate instance to get the format to set.
		 * @return Returns the oracleSelectClause.
		 */
		public static String getOracleInsertUpdateClause(OraDate pOraDate) {
			if (pOraDate.getUpdateUsing() == OraDate.UPD_BY_SYS_DATE) {
				if (pOraDate instanceof OraDateTimestamp) {
				  return "SYSTIMESTAMP";	
				} else {
				  return "SYSDATE";
				}
			}
			StringBuffer sb = new StringBuffer();
			if (pOraDate instanceof OraDateTimestamp) { 
			  sb.append("TO_TIMESTAMP(?, '");
			} else {
			  sb.append("TO_DATE(?, '");
			}
			sb.append(pOraDate.getOracleDateFormat());
			sb.append("')");
			return sb.toString();
		}

		/**
		 * @param pOracleInsertUpdateClause
		 *            The oracleInsertUpdateClause to set.
		 */
		public void setOracleInsertUpdateClause(String pOracleInsertUpdateClause) {
			this.oracleInsertUpdateClause = pOracleInsertUpdateClause;
		}

		/**
		 * @return Returns the oracleSelectClause.
		 */
		public String getOracleSelectClause() {
			if (StringUtils.isEmpty(this.oracleSelectClause)) {
				this.setOracleSelectClause(this.generateOracleSelectClause());
			}
			return this.oracleSelectClause;
		}

		private String generateOracleSelectClause() {
			if (this.getPropertyName().equals("oracleRowID")) {
				return "ROWIDTOCHAR(ROWID) \"oracleRowID\"";
			} else if (this.getPropertyClass().getName().indexOf("OraNumber") != -1) {
				return "TO_CHAR(" + this.oracleFieldName + ") \"" + this.getPropertyName() + "\"";
			}
			return this.oracleFieldName + " \""	+ this.getPropertyName() + "\"";
		}
		
		private String generateOracleInsertUpdateClause() {
			if (this.getPropertyName().equals("oracleRowID")) {
				return "CHARTOROWID(?)";
			} else if (this.getPropertyClass().getName().indexOf("OraNumber") != -1) {
				return "TO_NUMBER(?)";
			} 
			return "?";
		}
		
		/**
		 * @param pOraDate
		 *            The OraDate instance to get the format to set.
		 * @return Returns the oracleSelectClause.
		 */
		public String getOracleSelectClause(OraDate pOraDate) {
			StringBuffer sb = new StringBuffer();
			sb.append("TO_CHAR(");
			sb.append(this.oracleFieldName);
			sb.append(", '");
			sb.append(pOraDate.getOracleDateFormat());
			sb.append("')");
			sb.append(" \"");
			sb.append(this.propertyName);
			sb.append("\"");
			return sb.toString();
		}

		/**
		 * @param pOracleSelectClause
		 *            The oracleSelectClause to set.
		 */
		public void setOracleSelectClause(String pOracleSelectClause) {
			this.oracleSelectClause = pOracleSelectClause;
		}

		/**
		 * @return Returns the propertyClass.
		 */
		@SuppressWarnings("rawtypes")
		public Class getPropertyClass() {
			return this.propertyClass;
		}

		/**
		 * @param pPropertyClass
		 *            The propertyClass to set.
		 */
		@SuppressWarnings("rawtypes")
		public void setPropertyClass(Class pPropertyClass) {
			this.propertyClass = pPropertyClass;
			this.setOracleSelectClause(this.generateOracleSelectClause());
			this.setOracleInsertUpdateClause(this.generateOracleInsertUpdateClause());
		}

		/**
		 * @return Returns the propertyName.
		 */
		public String getPropertyName() {
			return this.propertyName;
		}

		/**
		 * @param pPropertyName
		 *            The propertyName to set.
		 */
		public void setPropertyName(String pPropertyName) {
			this.propertyName = pPropertyName;
			this.setOracleFieldName(hungarianToOracleName(propertyName));
		}

		/**
		 * @return Returns the propertyGetter.
		 */
		public Method getPropertyGetter() {
			return this.propertyGetter;
		}

		/**
		 * @param pPropertyGetter
		 *            The propertyGetter to set.
		 */
		public void setPropertyGetter(Method pPropertyGetter) {
			this.propertyGetter = pPropertyGetter;
		}

		/**
		 * @return Returns the setterMethod.
		 */
		public Method getPropertySetter() {
			return this.propertySetter;
		}

		/**
		 * @param pSetterMethod
		 *            The setterMethod to set.
		 */
		public void setPropertySetter(Method pSetterMethod) {
			this.propertySetter = pSetterMethod;
		}

		/**
		 * @return Returns the propertyOracleGetter.
		 */
		public Method getPropertyOracleGetter() {
			return this.propertyOracleGetter;
		}

		public String getPropertyValue(DaoBase pDaoBase) {
			try {
				return (String) this.propertyGetter.invoke(pDaoBase, (Object[]) null);
			} catch (Exception e) {
				logger.error("Class: " + this.getClass().getName()
						+ " / property: " + this.propertyName + " / "
						+ ExceptionUtils.getFullStackTrace(e));
				return null;
			}
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static Method findGetterMethod(Class pClass, String pPropertyName) {
			StringBuffer sb = new StringBuffer();
			sb.append("get");
			sb.append(getGetterSetterPropertyName(pPropertyName));
			try {
				return pClass.getMethod(sb.toString(), (Class[]) null);
			} catch (NoSuchMethodException ex) {
				logger.error("Class: " + pClass.getName() + " / property: "
						+ pPropertyName + " / " + sb.toString(), ex);
			}
			return null;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static Method findSetterMethod(Class pClass, String pPropertyName) {
			StringBuffer sb = new StringBuffer();
			sb.append("set");
			sb.append(getGetterSetterPropertyName(pPropertyName));
			try {
				return pClass.getMethod(sb.toString(), new Class[] { String.class });
			} catch (NoSuchMethodException ex) {
				logger.error("Class: " + pClass.getName() + " / property: "
						+ pPropertyName + " / " + sb.toString(), ex);
			}
			return null;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static Method findBlobSetterMethod(Class pClass, String pPropertyName) {
			StringBuffer sb = new StringBuffer();
			sb.append("set");
			sb.append(getGetterSetterPropertyName(pPropertyName));
			try {
				return pClass.getMethod(sb.toString(), new Class[] { byte[].class });
			} catch (NoSuchMethodException ex) {
				logger.error("Class: " + pClass.getName() + " / property: "
						+ pPropertyName + " / " + sb.toString(), ex);
			}
			return null;
		}
		
		public static String getGetterSetterPropertyName(String pPropertyName) {
			StringBuffer sb = new StringBuffer();
			sb.append(pPropertyName.length() > 0 ? pPropertyName
					.substring(0, 1).toUpperCase() : "");
			if (pPropertyName.length() > 1) {
				sb.append(pPropertyName.substring(1));
			}
			return sb.toString();
		}

		public boolean isClob() {
			return this.clob;
		}

		public void setClob(boolean pIsClob) {
			this.clob = pIsClob;
		}

		public boolean isBlob() {
			return blob;
		}

		public void setBlob(boolean blob) {
			this.blob = blob;
		}
	}

	public static class OracleColumn {
		/**
		 * Logger for this class
		 */
		private String daoFieldName;

		private String oracleFieldName;

		private String oracleSelectClause;

		private String oracleInsUpdValueClause;

		private String columnValue;

		private byte[] columnBlobValue;
		
		@SuppressWarnings("rawtypes")
		private Class propertyClass;
		
		/**
		 * @return Returns the columnValue.
		 */
		public String getColumnValue() {
			return this.columnValue;
		}

		/**
		 * @param pColumnValue
		 *            The columnValue to set.
		 */
		public void setColumnValue(String pColumnValue) {
			this.columnValue = pColumnValue;
		}

		public byte[] getColumnBlobValue() {
			return columnBlobValue;
		}

		public void setColumnBlobValue(byte[] blobValue) {
			this.columnBlobValue = blobValue;
		}

		/**
		 * @return Returns the oracleFieldName.
		 */
		public String getOracleFieldName() {
			return this.oracleFieldName;
		}

		/**
		 * @param pOracleFieldName
		 *            The oracleFieldName to set.
		 */
		public void setOracleFieldName(String pOracleFieldName) {
			this.oracleFieldName = pOracleFieldName;
		}

		/**
		 * @return Returns the daoFieldName.
		 */
		public String getDaoFieldName() {
			return this.daoFieldName;
		}

		/**
		 * @param pDaoFieldName
		 *            The daoFieldName to set.
		 */
		public void setDaoFieldName(String pDaoFieldName) {
			this.daoFieldName = pDaoFieldName;
		}

		/**
		 * @return Returns the oracleInsertUpdateClause.
		 */
		public String getOracleInsUpdValueClause() {
			return this.oracleInsUpdValueClause;
		}

		/**
		 * @param pOracleInsertUpdateClause
		 *            The oracleInsertUpdateClause to set.
		 */
		public void setOracleInsUpdValueClause(String pOracleInsertUpdateClause) {
			this.oracleInsUpdValueClause = pOracleInsertUpdateClause;
		}

		/**
		 * @return Returns the oracleSelectClause.
		 */
		public String getOracleSelectClause() {
			return this.oracleSelectClause;
		}

		/**
		 * @param pOracleSelectClause
		 *            The oracleSelectClause to set.
		 */
		public void setOracleSelectClause(String pOracleSelectClause) {
			this.oracleSelectClause = pOracleSelectClause;
		}

		@SuppressWarnings("rawtypes")
		public Class getPropertyClass() {
			return this.propertyClass;
		}

		@SuppressWarnings("rawtypes")
		public void setPropertyClass(Class pPropertyClass) {
			this.propertyClass = pPropertyClass;
		}
	}

	/**
	 * @param pBase
	 * @param pConnection
	 * @return
	 */
	public static boolean daoDoDelete(DaoBaseImpl pDao,
			ArrayList<String> pWhereColumns) throws Exception {
		DaoSql daoSql = buildDeleteStatement(pDao, pWhereColumns);
		return executeUpdateDelete(pDao, daoSql);
	}

	/**
	 * @param pDao
	 * @param pConnection
	 * @param pWhereColumns
	 * @return
	 */
	private static DaoSql buildDeleteStatement(DaoBaseImpl pDao,
			ArrayList<String> pWhereColumns) throws Exception {

		StringBuilder sbSql = new StringBuilder();
		DaoSql daoSql = new DaoSql();
		
		if (isExistMarkDelInd(pDao)) {
			sbSql.append("UPDATE ");
			sbSql.append(pDao.getTableName());
			sbSql.append(" SET MARK_DEL_IND = 'Y'");
			if (isExistTransType(pDao)) {
				sbSql.append(", TRANS_TYPE = 'D'");
			}
			if (isExistLastUpdDate(pDao)) {
				sbSql.append(", LAST_UPD_DATE = SYSDATE");
			}
		} else {
			sbSql.append("DELETE FROM ");
			sbSql.append(pDao.getTableName());
		}

		ArrayList<OracleColumn> oraWhereColumnList = new ArrayList<OracleColumn>();
		oraWhereColumnList = getSelUpdWhereFieldList(pDao, pWhereColumns, daoSql);

		OracleColumn whereColumn = null;
		if (oraWhereColumnList.size() > 0) {
			sbSql.append(" WHERE ");

			whereColumn = (OracleColumn) oraWhereColumnList.get(0);
			insertWhereField(whereColumn, sbSql);
			
			for (int i = 1; i < oraWhereColumnList.size(); i++) {
				whereColumn = (OracleColumn) oraWhereColumnList.get(i);
				sbSql.append(" AND ");
				insertWhereField(whereColumn, sbSql);
			}
		}

		ArrayList<String> bindingValueList = new ArrayList<String>();
		for (OracleColumn oraColumn : oraWhereColumnList) {
			if (oraColumn.getOracleInsUpdValueClause().indexOf("?") != -1) {
				if (StringUtils.isNotEmpty(oraColumn.getColumnValue())) {
					bindingValueList.add(oraColumn.getColumnValue());
				}
				if (OraBLOB.class.equals(oraColumn.getPropertyClass())) {
					daoSql.setBlobBindingValue(oraColumn.getColumnBlobValue());
				}
			}
		}
		daoSql.setSql(sbSql.toString());
		daoSql.setBindingValues(bindingValueList);

		return daoSql;
	}

	public static boolean isFieldExistInOracle(DaoBaseImpl pDao,
			String pOracleFieldName) {
		boolean isExist = false;
		ArrayList<OracleColumn> oraColumnList = getOracleColumnList(pDao);
		for (int i = 0; i < oraColumnList.size(); i++) {
			OracleColumn oraColumn = (OracleColumn) oraColumnList.get(i);
			if (StringUtils.equalsIgnoreCase(pOracleFieldName, oraColumn
					.getOracleFieldName())) {
				isExist = true;
				break;
			}
		}
		return isExist;
	}

	public static boolean isExistMarkDelInd(DaoBaseImpl pDao) {
		return isFieldExistInOracle(pDao, "MARK_DEL_IND");
	}

	public static boolean isExistTransType(DaoBaseImpl pDao) {
		return isFieldExistInOracle(pDao, "TRANS_TYPE");
	}

	public static boolean isExistLastUpdDate(DaoBaseImpl pDao) {
		return isFieldExistInOracle(pDao, "LAST_UPD_DATE");
	}

	public static String getDaoPropertyValue(DaoBase pDao, String pPropertyName) {
		Map<String, DaoProperty> propertyMap = DaoHelper.getPropertyMap(pDao.getClass());
		DaoHelper.DaoProperty daoProperty = (DaoHelper.DaoProperty) propertyMap
				.get(pPropertyName);
		if (daoProperty == null) {
			return null;
		}

		Method getter = daoProperty.getPropertyGetter();
		if (getter != null) {
			try {
				return (String) getter.invoke(pDao, (Object[]) null);
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}
	
	private static String getTableName(DaoBaseImpl pDao) {
		StringBuffer sbTableName = new StringBuffer();
		sbTableName.append(pDao.getTableName());
		if (pDao instanceof DaoBasePartitionImpl
				&& StringUtils.isNotBlank(((DaoBasePartitionImpl)pDao).getPartition())) {
			sbTableName.append(" partition(");
			sbTableName.append(((DaoBasePartitionImpl)pDao).getPartition());
			sbTableName.append(")");
		}
		return sbTableName.toString();
	}
	
	public static class DaoSql {
		private String sql;

		private ArrayList<OracleColumn> whereColumnList;
		
		private String[] bindingValues;
		
		private String sqlWhere;
		
		private String[] whereBindingValues;
		
		private byte[] blobBindingValue;
		
		private boolean checkOraRowscn;
		
		private boolean updateByRowId;
		
		private boolean updateByPrimaryKey;

		private int clobBindingValuesIndex = -1;
		
		private int blobBindingValuesIndex = -1;
		
		public String getSql() {
			return this.sql;
		}

		public void setSql(String pSql) {
			this.sql = pSql;
		}
		
		public String getDebugSql() {
			StringBuilder sbDebugSql = new StringBuilder(this.sql);
			if (ArrayUtils.isEmpty(this.bindingValues)) {
				return sbDebugSql.toString();
			}
			sbDebugSql.append("\nBinding Values:\n");
			for (String bindingValue : this.bindingValues) {
				sbDebugSql.append("\"");
				sbDebugSql.append(bindingValue);
				sbDebugSql.append("\"\n");
			}
			
			return sbDebugSql.toString();
		}

		public String[] getBindingValues() {
			return this.bindingValues;
		}

		public void setBindingValues(String[] pBindingValues) {
			this.bindingValues = pBindingValues;
		}

		public void setBindingValues(List<String> pBindingValueList) {
			this.bindingValues = pBindingValueList.toArray(new String[0]);
		}

		public void setWhereBindingValues(List<String> pBindingValueList) {
			this.whereBindingValues = pBindingValueList.toArray(new String[0]);
		}

		public boolean isUpdateByRowId() {
			return this.updateByRowId;
		}

		public void setUpdateByRowId(boolean pUpdateByRowId) {
			this.updateByRowId = pUpdateByRowId;
		}

		public boolean isUpdateByPrimaryKey() {
			return this.updateByPrimaryKey;
		}

		public void setUpdateByPrimaryKey(boolean pUpdateByPrimaryKey) {
			this.updateByPrimaryKey = pUpdateByPrimaryKey;
		}

		public ArrayList<OracleColumn> getWhereColumnList() {
			return this.whereColumnList;
		}

		public void setWhereColumnList(ArrayList<OracleColumn> pWhereColumnList) {
			this.whereColumnList = pWhereColumnList;
		}

		public int getClobBindingValuesIndex() {
			return this.clobBindingValuesIndex;
		}

		public void setClobBindingValuesIndex(int pClobBindingValuesIndex) {
			this.clobBindingValuesIndex = pClobBindingValuesIndex;
		}

		public int getBlobBindingValuesIndex() {
			return blobBindingValuesIndex;
		}

		public void setBlobBindingValuesIndex(int blobBindingValuesIndex) {
			this.blobBindingValuesIndex = blobBindingValuesIndex;
		}

		public byte[] getBlobBindingValue() {
			return blobBindingValue;
		}

		public void setBlobBindingValue(byte[] blobBindingValue) {
			this.blobBindingValue = blobBindingValue;
		}

		public boolean isCheckOraRowscn() {
			return this.checkOraRowscn;
		}

		public void setCheckOraRowscn(boolean pContainOraRowscn) {
			this.checkOraRowscn = pContainOraRowscn;
		}

		public String getSqlWhere() {
			return this.sqlWhere;
		}

		public void setSqlWhere(String pSqlWhere) {
			this.sqlWhere = pSqlWhere;
		}

		public String[] getWhereBindingValues() {
			return this.whereBindingValues;
		}

		public void setWhereBindingValues(String[] pWhereBindingValues) {
			this.whereBindingValues = pWhereBindingValues;
		}
	}	
	
	private static String sqlReplacePatten = 
			  "=\\s*([0-9]+)|"            //=123123
			+ "=\\s*'([^']*)'|"           //='asdasd'
			+ "<>\\s*([0-9]+)|"           //<>123123
			+ "<>\\s*'([^']*)'|"          //<>'asdasd'
			+ "to_date\\([^\\(]*?\\)|"    
			+ "to_number\\(\\s*([0-9]+)\\s*\\)|"
			+ "to_number\\(\\s*'([^']*)'\\s*\\)|"
			+ "to_char\\(\\s*([0-9]+)\\s*\\)|"
			+ "to_char\\(\\s*'([^']*)'\\s*\\)|"
			+ "like\\s*'([^']*)'|"
			+ "in\\s*\\([^\\(]*?\\)";

		
		public static void convertSqlConstant2BindingValues(StringBuffer pSbSql, List<String> pBindingParamList) {
			String sql = pSbSql.toString();
			Stack<Param> params = new Stack<Param>();
			Stack<String> values = new Stack<String>();
			
			Pattern p = Pattern.compile(sqlReplacePatten, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(sql);
			
			while (m.find()) {
				params.push(new Param(m.group(), m.start()));   //for backward process
				//System.out.println(m.group());
			}

			Param param = null;
			List<String> replacedStrList = null;
			//System.out.println(sql);
			while (!params.empty()) {
				param = (Param) params.pop();
				replacedStrList = processConvertSqlConstant2BindingValues(pSbSql, param);
				if (replacedStrList != null) {
					for (int i = 0; i < replacedStrList.size();i++) {
						values.push(replacedStrList.get(i));
					}
				}
			}
			//System.out.println(sb.toString());
			while (!values.empty()) {
				pBindingParamList.add(values.pop());
			}
		}
		
		private static List<String> processConvertSqlConstant2BindingValues(StringBuffer pSbSql, Param pParam) {
			String str = pParam.str;
			String patten = null;
			String value = null;
			List<String> returnList = new ArrayList<String>();
			
			if (str.charAt(0) == '=') {
				// return if 1=1
				if (isDummyCondition(pSbSql.toString(), pParam.start)) {
					return null;
				}
				patten = "([0-9]+)|'([^']*)'";
			} else if (str.charAt(0) == '<' && str.charAt(1) == '>') {
				patten = "([0-9]+)|'([^']*)'";
			} else if (str.toLowerCase().startsWith("to_date")) {
				if (StringUtils.contains(str.toUpperCase(), "NULL")) {
					return null;
				}
				patten = "'[0-9|:|\\s|.|/]+'";
			} else if (str.toLowerCase().startsWith("to_number")) {
				patten = "'([^']*)'|\\(\\s*([0-9]+)\\s*\\)";
			} else if (str.toLowerCase().startsWith("like")) {
				patten = "'([^']*)'";
			} else if (str.toLowerCase().startsWith("to_char")) {
				patten = "\\(\\s*[0-9]+\\s*\\)|'([^']*)'";
			} else if (str.toLowerCase().startsWith("in")) {
				patten = "'([^']*)'|[0-9]+";
			}
			Pattern p = Pattern.compile(patten, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(str);
			Stack<Param> backward = new Stack<Param>();
			while (m.find()) {
				value = m.group();
				backward.push(new Param(value, m.start()));
			}
			Param b = null;
			String bindingValue = null;
			while (!backward.empty()) {
				b = (Param) backward.pop();
				bindingValue = b.str;
				if (StringUtils.startsWith(bindingValue, "'") && StringUtils.endsWith(bindingValue, "'")) {
					bindingValue = StringUtils.substring(bindingValue, 1, bindingValue.length() - 1);
				}
				else if (StringUtils.startsWith(bindingValue, "(") && StringUtils.endsWith(bindingValue, ")"))
				{
					bindingValue = StringUtils.substring(bindingValue, 1, bindingValue.length() - 1);
					bindingValue = bindingValue.trim();//trim if not string
				}
				else {
					bindingValue = bindingValue.trim();//trim if not string
				}
				returnList.add(bindingValue);
				if (StringUtils.startsWith(b.str, "(") && StringUtils.endsWith(b.str, ")")) {
					pSbSql.replace(pParam.start + b.start, pParam.start + b.start + b.str.length(), "(?)");
				} else {
					pSbSql.replace(pParam.start + b.start, pParam.start + b.start + b.str.length(), "?");
				}

			}
			// process backward
			return returnList;
		}
		
		//search 1 =
		public static boolean isDummyCondition(String pString, int pStart) {
			int conditionStart = 0;
			if(pString.charAt(pStart-1)=='!')
			{
				pStart--;// if operator is !=
			}
			String condition = StringUtils.trim(StringUtils.substring(pString, 0, pStart));
			for (int i = condition.length() - 1; i > 0; i--) {
				if (condition.charAt(i) == ' ' || condition.charAt(i) == '\n' || condition.charAt(i) == '\t') {
					conditionStart = i+1;
					break;
				}
			}
			
			String conditionLeft = pString.substring(conditionStart, pStart);
			if (StringUtils.endsWith(conditionLeft, "!") || StringUtils.endsWith(conditionLeft, "<") || StringUtils.endsWith(conditionLeft, ">")) {
				conditionLeft = StringUtils.trim(StringUtils.substring(conditionLeft, 0, conditionLeft.length() - 1));
			}
			if (StringUtils.isNumeric(StringUtils.trim(conditionLeft))) {
				return true;
			}
			return false;
		}
		
		static class Param {
			public String str;
			public int start;
			public Param(String pStr, int pStart) {
				super();
				this.str = pStr;
				this.start = pStart;
			}
		}
}