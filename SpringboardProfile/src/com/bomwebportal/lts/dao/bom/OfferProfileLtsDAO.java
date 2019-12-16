package com.bomwebportal.lts.dao.bom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.bom.BomAttbDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;
import com.pccw.springboard.svc.server.dto.lts.OfferDetailCommitmentDTO;
import com.pccw.springboard.svc.server.dto.lts.OfferDetailDiscountDTO;
import com.pccw.springboard.svc.server.dto.lts.OfferDetailIncentiveDTO;
import com.pccw.springboard.svc.server.dto.lts.OfferDetailRecurringChargesDTO;

public class OfferProfileLtsDAO extends BaseDAO {

	
	private static final String SQL_GET_LTS_ENDED_OFFER_PROFILE = 
		"SELECT DISTINCT " +
		"		offer_prod.PACK_CD, "+
		"		NVL (compt.PSEF_CD, offer_prod.PACK_CD) psef_cd, "+
		"    	offer.offer_sub_key, offer.offer_id, offer.offer_sub_cd, "+
		"       offer.offer_sub_id, "+
		"        TO_CHAR (offer.eff_start_date, 'dd/mm/yyyy') AS eff_start_date, "+
		"        TO_CHAR (offer.eff_end_date, 'dd/mm/yyyy') AS eff_end_date, "+
		"        TO_CHAR (cond.eff_start_date, 'dd/mm/yyyy') AS cond_eff_start_date, "+
		"        TO_CHAR (cond.eff_end_date, 'dd/mm/yyyy') AS cond_eff_end_date, "+
		"        DECODE (cond.eff_end_date, NULL, 0, ROUND (MONTHS_BETWEEN (TO_DATE (:applnDate, 'dd/mm/yyyy'), cond.eff_end_date), 3)) AS expired_months, "+
		"        TO_CHAR (pri_sch.promotion_start_date, 'dd/mm/yyyy') AS promotion_start_date, "+
		"        TO_CHAR (pri_sch.promotion_end_date, 'dd/mm/yyyy' ) AS promotion_end_date, "+
		"        ROUND (MONTHS_BETWEEN (TO_DATE (:applnDate, 'dd/mm/yyyy'), pri_sch.promotion_end_date),3) AS promotion_expired_months, "+
		"        off_assgn.tc_grp_id "+
		"   FROM b_subc_grp_assgn grp_assgn, "+
		"        b_offer_sub offer, "+
		"        b_subc_condition cond, "+
		"        b_subc_pricing_scheme pri_sch, "+
		"		B_SUBC_OFFER_PROD offer_prod, "+
		"	  	B_SUBC_COMPT compt, "+
		"        b_offer_assgn_a off_assgn "+
		"  WHERE grp_assgn.subc_grp_num = offer.subc_grp_num "+
		"    AND offer.offer_sub_id = off_assgn.offer_sub_id "+
		"    AND offer.offer_sub_key = cond.offer_sub_key(+) "+
		"    AND offer.offer_sub_key = pri_sch.offer_sub_key(+) "+
		"    AND offer.eff_end_date IS NOT NULL "+
		"	AND offer.eff_end_date <= TO_DATE (:applnDate, 'dd/mm/yyyy') "+
		"    AND grp_assgn.subc_mem_num = :serviceId "+
		"	and offer.offer_sub_key = offer_prod.offer_sub_key(+) "+
		"	and offer_prod.PROD_SUB_KEY = compt.prod_sub_key(+) ";
	
	private static final String SQL_GET_LTS_OFFER_PROFILE =
			"select distinct psef.pack_cd, nvl(psef.psef_cd, psef.pack_cd) psef_cd, offer.offer_sub_key, offer.offer_id, offer.offer_sub_cd, offer.offer_sub_id, " + 
			"to_char(offer.eff_start_date, 'dd/mm/yyyy') as eff_start_date, to_char(offer.eff_end_date, 'dd/mm/yyyy') as eff_end_date, " + 
			"to_char(cond.eff_start_date, 'dd/mm/yyyy') as cond_eff_start_date, to_char(cond.eff_end_date, 'dd/mm/yyyy') as cond_eff_end_date, " + 
			"decode(cond.eff_end_date, null, 0, round(months_between(to_date(:applnDate,'dd/mm/yyyy'), cond.eff_end_date), 3)) as expired_months, " + 
			"to_char(pri_sch.promotion_start_date, 'dd/mm/yyyy') as promotion_start_date, to_char(pri_sch.promotion_end_date, 'dd/mm/yyyy') as promotion_end_date, " + 
			"round(months_between(to_date(:applnDate,'dd/mm/yyyy'), pri_sch.promotion_end_date),3) as promotion_expired_months, off_assgn.tc_grp_id " + 
			"from b_subc_grp_assgn grp_assgn, b_offer_sub offer, b_subc_condition cond, b_subc_pricing_scheme pri_sch, b_ord_simple_cc_offer_view_v psef, b_offer_assgn_a off_assgn " + 
			"where grp_assgn.subc_grp_num = offer.subc_grp_num " + 
			"and offer.offer_sub_id = off_assgn.offer_sub_id " + 
			"and offer.offer_sub_key = cond.offer_sub_key(+) " + 
			"and offer.offer_sub_key = pri_sch.offer_sub_key(+) " + 
			"and grp_assgn.eff_start_date(+) <= to_date(:applnDate,'dd/mm/yyyy') and nvl(grp_assgn.eff_end_date(+), to_date('31129999','ddmmyyyy')) > to_date(:applnDate,'dd/mm/yyyy') " + 
			"and offer.eff_start_date(+) <= to_date(:applnDate,'dd/mm/yyyy') and nvl(offer.eff_end_date(+), to_date('31129999','ddmmyyyy')) > to_date(:applnDate,'dd/mm/yyyy') " +  
			"and psef.service_id = grp_assgn.subc_mem_num and psef.offer_sub_key = offer.offer_sub_key " + 
			"and grp_assgn.subc_mem_num = :serviceId";
				
	private static final String SQL_GET_COMPONENT_PSEF_PROFILE = 
			"select psef_cd " +
			"from b_subc_compt " +
			"where subscriber_system_id = 'DRG' " +
			"and eff_start_date <= sysdate and (eff_end_date is null or eff_end_date > sysdate) " +
			"and subscriber_num = ?";

