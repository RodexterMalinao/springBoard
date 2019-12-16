package com.pccw.rpt.schema.dto;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.TreeMap;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.util.FastByteArrayInputStream;
import com.bomwebportal.util.FastByteArrayOutputStream;

public class ReportDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7719265698282999716L;

	private String jasperName = null;
	
	private byte[] companyLogo = null;
	
	private TreeMap<String, String> fieldCssMap = new TreeMap<String, String>();
	
	public ReportDTO() {}
	
	public ReportDTO(String pJasperName) {
		this.setJasperName(pJasperName);
	}
	
	public String getJasperName() {
		return jasperName;
	}
	
	public void setJasperName(String pJasperName) {
		jasperName = pJasperName;
	}

	public InputStream getCompanyLogo() {
		return new FastByteArrayInputStream(this.companyLogo, ArrayUtils.isEmpty(this.companyLogo) ? 0 : this.companyLogo.length);
	}

	public void setCompanyLogo(InputStream pCompanyLogoStream) {
		
		if (pCompanyLogoStream instanceof FastByteArrayInputStream) {
			this.companyLogo = ((FastByteArrayInputStream)pCompanyLogoStream).getBytes();
			return;
		}
		
        try {
			FastByteArrayOutputStream fbaos = new FastByteArrayOutputStream();
      
			byte[] b = new byte[1024];
			int noOfBytes = 0;
      
			//read bytes from source file and write to destination file
			while( (noOfBytes = pCompanyLogoStream.read(b)) != -1 ) {
			   fbaos.write(b, 0, noOfBytes);
			}
			this.companyLogo = fbaos.getByteArray();
      
			//close the streams
			pCompanyLogoStream.close();
			fbaos.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}                   
	}
	
	public void setCompanyLogo(byte[] pCompanyLogo) {
		this.companyLogo = pCompanyLogo;
	}
	
	public void putFieldCss(String pField, String pCss) {
		if (".css".equals(StringUtils.right(pField, 4))) {
			this.fieldCssMap.put(pField, pCss);
			return;
		}
		this.fieldCssMap.put(pField + ".css", pCss);
	}
	
	public TreeMap<String, String> getFieldCssMap() {
		return this.fieldCssMap;
	}
}