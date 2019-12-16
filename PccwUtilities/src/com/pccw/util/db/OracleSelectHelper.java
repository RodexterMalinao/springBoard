package com.pccw.util.db;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeSet;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.support.lob.DefaultLobHandler;

import com.pccw.util.db.DaoHelper.DaoProperty;
import com.pccw.util.db.stringOracleType.OraArray;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraNumber;
import com.pccw.util.spring.SpringApplicationContext;

public class OracleSelectHelper extends OracleHelperBase {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(OracleSelectHelper.class);

	protected String sqlStatement;

	protected Object[] bindingValues;

	protected Statement statement;

	protected ResultSet resultSet;

	protected ResultSetMetaData resultSetMetaData;

	protected Connection conn = null;

	/**
	 * 
	 * @param pOracleSelectHelper
	 */
	public static void close(OracleSelectHelper pOracleSelectHelper) {
		if (pOracleSelectHelper != null) {
			pOracleSelectHelper.clear();
		}
	}

	private static OracleSelectHelper getInstance(DataSource pDataSource,
			String pSql, Object[] pBindingValues) {

		OracleSelectHelper selectHelper = SpringApplicationContext
				.getBean("OracleSelectHelper");
		selectHelper.setDataSource(pDataSource);
		selectHelper.setSQLStatement(pSql);
		selectHelper.setBindingValues(pBindingValues);
		return selectHelper;
	}

	private static OracleSelectHelper getInstance(String pDataSourceName,
			String pSql, Object[] pBindingValues) {

		OracleSelectHelper selectHelper = SpringApplicationContext
				.getBean("OracleSelectHelper-" + pDataSourceName);
		selectHelper.setSQLStatement(pSql);
		selectHelper.setBindingValues(pBindingValues);
		return selectHelper;
	}

	/**
	 * 
	 * @param pConnection
	 * @param pSql
	 * @return
	 * @throws BOMException
	 * 
	 *             Get the results of the first column for pSQL
	 */
	public static String[] getSqlFirstColumnStrings(String pDataSourceName,
			String pSql) throws Exception {
		return getSqlFirstColumnStrings(pDataSourceName, pSql, null);
	}

	/**
	 * 
	 * @param pDataSource
	 * @param pSql
	 * @param pBindingValues
	 * @return
	 * @throws Exception
	 */
	public static String[] getSqlFirstColumnStrings(DataSource pDataSource,
			String pSql, Object[] pBindingValues) throws Exception {
		return getSqlColumnStrings(pDataSource, pSql, pBindingValues, 1);
	}

	/**
	 * 
	 * @param pConnection
	 * @param pSql
	 * @param pBindingValues
	 * @return
	 * @throws BOMException
	 * 
	 *             Get the results of the first column for pSQL
	 */
	public static String[] getSqlFirstColumnStrings(String pDataSourceName,
			String pSql, Object[] pBindingValues) throws Exception {
		return getSqlColumnStrings(pDataSourceName, pSql, pBindingValues, 1);
	}

	/**
	 * 
	 * @param pDataSource
	 * @param pSql
	 * @param pBindingValues
	 * @param pRsColumnIdx
	 * @return
	 * @throws Exception
	 */
	public static String[] getSqlColumnStrings(DataSource pDataSource,
			String pSql, Object[] pBindingValues, int pRsColumnIdx)
			throws Exception {
		return getSqlColumnStrings(
				getInstance(pDataSource, pSql, pBindingValues), pSql,
				pBindingValues, pRsColumnIdx);
	}

	/**
	 * 
	 * @param pDataSourceName
	 * @param pSql
	 * @param pBindingValues
	 * @param pRsColumnIdx
	 * @return
	 * @throws BOMException
	 * 
	 *             Get the results of the (pRsColumnIdx)th column for pSQL
	 */
	public static String[] getSqlColumnStrings(String pDataSourceName,
			String pSql, Object[] pBindingValues, int pRsColumnIdx)
			throws Exception {
		return getSqlColumnStrings(
				getInstance(pDataSourceName, pSql, pBindingValues), pSql,
				pBindingValues, pRsColumnIdx);
	}

