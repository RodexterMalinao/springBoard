package com.bomwebportal.ims.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ImsResourceServlet
extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int BUFFER_SIZE = 4096;
    private static String FILE_PATH;
    private static Properties props;
//
	public ImsResourceServlet() {
        super();
    }
 
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException{
    	/*
        RequestDispatcher dispatcher = request
                .getRequestDispatcher("/WEB-INF/pages/index.jsp");
        dispatcher.forward(request, response);*/
        
        
    	if(FILE_PATH==null) {
    		props = new Properties(); 
    		props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("configuration.properties"));
    		
    		String environment = props.getProperty("environment");
    		FILE_PATH = props.getProperty(environment + ".resources");
        }
        String url = request.getRequestURI();
        String path = FILE_PATH + url;
        if(readFileInPath(path, response) == false)
        {
        	path = request.getSession().getServletContext().getRealPath(url.substring(url.indexOf("/", 2) + 1));
			path = path.replaceAll("\\\\", "/") ;
			readFileInPath(path, response);
        }
 
    }
    
    private boolean readFileInPath(String path, HttpServletResponse response)
    throws  IOException{
    	path = path.replaceAll("%20", " ");
    	try {
	    	File file = new File(path);
			if(file != null)
			 {
		        FileInputStream inputStream = new FileInputStream(file);
		        
		        response.setContentLength((int) file.length());
		        byte[] buffer = new byte[BUFFER_SIZE];
		        int bytesRead = -1;
		        
		        OutputStream outStream = response.getOutputStream();
		        while ((bytesRead = inputStream.read(buffer)) != -1) {
		            outStream.write(buffer, 0, bytesRead);
		        }
		        
		        inputStream.close();
		        outStream.flush();
		        outStream.close();
		        
		        
		        return true;
		     }
	    	}catch (FileNotFoundException e) { 
	    		//no file 
	    		System.out.println("no find"+path);
	    		return false;
	    	}catch (IOException e) {
			    System.out.println(e.getMessage());
			}
	    	return false;
    }
}