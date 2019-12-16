package com.bomltsportal.interceptor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bomltsportal.exception.BrowserTypeException;

public class BrowserCheckInterceptor extends HandlerInterceptorAdapter{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		
		logger.trace("BrowserCheckInterceptor is involved");
		String userAgent =  request.getHeader("User-Agent");
		logger.trace(userAgent);
		
		if(request.getParameter("cookies")!=null && !"true".equals(request.getParameter("cookies"))){
			System.out.println("*****COOKIE IS NOT ALLOWED*****");		
		}
				
		Pattern pattern = Pattern.compile("Android\\s\\d\\.\\d\\.\\d\\;");
		Matcher matcher = pattern.matcher(userAgent);
		
		Pattern pattern2 = Pattern.compile("Android\\/\\d\\.\\d\\.\\d\\s");
		Matcher matcher2 = pattern2.matcher(userAgent);
		
		boolean matchEitherOne;
		boolean match1;
		boolean match2;
		
		match1 = matcher.find();
		match2 = matcher2.find();
		matchEitherOne = match1 || match2;
		
		if (matchEitherOne == true){
			   String versionString = "";
			   if (match1 == true) {
					
				//check if under version 2.3.3		
				//Android 2.4.4; en-us;
				/*   
				versionString = userAgent.substring(
						userAgent.indexOf("Android"), 
						userAgent.indexOf(";", userAgent.indexOf("Android")))
						.replaceAll("Android", "")
						.trim();
				*/
				   versionString =  matcher.group().replaceAll("[Android;]", "").trim();   
			
			   }else{
				//Android/1.1.1 Release/03.30.2013		
				  /* 
				versionString = userAgent.substring(
						userAgent.indexOf("Android"), 
						userAgent.indexOf(" ", userAgent.indexOf("Android")))
						.replaceAll("Android/", "")
						.trim();	
					*/	
				   versionString = matcher2.group().replaceAll("[Android/ ]", "").trim();
			   }	
			   int version1 = 0;
			   int version2 = 0;
			   int version3 = 0;
		
			  
			   String[] versionlist = versionString.split("\\.");
		
			   for(int i=0; i<versionlist.length; i++){
				   //System.out.println(versionlist[i]);
			
			   if(i==0){
				    version1 = Integer.parseInt(versionlist[i]);
				}else if(i==1){
				    version2 = Integer.parseInt(versionlist[i]);
				}else if(i==2){
			     	version3 = Integer.parseInt(versionlist[i]);
				}else{
				break;
				}
			
			   }
		
			   if(version1<=2 && version2<=3 && version3<=3){
				   logger.debug("Android version check unsupported:"+userAgent);
				   throw new BrowserTypeException();
			   }						
		
		}
		return true;
	}
	
	public static void main(String[] arg){
		
		//String test = "Mozilla/5.0 (Linux; U; Android 2.4.4; en-us; sdk Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
		String test = "sprd-GT-A7100/1.0 Linux/2.6.35.7 Android/1.1.1 Release/03.30.2013 Browser/AppleWebKit533.1 (KHTML, like Gecko) Mozilla/5.0 Mobile";
		//String test = "AMOI N816 Linux/3.0.13 Android/4.0.4 Release/12.19.2012 Browser/AppleWebKit534.30 Profile/MIDP-2.0 Configuration/CLDC-1.1 Mobile Safari/534.30 Android 4.0.1;";
		boolean matchEitherOne;
		boolean match1;
		boolean match2;
		

		Pattern pattern = Pattern.compile("Android\\s\\d\\.\\d\\.\\d\\;");
		Matcher matcher = pattern.matcher(test);

		
		
		Pattern pattern2 = Pattern.compile("Android\\/\\d\\.\\d\\.\\d\\s");
		Matcher matcher2 = pattern2.matcher(test);
		
		match1 = matcher.find();
		match2 = matcher2.find();
		//matchEitherOne = matcher.find() ||  matcher2.find();
		matchEitherOne = match1 || match2;
		
		System.out.println( "match1:" + match1);
		System.out.println( "match2:" + match2);
		System.out.println( "matchEitherOne:" + matchEitherOne); 
	
		//System.out.println("matcher.find()"+ matcher.find() +"/n" + "matcher2.find()"+ matcher2.find()); //false , true
	
		
	   if (matchEitherOne == true){
		   System.out.println("matchEitherOne found");
		   String versionString = "";
		if (match1== true) {
			/*versionString = test.substring(
									   test.indexOf("Android"), 
									   test.indexOf(";", test.indexOf("Android")))
									   .replaceAll("Android", "")
									   .trim();
			*/System.out.println( "matcher.group():" + matcher.group()); 
			versionString = matcher.group().replaceAll("[Android;]", "").trim();
		}else {							
			/*
			versionString = test.substring(
									   test.indexOf("Android"), 
									   test.indexOf(" ", test.indexOf("Android")))
									   .replaceAll("Android/", "")
									   .trim();		
			*/System.out.println( "matcher2.group():" + matcher2.group()); 
			versionString = matcher2.group().replaceAll("[Android/ ]", "").trim();
		}
		
		int version1 = 0;
		int version2 = 0;
		int version3 = 0;
										
		String[] versionlist = versionString.split("\\.");
			
		for(int i=0; i<versionlist.length; i++){
				System.out.println(versionlist[i]);
				if(i==0){
					version1 = Integer.parseInt(versionlist[i]);
				}else if(i==1){
					version2 = Integer.parseInt(versionlist[i]);
				}else if(i==2){
					version3 = Integer.parseInt(versionlist[i]);
				}else{
					break;
				}
				
		}
			
		if(version1<=2 && version2<=3 && version3<=3){
				System.out.println("Android version check unsupported:"+test);
				//throw new BrowserTypeException();
			}						
		
		System.out.println(version1+" "+version2+" "+version3);
	   }else {System.out.println("Either two patterns found, no checking:");}
	}
}