package com.bomltsportal.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSLockFactory;

import com.bomltsportal.dto.BuildingMarkerDTO;
import com.bomltsportal.dto.ListItemDTO;
import com.bomltsportal.dto.MarkerDTO;
import com.bomwebportal.exception.AppRuntimeException;

public class AddressLookupServiceImpl implements AddressLookupService {

	private static final int MAX_RESULT = 5000;
	private static final int QUICK_SEARCH_SHOW_ITEM = 10;

	protected final Log logger = LogFactory.getLog(getClass());

	private static final String IDX_LEVEL = "level";
	private static final String IDX_LAT = "lat";
	private static final String IDX_LNG = "lng";
	private static final String IDX_DESC_EN = "desc_en";
	private static final String IDX_DESC_CH = "desc_ch";
	private static final String IDX_RES_BB_IND = "res_bb";
	private static final String IDX_RES_TV_IND = "res_tv";
	private static final String IDX_BUS_BB_IND = "bus_bb";
	private static final String IDX_BUS_TV_IND = "bus_tv";
	private static final String IDX_RES_LAT = "res_lat";
	private static final String IDX_RES_LNG = "res_lng";
	private static final String IDX_BUS_LAT = "bus_lat";
	private static final String IDX_BUS_LNG = "bus_lng";
	private static final String IDX_RES_EYE_IND = "res_eye_ind";
	private static final String IDX_RES_EYE_PE_IND = "res_eye_pe_ind";
	private static final String IDX_RES_TEL_IND = "res_tel_ind";

	private static final String IDX_KEY = "key";
	private static final String IDX_SEARCH_LAT = "idx_lat";
	private static final String IDX_SEARCH_LNG = "idx_lng";

	private static final String IDX_NAME_EN = "name_en";
	private static final String IDX_NAME_CH = "name_ch";
	private static final String IDX_STREET_NUM = "street_num";
	private static final String IDX_STREET_NAME_EN = "street_name_en";
	private static final String IDX_STREET_NAME_CH = "street_name_ch";
	private static final String IDX_SECTION_DESC_EN = "section_desc_en";
	private static final String IDX_SECTION_DESC_CH = "section_desc_ch";
	private static final String IDX_DISTRICT_DESC_EN = "district_desc_en";
	private static final String IDX_DISTRICT_DESC_CH = "district_desc_ch";
	private static final String IDX_AREA_DESC_EN = "area_desc_en";
	private static final String IDX_AREA_DESC_CH = "area_desc_ch";
	private static final String IDX_BUILD_XY = "build_xy";
	private static final String IDX_SITE_GROUP = "site_group";
	private static final String IDX_SF_BLDG_RES = "sf_bldg_res";
	private static final String IDX_SF_BLDG_BUS = "sf_bldg_bus";
	private static final String IDX_SECT_CD = "sect_cd";
	private static final String IDX_DISTR_CD = "distr_cd";
	private static final String IDX_AREA_CD = "area_cd";
	private static final String IDX_RES_BASIC_BW = "res_basic_bw";
	private static final String IDX_RES_FTTB_BW = "res_fttb_bw";
	private static final String IDX_RES_FTTH_BW = "res_ftth_bw";
	private static final String IDX_RES_TV_SD = "res_tv_sd";
	private static final String IDX_RES_TV_HD = "res_tv_hd";
	private static final String IDX_BUS_BASIC_BW = "bus_basic_bw";
	private static final String IDX_BUS_FTTB_BW = "bus_fttb_bw";
	private static final String IDX_BUS_FTTH_BW = "bus_ftth_bw";
	private static final String IDX_BUS_TV_SD = "bus_tv_sd";
	private static final String IDX_BUS_TV_HD = "bus_tv_hd";
	private static final String IDX_IS_RM = "is_rm";
	private static final String IDX_IS_PREMIER = "is_premier";
	private static final String IDX_IS_SINGLE_BLDG = "is_single_bldg";
	private static final String IDX_HOUSING_ADDR_EN = "housing_addr_en";
	private static final String IDX_HOUSING_ADDR_CH = "housing_addr_ch";
	private static final String IDX_IS_PH = "is_ph";
	private static final String IDX_IS_HOS = "is_hos";
	private static final String IDX_RES_EYE = "res_eye";
	private static final String IDX_RES_EYE_PE = "res_eye_pe";
	private static final String IDX_RES_TEL = "res_tel";

	private static final String IDX_IS_CLEANED_VILLAGE = "is_cleaned_village";

	private static final String IDX_SEARCH_TYPE = "searchType";
	private static final String IDX_SEARCH_KEY = "searchKey";
	private static final String IDX_RESULT_KEY = "resultKey";
	private static final String IDX_RESULT_EN = "resultEn";
	private static final String IDX_RESULT_CH = "resultCh";
	private static final String IDX_DISPLAY_SEQ = "displaySeq";

	private static final String IDX_UNIQUE_KEY = "unique_key";

	private String idxFilePath;
	private String dataFilePath;

	private Directory leveldirectory;
	private Directory buildingdirectory;
	private Directory listdirectory;
	private Searcher levelsearcher;
	private Searcher buildingsearcher;
	private Searcher listsearcher;

	
	private static String STATIC_VERSION_NUMBER ="";
	
