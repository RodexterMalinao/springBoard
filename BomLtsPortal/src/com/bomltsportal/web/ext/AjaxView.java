package com.bomltsportal.web.ext;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.view.AbstractView;

import flexjson.JSONSerializer;

public class AjaxView extends AbstractView {

	protected final Log logger = LogFactory.getLog(getClass());

    /* (non-Javadoc)

     * @see org.springframework.web.servlet.view.AbstractView#renderMergedOutputModel(java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)

     */

    @Override
    protected void renderMergedOutputModel(Map map, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        //logger.info("Resolving ajax request view -"+map);
        
        //JSONSerializer serializer = new JSONSerializer();
        //String jsonString = serializer.serialize(map );
        JSONArray json = (JSONArray)map.get("jsonArray");
        if(json == null){
        	JSONSerializer serializer = new JSONSerializer(); 
        	String jsonString = serializer.serialize(map ); 
        	response.setContentType( "text/plain; charset=UTF-8" );
        	response.getOutputStream().write( jsonString.getBytes() );
        }else{
        	//logger.info("testing: " + testing.toString());
        	//logger.info("jsonString: " + map.toString());
        	response.setContentType( "text/plain; charset=UTF-8" );
        	response.getOutputStream().write( json.toString().getBytes());
        }
    }
}
