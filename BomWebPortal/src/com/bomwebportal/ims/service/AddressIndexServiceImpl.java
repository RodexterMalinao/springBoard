package com.bomwebportal.ims.service;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;

import com.bomwebportal.address.AddressHelper;
import com.bomwebportal.dto.AddressDTO;
import com.bomwebportal.ims.dto.ImsCCAddressDTO;
import com.bomwebportal.ims.address.CsvReader;
 

public class AddressIndexServiceImpl implements AddressIndexService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	private static final TreeMap<String, TreeSet<String>> searchKeyWordMap = new TreeMap<String, TreeSet<String>>();
	
	private DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}	 
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

    private String dataFilePath;
    
	public String getDataFilePath() {
		return dataFilePath;
	}
	public void setDataFilePath(String dataFilePath) {
		this.dataFilePath = dataFilePath;
	}

	public int buildAddressIndexFile(Directory pIndexDirectory) throws Exception {
		int numRec=0;
		Connection conn = null;
		Statement stmt = null;
	
		CsvReader csv = null;
		logger.info("Start read address keyword file : path " + this.dataFilePath + "addressKeyword.csv");
		csv = new CsvReader(this.dataFilePath + "addressKeyword.csv", ',', Charset.forName("UTF-8"));
		String[] values = null;
		while (csv.readRecord()) {
			values = csv.getValues();
			if (!searchKeyWordMap.containsKey(values[0])) {
				searchKeyWordMap.put(values[0], new TreeSet<String>());
			}
//			if (!searchKeyWordMap.containsKey(values[1])) {
//				searchKeyWordMap.put(values[1], new TreeSet<String>());
//			}
			searchKeyWordMap.get(values[0]).add(values[1]);
			//searchKeyWordMap.get(values[1]).add(values[0]);
		}
		csv.close();

		logger.info("Start build index for each record from b_addr_lkup table");
		try {            
			IndexWriter writer = new IndexWriter(pIndexDirectory,
				new WhitespaceAnalyzer(),
				true, IndexWriter.MaxFieldLength.UNLIMITED);

			conn = dataSource.getConnection();            
			stmt = conn.createStatement();             
//			ResultSet rs = stmt.executeQuery(
//                              "SELECT DISTINCT SERBDYNO, LOT_HSE_IND, HSELTNUM, LOTNO, BLDG_NAM, " +
//                              "       ST_NAM, STCATDESC, STCAT_CD, SECTDESC, SECT_CD, " + 
//                              "       DISTDESC, DISTRNUM, AREANAME, AREA_CD " +
//                              "FROM   B_ADDR_LKUP " + 
//                              "WHERE  BL_IND IS NULL"); 
			

			
			ResultSet rs = stmt.executeQuery(
					"SELECT a.NAME_CH, a.STREET_NAME_CH, b.SERBDYNO, b.LOT_HSE_IND, b.HSELTNUM, b.LOTNO, b.BLDG_NAM, b.ST_NAM," + 
					"       b.STCATDESC, b.STCAT_CD, b.SECTDESC, b.SECT_CD, b.DISTDESC, b.DISTRNUM, b.AREANAME, b.AREA_CD,    " +
					"		b.FLR_NUM, b.APFLTUN, 																			  " +
					"	   (SELECT DECODE (COUNT (*), 0, 'N', 'Y')                                                            " +
					"		FROM B_ADDR_LKUP A                                                                                " +
					"		WHERE A.SERBDYNO = B.SERBDYNO AND A.ADDPTYPE = 'I') SUPPORT_IMS                                   " +
					"FROM (SELECT DISTINCT SERBDYNO, LOT_HSE_IND, HSELTNUM, LOTNO, FLR_NUM, APFLTUN, BLDG_NAM,                " +
					"					  ST_NAM, STCATDESC, STCAT_CD, SECTDESC, SECT_CD,                                     " +
					"					  DISTDESC, DISTRNUM, AREANAME, AREA_CD, BL_IND                                       " +
					"	  FROM B_ADDR_LKUP) b, b_bldg_xy_src a, b_serbdyno_bldg_xy_src c                                      " +
					"WHERE b.BL_IND IS NULL                                                                                   " +
					"  AND b.serbdyno = c.sb_no(+)                                                                            " +
					"  AND a.geo_ref(+) = c.build_xy																		  ");
			
			while(rs.next()) { 
				numRec++;              
				ImsCCAddressDTO dto = new ImsCCAddressDTO();
				dto.setFloorNo(rs.getString("FLR_NUM"));
				dto.setFlatNo(rs.getString("APFLTUN"));
				dto.setServiceBoundaryNum(rs.getString("SERBDYNO"));
				dto.setLotHouseInd(rs.getString("LOT_HSE_IND"));
				dto.setHouseLotNum(rs.getString("HSELTNUM"));
				dto.setLotNum(rs.getString("LOTNO"));
				dto.setBuildingName(rs.getString("BLDG_NAM"));
				dto.setStreetName(rs.getString("ST_NAM"));
				dto.setStreetCatgDesc(rs.getString("STCATDESC"));
				dto.setStreetCatgCode(rs.getString("STCAT_CD"));
				dto.setSectionDesc(rs.getString("SECTDESC"));
				dto.setSectionCode(rs.getString("SECT_CD"));
				dto.setDistrictDesc(rs.getString("DISTDESC"));
				dto.setDistrictCode(rs.getString("DISTRNUM"));
				dto.setAreaDesc(rs.getString("AREANAME"));
				dto.setAreaCode(rs.getString("AREA_CD"));
				dto.setSupportIms(rs.getString("SUPPORT_IMS"));
				dto.setChiBuildingName(rs.getString("NAME_CH"));
				dto.setChiStreetName(rs.getString("STREET_NAME_CH"));
				
				writer.addDocument(genAddressDocument(dto));
				if (numRec % 50 == 0) {
					System.out.print(".");
				}
				if (numRec % 500 == 0) {
					System.out.println("");
					System.out.println(dto.getServiceBoundaryNum());
					System.out.flush();
				}
			}
			
			writer.optimize();
			writer.close();			
		}          
		catch(Exception e) { 
			e.printStackTrace(); 
			throw e;
		} 
		finally {
			if(stmt != null) {
				try {
					stmt.close();
				}   
				catch(Exception e) {
					e.printStackTrace();
					throw e;
				}
			}
			if(conn != null) {
				try {
					conn.close();
				}
				catch(Exception e) {
					e.printStackTrace();
					throw e;
				}
			}
		}      
		logger.info("End build index for each record from b_addr_lkup table, total record output = " + numRec);
		
		return numRec;
	}
	
	
	private static Document genAddressDocument(ImsCCAddressDTO addr) throws Exception {
		
		// "SERBDYNO","LOT_HSE_IND","HSELTNUM","LOTNO","BLDG_NAM","ST_NAM","STCATDESC","STCAT_CD","SECTDESC","SECT_CD","DISTDESC","DISTRNUM","AREANAME","AREA_CD","SUPPORT_IMS"
		
		Document doc = new Document();
		
		//doc.add(new Field("key", getAddressSearchString(addr), Field.Store.YES, Field.Index.ANALYZED));
		
		doc.add(new Field("SB", addr.getServiceBoundaryNum(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("ADDR_DESC", getAddressSearchString(addr), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("ADDR_DTO", AddressHelper.ImsAddressDTO2String(addr), Field.Store.YES, Field.Index.NO));
		doc.add(new Field("SUPPORT_IMS", addr.getSupportIms(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		return doc;
	}
	
	
	private static String getAddressSearchString(ImsCCAddressDTO pAddress) throws Exception {
		StringBuffer sb = new StringBuffer();
		
		//Tony added
		
		if (StringUtils.isNotBlank(pAddress.getFloorNo())) {
			
			//insertAddressKeyword(sb, pAddress.getFloorNo());
			sb.append(pAddress.getFloorNo()+"/F");
			sb.append(" ");
		}

		if (StringUtils.isNotBlank(pAddress.getFlatNo())) {

			//insertAddressKeyword(sb, pAddress.getFlatNo());
			sb.append("FLAT^"+pAddress.getFlatNo());
			sb.append(" ");
		}
		
		if (StringUtils.isNotBlank(pAddress.getChiBuildingName())) {

			insertAddressKeyword(sb, pAddress.getChiBuildingName());
			//sb.append(pAddress.getChiBuildingName());
			sb.append(" ");
		}

		if (StringUtils.isNotBlank(pAddress.getChiStreetName())) {

			insertAddressKeyword(sb, pAddress.getChiStreetName());
			//sb.append(pAddress.getChiStreetName());
			sb.append(" ");
		}
		
		//Tony added end

		if (StringUtils.isNotBlank(pAddress.getBuildingName())) {
			insertAddressKeyword(sb, pAddress.getBuildingName());
//			sb.append(pAddress.getBuildingName().replaceAll(" ", "^"));
			sb.append(" ");
			sb.append(pAddress.getBuildingName().replaceAll(" ", "^"));
			sb.append(" ");
		}
		
		if (StringUtils.isNotBlank(pAddress.getLotNum())) {
			insertHseLotNumSearchString(sb, pAddress.getLotNum());
		} else if (StringUtils.isNotBlank(pAddress.getHouseLotNum())) {
			insertHseLotNumSearchString(sb, pAddress.getHouseLotNum());
		}

		if (StringUtils.isNotBlank(pAddress.getStreetName())) {
			//sb.append(pAddress.getStreetName());
			//sb.append(" ");
			sb.append(pAddress.getStreetName().replaceAll(" ", "^"));
			if (StringUtils.isNotBlank(pAddress.getStreetCatgDesc())) {
				sb.append("^");
				insertAddressKeyword(sb, pAddress.getStreetCatgDesc());
//				sb.append(pAddress.getStreetCatgDesc().replaceAll(" ", "^"));
				sb.append(" ");
			}
			else{//
				sb.append(" ");
			}//
		}
		
		if (StringUtils.isNotBlank(pAddress.getSectionDesc())) {
			insertAddressKeyword(sb, pAddress.getSectionDesc());
//			sb.append(pAddress.getSectionDesc().replaceAll(" ", "^"));
			sb.append(" ");
		}
		
		if (StringUtils.isNotBlank(pAddress.getDistrictDesc())) {
			//sb.append(pAddress.getDistrictDesc());
			sb.append(pAddress.getDistrictDesc().replaceAll(" ", "^"));
			sb.append(" ");//original
		}

		if (StringUtils.isNotBlank(pAddress.getAreaDesc())) {
			insertAddressKeyword(sb, pAddress.getAreaDesc());
//			sb.append(pAddress.getAreaDesc().replaceAll(" ", "^"));
			sb.append(" ");//original
		}

		System.out.println("search string: "+sb.toString());
		return sb.toString();
	}
	
	private static void insertAddressKeyword(StringBuffer pStringBuffer, String pStringPhrase) throws Exception {
		try {
			StringTokenizer st = new StringTokenizer(
					pStringPhrase
						.replaceAll("\\(", " ")
						.replaceAll("\\)", " "), " ");
			String word = null;
			TreeSet<String> keywordSet = null;
			while (st.hasMoreTokens()) {
				word = st.nextToken();
				//pStringBuffer.append(word);
				//pStringBuffer.append(" ");
//				pStringBuffer.append("^");
//			if (word != null && word.length() <= 3) {
//				CreateAddressIndex.keywordSet.add(word);
//			}
				keywordSet = searchKeyWordMap.get(word);
				if (keywordSet == null) {
					pStringBuffer.append(word);
					//pStringBuffer.append(" ");
					pStringBuffer.append("^");
					continue;
				}
				for (String keyword : keywordSet) {
					
					pStringBuffer.append(keyword.replace(" ", "^"));
					
					pStringBuffer.append("^");
					//pStringBuffer.append(" ");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	private static void insertHseLotNumSearchString(StringBuffer pStringBuffer, String pHseLotNum) {
		pStringBuffer.append(pHseLotNum);
		pStringBuffer.append(" ");
		String hseLotNumKeyWord = pHseLotNum.toUpperCase()
				.replaceAll("\\(", " ")
				.replaceAll("\\)", " ")
				.replaceAll("&", " ")
				.replaceAll("-", " ")
				.replaceAll("'", " ")
				.replaceAll("/", " ")
				.replaceAll("\\\\", " ");
		if (pHseLotNum.equals(hseLotNumKeyWord)) {
			return;
		}
		if (StringUtils.isNotBlank(hseLotNumKeyWord)) {
			StringTokenizer st = new StringTokenizer(hseLotNumKeyWord, " ");
			while (st.hasMoreTokens()) {
				pStringBuffer.append(st.nextToken());
				pStringBuffer.append(" ");
			}
		}
	}
}