/*
 * Created on July 09, 2015
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bomwebportal.lts.dao.order;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

import com.bomwebportal.dao.BaseDAO;

public class FormPrintReqDAO extends BaseDAO {
	
	private static final String INSERT_RECORD =
		"INSERT INTO BOMWEB_FORM_PRINT_REQ " +
		"(ORDER_ID," + 
		" PRINT_REQ_TYPE," + 
		" POSTAL_ADDR_LINE_1, " +
		" POSTAL_ADDR_LINE_2, " +
		" POSTAL_ADDR_LINE_3, " +
		" POSTAL_ADDR_LINE_4, " +
		" POSTAL_ADDR_LINE_5, " +
		" POSTAL_ADDR_LINE_6, " +
		" EMAIL_CONTENT, " + 
		" EMAIL_SUBJECT, " + 
		" EMAIL_FROM, " + 
		" SMS_CONTENT, " +
		" CREATE_BY, " +
		" LAST_UPD_BY) " +
		" VALUES " +
		" (?,?,?,?,?, ?,?,?,?,?, ?,?,?,? ) RETURNING PRINT_REQ_ID INTO ?";

	public long insertRecord(final String pOrderId, final String pPrintReqType,
			final String pPostalAddrLine1, final String pPostalAddrLine2,
			final String pPostalAddrLine3, final String pPostalAddrLine4,
			final String pPostalAddrLine5, final String pPostalAddrLine6,
			final String pEmailContent, final String pEmailSubject,
			final String pEmailFrom, final String pSmsContent,
			final String pCreateBy, final String pLastUpdBy) {
									
		    Long retPrintReqId =(Long)jdbcTemplate.execute(
			    new CallableStatementCreator() {
				    LobHandler lobHandler = new DefaultLobHandler();
			    	
			        public CallableStatement createCallableStatement(Connection con) throws SQLException {
			            CallableStatement cs = con.prepareCall("{call " + INSERT_RECORD + "}");
			            cs.setString(1,  pOrderId); 
			            cs.setString(2,  pPrintReqType); 
			            cs.setString(3,  pPostalAddrLine1);
			            cs.setString(4,  pPostalAddrLine2);
			            cs.setString(5,  pPostalAddrLine3);
			            cs.setString(6,  pPostalAddrLine4);
			            cs.setString(7,  pPostalAddrLine5);
			            cs.setString(8,  pPostalAddrLine6);
			            lobHandler.getLobCreator().setClobAsString(cs, 9, pEmailContent);
			            lobHandler.getLobCreator().setClobAsString(cs,10, pEmailSubject);
			            //cs.setClob(9,  pEmailContent);
			            //cs.setClob(10, pEmailSubject);			            
			            cs.setString(11, pEmailFrom);			            
			            cs.setString(12, pSmsContent);
			            cs.setString(13, pCreateBy);
			            cs.setString(14, pLastUpdBy);
			            cs.registerOutParameter(15, Types.NUMERIC); 
			            return cs;
			        }
			    } ,   new CallableStatementCallback()  {
			        		public Long doInCallableStatement(CallableStatement cs) throws SQLException,
                            DataAccessException {
			        			long result = 0;
			        			try {
									cs.execute();
									result = cs.getLong(15);
			        			} catch (SQLException e) {
									e.printStackTrace();
									throw e;								
								}
			        			return result;
			        }
			       }
				);
		    
			return retPrintReqId.intValue();
	}
		
}
