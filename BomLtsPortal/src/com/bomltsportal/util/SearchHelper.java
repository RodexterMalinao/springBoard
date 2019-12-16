package com.bomltsportal.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.StringTokenizer;

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
import com.bomltsportal.dto.SearchAddressResultDTO;

public class SearchHelper {
	
	
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
	
	
	private static int MAX_RESULT = 5000;
	private static int QUICK_SEARCH_SHOW_ITEM = 10;
	
	protected static final Log logger = LogFactory.getLog(SearchHelper.class);
	
	private String idxFilePath;	
	private String dataFilePath;

	private Directory leveldirectory;
	private Directory buildingdirectory;
	private Directory listdirectory;
	private Searcher levelsearcher;
	private Searcher buildingsearcher;
	private Searcher listsearcher;
	
	public void closeIndexFile() throws Exception{
		
		if(levelsearcher!=null) levelsearcher.close();
		if(buildingsearcher!=null) buildingsearcher.close();
		if(listsearcher!=null) listsearcher.close();						
								
		if(leveldirectory!=null) leveldirectory.close();			
		if(buildingdirectory!=null) buildingdirectory.close();
		if(listdirectory!=null) listdirectory.close();
						
		levelsearcher = null;
		buildingsearcher = null;
		listsearcher = null;			
			
		leveldirectory = null;
		buildingdirectory = null;
		listdirectory = null;
			
		logger.info("Close Index File");
			
	}
	
	public ListItemDTO[] getListItem(String type, String key){
		ArrayList<ListItemDTO> rtnList = new ArrayList<ListItemDTO>(MAX_RESULT + 1);
		Date startTime = new Date();
		
		try{						
			
			ScoreDoc[] topDocs = getListByType(getListIndexSearcher(), type, key);
			if (topDocs == null) {
				return new ListItemDTO[0];				
			}						
			
			Document doc = null;			
			for (int i = 0; topDocs != null && i < topDocs.length; i++) {
				doc = listsearcher.doc(topDocs[i].doc);
				ListItemDTO dto = new ListItemDTO();								
								
				dto.setSearchType(doc.get(BomLtsConstant.IDX_SEARCH_TYPE));
				dto.setSearchKey(doc.get(BomLtsConstant.IDX_SEARCH_KEY));
				dto.setResultKey(doc.get(BomLtsConstant.IDX_RESULT_KEY));
				dto.setResultEn(doc.get(BomLtsConstant.IDX_RESULT_EN));
				dto.setResultCh(doc.get(BomLtsConstant.IDX_RESULT_CH));
				dto.setDisplaySeq(doc.get(BomLtsConstant.IDX_DISPLAY_SEQ));
				dto.setLat(doc.get(BomLtsConstant.IDX_LAT));
				dto.setLng(doc.get(BomLtsConstant.IDX_LNG));
				dto.setIndexKey(doc.get(BomLtsConstant.IDX_UNIQUE_KEY));
				
				
				rtnList.add(dto);				
				if (rtnList.size() >= MAX_RESULT) {
					break;
				}
			}						
						
		}catch (Exception e){
			e.printStackTrace();
		}
		
		Date endTime = new Date();
		logger.debug("getListItem "
				+ (endTime.getTime() - startTime.getTime())
				+ " (ms) used");
		return (ListItemDTO[]) rtnList.toArray(new ListItemDTO[0]);
	}
	
	public BuildingMarkerDTO[] searchBuildingByRange(
			String latLower, String latUpper, String lngLower, String lngUpper){
		
		ArrayList<BuildingMarkerDTO> rtnList = new ArrayList<BuildingMarkerDTO>(MAX_RESULT + 1);
		Date startTime = new Date();
		
		try{						
			
			ScoreDoc[] topDocs = getBuildingHitsByRange(getBuildingIndexSearcher(), 
					Float.parseFloat(latLower), 
					Float.parseFloat(latUpper), 
					Float.parseFloat(lngLower), 
					Float.parseFloat(lngUpper));
			if (topDocs == null) {
				return new BuildingMarkerDTO[0];				
			}						
			
			Document doc = null;			
			for (int i = 0; topDocs != null && i < topDocs.length; i++) {
				doc = buildingsearcher.doc(topDocs[i].doc);
				BuildingMarkerDTO dto = new BuildingMarkerDTO();
				
				dto.setIndexKey(doc.get(BomLtsConstant.IDX_UNIQUE_KEY));
				dto.setLat(doc.get(BomLtsConstant.IDX_LAT));
				dto.setLng(doc.get(BomLtsConstant.IDX_LNG));
				dto.setBldgNameEn(doc.get(BomLtsConstant.IDX_NAME_EN));
				dto.setBldgNameCh(doc.get(BomLtsConstant.IDX_NAME_CH));
				dto.setStreetNum(doc.get(BomLtsConstant.IDX_STREET_NUM));
				dto.setStreetNameEn(doc.get(BomLtsConstant.IDX_STREET_NAME_EN));
				dto.setStreetNameCh(doc.get(BomLtsConstant.IDX_STREET_NAME_CH));
				dto.setSectDescEn(doc.get(BomLtsConstant.IDX_SECTION_DESC_EN));
				dto.setSectDescCh(doc.get(BomLtsConstant.IDX_SECTION_DESC_CH));
				dto.setDistDescEn(doc.get(BomLtsConstant.IDX_DISTRICT_DESC_EN));
				dto.setDistDescCh(doc.get(BomLtsConstant.IDX_DISTRICT_DESC_CH));
				dto.setAreaDescEn(doc.get(BomLtsConstant.IDX_AREA_DESC_EN));
				dto.setAreaDescCh(doc.get(BomLtsConstant.IDX_AREA_DESC_CH));
				dto.setHousingAddrEn(doc.get(BomLtsConstant.IDX_HOUSING_ADDR_EN));
				dto.setHousingAddrCh(doc.get(BomLtsConstant.IDX_HOUSING_ADDR_CH));
				dto.setBuildXy(doc.get(BomLtsConstant.IDX_BUILD_XY));
				dto.setSiteGroup(doc.get(BomLtsConstant.IDX_SITE_GROUP));
				dto.setSfBldgRes(doc.get(BomLtsConstant.IDX_SF_BLDG_RES));
				dto.setSfBldgBus(doc.get(BomLtsConstant.IDX_SF_BLDG_BUS));
				dto.setSectCd(doc.get(BomLtsConstant.IDX_SECT_CD));
				dto.setDistrCd(doc.get(BomLtsConstant.IDX_DISTR_CD));
				dto.setAreaCd(doc.get(BomLtsConstant.IDX_AREA_CD));
				dto.setResBasicBw(doc.get(BomLtsConstant.IDX_RES_BASIC_BW));
				dto.setResFttbBw(doc.get(BomLtsConstant.IDX_RES_FTTB_BW));
				dto.setResFtthBw(doc.get(BomLtsConstant.IDX_RES_FTTH_BW));
				dto.setResTvSd(doc.get(BomLtsConstant.IDX_RES_TV_SD));
				dto.setResTvHd(doc.get(BomLtsConstant.IDX_RES_TV_HD));
				dto.setBusBasicBw(doc.get(BomLtsConstant.IDX_BUS_BASIC_BW));
				dto.setBusFttbBw(doc.get(BomLtsConstant.IDX_BUS_FTTB_BW));
				dto.setBusFtthBw(doc.get(BomLtsConstant.IDX_BUS_FTTH_BW));
				dto.setBusTvSd(doc.get(BomLtsConstant.IDX_BUS_TV_SD));
				dto.setBusTvHd(doc.get(BomLtsConstant.IDX_BUS_TV_HD));
				dto.setIsRm(doc.get(BomLtsConstant.IDX_IS_RM));
				dto.setIsPremier(doc.get(BomLtsConstant.IDX_IS_PREMIER));
				dto.setisPh(doc.get(BomLtsConstant.IDX_IS_PH));
				dto.setisHos(doc.get(BomLtsConstant.IDX_IS_HOS));
				dto.setIsCleanedVillage(doc.get(BomLtsConstant.IDX_IS_CLEANED_VILLAGE)); //Added on 20121221 by Eric Ng
				
				rtnList.add(dto);				
				if (rtnList.size() >= MAX_RESULT) {
					break;
				}
			}						
						
		}catch (Exception e){
			e.printStackTrace();
		}
		
		Date endTime = new Date();
		logger.debug("searchBuildingByRange "
				+ (endTime.getTime() - startTime.getTime())
				+ " (ms) used");
		return (BuildingMarkerDTO[]) rtnList.toArray(new BuildingMarkerDTO[0]);		
	}
	
