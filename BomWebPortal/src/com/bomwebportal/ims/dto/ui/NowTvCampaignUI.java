package com.bomwebportal.ims.dto.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.bomwebportal.ims.dto.ui.NowTvPackUI;

public class NowTvCampaignUI {
	private String imsType;
	private String campId;
	private String title;
	private int contractPeriod;
	private String tvType;
	private String fix_term_rate;
	private String mth_to_mth_rate;
	private String tnc;
	private int fix_term_rate_no;
	private int mth_to_mth_rate_no;
	private int max_select_cnt;
	private int min_select_cnt;
	
	private List<NowTvVasBundle> vasBundles = new ArrayList<NowTvVasBundle>();
	private List<NowTvPackUI> tvPacks = new ArrayList<NowTvPackUI>();
	private List<NowTvTopUpUI> topUps = new ArrayList<NowTvTopUpUI>();
	private int readRight=0;
	
	public boolean isSelected(){
		for(NowTvPackUI pack:this.getPacks()){
			if(pack.isSelected())
				return true;
		}
		return false;
	}
	
	public int getReadRight() {
		if("HD".equalsIgnoreCase(tvType)){
			readRight = 4;
		}
		else if("HDR".equalsIgnoreCase(tvType)){
			readRight = 3;
		}
		else if("SD".equalsIgnoreCase(tvType)){
			readRight = 0;
		}
		return readRight;
	}

	public void setReadRight(int readRight) {
		this.readRight = readRight;
	}
		public String getTnc() {
		return tnc;
	}

	public void setTnc(String tnc) {
		this.tnc = tnc;
	}

	public String getImsType() {
		return imsType;
	}

	public void setImsType(String imsType) {
		this.imsType = imsType;
	}

	public String getCampId() {
		return campId;
	}

	public void setCampId(String campId) {
		this.campId = campId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTvType() {
		return tvType;
	}

	public void setTvType(String tvType) {
		this.tvType = tvType;
	}

	public String getFix_term_rate() {
		return fix_term_rate;
	}

	public void setFix_term_rate(String fix_term_rate) {
		this.fix_term_rate = fix_term_rate;
	}

	public String getMth_to_mth_rate() {
		return mth_to_mth_rate;
	}

	public void setMth_to_mth_rate(String mth_to_mth_rate) {
		this.mth_to_mth_rate = mth_to_mth_rate;
	}

	public int getContractPeriod() {
		return contractPeriod;
	}

	public void setContractPeriod(int contractPeriod) {
		this.contractPeriod = contractPeriod;
	}

	public int getFix_term_rate_no() {
		return fix_term_rate_no;
	}

	public void setFix_term_rate_no(int fix_term_rate_no) {
		this.fix_term_rate_no = fix_term_rate_no;
	}

	public int getMth_to_mth_rate_no() {
		return mth_to_mth_rate_no;
	}

	public void setMth_to_mth_rate_no(int mth_to_mth_rate_no) {
		this.mth_to_mth_rate_no = mth_to_mth_rate_no;
	}

	public int getMax_select_cnt() {
		return max_select_cnt;
	}

	public void setMax_select_cnt(int max_select_cnt) {
		this.max_select_cnt = max_select_cnt;
	}

	public int getMin_select_cnt() {
		return min_select_cnt;
	}

	public void setMin_select_cnt(int min_select_cnt) {
		this.min_select_cnt = min_select_cnt;
	}

	public List<NowTvPackUI> getTvPacks() {
		return tvPacks;
	}

	public void setTvPacks(List<NowTvPackUI> tvPacks) {
		this.tvPacks = tvPacks;
	}
	
	public int getPackSize() {
		if (getTvPacks() == null) {
			return 0;
		} else {
			return getTvPacks().size();
		}
	}
	
	public int getNumOfSelectedPack() {
		int numOfSelected = 0;
		if (getTvPacks() != null) {
			for (NowTvPackUI pack: getTvPacks()) {
				if (pack.isSelected()) {
					numOfSelected++;
				}
			}
		}
		return numOfSelected;
	}

	public List<NowTvVasBundle> getVasBundles() {
		return vasBundles;
	}

	public void setVasBundles(List<NowTvVasBundle> vasBundles) {
		this.vasBundles = vasBundles;
	}

	public List<NowTvTopUpUI> getTopUps() {
		return topUps;
	}

	public void setTopUps(List<NowTvTopUpUI> topUps) {
		this.topUps = topUps;
	}

		public List<NowTvPackUI> getPacks() {
		List<NowTvPackUI> selectedPacks = new ArrayList<NowTvPackUI>();
		if (getTvPacks() != null) {
			for (NowTvPackUI pack: getTvPacks()) {
				if (pack.isSelected()) {
					selectedPacks.add(pack);
				}
			}
		}
		return selectedPacks;
	}
	
}
