/*
 * An XML document type.
 * Localname: sales_com
 * Namespace: 
 * Java type: noNamespace.SalesComDocument
 *
 * Automatically generated - do not modify.
 */
package noNamespace;


/**
 * A document containing one sales_com(@) element.
 *
 * This is a complex type.
 */
public interface SalesComDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(SalesComDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s663963164079E0E9B5FCF137586B2125").resolveHandle("salescom0375doctype");
    
    /**
     * Gets the "sales_com" element
     */
    noNamespace.SalesComDocument.SalesCom getSalesCom();
    
    /**
     * Sets the "sales_com" element
     */
    void setSalesCom(noNamespace.SalesComDocument.SalesCom salesCom);
    
    /**
     * Appends and returns a new empty "sales_com" element
     */
    noNamespace.SalesComDocument.SalesCom addNewSalesCom();
    
    /**
     * An XML sales_com(@).
     *
     * This is a complex type.
     */
    public interface SalesCom extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(SalesCom.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s663963164079E0E9B5FCF137586B2125").resolveHandle("salescomc6bfelemtype");
        
        /**
         * Gets the "ROWDATA" element
         */
        noNamespace.SalesComDocument.SalesCom.ROWDATA getROWDATA();
        
        /**
         * Sets the "ROWDATA" element
         */
        void setROWDATA(noNamespace.SalesComDocument.SalesCom.ROWDATA rowdata);
        
        /**
         * Appends and returns a new empty "ROWDATA" element
         */
        noNamespace.SalesComDocument.SalesCom.ROWDATA addNewROWDATA();
        
        /**
         * An XML ROWDATA(@).
         *
         * This is a complex type.
         */
        public interface ROWDATA extends org.apache.xmlbeans.XmlObject
        {
            public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
                org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ROWDATA.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s663963164079E0E9B5FCF137586B2125").resolveHandle("rowdata6b9felemtype");
            
            /**
             * Gets the "ROW" element
             */
            noNamespace.SalesComDocument.SalesCom.ROWDATA.ROW getROW();
            
            /**
             * Sets the "ROW" element
             */
            void setROW(noNamespace.SalesComDocument.SalesCom.ROWDATA.ROW row);
            
            /**
             * Appends and returns a new empty "ROW" element
             */
            noNamespace.SalesComDocument.SalesCom.ROWDATA.ROW addNewROW();
            
            /**
             * An XML ROW(@).
             *
             * This is an atomic type that is a restriction of noNamespace.SalesComDocument$SalesCom$ROWDATA$ROW.
             */
            public interface ROW extends org.apache.xmlbeans.XmlString
            {
                public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
                    org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ROW.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s663963164079E0E9B5FCF137586B2125").resolveHandle("row2455elemtype");
                
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
                 * Gets the "Salesman_ID" attribute
                 */
                java.lang.String getSalesmanID();
                
                /**
                 * Gets (as xml) the "Salesman_ID" attribute
                 */
                org.apache.xmlbeans.XmlString xgetSalesmanID();
                
                /**
                 * True if has "Salesman_ID" attribute
                 */
                boolean isSetSalesmanID();
                
                /**
                 * Sets the "Salesman_ID" attribute
                 */
                void setSalesmanID(java.lang.String salesmanID);
                
                /**
                 * Sets (as xml) the "Salesman_ID" attribute
                 */
                void xsetSalesmanID(org.apache.xmlbeans.XmlString salesmanID);
                
                /**
                 * Unsets the "Salesman_ID" attribute
                 */
                void unsetSalesmanID();
                
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
                 * Gets the "Com_Percent" attribute
                 */
                java.lang.String getComPercent();
                
                /**
                 * Gets (as xml) the "Com_Percent" attribute
                 */
                org.apache.xmlbeans.XmlString xgetComPercent();
                
                /**
                 * True if has "Com_Percent" attribute
                 */
                boolean isSetComPercent();
                
                /**
                 * Sets the "Com_Percent" attribute
                 */
                void setComPercent(java.lang.String comPercent);
                
                /**
                 * Sets (as xml) the "Com_Percent" attribute
                 */
                void xsetComPercent(org.apache.xmlbeans.XmlString comPercent);
                
                /**
                 * Unsets the "Com_Percent" attribute
                 */
                void unsetComPercent();
                
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
                 * Gets the "Channel" attribute
                 */
                java.lang.String getChannel();
                
                /**
                 * Gets (as xml) the "Channel" attribute
                 */
                org.apache.xmlbeans.XmlString xgetChannel();
                
                /**
                 * True if has "Channel" attribute
                 */
                boolean isSetChannel();
                
                /**
                 * Sets the "Channel" attribute
                 */
                void setChannel(java.lang.String channel);
                
                /**
                 * Sets (as xml) the "Channel" attribute
                 */
                void xsetChannel(org.apache.xmlbeans.XmlString channel);
                
                /**
                 * Unsets the "Channel" attribute
                 */
                void unsetChannel();
                
                /**
                 * Gets the "Salesman_Code" attribute
                 */
                java.lang.String getSalesmanCode();
                
                /**
                 * Gets (as xml) the "Salesman_Code" attribute
                 */
                org.apache.xmlbeans.XmlString xgetSalesmanCode();
                
                /**
                 * True if has "Salesman_Code" attribute
                 */
                boolean isSetSalesmanCode();
                
                /**
                 * Sets the "Salesman_Code" attribute
                 */
                void setSalesmanCode(java.lang.String salesmanCode);
                
                /**
                 * Sets (as xml) the "Salesman_Code" attribute
                 */
                void xsetSalesmanCode(org.apache.xmlbeans.XmlString salesmanCode);
                
                /**
                 * Unsets the "Salesman_Code" attribute
                 */
                void unsetSalesmanCode();
                
                /**
                 * A factory class with static methods for creating instances
                 * of this type.
                 */
                
                public static final class Factory
                {
                    public static noNamespace.SalesComDocument.SalesCom.ROWDATA.ROW newInstance() {
                      return (noNamespace.SalesComDocument.SalesCom.ROWDATA.ROW) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
                    
                    public static noNamespace.SalesComDocument.SalesCom.ROWDATA.ROW newInstance(org.apache.xmlbeans.XmlOptions options) {
                      return (noNamespace.SalesComDocument.SalesCom.ROWDATA.ROW) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
                    
                    private Factory() { } // No instance of this class allowed
                }
            }
            
            /**
             * A factory class with static methods for creating instances
             * of this type.
             */
            
            public static final class Factory
            {
                public static noNamespace.SalesComDocument.SalesCom.ROWDATA newInstance() {
                  return (noNamespace.SalesComDocument.SalesCom.ROWDATA) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
                
                public static noNamespace.SalesComDocument.SalesCom.ROWDATA newInstance(org.apache.xmlbeans.XmlOptions options) {
                  return (noNamespace.SalesComDocument.SalesCom.ROWDATA) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
                
                private Factory() { } // No instance of this class allowed
            }
        }
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static noNamespace.SalesComDocument.SalesCom newInstance() {
              return (noNamespace.SalesComDocument.SalesCom) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static noNamespace.SalesComDocument.SalesCom newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (noNamespace.SalesComDocument.SalesCom) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static noNamespace.SalesComDocument newInstance() {
          return (noNamespace.SalesComDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static noNamespace.SalesComDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (noNamespace.SalesComDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static noNamespace.SalesComDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesComDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static noNamespace.SalesComDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesComDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static noNamespace.SalesComDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesComDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static noNamespace.SalesComDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesComDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static noNamespace.SalesComDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesComDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static noNamespace.SalesComDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesComDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static noNamespace.SalesComDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesComDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static noNamespace.SalesComDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesComDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static noNamespace.SalesComDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesComDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static noNamespace.SalesComDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesComDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static noNamespace.SalesComDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesComDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static noNamespace.SalesComDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesComDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static noNamespace.SalesComDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesComDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static noNamespace.SalesComDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesComDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static noNamespace.SalesComDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (noNamespace.SalesComDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static noNamespace.SalesComDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (noNamespace.SalesComDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