	private static String[] getSqlColumnStrings(
			OracleSelectHelper pSelectHelper, String pSql,
			Object[] pBindingValues, int pRsColumnIdx) throws Exception {
		try {
			pSelectHelper.executeSQL();
			return OracleSelectHelper.getColumnValues(
					pSelectHelper.getResultSet(), 1);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		} finally {
			pSelectHelper.clear();
		}
	}

	/**
	 * 
	 * @param pDataSource
	 * @param pSql
	 * @return
	 * @throws Exception
	 */
	public static String getSqlFirstRowColumnString(DataSource pDataSource,
			String pSql) throws Exception {
		return getSqlFirstRowColumnString(pDataSource, pSql, null);
	}

	/**
	 * 
	 * @param pDataSourceName
	 * @param pSql
	 * @return
	 * @throws Exception
	 */
	public static String getSqlFirstRowColumnString(String pDataSourceName,
			String pSql) throws Exception {
		return getSqlFirstRowColumnString(pDataSourceName, pSql, null);
	}

	/**
	 * 
	 * @param pDataSourceName
	 * @param pSql
	 * @param pBindingValues
	 * @return
	 * @throws Exception
	 */
	public static String getSqlFirstRowColumnString(String pDataSourceName,
			String pSql, Object[] pBindingValues) throws Exception {
		return getSqlFirstRowColumnString(getInstance(pDataSourceName, pSql,
				pBindingValues));

	}

	/**
	 * 
	 * @param pDataSource
	 * @param pSql
	 * @param pBindingValues
	 * @return
	 * @throws Exception
	 */
	public static String getSqlFirstRowColumnString(DataSource pDataSource,
			String pSql, Object[] pBindingValues) throws Exception {
		return getSqlFirstRowColumnString(getInstance(pDataSource, pSql,
				pBindingValues));

	}

	private static String getSqlFirstRowColumnString(
			OracleSelectHelper pSelectHelper) throws Exception {
		try {
			pSelectHelper.executeSQL();
			return OracleSelectHelper.getFirstRowColumnValue(pSelectHelper
					.getResultSet());
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		} finally {
			pSelectHelper.clear();
		}
	}

	/**
	 * 
	 * @param pDataSourceName
	 * @param pSql
	 * @param pClass
	 * @return
	 * @throws Exception
	 */
	public static <T> T[] getSqlResultObjects(String pDataSourceName,
			String pSql, Class<T> pClass) throws Exception {
		return getSqlResultObjects(pDataSourceName, pSql, null, pClass);
	}

	/**
	 * 
	 * @param pDataSource
	 * @param pSql
	 * @param pClass
	 * @return
	 * @throws Exception
	 */
	public static <T> T[] getSqlResultObjects(DataSource pDataSource,
			String pSql, Class<T> pClass) throws Exception {
		return getSqlResultObjects(pDataSource, pSql, null, pClass);
	}

	/**
	 * 
	 * @param pDataSourceName
	 * @param pSql
	 * @param pBindingValues
	 * @param pClass
	 * @return
	 * @throws Exception
	 */
	public static <T> T[] getSqlResultObjects(String pDataSourceName,
			String pSql, Object[] pBindingValues, Class<T> pClass)
			throws Exception {
		return getSqlResultObjects(
				getInstance(pDataSourceName, pSql, pBindingValues), pClass);
	}

	/**
	 * 
	 * @param pDataSource
	 * @param pSql
	 * @param pBindingValues
	 * @param pClass
	 * @return
	 * @throws Exception
	 */
	public static <T> T[] getSqlResultObjects(DataSource pDataSource,
			String pSql, Object[] pBindingValues, Class<T> pClass)
			throws Exception {
		return getSqlResultObjects(
				getInstance(pDataSource, pSql, pBindingValues), pClass);
	}