	private static final String SQL_GET_IMS_OFFER_PROFILE = 
			"select distinct offer_sub_key, offer_id, prod_id, offer_sub_cd, offer_sub_id, " + 
			"eff_start_date, eff_end_date, " + 
			"to_char(cond_eff_start_date, 'dd/mm/yyyy') cond_eff_start_date, to_char(cond_eff_end_date, 'dd/mm/yyyy') cond_eff_end_date, " + 
			"decode(cond_eff_end_date, null, 0, round(months_between(to_date(:applnDate,'dd/mm/yyyy'), cond_eff_end_date), 3)) as expired_months " + 
			"from ( " + 
			"select offer.offer_sub_key, offer.offer_id, prod.prod_id, offer.offer_sub_cd, offer.offer_sub_id, " + 
			"to_char(offer.eff_start_date, 'dd/mm/yyyy') as eff_start_date, to_char(offer.eff_end_date, 'dd/mm/yyyy') as eff_end_date, " +
			"cond.cstartdt as cond_eff_start_date, add_months(cond.cstartdt, cond.cperiod) as cond_eff_end_date " + 
			"from b_subc_grp_assgn grp_assgn, b_offer_sub offer, b_subc_offer_prod prod, cucommit cond " + 
			"where grp_assgn.subc_grp_num = offer.subc_grp_num " + 
			"and offer.offer_sub_key = prod.offer_sub_key " + 
			"and cond.csgrpid(+) = :fsa " + 
			"and prod.prod_id = cond.prodid(+) " + 
			"and cond.eartmdt(+) is null " +
			"and prod.eff_start_date = cond.cstartdt(+) " + 
			"and grp_assgn.eff_start_date(+) <= to_date(:applnDate,'dd/mm/yyyy') and nvl(grp_assgn.eff_end_date(+), to_date('31129999','ddmmyyyy')) > to_date(:applnDate,'dd/mm/yyyy') " +
			"and offer.eff_start_date(+) <= to_date(:applnDate,'dd/mm/yyyy') and nvl(offer.eff_end_date(+), to_date('31129999','ddmmyyyy')) > to_date(:applnDate,'dd/mm/yyyy') " +
			"and grp_assgn.subc_mem_num = :fsa)";
	
	private static final String SQL_GET_PSEF_BY_OFFER_ID = 
			"select distinct psef_cd " +
			"from ( " +
			"select p.psef_cd " +
			"from b_offer_a o, b_offer_assgn_a oa, b_offer_product_assgn_a opa, b_psef_assgn pa, b_psef_a p " +
			"where o.offer_id = oa.offer_id " +
			"and oa.offer_sub_id = opa.offer_sub_id " +
			"and pa.item_type = 'R' " +
			"and pa.item_id = opa.offer_prod_assgn_id " +
			"and pa.psef_id = p.psef_id " +
			"and o.offer_id in (:offerList) " +
			"union "+
			"select p.psef_cd " + 
			"from b_offer_a o, b_offer_assgn_a oa, b_offer_product_assgn_a opa, b_product_compt_assgn_a pc, b_psef_assgn_a pa, b_psef_a p " +
			"where o.offer_id = oa.offer_id " +
			"and oa.offer_sub_id = opa.offer_sub_id " +
			"and opa.prod_id = pc.prod_id " +
			"and pa.item_id = pc.compt_id " +
			"and pa.psef_id = p.psef_id " +
			"and pa.item_type = 'C' " +
			"and o.offer_id in (:offerList))";
	
	private static final String SQL_GET_COMMITMENT_BY_SERVICE_ID =
			"select distinct nvl(bs.srv_num, bs.service_id) service_number, bs.service_id, bs.system_id, bsc.offer_sub_key, bsc.tc_id, bsc.tc_cd, bsc.credit_prod_id, bsc.start_mth, " +
			"bsc.measure_unit, bsc.measure_qty, trim(to_char(bsc.eff_start_date,'DD/MM/YYYY')) as eff_start_date, bsc.eff_start_date as sortdate, " +
			"trim(to_char(bsc.eff_end_date,'DD/MM/YYYY')) as eff_end_date, bsc.duration, bsc.modify_ind, " +
			"trim(to_char(bsc.last_upd_date,'DD/MM/YYYY')) as last_upd_date, bsc.last_upd_by, " +
			"bsc.rowseqnb, bpa.prod_desc, bca.tc_desc, to_char(add_months(bsc.eff_start_date, bsc.duration)-1,'DD/MM/YYYY') as cal_duration, " +
			"bsc.service_order_num, trim(to_char(bsc.appl_date,'DD/MM/YYYY')) as appl_date, bos.offer_id, boa.offer_cd, " +
			"bsp.pen_id, bsp.penalty_amt, to_char(bsp.eff_start_date,'DD/MM/YYYY') as pen_eff_start_date, " +
			"trim(to_char(bsp.eff_end_date,'DD/MM/YYYY')) as pen_eff_end_date, " +
			"bsp.modify_ind as pen_modify_ind, trim(to_char(bsp.last_upd_date,'DD/MM/YYYY')) as pen_last_upd_date, bsp.last_upd_by as pen_last_upd_by, " +
			"bsp.rowseqnb as pen_rowseqnb, bspdi.waive_reason, bspdi.domain_type, bspdi.term_order_num, bspdi.scale, " +
			"trim(to_char(case when bs.system_id = 'DRG' " +
			"then bsp.order_term_date else bspdi.early_end_date end, 'DD/MM/YYYY')) early_end_date, " +
			"bspdi.manual_del_ind, bspdi.com_option, bl.bom_desc as com_option_desc, bspdi.rowseqnb as pen_detail_rowseqnb, " +
			"bspdi.grpseq, bsp.pen_subs_id, bsp.waive_ind, bsp.pnal_cd, lpad(bsc.credit_prod_id,7,'0') as pad_credit_prod_id, " +
			"bspdi.self_chg_ind as self_chg_ind, " +
			"trim(to_char(bspdi.autoroll_effectdt, 'DD/MM/YYYY'))  as autoroll_effectdt, " +
			"trim(to_char(bspdi.autoroll_termdt, 'DD/MM/YYYY'))  as autoroll_termdt, " +
			"bspdi.target_commitid as target_commitid " +
			"from b_subc_condition bsc, b_product_a bpa, b_condition_a bca, " +
			"b_subc_penalty bsp, b_subc_penalty_details_ims bspdi, " +
			"b_offer_sub bos, b_offer_a boa, b_subc_grp_assgn bsga, b_service bs, b_lookup bl " +
			"where bsc.CREDIT_PROD_ID = bpa.PROD_ID " +
			"and bsc.TC_ID = bca.TC_ID " +
			"and bsc.offer_sub_key = bsp.offer_sub_key " +
			"and bsc.tc_id = bsp.tc_id " +
			"and bsc.credit_prod_id = bsp.credit_prod_id " +
			"and nvl(bsc.service_order_num, '0') = nvl(bspdi.service_order_num, '0') " +
			"and bsp.pen_subs_id = bspdi.pen_subs_id(+) " +
			"and bsc.offer_sub_key = bos.offer_sub_key " +
			"and bsga.subc_grp_num =  bos.subc_grp_num " +
			"and bsga.subc_mem_num = bs.service_id " +
			"and bsga.subc_mem_system_id = bs.system_id " +
			"and bspdi.com_option = bl.ims_code(+) " +
			"and (bl.ims_grp_id = 'com_option' or bl.ims_grp_id is null) " +
			"and bos.offer_id = boa.offer_id(+) " +
			"and bsc.refer_by is null " +
			"and bsp.refer_by is null " +
			"and bspdi.refer_by is null " +
			"and bs.service_id = ? " +
			"and bs.system_id = ? ";
	