	public void checkVersionForCloseIndex(){
		logger.info("checkVersionForCloseIndex is called");
		try {
			if(!STATIC_VERSION_NUMBER.equals(getIndexDirectoryVersion())){
				closeIndexFile();
				logger.info("before STATIC_VERSION_NUMBER:"+STATIC_VERSION_NUMBER);
				STATIC_VERSION_NUMBER = getIndexDirectoryVersion();
				logger.info("after STATIC_VERSION_NUMBER:"+STATIC_VERSION_NUMBER);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
	}
	
	public void closeIndexFile() throws Exception {

		if (levelsearcher != null) {
			levelsearcher.close();
		}

		if (buildingsearcher != null) {
			buildingsearcher.close();
		}

		if (listsearcher != null) {
			listsearcher.close();
		}

		if (leveldirectory != null) {
			leveldirectory.close();
		}

		if (buildingdirectory != null) {
			buildingdirectory.close();
		}

		if (listdirectory != null) {
			listdirectory.close();
		}

		levelsearcher = null;
		buildingsearcher = null;
		listsearcher = null;

		leveldirectory = null;
		buildingdirectory = null;
		listdirectory = null;

		logger.info("Close Index File");
	}

	public ListItemDTO[] getListItem(String type, String key) {

		try {

			ScoreDoc[] topDocs = getListByType(getListIndexSearcher(), type,
					key);

			List<ListItemDTO> rtnList = new ArrayList<ListItemDTO>();

			for (int i = 0; topDocs != null && i < topDocs.length
					&& i < MAX_RESULT; i++) {
				rtnList.add(createListItem(listsearcher.doc(topDocs[i].doc)));
			}

			return (ListItemDTO[]) rtnList.toArray(new ListItemDTO[0]);

		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getMessage());
		}
	}

	public BuildingMarkerDTO[] searchBuildingByRange(String latLower,
			String latUpper, String lngLower, String lngUpper) {

		try {

			ScoreDoc[] topDocs = getBuildingHitsByRange(
					getBuildingIndexSearcher(), Float.parseFloat(latLower),
					Float.parseFloat(latUpper), Float.parseFloat(lngLower),
					Float.parseFloat(lngUpper));

			List<BuildingMarkerDTO> rtnList = new ArrayList<BuildingMarkerDTO>();

			for (int i = 0; topDocs != null && i < topDocs.length
					&& i < MAX_RESULT; i++) {
				rtnList.add(createBuildingMarker(buildingsearcher
						.doc(topDocs[i].doc)));
			}

			return (BuildingMarkerDTO[]) rtnList
					.toArray(new BuildingMarkerDTO[0]);

		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getMessage());
		}

	}

