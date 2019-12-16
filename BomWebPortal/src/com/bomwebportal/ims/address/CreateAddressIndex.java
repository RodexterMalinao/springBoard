package com.bomwebportal.ims.address;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.ims.service.AddressIndexService;


public class CreateAddressIndex {
	
	protected static final Log logger = LogFactory.getLog(CreateAddressIndex.class);
	
	public static final int MAX_VERSION = 3;

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

    private AddressIndexService addrIdxService;
    
	public AddressIndexService getAddrIdxService() {
		return addrIdxService;
	}
	public void setAddrIdxService(AddressIndexService addrIdxService) {
		this.addrIdxService = addrIdxService;
	}

	public static void main(String[] args) {
		
	}
	
	public File getIndexDirectory(String version) throws Exception {
		
		try {
			logger.info("Open index file directory");
			return new File(this.idxFilePath + "_v" + version);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}
	
	public String getVersionFileName() {
		
		return this.dataFilePath + "version.ini";
	}
	
	public String getIndexDirectoryVersion() throws Exception {
		int version = 0;
		String s = new String();
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(getVersionFileName()));
			
			s = in.readLine().trim().substring(0, 1);
			if (s != null) {
				version = Integer.parseInt(s);
			} else { 
				version = 0;
			}
			in.close();
			
		} catch (NumberFormatException e) {
			version = 0;
		} catch (FileNotFoundException e) {
			version = 0;
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
		return (""+((version%MAX_VERSION)+1));
	}
	
	public void writeIndexDirectoryVersion(String version) throws Exception {
		
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(getVersionFileName()));
			
			out.write(version);
			out.close();
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}
	
	public static boolean removeFiles(File f) {

		if (f == null) {
			logger.info("directory is null");
			return false;
		}
		
		if (!f.exists()) {
			logger.info("directory is not exist");
			f.mkdir();
			return false;
		}
		if (!f.isDirectory()) {
			logger.info("directory is not directory");
			return false;
		}

		String[] list = f.list();

		if (list != null) {
			for (int i = 0; i < list.length; i++) {
				File entry = new File(f, list[i]);

				if (!entry.isDirectory()) {
					if (!entry.delete()) {
						logger.debug(">>File can not delete name == " + entry.toString());
						return false;
					}
				}
			}
		} 

		return true;
	}

	public void buildIndexFile() throws Exception {
		String version = "";
		File f;
		Directory indexDir;
		int numRec;
		
		try {
			System.out.println("start build address index");
			
			// get next version from version.ini
			version = getIndexDirectoryVersion();
			logger.info("Next Version == " + version);

			// remove index file under index directory with version
			f = getIndexDirectory(version);
			if (removeFiles(f)) {
				logger.info("remove file successfully");
			} else {
				logger.info("remove file failure");			
			}
			
			// build index file under index directory with version
			indexDir = FSDirectory.open(f);
			numRec = addrIdxService.buildAddressIndexFile(indexDir);
			logger.info("No of Records build into address index file == " + numRec);
			
			// update version into version.ini
			writeIndexDirectoryVersion(version);
			
			System.out.println("finish build address index");
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppRuntimeException(e);
		}
		
	}
		
}