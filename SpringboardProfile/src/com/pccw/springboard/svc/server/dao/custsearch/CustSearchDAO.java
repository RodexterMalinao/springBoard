package com.pccw.springboard.svc.server.dao.custsearch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.util.LtsProfileConstant;
import com.pccw.springboard.svc.server.dto.AddressDTO;
import com.pccw.springboard.svc.server.dto.custsearch.AccountDTO;
import com.pccw.springboard.svc.server.dto.custsearch.AlertDTO;
import com.pccw.springboard.svc.server.dto.custsearch.CustomerOverviewDTO;
import com.pccw.springboard.svc.server.dto.custsearch.PcdLogin;
import com.pccw.springboard.svc.server.dto.custsearch.ServiceSummaryDTO;

/**
 * 
 * Springboard Single View of Customer & Customer Journey(SVC)

 * 
 * <br>
 * <br>&copy; PCCW Solution Limited. All rights reserved.
 * <br>Revision History: <br>
 * <TABLE BORDER=1>
 * <TR><TH>Date</TH><TH>Name</TH><TH>Changes</TH></TR>
 * <TR><TD>05/03/2014</TD><TD>Kenny Yip</TD><TD>Initial version</TD></TR>
 * <TR><TD>23/04/2015</TD><TD>Kenny Yip</TD><TD>SVC Phase 2</TD></TR>
 * </TABLE>
 *
 */


public class CustSearchDAO extends BaseDAO {
	
	private static final String SQL_GET_CUSTOMER_OVERVIEW_BY_PARENT_CUST_NUM = 
		"select a.system_id, a.cust_num, a.parent_cust_num, a.grp_id_doc_type, " + 
		"(select bom_desc from b_lookup where bom_grp_id = 'GRPIDDOCTYP' and bom_code = a.grp_id_doc_type and bom_status = 'A') grp_id_doc_type_desc, " +
		"a.grp_id_doc_num, a.id_doc_type, " +
		"(select bom_desc from b_lookup where bom_grp_id = 'IDDOCTYP' and bom_code = a.id_doc_type and bom_status = 'A') id_doc_type_desc, " +
		"a.id_doc_num, a.title, a.cust_first_name, a.cust_last_name, b.company_name, b.cust_catg, " +
		"(select bom_desc from b_lookup where bom_grp_id = 'CUSTCATG' and bom_code = b.cust_catg and bom_status = 'A') cust_catg_desc, " +
		"(select premier_type from b_customer where cust_num = a.parent_cust_num and system_id = 'BOM') premier_type, " +
		"(case when a.system_id = 'DRG' then nvl(b.blacklist_ind,'N') " +
		"when a.system_id = 'IMS' then nvl((select 'Y' from blacklst where custnb = a.cust_num and reldate is null and rownum = 1),'N') else null end) blacklist_ind, " +
		"nvl((select 'Y' from b_customer_remark where cust_num = a.cust_num and system_id = a.system_id and rownum = 1),'N') remarks_ind, " +
		"(case when a.system_id in ('DRG','IMS') then b.written_approval_required else null end) wip_ind, " +
		"nvl(b.spec_handle_ind,'N') spec_handle_ind, a.cust_type " +
		"from b_customer a, b_customer_details b " +
		"where a.parent_cust_num = ? " +
		"and b.cust_num = a.cust_num " +
		"and b.system_id = a.system_id " +
		"order by a.system_id, a.title, a.cust_last_name, " +
		"a.cust_first_name, b.company_name, a.id_doc_type, a.id_doc_num ";
	
