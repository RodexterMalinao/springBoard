package com.bomwebportal.address;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSLockFactory;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.AddressDTO;
import com.bomwebportal.ims.address.CsvReader;
import com.bomwebportal.ims.dto.ImsCCAddressDTO;
import com.google.gson.Gson;

public class AddressHelper {

	private static Gson gson = new Gson();
	
	//steven added 20130425
	private static String STATIC_VERSION_NUMBER ="";
	// martin, 20180529
	private static String STATIC_USE_LOCAL_IDX = "Y";
	private static String[] STATIC_VERSION_ARRAY = {"1","2","3"};
	private static String STATIC_PREV_VERSION_NUMBER = "";
	private static boolean STATIC_CHG_VER_IND = true;
	
	public void checkVersionForCloseIndex(){
		logger.debug("checkVersionForCloseIndex is called");
		try {
			logger.info("before STATIC_USE_LOCAL_IDX:"+STATIC_USE_LOCAL_IDX);
			String verNum = getIndexDirectoryVersion();
			logger.info("after STATIC_USE_LOCAL_IDX:"+STATIC_USE_LOCAL_IDX);
			if(!STATIC_VERSION_NUMBER.equals(verNum)){
				STATIC_PREV_VERSION_NUMBER = "";
				logger.info("before STATIC_VERSION_NUMBER:"+STATIC_VERSION_NUMBER);
				closeIndexFile();
				STATIC_VERSION_NUMBER = getIndexDirectoryVersion();
				logger.info("after STATIC_VERSION_NUMBER:"+STATIC_VERSION_NUMBER);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
	}
	//steven added 20130425 end	

	
	public void createSearchKeyWordMap()throws Exception {
		
		CsvReader csv = null;
		logger.info("Start read address keyword file : path " + this.dataFilePath + "addressKeyword.csv");
		csv = new CsvReader(this.dataFilePath + "addressKeyword.csv", ',', Charset.forName("UTF-8"));
		
		String[] values = null;
		while (csv.readRecord()) {
			values = csv.getValues();
			if (!searchKeyWordMap.containsKey(values[0])) {
				searchKeyWordMap.put(values[0], new TreeSet<String>());
			}
			//if (!searchKeyWordMap.containsKey(values[1])) {
			//	searchKeyWordMap.put(values[1], new TreeSet<String>());
			//}
			
			searchKeyWordMap.get(values[0]).add(values[1]);
		//	searchKeyWordMap.get(values[1]).add(values[0]);
		}
		csv.close();

//		logger.info("searchKeyWordMap:" + gson.toJson(searchKeyWordMap));
	}
	
	
	private static final TreeMap<String, TreeSet<String>> searchKeyWordMap = new TreeMap<String, TreeSet<String>>();

	
	protected static final Log logger = LogFactory.getLog(AddressHelper.class);
	
	public static final String SUPPORT_IMS = "Y";
	
	public static final String ADDRESS_FIELD_SB = "SB";
	
	public static final String ADDRESS_FIELD_SINGLE_LINE = "ADDR_DESC";

	public static final String ADDRESS_FIELD_DTO = "ADDR_DTO";
	
	public static final String ADDRESS_FIELD_SUPPORT_IMS = "SUPPORT_IMS";
	
    private String idxFilePath;
    
	public String getIdxFilePath() {
		return idxFilePath;
	}

	public void setIdxFilePath(String idxFilePath) {
		this.idxFilePath = idxFilePath;
	}

    private String dataFilePath;
    
	public String getDataFilePath() {
		return dataFilePath;
	}

	public void setDataFilePath(String dataFilePath) {
		this.dataFilePath = dataFilePath;
	}
	
	private String localIdxFilePath;
	private String localDataFilePath;
	
	public String getLocalIdxFilePath() {
		return localIdxFilePath;
	}
	public void setLocalIdxFilePath(String localIdxFilePath) {
		this.localIdxFilePath = localIdxFilePath;
	}
	public String getLocalDataFilePath() {
		return localDataFilePath;
	}
	public void setLocalDataFilePath(String localDataFilePath) {
		this.localDataFilePath = localDataFilePath;
	}

	public static final int MAX_RESULT = 20;
	
	public static void main(String[] args) {
		//searchAddress("KEI LING HA MANAGEMENT CENTER");
	}
	
	private Directory directory;
	
	private Searcher searcher;
	
	public void closeIndexFile() throws Exception{
		try {
			if (searcher != null){
				searcher.close();
			}
			if (directory != null){
				directory.close();
			}
			searcher = null;
			directory = null;
			logger.debug("Close Index File");
		}catch(Exception e) {
			logger.error("", e);
		}finally {
			searcher = null;
			directory = null;
		}
	}
	
	public Searcher getIndexSearcher() throws Exception{
		checkVersionForCloseIndex();//steven added 20130425
		try{
			if(searcher==null){
				logger.debug("Initialize Searcher object");
				searcher = new IndexSearcher(getIndexDirectory(), true);
				createSearchKeyWordMap();
			}
			logger.debug(">> Directory==" + (directory==null?"NULL":directory.toString()));
		}catch(Exception e){
			closeIndexFile();
			if (STATIC_PREV_VERSION_NUMBER!=null&&!"".equals(STATIC_PREV_VERSION_NUMBER)) {
				STATIC_PREV_VERSION_NUMBER = getPrevVerNum(STATIC_PREV_VERSION_NUMBER);
			} else {
				STATIC_PREV_VERSION_NUMBER = getPrevVerNum(STATIC_VERSION_NUMBER);
			}
			//STATIC_VERSION_NUMBER = "";
			//STATIC_USE_LOCAL_IDX = "Y";
			logger.error("getIndexSearcher Exception", e);
		}
		return searcher;
	}
	
	public Directory getIndexDirectory() throws Exception {
		String version = "";
		
		try {
			if (directory == null) {
				version = getIndexDirectoryVersion();
				if (STATIC_PREV_VERSION_NUMBER!=null&&!"".equals(STATIC_PREV_VERSION_NUMBER)) {
					version = STATIC_PREV_VERSION_NUMBER;
					logger.debug("fallback version == " + version);
				} else {
					logger.debug("version == " + version);
				}
				
				logger.debug("Open index file directory");
				
				File idxDir = new File(this.localIdxFilePath + "_v" + version);
				if (!"Y".equals(STATIC_USE_LOCAL_IDX) || idxDir == null || !idxDir.exists()) {
					idxDir = new File(this.idxFilePath + "_v" + version);
					STATIC_USE_LOCAL_IDX = "N";
				}
				directory = FSDirectory.open(idxDir, new SimpleFSLockFactory());
			}			
			return directory;
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}
	
	public String getIndexDirectoryVersion() throws Exception {
		String version = "";
		
		try {
			File verFile = new File(this.localDataFilePath + "version.ini");
			if (!"Y".equals(STATIC_USE_LOCAL_IDX) || verFile == null || !verFile.exists()) {
				verFile = new File(this.dataFilePath + "version.ini");
				if ("Y".equals(STATIC_USE_LOCAL_IDX)) {
					STATIC_VERSION_NUMBER = "";
					STATIC_USE_LOCAL_IDX = "N";
				}
			}
			BufferedReader in = new BufferedReader(new FileReader(verFile));			
			version = in.readLine().trim().substring(0, 1);
			in.close();
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return version;
	}
	
	public String getPrevVerNum(String curVerNum) {
		String version = "";
		int prevInd = 0;
		if (STATIC_CHG_VER_IND) {
			STATIC_CHG_VER_IND = false;
			try {
				for (int i = 0; i < STATIC_VERSION_ARRAY.length; i++) {
					if (STATIC_VERSION_ARRAY[i].equals(curVerNum)) {
						prevInd = i - 1;
						break;
					}
				}
				if (prevInd < 0) {
					prevInd = STATIC_VERSION_ARRAY.length-1;
				}
			} catch (Exception e) {
				logger.error("getPrevVerNum error", e);
			} finally {
				STATIC_CHG_VER_IND = true;
			}
			version = STATIC_VERSION_ARRAY[prevInd];
			STATIC_CHG_VER_IND = true;
			return version;
		} else {
			return curVerNum;
		}
	}
	
	public AddressDTO[] searchAddress(String pSearchString) {
		ArrayList<AddressDTO> rtnList = new ArrayList<AddressDTO>(MAX_RESULT + 1);
		Date startTime = new Date();
		try {
			if (StringUtils.isBlank(pSearchString)) {
				return new AddressDTO[0];
			}
			
			//Searcher searcher = new IndexSearcher(getIndexDirectory(), true);
			//ScoreDoc[] topDocs = getHits(searcher, pSearchString.toUpperCase());
			ScoreDoc[] topDocs = getHits(getIndexSearcher(), pSearchString.toUpperCase());
			if (topDocs == null) {
				return new AddressDTO[0];
			}
			//logger.info("SearchAddress - Result Found: " + topDocs.length);
			
			Document doc = null;
			AddressDTO addressDTO = null;
			for (int i = 0; topDocs != null && i < topDocs.length; i++) {
				doc = searcher.doc(topDocs[i].doc);
				addressDTO = string2AddressDTO(doc.get(ADDRESS_FIELD_DTO));
				rtnList.add(addressDTO);
				//System.out.println("RESULT: " + addressDTO.getSingleLineAddress() + " - " + doc.get(ADDRESS_FIELD_SINGLE_LINE));
				if (rtnList.size() >= MAX_RESULT) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			//logger.error(ExceptionUtils.getFullStackTrace(e));
			logger.error(e);
		}
		Date endTime = new Date();
		logger.debug("SearchAddress "
				+ (endTime.getTime() - startTime.getTime())
				+ " (ms) used");
		return (AddressDTO[]) rtnList.toArray(new AddressDTO[0]);
	}
	
	public AddressDTO[] imsSearchAddress(String pSearchString, String type) {
		ArrayList<AddressDTO> rtnList = new ArrayList<AddressDTO>(MAX_RESULT + 1);
		Date startTime = new Date();
		try {
			if (StringUtils.isBlank(pSearchString)) {
				return new AddressDTO[0];
			}

			//Searcher searcher = new IndexSearcher(getIndexDirectory(), true);
			//ScoreDoc[] topDocs = getHits(searcher, pSearchString.toUpperCase());
			ScoreDoc[] topDocs = imsGetHits(getIndexSearcher(), pSearchString.toUpperCase(), type, SUPPORT_IMS);
			if (topDocs == null) {
				return new AddressDTO[0];
			}
			
			logger.info("type "+ type + " SearchAddress: " + pSearchString + " SearchAddress - Result Found: " + topDocs.length);
			
			Document doc = null;
			AddressDTO addressDTO = null;
			for (int i = 0; topDocs != null && i < topDocs.length; i++) {
				doc = searcher.doc(topDocs[i].doc);
//				logger.info("COVER_EYEX:" + doc.get("COVER_EYEX")+"  COVER_PE:" + doc.get("COVER_PE"));
				addressDTO = string2AddressDTO(doc.get(ADDRESS_FIELD_DTO));
				rtnList.add(addressDTO);
				//System.out.println("RESULT: " + addressDTO.getSingleLineAddress() + " - " + doc.get(ADDRESS_FIELD_SINGLE_LINE));
				if (rtnList.size() >= MAX_RESULT) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			//logger.error(ExceptionUtils.getFullStackTrace(e));
			logger.error(e);
		}
		Date endTime = new Date();
		logger.debug("SearchAddress "
				+ (endTime.getTime() - startTime.getTime())
				+ " (ms) used");
		return (AddressDTO[]) rtnList.toArray(new AddressDTO[0]);
	}
	
	public AddressDTO[] ltsSearchAddress(String pSearchString) {
		ArrayList<AddressDTO> rtnList = new ArrayList<AddressDTO>(MAX_RESULT + 1);
		Date startTime = new Date();
		try {
			if (StringUtils.isBlank(pSearchString)) {
				return new AddressDTO[0];
			}
			// logger.info("type: lts addr search");

			ScoreDoc[] topDocs = null;
			topDocs = imsGetHits(getIndexSearcher(), pSearchString.toUpperCase(), "address", null);
			
			if (topDocs == null) {
				return new AddressDTO[0];
			}
			logger.info("SearchAddress: " + pSearchString + " SearchAddress - Result Found: " + topDocs.length);
			
			Document doc = null;
			AddressDTO addressDTO = null;
			for (int i = 0; topDocs != null && i < topDocs.length; i++) {
				doc = searcher.doc(topDocs[i].doc);
				addressDTO = string2AddressDTO(doc.get(ADDRESS_FIELD_DTO));
				rtnList.add(addressDTO);
				//System.out.println("RESULT: " + addressDTO.getSingleLineAddress() + " - " + doc.get(ADDRESS_FIELD_SINGLE_LINE));
				if (rtnList.size() >= MAX_RESULT) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			//logger.error(ExceptionUtils.getFullStackTrace(e));
			logger.error(e);
		}
		Date endTime = new Date();
		logger.debug("SearchAddress "
				+ (endTime.getTime() - startTime.getTime())
				+ " (ms) used");
		return (AddressDTO[]) rtnList.toArray(new AddressDTO[0]);
	}
	
	private static ScoreDoc[] getHits(Searcher pSearcher, String pSearchString) throws Exception {
		//StringTokenizer st = new StringTokenizer(pSearchString, " ");
		//StringTokenizer st = new StringTokenizer(pSearchString, ",");
		StringTokenizer st;
		if (pSearchString.indexOf(",")>0){
			st = new StringTokenizer(pSearchString, ",");
		}
		else{
			st = new StringTokenizer(pSearchString, " ");
		}
		ArrayList<String> searchPhraseList = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			searchPhraseList.add(st.nextToken());
		}
	
		TopDocs topDocs = getMatches(pSearcher, searchPhraseList, null);
	    //TopDocs topDocs = imsGetMatches(pSearcher, searchPhraseList,"address", null,null);
		if (topDocs.totalHits > 0) {
			return topDocs.scoreDocs;	
		}
		return null;
//		
//		ArrayList<String> firstMatchPhraseList = new ArrayList<String>();
//		TopDocs mostMatchTopDocs = null;
//		for (String searchPhrase : searchPhraseList) {
//			topDocs = getMatches(pSearcher, firstMatchPhraseList, searchPhrase);
//			if (topDocs.totalHits > 0) {
//				mostMatchTopDocs = topDocs;
//				firstMatchPhraseList.add(searchPhrase);
//			}
//		}
//		if (mostMatchTopDocs == null) {
//			return null;
//		}
//		return mostMatchTopDocs.scoreDocs;
	}
	
	private static ScoreDoc[] imsGetHits(Searcher pSearcher, String pSearchString, String type, String pSupportIms) throws Exception {
		//StringTokenizer st = new StringTokenizer(pSearchString, " ");
		StringTokenizer st = new StringTokenizer(pSearchString, ",");
		Boolean isUseComma=true;
		if (pSearchString.indexOf(",")>0){
//			logger.info("is Use Comma");
			st = new StringTokenizer(pSearchString, ",");}
		else{
//			logger.info("is not Use Comma");
			isUseComma=false;
			st = new StringTokenizer(pSearchString, " ");
		}
		
		ArrayList<String> searchPhraseList = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			searchPhraseList.add(st.nextToken());
		}
		
		TopDocs topDocs = imsGetMatches(pSearcher, searchPhraseList, type, null, pSupportIms, isUseComma);
		if (topDocs.totalHits > 0) {
			return topDocs.scoreDocs;	
		}
		return null;
//		
//		ArrayList<String> firstMatchPhraseList = new ArrayList<String>();
//		TopDocs mostMatchTopDocs = null;
//		for (String searchPhrase : searchPhraseList) {
//			topDocs = getMatches(pSearcher, firstMatchPhraseList, searchPhrase);
//			if (topDocs.totalHits > 0) {
//				mostMatchTopDocs = topDocs;
//				firstMatchPhraseList.add(searchPhrase);
//			}
//		}
//		if (mostMatchTopDocs == null) {
//			return null;
//		}
//		return mostMatchTopDocs.scoreDocs;
	}
	
	private static TopDocs getMatches(Searcher pSearcher,
			ArrayList<String> pSearchPhraseList, String pAdditionalSearchPhrase) throws Exception {
		PhraseQuery phraseQuery = new PhraseQuery();
		phraseQuery.setSlop(20);
		Query query = new BooleanQuery();
		StringBuffer sb = new StringBuffer();

		for (String searchPhrase : pSearchPhraseList) {
			
			String newSearchPhrase = updateAddressKeyword(searchPhrase);
			if (newSearchPhrase != null){
				if (newSearchPhrase.indexOf("*") != -1) {
					
					if (!ArrayUtils.isEmpty(phraseQuery.getTerms())) {
						((BooleanQuery) query).add(phraseQuery,
								BooleanClause.Occur.MUST);
						phraseQuery = new PhraseQuery();
						phraseQuery.setSlop(20);
					}
	
					((BooleanQuery) query).add(
							new PrefixQuery(new Term(ADDRESS_FIELD_SINGLE_LINE,
									StringUtils.left(newSearchPhrase,
											newSearchPhrase.length() - 1))),
							BooleanClause.Occur.MUST);
					continue;
				}
				((BooleanQuery) query).add(
						new WildcardQuery(new Term(ADDRESS_FIELD_SINGLE_LINE,
								"*"+newSearchPhrase.trim().replace(" ", "^")+"*")),
						BooleanClause.Occur.MUST);
				
				sb.append(newSearchPhrase);
				sb.append(" ");
			}
		}

		if (StringUtils.isNotBlank(pAdditionalSearchPhrase)) {
			phraseQuery.add(new Term(ADDRESS_FIELD_SINGLE_LINE,
					pAdditionalSearchPhrase));
			sb.append(pAdditionalSearchPhrase);
			sb.append(" ");
		}

		if (!ArrayUtils.isEmpty(phraseQuery.getTerms())) {
			((BooleanQuery) query).add(phraseQuery, BooleanClause.Occur.MUST);
		}

		Date startTime = new Date();
		TopDocs matches = pSearcher.search(query, 20);
		Date endTime = new Date();
		logger.debug("SearchAddress "
				+ (endTime.getTime() - startTime.getTime())
				+ " (ms) used with " + matches.totalHits + " total matching - "
				+ sb.toString());
		return matches;
	}
	
	private static TopDocs imsGetMatches(Searcher pSearcher,
			ArrayList<String> pSearchPhraseList, String type, String pAdditionalSearchPhrase, String pSupportIms, Boolean isUseComma) throws Exception {	
		
		//Tony
		
		StringBuffer sb = new StringBuffer();
		BooleanQuery query= new BooleanQuery();
		System.out.println("type "+type);
//		logger.info("pSearchPhraseList:" + gson.toJson(pSearchPhraseList));
		
		if (type.equalsIgnoreCase("address")){
		
			for (String searchPhrase : pSearchPhraseList) {
//				logger.info("b4 replace newSarchPhrase:" + searchPhrase);
				Boolean replaceKeyword = true;
				if(LookupTableBean.getInstance().getImsNonReplaceKeywords().get(searchPhrase.replace(" ", ""))!=null){
					replaceKeyword=false;
				}
				String newSarchPhrase = searchPhrase;
				if(replaceKeyword){
					newSarchPhrase = updateAddressKeyword(searchPhrase);
				}
//				logger.info("after replace newSarchPhrase:" + newSarchPhrase);
				if (newSarchPhrase != null){
					if(isUseComma&&isNumeric(newSarchPhrase.replace(" ", ""))){
//						logger.info("number");
						PhraseQuery q = new PhraseQuery();
						q.add(new Term(ADDRESS_FIELD_SINGLE_LINE, newSarchPhrase.replace(" ", "")));
						
						query.add(q, BooleanClause.Occur.MUST);
					
						sb.append(newSarchPhrase);
					}else{
//						logger.info("not number");
						Query q =new WildcardQuery(
								new Term(ADDRESS_FIELD_SINGLE_LINE, 
										"*"+newSarchPhrase.trim().replace(" ", "^")+"*"));
						query.add(q, BooleanClause.Occur.MUST);
					
						sb.append(newSarchPhrase);
					}
				}
			}		
		
			if (StringUtils.isNotBlank(pSupportIms)) {
				PhraseQuery imsPhraseQuery = new PhraseQuery();
				imsPhraseQuery.add(new Term(ADDRESS_FIELD_SUPPORT_IMS, pSupportIms));
				sb.append(pSupportIms);
				sb.append(" ");
				((BooleanQuery) query).add(imsPhraseQuery, BooleanClause.Occur.MUST);
			}		
		}
		
		if (type.equalsIgnoreCase("sb")){
		
			for (String searchPhrase : pSearchPhraseList) {
				Query q =new WildcardQuery(
						new Term("SB", 
								"*"+searchPhrase.trim().replace(" ", "^")+"*"));
				query.add(q, BooleanClause.Occur.MUST);
			
				sb.append(searchPhrase);
			}
		}
		
		Date startTime = new Date();
		logger.debug("query:" + query);
		TopDocs matches = pSearcher.search(query, MAX_RESULT);
//		logger.info("matches:" + gson.toJson(matcs));
		Date endTime = new Date();
		logger.debug("SearchAddress "
				+ (endTime.getTime() - startTime.getTime())
				+ " (ms) used with " + matches.totalHits
				+ " total matching - " + sb.toString());
		return matches;
		
		//Tony ended
		
//		Commented by Tony		
//		PhraseQuery phraseQuery = new PhraseQuery();
//		phraseQuery.setSlop(20);
//		BooleanQuery query = new BooleanQuery();
//		StringBuffer sb = new StringBuffer();
		
//		if (type.equalsIgnoreCase("sb")){
//		
//			for (String searchPhrase : pSearchPhraseList) {
//				Query q =new WildcardQuery(
//						new Term("SB", 
//								"*"+searchPhrase.trim().replace(" ", "^")+"*"));
//				query.add(q, BooleanClause.Occur.MUST);
//			
//				sb.append(searchPhrase);
//			}
//		}

//		if (type.equalsIgnoreCase("address")){//
//		
//			for (String searchPhrase : pSearchPhraseList) {
//				if (searchPhrase.indexOf("*") != -1) {
//					if (query == null) {
//						query = new BooleanQuery();
//					}
//					if (!ArrayUtils.isEmpty(phraseQuery.getTerms())) {
//						((BooleanQuery) query).add(phraseQuery,
//								BooleanClause.Occur.MUST);
//						phraseQuery = new PhraseQuery();
//						phraseQuery.setSlop(20);
//					}
//
//					((BooleanQuery) query).add(
//							new PrefixQuery(new Term(ADDRESS_FIELD_SINGLE_LINE,
//									StringUtils.left(searchPhrase,
//											searchPhrase.length() - 1))),
//											BooleanClause.Occur.MUST);
//					continue;	
//				}
//
//				phraseQuery.add(new Term(ADDRESS_FIELD_SINGLE_LINE, searchPhrase));
//				sb.append(searchPhrase);
//				sb.append(" ");
//			}
//
//			if (StringUtils.isNotBlank(pAdditionalSearchPhrase)) {
//				phraseQuery.add(new Term(ADDRESS_FIELD_SINGLE_LINE,
//						pAdditionalSearchPhrase));
//				sb.append(pAdditionalSearchPhrase);
//				sb.append(" ");
//			}
//
//			((BooleanQuery) query).add(phraseQuery, BooleanClause.Occur.MUST);
//		
//			if (StringUtils.isNotBlank(pSupportIms)) {
//				PhraseQuery imsPhraseQuery = new PhraseQuery();
//				imsPhraseQuery.add(new Term(ADDRESS_FIELD_SUPPORT_IMS, pSupportIms));
//				sb.append(pSupportIms);
//				sb.append(" ");
//				((BooleanQuery) query).add(imsPhraseQuery, BooleanClause.Occur.MUST);
//			}
//		}//
//		
//		Date startTime = new Date();
//		TopDocs matches = pSearcher.search(query, MAX_RESULT+15);
//		Date endTime = new Date();
//		logger.debug("imsSearchAddress "
//				+ (endTime.getTime() - startTime.getTime())
//				+ " (ms) used with " + matches.totalHits + " total matching - "
//				+ sb.toString());
//		return matches;
	}
	
	public static boolean isNumeric(String str)
	{
	  NumberFormat formatter = NumberFormat.getInstance();
	  ParsePosition pos = new ParsePosition(0);
	  formatter.parse(str, pos);
	  return str.length() == pos.getIndex();
	}
	
	public static String updateAddressKeyword(String pStringPhrase) throws Exception {

//		logger.info("pSearchPhraseList b4:" + gson.toJson(pStringPhrase));
		try {
			String temp = null;
			StringTokenizer st = new StringTokenizer(
					pStringPhrase
						.replaceAll("\\(", " ")
						.replaceAll("\\)", " "), " ");
			String word = null;
			TreeSet<String> keywordSet = null;
			while (st.hasMoreTokens()) {
				word = st.nextToken();
				
//				logger.info("word is-----"+word);
				
				keywordSet = searchKeyWordMap.get(word);
//				logger.info((keywordSet == null));
				if (keywordSet == null||keywordSet.isEmpty()) {
					if (temp == null){
						temp = word + " ";
					}else{
						temp = temp + word+ " ";
					}
					continue;
				}else{
//					if (temp == null){
//						temp = word + " ";
//					}else{
//						temp = temp + word+ " ";
//					}

//					logger.info("keywordSet:" + gson.toJson(keywordSet));
					for (String keyword : keywordSet) {
						//System.out.println("find match---"+word+" "+keyword);
						//temp = temp.replaceAll(" "+word+" ", " "+keyword+" ");
						if (temp == null){
							temp = keyword + " ";
						}else{
							temp = temp + keyword+ " ";
						}
						//System.out.println("temp is---"+temp);
					}
				}
			}
//			logger.info("temp after:" + gson.toJson(temp));
			return temp;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	private static String translateQuery(String pQuery) {
		pQuery = pQuery.trim();
		if ("*".equals(StringUtils.right(pQuery, 1))) {
			pQuery = pQuery.substring(0, pQuery.length() - 1);
		}
		pQuery = StringUtils.replace(pQuery, "*", " AND ");
		if (StringUtils.indexOf(pQuery, "\"") == -1) {
			return StringUtils.replace(pQuery, "#", "*");
		}
		
		StringBuffer sbQuery = new StringBuffer();
		String tmpQuery = pQuery;
		String leftStr = null;
		int pos = StringUtils.indexOf(tmpQuery, "\"");
		while (pos != -1) {
			leftStr = StringUtils.substringBefore(tmpQuery, "\"");
			sbQuery.append(StringUtils.replace(leftStr, "#", "*"));
			tmpQuery = StringUtils.substringAfter(tmpQuery, "\"");
			leftStr = StringUtils.substringBefore(tmpQuery, "\"");
			sbQuery.append("\"");
			sbQuery.append(leftStr);
			sbQuery.append("\"");
			tmpQuery = StringUtils.substringAfter(tmpQuery, "\"");
			pos = StringUtils.indexOf(tmpQuery, "\"");
		}
		sbQuery.append(StringUtils.defaultString(StringUtils.replace(tmpQuery, "#", "*")));
		return sbQuery.toString();
	}
	
	private static boolean isAcceptSearchResult(String pSearchString, String pSingleLineAddress) {
		StringTokenizer st = new StringTokenizer(pSearchString, " ");
		String lastToken = null;
		int lastIdx = -1;
		String token = null;
		int pos = -1;
		while (st.hasMoreTokens()) {
			token = st.nextToken();
			pos = pSingleLineAddress.indexOf(token);
			if (pos != -1) {
				if (pos < lastIdx) {
					if (pSingleLineAddress.indexOf(lastToken + " " + token) != -1
							|| pSingleLineAddress.indexOf(lastToken + token) != -1) {
						lastToken = token;
						continue;
					} else {
						return false;
					}
				}
				lastIdx = pos;
				lastToken = token;
			}
		}
		return true;
	}
	
	public static AddressDTO string2AddressDTO(String pAddress) {
		StringTokenizer st = new StringTokenizer(pAddress, "^");
		String token = null;
		AddressDTO addr = new AddressDTO();
		token = st.nextToken();
		if (StringUtils.isNotBlank(token)) {
			addr.setServiceBoundaryNum(token.trim());	
		}
		token = st.nextToken();
		if (StringUtils.isNotBlank(token)) {
			addr.setHouseLotNum(token.trim());
		}
		token = st.nextToken();
		if (StringUtils.isNotBlank(token)) {
			addr.setLotNum(token.trim());
		}
		token = st.nextToken();
		if (StringUtils.isNotBlank(token)) {
			addr.setBuildingName(token.trim());
		}
		token = st.nextToken();
		if (StringUtils.isNotBlank(token)) {
			addr.setStreetName(token.trim());
		}
		token = st.nextToken();
		if (StringUtils.isNotBlank(token)) {
			addr.setStreetCatgDesc(token.trim());
		}
		token = st.nextToken();
		if (StringUtils.isNotBlank(token)) {
			addr.setStreetCatgCode(token.trim());
		}
		token = st.nextToken();
		if (StringUtils.isNotBlank(token)) {
			addr.setSectionDesc(token.trim());
		}
		token = st.nextToken();
		if (StringUtils.isNotBlank(token)) {
			addr.setSectionCode(token.trim());
		}
		token = st.nextToken();
		if (StringUtils.isNotBlank(token)) {
			addr.setDistrictDesc(token.trim());
		}
		token = st.nextToken();
		if (StringUtils.isNotBlank(token)) {
			addr.setDistrictCode(token.trim());
		}
		token = st.nextToken();
		if (StringUtils.isNotBlank(token)) {
			addr.setAreaDesc(token.trim());
		}
		token = st.nextToken();
		if (StringUtils.isNotBlank(token)) {
			addr.setAreaCode(token.trim());
		}
		addr.setAddress(addr.getSingleLineAddress());
		return addr;
	}
	
	public static String AddressDTO2String(AddressDTO pAddress) {
		StringBuffer sb = new StringBuffer(StringUtils.defaultIfEmpty(pAddress.getServiceBoundaryNum(), "0"));
		sb.append("^");
		sb.append(StringUtils.defaultIfEmpty(pAddress.getHouseLotNum(), " "));
		sb.append("^");
		sb.append(StringUtils.defaultIfEmpty(pAddress.getLotNum(), " "));
		sb.append("^");
		sb.append(StringUtils.defaultIfEmpty(pAddress.getBuildingName(), " "));
		sb.append("^");
		sb.append(StringUtils.defaultIfEmpty(pAddress.getStreetName(), " "));
		sb.append("^");
		sb.append(StringUtils.defaultIfEmpty(pAddress.getStreetCatgDesc(), " "));
		sb.append("^");
		sb.append(StringUtils.defaultIfEmpty(pAddress.getStreetCatgCode(), " "));
		sb.append("^");
		sb.append(StringUtils.defaultIfEmpty(pAddress.getSectionDesc(), " "));
		sb.append("^");
		sb.append(StringUtils.defaultIfEmpty(pAddress.getSectionCode(), " "));
		sb.append("^");
		sb.append(StringUtils.defaultIfEmpty(pAddress.getDistrictDesc(), " "));
		sb.append("^");
		sb.append(StringUtils.defaultIfEmpty(pAddress.getDistrictCode(), " "));
		sb.append("^");
		sb.append(StringUtils.defaultIfEmpty(pAddress.getAreaDesc(), " "));
		sb.append("^");
		sb.append(StringUtils.defaultIfEmpty(pAddress.getAreaCode(), " "));
		return sb.toString();
	}
	
	public static String ImsAddressDTO2String(ImsCCAddressDTO pAddress) {
		StringBuffer sb = new StringBuffer(StringUtils.defaultIfEmpty(pAddress.getServiceBoundaryNum(), "0"));
		sb.append("^");
		sb.append(StringUtils.defaultIfEmpty(pAddress.getHouseLotNum(), " "));
		sb.append("^");
		sb.append(StringUtils.defaultIfEmpty(pAddress.getLotNum(), " "));
		sb.append("^");
		sb.append(StringUtils.defaultIfEmpty(pAddress.getBuildingName(), " "));
		sb.append("^");
		sb.append(StringUtils.defaultIfEmpty(pAddress.getStreetName(), " "));
		sb.append("^");
		sb.append(StringUtils.defaultIfEmpty(pAddress.getStreetCatgDesc(), " "));
		sb.append("^");
		sb.append(StringUtils.defaultIfEmpty(pAddress.getStreetCatgCode(), " "));
		sb.append("^");
		sb.append(StringUtils.defaultIfEmpty(pAddress.getSectionDesc(), " "));
		sb.append("^");
		sb.append(StringUtils.defaultIfEmpty(pAddress.getSectionCode(), " "));
		sb.append("^");
		sb.append(StringUtils.defaultIfEmpty(pAddress.getDistrictDesc(), " "));
		sb.append("^");
		sb.append(StringUtils.defaultIfEmpty(pAddress.getDistrictCode(), " "));
		sb.append("^");
		sb.append(StringUtils.defaultIfEmpty(pAddress.getAreaDesc(), " "));
		sb.append("^");
		sb.append(StringUtils.defaultIfEmpty(pAddress.getAreaCode(), " "));
		return sb.toString();
	}
}
