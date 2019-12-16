package com.bomwebportal.ims.service;

import java.util.List;
import java.util.Map;

public interface PTService {
	public int getSalesNum(String code);
	public List<Map<String, String>> getNowTVPTList();
}