	private static final String SQL_GET_EFFECTIVE_FOR_COMMITMENT =
	    "and trunc(sysdate) <  nvl(bos.eff_end_date, to_date('47121231','YYYYMMDD')) " +
	    "order by service_number, boa.offer_cd, sortdate  ";
	
    private static final String SQL_GET_HISTORY_FOR_COMMITMENT =	
		"and trunc(sysdate) >=  nvl(bos.eff_end_date, to_date('47121231','YYYYMMDD')) " +
		"order by service_number, boa.offer_cd, sortdate  ";
	
    private static final String SQL_GET_RECURRING_CHARGES_BY_SERVICE_ID =
	    "select distinct bos.offer_id, bos.offer_sub_key, bsps.prc_sch_id, bsps.sub_fee_ind, " +
	    "bsop.prod_id, bspa.item_type, nvl(bos.qty, 1) * decode( bspt.modify_ind,'Y',bspt.chrg_amt, bpt.chrg_amt) chrg_amt,  trim(to_char(bspa.eff_start_date, 'DD/MM/YYYY')) eff_start_date, " +
	    "trim(to_char(bspa.eff_end_date, 'DD/MM/YYYY')) eff_end_date, bspa.prc_combn_id, bpt.prc_tier_id, bspt.modify_ind, " +
	    "bspt.chrg_percent, bp.prod_desc, bps.prc_sch_cd, bps.prc_sch_desc, " +
	    "nvl(bos.qty, 1) * bpt.chrg_amt default_amt, boa.offer_cd, trim(to_char(bsps.promotion_start_date, 'DD/MM/YYYY')) promotion_start_date, trim(to_char(bsps.promotion_end_date, 'DD/MM/YYYY')) promotion_end_date, " +
	    "bsop.prod_sub_key, bp.chrg_cat, bsop.pack_cd, nvl(bs.srv_num, bs.service_id) service_number, bs.service_id, " +
	    "bps.rate_type, bps.prc_unit, nvl(bos.qty, 1) as offer_qty, decode( bspt.modify_ind,'Y',bspt.chrg_amt, bpt.chrg_amt) as unit_chrg_amt " +
	    ",trim(to_char(bsop.autoroll_effectdt, 'DD/MM/YYYY')) as autoroll_effectdt " +
	    ",trim(to_char(bsop.autoroll_termdt, 'DD/MM/YYYY'))  as autoroll_termdt " +
	    ", compt_count.compt_qty, compt_count.measure_unit_qty " +
	    "from b_offer_sub bos, b_subc_offer_prod bsop, b_product_a bp, " +
	    "b_subc_pricing_assgn bspa, b_pricing_scheme_a bps, b_pricing_tier_a bpt, " +
	    "b_subc_pricing_scheme bsps, b_subc_pricing_tier bspt, b_offer_a boa, " +
	    "b_subc_grp_assgn bsga, b_service bs " +
	    ", (select prod_sub_key, sum(qty) as compt_qty, sum(case when primary_ind = 'Y' " +
	    "then measure_unit_qty else 0 end) as measure_unit_qty from b_subc_compt " +
	    "group by prod_sub_key) compt_count " +   
	    "where bos.offer_sub_key = bsop.offer_sub_key " +
	    "and bos.offer_id = boa.offer_id(+) " +
	    "and bsop.prod_id = bp.prod_id " +
	    "and bsop.prod_id = bspa.item_id " +
	    "and bspa.item_type in ('P', 'O') " +  
	    "and bspa.PRC_SCH_ID = bps.PRC_SCH_ID " + 
	    "and bspt.prc_sch_id = bpt.prc_sch_id " +
	    "and bps.prc_sch_id = bpt.prc_sch_id " +
	    "and (bps.prc_type = '01' or bps.prc_type = '02') " +
	    "and bos.offer_sub_key = bsps.offer_sub_key " +
	    "and bsps.offer_sub_key = bspt.offer_sub_key " +
	    "and bsps.prc_sch_id = bspt.prc_sch_id " +
	    "and bsps.offer_sub_key = bspa.offer_sub_key " +
	    "and bsps.prc_sch_id = bspa.prc_sch_id " +
	    "and nvl(bspa.eff_end_date, to_date('31124721', 'DDMMYYYY')) " +
	    "between (decode(bspa.eff_start_date, bspa.eff_end_date, bps.eff_start_date, bps.eff_start_date + 1)) and nvl(bps.eff_end_date, to_date('31124721', 'DDMMYYYY')) " +
	    "and nvl(bspt.eff_end_date, to_date('31124721', 'DDMMYYYY')) " +
	    "between (decode(bspt.eff_start_date, bspt.eff_end_date, bpt.eff_start_date, bpt.eff_start_date + 1)) and nvl(bpt.eff_end_date, to_date('31124721', 'DDMMYYYY')) " +
	    "and bsga.subc_grp_num =  bos.subc_grp_num " +
	    "and bsga.subc_mem_num = bs.service_id " +
	    "and bsga.subc_mem_system_id = bs.system_id " +
	    "and compt_count.prod_sub_key = bsop.prod_sub_key " +
	    "and bs.service_id = ? " + 
	    "and bs.system_id = ? ";    
    
