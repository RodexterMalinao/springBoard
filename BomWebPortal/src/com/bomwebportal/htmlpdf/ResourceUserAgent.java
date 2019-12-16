package com.bomwebportal.htmlpdf;

import java.io.InputStream;
import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.util.UrlPathHelper;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextUserAgent;

import com.google.gson.Gson;

public class ResourceUserAgent extends ITextUserAgent {

	private final Log logger = LogFactory.getLog(getClass());

	private ApplicationContext context;
	private HttpServletRequest request;
	
	public ResourceUserAgent(ITextOutputDevice outputDevice, ApplicationContext ctx, HttpServletRequest request) {
		super(outputDevice);
		this.context = ctx;
		this.request = request;
	}

	
	protected InputStream resolveAndOpenStream(String uri) {
		InputStream is = super.resolveAndOpenStream(uri);
		try {
			if (is == null) {
				if (uri.contains("WEB-INF") || uri.contains("META-INF")) {
					return null;
				}
				/*
				UrlPathHelper uph = new UrlPathHelper();
				System.out.println(uph.getContextPath(request));
				System.out.println(uph.getLookupPathForRequest(request));
				System.out.println(uph.getOriginatingContextPath(request));

				System.out.println(uph.getOriginatingRequestUri(request));

				System.out.println(uph.getPathWithinApplication(request));
				System.out.println(uph.getPathWithinServletMapping(request));
				System.out.println(uph.getRequestUri(request));
				System.out.println(uph.getServletPath(request));
				*/

				URI currentUri = new URI(
						StringUtils.trimToEmpty(request.getServletPath())
						+ StringUtils.trimToEmpty(request.getPathInfo()) );
				
				URI thisUri = currentUri.resolve(uri);
				
				

				Resource r = context.getResource(thisUri.toString());
				
				if (! r.exists()) {
					r = context.getResource("classpath:"+uri);
				}

				if (! r.exists()) {
					r = context.getResource("file:"+uri);

				}
				



				logger.debug("RES : " + uri + " -> " + r);
	
	
				is = r.getInputStream();
			}

		} catch (Exception e) {
			logger.error("Error loading resources. uri:"+uri, e);

		}
		return is;
	
	}

	public String resolveURI(String uri) {
		return uri;

	}

}
