package com.bomwebportal.ims.service;

import org.apache.lucene.store.Directory;


public interface AddressIndexService {
	
	public int buildAddressIndexFile(Directory pIndexDirectory) throws Exception;

}
