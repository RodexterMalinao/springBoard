package com.bomwebportal.ims.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import java.util.regex.Pattern;


public class CheckAsiaMilesAttbController implements Controller{
	protected final Log logger = LogFactory.getLog(getClass());

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("in CheckAsiaMilesIdController");
		
		String inputValue = request.getParameter("input");
		String type = request.getParameter("type");
		String valid;

		logger.info("checking Asia Miles attribute type: "+type);
		logger.info("checking Asia Miles attribute value: "+inputValue);
		
		if (type.equalsIgnoreCase("id")){
		
			char[] array = inputValue.toCharArray();
		
			if(array.length==0){
				valid="N";
			}else if(array.length==10){
				if(!Pattern.matches("[0-9]+", inputValue)){
					valid="N";
				}else{
					int checknum = Integer.parseInt(String.valueOf(array[0]))*1 +
							   	   Integer.parseInt(String.valueOf(array[1]))*2 +
							   	   Integer.parseInt(String.valueOf(array[2]))*3 +
							   	   Integer.parseInt(String.valueOf(array[3]))*4 +
							   	   Integer.parseInt(String.valueOf(array[4]))*5 +
							   	   Integer.parseInt(String.valueOf(array[5]))*6 +
							   	   Integer.parseInt(String.valueOf(array[6]))*7 +
							   	   Integer.parseInt(String.valueOf(array[7]))*8 +
							   	   Integer.parseInt(String.valueOf(array[8]))*9 ;
					checknum = checknum%10; 
					if(checknum!=Integer.parseInt(String.valueOf(array[9]))){
						valid="N";
					}else{
						valid="Y";
					}
				}
			}else{
				valid="N";
			}
		}else if(type.equalsIgnoreCase("name")){
			if(!Pattern.matches("[a-zA-Z ]+", inputValue)){
				valid="N";
			}else{
				valid="Y";
			}
		}else{
			valid="N";
		}
		
		JSONArray jsonArray = new JSONArray();


		logger.info("valid: "+valid);
		
		jsonArray.add(
				
				"{\"aisaMilesAttb\":\"" +inputValue + 
				"\",\"valid\":\"" + valid +
				"\"}");
		
		logger.info(jsonArray);
		
		return new ModelAndView("ajax_imscheckcorder", "jsonArray", jsonArray);
				
		
	}

}

