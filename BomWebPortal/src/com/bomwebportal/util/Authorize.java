package com.bomwebportal.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.dto.AuthorizeDTO;

public class Authorize extends SimpleTagSupport{
	
	private static final long serialVersionUID = 7138151769983091286L;
	
	private static Pattern tagPattern = Pattern.compile("<([^>]+)>");
	
	private String id;
	private PageContext pageContext;
	private String toDisable;
	private String startTag;
	
	public String getStartTag() {
		return startTag;
	}

	public void setStartTag(String startTag) {
		this.startTag = startTag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
		
	public void doTag() throws JspException, IOException{

		pageContext = (PageContext) getJspContext();

		Map<String, List<AuthorizeDTO>> authorize = (Map<String, List<AuthorizeDTO>>) pageContext.getAttribute("authorizationInfo", PageContext.SESSION_SCOPE);

		List<AuthorizeDTO> usersList = null;
		
		String path = getJsp();
		
		String actionType = ((String) pageContext.getRequest().getParameter("actionType") != null) ? 
				(String) pageContext.getRequest().getParameter("actionType"): "NONE";
		
		if (authorize != null && !authorize.isEmpty()) {
			usersList = authorize.get(path);
		}
				
		if (usersList != null && !usersList.isEmpty()) {
			
			for (int i=0; i < usersList.size(); i++) {
				if (getId().equalsIgnoreCase(usersList.get(i).getAttribute())) {
					toDisable = "false";
					break;
				} else if (i == usersList.size() - 1) {
					if (getId().equalsIgnoreCase(usersList.get(i).getAttribute())) {
						toDisable = "false";
					} else {
						toDisable = "true";
					}
				}
			}
			retrieveTag();
		} else {
			if (!"NONE".equalsIgnoreCase(actionType)) {
				toDisable = "false";
			} else {
				toDisable = "true";
			}
			retrieveTag();
		}
	}
	
	private String getJsp(){
		
		HttpServletRequest hsrq = (HttpServletRequest) pageContext.getRequest();
		String url = hsrq.getRequestURI();
		
		int startIndex = url.lastIndexOf("/");
		int endIndex = url.lastIndexOf(".");
		
		return url.substring(startIndex + 1, endIndex);
		
	}
	
	private List<String> breakTags(String tag) {
		
		List<String> result = new ArrayList<String>();
		Matcher m = tagPattern.matcher(tag);
		
		while (m.find()) {
			result.add(m.group());
		}
		
		return result;
	}
	
	private void getStartTag(String tag) {
		
		int i = 0;		
		List<String> tags = breakTags(tag);
		
		String firstLine = tags.get(i);
		
		while(firstLine.contains("<!--") || firstLine.contains("</") || firstLine.contains("-->")) {
			i = i + 1;
			firstLine = tags.get(i);
		}
				
		int start = firstLine.indexOf("<");
		int end = firstLine.indexOf(" ", start);
		
		if (end <= 0) {
			end = firstLine.indexOf("/", start);
			if (end <= 0) {
				end = firstLine.indexOf(">", start);
			}
		}
		
		startTag = firstLine.substring(start + 1, end);
	}
	
	private void retrieveTag() throws JspException, IOException {
		
		StringWriter sw = new StringWriter();
		JspWriter out = pageContext.getOut();
		
		JspFragment body = getJspBody();
		try {
			body.invoke(sw);
			if (StringUtils.isNotBlank(sw.toString())) {
				
				getStartTag(sw.toString());
				List<String> tags = breakTags(sw.toString());
				String disabledPattern = null;
				String result = null;
				
				for (String s : tags) {
					if (s.contains(startTag)) {
						if (toDisable.equalsIgnoreCase("true")) {
							result = sw.toString().replaceAll("<" + startTag, "<" + startTag + " disabled=\"true\" ");
							result = result.replaceFirst("</" + startTag + " disabled=\"true\">", "</" + startTag + ">");
						} else {
							if (s.contains("disabled")) {
								String[] attr = s.split(" ");
								if (attr != null) {
									for (String e : attr) {
										if (e.contains("disabled")) {
											disabledPattern = e;
										}
									}
								}
								result = sw.toString().replaceAll(disabledPattern, "");
							} 
						}
					}
				}
				
//				if (repeatedTag) {
//					if (toDisable.equalsIgnoreCase("true")) {
//						int startIndex = 0;
//						sb = new StringBuffer(sw.toString());
//						do {
//							
//							startIndex = sb.indexOf(startTag, startIndex);
//									
//							if (startIndex > 0) {
//								sb = insertDisabledTag(sb.toString(), startIndex);
//								startIndex = startIndex + 1;
//							}
//							
//						} while (startIndex > 0);
//					} else {
//						sb = new StringBuffer(sw.toString());
//						if (sw.toString().contains("disabled")) {
//							int startIndex = 0;
//							do {
//								
//								startIndex = sb.indexOf(startTag, startIndex);
//								
//								if (startIndex > 0) {
//									startIndex = startIndex + 1;
//									sb = removeDisabledAttr(sb.toString(), startIndex);
//								}
//								
//							} while (startIndex > 0);
//						}
//						
//					}
//					
//				}
				
				out.println(result == null ? result = sw.toString() : result);
				
			}
						
		} catch (JspException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	private StringBuffer replaceTag(String tag, String regex) {
		
		StringBuffer sb = new StringBuffer();
		
			if (regex.toString().contains("<" + tag + ">")) {
				
				StringBuffer selectString = new StringBuffer(regex.toString());
				
				int start = selectString.indexOf("<");
				int end = selectString.indexOf(">");
				
				selectString.replace(start, end + 1, "<" + tag + " disabled=\"" + toDisable + "\"" + ">");
				sb.append(selectString.toString());
			}
		
		return sb;	
	}
		
	private StringBuffer removeDisabledAttr(String regex, int start) {
		
		StringBuffer sb = new StringBuffer(regex);

			int end = sb.indexOf("\" ", start);
			
			if (end <= 0) {
				end = sb.indexOf(">", start);
			} else {
				end = end + 1;
			}
			
			sb.replace(start, end, "");
		
		return sb;
	}
	
	private StringBuffer removeDisabledAttr(String regex) {
		
		StringBuffer sb = new StringBuffer(regex);
		
		if (regex.contains("disabled")) {
			int start = regex.indexOf("disabled");
			int end = regex.lastIndexOf("\"");
			
			sb.replace(start, end, "");
		}
		
		return sb;
	}
	
	private StringBuffer insertDisabledTag(String tag) {
		StringBuffer sb = new StringBuffer(tag);
				
		int i = sb.indexOf(" ");
		sb.insert(i, " disabled=\"" + toDisable + "\"");
				
		return sb;
	}
	
	private StringBuffer insertDisabledTag(String tag, int index) {
		StringBuffer sb = new StringBuffer(tag);
		
		int i = sb.indexOf(" ", index);
		sb.insert(i, " disabled=\"" + toDisable + "\"");
				
		return sb;
	}
	
	private boolean isRepeatedTag(String tag, int fromIndex) {
		
		int next = tag.indexOf(tag.toString(), fromIndex);
		
		return next == fromIndex ? false : true;
	}
	
	/*private Map<String, List<AuthorizeDTO>> groupCategory(String channel) {
		Map<String, List<AuthorizeDTO>> categoryMap = new HashMap<String, List<AuthorizeDTO>>();
		Map<String, List<AuthorizeDTO>> channelMap = new HashMap<String, List<AuthorizeDTO>>();
		
		channelMap = LookupTableBean.getInstance().getRoleLkupList();
		List<AuthorizeDTO> channelGroup = channelMap.get(channel);
		
		if (channelGroup != null && !channelGroup.isEmpty()) {
			for (AuthorizeDTO dto : channelGroup) {
				List<AuthorizeDTO> grouped = null;
				
				if (categoryMap.containsKey(dto.getCategory())) {
					grouped = categoryMap.get(dto.getCategory());
					grouped.add(dto);
				} else {
					grouped = new ArrayList<AuthorizeDTO>();
					grouped.add(dto);
					categoryMap.put(dto.getCategory(), grouped);
				}
			}
		}
		
		return categoryMap;
	}*/
	
	
}
