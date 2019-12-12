package com.pccw.wq.dao;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Joiner;
import com.pccw.util.dao.DaoBaseCustomQuery;
import com.pccw.util.search.Criteria;
import com.pccw.util.search.SearchResult;
import com.pccw.wq.schema.form.WqInquiryResultFormDTO;

public class WorkQueueInquiryDAOImpl extends DaoBaseCustomQuery implements WorkQueueInquiryDAO {

	@Override
	public SearchResult<WqInquiryResultFormDTO> searchWorkQueue(Criteria pCriteria) throws Exception {
		
		// Pass through MessageFormat.format, use '' instead of '
		
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT")
           .append("  WQ_ID \"wqId\", ")
           .append("  SB_ID \"sbId\", ")
           .append("  SB_DTL_ID \"sbDtlId\", ")
           .append("  SB_ACTV_ID \"sbActvId\", ")
           .append("  SB_SHOP_CD \"sbShopCode\", ")
           .append("  TYPE_OF_SRV \"typeOfService\", ")
           .append("  SRV_ID \"serviceId\", ")
           .append("  RELATED_SRV_TYPE \"relatedSrvType\", ")
           .append("  RELATED_SRV_NUM \"relatedSrvNum\", ")
           .append("  TO_CHAR(SRD, ''YYYYMMDDHH24MISS'') \"srd\", ")
           .append("  BOM_OC_ID \"bomOcId\", ")
           .append("  BOM_DTL_SEQ \"bomDtlId\", ")
           .append("  BOM_STATUS \"BomStatus\", ")
           .append("  BOM_LEGACY_ORD_STATUS \"bomLegacyOrderStatus\", ")
           .append("  wwsv.WQ_WP_ASSGN_ID \"wqWpAssgnId\", ")
           .append("  WQ_SERIAL \"wqSerial\", ")
           .append("  WQ_BATCH_ID \"wqBatchId\", ")
           .append("  TO_CHAR(RECEIVE_DATE, ''YYYYMMDDHH24MISS'') \"wqReceiveDate\", ")
           .append("  WQ_TYPE \"wqType\", ")
           .append("  WQ_TYPE_DESC \"wqTypeDesc\", ")
           .append("  WQ_SUBTYPE \"wqSubType\", ")
           .append("  WQ_SUB_TYPE_DESC \"wqSubTypeDesc\", ")
           .append("  WP_ID \"wpId\", ")
           .append("  WP_DESC \"wpDesc\", ")
           .append("  (select RTRIM(XMLAGG(XMLELEMENT(e, wn.wq_nature_desc || chr(13)) ORDER BY wn.wq_nature_desc).EXTRACT(''//text()''),'','') AS wq_nature_desc")
           .append("     from q_wq_wp_assgn_nature wwan, q_wq_nature wn ")
           .append("    where wwan.wq_nature_id = wn.wq_nature_id ")
           .append("      and nvl(wn.hidden_ind, ''N'') = ''N'' ")
           .append("      and wwan.wq_wp_assgn_id = wwsv.WQ_WP_ASSGN_ID) \"wqNatureDesc\", ")
           .append("  (select RTRIM(XMLAGG(XMLELEMENT(e, ''wqWpAssgnId='' || wwd.wq_wp_assgn_id || chr(38) || ''wqWpAssgnDocId='' || wwd.wq_wp_assgn_doc_id || chr(38) || ''wqDocumentId='' || wwd.wq_document_id || chr(38) || ''attachmentType='' || SUBSTR(wd.attachment_path, INSTR(wd.attachment_path, ''.'', -1) + 1) || chr(13))).EXTRACT(''//text()''),'','') AS wq_document_id_str")
           .append("     from q_wq_wp_document wwd, q_wq_document wd ")
           .append("    where wwd.wq_wp_assgn_id = wwsv.WQ_WP_ASSGN_ID ")
           .append("      and wwd.wq_document_id = wd.wq_document_id) \"wqDocumentIdString\", ")
           .append("  ASSIGNEE \"assignee\", ")
           .append("  URL \"url\", ")
           .append("  REASON_CD \"reasonCode\", ")
           .append("  REASON_CD \"reasonDesc\", ")
           .append("  STATUS_CD \"wqStatus\", ")
           .append("  STATUS_DESC \"wqStatusDesc\", ")
           .append("  TO_CHAR(STATUS_DATE, ''YYYYMMDDHH24MISS'') \"wqStatusDate\", ")
           .append("  NEXT_STATUS \"nextStatus\" ")
           .append("  from q_wq_wp_search_v wwsv ")
           .append("where wwsv.wq_wp_assgn_id IN (SELECT wq_wp_assgn_id FROM q_wq_wp_search_id_v {0}) ");

        //logger.debug(sql.toString());
		return search("searchWQ", sql, pCriteria, WqInquiryResultFormDTO.class);
    }
	
	private void unionWqAssgnIdSql(Criteria[] pCriterias, Map<String, Object> pBindingValueMap, StringBuilder pSql) {
		pBindingValueMap.putAll(pCriterias[0].getValueMap());
		for (int i = 1; i < pCriterias.length; i++) {
			addUnionWqAssgnIdSql(pCriterias[i], pBindingValueMap, pSql, i);
		}
	}
	
