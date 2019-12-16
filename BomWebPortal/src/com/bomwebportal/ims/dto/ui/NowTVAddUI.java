package com.bomwebportal.ims.dto.ui;

import java.util.ArrayList;
import java.util.List;

public class NowTVAddUI {
	private NowTvCampaignUI ftaCampaign;
	private NowTvCampaignUI hardCampaign;
	private List<NowTvCampaignUI> payTvCampaign = new ArrayList<NowTvCampaignUI>();
	private int ntvConnType;
	
	public NowTvCampaignUI getFtaCampaign() {
		return ftaCampaign;
	}
	public void setFtaCampaign(NowTvCampaignUI ftaCampaign) {
		this.ftaCampaign = ftaCampaign;
	}
	public NowTvCampaignUI getHardCampaign() {
		return hardCampaign;
	}
	public void setHardCampaign(NowTvCampaignUI hardCampaign) {
		this.hardCampaign = hardCampaign;
	}
	public List<NowTvCampaignUI> getPayTvCampaign() {
		return payTvCampaign;
	}
	public void setPayTvCampaign(List<NowTvCampaignUI> payTvCampaign) {
		this.payTvCampaign = payTvCampaign;
	}
	
	public List<NowTvTopUpUI> getFtaSelectedTopUp() {
		List<NowTvTopUpUI> selectedTopUp = new ArrayList<NowTvTopUpUI>();
		if (getFtaCampaign() != null) {
			selectedTopUp.addAll(getFtaCampaign().getTopUps());
		}	
		return selectedTopUp;
	}
	
	public List<NowTvTopUpUI> getHardSelectedTopUp() {
		List<NowTvTopUpUI> selectedTopUp = new ArrayList<NowTvTopUpUI>();
		if (getHardCampaign() != null) {
			selectedTopUp.addAll(getHardCampaign().getTopUps());
		}	
		return selectedTopUp;
	}
	
	
	public List<NowTvTopUpUI> getPayTvSelectedTopUp() {
		List<NowTvTopUpUI> selectedTopUp = new ArrayList<NowTvTopUpUI>();
		if (getPayTvCampaign() != null) {
			for (NowTvCampaignUI payTv: getPayTvCampaign()) {
				selectedTopUp.addAll(payTv.getTopUps());
			}
		}
		return selectedTopUp;
	}
	
	
	public List<NowTvPackUI> getSelectedPacks() {
		List<NowTvPackUI> selectedPacks = new ArrayList<NowTvPackUI>();
		if (getFtaCampaign() != null) {
			selectedPacks.addAll(getFtaCampaign().getPacks());
		}
		if (getHardCampaign() != null) {
			selectedPacks.addAll(getHardCampaign().getPacks());
		}
		if (getPayTvCampaign() != null) {
			for (NowTvCampaignUI payTv: getPayTvCampaign()) {
				selectedPacks.addAll(payTv.getPacks());
			}
		}
		
		return selectedPacks;
	}

	public void loadNowTVAddUISettings(List<SubscribedItemUI> items) {
		class checkPacks {
			public void checkItem(SubscribedItemUI item, NowTvCampaignUI campaign) {
				if (campaign.getTvPacks() != null && campaign.getTvPacks().size() > 0) {
					for (NowTvPackUI pack: campaign.getTvPacks()) {
						if (item.getId().equals(pack.getPackId()) && item.getBasketId()!=null && item.getBasketId().equals(pack.getParentCampaignId())) { 
							pack.setSelected(true);
						}
					}
				}
				if (campaign.getTopUps() != null && campaign.getTopUps().size() > 0) {
					for (NowTvTopUpUI topUp: campaign.getTopUps()) {
						if (item.getId().equals(topUp.getItemId()) && item.getBasketId()!=null && item.getBasketId().equals(topUp.getParentCampaignId())) {
							topUp.setSelected(true);
						}
					}
				}
			}
			public void forceFalse(List<NowTvPackUI> packs) {
				if (packs != null && packs.size() > 0) {
					for (NowTvPackUI pack: packs) 
						pack.setSelected(false);
				}
			}
			public checkPacks() {
				if (getFtaCampaign()!=null) {
					forceFalse(getFtaCampaign().getTvPacks());
				}
				if (getHardCampaign()!=null) {
					forceFalse(getHardCampaign().getTvPacks());
				}
				if (getPayTvCampaign()!=null) {
					for (NowTvCampaignUI PayTv: getPayTvCampaign()) {
						forceFalse(PayTv.getTvPacks());
					}
				}
			}
		}
		checkPacks cPack = new checkPacks();
		if (items != null && items.size() > 0) {
			for (SubscribedItemUI item: items) {
				if (getFtaCampaign()!=null) {
					cPack.checkItem(item, getFtaCampaign());
				}
				if (getHardCampaign()!=null) {
					cPack.checkItem(item, getHardCampaign());
				}
				if (getPayTvCampaign()!=null) {
					for (NowTvCampaignUI PayTv: getPayTvCampaign()) {
						cPack.checkItem(item, PayTv);
					}
				}
			}
		}
	}
	public int getNtvConnType() {
		return ntvConnType;
	}
	public void setNtvConnType(int ntvConnType) {
		this.ntvConnType = ntvConnType;
	}
	public int renewConnType(boolean nowBoxPurchased, boolean hdPurchased, String modelBandwidth) {
        int connType = 0;
        float bandwidth = 0;
        if (modelBandwidth != null && !"".equals(modelBandwidth)) {
           try {
                      bandwidth = Float.parseFloat(modelBandwidth);
           } catch (NumberFormatException e) {
                      bandwidth = 0;
           }
        }
        if (bandwidth != 0) {
           if (nowBoxPurchased) {
        	   connType = 7;
           }
           else if (hdPurchased) {
                      connType = 5;
           } else if (bandwidth > 18) { // HDR
                      connType = 3;
           } else {
                      connType = 0;
           }
        }
        return connType;
	}


	
}


