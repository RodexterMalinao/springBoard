package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.List;

public class CPQNTVInfo {
	public Campaign campaigns[];
    public String[] vas;
    public String[] paymentMethod;
    public IA IA;

    public class Campaign {
        public String campaign_id;
        public String[] pack_id;
        public String[] gift_id;
        public String[] channel_gift_id;
    }

    public class IA {
        public String sb;
        public String flr;
        public String flt;
    }
    public Service service;

    public class Service {
        public String ID_DOC_TYPE;
        public String ID_DOC_NUM;
        public String FSA;
    }
    public String order_type;
    public String action_type;
    public String camp_nature;
    
    public String inWorkQueue; // BOM2018062, martin
	public String promoteCode; // BOM2018062, martin
	public String promoteOfferType; // BOM2018062, martin
}