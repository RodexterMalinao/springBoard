/*
 * An XML document type.
 * Localname: sales_h
 * Namespace: 
 * Java type: noNamespace.SalesHDocument
 *
 * Automatically generated - do not modify.
 */
package noNamespace;


/**
 * A document containing one sales_h(@) element.
 *
 * This is a complex type.
 */
public interface SalesHDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(SalesHDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sA7FD9BD6D3A5FF13BEC61D6C0C2BE639").resolveHandle("salesh6f5cdoctype");
    
    /**
     * Gets the "sales_h" element
     */
    noNamespace.SalesHDocument.SalesH getSalesH();
    
    /**
     * Sets the "sales_h" element
     */
    void setSalesH(noNamespace.SalesHDocument.SalesH salesH);
    
    /**
     * Appends and returns a new empty "sales_h" element
     */
    noNamespace.SalesHDocument.SalesH addNewSalesH();
    
    /**
     * An XML sales_h(@).
     *
     * This is a complex type.
     */
    public interface SalesH extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(SalesH.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sA7FD9BD6D3A5FF13BEC61D6C0C2BE639").resolveHandle("salesha48delemtype");
        
        /**
         * Gets the "ROWDATA" element
         */
        noNamespace.SalesHDocument.SalesH.ROWDATA getROWDATA();
        
        /**
         * Sets the "ROWDATA" element
         */
        void setROWDATA(noNamespace.SalesHDocument.SalesH.ROWDATA rowdata);
        
        /**
         * Appends and returns a new empty "ROWDATA" element
         */
        noNamespace.SalesHDocument.SalesH.ROWDATA addNewROWDATA();
        
        /**
         * An XML ROWDATA(@).
         *
         * This is a complex type.
         */
        public interface ROWDATA extends org.apache.xmlbeans.XmlObject
        {
            public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
                org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ROWDATA.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sA7FD9BD6D3A5FF13BEC61D6C0C2BE639").resolveHandle("rowdatab96delemtype");
            
            /**
             * Gets the "ROW" element
             */
            noNamespace.SalesHDocument.SalesH.ROWDATA.ROW getROW();
            
            /**
             * Sets the "ROW" element
             */
            void setROW(noNamespace.SalesHDocument.SalesH.ROWDATA.ROW row);
            
            /**
             * Appends and returns a new empty "ROW" element
             */
            noNamespace.SalesHDocument.SalesH.ROWDATA.ROW addNewROW();
            
            /**
             * An XML ROW(@).
             *
             * This is an atomic type that is a restriction of noNamespace.SalesHDocument$SalesH$ROWDATA$ROW.
             */
            public interface ROW extends org.apache.xmlbeans.XmlString
            {
                public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
                    org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ROW.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sA7FD9BD6D3A5FF13BEC61D6C0C2BE639").resolveHandle("row3d23elemtype");
                
                /**
                 * Gets the "Store_ID" attribute
                 */
                java.lang.String getStoreID();
                
                /**
                 * Gets (as xml) the "Store_ID" attribute
                 */
                org.apache.xmlbeans.XmlString xgetStoreID();
                
                /**
                 * True if has "Store_ID" attribute
                 */
                boolean isSetStoreID();
                
                /**
                 * Sets the "Store_ID" attribute
                 */
                void setStoreID(java.lang.String storeID);
                
                /**
                 * Sets (as xml) the "Store_ID" attribute
                 */
                void xsetStoreID(org.apache.xmlbeans.XmlString storeID);
                
                /**
                 * Unsets the "Store_ID" attribute
                 */
                void unsetStoreID();
                
                /**
                 * Gets the "Register_ID" attribute
                 */
                java.lang.String getRegisterID();
                
                /**
                 * Gets (as xml) the "Register_ID" attribute
                 */
                org.apache.xmlbeans.XmlString xgetRegisterID();
                
                /**
                 * True if has "Register_ID" attribute
                 */
                boolean isSetRegisterID();
                
                /**
                 * Sets the "Register_ID" attribute
                 */
                void setRegisterID(java.lang.String registerID);
                
                /**
                 * Sets (as xml) the "Register_ID" attribute
                 */
                void xsetRegisterID(org.apache.xmlbeans.XmlString registerID);
                
                /**
                 * Unsets the "Register_ID" attribute
                 */
                void unsetRegisterID();
                
                /**
                 * Gets the "Trans_Num" attribute
                 */
                java.lang.String getTransNum();
                
                /**
                 * Gets (as xml) the "Trans_Num" attribute
                 */
                org.apache.xmlbeans.XmlString xgetTransNum();
                
                /**
                 * True if has "Trans_Num" attribute
                 */
                boolean isSetTransNum();
                
                /**
                 * Sets the "Trans_Num" attribute
                 */
                void setTransNum(java.lang.String transNum);
                
                /**
                 * Sets (as xml) the "Trans_Num" attribute
                 */
                void xsetTransNum(org.apache.xmlbeans.XmlString transNum);
                
                /**
                 * Unsets the "Trans_Num" attribute
                 */
                void unsetTransNum();
                
                /**
                 * Gets the "Bus_Date" attribute
                 */
                java.lang.String getBusDate();
                
                /**
                 * Gets (as xml) the "Bus_Date" attribute
                 */
                org.apache.xmlbeans.XmlString xgetBusDate();
                
                /**
                 * True if has "Bus_Date" attribute
                 */
                boolean isSetBusDate();
                
                /**
                 * Sets the "Bus_Date" attribute
                 */
                void setBusDate(java.lang.String busDate);
                
                /**
                 * Sets (as xml) the "Bus_Date" attribute
                 */
                void xsetBusDate(org.apache.xmlbeans.XmlString busDate);
                
                /**
                 * Unsets the "Bus_Date" attribute
                 */
                void unsetBusDate();
                
                /**
                 * Gets the "Txn_Date" attribute
                 */
                java.lang.String getTxnDate();
                
                /**
                 * Gets (as xml) the "Txn_Date" attribute
                 */
                org.apache.xmlbeans.XmlString xgetTxnDate();
                
                /**
                 * True if has "Txn_Date" attribute
                 */
                boolean isSetTxnDate();
                
                /**
                 * Sets the "Txn_Date" attribute
                 */
                void setTxnDate(java.lang.String txnDate);
                
                /**
                 * Sets (as xml) the "Txn_Date" attribute
                 */
                void xsetTxnDate(org.apache.xmlbeans.XmlString txnDate);
                
                /**
                 * Unsets the "Txn_Date" attribute
                 */
                void unsetTxnDate();
                
                /**
                 * Gets the "SM_Type" attribute
                 */
                java.lang.String getSMType();
                
                /**
                 * Gets (as xml) the "SM_Type" attribute
                 */
                org.apache.xmlbeans.XmlString xgetSMType();
                
                /**
                 * True if has "SM_Type" attribute
                 */
                boolean isSetSMType();
                
                /**
                 * Sets the "SM_Type" attribute
                 */
                void setSMType(java.lang.String smType);
                
                /**
                 * Sets (as xml) the "SM_Type" attribute
                 */
                void xsetSMType(org.apache.xmlbeans.XmlString smType);
                
                /**
                 * Unsets the "SM_Type" attribute
                 */
                void unsetSMType();
                
                /**
                 * Gets the "Cashier_ID" attribute
                 */
                java.lang.String getCashierID();
                
                /**
                 * Gets (as xml) the "Cashier_ID" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCashierID();
                
                /**
                 * True if has "Cashier_ID" attribute
                 */
                boolean isSetCashierID();
                
                /**
                 * Sets the "Cashier_ID" attribute
                 */
                void setCashierID(java.lang.String cashierID);
                
                /**
                 * Sets (as xml) the "Cashier_ID" attribute
                 */
                void xsetCashierID(org.apache.xmlbeans.XmlString cashierID);
                
                /**
                 * Unsets the "Cashier_ID" attribute
                 */
                void unsetCashierID();
                
                /**
                 * Gets the "SalesMan_ID" attribute
                 */
                java.lang.String getSalesManID();
                
                /**
                 * Gets (as xml) the "SalesMan_ID" attribute
                 */
                org.apache.xmlbeans.XmlString xgetSalesManID();
                
                /**
                 * True if has "SalesMan_ID" attribute
                 */
                boolean isSetSalesManID();
                
                /**
                 * Sets the "SalesMan_ID" attribute
                 */
                void setSalesManID(java.lang.String salesManID);
                
                /**
                 * Sets (as xml) the "SalesMan_ID" attribute
                 */
                void xsetSalesManID(org.apache.xmlbeans.XmlString salesManID);
                
                /**
                 * Unsets the "SalesMan_ID" attribute
                 */
                void unsetSalesManID();
                
                /**
                 * Gets the "Salesman_Ext" attribute
                 */
                java.lang.String getSalesmanExt();
                
                /**
                 * Gets (as xml) the "Salesman_Ext" attribute
                 */
                org.apache.xmlbeans.XmlString xgetSalesmanExt();
                
                /**
                 * True if has "Salesman_Ext" attribute
                 */
                boolean isSetSalesmanExt();
                
                /**
                 * Sets the "Salesman_Ext" attribute
                 */
                void setSalesmanExt(java.lang.String salesmanExt);
                
                /**
                 * Sets (as xml) the "Salesman_Ext" attribute
                 */
                void xsetSalesmanExt(org.apache.xmlbeans.XmlString salesmanExt);
                
                /**
                 * Unsets the "Salesman_Ext" attribute
                 */
                void unsetSalesmanExt();
                
                /**
                 * Gets the "Team_Code" attribute
                 */
                java.lang.String getTeamCode();
                
                /**
                 * Gets (as xml) the "Team_Code" attribute
                 */
                org.apache.xmlbeans.XmlString xgetTeamCode();
                
                /**
                 * True if has "Team_Code" attribute
                 */
                boolean isSetTeamCode();
                
                /**
                 * Sets the "Team_Code" attribute
                 */
                void setTeamCode(java.lang.String teamCode);
                
                /**
                 * Sets (as xml) the "Team_Code" attribute
                 */
                void xsetTeamCode(org.apache.xmlbeans.XmlString teamCode);
                
                /**
                 * Unsets the "Team_Code" attribute
                 */
                void unsetTeamCode();
                
                /**
                 * Gets the "channel" attribute
                 */
                java.lang.String getChannel();
                
                /**
                 * Gets (as xml) the "channel" attribute
                 */
                org.apache.xmlbeans.XmlString xgetChannel();
                
                /**
                 * True if has "channel" attribute
                 */
                boolean isSetChannel();
                
                /**
                 * Sets the "channel" attribute
                 */
                void setChannel(java.lang.String channel);
                
                /**
                 * Sets (as xml) the "channel" attribute
                 */
                void xsetChannel(org.apache.xmlbeans.XmlString channel);
                
                /**
                 * Unsets the "channel" attribute
                 */
                void unsetChannel();
                
                /**
                 * Gets the "salesman_code" attribute
                 */
                java.lang.String getSalesmanCode();
                
                /**
                 * Gets (as xml) the "salesman_code" attribute
                 */
                org.apache.xmlbeans.XmlString xgetSalesmanCode();
                
                /**
                 * True if has "salesman_code" attribute
                 */
                boolean isSetSalesmanCode();
                
                /**
                 * Sets the "salesman_code" attribute
                 */
                void setSalesmanCode(java.lang.String salesmanCode);
                
                /**
                 * Sets (as xml) the "salesman_code" attribute
                 */
                void xsetSalesmanCode(org.apache.xmlbeans.XmlString salesmanCode);
                
                /**
                 * Unsets the "salesman_code" attribute
                 */
                void unsetSalesmanCode();
                
                /**
                 * Gets the "QU_Num" attribute
                 */
                java.lang.String getQUNum();
                
                /**
                 * Gets (as xml) the "QU_Num" attribute
                 */
                org.apache.xmlbeans.XmlString xgetQUNum();
                
                /**
                 * True if has "QU_Num" attribute
                 */
                boolean isSetQUNum();
                
                /**
                 * Sets the "QU_Num" attribute
                 */
                void setQUNum(java.lang.String quNum);
                
                /**
                 * Sets (as xml) the "QU_Num" attribute
                 */
                void xsetQUNum(org.apache.xmlbeans.XmlString quNum);
                
                /**
                 * Unsets the "QU_Num" attribute
                 */
                void unsetQUNum();
                
                /**
                 * Gets the "SM_Num" attribute
                 */
                java.lang.String getSMNum();
                
                /**
                 * Gets (as xml) the "SM_Num" attribute
                 */
                org.apache.xmlbeans.XmlString xgetSMNum();
                
                /**
                 * True if has "SM_Num" attribute
                 */
                boolean isSetSMNum();
                
                /**
                 * Sets the "SM_Num" attribute
                 */
                void setSMNum(java.lang.String smNum);
                
                /**
                 * Sets (as xml) the "SM_Num" attribute
                 */
                void xsetSMNum(org.apache.xmlbeans.XmlString smNum);
                
                /**
                 * Unsets the "SM_Num" attribute
                 */
                void unsetSMNum();
                
                /**
                 * Gets the "TotalAmt" attribute
                 */
                short getTotalAmt();
                
                /**
                 * Gets (as xml) the "TotalAmt" attribute
                 */
                org.apache.xmlbeans.XmlShort xgetTotalAmt();
                
                /**
                 * True if has "TotalAmt" attribute
                 */
                boolean isSetTotalAmt();
                
                /**
                 * Sets the "TotalAmt" attribute
                 */
                void setTotalAmt(short totalAmt);
                
                /**
                 * Sets (as xml) the "TotalAmt" attribute
                 */
                void xsetTotalAmt(org.apache.xmlbeans.XmlShort totalAmt);
                
                /**
                 * Unsets the "TotalAmt" attribute
                 */
                void unsetTotalAmt();
                
                /**
                 * Gets the "SI_Need" attribute
                 */
                java.lang.String getSINeed();
                
                /**
                 * Gets (as xml) the "SI_Need" attribute
                 */
                org.apache.xmlbeans.XmlString xgetSINeed();
                
                /**
                 * True if has "SI_Need" attribute
                 */
                boolean isSetSINeed();
                
                /**
                 * Sets the "SI_Need" attribute
                 */
                void setSINeed(java.lang.String siNeed);
                
                /**
                 * Sets (as xml) the "SI_Need" attribute
                 */
                void xsetSINeed(org.apache.xmlbeans.XmlString siNeed);
                
                /**
                 * Unsets the "SI_Need" attribute
                 */
                void unsetSINeed();
                
                /**
                 * Gets the "SM_CreateDate" attribute
                 */
                java.lang.String getSMCreateDate();
                
                /**
                 * Gets (as xml) the "SM_CreateDate" attribute
                 */
                org.apache.xmlbeans.XmlString xgetSMCreateDate();
                
                /**
                 * True if has "SM_CreateDate" attribute
                 */
                boolean isSetSMCreateDate();
                
                /**
                 * Sets the "SM_CreateDate" attribute
                 */
                void setSMCreateDate(java.lang.String smCreateDate);
                
                /**
                 * Sets (as xml) the "SM_CreateDate" attribute
                 */
                void xsetSMCreateDate(org.apache.xmlbeans.XmlString smCreateDate);
                
                /**
                 * Unsets the "SM_CreateDate" attribute
                 */
                void unsetSMCreateDate();
                
                /**
                 * Gets the "Modifyby" attribute
                 */
                java.lang.String getModifyby();
                
                /**
                 * Gets (as xml) the "Modifyby" attribute
                 */
                org.apache.xmlbeans.XmlString xgetModifyby();
                
                /**
                 * True if has "Modifyby" attribute
                 */
                boolean isSetModifyby();
                
                /**
                 * Sets the "Modifyby" attribute
                 */
                void setModifyby(java.lang.String modifyby);
                
                /**
                 * Sets (as xml) the "Modifyby" attribute
                 */
                void xsetModifyby(org.apache.xmlbeans.XmlString modifyby);
                
                /**
                 * Unsets the "Modifyby" attribute
                 */
                void unsetModifyby();
                
                /**
                 * Gets the "ModifyDate" attribute
                 */
                java.lang.String getModifyDate();
                
                /**
                 * Gets (as xml) the "ModifyDate" attribute
                 */
                org.apache.xmlbeans.XmlString xgetModifyDate();
                
                /**
                 * True if has "ModifyDate" attribute
                 */
                boolean isSetModifyDate();
                
                /**
                 * Sets the "ModifyDate" attribute
                 */
                void setModifyDate(java.lang.String modifyDate);
                
                /**
                 * Sets (as xml) the "ModifyDate" attribute
                 */
                void xsetModifyDate(org.apache.xmlbeans.XmlString modifyDate);
                
                /**
                 * Unsets the "ModifyDate" attribute
                 */
                void unsetModifyDate();
                
                /**
                 * Gets the "ConsolDate" attribute
                 */
                java.lang.String getConsolDate();
                
                /**
                 * Gets (as xml) the "ConsolDate" attribute
                 */
                org.apache.xmlbeans.XmlString xgetConsolDate();
                
                /**
                 * True if has "ConsolDate" attribute
                 */
                boolean isSetConsolDate();
                
                /**
                 * Sets the "ConsolDate" attribute
                 */
                void setConsolDate(java.lang.String consolDate);
                
                /**
                 * Sets (as xml) the "ConsolDate" attribute
                 */
                void xsetConsolDate(org.apache.xmlbeans.XmlString consolDate);
                
                /**
                 * Unsets the "ConsolDate" attribute
                 */
                void unsetConsolDate();
                
                /**
                 * Gets the "SettlementDate" attribute
                 */
                java.lang.String getSettlementDate();
                
                /**
                 * Gets (as xml) the "SettlementDate" attribute
                 */
                org.apache.xmlbeans.XmlString xgetSettlementDate();
                
                /**
                 * True if has "SettlementDate" attribute
                 */
                boolean isSetSettlementDate();
                
                /**
                 * Sets the "SettlementDate" attribute
                 */
                void setSettlementDate(java.lang.String settlementDate);
                
                /**
                 * Sets (as xml) the "SettlementDate" attribute
                 */
                void xsetSettlementDate(org.apache.xmlbeans.XmlString settlementDate);
                
                /**
                 * Unsets the "SettlementDate" attribute
                 */
                void unsetSettlementDate();
                
                /**
                 * Gets the "Settle_Log_Date" attribute
                 */
                java.lang.String getSettleLogDate();
                
                /**
                 * Gets (as xml) the "Settle_Log_Date" attribute
                 */
                org.apache.xmlbeans.XmlString xgetSettleLogDate();
                
                /**
                 * True if has "Settle_Log_Date" attribute
                 */
                boolean isSetSettleLogDate();
                
                /**
                 * Sets the "Settle_Log_Date" attribute
                 */
                void setSettleLogDate(java.lang.String settleLogDate);
                
                /**
                 * Sets (as xml) the "Settle_Log_Date" attribute
                 */
                void xsetSettleLogDate(org.apache.xmlbeans.XmlString settleLogDate);
                
                /**
                 * Unsets the "Settle_Log_Date" attribute
                 */
                void unsetSettleLogDate();
                
                /**
                 * Gets the "Settlement_Staff_ID" attribute
                 */
                java.lang.String getSettlementStaffID();
                
                /**
                 * Gets (as xml) the "Settlement_Staff_ID" attribute
                 */
                org.apache.xmlbeans.XmlString xgetSettlementStaffID();
                
                /**
                 * True if has "Settlement_Staff_ID" attribute
                 */
                boolean isSetSettlementStaffID();
                
                /**
                 * Sets the "Settlement_Staff_ID" attribute
                 */
                void setSettlementStaffID(java.lang.String settlementStaffID);
                
                /**
                 * Sets (as xml) the "Settlement_Staff_ID" attribute
                 */
                void xsetSettlementStaffID(org.apache.xmlbeans.XmlString settlementStaffID);
                
                /**
                 * Unsets the "Settlement_Staff_ID" attribute
                 */
                void unsetSettlementStaffID();
                
                /**
                 * Gets the "StaChgDate" attribute
                 */
                java.lang.String getStaChgDate();
                
                /**
                 * Gets (as xml) the "StaChgDate" attribute
                 */
                org.apache.xmlbeans.XmlString xgetStaChgDate();
                
                /**
                 * True if has "StaChgDate" attribute
                 */
                boolean isSetStaChgDate();
                
                /**
                 * Sets the "StaChgDate" attribute
                 */
                void setStaChgDate(java.lang.String staChgDate);
                
                /**
                 * Sets (as xml) the "StaChgDate" attribute
                 */
                void xsetStaChgDate(org.apache.xmlbeans.XmlString staChgDate);
                
                /**
                 * Unsets the "StaChgDate" attribute
                 */
                void unsetStaChgDate();
                
                /**
                 * Gets the "StaChgBy" attribute
                 */
                java.lang.String getStaChgBy();
                
                /**
                 * Gets (as xml) the "StaChgBy" attribute
                 */
                org.apache.xmlbeans.XmlString xgetStaChgBy();
                
                /**
                 * True if has "StaChgBy" attribute
                 */
                boolean isSetStaChgBy();
                
                /**
                 * Sets the "StaChgBy" attribute
                 */
                void setStaChgBy(java.lang.String staChgBy);
                
                /**
                 * Sets (as xml) the "StaChgBy" attribute
                 */
                void xsetStaChgBy(org.apache.xmlbeans.XmlString staChgBy);
                
                /**
                 * Unsets the "StaChgBy" attribute
                 */
                void unsetStaChgBy();
                
                /**
                 * Gets the "Pay_Settle_PhyDate" attribute
                 */
                java.lang.String getPaySettlePhyDate();
                
                /**
                 * Gets (as xml) the "Pay_Settle_PhyDate" attribute
                 */
                org.apache.xmlbeans.XmlString xgetPaySettlePhyDate();
                
                /**
                 * True if has "Pay_Settle_PhyDate" attribute
                 */
                boolean isSetPaySettlePhyDate();
                
                /**
                 * Sets the "Pay_Settle_PhyDate" attribute
                 */
                void setPaySettlePhyDate(java.lang.String paySettlePhyDate);
                
                /**
                 * Sets (as xml) the "Pay_Settle_PhyDate" attribute
                 */
                void xsetPaySettlePhyDate(org.apache.xmlbeans.XmlString paySettlePhyDate);
                
                /**
                 * Unsets the "Pay_Settle_PhyDate" attribute
                 */
                void unsetPaySettlePhyDate();
                
                /**
                 * Gets the "Pay_Settle_LogDate" attribute
                 */
                java.lang.String getPaySettleLogDate();
                
                /**
                 * Gets (as xml) the "Pay_Settle_LogDate" attribute
                 */
                org.apache.xmlbeans.XmlString xgetPaySettleLogDate();
                
                /**
                 * True if has "Pay_Settle_LogDate" attribute
                 */
                boolean isSetPaySettleLogDate();
                
                /**
                 * Sets the "Pay_Settle_LogDate" attribute
                 */
                void setPaySettleLogDate(java.lang.String paySettleLogDate);
                
                /**
                 * Sets (as xml) the "Pay_Settle_LogDate" attribute
                 */
                void xsetPaySettleLogDate(org.apache.xmlbeans.XmlString paySettleLogDate);
                
                /**
                 * Unsets the "Pay_Settle_LogDate" attribute
                 */
                void unsetPaySettleLogDate();
                
                /**
                 * Gets the "Status" attribute
                 */
                java.lang.String getStatus();
                
                /**
                 * Gets (as xml) the "Status" attribute
                 */
                org.apache.xmlbeans.XmlString xgetStatus();
                
                /**
                 * True if has "Status" attribute
                 */
                boolean isSetStatus();
                
                /**
                 * Sets the "Status" attribute
                 */
                void setStatus(java.lang.String status);
                
                /**
                 * Sets (as xml) the "Status" attribute
                 */
                void xsetStatus(org.apache.xmlbeans.XmlString status);
                
                /**
                 * Unsets the "Status" attribute
                 */
                void unsetStatus();
                
                /**
                 * Gets the "CU_Title" attribute
                 */
                java.lang.String getCUTitle();
                
                /**
                 * Gets (as xml) the "CU_Title" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUTitle();
                
                /**
                 * True if has "CU_Title" attribute
                 */
                boolean isSetCUTitle();
                
                /**
                 * Sets the "CU_Title" attribute
                 */
                void setCUTitle(java.lang.String cuTitle);
                
                /**
                 * Sets (as xml) the "CU_Title" attribute
                 */
                void xsetCUTitle(org.apache.xmlbeans.XmlString cuTitle);
                
                /**
                 * Unsets the "CU_Title" attribute
                 */
                void unsetCUTitle();
                
                /**
                 * Gets the "CU_Name" attribute
                 */
                java.lang.String getCUName();
                
                /**
                 * Gets (as xml) the "CU_Name" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUName();
                
                /**
                 * True if has "CU_Name" attribute
                 */
                boolean isSetCUName();
                
                /**
                 * Sets the "CU_Name" attribute
                 */
                void setCUName(java.lang.String cuName);
                
                /**
                 * Sets (as xml) the "CU_Name" attribute
                 */
                void xsetCUName(org.apache.xmlbeans.XmlString cuName);
                
                /**
                 * Unsets the "CU_Name" attribute
                 */
                void unsetCUName();
                
                /**
                 * Gets the "CU_CCCNO" attribute
                 */
                java.lang.String getCUCCCNO();
                
                /**
                 * Gets (as xml) the "CU_CCCNO" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUCCCNO();
                
                /**
                 * True if has "CU_CCCNO" attribute
                 */
                boolean isSetCUCCCNO();
                
                /**
                 * Sets the "CU_CCCNO" attribute
                 */
                void setCUCCCNO(java.lang.String cucccno);
                
                /**
                 * Sets (as xml) the "CU_CCCNO" attribute
                 */
                void xsetCUCCCNO(org.apache.xmlbeans.XmlString cucccno);
                
                /**
                 * Unsets the "CU_CCCNO" attribute
                 */
                void unsetCUCCCNO();
                
                /**
                 * Gets the "CU_Tel" attribute
                 */
                java.lang.String getCUTel();
                
                /**
                 * Gets (as xml) the "CU_Tel" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUTel();
                
                /**
                 * True if has "CU_Tel" attribute
                 */
                boolean isSetCUTel();
                
                /**
                 * Sets the "CU_Tel" attribute
                 */
                void setCUTel(java.lang.String cuTel);
                
                /**
                 * Sets (as xml) the "CU_Tel" attribute
                 */
                void xsetCUTel(org.apache.xmlbeans.XmlString cuTel);
                
                /**
                 * Unsets the "CU_Tel" attribute
                 */
                void unsetCUTel();
                
                /**
                 * Gets the "CU_Prime" attribute
                 */
                java.lang.String getCUPrime();
                
                /**
                 * Gets (as xml) the "CU_Prime" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUPrime();
                
                /**
                 * True if has "CU_Prime" attribute
                 */
                boolean isSetCUPrime();
                
                /**
                 * Sets the "CU_Prime" attribute
                 */
                void setCUPrime(java.lang.String cuPrime);
                
                /**
                 * Sets (as xml) the "CU_Prime" attribute
                 */
                void xsetCUPrime(org.apache.xmlbeans.XmlString cuPrime);
                
                /**
                 * Unsets the "CU_Prime" attribute
                 */
                void unsetCUPrime();
                
                /**
                 * Gets the "CU_FaxNo" attribute
                 */
                java.lang.String getCUFaxNo();
                
                /**
                 * Gets (as xml) the "CU_FaxNo" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUFaxNo();
                
                /**
                 * True if has "CU_FaxNo" attribute
                 */
                boolean isSetCUFaxNo();
                
                /**
                 * Sets the "CU_FaxNo" attribute
                 */
                void setCUFaxNo(java.lang.String cuFaxNo);
                
                /**
                 * Sets (as xml) the "CU_FaxNo" attribute
                 */
                void xsetCUFaxNo(org.apache.xmlbeans.XmlString cuFaxNo);
                
                /**
                 * Unsets the "CU_FaxNo" attribute
                 */
                void unsetCUFaxNo();
                
                /**
                 * Gets the "CU_MemType" attribute
                 */
                java.lang.String getCUMemType();
                
                /**
                 * Gets (as xml) the "CU_MemType" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUMemType();
                
                /**
                 * True if has "CU_MemType" attribute
                 */
                boolean isSetCUMemType();
                
                /**
                 * Sets the "CU_MemType" attribute
                 */
                void setCUMemType(java.lang.String cuMemType);
                
                /**
                 * Sets (as xml) the "CU_MemType" attribute
                 */
                void xsetCUMemType(org.apache.xmlbeans.XmlString cuMemType);
                
                /**
                 * Unsets the "CU_MemType" attribute
                 */
                void unsetCUMemType();
                
                /**
                 * Gets the "CU_ContPer" attribute
                 */
                java.lang.String getCUContPer();
                
                /**
                 * Gets (as xml) the "CU_ContPer" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUContPer();
                
                /**
                 * True if has "CU_ContPer" attribute
                 */
                boolean isSetCUContPer();
                
                /**
                 * Sets the "CU_ContPer" attribute
                 */
                void setCUContPer(java.lang.String cuContPer);
                
                /**
                 * Sets (as xml) the "CU_ContPer" attribute
                 */
                void xsetCUContPer(org.apache.xmlbeans.XmlString cuContPer);
                
                /**
                 * Unsets the "CU_ContPer" attribute
                 */
                void unsetCUContPer();
                
                /**
                 * Gets the "CU_MemberNo" attribute
                 */
                java.lang.String getCUMemberNo();
                
                /**
                 * Gets (as xml) the "CU_MemberNo" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUMemberNo();
                
                /**
                 * True if has "CU_MemberNo" attribute
                 */
                boolean isSetCUMemberNo();
                
                /**
                 * Sets the "CU_MemberNo" attribute
                 */
                void setCUMemberNo(java.lang.String cuMemberNo);
                
                /**
                 * Sets (as xml) the "CU_MemberNo" attribute
                 */
                void xsetCUMemberNo(org.apache.xmlbeans.XmlString cuMemberNo);
                
                /**
                 * Unsets the "CU_MemberNo" attribute
                 */
                void unsetCUMemberNo();
                
                /**
                 * Gets the "CU_InterCom" attribute
                 */
                java.lang.String getCUInterCom();
                
                /**
                 * Gets (as xml) the "CU_InterCom" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUInterCom();
                
                /**
                 * True if has "CU_InterCom" attribute
                 */
                boolean isSetCUInterCom();
                
                /**
                 * Sets the "CU_InterCom" attribute
                 */
                void setCUInterCom(java.lang.String cuInterCom);
                
                /**
                 * Sets (as xml) the "CU_InterCom" attribute
                 */
                void xsetCUInterCom(org.apache.xmlbeans.XmlString cuInterCom);
                
                /**
                 * Unsets the "CU_InterCom" attribute
                 */
                void unsetCUInterCom();
                
                /**
                 * Gets the "CU_IDBusType" attribute
                 */
                java.lang.String getCUIDBusType();
                
                /**
                 * Gets (as xml) the "CU_IDBusType" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUIDBusType();
                
                /**
                 * True if has "CU_IDBusType" attribute
                 */
                boolean isSetCUIDBusType();
                
                /**
                 * Sets the "CU_IDBusType" attribute
                 */
                void setCUIDBusType(java.lang.String cuidBusType);
                
                /**
                 * Sets (as xml) the "CU_IDBusType" attribute
                 */
                void xsetCUIDBusType(org.apache.xmlbeans.XmlString cuidBusType);
                
                /**
                 * Unsets the "CU_IDBusType" attribute
                 */
                void unsetCUIDBusType();
                
                /**
                 * Gets the "CU_IDBusNo" attribute
                 */
                java.lang.String getCUIDBusNo();
                
                /**
                 * Gets (as xml) the "CU_IDBusNo" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUIDBusNo();
                
                /**
                 * True if has "CU_IDBusNo" attribute
                 */
                boolean isSetCUIDBusNo();
                
                /**
                 * Sets the "CU_IDBusNo" attribute
                 */
                void setCUIDBusNo(java.lang.String cuidBusNo);
                
                /**
                 * Sets (as xml) the "CU_IDBusNo" attribute
                 */
                void xsetCUIDBusNo(org.apache.xmlbeans.XmlString cuidBusNo);
                
                /**
                 * Unsets the "CU_IDBusNo" attribute
                 */
                void unsetCUIDBusNo();
                
                /**
                 * Gets the "CU_Remark" attribute
                 */
                java.lang.String getCURemark();
                
                /**
                 * Gets (as xml) the "CU_Remark" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCURemark();
                
                /**
                 * True if has "CU_Remark" attribute
                 */
                boolean isSetCURemark();
                
                /**
                 * Sets the "CU_Remark" attribute
                 */
                void setCURemark(java.lang.String cuRemark);
                
                /**
                 * Sets (as xml) the "CU_Remark" attribute
                 */
                void xsetCURemark(org.apache.xmlbeans.XmlString cuRemark);
                
                /**
                 * Unsets the "CU_Remark" attribute
                 */
                void unsetCURemark();
                
                /**
                 * Gets the "CU_Request_Date" attribute
                 */
                java.lang.String getCURequestDate();
                
                /**
                 * Gets (as xml) the "CU_Request_Date" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCURequestDate();
                
                /**
                 * True if has "CU_Request_Date" attribute
                 */
                boolean isSetCURequestDate();
                
                /**
                 * Sets the "CU_Request_Date" attribute
                 */
                void setCURequestDate(java.lang.String cuRequestDate);
                
                /**
                 * Sets (as xml) the "CU_Request_Date" attribute
                 */
                void xsetCURequestDate(org.apache.xmlbeans.XmlString cuRequestDate);
                
                /**
                 * Unsets the "CU_Request_Date" attribute
                 */
                void unsetCURequestDate();
                
                /**
                 * Gets the "CU_Request_Time" attribute
                 */
                java.lang.String getCURequestTime();
                
                /**
                 * Gets (as xml) the "CU_Request_Time" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCURequestTime();
                
                /**
                 * True if has "CU_Request_Time" attribute
                 */
                boolean isSetCURequestTime();
                
                /**
                 * Sets the "CU_Request_Time" attribute
                 */
                void setCURequestTime(java.lang.String cuRequestTime);
                
                /**
                 * Sets (as xml) the "CU_Request_Time" attribute
                 */
                void xsetCURequestTime(org.apache.xmlbeans.XmlString cuRequestTime);
                
                /**
                 * Unsets the "CU_Request_Time" attribute
                 */
                void unsetCURequestTime();
                
                /**
                 * Gets the "CU_PosBadDebt" attribute
                 */
                java.lang.String getCUPosBadDebt();
                
                /**
                 * Gets (as xml) the "CU_PosBadDebt" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUPosBadDebt();
                
                /**
                 * True if has "CU_PosBadDebt" attribute
                 */
                boolean isSetCUPosBadDebt();
                
                /**
                 * Sets the "CU_PosBadDebt" attribute
                 */
                void setCUPosBadDebt(java.lang.String cuPosBadDebt);
                
                /**
                 * Sets (as xml) the "CU_PosBadDebt" attribute
                 */
                void xsetCUPosBadDebt(org.apache.xmlbeans.XmlString cuPosBadDebt);
                
                /**
                 * Unsets the "CU_PosBadDebt" attribute
                 */
                void unsetCUPosBadDebt();
                
                /**
                 * Gets the "CU_CFLATUNIT" attribute
                 */
                java.lang.String getCUCFLATUNIT();
                
                /**
                 * Gets (as xml) the "CU_CFLATUNIT" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUCFLATUNIT();
                
                /**
                 * True if has "CU_CFLATUNIT" attribute
                 */
                boolean isSetCUCFLATUNIT();
                
                /**
                 * Sets the "CU_CFLATUNIT" attribute
                 */
                void setCUCFLATUNIT(java.lang.String cucflatunit);
                
                /**
                 * Sets (as xml) the "CU_CFLATUNIT" attribute
                 */
                void xsetCUCFLATUNIT(org.apache.xmlbeans.XmlString cucflatunit);
                
                /**
                 * Unsets the "CU_CFLATUNIT" attribute
                 */
                void unsetCUCFLATUNIT();
                
                /**
                 * Gets the "CU_CFLOOR" attribute
                 */
                java.lang.String getCUCFLOOR();
                
                /**
                 * Gets (as xml) the "CU_CFLOOR" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUCFLOOR();
                
                /**
                 * True if has "CU_CFLOOR" attribute
                 */
                boolean isSetCUCFLOOR();
                
                /**
                 * Sets the "CU_CFLOOR" attribute
                 */
                void setCUCFLOOR(java.lang.String cucfloor);
                
                /**
                 * Sets (as xml) the "CU_CFLOOR" attribute
                 */
                void xsetCUCFLOOR(org.apache.xmlbeans.XmlString cucfloor);
                
                /**
                 * Unsets the "CU_CFLOOR" attribute
                 */
                void unsetCUCFLOOR();
                
                /**
                 * Gets the "CU_CBLDG" attribute
                 */
                java.lang.String getCUCBLDG();
                
                /**
                 * Gets (as xml) the "CU_CBLDG" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUCBLDG();
                
                /**
                 * True if has "CU_CBLDG" attribute
                 */
                boolean isSetCUCBLDG();
                
                /**
                 * Sets the "CU_CBLDG" attribute
                 */
                void setCUCBLDG(java.lang.String cucbldg);
                
                /**
                 * Sets (as xml) the "CU_CBLDG" attribute
                 */
                void xsetCUCBLDG(org.apache.xmlbeans.XmlString cucbldg);
                
                /**
                 * Unsets the "CU_CBLDG" attribute
                 */
                void unsetCUCBLDG();
                
                /**
                 * Gets the "CU_CPOBOX" attribute
                 */
                java.lang.String getCUCPOBOX();
                
                /**
                 * Gets (as xml) the "CU_CPOBOX" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUCPOBOX();
                
                /**
                 * True if has "CU_CPOBOX" attribute
                 */
                boolean isSetCUCPOBOX();
                
                /**
                 * Sets the "CU_CPOBOX" attribute
                 */
                void setCUCPOBOX(java.lang.String cucpobox);
                
                /**
                 * Sets (as xml) the "CU_CPOBOX" attribute
                 */
                void xsetCUCPOBOX(org.apache.xmlbeans.XmlString cucpobox);
                
                /**
                 * Unsets the "CU_CPOBOX" attribute
                 */
                void unsetCUCPOBOX();
                
                /**
                 * Gets the "CU_CHSESTNO" attribute
                 */
                java.lang.String getCUCHSESTNO();
                
                /**
                 * Gets (as xml) the "CU_CHSESTNO" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUCHSESTNO();
                
                /**
                 * True if has "CU_CHSESTNO" attribute
                 */
                boolean isSetCUCHSESTNO();
                
                /**
                 * Sets the "CU_CHSESTNO" attribute
                 */
                void setCUCHSESTNO(java.lang.String cuchsestno);
                
                /**
                 * Sets (as xml) the "CU_CHSESTNO" attribute
                 */
                void xsetCUCHSESTNO(org.apache.xmlbeans.XmlString cuchsestno);
                
                /**
                 * Unsets the "CU_CHSESTNO" attribute
                 */
                void unsetCUCHSESTNO();
                
                /**
                 * Gets the "CU_CLOTLDNO" attribute
                 */
                java.lang.String getCUCLOTLDNO();
                
                /**
                 * Gets (as xml) the "CU_CLOTLDNO" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUCLOTLDNO();
                
                /**
                 * True if has "CU_CLOTLDNO" attribute
                 */
                boolean isSetCUCLOTLDNO();
                
                /**
                 * Sets the "CU_CLOTLDNO" attribute
                 */
                void setCUCLOTLDNO(java.lang.String cuclotldno);
                
                /**
                 * Sets (as xml) the "CU_CLOTLDNO" attribute
                 */
                void xsetCUCLOTLDNO(org.apache.xmlbeans.XmlString cuclotldno);
                
                /**
                 * Unsets the "CU_CLOTLDNO" attribute
                 */
                void unsetCUCLOTLDNO();
                
                /**
                 * Gets the "CU_CSTNAME" attribute
                 */
                java.lang.String getCUCSTNAME();
                
                /**
                 * Gets (as xml) the "CU_CSTNAME" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUCSTNAME();
                
                /**
                 * True if has "CU_CSTNAME" attribute
                 */
                boolean isSetCUCSTNAME();
                
                /**
                 * Sets the "CU_CSTNAME" attribute
                 */
                void setCUCSTNAME(java.lang.String cucstname);
                
                /**
                 * Sets (as xml) the "CU_CSTNAME" attribute
                 */
                void xsetCUCSTNAME(org.apache.xmlbeans.XmlString cucstname);
                
                /**
                 * Unsets the "CU_CSTNAME" attribute
                 */
                void unsetCUCSTNAME();
                
                /**
                 * Gets the "CU_CSTCAT" attribute
                 */
                java.lang.String getCUCSTCAT();
                
                /**
                 * Gets (as xml) the "CU_CSTCAT" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUCSTCAT();
                
                /**
                 * True if has "CU_CSTCAT" attribute
                 */
                boolean isSetCUCSTCAT();
                
                /**
                 * Sets the "CU_CSTCAT" attribute
                 */
                void setCUCSTCAT(java.lang.String cucstcat);
                
                /**
                 * Sets (as xml) the "CU_CSTCAT" attribute
                 */
                void xsetCUCSTCAT(org.apache.xmlbeans.XmlString cucstcat);
                
                /**
                 * Unsets the "CU_CSTCAT" attribute
                 */
                void unsetCUCSTCAT();
                
                /**
                 * Gets the "CU_CSECT" attribute
                 */
                java.lang.String getCUCSECT();
                
                /**
                 * Gets (as xml) the "CU_CSECT" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUCSECT();
                
                /**
                 * True if has "CU_CSECT" attribute
                 */
                boolean isSetCUCSECT();
                
                /**
                 * Sets the "CU_CSECT" attribute
                 */
                void setCUCSECT(java.lang.String cucsect);
                
                /**
                 * Sets (as xml) the "CU_CSECT" attribute
                 */
                void xsetCUCSECT(org.apache.xmlbeans.XmlString cucsect);
                
                /**
                 * Unsets the "CU_CSECT" attribute
                 */
                void unsetCUCSECT();
                
                /**
                 * Gets the "CU_CDISTR" attribute
                 */
                java.lang.String getCUCDISTR();
                
                /**
                 * Gets (as xml) the "CU_CDISTR" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUCDISTR();
                
                /**
                 * True if has "CU_CDISTR" attribute
                 */
                boolean isSetCUCDISTR();
                
                /**
                 * Sets the "CU_CDISTR" attribute
                 */
                void setCUCDISTR(java.lang.String cucdistr);
                
                /**
                 * Sets (as xml) the "CU_CDISTR" attribute
                 */
                void xsetCUCDISTR(org.apache.xmlbeans.XmlString cucdistr);
                
                /**
                 * Unsets the "CU_CDISTR" attribute
                 */
                void unsetCUCDISTR();
                
                /**
                 * Gets the "CU_CAREA" attribute
                 */
                java.lang.String getCUCAREA();
                
                /**
                 * Gets (as xml) the "CU_CAREA" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUCAREA();
                
                /**
                 * True if has "CU_CAREA" attribute
                 */
                boolean isSetCUCAREA();
                
                /**
                 * Sets the "CU_CAREA" attribute
                 */
                void setCUCAREA(java.lang.String cucarea);
                
                /**
                 * Sets (as xml) the "CU_CAREA" attribute
                 */
                void xsetCUCAREA(org.apache.xmlbeans.XmlString cucarea);
                
                /**
                 * Unsets the "CU_CAREA" attribute
                 */
                void unsetCUCAREA();
                
                /**
                 * Gets the "CU_CPOSTREGN" attribute
                 */
                java.lang.String getCUCPOSTREGN();
                
                /**
                 * Gets (as xml) the "CU_CPOSTREGN" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUCPOSTREGN();
                
                /**
                 * True if has "CU_CPOSTREGN" attribute
                 */
                boolean isSetCUCPOSTREGN();
                
                /**
                 * Sets the "CU_CPOSTREGN" attribute
                 */
                void setCUCPOSTREGN(java.lang.String cucpostregn);
                
                /**
                 * Sets (as xml) the "CU_CPOSTREGN" attribute
                 */
                void xsetCUCPOSTREGN(org.apache.xmlbeans.XmlString cucpostregn);
                
                /**
                 * Unsets the "CU_CPOSTREGN" attribute
                 */
                void unsetCUCPOSTREGN();
                
                /**
                 * Gets the "CU_CPOSTCD" attribute
                 */
                java.lang.String getCUCPOSTCD();
                
                /**
                 * Gets (as xml) the "CU_CPOSTCD" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUCPOSTCD();
                
                /**
                 * True if has "CU_CPOSTCD" attribute
                 */
                boolean isSetCUCPOSTCD();
                
                /**
                 * Sets the "CU_CPOSTCD" attribute
                 */
                void setCUCPOSTCD(java.lang.String cucpostcd);
                
                /**
                 * Sets (as xml) the "CU_CPOSTCD" attribute
                 */
                void xsetCUCPOSTCD(org.apache.xmlbeans.XmlString cucpostcd);
                
                /**
                 * Unsets the "CU_CPOSTCD" attribute
                 */
                void unsetCUCPOSTCD();
                
                /**
                 * Gets the "CU_CCOUNTRY" attribute
                 */
                java.lang.String getCUCCOUNTRY();
                
                /**
                 * Gets (as xml) the "CU_CCOUNTRY" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUCCOUNTRY();
                
                /**
                 * True if has "CU_CCOUNTRY" attribute
                 */
                boolean isSetCUCCOUNTRY();
                
                /**
                 * Sets the "CU_CCOUNTRY" attribute
                 */
                void setCUCCOUNTRY(java.lang.String cuccountry);
                
                /**
                 * Sets (as xml) the "CU_CCOUNTRY" attribute
                 */
                void xsetCUCCOUNTRY(org.apache.xmlbeans.XmlString cuccountry);
                
                /**
                 * Unsets the "CU_CCOUNTRY" attribute
                 */
                void unsetCUCCOUNTRY();
                
                /**
                 * Gets the "CU_CATTETO" attribute
                 */
                java.lang.String getCUCATTETO();
                
                /**
                 * Gets (as xml) the "CU_CATTETO" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUCATTETO();
                
                /**
                 * True if has "CU_CATTETO" attribute
                 */
                boolean isSetCUCATTETO();
                
                /**
                 * Sets the "CU_CATTETO" attribute
                 */
                void setCUCATTETO(java.lang.String cucatteto);
                
                /**
                 * Sets (as xml) the "CU_CATTETO" attribute
                 */
                void xsetCUCATTETO(org.apache.xmlbeans.XmlString cucatteto);
                
                /**
                 * Unsets the "CU_CATTETO" attribute
                 */
                void unsetCUCATTETO();
                
                /**
                 * Gets the "CU_CCO" attribute
                 */
                java.lang.String getCUCCO();
                
                /**
                 * Gets (as xml) the "CU_CCO" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUCCO();
                
                /**
                 * True if has "CU_CCO" attribute
                 */
                boolean isSetCUCCO();
                
                /**
                 * Sets the "CU_CCO" attribute
                 */
                void setCUCCO(java.lang.String cucco);
                
                /**
                 * Sets (as xml) the "CU_CCO" attribute
                 */
                void xsetCUCCO(org.apache.xmlbeans.XmlString cucco);
                
                /**
                 * Unsets the "CU_CCO" attribute
                 */
                void unsetCUCCO();
                
                /**
                 * Gets the "CU_IFLATUNIT" attribute
                 */
                java.lang.String getCUIFLATUNIT();
                
                /**
                 * Gets (as xml) the "CU_IFLATUNIT" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUIFLATUNIT();
                
                /**
                 * True if has "CU_IFLATUNIT" attribute
                 */
                boolean isSetCUIFLATUNIT();
                
                /**
                 * Sets the "CU_IFLATUNIT" attribute
                 */
                void setCUIFLATUNIT(java.lang.String cuiflatunit);
                
                /**
                 * Sets (as xml) the "CU_IFLATUNIT" attribute
                 */
                void xsetCUIFLATUNIT(org.apache.xmlbeans.XmlString cuiflatunit);
                
                /**
                 * Unsets the "CU_IFLATUNIT" attribute
                 */
                void unsetCUIFLATUNIT();
                
                /**
                 * Gets the "CU_IFLOOR" attribute
                 */
                java.lang.String getCUIFLOOR();
                
                /**
                 * Gets (as xml) the "CU_IFLOOR" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUIFLOOR();
                
                /**
                 * True if has "CU_IFLOOR" attribute
                 */
                boolean isSetCUIFLOOR();
                
                /**
                 * Sets the "CU_IFLOOR" attribute
                 */
                void setCUIFLOOR(java.lang.String cuifloor);
                
                /**
                 * Sets (as xml) the "CU_IFLOOR" attribute
                 */
                void xsetCUIFLOOR(org.apache.xmlbeans.XmlString cuifloor);
                
                /**
                 * Unsets the "CU_IFLOOR" attribute
                 */
                void unsetCUIFLOOR();
                
                /**
                 * Gets the "CU_IBLDG" attribute
                 */
                java.lang.String getCUIBLDG();
                
                /**
                 * Gets (as xml) the "CU_IBLDG" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUIBLDG();
                
                /**
                 * True if has "CU_IBLDG" attribute
                 */
                boolean isSetCUIBLDG();
                
                /**
                 * Sets the "CU_IBLDG" attribute
                 */
                void setCUIBLDG(java.lang.String cuibldg);
                
                /**
                 * Sets (as xml) the "CU_IBLDG" attribute
                 */
                void xsetCUIBLDG(org.apache.xmlbeans.XmlString cuibldg);
                
                /**
                 * Unsets the "CU_IBLDG" attribute
                 */
                void unsetCUIBLDG();
                
                /**
                 * Gets the "CU_IPOBOX" attribute
                 */
                java.lang.String getCUIPOBOX();
                
                /**
                 * Gets (as xml) the "CU_IPOBOX" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUIPOBOX();
                
                /**
                 * True if has "CU_IPOBOX" attribute
                 */
                boolean isSetCUIPOBOX();
                
                /**
                 * Sets the "CU_IPOBOX" attribute
                 */
                void setCUIPOBOX(java.lang.String cuipobox);
                
                /**
                 * Sets (as xml) the "CU_IPOBOX" attribute
                 */
                void xsetCUIPOBOX(org.apache.xmlbeans.XmlString cuipobox);
                
                /**
                 * Unsets the "CU_IPOBOX" attribute
                 */
                void unsetCUIPOBOX();
                
                /**
                 * Gets the "CU_IHSESTNO" attribute
                 */
                java.lang.String getCUIHSESTNO();
                
                /**
                 * Gets (as xml) the "CU_IHSESTNO" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUIHSESTNO();
                
                /**
                 * True if has "CU_IHSESTNO" attribute
                 */
                boolean isSetCUIHSESTNO();
                
                /**
                 * Sets the "CU_IHSESTNO" attribute
                 */
                void setCUIHSESTNO(java.lang.String cuihsestno);
                
                /**
                 * Sets (as xml) the "CU_IHSESTNO" attribute
                 */
                void xsetCUIHSESTNO(org.apache.xmlbeans.XmlString cuihsestno);
                
                /**
                 * Unsets the "CU_IHSESTNO" attribute
                 */
                void unsetCUIHSESTNO();
                
                /**
                 * Gets the "CU_ILOTLDNO" attribute
                 */
                java.lang.String getCUILOTLDNO();
                
                /**
                 * Gets (as xml) the "CU_ILOTLDNO" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUILOTLDNO();
                
                /**
                 * True if has "CU_ILOTLDNO" attribute
                 */
                boolean isSetCUILOTLDNO();
                
                /**
                 * Sets the "CU_ILOTLDNO" attribute
                 */
                void setCUILOTLDNO(java.lang.String cuilotldno);
                
                /**
                 * Sets (as xml) the "CU_ILOTLDNO" attribute
                 */
                void xsetCUILOTLDNO(org.apache.xmlbeans.XmlString cuilotldno);
                
                /**
                 * Unsets the "CU_ILOTLDNO" attribute
                 */
                void unsetCUILOTLDNO();
                
                /**
                 * Gets the "CU_ISTNAME" attribute
                 */
                java.lang.String getCUISTNAME();
                
                /**
                 * Gets (as xml) the "CU_ISTNAME" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUISTNAME();
                
                /**
                 * True if has "CU_ISTNAME" attribute
                 */
                boolean isSetCUISTNAME();
                
                /**
                 * Sets the "CU_ISTNAME" attribute
                 */
                void setCUISTNAME(java.lang.String cuistname);
                
                /**
                 * Sets (as xml) the "CU_ISTNAME" attribute
                 */
                void xsetCUISTNAME(org.apache.xmlbeans.XmlString cuistname);
                
                /**
                 * Unsets the "CU_ISTNAME" attribute
                 */
                void unsetCUISTNAME();
                
                /**
                 * Gets the "CU_ISTCAT" attribute
                 */
                java.lang.String getCUISTCAT();
                
                /**
                 * Gets (as xml) the "CU_ISTCAT" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUISTCAT();
                
                /**
                 * True if has "CU_ISTCAT" attribute
                 */
                boolean isSetCUISTCAT();
                
                /**
                 * Sets the "CU_ISTCAT" attribute
                 */
                void setCUISTCAT(java.lang.String cuistcat);
                
                /**
                 * Sets (as xml) the "CU_ISTCAT" attribute
                 */
                void xsetCUISTCAT(org.apache.xmlbeans.XmlString cuistcat);
                
                /**
                 * Unsets the "CU_ISTCAT" attribute
                 */
                void unsetCUISTCAT();
                
                /**
                 * Gets the "CU_ISECT" attribute
                 */
                java.lang.String getCUISECT();
                
                /**
                 * Gets (as xml) the "CU_ISECT" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUISECT();
                
                /**
                 * True if has "CU_ISECT" attribute
                 */
                boolean isSetCUISECT();
                
                /**
                 * Sets the "CU_ISECT" attribute
                 */
                void setCUISECT(java.lang.String cuisect);
                
                /**
                 * Sets (as xml) the "CU_ISECT" attribute
                 */
                void xsetCUISECT(org.apache.xmlbeans.XmlString cuisect);
                
                /**
                 * Unsets the "CU_ISECT" attribute
                 */
                void unsetCUISECT();
                
                /**
                 * Gets the "CU_IDISTR" attribute
                 */
                java.lang.String getCUIDISTR();
                
                /**
                 * Gets (as xml) the "CU_IDISTR" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUIDISTR();
                
                /**
                 * True if has "CU_IDISTR" attribute
                 */
                boolean isSetCUIDISTR();
                
                /**
                 * Sets the "CU_IDISTR" attribute
                 */
                void setCUIDISTR(java.lang.String cuidistr);
                
                /**
                 * Sets (as xml) the "CU_IDISTR" attribute
                 */
                void xsetCUIDISTR(org.apache.xmlbeans.XmlString cuidistr);
                
                /**
                 * Unsets the "CU_IDISTR" attribute
                 */
                void unsetCUIDISTR();
                
                /**
                 * Gets the "CU_IAREA" attribute
                 */
                java.lang.String getCUIAREA();
                
                /**
                 * Gets (as xml) the "CU_IAREA" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUIAREA();
                
                /**
                 * True if has "CU_IAREA" attribute
                 */
                boolean isSetCUIAREA();
                
                /**
                 * Sets the "CU_IAREA" attribute
                 */
                void setCUIAREA(java.lang.String cuiarea);
                
                /**
                 * Sets (as xml) the "CU_IAREA" attribute
                 */
                void xsetCUIAREA(org.apache.xmlbeans.XmlString cuiarea);
                
                /**
                 * Unsets the "CU_IAREA" attribute
                 */
                void unsetCUIAREA();
                
                /**
                 * Gets the "CU_IPOSTREGN" attribute
                 */
                java.lang.String getCUIPOSTREGN();
                
                /**
                 * Gets (as xml) the "CU_IPOSTREGN" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUIPOSTREGN();
                
                /**
                 * True if has "CU_IPOSTREGN" attribute
                 */
                boolean isSetCUIPOSTREGN();
                
                /**
                 * Sets the "CU_IPOSTREGN" attribute
                 */
                void setCUIPOSTREGN(java.lang.String cuipostregn);
                
                /**
                 * Sets (as xml) the "CU_IPOSTREGN" attribute
                 */
                void xsetCUIPOSTREGN(org.apache.xmlbeans.XmlString cuipostregn);
                
                /**
                 * Unsets the "CU_IPOSTREGN" attribute
                 */
                void unsetCUIPOSTREGN();
                
                /**
                 * Gets the "CU_IPOSTCD" attribute
                 */
                java.lang.String getCUIPOSTCD();
                
                /**
                 * Gets (as xml) the "CU_IPOSTCD" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUIPOSTCD();
                
                /**
                 * True if has "CU_IPOSTCD" attribute
                 */
                boolean isSetCUIPOSTCD();
                
                /**
                 * Sets the "CU_IPOSTCD" attribute
                 */
                void setCUIPOSTCD(java.lang.String cuipostcd);
                
                /**
                 * Sets (as xml) the "CU_IPOSTCD" attribute
                 */
                void xsetCUIPOSTCD(org.apache.xmlbeans.XmlString cuipostcd);
                
                /**
                 * Unsets the "CU_IPOSTCD" attribute
                 */
                void unsetCUIPOSTCD();
                
                /**
                 * Gets the "CU_ICOUNTRY" attribute
                 */
                java.lang.String getCUICOUNTRY();
                
                /**
                 * Gets (as xml) the "CU_ICOUNTRY" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUICOUNTRY();
                
                /**
                 * True if has "CU_ICOUNTRY" attribute
                 */
                boolean isSetCUICOUNTRY();
                
                /**
                 * Sets the "CU_ICOUNTRY" attribute
                 */
                void setCUICOUNTRY(java.lang.String cuicountry);
                
                /**
                 * Sets (as xml) the "CU_ICOUNTRY" attribute
                 */
                void xsetCUICOUNTRY(org.apache.xmlbeans.XmlString cuicountry);
                
                /**
                 * Unsets the "CU_ICOUNTRY" attribute
                 */
                void unsetCUICOUNTRY();
                
                /**
                 * Gets the "CPEFLAG" attribute
                 */
                java.lang.String getCPEFLAG();
                
                /**
                 * Gets (as xml) the "CPEFLAG" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCPEFLAG();
                
                /**
                 * True if has "CPEFLAG" attribute
                 */
                boolean isSetCPEFLAG();
                
                /**
                 * Sets the "CPEFLAG" attribute
                 */
                void setCPEFLAG(java.lang.String cpeflag);
                
                /**
                 * Sets (as xml) the "CPEFLAG" attribute
                 */
                void xsetCPEFLAG(org.apache.xmlbeans.XmlString cpeflag);
                
                /**
                 * Unsets the "CPEFLAG" attribute
                 */
                void unsetCPEFLAG();
                
                /**
                 * Gets the "Trans_Discount" attribute
                 */
                java.lang.String getTransDiscount();
                
                /**
                 * Gets (as xml) the "Trans_Discount" attribute
                 */
                org.apache.xmlbeans.XmlString xgetTransDiscount();
                
                /**
                 * True if has "Trans_Discount" attribute
                 */
                boolean isSetTransDiscount();
                
                /**
                 * Sets the "Trans_Discount" attribute
                 */
                void setTransDiscount(java.lang.String transDiscount);
                
                /**
                 * Sets (as xml) the "Trans_Discount" attribute
                 */
                void xsetTransDiscount(org.apache.xmlbeans.XmlString transDiscount);
                
                /**
                 * Unsets the "Trans_Discount" attribute
                 */
                void unsetTransDiscount();
                
                /**
                 * Gets the "Trans_Reason" attribute
                 */
                java.lang.String getTransReason();
                
                /**
                 * Gets (as xml) the "Trans_Reason" attribute
                 */
                org.apache.xmlbeans.XmlString xgetTransReason();
                
                /**
                 * True if has "Trans_Reason" attribute
                 */
                boolean isSetTransReason();
                
                /**
                 * Sets the "Trans_Reason" attribute
                 */
                void setTransReason(java.lang.String transReason);
                
                /**
                 * Sets (as xml) the "Trans_Reason" attribute
                 */
                void xsetTransReason(org.apache.xmlbeans.XmlString transReason);
                
                /**
                 * Unsets the "Trans_Reason" attribute
                 */
                void unsetTransReason();
                
                /**
                 * Gets the "CPE_Work_Num" attribute
                 */
                java.lang.String getCPEWorkNum();
                
                /**
                 * Gets (as xml) the "CPE_Work_Num" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCPEWorkNum();
                
                /**
                 * True if has "CPE_Work_Num" attribute
                 */
                boolean isSetCPEWorkNum();
                
                /**
                 * Sets the "CPE_Work_Num" attribute
                 */
                void setCPEWorkNum(java.lang.String cpeWorkNum);
                
                /**
                 * Sets (as xml) the "CPE_Work_Num" attribute
                 */
                void xsetCPEWorkNum(org.apache.xmlbeans.XmlString cpeWorkNum);
                
                /**
                 * Unsets the "CPE_Work_Num" attribute
                 */
                void unsetCPEWorkNum();
                
                /**
                 * Gets the "Converted" attribute
                 */
                java.lang.String getConverted();
                
                /**
                 * Gets (as xml) the "Converted" attribute
                 */
                org.apache.xmlbeans.XmlString xgetConverted();
                
                /**
                 * True if has "Converted" attribute
                 */
                boolean isSetConverted();
                
                /**
                 * Sets the "Converted" attribute
                 */
                void setConverted(java.lang.String converted);
                
                /**
                 * Sets (as xml) the "Converted" attribute
                 */
                void xsetConverted(org.apache.xmlbeans.XmlString converted);
                
                /**
                 * Unsets the "Converted" attribute
                 */
                void unsetConverted();
                
                /**
                 * Gets the "Org_QU_Num" attribute
                 */
                java.lang.String getOrgQUNum();
                
                /**
                 * Gets (as xml) the "Org_QU_Num" attribute
                 */
                org.apache.xmlbeans.XmlString xgetOrgQUNum();
                
                /**
                 * True if has "Org_QU_Num" attribute
                 */
                boolean isSetOrgQUNum();
                
                /**
                 * Sets the "Org_QU_Num" attribute
                 */
                void setOrgQUNum(java.lang.String orgQUNum);
                
                /**
                 * Sets (as xml) the "Org_QU_Num" attribute
                 */
                void xsetOrgQUNum(org.apache.xmlbeans.XmlString orgQUNum);
                
                /**
                 * Unsets the "Org_QU_Num" attribute
                 */
                void unsetOrgQUNum();
                
                /**
                 * Gets the "Ref_SM_No" attribute
                 */
                java.lang.String getRefSMNo();
                
                /**
                 * Gets (as xml) the "Ref_SM_No" attribute
                 */
                org.apache.xmlbeans.XmlString xgetRefSMNo();
                
                /**
                 * True if has "Ref_SM_No" attribute
                 */
                boolean isSetRefSMNo();
                
                /**
                 * Sets the "Ref_SM_No" attribute
                 */
                void setRefSMNo(java.lang.String refSMNo);
                
                /**
                 * Sets (as xml) the "Ref_SM_No" attribute
                 */
                void xsetRefSMNo(org.apache.xmlbeans.XmlString refSMNo);
                
                /**
                 * Unsets the "Ref_SM_No" attribute
                 */
                void unsetRefSMNo();
                
                /**
                 * Gets the "Invalidated_Flag" attribute
                 */
                java.lang.String getInvalidatedFlag();
                
                /**
                 * Gets (as xml) the "Invalidated_Flag" attribute
                 */
                org.apache.xmlbeans.XmlString xgetInvalidatedFlag();
                
                /**
                 * True if has "Invalidated_Flag" attribute
                 */
                boolean isSetInvalidatedFlag();
                
                /**
                 * Sets the "Invalidated_Flag" attribute
                 */
                void setInvalidatedFlag(java.lang.String invalidatedFlag);
                
                /**
                 * Sets (as xml) the "Invalidated_Flag" attribute
                 */
                void xsetInvalidatedFlag(org.apache.xmlbeans.XmlString invalidatedFlag);
                
                /**
                 * Unsets the "Invalidated_Flag" attribute
                 */
                void unsetInvalidatedFlag();
                
                /**
                 * Gets the "ExchangeFlag" attribute
                 */
                java.lang.String getExchangeFlag();
                
                /**
                 * Gets (as xml) the "ExchangeFlag" attribute
                 */
                org.apache.xmlbeans.XmlString xgetExchangeFlag();
                
                /**
                 * True if has "ExchangeFlag" attribute
                 */
                boolean isSetExchangeFlag();
                
                /**
                 * Sets the "ExchangeFlag" attribute
                 */
                void setExchangeFlag(java.lang.String exchangeFlag);
                
                /**
                 * Sets (as xml) the "ExchangeFlag" attribute
                 */
                void xsetExchangeFlag(org.apache.xmlbeans.XmlString exchangeFlag);
                
                /**
                 * Unsets the "ExchangeFlag" attribute
                 */
                void unsetExchangeFlag();
                
                /**
                 * Gets the "Download" attribute
                 */
                java.lang.String getDownload();
                
                /**
                 * Gets (as xml) the "Download" attribute
                 */
                org.apache.xmlbeans.XmlString xgetDownload();
                
                /**
                 * True if has "Download" attribute
                 */
                boolean isSetDownload();
                
                /**
                 * Sets the "Download" attribute
                 */
                void setDownload(java.lang.String download);
                
                /**
                 * Sets (as xml) the "Download" attribute
                 */
                void xsetDownload(org.apache.xmlbeans.XmlString download);
                
                /**
                 * Unsets the "Download" attribute
                 */
                void unsetDownload();
                
                /**
                 * Gets the "CU_Email" attribute
                 */
                java.lang.String getCUEmail();
                
                /**
                 * Gets (as xml) the "CU_Email" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUEmail();
                
                /**
                 * True if has "CU_Email" attribute
                 */
                boolean isSetCUEmail();
                
                /**
                 * Sets the "CU_Email" attribute
                 */
                void setCUEmail(java.lang.String cuEmail);
                
                /**
                 * Sets (as xml) the "CU_Email" attribute
                 */
                void xsetCUEmail(org.apache.xmlbeans.XmlString cuEmail);
                
                /**
                 * Unsets the "CU_Email" attribute
                 */
                void unsetCUEmail();
                
                /**
                 * Gets the "Store_Type" attribute
                 */
                java.lang.String getStoreType();
                
                /**
                 * Gets (as xml) the "Store_Type" attribute
                 */
                org.apache.xmlbeans.XmlString xgetStoreType();
                
                /**
                 * True if has "Store_Type" attribute
                 */
                boolean isSetStoreType();
                
                /**
                 * Sets the "Store_Type" attribute
                 */
                void setStoreType(java.lang.String storeType);
                
                /**
                 * Sets (as xml) the "Store_Type" attribute
                 */
                void xsetStoreType(org.apache.xmlbeans.XmlString storeType);
                
                /**
                 * Unsets the "Store_Type" attribute
                 */
                void unsetStoreType();
                
                /**
                 * Gets the "Store_Name" attribute
                 */
                java.lang.String getStoreName();
                
                /**
                 * Gets (as xml) the "Store_Name" attribute
                 */
                org.apache.xmlbeans.XmlString xgetStoreName();
                
                /**
                 * True if has "Store_Name" attribute
                 */
                boolean isSetStoreName();
                
                /**
                 * Sets the "Store_Name" attribute
                 */
                void setStoreName(java.lang.String storeName);
                
                /**
                 * Sets (as xml) the "Store_Name" attribute
                 */
                void xsetStoreName(org.apache.xmlbeans.XmlString storeName);
                
                /**
                 * Unsets the "Store_Name" attribute
                 */
                void unsetStoreName();
                
                /**
                 * Gets the "Store_Tel" attribute
                 */
                java.lang.String getStoreTel();
                
                /**
                 * Gets (as xml) the "Store_Tel" attribute
                 */
                org.apache.xmlbeans.XmlString xgetStoreTel();
                
                /**
                 * True if has "Store_Tel" attribute
                 */
                boolean isSetStoreTel();
                
                /**
                 * Sets the "Store_Tel" attribute
                 */
                void setStoreTel(java.lang.String storeTel);
                
                /**
                 * Sets (as xml) the "Store_Tel" attribute
                 */
                void xsetStoreTel(org.apache.xmlbeans.XmlString storeTel);
                
                /**
                 * Unsets the "Store_Tel" attribute
                 */
                void unsetStoreTel();
                
                /**
                 * Gets the "Store_Begin_Hours" attribute
                 */
                java.lang.String getStoreBeginHours();
                
                /**
                 * Gets (as xml) the "Store_Begin_Hours" attribute
                 */
                org.apache.xmlbeans.XmlString xgetStoreBeginHours();
                
                /**
                 * True if has "Store_Begin_Hours" attribute
                 */
                boolean isSetStoreBeginHours();
                
                /**
                 * Sets the "Store_Begin_Hours" attribute
                 */
                void setStoreBeginHours(java.lang.String storeBeginHours);
                
                /**
                 * Sets (as xml) the "Store_Begin_Hours" attribute
                 */
                void xsetStoreBeginHours(org.apache.xmlbeans.XmlString storeBeginHours);
                
                /**
                 * Unsets the "Store_Begin_Hours" attribute
                 */
                void unsetStoreBeginHours();
                
                /**
                 * Gets the "Store_End_Hours" attribute
                 */
                java.lang.String getStoreEndHours();
                
                /**
                 * Gets (as xml) the "Store_End_Hours" attribute
                 */
                org.apache.xmlbeans.XmlString xgetStoreEndHours();
                
                /**
                 * True if has "Store_End_Hours" attribute
                 */
                boolean isSetStoreEndHours();
                
                /**
                 * Sets the "Store_End_Hours" attribute
                 */
                void setStoreEndHours(java.lang.String storeEndHours);
                
                /**
                 * Sets (as xml) the "Store_End_Hours" attribute
                 */
                void xsetStoreEndHours(org.apache.xmlbeans.XmlString storeEndHours);
                
                /**
                 * Unsets the "Store_End_Hours" attribute
                 */
                void unsetStoreEndHours();
                
                /**
                 * Gets the "Store_Addr_1" attribute
                 */
                java.lang.String getStoreAddr1();
                
                /**
                 * Gets (as xml) the "Store_Addr_1" attribute
                 */
                org.apache.xmlbeans.XmlString xgetStoreAddr1();
                
                /**
                 * True if has "Store_Addr_1" attribute
                 */
                boolean isSetStoreAddr1();
                
                /**
                 * Sets the "Store_Addr_1" attribute
                 */
                void setStoreAddr1(java.lang.String storeAddr1);
                
                /**
                 * Sets (as xml) the "Store_Addr_1" attribute
                 */
                void xsetStoreAddr1(org.apache.xmlbeans.XmlString storeAddr1);
                
                /**
                 * Unsets the "Store_Addr_1" attribute
                 */
                void unsetStoreAddr1();
                
                /**
                 * Gets the "Store_Addr_2" attribute
                 */
                java.lang.String getStoreAddr2();
                
                /**
                 * Gets (as xml) the "Store_Addr_2" attribute
                 */
                org.apache.xmlbeans.XmlString xgetStoreAddr2();
                
                /**
                 * True if has "Store_Addr_2" attribute
                 */
                boolean isSetStoreAddr2();
                
                /**
                 * Sets the "Store_Addr_2" attribute
                 */
                void setStoreAddr2(java.lang.String storeAddr2);
                
                /**
                 * Sets (as xml) the "Store_Addr_2" attribute
                 */
                void xsetStoreAddr2(org.apache.xmlbeans.XmlString storeAddr2);
                
                /**
                 * Unsets the "Store_Addr_2" attribute
                 */
                void unsetStoreAddr2();
                
                /**
                 * Gets the "Store_Addr_3" attribute
                 */
                java.lang.String getStoreAddr3();
                
                /**
                 * Gets (as xml) the "Store_Addr_3" attribute
                 */
                org.apache.xmlbeans.XmlString xgetStoreAddr3();
                
                /**
                 * True if has "Store_Addr_3" attribute
                 */
                boolean isSetStoreAddr3();
                
                /**
                 * Sets the "Store_Addr_3" attribute
                 */
                void setStoreAddr3(java.lang.String storeAddr3);
                
                /**
                 * Sets (as xml) the "Store_Addr_3" attribute
                 */
                void xsetStoreAddr3(org.apache.xmlbeans.XmlString storeAddr3);
                
                /**
                 * Unsets the "Store_Addr_3" attribute
                 */
                void unsetStoreAddr3();
                
                /**
                 * Gets the "Salesman_Name" attribute
                 */
                java.lang.String getSalesmanName();
                
                /**
                 * Gets (as xml) the "Salesman_Name" attribute
                 */
                org.apache.xmlbeans.XmlString xgetSalesmanName();
                
                /**
                 * True if has "Salesman_Name" attribute
                 */
                boolean isSetSalesmanName();
                
                /**
                 * Sets the "Salesman_Name" attribute
                 */
                void setSalesmanName(java.lang.String salesmanName);
                
                /**
                 * Sets (as xml) the "Salesman_Name" attribute
                 */
                void xsetSalesmanName(org.apache.xmlbeans.XmlString salesmanName);
                
                /**
                 * Unsets the "Salesman_Name" attribute
                 */
                void unsetSalesmanName();
                
                /**
                 * Gets the "Settle_Staff_Name" attribute
                 */
                java.lang.String getSettleStaffName();
                
                /**
                 * Gets (as xml) the "Settle_Staff_Name" attribute
                 */
                org.apache.xmlbeans.XmlString xgetSettleStaffName();
                
                /**
                 * True if has "Settle_Staff_Name" attribute
                 */
                boolean isSetSettleStaffName();
                
                /**
                 * Sets the "Settle_Staff_Name" attribute
                 */
                void setSettleStaffName(java.lang.String settleStaffName);
                
                /**
                 * Sets (as xml) the "Settle_Staff_Name" attribute
                 */
                void xsetSettleStaffName(org.apache.xmlbeans.XmlString settleStaffName);
                
                /**
                 * Unsets the "Settle_Staff_Name" attribute
                 */
                void unsetSettleStaffName();
                
                /**
                 * Gets the "CMR" attribute
                 */
                java.lang.String getCMR();
                
                /**
                 * Gets (as xml) the "CMR" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCMR();
                
                /**
                 * True if has "CMR" attribute
                 */
                boolean isSetCMR();
                
                /**
                 * Sets the "CMR" attribute
                 */
                void setCMR(java.lang.String cmr);
                
                /**
                 * Sets (as xml) the "CMR" attribute
                 */
                void xsetCMR(org.apache.xmlbeans.XmlString cmr);
                
                /**
                 * Unsets the "CMR" attribute
                 */
                void unsetCMR();
                
                /**
                 * Gets the "UM" attribute
                 */
                java.lang.String getUM();
                
                /**
                 * Gets (as xml) the "UM" attribute
                 */
                org.apache.xmlbeans.XmlString xgetUM();
                
                /**
                 * True if has "UM" attribute
                 */
                boolean isSetUM();
                
                /**
                 * Sets the "UM" attribute
                 */
                void setUM(java.lang.String um);
                
                /**
                 * Sets (as xml) the "UM" attribute
                 */
                void xsetUM(org.apache.xmlbeans.XmlString um);
                
                /**
                 * Unsets the "UM" attribute
                 */
                void unsetUM();
                
                /**
                 * Gets the "Title" attribute
                 */
                java.lang.String getTitle();
                
                /**
                 * Gets (as xml) the "Title" attribute
                 */
                org.apache.xmlbeans.XmlString xgetTitle();
                
                /**
                 * True if has "Title" attribute
                 */
                boolean isSetTitle();
                
                /**
                 * Sets the "Title" attribute
                 */
                void setTitle(java.lang.String title);
                
                /**
                 * Sets (as xml) the "Title" attribute
                 */
                void xsetTitle(org.apache.xmlbeans.XmlString title);
                
                /**
                 * Unsets the "Title" attribute
                 */
                void unsetTitle();
                
                /**
                 * Gets the "Team_Name" attribute
                 */
                java.lang.String getTeamName();
                
                /**
                 * Gets (as xml) the "Team_Name" attribute
                 */
                org.apache.xmlbeans.XmlString xgetTeamName();
                
                /**
                 * True if has "Team_Name" attribute
                 */
                boolean isSetTeamName();
                
                /**
                 * Sets the "Team_Name" attribute
                 */
                void setTeamName(java.lang.String teamName);
                
                /**
                 * Sets (as xml) the "Team_Name" attribute
                 */
                void xsetTeamName(org.apache.xmlbeans.XmlString teamName);
                
                /**
                 * Unsets the "Team_Name" attribute
                 */
                void unsetTeamName();
                
                /**
                 * Gets the "Dep" attribute
                 */
                java.lang.String getDep();
                
                /**
                 * Gets (as xml) the "Dep" attribute
                 */
                org.apache.xmlbeans.XmlString xgetDep();
                
                /**
                 * True if has "Dep" attribute
                 */
                boolean isSetDep();
                
                /**
                 * Sets the "Dep" attribute
                 */
                void setDep(java.lang.String dep);
                
                /**
                 * Sets (as xml) the "Dep" attribute
                 */
                void xsetDep(org.apache.xmlbeans.XmlString dep);
                
                /**
                 * Unsets the "Dep" attribute
                 */
                void unsetDep();
                
                /**
                 * Gets the "Dep_Name" attribute
                 */
                java.lang.String getDepName();
                
                /**
                 * Gets (as xml) the "Dep_Name" attribute
                 */
                org.apache.xmlbeans.XmlString xgetDepName();
                
                /**
                 * True if has "Dep_Name" attribute
                 */
                boolean isSetDepName();
                
                /**
                 * Sets the "Dep_Name" attribute
                 */
                void setDepName(java.lang.String depName);
                
                /**
                 * Sets (as xml) the "Dep_Name" attribute
                 */
                void xsetDepName(org.apache.xmlbeans.XmlString depName);
                
                /**
                 * Unsets the "Dep_Name" attribute
                 */
                void unsetDepName();
                
                /**
                 * Gets the "coll_time" attribute
                 */
                java.lang.String getCollTime();
                
                /**
                 * Gets (as xml) the "coll_time" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCollTime();
                
                /**
                 * True if has "coll_time" attribute
                 */
                boolean isSetCollTime();
                
                /**
                 * Sets the "coll_time" attribute
                 */
                void setCollTime(java.lang.String collTime);
                
                /**
                 * Sets (as xml) the "coll_time" attribute
                 */
                void xsetCollTime(org.apache.xmlbeans.XmlString collTime);
                
                /**
                 * Unsets the "coll_time" attribute
                 */
                void unsetCollTime();
                
                /**
                 * Gets the "IMS_Acno" attribute
                 */
                java.lang.String getIMSAcno();
                
                /**
                 * Gets (as xml) the "IMS_Acno" attribute
                 */
                org.apache.xmlbeans.XmlString xgetIMSAcno();
                
                /**
                 * True if has "IMS_Acno" attribute
                 */
                boolean isSetIMSAcno();
                
                /**
                 * Sets the "IMS_Acno" attribute
                 */
                void setIMSAcno(java.lang.String imsAcno);
                
                /**
                 * Sets (as xml) the "IMS_Acno" attribute
                 */
                void xsetIMSAcno(org.apache.xmlbeans.XmlString imsAcno);
                
                /**
                 * Unsets the "IMS_Acno" attribute
                 */
                void unsetIMSAcno();
                
                /**
                 * Gets the "Paid_Amt" attribute
                 */
                short getPaidAmt();
                
                /**
                 * Gets (as xml) the "Paid_Amt" attribute
                 */
                org.apache.xmlbeans.XmlShort xgetPaidAmt();
                
                /**
                 * True if has "Paid_Amt" attribute
                 */
                boolean isSetPaidAmt();
                
                /**
                 * Sets the "Paid_Amt" attribute
                 */
                void setPaidAmt(short paidAmt);
                
                /**
                 * Sets (as xml) the "Paid_Amt" attribute
                 */
                void xsetPaidAmt(org.apache.xmlbeans.XmlShort paidAmt);
                
                /**
                 * Unsets the "Paid_Amt" attribute
                 */
                void unsetPaidAmt();
                
                /**
                 * Gets the "Txn_Date_POS_BOM" attribute
                 */
                java.lang.String getTxnDatePOSBOM();
                
                /**
                 * Gets (as xml) the "Txn_Date_POS_BOM" attribute
                 */
                org.apache.xmlbeans.XmlString xgetTxnDatePOSBOM();
                
                /**
                 * True if has "Txn_Date_POS_BOM" attribute
                 */
                boolean isSetTxnDatePOSBOM();
                
                /**
                 * Sets the "Txn_Date_POS_BOM" attribute
                 */
                void setTxnDatePOSBOM(java.lang.String txnDatePOSBOM);
                
                /**
                 * Sets (as xml) the "Txn_Date_POS_BOM" attribute
                 */
                void xsetTxnDatePOSBOM(org.apache.xmlbeans.XmlString txnDatePOSBOM);
                
                /**
                 * Unsets the "Txn_Date_POS_BOM" attribute
                 */
                void unsetTxnDatePOSBOM();
                
                /**
                 * Gets the "Txn_Time_POS_BOM" attribute
                 */
                java.lang.String getTxnTimePOSBOM();
                
                /**
                 * Gets (as xml) the "Txn_Time_POS_BOM" attribute
                 */
                org.apache.xmlbeans.XmlString xgetTxnTimePOSBOM();
                
                /**
                 * True if has "Txn_Time_POS_BOM" attribute
                 */
                boolean isSetTxnTimePOSBOM();
                
                /**
                 * Sets the "Txn_Time_POS_BOM" attribute
                 */
                void setTxnTimePOSBOM(java.lang.String txnTimePOSBOM);
                
                /**
                 * Sets (as xml) the "Txn_Time_POS_BOM" attribute
                 */
                void xsetTxnTimePOSBOM(org.apache.xmlbeans.XmlString txnTimePOSBOM);
                
                /**
                 * Unsets the "Txn_Time_POS_BOM" attribute
                 */
                void unsetTxnTimePOSBOM();
                
                /**
                 * Gets the "OCID" attribute
                 */
                java.lang.String getOCID();
                
                /**
                 * Gets (as xml) the "OCID" attribute
                 */
                org.apache.xmlbeans.XmlString xgetOCID();
                
                /**
                 * True if has "OCID" attribute
                 */
                boolean isSetOCID();
                
                /**
                 * Sets the "OCID" attribute
                 */
                void setOCID(java.lang.String ocid);
                
                /**
                 * Sets (as xml) the "OCID" attribute
                 */
                void xsetOCID(org.apache.xmlbeans.XmlString ocid);
                
                /**
                 * Unsets the "OCID" attribute
                 */
                void unsetOCID();
                
                /**
                 * Gets the "Request_ID" attribute
                 */
                java.lang.String getRequestID();
                
                /**
                 * Gets (as xml) the "Request_ID" attribute
                 */
                org.apache.xmlbeans.XmlString xgetRequestID();
                
                /**
                 * True if has "Request_ID" attribute
                 */
                boolean isSetRequestID();
                
                /**
                 * Sets the "Request_ID" attribute
                 */
                void setRequestID(java.lang.String requestID);
                
                /**
                 * Sets (as xml) the "Request_ID" attribute
                 */
                void xsetRequestID(org.apache.xmlbeans.XmlString requestID);
                
                /**
                 * Unsets the "Request_ID" attribute
                 */
                void unsetRequestID();
                
                /**
                 * Gets the "Customer_ID" attribute
                 */
                java.lang.String getCustomerID();
                
                /**
                 * Gets (as xml) the "Customer_ID" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCustomerID();
                
                /**
                 * True if has "Customer_ID" attribute
                 */
                boolean isSetCustomerID();
                
                /**
                 * Sets the "Customer_ID" attribute
                 */
                void setCustomerID(java.lang.String customerID);
                
                /**
                 * Sets (as xml) the "Customer_ID" attribute
                 */
                void xsetCustomerID(org.apache.xmlbeans.XmlString customerID);
                
                /**
                 * Unsets the "Customer_ID" attribute
                 */
                void unsetCustomerID();
                
                /**
                 * Gets the "Account_Code" attribute
                 */
                java.lang.String getAccountCode();
                
                /**
                 * Gets (as xml) the "Account_Code" attribute
                 */
                org.apache.xmlbeans.XmlString xgetAccountCode();
                
                /**
                 * True if has "Account_Code" attribute
                 */
                boolean isSetAccountCode();
                
                /**
                 * Sets the "Account_Code" attribute
                 */
                void setAccountCode(java.lang.String accountCode);
                
                /**
                 * Sets (as xml) the "Account_Code" attribute
                 */
                void xsetAccountCode(org.apache.xmlbeans.XmlString accountCode);
                
                /**
                 * Unsets the "Account_Code" attribute
                 */
                void unsetAccountCode();
                
                /**
                 * Gets the "SSAgreeNo" attribute
                 */
                java.lang.String getSSAgreeNo();
                
                /**
                 * Gets (as xml) the "SSAgreeNo" attribute
                 */
                org.apache.xmlbeans.XmlString xgetSSAgreeNo();
                
                /**
                 * True if has "SSAgreeNo" attribute
                 */
                boolean isSetSSAgreeNo();
                
                /**
                 * Sets the "SSAgreeNo" attribute
                 */
                void setSSAgreeNo(java.lang.String ssAgreeNo);
                
                /**
                 * Sets (as xml) the "SSAgreeNo" attribute
                 */
                void xsetSSAgreeNo(org.apache.xmlbeans.XmlString ssAgreeNo);
                
                /**
                 * Unsets the "SSAgreeNo" attribute
                 */
                void unsetSSAgreeNo();
                
                /**
                 * Gets the "CU_MobileNo" attribute
                 */
                java.lang.String getCUMobileNo();
                
                /**
                 * Gets (as xml) the "CU_MobileNo" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUMobileNo();
                
                /**
                 * True if has "CU_MobileNo" attribute
                 */
                boolean isSetCUMobileNo();
                
                /**
                 * Sets the "CU_MobileNo" attribute
                 */
                void setCUMobileNo(java.lang.String cuMobileNo);
                
                /**
                 * Sets (as xml) the "CU_MobileNo" attribute
                 */
                void xsetCUMobileNo(org.apache.xmlbeans.XmlString cuMobileNo);
                
                /**
                 * Unsets the "CU_MobileNo" attribute
                 */
                void unsetCUMobileNo();
                
                /**
                 * Gets the "MobileNoType" attribute
                 */
                java.lang.String getMobileNoType();
                
                /**
                 * Gets (as xml) the "MobileNoType" attribute
                 */
                org.apache.xmlbeans.XmlString xgetMobileNoType();
                
                /**
                 * True if has "MobileNoType" attribute
                 */
                boolean isSetMobileNoType();
                
                /**
                 * Sets the "MobileNoType" attribute
                 */
                void setMobileNoType(java.lang.String mobileNoType);
                
                /**
                 * Sets (as xml) the "MobileNoType" attribute
                 */
                void xsetMobileNoType(org.apache.xmlbeans.XmlString mobileNoType);
                
                /**
                 * Unsets the "MobileNoType" attribute
                 */
                void unsetMobileNoType();
                
                /**
                 * Gets the "MobileNo" attribute
                 */
                java.lang.String getMobileNo();
                
                /**
                 * Gets (as xml) the "MobileNo" attribute
                 */
                org.apache.xmlbeans.XmlString xgetMobileNo();
                
                /**
                 * True if has "MobileNo" attribute
                 */
                boolean isSetMobileNo();
                
                /**
                 * Sets the "MobileNo" attribute
                 */
                void setMobileNo(java.lang.String mobileNo);
                
                /**
                 * Sets (as xml) the "MobileNo" attribute
                 */
                void xsetMobileNo(org.apache.xmlbeans.XmlString mobileNo);
                
                /**
                 * Unsets the "MobileNo" attribute
                 */
                void unsetMobileNo();
                
                /**
                 * A factory class with static methods for creating instances
                 * of this type.
                 */
                
                public static final class Factory
                {
                    public static noNamespace.SalesHDocument.SalesH.ROWDATA.ROW newInstance() {
                      return (noNamespace.SalesHDocument.SalesH.ROWDATA.ROW) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
                    
                    public static noNamespace.SalesHDocument.SalesH.ROWDATA.ROW newInstance(org.apache.xmlbeans.XmlOptions options) {
                      return (noNamespace.SalesHDocument.SalesH.ROWDATA.ROW) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
                    
                    private Factory() { } // No instance of this class allowed
                }
            }
            
            /**
             * A factory class with static methods for creating instances
             * of this type.
             */
            
            public static final class Factory
            {
                public static noNamespace.SalesHDocument.SalesH.ROWDATA newInstance() {
                  return (noNamespace.SalesHDocument.SalesH.ROWDATA) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
                
                public static noNamespace.SalesHDocument.SalesH.ROWDATA newInstance(org.apache.xmlbeans.XmlOptions options) {
                  return (noNamespace.SalesHDocument.SalesH.ROWDATA) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
                
                private Factory() { } // No instance of this class allowed
            }
        }
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static noNamespace.SalesHDocument.SalesH newInstance() {
              return (noNamespace.SalesHDocument.SalesH) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static noNamespace.SalesHDocument.SalesH newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (noNamespace.SalesHDocument.SalesH) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static noNamespace.SalesHDocument newInstance() {
          return (noNamespace.SalesHDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static noNamespace.SalesHDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (noNamespace.SalesHDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static noNamespace.SalesHDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesHDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static noNamespace.SalesHDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesHDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static noNamespace.SalesHDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesHDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static noNamespace.SalesHDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesHDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static noNamespace.SalesHDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesHDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static noNamespace.SalesHDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesHDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static noNamespace.SalesHDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesHDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static noNamespace.SalesHDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesHDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static noNamespace.SalesHDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesHDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static noNamespace.SalesHDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesHDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static noNamespace.SalesHDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesHDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static noNamespace.SalesHDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesHDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static noNamespace.SalesHDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesHDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static noNamespace.SalesHDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesHDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static noNamespace.SalesHDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (noNamespace.SalesHDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static noNamespace.SalesHDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (noNamespace.SalesHDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
