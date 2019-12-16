package com.bomwebportal.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
/**
 * Code Description Tag Handler. 
 * <P>This tag accepts String parameters and return Code Description back to JSP.
 * It extends TagSupport and overrides a few methods.
 * @author James Wong
 *
 */
public class CodeDescTag extends TagSupport{
		
	private static final long serialVersionUID = -1629840058490134742L;
	/**
	 * Order Status
	 */
	private static final String ORDER_STATUS = "ORDER_STATUS";
	/**
	 * Map of List of String that defined by source parameters
	 */
	private Map<String, List<String>> codeMap;
	/**
	 * Code Type
	 */
	private String codeType;
	/**
	 * Name of database table that its values are retrieving from	
	 */
	private String source;
	
	/**
	 * @return the codeMap
	 */
	public Map<String, List<String>> getCodeMap() {
		return codeMap;
	}

	/**
	 * @param codeMap the codeMap to set
	 */
	public void setCodeMap(Map<String, List<String>> codeMap) {
		this.codeMap = codeMap;
	}
	/**
	 * Set list of String into a map
	 * @param key
	 * @param value List of String
	 */
	private void setCodeMapValue(String key, List<String> value) {
		if (this.codeMap == null) {
			this.codeMap = new HashMap<String, List<String>>();
		}
		
		codeMap.put(key, value);
	}
	/**
	 * retrieve value from map
	 * @param key
	 * @return String 
	 */
	private List<String> getCodeMapValue(String key) {
		return codeMap.get(key);
	}
	
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the codeType
	 */
	public String getCodeType() {
		return codeType;
	}

	/**
	 * @param codeType the codeType to set
	 */
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	@Override
	public int doStartTag() throws JspException {
		
		//define source (parameter)
		String[] orderStatus = {"ORD_CHECK_POINT", "ORD_FALLOUT_CODE"};
		List<String> orderStatusList = Arrays.asList(orderStatus);
		setCodeMapValue(ORDER_STATUS, orderStatusList);
		
		return SKIP_BODY;
		
	}
	
	private void doDecode(List<CodeLkupDTO> codeList) throws JspException, IOException {
		JspWriter out = pageContext.getOut();
		for (int i=0; i < codeList.size(); i++) {
			if (codeList.get(i).getCodeId().equalsIgnoreCase(id)) {
				out.println(codeList.get(i).getCodeDesc());
				break;
			} else if ((i == codeList.size() - 1) //print id if found no match 
					&& !codeList.get(i).getCodeId().equalsIgnoreCase(id)) {
				out.println(id);
			}
		}
	}
	
	 @Override
    public int doEndTag() throws JspException {

		//retrieve values from database
		Map<String, List<CodeLkupDTO>> entityCodeMap = LookupTableBean.getInstance().getCodeLkupList();
		//if source parameter is defined on jsp tag
		if (source != null && !source.isEmpty()) {
			List<String> keyList = getCodeMapValue(source); 
			//if source is ORDER_STATUS we will handle differently 
			if (source.equalsIgnoreCase(ORDER_STATUS)) {
				if (Utility.isDigit(id)) {
					List<CodeLkupDTO> valueList = entityCodeMap.get(keyList.get(0));
					try {
						doDecode(valueList);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					List<CodeLkupDTO> valueList = entityCodeMap.get(keyList.get(1));
					try {
						doDecode(valueList);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				//we take value of source parameter to shorten our code description searching process
				for (String keys : keyList) {
					if (entityCodeMap.containsKey(keys)) {
						List <CodeLkupDTO> valueList = entityCodeMap.get(keys);
						try {
							doDecode(valueList);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} else { //if souce parameter is not defined, retrieve code description by using codeType and code id
			if (!this.codeType.isEmpty()) {
				List<CodeLkupDTO> valueList =  entityCodeMap.get(codeType);
				try {
					doDecode(valueList);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	 
	 return EVAL_PAGE;
    }
	
}	
