/*
 * An XML document type.
 * Localname: OrderRegInfo
 * Namespace: 
 * Java type: noNamespace.OrderRegInfoDocument
 *
 * Automatically generated - do not modify.
 */
package noNamespace;


/**
 * A document containing one OrderRegInfo(@) element.
 *
 * This is a complex type.
 */
public interface OrderRegInfoDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(OrderRegInfoDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s1FF98B5715F9EE67BF5AE52C2CFC4EE4").resolveHandle("orderreginfo734ddoctype");
    
    /**
     * Gets the "OrderRegInfo" element
     */
    noNamespace.OrderRegInfoDocument.OrderRegInfo getOrderRegInfo();
    
    /**
     * Sets the "OrderRegInfo" element
     */
    void setOrderRegInfo(noNamespace.OrderRegInfoDocument.OrderRegInfo orderRegInfo);
    
    /**
     * Appends and returns a new empty "OrderRegInfo" element
     */
    noNamespace.OrderRegInfoDocument.OrderRegInfo addNewOrderRegInfo();
    
    /**
     * An XML OrderRegInfo(@).
     *
     * This is a complex type.
     */
    public interface OrderRegInfo extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(OrderRegInfo.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s1FF98B5715F9EE67BF5AE52C2CFC4EE4").resolveHandle("orderreginfo006delemtype");
        
        /**
         * Gets the "orderReg" element
         */
        noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderReg getOrderReg();
        
        /**
         * Sets the "orderReg" element
         */
        void setOrderReg(noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderReg orderReg);
        
        /**
         * Appends and returns a new empty "orderReg" element
         */
        noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderReg addNewOrderReg();
        
        /**
         * Gets the "customer" element
         */
        noNamespace.OrderRegInfoDocument.OrderRegInfo.Customer getCustomer();
        
        /**
         * Sets the "customer" element
         */
        void setCustomer(noNamespace.OrderRegInfoDocument.OrderRegInfo.Customer customer);
        
        /**
         * Appends and returns a new empty "customer" element
         */
        noNamespace.OrderRegInfoDocument.OrderRegInfo.Customer addNewCustomer();
        
        /**
         * Gets the "Address" element
         */
        noNamespace.OrderRegInfoDocument.OrderRegInfo.Address getAddress();
        
        /**
         * Sets the "Address" element
         */
        void setAddress(noNamespace.OrderRegInfoDocument.OrderRegInfo.Address address);
        
        /**
         * Appends and returns a new empty "Address" element
         */
        noNamespace.OrderRegInfoDocument.OrderRegInfo.Address addNewAddress();
        
        /**
         * Gets the "orderRegDtls" element
         */
        noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls getOrderRegDtls();
        
        /**
         * Sets the "orderRegDtls" element
         */
        void setOrderRegDtls(noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls orderRegDtls);
        
        /**
         * Appends and returns a new empty "orderRegDtls" element
         */
        noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls addNewOrderRegDtls();
        
        /**
         * Gets the "orderRegTenders" element
         */
        noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders getOrderRegTenders();
        
        /**
         * Sets the "orderRegTenders" element
         */
        void setOrderRegTenders(noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders orderRegTenders);
        
        /**
         * Appends and returns a new empty "orderRegTenders" element
         */
        noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders addNewOrderRegTenders();
        
        /**
         * An XML orderReg(@).
         *
         * This is a complex type.
         */
        public interface OrderReg extends org.apache.xmlbeans.XmlObject
        {
            public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
                org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(OrderReg.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s1FF98B5715F9EE67BF5AE52C2CFC4EE4").resolveHandle("orderreg1ddfelemtype");
            
            /**
             * Gets the "Store_ID" element
             */
            java.lang.String getStoreID();
            
            /**
             * Gets (as xml) the "Store_ID" element
             */
            org.apache.xmlbeans.XmlString xgetStoreID();
            
            /**
             * Sets the "Store_ID" element
             */
            void setStoreID(java.lang.String storeID);
            
            /**
             * Sets (as xml) the "Store_ID" element
             */
            void xsetStoreID(org.apache.xmlbeans.XmlString storeID);
            
            /**
             * Gets the "Register_ID" element
             */
            java.lang.String getRegisterID();
            
            /**
             * Gets (as xml) the "Register_ID" element
             */
            org.apache.xmlbeans.XmlString xgetRegisterID();
            
            /**
             * Sets the "Register_ID" element
             */
            void setRegisterID(java.lang.String registerID);
            
            /**
             * Sets (as xml) the "Register_ID" element
             */
            void xsetRegisterID(org.apache.xmlbeans.XmlString registerID);
            
            /**
             * Gets the "Trans_Num" element
             */
            java.lang.String getTransNum();
            
            /**
             * Gets (as xml) the "Trans_Num" element
             */
            org.apache.xmlbeans.XmlString xgetTransNum();
            
            /**
             * Sets the "Trans_Num" element
             */
            void setTransNum(java.lang.String transNum);
            
            /**
             * Sets (as xml) the "Trans_Num" element
             */
            void xsetTransNum(org.apache.xmlbeans.XmlString transNum);
            
            /**
             * Gets the "Bus_Date" element
             */
            java.lang.String getBusDate();
            
            /**
             * Gets (as xml) the "Bus_Date" element
             */
            org.apache.xmlbeans.XmlString xgetBusDate();
            
            /**
             * Sets the "Bus_Date" element
             */
            void setBusDate(java.lang.String busDate);
            
            /**
             * Sets (as xml) the "Bus_Date" element
             */
            void xsetBusDate(org.apache.xmlbeans.XmlString busDate);
            
            /**
             * Gets the "Txn_Date" element
             */
            java.lang.String getTxnDate();
            
            /**
             * Gets (as xml) the "Txn_Date" element
             */
            org.apache.xmlbeans.XmlString xgetTxnDate();
            
            /**
             * Sets the "Txn_Date" element
             */
            void setTxnDate(java.lang.String txnDate);
            
            /**
             * Sets (as xml) the "Txn_Date" element
             */
            void xsetTxnDate(org.apache.xmlbeans.XmlString txnDate);
            
            /**
             * Gets the "SM_Type" element
             */
            int getSMType();
            
            /**
             * Gets (as xml) the "SM_Type" element
             */
            org.apache.xmlbeans.XmlInt xgetSMType();
            
            /**
             * Sets the "SM_Type" element
             */
            void setSMType(int smType);
            
            /**
             * Sets (as xml) the "SM_Type" element
             */
            void xsetSMType(org.apache.xmlbeans.XmlInt smType);
            
            /**
             * Gets the "Cashier_ID" element
             */
            java.lang.String getCashierID();
            
            /**
             * Gets (as xml) the "Cashier_ID" element
             */
            org.apache.xmlbeans.XmlString xgetCashierID();
            
            /**
             * Sets the "Cashier_ID" element
             */
            void setCashierID(java.lang.String cashierID);
            
            /**
             * Sets (as xml) the "Cashier_ID" element
             */
            void xsetCashierID(org.apache.xmlbeans.XmlString cashierID);
            
            /**
             * Gets the "SalesMan_ID" element
             */
            java.lang.String getSalesManID();
            
            /**
             * Gets (as xml) the "SalesMan_ID" element
             */
            org.apache.xmlbeans.XmlString xgetSalesManID();
            
            /**
             * Sets the "SalesMan_ID" element
             */
            void setSalesManID(java.lang.String salesManID);
            
            /**
             * Sets (as xml) the "SalesMan_ID" element
             */
            void xsetSalesManID(org.apache.xmlbeans.XmlString salesManID);
            
            /**
             * Gets the "Salesman_Ext" element
             */
            java.lang.String getSalesmanExt();
            
            /**
             * Gets (as xml) the "Salesman_Ext" element
             */
            org.apache.xmlbeans.XmlString xgetSalesmanExt();
            
            /**
             * Sets the "Salesman_Ext" element
             */
            void setSalesmanExt(java.lang.String salesmanExt);
            
            /**
             * Sets (as xml) the "Salesman_Ext" element
             */
            void xsetSalesmanExt(org.apache.xmlbeans.XmlString salesmanExt);
            
            /**
             * Gets the "Team_Code" element
             */
            java.lang.String getTeamCode();
            
            /**
             * Gets (as xml) the "Team_Code" element
             */
            org.apache.xmlbeans.XmlString xgetTeamCode();
            
            /**
             * Sets the "Team_Code" element
             */
            void setTeamCode(java.lang.String teamCode);
            
            /**
             * Sets (as xml) the "Team_Code" element
             */
            void xsetTeamCode(org.apache.xmlbeans.XmlString teamCode);
            
            /**
             * Gets the "Channel" element
             */
            java.lang.String getChannel();
            
            /**
             * Gets (as xml) the "Channel" element
             */
            org.apache.xmlbeans.XmlString xgetChannel();
            
            /**
             * Sets the "Channel" element
             */
            void setChannel(java.lang.String channel);
            
            /**
             * Sets (as xml) the "Channel" element
             */
            void xsetChannel(org.apache.xmlbeans.XmlString channel);
            
            /**
             * Gets the "Salesman_Code" element
             */
            java.lang.String getSalesmanCode();
            
            /**
             * Gets (as xml) the "Salesman_Code" element
             */
            org.apache.xmlbeans.XmlString xgetSalesmanCode();
            
            /**
             * Sets the "Salesman_Code" element
             */
            void setSalesmanCode(java.lang.String salesmanCode);
            
            /**
             * Sets (as xml) the "Salesman_Code" element
             */
            void xsetSalesmanCode(org.apache.xmlbeans.XmlString salesmanCode);
            
            /**
             * Gets the "SM_ApprovedBy" element
             */
            java.lang.String getSMApprovedBy();
            
            /**
             * Gets (as xml) the "SM_ApprovedBy" element
             */
            org.apache.xmlbeans.XmlString xgetSMApprovedBy();
            
            /**
             * Sets the "SM_ApprovedBy" element
             */
            void setSMApprovedBy(java.lang.String smApprovedBy);
            
            /**
             * Sets (as xml) the "SM_ApprovedBy" element
             */
            void xsetSMApprovedBy(org.apache.xmlbeans.XmlString smApprovedBy);
            
            /**
             * Gets the "QU_Num" element
             */
            java.lang.String getQUNum();
            
            /**
             * Gets (as xml) the "QU_Num" element
             */
            org.apache.xmlbeans.XmlString xgetQUNum();
            
            /**
             * Sets the "QU_Num" element
             */
            void setQUNum(java.lang.String quNum);
            
            /**
             * Sets (as xml) the "QU_Num" element
             */
            void xsetQUNum(org.apache.xmlbeans.XmlString quNum);
            
            /**
             * Gets the "SM_Num" element
             */
            java.lang.String getSMNum();
            
            /**
             * Gets (as xml) the "SM_Num" element
             */
            org.apache.xmlbeans.XmlString xgetSMNum();
            
            /**
             * Sets the "SM_Num" element
             */
            void setSMNum(java.lang.String smNum);
            
            /**
             * Sets (as xml) the "SM_Num" element
             */
            void xsetSMNum(org.apache.xmlbeans.XmlString smNum);
            
            /**
             * Gets the "TotalAmt" element
             */
            double getTotalAmt();
            
            /**
             * Gets (as xml) the "TotalAmt" element
             */
            org.apache.xmlbeans.XmlDouble xgetTotalAmt();
            
            /**
             * Sets the "TotalAmt" element
             */
            void setTotalAmt(double totalAmt);
            
            /**
             * Sets (as xml) the "TotalAmt" element
             */
            void xsetTotalAmt(org.apache.xmlbeans.XmlDouble totalAmt);
            
            /**
             * Gets the "SI_Need" element
             */
            int getSINeed();
            
            /**
             * Gets (as xml) the "SI_Need" element
             */
            org.apache.xmlbeans.XmlInt xgetSINeed();
            
            /**
             * Sets the "SI_Need" element
             */
            void setSINeed(int siNeed);
            
            /**
             * Sets (as xml) the "SI_Need" element
             */
            void xsetSINeed(org.apache.xmlbeans.XmlInt siNeed);
            
            /**
             * Gets the "SM_CreateDate" element
             */
            java.lang.String getSMCreateDate();
            
            /**
             * Gets (as xml) the "SM_CreateDate" element
             */
            org.apache.xmlbeans.XmlString xgetSMCreateDate();
            
            /**
             * Sets the "SM_CreateDate" element
             */
            void setSMCreateDate(java.lang.String smCreateDate);
            
            /**
             * Sets (as xml) the "SM_CreateDate" element
             */
            void xsetSMCreateDate(org.apache.xmlbeans.XmlString smCreateDate);
            
            /**
             * Gets the "Modifyby" element
             */
            java.lang.String getModifyby();
            
            /**
             * Gets (as xml) the "Modifyby" element
             */
            org.apache.xmlbeans.XmlString xgetModifyby();
            
            /**
             * Sets the "Modifyby" element
             */
            void setModifyby(java.lang.String modifyby);
            
            /**
             * Sets (as xml) the "Modifyby" element
             */
            void xsetModifyby(org.apache.xmlbeans.XmlString modifyby);
            
            /**
             * Gets the "ModifyDate" element
             */
            java.lang.String getModifyDate();
            
            /**
             * Gets (as xml) the "ModifyDate" element
             */
            org.apache.xmlbeans.XmlString xgetModifyDate();
            
            /**
             * Sets the "ModifyDate" element
             */
            void setModifyDate(java.lang.String modifyDate);
            
            /**
             * Sets (as xml) the "ModifyDate" element
             */
            void xsetModifyDate(org.apache.xmlbeans.XmlString modifyDate);
            
            /**
             * Gets the "ConsolDate" element
             */
            java.lang.String getConsolDate();
            
            /**
             * Gets (as xml) the "ConsolDate" element
             */
            org.apache.xmlbeans.XmlString xgetConsolDate();
            
            /**
             * Sets the "ConsolDate" element
             */
            void setConsolDate(java.lang.String consolDate);
            
            /**
             * Sets (as xml) the "ConsolDate" element
             */
            void xsetConsolDate(org.apache.xmlbeans.XmlString consolDate);
            
            /**
             * Gets the "SettlementDate" element
             */
            java.lang.String getSettlementDate();
            
            /**
             * Gets (as xml) the "SettlementDate" element
             */
            org.apache.xmlbeans.XmlString xgetSettlementDate();
            
            /**
             * Sets the "SettlementDate" element
             */
            void setSettlementDate(java.lang.String settlementDate);
            
            /**
             * Sets (as xml) the "SettlementDate" element
             */
            void xsetSettlementDate(org.apache.xmlbeans.XmlString settlementDate);
            
            /**
             * Gets the "Settle_Log_Date" element
             */
            java.lang.String getSettleLogDate();
            
            /**
             * Gets (as xml) the "Settle_Log_Date" element
             */
            org.apache.xmlbeans.XmlString xgetSettleLogDate();
            
            /**
             * Sets the "Settle_Log_Date" element
             */
            void setSettleLogDate(java.lang.String settleLogDate);
            
            /**
             * Sets (as xml) the "Settle_Log_Date" element
             */
            void xsetSettleLogDate(org.apache.xmlbeans.XmlString settleLogDate);
            
            /**
             * Gets the "Settlement_Staff_ID" element
             */
            java.lang.String getSettlementStaffID();
            
            /**
             * Gets (as xml) the "Settlement_Staff_ID" element
             */
            org.apache.xmlbeans.XmlString xgetSettlementStaffID();
            
            /**
             * Sets the "Settlement_Staff_ID" element
             */
            void setSettlementStaffID(java.lang.String settlementStaffID);
            
            /**
             * Sets (as xml) the "Settlement_Staff_ID" element
             */
            void xsetSettlementStaffID(org.apache.xmlbeans.XmlString settlementStaffID);
            
            /**
             * Gets the "StaChgDate" element
             */
            java.lang.String getStaChgDate();
            
            /**
             * Gets (as xml) the "StaChgDate" element
             */
            org.apache.xmlbeans.XmlString xgetStaChgDate();
            
            /**
             * Sets the "StaChgDate" element
             */
            void setStaChgDate(java.lang.String staChgDate);
            
            /**
             * Sets (as xml) the "StaChgDate" element
             */
            void xsetStaChgDate(org.apache.xmlbeans.XmlString staChgDate);
            
            /**
             * Gets the "StaChgBy" element
             */
            java.lang.String getStaChgBy();
            
            /**
             * Gets (as xml) the "StaChgBy" element
             */
            org.apache.xmlbeans.XmlString xgetStaChgBy();
            
            /**
             * Sets the "StaChgBy" element
             */
            void setStaChgBy(java.lang.String staChgBy);
            
            /**
             * Sets (as xml) the "StaChgBy" element
             */
            void xsetStaChgBy(org.apache.xmlbeans.XmlString staChgBy);
            
            /**
             * Gets the "Pay_Settle_LogDate" element
             */
            java.lang.String getPaySettleLogDate();
            
            /**
             * Gets (as xml) the "Pay_Settle_LogDate" element
             */
            org.apache.xmlbeans.XmlString xgetPaySettleLogDate();
            
            /**
             * Sets the "Pay_Settle_LogDate" element
             */
            void setPaySettleLogDate(java.lang.String paySettleLogDate);
            
            /**
             * Sets (as xml) the "Pay_Settle_LogDate" element
             */
            void xsetPaySettleLogDate(org.apache.xmlbeans.XmlString paySettleLogDate);
            
            /**
             * Gets the "Pay_Settle_PhyDate" element
             */
            java.lang.String getPaySettlePhyDate();
            
            /**
             * Gets (as xml) the "Pay_Settle_PhyDate" element
             */
            org.apache.xmlbeans.XmlString xgetPaySettlePhyDate();
            
            /**
             * Sets the "Pay_Settle_PhyDate" element
             */
            void setPaySettlePhyDate(java.lang.String paySettlePhyDate);
            
            /**
             * Sets (as xml) the "Pay_Settle_PhyDate" element
             */
            void xsetPaySettlePhyDate(org.apache.xmlbeans.XmlString paySettlePhyDate);
            
            /**
             * Gets the "Deliver_Date" element
             */
            java.lang.String getDeliverDate();
            
            /**
             * Gets (as xml) the "Deliver_Date" element
             */
            org.apache.xmlbeans.XmlString xgetDeliverDate();
            
            /**
             * Sets the "Deliver_Date" element
             */
            void setDeliverDate(java.lang.String deliverDate);
            
            /**
             * Sets (as xml) the "Deliver_Date" element
             */
            void xsetDeliverDate(org.apache.xmlbeans.XmlString deliverDate);
            
            /**
             * Gets the "Status" element
             */
            int getStatus();
            
            /**
             * Gets (as xml) the "Status" element
             */
            org.apache.xmlbeans.XmlInt xgetStatus();
            
            /**
             * Sets the "Status" element
             */
            void setStatus(int status);
            
            /**
             * Sets (as xml) the "Status" element
             */
            void xsetStatus(org.apache.xmlbeans.XmlInt status);
            
            /**
             * Gets the "CPEFLAG" element
             */
            java.lang.String getCPEFLAG();
            
            /**
             * Gets (as xml) the "CPEFLAG" element
             */
            org.apache.xmlbeans.XmlString xgetCPEFLAG();
            
            /**
             * Sets the "CPEFLAG" element
             */
            void setCPEFLAG(java.lang.String cpeflag);
            
            /**
             * Sets (as xml) the "CPEFLAG" element
             */
            void xsetCPEFLAG(org.apache.xmlbeans.XmlString cpeflag);
            
            /**
             * Gets the "Trans_Discount" element
             */
            double getTransDiscount();
            
            /**
             * Gets (as xml) the "Trans_Discount" element
             */
            org.apache.xmlbeans.XmlDouble xgetTransDiscount();
            
            /**
             * Sets the "Trans_Discount" element
             */
            void setTransDiscount(double transDiscount);
            
            /**
             * Sets (as xml) the "Trans_Discount" element
             */
            void xsetTransDiscount(org.apache.xmlbeans.XmlDouble transDiscount);
            
            /**
             * Gets the "Trans_Reason" element
             */
            java.lang.String getTransReason();
            
            /**
             * Gets (as xml) the "Trans_Reason" element
             */
            org.apache.xmlbeans.XmlString xgetTransReason();
            
            /**
             * Sets the "Trans_Reason" element
             */
            void setTransReason(java.lang.String transReason);
            
            /**
             * Sets (as xml) the "Trans_Reason" element
             */
            void xsetTransReason(org.apache.xmlbeans.XmlString transReason);
            
            /**
             * Gets the "CPE_Work_Num" element
             */
            java.lang.String getCPEWorkNum();
            
            /**
             * Gets (as xml) the "CPE_Work_Num" element
             */
            org.apache.xmlbeans.XmlString xgetCPEWorkNum();
            
            /**
             * Sets the "CPE_Work_Num" element
             */
            void setCPEWorkNum(java.lang.String cpeWorkNum);
            
            /**
             * Sets (as xml) the "CPE_Work_Num" element
             */
            void xsetCPEWorkNum(org.apache.xmlbeans.XmlString cpeWorkNum);
            
            /**
             * Gets the "Converted" element
             */
            int getConverted();
            
            /**
             * Gets (as xml) the "Converted" element
             */
            org.apache.xmlbeans.XmlInt xgetConverted();
            
            /**
             * Sets the "Converted" element
             */
            void setConverted(int converted);
            
            /**
             * Sets (as xml) the "Converted" element
             */
            void xsetConverted(org.apache.xmlbeans.XmlInt converted);
            
            /**
             * Gets the "Org_QU_Num" element
             */
            java.lang.String getOrgQUNum();
            
            /**
             * Gets (as xml) the "Org_QU_Num" element
             */
            org.apache.xmlbeans.XmlString xgetOrgQUNum();
            
            /**
             * Sets the "Org_QU_Num" element
             */
            void setOrgQUNum(java.lang.String orgQUNum);
            
            /**
             * Sets (as xml) the "Org_QU_Num" element
             */
            void xsetOrgQUNum(org.apache.xmlbeans.XmlString orgQUNum);
            
            /**
             * Gets the "Ref_SM_No" element
             */
            java.lang.String getRefSMNo();
            
            /**
             * Gets (as xml) the "Ref_SM_No" element
             */
            org.apache.xmlbeans.XmlString xgetRefSMNo();
            
            /**
             * Sets the "Ref_SM_No" element
             */
            void setRefSMNo(java.lang.String refSMNo);
            
            /**
             * Sets (as xml) the "Ref_SM_No" element
             */
            void xsetRefSMNo(org.apache.xmlbeans.XmlString refSMNo);
            
            /**
             * Gets the "Invalidated_Flag" element
             */
            int getInvalidatedFlag();
            
            /**
             * Gets (as xml) the "Invalidated_Flag" element
             */
            org.apache.xmlbeans.XmlInt xgetInvalidatedFlag();
            
            /**
             * Sets the "Invalidated_Flag" element
             */
            void setInvalidatedFlag(int invalidatedFlag);
            
            /**
             * Sets (as xml) the "Invalidated_Flag" element
             */
            void xsetInvalidatedFlag(org.apache.xmlbeans.XmlInt invalidatedFlag);
            
            /**
             * Gets the "Download" element
             */
            int getDownload();
            
            /**
             * Gets (as xml) the "Download" element
             */
            org.apache.xmlbeans.XmlInt xgetDownload();
            
            /**
             * Sets the "Download" element
             */
            void setDownload(int download);
            
            /**
             * Sets (as xml) the "Download" element
             */
            void xsetDownload(org.apache.xmlbeans.XmlInt download);
            
            /**
             * Gets the "Print_Template" element
             */
            java.lang.String getPrintTemplate();
            
            /**
             * Gets (as xml) the "Print_Template" element
             */
            org.apache.xmlbeans.XmlString xgetPrintTemplate();
            
            /**
             * Sets the "Print_Template" element
             */
            void setPrintTemplate(java.lang.String printTemplate);
            
            /**
             * Sets (as xml) the "Print_Template" element
             */
            void xsetPrintTemplate(org.apache.xmlbeans.XmlString printTemplate);
            
            /**
             * Gets the "Store_Type" element
             */
            int getStoreType();
            
            /**
             * Gets (as xml) the "Store_Type" element
             */
            org.apache.xmlbeans.XmlInt xgetStoreType();
            
            /**
             * Sets the "Store_Type" element
             */
            void setStoreType(int storeType);
            
            /**
             * Sets (as xml) the "Store_Type" element
             */
            void xsetStoreType(org.apache.xmlbeans.XmlInt storeType);
            
            /**
             * Gets the "Store_Name" element
             */
            java.lang.String getStoreName();
            
            /**
             * Gets (as xml) the "Store_Name" element
             */
            org.apache.xmlbeans.XmlString xgetStoreName();
            
            /**
             * Sets the "Store_Name" element
             */
            void setStoreName(java.lang.String storeName);
            
            /**
             * Sets (as xml) the "Store_Name" element
             */
            void xsetStoreName(org.apache.xmlbeans.XmlString storeName);
            
            /**
             * Gets the "Store_Tel" element
             */
            java.lang.String getStoreTel();
            
            /**
             * Gets (as xml) the "Store_Tel" element
             */
            org.apache.xmlbeans.XmlString xgetStoreTel();
            
            /**
             * Sets the "Store_Tel" element
             */
            void setStoreTel(java.lang.String storeTel);
            
            /**
             * Sets (as xml) the "Store_Tel" element
             */
            void xsetStoreTel(org.apache.xmlbeans.XmlString storeTel);
            
            /**
             * Gets the "Store_Begin_Hours" element
             */
            java.lang.String getStoreBeginHours();
            
            /**
             * Gets (as xml) the "Store_Begin_Hours" element
             */
            org.apache.xmlbeans.XmlString xgetStoreBeginHours();
            
            /**
             * Sets the "Store_Begin_Hours" element
             */
            void setStoreBeginHours(java.lang.String storeBeginHours);
            
            /**
             * Sets (as xml) the "Store_Begin_Hours" element
             */
            void xsetStoreBeginHours(org.apache.xmlbeans.XmlString storeBeginHours);
            
            /**
             * Gets the "Store_End_Hours" element
             */
            java.lang.String getStoreEndHours();
            
            /**
             * Gets (as xml) the "Store_End_Hours" element
             */
            org.apache.xmlbeans.XmlString xgetStoreEndHours();
            
            /**
             * Sets the "Store_End_Hours" element
             */
            void setStoreEndHours(java.lang.String storeEndHours);
            
            /**
             * Sets (as xml) the "Store_End_Hours" element
             */
            void xsetStoreEndHours(org.apache.xmlbeans.XmlString storeEndHours);
            
            /**
             * Gets the "Store_Addr_1" element
             */
            java.lang.String getStoreAddr1();
            
            /**
             * Gets (as xml) the "Store_Addr_1" element
             */
            org.apache.xmlbeans.XmlString xgetStoreAddr1();
            
            /**
             * Sets the "Store_Addr_1" element
             */
            void setStoreAddr1(java.lang.String storeAddr1);
            
            /**
             * Sets (as xml) the "Store_Addr_1" element
             */
            void xsetStoreAddr1(org.apache.xmlbeans.XmlString storeAddr1);
            
            /**
             * Gets the "Store_Addr_2" element
             */
            java.lang.String getStoreAddr2();
            
            /**
             * Gets (as xml) the "Store_Addr_2" element
             */
            org.apache.xmlbeans.XmlString xgetStoreAddr2();
            
            /**
             * Sets the "Store_Addr_2" element
             */
            void setStoreAddr2(java.lang.String storeAddr2);
            
            /**
             * Sets (as xml) the "Store_Addr_2" element
             */
            void xsetStoreAddr2(org.apache.xmlbeans.XmlString storeAddr2);
            
            /**
             * Gets the "Store_Addr_3" element
             */
            java.lang.String getStoreAddr3();
            
            /**
             * Gets (as xml) the "Store_Addr_3" element
             */
            org.apache.xmlbeans.XmlString xgetStoreAddr3();
            
            /**
             * Sets the "Store_Addr_3" element
             */
            void setStoreAddr3(java.lang.String storeAddr3);
            
            /**
             * Sets (as xml) the "Store_Addr_3" element
             */
            void xsetStoreAddr3(org.apache.xmlbeans.XmlString storeAddr3);
            
            /**
             * Gets the "Salesman_Name" element
             */
            java.lang.String getSalesmanName();
            
            /**
             * Gets (as xml) the "Salesman_Name" element
             */
            org.apache.xmlbeans.XmlString xgetSalesmanName();
            
            /**
             * Sets the "Salesman_Name" element
             */
            void setSalesmanName(java.lang.String salesmanName);
            
            /**
             * Sets (as xml) the "Salesman_Name" element
             */
            void xsetSalesmanName(org.apache.xmlbeans.XmlString salesmanName);
            
            /**
             * Gets the "Settle_Staff_Name" element
             */
            java.lang.String getSettleStaffName();
            
            /**
             * Gets (as xml) the "Settle_Staff_Name" element
             */
            org.apache.xmlbeans.XmlString xgetSettleStaffName();
            
            /**
             * Sets the "Settle_Staff_Name" element
             */
            void setSettleStaffName(java.lang.String settleStaffName);
            
            /**
             * Sets (as xml) the "Settle_Staff_Name" element
             */
            void xsetSettleStaffName(org.apache.xmlbeans.XmlString settleStaffName);
            
            /**
             * Gets the "CMR" element
             */
            java.lang.String getCMR();
            
            /**
             * Gets (as xml) the "CMR" element
             */
            org.apache.xmlbeans.XmlString xgetCMR();
            
            /**
             * Sets the "CMR" element
             */
            void setCMR(java.lang.String cmr);
            
            /**
             * Sets (as xml) the "CMR" element
             */
            void xsetCMR(org.apache.xmlbeans.XmlString cmr);
            
            /**
             * Gets the "UM" element
             */
            java.lang.String getUM();
            
            /**
             * Gets (as xml) the "UM" element
             */
            org.apache.xmlbeans.XmlString xgetUM();
            
            /**
             * Sets the "UM" element
             */
            void setUM(java.lang.String um);
            
            /**
             * Sets (as xml) the "UM" element
             */
            void xsetUM(org.apache.xmlbeans.XmlString um);
            
            /**
             * Gets the "Title" element
             */
            java.lang.String getTitle();
            
            /**
             * Gets (as xml) the "Title" element
             */
            org.apache.xmlbeans.XmlString xgetTitle();
            
            /**
             * Sets the "Title" element
             */
            void setTitle(java.lang.String title);
            
            /**
             * Sets (as xml) the "Title" element
             */
            void xsetTitle(org.apache.xmlbeans.XmlString title);
            
            /**
             * Gets the "Team_Name" element
             */
            java.lang.String getTeamName();
            
            /**
             * Gets (as xml) the "Team_Name" element
             */
            org.apache.xmlbeans.XmlString xgetTeamName();
            
            /**
             * Sets the "Team_Name" element
             */
            void setTeamName(java.lang.String teamName);
            
            /**
             * Sets (as xml) the "Team_Name" element
             */
            void xsetTeamName(org.apache.xmlbeans.XmlString teamName);
            
            /**
             * Gets the "Dep" element
             */
            java.lang.String getDep();
            
            /**
             * Gets (as xml) the "Dep" element
             */
            org.apache.xmlbeans.XmlString xgetDep();
            
            /**
             * Sets the "Dep" element
             */
            void setDep(java.lang.String dep);
            
            /**
             * Sets (as xml) the "Dep" element
             */
            void xsetDep(org.apache.xmlbeans.XmlString dep);
            
            /**
             * Gets the "Dep_Name" element
             */
            java.lang.String getDepName();
            
            /**
             * Gets (as xml) the "Dep_Name" element
             */
            org.apache.xmlbeans.XmlString xgetDepName();
            
            /**
             * Sets the "Dep_Name" element
             */
            void setDepName(java.lang.String depName);
            
            /**
             * Sets (as xml) the "Dep_Name" element
             */
            void xsetDepName(org.apache.xmlbeans.XmlString depName);
            
            /**
             * Gets the "Coll_Time" element
             */
            java.lang.String getCollTime();
            
            /**
             * Gets (as xml) the "Coll_Time" element
             */
            org.apache.xmlbeans.XmlString xgetCollTime();
            
            /**
             * Sets the "Coll_Time" element
             */
            void setCollTime(java.lang.String collTime);
            
            /**
             * Sets (as xml) the "Coll_Time" element
             */
            void xsetCollTime(org.apache.xmlbeans.XmlString collTime);
            
            /**
             * Gets the "IMS_Acno" element
             */
            java.lang.String getIMSAcno();
            
            /**
             * Gets (as xml) the "IMS_Acno" element
             */
            org.apache.xmlbeans.XmlString xgetIMSAcno();
            
            /**
             * Sets the "IMS_Acno" element
             */
            void setIMSAcno(java.lang.String imsAcno);
            
            /**
             * Sets (as xml) the "IMS_Acno" element
             */
            void xsetIMSAcno(org.apache.xmlbeans.XmlString imsAcno);
            
            /**
             * Gets the "Coll_Date" element
             */
            java.lang.String getCollDate();
            
            /**
             * Gets (as xml) the "Coll_Date" element
             */
            org.apache.xmlbeans.XmlString xgetCollDate();
            
            /**
             * Sets the "Coll_Date" element
             */
            void setCollDate(java.lang.String collDate);
            
            /**
             * Sets (as xml) the "Coll_Date" element
             */
            void xsetCollDate(org.apache.xmlbeans.XmlString collDate);
            
            /**
             * Gets the "Paid_Amt" element
             */
            double getPaidAmt();
            
            /**
             * Gets (as xml) the "Paid_Amt" element
             */
            org.apache.xmlbeans.XmlDouble xgetPaidAmt();
            
            /**
             * Sets the "Paid_Amt" element
             */
            void setPaidAmt(double paidAmt);
            
            /**
             * Sets (as xml) the "Paid_Amt" element
             */
            void xsetPaidAmt(org.apache.xmlbeans.XmlDouble paidAmt);
            
            /**
             * Gets the "Ref_SM_Type" element
             */
            int getRefSMType();
            
            /**
             * Gets (as xml) the "Ref_SM_Type" element
             */
            org.apache.xmlbeans.XmlInt xgetRefSMType();
            
            /**
             * Sets the "Ref_SM_Type" element
             */
            void setRefSMType(int refSMType);
            
            /**
             * Sets (as xml) the "Ref_SM_Type" element
             */
            void xsetRefSMType(org.apache.xmlbeans.XmlInt refSMType);
            
            /**
             * Gets the "Txn_Time_POS_BOM" element
             */
            java.lang.String getTxnTimePOSBOM();
            
            /**
             * Gets (as xml) the "Txn_Time_POS_BOM" element
             */
            org.apache.xmlbeans.XmlString xgetTxnTimePOSBOM();
            
            /**
             * Sets the "Txn_Time_POS_BOM" element
             */
            void setTxnTimePOSBOM(java.lang.String txnTimePOSBOM);
            
            /**
             * Sets (as xml) the "Txn_Time_POS_BOM" element
             */
            void xsetTxnTimePOSBOM(org.apache.xmlbeans.XmlString txnTimePOSBOM);
            
            /**
             * Gets the "Txn_Date_POS_BOM" element
             */
            java.lang.String getTxnDatePOSBOM();
            
            /**
             * Gets (as xml) the "Txn_Date_POS_BOM" element
             */
            org.apache.xmlbeans.XmlString xgetTxnDatePOSBOM();
            
            /**
             * Sets the "Txn_Date_POS_BOM" element
             */
            void setTxnDatePOSBOM(java.lang.String txnDatePOSBOM);
            
            /**
             * Sets (as xml) the "Txn_Date_POS_BOM" element
             */
            void xsetTxnDatePOSBOM(org.apache.xmlbeans.XmlString txnDatePOSBOM);
            
            /**
             * Gets the "Req_No" element
             */
            java.lang.String getReqNo();
            
            /**
             * Gets (as xml) the "Req_No" element
             */
            org.apache.xmlbeans.XmlString xgetReqNo();
            
            /**
             * Sets the "Req_No" element
             */
            void setReqNo(java.lang.String reqNo);
            
            /**
             * Sets (as xml) the "Req_No" element
             */
            void xsetReqNo(org.apache.xmlbeans.XmlString reqNo);
            
            /**
             * Gets the "Customer_ID" element
             */
            java.lang.String getCustomerID();
            
            /**
             * Gets (as xml) the "Customer_ID" element
             */
            org.apache.xmlbeans.XmlString xgetCustomerID();
            
            /**
             * Sets the "Customer_ID" element
             */
            void setCustomerID(java.lang.String customerID);
            
            /**
             * Sets (as xml) the "Customer_ID" element
             */
            void xsetCustomerID(org.apache.xmlbeans.XmlString customerID);
            
            /**
             * Gets the "Account_Code" element
             */
            java.lang.String getAccountCode();
            
            /**
             * Gets (as xml) the "Account_Code" element
             */
            org.apache.xmlbeans.XmlString xgetAccountCode();
            
            /**
             * Sets the "Account_Code" element
             */
            void setAccountCode(java.lang.String accountCode);
            
            /**
             * Sets (as xml) the "Account_Code" element
             */
            void xsetAccountCode(org.apache.xmlbeans.XmlString accountCode);
            
            /**
             * Gets the "SL_ID" element
             */
            java.lang.String getSLID();
            
            /**
             * Gets (as xml) the "SL_ID" element
             */
            org.apache.xmlbeans.XmlString xgetSLID();
            
            /**
             * Sets the "SL_ID" element
             */
            void setSLID(java.lang.String slid);
            
            /**
             * Sets (as xml) the "SL_ID" element
             */
            void xsetSLID(org.apache.xmlbeans.XmlString slid);
            
            /**
             * Gets the "Print_SM" element
             */
            java.lang.String getPrintSM();
            
            /**
             * Gets (as xml) the "Print_SM" element
             */
            org.apache.xmlbeans.XmlString xgetPrintSM();
            
            /**
             * Sets the "Print_SM" element
             */
            void setPrintSM(java.lang.String printSM);
            
            /**
             * Sets (as xml) the "Print_SM" element
             */
            void xsetPrintSM(org.apache.xmlbeans.XmlString printSM);
            
            /**
             * Gets the "Gen_SM_Pdf" element
             */
            java.lang.String getGenSMPdf();
            
            /**
             * Gets (as xml) the "Gen_SM_Pdf" element
             */
            org.apache.xmlbeans.XmlString xgetGenSMPdf();
            
            /**
             * Sets the "Gen_SM_Pdf" element
             */
            void setGenSMPdf(java.lang.String genSMPdf);
            
            /**
             * Sets (as xml) the "Gen_SM_Pdf" element
             */
            void xsetGenSMPdf(org.apache.xmlbeans.XmlString genSMPdf);
            
            /**
             * A factory class with static methods for creating instances
             * of this type.
             */
            
            public static final class Factory
            {
                public static noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderReg newInstance() {
                  return (noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderReg) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
                
                public static noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderReg newInstance(org.apache.xmlbeans.XmlOptions options) {
                  return (noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderReg) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
                
                private Factory() { } // No instance of this class allowed
            }
        }
        
        /**
         * An XML customer(@).
         *
         * This is a complex type.
         */
        public interface Customer extends org.apache.xmlbeans.XmlObject
        {
            public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
                org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Customer.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s1FF98B5715F9EE67BF5AE52C2CFC4EE4").resolveHandle("customered57elemtype");
            
            /**
             * Gets the "CU_Title" element
             */
            java.lang.String getCUTitle();
            
            /**
             * Gets (as xml) the "CU_Title" element
             */
            org.apache.xmlbeans.XmlString xgetCUTitle();
            
            /**
             * Sets the "CU_Title" element
             */
            void setCUTitle(java.lang.String cuTitle);
            
            /**
             * Sets (as xml) the "CU_Title" element
             */
            void xsetCUTitle(org.apache.xmlbeans.XmlString cuTitle);
            
            /**
             * Gets the "CU_Name" element
             */
            java.lang.String getCUName();
            
            /**
             * Gets (as xml) the "CU_Name" element
             */
            org.apache.xmlbeans.XmlString xgetCUName();
            
            /**
             * Sets the "CU_Name" element
             */
            void setCUName(java.lang.String cuName);
            
            /**
             * Sets (as xml) the "CU_Name" element
             */
            void xsetCUName(org.apache.xmlbeans.XmlString cuName);
            
            /**
             * Gets the "CU_CCCNO" element
             */
            java.lang.String getCUCCCNO();
            
            /**
             * Gets (as xml) the "CU_CCCNO" element
             */
            org.apache.xmlbeans.XmlString xgetCUCCCNO();
            
            /**
             * Sets the "CU_CCCNO" element
             */
            void setCUCCCNO(java.lang.String cucccno);
            
            /**
             * Sets (as xml) the "CU_CCCNO" element
             */
            void xsetCUCCCNO(org.apache.xmlbeans.XmlString cucccno);
            
            /**
             * Gets the "CU_Tel" element
             */
            java.lang.String getCUTel();
            
            /**
             * Gets (as xml) the "CU_Tel" element
             */
            org.apache.xmlbeans.XmlString xgetCUTel();
            
            /**
             * Sets the "CU_Tel" element
             */
            void setCUTel(java.lang.String cuTel);
            
            /**
             * Sets (as xml) the "CU_Tel" element
             */
            void xsetCUTel(org.apache.xmlbeans.XmlString cuTel);
            
            /**
             * Gets the "CU_Prime" element
             */
            java.lang.String getCUPrime();
            
            /**
             * Gets (as xml) the "CU_Prime" element
             */
            org.apache.xmlbeans.XmlString xgetCUPrime();
            
            /**
             * Sets the "CU_Prime" element
             */
            void setCUPrime(java.lang.String cuPrime);
            
            /**
             * Sets (as xml) the "CU_Prime" element
             */
            void xsetCUPrime(org.apache.xmlbeans.XmlString cuPrime);
            
            /**
             * Gets the "CU_FaxNo" element
             */
            java.lang.String getCUFaxNo();
            
            /**
             * Gets (as xml) the "CU_FaxNo" element
             */
            org.apache.xmlbeans.XmlString xgetCUFaxNo();
            
            /**
             * Sets the "CU_FaxNo" element
             */
            void setCUFaxNo(java.lang.String cuFaxNo);
            
            /**
             * Sets (as xml) the "CU_FaxNo" element
             */
            void xsetCUFaxNo(org.apache.xmlbeans.XmlString cuFaxNo);
            
            /**
             * Gets the "CU_MemType" element
             */
            java.lang.String getCUMemType();
            
            /**
             * Gets (as xml) the "CU_MemType" element
             */
            org.apache.xmlbeans.XmlString xgetCUMemType();
            
            /**
             * Sets the "CU_MemType" element
             */
            void setCUMemType(java.lang.String cuMemType);
            
            /**
             * Sets (as xml) the "CU_MemType" element
             */
            void xsetCUMemType(org.apache.xmlbeans.XmlString cuMemType);
            
            /**
             * Gets the "CU_ContPer" element
             */
            java.lang.String getCUContPer();
            
            /**
             * Gets (as xml) the "CU_ContPer" element
             */
            org.apache.xmlbeans.XmlString xgetCUContPer();
            
            /**
             * Sets the "CU_ContPer" element
             */
            void setCUContPer(java.lang.String cuContPer);
            
            /**
             * Sets (as xml) the "CU_ContPer" element
             */
            void xsetCUContPer(org.apache.xmlbeans.XmlString cuContPer);
            
            /**
             * Gets the "CU_MemberNo" element
             */
            java.lang.String getCUMemberNo();
            
            /**
             * Gets (as xml) the "CU_MemberNo" element
             */
            org.apache.xmlbeans.XmlString xgetCUMemberNo();
            
            /**
             * Sets the "CU_MemberNo" element
             */
            void setCUMemberNo(java.lang.String cuMemberNo);
            
            /**
             * Sets (as xml) the "CU_MemberNo" element
             */
            void xsetCUMemberNo(org.apache.xmlbeans.XmlString cuMemberNo);
            
            /**
             * Gets the "CU_InterCom" element
             */
            boolean getCUInterCom();
            
            /**
             * Gets (as xml) the "CU_InterCom" element
             */
            org.apache.xmlbeans.XmlBoolean xgetCUInterCom();
            
            /**
             * Sets the "CU_InterCom" element
             */
            void setCUInterCom(boolean cuInterCom);
            
            /**
             * Sets (as xml) the "CU_InterCom" element
             */
            void xsetCUInterCom(org.apache.xmlbeans.XmlBoolean cuInterCom);
            
            /**
             * Gets the "CU_IDBusType" element
             */
            java.lang.String getCUIDBusType();
            
            /**
             * Gets (as xml) the "CU_IDBusType" element
             */
            org.apache.xmlbeans.XmlString xgetCUIDBusType();
            
            /**
             * Sets the "CU_IDBusType" element
             */
            void setCUIDBusType(java.lang.String cuidBusType);
            
            /**
             * Sets (as xml) the "CU_IDBusType" element
             */
            void xsetCUIDBusType(org.apache.xmlbeans.XmlString cuidBusType);
            
            /**
             * Gets the "CU_IDBusNo" element
             */
            java.lang.String getCUIDBusNo();
            
            /**
             * Gets (as xml) the "CU_IDBusNo" element
             */
            org.apache.xmlbeans.XmlString xgetCUIDBusNo();
            
            /**
             * Sets the "CU_IDBusNo" element
             */
            void setCUIDBusNo(java.lang.String cuidBusNo);
            
            /**
             * Sets (as xml) the "CU_IDBusNo" element
             */
            void xsetCUIDBusNo(org.apache.xmlbeans.XmlString cuidBusNo);
            
            /**
             * Gets the "CU_Remark" element
             */
            java.lang.String getCURemark();
            
            /**
             * Gets (as xml) the "CU_Remark" element
             */
            org.apache.xmlbeans.XmlString xgetCURemark();
            
            /**
             * Sets the "CU_Remark" element
             */
            void setCURemark(java.lang.String cuRemark);
            
            /**
             * Sets (as xml) the "CU_Remark" element
             */
            void xsetCURemark(org.apache.xmlbeans.XmlString cuRemark);
            
            /**
             * Gets the "CU_Int_Remarks" element
             */
            java.lang.String getCUIntRemarks();
            
            /**
             * Gets (as xml) the "CU_Int_Remarks" element
             */
            org.apache.xmlbeans.XmlString xgetCUIntRemarks();
            
            /**
             * Sets the "CU_Int_Remarks" element
             */
            void setCUIntRemarks(java.lang.String cuIntRemarks);
            
            /**
             * Sets (as xml) the "CU_Int_Remarks" element
             */
            void xsetCUIntRemarks(org.apache.xmlbeans.XmlString cuIntRemarks);
            
            /**
             * Gets the "CU_Request_Date" element
             */
            java.lang.String getCURequestDate();
            
            /**
             * Gets (as xml) the "CU_Request_Date" element
             */
            org.apache.xmlbeans.XmlString xgetCURequestDate();
            
            /**
             * Sets the "CU_Request_Date" element
             */
            void setCURequestDate(java.lang.String cuRequestDate);
            
            /**
             * Sets (as xml) the "CU_Request_Date" element
             */
            void xsetCURequestDate(org.apache.xmlbeans.XmlString cuRequestDate);
            
            /**
             * Gets the "CU_Request_Time" element
             */
            java.lang.String getCURequestTime();
            
            /**
             * Gets (as xml) the "CU_Request_Time" element
             */
            org.apache.xmlbeans.XmlString xgetCURequestTime();
            
            /**
             * Sets the "CU_Request_Time" element
             */
            void setCURequestTime(java.lang.String cuRequestTime);
            
            /**
             * Sets (as xml) the "CU_Request_Time" element
             */
            void xsetCURequestTime(org.apache.xmlbeans.XmlString cuRequestTime);
            
            /**
             * Gets the "CU_PosBadDebt" element
             */
            boolean getCUPosBadDebt();
            
            /**
             * Gets (as xml) the "CU_PosBadDebt" element
             */
            org.apache.xmlbeans.XmlBoolean xgetCUPosBadDebt();
            
            /**
             * Sets the "CU_PosBadDebt" element
             */
            void setCUPosBadDebt(boolean cuPosBadDebt);
            
            /**
             * Sets (as xml) the "CU_PosBadDebt" element
             */
            void xsetCUPosBadDebt(org.apache.xmlbeans.XmlBoolean cuPosBadDebt);
            
            /**
             * Gets the "CU_Email" element
             */
            java.lang.String getCUEmail();
            
            /**
             * Gets (as xml) the "CU_Email" element
             */
            org.apache.xmlbeans.XmlString xgetCUEmail();
            
            /**
             * Sets the "CU_Email" element
             */
            void setCUEmail(java.lang.String cuEmail);
            
            /**
             * Sets (as xml) the "CU_Email" element
             */
            void xsetCUEmail(org.apache.xmlbeans.XmlString cuEmail);
            
            /**
             * A factory class with static methods for creating instances
             * of this type.
             */
            
            public static final class Factory
            {
                public static noNamespace.OrderRegInfoDocument.OrderRegInfo.Customer newInstance() {
                  return (noNamespace.OrderRegInfoDocument.OrderRegInfo.Customer) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
                
                public static noNamespace.OrderRegInfoDocument.OrderRegInfo.Customer newInstance(org.apache.xmlbeans.XmlOptions options) {
                  return (noNamespace.OrderRegInfoDocument.OrderRegInfo.Customer) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
                
                private Factory() { } // No instance of this class allowed
            }
        }
        
        /**
         * An XML Address(@).
         *
         * This is a complex type.
         */
        public interface Address extends org.apache.xmlbeans.XmlObject
        {
            public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
                org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Address.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s1FF98B5715F9EE67BF5AE52C2CFC4EE4").resolveHandle("address54bdelemtype");
            
            /**
             * Gets the "CU_CFLATUNIT" element
             */
            java.lang.String getCUCFLATUNIT();
            
            /**
             * Gets (as xml) the "CU_CFLATUNIT" element
             */
            org.apache.xmlbeans.XmlString xgetCUCFLATUNIT();
            
            /**
             * Sets the "CU_CFLATUNIT" element
             */
            void setCUCFLATUNIT(java.lang.String cucflatunit);
            
            /**
             * Sets (as xml) the "CU_CFLATUNIT" element
             */
            void xsetCUCFLATUNIT(org.apache.xmlbeans.XmlString cucflatunit);
            
            /**
             * Gets the "CU_CFLOOR" element
             */
            java.lang.String getCUCFLOOR();
            
            /**
             * Gets (as xml) the "CU_CFLOOR" element
             */
            org.apache.xmlbeans.XmlString xgetCUCFLOOR();
            
            /**
             * Sets the "CU_CFLOOR" element
             */
            void setCUCFLOOR(java.lang.String cucfloor);
            
            /**
             * Sets (as xml) the "CU_CFLOOR" element
             */
            void xsetCUCFLOOR(org.apache.xmlbeans.XmlString cucfloor);
            
            /**
             * Gets the "CU_CBLDG" element
             */
            java.lang.String getCUCBLDG();
            
            /**
             * Gets (as xml) the "CU_CBLDG" element
             */
            org.apache.xmlbeans.XmlString xgetCUCBLDG();
            
            /**
             * Sets the "CU_CBLDG" element
             */
            void setCUCBLDG(java.lang.String cucbldg);
            
            /**
             * Sets (as xml) the "CU_CBLDG" element
             */
            void xsetCUCBLDG(org.apache.xmlbeans.XmlString cucbldg);
            
            /**
             * Gets the "CU_CPOBOX" element
             */
            java.lang.String getCUCPOBOX();
            
            /**
             * Gets (as xml) the "CU_CPOBOX" element
             */
            org.apache.xmlbeans.XmlString xgetCUCPOBOX();
            
            /**
             * Sets the "CU_CPOBOX" element
             */
            void setCUCPOBOX(java.lang.String cucpobox);
            
            /**
             * Sets (as xml) the "CU_CPOBOX" element
             */
            void xsetCUCPOBOX(org.apache.xmlbeans.XmlString cucpobox);
            
            /**
             * Gets the "CU_CHSESTNO" element
             */
            java.lang.String getCUCHSESTNO();
            
            /**
             * Gets (as xml) the "CU_CHSESTNO" element
             */
            org.apache.xmlbeans.XmlString xgetCUCHSESTNO();
            
            /**
             * Sets the "CU_CHSESTNO" element
             */
            void setCUCHSESTNO(java.lang.String cuchsestno);
            
            /**
             * Sets (as xml) the "CU_CHSESTNO" element
             */
            void xsetCUCHSESTNO(org.apache.xmlbeans.XmlString cuchsestno);
            
            /**
             * Gets the "CU_CLOTLDNO" element
             */
            java.lang.String getCUCLOTLDNO();
            
            /**
             * Gets (as xml) the "CU_CLOTLDNO" element
             */
            org.apache.xmlbeans.XmlString xgetCUCLOTLDNO();
            
            /**
             * Sets the "CU_CLOTLDNO" element
             */
            void setCUCLOTLDNO(java.lang.String cuclotldno);
            
            /**
             * Sets (as xml) the "CU_CLOTLDNO" element
             */
            void xsetCUCLOTLDNO(org.apache.xmlbeans.XmlString cuclotldno);
            
            /**
             * Gets the "CU_CSTNAME" element
             */
            java.lang.String getCUCSTNAME();
            
            /**
             * Gets (as xml) the "CU_CSTNAME" element
             */
            org.apache.xmlbeans.XmlString xgetCUCSTNAME();
            
            /**
             * Sets the "CU_CSTNAME" element
             */
            void setCUCSTNAME(java.lang.String cucstname);
            
            /**
             * Sets (as xml) the "CU_CSTNAME" element
             */
            void xsetCUCSTNAME(org.apache.xmlbeans.XmlString cucstname);
            
            /**
             * Gets the "CU_CSTCAT" element
             */
            java.lang.String getCUCSTCAT();
            
            /**
             * Gets (as xml) the "CU_CSTCAT" element
             */
            org.apache.xmlbeans.XmlString xgetCUCSTCAT();
            
            /**
             * Sets the "CU_CSTCAT" element
             */
            void setCUCSTCAT(java.lang.String cucstcat);
            
            /**
             * Sets (as xml) the "CU_CSTCAT" element
             */
            void xsetCUCSTCAT(org.apache.xmlbeans.XmlString cucstcat);
            
            /**
             * Gets the "CU_CSECT" element
             */
            java.lang.String getCUCSECT();
            
            /**
             * Gets (as xml) the "CU_CSECT" element
             */
            org.apache.xmlbeans.XmlString xgetCUCSECT();
            
            /**
             * Sets the "CU_CSECT" element
             */
            void setCUCSECT(java.lang.String cucsect);
            
            /**
             * Sets (as xml) the "CU_CSECT" element
             */
            void xsetCUCSECT(org.apache.xmlbeans.XmlString cucsect);
            
            /**
             * Gets the "CU_CDISTR" element
             */
            java.lang.String getCUCDISTR();
            
            /**
             * Gets (as xml) the "CU_CDISTR" element
             */
            org.apache.xmlbeans.XmlString xgetCUCDISTR();
            
            /**
             * Sets the "CU_CDISTR" element
             */
            void setCUCDISTR(java.lang.String cucdistr);
            
            /**
             * Sets (as xml) the "CU_CDISTR" element
             */
            void xsetCUCDISTR(org.apache.xmlbeans.XmlString cucdistr);
            
            /**
             * Gets the "CU_CAREA" element
             */
            java.lang.String getCUCAREA();
            
            /**
             * Gets (as xml) the "CU_CAREA" element
             */
            org.apache.xmlbeans.XmlString xgetCUCAREA();
            
            /**
             * Sets the "CU_CAREA" element
             */
            void setCUCAREA(java.lang.String cucarea);
            
            /**
             * Sets (as xml) the "CU_CAREA" element
             */
            void xsetCUCAREA(org.apache.xmlbeans.XmlString cucarea);
            
            /**
             * Gets the "CU_CPOSTREGN" element
             */
            java.lang.String getCUCPOSTREGN();
            
            /**
             * Gets (as xml) the "CU_CPOSTREGN" element
             */
            org.apache.xmlbeans.XmlString xgetCUCPOSTREGN();
            
            /**
             * Sets the "CU_CPOSTREGN" element
             */
            void setCUCPOSTREGN(java.lang.String cucpostregn);
            
            /**
             * Sets (as xml) the "CU_CPOSTREGN" element
             */
            void xsetCUCPOSTREGN(org.apache.xmlbeans.XmlString cucpostregn);
            
            /**
             * Gets the "CU_CPOSTCD" element
             */
            java.lang.String getCUCPOSTCD();
            
            /**
             * Gets (as xml) the "CU_CPOSTCD" element
             */
            org.apache.xmlbeans.XmlString xgetCUCPOSTCD();
            
            /**
             * Sets the "CU_CPOSTCD" element
             */
            void setCUCPOSTCD(java.lang.String cucpostcd);
            
            /**
             * Sets (as xml) the "CU_CPOSTCD" element
             */
            void xsetCUCPOSTCD(org.apache.xmlbeans.XmlString cucpostcd);
            
            /**
             * Gets the "CU_CCOUNTRY" element
             */
            java.lang.String getCUCCOUNTRY();
            
            /**
             * Gets (as xml) the "CU_CCOUNTRY" element
             */
            org.apache.xmlbeans.XmlString xgetCUCCOUNTRY();
            
            /**
             * Sets the "CU_CCOUNTRY" element
             */
            void setCUCCOUNTRY(java.lang.String cuccountry);
            
            /**
             * Sets (as xml) the "CU_CCOUNTRY" element
             */
            void xsetCUCCOUNTRY(org.apache.xmlbeans.XmlString cuccountry);
            
            /**
             * Gets the "CU_CATTETO" element
             */
            java.lang.String getCUCATTETO();
            
            /**
             * Gets (as xml) the "CU_CATTETO" element
             */
            org.apache.xmlbeans.XmlString xgetCUCATTETO();
            
            /**
             * Sets the "CU_CATTETO" element
             */
            void setCUCATTETO(java.lang.String cucatteto);
            
            /**
             * Sets (as xml) the "CU_CATTETO" element
             */
            void xsetCUCATTETO(org.apache.xmlbeans.XmlString cucatteto);
            
            /**
             * Gets the "CU_CCO" element
             */
            java.lang.String getCUCCO();
            
            /**
             * Gets (as xml) the "CU_CCO" element
             */
            org.apache.xmlbeans.XmlString xgetCUCCO();
            
            /**
             * Sets the "CU_CCO" element
             */
            void setCUCCO(java.lang.String cucco);
            
            /**
             * Sets (as xml) the "CU_CCO" element
             */
            void xsetCUCCO(org.apache.xmlbeans.XmlString cucco);
            
            /**
             * Gets the "CU_IFLATUNIT" element
             */
            java.lang.String getCUIFLATUNIT();
            
            /**
             * Gets (as xml) the "CU_IFLATUNIT" element
             */
            org.apache.xmlbeans.XmlString xgetCUIFLATUNIT();
            
            /**
             * Sets the "CU_IFLATUNIT" element
             */
            void setCUIFLATUNIT(java.lang.String cuiflatunit);
            
            /**
             * Sets (as xml) the "CU_IFLATUNIT" element
             */
            void xsetCUIFLATUNIT(org.apache.xmlbeans.XmlString cuiflatunit);
            
            /**
             * Gets the "CU_IFLOOR" element
             */
            java.lang.String getCUIFLOOR();
            
            /**
             * Gets (as xml) the "CU_IFLOOR" element
             */
            org.apache.xmlbeans.XmlString xgetCUIFLOOR();
            
            /**
             * Sets the "CU_IFLOOR" element
             */
            void setCUIFLOOR(java.lang.String cuifloor);
            
            /**
             * Sets (as xml) the "CU_IFLOOR" element
             */
            void xsetCUIFLOOR(org.apache.xmlbeans.XmlString cuifloor);
            
            /**
             * Gets the "CU_IBLDG" element
             */
            java.lang.String getCUIBLDG();
            
            /**
             * Gets (as xml) the "CU_IBLDG" element
             */
            org.apache.xmlbeans.XmlString xgetCUIBLDG();
            
            /**
             * Sets the "CU_IBLDG" element
             */
            void setCUIBLDG(java.lang.String cuibldg);
            
            /**
             * Sets (as xml) the "CU_IBLDG" element
             */
            void xsetCUIBLDG(org.apache.xmlbeans.XmlString cuibldg);
            
            /**
             * Gets the "CU_IPOBOX" element
             */
            java.lang.String getCUIPOBOX();
            
            /**
             * Gets (as xml) the "CU_IPOBOX" element
             */
            org.apache.xmlbeans.XmlString xgetCUIPOBOX();
            
            /**
             * Sets the "CU_IPOBOX" element
             */
            void setCUIPOBOX(java.lang.String cuipobox);
            
            /**
             * Sets (as xml) the "CU_IPOBOX" element
             */
            void xsetCUIPOBOX(org.apache.xmlbeans.XmlString cuipobox);
            
            /**
             * Gets the "CU_IHSESTNO" element
             */
            java.lang.String getCUIHSESTNO();
            
            /**
             * Gets (as xml) the "CU_IHSESTNO" element
             */
            org.apache.xmlbeans.XmlString xgetCUIHSESTNO();
            
            /**
             * Sets the "CU_IHSESTNO" element
             */
            void setCUIHSESTNO(java.lang.String cuihsestno);
            
            /**
             * Sets (as xml) the "CU_IHSESTNO" element
             */
            void xsetCUIHSESTNO(org.apache.xmlbeans.XmlString cuihsestno);
            
            /**
             * Gets the "CU_ILOTLDNO" element
             */
            java.lang.String getCUILOTLDNO();
            
            /**
             * Gets (as xml) the "CU_ILOTLDNO" element
             */
            org.apache.xmlbeans.XmlString xgetCUILOTLDNO();
            
            /**
             * Sets the "CU_ILOTLDNO" element
             */
            void setCUILOTLDNO(java.lang.String cuilotldno);
            
            /**
             * Sets (as xml) the "CU_ILOTLDNO" element
             */
            void xsetCUILOTLDNO(org.apache.xmlbeans.XmlString cuilotldno);
            
            /**
             * Gets the "CU_ISTNAME" element
             */
            java.lang.String getCUISTNAME();
            
            /**
             * Gets (as xml) the "CU_ISTNAME" element
             */
            org.apache.xmlbeans.XmlString xgetCUISTNAME();
            
            /**
             * Sets the "CU_ISTNAME" element
             */
            void setCUISTNAME(java.lang.String cuistname);
            
            /**
             * Sets (as xml) the "CU_ISTNAME" element
             */
            void xsetCUISTNAME(org.apache.xmlbeans.XmlString cuistname);
            
            /**
             * Gets the "CU_ISTCAT" element
             */
            java.lang.String getCUISTCAT();
            
            /**
             * Gets (as xml) the "CU_ISTCAT" element
             */
            org.apache.xmlbeans.XmlString xgetCUISTCAT();
            
            /**
             * Sets the "CU_ISTCAT" element
             */
            void setCUISTCAT(java.lang.String cuistcat);
            
            /**
             * Sets (as xml) the "CU_ISTCAT" element
             */
            void xsetCUISTCAT(org.apache.xmlbeans.XmlString cuistcat);
            
            /**
             * Gets the "CU_ISECT" element
             */
            java.lang.String getCUISECT();
            
            /**
             * Gets (as xml) the "CU_ISECT" element
             */
            org.apache.xmlbeans.XmlString xgetCUISECT();
            
            /**
             * Sets the "CU_ISECT" element
             */
            void setCUISECT(java.lang.String cuisect);
            
            /**
             * Sets (as xml) the "CU_ISECT" element
             */
            void xsetCUISECT(org.apache.xmlbeans.XmlString cuisect);
            
            /**
             * Gets the "CU_IDISTR" element
             */
            java.lang.String getCUIDISTR();
            
            /**
             * Gets (as xml) the "CU_IDISTR" element
             */
            org.apache.xmlbeans.XmlString xgetCUIDISTR();
            
            /**
             * Sets the "CU_IDISTR" element
             */
            void setCUIDISTR(java.lang.String cuidistr);
            
            /**
             * Sets (as xml) the "CU_IDISTR" element
             */
            void xsetCUIDISTR(org.apache.xmlbeans.XmlString cuidistr);
            
            /**
             * Gets the "CU_IAREA" element
             */
            java.lang.String getCUIAREA();
            
            /**
             * Gets (as xml) the "CU_IAREA" element
             */
            org.apache.xmlbeans.XmlString xgetCUIAREA();
            
            /**
             * Sets the "CU_IAREA" element
             */
            void setCUIAREA(java.lang.String cuiarea);
            
            /**
             * Sets (as xml) the "CU_IAREA" element
             */
            void xsetCUIAREA(org.apache.xmlbeans.XmlString cuiarea);
            
            /**
             * Gets the "CU_IPOSTREGN" element
             */
            java.lang.String getCUIPOSTREGN();
            
            /**
             * Gets (as xml) the "CU_IPOSTREGN" element
             */
            org.apache.xmlbeans.XmlString xgetCUIPOSTREGN();
            
            /**
             * Sets the "CU_IPOSTREGN" element
             */
            void setCUIPOSTREGN(java.lang.String cuipostregn);
            
            /**
             * Sets (as xml) the "CU_IPOSTREGN" element
             */
            void xsetCUIPOSTREGN(org.apache.xmlbeans.XmlString cuipostregn);
            
            /**
             * Gets the "CU_IPOSTCD" element
             */
            java.lang.String getCUIPOSTCD();
            
            /**
             * Gets (as xml) the "CU_IPOSTCD" element
             */
            org.apache.xmlbeans.XmlString xgetCUIPOSTCD();
            
            /**
             * Sets the "CU_IPOSTCD" element
             */
            void setCUIPOSTCD(java.lang.String cuipostcd);
            
            /**
             * Sets (as xml) the "CU_IPOSTCD" element
             */
            void xsetCUIPOSTCD(org.apache.xmlbeans.XmlString cuipostcd);
            
            /**
             * Gets the "CU_ICOUNTRY" element
             */
            java.lang.String getCUICOUNTRY();
            
            /**
             * Gets (as xml) the "CU_ICOUNTRY" element
             */
            org.apache.xmlbeans.XmlString xgetCUICOUNTRY();
            
            /**
             * Sets the "CU_ICOUNTRY" element
             */
            void setCUICOUNTRY(java.lang.String cuicountry);
            
            /**
             * Sets (as xml) the "CU_ICOUNTRY" element
             */
            void xsetCUICOUNTRY(org.apache.xmlbeans.XmlString cuicountry);
            
            /**
             * A factory class with static methods for creating instances
             * of this type.
             */
            
            public static final class Factory
            {
                public static noNamespace.OrderRegInfoDocument.OrderRegInfo.Address newInstance() {
                  return (noNamespace.OrderRegInfoDocument.OrderRegInfo.Address) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
                
                public static noNamespace.OrderRegInfoDocument.OrderRegInfo.Address newInstance(org.apache.xmlbeans.XmlOptions options) {
                  return (noNamespace.OrderRegInfoDocument.OrderRegInfo.Address) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
                
                private Factory() { } // No instance of this class allowed
            }
        }
        
        /**
         * An XML orderRegDtls(@).
         *
         * This is a complex type.
         */
        public interface OrderRegDtls extends org.apache.xmlbeans.XmlObject
        {
            public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
                org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(OrderRegDtls.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s1FF98B5715F9EE67BF5AE52C2CFC4EE4").resolveHandle("orderregdtls66d6elemtype");
            
            /**
             * Gets array of all "OrderRegInfoDtl" elements
             */
            noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls.OrderRegInfoDtl[] getOrderRegInfoDtlArray();
            
            /**
             * Gets ith "OrderRegInfoDtl" element
             */
            noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls.OrderRegInfoDtl getOrderRegInfoDtlArray(int i);
            
            /**
             * Returns number of "OrderRegInfoDtl" element
             */
            int sizeOfOrderRegInfoDtlArray();
            
            /**
             * Sets array of all "OrderRegInfoDtl" element
             */
            void setOrderRegInfoDtlArray(noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls.OrderRegInfoDtl[] orderRegInfoDtlArray);
            
            /**
             * Sets ith "OrderRegInfoDtl" element
             */
            void setOrderRegInfoDtlArray(int i, noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls.OrderRegInfoDtl orderRegInfoDtl);
            
            /**
             * Inserts and returns a new empty value (as xml) as the ith "OrderRegInfoDtl" element
             */
            noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls.OrderRegInfoDtl insertNewOrderRegInfoDtl(int i);
            
            /**
             * Appends and returns a new empty value (as xml) as the last "OrderRegInfoDtl" element
             */
            noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls.OrderRegInfoDtl addNewOrderRegInfoDtl();
            
            /**
             * Removes the ith "OrderRegInfoDtl" element
             */
            void removeOrderRegInfoDtl(int i);
            
            /**
             * An XML OrderRegInfoDtl(@).
             *
             * This is a complex type.
             */
            public interface OrderRegInfoDtl extends org.apache.xmlbeans.XmlObject
            {
                public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
                    org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(OrderRegInfoDtl.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s1FF98B5715F9EE67BF5AE52C2CFC4EE4").resolveHandle("orderreginfodtldff2elemtype");
                
                /**
                 * Gets the "Store_Code" element
                 */
                java.lang.String getStoreCode();
                
                /**
                 * Gets (as xml) the "Store_Code" element
                 */
                org.apache.xmlbeans.XmlString xgetStoreCode();
                
                /**
                 * Sets the "Store_Code" element
                 */
                void setStoreCode(java.lang.String storeCode);
                
                /**
                 * Sets (as xml) the "Store_Code" element
                 */
                void xsetStoreCode(org.apache.xmlbeans.XmlString storeCode);
                
                /**
                 * Gets the "Register_ID" element
                 */
                java.lang.String getRegisterID();
                
                /**
                 * Gets (as xml) the "Register_ID" element
                 */
                org.apache.xmlbeans.XmlString xgetRegisterID();
                
                /**
                 * Sets the "Register_ID" element
                 */
                void setRegisterID(java.lang.String registerID);
                
                /**
                 * Sets (as xml) the "Register_ID" element
                 */
                void xsetRegisterID(org.apache.xmlbeans.XmlString registerID);
                
                /**
                 * Gets the "Trans_Num" element
                 */
                java.lang.String getTransNum();
                
                /**
                 * Gets (as xml) the "Trans_Num" element
                 */
                org.apache.xmlbeans.XmlString xgetTransNum();
                
                /**
                 * Sets the "Trans_Num" element
                 */
                void setTransNum(java.lang.String transNum);
                
                /**
                 * Sets (as xml) the "Trans_Num" element
                 */
                void xsetTransNum(org.apache.xmlbeans.XmlString transNum);
                
                /**
                 * Gets the "Bus_Date" element
                 */
                java.lang.String getBusDate();
                
                /**
                 * Gets (as xml) the "Bus_Date" element
                 */
                org.apache.xmlbeans.XmlString xgetBusDate();
                
                /**
                 * Sets the "Bus_Date" element
                 */
                void setBusDate(java.lang.String busDate);
                
                /**
                 * Sets (as xml) the "Bus_Date" element
                 */
                void xsetBusDate(org.apache.xmlbeans.XmlString busDate);
                
                /**
                 * Gets the "Txn_Date" element
                 */
                java.lang.String getTxnDate();
                
                /**
                 * Gets (as xml) the "Txn_Date" element
                 */
                org.apache.xmlbeans.XmlString xgetTxnDate();
                
                /**
                 * Sets the "Txn_Date" element
                 */
                void setTxnDate(java.lang.String txnDate);
                
                /**
                 * Sets (as xml) the "Txn_Date" element
                 */
                void xsetTxnDate(org.apache.xmlbeans.XmlString txnDate);
                
                /**
                 * Gets the "SM_Type" element
                 */
                int getSMType();
                
                /**
                 * Gets (as xml) the "SM_Type" element
                 */
                org.apache.xmlbeans.XmlInt xgetSMType();
                
                /**
                 * Sets the "SM_Type" element
                 */
                void setSMType(int smType);
                
                /**
                 * Sets (as xml) the "SM_Type" element
                 */
                void xsetSMType(org.apache.xmlbeans.XmlInt smType);
                
                /**
                 * Gets the "Seq_Num" element
                 */
                java.lang.String getSeqNum();
                
                /**
                 * Gets (as xml) the "Seq_Num" element
                 */
                org.apache.xmlbeans.XmlString xgetSeqNum();
                
                /**
                 * Sets the "Seq_Num" element
                 */
                void setSeqNum(java.lang.String seqNum);
                
                /**
                 * Sets (as xml) the "Seq_Num" element
                 */
                void xsetSeqNum(org.apache.xmlbeans.XmlString seqNum);
                
                /**
                 * Gets the "Barcode" element
                 */
                java.lang.String getBarcode();
                
                /**
                 * Gets (as xml) the "Barcode" element
                 */
                org.apache.xmlbeans.XmlString xgetBarcode();
                
                /**
                 * Sets the "Barcode" element
                 */
                void setBarcode(java.lang.String barcode);
                
                /**
                 * Sets (as xml) the "Barcode" element
                 */
                void xsetBarcode(org.apache.xmlbeans.XmlString barcode);
                
                /**
                 * Gets the "SKU" element
                 */
                java.lang.String getSKU();
                
                /**
                 * Gets (as xml) the "SKU" element
                 */
                org.apache.xmlbeans.XmlString xgetSKU();
                
                /**
                 * Sets the "SKU" element
                 */
                void setSKU(java.lang.String sku);
                
                /**
                 * Sets (as xml) the "SKU" element
                 */
                void xsetSKU(org.apache.xmlbeans.XmlString sku);
                
                /**
                 * Gets the "ProdDesc" element
                 */
                java.lang.String getProdDesc();
                
                /**
                 * Gets (as xml) the "ProdDesc" element
                 */
                org.apache.xmlbeans.XmlString xgetProdDesc();
                
                /**
                 * Sets the "ProdDesc" element
                 */
                void setProdDesc(java.lang.String prodDesc);
                
                /**
                 * Sets (as xml) the "ProdDesc" element
                 */
                void xsetProdDesc(org.apache.xmlbeans.XmlString prodDesc);
                
                /**
                 * Gets the "Qty" element
                 */
                int getQty();
                
                /**
                 * Gets (as xml) the "Qty" element
                 */
                org.apache.xmlbeans.XmlInt xgetQty();
                
                /**
                 * Sets the "Qty" element
                 */
                void setQty(int qty);
                
                /**
                 * Sets (as xml) the "Qty" element
                 */
                void xsetQty(org.apache.xmlbeans.XmlInt qty);
                
                /**
                 * Gets the "Org_Price" element
                 */
                double getOrgPrice();
                
                /**
                 * Gets (as xml) the "Org_Price" element
                 */
                org.apache.xmlbeans.XmlDouble xgetOrgPrice();
                
                /**
                 * Sets the "Org_Price" element
                 */
                void setOrgPrice(double orgPrice);
                
                /**
                 * Sets (as xml) the "Org_Price" element
                 */
                void xsetOrgPrice(org.apache.xmlbeans.XmlDouble orgPrice);
                
                /**
                 * Gets the "Unit_Price" element
                 */
                double getUnitPrice();
                
                /**
                 * Gets (as xml) the "Unit_Price" element
                 */
                org.apache.xmlbeans.XmlDouble xgetUnitPrice();
                
                /**
                 * Sets the "Unit_Price" element
                 */
                void setUnitPrice(double unitPrice);
                
                /**
                 * Sets (as xml) the "Unit_Price" element
                 */
                void xsetUnitPrice(org.apache.xmlbeans.XmlDouble unitPrice);
                
                /**
                 * Gets the "Net_Price" element
                 */
                double getNetPrice();
                
                /**
                 * Gets (as xml) the "Net_Price" element
                 */
                org.apache.xmlbeans.XmlDouble xgetNetPrice();
                
                /**
                 * Sets the "Net_Price" element
                 */
                void setNetPrice(double netPrice);
                
                /**
                 * Sets (as xml) the "Net_Price" element
                 */
                void xsetNetPrice(org.apache.xmlbeans.XmlDouble netPrice);
                
                /**
                 * Gets the "Org_Amount" element
                 */
                double getOrgAmount();
                
                /**
                 * Gets (as xml) the "Org_Amount" element
                 */
                org.apache.xmlbeans.XmlDouble xgetOrgAmount();
                
                /**
                 * Sets the "Org_Amount" element
                 */
                void setOrgAmount(double orgAmount);
                
                /**
                 * Sets (as xml) the "Org_Amount" element
                 */
                void xsetOrgAmount(org.apache.xmlbeans.XmlDouble orgAmount);
                
                /**
                 * Gets the "Unit_Amount" element
                 */
                double getUnitAmount();
                
                /**
                 * Gets (as xml) the "Unit_Amount" element
                 */
                org.apache.xmlbeans.XmlDouble xgetUnitAmount();
                
                /**
                 * Sets the "Unit_Amount" element
                 */
                void setUnitAmount(double unitAmount);
                
                /**
                 * Sets (as xml) the "Unit_Amount" element
                 */
                void xsetUnitAmount(org.apache.xmlbeans.XmlDouble unitAmount);
                
                /**
                 * Gets the "Net_Amount" element
                 */
                double getNetAmount();
                
                /**
                 * Gets (as xml) the "Net_Amount" element
                 */
                org.apache.xmlbeans.XmlDouble xgetNetAmount();
                
                /**
                 * Sets the "Net_Amount" element
                 */
                void setNetAmount(double netAmount);
                
                /**
                 * Sets (as xml) the "Net_Amount" element
                 */
                void xsetNetAmount(org.apache.xmlbeans.XmlDouble netAmount);
                
                /**
                 * Gets the "Total_Qty" element
                 */
                int getTotalQty();
                
                /**
                 * Gets (as xml) the "Total_Qty" element
                 */
                org.apache.xmlbeans.XmlInt xgetTotalQty();
                
                /**
                 * Sets the "Total_Qty" element
                 */
                void setTotalQty(int totalQty);
                
                /**
                 * Sets (as xml) the "Total_Qty" element
                 */
                void xsetTotalQty(org.apache.xmlbeans.XmlInt totalQty);
                
                /**
                 * Gets the "Discount_Price" element
                 */
                boolean getDiscountPrice();
                
                /**
                 * Gets (as xml) the "Discount_Price" element
                 */
                org.apache.xmlbeans.XmlBoolean xgetDiscountPrice();
                
                /**
                 * Sets the "Discount_Price" element
                 */
                void setDiscountPrice(boolean discountPrice);
                
                /**
                 * Sets (as xml) the "Discount_Price" element
                 */
                void xsetDiscountPrice(org.apache.xmlbeans.XmlBoolean discountPrice);
                
                /**
                 * Gets the "PO_Value" element
                 */
                double getPOValue();
                
                /**
                 * Gets (as xml) the "PO_Value" element
                 */
                org.apache.xmlbeans.XmlDouble xgetPOValue();
                
                /**
                 * Sets the "PO_Value" element
                 */
                void setPOValue(double poValue);
                
                /**
                 * Sets (as xml) the "PO_Value" element
                 */
                void xsetPOValue(org.apache.xmlbeans.XmlDouble poValue);
                
                /**
                 * Gets the "PO_ReasonCode" element
                 */
                java.lang.String getPOReasonCode();
                
                /**
                 * Gets (as xml) the "PO_ReasonCode" element
                 */
                org.apache.xmlbeans.XmlString xgetPOReasonCode();
                
                /**
                 * Sets the "PO_ReasonCode" element
                 */
                void setPOReasonCode(java.lang.String poReasonCode);
                
                /**
                 * Sets (as xml) the "PO_ReasonCode" element
                 */
                void xsetPOReasonCode(org.apache.xmlbeans.XmlString poReasonCode);
                
                /**
                 * Gets the "ID_Type" element
                 */
                int getIDType();
                
                /**
                 * Gets (as xml) the "ID_Type" element
                 */
                org.apache.xmlbeans.XmlInt xgetIDType();
                
                /**
                 * Sets the "ID_Type" element
                 */
                void setIDType(int idType);
                
                /**
                 * Sets (as xml) the "ID_Type" element
                 */
                void xsetIDType(org.apache.xmlbeans.XmlInt idType);
                
                /**
                 * Gets the "ID_ReasonCode" element
                 */
                java.lang.String getIDReasonCode();
                
                /**
                 * Gets (as xml) the "ID_ReasonCode" element
                 */
                org.apache.xmlbeans.XmlString xgetIDReasonCode();
                
                /**
                 * Sets the "ID_ReasonCode" element
                 */
                void setIDReasonCode(java.lang.String idReasonCode);
                
                /**
                 * Sets (as xml) the "ID_ReasonCode" element
                 */
                void xsetIDReasonCode(org.apache.xmlbeans.XmlString idReasonCode);
                
                /**
                 * Gets the "ID_Value" element
                 */
                double getIDValue();
                
                /**
                 * Gets (as xml) the "ID_Value" element
                 */
                org.apache.xmlbeans.XmlDouble xgetIDValue();
                
                /**
                 * Sets the "ID_Value" element
                 */
                void setIDValue(double idValue);
                
                /**
                 * Sets (as xml) the "ID_Value" element
                 */
                void xsetIDValue(org.apache.xmlbeans.XmlDouble idValue);
                
                /**
                 * Gets the "MNM_ID" element
                 */
                java.lang.String getMNMID();
                
                /**
                 * Gets (as xml) the "MNM_ID" element
                 */
                org.apache.xmlbeans.XmlString xgetMNMID();
                
                /**
                 * Sets the "MNM_ID" element
                 */
                void setMNMID(java.lang.String mnmid);
                
                /**
                 * Sets (as xml) the "MNM_ID" element
                 */
                void xsetMNMID(org.apache.xmlbeans.XmlString mnmid);
                
                /**
                 * Gets the "Additional1" element
                 */
                java.lang.String getAdditional1();
                
                /**
                 * Gets (as xml) the "Additional1" element
                 */
                org.apache.xmlbeans.XmlString xgetAdditional1();
                
                /**
                 * Sets the "Additional1" element
                 */
                void setAdditional1(java.lang.String additional1);
                
                /**
                 * Sets (as xml) the "Additional1" element
                 */
                void xsetAdditional1(org.apache.xmlbeans.XmlString additional1);
                
                /**
                 * Gets the "Additional2" element
                 */
                java.lang.String getAdditional2();
                
                /**
                 * Gets (as xml) the "Additional2" element
                 */
                org.apache.xmlbeans.XmlString xgetAdditional2();
                
                /**
                 * Sets the "Additional2" element
                 */
                void setAdditional2(java.lang.String additional2);
                
                /**
                 * Sets (as xml) the "Additional2" element
                 */
                void xsetAdditional2(org.apache.xmlbeans.XmlString additional2);
                
                /**
                 * Gets the "Additional3" element
                 */
                java.lang.String getAdditional3();
                
                /**
                 * Gets (as xml) the "Additional3" element
                 */
                org.apache.xmlbeans.XmlString xgetAdditional3();
                
                /**
                 * Sets the "Additional3" element
                 */
                void setAdditional3(java.lang.String additional3);
                
                /**
                 * Sets (as xml) the "Additional3" element
                 */
                void xsetAdditional3(org.apache.xmlbeans.XmlString additional3);
                
                /**
                 * Gets the "AddType" element
                 */
                int getAddType();
                
                /**
                 * Gets (as xml) the "AddType" element
                 */
                org.apache.xmlbeans.XmlInt xgetAddType();
                
                /**
                 * Sets the "AddType" element
                 */
                void setAddType(int addType);
                
                /**
                 * Sets (as xml) the "AddType" element
                 */
                void xsetAddType(org.apache.xmlbeans.XmlInt addType);
                
                /**
                 * Gets the "ChgClass" element
                 */
                java.lang.String getChgClass();
                
                /**
                 * Gets (as xml) the "ChgClass" element
                 */
                org.apache.xmlbeans.XmlString xgetChgClass();
                
                /**
                 * Sets the "ChgClass" element
                 */
                void setChgClass(java.lang.String chgClass);
                
                /**
                 * Sets (as xml) the "ChgClass" element
                 */
                void xsetChgClass(org.apache.xmlbeans.XmlString chgClass);
                
                /**
                 * Gets the "Modifier" element
                 */
                java.lang.String getModifier();
                
                /**
                 * Gets (as xml) the "Modifier" element
                 */
                org.apache.xmlbeans.XmlString xgetModifier();
                
                /**
                 * Sets the "Modifier" element
                 */
                void setModifier(java.lang.String modifier);
                
                /**
                 * Sets (as xml) the "Modifier" element
                 */
                void xsetModifier(org.apache.xmlbeans.XmlString modifier);
                
                /**
                 * Gets the "IsBOM" element
                 */
                boolean getIsBOM();
                
                /**
                 * Gets (as xml) the "IsBOM" element
                 */
                org.apache.xmlbeans.XmlBoolean xgetIsBOM();
                
                /**
                 * Sets the "IsBOM" element
                 */
                void setIsBOM(boolean isBOM);
                
                /**
                 * Sets (as xml) the "IsBOM" element
                 */
                void xsetIsBOM(org.apache.xmlbeans.XmlBoolean isBOM);
                
                /**
                 * Gets the "MNPNeed" element
                 */
                boolean getMNPNeed();
                
                /**
                 * Gets (as xml) the "MNPNeed" element
                 */
                org.apache.xmlbeans.XmlBoolean xgetMNPNeed();
                
                /**
                 * Sets the "MNPNeed" element
                 */
                void setMNPNeed(boolean mnpNeed);
                
                /**
                 * Sets (as xml) the "MNPNeed" element
                 */
                void xsetMNPNeed(org.apache.xmlbeans.XmlBoolean mnpNeed);
                
                /**
                 * Gets the "CNPNeed" element
                 */
                boolean getCNPNeed();
                
                /**
                 * Gets (as xml) the "CNPNeed" element
                 */
                org.apache.xmlbeans.XmlBoolean xgetCNPNeed();
                
                /**
                 * Sets the "CNPNeed" element
                 */
                void setCNPNeed(boolean cnpNeed);
                
                /**
                 * Sets (as xml) the "CNPNeed" element
                 */
                void xsetCNPNeed(org.apache.xmlbeans.XmlBoolean cnpNeed);
                
                /**
                 * Gets the "ChargeType" element
                 */
                int getChargeType();
                
                /**
                 * Gets (as xml) the "ChargeType" element
                 */
                org.apache.xmlbeans.XmlInt xgetChargeType();
                
                /**
                 * Sets the "ChargeType" element
                 */
                void setChargeType(int chargeType);
                
                /**
                 * Sets (as xml) the "ChargeType" element
                 */
                void xsetChargeType(org.apache.xmlbeans.XmlInt chargeType);
                
                /**
                 * Gets the "SN_Need" element
                 */
                int getSNNeed();
                
                /**
                 * Gets (as xml) the "SN_Need" element
                 */
                org.apache.xmlbeans.XmlInt xgetSNNeed();
                
                /**
                 * Sets the "SN_Need" element
                 */
                void setSNNeed(int snNeed);
                
                /**
                 * Sets (as xml) the "SN_Need" element
                 */
                void xsetSNNeed(org.apache.xmlbeans.XmlInt snNeed);
                
                /**
                 * Gets the "SN_Num" element
                 */
                java.lang.String getSNNum();
                
                /**
                 * Gets (as xml) the "SN_Num" element
                 */
                org.apache.xmlbeans.XmlString xgetSNNum();
                
                /**
                 * Sets the "SN_Num" element
                 */
                void setSNNum(java.lang.String snNum);
                
                /**
                 * Sets (as xml) the "SN_Num" element
                 */
                void xsetSNNum(org.apache.xmlbeans.XmlString snNum);
                
                /**
                 * Gets the "StockType" element
                 */
                java.lang.String getStockType();
                
                /**
                 * Gets (as xml) the "StockType" element
                 */
                org.apache.xmlbeans.XmlString xgetStockType();
                
                /**
                 * Sets the "StockType" element
                 */
                void setStockType(java.lang.String stockType);
                
                /**
                 * Sets (as xml) the "StockType" element
                 */
                void xsetStockType(org.apache.xmlbeans.XmlString stockType);
                
                /**
                 * Gets the "Collected" element
                 */
                int getCollected();
                
                /**
                 * Gets (as xml) the "Collected" element
                 */
                org.apache.xmlbeans.XmlInt xgetCollected();
                
                /**
                 * Sets the "Collected" element
                 */
                void setCollected(int collected);
                
                /**
                 * Sets (as xml) the "Collected" element
                 */
                void xsetCollected(org.apache.xmlbeans.XmlInt collected);
                
                /**
                 * Gets the "Pickup_Location" element
                 */
                java.lang.String getPickupLocation();
                
                /**
                 * Gets (as xml) the "Pickup_Location" element
                 */
                org.apache.xmlbeans.XmlString xgetPickupLocation();
                
                /**
                 * Sets the "Pickup_Location" element
                 */
                void setPickupLocation(java.lang.String pickupLocation);
                
                /**
                 * Sets (as xml) the "Pickup_Location" element
                 */
                void xsetPickupLocation(org.apache.xmlbeans.XmlString pickupLocation);
                
                /**
                 * Gets the "Pickup_Staff" element
                 */
                java.lang.String getPickupStaff();
                
                /**
                 * Gets (as xml) the "Pickup_Staff" element
                 */
                org.apache.xmlbeans.XmlString xgetPickupStaff();
                
                /**
                 * Sets the "Pickup_Staff" element
                 */
                void setPickupStaff(java.lang.String pickupStaff);
                
                /**
                 * Sets (as xml) the "Pickup_Staff" element
                 */
                void xsetPickupStaff(org.apache.xmlbeans.XmlString pickupStaff);
                
                /**
                 * Gets the "Pickup_Date" element
                 */
                java.lang.String getPickupDate();
                
                /**
                 * Gets (as xml) the "Pickup_Date" element
                 */
                org.apache.xmlbeans.XmlString xgetPickupDate();
                
                /**
                 * Sets the "Pickup_Date" element
                 */
                void setPickupDate(java.lang.String pickupDate);
                
                /**
                 * Sets (as xml) the "Pickup_Date" element
                 */
                void xsetPickupDate(org.apache.xmlbeans.XmlString pickupDate);
                
                /**
                 * Gets the "DeptCode" element
                 */
                java.lang.String getDeptCode();
                
                /**
                 * Gets (as xml) the "DeptCode" element
                 */
                org.apache.xmlbeans.XmlString xgetDeptCode();
                
                /**
                 * Sets the "DeptCode" element
                 */
                void setDeptCode(java.lang.String deptCode);
                
                /**
                 * Sets (as xml) the "DeptCode" element
                 */
                void xsetDeptCode(org.apache.xmlbeans.XmlString deptCode);
                
                /**
                 * Gets the "Uploaded_FIN" element
                 */
                boolean getUploadedFIN();
                
                /**
                 * Gets (as xml) the "Uploaded_FIN" element
                 */
                org.apache.xmlbeans.XmlBoolean xgetUploadedFIN();
                
                /**
                 * Sets the "Uploaded_FIN" element
                 */
                void setUploadedFIN(boolean uploadedFIN);
                
                /**
                 * Sets (as xml) the "Uploaded_FIN" element
                 */
                void xsetUploadedFIN(org.apache.xmlbeans.XmlBoolean uploadedFIN);
                
                /**
                 * Gets the "LIS_Reserve_ID" element
                 */
                java.lang.String getLISReserveID();
                
                /**
                 * Gets (as xml) the "LIS_Reserve_ID" element
                 */
                org.apache.xmlbeans.XmlString xgetLISReserveID();
                
                /**
                 * Sets the "LIS_Reserve_ID" element
                 */
                void setLISReserveID(java.lang.String lisReserveID);
                
                /**
                 * Sets (as xml) the "LIS_Reserve_ID" element
                 */
                void xsetLISReserveID(org.apache.xmlbeans.XmlString lisReserveID);
                
                /**
                 * Gets the "DISC_PRICE" element
                 */
                double getDISCPRICE();
                
                /**
                 * Gets (as xml) the "DISC_PRICE" element
                 */
                org.apache.xmlbeans.XmlDouble xgetDISCPRICE();
                
                /**
                 * Sets the "DISC_PRICE" element
                 */
                void setDISCPRICE(double discprice);
                
                /**
                 * Sets (as xml) the "DISC_PRICE" element
                 */
                void xsetDISCPRICE(org.apache.xmlbeans.XmlDouble discprice);
                
                /**
                 * Gets the "DISC_AMOUNT" element
                 */
                double getDISCAMOUNT();
                
                /**
                 * Gets (as xml) the "DISC_AMOUNT" element
                 */
                org.apache.xmlbeans.XmlDouble xgetDISCAMOUNT();
                
                /**
                 * Sets the "DISC_AMOUNT" element
                 */
                void setDISCAMOUNT(double discamount);
                
                /**
                 * Sets (as xml) the "DISC_AMOUNT" element
                 */
                void xsetDISCAMOUNT(org.apache.xmlbeans.XmlDouble discamount);
                
                /**
                 * Gets the "IMEI" element
                 */
                java.lang.String getIMEI();
                
                /**
                 * Gets (as xml) the "IMEI" element
                 */
                org.apache.xmlbeans.XmlString xgetIMEI();
                
                /**
                 * Sets the "IMEI" element
                 */
                void setIMEI(java.lang.String imei);
                
                /**
                 * Sets (as xml) the "IMEI" element
                 */
                void xsetIMEI(org.apache.xmlbeans.XmlString imei);
                
                /**
                 * Gets the "Add_Charge" element
                 */
                double getAddCharge();
                
                /**
                 * Gets (as xml) the "Add_Charge" element
                 */
                org.apache.xmlbeans.XmlDouble xgetAddCharge();
                
                /**
                 * Sets the "Add_Charge" element
                 */
                void setAddCharge(double addCharge);
                
                /**
                 * Sets (as xml) the "Add_Charge" element
                 */
                void xsetAddCharge(org.apache.xmlbeans.XmlDouble addCharge);
                
                /**
                 * Gets the "Disc_Type" element
                 */
                int getDiscType();
                
                /**
                 * Gets (as xml) the "Disc_Type" element
                 */
                org.apache.xmlbeans.XmlInt xgetDiscType();
                
                /**
                 * Sets the "Disc_Type" element
                 */
                void setDiscType(int discType);
                
                /**
                 * Sets (as xml) the "Disc_Type" element
                 */
                void xsetDiscType(org.apache.xmlbeans.XmlInt discType);
                
                /**
                 * Gets the "Is_coupon_SKU" element
                 */
                boolean getIsCouponSKU();
                
                /**
                 * Gets (as xml) the "Is_coupon_SKU" element
                 */
                org.apache.xmlbeans.XmlBoolean xgetIsCouponSKU();
                
                /**
                 * Sets the "Is_coupon_SKU" element
                 */
                void setIsCouponSKU(boolean isCouponSKU);
                
                /**
                 * Sets (as xml) the "Is_coupon_SKU" element
                 */
                void xsetIsCouponSKU(org.apache.xmlbeans.XmlBoolean isCouponSKU);
                
                /**
                 * Gets the "Is_buyback_SKU" element
                 */
                boolean getIsBuybackSKU();
                
                /**
                 * Gets (as xml) the "Is_buyback_SKU" element
                 */
                org.apache.xmlbeans.XmlBoolean xgetIsBuybackSKU();
                
                /**
                 * Sets the "Is_buyback_SKU" element
                 */
                void setIsBuybackSKU(boolean isBuybackSKU);
                
                /**
                 * Sets (as xml) the "Is_buyback_SKU" element
                 */
                void xsetIsBuybackSKU(org.apache.xmlbeans.XmlBoolean isBuybackSKU);
                
                /**
                 * Gets the "Lbl_Sno" element
                 */
                java.lang.String getLblSno();
                
                /**
                 * Gets (as xml) the "Lbl_Sno" element
                 */
                org.apache.xmlbeans.XmlString xgetLblSno();
                
                /**
                 * Sets the "Lbl_Sno" element
                 */
                void setLblSno(java.lang.String lblSno);
                
                /**
                 * Sets (as xml) the "Lbl_Sno" element
                 */
                void xsetLblSno(org.apache.xmlbeans.XmlString lblSno);
                
                /**
                 * Gets the "Warranty" element
                 */
                boolean getWarranty();
                
                /**
                 * Gets (as xml) the "Warranty" element
                 */
                org.apache.xmlbeans.XmlBoolean xgetWarranty();
                
                /**
                 * Sets the "Warranty" element
                 */
                void setWarranty(boolean warranty);
                
                /**
                 * Sets (as xml) the "Warranty" element
                 */
                void xsetWarranty(org.apache.xmlbeans.XmlBoolean warranty);
                
                /**
                 * Gets the "Pickup_Store_Type" element
                 */
                int getPickupStoreType();
                
                /**
                 * Gets (as xml) the "Pickup_Store_Type" element
                 */
                org.apache.xmlbeans.XmlInt xgetPickupStoreType();
                
                /**
                 * Sets the "Pickup_Store_Type" element
                 */
                void setPickupStoreType(int pickupStoreType);
                
                /**
                 * Sets (as xml) the "Pickup_Store_Type" element
                 */
                void xsetPickupStoreType(org.apache.xmlbeans.XmlInt pickupStoreType);
                
                /**
                 * Gets the "Pickup_Store_Name" element
                 */
                java.lang.String getPickupStoreName();
                
                /**
                 * Gets (as xml) the "Pickup_Store_Name" element
                 */
                org.apache.xmlbeans.XmlString xgetPickupStoreName();
                
                /**
                 * Sets the "Pickup_Store_Name" element
                 */
                void setPickupStoreName(java.lang.String pickupStoreName);
                
                /**
                 * Sets (as xml) the "Pickup_Store_Name" element
                 */
                void xsetPickupStoreName(org.apache.xmlbeans.XmlString pickupStoreName);
                
                /**
                 * Gets the "Pickup_Store_Tel" element
                 */
                java.lang.String getPickupStoreTel();
                
                /**
                 * Gets (as xml) the "Pickup_Store_Tel" element
                 */
                org.apache.xmlbeans.XmlString xgetPickupStoreTel();
                
                /**
                 * Sets the "Pickup_Store_Tel" element
                 */
                void setPickupStoreTel(java.lang.String pickupStoreTel);
                
                /**
                 * Sets (as xml) the "Pickup_Store_Tel" element
                 */
                void xsetPickupStoreTel(org.apache.xmlbeans.XmlString pickupStoreTel);
                
                /**
                 * Gets the "Pickup_Store_begin_hours" element
                 */
                java.lang.String getPickupStoreBeginHours();
                
                /**
                 * Gets (as xml) the "Pickup_Store_begin_hours" element
                 */
                org.apache.xmlbeans.XmlString xgetPickupStoreBeginHours();
                
                /**
                 * Sets the "Pickup_Store_begin_hours" element
                 */
                void setPickupStoreBeginHours(java.lang.String pickupStoreBeginHours);
                
                /**
                 * Sets (as xml) the "Pickup_Store_begin_hours" element
                 */
                void xsetPickupStoreBeginHours(org.apache.xmlbeans.XmlString pickupStoreBeginHours);
                
                /**
                 * Gets the "Pickup_Store_End_Hours" element
                 */
                java.lang.String getPickupStoreEndHours();
                
                /**
                 * Gets (as xml) the "Pickup_Store_End_Hours" element
                 */
                org.apache.xmlbeans.XmlString xgetPickupStoreEndHours();
                
                /**
                 * Sets the "Pickup_Store_End_Hours" element
                 */
                void setPickupStoreEndHours(java.lang.String pickupStoreEndHours);
                
                /**
                 * Sets (as xml) the "Pickup_Store_End_Hours" element
                 */
                void xsetPickupStoreEndHours(org.apache.xmlbeans.XmlString pickupStoreEndHours);
                
                /**
                 * Gets the "Pickup_Store_Addr_1" element
                 */
                java.lang.String getPickupStoreAddr1();
                
                /**
                 * Gets (as xml) the "Pickup_Store_Addr_1" element
                 */
                org.apache.xmlbeans.XmlString xgetPickupStoreAddr1();
                
                /**
                 * Sets the "Pickup_Store_Addr_1" element
                 */
                void setPickupStoreAddr1(java.lang.String pickupStoreAddr1);
                
                /**
                 * Sets (as xml) the "Pickup_Store_Addr_1" element
                 */
                void xsetPickupStoreAddr1(org.apache.xmlbeans.XmlString pickupStoreAddr1);
                
                /**
                 * Gets the "Pickup_Store_Addr_2" element
                 */
                java.lang.String getPickupStoreAddr2();
                
                /**
                 * Gets (as xml) the "Pickup_Store_Addr_2" element
                 */
                org.apache.xmlbeans.XmlString xgetPickupStoreAddr2();
                
                /**
                 * Sets the "Pickup_Store_Addr_2" element
                 */
                void setPickupStoreAddr2(java.lang.String pickupStoreAddr2);
                
                /**
                 * Sets (as xml) the "Pickup_Store_Addr_2" element
                 */
                void xsetPickupStoreAddr2(org.apache.xmlbeans.XmlString pickupStoreAddr2);
                
                /**
                 * Gets the "Pickup_Store_Addr_3" element
                 */
                java.lang.String getPickupStoreAddr3();
                
                /**
                 * Gets (as xml) the "Pickup_Store_Addr_3" element
                 */
                org.apache.xmlbeans.XmlString xgetPickupStoreAddr3();
                
                /**
                 * Sets the "Pickup_Store_Addr_3" element
                 */
                void setPickupStoreAddr3(java.lang.String pickupStoreAddr3);
                
                /**
                 * Sets (as xml) the "Pickup_Store_Addr_3" element
                 */
                void xsetPickupStoreAddr3(org.apache.xmlbeans.XmlString pickupStoreAddr3);
                
                /**
                 * Gets the "Fault_Code" element
                 */
                java.lang.String getFaultCode();
                
                /**
                 * Gets (as xml) the "Fault_Code" element
                 */
                org.apache.xmlbeans.XmlString xgetFaultCode();
                
                /**
                 * Sets the "Fault_Code" element
                 */
                void setFaultCode(java.lang.String faultCode);
                
                /**
                 * Sets (as xml) the "Fault_Code" element
                 */
                void xsetFaultCode(org.apache.xmlbeans.XmlString faultCode);
                
                /**
                 * Gets the "Fault_Desc" element
                 */
                java.lang.String getFaultDesc();
                
                /**
                 * Gets (as xml) the "Fault_Desc" element
                 */
                org.apache.xmlbeans.XmlString xgetFaultDesc();
                
                /**
                 * Sets the "Fault_Desc" element
                 */
                void setFaultDesc(java.lang.String faultDesc);
                
                /**
                 * Sets (as xml) the "Fault_Desc" element
                 */
                void xsetFaultDesc(org.apache.xmlbeans.XmlString faultDesc);
                
                /**
                 * Gets the "Record_Type" element
                 */
                int getRecordType();
                
                /**
                 * Gets (as xml) the "Record_Type" element
                 */
                org.apache.xmlbeans.XmlInt xgetRecordType();
                
                /**
                 * Sets the "Record_Type" element
                 */
                void setRecordType(int recordType);
                
                /**
                 * Sets (as xml) the "Record_Type" element
                 */
                void xsetRecordType(org.apache.xmlbeans.XmlInt recordType);
                
                /**
                 * Gets the "Ref_GUID" element
                 */
                java.lang.String getRefGUID();
                
                /**
                 * Gets (as xml) the "Ref_GUID" element
                 */
                org.apache.xmlbeans.XmlString xgetRefGUID();
                
                /**
                 * Sets the "Ref_GUID" element
                 */
                void setRefGUID(java.lang.String refGUID);
                
                /**
                 * Sets (as xml) the "Ref_GUID" element
                 */
                void xsetRefGUID(org.apache.xmlbeans.XmlString refGUID);
                
                /**
                 * Gets the "ActAmount" element
                 */
                double getActAmount();
                
                /**
                 * Gets (as xml) the "ActAmount" element
                 */
                org.apache.xmlbeans.XmlDouble xgetActAmount();
                
                /**
                 * Sets the "ActAmount" element
                 */
                void setActAmount(double actAmount);
                
                /**
                 * Sets (as xml) the "ActAmount" element
                 */
                void xsetActAmount(org.apache.xmlbeans.XmlDouble actAmount);
                
                /**
                 * Gets the "ActValue" element
                 */
                double getActValue();
                
                /**
                 * Gets (as xml) the "ActValue" element
                 */
                org.apache.xmlbeans.XmlDouble xgetActValue();
                
                /**
                 * Sets the "ActValue" element
                 */
                void setActValue(double actValue);
                
                /**
                 * Sets (as xml) the "ActValue" element
                 */
                void xsetActValue(org.apache.xmlbeans.XmlDouble actValue);
                
                /**
                 * Gets the "Txn_Time_POS_BOM" element
                 */
                java.lang.String getTxnTimePOSBOM();
                
                /**
                 * Gets (as xml) the "Txn_Time_POS_BOM" element
                 */
                org.apache.xmlbeans.XmlString xgetTxnTimePOSBOM();
                
                /**
                 * Sets the "Txn_Time_POS_BOM" element
                 */
                void setTxnTimePOSBOM(java.lang.String txnTimePOSBOM);
                
                /**
                 * Sets (as xml) the "Txn_Time_POS_BOM" element
                 */
                void xsetTxnTimePOSBOM(org.apache.xmlbeans.XmlString txnTimePOSBOM);
                
                /**
                 * Gets the "Txn_Date_POS_BOM" element
                 */
                java.lang.String getTxnDatePOSBOM();
                
                /**
                 * Gets (as xml) the "Txn_Date_POS_BOM" element
                 */
                org.apache.xmlbeans.XmlString xgetTxnDatePOSBOM();
                
                /**
                 * Sets the "Txn_Date_POS_BOM" element
                 */
                void setTxnDatePOSBOM(java.lang.String txnDatePOSBOM);
                
                /**
                 * Sets (as xml) the "Txn_Date_POS_BOM" element
                 */
                void xsetTxnDatePOSBOM(org.apache.xmlbeans.XmlString txnDatePOSBOM);
                
                /**
                 * Gets the "Req_No" element
                 */
                java.lang.String getReqNo();
                
                /**
                 * Gets (as xml) the "Req_No" element
                 */
                org.apache.xmlbeans.XmlString xgetReqNo();
                
                /**
                 * Sets the "Req_No" element
                 */
                void setReqNo(java.lang.String reqNo);
                
                /**
                 * Sets (as xml) the "Req_No" element
                 */
                void xsetReqNo(org.apache.xmlbeans.XmlString reqNo);
                
                /**
                 * Gets the "Ref_No" element
                 */
                java.lang.String getRefNo();
                
                /**
                 * Gets (as xml) the "Ref_No" element
                 */
                org.apache.xmlbeans.XmlString xgetRefNo();
                
                /**
                 * Sets the "Ref_No" element
                 */
                void setRefNo(java.lang.String refNo);
                
                /**
                 * Sets (as xml) the "Ref_No" element
                 */
                void xsetRefNo(org.apache.xmlbeans.XmlString refNo);
                
                /**
                 * Gets the "MSISDN" element
                 */
                java.lang.String getMSISDN();
                
                /**
                 * Gets (as xml) the "MSISDN" element
                 */
                org.apache.xmlbeans.XmlString xgetMSISDN();
                
                /**
                 * Sets the "MSISDN" element
                 */
                void setMSISDN(java.lang.String msisdn);
                
                /**
                 * Sets (as xml) the "MSISDN" element
                 */
                void xsetMSISDN(org.apache.xmlbeans.XmlString msisdn);
                
                /**
                 * Gets the "SIM_No" element
                 */
                java.lang.String getSIMNo();
                
                /**
                 * Gets (as xml) the "SIM_No" element
                 */
                org.apache.xmlbeans.XmlString xgetSIMNo();
                
                /**
                 * Sets the "SIM_No" element
                 */
                void setSIMNo(java.lang.String simNo);
                
                /**
                 * Sets (as xml) the "SIM_No" element
                 */
                void xsetSIMNo(org.apache.xmlbeans.XmlString simNo);
                
                /**
                 * A factory class with static methods for creating instances
                 * of this type.
                 */
                
                public static final class Factory
                {
                    public static noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls.OrderRegInfoDtl newInstance() {
                      return (noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls.OrderRegInfoDtl) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
                    
                    public static noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls.OrderRegInfoDtl newInstance(org.apache.xmlbeans.XmlOptions options) {
                      return (noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls.OrderRegInfoDtl) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
                    
                    private Factory() { } // No instance of this class allowed
                }
            }
            
            /**
             * A factory class with static methods for creating instances
             * of this type.
             */
            
            public static final class Factory
            {
                public static noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls newInstance() {
                  return (noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
                
                public static noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls newInstance(org.apache.xmlbeans.XmlOptions options) {
                  return (noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
                
                private Factory() { } // No instance of this class allowed
            }
        }
        
        /**
         * An XML orderRegTenders(@).
         *
         * This is a complex type.
         */
        public interface OrderRegTenders extends org.apache.xmlbeans.XmlObject
        {
            public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
                org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(OrderRegTenders.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s1FF98B5715F9EE67BF5AE52C2CFC4EE4").resolveHandle("orderregtenders2502elemtype");
            
            /**
             * Gets array of all "OrderRegInfoTender" elements
             */
            noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders.OrderRegInfoTender[] getOrderRegInfoTenderArray();
            
            /**
             * Gets ith "OrderRegInfoTender" element
             */
            noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders.OrderRegInfoTender getOrderRegInfoTenderArray(int i);
            
            /**
             * Returns number of "OrderRegInfoTender" element
             */
            int sizeOfOrderRegInfoTenderArray();
            
            /**
             * Sets array of all "OrderRegInfoTender" element
             */
            void setOrderRegInfoTenderArray(noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders.OrderRegInfoTender[] orderRegInfoTenderArray);
            
            /**
             * Sets ith "OrderRegInfoTender" element
             */
            void setOrderRegInfoTenderArray(int i, noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders.OrderRegInfoTender orderRegInfoTender);
            
            /**
             * Inserts and returns a new empty value (as xml) as the ith "OrderRegInfoTender" element
             */
            noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders.OrderRegInfoTender insertNewOrderRegInfoTender(int i);
            
            /**
             * Appends and returns a new empty value (as xml) as the last "OrderRegInfoTender" element
             */
            noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders.OrderRegInfoTender addNewOrderRegInfoTender();
            
            /**
             * Removes the ith "OrderRegInfoTender" element
             */
            void removeOrderRegInfoTender(int i);
            
            /**
             * An XML OrderRegInfoTender(@).
             *
             * This is a complex type.
             */
            public interface OrderRegInfoTender extends org.apache.xmlbeans.XmlObject
            {
                public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
                    org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(OrderRegInfoTender.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s1FF98B5715F9EE67BF5AE52C2CFC4EE4").resolveHandle("orderreginfotendercdd6elemtype");
                
                /**
                 * Gets the "Store_Code" element
                 */
                java.lang.String getStoreCode();
                
                /**
                 * Gets (as xml) the "Store_Code" element
                 */
                org.apache.xmlbeans.XmlString xgetStoreCode();
                
                /**
                 * Sets the "Store_Code" element
                 */
                void setStoreCode(java.lang.String storeCode);
                
                /**
                 * Sets (as xml) the "Store_Code" element
                 */
                void xsetStoreCode(org.apache.xmlbeans.XmlString storeCode);
                
                /**
                 * Gets the "Register_ID" element
                 */
                java.lang.String getRegisterID();
                
                /**
                 * Gets (as xml) the "Register_ID" element
                 */
                org.apache.xmlbeans.XmlString xgetRegisterID();
                
                /**
                 * Sets the "Register_ID" element
                 */
                void setRegisterID(java.lang.String registerID);
                
                /**
                 * Sets (as xml) the "Register_ID" element
                 */
                void xsetRegisterID(org.apache.xmlbeans.XmlString registerID);
                
                /**
                 * Gets the "Trans_Num" element
                 */
                java.lang.String getTransNum();
                
                /**
                 * Gets (as xml) the "Trans_Num" element
                 */
                org.apache.xmlbeans.XmlString xgetTransNum();
                
                /**
                 * Sets the "Trans_Num" element
                 */
                void setTransNum(java.lang.String transNum);
                
                /**
                 * Sets (as xml) the "Trans_Num" element
                 */
                void xsetTransNum(org.apache.xmlbeans.XmlString transNum);
                
                /**
                 * Gets the "Bus_Date" element
                 */
                java.lang.String getBusDate();
                
                /**
                 * Gets (as xml) the "Bus_Date" element
                 */
                org.apache.xmlbeans.XmlString xgetBusDate();
                
                /**
                 * Sets the "Bus_Date" element
                 */
                void setBusDate(java.lang.String busDate);
                
                /**
                 * Sets (as xml) the "Bus_Date" element
                 */
                void xsetBusDate(org.apache.xmlbeans.XmlString busDate);
                
                /**
                 * Gets the "Txn_Date" element
                 */
                java.lang.String getTxnDate();
                
                /**
                 * Gets (as xml) the "Txn_Date" element
                 */
                org.apache.xmlbeans.XmlString xgetTxnDate();
                
                /**
                 * Sets the "Txn_Date" element
                 */
                void setTxnDate(java.lang.String txnDate);
                
                /**
                 * Sets (as xml) the "Txn_Date" element
                 */
                void xsetTxnDate(org.apache.xmlbeans.XmlString txnDate);
                
                /**
                 * Gets the "SM_Type" element
                 */
                int getSMType();
                
                /**
                 * Gets (as xml) the "SM_Type" element
                 */
                org.apache.xmlbeans.XmlInt xgetSMType();
                
                /**
                 * Sets the "SM_Type" element
                 */
                void setSMType(int smType);
                
                /**
                 * Sets (as xml) the "SM_Type" element
                 */
                void xsetSMType(org.apache.xmlbeans.XmlInt smType);
                
                /**
                 * Gets the "Seq_Num" element
                 */
                java.lang.String getSeqNum();
                
                /**
                 * Gets (as xml) the "Seq_Num" element
                 */
                org.apache.xmlbeans.XmlString xgetSeqNum();
                
                /**
                 * Sets the "Seq_Num" element
                 */
                void setSeqNum(java.lang.String seqNum);
                
                /**
                 * Sets (as xml) the "Seq_Num" element
                 */
                void xsetSeqNum(org.apache.xmlbeans.XmlString seqNum);
                
                /**
                 * Gets the "Tender_ID" element
                 */
                java.lang.String getTenderID();
                
                /**
                 * Gets (as xml) the "Tender_ID" element
                 */
                org.apache.xmlbeans.XmlString xgetTenderID();
                
                /**
                 * Sets the "Tender_ID" element
                 */
                void setTenderID(java.lang.String tenderID);
                
                /**
                 * Sets (as xml) the "Tender_ID" element
                 */
                void xsetTenderID(org.apache.xmlbeans.XmlString tenderID);
                
                /**
                 * Gets the "Tender_Type" element
                 */
                java.lang.String getTenderType();
                
                /**
                 * Gets (as xml) the "Tender_Type" element
                 */
                org.apache.xmlbeans.XmlString xgetTenderType();
                
                /**
                 * Sets the "Tender_Type" element
                 */
                void setTenderType(java.lang.String tenderType);
                
                /**
                 * Sets (as xml) the "Tender_Type" element
                 */
                void xsetTenderType(org.apache.xmlbeans.XmlString tenderType);
                
                /**
                 * Gets the "Tender_Amount" element
                 */
                double getTenderAmount();
                
                /**
                 * Gets (as xml) the "Tender_Amount" element
                 */
                org.apache.xmlbeans.XmlDouble xgetTenderAmount();
                
                /**
                 * Sets the "Tender_Amount" element
                 */
                void setTenderAmount(double tenderAmount);
                
                /**
                 * Sets (as xml) the "Tender_Amount" element
                 */
                void xsetTenderAmount(org.apache.xmlbeans.XmlDouble tenderAmount);
                
                /**
                 * Gets the "Local_Amount" element
                 */
                double getLocalAmount();
                
                /**
                 * Gets (as xml) the "Local_Amount" element
                 */
                org.apache.xmlbeans.XmlDouble xgetLocalAmount();
                
                /**
                 * Sets the "Local_Amount" element
                 */
                void setLocalAmount(double localAmount);
                
                /**
                 * Sets (as xml) the "Local_Amount" element
                 */
                void xsetLocalAmount(org.apache.xmlbeans.XmlDouble localAmount);
                
                /**
                 * Gets the "Tender_Desc" element
                 */
                java.lang.String getTenderDesc();
                
                /**
                 * Gets (as xml) the "Tender_Desc" element
                 */
                org.apache.xmlbeans.XmlString xgetTenderDesc();
                
                /**
                 * Sets the "Tender_Desc" element
                 */
                void setTenderDesc(java.lang.String tenderDesc);
                
                /**
                 * Sets (as xml) the "Tender_Desc" element
                 */
                void xsetTenderDesc(org.apache.xmlbeans.XmlString tenderDesc);
                
                /**
                 * Gets the "Additional" element
                 */
                java.lang.String getAdditional();
                
                /**
                 * Gets (as xml) the "Additional" element
                 */
                org.apache.xmlbeans.XmlString xgetAdditional();
                
                /**
                 * Sets the "Additional" element
                 */
                void setAdditional(java.lang.String additional);
                
                /**
                 * Sets (as xml) the "Additional" element
                 */
                void xsetAdditional(org.apache.xmlbeans.XmlString additional);
                
                /**
                 * Gets the "Org_Amount" element
                 */
                double getOrgAmount();
                
                /**
                 * Gets (as xml) the "Org_Amount" element
                 */
                org.apache.xmlbeans.XmlDouble xgetOrgAmount();
                
                /**
                 * Sets the "Org_Amount" element
                 */
                void setOrgAmount(double orgAmount);
                
                /**
                 * Sets (as xml) the "Org_Amount" element
                 */
                void xsetOrgAmount(org.apache.xmlbeans.XmlDouble orgAmount);
                
                /**
                 * Gets the "Payment_Diff" element
                 */
                double getPaymentDiff();
                
                /**
                 * Gets (as xml) the "Payment_Diff" element
                 */
                org.apache.xmlbeans.XmlDouble xgetPaymentDiff();
                
                /**
                 * Sets the "Payment_Diff" element
                 */
                void setPaymentDiff(double paymentDiff);
                
                /**
                 * Sets (as xml) the "Payment_Diff" element
                 */
                void xsetPaymentDiff(org.apache.xmlbeans.XmlDouble paymentDiff);
                
                /**
                 * Gets the "Pay_Confirm_Date" element
                 */
                java.lang.String getPayConfirmDate();
                
                /**
                 * Gets (as xml) the "Pay_Confirm_Date" element
                 */
                org.apache.xmlbeans.XmlString xgetPayConfirmDate();
                
                /**
                 * Sets the "Pay_Confirm_Date" element
                 */
                void setPayConfirmDate(java.lang.String payConfirmDate);
                
                /**
                 * Sets (as xml) the "Pay_Confirm_Date" element
                 */
                void xsetPayConfirmDate(org.apache.xmlbeans.XmlString payConfirmDate);
                
                /**
                 * Gets the "Deposit" element
                 */
                boolean getDeposit();
                
                /**
                 * Gets (as xml) the "Deposit" element
                 */
                org.apache.xmlbeans.XmlBoolean xgetDeposit();
                
                /**
                 * Sets the "Deposit" element
                 */
                void setDeposit(boolean deposit);
                
                /**
                 * Sets (as xml) the "Deposit" element
                 */
                void xsetDeposit(org.apache.xmlbeans.XmlBoolean deposit);
                
                /**
                 * Gets the "Record_Type" element
                 */
                int getRecordType();
                
                /**
                 * Gets (as xml) the "Record_Type" element
                 */
                org.apache.xmlbeans.XmlInt xgetRecordType();
                
                /**
                 * Sets the "Record_Type" element
                 */
                void setRecordType(int recordType);
                
                /**
                 * Sets (as xml) the "Record_Type" element
                 */
                void xsetRecordType(org.apache.xmlbeans.XmlInt recordType);
                
                /**
                 * Gets the "EOD_Flag" element
                 */
                int getEODFlag();
                
                /**
                 * Gets (as xml) the "EOD_Flag" element
                 */
                org.apache.xmlbeans.XmlInt xgetEODFlag();
                
                /**
                 * Sets the "EOD_Flag" element
                 */
                void setEODFlag(int eodFlag);
                
                /**
                 * Sets (as xml) the "EOD_Flag" element
                 */
                void xsetEODFlag(org.apache.xmlbeans.XmlInt eodFlag);
                
                /**
                 * Gets the "Input_Type" element
                 */
                java.lang.String getInputType();
                
                /**
                 * Gets (as xml) the "Input_Type" element
                 */
                org.apache.xmlbeans.XmlString xgetInputType();
                
                /**
                 * Sets the "Input_Type" element
                 */
                void setInputType(java.lang.String inputType);
                
                /**
                 * Sets (as xml) the "Input_Type" element
                 */
                void xsetInputType(org.apache.xmlbeans.XmlString inputType);
                
                /**
                 * Gets the "Exchange_Rate" element
                 */
                double getExchangeRate();
                
                /**
                 * Gets (as xml) the "Exchange_Rate" element
                 */
                org.apache.xmlbeans.XmlDouble xgetExchangeRate();
                
                /**
                 * Sets the "Exchange_Rate" element
                 */
                void setExchangeRate(double exchangeRate);
                
                /**
                 * Sets (as xml) the "Exchange_Rate" element
                 */
                void xsetExchangeRate(org.apache.xmlbeans.XmlDouble exchangeRate);
                
                /**
                 * Gets the "Payment_Type" element
                 */
                int getPaymentType();
                
                /**
                 * Gets (as xml) the "Payment_Type" element
                 */
                org.apache.xmlbeans.XmlInt xgetPaymentType();
                
                /**
                 * Sets the "Payment_Type" element
                 */
                void setPaymentType(int paymentType);
                
                /**
                 * Sets (as xml) the "Payment_Type" element
                 */
                void xsetPaymentType(org.apache.xmlbeans.XmlInt paymentType);
                
                /**
                 * Gets the "Status" element
                 */
                int getStatus();
                
                /**
                 * Gets (as xml) the "Status" element
                 */
                org.apache.xmlbeans.XmlInt xgetStatus();
                
                /**
                 * Sets the "Status" element
                 */
                void setStatus(int status);
                
                /**
                 * Sets (as xml) the "Status" element
                 */
                void xsetStatus(org.apache.xmlbeans.XmlInt status);
                
                /**
                 * A factory class with static methods for creating instances
                 * of this type.
                 */
                
                public static final class Factory
                {
                    public static noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders.OrderRegInfoTender newInstance() {
                      return (noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders.OrderRegInfoTender) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
                    
                    public static noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders.OrderRegInfoTender newInstance(org.apache.xmlbeans.XmlOptions options) {
                      return (noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders.OrderRegInfoTender) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
                    
                    private Factory() { } // No instance of this class allowed
                }
            }
            
            /**
             * A factory class with static methods for creating instances
             * of this type.
             */
            
            public static final class Factory
            {
                public static noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders newInstance() {
                  return (noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
                
                public static noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders newInstance(org.apache.xmlbeans.XmlOptions options) {
                  return (noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
                
                private Factory() { } // No instance of this class allowed
            }
        }
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static noNamespace.OrderRegInfoDocument.OrderRegInfo newInstance() {
              return (noNamespace.OrderRegInfoDocument.OrderRegInfo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static noNamespace.OrderRegInfoDocument.OrderRegInfo newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (noNamespace.OrderRegInfoDocument.OrderRegInfo) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static noNamespace.OrderRegInfoDocument newInstance() {
          return (noNamespace.OrderRegInfoDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static noNamespace.OrderRegInfoDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (noNamespace.OrderRegInfoDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static noNamespace.OrderRegInfoDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.OrderRegInfoDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static noNamespace.OrderRegInfoDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.OrderRegInfoDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static noNamespace.OrderRegInfoDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.OrderRegInfoDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static noNamespace.OrderRegInfoDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.OrderRegInfoDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static noNamespace.OrderRegInfoDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.OrderRegInfoDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static noNamespace.OrderRegInfoDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.OrderRegInfoDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static noNamespace.OrderRegInfoDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.OrderRegInfoDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static noNamespace.OrderRegInfoDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.OrderRegInfoDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static noNamespace.OrderRegInfoDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.OrderRegInfoDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static noNamespace.OrderRegInfoDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.OrderRegInfoDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static noNamespace.OrderRegInfoDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.OrderRegInfoDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static noNamespace.OrderRegInfoDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.OrderRegInfoDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static noNamespace.OrderRegInfoDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.OrderRegInfoDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static noNamespace.OrderRegInfoDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.OrderRegInfoDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static noNamespace.OrderRegInfoDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (noNamespace.OrderRegInfoDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static noNamespace.OrderRegInfoDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (noNamespace.OrderRegInfoDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