    private static final String SQL_GET_EFFECTIVE_FOR_RECURRING_CHARGES =
    	"and trunc(sysdate) <  nvl(bos.eff_end_date, to_date('47121231','YYYYMMDD')) " +
    	"and trunc(sysdate) <  nvl(bsps.eff_end_date, to_date('47121231','YYYYMMDD')) " + 
    	"and trunc(sysdate) <  nvl(bspa.eff_end_date, to_date('47121231','YYYYMMDD')) " + 
    	"and trunc(sysdate) <  nvl(bspt.eff_end_date, to_date('47121231','YYYYMMDD')) " +
        "order by service_number, boa.offer_cd  ";
	
    private static final String SQL_GET_HISTORY_FOR_RECURRING_CHARGES =	
    	"and (trunc(sysdate) >=  nvl(bos.eff_end_date, to_date('47121231','YYYYMMDD')) " +
    	"or (trunc(sysdate) >=  nvl(bsps.eff_end_date, to_date('47121231','YYYYMMDD')) " +
    	"and trunc(sysdate) >=  nvl(bspa.eff_end_date, to_date('47121231','YYYYMMDD')) " +
    	"and trunc(sysdate) >=  nvl(bspt.eff_end_date, to_date('47121231','YYYYMMDD')))) " +
    	"order by service_number, boa.offer_cd  ";
    
    private static final String SQL_GET_DISCOUNT_BY_SERVICE_ID =
	    "select distinct bsd.dis_id, bsd.dis_cd, bsd.dis_type, bsd.credit_prod_id, " +
	    "bsd.discount_amt, bsd.discount_pct, trim(to_char(bsd.eff_start_date,'DD/MM/YYYY')) eff_start_date, " + 
	    "trim(to_char(bsd.eff_end_date,'DD/MM/YYYY')) eff_end_date, " +
	    "bsd.domain_type, bsd.modify_ind, trim(to_char(bsd.last_upd_date,'DD/MM/YYYY')) last_upd_date, bsd.last_upd_by, " +
	    "bsd.rowseqnb, trim(to_char(nvl(bsddi.cr_date, bsd.cc_create_date),'DD/MM/YYYY')) cr_date, bsddi.log_id, bsddi.grpflag, " +
	    "bsddi.recstat, bsddi.bv_user_id, trim(to_char(bsddi.sent_date,'DD/MM/YYYY')) sent_date, bsddi.remarks, " +
	    "bsddi.del_order_num, bsddi.manual_del_ind, bsddi.service_order_num, bsddi.rowseqnb, " +
	    "bsddi.dis_seq_num, bsddi.grpseq, bsd.dis_subs_id, bpa.prod_desc, bda.dis_desc, bsd.offer_sub_key, " +
	    "nvl(bs.srv_num, bs.service_id) service_number, bos.offer_id, boa.offer_cd " +
	    "from b_subc_discount bsd, b_discount_a bda, b_product_a bpa, " +
	    "b_subc_discount_details_ims bsddi, " +
	    "b_offer_sub bos, b_offer_a boa, b_subc_grp_assgn bsga, b_service bs " + 
	    "where bsd.offer_sub_key = bsddi.offer_sub_key(+) " +
	    "and bsd.DIS_ID = bda.dis_id(+) " +
	    "and bsd.CREDIT_PROD_ID = bpa.prod_id(+) " +
	    "and bsd.dis_id = bsddi.dis_id(+) " +
	    "and bsd.dis_subs_id = bsddi.dis_subs_id(+) " +
	    "and bsd.offer_sub_key = bos.offer_sub_key " +
	    "and bsga.subc_grp_num =  bos.subc_grp_num " +
	    "and bsga.subc_mem_num = bs.service_id " +
	    "and bsga.subc_mem_system_id = bs.system_id " +
	    "and bos.offer_id = boa.offer_id(+) " +
	    "and bsd.refer_by is null " +
	    "and bsddi.refer_by is null " +
	    "and bs.service_id = ? " +
	    "and bs.system_id = ? ";

    private static final String SQL_GET_EFFECTIVE_FOR_DISCOUNT =
    	"and trunc(sysdate) <  nvl(bos.eff_end_date, to_date('47121231','YYYYMMDD')) " +
        "order by service_number, boa.offer_cd, cr_date, bsddi.service_order_num, bsddi.log_id, bsddi.dis_seq_num ";
	
    private static final String SQL_GET_HISTORY_FOR_DISCOUNT =	
    	"and trunc(sysdate) >=  nvl(bos.eff_end_date, to_date('47121231','YYYYMMDD')) " +
        "order by service_number, boa.offer_cd, cr_date, bsddi.service_order_num, bsddi.log_id, bsddi.dis_seq_num ";
    	
    private static final String SQL_GET_INCENTIVE_BY_SERVICE_ID =
	    "select distinct bsi.inc_id, bsi.inc_cd, bsi.credit_prod_id, bsi.start_mth, " +
	    "bsi.MEASURE_UNIT, bsi.MEASURE_QTY,  to_char(bsi.eff_start_date,'DD/MM/YYYY') eff_start_date, to_char(bsi.eff_end_date,'DD/MM/YYYY') eff_end_date, " +
	    "bsi.domain_type, bsi.modify_ind, to_char(bsi.last_upd_date,'DD/MM/YYYY') last_upd_date, bsi.last_upd_by, " +
	    "bsi.rowseqnb, to_char(nvl(bsidi.cr_date, bsi.cc_create_date),'DD/MM/YYYY') cr_date, bsidi.log_id, bsidi.grpflag, " +
	    "bsidi.recstat, bsidi.bv_user_id, to_char(bsidi.sent_date,'DD/MM/YYYY') sent_date, bsidi.remarks, " +
	    "bsidi.del_order_num, bsidi.manual_del_ind, bsidi.service_order_num, bsidi.rowseqnb, " +
	    "bsidi.inc_seq_num, bsidi.grpseq, bsi.inc_subs_id, bpa.prod_desc, bia.inc_desc, bsi.offer_sub_key, " +
	    "nvl(bs.srv_num, bs.service_id) service_number, bos.offer_id, boa.offer_cd " +
	    "from b_subc_incentive bsi, b_product_a bpa, b_incentive_a bia, " +
	    "b_subc_incentive_details_ims bsidi, " +
	    "b_offer_sub bos, b_offer_a boa, b_subc_grp_assgn bsga, b_service bs " + 
	    "where bsi.offer_sub_key = bsidi.offer_sub_key(+) " +
	    "and bsi.INC_ID = bia.inc_id(+) " +
	    "and bsi.CREDIT_PROD_ID = bpa.prod_id(+) " +
	    "and bsi.inc_id = bsidi.inc_id(+) " +
	    "and bsi.inc_subs_id = bsidi.inc_subs_id(+) " +
	    "and bsi.offer_sub_key = bos.offer_sub_key  " +
	    "and bsga.subc_grp_num =  bos.subc_grp_num " +
	    "and bsga.subc_mem_num = bs.service_id " +
	    "and bsga.subc_mem_system_id = bs.system_id " +
	    "and bos.offer_id = boa.offer_id(+) " +
	    "and bsi.refer_by is null " +
	    "and bsidi.refer_by is null " +
	    "and bs.service_id = ? " +
	    "and bs.system_id = ? ";
    