	private static <T> T[] getSqlResultObjects(
			OracleSelectHelper pSelectHelper, Class<T> pClass) throws Exception {
		try {
			pSelectHelper.executeSQL();
			return OracleSelectHelper.getResultSetObjects(pSelectHelper
					.getResultSet(),
					pSelectHelper.getResultSet().getMetaData(), pClass);

		} catch (Exception e) {
			logger.error(e);
			throw e;
		} finally {
			pSelectHelper.clear();
		}
	}

	/**
     *  
     */
	public OracleSelectHelper() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void clear() {
		this.sqlStatement = null;
		try {
			if (this.resultSet != null) {
				this.resultSet.close();
			}
			if (this.statement != null) {
				this.statement.close();
			}
			if (this.conn != null) {
				this.releaseConnection(this.conn);
				this.conn = null;
			}
		} catch (SQLException ex) {
			this.errMsg = ex.toString() + "\n"
					+ ExceptionUtils.getFullStackTrace(ex);
			logger.error(ex);
		}

		this.sqlStatement = null;
		this.resultSet = null;
		this.resultSetMetaData = null;
	}

	public void setSQLStatement(String psSQLStatement) {
		sqlStatement = psSQLStatement;
	}

	public String getSQLStatement() {
		return sqlStatement;
	}

	public void executeSQL() throws Exception {

		if (this.conn == null) {
			this.conn = this.getConnection();
		}

		if (this.conn == null) {
			this.errMsg = "Can't establish DB connection";
			throw new UncategorizedSQLException(null, null, new SQLException(
					this.errMsg));
		}

		try {
			if (isBindingValueExists(this.sqlStatement)) {
				this.statement = this.conn.prepareStatement(this.sqlStatement);
				setBindingValues(this.conn, this.statement, this.bindingValues);
				this.resultSet = ((PreparedStatement) this.statement)
						.executeQuery();
			} else {
				this.statement = this.conn.createStatement();
				this.resultSet = this.statement.executeQuery(this.sqlStatement);
			}
			this.resultSetMetaData = this.resultSet.getMetaData();
		} catch (Exception ex) {
			this.errMsg = this.sqlStatement + "\n" + this.printBindingValues()
					+ "\n" + ExceptionUtils.getFullStackTrace(ex);
			logger.error(this.errMsg);
			throw ex;
		}
	}

	public String printBindingValues() {
		return printBindingValues(this.bindingValues);
	}

	/**
	 * Print out the binding values for debug
	 * 
	 * @param pBindingValuesAL
	 * @return
	 */
	public static String printBindingValues(ArrayList<Object> pBindingValuesAL) {
		return printBindingValues(pBindingValuesAL.toArray());
	}

	/**
	 * Print out the binding values for debug
	 * 
	 * @param pBindingValues
	 * @return
	 */
	public static String printBindingValues(Object[] pBindingValues) {
		if (ArrayUtils.isEmpty(pBindingValues)) {
			return "";
		}
		StringBuffer sb = new StringBuffer("Binding Values:");
		for (int i = 0; i < pBindingValues.length; i++) {
			sb.append("\n");
			sb.append("(");
			sb.append(i);
			sb.append(") ");
			if (pBindingValues[i] instanceof String
					|| pBindingValues[i] == null) {
				sb.append("String");
			} else if (pBindingValues[i] instanceof Boolean) {
				sb.append("Boolean");
			} else if (pBindingValues[i] instanceof Integer) {
				sb.append("Integer");
			} else if (pBindingValues[i] instanceof java.sql.Date) {
				sb.append("java.sql.Date");
			} else if (pBindingValues[i] instanceof java.sql.Time) {
				sb.append("java.sql.Time");
			} else if (pBindingValues[i] instanceof Timestamp) {
				sb.append("Timestamp");
			} else if (pBindingValues[i] instanceof Double) {
				sb.append("Double");
			} else if (pBindingValues[i] instanceof Float) {
				sb.append("Float");
			} else if (pBindingValues[i] instanceof Byte) {
				sb.append("Byte");
			} else if (pBindingValues[i] instanceof byte[]) {
				sb.append("byte[]");
			} else if (pBindingValues[i] instanceof Long) {
				sb.append("Long");
			} else if (pBindingValues[i] instanceof Short) {
				sb.append("Short");
			} else if (pBindingValues[i] instanceof BigDecimal) {
				sb.append("BigDecimal");
			} else if (pBindingValues[i] instanceof Ref) {
				sb.append("Ref");
			} else if (pBindingValues[i] instanceof Clob) {
				sb.append("Clob");
			} else if (pBindingValues[i] instanceof java.sql.Array) {
				sb.append("java.sql.Array");
			} else {
				sb.append(pBindingValues[i].getClass().getName());
			}
			sb.append(":");
			sb.append(pBindingValues[i] == null ? "NULL" : String
					.valueOf(pBindingValues[i]));
		}
		return sb.toString();
	}

