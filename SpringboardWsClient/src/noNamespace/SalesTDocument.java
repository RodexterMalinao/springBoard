/*
 * An XML document type.
 * Localname: sales_t
 * Namespace: 
 * Java type: noNamespace.SalesTDocument
 *
 * Automatically generated - do not modify.
 */
package noNamespace;


/**
 * A document containing one sales_t(@) element.
 *
 * This is a complex type.
 */
public interface SalesTDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(SalesTDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC3B2C4414585EFAE147D9506E341EE43").resolveHandle("salest6f68doctype");
    
    /**
     * Gets the "sales_t" element
     */
    noNamespace.SalesTDocument.SalesT getSalesT();
    
    /**
     * Sets the "sales_t" element
     */
    void setSalesT(noNamespace.SalesTDocument.SalesT salesT);
    
    /**
     * Appends and returns a new empty "sales_t" element
     */
    noNamespace.SalesTDocument.SalesT addNewSalesT();
    
    /**
     * An XML sales_t(@).
     *
     * This is a complex type.
     */
    public interface SalesT extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(SalesT.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC3B2C4414585EFAE147D9506E341EE43").resolveHandle("salest05a5elemtype");
        
        /**
         * Gets the "ROWDATA" element
         */
        noNamespace.SalesTDocument.SalesT.ROWDATA getROWDATA();
        
        /**
         * Sets the "ROWDATA" element
         */
        void setROWDATA(noNamespace.SalesTDocument.SalesT.ROWDATA rowdata);
        
        /**
         * Appends and returns a new empty "ROWDATA" element
         */
        noNamespace.SalesTDocument.SalesT.ROWDATA addNewROWDATA();
        
        /**
         * An XML ROWDATA(@).
         *
         * This is a complex type.
         */
        public interface ROWDATA extends org.apache.xmlbeans.XmlObject
        {
            public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
                org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ROWDATA.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC3B2C4414585EFAE147D9506E341EE43").resolveHandle("rowdata1a85elemtype");
            
            /**
             * Gets array of all "ROW" elements
             */
            noNamespace.SalesTDocument.SalesT.ROWDATA.ROW[] getROWArray();
            
            /**
             * Gets ith "ROW" element
             */
            noNamespace.SalesTDocument.SalesT.ROWDATA.ROW getROWArray(int i);
            
            /**
             * Returns number of "ROW" element
             */
            int sizeOfROWArray();
            
            /**
             * Sets array of all "ROW" element
             */
            void setROWArray(noNamespace.SalesTDocument.SalesT.ROWDATA.ROW[] rowArray);
            
            /**
             * Sets ith "ROW" element
             */
            void setROWArray(int i, noNamespace.SalesTDocument.SalesT.ROWDATA.ROW row);
            
            /**
             * Inserts and returns a new empty value (as xml) as the ith "ROW" element
             */
            noNamespace.SalesTDocument.SalesT.ROWDATA.ROW insertNewROW(int i);
            
            /**
             * Appends and returns a new empty value (as xml) as the last "ROW" element
             */
            noNamespace.SalesTDocument.SalesT.ROWDATA.ROW addNewROW();
            
            /**
             * Removes the ith "ROW" element
             */
            void removeROW(int i);
            
            /**
             * An XML ROW(@).
             *
             * This is an atomic type that is a restriction of noNamespace.SalesTDocument$SalesT$ROWDATA$ROW.
             */
            public interface ROW extends org.apache.xmlbeans.XmlString
            {
                public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
                    org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ROW.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC3B2C4414585EFAE147D9506E341EE43").resolveHandle("row9e3belemtype");
                
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
                 * Gets the "Seq_Num" attribute
                 */
                java.lang.String getSeqNum();
                
                /**
                 * Gets (as xml) the "Seq_Num" attribute
                 */
                org.apache.xmlbeans.XmlString xgetSeqNum();
                
                /**
                 * True if has "Seq_Num" attribute
                 */
                boolean isSetSeqNum();
                
                /**
                 * Sets the "Seq_Num" attribute
                 */
                void setSeqNum(java.lang.String seqNum);
                
                /**
                 * Sets (as xml) the "Seq_Num" attribute
                 */
                void xsetSeqNum(org.apache.xmlbeans.XmlString seqNum);
                
                /**
                 * Unsets the "Seq_Num" attribute
                 */
                void unsetSeqNum();
                
                /**
                 * Gets the "Tender_ID" attribute
                 */
                java.lang.String getTenderID();
                
                /**
                 * Gets (as xml) the "Tender_ID" attribute
                 */
                org.apache.xmlbeans.XmlString xgetTenderID();
                
                /**
                 * True if has "Tender_ID" attribute
                 */
                boolean isSetTenderID();
                
                /**
                 * Sets the "Tender_ID" attribute
                 */
                void setTenderID(java.lang.String tenderID);
                
                /**
                 * Sets (as xml) the "Tender_ID" attribute
                 */
                void xsetTenderID(org.apache.xmlbeans.XmlString tenderID);
                
                /**
                 * Unsets the "Tender_ID" attribute
                 */
                void unsetTenderID();
                
                /**
                 * Gets the "Tender_Amount" attribute
                 */
                short getTenderAmount();
                
                /**
                 * Gets (as xml) the "Tender_Amount" attribute
                 */
                org.apache.xmlbeans.XmlShort xgetTenderAmount();
                
                /**
                 * True if has "Tender_Amount" attribute
                 */
                boolean isSetTenderAmount();
                
                /**
                 * Sets the "Tender_Amount" attribute
                 */
                void setTenderAmount(short tenderAmount);
                
                /**
                 * Sets (as xml) the "Tender_Amount" attribute
                 */
                void xsetTenderAmount(org.apache.xmlbeans.XmlShort tenderAmount);
                
                /**
                 * Unsets the "Tender_Amount" attribute
                 */
                void unsetTenderAmount();
                
                /**
                 * Gets the "Local_Amount" attribute
                 */
                short getLocalAmount();
                
                /**
                 * Gets (as xml) the "Local_Amount" attribute
                 */
                org.apache.xmlbeans.XmlShort xgetLocalAmount();
                
                /**
                 * True if has "Local_Amount" attribute
                 */
                boolean isSetLocalAmount();
                
                /**
                 * Sets the "Local_Amount" attribute
                 */
                void setLocalAmount(short localAmount);
                
                /**
                 * Sets (as xml) the "Local_Amount" attribute
                 */
                void xsetLocalAmount(org.apache.xmlbeans.XmlShort localAmount);
                
                /**
                 * Unsets the "Local_Amount" attribute
                 */
                void unsetLocalAmount();
                
                /**
                 * Gets the "Tender_Desc" attribute
                 */
                java.lang.String getTenderDesc();
                
                /**
                 * Gets (as xml) the "Tender_Desc" attribute
                 */
                org.apache.xmlbeans.XmlString xgetTenderDesc();
                
                /**
                 * True if has "Tender_Desc" attribute
                 */
                boolean isSetTenderDesc();
                
                /**
                 * Sets the "Tender_Desc" attribute
                 */
                void setTenderDesc(java.lang.String tenderDesc);
                
                /**
                 * Sets (as xml) the "Tender_Desc" attribute
                 */
                void xsetTenderDesc(org.apache.xmlbeans.XmlString tenderDesc);
                
                /**
                 * Unsets the "Tender_Desc" attribute
                 */
                void unsetTenderDesc();
                
                /**
                 * Gets the "Exchange_Rate" attribute
                 */
                short getExchangeRate();
                
                /**
                 * Gets (as xml) the "Exchange_Rate" attribute
                 */
                org.apache.xmlbeans.XmlShort xgetExchangeRate();
                
                /**
                 * True if has "Exchange_Rate" attribute
                 */
                boolean isSetExchangeRate();
                
                /**
                 * Sets the "Exchange_Rate" attribute
                 */
                void setExchangeRate(short exchangeRate);
                
                /**
                 * Sets (as xml) the "Exchange_Rate" attribute
                 */
                void xsetExchangeRate(org.apache.xmlbeans.XmlShort exchangeRate);
                
                /**
                 * Unsets the "Exchange_Rate" attribute
                 */
                void unsetExchangeRate();
                
                /**
                 * Gets the "Payment_Type" attribute
                 */
                java.lang.String getPaymentType();
                
                /**
                 * Gets (as xml) the "Payment_Type" attribute
                 */
                org.apache.xmlbeans.XmlString xgetPaymentType();
                
                /**
                 * True if has "Payment_Type" attribute
                 */
                boolean isSetPaymentType();
                
                /**
                 * Sets the "Payment_Type" attribute
                 */
                void setPaymentType(java.lang.String paymentType);
                
                /**
                 * Sets (as xml) the "Payment_Type" attribute
                 */
                void xsetPaymentType(org.apache.xmlbeans.XmlString paymentType);
                
                /**
                 * Unsets the "Payment_Type" attribute
                 */
                void unsetPaymentType();
                
                /**
                 * Gets the "Additional" attribute
                 */
                java.lang.String getAdditional();
                
                /**
                 * Gets (as xml) the "Additional" attribute
                 */
                org.apache.xmlbeans.XmlString xgetAdditional();
                
                /**
                 * True if has "Additional" attribute
                 */
                boolean isSetAdditional();
                
                /**
                 * Sets the "Additional" attribute
                 */
                void setAdditional(java.lang.String additional);
                
                /**
                 * Sets (as xml) the "Additional" attribute
                 */
                void xsetAdditional(org.apache.xmlbeans.XmlString additional);
                
                /**
                 * Unsets the "Additional" attribute
                 */
                void unsetAdditional();
                
                /**
                 * Gets the "Tender_Type" attribute
                 */
                java.lang.String getTenderType();
                
                /**
                 * Gets (as xml) the "Tender_Type" attribute
                 */
                org.apache.xmlbeans.XmlString xgetTenderType();
                
                /**
                 * True if has "Tender_Type" attribute
                 */
                boolean isSetTenderType();
                
                /**
                 * Sets the "Tender_Type" attribute
                 */
                void setTenderType(java.lang.String tenderType);
                
                /**
                 * Sets (as xml) the "Tender_Type" attribute
                 */
                void xsetTenderType(org.apache.xmlbeans.XmlString tenderType);
                
                /**
                 * Unsets the "Tender_Type" attribute
                 */
                void unsetTenderType();
                
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
                 * Gets the "Org_Amount" attribute
                 */
                short getOrgAmount();
                
                /**
                 * Gets (as xml) the "Org_Amount" attribute
                 */
                org.apache.xmlbeans.XmlShort xgetOrgAmount();
                
                /**
                 * True if has "Org_Amount" attribute
                 */
                boolean isSetOrgAmount();
                
                /**
                 * Sets the "Org_Amount" attribute
                 */
                void setOrgAmount(short orgAmount);
                
                /**
                 * Sets (as xml) the "Org_Amount" attribute
                 */
                void xsetOrgAmount(org.apache.xmlbeans.XmlShort orgAmount);
                
                /**
                 * Unsets the "Org_Amount" attribute
                 */
                void unsetOrgAmount();
                
                /**
                 * Gets the "Pay_Confirm_Date" attribute
                 */
                java.lang.String getPayConfirmDate();
                
                /**
                 * Gets (as xml) the "Pay_Confirm_Date" attribute
                 */
                org.apache.xmlbeans.XmlString xgetPayConfirmDate();
                
                /**
                 * True if has "Pay_Confirm_Date" attribute
                 */
                boolean isSetPayConfirmDate();
                
                /**
                 * Sets the "Pay_Confirm_Date" attribute
                 */
                void setPayConfirmDate(java.lang.String payConfirmDate);
                
                /**
                 * Sets (as xml) the "Pay_Confirm_Date" attribute
                 */
                void xsetPayConfirmDate(org.apache.xmlbeans.XmlString payConfirmDate);
                
                /**
                 * Unsets the "Pay_Confirm_Date" attribute
                 */
                void unsetPayConfirmDate();
                
                /**
                 * Gets the "Deposit" attribute
                 */
                java.lang.String getDeposit();
                
                /**
                 * Gets (as xml) the "Deposit" attribute
                 */
                org.apache.xmlbeans.XmlString xgetDeposit();
                
                /**
                 * True if has "Deposit" attribute
                 */
                boolean isSetDeposit();
                
                /**
                 * Sets the "Deposit" attribute
                 */
                void setDeposit(java.lang.String deposit);
                
                /**
                 * Sets (as xml) the "Deposit" attribute
                 */
                void xsetDeposit(org.apache.xmlbeans.XmlString deposit);
                
                /**
                 * Unsets the "Deposit" attribute
                 */
                void unsetDeposit();
                
                /**
                 * Gets the "Record_Type" attribute
                 */
                java.lang.String getRecordType();
                
                /**
                 * Gets (as xml) the "Record_Type" attribute
                 */
                org.apache.xmlbeans.XmlString xgetRecordType();
                
                /**
                 * True if has "Record_Type" attribute
                 */
                boolean isSetRecordType();
                
                /**
                 * Sets the "Record_Type" attribute
                 */
                void setRecordType(java.lang.String recordType);
                
                /**
                 * Sets (as xml) the "Record_Type" attribute
                 */
                void xsetRecordType(org.apache.xmlbeans.XmlString recordType);
                
                /**
                 * Unsets the "Record_Type" attribute
                 */
                void unsetRecordType();
                
                /**
                 * Gets the "EOD_Flag" attribute
                 */
                java.lang.String getEODFlag();
                
                /**
                 * Gets (as xml) the "EOD_Flag" attribute
                 */
                org.apache.xmlbeans.XmlString xgetEODFlag();
                
                /**
                 * True if has "EOD_Flag" attribute
                 */
                boolean isSetEODFlag();
                
                /**
                 * Sets the "EOD_Flag" attribute
                 */
                void setEODFlag(java.lang.String eodFlag);
                
                /**
                 * Sets (as xml) the "EOD_Flag" attribute
                 */
                void xsetEODFlag(org.apache.xmlbeans.XmlString eodFlag);
                
                /**
                 * Unsets the "EOD_Flag" attribute
                 */
                void unsetEODFlag();
                
                /**
                 * Gets the "Input_Type" attribute
                 */
                java.lang.String getInputType();
                
                /**
                 * Gets (as xml) the "Input_Type" attribute
                 */
                org.apache.xmlbeans.XmlString xgetInputType();
                
                /**
                 * True if has "Input_Type" attribute
                 */
                boolean isSetInputType();
                
                /**
                 * Sets the "Input_Type" attribute
                 */
                void setInputType(java.lang.String inputType);
                
                /**
                 * Sets (as xml) the "Input_Type" attribute
                 */
                void xsetInputType(org.apache.xmlbeans.XmlString inputType);
                
                /**
                 * Unsets the "Input_Type" attribute
                 */
                void unsetInputType();
                
                /**
                 * A factory class with static methods for creating instances
                 * of this type.
                 */
                
                public static final class Factory
                {
                    public static noNamespace.SalesTDocument.SalesT.ROWDATA.ROW newInstance() {
                      return (noNamespace.SalesTDocument.SalesT.ROWDATA.ROW) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
                    
                    public static noNamespace.SalesTDocument.SalesT.ROWDATA.ROW newInstance(org.apache.xmlbeans.XmlOptions options) {
                      return (noNamespace.SalesTDocument.SalesT.ROWDATA.ROW) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
                    
                    private Factory() { } // No instance of this class allowed
                }
            }
            
            /**
             * A factory class with static methods for creating instances
             * of this type.
             */
            
            public static final class Factory
            {
                public static noNamespace.SalesTDocument.SalesT.ROWDATA newInstance() {
                  return (noNamespace.SalesTDocument.SalesT.ROWDATA) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
                
                public static noNamespace.SalesTDocument.SalesT.ROWDATA newInstance(org.apache.xmlbeans.XmlOptions options) {
                  return (noNamespace.SalesTDocument.SalesT.ROWDATA) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
                
                private Factory() { } // No instance of this class allowed
            }
        }
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static noNamespace.SalesTDocument.SalesT newInstance() {
              return (noNamespace.SalesTDocument.SalesT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static noNamespace.SalesTDocument.SalesT newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (noNamespace.SalesTDocument.SalesT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static noNamespace.SalesTDocument newInstance() {
          return (noNamespace.SalesTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static noNamespace.SalesTDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (noNamespace.SalesTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static noNamespace.SalesTDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static noNamespace.SalesTDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static noNamespace.SalesTDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static noNamespace.SalesTDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static noNamespace.SalesTDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static noNamespace.SalesTDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static noNamespace.SalesTDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static noNamespace.SalesTDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static noNamespace.SalesTDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static noNamespace.SalesTDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static noNamespace.SalesTDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static noNamespace.SalesTDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static noNamespace.SalesTDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static noNamespace.SalesTDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static noNamespace.SalesTDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (noNamespace.SalesTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static noNamespace.SalesTDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (noNamespace.SalesTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