	private static final String SQL_GET_LTS_CUSTOMER_OVERVIEW_BY_SINGLE_SEARCH =
		"select distinct a.system_id, a.cust_num, a.parent_cust_num, a.grp_id_doc_type, " +
		"(select bom_desc from b_lookup where bom_grp_id = 'GRPIDDOCTYP' and bom_code = a.grp_id_doc_type and bom_status = 'A') grp_id_doc_type_desc, " +
		"a.grp_id_doc_num, a.id_doc_type, " +
		"(select bom_desc from b_lookup where bom_grp_id = 'IDDOCTYP' and bom_code = a.id_doc_type and bom_status = 'A') id_doc_type_desc, " +
		"a.id_doc_num, a.title, a.cust_first_name, a.cust_last_name, b.company_name, b.cust_catg, " +
		"(select bom_desc from b_lookup where bom_grp_id = 'CUSTCATG' and bom_code = b.cust_catg and bom_status = 'A') cust_catg_desc, " +
		"(select premier_type from b_customer where cust_num = a.parent_cust_num and system_id = 'BOM') premier_type, " +
		"nvl(b.blacklist_ind,'N') blacklist_ind, " +
		"nvl((select 'Y' from b_customer_remark where cust_num = a.cust_num and system_id = a.system_id and rownum = 1),'N') remarks_ind, " +
		"b.written_approval_required wip_ind, " +
		"nvl(b.spec_handle_ind,'N') spec_handle_ind, a.cust_type " +
		"from b_service_member m, b_service s, b_account_service_assgn ass, b_account acc, b_customer a, b_customer_details b " +
		"where m.service_id = s.service_id " +
		"and m.system_id = s.system_id " +
		"and m.srv_num = ? " +
		"and s.service_type = ? " +
		"and s.service_id = ass.service_id " +
		"and ass.acct_num = acc.acct_num " +
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " + 
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null))) " +
	    "and a.parent_cust_num = ? " +
		"and a.cust_num = acc.cust_num " +
		"and a.system_id = s.system_id " +
		"and b.cust_num = a.cust_num " +
		"and b.system_id = a.system_id " +
		"order by a.system_id, a.title, a.cust_last_name, " +
		"a.cust_first_name, b.company_name, a.id_doc_type, a.id_doc_num ";
	
	private static final String SQL_GET_IMS_CUSTOMER_OVERVIEW_BY_SINGLE_SEARCH = 
		"select distinct a.system_id, a.cust_num, a.parent_cust_num, a.grp_id_doc_type, " +
		"(select bom_desc from b_lookup where bom_grp_id = 'GRPIDDOCTYP' and bom_code = a.grp_id_doc_type and bom_status = 'A') grp_id_doc_type_desc, " +
		"a.grp_id_doc_num, a.id_doc_type, " +
		"(select bom_desc from b_lookup where bom_grp_id = 'IDDOCTYP' and bom_code = a.id_doc_type and bom_status = 'A') id_doc_type_desc, " +
		"a.id_doc_num, a.title, a.cust_first_name, a.cust_last_name, b.company_name, b.cust_catg, " +
		"(select bom_desc from b_lookup where bom_grp_id = 'CUSTCATG' and bom_code = b.cust_catg and bom_status = 'A') cust_catg_desc, " +
		"(select premier_type from b_customer where cust_num = a.parent_cust_num and system_id = 'BOM') premier_type, " +
		"nvl((select 'Y' from blacklst where custnb = a.cust_num and reldate is null and rownum = 1),'N') blacklist_ind, " +
		"nvl((select 'Y' from b_customer_remark where cust_num = a.cust_num and system_id = a.system_id and rownum = 1),'N') remarks_ind, " +
		"b.written_approval_required wip_ind, nvl(b.spec_handle_ind,'N') spec_handle_ind, a.cust_type " +
		"from b_service s, b_account_service_assgn ass, b_account acc, b_customer a, b_customer_details b " +
		"where s.service_id = ? " +
		"and s.service_type = ? " +
		"and s.service_id = ass.service_id " +
		"and ass.acct_num = acc.acct_num " +
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " + 
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null))) " +
		"and a.parent_cust_num = ? " +
		"and a.cust_num = acc.cust_num " +
		"and a.system_id = s.system_id " +
		"and b.cust_num = a.cust_num " +
		"and b.system_id = a.system_id " +
		"order by a.system_id, a.title, a.cust_last_name, " +
		"a.cust_first_name, b.company_name, a.id_doc_type, a.id_doc_num ";
		
	private static final String SQL_GET_MOB_CUSTOMER_OVERVIEW_BY_SINGLE_SEARCH = 	
		"select distinct a.system_id, a.cust_num, a.parent_cust_num, a.grp_id_doc_type, " +
		"(select bom_desc from b_lookup where bom_grp_id = 'GRPIDDOCTYP' and bom_code = a.grp_id_doc_type and bom_status = 'A') grp_id_doc_type_desc, " +
		"a.grp_id_doc_num, a.id_doc_type, " +
		"(select bom_desc from b_lookup where bom_grp_id = 'IDDOCTYP' and bom_code = a.id_doc_type and bom_status = 'A') id_doc_type_desc, " +
		"a.id_doc_num, a.title, a.cust_first_name, a.cust_last_name, b.company_name, b.cust_catg, " +
		"(select bom_desc from b_lookup where bom_grp_id = 'CUSTCATG' and bom_code = b.cust_catg and bom_status = 'A') cust_catg_desc, " +
		"(select premier_type from b_customer where cust_num = a.parent_cust_num and system_id = 'BOM') premier_type, " +
		"null blacklist_ind, " +
		"nvl((select 'Y' from b_customer_remark where cust_num = a.cust_num and system_id = a.system_id and rownum = 1),'N') remarks_ind, " +
		"null wip_ind, nvl(b.spec_handle_ind,'N') spec_handle_ind, a.cust_type " +
		"from b_service s, b_account_service_assgn ass, b_account acc, b_customer a, b_customer_details b " +
		"where s.srv_num = ? " +
		"and s.service_type = ? " +
		"and (s.eff_end_date is null or months_between(trunc(sysdate,'month'), trunc(s.eff_end_date,'month')) <= 13) " +
		"and s.service_id = ass.service_id " +
		"and ass.acct_num = acc.acct_num " +
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " + 
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null))) " +
		"and a.parent_cust_num = ? " +
		"and a.cust_num = acc.cust_num " +
		"and a.system_id = s.system_id " +
		"and b.cust_num = a.cust_num " +
		"and b.system_id = a.system_id " +
		"order by a.system_id, a.title, a.cust_last_name, " +
		"a.cust_first_name, b.company_name, a.id_doc_type, a.id_doc_num ";	
	
	private static final String SQL_GET_PARENT_CUST_NUM_BY_ID_DOC_NUM = 		
		"select distinct c.parent_cust_num " +
		"from b_customer c " +
		"where c.id_doc_type = ? " +
		"and c.id_doc_num = ? ";
	
	private static final String SQL_GET_PARENT_CUST_NUM_BY_ACCT_NUM =
		"select distinct c.parent_cust_num " +
		"from b_customer c, b_account a " +
		"where a.acct_num = ? " +
		"and c.cust_num = a.cust_num " +
		"and c.system_id = a.system_id ";
	
	private static final String SQL_GET_FSA_BY_BSN =
		"select distinct service_id " +
		"from b_service_details_ims " +
		"where circuit_num = ? "; 
			
	private static final String SQL_GET_PARENT_CUST_NUM_BY_LTS = 
			"select distinct c.parent_cust_num " +
			"  from b_service_member m, b_service s, b_account_service_assgn ass, b_account acc, b_customer c " +
			" where m.service_id = s.service_id " +
			"   and m.system_id = s.system_id " +
			"   and m.srv_num = ? " +
			"   and s.service_type = ? " +
			"   and s.service_id = ass.service_id " +
			"   and ass.acct_num = acc.acct_num " +
			"   and (ass.eff_end_date is null or (     ass.eff_end_date is not null and s.eff_end_date is not null " + 
			"                                      and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
			"                                                               where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null))) " +
			"   and c.cust_num = acc.cust_num " +
			"   and c.system_id = s.system_id ";
	
	private static final String SQL_GET_PARENT_CUST_NUM_BY_IMS = 
		"select distinct c.parent_cust_num " +
		"from b_service s, b_account_service_assgn ass, b_account acc, b_customer c " +
		"where s.service_id = ? " +
		"and s.service_type = ? " +
		"and s.service_id = ass.service_id " +
		"and ass.acct_num = acc.acct_num " +
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " + 
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null))) " +
		"and c.cust_num = acc.cust_num " +
		"and c.system_id = s.system_id ";
	
	private static final String SQL_GET_PARENT_CUST_NUM_BY_MOB = 
		"select distinct c.parent_cust_num " +
		"from b_service s, b_account_service_assgn ass, b_account acc, b_customer c " +
		"where s.srv_num = ? " +
		"and s.service_type = ? " +
		"and (s.eff_end_date is null or months_between(trunc(sysdate,'month'), trunc(s.eff_end_date,'month')) <= 13) " +
		"and s.service_id = ass.service_id " +
		"and ass.acct_num = acc.acct_num " +
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " + 
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null))) " +
		"and c.cust_num = acc.cust_num " +
		"and c.system_id = s.system_id ";
	
	private static final String SQL_GET_PARENT_CUST_NUM_BY_IMS_LOGIN =
		"select distinct c.parent_cust_num " +
		"from b_service s, b_service_member_details_ims m, b_account_service_assgn ass, b_account acc, b_customer c " +
		"where m.login_id = ? " +
		"and m.domain_type = ? " +
		"and m.member_status in ('ACT','TERM') " +
		"and s.service_id = m.service_id " +
		"and ass.service_id = s.service_id " +
		"and ass.system_id = s.system_id " +
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " + 
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null))) " +
		"and acc.acct_num = ass.acct_num " +
		"and acc.system_id = ass.system_id " +
		"and c.cust_num = acc.cust_num " +
		"and c.system_id = acc.system_id ";
	
	private static final String SQL_GET_LTS_SERVICE_SUMMARY =
		"select g.srv_num, g.system_id, g.service_id, g.service_type, g.service_status, g.duplex_b_num, g.eff_start_date, g.eff_end_date, g.dat_cd, " + 
		"g.cls_of_srv, g.addr_id, g.service_boundary, g.address_premier, g.eye_grp_id, " +
		"a.distr_num, a.sect_cd, a.street_name, a.hlot_num, a.st_catg_cd, a.build_name, a.floor_num, a.unit_num, a.street_num, d.area_cd, " +
		"d.dist_desc, e.areadsc, c.sect_desc, b.stcatdsc " +
		"from (select distinct s.srv_num, s.system_id, s.service_id, s.service_type, " +
		"nvl2(m.duplex_type,decode(m.duplex_type,'A',m.duplex_num,m.srv_num),null) duplex_b_num, nvl2(s.eff_end_date,'Inactive','Active') service_status, " + 
		"trim(to_char(s.eff_start_date, 'DD/MM/YYYY')) as eff_start_date, trim(to_char(s.eff_end_date, 'DD/MM/YYYY')) as eff_end_date, " +
		"ass2.dat_cd, s.install_addr addr_id, p.cls_of_srv, " +
		"(select d.srvbdry_num from b_address_dtl d where d.addr_id = s.install_addr) service_boundary, " +
		"(select p.housing_type from b_address_dtl d, b_premier_info p " +
		"where d.addr_id = s.install_addr " +
		"and p.serbdyno = trim(to_char(d.srvbdry_num, '000000'))) address_premier, " +
		"(select max(srv_grp_num) from b_srv_grp_assgn " +
		"where service_id = s.service_id and (eff_end_date is null " +
		"or (eff_end_date is not null and m.eff_end_date is not null and trunc(eff_end_date) = trunc(m.eff_end_date))) " +
		"and srv_grp_type like 'EYE%') eye_grp_id " +
		"from b_account acc, b_account_service_assgn ass, b_service s, b_service_member m, b_service_type_assgn ass2, b_hkt_profile p " +
		"where acc.acct_num = ass.acct_num " +
		"and acc.system_id = ass.system_id " +
		"and acc.cust_num = ? " +
		"and ass.service_id = s.service_id " +
		"and m.service_id = s.service_id " +
		"and (m.duplex_type is null or m.duplex_type = 'A' or (m.duplex_type = 'B' and m.eff_end_date is null)) " + 
		"and s.system_id = ? " +
		"and ass2.service_id = s.service_id " +
		"and ass2.service_type = s.service_type " +
		"and p.service_id(+) = s.service_id " +
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " + 
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null))) " +
		"and (ass2.eff_end_date is null or (ass2.eff_end_date is not null and s.eff_end_date is not null " +
		"and ass2.eff_end_date = (select max(c2.eff_end_date) from b_service_type_assgn c2 " +
		"where c2.service_id = ass2.service_id and c2.system_id = ass2.system_id and c2.eff_end_date is not null))) " +
		"and (p.eff_end_date is null or (p.eff_end_date is not null and s.eff_end_date is not null " +
		"and p.eff_end_date = (select max(p2.eff_end_date) from b_hkt_profile p2 " +
		"where p2.service_id = p.service_id and p2.eff_end_date is not null)))) g, b_address_dtl a, stcatlu b, b_section c,b_distlkup d, area e " +
		"where a.addr_id(+) = g.addr_id " +
		"and b.stcatgcd(+) = a.st_catg_cd " +
		"and c.sect_cd(+)  = a.sect_cd " +
		"and c.distrnum(+) = a.distr_num " +
		"and d.distrnum(+) = a.distr_num " +
		"and e.areacd(+) = d.area_cd ";
	
	private static final String SQL_GET_IMS_SERVICE_SUMMARY =
		"select g.srv_num, g.system_id, g.service_id, g.service_type, g.service_status, duplex_b_num, g.eff_start_date, g.eff_end_date, g.dat_cd, " + 
		"g.cls_of_srv, g.addr_id, g.service_boundary, g.address_premier, g.eye_grp_id, " +
		"a.unitnb unit_num, a.floornb floor_num, a.buildnm build_name, a.hlotnb hlot_num, " +
		"a.streetnb street_num, a.streetnm street_name, a.stcatgcd st_catg_cd, a.sectcd sect_cd, a.distrnb distr_num, e.areacd area_cd, " +
		"d.distdsc dist_desc, e.areadsc, c.sectdsc sect_desc, b.stcatdsc " +
		"from (select distinct s.system_id, s.service_id srv_num, null duplex_b_num, nvl2(s.eff_end_date,'Inactive','Active') service_status, " + 
		"trim(to_char(s.eff_start_date, 'DD/MM/YYYY')) as eff_start_date, trim(to_char(s.eff_end_date, 'DD/MM/YYYY')) as eff_end_date, " +
		"s.service_id, s.service_type, null dat_cd, s.install_addr addr_id, null cls_of_srv, " +
		"(select d.serbdyno from address d where d.addrid = s.install_addr) service_boundary, " +
		"(select p.housing_type from address d, b_premier_info p " +
		"where d.addrid = s.install_addr " +
		"and p.serbdyno = d.serbdyno) address_premier, " +
		"(select max(srv_grp_num) from b_srv_grp_assgn " +
		"where service_id = s.service_id and (eff_end_date is null " +
		"or (eff_end_date is not null and s.eff_end_date is not null and trunc(eff_end_date) = trunc(s.eff_end_date))) " + 
		"and srv_grp_type like 'EYE%') eye_grp_id " +
		"from b_account acc, b_account_service_assgn ass, b_service s " +
		"where acc.acct_num = ass.acct_num " +
		"and acc.system_id = ass.system_id " +
		"and acc.cust_num = ? " +
		"and ass.service_id = s.service_id " +
		"and s.system_id = ? " +
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " + 
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null)))) g, " + 
		"address a, stcatlu b, section c,distlkup d, area e " +
		"where a.addrid(+) = g.addr_id " +
		"and b.stcatgcd(+) = a.stcatgcd " +
		"and c.sectcd(+)  = a.sectcd " +
		"and c.distrnb(+) = a.distrnb " +
		"and d.distrnb(+) = a.distrnb " +
		"and e.areacd(+) = d.areacd ";
	
	private static final String SQL_GET_MOB_SERVICE_SUMMARY =
		"select g.srv_num, g.system_id, g.service_id, g.service_type, g.service_status, duplex_b_num, g.eff_start_date, g.eff_end_date, g.dat_cd, " + 
		"g.cls_of_srv, g.addr_id, g.service_boundary, g.address_premier, g.eye_grp_id, " +
		"a.distr_num, a.sect_cd, a.street_name, a.hlot_num, a.st_catg_cd, a.build_name, a.floor_num, a.unit_num, a.street_num, d.area_cd, " +
		"d.dist_desc, e.areadsc, c.sect_desc, b.stcatdsc " +
		"from (select distinct s.system_id, s.srv_num, null duplex_b_num, " +
		"nvl2((select 'Y' from b_service_details_mob where service_status in ('BAR','ISUS','LSUS','VSUS') and service_id = s.service_id and rownum = 1), " +
		"'Suspend',nvl2(s.eff_end_date,'Inactive','Active')) service_status, " +
		"trim(to_char(s.eff_start_date, 'DD/MM/YYYY')) as eff_start_date, trim(to_char(s.eff_end_date, 'DD/MM/YYYY')) as eff_end_date, " +
		"s.service_id, s.service_type, null dat_cd, null cls_of_srv, " +
		"(select corr_addr from b_customer_details where cust_num = acc.cust_num and system_id = s.system_id) addr_id, " + 
		"(select d.srvbdry_num from b_address_dtl d " +
		"where d.addr_id = (select corr_addr from b_customer_details where cust_num = acc.cust_num and system_id = s.system_id)) service_boundary, " +
		"(select p.housing_type from b_address_dtl d, b_premier_info p " +
		"where d.addr_id = (select corr_addr from b_customer_details where cust_num = acc.cust_num and system_id = s.system_id) " +
		"and p.serbdyno = trim(to_char(d.srvbdry_num, '000000'))) address_premier, null eye_grp_id " +
		"from b_account acc, b_account_service_assgn ass, b_service s " +
		"where acc.acct_num = ass.acct_num " +
		"and acc.system_id = ass.system_id " +
		"and acc.cust_num = ? " +
		"and ass.service_id = s.service_id " +
		"and s.system_id = ? " +
		"and (s.eff_end_date is null or months_between(trunc(sysdate,'month'), trunc(s.eff_end_date,'month')) <= 13) " +
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " + 
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null)))) g, b_address_dtl a, stcatlu b, b_section c,b_distlkup d, area e " +
		"where a.addr_id(+) = g.addr_id " +
		"and b.stcatgcd(+) = a.st_catg_cd " +
		"and c.sect_cd(+)  = a.sect_cd " +
		"and c.distrnum(+) = a.distr_num " +
		"and d.distrnum(+) = a.distr_num " +
		"and e.areacd(+) = d.area_cd ";
	
	private static final String SQL_SERVICE_COUNT_LTS =
		"select distinct s.srv_num, s.system_id " +
		"from b_account acc, b_account_service_assgn ass, b_service s, b_service_member m, b_service_type_assgn ass2, b_hkt_profile p " +
		"where acc.acct_num = ass.acct_num " +
		"and acc.system_id = ass.system_id " +
		"and acc.cust_num = ? " +
		"and ass.service_id = s.service_id " +
		"and m.service_id = s.service_id " +
		"and (m.duplex_type is null or m.duplex_type = 'A' or (m.duplex_type = 'B' and m.eff_end_date is null)) " + 
		"and s.system_id = ? " +
		"and ass2.service_id = s.service_id " +
		"and ass2.service_type = s.service_type " +
		"and p.service_id(+) = s.service_id " +
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " + 
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null))) " +
		"and (ass2.eff_end_date is null or (ass2.eff_end_date is not null and s.eff_end_date is not null " +
		"and ass2.eff_end_date = (select max(c2.eff_end_date) from b_service_type_assgn c2 " +
		"where c2.service_id = ass2.service_id and c2.system_id = ass2.system_id and c2.eff_end_date is not null))) " +
		"and (p.eff_end_date is null or (p.eff_end_date is not null and s.eff_end_date is not null " +
		"and p.eff_end_date = (select max(p2.eff_end_date) from b_hkt_profile p2 " +
		"where p2.service_id = p.service_id and p2.eff_end_date is not null))) ";
	
	private static final String SQL_SERVICE_COUNT_IMS =
		"select distinct s.service_id srv_num, s.system_id " +
		"from b_account acc, b_account_service_assgn ass, b_service s " +
		"where acc.acct_num = ass.acct_num " +
		"and acc.system_id = ass.system_id " +
		"and acc.cust_num = ? " +
		"and ass.service_id = s.service_id " +
		"and s.system_id = ? " +
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " + 
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null))) ";
	
	private static final String SQL_SERVICE_COUNT_MOB =
		"select distinct s.srv_num, s.system_id " +
		"from b_account acc, b_account_service_assgn ass, b_service s " +
		"where acc.acct_num = ass.acct_num " +
		"and acc.system_id = ass.system_id " +
		"and acc.cust_num = ? " +
		"and ass.service_id = s.service_id " +
		"and s.system_id = ? " +
		"and (s.eff_end_date is null or months_between(trunc(sysdate,'month'), trunc(s.eff_end_date,'month')) <= 13) " +
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " + 
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null))) ";
	
	private static final String SQL_BY_MAXIMUM_SERVICE_COUNT = "and rownum < (?*5) ";
	
	private static final String SQL_GET_LTS_SERVICE_SUMMARY_BY_SINGLE_SEARCH =
		"select g.srv_num, g.system_id, g.service_id, g.service_type, g.service_status, g.duplex_b_num, g.eff_start_date, g.eff_end_date, g.dat_cd, " + 
		"g.cls_of_srv, g.addr_id, g.service_boundary, g.address_premier, g.eye_grp_id, " +
		"a.distr_num, a.sect_cd, a.street_name, a.hlot_num, a.st_catg_cd, a.build_name, a.floor_num, a.unit_num, a.street_num, d.area_cd, " +
		"d.dist_desc, e.areadsc, c.sect_desc, b.stcatdsc " +
		"from (select distinct s.srv_num, s.system_id, s.service_id, s.service_type, " +
		"nvl2(m.duplex_type,decode(m.duplex_type,'A',m.duplex_num,m.srv_num),null) duplex_b_num, nvl2(s.eff_end_date,'Inactive','Active') service_status, " + 
		"trim(to_char(s.eff_start_date, 'DD/MM/YYYY')) as eff_start_date, trim(to_char(s.eff_end_date, 'DD/MM/YYYY')) as eff_end_date, " +
		"ass2.dat_cd, s.install_addr addr_id, p.cls_of_srv, " +
		"(select d.srvbdry_num from b_address_dtl d where d.addr_id = s.install_addr) service_boundary, " +
		"(select p.premier_cust_tag from b_address_dtl d, b_premier_info p " +
		"where d.addr_id = s.install_addr " +
		"and p.serbdyno = trim(to_char(d.srvbdry_num, '000000'))) address_premier, " +
		"(select max(srv_grp_num) from b_srv_grp_assgn " +
		"where service_id = s.service_id and (eff_end_date is null " +
		"or (eff_end_date is not null and m.eff_end_date is not null and trunc(eff_end_date) = trunc(m.eff_end_date))) " + 
		"and srv_grp_type like 'EYE%') eye_grp_id " +
		"from b_account acc, b_account_service_assgn ass, b_service s, b_service_member m, b_service_type_assgn ass2, b_hkt_profile p " +
		"where acc.acct_num = ass.acct_num " +
		"and acc.system_id = ass.system_id " +
		"and acc.cust_num = ? " +
		"and ass.service_id = s.service_id " +
		"and m.service_id = s.service_id " +
		"and (m.duplex_type is null or m.duplex_type = 'A' or (m.duplex_type = 'B' and m.eff_end_date is null)) " +
		"and m.srv_num = ? " +
		"and s.service_type = ? " +
		"and s.system_id = ? " +
		"and ass2.service_id = s.service_id " +
		"and ass2.service_type = s.service_type " +
		"and p.service_id(+) = s.service_id " +
		//TC
		"and  nvl(s.eff_end_date, to_date('99990101', 'yyyymmdd')) = (select max(nvl(s1.eff_end_date, to_date('99990101', 'yyyymmdd'))) " +
		"from b_service s1, b_account_service_assgn basa, b_account ba, b_customer bc " +
		"where s1.srv_num = s.srv_num " +
		"and s1.system_id = s.system_id " +
		"and s1.service_id = basa.service_id " +
		"and basa.system_id = s.system_id " +
		"and basa.acct_num = ba.acct_num " +
		"and ba.system_id = basa.system_id " +
		"and ba.cust_num = bc.cust_num " +
		"and bc.system_id = basa.system_id " +
		"and bc.cust_num = acc.cust_num) " +
		//
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " + 
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null))) " +
		"and (ass2.eff_end_date is null or (ass2.eff_end_date is not null and s.eff_end_date is not null " +
		"and ass2.eff_end_date = (select max(c2.eff_end_date) from b_service_type_assgn c2 " +
		"where c2.service_id = ass2.service_id and c2.system_id = ass2.system_id and c2.eff_end_date is not null))) " +
		"and (p.eff_end_date is null or (p.eff_end_date is not null and s.eff_end_date is not null " +
		"and p.eff_end_date = (select max(p2.eff_end_date) from b_hkt_profile p2 " +
		"where p2.service_id = p.service_id and p2.eff_end_date is not null)))) g, b_address_dtl a, stcatlu b, b_section c,b_distlkup d, area e " +
		"where a.addr_id(+) = g.addr_id " +
		"and b.stcatgcd(+) = a.st_catg_cd " +
		"and c.sect_cd(+)  = a.sect_cd " +
		"and c.distrnum(+) = a.distr_num " +
		"and d.distrnum(+) = a.distr_num " +
		"and e.areacd(+) = d.area_cd ";
	
	private static final String SQL_GET_IMS_SERVICE_SUMMARY_BY_SINGLE_SEARCH =
		"select g.srv_num, g.system_id, g.service_id, g.service_type, g.service_status, duplex_b_num, g.eff_start_date, g.eff_end_date, g.dat_cd, " + 
		"g.cls_of_srv, g.addr_id, g.service_boundary, g.address_premier, g.eye_grp_id, " +
		"a.unitnb unit_num, a.floornb floor_num, a.buildnm build_name, a.hlotnb hlot_num, " +
		"a.streetnb street_num, a.streetnm street_name, a.stcatgcd st_catg_cd, a.sectcd sect_cd, a.distrnb distr_num, e.areacd area_cd, " +
		"d.distdsc dist_desc, e.areadsc, c.sectdsc sect_desc, b.stcatdsc " +
		"from (select distinct s.system_id, s.service_id srv_num, null duplex_b_num, nvl2(s.eff_end_date,'Inactive','Active') service_status, " + 
		"trim(to_char(s.eff_start_date, 'DD/MM/YYYY')) as eff_start_date, trim(to_char(s.eff_end_date, 'DD/MM/YYYY')) as eff_end_date, " +
		"s.service_id, s.service_type, null dat_cd, s.install_addr addr_id, null cls_of_srv, " +
		"(select d.serbdyno from address d where d.addrid = s.install_addr) service_boundary, " +
		"(select p.premier_cust_tag from address d, b_premier_info p " +
		"where d.addrid = s.install_addr " +
		"and p.serbdyno = d.serbdyno) address_premier, " +
		"(select max(srv_grp_num) from b_srv_grp_assgn " +
		"where service_id = s.service_id and (eff_end_date is null " +
		"or (eff_end_date is not null and s.eff_end_date is not null and trunc(eff_end_date) = trunc(s.eff_end_date))) " + 
		"and srv_grp_type like 'EYE%') eye_grp_id " +
		"from b_account acc, b_account_service_assgn ass, b_service s " +
		"where acc.acct_num = ass.acct_num " +
		"and acc.system_id = ass.system_id " +
		"and acc.cust_num = ? " +
		"and ass.service_id = s.service_id " +
		"and s.service_id = ? " +
		"and s.service_type = ? " +
		"and s.system_id = ? " +
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " + 
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null)))) g, " + 
		"address a, stcatlu b, section c,distlkup d, area e " +
		"where a.addrid(+) = g.addr_id " +
		"and b.stcatgcd(+) = a.stcatgcd " +
		"and c.sectcd(+)  = a.sectcd " +
		"and c.distrnb(+) = a.distrnb " +
		"and d.distrnb(+) = a.distrnb " +
		"and e.areacd(+) = d.areacd ";
	
	private static final String SQL_GET_MOB_SERVICE_SUMMARY_BY_SINGLE_SEARCH =
		"select g.srv_num, g.system_id, g.service_id, g.service_type, g.service_status, duplex_b_num, g.eff_start_date, g.eff_end_date, g.dat_cd, " + 
		"g.cls_of_srv, g.addr_id, g.service_boundary, g.address_premier, g.eye_grp_id, " +
		"a.distr_num, a.sect_cd, a.street_name, a.hlot_num, a.st_catg_cd, a.build_name, a.floor_num, a.unit_num, a.street_num, d.area_cd, " +
		"d.dist_desc, e.areadsc, c.sect_desc, b.stcatdsc " +
		"from (select distinct s.system_id, s.srv_num, null duplex_b_num, " +
		"nvl2((select 'Y' from b_service_details_mob where service_status in ('BAR','ISUS','LSUS','VSUS') and service_id = s.service_id and rownum = 1), " +
		"'Suspend',nvl2(s.eff_end_date,'Inactive','Active')) service_status, " +
		"trim(to_char(s.eff_start_date, 'DD/MM/YYYY')) as eff_start_date, trim(to_char(s.eff_end_date, 'DD/MM/YYYY')) as eff_end_date, " +
		"s.service_id, s.service_type, null dat_cd, null cls_of_srv, " +
		"(select corr_addr from b_customer_details where cust_num = acc.cust_num and system_id = s.system_id) addr_id, " + 
		"(select d.srvbdry_num from b_address_dtl d " +
		"where d.addr_id = (select corr_addr from b_customer_details where cust_num = acc.cust_num and system_id = s.system_id)) service_boundary, " +
		"(select p.premier_cust_tag from b_address_dtl d, b_premier_info p " +
		"where d.addr_id = (select corr_addr from b_customer_details where cust_num = acc.cust_num and system_id = s.system_id) " +
		"and p.serbdyno = trim(to_char(d.srvbdry_num, '000000'))) address_premier, null eye_grp_id " +
		"from b_account acc, b_account_service_assgn ass, b_service s " +
		"where acc.acct_num = ass.acct_num " +
		"and acc.system_id = ass.system_id " +
		"and acc.cust_num = ? " +
		"and ass.service_id = s.service_id " +
		"and s.srv_num = ? " +
		"and s.service_type = ? " +
		"and s.system_id = ? " +
		"and (s.eff_end_date is null or months_between(trunc(sysdate,'month'), trunc(s.eff_end_date,'month')) <= 13) " +
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " + 
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null)))) g, b_address_dtl a, stcatlu b, b_section c,b_distlkup d, area e " +
		"where a.addr_id(+) = g.addr_id " +
		"and b.stcatgcd(+) = a.st_catg_cd " +
		"and c.sect_cd(+)  = a.sect_cd " +
		"and c.distrnum(+) = a.distr_num " +
		"and d.distrnum(+) = a.distr_num " +
		"and e.areacd(+) = d.area_cd ";
	
	private static final String SQL_GET_LTS_ACCOUNT_OF_SERVICE_SUMMARY =
		"select ass.acct_num, ass.chrg_type, acc.acct_type, s.service_id, acc.cust_num " +
		"from b_account acc, b_account_service_assgn ass, b_service s " +
		"where acc.cust_num = ? " +
		"and acc.system_id = ? " +
		"and ass.acct_num = acc.acct_num " +
		"and ass.system_id = acc.system_id " +
		"and s.service_id = ass.service_id " +
		"and s.system_id = ass.system_id " +
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " +
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null))) " +
		"order by s.service_id ";
	
	private static final String SQL_GET_IMS_ACCOUNT_OF_SERVICE_SUMMARY =
		"select ass.acct_num, ass.chrg_type, acc.acct_type, s.service_id, acc.cust_num " +
		"from b_account acc, b_account_service_assgn ass, b_service s " +
		"where acc.cust_num = ? " +
		"and acc.system_id = ? " +
		"and ass.acct_num = acc.acct_num " +
		"and ass.system_id = acc.system_id " +
		"and s.service_id = ass.service_id " +
		"and s.system_id = ass.system_id " +
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " +
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null))) " +
		"order by s.service_id ";
	
	private static final String SQL_GET_MOB_ACCOUNT_OF_SERVICE_SUMMARY =
		"select ass.acct_num, ass.chrg_type, acc.acct_type, s.service_id, acc.cust_num " +
		"from b_account acc, b_account_service_assgn ass, b_service s " +
		"where acc.cust_num = ? " +
		"and acc.system_id = ? " +
		"and ass.acct_num = acc.acct_num " +
		"and ass.system_id = acc.system_id " +
		"and s.service_id = ass.service_id " +
		"and s.system_id = ass.system_id " +
		"and (s.eff_end_date is null or months_between(trunc(sysdate,'month'), trunc(s.eff_end_date,'month')) <= 13) " +
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " +
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null))) " +
		"order by s.service_id ";
	
	private static final String SQL_GET_LTS_ACCOUNT_OF_SERVICE_SUMMARY_BY_SINGLE_SEARCH =
		"select ass.acct_num, ass.chrg_type, acc.acct_type, s.service_id, acc.cust_num " +
		"from b_account acc, b_account_service_assgn ass, b_service s " +
		"where acc.cust_num = ? " +		
		"and ass.acct_num = acc.acct_num " +
		"and ass.system_id = acc.system_id " +
		"and s.service_id = ass.service_id " +
		"and s.system_id = ass.system_id " +
		"and s.service_id = ? " +
		"and s.service_type = ? " +
		"and s.system_id = ? " +
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " +
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null))) ";
	
	private static final String SQL_GET_IMS_ACCOUNT_OF_SERVICE_SUMMARY_BY_SINGLE_SEARCH =
		"select ass.acct_num, ass.chrg_type, acc.acct_type, s.service_id, acc.cust_num " +
		"from b_account acc, b_account_service_assgn ass, b_service s " +
		"where acc.cust_num = ? " +
		"and ass.acct_num = acc.acct_num " +
		"and ass.system_id = acc.system_id " +
		"and s.service_id = ass.service_id " +
		"and s.system_id = ass.system_id " +
		"and s.service_id = ? " +
		"and s.service_type = ? " +
		"and s.system_id = ? " +
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " +
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null))) ";
	
	private static final String SQL_GET_MOB_ACCOUNT_OF_SERVICE_SUMMARY_BY_SINGLE_SEARCH =
		"select ass.acct_num, ass.chrg_type, acc.acct_type, s.service_id, acc.cust_num " +
		"from b_account acc, b_account_service_assgn ass, b_service s " +
		"where acc.cust_num = ? " +
		"and ass.acct_num = acc.acct_num " +
		"and ass.system_id = acc.system_id " +
		"and s.service_id = ass.service_id " +
		"and s.system_id = ass.system_id " +
		"and s.service_id = ? " +
		"and s.service_type = ? " +
		"and s.system_id = ? " +
		"and (s.eff_end_date is null or months_between(trunc(sysdate,'month'), trunc(s.eff_end_date,'month')) <= 13) " +
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " +
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null))) ";
	
	private static final String SQL_GET_MULTIPLE_PARENT_CUSTOMER_LIST_BY_PARENT_CUST_NUM =
		"select a.system_id, a.cust_num, a.parent_cust_num, a.grp_id_doc_type, " +
		"(select bom_desc from b_lookup where bom_grp_id = 'GRPIDDOCTYP' and bom_code = a.grp_id_doc_type and bom_status = 'A') grp_id_doc_type_desc, " +
		"a.grp_id_doc_num, a.id_doc_type, " +
		"(select bom_desc from b_lookup where bom_grp_id = 'IDDOCTYP' and bom_code = a.id_doc_type and bom_status = 'A') id_doc_type_desc, " +
		"a.id_doc_num, a.title, a.cust_first_name, a.cust_last_name, b.company_name, a.cust_type, " +
		"null cust_catg, null cust_catg_desc, null premier_type, null blacklist_ind, null remarks_ind, null wip_ind, null spec_handle_ind " +
		"from b_customer a, b_customer_details b " +
		"where a.parent_cust_num = ? " +
		"and b.cust_num = a.cust_num " +
		"and b.system_id = a.system_id ";
	
	private static final String SQL_GET_MULTIPLE_PARENT_CUSTOMER_LIST_BY_LTS =
		"select a.system_id, a.cust_num, a.parent_cust_num, a.grp_id_doc_type, " +
		"(select bom_desc from b_lookup where bom_grp_id = 'GRPIDDOCTYP' and bom_code = a.grp_id_doc_type and bom_status = 'A') grp_id_doc_type_desc, " +
		"a.grp_id_doc_num, a.id_doc_type, " +
		"(select bom_desc from b_lookup where bom_grp_id = 'IDDOCTYP' and bom_code = a.id_doc_type and bom_status = 'A') id_doc_type_desc, " +
		"a.id_doc_num, a.title, a.cust_first_name, a.cust_last_name, b.company_name, a.cust_type, " +
		"null cust_catg, null cust_catg_desc, null premier_type, null blacklist_ind, null remarks_ind, null wip_ind, null spec_handle_ind " +
		"from b_service_member m, b_service s, b_account_service_assgn ass, b_account acc, b_customer a, b_customer_details b " +
		"where m.service_id = s.service_id " +
		"and m.system_id = s.system_id " +
		"and m.srv_num = ? " +
		"and s.service_type = ? " +
		"and s.service_id = ass.service_id " +
		"and ass.acct_num = acc.acct_num " +
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " +
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null))) " +
		"and a.cust_num = acc.cust_num " +
		"and a.system_id = s.system_id " +
		"and b.cust_num = a.cust_num " +
		"and b.system_id = a.system_id ";
	
	private static final String SQL_GET_MULTIPLE_PARENT_CUSTOMER_LIST_BY_IMS =
	    "select a.system_id, a.cust_num, a.parent_cust_num, a.grp_id_doc_type, " +
        "(select bom_desc from b_lookup where bom_grp_id = 'GRPIDDOCTYP' and bom_code = a.grp_id_doc_type and bom_status = 'A') grp_id_doc_type_desc, " +
		"a.grp_id_doc_num, a.id_doc_type, " +
		"(select bom_desc from b_lookup where bom_grp_id = 'IDDOCTYP' and bom_code = a.id_doc_type and bom_status = 'A') id_doc_type_desc, " +
	    "a.id_doc_num, a.title, a.cust_first_name, a.cust_last_name, b.company_name, a.cust_type, " +
	    "null cust_catg, null cust_catg_desc, null premier_type, null blacklist_ind, null remarks_ind, null wip_ind, null spec_handle_ind " +
        "from b_service s, b_account_service_assgn ass, b_account acc, b_customer a, b_customer_details b " +
		"where s.service_id = ? " +
		"and s.service_type = ? " +
		"and s.service_id = ass.service_id " +
		"and ass.acct_num = acc.acct_num " +
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " +
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null))) " +
		"and a.cust_num = acc.cust_num " +
		"and a.system_id = s.system_id " +
		"and b.cust_num = a.cust_num " +
		"and b.system_id = a.system_id ";
		
	private static final String SQL_GET_MULTIPLE_PARENT_CUSTOMER_LIST_BY_MOB =
		"select a.system_id, a.cust_num, a.parent_cust_num, a.grp_id_doc_type, " +
		"(select bom_desc from b_lookup where bom_grp_id = 'GRPIDDOCTYP' and bom_code = a.grp_id_doc_type and bom_status = 'A') grp_id_doc_type_desc, " +
		"a.grp_id_doc_num, a.id_doc_type, " +
		"(select bom_desc from b_lookup where bom_grp_id = 'IDDOCTYP' and bom_code = a.id_doc_type and bom_status = 'A') id_doc_type_desc, " +
		"a.id_doc_num, a.title, a.cust_first_name, a.cust_last_name, b.company_name, a.cust_type, " +
		"null cust_catg, null cust_catg_desc, null premier_type, null blacklist_ind, null remarks_ind, null wip_ind, null spec_handle_ind " +
		"from b_service s, b_account_service_assgn ass, b_account acc, b_customer a, b_customer_details b " +
		"where s.srv_num = ? " +
		"and s.service_type = ? " +
		"and (s.eff_end_date is null or months_between(trunc(sysdate,'month'), trunc(s.eff_end_date,'month')) <= 13) " +
		"and s.service_id = ass.service_id " +
		"and ass.acct_num = acc.acct_num " +
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " +
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null))) " +
		"and a.cust_num = acc.cust_num " +
		"and a.system_id = s.system_id " +
		"and b.cust_num = a.cust_num " +
		"and b.system_id = a.system_id ";	
	
	private static final String SQL_GET_MULTIPLE_PARENT_CUSTOMER_LIST_BY_LOGIN =
		"select a.system_id, a.cust_num, a.parent_cust_num, a.grp_id_doc_type, " +
		"(select bom_desc from b_lookup where bom_grp_id = 'GRPIDDOCTYP' and bom_code = a.grp_id_doc_type and bom_status = 'A') grp_id_doc_type_desc, " +
		"a.grp_id_doc_num, a.id_doc_type, " +
		"(select bom_desc from b_lookup where bom_grp_id = 'IDDOCTYP' and bom_code = a.id_doc_type and bom_status = 'A') id_doc_type_desc, " +
		"a.id_doc_num, a.title, a.cust_first_name, a.cust_last_name, b.company_name, a.cust_type, " +
		"null cust_catg, null cust_catg_desc, null premier_type, null blacklist_ind, null remarks_ind, null wip_ind, null spec_handle_ind " +
		"from b_service s, b_service_member_details_ims m, b_account_service_assgn ass, b_account acc, b_customer a, b_customer_details b " +
		"where m.login_id = ? " +
		"and m.domain_type = ? " +
		"and m.member_status in ('ACT','TERM') " +
		"and s.service_id = m.service_id " +
		"and ass.service_id = s.service_id " +
		"and ass.system_id = s.system_id " +
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " +
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null))) " +
		"and acc.acct_num = ass.acct_num " +
		"and acc.system_id = ass.system_id " +
		"and a.cust_num = acc.cust_num " +
		"and a.system_id = s.system_id " +
		"and b.cust_num = a.cust_num " +
		"and b.system_id = a.system_id ";
	
	private static final String SQL_GET_PCD_LOGIN_BY_FSA =
		"select distinct m.domain_type, m.login_id, p.servname, m.service_id, m.member_status " +
		"from b_account acc, b_account_service_assgn ass, b_service s, b_service_member_details_ims m, domain p " +
		"where acc.acct_num = ass.acct_num " +
		"and acc.system_id = ass.system_id " +
		"and acc.cust_num = ? " +
		"and ass.service_id = s.service_id " +
		"and s.system_id = ? " +
		"and m.service_id = s.service_id " +
		"and m.member_status in ('ACT','TERM') " +
		"and m.domain_type <> 'P' " +
		"and p.domntype = m.domain_type " +
		"and (ass.eff_end_date is null or (ass.eff_end_date is not null and s.eff_end_date is not null " + 
		"and ass.eff_end_date = (select max(c1.eff_end_date) from b_account_service_assgn c1 " +
		"where c1.service_id = ass.service_id and c1.system_id = ass.system_id and c1.eff_end_date is not null))) ";
	
	private static final String SQL_GET_PCD_LOGIN_BY_FSA_BY_SINGLE_SEARCH =
		"select distinct m.domain_type, m.login_id, p.servname, m.service_id, m.member_status " +
		"from b_service_member_details_ims m, domain p " +
		"where m.service_id = ? " +
		"and m.member_status in ('ACT','TERM') " +
		"and m.domain_type <> 'P' " +
		"and p.domntype = m.domain_type ";
	
	private static final String SQL_CHECK_SPECIAL_COMPLAINT_CASE =
		"select bom_desc from b_lookup where bom_grp_id = 'BL_IDDOC_NUM' and bom_code = ? and bom_status = 'A' ";
	
    public String[] getParentCustNumByIdDocNum(String idDocType, String idDocNum) throws DAOException {
		try {
			return simpleJdbcTemplate.query(
					SQL_GET_PARENT_CUST_NUM_BY_ID_DOC_NUM, parentCustNumMapper, idDocType, idDocNum).toArray(new String[0]);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getParentCustNumByIdDocNum - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	   
    public String[] getParentCustNumByAcctNum(String acctNum) throws DAOException {
		try {
			return simpleJdbcTemplate.query(
					SQL_GET_PARENT_CUST_NUM_BY_ACCT_NUM, parentCustNumMapper, acctNum).toArray(new String[0]);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getParentCustNumByAcctNum - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

    public String[] getParentCustNumByBsn(String bsn) throws DAOException {
		try {
			List<String> itemList = simpleJdbcTemplate.query(SQL_GET_FSA_BY_BSN, bsnMapper, bsn);
			return itemList.isEmpty() ? new String[0] : 
				getParentCustNumByIms(LtsProfileConstant.SERVICE_TYPE_IMS, itemList.get(0));				
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getParentCustNumByBsn - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}    
    
    public String[] getParentCustNumByLts(String serviceType, String serviceNum) throws DAOException {
		try {
			return simpleJdbcTemplate.query(
					SQL_GET_PARENT_CUST_NUM_BY_LTS, parentCustNumMapper, serviceNum, serviceType).toArray(new String[0]);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getParentCustNumByLts - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
    
    public String[] getParentCustNumByIms(String serviceType, String serviceNum) throws DAOException {
		try {
			return simpleJdbcTemplate.query(
					SQL_GET_PARENT_CUST_NUM_BY_IMS, parentCustNumMapper, serviceNum, serviceType).toArray(new String[0]);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getParentCustNumByIms - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
    
    public String[] getParentCustNumByMob(String serviceType, String serviceNum) throws DAOException {
		try {
			return simpleJdbcTemplate.query(
					SQL_GET_PARENT_CUST_NUM_BY_MOB, parentCustNumMapper, serviceNum, serviceType).toArray(new String[0]);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getParentCustNumByMob - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
    
    public String[] getParentCustNumByImsLoginId(String loginId, String domainType) throws DAOException {
		try {
			return simpleJdbcTemplate.query(
					SQL_GET_PARENT_CUST_NUM_BY_IMS_LOGIN, parentCustNumMapper, loginId, domainType).toArray(new String[0]);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getParentCustNumByImsLoginId - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
    
    private ParameterizedRowMapper<String> parentCustNumMapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("PARENT_CUST_NUM");
		}
	};
    
	public List<CustomerOverviewDTO> customerSearchByParentCustNum(String parentCustNum) throws DAOException {		
	    try{
	    	List<CustomerOverviewDTO> itemList = simpleJdbcTemplate.query(
	    			SQL_GET_CUSTOMER_OVERVIEW_BY_PARENT_CUST_NUM, custSearchMapper, parentCustNum);
	    	if (!itemList.isEmpty()) {
	    		for (int i=0; i<itemList.size(); i++) {
	    			if (LtsProfileConstant.SYSTEM_ID_MOB.equals(itemList.get(i).getSystemId())) {
	    				itemList.get(i).setBlacklistInd(
	    						getMobBlackListInd(itemList.get(i).getIdDocTypeCode(), itemList.get(i).getIdDocNum()));
	    			}
	    		}
	    	}	    	
	    	return itemList.isEmpty() ? null : itemList;
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in customerSearchByParentCustNum - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<CustomerOverviewDTO> getLtsCustomerOverviewBySingleSearch(String parentCustNum, String serviceType, String serviceNum) throws DAOException {		
	    try{
	    	List<CustomerOverviewDTO> itemList = simpleJdbcTemplate.query(
	    			SQL_GET_LTS_CUSTOMER_OVERVIEW_BY_SINGLE_SEARCH, custSearchMapper, serviceNum, serviceType, parentCustNum);	    	   	
	    	return itemList.isEmpty() ? null : itemList;
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getLtsCustomerOverviewBySingleSearch - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<CustomerOverviewDTO> getImsCustomerOverviewBySingleSearch(String parentCustNum, String serviceType, String serviceNum) throws DAOException {		
	    try{
	    	List<CustomerOverviewDTO> itemList = simpleJdbcTemplate.query(
	    			SQL_GET_IMS_CUSTOMER_OVERVIEW_BY_SINGLE_SEARCH, custSearchMapper, serviceNum, serviceType, parentCustNum);	    	
	    	return itemList.isEmpty() ? null : itemList;
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getImsCustomerOverviewBySingleSearch - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<CustomerOverviewDTO> getMobCustomerOverviewBySingleSearch(String parentCustNum, String serviceType, String serviceNum) throws DAOException {		
	    try{
	    	List<CustomerOverviewDTO> itemList = simpleJdbcTemplate.query(
	    			SQL_GET_MOB_CUSTOMER_OVERVIEW_BY_SINGLE_SEARCH, custSearchMapper, serviceNum, serviceType, parentCustNum);
	    	if (!itemList.isEmpty()) {
	    		for (int i=0; i<itemList.size(); i++) {	    			
	    			itemList.get(i).setBlacklistInd(
	    					getMobBlackListInd(itemList.get(i).getIdDocTypeCode(), itemList.get(i).getIdDocNum()));
	    		}
	    	}	    	
	    	return itemList.isEmpty() ? null : itemList;
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getMobCustomerOverviewBySingleSearch - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<ServiceSummaryDTO> getServiceSummary(String custNum, String systemId) throws DAOException {		
	    try{
	    	List<ServiceSummaryDTO> itemList = simpleJdbcTemplate.query(
	    			LtsProfileConstant.SYSTEM_ID_DRG.equals(systemId)? SQL_GET_LTS_SERVICE_SUMMARY 
	    					: LtsProfileConstant.SYSTEM_ID_IMS.equals(systemId)? SQL_GET_IMS_SERVICE_SUMMARY
	    							: SQL_GET_MOB_SERVICE_SUMMARY, serviceSummaryMapper, custNum, systemId);	    	    	
	    	return itemList.isEmpty() ? null : itemList;
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getServiceSummary - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public boolean isServiceExceedLimit(String custNum, String systemId, int maxServiceCnt) throws DAOException {
		try{
	    	List<ServiceSummaryDTO> itemList = simpleJdbcTemplate.query(
	    			LtsProfileConstant.SYSTEM_ID_DRG.equals(systemId)? SQL_SERVICE_COUNT_LTS + SQL_BY_MAXIMUM_SERVICE_COUNT 
	    					: LtsProfileConstant.SYSTEM_ID_IMS.equals(systemId)? SQL_SERVICE_COUNT_IMS  + SQL_BY_MAXIMUM_SERVICE_COUNT
	    							: SQL_SERVICE_COUNT_MOB + SQL_BY_MAXIMUM_SERVICE_COUNT, serviceCountMapper, custNum, systemId, maxServiceCnt);	    	    	
	    	return itemList.isEmpty() || itemList.size() <= maxServiceCnt? false : true;
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in isServiceExceedLimit - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<ServiceSummaryDTO> getServiceSummaryBySingleSearch(String custNum, String serviceType, String serviceNum, String systemId) throws DAOException {		
	    try{
	    	List<ServiceSummaryDTO> itemList = simpleJdbcTemplate.query(
	    			LtsProfileConstant.SYSTEM_ID_DRG.equals(systemId)? SQL_GET_LTS_SERVICE_SUMMARY_BY_SINGLE_SEARCH 
	    					: LtsProfileConstant.SYSTEM_ID_IMS.equals(systemId)? SQL_GET_IMS_SERVICE_SUMMARY_BY_SINGLE_SEARCH
	    							: SQL_GET_MOB_SERVICE_SUMMARY_BY_SINGLE_SEARCH, serviceSummaryMapper
	    							, custNum, serviceNum, serviceType, systemId);	    	    	
	    	return itemList.isEmpty() ? null : itemList;
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getServiceSummaryBySingleSearch - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<CustomerOverviewDTO> getMultipleParentCustNumListByParentCust(String parentCustNum) throws DAOException {		
	    try{
	    	List<CustomerOverviewDTO> itemList = simpleJdbcTemplate.query(
	    			SQL_GET_MULTIPLE_PARENT_CUSTOMER_LIST_BY_PARENT_CUST_NUM, custSearchMapper, parentCustNum);
	    	return itemList.isEmpty() ? null : itemList;
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getMultipleParentCustNumListByParentCust - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<CustomerOverviewDTO> getMultipleParentCustNumListByLts(String serviceType, String serviceNum) throws DAOException {		
	    try{
	    	List<CustomerOverviewDTO> itemList = simpleJdbcTemplate.query(
	    			SQL_GET_MULTIPLE_PARENT_CUSTOMER_LIST_BY_LTS, custSearchMapper, serviceNum, serviceType);	    	    	
	    	return itemList.isEmpty() ? null : itemList;
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getMultipleParentCustNumListByLts - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<CustomerOverviewDTO> getMultipleParentCustNumListByIms(String serviceType, String serviceNum) throws DAOException {		
	    try{
	    	List<CustomerOverviewDTO> itemList = simpleJdbcTemplate.query(
	    			SQL_GET_MULTIPLE_PARENT_CUSTOMER_LIST_BY_IMS, custSearchMapper, serviceNum, serviceType);	    	    	
	    	return itemList.isEmpty() ? null : itemList;
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getMultipleParentCustNumListByIms - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<CustomerOverviewDTO> getMultipleParentCustNumListByMob(String serviceType, String serviceNum) throws DAOException {		
	    try{
	    	List<CustomerOverviewDTO> itemList = simpleJdbcTemplate.query(
	    			SQL_GET_MULTIPLE_PARENT_CUSTOMER_LIST_BY_MOB, custSearchMapper, serviceNum, serviceType);	    	    	
	    	return itemList.isEmpty() ? null : itemList;
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getMultipleParentCustNumListByMob - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<CustomerOverviewDTO> getMultipleParentCustNumListByLogin(String loginId, String domainType) throws DAOException {		
	    try{
	    	List<CustomerOverviewDTO> itemList = simpleJdbcTemplate.query(
	    			SQL_GET_MULTIPLE_PARENT_CUSTOMER_LIST_BY_LOGIN, custSearchMapper, loginId, domainType);	    	    	
	    	return itemList.isEmpty() ? null : itemList;
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getMultipleParentCustNumListByLogin - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private ParameterizedRowMapper<CustomerOverviewDTO> custSearchMapper = new ParameterizedRowMapper<CustomerOverviewDTO>() {
		public CustomerOverviewDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			CustomerOverviewDTO customerOverview = new CustomerOverviewDTO();
			customerOverview.setSystemId(rs.getString("SYSTEM_ID"));
			customerOverview.setCustNum(rs.getString("CUST_NUM"));
			customerOverview.setParentCustNum(rs.getString("PARENT_CUST_NUM"));
			customerOverview.setGrpIdDocTypeCode(rs.getString("GRP_ID_DOC_TYPE"));
			customerOverview.setGrpIdDocTypeDesc(rs.getString("GRP_ID_DOC_TYPE_DESC"));
			customerOverview.setGrpIdDocNum(rs.getString("GRP_ID_DOC_NUM"));
			customerOverview.setIdDocTypeCode(rs.getString("ID_DOC_TYPE"));
			customerOverview.setIdDocTypeDesc(rs.getString("ID_DOC_TYPE_DESC"));
			customerOverview.setIdDocNum(rs.getString("ID_DOC_NUM"));			
			customerOverview.setTitle(rs.getString("TITLE"));
			customerOverview.setFirstName(rs.getString("CUST_FIRST_NAME"));
			customerOverview.setLastName(rs.getString("CUST_LAST_NAME"));
			customerOverview.setCompanyName(rs.getString("COMPANY_NAME"));
			customerOverview.setCustCatCode(rs.getString("CUST_CATG"));
			customerOverview.setCustCatDesc(rs.getString("CUST_CATG_DESC"));
			customerOverview.setPremier(rs.getString("PREMIER_TYPE"));
			customerOverview.setBlacklistInd(rs.getString("BLACKLIST_IND"));
			customerOverview.setCustomerRemarksInd(rs.getString("REMARKS_IND"));
			customerOverview.setWipInd(rs.getString("WIP_IND"));
			customerOverview.setSpecialHandlingInd(rs.getString("SPEC_HANDLE_IND"));
			customerOverview.setCustType(rs.getString("CUST_TYPE"));			
			return customerOverview;
		}
	};
	
	private ParameterizedRowMapper<ServiceSummaryDTO> serviceSummaryMapper = new ParameterizedRowMapper<ServiceSummaryDTO>() {
		public ServiceSummaryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			// set basic service summary
			ServiceSummaryDTO serviceSummary = new ServiceSummaryDTO();
			serviceSummary.setSystemId(rs.getString("SYSTEM_ID"));
			serviceSummary.setServiceNo(rs.getString("SRV_NUM"));
			serviceSummary.setDuplexBnumber(rs.getString("DUPLEX_B_NUM"));
			serviceSummary.setServiceStatus(rs.getString("SERVICE_STATUS"));
			serviceSummary.setServiceStartDate(rs.getString("EFF_START_DATE"));
			serviceSummary.setServiceEndDate(rs.getString("EFF_END_DATE"));
			serviceSummary.setServiceId(rs.getString("SERVICE_ID"));
			serviceSummary.setServiceType(rs.getString("SERVICE_TYPE"));
			serviceSummary.setDatCode(rs.getString("DAT_CD"));
			serviceSummary.setAddress_id(rs.getString("ADDR_ID"));
			serviceSummary.setTariff(rs.getString("CLS_OF_SRV"));
			serviceSummary.setServiceBoundary(rs.getString("SERVICE_BOUNDARY"));
			serviceSummary.setAddressPremier(rs.getString("ADDRESS_PREMIER"));
			serviceSummary.setEyeGroupId(rs.getString("EYE_GRP_ID"));
			// set address
			if (rs.getString("ADDR_ID") != null && !"0".equals(rs.getString("ADDR_ID"))) {
				AddressDTO address = new AddressDTO();
				address.setAreaCode(rs.getString("AREA_CD"));
				address.setAreaDesc(rs.getString("AREADSC"));
				address.setDistrictNum(rs.getString("DISTR_NUM"));
				address.setDistrictDesc(rs.getString("DIST_DESC"));
				address.setSectionCode(rs.getString("SECT_CD"));
				address.setSectionDesc(rs.getString("SECT_DESC"));
				address.setStreetName(rs.getString("STREET_NAME"));
				address.setHlLotNum(rs.getString("HLOT_NUM"));
				address.setStreetCatCode(rs.getString("ST_CATG_CD"));
				address.setStreetCatgDesc(rs.getString("STCATDSC"));
				address.setBuildNum(rs.getString("BUILD_NAME"));
				address.setFloorNum(rs.getString("FLOOR_NUM"));
				address.setUnitNum(rs.getString("UNIT_NUM"));
				address.setStreetNum(rs.getString("STREET_NUM"));			
				serviceSummary.setAddress(address);
			}			
			return serviceSummary;
		}
	};
	
	private ParameterizedRowMapper<ServiceSummaryDTO> serviceCountMapper = new ParameterizedRowMapper<ServiceSummaryDTO>() {
		public ServiceSummaryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			// set basic service summary
			ServiceSummaryDTO serviceSummary = new ServiceSummaryDTO();
			serviceSummary.setSystemId(rs.getString("SYSTEM_ID"));
			serviceSummary.setServiceNo(rs.getString("SRV_NUM"));					
			return serviceSummary;
		}
	};
	
	private String getMobBlackListInd(String idDocType, String idDocNum) throws DAOException {
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				//.withSchemaName("ops$bom")
				.withCatalogName("b_cc_mob_customer_pkg")
				.withProcedureName("check_black_list_exist")
				.declareParameters(
						new SqlParameter("i_doc_type", Types.VARCHAR),
						new SqlParameter("i_doc_num", Types.VARCHAR),
						new SqlOutParameter("o_black_list_exist", Types.VARCHAR),
						new SqlOutParameter("gnretval", Types.INTEGER),
						new SqlOutParameter("gnerrcode", Types.INTEGER),
						new SqlOutParameter("gserrtext", Types.VARCHAR));
		
		jdbcCall.setAccessCallParameterMetaData(false);
		
		MapSqlParameterSource inMap = new MapSqlParameterSource();
		inMap.addValue("i_doc_type", idDocType);
		inMap.addValue("i_doc_num", idDocNum);
		Map<String, Object> out = null;

		try {
			out = jdbcCall.execute(inMap);
		} catch (Exception e) {
			logger.error("Exception caught in getMobBlackListInd()", e);
			throw new DAOException(e.getMessage(), e);
		}
		if ((Integer)out.get("gnerrcode") == 0 ) {
			return (String)out.get("o_black_list_exist");	
		}
		return null;
		
	}
	
	public List<AccountDTO> getRelatedAcctsOfService(String custNum, String systemId) throws DAOException {		
	    try{
	    	List<AccountDTO> itemList = simpleJdbcTemplate.query(
	    			LtsProfileConstant.SYSTEM_ID_DRG.equals(systemId)? SQL_GET_LTS_ACCOUNT_OF_SERVICE_SUMMARY 
					: LtsProfileConstant.SYSTEM_ID_IMS.equals(systemId)? SQL_GET_IMS_ACCOUNT_OF_SERVICE_SUMMARY
							: SQL_GET_MOB_ACCOUNT_OF_SERVICE_SUMMARY, accountListMapper, custNum, systemId);
	    	return itemList.isEmpty() ? null : itemList;
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getLtsRelatedAccts - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<AccountDTO> getRelatedAcctsOfServiceBySingleSearch(String custNum, String serviceId, String serviceType, String systemId) throws DAOException {		
	    try{
	    	List<AccountDTO> itemList = simpleJdbcTemplate.query(
	    			LtsProfileConstant.SYSTEM_ID_DRG.equals(systemId)? SQL_GET_LTS_ACCOUNT_OF_SERVICE_SUMMARY_BY_SINGLE_SEARCH
					: LtsProfileConstant.SYSTEM_ID_IMS.equals(systemId)? SQL_GET_IMS_ACCOUNT_OF_SERVICE_SUMMARY_BY_SINGLE_SEARCH
							: SQL_GET_MOB_ACCOUNT_OF_SERVICE_SUMMARY_BY_SINGLE_SEARCH, accountListMapper, custNum, serviceId, serviceType, systemId);
	    	return itemList.isEmpty() ? null : itemList;
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getRelatedAcctsOfServiceBySingleSearch - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
		
	private ParameterizedRowMapper<AccountDTO> accountListMapper = new ParameterizedRowMapper<AccountDTO>() {
		public AccountDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			AccountDTO account = new AccountDTO();
			account.setCustNum(rs.getString("CUST_NUM"));
	        account.setAccountNum(rs.getString("ACCT_NUM"));
	        account.setChargeCat(rs.getString("CHRG_TYPE"));
	        account.setAccountType(rs.getString("ACCT_TYPE"));
	        account.setServiceId(rs.getString("SERVICE_ID"));
	        return account;
		}
	};
	
	public List<PcdLogin> getPcdLoginByFsa(String custNum, String systemId) throws DAOException {		
	    try{
	    	List<PcdLogin> itemList = simpleJdbcTemplate.query(SQL_GET_PCD_LOGIN_BY_FSA, pcdLoginMapper, custNum, systemId);
	    	return itemList.isEmpty() ? null : itemList;
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getPcdLoginByFsa - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<PcdLogin> getPcdLoginByFsaBySingleSearch(String serviceId) throws DAOException {		
	    try{
	    	List<PcdLogin> itemList = simpleJdbcTemplate.query(
	    			SQL_GET_PCD_LOGIN_BY_FSA_BY_SINGLE_SEARCH, pcdLoginMapper, serviceId);
	    	return itemList.isEmpty() ? null : itemList;
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getPcdLoginByFsaBySingleSearch - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private ParameterizedRowMapper<PcdLogin> pcdLoginMapper = new ParameterizedRowMapper<PcdLogin>() {
		public PcdLogin mapRow(ResultSet rs, int rowNum) throws SQLException {
			PcdLogin pcdLogin = new PcdLogin();
			pcdLogin.setDomainType(rs.getString("DOMAIN_TYPE"));
			pcdLogin.setLoginID(rs.getString("LOGIN_ID"));
			pcdLogin.setServiceName(rs.getString("SERVNAME"));
			pcdLogin.setServiceId(rs.getString("SERVICE_ID"));
			pcdLogin.setMemberStatus(rs.getString("MEMBER_STATUS"));
			return pcdLogin;
		}
	};
	
	public AlertDTO checkSpecialComplaintCase(String idDocNum) throws DAOException {		
	    try{
	    	List<AlertDTO> itemList = simpleJdbcTemplate.query(SQL_CHECK_SPECIAL_COMPLAINT_CASE, alertMapper, idDocNum);	    	    	
	    	if (itemList.isEmpty()) {
	    		AlertDTO alertDTO = new AlertDTO();
	    		alertDTO.setAlertInd("N");
	    		return alertDTO;
	    	} else {
		    	return itemList.get(0);
	    	}
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in checkSpecialComplaintCase - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	private ParameterizedRowMapper<AlertDTO> alertMapper = new ParameterizedRowMapper<AlertDTO>() {
		public AlertDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			AlertDTO alertDTO = new AlertDTO();
			alertDTO.setAlertContent(rs.getString("BOM_DESC"));
			alertDTO.setAlertInd("Y");	
			return alertDTO;
		}
	};
	
	private ParameterizedRowMapper<String> bsnMapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("SERVICE_ID");
		}
	};
	
}