	public static boolean isBindingValueExists(String pSqlStatement) {
		// see if there are any '?' in the statement that are not bind variables
		// and filter them out.
		boolean isString = false;
		char[] sqlString = pSqlStatement.toCharArray();
		for (int i = 0; i < sqlString.length; i++) {
			if (sqlString[i] == '\'') {
				isString = !isString;
			}

			if (sqlString[i] == '?' && !isString) {
				return true;
			}
		}
		return false;
	}

	public void setBindingValues(Object[] pBindingValues) {
		this.bindingValues = pBindingValues;
	}

	/**
	 * Helper to set binding variables to PreparedStatement
	 * 
	 * @param pConnection
	 * @param pStatement
	 * @param pBindingValues
	 * @throws SQLException
	 */
	public static void setBindingValues(Connection pConnection,
			Statement pStatement, Object[] pBindingValues) throws SQLException {
		setBindingValues(pConnection, pStatement, pBindingValues, null);
	}

	/**
	 * Helper to set binding variables to PreparedStatement
	 * 
	 * @param pConnection
	 * @param pStatement
	 * @param pBindingValues
	 * @param pSbDebug
	 * @throws SQLException
	 */
	public static void setBindingValues(Connection pConnection,
			Statement pStatement, Object[] pBindingValues, StringBuffer pSbDebug)
			throws SQLException {
		if (ArrayUtils.isEmpty(pBindingValues)) {
			return;
		}

		if (!(pStatement instanceof PreparedStatement)) {
			return;
		}

		for (int i = 0; i < pBindingValues.length; i++) {
			setBindingValue(pConnection, pStatement, i + 1, pBindingValues[i], pSbDebug);
		}
	}
	