	public ListItemDTO getListItemByKey(String key) {

		try {

			ScoreDoc[] topDocs = getListHitsByKey(getListIndexSearcher(), key);

			if (ArrayUtils.isEmpty(topDocs)) {
				return null;
			}

			return createListItem(listsearcher.doc(topDocs[0].doc));

		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getMessage());
		}

	}

	public BuildingMarkerDTO getBuildingMarkerDetailByKey(String key) {

		try {

			ScoreDoc[] topDocs = getBuildingHitsByKey(
					getBuildingIndexSearcher(), key);

			if (ArrayUtils.isEmpty(topDocs)) {
				return null;
			}

			return createBuildingMarker(buildingsearcher.doc(topDocs[0].doc));

		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getMessage());
		}
	}

	public BuildingMarkerDTO[] searchBuilding(String searchString) {

		try {

			ScoreDoc[] topDocs = getBuildHits(getBuildingIndexSearcher(),
					searchString.toUpperCase());

			List<MarkerDTO> rtnList = new ArrayList<MarkerDTO>(
					QUICK_SEARCH_SHOW_ITEM + 1);

			for (int i = 0; topDocs != null && i < topDocs.length
					&& i < QUICK_SEARCH_SHOW_ITEM; i++) {

				rtnList.add(createBuildingMarker(buildingsearcher
						.doc(topDocs[i].doc)));
			}

			return ((BuildingMarkerDTO[]) rtnList
					.toArray(new BuildingMarkerDTO[0]));

		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getMessage());
		}
	}

	public MarkerDTO[] getMarkerByLevel(String level, String latLower,
			String latUpper, String lngLower, String lngUpper) {

		if (StringUtils.isBlank(level)) {
			return new MarkerDTO[0];
		}

		float latMin = 0, latMax = 0, lngMin = 0, lngMax = 0;

		if (Integer.parseInt(level) > 2) {
			latMin = Float.parseFloat(latLower);
			latMax = Float.parseFloat(latUpper);
			lngMin = Float.parseFloat(lngLower);
			lngMax = Float.parseFloat(lngUpper);
		}

		try {

			ScoreDoc[] topDocs = getHitsByLevel(getLevelIndexSearcher(), level,
					latMin, latMax, lngMin, lngMax);

			List<MarkerDTO> rtnList = new ArrayList<MarkerDTO>(MAX_RESULT + 1);

			for (int i = 0; topDocs != null && i < topDocs.length
					&& i < MAX_RESULT; i++) {
				rtnList.add(createMarker(levelsearcher.doc(topDocs[i].doc)));
			}

			return (MarkerDTO[]) rtnList.toArray(new MarkerDTO[0]);

		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getMessage());
		}
	}

	private ListItemDTO createListItem(Document document) {

		if (document == null) {
			return null;
		}

		ListItemDTO dto = new ListItemDTO();

		dto.setSearchType(document.get(IDX_SEARCH_TYPE));
		dto.setSearchKey(document.get(IDX_SEARCH_KEY));
		dto.setResultKey(document.get(IDX_RESULT_KEY));
		dto.setResultEn(document.get(IDX_RESULT_EN));
		dto.setResultCh(document.get(IDX_RESULT_CH));
		dto.setDisplaySeq(document.get(IDX_DISPLAY_SEQ));
		dto.setLat(document.get(IDX_LAT));
		dto.setLng(document.get(IDX_LNG));
		dto.setIndexKey(document.get(IDX_UNIQUE_KEY));

		return dto;
	}

	private MarkerDTO createMarker(Document document) {

		if (document == null) {
			return null;
		}

		MarkerDTO dto = new MarkerDTO();

		dto.setLevel(document.get(IDX_LEVEL));
		dto.setLat(document.get(IDX_LAT));
		dto.setLng(document.get(IDX_LNG));
		dto.setDescCh(document.get(IDX_DESC_CH));
		dto.setDescEn(document.get(IDX_DESC_EN));
		dto.setResBbInd(document.get(IDX_RES_BB_IND));
		dto.setResTvInd(document.get(IDX_RES_TV_IND));
		dto.setBusBbInd(document.get(IDX_BUS_BB_IND));
		dto.setBusTvInd(document.get(IDX_BUS_TV_IND));
		dto.setResLat(document.get(IDX_RES_LAT));
		dto.setResLng(document.get(IDX_RES_LNG));
		dto.setBusLat(document.get(IDX_BUS_LAT));
		dto.setBusLng(document.get(IDX_BUS_LNG));
		dto.setResEyeInd(document.get(IDX_RES_EYE_IND));
		dto.setResEyePeInd(document.get(IDX_RES_EYE_PE_IND));
		dto.setResTelInd(document.get(IDX_RES_TEL_IND));

		return dto;
	}

	private BuildingMarkerDTO createBuildingMarker(Document document) {

		if (document == null) {
			return null;
		}

		BuildingMarkerDTO dto = new BuildingMarkerDTO();

		dto.setIndexKey(document.get(IDX_UNIQUE_KEY));
		dto.setLat(document.get(IDX_LAT));
		dto.setLng(document.get(IDX_LNG));
		dto.setBldgNameEn(document.get(IDX_NAME_EN));
		dto.setBldgNameCh(document.get(IDX_NAME_CH));
		dto.setStreetNum(document.get(IDX_STREET_NUM));
		dto.setStreetNameEn(document.get(IDX_STREET_NAME_EN));
		dto.setStreetNameCh(document.get(IDX_STREET_NAME_CH));
		dto.setSectDescEn(document.get(IDX_SECTION_DESC_EN));
		dto.setSectDescCh(document.get(IDX_SECTION_DESC_CH));
		dto.setDistDescEn(document.get(IDX_DISTRICT_DESC_EN));
		dto.setDistDescCh(document.get(IDX_DISTRICT_DESC_CH));
		dto.setAreaDescEn(document.get(IDX_AREA_DESC_EN));
		dto.setAreaDescCh(document.get(IDX_AREA_DESC_CH));
		dto.setHousingAddrEn(document.get(IDX_HOUSING_ADDR_EN));
		dto.setHousingAddrCh(document.get(IDX_HOUSING_ADDR_CH));
		dto.setBuildXy(document.get(IDX_BUILD_XY));
		dto.setSiteGroup(document.get(IDX_SITE_GROUP));
		dto.setSfBldgRes(document.get(IDX_SF_BLDG_RES));
		dto.setSfBldgBus(document.get(IDX_SF_BLDG_BUS));
		dto.setSectCd(document.get(IDX_SECT_CD));
		dto.setDistrCd(document.get(IDX_DISTR_CD));
		dto.setAreaCd(document.get(IDX_AREA_CD));
		dto.setResBasicBw(document.get(IDX_RES_BASIC_BW));
		dto.setResFttbBw(document.get(IDX_RES_FTTB_BW));
		dto.setResFtthBw(document.get(IDX_RES_FTTH_BW));
		dto.setResTvSd(document.get(IDX_RES_TV_SD));
		dto.setResTvHd(document.get(IDX_RES_TV_HD));
		dto.setBusBasicBw(document.get(IDX_BUS_BASIC_BW));
		dto.setBusFttbBw(document.get(IDX_BUS_FTTB_BW));
		dto.setBusFtthBw(document.get(IDX_BUS_FTTH_BW));
		dto.setBusTvSd(document.get(IDX_BUS_TV_SD));
		dto.setBusTvHd(document.get(IDX_BUS_TV_HD));
		dto.setIsRm(document.get(IDX_IS_RM));
		dto.setIsPremier(document.get(IDX_IS_PREMIER));
		dto.setisPh(document.get(IDX_IS_PH));
		dto.setisHos(document.get(IDX_IS_HOS));
		dto.setIsCleanedVillage(document.get(IDX_IS_CLEANED_VILLAGE));
		dto.setResEyeInd(document.get(IDX_RES_EYE));
		dto.setResEyePeInd(document.get(IDX_RES_EYE_PE));
		dto.setResTelInd(document.get(IDX_RES_TEL));
		return dto;
	}

	private String getIndexDirectoryVersion() throws Exception {
		String version = "";

		try {
			BufferedReader in = new BufferedReader(new FileReader(dataFilePath
					+ "online_sales_version.ini"));
			version = in.readLine().trim().substring(0, 1);
			in.close();
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return version;
	}

	private Searcher getLevelIndexSearcher() throws Exception {
		checkVersionForCloseIndex();
		try {
			if (levelsearcher == null) {
				logger.info("Initialize Searcher object");
				levelsearcher = new IndexSearcher(getLevelIndexDirectory(),
						true);
			}
			logger.debug(">> Directory=="
					+ (leveldirectory == null ? "NULL" : leveldirectory
							.toString()));
		} catch (Exception e) {
			logger.error(e);
		}
		return levelsearcher;
	}

	private Searcher getListIndexSearcher() throws Exception {
		checkVersionForCloseIndex();
		try {
			if (listsearcher == null) {
				logger.info("Initialize Searcher object");
				listsearcher = new IndexSearcher(getListIndexDirectory(), true);
			}
			logger.debug(">> Directory=="
					+ (listdirectory == null ? "NULL" : listdirectory
							.toString()));
		} catch (Exception e) {
			logger.error(e);
		}
		return listsearcher;
	}

	private Searcher getBuildingIndexSearcher() throws Exception {
		
		checkVersionForCloseIndex();
		try {
			if (buildingsearcher == null) {
				logger.info("Initialize Searcher object");
				buildingsearcher = new IndexSearcher(
						getBuildingIndexDirectory(), true);
			}
			logger.debug(">> Directory=="
					+ (buildingdirectory == null ? "NULL" : buildingdirectory
							.toString()));
		} catch (Exception e) {
			logger.error(e);
		}
		return buildingsearcher;
	}

	public Directory getBuildingIndexDirectory() throws Exception {
		try {
			if (buildingdirectory == null) {
				logger.info("Open index file directory");

				String version = getIndexDirectoryVersion();
				logger.info("version == " + version);

				buildingdirectory = FSDirectory.open(new File(idxFilePath
						+ "_v" + version + "/bldg"), new SimpleFSLockFactory());
			}
			return buildingdirectory;
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}

	public Directory getListIndexDirectory() throws Exception {
		try {
			if (listdirectory == null) {
				logger.info("Open index file directory");

				String version = getIndexDirectoryVersion();
				logger.info("version == " + version);

				listdirectory = FSDirectory.open(new File(idxFilePath + "_v"
						+ version + "/list"), new SimpleFSLockFactory());
			}
			return listdirectory;
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}

	public Directory getLevelIndexDirectory() throws Exception {
		try {
			if (leveldirectory == null) {

				String version = getIndexDirectoryVersion();
				logger.info("version == " + version);

				logger.info("Open index file directory");
				leveldirectory = FSDirectory.open(new File(idxFilePath + "_v"
						+ version + "/level"), new SimpleFSLockFactory());
			}
			return leveldirectory;
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}

	private ScoreDoc[] getBuildHits(Searcher pSearcher, String pSearchString)
			throws Exception {
		StringTokenizer st = new StringTokenizer(pSearchString, ",");
		ArrayList<String> searchPhraseList = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			searchPhraseList.add(st.nextToken());
		}

		TopDocs topDocs = getBuildingMatches(pSearcher, searchPhraseList);
		if (topDocs.totalHits > 0) {
			return topDocs.scoreDocs;
		}
		return null;
	}

	private ScoreDoc[] getListByType(Searcher pSearcher, String type, String key)
			throws Exception {
		Sort sort = new Sort(new SortField(IDX_DISPLAY_SEQ, SortField.INT));
		BooleanQuery query = new BooleanQuery();
		PhraseQuery pq1 = new PhraseQuery();

		pq1.add(new Term(IDX_SEARCH_TYPE, type));
		query.add(pq1, BooleanClause.Occur.MUST);

		if (key != null && key.length() > 0) {
			PhraseQuery pq2 = new PhraseQuery();
			pq2.add(new Term(IDX_SEARCH_KEY, key));
			query.add(pq2, BooleanClause.Occur.MUST);
		}

		Date startTime = new Date();
		TopDocs matches = pSearcher.search(query, null, MAX_RESULT, sort);
		Date endTime = new Date();
		System.out.println("getListByType "
				+ (endTime.getTime() - startTime.getTime())
				+ " (ms) used with " + matches.totalHits + " type:" + type
				+ " key:" + key);
		return matches.scoreDocs;

	}

	private ScoreDoc[] getListHitsByKey(Searcher pSearcher, String key)
			throws Exception {
		TermQuery query = new TermQuery(new Term(IDX_UNIQUE_KEY, key));

		Date startTime = new Date();
		TopDocs matches = pSearcher.search(query, MAX_RESULT);
		Date endTime = new Date();
		System.out.println("getListHitsByKey "
				+ (endTime.getTime() - startTime.getTime())
				+ " (ms) used with " + matches.totalHits + " key=" + key);
		return matches.scoreDocs;

	}

	private ScoreDoc[] getBuildingHitsByKey(Searcher pSearcher, String key)
			throws Exception {
		TermQuery query = new TermQuery(new Term(IDX_UNIQUE_KEY, key));

		Date startTime = new Date();
		TopDocs matches = pSearcher.search(query, MAX_RESULT);
		Date endTime = new Date();
		System.out.println("getBuildingHitsByKey "
				+ (endTime.getTime() - startTime.getTime())
				+ " (ms) used with " + matches.totalHits + " key=" + key);
		return matches.scoreDocs;
	}

	private ScoreDoc[] getBuildingHitsByRange(Searcher pSearcher, float latMin,
			float latMax, float lngMin, float lngMax) throws Exception {
		BooleanQuery query = new BooleanQuery();

		Query qLat = NumericRangeQuery.newFloatRange(IDX_SEARCH_LAT, latMin,
				latMax, true, true);
		Query qLng = NumericRangeQuery.newFloatRange(IDX_SEARCH_LNG, lngMin,
				lngMax, true, true);

		query.add(qLat, BooleanClause.Occur.MUST);
		query.add(qLng, BooleanClause.Occur.MUST);

		Date startTime = new Date();
		TopDocs matches = pSearcher.search(query, MAX_RESULT);
		Date endTime = new Date();
		System.out.println("getBuildingHitsByRange "
				+ (endTime.getTime() - startTime.getTime())
				+ " (ms) used with " + matches.totalHits + " lat(" + latMin
				+ "," + latMax + ")" + " lng(" + lngMin + "," + lngMax + ")");
		return matches.scoreDocs;

	}

	private ScoreDoc[] getHitsByLevel(Searcher pSearcher, String level,
			float latMin, float latMax, float lngMin, float lngMax)
			throws Exception {

		BooleanQuery query = new BooleanQuery();

		PhraseQuery q = new PhraseQuery();

		q.add(new Term(IDX_LEVEL, level));

		query.add(q, BooleanClause.Occur.MUST);

		if (latMin != 0 || latMax != 0 || lngMin != 0 || lngMax != 0) {
			Query qLat = NumericRangeQuery.newFloatRange(IDX_SEARCH_LAT,
					latMin, latMax, true, true);
			Query qLng = NumericRangeQuery.newFloatRange(IDX_SEARCH_LNG,
					lngMin, lngMax, true, true);

			query.add(qLat, BooleanClause.Occur.MUST);
			query.add(qLng, BooleanClause.Occur.MUST);

		}

		Date startTime = new Date();
		TopDocs matches = pSearcher.search(query, MAX_RESULT);
		Date endTime = new Date();
		System.out.println("SearchAddress "
				+ (endTime.getTime() - startTime.getTime())
				+ " (ms) used with " + matches.totalHits
				+ " total matching - level:" + level + " lat(" + latMin + ","
				+ latMax + ")" + " lng(" + lngMin + "," + lngMax + ")");
		return matches.scoreDocs;
	}

	/*
	 * private ScoreDoc[] getHits(Searcher pSearcher, String pSearchString)
	 * throws Exception { StringTokenizer st = new
	 * StringTokenizer(pSearchString, " "); ArrayList<String> searchPhraseList =
	 * new ArrayList<String>(); while (st.hasMoreTokens()) {
	 * searchPhraseList.add(st.nextToken()); }
	 * 
	 * TopDocs topDocs = getMatches(pSearcher, searchPhraseList); if
	 * (topDocs.totalHits > 0) { return topDocs.scoreDocs; } return null; }
	 */

	private TopDocs getBuildingMatches(Searcher pSearcher,
			ArrayList<String> pSearchPhraseList) throws Exception {

		StringBuffer sb = new StringBuffer();
		BooleanQuery query = new BooleanQuery();

		for (String searchPhrase : pSearchPhraseList) {
			Query q = new WildcardQuery(new Term(IDX_KEY, "*"
					+ searchPhrase.trim().replace(" ", "^") + "*"));
			query.add(q, BooleanClause.Occur.MUST);

			sb.append(searchPhrase);
		}

		Date startTime = new Date();
		TopDocs matches = pSearcher.search(query, MAX_RESULT);
		Date endTime = new Date();
		System.out.println("SearchAddress "
				+ (endTime.getTime() - startTime.getTime())
				+ " (ms) used with " + matches.totalHits + " total matching - "
				+ sb.toString());
		return matches;
	}

	/*
	 * private TopDocs getMatches(Searcher pSearcher, ArrayList<String>
	 * pSearchPhraseList) throws Exception { PhraseQuery query = new
	 * PhraseQuery(); query.setSlop(0); StringBuffer sb = new StringBuffer();
	 * for (String searchPhrase : pSearchPhraseList) { query.add(new
	 * Term(IDX_LEVEL, searchPhrase)); sb.append(searchPhrase); sb.append(" ");
	 * }
	 * 
	 * Date startTime = new Date(); TopDocs matches = pSearcher.search(query,
	 * MAX_RESULT); Date endTime = new Date(); logger.info("SearchAddress " +
	 * (endTime.getTime() - startTime.getTime()) + " (ms) used with " +
	 * matches.totalHits + " total matching - " + sb.toString()); return
	 * matches; }
	 */

	public Document genListDocumnet(ListItemDTO dto) throws Exception {

		Document doc = new Document();

		doc.add(new Field(IDX_UNIQUE_KEY, dto.getIndexKey(), Field.Store.YES,
				Field.Index.NO));

		doc.add(new Field(IDX_SEARCH_TYPE, dto.getSearchType(),
				Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(IDX_SEARCH_KEY, dto.getSearchKey(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		if (dto.getResultKey() != null)
			doc.add(new Field(IDX_RESULT_KEY, dto.getResultKey(),
					Field.Store.YES, Field.Index.NO));
		if (dto.getResultEn() != null)
			doc.add(new Field(IDX_RESULT_EN, dto.getResultEn(),
					Field.Store.YES, Field.Index.NO));
		if (dto.getResultCh() != null)
			doc.add(new Field(IDX_RESULT_CH, dto.getResultCh(),
					Field.Store.YES, Field.Index.NO));
		doc.add(new NumericField(IDX_DISPLAY_SEQ, Field.Store.YES, true)
				.setIntValue(Integer.parseInt(dto.getDisplaySeq())));
		if (dto.getLat() != null)
			doc.add(new Field(IDX_LAT, dto.getLat(), Field.Store.YES,
					Field.Index.NO));
		if (dto.getLng() != null)
			doc.add(new Field(IDX_LNG, dto.getLng(), Field.Store.YES,
					Field.Index.NO));

		return doc;
	}

	public Document genBuildMarkerDocument(BuildingMarkerDTO dto)
			throws Exception {

		Document doc = new Document();

		doc.add(new Field(IDX_UNIQUE_KEY, dto.getIndexKey(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));

		doc.add(new Field(IDX_KEY, getBuildingSearchString(dto),
				Field.Store.YES, Field.Index.ANALYZED));

		if (dto.getLat() != null)
			doc.add(new Field(IDX_LAT, dto.getLat(), Field.Store.YES,
					Field.Index.NO));
		if (dto.getLng() != null)
			doc.add(new Field(IDX_LNG, dto.getLng(), Field.Store.YES,
					Field.Index.NO));
		if (dto.getBldgNameEn() != null)
			doc.add(new Field(IDX_NAME_EN, dto.getBldgNameEn(),
					Field.Store.YES, Field.Index.NO));
		if (dto.getBldgNameCh() != null)
			doc.add(new Field(IDX_NAME_CH, dto.getBldgNameCh(),
					Field.Store.YES, Field.Index.NO));
		if (dto.getStreetNum() != null)
			doc.add(new Field(IDX_STREET_NUM, dto.getStreetNum(),
					Field.Store.YES, Field.Index.NO));
		if (dto.getStreetNameEn() != null)
			doc.add(new Field(IDX_STREET_NAME_EN, dto.getStreetNameEn(),
					Field.Store.YES, Field.Index.NO));
		if (dto.getStreetNameCh() != null)
			doc.add(new Field(IDX_STREET_NAME_CH, dto.getStreetNameCh(),
					Field.Store.YES, Field.Index.NO));
		if (dto.getSectDescEn() != null)
			doc.add(new Field(IDX_SECTION_DESC_EN, dto.getSectDescEn(),
					Field.Store.YES, Field.Index.NO));
		if (dto.getSectDescCh() != null)
			doc.add(new Field(IDX_SECTION_DESC_CH, dto.getSectDescCh(),
					Field.Store.YES, Field.Index.NO));
		if (dto.getDistDescEn() != null)
			doc.add(new Field(IDX_DISTRICT_DESC_EN, dto.getDistDescEn(),
					Field.Store.YES, Field.Index.NO));
		if (dto.getDistDescCh() != null)
			doc.add(new Field(IDX_DISTRICT_DESC_CH, dto.getDistDescCh(),
					Field.Store.YES, Field.Index.NO));
		if (dto.getAreaDescEn() != null)
			doc.add(new Field(IDX_AREA_DESC_EN, dto.getAreaDescEn(),
					Field.Store.YES, Field.Index.NO));
		if (dto.getAreaDescCh() != null)
			doc.add(new Field(IDX_AREA_DESC_CH, dto.getAreaDescCh(),
					Field.Store.YES, Field.Index.NO));
		if (dto.getBuildXy() != null)
			doc.add(new Field(IDX_BUILD_XY, dto.getBuildXy(), Field.Store.YES,
					Field.Index.NO));
		if (dto.getSiteGroup() != null)
			doc.add(new Field(IDX_SITE_GROUP, dto.getSiteGroup(),
					Field.Store.YES, Field.Index.NO));
		if (dto.getSfBldgRes() != null)
			doc.add(new Field(IDX_SF_BLDG_RES, dto.getSfBldgRes(),
					Field.Store.YES, Field.Index.NO));
		if (dto.getSfBldgBus() != null)
			doc.add(new Field(IDX_SF_BLDG_BUS, dto.getSfBldgBus(),
					Field.Store.YES, Field.Index.NO));
		if (dto.getSectCd() != null)
			doc.add(new Field(IDX_SECT_CD, dto.getSectCd(), Field.Store.YES,
					Field.Index.NO));
		if (dto.getDistrCd() != null)
			doc.add(new Field(IDX_DISTR_CD, dto.getDistrCd(), Field.Store.YES,
					Field.Index.NO));
		if (dto.getAreaCd() != null)
			doc.add(new Field(IDX_AREA_CD, dto.getAreaCd(), Field.Store.YES,
					Field.Index.NO));
		if (dto.getResBasicBw() != null)
			doc.add(new Field(IDX_RES_BASIC_BW, dto.getResBasicBw(),
					Field.Store.YES, Field.Index.NO));
		if (dto.getResFttbBw() != null)
			doc.add(new Field(IDX_RES_FTTB_BW, dto.getResFttbBw(),
					Field.Store.YES, Field.Index.NO));
		if (dto.getResFtthBw() != null)
			doc.add(new Field(IDX_RES_FTTH_BW, dto.getResFtthBw(),
					Field.Store.YES, Field.Index.NO));
		if (dto.getResTvSd() != null)
			doc.add(new Field(IDX_RES_TV_SD, dto.getResTvSd(), Field.Store.YES,
					Field.Index.NO));
		if (dto.getResTvHd() != null)
			doc.add(new Field(IDX_RES_TV_HD, dto.getResTvHd(), Field.Store.YES,
					Field.Index.NO));
		if (dto.getBusBasicBw() != null)
			doc.add(new Field(IDX_BUS_BASIC_BW, dto.getBusBasicBw(),
					Field.Store.YES, Field.Index.NO));
		if (dto.getBusFttbBw() != null)
			doc.add(new Field(IDX_BUS_FTTB_BW, dto.getBusFttbBw(),
					Field.Store.YES, Field.Index.NO));
		if (dto.getBusFtthBw() != null)
			doc.add(new Field(IDX_BUS_FTTH_BW, dto.getBusFtthBw(),
					Field.Store.YES, Field.Index.NO));
		if (dto.getBusTvSd() != null)
			doc.add(new Field(IDX_BUS_TV_SD, dto.getBusTvSd(), Field.Store.YES,
					Field.Index.NO));
		if (dto.getBusTvHd() != null)
			doc.add(new Field(IDX_BUS_TV_HD, dto.getBusTvHd(), Field.Store.YES,
					Field.Index.NO));
		if (dto.getIsRm() != null)
			doc.add(new Field(IDX_IS_RM, dto.getIsRm(), Field.Store.YES,
					Field.Index.NO));
		if (dto.getIsPremier() != null)
			doc.add(new Field(IDX_IS_PREMIER, dto.getIsPremier(),
					Field.Store.YES, Field.Index.NO));
		if (dto.getHousingAddrEn() != null)
			doc.add(new Field(IDX_HOUSING_ADDR_EN, dto.getHousingAddrEn(),
					Field.Store.YES, Field.Index.NO));
		if (dto.getHousingAddrCh() != null)
			doc.add(new Field(IDX_HOUSING_ADDR_CH, dto.getHousingAddrCh(),
					Field.Store.YES, Field.Index.NO));
		if (dto.getisPh() != null)
			doc.add(new Field(IDX_IS_PH, dto.getisPh(), Field.Store.YES,
					Field.Index.NO));
		if (dto.getisHos() != null)
			doc.add(new Field(IDX_IS_HOS, dto.getisHos(), Field.Store.YES,
					Field.Index.NO));

		if (dto.getIsCleanedVillage() != null) // added on 20121221 by Eric Ng
			doc.add(new Field(IDX_IS_CLEANED_VILLAGE,
					dto.getIsCleanedVillage(), Field.Store.YES, Field.Index.NO));

		doc.add(new NumericField(IDX_SEARCH_LAT).setFloatValue(Float
				.parseFloat(dto.getLat())));
		doc.add(new NumericField(IDX_SEARCH_LNG).setFloatValue(Float
				.parseFloat(dto.getLng())));

		return doc;

	}

	public Document genMarkerDocument(MarkerDTO dto) throws Exception {

		Document doc = new Document();

		doc.add(new Field(IDX_LEVEL, dto.getLevel(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field(IDX_LAT, dto.getLat(), Field.Store.YES,
				Field.Index.NO));
		doc.add(new Field(IDX_LNG, dto.getLng(), Field.Store.YES,
				Field.Index.NO));
		doc.add(new Field(IDX_DESC_CH, dto.getDescCh(), Field.Store.YES,
				Field.Index.NO));
		doc.add(new Field(IDX_DESC_EN, dto.getDescEn(), Field.Store.YES,
				Field.Index.NO));
		doc.add(new Field(IDX_RES_BB_IND, dto.getResBbInd(), Field.Store.YES,
				Field.Index.NO));
		doc.add(new Field(IDX_RES_TV_IND, dto.getResTvInd(), Field.Store.YES,
				Field.Index.NO));
		doc.add(new Field(IDX_BUS_BB_IND, dto.getBusBbInd(), Field.Store.YES,
				Field.Index.NO));
		doc.add(new Field(IDX_BUS_TV_IND, dto.getBusTvInd(), Field.Store.YES,
				Field.Index.NO));
		doc.add(new NumericField(IDX_SEARCH_LAT).setFloatValue(Float
				.parseFloat(dto.getLat())));
		doc.add(new NumericField(IDX_SEARCH_LNG).setFloatValue(Float
				.parseFloat(dto.getLng())));
		doc.add(new Field(IDX_RES_LAT, dto.getResLat(), Field.Store.YES,
				Field.Index.NO));
		doc.add(new Field(IDX_RES_LNG, dto.getResLng(), Field.Store.YES,
				Field.Index.NO));
		doc.add(new Field(IDX_BUS_LAT, dto.getBusLat(), Field.Store.YES,
				Field.Index.NO));
		doc.add(new Field(IDX_BUS_LNG, dto.getBusLng(), Field.Store.YES,
				Field.Index.NO));

		return doc;
	}

	private String getBuildingSearchString(BuildingMarkerDTO dto)
			throws Exception {
		StringBuffer sb = new StringBuffer();

		if (StringUtils.isNotBlank(dto.getBldgNameEn())) {
			sb.append(dto.getBldgNameEn().replaceAll(" ", "^"));
			sb.append(" ");
		}

		if (StringUtils.isNotBlank(dto.getBldgNameCh())) {
			sb.append(dto.getBldgNameCh().replaceAll(" ", "^"));
			sb.append(" ");
		}

		// handle for searching "street number street name" or
		// "street name street number"
		if (StringUtils.isNotBlank(dto.getStreetNum())
				&& StringUtils.isNotBlank(dto.getStreetNameEn())) {

			sb.append(dto.getStreetNum().replaceAll(" ", "^"));
			sb.append("^" + dto.getStreetNameEn().replaceAll(" ", "^") + "^");
			sb.append(dto.getStreetNum().replaceAll(" ", "^"));
			sb.append(" ");

		} else {

			if (StringUtils.isNotBlank(dto.getStreetNum())) {
				sb.append(dto.getStreetNum().replaceAll(" ", "^"));
				sb.append(" ");
			}

			if (StringUtils.isNotBlank(dto.getStreetNameEn())) {
				sb.append(dto.getStreetNameEn().replaceAll(" ", "^"));
				sb.append(" ");
			}

		}

		if (StringUtils.isNotBlank(dto.getStreetNum())
				&& StringUtils.isNotBlank(dto.getStreetNameCh())) {

			sb.append(dto.getStreetNum().replaceAll(" ", "^"));
			sb.append("^" + dto.getStreetNameCh().replaceAll(" ", "^") + "^");
			sb.append(dto.getStreetNum().replaceAll(" ", "^"));
			sb.append(" ");

		} else {

			if (StringUtils.isNotBlank(dto.getStreetNameCh())) {
				sb.append(dto.getStreetNameCh().replaceAll(" ", "^"));
				sb.append(" ");
			}

		}

		if (StringUtils.isNotBlank(dto.getSectDescEn())) {
			sb.append(dto.getSectDescEn().replaceAll(" ", "^"));
			sb.append(" ");
		}

		if (StringUtils.isNotBlank(dto.getSectDescCh())) {
			sb.append(dto.getSectDescCh().replaceAll(" ", "^"));
			sb.append(" ");
		}

		if (StringUtils.isNotBlank(dto.getDistDescEn())) {
			sb.append(dto.getDistDescEn().replaceAll(" ", "^"));
			sb.append(" ");
		}

		if (StringUtils.isNotBlank(dto.getDistDescCh())) {
			sb.append(dto.getDistDescCh().replaceAll(" ", "^"));
			sb.append(" ");
		}
		/*
		 * if(StringUtils.isNotBlank(dto.getAreaDescEn())){
		 * sb.append(dto.getAreaDescEn().replaceAll(" ", "^")); sb.append(" ");
		 * }
		 * 
		 * if(StringUtils.isNotBlank(dto.getAreaDescCh())){
		 * sb.append(dto.getAreaDescCh().replaceAll(" ", "^")); sb.append(" ");
		 * }
		 */

		if (StringUtils.isNotBlank(dto.getExtNameEn())) {
			sb.append(dto.getExtNameEn());
			sb.append(" ");
		}

		if (StringUtils.isNotBlank(dto.getExtNameCh())) {
			sb.append(dto.getExtNameCh());
			sb.append(" ");
		}

		return sb.toString();
	}

	public String getIdxFilePath() {
		return idxFilePath;
	}

	public void setIdxFilePath(String idxFilePath) {
		this.idxFilePath = idxFilePath;
	}

	public String getDataFilePath() {
		return dataFilePath;
	}

	public void setDataFilePath(String dataFilePath) {
		this.dataFilePath = dataFilePath;
	}

}