	private void addUnionWqAssgnIdSql(Criteria pCriteria, Map<String, Object> pBindingValueMap, StringBuilder pSql, int pIndex) {
        String key = null;
         String tmpSql = (MessageFormat.format(
				"      UNION SELECT wq_wp_assgn_id FROM q_wq_wp_search_id_v {0} ", 
				new Object[] {pCriteria.getCriteriaList().isEmpty() ? "" : " WHERE " + Joiner.on(" AND ").useForNull("null").join(pCriteria.getCriteriaList())}));
    	for (Entry<String, Object> entry : pCriteria.getValueMap().entrySet()) {
			if (pBindingValueMap.containsKey(entry.getKey())) {
				if (!((entry.getValue() instanceof String) 
						&& (pBindingValueMap.get(entry.getKey()) instanceof String)
						&& (StringUtils.equals((String)entry.getValue(), (String)pBindingValueMap.get(entry.getKey()))))) {
					key = entry.getKey() + "_" + pIndex;
					tmpSql = StringUtils.replace(tmpSql, ":" + entry.getKey(), ":" + key);
					pBindingValueMap.put(key, entry.getValue());
				}
			} else {
				pBindingValueMap.put(entry.getKey(), entry.getValue());
			}
		} 
    	pSql.append(tmpSql);
		
	}

	private SearchResult<WqInquiryResultFormDTO> searchWithMultiCriteria(Criteria pCriteria, Map<String, Object> pBindingValueMap, StringBuilder pSql) throws Exception {
        Map<String, Object> originalValueMap = new TreeMap<String, Object>();
        originalValueMap.putAll(pCriteria.getValueMap());
        pCriteria.getValueMap().clear();
        pCriteria.getValueMap().putAll(pBindingValueMap);
        
        SearchResult<WqInquiryResultFormDTO> result = search("searchWqSbId", pSql, pCriteria, WqInquiryResultFormDTO.class);
        pCriteria.getValueMap().clear();
        pCriteria.getValueMap().putAll(originalValueMap);
		return result;
	}
	
	@Override
	public SearchResult<WqInquiryResultFormDTO> searchSbIdsWithWq(Criteria[] pCriterias) throws Exception {
		
		// Pass through MessageFormat.format, use '' instead of '
        StringBuilder sql = new StringBuilder();
        sql.append("select wwsiv.sb_id \"sbId\", wwsiv.type_of_srv \"typeOfService\", ")
           .append("       RTRIM(XMLAGG(XMLELEMENT(e, wn.wq_nature_desc || chr(13)) ORDER BY wn.wq_nature_desc).EXTRACT(''//text()''),'','') \"wqNatureDesc\", ")
           .append("       (SELECT REPLACE(RTRIM (XMLAGG (XMLELEMENT (E, XMLATTRIBUTES (wp_desc || decode(ASSIGNEE, null, '''', '' - '' || ASSIGNEE) || ''^n^'' AS \"wpAssignee\")) ORDER BY wp_desc, assignee).EXTRACT (''./E[not(@wpAssignee = preceding-sibling'' || chr(58) || chr(58) ||''E/@wpAssignee)]/@wpAssignee''),'',''), ''^n^'', chr(13))  ")
           .append("          from Q_WQ_WP_SEARCH_V qwwsv where qwwsv.SB_ID = wwsiv.sb_id AND STATUS_CD NOT IN (SELECT CODE FROM q_dic_code_lkup WHERE GRP_ID = ''WQ_ENDING_STATUS'')) \"wpDesc\" ")
           .append("  from q_wq_nature wn, ")
           .append("       (SELECT DISTINCT sb_id, type_of_srv, wq_nature_id FROM q_wq_wp_search_id_v ")
           .append("		 WHERE sb_id IS NOT NULL AND ((TYPE_OF_SRV != ''SLV'') OR (TYPE_OF_SRV = ''SLV'' AND SB_ACTV_ID IS NULL)) and wq_wp_assgn_id in (SELECT wq_wp_assgn_id FROM q_wq_wp_search_id_v {0} ");
        
        Map<String, Object> bindingValueMap = new TreeMap<String, Object>();
        unionWqAssgnIdSql(pCriterias, bindingValueMap, sql);
        
        sql.append(")) wwsiv ")
           .append(" where wwsiv.wq_nature_id = wn.wq_nature_id ")
           .append("   and nvl(wn.hidden_ind, ''N'') = ''N'' ")
           .append("group by wwsiv.sb_id, wwsiv.type_of_srv ");
        
        return searchWithMultiCriteria(pCriterias[0], bindingValueMap, sql);
    }
	