	/**
	 * Helper to set binding variables to PreparedStatement
	 * 
	 * @param pConnection
	 * @param pStatement
	 * @param pBindingValues
	 * @param pSbDebug
	 * @throws SQLException
	 */
	public static void setBindingValue(Connection pConnection,
			Statement pStatement, int pPosition, Object pBindingValue, StringBuffer pSbDebug)
			throws SQLException {
		if (!(pStatement instanceof PreparedStatement)) {
			return;
		}

		PreparedStatement prepStmt = (PreparedStatement) pStatement;
		if (pSbDebug != null) {
			pSbDebug.append(":"	+ (pBindingValue == null ? null : pBindingValue.toString()) + ":" + "\n");
		}
		if (pBindingValue instanceof OraArray) {
			prepStmt.setArray(pPosition, ((OraArray) pBindingValue).getOracleArray(pConnection));
		} else if ((pBindingValue == null) || (pBindingValue instanceof String)) {
			prepStmt.setString(pPosition, StringUtils.defaultIfEmpty((String) pBindingValue, ""));
		} else if (pBindingValue instanceof OraDate) {
			prepStmt.setString(pPosition, ((OraDate) pBindingValue).getValue());
		} else if (pBindingValue instanceof OraNumber) {
			prepStmt.setString(pPosition, ((OraNumber) pBindingValue).getValue());
		} else if (pBindingValue instanceof Boolean) {
			prepStmt.setBoolean(pPosition, ((Boolean) pBindingValue).booleanValue());
		} else if (pBindingValue instanceof Integer) {
			prepStmt.setInt(pPosition, ((Integer) pBindingValue).intValue());
		} else if (pBindingValue instanceof java.sql.Date) {
			prepStmt.setDate(pPosition, (java.sql.Date) pBindingValue);
		} else if (pBindingValue instanceof java.sql.Time) {
			prepStmt.setTime(pPosition, (java.sql.Time) pBindingValue);
		} else if (pBindingValue instanceof Timestamp) {
			prepStmt.setTimestamp(pPosition, (Timestamp) pBindingValue);
		} else if (pBindingValue instanceof Double) {
			prepStmt.setDouble(pPosition, ((Double) pBindingValue).doubleValue());
		} else if (pBindingValue instanceof Float) {
			prepStmt.setFloat(pPosition, ((Float) pBindingValue).floatValue());
		} else if (pBindingValue instanceof Byte) {
			prepStmt.setByte(pPosition, ((Byte) pBindingValue).byteValue());
		} else if (pBindingValue instanceof byte[]) {
			prepStmt.setBytes(pPosition, (byte[]) pBindingValue);
		} else if (pBindingValue instanceof Long) {
			prepStmt.setLong(pPosition, ((Long) pBindingValue).longValue());
		} else if (pBindingValue instanceof Short) {
			prepStmt.setShort(pPosition, ((Short) pBindingValue).shortValue());
		} else if (pBindingValue instanceof BigDecimal) {
			prepStmt.setBigDecimal(pPosition, (BigDecimal) pBindingValue);
		} else if (pBindingValue instanceof Ref) {
			prepStmt.setRef(pPosition, (Ref) pBindingValue);
		} else if (pBindingValue instanceof Clob) {
			prepStmt.setClob(pPosition, (Clob) pBindingValue);
		} else if (pBindingValue instanceof java.sql.Array) {
			prepStmt.setArray(pPosition, (java.sql.Array) pBindingValue);
		} else {
			prepStmt.setObject(pPosition, pBindingValue);
		}
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public ResultSetMetaData getResultSetMetaData() {
		return resultSetMetaData;
	}

	public int getColumnCount() {
		return getColumnCount(this.resultSetMetaData);
	}

	public static int getColumnCount(ResultSetMetaData pResultSetMetaData) {
		try {
			return pResultSetMetaData.getColumnCount();
		} catch (SQLException ex) {
			logger.error("SQLException: ", ex);
			return -1;
		}
	}

	public String getColumnName(int pColumnIndex) {
		return getColumnName(this.resultSetMetaData, pColumnIndex);
	}

	public static String getColumnName(ResultSetMetaData pResultSetMetaData,
			int pColumnIndex) {
		try {
			return pResultSetMetaData.getColumnName(pColumnIndex);
		} catch (SQLException ex) {
			logger.error("SQLException: ", ex);
			return null;
		}
	}

	public int getColumnIndex(String pColumnName) {
		return getColumnIndex(this.resultSetMetaData, pColumnName);
	}

	public static int getColumnIndex(ResultSetMetaData pResultSetMetaData,
			String pColumnName) {
		try {
			for (int colCtr = 0; colCtr < pResultSetMetaData.getColumnCount(); colCtr++) {
				if (StringUtils.defaultIfEmpty(
						pResultSetMetaData.getColumnName(colCtr), "").equals(
						pColumnName)) {
					return colCtr;
				}
			}
		} catch (SQLException oSQLException) {
			return -1;
		}

		return -1;
	}

	/**
	 * 
	 * @param pClass
	 * @return
	 */
	public <T> T[] getResultSetObjects(Class<T> pClass) {
		return getResultSetObjects(this.resultSet, this.resultSetMetaData,
				pClass);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList<String> getColumnPropertyMappingList(
			ResultSetMetaData pResultSetMetaData, Class pClass) {
		ArrayList<String> columnPropertyMappingList = new ArrayList<String>();

		TreeSet<String> propertySet = new TreeSet<String>();
		boolean isDao = false;

		try {
			isDao = (pClass.newInstance() instanceof DaoBaseImpl);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}

		if (isDao) {
			Map<String, DaoProperty> propertyMap = DaoHelper
					.getPropertyMap(pClass);
			propertyMap.put("oracleRowID", DaoHelper.getDaoPropertyRowID());
			propertySet.addAll(propertyMap.keySet());
		} else {
			try {
				Map propertyMap = BeanUtils.describe(pClass.newInstance());
				propertySet.addAll(propertyMap.keySet());
			} catch (Exception e) {
				logger.error("SQLException", e);
			}
		}
		for (int iCol = 1; iCol <= getColumnCount(pResultSetMetaData); iCol++) {
			if (columnPropertyMappingList.size() <= (iCol - 1)) {
				columnPropertyMappingList
						.add(DaoHelper.isDaoContainsProperty(propertySet,
								getColumnName(pResultSetMetaData, iCol)) ? getColumnName(
								pResultSetMetaData, iCol) : null);
			}
		}
		return columnPropertyMappingList;
	}

	@SuppressWarnings("unchecked")
	public static <T>T getResultSetObject(ResultSet pResultSet,
			ResultSetMetaData pResultSetMetaData, Class<T> pClass)
			throws Exception {
		return (T) getResultSetRowObject(
				getColumnPropertyMappingList(pResultSetMetaData, pClass), pResultSet, pResultSetMetaData,
				pClass.newInstance());
	}
	
	public static Object getResultSetObject(ResultSet pResultSet,
			ResultSetMetaData pResultSetMetaData, Object pObject)
			throws Exception {
		return getResultSetRowObject(
				getColumnPropertyMappingList(pResultSetMetaData,
						pObject.getClass()), pResultSet, pResultSetMetaData,
				pObject);
	}

	@SuppressWarnings("unchecked")
	public <T>T getResultSet1stRowObject(Class<T> pClass) throws Exception {
		try {
			if (!this.resultSet.next()) {
				return null;
			}
			return (T) getResultSetObject(this.resultSet, this.resultSetMetaData, pClass.newInstance());
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}

	public static Object getResultSetRowObject(
			ArrayList<String> pColumnPropertyMappingList, ResultSet pResultSet,
			ResultSetMetaData pResultSetMetaData, Object pRowObject)
			throws Exception {

		try {

			Map<String, DaoProperty> propertyMap = null;

			if (pRowObject instanceof DaoBaseImpl) {
				propertyMap = DaoHelper.getPropertyMap(pRowObject.getClass());
				propertyMap.put("oracleRowID", DaoHelper.getDaoPropertyRowID());
			}

			// MUST PROCESS LONG DATA TYPE FIRST
			String oraColumnType = null;
			DefaultLobHandler lobHandler = new DefaultLobHandler();
			Object value = null;
			for (int iCol = 1; iCol <= getColumnCount(pResultSetMetaData); iCol++) {
				oraColumnType = pResultSetMetaData.getColumnTypeName(iCol);
				if (!"LONG".equals(oraColumnType)) {
					continue;
				}

				value = lobHandler.getClobAsString(pResultSet, iCol);
				setColumnValue(
						pRowObject, 
						propertyMap,
						(pRowObject instanceof Map) ? getColumnName(pResultSetMetaData, iCol) : pColumnPropertyMappingList.get(iCol - 1), 
						value);
			}
			
			for (int iCol = 1; iCol <= getColumnCount(pResultSetMetaData); iCol++) {

				oraColumnType = pResultSetMetaData.getColumnTypeName(iCol);
				if ("LONG".equals(oraColumnType)) {
					continue;
				}
				
				if (pResultSetMetaData.getColumnType(iCol) == java.sql.Types.CLOB) {
					value = lobHandler.getClobAsString(pResultSet, iCol);
				} else if (pResultSetMetaData.getColumnType(iCol) == java.sql.Types.BLOB) {
					value = lobHandler.getBlobAsBytes(pResultSet, iCol);
				} else {
					value = pResultSet.getString(iCol);
				}

				setColumnValue(
						pRowObject,
						propertyMap,
						(pRowObject instanceof Map) ? getColumnName(pResultSetMetaData, iCol) : pColumnPropertyMappingList.get(iCol - 1), 
						value);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}

		return pRowObject;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void setColumnValue(Object pRowObject,
			Map<String, DaoProperty> propertyMap, String pColumnName,
			Object pValue) throws Exception {

		if (StringUtils.isEmpty(pColumnName)) {
			return;
		}
		
		if (pRowObject instanceof Map) {
			((Map) pRowObject).put(pColumnName, pValue);
			return;
		}
		
		if (pValue == null) {
			return;
		}

		if (pRowObject instanceof DaoBaseImpl) {
			DaoHelper.DaoProperty daoProperty = DaoHelper.getDaoProperty(propertyMap, pColumnName);
			if (daoProperty == null) {
				return;
			}

			Method setter = daoProperty.getPropertySetter();
			if (setter != null) {
				setter.invoke(pRowObject, new Object[] { pValue });
			}
		} else {
			BeanUtils.setProperty(pRowObject, pColumnName, pValue);
		}
	}
	
	public static void fillObjectWithResultSetFirstRow(ResultSet pResultSet,
			ResultSetMetaData pResultSetMetaData, Object pObject) {
		ArrayList<String> columnPropertyMappingList = getColumnPropertyMappingList(
				pResultSetMetaData, pObject.getClass());
		try {
			if (pResultSet.next()) {
				getResultSetRowObject(columnPropertyMappingList, pResultSet,
						pResultSetMetaData, pObject);
			}
		} catch (SQLException e) {
			logger.error("SQLException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
	}

	public static <T> T[] getResultSetObjects(ResultSet pResultSet,
			ResultSetMetaData pResultSetMetaData, Class<T> pRequiredClass) {
		ArrayList<T> rsObjectList = new ArrayList<T>();
		try {

			T rowObj = pRequiredClass.newInstance();

			ArrayList<String> columnPropertyMappingList = getColumnPropertyMappingList(
					pResultSetMetaData, pRequiredClass);

			while (pResultSet.next()) {
				rowObj = pRequiredClass.newInstance();

				getResultSetRowObject(columnPropertyMappingList, pResultSet,
						pResultSetMetaData, rowObj);

				rsObjectList.add(rowObj);
			}
		} catch (SQLException e) {
			logger.error("SQLException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		@SuppressWarnings("unchecked")
		T[] rtnArray = (T[]) Array.newInstance(pRequiredClass,
				rsObjectList.size());
		return rsObjectList.toArray(rtnArray);
	}

	public static String getFirstRowColumnValue(ResultSet pResultSet) {
		try {
			if (pResultSet.next()) {
				return pResultSet.getString(1);
			}
		} catch (SQLException e) {
			logger.error("getFirstRowColumnValue() - SQLException", e);
		}
		return null;
	}

	public static String[] getFirstColumnValues(ResultSet pResultSet) {
		return getColumnValues(pResultSet, 1);
	}

	public static String[] getColumnValues(ResultSet pResultSet,
			int pRsColumnIndex) {
		ArrayList<String> rtnValueList = new ArrayList<String>();
		try {
			while (pResultSet.next()) {
				rtnValueList.add(pResultSet.getString(pRsColumnIndex));
			}
		} catch (SQLException e) {
			logger.error("getFirstRowColumnValue() - SQLException", e);
		}
		return (String[]) rtnValueList.toArray(new String[0]);
	}
}