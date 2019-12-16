package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.util.StringUtils;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.dto.report.ReportSetDTO;
import com.bomwebportal.dto.report.ReportSetDetailDTO;
import com.bomwebportal.exception.DAOException;



public class ReportSetLkupDAO extends CodeLkupDAOImpl {

	private static final String SQL_GET_REPORT_RESULTSET = 
			"select s.lob, s.channel, s.rpt_set, s.set_store_path, s.set_file_name, s.set_src_path, s.set_src_file_name, " + 
			"s.permission, s.copies, s.encrypt_ind, s.re_gen_ind, s.set_re_gen_ind, " + 
			"s.rpt_seq, s.rpt_type, s.store_path, s.file_name, s.lang, s.editable_ind, s.print_signature_ind, s.print_terms_ind, " + 
			"r.src_path, r.src_file_name, r.rpt_name " + 
			"from (select s.lob, s.channel, s.rpt_set, s.store_path set_store_path, s.file_name set_file_name, s.src_path set_src_path, s.src_file_name set_src_file_name, " + 
				  "s.permission, s.copies, s.encrypt_ind, s.re_gen_ind set_re_gen_ind, sa.re_gen_ind, " + 
				  "sa.rpt_seq, sa.rpt_type, sa.store_path, sa.file_name, sa.lang, sa.editable_ind, sa.print_signature_ind, sa.print_terms_ind " +
				  "from bomweb_rpt_set_lkup s, bomweb_rpt_set_assgn_lkup sa " +
				  "where s.lob = '***' and s.lob = sa.lob(+) and s.channel = sa.channel(+) and s.rpt_set = sa.rpt_set(+)) s, bomweb_rpt_lkup r " +
			"where s.rpt_type = r.rpt_type (+) " +
			"order by s.lob, s.channel, s.rpt_set, s.rpt_seq"; 
	
    
	
	public LookupItemDTO [] getCodeLkup() throws DAOException {
		
		final Map<String, List<ReportSetDetailDTO>> rsDetailMap = new HashMap<String, List<ReportSetDetailDTO>>();
		final Map<String, ReportSetDTO> reportSetMap = new HashMap<String, ReportSetDTO>();	

		ParameterizedRowMapper<ReportSetDTO> mapper = new ParameterizedRowMapper<ReportSetDTO>() {
			public ReportSetDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

				List<ReportSetDetailDTO> rsList = null;
				ReportSetDTO rsDetailSet = new ReportSetDTO();
				
				rsDetailSet.setChannelId(rs.getString("CHANNEL"));
				rsDetailSet.setRptSet(rs.getString("RPT_SET"));
				rsDetailSet.setLob(rs.getString("LOB"));

                String sb = rsDetailSet.getLkupKey();
				
				if (!reportSetMap.containsKey(sb)) {
										
					rsDetailSet.setChannelId(rs.getString("CHANNEL"));
					rsDetailSet.setRptSet(rs.getString("RPT_SET"));
					rsDetailSet.setStorePath(rs.getString("SET_STORE_PATH"));
					rsDetailSet.setFileName(rs.getString("SET_FILE_NAME"));
					rsDetailSet.setPermission(rs.getString("PERMISSION"));
					rsDetailSet.setCopies(rs.getString("COPIES"));
					rsDetailSet.setEncrypt("Y".equals(rs.getString("ENCRYPT_IND")));
					rsDetailSet.setSrcStorePath(rs.getString("SET_SRC_PATH"));
					rsDetailSet.setSrcFileName(rs.getString("SET_SRC_FILE_NAME"));
					rsDetailSet.setReGen("Y".equals(rs.getString("SET_RE_GEN_IND")));
					
					reportSetMap.put(sb, rsDetailSet);
				}
				
				if (rsDetailMap.containsKey(sb)) {
					rsList = rsDetailMap.get(sb);
				} else {
					rsList = new ArrayList<ReportSetDetailDTO>();
					rsDetailMap.put(sb, rsList);
				}
				String rptSeq = rs.getString("RPT_SEQ");
				
				if (rptSeq == null) {
					return null;
				}
				ReportSetDetailDTO reportSetDetail = new ReportSetDetailDTO();
				reportSetDetail.setRptName(rs.getString("RPT_NAME"));
				reportSetDetail.setRptType(rs.getString("RPT_TYPE"));
				reportSetDetail.setRptSeq(Integer.parseInt(rptSeq));
				reportSetDetail.setLang(rs.getString("LANG"));
				reportSetDetail.setStorePath(rs.getString("STORE_PATH"));
				reportSetDetail.setFileName(rs.getString("FILE_NAME"));
				reportSetDetail.setIsEditable(rs.getString("EDITABLE_IND"));
				reportSetDetail.setIsPrintSignature(rs.getString("PRINT_SIGNATURE_IND"));
				reportSetDetail.setIsPrintTerms(rs.getString("PRINT_TERMS_IND"));
				reportSetDetail.setSrcStorePath(rs.getString("SRC_PATH"));
				reportSetDetail.setSrcFileName(rs.getString("SRC_FILE_NAME"));
				reportSetDetail.setReGen("Y".equals(rs.getString("RE_GEN_IND")));
				rsList.add(reportSetDetail);
				
				return null;                
			}};
			
		try {       
			this.simpleJdbcTemplate.query(StringUtils.replace(SQL_GET_REPORT_RESULTSET, "***", this.getLkupCode()),mapper);
		} catch (Exception e) {
			logger.error("Error in getCodeLkup() get offer action\n", e);
			throw new DAOException(e.getMessage(), e);
		}	
		
		LookupItemDTO [] lkupItemDTOs = new LookupItemDTO [reportSetMap.size()];
		Iterator<String> it = rsDetailMap.keySet().iterator();
		List<ReportSetDetailDTO> rptDtlList = null;
		ReportSetDTO rsDetailSet = null;
		String rsKey;
		int index=0;
		
		while (it.hasNext()) {
			rsKey = it.next();

			rsDetailSet = reportSetMap.get(rsKey);
			rptDtlList = rsDetailMap.get(rsKey);
			if (rptDtlList != null) {
				rsDetailSet.setRptDtls(rptDtlList.toArray(new ReportSetDetailDTO[rptDtlList.size()]));
			}

			lkupItemDTOs[index]= new LookupItemDTO();
			lkupItemDTOs[index].setItemKey(rsKey);
			lkupItemDTOs[index].setItemValue(rsDetailSet);
			
			index++;
		}
		
		return lkupItemDTOs;
	}
	
}
