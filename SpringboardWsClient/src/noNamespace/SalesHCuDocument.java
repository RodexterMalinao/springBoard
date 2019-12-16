/*
 * An XML document type.
 * Localname: sales_h_cu
 * Namespace: 
 * Java type: noNamespace.SalesHCuDocument
 *
 * Automatically generated - do not modify.
 */
package noNamespace;


/**
 * A document containing one sales_h_cu(@) element.
 *
 * This is a complex type.
 */
public interface SalesHCuDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(SalesHCuDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s2767C7B598C07BA55373BD46FFDB6B6C").resolveHandle("saleshcu7435doctype");
    
    /**
     * Gets the "sales_h_cu" element
     */
    noNamespace.SalesHCuDocument.SalesHCu getSalesHCu();
    
    /**
     * Sets the "sales_h_cu" element
     */
    void setSalesHCu(noNamespace.SalesHCuDocument.SalesHCu salesHCu);
    
    /**
     * Appends and returns a new empty "sales_h_cu" element
     */
    noNamespace.SalesHCuDocument.SalesHCu addNewSalesHCu();
    
    /**
     * An XML sales_h_cu(@).
     *
     * This is a complex type.
     */
    public interface SalesHCu extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(SalesHCu.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s2767C7B598C07BA55373BD46FFDB6B6C").resolveHandle("saleshcu81adelemtype");
        
        /**
         * Gets the "ROWDATA" element
         */
        noNamespace.SalesHCuDocument.SalesHCu.ROWDATA getROWDATA();
        
        /**
         * Sets the "ROWDATA" element
         */
        void setROWDATA(noNamespace.SalesHCuDocument.SalesHCu.ROWDATA rowdata);
        
        /**
         * Appends and returns a new empty "ROWDATA" element
         */
        noNamespace.SalesHCuDocument.SalesHCu.ROWDATA addNewROWDATA();
        
        /**
         * An XML ROWDATA(@).
         *
         * This is a complex type.
         */
        public interface ROWDATA extends org.apache.xmlbeans.XmlObject
        {
            public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
                org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ROWDATA.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s2767C7B598C07BA55373BD46FFDB6B6C").resolveHandle("rowdata6e8delemtype");
            
            /**
             * Gets the "ROW" element
             */
            noNamespace.SalesHCuDocument.SalesHCu.ROWDATA.ROW getROW();
            
            /**
             * Sets the "ROW" element
             */
            void setROW(noNamespace.SalesHCuDocument.SalesHCu.ROWDATA.ROW row);
            
            /**
             * Appends and returns a new empty "ROW" element
             */
            noNamespace.SalesHCuDocument.SalesHCu.ROWDATA.ROW addNewROW();
            
            /**
             * An XML ROW(@).
             *
             * This is an atomic type that is a restriction of noNamespace.SalesHCuDocument$SalesHCu$ROWDATA$ROW.
             */
            public interface ROW extends org.apache.xmlbeans.XmlString
            {
                public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
                    org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ROW.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s2767C7B598C07BA55373BD46FFDB6B6C").resolveHandle("rowd1c3elemtype");
                
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
                 * Gets the "SRV_TYPE" attribute
                 */
                java.lang.String getSRVTYPE();
                
                /**
                 * Gets (as xml) the "SRV_TYPE" attribute
                 */
                org.apache.xmlbeans.XmlString xgetSRVTYPE();
                
                /**
                 * True if has "SRV_TYPE" attribute
                 */
                boolean isSetSRVTYPE();
                
                /**
                 * Sets the "SRV_TYPE" attribute
                 */
                void setSRVTYPE(java.lang.String srvtype);
                
                /**
                 * Sets (as xml) the "SRV_TYPE" attribute
                 */
                void xsetSRVTYPE(org.apache.xmlbeans.XmlString srvtype);
                
                /**
                 * Unsets the "SRV_TYPE" attribute
                 */
                void unsetSRVTYPE();
                
                /**
                 * Gets the "SRV_NUM" attribute
                 */
                java.lang.String getSRVNUM();
                
                /**
                 * Gets (as xml) the "SRV_NUM" attribute
                 */
                org.apache.xmlbeans.XmlString xgetSRVNUM();
                
                /**
                 * True if has "SRV_NUM" attribute
                 */
                boolean isSetSRVNUM();
                
                /**
                 * Sets the "SRV_NUM" attribute
                 */
                void setSRVNUM(java.lang.String srvnum);
                
                /**
                 * Sets (as xml) the "SRV_NUM" attribute
                 */
                void xsetSRVNUM(org.apache.xmlbeans.XmlString srvnum);
                
                /**
                 * Unsets the "SRV_NUM" attribute
                 */
                void unsetSRVNUM();
                
                /**
                 * Gets the "WO_DESC" attribute
                 */
                java.lang.String getWODESC();
                
                /**
                 * Gets (as xml) the "WO_DESC" attribute
                 */
                org.apache.xmlbeans.XmlString xgetWODESC();
                
                /**
                 * True if has "WO_DESC" attribute
                 */
                boolean isSetWODESC();
                
                /**
                 * Sets the "WO_DESC" attribute
                 */
                void setWODESC(java.lang.String wodesc);
                
                /**
                 * Sets (as xml) the "WO_DESC" attribute
                 */
                void xsetWODESC(org.apache.xmlbeans.XmlString wodesc);
                
                /**
                 * Unsets the "WO_DESC" attribute
                 */
                void unsetWODESC();
                
                /**
                 * Gets the "BIS_Date" attribute
                 */
                java.lang.String getBISDate();
                
                /**
                 * Gets (as xml) the "BIS_Date" attribute
                 */
                org.apache.xmlbeans.XmlString xgetBISDate();
                
                /**
                 * True if has "BIS_Date" attribute
                 */
                boolean isSetBISDate();
                
                /**
                 * Sets the "BIS_Date" attribute
                 */
                void setBISDate(java.lang.String bisDate);
                
                /**
                 * Sets (as xml) the "BIS_Date" attribute
                 */
                void xsetBISDate(org.apache.xmlbeans.XmlString bisDate);
                
                /**
                 * Unsets the "BIS_Date" attribute
                 */
                void unsetBISDate();
                
                /**
                 * Gets the "INSTALL_BU" attribute
                 */
                java.lang.String getINSTALLBU();
                
                /**
                 * Gets (as xml) the "INSTALL_BU" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLBU();
                
                /**
                 * True if has "INSTALL_BU" attribute
                 */
                boolean isSetINSTALLBU();
                
                /**
                 * Sets the "INSTALL_BU" attribute
                 */
                void setINSTALLBU(java.lang.String installbu);
                
                /**
                 * Sets (as xml) the "INSTALL_BU" attribute
                 */
                void xsetINSTALLBU(org.apache.xmlbeans.XmlString installbu);
                
                /**
                 * Unsets the "INSTALL_BU" attribute
                 */
                void unsetINSTALLBU();
                
                /**
                 * Gets the "PROD_LINE" attribute
                 */
                java.lang.String getPRODLINE();
                
                /**
                 * Gets (as xml) the "PROD_LINE" attribute
                 */
                org.apache.xmlbeans.XmlString xgetPRODLINE();
                
                /**
                 * True if has "PROD_LINE" attribute
                 */
                boolean isSetPRODLINE();
                
                /**
                 * Sets the "PROD_LINE" attribute
                 */
                void setPRODLINE(java.lang.String prodline);
                
                /**
                 * Sets (as xml) the "PROD_LINE" attribute
                 */
                void xsetPRODLINE(org.apache.xmlbeans.XmlString prodline);
                
                /**
                 * Unsets the "PROD_LINE" attribute
                 */
                void unsetPRODLINE();
                
                /**
                 * Gets the "EXCHG_CODE" attribute
                 */
                java.lang.String getEXCHGCODE();
                
                /**
                 * Gets (as xml) the "EXCHG_CODE" attribute
                 */
                org.apache.xmlbeans.XmlString xgetEXCHGCODE();
                
                /**
                 * True if has "EXCHG_CODE" attribute
                 */
                boolean isSetEXCHGCODE();
                
                /**
                 * Sets the "EXCHG_CODE" attribute
                 */
                void setEXCHGCODE(java.lang.String exchgcode);
                
                /**
                 * Sets (as xml) the "EXCHG_CODE" attribute
                 */
                void xsetEXCHGCODE(org.apache.xmlbeans.XmlString exchgcode);
                
                /**
                 * Unsets the "EXCHG_CODE" attribute
                 */
                void unsetEXCHGCODE();
                
                /**
                 * Gets the "CTRCT_END_DATE" attribute
                 */
                java.lang.String getCTRCTENDDATE();
                
                /**
                 * Gets (as xml) the "CTRCT_END_DATE" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCTRCTENDDATE();
                
                /**
                 * True if has "CTRCT_END_DATE" attribute
                 */
                boolean isSetCTRCTENDDATE();
                
                /**
                 * Sets the "CTRCT_END_DATE" attribute
                 */
                void setCTRCTENDDATE(java.lang.String ctrctenddate);
                
                /**
                 * Sets (as xml) the "CTRCT_END_DATE" attribute
                 */
                void xsetCTRCTENDDATE(org.apache.xmlbeans.XmlString ctrctenddate);
                
                /**
                 * Unsets the "CTRCT_END_DATE" attribute
                 */
                void unsetCTRCTENDDATE();
                
                /**
                 * Gets the "CUS_NUM" attribute
                 */
                java.lang.String getCUSNUM();
                
                /**
                 * Gets (as xml) the "CUS_NUM" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUSNUM();
                
                /**
                 * True if has "CUS_NUM" attribute
                 */
                boolean isSetCUSNUM();
                
                /**
                 * Sets the "CUS_NUM" attribute
                 */
                void setCUSNUM(java.lang.String cusnum);
                
                /**
                 * Sets (as xml) the "CUS_NUM" attribute
                 */
                void xsetCUSNUM(org.apache.xmlbeans.XmlString cusnum);
                
                /**
                 * Unsets the "CUS_NUM" attribute
                 */
                void unsetCUSNUM();
                
                /**
                 * Gets the "ACCT_NUM" attribute
                 */
                java.lang.String getACCTNUM();
                
                /**
                 * Gets (as xml) the "ACCT_NUM" attribute
                 */
                org.apache.xmlbeans.XmlString xgetACCTNUM();
                
                /**
                 * True if has "ACCT_NUM" attribute
                 */
                boolean isSetACCTNUM();
                
                /**
                 * Sets the "ACCT_NUM" attribute
                 */
                void setACCTNUM(java.lang.String acctnum);
                
                /**
                 * Sets (as xml) the "ACCT_NUM" attribute
                 */
                void xsetACCTNUM(org.apache.xmlbeans.XmlString acctnum);
                
                /**
                 * Unsets the "ACCT_NUM" attribute
                 */
                void unsetACCTNUM();
                
                /**
                 * Gets the "CUS_NAME" attribute
                 */
                java.lang.String getCUSNAME();
                
                /**
                 * Gets (as xml) the "CUS_NAME" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUSNAME();
                
                /**
                 * True if has "CUS_NAME" attribute
                 */
                boolean isSetCUSNAME();
                
                /**
                 * Sets the "CUS_NAME" attribute
                 */
                void setCUSNAME(java.lang.String cusname);
                
                /**
                 * Sets (as xml) the "CUS_NAME" attribute
                 */
                void xsetCUSNAME(org.apache.xmlbeans.XmlString cusname);
                
                /**
                 * Unsets the "CUS_NAME" attribute
                 */
                void unsetCUSNAME();
                
                /**
                 * Gets the "CUS_STATUS" attribute
                 */
                java.lang.String getCUSSTATUS();
                
                /**
                 * Gets (as xml) the "CUS_STATUS" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUSSTATUS();
                
                /**
                 * True if has "CUS_STATUS" attribute
                 */
                boolean isSetCUSSTATUS();
                
                /**
                 * Sets the "CUS_STATUS" attribute
                 */
                void setCUSSTATUS(java.lang.String cusstatus);
                
                /**
                 * Sets (as xml) the "CUS_STATUS" attribute
                 */
                void xsetCUSSTATUS(org.apache.xmlbeans.XmlString cusstatus);
                
                /**
                 * Unsets the "CUS_STATUS" attribute
                 */
                void unsetCUSSTATUS();
                
                /**
                 * Gets the "LEAS_CODE" attribute
                 */
                java.lang.String getLEASCODE();
                
                /**
                 * Gets (as xml) the "LEAS_CODE" attribute
                 */
                org.apache.xmlbeans.XmlString xgetLEASCODE();
                
                /**
                 * True if has "LEAS_CODE" attribute
                 */
                boolean isSetLEASCODE();
                
                /**
                 * Sets the "LEAS_CODE" attribute
                 */
                void setLEASCODE(java.lang.String leascode);
                
                /**
                 * Sets (as xml) the "LEAS_CODE" attribute
                 */
                void xsetLEASCODE(org.apache.xmlbeans.XmlString leascode);
                
                /**
                 * Unsets the "LEAS_CODE" attribute
                 */
                void unsetLEASCODE();
                
                /**
                 * Gets the "LPS_MEMSHIP_NUM" attribute
                 */
                java.lang.String getLPSMEMSHIPNUM();
                
                /**
                 * Gets (as xml) the "LPS_MEMSHIP_NUM" attribute
                 */
                org.apache.xmlbeans.XmlString xgetLPSMEMSHIPNUM();
                
                /**
                 * True if has "LPS_MEMSHIP_NUM" attribute
                 */
                boolean isSetLPSMEMSHIPNUM();
                
                /**
                 * Sets the "LPS_MEMSHIP_NUM" attribute
                 */
                void setLPSMEMSHIPNUM(java.lang.String lpsmemshipnum);
                
                /**
                 * Sets (as xml) the "LPS_MEMSHIP_NUM" attribute
                 */
                void xsetLPSMEMSHIPNUM(org.apache.xmlbeans.XmlString lpsmemshipnum);
                
                /**
                 * Unsets the "LPS_MEMSHIP_NUM" attribute
                 */
                void unsetLPSMEMSHIPNUM();
                
                /**
                 * Gets the "USER_NAME" attribute
                 */
                java.lang.String getUSERNAME();
                
                /**
                 * Gets (as xml) the "USER_NAME" attribute
                 */
                org.apache.xmlbeans.XmlString xgetUSERNAME();
                
                /**
                 * True if has "USER_NAME" attribute
                 */
                boolean isSetUSERNAME();
                
                /**
                 * Sets the "USER_NAME" attribute
                 */
                void setUSERNAME(java.lang.String username);
                
                /**
                 * Sets (as xml) the "USER_NAME" attribute
                 */
                void xsetUSERNAME(org.apache.xmlbeans.XmlString username);
                
                /**
                 * Unsets the "USER_NAME" attribute
                 */
                void unsetUSERNAME();
                
                /**
                 * Gets the "CONTACT_PERSON" attribute
                 */
                java.lang.String getCONTACTPERSON();
                
                /**
                 * Gets (as xml) the "CONTACT_PERSON" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCONTACTPERSON();
                
                /**
                 * True if has "CONTACT_PERSON" attribute
                 */
                boolean isSetCONTACTPERSON();
                
                /**
                 * Sets the "CONTACT_PERSON" attribute
                 */
                void setCONTACTPERSON(java.lang.String contactperson);
                
                /**
                 * Sets (as xml) the "CONTACT_PERSON" attribute
                 */
                void xsetCONTACTPERSON(org.apache.xmlbeans.XmlString contactperson);
                
                /**
                 * Unsets the "CONTACT_PERSON" attribute
                 */
                void unsetCONTACTPERSON();
                
                /**
                 * Gets the "CONTACT_TEL" attribute
                 */
                java.lang.String getCONTACTTEL();
                
                /**
                 * Gets (as xml) the "CONTACT_TEL" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCONTACTTEL();
                
                /**
                 * True if has "CONTACT_TEL" attribute
                 */
                boolean isSetCONTACTTEL();
                
                /**
                 * Sets the "CONTACT_TEL" attribute
                 */
                void setCONTACTTEL(java.lang.String contacttel);
                
                /**
                 * Sets (as xml) the "CONTACT_TEL" attribute
                 */
                void xsetCONTACTTEL(org.apache.xmlbeans.XmlString contacttel);
                
                /**
                 * Unsets the "CONTACT_TEL" attribute
                 */
                void unsetCONTACTTEL();
                
                /**
                 * Gets the "CONTACT_FAX" attribute
                 */
                java.lang.String getCONTACTFAX();
                
                /**
                 * Gets (as xml) the "CONTACT_FAX" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCONTACTFAX();
                
                /**
                 * True if has "CONTACT_FAX" attribute
                 */
                boolean isSetCONTACTFAX();
                
                /**
                 * Sets the "CONTACT_FAX" attribute
                 */
                void setCONTACTFAX(java.lang.String contactfax);
                
                /**
                 * Sets (as xml) the "CONTACT_FAX" attribute
                 */
                void xsetCONTACTFAX(org.apache.xmlbeans.XmlString contactfax);
                
                /**
                 * Unsets the "CONTACT_FAX" attribute
                 */
                void unsetCONTACTFAX();
                
                /**
                 * Gets the "CUS_REFERENCE" attribute
                 */
                java.lang.String getCUSREFERENCE();
                
                /**
                 * Gets (as xml) the "CUS_REFERENCE" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUSREFERENCE();
                
                /**
                 * True if has "CUS_REFERENCE" attribute
                 */
                boolean isSetCUSREFERENCE();
                
                /**
                 * Sets the "CUS_REFERENCE" attribute
                 */
                void setCUSREFERENCE(java.lang.String cusreference);
                
                /**
                 * Sets (as xml) the "CUS_REFERENCE" attribute
                 */
                void xsetCUSREFERENCE(org.apache.xmlbeans.XmlString cusreference);
                
                /**
                 * Unsets the "CUS_REFERENCE" attribute
                 */
                void unsetCUSREFERENCE();
                
                /**
                 * Gets the "CUS_PO" attribute
                 */
                java.lang.String getCUSPO();
                
                /**
                 * Gets (as xml) the "CUS_PO" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCUSPO();
                
                /**
                 * True if has "CUS_PO" attribute
                 */
                boolean isSetCUSPO();
                
                /**
                 * Sets the "CUS_PO" attribute
                 */
                void setCUSPO(java.lang.String cuspo);
                
                /**
                 * Sets (as xml) the "CUS_PO" attribute
                 */
                void xsetCUSPO(org.apache.xmlbeans.XmlString cuspo);
                
                /**
                 * Unsets the "CUS_PO" attribute
                 */
                void unsetCUSPO();
                
                /**
                 * Gets the "CORR_FLT" attribute
                 */
                java.lang.String getCORRFLT();
                
                /**
                 * Gets (as xml) the "CORR_FLT" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCORRFLT();
                
                /**
                 * True if has "CORR_FLT" attribute
                 */
                boolean isSetCORRFLT();
                
                /**
                 * Sets the "CORR_FLT" attribute
                 */
                void setCORRFLT(java.lang.String corrflt);
                
                /**
                 * Sets (as xml) the "CORR_FLT" attribute
                 */
                void xsetCORRFLT(org.apache.xmlbeans.XmlString corrflt);
                
                /**
                 * Unsets the "CORR_FLT" attribute
                 */
                void unsetCORRFLT();
                
                /**
                 * Gets the "CORR_FLOOR" attribute
                 */
                java.lang.String getCORRFLOOR();
                
                /**
                 * Gets (as xml) the "CORR_FLOOR" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCORRFLOOR();
                
                /**
                 * True if has "CORR_FLOOR" attribute
                 */
                boolean isSetCORRFLOOR();
                
                /**
                 * Sets the "CORR_FLOOR" attribute
                 */
                void setCORRFLOOR(java.lang.String corrfloor);
                
                /**
                 * Sets (as xml) the "CORR_FLOOR" attribute
                 */
                void xsetCORRFLOOR(org.apache.xmlbeans.XmlString corrfloor);
                
                /**
                 * Unsets the "CORR_FLOOR" attribute
                 */
                void unsetCORRFLOOR();
                
                /**
                 * Gets the "CORR_BLDG" attribute
                 */
                java.lang.String getCORRBLDG();
                
                /**
                 * Gets (as xml) the "CORR_BLDG" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCORRBLDG();
                
                /**
                 * True if has "CORR_BLDG" attribute
                 */
                boolean isSetCORRBLDG();
                
                /**
                 * Sets the "CORR_BLDG" attribute
                 */
                void setCORRBLDG(java.lang.String corrbldg);
                
                /**
                 * Sets (as xml) the "CORR_BLDG" attribute
                 */
                void xsetCORRBLDG(org.apache.xmlbeans.XmlString corrbldg);
                
                /**
                 * Unsets the "CORR_BLDG" attribute
                 */
                void unsetCORRBLDG();
                
                /**
                 * Gets the "CORR_PO_BOX" attribute
                 */
                java.lang.String getCORRPOBOX();
                
                /**
                 * Gets (as xml) the "CORR_PO_BOX" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCORRPOBOX();
                
                /**
                 * True if has "CORR_PO_BOX" attribute
                 */
                boolean isSetCORRPOBOX();
                
                /**
                 * Sets the "CORR_PO_BOX" attribute
                 */
                void setCORRPOBOX(java.lang.String corrpobox);
                
                /**
                 * Sets (as xml) the "CORR_PO_BOX" attribute
                 */
                void xsetCORRPOBOX(org.apache.xmlbeans.XmlString corrpobox);
                
                /**
                 * Unsets the "CORR_PO_BOX" attribute
                 */
                void unsetCORRPOBOX();
                
                /**
                 * Gets the "CORR_ST_NUM" attribute
                 */
                java.lang.String getCORRSTNUM();
                
                /**
                 * Gets (as xml) the "CORR_ST_NUM" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCORRSTNUM();
                
                /**
                 * True if has "CORR_ST_NUM" attribute
                 */
                boolean isSetCORRSTNUM();
                
                /**
                 * Sets the "CORR_ST_NUM" attribute
                 */
                void setCORRSTNUM(java.lang.String corrstnum);
                
                /**
                 * Sets (as xml) the "CORR_ST_NUM" attribute
                 */
                void xsetCORRSTNUM(org.apache.xmlbeans.XmlString corrstnum);
                
                /**
                 * Unsets the "CORR_ST_NUM" attribute
                 */
                void unsetCORRSTNUM();
                
                /**
                 * Gets the "CORR_LAND_NUM" attribute
                 */
                java.lang.String getCORRLANDNUM();
                
                /**
                 * Gets (as xml) the "CORR_LAND_NUM" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCORRLANDNUM();
                
                /**
                 * True if has "CORR_LAND_NUM" attribute
                 */
                boolean isSetCORRLANDNUM();
                
                /**
                 * Sets the "CORR_LAND_NUM" attribute
                 */
                void setCORRLANDNUM(java.lang.String corrlandnum);
                
                /**
                 * Sets (as xml) the "CORR_LAND_NUM" attribute
                 */
                void xsetCORRLANDNUM(org.apache.xmlbeans.XmlString corrlandnum);
                
                /**
                 * Unsets the "CORR_LAND_NUM" attribute
                 */
                void unsetCORRLANDNUM();
                
                /**
                 * Gets the "CORR_ST_NAME" attribute
                 */
                java.lang.String getCORRSTNAME();
                
                /**
                 * Gets (as xml) the "CORR_ST_NAME" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCORRSTNAME();
                
                /**
                 * True if has "CORR_ST_NAME" attribute
                 */
                boolean isSetCORRSTNAME();
                
                /**
                 * Sets the "CORR_ST_NAME" attribute
                 */
                void setCORRSTNAME(java.lang.String corrstname);
                
                /**
                 * Sets (as xml) the "CORR_ST_NAME" attribute
                 */
                void xsetCORRSTNAME(org.apache.xmlbeans.XmlString corrstname);
                
                /**
                 * Unsets the "CORR_ST_NAME" attribute
                 */
                void unsetCORRSTNAME();
                
                /**
                 * Gets the "CORR_ST_CATG" attribute
                 */
                java.lang.String getCORRSTCATG();
                
                /**
                 * Gets (as xml) the "CORR_ST_CATG" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCORRSTCATG();
                
                /**
                 * True if has "CORR_ST_CATG" attribute
                 */
                boolean isSetCORRSTCATG();
                
                /**
                 * Sets the "CORR_ST_CATG" attribute
                 */
                void setCORRSTCATG(java.lang.String corrstcatg);
                
                /**
                 * Sets (as xml) the "CORR_ST_CATG" attribute
                 */
                void xsetCORRSTCATG(org.apache.xmlbeans.XmlString corrstcatg);
                
                /**
                 * Unsets the "CORR_ST_CATG" attribute
                 */
                void unsetCORRSTCATG();
                
                /**
                 * Gets the "CORR_SECT" attribute
                 */
                java.lang.String getCORRSECT();
                
                /**
                 * Gets (as xml) the "CORR_SECT" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCORRSECT();
                
                /**
                 * True if has "CORR_SECT" attribute
                 */
                boolean isSetCORRSECT();
                
                /**
                 * Sets the "CORR_SECT" attribute
                 */
                void setCORRSECT(java.lang.String corrsect);
                
                /**
                 * Sets (as xml) the "CORR_SECT" attribute
                 */
                void xsetCORRSECT(org.apache.xmlbeans.XmlString corrsect);
                
                /**
                 * Unsets the "CORR_SECT" attribute
                 */
                void unsetCORRSECT();
                
                /**
                 * Gets the "CORR_DISTR" attribute
                 */
                java.lang.String getCORRDISTR();
                
                /**
                 * Gets (as xml) the "CORR_DISTR" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCORRDISTR();
                
                /**
                 * True if has "CORR_DISTR" attribute
                 */
                boolean isSetCORRDISTR();
                
                /**
                 * Sets the "CORR_DISTR" attribute
                 */
                void setCORRDISTR(java.lang.String corrdistr);
                
                /**
                 * Sets (as xml) the "CORR_DISTR" attribute
                 */
                void xsetCORRDISTR(org.apache.xmlbeans.XmlString corrdistr);
                
                /**
                 * Unsets the "CORR_DISTR" attribute
                 */
                void unsetCORRDISTR();
                
                /**
                 * Gets the "CORR_AREA" attribute
                 */
                java.lang.String getCORRAREA();
                
                /**
                 * Gets (as xml) the "CORR_AREA" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCORRAREA();
                
                /**
                 * True if has "CORR_AREA" attribute
                 */
                boolean isSetCORRAREA();
                
                /**
                 * Sets the "CORR_AREA" attribute
                 */
                void setCORRAREA(java.lang.String corrarea);
                
                /**
                 * Sets (as xml) the "CORR_AREA" attribute
                 */
                void xsetCORRAREA(org.apache.xmlbeans.XmlString corrarea);
                
                /**
                 * Unsets the "CORR_AREA" attribute
                 */
                void unsetCORRAREA();
                
                /**
                 * Gets the "CORR_POST_REGN" attribute
                 */
                java.lang.String getCORRPOSTREGN();
                
                /**
                 * Gets (as xml) the "CORR_POST_REGN" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCORRPOSTREGN();
                
                /**
                 * True if has "CORR_POST_REGN" attribute
                 */
                boolean isSetCORRPOSTREGN();
                
                /**
                 * Sets the "CORR_POST_REGN" attribute
                 */
                void setCORRPOSTREGN(java.lang.String corrpostregn);
                
                /**
                 * Sets (as xml) the "CORR_POST_REGN" attribute
                 */
                void xsetCORRPOSTREGN(org.apache.xmlbeans.XmlString corrpostregn);
                
                /**
                 * Unsets the "CORR_POST_REGN" attribute
                 */
                void unsetCORRPOSTREGN();
                
                /**
                 * Gets the "CORR_POST_CODE" attribute
                 */
                java.lang.String getCORRPOSTCODE();
                
                /**
                 * Gets (as xml) the "CORR_POST_CODE" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCORRPOSTCODE();
                
                /**
                 * True if has "CORR_POST_CODE" attribute
                 */
                boolean isSetCORRPOSTCODE();
                
                /**
                 * Sets the "CORR_POST_CODE" attribute
                 */
                void setCORRPOSTCODE(java.lang.String corrpostcode);
                
                /**
                 * Sets (as xml) the "CORR_POST_CODE" attribute
                 */
                void xsetCORRPOSTCODE(org.apache.xmlbeans.XmlString corrpostcode);
                
                /**
                 * Unsets the "CORR_POST_CODE" attribute
                 */
                void unsetCORRPOSTCODE();
                
                /**
                 * Gets the "CORR_COUNTRY" attribute
                 */
                java.lang.String getCORRCOUNTRY();
                
                /**
                 * Gets (as xml) the "CORR_COUNTRY" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCORRCOUNTRY();
                
                /**
                 * True if has "CORR_COUNTRY" attribute
                 */
                boolean isSetCORRCOUNTRY();
                
                /**
                 * Sets the "CORR_COUNTRY" attribute
                 */
                void setCORRCOUNTRY(java.lang.String corrcountry);
                
                /**
                 * Sets (as xml) the "CORR_COUNTRY" attribute
                 */
                void xsetCORRCOUNTRY(org.apache.xmlbeans.XmlString corrcountry);
                
                /**
                 * Unsets the "CORR_COUNTRY" attribute
                 */
                void unsetCORRCOUNTRY();
                
                /**
                 * Gets the "CORR_ATTN" attribute
                 */
                java.lang.String getCORRATTN();
                
                /**
                 * Gets (as xml) the "CORR_ATTN" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCORRATTN();
                
                /**
                 * True if has "CORR_ATTN" attribute
                 */
                boolean isSetCORRATTN();
                
                /**
                 * Sets the "CORR_ATTN" attribute
                 */
                void setCORRATTN(java.lang.String corrattn);
                
                /**
                 * Sets (as xml) the "CORR_ATTN" attribute
                 */
                void xsetCORRATTN(org.apache.xmlbeans.XmlString corrattn);
                
                /**
                 * Unsets the "CORR_ATTN" attribute
                 */
                void unsetCORRATTN();
                
                /**
                 * Gets the "CORR_CO" attribute
                 */
                java.lang.String getCORRCO();
                
                /**
                 * Gets (as xml) the "CORR_CO" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCORRCO();
                
                /**
                 * True if has "CORR_CO" attribute
                 */
                boolean isSetCORRCO();
                
                /**
                 * Sets the "CORR_CO" attribute
                 */
                void setCORRCO(java.lang.String corrco);
                
                /**
                 * Sets (as xml) the "CORR_CO" attribute
                 */
                void xsetCORRCO(org.apache.xmlbeans.XmlString corrco);
                
                /**
                 * Unsets the "CORR_CO" attribute
                 */
                void unsetCORRCO();
                
                /**
                 * Gets the "INSTALL_FLT_A" attribute
                 */
                java.lang.String getINSTALLFLTA();
                
                /**
                 * Gets (as xml) the "INSTALL_FLT_A" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLFLTA();
                
                /**
                 * True if has "INSTALL_FLT_A" attribute
                 */
                boolean isSetINSTALLFLTA();
                
                /**
                 * Sets the "INSTALL_FLT_A" attribute
                 */
                void setINSTALLFLTA(java.lang.String installflta);
                
                /**
                 * Sets (as xml) the "INSTALL_FLT_A" attribute
                 */
                void xsetINSTALLFLTA(org.apache.xmlbeans.XmlString installflta);
                
                /**
                 * Unsets the "INSTALL_FLT_A" attribute
                 */
                void unsetINSTALLFLTA();
                
                /**
                 * Gets the "INSTALL_FLOOR_A" attribute
                 */
                java.lang.String getINSTALLFLOORA();
                
                /**
                 * Gets (as xml) the "INSTALL_FLOOR_A" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLFLOORA();
                
                /**
                 * True if has "INSTALL_FLOOR_A" attribute
                 */
                boolean isSetINSTALLFLOORA();
                
                /**
                 * Sets the "INSTALL_FLOOR_A" attribute
                 */
                void setINSTALLFLOORA(java.lang.String installfloora);
                
                /**
                 * Sets (as xml) the "INSTALL_FLOOR_A" attribute
                 */
                void xsetINSTALLFLOORA(org.apache.xmlbeans.XmlString installfloora);
                
                /**
                 * Unsets the "INSTALL_FLOOR_A" attribute
                 */
                void unsetINSTALLFLOORA();
                
                /**
                 * Gets the "INSTALL_BLDG_A" attribute
                 */
                java.lang.String getINSTALLBLDGA();
                
                /**
                 * Gets (as xml) the "INSTALL_BLDG_A" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLBLDGA();
                
                /**
                 * True if has "INSTALL_BLDG_A" attribute
                 */
                boolean isSetINSTALLBLDGA();
                
                /**
                 * Sets the "INSTALL_BLDG_A" attribute
                 */
                void setINSTALLBLDGA(java.lang.String installbldga);
                
                /**
                 * Sets (as xml) the "INSTALL_BLDG_A" attribute
                 */
                void xsetINSTALLBLDGA(org.apache.xmlbeans.XmlString installbldga);
                
                /**
                 * Unsets the "INSTALL_BLDG_A" attribute
                 */
                void unsetINSTALLBLDGA();
                
                /**
                 * Gets the "INSTALL_PO_BOX_A" attribute
                 */
                java.lang.String getINSTALLPOBOXA();
                
                /**
                 * Gets (as xml) the "INSTALL_PO_BOX_A" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLPOBOXA();
                
                /**
                 * True if has "INSTALL_PO_BOX_A" attribute
                 */
                boolean isSetINSTALLPOBOXA();
                
                /**
                 * Sets the "INSTALL_PO_BOX_A" attribute
                 */
                void setINSTALLPOBOXA(java.lang.String installpoboxa);
                
                /**
                 * Sets (as xml) the "INSTALL_PO_BOX_A" attribute
                 */
                void xsetINSTALLPOBOXA(org.apache.xmlbeans.XmlString installpoboxa);
                
                /**
                 * Unsets the "INSTALL_PO_BOX_A" attribute
                 */
                void unsetINSTALLPOBOXA();
                
                /**
                 * Gets the "INSTALL_ST_NUM_A" attribute
                 */
                java.lang.String getINSTALLSTNUMA();
                
                /**
                 * Gets (as xml) the "INSTALL_ST_NUM_A" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLSTNUMA();
                
                /**
                 * True if has "INSTALL_ST_NUM_A" attribute
                 */
                boolean isSetINSTALLSTNUMA();
                
                /**
                 * Sets the "INSTALL_ST_NUM_A" attribute
                 */
                void setINSTALLSTNUMA(java.lang.String installstnuma);
                
                /**
                 * Sets (as xml) the "INSTALL_ST_NUM_A" attribute
                 */
                void xsetINSTALLSTNUMA(org.apache.xmlbeans.XmlString installstnuma);
                
                /**
                 * Unsets the "INSTALL_ST_NUM_A" attribute
                 */
                void unsetINSTALLSTNUMA();
                
                /**
                 * Gets the "INSTALL_LAND_NUM_A" attribute
                 */
                java.lang.String getINSTALLLANDNUMA();
                
                /**
                 * Gets (as xml) the "INSTALL_LAND_NUM_A" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLLANDNUMA();
                
                /**
                 * True if has "INSTALL_LAND_NUM_A" attribute
                 */
                boolean isSetINSTALLLANDNUMA();
                
                /**
                 * Sets the "INSTALL_LAND_NUM_A" attribute
                 */
                void setINSTALLLANDNUMA(java.lang.String installlandnuma);
                
                /**
                 * Sets (as xml) the "INSTALL_LAND_NUM_A" attribute
                 */
                void xsetINSTALLLANDNUMA(org.apache.xmlbeans.XmlString installlandnuma);
                
                /**
                 * Unsets the "INSTALL_LAND_NUM_A" attribute
                 */
                void unsetINSTALLLANDNUMA();
                
                /**
                 * Gets the "INSTALL_ST_NAME_A" attribute
                 */
                java.lang.String getINSTALLSTNAMEA();
                
                /**
                 * Gets (as xml) the "INSTALL_ST_NAME_A" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLSTNAMEA();
                
                /**
                 * True if has "INSTALL_ST_NAME_A" attribute
                 */
                boolean isSetINSTALLSTNAMEA();
                
                /**
                 * Sets the "INSTALL_ST_NAME_A" attribute
                 */
                void setINSTALLSTNAMEA(java.lang.String installstnamea);
                
                /**
                 * Sets (as xml) the "INSTALL_ST_NAME_A" attribute
                 */
                void xsetINSTALLSTNAMEA(org.apache.xmlbeans.XmlString installstnamea);
                
                /**
                 * Unsets the "INSTALL_ST_NAME_A" attribute
                 */
                void unsetINSTALLSTNAMEA();
                
                /**
                 * Gets the "INSTALL_ST_CATG_A" attribute
                 */
                java.lang.String getINSTALLSTCATGA();
                
                /**
                 * Gets (as xml) the "INSTALL_ST_CATG_A" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLSTCATGA();
                
                /**
                 * True if has "INSTALL_ST_CATG_A" attribute
                 */
                boolean isSetINSTALLSTCATGA();
                
                /**
                 * Sets the "INSTALL_ST_CATG_A" attribute
                 */
                void setINSTALLSTCATGA(java.lang.String installstcatga);
                
                /**
                 * Sets (as xml) the "INSTALL_ST_CATG_A" attribute
                 */
                void xsetINSTALLSTCATGA(org.apache.xmlbeans.XmlString installstcatga);
                
                /**
                 * Unsets the "INSTALL_ST_CATG_A" attribute
                 */
                void unsetINSTALLSTCATGA();
                
                /**
                 * Gets the "INSTALL_SECT_A" attribute
                 */
                java.lang.String getINSTALLSECTA();
                
                /**
                 * Gets (as xml) the "INSTALL_SECT_A" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLSECTA();
                
                /**
                 * True if has "INSTALL_SECT_A" attribute
                 */
                boolean isSetINSTALLSECTA();
                
                /**
                 * Sets the "INSTALL_SECT_A" attribute
                 */
                void setINSTALLSECTA(java.lang.String installsecta);
                
                /**
                 * Sets (as xml) the "INSTALL_SECT_A" attribute
                 */
                void xsetINSTALLSECTA(org.apache.xmlbeans.XmlString installsecta);
                
                /**
                 * Unsets the "INSTALL_SECT_A" attribute
                 */
                void unsetINSTALLSECTA();
                
                /**
                 * Gets the "INSTALL_DISTR_A" attribute
                 */
                java.lang.String getINSTALLDISTRA();
                
                /**
                 * Gets (as xml) the "INSTALL_DISTR_A" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLDISTRA();
                
                /**
                 * True if has "INSTALL_DISTR_A" attribute
                 */
                boolean isSetINSTALLDISTRA();
                
                /**
                 * Sets the "INSTALL_DISTR_A" attribute
                 */
                void setINSTALLDISTRA(java.lang.String installdistra);
                
                /**
                 * Sets (as xml) the "INSTALL_DISTR_A" attribute
                 */
                void xsetINSTALLDISTRA(org.apache.xmlbeans.XmlString installdistra);
                
                /**
                 * Unsets the "INSTALL_DISTR_A" attribute
                 */
                void unsetINSTALLDISTRA();
                
                /**
                 * Gets the "INSTALL_AREA_A" attribute
                 */
                java.lang.String getINSTALLAREAA();
                
                /**
                 * Gets (as xml) the "INSTALL_AREA_A" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLAREAA();
                
                /**
                 * True if has "INSTALL_AREA_A" attribute
                 */
                boolean isSetINSTALLAREAA();
                
                /**
                 * Sets the "INSTALL_AREA_A" attribute
                 */
                void setINSTALLAREAA(java.lang.String installareaa);
                
                /**
                 * Sets (as xml) the "INSTALL_AREA_A" attribute
                 */
                void xsetINSTALLAREAA(org.apache.xmlbeans.XmlString installareaa);
                
                /**
                 * Unsets the "INSTALL_AREA_A" attribute
                 */
                void unsetINSTALLAREAA();
                
                /**
                 * Gets the "INSTALL_POST_REGN_A" attribute
                 */
                java.lang.String getINSTALLPOSTREGNA();
                
                /**
                 * Gets (as xml) the "INSTALL_POST_REGN_A" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLPOSTREGNA();
                
                /**
                 * True if has "INSTALL_POST_REGN_A" attribute
                 */
                boolean isSetINSTALLPOSTREGNA();
                
                /**
                 * Sets the "INSTALL_POST_REGN_A" attribute
                 */
                void setINSTALLPOSTREGNA(java.lang.String installpostregna);
                
                /**
                 * Sets (as xml) the "INSTALL_POST_REGN_A" attribute
                 */
                void xsetINSTALLPOSTREGNA(org.apache.xmlbeans.XmlString installpostregna);
                
                /**
                 * Unsets the "INSTALL_POST_REGN_A" attribute
                 */
                void unsetINSTALLPOSTREGNA();
                
                /**
                 * Gets the "INSTALL_POST_CODE_A" attribute
                 */
                java.lang.String getINSTALLPOSTCODEA();
                
                /**
                 * Gets (as xml) the "INSTALL_POST_CODE_A" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLPOSTCODEA();
                
                /**
                 * True if has "INSTALL_POST_CODE_A" attribute
                 */
                boolean isSetINSTALLPOSTCODEA();
                
                /**
                 * Sets the "INSTALL_POST_CODE_A" attribute
                 */
                void setINSTALLPOSTCODEA(java.lang.String installpostcodea);
                
                /**
                 * Sets (as xml) the "INSTALL_POST_CODE_A" attribute
                 */
                void xsetINSTALLPOSTCODEA(org.apache.xmlbeans.XmlString installpostcodea);
                
                /**
                 * Unsets the "INSTALL_POST_CODE_A" attribute
                 */
                void unsetINSTALLPOSTCODEA();
                
                /**
                 * Gets the "INSTALL_COUNTRY_A" attribute
                 */
                java.lang.String getINSTALLCOUNTRYA();
                
                /**
                 * Gets (as xml) the "INSTALL_COUNTRY_A" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLCOUNTRYA();
                
                /**
                 * True if has "INSTALL_COUNTRY_A" attribute
                 */
                boolean isSetINSTALLCOUNTRYA();
                
                /**
                 * Sets the "INSTALL_COUNTRY_A" attribute
                 */
                void setINSTALLCOUNTRYA(java.lang.String installcountrya);
                
                /**
                 * Sets (as xml) the "INSTALL_COUNTRY_A" attribute
                 */
                void xsetINSTALLCOUNTRYA(org.apache.xmlbeans.XmlString installcountrya);
                
                /**
                 * Unsets the "INSTALL_COUNTRY_A" attribute
                 */
                void unsetINSTALLCOUNTRYA();
                
                /**
                 * Gets the "INSTALL_FLT_B" attribute
                 */
                java.lang.String getINSTALLFLTB();
                
                /**
                 * Gets (as xml) the "INSTALL_FLT_B" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLFLTB();
                
                /**
                 * True if has "INSTALL_FLT_B" attribute
                 */
                boolean isSetINSTALLFLTB();
                
                /**
                 * Sets the "INSTALL_FLT_B" attribute
                 */
                void setINSTALLFLTB(java.lang.String installfltb);
                
                /**
                 * Sets (as xml) the "INSTALL_FLT_B" attribute
                 */
                void xsetINSTALLFLTB(org.apache.xmlbeans.XmlString installfltb);
                
                /**
                 * Unsets the "INSTALL_FLT_B" attribute
                 */
                void unsetINSTALLFLTB();
                
                /**
                 * Gets the "INSTALL_FLOOR_B" attribute
                 */
                java.lang.String getINSTALLFLOORB();
                
                /**
                 * Gets (as xml) the "INSTALL_FLOOR_B" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLFLOORB();
                
                /**
                 * True if has "INSTALL_FLOOR_B" attribute
                 */
                boolean isSetINSTALLFLOORB();
                
                /**
                 * Sets the "INSTALL_FLOOR_B" attribute
                 */
                void setINSTALLFLOORB(java.lang.String installfloorb);
                
                /**
                 * Sets (as xml) the "INSTALL_FLOOR_B" attribute
                 */
                void xsetINSTALLFLOORB(org.apache.xmlbeans.XmlString installfloorb);
                
                /**
                 * Unsets the "INSTALL_FLOOR_B" attribute
                 */
                void unsetINSTALLFLOORB();
                
                /**
                 * Gets the "INSTALL_BLDG_B" attribute
                 */
                java.lang.String getINSTALLBLDGB();
                
                /**
                 * Gets (as xml) the "INSTALL_BLDG_B" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLBLDGB();
                
                /**
                 * True if has "INSTALL_BLDG_B" attribute
                 */
                boolean isSetINSTALLBLDGB();
                
                /**
                 * Sets the "INSTALL_BLDG_B" attribute
                 */
                void setINSTALLBLDGB(java.lang.String installbldgb);
                
                /**
                 * Sets (as xml) the "INSTALL_BLDG_B" attribute
                 */
                void xsetINSTALLBLDGB(org.apache.xmlbeans.XmlString installbldgb);
                
                /**
                 * Unsets the "INSTALL_BLDG_B" attribute
                 */
                void unsetINSTALLBLDGB();
                
                /**
                 * Gets the "INSTALL_PO_BOX_B" attribute
                 */
                java.lang.String getINSTALLPOBOXB();
                
                /**
                 * Gets (as xml) the "INSTALL_PO_BOX_B" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLPOBOXB();
                
                /**
                 * True if has "INSTALL_PO_BOX_B" attribute
                 */
                boolean isSetINSTALLPOBOXB();
                
                /**
                 * Sets the "INSTALL_PO_BOX_B" attribute
                 */
                void setINSTALLPOBOXB(java.lang.String installpoboxb);
                
                /**
                 * Sets (as xml) the "INSTALL_PO_BOX_B" attribute
                 */
                void xsetINSTALLPOBOXB(org.apache.xmlbeans.XmlString installpoboxb);
                
                /**
                 * Unsets the "INSTALL_PO_BOX_B" attribute
                 */
                void unsetINSTALLPOBOXB();
                
                /**
                 * Gets the "INSTALL_ST_NUM_B" attribute
                 */
                java.lang.String getINSTALLSTNUMB();
                
                /**
                 * Gets (as xml) the "INSTALL_ST_NUM_B" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLSTNUMB();
                
                /**
                 * True if has "INSTALL_ST_NUM_B" attribute
                 */
                boolean isSetINSTALLSTNUMB();
                
                /**
                 * Sets the "INSTALL_ST_NUM_B" attribute
                 */
                void setINSTALLSTNUMB(java.lang.String installstnumb);
                
                /**
                 * Sets (as xml) the "INSTALL_ST_NUM_B" attribute
                 */
                void xsetINSTALLSTNUMB(org.apache.xmlbeans.XmlString installstnumb);
                
                /**
                 * Unsets the "INSTALL_ST_NUM_B" attribute
                 */
                void unsetINSTALLSTNUMB();
                
                /**
                 * Gets the "INSTALL_LAND_NUM_B" attribute
                 */
                java.lang.String getINSTALLLANDNUMB();
                
                /**
                 * Gets (as xml) the "INSTALL_LAND_NUM_B" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLLANDNUMB();
                
                /**
                 * True if has "INSTALL_LAND_NUM_B" attribute
                 */
                boolean isSetINSTALLLANDNUMB();
                
                /**
                 * Sets the "INSTALL_LAND_NUM_B" attribute
                 */
                void setINSTALLLANDNUMB(java.lang.String installlandnumb);
                
                /**
                 * Sets (as xml) the "INSTALL_LAND_NUM_B" attribute
                 */
                void xsetINSTALLLANDNUMB(org.apache.xmlbeans.XmlString installlandnumb);
                
                /**
                 * Unsets the "INSTALL_LAND_NUM_B" attribute
                 */
                void unsetINSTALLLANDNUMB();
                
                /**
                 * Gets the "INSTALL_ST_NAME_B" attribute
                 */
                java.lang.String getINSTALLSTNAMEB();
                
                /**
                 * Gets (as xml) the "INSTALL_ST_NAME_B" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLSTNAMEB();
                
                /**
                 * True if has "INSTALL_ST_NAME_B" attribute
                 */
                boolean isSetINSTALLSTNAMEB();
                
                /**
                 * Sets the "INSTALL_ST_NAME_B" attribute
                 */
                void setINSTALLSTNAMEB(java.lang.String installstnameb);
                
                /**
                 * Sets (as xml) the "INSTALL_ST_NAME_B" attribute
                 */
                void xsetINSTALLSTNAMEB(org.apache.xmlbeans.XmlString installstnameb);
                
                /**
                 * Unsets the "INSTALL_ST_NAME_B" attribute
                 */
                void unsetINSTALLSTNAMEB();
                
                /**
                 * Gets the "INSTALL_ST_CATG_B" attribute
                 */
                java.lang.String getINSTALLSTCATGB();
                
                /**
                 * Gets (as xml) the "INSTALL_ST_CATG_B" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLSTCATGB();
                
                /**
                 * True if has "INSTALL_ST_CATG_B" attribute
                 */
                boolean isSetINSTALLSTCATGB();
                
                /**
                 * Sets the "INSTALL_ST_CATG_B" attribute
                 */
                void setINSTALLSTCATGB(java.lang.String installstcatgb);
                
                /**
                 * Sets (as xml) the "INSTALL_ST_CATG_B" attribute
                 */
                void xsetINSTALLSTCATGB(org.apache.xmlbeans.XmlString installstcatgb);
                
                /**
                 * Unsets the "INSTALL_ST_CATG_B" attribute
                 */
                void unsetINSTALLSTCATGB();
                
                /**
                 * Gets the "INSTALL_SECT_B" attribute
                 */
                java.lang.String getINSTALLSECTB();
                
                /**
                 * Gets (as xml) the "INSTALL_SECT_B" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLSECTB();
                
                /**
                 * True if has "INSTALL_SECT_B" attribute
                 */
                boolean isSetINSTALLSECTB();
                
                /**
                 * Sets the "INSTALL_SECT_B" attribute
                 */
                void setINSTALLSECTB(java.lang.String installsectb);
                
                /**
                 * Sets (as xml) the "INSTALL_SECT_B" attribute
                 */
                void xsetINSTALLSECTB(org.apache.xmlbeans.XmlString installsectb);
                
                /**
                 * Unsets the "INSTALL_SECT_B" attribute
                 */
                void unsetINSTALLSECTB();
                
                /**
                 * Gets the "INSTALL_DISTR_B" attribute
                 */
                java.lang.String getINSTALLDISTRB();
                
                /**
                 * Gets (as xml) the "INSTALL_DISTR_B" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLDISTRB();
                
                /**
                 * True if has "INSTALL_DISTR_B" attribute
                 */
                boolean isSetINSTALLDISTRB();
                
                /**
                 * Sets the "INSTALL_DISTR_B" attribute
                 */
                void setINSTALLDISTRB(java.lang.String installdistrb);
                
                /**
                 * Sets (as xml) the "INSTALL_DISTR_B" attribute
                 */
                void xsetINSTALLDISTRB(org.apache.xmlbeans.XmlString installdistrb);
                
                /**
                 * Unsets the "INSTALL_DISTR_B" attribute
                 */
                void unsetINSTALLDISTRB();
                
                /**
                 * Gets the "INSTALL_AREA_B" attribute
                 */
                java.lang.String getINSTALLAREAB();
                
                /**
                 * Gets (as xml) the "INSTALL_AREA_B" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLAREAB();
                
                /**
                 * True if has "INSTALL_AREA_B" attribute
                 */
                boolean isSetINSTALLAREAB();
                
                /**
                 * Sets the "INSTALL_AREA_B" attribute
                 */
                void setINSTALLAREAB(java.lang.String installareab);
                
                /**
                 * Sets (as xml) the "INSTALL_AREA_B" attribute
                 */
                void xsetINSTALLAREAB(org.apache.xmlbeans.XmlString installareab);
                
                /**
                 * Unsets the "INSTALL_AREA_B" attribute
                 */
                void unsetINSTALLAREAB();
                
                /**
                 * Gets the "INSTALL_POST_REGN_B" attribute
                 */
                java.lang.String getINSTALLPOSTREGNB();
                
                /**
                 * Gets (as xml) the "INSTALL_POST_REGN_B" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLPOSTREGNB();
                
                /**
                 * True if has "INSTALL_POST_REGN_B" attribute
                 */
                boolean isSetINSTALLPOSTREGNB();
                
                /**
                 * Sets the "INSTALL_POST_REGN_B" attribute
                 */
                void setINSTALLPOSTREGNB(java.lang.String installpostregnb);
                
                /**
                 * Sets (as xml) the "INSTALL_POST_REGN_B" attribute
                 */
                void xsetINSTALLPOSTREGNB(org.apache.xmlbeans.XmlString installpostregnb);
                
                /**
                 * Unsets the "INSTALL_POST_REGN_B" attribute
                 */
                void unsetINSTALLPOSTREGNB();
                
                /**
                 * Gets the "INSTALL_POST_CODE_B" attribute
                 */
                java.lang.String getINSTALLPOSTCODEB();
                
                /**
                 * Gets (as xml) the "INSTALL_POST_CODE_B" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLPOSTCODEB();
                
                /**
                 * True if has "INSTALL_POST_CODE_B" attribute
                 */
                boolean isSetINSTALLPOSTCODEB();
                
                /**
                 * Sets the "INSTALL_POST_CODE_B" attribute
                 */
                void setINSTALLPOSTCODEB(java.lang.String installpostcodeb);
                
                /**
                 * Sets (as xml) the "INSTALL_POST_CODE_B" attribute
                 */
                void xsetINSTALLPOSTCODEB(org.apache.xmlbeans.XmlString installpostcodeb);
                
                /**
                 * Unsets the "INSTALL_POST_CODE_B" attribute
                 */
                void unsetINSTALLPOSTCODEB();
                
                /**
                 * Gets the "INSTALL_COUNTRY_B" attribute
                 */
                java.lang.String getINSTALLCOUNTRYB();
                
                /**
                 * Gets (as xml) the "INSTALL_COUNTRY_B" attribute
                 */
                org.apache.xmlbeans.XmlString xgetINSTALLCOUNTRYB();
                
                /**
                 * True if has "INSTALL_COUNTRY_B" attribute
                 */
                boolean isSetINSTALLCOUNTRYB();
                
                /**
                 * Sets the "INSTALL_COUNTRY_B" attribute
                 */
                void setINSTALLCOUNTRYB(java.lang.String installcountryb);
                
                /**
                 * Sets (as xml) the "INSTALL_COUNTRY_B" attribute
                 */
                void xsetINSTALLCOUNTRYB(org.apache.xmlbeans.XmlString installcountryb);
                
                /**
                 * Unsets the "INSTALL_COUNTRY_B" attribute
                 */
                void unsetINSTALLCOUNTRYB();
                
                /**
                 * Gets the "PROGRAM_CODE" attribute
                 */
                java.lang.String getPROGRAMCODE();
                
                /**
                 * Gets (as xml) the "PROGRAM_CODE" attribute
                 */
                org.apache.xmlbeans.XmlString xgetPROGRAMCODE();
                
                /**
                 * True if has "PROGRAM_CODE" attribute
                 */
                boolean isSetPROGRAMCODE();
                
                /**
                 * Sets the "PROGRAM_CODE" attribute
                 */
                void setPROGRAMCODE(java.lang.String programcode);
                
                /**
                 * Sets (as xml) the "PROGRAM_CODE" attribute
                 */
                void xsetPROGRAMCODE(org.apache.xmlbeans.XmlString programcode);
                
                /**
                 * Unsets the "PROGRAM_CODE" attribute
                 */
                void unsetPROGRAMCODE();
                
                /**
                 * Gets the "DELV_NAME" attribute
                 */
                java.lang.String getDELVNAME();
                
                /**
                 * Gets (as xml) the "DELV_NAME" attribute
                 */
                org.apache.xmlbeans.XmlString xgetDELVNAME();
                
                /**
                 * True if has "DELV_NAME" attribute
                 */
                boolean isSetDELVNAME();
                
                /**
                 * Sets the "DELV_NAME" attribute
                 */
                void setDELVNAME(java.lang.String delvname);
                
                /**
                 * Sets (as xml) the "DELV_NAME" attribute
                 */
                void xsetDELVNAME(org.apache.xmlbeans.XmlString delvname);
                
                /**
                 * Unsets the "DELV_NAME" attribute
                 */
                void unsetDELVNAME();
                
                /**
                 * Gets the "DELV_ADDR_1" attribute
                 */
                java.lang.String getDELVADDR1();
                
                /**
                 * Gets (as xml) the "DELV_ADDR_1" attribute
                 */
                org.apache.xmlbeans.XmlString xgetDELVADDR1();
                
                /**
                 * True if has "DELV_ADDR_1" attribute
                 */
                boolean isSetDELVADDR1();
                
                /**
                 * Sets the "DELV_ADDR_1" attribute
                 */
                void setDELVADDR1(java.lang.String delvaddr1);
                
                /**
                 * Sets (as xml) the "DELV_ADDR_1" attribute
                 */
                void xsetDELVADDR1(org.apache.xmlbeans.XmlString delvaddr1);
                
                /**
                 * Unsets the "DELV_ADDR_1" attribute
                 */
                void unsetDELVADDR1();
                
                /**
                 * Gets the "DELV_ADDR_2" attribute
                 */
                java.lang.String getDELVADDR2();
                
                /**
                 * Gets (as xml) the "DELV_ADDR_2" attribute
                 */
                org.apache.xmlbeans.XmlString xgetDELVADDR2();
                
                /**
                 * True if has "DELV_ADDR_2" attribute
                 */
                boolean isSetDELVADDR2();
                
                /**
                 * Sets the "DELV_ADDR_2" attribute
                 */
                void setDELVADDR2(java.lang.String delvaddr2);
                
                /**
                 * Sets (as xml) the "DELV_ADDR_2" attribute
                 */
                void xsetDELVADDR2(org.apache.xmlbeans.XmlString delvaddr2);
                
                /**
                 * Unsets the "DELV_ADDR_2" attribute
                 */
                void unsetDELVADDR2();
                
                /**
                 * Gets the "DELV_ADDR_3" attribute
                 */
                java.lang.String getDELVADDR3();
                
                /**
                 * Gets (as xml) the "DELV_ADDR_3" attribute
                 */
                org.apache.xmlbeans.XmlString xgetDELVADDR3();
                
                /**
                 * True if has "DELV_ADDR_3" attribute
                 */
                boolean isSetDELVADDR3();
                
                /**
                 * Sets the "DELV_ADDR_3" attribute
                 */
                void setDELVADDR3(java.lang.String delvaddr3);
                
                /**
                 * Sets (as xml) the "DELV_ADDR_3" attribute
                 */
                void xsetDELVADDR3(org.apache.xmlbeans.XmlString delvaddr3);
                
                /**
                 * Unsets the "DELV_ADDR_3" attribute
                 */
                void unsetDELVADDR3();
                
                /**
                 * Gets the "STAFF_TEL" attribute
                 */
                java.lang.String getSTAFFTEL();
                
                /**
                 * Gets (as xml) the "STAFF_TEL" attribute
                 */
                org.apache.xmlbeans.XmlString xgetSTAFFTEL();
                
                /**
                 * True if has "STAFF_TEL" attribute
                 */
                boolean isSetSTAFFTEL();
                
                /**
                 * Sets the "STAFF_TEL" attribute
                 */
                void setSTAFFTEL(java.lang.String stafftel);
                
                /**
                 * Sets (as xml) the "STAFF_TEL" attribute
                 */
                void xsetSTAFFTEL(org.apache.xmlbeans.XmlString stafftel);
                
                /**
                 * Unsets the "STAFF_TEL" attribute
                 */
                void unsetSTAFFTEL();
                
                /**
                 * A factory class with static methods for creating instances
                 * of this type.
                 */
                
                public static final class Factory
                {
                    public static noNamespace.SalesHCuDocument.SalesHCu.ROWDATA.ROW newInstance() {
                      return (noNamespace.SalesHCuDocument.SalesHCu.ROWDATA.ROW) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
                    
                    public static noNamespace.SalesHCuDocument.SalesHCu.ROWDATA.ROW newInstance(org.apache.xmlbeans.XmlOptions options) {
                      return (noNamespace.SalesHCuDocument.SalesHCu.ROWDATA.ROW) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
                    
                    private Factory() { } // No instance of this class allowed
                }
            }
            
            /**
             * A factory class with static methods for creating instances
             * of this type.
             */
            
            public static final class Factory
            {
                public static noNamespace.SalesHCuDocument.SalesHCu.ROWDATA newInstance() {
                  return (noNamespace.SalesHCuDocument.SalesHCu.ROWDATA) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
                
                public static noNamespace.SalesHCuDocument.SalesHCu.ROWDATA newInstance(org.apache.xmlbeans.XmlOptions options) {
                  return (noNamespace.SalesHCuDocument.SalesHCu.ROWDATA) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
                
                private Factory() { } // No instance of this class allowed
            }
        }
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static noNamespace.SalesHCuDocument.SalesHCu newInstance() {
              return (noNamespace.SalesHCuDocument.SalesHCu) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static noNamespace.SalesHCuDocument.SalesHCu newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (noNamespace.SalesHCuDocument.SalesHCu) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static noNamespace.SalesHCuDocument newInstance() {
          return (noNamespace.SalesHCuDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static noNamespace.SalesHCuDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (noNamespace.SalesHCuDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static noNamespace.SalesHCuDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesHCuDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static noNamespace.SalesHCuDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesHCuDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static noNamespace.SalesHCuDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesHCuDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static noNamespace.SalesHCuDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesHCuDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static noNamespace.SalesHCuDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesHCuDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static noNamespace.SalesHCuDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesHCuDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static noNamespace.SalesHCuDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesHCuDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static noNamespace.SalesHCuDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesHCuDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static noNamespace.SalesHCuDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesHCuDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static noNamespace.SalesHCuDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesHCuDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static noNamespace.SalesHCuDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesHCuDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static noNamespace.SalesHCuDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesHCuDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static noNamespace.SalesHCuDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesHCuDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static noNamespace.SalesHCuDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesHCuDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static noNamespace.SalesHCuDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (noNamespace.SalesHCuDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static noNamespace.SalesHCuDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (noNamespace.SalesHCuDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