	@Override
	public SearchResult<WqInquiryResultFormDTO> searchSbActvIdsWithWq(Criteria[] pCriterias) throws Exception {
		
		// Pass through MessageFormat.format, use '' instead of '
        StringBuilder sql = new StringBuilder();
        sql.append("select wwsiv.sb_actv_id \"sbActvId\", wwsiv.type_of_srv \"typeOfService\", ")
           .append("       RTRIM(XMLAGG(XMLELEMENT(e, wn.wq_nature_desc || chr(13)) ORDER BY wn.wq_nature_desc).EXTRACT(''//text()''),'','') \"wqNatureDesc\", ")
           .append("       (SELECT REPLACE(RTRIM (XMLAGG (XMLELEMENT (E, XMLATTRIBUTES (wp_desc || decode(ASSIGNEE, null, '''', '' - '' || ASSIGNEE) || ''^n^'' AS \"wpAssignee\")) ORDER BY wp_desc, assignee).EXTRACT (''./E[not(@wpAssignee = preceding-sibling'' || chr(58) || chr(58) ||''E/@wpAssignee)]/@wpAssignee''),'',''), ''^n^'', chr(13))  ")
           .append("          from Q_WQ_WP_SEARCH_V qwwsv where qwwsv.sb_actv_id = wwsiv.sb_actv_id AND STATUS_CD NOT IN (SELECT CODE FROM q_dic_code_lkup WHERE GRP_ID = ''WQ_ENDING_STATUS'')) \"wpDesc\" ")
           .append("  from q_wq_nature wn, ")
           .append("       (SELECT DISTINCT sb_actv_id, type_of_srv, wq_nature_id FROM q_wq_wp_search_id_v ")
           .append("         WHERE sb_actv_id IS NOT NULL and wq_wp_assgn_id in (SELECT wq_wp_assgn_id FROM q_wq_wp_search_id_v {0} ");
           
        Map<String, Object> bindingValueMap = new TreeMap<String, Object>();
        unionWqAssgnIdSql(pCriterias, bindingValueMap, sql);

        sql.append(")) wwsiv ")
           .append(" where wwsiv.wq_nature_id = wn.wq_nature_id ")
           .append("   and nvl(wn.hidden_ind, ''N'') = ''N'' ")
           .append("group by wwsiv.sb_actv_id, wwsiv.type_of_srv");

        return searchWithMultiCriteria(pCriterias[0], bindingValueMap, sql);
    }
	
	
	public SearchResult<WqInquiryResultFormDTO> serachWqNatureStatusWithSbId(Criteria pCriteria) throws Exception {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT qwwsv.SB_ID \"sbId\", wn.wq_nature_desc \"wqNatureDesc\", qwwsv.APPROVAL_STATUS_CD \"wqStatus\", ");
		sql.append(" dcls.DESCRIPTION \"wqStatusDesc\", qwwsv.STATUS_DATE \"wqStatusDate\", qwwsv.ACTION_BY \"assignee\" ");
		sql.append(" FROM  ( select DISTINCT WQ.SB_ID, wwan.wq_nature_id, wwa.wq_wp_assgn_id, ");
		sql.append(" FIRST_VALUE(qwwasl.STATUS_CD) OVER (PARTITION BY qwwasl.WQ_WP_ASSGN_ID ORDER BY qwwasl.CREATE_DATE DESC) APPROVAL_STATUS_CD, ");
		sql.append(" FIRST_VALUE(qwwasl.CREATE_BY) OVER (PARTITION BY qwwasl.WQ_WP_ASSGN_ID ORDER BY qwwasl.CREATE_DATE DESC) ACTION_BY, ");
		sql.append(" FIRST_VALUE(qwwasl.CREATE_DATE) OVER (PARTITION BY qwwasl.WQ_WP_ASSGN_ID ORDER BY qwwasl.CREATE_DATE DESC) STATUS_DATE ");
		sql.append(" from q_work_queue wq, ");
		sql.append(" q_wq_wp_assgn wwa, ");
		sql.append(" Q_WQ_WP_ASSGN_NATURE wwan, ");
		sql.append(" q_dic_code_lkup dcls, ");
		sql.append(" Q_WQ_WP_ASSGN_STATUS_LOG qwwasl ");
		sql.append("  {0} ");
		sql.append(" AND wq.wq_id = wwa.wq_id ");
		sql.append(" AND wwan.WQ_WP_ASSGN_ID = wwa.wq_wp_assgn_id ");
		sql.append(" AND wwa.WQ_SUBTYPE like ''APPR%'' ");
		sql.append(" and wwa.wq_wp_assgn_id = qwwasl.wq_wp_assgn_id ");
		sql.append(" and qwwasl.status_cd in (''040'', ''050'', ''060'') ");
		sql.append("   ) qwwsv, ");
		sql.append(" Q_WQ_NATURE wn, ");
		sql.append(" q_dic_code_lkup dcls ");
		sql.append(" WHERE  dcls.grp_id = ''WQ_STATUS'' ");
		sql.append(" AND dcls.code = qwwsv.APPROVAL_STATUS_CD ");
		sql.append(" and wn.wq_nature_id = qwwsv.wq_nature_id ");
		
		return search("serachWqNatureStatusWithSbId", sql, pCriteria, WqInquiryResultFormDTO.class);
		
	}
	
}