	public ListItemDTO getListItemByKey(String key){
		
		ListItemDTO resultDto = null;
		ArrayList<ListItemDTO> rtnList = new ArrayList<ListItemDTO>(MAX_RESULT + 1);
		Date startTime = new Date();
		
		try{						
			
			ScoreDoc[] topDocs = getListHitsByKey(getListIndexSearcher(), key);
			if (topDocs == null) {
				return resultDto;				
			}						
			
			Document doc = null;			
			for (int i = 0; topDocs != null && i < topDocs.length; i++) {
				doc = listsearcher.doc(topDocs[i].doc);
				ListItemDTO dto = new ListItemDTO();								
				
				dto.setIndexKey(doc.get(BomLtsConstant.IDX_UNIQUE_KEY));
				dto.setSearchType(doc.get(BomLtsConstant.IDX_SEARCH_TYPE));
				dto.setSearchKey(doc.get(BomLtsConstant.IDX_SEARCH_KEY));
				dto.setResultKey(doc.get(BomLtsConstant.IDX_RESULT_KEY));
				dto.setResultEn(doc.get(BomLtsConstant.IDX_RESULT_EN));
				dto.setResultCh(doc.get(BomLtsConstant.IDX_RESULT_CH));
				dto.setDisplaySeq(doc.get(BomLtsConstant.IDX_DISPLAY_SEQ));
				dto.setLat(doc.get(BomLtsConstant.IDX_LAT));
				dto.setLng(doc.get(BomLtsConstant.IDX_LNG));
				
				rtnList.add(dto);				
				if (rtnList.size() >= MAX_RESULT) {
					break;
				}
			}						
			
			resultDto = rtnList.get(0);
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		Date endTime = new Date();
		logger.debug("getListItemByKey "
				+ (endTime.getTime() - startTime.getTime())
				+ " (ms) used");
		
		return resultDto;
		
	}
	
	public BuildingMarkerDTO getBuildingMarkerDetailByKey(String key){
		BuildingMarkerDTO resultDto = null;
		
		ArrayList<MarkerDTO> rtnList = new ArrayList<MarkerDTO>(MAX_RESULT + 1);
		
		try{						
						
			ScoreDoc[] topDocs = getBuildingHitsByKey(getBuildingIndexSearcher(), key);
			if (topDocs == null) {
				return resultDto;				
			}			
			
			Document doc = null;						
			
			for (int i = 0; topDocs != null && i < topDocs.length; i++) {
				
				doc = buildingsearcher.doc(topDocs[i].doc);
				BuildingMarkerDTO dto = new BuildingMarkerDTO();
				
				dto.setIndexKey(doc.get(BomLtsConstant.IDX_UNIQUE_KEY));
				
				dto.setLat(doc.get(BomLtsConstant.IDX_LAT));
				dto.setLng(doc.get(BomLtsConstant.IDX_LNG));
				dto.setBldgNameEn(doc.get(BomLtsConstant.IDX_NAME_EN));
				dto.setBldgNameCh(doc.get(BomLtsConstant.IDX_NAME_CH));
				dto.setStreetNum(doc.get(BomLtsConstant.IDX_STREET_NUM));
				dto.setStreetNameEn(doc.get(BomLtsConstant.IDX_STREET_NAME_EN));
				dto.setStreetNameCh(doc.get(BomLtsConstant.IDX_STREET_NAME_CH));
				dto.setSectDescEn(doc.get(BomLtsConstant.IDX_SECTION_DESC_EN));
				dto.setSectDescCh(doc.get(BomLtsConstant.IDX_SECTION_DESC_CH));
				dto.setDistDescEn(doc.get(BomLtsConstant.IDX_DISTRICT_DESC_EN));
				dto.setDistDescCh(doc.get(BomLtsConstant.IDX_DISTRICT_DESC_CH));
				dto.setAreaDescEn(doc.get(BomLtsConstant.IDX_AREA_DESC_EN));
				dto.setAreaDescCh(doc.get(BomLtsConstant.IDX_AREA_DESC_CH));
				dto.setBuildXy(doc.get(BomLtsConstant.IDX_BUILD_XY));
				dto.setSiteGroup(doc.get(BomLtsConstant.IDX_SITE_GROUP));
				dto.setSfBldgRes(doc.get(BomLtsConstant.IDX_SF_BLDG_RES));
				dto.setSfBldgBus(doc.get(BomLtsConstant.IDX_SF_BLDG_BUS));
				dto.setSectCd(doc.get(BomLtsConstant.IDX_SECT_CD));
				dto.setDistrCd(doc.get(BomLtsConstant.IDX_DISTR_CD));
				dto.setAreaCd(doc.get(BomLtsConstant.IDX_AREA_CD));
				dto.setResBasicBw(doc.get(BomLtsConstant.IDX_RES_BASIC_BW));
				dto.setResFttbBw(doc.get(BomLtsConstant.IDX_RES_FTTB_BW));
				dto.setResFtthBw(doc.get(BomLtsConstant.IDX_RES_FTTH_BW));
				dto.setResTvSd(doc.get(BomLtsConstant.IDX_RES_TV_SD));
				dto.setResTvHd(doc.get(BomLtsConstant.IDX_RES_TV_HD));
				dto.setBusBasicBw(doc.get(BomLtsConstant.IDX_BUS_BASIC_BW));
				dto.setBusFttbBw(doc.get(BomLtsConstant.IDX_BUS_FTTB_BW));
				dto.setBusFtthBw(doc.get(BomLtsConstant.IDX_BUS_FTTH_BW));
				dto.setBusTvSd(doc.get(BomLtsConstant.IDX_BUS_TV_SD));
				dto.setBusTvHd(doc.get(BomLtsConstant.IDX_BUS_TV_HD));
				dto.setIsRm(doc.get(BomLtsConstant.IDX_IS_RM));
				dto.setIsPremier(doc.get(BomLtsConstant.IDX_IS_PREMIER));
				dto.setisPh(doc.get(BomLtsConstant.IDX_IS_PH));
				dto.setisHos(doc.get(BomLtsConstant.IDX_IS_HOS));
				
				dto.setHousingAddrEn(doc.get(BomLtsConstant.IDX_HOUSING_ADDR_EN));
				dto.setHousingAddrCh(doc.get(BomLtsConstant.IDX_HOUSING_ADDR_CH));
				
				dto.setIsCleanedVillage(doc.get(BomLtsConstant.IDX_IS_CLEANED_VILLAGE));//Added on 20121221 by Eric Ng
				
				rtnList.add(dto);				
				if (rtnList.size() >= MAX_RESULT) {
					break;
				}
			}
			
			resultDto = (BuildingMarkerDTO)rtnList.get(0);
						
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return resultDto;
	}
	
	public SearchAddressResultDTO searchBuilding(String searchString){
		SearchAddressResultDTO resultDto = new SearchAddressResultDTO();
		
		ArrayList<MarkerDTO> rtnList = new ArrayList<MarkerDTO>(MAX_RESULT + 1);
		
		try{						
			Date stime = new Date();
			ScoreDoc[] topDocs = getBuildHits(getBuildingIndexSearcher(), searchString.toUpperCase());
			if (topDocs == null) {
				return resultDto;				
			}			
			
			Document doc = null;
			
			resultDto.setResultSize(topDocs.length);
			
			for (int i = 0; topDocs != null && i < topDocs.length; i++) {
				
				doc = buildingsearcher.doc(topDocs[i].doc);
				BuildingMarkerDTO dto = new BuildingMarkerDTO();
				
				dto.setIndexKey(doc.get(BomLtsConstant.IDX_UNIQUE_KEY));
				dto.setLat(doc.get(BomLtsConstant.IDX_LAT));
				dto.setLng(doc.get(BomLtsConstant.IDX_LNG));
				dto.setBldgNameEn(doc.get(BomLtsConstant.IDX_NAME_EN));
				dto.setBldgNameCh(doc.get(BomLtsConstant.IDX_NAME_CH));
				dto.setStreetNum(doc.get(BomLtsConstant.IDX_STREET_NUM));
				dto.setStreetNameEn(doc.get(BomLtsConstant.IDX_STREET_NAME_EN));
				dto.setStreetNameCh(doc.get(BomLtsConstant.IDX_STREET_NAME_CH));
				dto.setSectDescEn(doc.get(BomLtsConstant.IDX_SECTION_DESC_EN));
				dto.setSectDescCh(doc.get(BomLtsConstant.IDX_SECTION_DESC_CH));
				dto.setDistDescEn(doc.get(BomLtsConstant.IDX_DISTRICT_DESC_EN));
				dto.setDistDescCh(doc.get(BomLtsConstant.IDX_DISTRICT_DESC_CH));
				dto.setAreaDescEn(doc.get(BomLtsConstant.IDX_AREA_DESC_EN));
				dto.setAreaDescCh(doc.get(BomLtsConstant.IDX_AREA_DESC_CH));
				dto.setBuildXy(doc.get(BomLtsConstant.IDX_BUILD_XY));
				dto.setSiteGroup(doc.get(BomLtsConstant.IDX_SITE_GROUP));
				dto.setSfBldgRes(doc.get(BomLtsConstant.IDX_SF_BLDG_RES));
				dto.setSfBldgBus(doc.get(BomLtsConstant.IDX_SF_BLDG_BUS));
				dto.setSectCd(doc.get(BomLtsConstant.IDX_SECT_CD));
				dto.setDistrCd(doc.get(BomLtsConstant.IDX_DISTR_CD));
				dto.setAreaCd(doc.get(BomLtsConstant.IDX_AREA_CD));
				dto.setResBasicBw(doc.get(BomLtsConstant.IDX_RES_BASIC_BW));
				dto.setResFttbBw(doc.get(BomLtsConstant.IDX_RES_FTTB_BW));
				dto.setResFtthBw(doc.get(BomLtsConstant.IDX_RES_FTTH_BW));
				dto.setResTvSd(doc.get(BomLtsConstant.IDX_RES_TV_SD));
				dto.setResTvHd(doc.get(BomLtsConstant.IDX_RES_TV_HD));
				dto.setBusBasicBw(doc.get(BomLtsConstant.IDX_BUS_BASIC_BW));
				dto.setBusFttbBw(doc.get(BomLtsConstant.IDX_BUS_FTTB_BW));
				dto.setBusFtthBw(doc.get(BomLtsConstant.IDX_BUS_FTTH_BW));
				dto.setBusTvSd(doc.get(BomLtsConstant.IDX_BUS_TV_SD));
				dto.setBusTvHd(doc.get(BomLtsConstant.IDX_BUS_TV_HD));
				dto.setIsRm(doc.get(BomLtsConstant.IDX_IS_RM));
				dto.setIsPremier(doc.get(BomLtsConstant.IDX_IS_PREMIER));
				dto.setisPh(doc.get(BomLtsConstant.IDX_IS_PH));
				dto.setisHos(doc.get(BomLtsConstant.IDX_IS_HOS));
				
				dto.setHousingAddrEn(doc.get(BomLtsConstant.IDX_HOUSING_ADDR_EN));
				dto.setHousingAddrCh(doc.get(BomLtsConstant.IDX_HOUSING_ADDR_CH));
				
				dto.setIsCleanedVillage(doc.get(BomLtsConstant.IDX_IS_CLEANED_VILLAGE)); //Added on 20121221 by Eric Ng
				
				rtnList.add(dto);				
				if (rtnList.size() >= QUICK_SEARCH_SHOW_ITEM) {
					break;
				}
			}
			
			
			resultDto.setResultList((BuildingMarkerDTO[]) rtnList.toArray(new BuildingMarkerDTO[0]));
			Arrays.sort(resultDto.getResultList());
			
			Date etime = new Date();
			logger.debug("searchBuilding key " + searchString + " total time used " + 
					(etime.getTime() - stime.getTime()));
						
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return resultDto;
	}		
	
	public MarkerDTO[] getMarkerByLevel(String level, 
			String latLower, String latUpper, String lngLower, String lngUpper){
		
		ArrayList<MarkerDTO> rtnList = new ArrayList<MarkerDTO>(MAX_RESULT + 1);				
		Date startTime = new Date();
		
		float latMin=0, latMax=0, lngMin=0, lngMax=0;
		
		if(Integer.parseInt(level)>2){
			latMin=Float.parseFloat(latLower);
			latMax=Float.parseFloat(latUpper);
			lngMin=Float.parseFloat(lngLower);
			lngMax=Float.parseFloat(lngUpper);
		}
		
		try{						
						
			ScoreDoc[] topDocs = getHitsByLevel(getLevelIndexSearcher(), level, latMin, latMax, lngMin, lngMax);
			if (topDocs == null) {
				return new MarkerDTO[0];				
			}						
			
			Document doc = null;			
			for (int i = 0; topDocs != null && i < topDocs.length; i++) {
				doc = levelsearcher.doc(topDocs[i].doc);
				MarkerDTO dto = new MarkerDTO();
				
				dto.setLevel(doc.get(BomLtsConstant.IDX_LEVEL));
				dto.setLat(doc.get(BomLtsConstant.IDX_LAT));
				dto.setLng(doc.get(BomLtsConstant.IDX_LNG));
				dto.setDescCh(doc.get(BomLtsConstant.IDX_DESC_CH));			
				dto.setDescEn(doc.get(BomLtsConstant.IDX_DESC_EN));
				dto.setResBbInd(doc.get(BomLtsConstant.IDX_RES_BB_IND));
				dto.setResTvInd(doc.get(BomLtsConstant.IDX_RES_TV_IND));
				dto.setBusBbInd(doc.get(BomLtsConstant.IDX_BUS_BB_IND));
				dto.setBusTvInd(doc.get(BomLtsConstant.IDX_BUS_TV_IND));
				dto.setResLat(doc.get(BomLtsConstant.IDX_RES_LAT));
				dto.setResLng(doc.get(BomLtsConstant.IDX_RES_LNG));
				dto.setBusLat(doc.get(BomLtsConstant.IDX_BUS_LAT));
				dto.setBusLng(doc.get(BomLtsConstant.IDX_BUS_LNG));
				
				rtnList.add(dto);				
				if (rtnList.size() >= MAX_RESULT) {
					break;
				}
			}						
						
		}catch (Exception e){
			e.printStackTrace();
		}
		
		Date endTime = new Date();
		logger.debug("getMarkerByLevel "
				+ (endTime.getTime() - startTime.getTime())
				+ " (ms) used");
		return (MarkerDTO[]) rtnList.toArray(new MarkerDTO[0]);
		
	}	
	
	private String getIndexDirectoryVersion() throws Exception {
		String version = "";
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(dataFilePath + "online_sales_version.ini"));			
			version = in.readLine().trim().substring(0, 1);
			in.close();
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return version;
	}		
	
	private Searcher getLevelIndexSearcher() throws Exception{
		
		checkVersionForCloseIndex();
		try{
			if(levelsearcher==null){
				logger.info("Initialize Searcher object");
				levelsearcher = new IndexSearcher(getLevelIndexDirectory(), true);
			}			
			logger.debug(">> Directory==" + (leveldirectory==null?"NULL":leveldirectory.toString()));
		}catch(Exception e){
			logger.error(e);
		}
		return levelsearcher;
	}
	
	private Searcher getListIndexSearcher() throws Exception{
		checkVersionForCloseIndex();
		
		try{
			if(listsearcher==null){
				logger.info("Initialize Searcher object");
				listsearcher = new IndexSearcher(getListIndexDirectory(), true);
			}			
			logger.debug(">> Directory==" + (listdirectory==null?"NULL":listdirectory.toString()));
		}catch(Exception e){
			logger.error(e);
		}
		return listsearcher;
	}
	
	private Searcher getBuildingIndexSearcher() throws Exception{
		checkVersionForCloseIndex();
		
		try{
			if(buildingsearcher==null){
				logger.info("Initialize Searcher object");
				buildingsearcher = new IndexSearcher(getBuildingIndexDirectory(), true);
			}			
			logger.debug(">> Directory==" + (buildingdirectory==null?"NULL":buildingdirectory.toString()));
		}catch(Exception e){
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
				
				buildingdirectory = FSDirectory.open(new File(
						idxFilePath+"_v"+version+"/bldg"), 
						new SimpleFSLockFactory());
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
				
				listdirectory = FSDirectory.open(new File(
						idxFilePath+"_v"+version+"/list"), 
						new SimpleFSLockFactory());
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
				leveldirectory = FSDirectory.open(new File(
						idxFilePath+"_v"+version+"/level"), 
						new SimpleFSLockFactory());
			}			
			return leveldirectory;
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}
	
	private ScoreDoc[] getBuildHits(Searcher pSearcher, String pSearchString) throws Exception {
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
	
	private ScoreDoc[] getListByType(Searcher pSearcher, String type, String key) throws Exception{
		Sort sort = new Sort(new SortField(BomLtsConstant.IDX_DISPLAY_SEQ, SortField.INT));				
		BooleanQuery query= new BooleanQuery();
		PhraseQuery pq1 = new PhraseQuery();
		
		pq1.add(new Term(BomLtsConstant.IDX_SEARCH_TYPE, type));
		query.add(pq1, BooleanClause.Occur.MUST);
				
		if(key!=null && key.length()>0){
			PhraseQuery pq2 = new PhraseQuery();
			pq2.add(new Term(BomLtsConstant.IDX_SEARCH_KEY, key));
			query.add(pq2, BooleanClause.Occur.MUST);
		}
		
		Date startTime = new Date();
		TopDocs matches = pSearcher.search(query, null, MAX_RESULT, sort);
		Date endTime = new Date();
		System.out.println("getListByType "
				+ (endTime.getTime() - startTime.getTime())
				+ " (ms) used with " + matches.totalHits
				+ " type:" + type
				+ " key:" + key);
		return matches.scoreDocs;
		
	}
	
	private ScoreDoc[] getListHitsByKey(Searcher pSearcher, String key) throws Exception{		
		TermQuery query = new TermQuery(new Term(BomLtsConstant.IDX_UNIQUE_KEY, key));
		
		Date startTime = new Date();
		TopDocs matches = pSearcher.search(query, MAX_RESULT);
		Date endTime = new Date();
		System.out.println("getListHitsByKey "
				+ (endTime.getTime() - startTime.getTime())
				+ " (ms) used with " + matches.totalHits							
				+ " key=" + key);
		return matches.scoreDocs;
		
	}
	
	private ScoreDoc[] getBuildingHitsByKey(Searcher pSearcher, String key) throws Exception{
		TermQuery query = new TermQuery(new Term(BomLtsConstant.IDX_UNIQUE_KEY, key));
		
		Date startTime = new Date();
		TopDocs matches = pSearcher.search(query, MAX_RESULT);
		Date endTime = new Date();
		System.out.println("getBuildingHitsByKey "
				+ (endTime.getTime() - startTime.getTime())
				+ " (ms) used with " + matches.totalHits							
				+ " key=" + key);
		return matches.scoreDocs;
	}
	
	private ScoreDoc[] getBuildingHitsByRange(Searcher pSearcher,
			float latMin, float latMax, float lngMin, float lngMax) throws Exception{
		BooleanQuery query= new BooleanQuery();
		
		Query qLat = NumericRangeQuery.newFloatRange(BomLtsConstant.IDX_SEARCH_LAT,
                latMin, latMax,
                true, true);
		Query qLng = NumericRangeQuery.newFloatRange(BomLtsConstant.IDX_SEARCH_LNG,
                lngMin, lngMax,
                true, true);
		
		query.add(qLat, BooleanClause.Occur.MUST);
		query.add(qLng, BooleanClause.Occur.MUST);
		
		Date startTime = new Date();
		TopDocs matches = pSearcher.search(query, MAX_RESULT);
		Date endTime = new Date();
		System.out.println("getBuildingHitsByRange "
				+ (endTime.getTime() - startTime.getTime())
				+ " (ms) used with " + matches.totalHits			
				+ " lat(" + latMin + "," + latMax + ")"
				+ " lng(" + lngMin + "," + lngMax + ")");
		return matches.scoreDocs;
		
	}
	
	private ScoreDoc[] getHitsByLevel(Searcher pSearcher, String level, 
			float latMin, float latMax, float lngMin, float lngMax) throws Exception {
				
		BooleanQuery query= new BooleanQuery();
		
		PhraseQuery q = new PhraseQuery();
		
		q.add(new Term(BomLtsConstant.IDX_LEVEL, level));
		
		query.add(q, BooleanClause.Occur.MUST);
		
		if(latMin!=0 || latMax!=0 || lngMin!=0 || lngMax!=0){
			Query qLat = NumericRangeQuery.newFloatRange(BomLtsConstant.IDX_SEARCH_LAT,
                    latMin, latMax,
                    true, true);
			Query qLng = NumericRangeQuery.newFloatRange(BomLtsConstant.IDX_SEARCH_LNG,
                    lngMin, lngMax,
                    true, true);
			
			query.add(qLat, BooleanClause.Occur.MUST);
			query.add(qLng, BooleanClause.Occur.MUST);
			
		}
		
		Date startTime = new Date();
		TopDocs matches = pSearcher.search(query, MAX_RESULT);
		Date endTime = new Date();
		System.out.println("SearchAddress "
				+ (endTime.getTime() - startTime.getTime())
				+ " (ms) used with " + matches.totalHits
				+ " total matching - level:" + level 
				+ " lat(" + latMin + "," + latMax + ")"
				+ " lng(" + lngMin + "," + lngMax + ")");
		return matches.scoreDocs;
	}
	/*
	private ScoreDoc[] getHits(Searcher pSearcher, String pSearchString) throws Exception {
		StringTokenizer st = new StringTokenizer(pSearchString, " ");
		ArrayList<String> searchPhraseList = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			searchPhraseList.add(st.nextToken());
		}
		
		TopDocs topDocs = getMatches(pSearcher, searchPhraseList);
		if (topDocs.totalHits > 0) {
			return topDocs.scoreDocs;	
		}
		return null;
	}*/
	
	private TopDocs getBuildingMatches(Searcher pSearcher,
			ArrayList<String> pSearchPhraseList) throws Exception {
		
		StringBuffer sb = new StringBuffer();
		BooleanQuery query= new BooleanQuery();
		
		for (String searchPhrase : pSearchPhraseList) {
			Query q =new WildcardQuery(
					new Term(BomLtsConstant.IDX_KEY, 
							"*"+searchPhrase.trim().replace(" ", "^")+"*"));
			query.add(q, BooleanClause.Occur.MUST);
			
			sb.append(searchPhrase);
		}						
		
		Date startTime = new Date();
		TopDocs matches = pSearcher.search(query, MAX_RESULT);
		Date endTime = new Date();
		System.out.println("SearchAddress "
				+ (endTime.getTime() - startTime.getTime())
				+ " (ms) used with " + matches.totalHits
				+ " total matching - " + sb.toString());
		return matches;
	}
	/*
	private TopDocs getMatches(Searcher pSearcher,
			ArrayList<String> pSearchPhraseList) throws Exception {
		PhraseQuery query = new PhraseQuery();
		query.setSlop(0);
		StringBuffer sb = new StringBuffer();
		for (String searchPhrase : pSearchPhraseList) {
			query.add(new Term(BomLtsConstant.IDX_LEVEL, searchPhrase));
			sb.append(searchPhrase);
			sb.append(" ");
		}			
		
		Date startTime = new Date();
		TopDocs matches = pSearcher.search(query, MAX_RESULT);
		Date endTime = new Date();
		logger.info("SearchAddress "
				+ (endTime.getTime() - startTime.getTime())
				+ " (ms) used with " + matches.totalHits
				+ " total matching - " + sb.toString());
		return matches;
	}*/
	
	public Document genListDocumnet(ListItemDTO dto) throws Exception {
		
		Document doc = new Document();			
		
		doc.add(new Field(BomLtsConstant.IDX_UNIQUE_KEY, dto.getIndexKey(), Field.Store.YES, Field.Index.NO));
		
		doc.add(new Field(BomLtsConstant.IDX_SEARCH_TYPE, dto.getSearchType(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(BomLtsConstant.IDX_SEARCH_KEY, dto.getSearchKey(), Field.Store.YES, Field.Index.NOT_ANALYZED)); 
		if(dto.getResultKey()!=null) doc.add(new Field(BomLtsConstant.IDX_RESULT_KEY, dto.getResultKey(), Field.Store.YES, Field.Index.NO));
		if(dto.getResultEn()!=null) doc.add(new Field(BomLtsConstant.IDX_RESULT_EN, dto.getResultEn(), Field.Store.YES, Field.Index.NO));
		if(dto.getResultCh()!=null) doc.add(new Field(BomLtsConstant.IDX_RESULT_CH, dto.getResultCh(), Field.Store.YES, Field.Index.NO));
		doc.add(new NumericField(BomLtsConstant.IDX_DISPLAY_SEQ, Field.Store.YES, true)
			.setIntValue(Integer.parseInt(dto.getDisplaySeq())));		
		if(dto.getLat()!=null) doc.add(new Field(BomLtsConstant.IDX_LAT, dto.getLat(), Field.Store.YES, Field.Index.NO));
		if(dto.getLng()!=null) doc.add(new Field(BomLtsConstant.IDX_LNG, dto.getLng(), Field.Store.YES, Field.Index.NO));
		
		return doc;
	}
	
	public Document genBuildMarkerDocument(BuildingMarkerDTO dto) throws Exception {
		
		Document doc = new Document();
		
		doc.add(new Field(BomLtsConstant.IDX_UNIQUE_KEY, dto.getIndexKey(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		
		doc.add(new Field(BomLtsConstant.IDX_KEY, getBuildingSearchString(dto), Field.Store.YES, Field.Index.ANALYZED));
		
		if(dto.getLat()!=null) doc.add(new Field(BomLtsConstant.IDX_LAT, dto.getLat(), Field.Store.YES, Field.Index.NO));
		if(dto.getLng()!=null) doc.add(new Field(BomLtsConstant.IDX_LNG, dto.getLng(), Field.Store.YES, Field.Index.NO));
		if(dto.getBldgNameEn()!=null) doc.add(new Field(BomLtsConstant.IDX_NAME_EN, dto.getBldgNameEn(), Field.Store.YES, Field.Index.NO));
		if(dto.getBldgNameCh()!=null) doc.add(new Field(BomLtsConstant.IDX_NAME_CH, dto.getBldgNameCh(), Field.Store.YES, Field.Index.NO));
		if(dto.getStreetNum()!=null) doc.add(new Field(BomLtsConstant.IDX_STREET_NUM, dto.getStreetNum(), Field.Store.YES, Field.Index.NO));
		if(dto.getStreetNameEn()!=null) doc.add(new Field(BomLtsConstant.IDX_STREET_NAME_EN, dto.getStreetNameEn(), Field.Store.YES, Field.Index.NO));
		if(dto.getStreetNameCh()!=null) doc.add(new Field(BomLtsConstant.IDX_STREET_NAME_CH, dto.getStreetNameCh(), Field.Store.YES, Field.Index.NO));
		if(dto.getSectDescEn()!=null) doc.add(new Field(BomLtsConstant.IDX_SECTION_DESC_EN, dto.getSectDescEn(), Field.Store.YES, Field.Index.NO));
		if(dto.getSectDescCh()!=null) doc.add(new Field(BomLtsConstant.IDX_SECTION_DESC_CH, dto.getSectDescCh(), Field.Store.YES, Field.Index.NO));
		if(dto.getDistDescEn()!=null) doc.add(new Field(BomLtsConstant.IDX_DISTRICT_DESC_EN, dto.getDistDescEn(), Field.Store.YES, Field.Index.NO));
		if(dto.getDistDescCh()!=null) doc.add(new Field(BomLtsConstant.IDX_DISTRICT_DESC_CH, dto.getDistDescCh(), Field.Store.YES, Field.Index.NO));
		if(dto.getAreaDescEn()!=null) doc.add(new Field(BomLtsConstant.IDX_AREA_DESC_EN, dto.getAreaDescEn(), Field.Store.YES, Field.Index.NO));
		if(dto.getAreaDescCh()!=null) doc.add(new Field(BomLtsConstant.IDX_AREA_DESC_CH, dto.getAreaDescCh(), Field.Store.YES, Field.Index.NO));
		if(dto.getBuildXy()!=null) doc.add(new Field(BomLtsConstant.IDX_BUILD_XY, dto.getBuildXy(), Field.Store.YES, Field.Index.NO));
		if(dto.getSiteGroup()!=null) doc.add(new Field(BomLtsConstant.IDX_SITE_GROUP, dto.getSiteGroup(), Field.Store.YES, Field.Index.NO));
		if(dto.getSfBldgRes()!=null) doc.add(new Field(BomLtsConstant.IDX_SF_BLDG_RES, dto.getSfBldgRes(), Field.Store.YES, Field.Index.NO));
		if(dto.getSfBldgBus()!=null) doc.add(new Field(BomLtsConstant.IDX_SF_BLDG_BUS, dto.getSfBldgBus(), Field.Store.YES, Field.Index.NO));
		if(dto.getSectCd()!=null) doc.add(new Field(BomLtsConstant.IDX_SECT_CD, dto.getSectCd(), Field.Store.YES, Field.Index.NO));
		if(dto.getDistrCd()!=null) doc.add(new Field(BomLtsConstant.IDX_DISTR_CD, dto.getDistrCd(), Field.Store.YES, Field.Index.NO));
		if(dto.getAreaCd()!=null) doc.add(new Field(BomLtsConstant.IDX_AREA_CD, dto.getAreaCd(), Field.Store.YES, Field.Index.NO));
		if(dto.getResBasicBw()!=null) doc.add(new Field(BomLtsConstant.IDX_RES_BASIC_BW, dto.getResBasicBw(), Field.Store.YES, Field.Index.NO));
		if(dto.getResFttbBw()!=null) doc.add(new Field(BomLtsConstant.IDX_RES_FTTB_BW, dto.getResFttbBw(), Field.Store.YES, Field.Index.NO));
		if(dto.getResFtthBw()!=null) doc.add(new Field(BomLtsConstant.IDX_RES_FTTH_BW, dto.getResFtthBw(), Field.Store.YES, Field.Index.NO));
		if(dto.getResTvSd()!=null) doc.add(new Field(BomLtsConstant.IDX_RES_TV_SD, dto.getResTvSd(), Field.Store.YES, Field.Index.NO));
		if(dto.getResTvHd()!=null) doc.add(new Field(BomLtsConstant.IDX_RES_TV_HD, dto.getResTvHd(), Field.Store.YES, Field.Index.NO));
		if(dto.getBusBasicBw()!=null) doc.add(new Field(BomLtsConstant.IDX_BUS_BASIC_BW, dto.getBusBasicBw(), Field.Store.YES, Field.Index.NO));
		if(dto.getBusFttbBw()!=null) doc.add(new Field(BomLtsConstant.IDX_BUS_FTTB_BW, dto.getBusFttbBw(), Field.Store.YES, Field.Index.NO));
		if(dto.getBusFtthBw()!=null) doc.add(new Field(BomLtsConstant.IDX_BUS_FTTH_BW, dto.getBusFtthBw(), Field.Store.YES, Field.Index.NO));
		if(dto.getBusTvSd()!=null) doc.add(new Field(BomLtsConstant.IDX_BUS_TV_SD, dto.getBusTvSd(), Field.Store.YES, Field.Index.NO));
		if(dto.getBusTvHd()!=null) doc.add(new Field(BomLtsConstant.IDX_BUS_TV_HD, dto.getBusTvHd(), Field.Store.YES, Field.Index.NO));
		if(dto.getIsRm()!=null) doc.add(new Field(BomLtsConstant.IDX_IS_RM, dto.getIsRm(), Field.Store.YES, Field.Index.NO));
		if(dto.getIsPremier()!=null) doc.add(new Field(BomLtsConstant.IDX_IS_PREMIER, dto.getIsPremier(), Field.Store.YES, Field.Index.NO));
		if(dto.getHousingAddrEn()!=null) doc.add(new Field(BomLtsConstant.IDX_HOUSING_ADDR_EN, dto.getHousingAddrEn(), Field.Store.YES, Field.Index.NO));
		if(dto.getHousingAddrCh()!=null) doc.add(new Field(BomLtsConstant.IDX_HOUSING_ADDR_CH, dto.getHousingAddrCh(), Field.Store.YES, Field.Index.NO));
		if(dto.getisPh()!=null) doc.add(new Field(BomLtsConstant.IDX_IS_PH, dto.getisPh(), Field.Store.YES, Field.Index.NO));
		if(dto.getisHos()!=null) doc.add(new Field(BomLtsConstant.IDX_IS_HOS, dto.getisHos(), Field.Store.YES, Field.Index.NO));
		
		if (dto.getIsCleanedVillage()!= null) //added on 20121221 by Eric Ng
			doc.add(new Field(BomLtsConstant.IDX_IS_CLEANED_VILLAGE,dto.getIsCleanedVillage(), Field.Store.YES, Field.Index.NO));
		
		doc.add(new NumericField(BomLtsConstant.IDX_SEARCH_LAT).setFloatValue(Float.parseFloat(dto.getLat())));
		doc.add(new NumericField(BomLtsConstant.IDX_SEARCH_LNG).setFloatValue(Float.parseFloat(dto.getLng())));		
		
		return doc;
		
	}
	
	public Document genMarkerDocument(MarkerDTO dto) throws Exception {			
		
		Document doc = new Document();
		
		doc.add(new Field(BomLtsConstant.IDX_LEVEL, dto.getLevel(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(BomLtsConstant.IDX_LAT, dto.getLat(), Field.Store.YES, Field.Index.NO));
		doc.add(new Field(BomLtsConstant.IDX_LNG, dto.getLng(), Field.Store.YES, Field.Index.NO));
		doc.add(new Field(BomLtsConstant.IDX_DESC_CH, dto.getDescCh(), Field.Store.YES, Field.Index.NO));
		doc.add(new Field(BomLtsConstant.IDX_DESC_EN, dto.getDescEn(), Field.Store.YES, Field.Index.NO));
		doc.add(new Field(BomLtsConstant.IDX_RES_BB_IND, dto.getResBbInd(), Field.Store.YES, Field.Index.NO));
		doc.add(new Field(BomLtsConstant.IDX_RES_TV_IND, dto.getResTvInd(), Field.Store.YES, Field.Index.NO));
		doc.add(new Field(BomLtsConstant.IDX_BUS_BB_IND, dto.getBusBbInd(), Field.Store.YES, Field.Index.NO));
		doc.add(new Field(BomLtsConstant.IDX_BUS_TV_IND, dto.getBusTvInd(), Field.Store.YES, Field.Index.NO));
		doc.add(new NumericField(BomLtsConstant.IDX_SEARCH_LAT).setFloatValue(Float.parseFloat(dto.getLat())));
		doc.add(new NumericField(BomLtsConstant.IDX_SEARCH_LNG).setFloatValue(Float.parseFloat(dto.getLng())));
		doc.add(new Field(BomLtsConstant.IDX_RES_LAT, dto.getResLat(), Field.Store.YES, Field.Index.NO));
		doc.add(new Field(BomLtsConstant.IDX_RES_LNG, dto.getResLng(), Field.Store.YES, Field.Index.NO));
		doc.add(new Field(BomLtsConstant.IDX_BUS_LAT, dto.getBusLat(), Field.Store.YES, Field.Index.NO));
		doc.add(new Field(BomLtsConstant.IDX_BUS_LNG, dto.getBusLng(), Field.Store.YES, Field.Index.NO));
				
		return doc;
	}
	
	private String getBuildingSearchString(BuildingMarkerDTO dto) throws Exception {
		StringBuffer sb = new StringBuffer();				
		
		if(StringUtils.isNotBlank(dto.getBldgNameEn())){
			sb.append(dto.getBldgNameEn().replaceAll(" ", "^"));
			sb.append(" ");
		}
		
		if(StringUtils.isNotBlank(dto.getBldgNameCh())){
			sb.append(dto.getBldgNameCh().replaceAll(" ", "^"));
			sb.append(" ");
		}
		
		//handle for searching "street number street name" or "street name street number"
		if(StringUtils.isNotBlank(dto.getStreetNum()) && StringUtils.isNotBlank(dto.getStreetNameEn())){
			
			sb.append(dto.getStreetNum().replaceAll(" ", "^"));
			sb.append("^"+dto.getStreetNameEn().replaceAll(" ", "^")+"^");
			sb.append(dto.getStreetNum().replaceAll(" ", "^"));
			sb.append(" ");
			
		}else{
			
			if(StringUtils.isNotBlank(dto.getStreetNum())){
				sb.append(dto.getStreetNum().replaceAll(" ", "^"));
				sb.append(" ");
			}
			
			if(StringUtils.isNotBlank(dto.getStreetNameEn())){
				sb.append(dto.getStreetNameEn().replaceAll(" ", "^"));
				sb.append(" ");
			}
			
		}
		
		if(StringUtils.isNotBlank(dto.getStreetNum()) && StringUtils.isNotBlank(dto.getStreetNameCh())){
			
			sb.append(dto.getStreetNum().replaceAll(" ", "^"));
			sb.append("^"+dto.getStreetNameCh().replaceAll(" ", "^")+"^");
			sb.append(dto.getStreetNum().replaceAll(" ", "^"));
			sb.append(" ");
			
		}else{
			
			if(StringUtils.isNotBlank(dto.getStreetNameCh())){
				sb.append(dto.getStreetNameCh().replaceAll(" ", "^"));
				sb.append(" ");
			}
			
		}	
		
		if(StringUtils.isNotBlank(dto.getSectDescEn())){
			sb.append(dto.getSectDescEn().replaceAll(" ", "^"));
			sb.append(" ");
		}

		if(StringUtils.isNotBlank(dto.getSectDescCh())){
			sb.append(dto.getSectDescCh().replaceAll(" ", "^"));
			sb.append(" ");
		}
		
		if(StringUtils.isNotBlank(dto.getDistDescEn())){
			sb.append(dto.getDistDescEn().replaceAll(" ", "^"));
			sb.append(" ");
		}

		if(StringUtils.isNotBlank(dto.getDistDescCh())){
			sb.append(dto.getDistDescCh().replaceAll(" ", "^"));
			sb.append(" ");
		}
		/*
		if(StringUtils.isNotBlank(dto.getAreaDescEn())){
			sb.append(dto.getAreaDescEn().replaceAll(" ", "^"));
			sb.append(" ");
		}

		if(StringUtils.isNotBlank(dto.getAreaDescCh())){
			sb.append(dto.getAreaDescCh().replaceAll(" ", "^"));
			sb.append(" ");
		}*/
		
		if(StringUtils.isNotBlank(dto.getExtNameEn())){
			sb.append(dto.getExtNameEn());
			sb.append(" ");
		}
		
		if(StringUtils.isNotBlank(dto.getExtNameCh())){
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