    private static final String SQL_GET_EFFECTIVE_FOR_INCENTIVE =
    	"and trunc(sysdate) <  nvl(bos.eff_end_date, to_date('47121231','YYYYMMDD')) " +
    	"order by service_number, boa.offer_cd, cr_date, bsidi.service_order_num, bsidi.log_id, bsidi.inc_seq_num ";
	
    private static final String SQL_GET_HISTORY_FOR_INCENTIVE =	
    	"and trunc(sysdate) >=  nvl(bos.eff_end_date, to_date('47121231','YYYYMMDD')) " +
    	"order by service_number, boa.offer_cd, cr_date, bsidi.service_order_num, bsidi.log_id, bsidi.inc_seq_num ";

    
    private static final String SQL_GET_ALL_BOM_ATTB_BY_OFFER = 
    	" select ba.ATTB_ID, ba.ATTB_DESC, ba.ATTB_VALUE, 'O' AS ATTB_LEVEL, bat.ATTB_TYPE_DESC " +
    	" from  " +
    	" b_attb_a ba, " +
    	" b_attb_type_a bat, " +
    	" b_offer_attb_a boattb, " +
    	" b_offer_a bo " +
    	" where (bo.offer_cd = :offerCd or bo.offer_id = :offerId)" +
    	" and trunc(bo.EFF_START_DATE) <= trunc(to_date(:appDate, 'yyyyMMdd')) " +
    	" and ( trunc(bo.EFF_END_DATE) > trunc(to_date(:appDate, 'yyyyMMdd')) or bo.eff_end_date is null ) " +
    	" and bo.offer_id = boattb.OFFER_ID " +
    	" and trunc(boattb.EFF_START_DATE) <= trunc(to_date(:appDate, 'yyyyMMdd')) " +
    	" and ( trunc(boattb.EFF_END_DATE) > trunc(to_date(:appDate, 'yyyyMMdd')) or boattb.eff_end_date is null ) " +
    	" and boattb.OFFER_ATTB_ID = ba.ATTB_ID " +
    	" and ba.attb_type_id = bat.attb_type_id " +
    	" UNION " +
    	" select ba.ATTB_ID, ba.ATTB_DESC, ba.ATTB_VALUE, 'P' AS ATTB_LEVEL, bat.ATTB_TYPE_DESC " +
    	" from  " +
    	" b_attb_a ba, " +
    	" b_attb_type_a bat, " +
    	" b_product_attb_a bpattb, " +
    	" b_offer_product_assgn_a bopa, " +
    	" b_offer_a bo, " +
    	" b_offer_assgn_a boa " +
    	" where (bo.offer_cd = :offerCd or bo.offer_id = :offerId)" +
    	" and trunc(bo.EFF_START_DATE) <= trunc(to_date(:appDate, 'yyyyMMdd')) " +
    	" and ( trunc(bo.EFF_END_DATE) > trunc(to_date(:appDate, 'yyyyMMdd')) or bo.eff_end_date is null ) " +
    	" and bo.offer_id = boa.offer_id " +
    	" and boa.OFFER_SUB_ID = bopa.OFFER_SUB_ID " +
    	" and trunc(bopa.EFF_START_DATE) <= trunc(to_date(:appDate, 'yyyyMMdd')) " +
    	" and ( trunc(bopa.EFF_END_DATE) > trunc(to_date(:appDate, 'yyyyMMdd')) or bopa.eff_end_date is null ) " +
    	" and bopa.prod_id = bpattb.prod_id " +
    	" and trunc(bpattb.EFF_START_DATE) <= trunc(to_date(:appDate, 'yyyyMMdd')) " +
    	" and ( trunc(bpattb.EFF_END_DATE) > trunc(to_date(:appDate, 'yyyyMMdd')) or bpattb.eff_end_date is null ) " +
    	" and bpattb.PROD_ATTB_ID = ba.ATTB_ID " +
    	" and ba.attb_type_id = bat.attb_type_id " +
    	" UNION " +
    	" select ba.ATTB_ID, ba.ATTB_DESC, ba.ATTB_VALUE, 'C' AS ATTB_LEVEL, bat.ATTB_TYPE_DESC " +
    	" from  " +
    	" b_attb_a ba, " +
    	" b_attb_type_a bat, " +
    	" b_component_attb_a bcattb, " +
    	" b_product_compt_assgn_a bpca, " +
    	" b_offer_product_assgn_a bopa, " +
    	" b_offer_a bo, " +
    	" b_offer_assgn_a boa " +
    	" where (bo.offer_cd = :offerCd or bo.offer_id = :offerId)" +
    	" and trunc(bo.EFF_START_DATE) <= trunc(to_date(:appDate, 'yyyyMMdd')) " +
    	" and ( trunc(bo.EFF_END_DATE) > trunc(to_date(:appDate, 'yyyyMMdd')) or bo.eff_end_date is null ) " +
    	" and bo.offer_id = boa.offer_id " +
    	" and boa.OFFER_SUB_ID = bopa.OFFER_SUB_ID " +
    	" and trunc(bopa.EFF_START_DATE) <= trunc(to_date(:appDate, 'yyyyMMdd')) " +
    	" and ( trunc(bopa.EFF_END_DATE) > trunc(to_date(:appDate, 'yyyyMMdd')) or bopa.eff_end_date is null ) " +
    	" and bopa.prod_id = bpca.prod_id " +
    	" and trunc(bpca.EFF_START_DATE) <= trunc(to_date(:appDate, 'yyyyMMdd')) " +
    	" and ( trunc(bpca.EFF_END_DATE) > trunc(to_date(:appDate, 'yyyyMMdd')) or bpca.eff_end_date is null ) " +
    	" and bpca.COMPT_ID = bcattb.compt_id " +
    	" and trunc(bcattb.EFF_START_DATE) <= trunc(to_date(:appDate, 'yyyyMMdd')) " +
    	" and ( trunc(bcattb.EFF_END_DATE) > trunc(to_date(:appDate, 'yyyyMMdd')) or bcattb.eff_end_date is null ) " +
    	" and bcattb.COMPT_ATTB_ID = ba.ATTB_ID " +
    	" and ba.attb_type_id = bat.attb_type_id ";
    
    
    public OfferDetailProfileLtsDTO[] getLtsEndedOfferProfile(String pCcSrvId, String pApplnDate) throws DAOException {
		
		final Map<String,OfferDetailProfileLtsDTO> offerMap = new HashMap<String,OfferDetailProfileLtsDTO>();
		
		ParameterizedRowMapper<OfferDetailProfileLtsDTO> mapper = new ParameterizedRowMapper<OfferDetailProfileLtsDTO>() {
			public OfferDetailProfileLtsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				String offerId = rs.getString("OFFER_ID");
				String packCd = rs.getString("PACK_CD");
				String psefCd = rs.getString("PSEF_CD");
				OfferDetailProfileLtsDTO offer = null;
				
				if (offerMap.containsKey(offerId)) {
					offer = offerMap.get(offerId);
				} else {
					offer = new OfferDetailProfileLtsDTO();
					offer.setOfferId(offerId);
					offer.setOfferSubKey(rs.getString("OFFER_SUB_KEY"));
					offer.setOfferSubCd(rs.getString("OFFER_SUB_CD"));
					offer.setOfferSubId(rs.getString("OFFER_SUB_ID"));
					offer.setEffStartDate(rs.getString("EFF_START_DATE"));
					offer.setEffEndDate(rs.getString("EFF_END_DATE"));
					offer.setConditionEffStartDate(rs.getString("COND_EFF_START_DATE"));
					offer.setConditionEffEndDate(rs.getString("COND_EFF_END_DATE"));
					offer.setExpiredMonths(rs.getDouble("EXPIRED_MONTHS"));
					offer.setPromotionStartDate(rs.getString("PROMOTION_START_DATE"));
					offer.setPromotionEndDate(rs.getString("PROMOTION_END_DATE"));
					offer.setPromotionExpiredMonths(rs.getString("PROMOTION_EXPIRED_MONTHS"));
					offerMap.put(offerId, offer);
					
//					if (StringUtils.isNotBlank(rs.getString("TC_GRP_ID")) && offer.getExpiredMonths() == 0) {
//						offer.setExpiredMonths(999);
//					}
					
					
				}
				if (!StringUtils.isBlank(packCd)) {
					offer.addPsef(packCd);
				}
				if (!StringUtils.isBlank(psefCd)) {
					offer.addPsef(psefCd);
				}
				return null;
			}
		};		
		try {
			Map<String,String> inputMap = new HashMap<String,String>();
			inputMap.put("serviceId", pCcSrvId);
			inputMap.put("applnDate", pApplnDate.split(" ")[0]);
			simpleJdbcTemplate.query(SQL_GET_LTS_ENDED_OFFER_PROFILE, mapper, inputMap);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getLtsOfferProfile().  CC ServiceId: " + pCcSrvId, e);
			throw new DAOException(e.getMessage(), e);
		}
		return offerMap.values().toArray(new OfferDetailProfileLtsDTO[offerMap.size()]);
	}
    
	public OfferDetailProfileLtsDTO[] getLtsOfferProfile(String pCcSrvId, String pApplnDate) throws DAOException {
		
		final Map<String,OfferDetailProfileLtsDTO> offerMap = new HashMap<String,OfferDetailProfileLtsDTO>();
		
		ParameterizedRowMapper<OfferDetailProfileLtsDTO> mapper = new ParameterizedRowMapper<OfferDetailProfileLtsDTO>() {
			public OfferDetailProfileLtsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				String offerId = rs.getString("OFFER_ID");
				String packCd = rs.getString("PACK_CD");
				String psefCd = rs.getString("PSEF_CD");
				OfferDetailProfileLtsDTO offer = null;
				
				if (offerMap.containsKey(offerId)) {
					offer = offerMap.get(offerId);
				} else {
					offer = new OfferDetailProfileLtsDTO();
					offer.setOfferId(offerId);
					offer.setOfferSubKey(rs.getString("OFFER_SUB_KEY"));
					offer.setOfferSubCd(rs.getString("OFFER_SUB_CD"));
					offer.setOfferSubId(rs.getString("OFFER_SUB_ID"));
					offer.setEffStartDate(rs.getString("EFF_START_DATE"));
					offer.setEffEndDate(rs.getString("EFF_END_DATE"));
					offer.setConditionEffStartDate(rs.getString("COND_EFF_START_DATE"));
					offer.setConditionEffEndDate(rs.getString("COND_EFF_END_DATE"));
					offer.setExpiredMonths(rs.getDouble("EXPIRED_MONTHS"));
					offer.setPromotionStartDate(rs.getString("PROMOTION_START_DATE"));
					offer.setPromotionEndDate(rs.getString("PROMOTION_END_DATE"));
					offer.setPromotionExpiredMonths(rs.getString("PROMOTION_EXPIRED_MONTHS"));
					offerMap.put(offerId, offer);
					
//					if (StringUtils.isNotBlank(rs.getString("TC_GRP_ID")) && offer.getExpiredMonths() == 0) {
//						offer.setExpiredMonths(999);
//					}
					
					
				}
				if (!StringUtils.isBlank(packCd)) {
					offer.addPsef(packCd);
				}
				if (!StringUtils.isBlank(psefCd)) {
					offer.addPsef(psefCd);
				}
				return null;
			}
		};		
		try {
			Map<String,String> inputMap = new HashMap<String,String>();
			inputMap.put("serviceId", pCcSrvId);
			inputMap.put("applnDate", pApplnDate);
			simpleJdbcTemplate.query(SQL_GET_LTS_OFFER_PROFILE, mapper, inputMap);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getLtsOfferProfile().  CC ServiceId: " + pCcSrvId, e);
			throw new DAOException(e.getMessage(), e);
		}
		return offerMap.values().toArray(new OfferDetailProfileLtsDTO[offerMap.size()]);
	}
	
	public String[] getComponentPsefProfile(String pCcSrvId) throws DAOException {
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("PSEF_CD");
			}
		};
		try {
			return simpleJdbcTemplate.query(SQL_GET_COMPONENT_PSEF_PROFILE, mapper, pCcSrvId).toArray(new String[0]);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getComponentPsefProfile()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public OfferDetailProfileLtsDTO[] getImsOfferProfile(String pFsa, String pApplnDate) throws DAOException {
		
		ParameterizedRowMapper<OfferDetailProfileLtsDTO> mapper = new ParameterizedRowMapper<OfferDetailProfileLtsDTO>() {
			public OfferDetailProfileLtsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				OfferDetailProfileLtsDTO offer = new OfferDetailProfileLtsDTO();
				offer.setOfferId(rs.getString("OFFER_ID"));
				offer.setOfferSubKey(rs.getString("OFFER_SUB_KEY"));
				offer.setOfferSubCd(rs.getString("OFFER_SUB_CD"));
				offer.setOfferSubId(rs.getString("OFFER_SUB_ID"));
				offer.setEffStartDate(rs.getString("EFF_START_DATE"));
				offer.setEffEndDate(rs.getString("EFF_END_DATE"));
				offer.setConditionEffStartDate(rs.getString("COND_EFF_START_DATE"));
				offer.setConditionEffEndDate(rs.getString("COND_EFF_END_DATE"));
				offer.setExpiredMonths(rs.getDouble("EXPIRED_MONTHS"));
				return offer;
			}
		};		
		try {
			Map<String,String> inputMap = new HashMap<String,String>();
			inputMap.put("fsa", pFsa);
			inputMap.put("applnDate", pApplnDate);
			return simpleJdbcTemplate.query(SQL_GET_IMS_OFFER_PROFILE, mapper, inputMap).toArray(new OfferDetailProfileLtsDTO[0]);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getImsOfferProfile().  CC ServiceId: " + pFsa, e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<String> getPsefCdByOfferId(List<String> pOfferList) throws DAOException {
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("PSEF_CD");
			}
		};	
		try {
			return simpleJdbcTemplate.query(SQL_GET_PSEF_BY_OFFER_ID, mapper, Collections.singletonMap("offerList", pOfferList));
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getPsefCdByOfferId()." , e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<OfferDetailCommitmentDTO> getCommitment(String pSystemId, String pServiceId, String effectiveInd) throws DAOException {		
	    try{
	    	List<OfferDetailCommitmentDTO> itemList = simpleJdbcTemplate.query(
	    			(StringUtils.isEmpty(effectiveInd) || "Y".equals(effectiveInd)) ? 
	    					SQL_GET_COMMITMENT_BY_SERVICE_ID + SQL_GET_EFFECTIVE_FOR_COMMITMENT 
	    					: ("N".equals(effectiveInd)) ? 
	    							SQL_GET_COMMITMENT_BY_SERVICE_ID + SQL_GET_HISTORY_FOR_COMMITMENT : ""
	    			, ltsCommitmentMapper, pServiceId, pSystemId);	    	    	
	    	return itemList.isEmpty() ? null : itemList;
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getCommitment - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private ParameterizedRowMapper<OfferDetailCommitmentDTO> ltsCommitmentMapper = new ParameterizedRowMapper<OfferDetailCommitmentDTO>() {
		public OfferDetailCommitmentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			OfferDetailCommitmentDTO offerDetailCommitment = new OfferDetailCommitmentDTO();			
			offerDetailCommitment.setOfferCode(rs.getString("OFFER_CD"));			
			offerDetailCommitment.setServiceNum(rs.getString("SERVICE_NUMBER"));
			offerDetailCommitment.setProductId(rs.getString("CREDIT_PROD_ID"));
			offerDetailCommitment.setProductDesc(rs.getString("PROD_DESC"));
			offerDetailCommitment.setCommitmentCode(rs.getString("TC_CD"));
			offerDetailCommitment.setCommitmentDesc(rs.getString("TC_DESC"));
			offerDetailCommitment.setEffectiveDate(rs.getString("EFF_START_DATE"));
			offerDetailCommitment.setEndDate(rs.getString("EFF_END_DATE"));
			offerDetailCommitment.setContractPeriod(rs.getString("DURATION"));			
			offerDetailCommitment.setEarlyTerminationDate(rs.getString("EARLY_END_DATE"));
			offerDetailCommitment.setTerminationId(rs.getString("TERM_ORDER_NUM"));
			offerDetailCommitment.setPenaltyAmt(rs.getString("PENALTY_AMT"));
			offerDetailCommitment.setWaiveReason(rs.getString("WAIVE_REASON"));
			offerDetailCommitment.setWaiveInd(rs.getString("WAIVE_IND"));
			//offerDetailCommitment.setOfferType("");
			//offerDetailCommitment.setWaiveType("");
			return offerDetailCommitment;
		}
	};
	
	public List<OfferDetailRecurringChargesDTO> getRecurringCharges(String pSystemId, String pServiceId, String effectiveInd) throws DAOException {		
	    try{
	    	List<OfferDetailRecurringChargesDTO> itemList = simpleJdbcTemplate.query(
	    			(StringUtils.isEmpty(effectiveInd) || "Y".equals(effectiveInd)) ? 
	    					SQL_GET_RECURRING_CHARGES_BY_SERVICE_ID + SQL_GET_EFFECTIVE_FOR_RECURRING_CHARGES 
	    					: ("N".equals(effectiveInd)) ? 
	    							SQL_GET_RECURRING_CHARGES_BY_SERVICE_ID + SQL_GET_HISTORY_FOR_RECURRING_CHARGES : ""
	    			, ltsRecurringChargesMapper, pServiceId, pSystemId);	    	    	
	    	return itemList.isEmpty() ? null : itemList;
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getRecurringCharges - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private ParameterizedRowMapper<OfferDetailRecurringChargesDTO> ltsRecurringChargesMapper = new ParameterizedRowMapper<OfferDetailRecurringChargesDTO>() {		
		public OfferDetailRecurringChargesDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			OfferDetailRecurringChargesDTO recurringCharges = new OfferDetailRecurringChargesDTO();			
			recurringCharges.setOfferCode(rs.getString("OFFER_CD"));
			recurringCharges.setServiceNum(rs.getString("SERVICE_NUMBER"));
			recurringCharges.setProductId(rs.getString("PROD_ID"));
			recurringCharges.setProductDesc(rs.getString("PROD_DESC"));
			recurringCharges.setChargeCode(rs.getString("PRC_SCH_CD"));
			recurringCharges.setChargeDesc(rs.getString("PRC_SCH_DESC"));
			recurringCharges.setDefaultAmount(rs.getString("DEFAULT_AMT"));
			recurringCharges.setChargeAmount(rs.getString("CHRG_AMT"));
			recurringCharges.setEffectiveDate(rs.getString("EFF_START_DATE"));
			recurringCharges.setTerminateDate(rs.getString("EFF_END_DATE"));
			recurringCharges.setContractOrPromotionStartDate(rs.getString("PROMOTION_START_DATE"));
			recurringCharges.setContractOrPromotionEndDate(rs.getString("PROMOTION_END_DATE"));								
			return recurringCharges;
		}
	};
	
	public List<OfferDetailDiscountDTO> getDiscount(String pSystemId, String pServiceId, String effectiveInd) throws DAOException {		
	    try{
	    	List<OfferDetailDiscountDTO> itemList = simpleJdbcTemplate.query(
	    			(StringUtils.isEmpty(effectiveInd) || "Y".equals(effectiveInd)) ? 
	    					SQL_GET_DISCOUNT_BY_SERVICE_ID + SQL_GET_EFFECTIVE_FOR_DISCOUNT 
	    					: ("N".equals(effectiveInd)) ? 
	    							SQL_GET_DISCOUNT_BY_SERVICE_ID + SQL_GET_HISTORY_FOR_DISCOUNT : ""
	    			, ltsDiscountMapper, pServiceId, pSystemId);	    	    	
	    	return itemList.isEmpty() ? null : itemList;
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getDiscount - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private ParameterizedRowMapper<OfferDetailDiscountDTO> ltsDiscountMapper = new ParameterizedRowMapper<OfferDetailDiscountDTO>() {		
		public OfferDetailDiscountDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			OfferDetailDiscountDTO discount = new OfferDetailDiscountDTO();			
			discount.setOfferCode(rs.getString("OFFER_CD"));
			discount.setProductId(rs.getString("CREDIT_PROD_ID"));
			discount.setProductDesc(rs.getString("PROD_DESC"));
			discount.setDiscountCode(rs.getString("DIS_CD"));
			discount.setDiscountDesc(rs.getString("DIS_DESC"));
			discount.setEffectiveDate(rs.getString("EFF_START_DATE"));
			discount.setTerminationDate(rs.getString("EFF_END_DATE"));			
			discount.setCreatedDate(rs.getString("CR_DATE"));
			discount.setSentDate(rs.getString("SENT_DATE"));
			discount.setRemark(rs.getString("REMARKS"));
			discount.setUpdatedBy(rs.getString("LAST_UPD_BY"));
			discount.setUpdatedDate(rs.getString("LAST_UPD_DATE"));
			//discount.setStartMonth("");
			//discount.setDuration("");
			//discount.setEarlyTerminationDate("");
			//discount.setForfeitCondition("");
			//discount.setExecMode("");
			return discount;
		}
	};
	
	public List<OfferDetailIncentiveDTO> getIncentive(String pSystemId, String pServiceId, String effectiveInd) throws DAOException {		
	    try{
	    	List<OfferDetailIncentiveDTO> itemList = simpleJdbcTemplate.query(
	    			(StringUtils.isEmpty(effectiveInd) || "Y".equals(effectiveInd)) ? 
	    					SQL_GET_INCENTIVE_BY_SERVICE_ID + SQL_GET_EFFECTIVE_FOR_INCENTIVE 
	    					: ("N".equals(effectiveInd)) ? 
	    							SQL_GET_INCENTIVE_BY_SERVICE_ID + SQL_GET_HISTORY_FOR_INCENTIVE : ""
	    			, ltsIncentiveMapper, pServiceId, pSystemId);	    	    	
	    	return itemList.isEmpty() ? null : itemList;
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getIncentive - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private ParameterizedRowMapper<OfferDetailIncentiveDTO> ltsIncentiveMapper = new ParameterizedRowMapper<OfferDetailIncentiveDTO>() {		
		public OfferDetailIncentiveDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			OfferDetailIncentiveDTO incentive = new OfferDetailIncentiveDTO();			
			incentive.setOfferCode(rs.getString("OFFER_CD"));
			incentive.setProductId(rs.getString("CREDIT_PROD_ID"));
			incentive.setProductDesc(rs.getString("PROD_DESC"));
			incentive.setIncentiveCode(rs.getString("INC_CD"));
			incentive.setIncentiveDesc(rs.getString("INC_DESC"));
			incentive.setEffectiveDate(rs.getString("EFF_START_DATE"));
			incentive.setTerminationDate(rs.getString("EFF_END_DATE"));
			incentive.setRemark(rs.getString("REMARKS"));
			incentive.setCreateDate(rs.getString("CR_DATE"));
			incentive.setUpdatedBy(rs.getString("LAST_UPD_BY"));
			incentive.setUpdatedDate(rs.getString("LAST_UPD_DATE"));
			//incentive.setEarlyTerminationDate("");
			//incentive.setDuration("");
			return incentive;
		}
	};
	
	public List<BomAttbDTO> getBomAttb(String offerId, String offerCd, String appDate) throws DAOException {		
	    
		ParameterizedRowMapper<BomAttbDTO> bomAttbMapper = new ParameterizedRowMapper<BomAttbDTO>() {		
			public BomAttbDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BomAttbDTO bomAttb = new BomAttbDTO();			
				bomAttb.setAttbId(rs.getString("ATTB_ID"));
				bomAttb.setAttbDesc(rs.getString("ATTB_DESC"));
				bomAttb.setAttbValue(rs.getString("ATTB_VALUE"));
				bomAttb.setAttbLevel(rs.getString("ATTB_LEVEL"));
				bomAttb.setAttbTypeDesc(rs.getString("ATTB_TYPE_DESC"));
				return bomAttb;
			}
		};
		
		try{
			Map<String,String> inputMap = new HashMap<String,String>();
			inputMap.put("offerId", StringUtils.defaultIfEmpty(offerId, "-999"));
			inputMap.put("offerCd", StringUtils.defaultIfEmpty(offerCd, "XXX"));
			inputMap.put("appDate", appDate.split(" ")[0]);
	    	
			return this.simpleJdbcTemplate.query(SQL_GET_ALL_BOM_ATTB_BY_OFFER, bomAttbMapper, inputMap);
	    	
	    } catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new DAOException(ExceptionUtils.getFullStackTrace(e), e);
		}
	}
	
}
