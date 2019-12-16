package com.bomwebportal.web.ext;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.configuration.BomPropertyPlaceholderConfigurer;

public class BomWebPortalApplicationFlow implements Serializable {

	private static final long serialVersionUID = -3892819251201956230L;

	protected final Log logger = LogFactory.getLog(getClass());
	
	private List<String> appModeList;
	
	public List<String> getAppModeList() {
		return appModeList;
	}

	public void setAppModeList(List<String> appModeList) {
		this.appModeList = appModeList;
	}

	private BomPropertyPlaceholderConfigurer propertyConfigurer;
	
	public BomPropertyPlaceholderConfigurer getPropertyConfigurer() {
		return propertyConfigurer;
	}

	public void setPropertyConfigurer(
			BomPropertyPlaceholderConfigurer propertyConfigurer) {
		this.propertyConfigurer = propertyConfigurer;
		loadingApplicationFlowProperties();
	}

	private HashMap<String, List<String>> appModeMap = new HashMap<String, List<String>>();
	
	public HashMap<String, List<String>> getAppModeMap() {
		return appModeMap;
	}

	public void setAppModeMap(HashMap<String, List<String>> appModeMap) {
		this.appModeMap = appModeMap;
	}
	
	private String defaultAppMode;
	
	public String getDefaultAppMode() {
		return defaultAppMode;
	}

	public void setDefaultAppMode(String defaultAppMode) {
		this.defaultAppMode = defaultAppMode;
	}

	public void loadingApplicationFlowProperties(){
		try{
			appModeList = Arrays.asList(propertyConfigurer.getMergedProperties().getProperty("appMode").split(","));			
			for (String appFlow : appModeList) {				
				List<String> appFlowValList = Arrays.asList(propertyConfigurer.getMergedProperties().getProperty(appFlow).split(","));
				appModeMap.put(appFlow, appFlowValList);			
			}
		}catch(IOException e){};
	}
	
	public String getNextView(String mode, String currentView){
		List<String> appList = (List<String>)appModeMap.get(mode);
		for(int i=0; i<appList.size(); i++){ 
			String view = (String)appList.get(i);
			if(view.equals(currentView)){
				return (i+1>=appList.size())?null:(String)appList.get(i+1);
			}
		}
		return null;
	}
}
