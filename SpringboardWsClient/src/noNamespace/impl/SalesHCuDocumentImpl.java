/*
 * An XML document type.
 * Localname: sales_h_cu
 * Namespace: 
 * Java type: noNamespace.SalesHCuDocument
 *
 * Automatically generated - do not modify.
 */
package noNamespace.impl;
/**
 * A document containing one sales_h_cu(@) element.
 *
 * This is a complex type.
 */
public class SalesHCuDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.SalesHCuDocument
{
    private static final long serialVersionUID = 1L;
    
    public SalesHCuDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SALESHCU$0 = 
        new javax.xml.namespace.QName("", "sales_h_cu");
    
    
    /**
     * Gets the "sales_h_cu" element
     */
    public noNamespace.SalesHCuDocument.SalesHCu getSalesHCu()
    {
        synchronized (monitor())
        {
            check_orphaned();
            noNamespace.SalesHCuDocument.SalesHCu target = null;
            target = (noNamespace.SalesHCuDocument.SalesHCu)get_store().find_element_user(SALESHCU$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "sales_h_cu" element
     */
    public void setSalesHCu(noNamespace.SalesHCuDocument.SalesHCu salesHCu)
    {
        generatedSetterHelperImpl(salesHCu, SALESHCU$0, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }
    
    /**
     * Appends and returns a new empty "sales_h_cu" element
     */
    public noNamespace.SalesHCuDocument.SalesHCu addNewSalesHCu()
    {
        synchronized (monitor())
        {
            check_orphaned();
            noNamespace.SalesHCuDocument.SalesHCu target = null;
            target = (noNamespace.SalesHCuDocument.SalesHCu)get_store().add_element_user(SALESHCU$0);
            return target;
        }
    }
    /**
     * An XML sales_h_cu(@).
     *
     * This is a complex type.
     */
    public static class SalesHCuImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.SalesHCuDocument.SalesHCu
    {
        private static final long serialVersionUID = 1L;
        
        public SalesHCuImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName ROWDATA$0 = 
            new javax.xml.namespace.QName("", "ROWDATA");
        
        
        /**
         * Gets the "ROWDATA" element
         */
        public noNamespace.SalesHCuDocument.SalesHCu.ROWDATA getROWDATA()
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.SalesHCuDocument.SalesHCu.ROWDATA target = null;
                target = (noNamespace.SalesHCuDocument.SalesHCu.ROWDATA)get_store().find_element_user(ROWDATA$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Sets the "ROWDATA" element
         */
        public void setROWDATA(noNamespace.SalesHCuDocument.SalesHCu.ROWDATA rowdata)
        {
            generatedSetterHelperImpl(rowdata, ROWDATA$0, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
        }
        
        /**
         * Appends and returns a new empty "ROWDATA" element
         */
        public noNamespace.SalesHCuDocument.SalesHCu.ROWDATA addNewROWDATA()
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.SalesHCuDocument.SalesHCu.ROWDATA target = null;
                target = (noNamespace.SalesHCuDocument.SalesHCu.ROWDATA)get_store().add_element_user(ROWDATA$0);
                return target;
            }
        }
        /**
         * An XML ROWDATA(@).
         *
         * This is a complex type.
         */
        public static class ROWDATAImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.SalesHCuDocument.SalesHCu.ROWDATA
        {
            private static final long serialVersionUID = 1L;
            
            public ROWDATAImpl(org.apache.xmlbeans.SchemaType sType)
            {
                super(sType);
            }
            
            private static final javax.xml.namespace.QName ROW$0 = 
                new javax.xml.namespace.QName("", "ROW");
            
            
            /**
             * Gets the "ROW" element
             */
            public noNamespace.SalesHCuDocument.SalesHCu.ROWDATA.ROW getROW()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    noNamespace.SalesHCuDocument.SalesHCu.ROWDATA.ROW target = null;
                    target = (noNamespace.SalesHCuDocument.SalesHCu.ROWDATA.ROW)get_store().find_element_user(ROW$0, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target;
                }
            }
            
            /**
             * Sets the "ROW" element
             */
            public void setROW(noNamespace.SalesHCuDocument.SalesHCu.ROWDATA.ROW row)
            {
                generatedSetterHelperImpl(row, ROW$0, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
            }
            
            /**
             * Appends and returns a new empty "ROW" element
             */
            public noNamespace.SalesHCuDocument.SalesHCu.ROWDATA.ROW addNewROW()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    noNamespace.SalesHCuDocument.SalesHCu.ROWDATA.ROW target = null;
                    target = (noNamespace.SalesHCuDocument.SalesHCu.ROWDATA.ROW)get_store().add_element_user(ROW$0);
                    return target;
                }
            }
            /**
             * An XML ROW(@).
             *
             * This is an atomic type that is a restriction of noNamespace.SalesHCuDocument$SalesHCu$ROWDATA$ROW.
             */
            public static class ROWImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements noNamespace.SalesHCuDocument.SalesHCu.ROWDATA.ROW
            {
                private static final long serialVersionUID = 1L;
                
                public ROWImpl(org.apache.xmlbeans.SchemaType sType)
                {
                    super(sType, true);
                }
                
                protected ROWImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
                {
                    super(sType, b);
                }
                
                private static final javax.xml.namespace.QName STOREID$0 = 
                    new javax.xml.namespace.QName("", "Store_ID");
                private static final javax.xml.namespace.QName REGISTERID$2 = 
                    new javax.xml.namespace.QName("", "Register_ID");
                private static final javax.xml.namespace.QName TRANSNUM$4 = 
                    new javax.xml.namespace.QName("", "Trans_Num");
                private static final javax.xml.namespace.QName BUSDATE$6 = 
                    new javax.xml.namespace.QName("", "Bus_Date");
                private static final javax.xml.namespace.QName TXNDATE$8 = 
                    new javax.xml.namespace.QName("", "Txn_Date");
                private static final javax.xml.namespace.QName SMTYPE$10 = 
                    new javax.xml.namespace.QName("", "SM_Type");
                private static final javax.xml.namespace.QName SRVTYPE$12 = 
                    new javax.xml.namespace.QName("", "SRV_TYPE");
                private static final javax.xml.namespace.QName SRVNUM$14 = 
                    new javax.xml.namespace.QName("", "SRV_NUM");
                private static final javax.xml.namespace.QName WODESC$16 = 
                    new javax.xml.namespace.QName("", "WO_DESC");
                private static final javax.xml.namespace.QName BISDATE$18 = 
                    new javax.xml.namespace.QName("", "BIS_Date");
                private static final javax.xml.namespace.QName INSTALLBU$20 = 
                    new javax.xml.namespace.QName("", "INSTALL_BU");
                private static final javax.xml.namespace.QName PRODLINE$22 = 
                    new javax.xml.namespace.QName("", "PROD_LINE");
                private static final javax.xml.namespace.QName EXCHGCODE$24 = 
                    new javax.xml.namespace.QName("", "EXCHG_CODE");
                private static final javax.xml.namespace.QName CTRCTENDDATE$26 = 
                    new javax.xml.namespace.QName("", "CTRCT_END_DATE");
                private static final javax.xml.namespace.QName CUSNUM$28 = 
                    new javax.xml.namespace.QName("", "CUS_NUM");
                private static final javax.xml.namespace.QName ACCTNUM$30 = 
                    new javax.xml.namespace.QName("", "ACCT_NUM");
                private static final javax.xml.namespace.QName CUSNAME$32 = 
                    new javax.xml.namespace.QName("", "CUS_NAME");
                private static final javax.xml.namespace.QName CUSSTATUS$34 = 
                    new javax.xml.namespace.QName("", "CUS_STATUS");
                private static final javax.xml.namespace.QName LEASCODE$36 = 
                    new javax.xml.namespace.QName("", "LEAS_CODE");
                private static final javax.xml.namespace.QName LPSMEMSHIPNUM$38 = 
                    new javax.xml.namespace.QName("", "LPS_MEMSHIP_NUM");
                private static final javax.xml.namespace.QName USERNAME$40 = 
                    new javax.xml.namespace.QName("", "USER_NAME");
                private static final javax.xml.namespace.QName CONTACTPERSON$42 = 
                    new javax.xml.namespace.QName("", "CONTACT_PERSON");
                private static final javax.xml.namespace.QName CONTACTTEL$44 = 
                    new javax.xml.namespace.QName("", "CONTACT_TEL");
                private static final javax.xml.namespace.QName CONTACTFAX$46 = 
                    new javax.xml.namespace.QName("", "CONTACT_FAX");
                private static final javax.xml.namespace.QName CUSREFERENCE$48 = 
                    new javax.xml.namespace.QName("", "CUS_REFERENCE");
                private static final javax.xml.namespace.QName CUSPO$50 = 
                    new javax.xml.namespace.QName("", "CUS_PO");
                private static final javax.xml.namespace.QName CORRFLT$52 = 
                    new javax.xml.namespace.QName("", "CORR_FLT");
                private static final javax.xml.namespace.QName CORRFLOOR$54 = 
                    new javax.xml.namespace.QName("", "CORR_FLOOR");
                private static final javax.xml.namespace.QName CORRBLDG$56 = 
                    new javax.xml.namespace.QName("", "CORR_BLDG");
                private static final javax.xml.namespace.QName CORRPOBOX$58 = 
                    new javax.xml.namespace.QName("", "CORR_PO_BOX");
                private static final javax.xml.namespace.QName CORRSTNUM$60 = 
                    new javax.xml.namespace.QName("", "CORR_ST_NUM");
                private static final javax.xml.namespace.QName CORRLANDNUM$62 = 
                    new javax.xml.namespace.QName("", "CORR_LAND_NUM");
                private static final javax.xml.namespace.QName CORRSTNAME$64 = 
                    new javax.xml.namespace.QName("", "CORR_ST_NAME");
                private static final javax.xml.namespace.QName CORRSTCATG$66 = 
                    new javax.xml.namespace.QName("", "CORR_ST_CATG");
                private static final javax.xml.namespace.QName CORRSECT$68 = 
                    new javax.xml.namespace.QName("", "CORR_SECT");
                private static final javax.xml.namespace.QName CORRDISTR$70 = 
                    new javax.xml.namespace.QName("", "CORR_DISTR");
                private static final javax.xml.namespace.QName CORRAREA$72 = 
                    new javax.xml.namespace.QName("", "CORR_AREA");
                private static final javax.xml.namespace.QName CORRPOSTREGN$74 = 
                    new javax.xml.namespace.QName("", "CORR_POST_REGN");
                private static final javax.xml.namespace.QName CORRPOSTCODE$76 = 
                    new javax.xml.namespace.QName("", "CORR_POST_CODE");
                private static final javax.xml.namespace.QName CORRCOUNTRY$78 = 
                    new javax.xml.namespace.QName("", "CORR_COUNTRY");
                private static final javax.xml.namespace.QName CORRATTN$80 = 
                    new javax.xml.namespace.QName("", "CORR_ATTN");
                private static final javax.xml.namespace.QName CORRCO$82 = 
                    new javax.xml.namespace.QName("", "CORR_CO");
                private static final javax.xml.namespace.QName INSTALLFLTA$84 = 
                    new javax.xml.namespace.QName("", "INSTALL_FLT_A");
                private static final javax.xml.namespace.QName INSTALLFLOORA$86 = 
                    new javax.xml.namespace.QName("", "INSTALL_FLOOR_A");
                private static final javax.xml.namespace.QName INSTALLBLDGA$88 = 
                    new javax.xml.namespace.QName("", "INSTALL_BLDG_A");
                private static final javax.xml.namespace.QName INSTALLPOBOXA$90 = 
                    new javax.xml.namespace.QName("", "INSTALL_PO_BOX_A");
                private static final javax.xml.namespace.QName INSTALLSTNUMA$92 = 
                    new javax.xml.namespace.QName("", "INSTALL_ST_NUM_A");
                private static final javax.xml.namespace.QName INSTALLLANDNUMA$94 = 
                    new javax.xml.namespace.QName("", "INSTALL_LAND_NUM_A");
                private static final javax.xml.namespace.QName INSTALLSTNAMEA$96 = 
                    new javax.xml.namespace.QName("", "INSTALL_ST_NAME_A");
                private static final javax.xml.namespace.QName INSTALLSTCATGA$98 = 
                    new javax.xml.namespace.QName("", "INSTALL_ST_CATG_A");
                private static final javax.xml.namespace.QName INSTALLSECTA$100 = 
                    new javax.xml.namespace.QName("", "INSTALL_SECT_A");
                private static final javax.xml.namespace.QName INSTALLDISTRA$102 = 
                    new javax.xml.namespace.QName("", "INSTALL_DISTR_A");
                private static final javax.xml.namespace.QName INSTALLAREAA$104 = 
                    new javax.xml.namespace.QName("", "INSTALL_AREA_A");
                private static final javax.xml.namespace.QName INSTALLPOSTREGNA$106 = 
                    new javax.xml.namespace.QName("", "INSTALL_POST_REGN_A");
                private static final javax.xml.namespace.QName INSTALLPOSTCODEA$108 = 
                    new javax.xml.namespace.QName("", "INSTALL_POST_CODE_A");
                private static final javax.xml.namespace.QName INSTALLCOUNTRYA$110 = 
                    new javax.xml.namespace.QName("", "INSTALL_COUNTRY_A");
                private static final javax.xml.namespace.QName INSTALLFLTB$112 = 
                    new javax.xml.namespace.QName("", "INSTALL_FLT_B");
                private static final javax.xml.namespace.QName INSTALLFLOORB$114 = 
                    new javax.xml.namespace.QName("", "INSTALL_FLOOR_B");
                private static final javax.xml.namespace.QName INSTALLBLDGB$116 = 
                    new javax.xml.namespace.QName("", "INSTALL_BLDG_B");
                private static final javax.xml.namespace.QName INSTALLPOBOXB$118 = 
                    new javax.xml.namespace.QName("", "INSTALL_PO_BOX_B");
                private static final javax.xml.namespace.QName INSTALLSTNUMB$120 = 
                    new javax.xml.namespace.QName("", "INSTALL_ST_NUM_B");
                private static final javax.xml.namespace.QName INSTALLLANDNUMB$122 = 
                    new javax.xml.namespace.QName("", "INSTALL_LAND_NUM_B");
                private static final javax.xml.namespace.QName INSTALLSTNAMEB$124 = 
                    new javax.xml.namespace.QName("", "INSTALL_ST_NAME_B");
                private static final javax.xml.namespace.QName INSTALLSTCATGB$126 = 
                    new javax.xml.namespace.QName("", "INSTALL_ST_CATG_B");
                private static final javax.xml.namespace.QName INSTALLSECTB$128 = 
                    new javax.xml.namespace.QName("", "INSTALL_SECT_B");
                private static final javax.xml.namespace.QName INSTALLDISTRB$130 = 
                    new javax.xml.namespace.QName("", "INSTALL_DISTR_B");
                private static final javax.xml.namespace.QName INSTALLAREAB$132 = 
                    new javax.xml.namespace.QName("", "INSTALL_AREA_B");
                private static final javax.xml.namespace.QName INSTALLPOSTREGNB$134 = 
                    new javax.xml.namespace.QName("", "INSTALL_POST_REGN_B");
                private static final javax.xml.namespace.QName INSTALLPOSTCODEB$136 = 
                    new javax.xml.namespace.QName("", "INSTALL_POST_CODE_B");
                private static final javax.xml.namespace.QName INSTALLCOUNTRYB$138 = 
                    new javax.xml.namespace.QName("", "INSTALL_COUNTRY_B");
                private static final javax.xml.namespace.QName PROGRAMCODE$140 = 
                    new javax.xml.namespace.QName("", "PROGRAM_CODE");
                private static final javax.xml.namespace.QName DELVNAME$142 = 
                    new javax.xml.namespace.QName("", "DELV_NAME");
                private static final javax.xml.namespace.QName DELVADDR1$144 = 
                    new javax.xml.namespace.QName("", "DELV_ADDR_1");
                private static final javax.xml.namespace.QName DELVADDR2$146 = 
                    new javax.xml.namespace.QName("", "DELV_ADDR_2");
                private static final javax.xml.namespace.QName DELVADDR3$148 = 
                    new javax.xml.namespace.QName("", "DELV_ADDR_3");
                private static final javax.xml.namespace.QName STAFFTEL$150 = 
                    new javax.xml.namespace.QName("", "STAFF_TEL");
                
                
                /**
                 * Gets the "Store_ID" attribute
                 */
                public java.lang.String getStoreID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STOREID$0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Store_ID" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetStoreID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STOREID$0);
                      return target;
                    }
                }
                
                /**
                 * True if has "Store_ID" attribute
                 */
                public boolean isSetStoreID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(STOREID$0) != null;
                    }
                }
                
                /**
                 * Sets the "Store_ID" attribute
                 */
                public void setStoreID(java.lang.String storeID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STOREID$0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(STOREID$0);
                      }
                      target.setStringValue(storeID);
                    }
                }
                
                /**
                 * Sets (as xml) the "Store_ID" attribute
                 */
                public void xsetStoreID(org.apache.xmlbeans.XmlString storeID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STOREID$0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(STOREID$0);
                      }
                      target.set(storeID);
                    }
                }
                
                /**
                 * Unsets the "Store_ID" attribute
                 */
                public void unsetStoreID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(STOREID$0);
                    }
                }
                
                /**
                 * Gets the "Register_ID" attribute
                 */
                public java.lang.String getRegisterID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(REGISTERID$2);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Register_ID" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetRegisterID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(REGISTERID$2);
                      return target;
                    }
                }
                
                /**
                 * True if has "Register_ID" attribute
                 */
                public boolean isSetRegisterID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(REGISTERID$2) != null;
                    }
                }
                
                /**
                 * Sets the "Register_ID" attribute
                 */
                public void setRegisterID(java.lang.String registerID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(REGISTERID$2);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(REGISTERID$2);
                      }
                      target.setStringValue(registerID);
                    }
                }
                
                /**
                 * Sets (as xml) the "Register_ID" attribute
                 */
                public void xsetRegisterID(org.apache.xmlbeans.XmlString registerID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(REGISTERID$2);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(REGISTERID$2);
                      }
                      target.set(registerID);
                    }
                }
                
                /**
                 * Unsets the "Register_ID" attribute
                 */
                public void unsetRegisterID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(REGISTERID$2);
                    }
                }
                
                /**
                 * Gets the "Trans_Num" attribute
                 */
                public java.lang.String getTransNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TRANSNUM$4);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Trans_Num" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetTransNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TRANSNUM$4);
                      return target;
                    }
                }
                
                /**
                 * True if has "Trans_Num" attribute
                 */
                public boolean isSetTransNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(TRANSNUM$4) != null;
                    }
                }
                
                /**
                 * Sets the "Trans_Num" attribute
                 */
                public void setTransNum(java.lang.String transNum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TRANSNUM$4);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TRANSNUM$4);
                      }
                      target.setStringValue(transNum);
                    }
                }
                
                /**
                 * Sets (as xml) the "Trans_Num" attribute
                 */
                public void xsetTransNum(org.apache.xmlbeans.XmlString transNum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TRANSNUM$4);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(TRANSNUM$4);
                      }
                      target.set(transNum);
                    }
                }
                
                /**
                 * Unsets the "Trans_Num" attribute
                 */
                public void unsetTransNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(TRANSNUM$4);
                    }
                }
                
                /**
                 * Gets the "Bus_Date" attribute
                 */
                public java.lang.String getBusDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(BUSDATE$6);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Bus_Date" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetBusDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(BUSDATE$6);
                      return target;
                    }
                }
                
                /**
                 * True if has "Bus_Date" attribute
                 */
                public boolean isSetBusDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(BUSDATE$6) != null;
                    }
                }
                
                /**
                 * Sets the "Bus_Date" attribute
                 */
                public void setBusDate(java.lang.String busDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(BUSDATE$6);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(BUSDATE$6);
                      }
                      target.setStringValue(busDate);
                    }
                }
                
                /**
                 * Sets (as xml) the "Bus_Date" attribute
                 */
                public void xsetBusDate(org.apache.xmlbeans.XmlString busDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(BUSDATE$6);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(BUSDATE$6);
                      }
                      target.set(busDate);
                    }
                }
                
                /**
                 * Unsets the "Bus_Date" attribute
                 */
                public void unsetBusDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(BUSDATE$6);
                    }
                }
                
                /**
                 * Gets the "Txn_Date" attribute
                 */
                public java.lang.String getTxnDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TXNDATE$8);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Txn_Date" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetTxnDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TXNDATE$8);
                      return target;
                    }
                }
                
                /**
                 * True if has "Txn_Date" attribute
                 */
                public boolean isSetTxnDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(TXNDATE$8) != null;
                    }
                }
                
                /**
                 * Sets the "Txn_Date" attribute
                 */
                public void setTxnDate(java.lang.String txnDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TXNDATE$8);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TXNDATE$8);
                      }
                      target.setStringValue(txnDate);
                    }
                }
                
                /**
                 * Sets (as xml) the "Txn_Date" attribute
                 */
                public void xsetTxnDate(org.apache.xmlbeans.XmlString txnDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TXNDATE$8);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(TXNDATE$8);
                      }
                      target.set(txnDate);
                    }
                }
                
                /**
                 * Unsets the "Txn_Date" attribute
                 */
                public void unsetTxnDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(TXNDATE$8);
                    }
                }
                
                /**
                 * Gets the "SM_Type" attribute
                 */
                public java.lang.String getSMType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SMTYPE$10);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "SM_Type" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetSMType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SMTYPE$10);
                      return target;
                    }
                }
                
                /**
                 * True if has "SM_Type" attribute
                 */
                public boolean isSetSMType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(SMTYPE$10) != null;
                    }
                }
                
                /**
                 * Sets the "SM_Type" attribute
                 */
                public void setSMType(java.lang.String smType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SMTYPE$10);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SMTYPE$10);
                      }
                      target.setStringValue(smType);
                    }
                }
                
                /**
                 * Sets (as xml) the "SM_Type" attribute
                 */
                public void xsetSMType(org.apache.xmlbeans.XmlString smType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SMTYPE$10);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SMTYPE$10);
                      }
                      target.set(smType);
                    }
                }
                
                /**
                 * Unsets the "SM_Type" attribute
                 */
                public void unsetSMType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(SMTYPE$10);
                    }
                }
                
                /**
                 * Gets the "SRV_TYPE" attribute
                 */
                public java.lang.String getSRVTYPE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SRVTYPE$12);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "SRV_TYPE" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetSRVTYPE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SRVTYPE$12);
                      return target;
                    }
                }
                
                /**
                 * True if has "SRV_TYPE" attribute
                 */
                public boolean isSetSRVTYPE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(SRVTYPE$12) != null;
                    }
                }
                
                /**
                 * Sets the "SRV_TYPE" attribute
                 */
                public void setSRVTYPE(java.lang.String srvtype)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SRVTYPE$12);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SRVTYPE$12);
                      }
                      target.setStringValue(srvtype);
                    }
                }
                
                /**
                 * Sets (as xml) the "SRV_TYPE" attribute
                 */
                public void xsetSRVTYPE(org.apache.xmlbeans.XmlString srvtype)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SRVTYPE$12);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SRVTYPE$12);
                      }
                      target.set(srvtype);
                    }
                }
                
                /**
                 * Unsets the "SRV_TYPE" attribute
                 */
                public void unsetSRVTYPE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(SRVTYPE$12);
                    }
                }
                
                /**
                 * Gets the "SRV_NUM" attribute
                 */
                public java.lang.String getSRVNUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SRVNUM$14);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "SRV_NUM" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetSRVNUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SRVNUM$14);
                      return target;
                    }
                }
                
                /**
                 * True if has "SRV_NUM" attribute
                 */
                public boolean isSetSRVNUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(SRVNUM$14) != null;
                    }
                }
                
                /**
                 * Sets the "SRV_NUM" attribute
                 */
                public void setSRVNUM(java.lang.String srvnum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SRVNUM$14);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SRVNUM$14);
                      }
                      target.setStringValue(srvnum);
                    }
                }
                
                /**
                 * Sets (as xml) the "SRV_NUM" attribute
                 */
                public void xsetSRVNUM(org.apache.xmlbeans.XmlString srvnum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SRVNUM$14);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SRVNUM$14);
                      }
                      target.set(srvnum);
                    }
                }
                
                /**
                 * Unsets the "SRV_NUM" attribute
                 */
                public void unsetSRVNUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(SRVNUM$14);
                    }
                }
                
                /**
                 * Gets the "WO_DESC" attribute
                 */
                public java.lang.String getWODESC()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(WODESC$16);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "WO_DESC" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetWODESC()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(WODESC$16);
                      return target;
                    }
                }
                
                /**
                 * True if has "WO_DESC" attribute
                 */
                public boolean isSetWODESC()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(WODESC$16) != null;
                    }
                }
                
                /**
                 * Sets the "WO_DESC" attribute
                 */
                public void setWODESC(java.lang.String wodesc)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(WODESC$16);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(WODESC$16);
                      }
                      target.setStringValue(wodesc);
                    }
                }
                
                /**
                 * Sets (as xml) the "WO_DESC" attribute
                 */
                public void xsetWODESC(org.apache.xmlbeans.XmlString wodesc)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(WODESC$16);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(WODESC$16);
                      }
                      target.set(wodesc);
                    }
                }
                
                /**
                 * Unsets the "WO_DESC" attribute
                 */
                public void unsetWODESC()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(WODESC$16);
                    }
                }
                
                /**
                 * Gets the "BIS_Date" attribute
                 */
                public java.lang.String getBISDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(BISDATE$18);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "BIS_Date" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetBISDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(BISDATE$18);
                      return target;
                    }
                }
                
                /**
                 * True if has "BIS_Date" attribute
                 */
                public boolean isSetBISDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(BISDATE$18) != null;
                    }
                }
                
                /**
                 * Sets the "BIS_Date" attribute
                 */
                public void setBISDate(java.lang.String bisDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(BISDATE$18);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(BISDATE$18);
                      }
                      target.setStringValue(bisDate);
                    }
                }
                
                /**
                 * Sets (as xml) the "BIS_Date" attribute
                 */
                public void xsetBISDate(org.apache.xmlbeans.XmlString bisDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(BISDATE$18);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(BISDATE$18);
                      }
                      target.set(bisDate);
                    }
                }
                
                /**
                 * Unsets the "BIS_Date" attribute
                 */
                public void unsetBISDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(BISDATE$18);
                    }
                }
                
                /**
                 * Gets the "INSTALL_BU" attribute
                 */
                public java.lang.String getINSTALLBU()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLBU$20);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_BU" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLBU()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLBU$20);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_BU" attribute
                 */
                public boolean isSetINSTALLBU()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLBU$20) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_BU" attribute
                 */
                public void setINSTALLBU(java.lang.String installbu)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLBU$20);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLBU$20);
                      }
                      target.setStringValue(installbu);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_BU" attribute
                 */
                public void xsetINSTALLBU(org.apache.xmlbeans.XmlString installbu)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLBU$20);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLBU$20);
                      }
                      target.set(installbu);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_BU" attribute
                 */
                public void unsetINSTALLBU()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLBU$20);
                    }
                }
                
                /**
                 * Gets the "PROD_LINE" attribute
                 */
                public java.lang.String getPRODLINE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PRODLINE$22);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "PROD_LINE" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetPRODLINE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PRODLINE$22);
                      return target;
                    }
                }
                
                /**
                 * True if has "PROD_LINE" attribute
                 */
                public boolean isSetPRODLINE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(PRODLINE$22) != null;
                    }
                }
                
                /**
                 * Sets the "PROD_LINE" attribute
                 */
                public void setPRODLINE(java.lang.String prodline)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PRODLINE$22);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PRODLINE$22);
                      }
                      target.setStringValue(prodline);
                    }
                }
                
                /**
                 * Sets (as xml) the "PROD_LINE" attribute
                 */
                public void xsetPRODLINE(org.apache.xmlbeans.XmlString prodline)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PRODLINE$22);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PRODLINE$22);
                      }
                      target.set(prodline);
                    }
                }
                
                /**
                 * Unsets the "PROD_LINE" attribute
                 */
                public void unsetPRODLINE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(PRODLINE$22);
                    }
                }
                
                /**
                 * Gets the "EXCHG_CODE" attribute
                 */
                public java.lang.String getEXCHGCODE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(EXCHGCODE$24);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "EXCHG_CODE" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetEXCHGCODE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(EXCHGCODE$24);
                      return target;
                    }
                }
                
                /**
                 * True if has "EXCHG_CODE" attribute
                 */
                public boolean isSetEXCHGCODE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(EXCHGCODE$24) != null;
                    }
                }
                
                /**
                 * Sets the "EXCHG_CODE" attribute
                 */
                public void setEXCHGCODE(java.lang.String exchgcode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(EXCHGCODE$24);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(EXCHGCODE$24);
                      }
                      target.setStringValue(exchgcode);
                    }
                }
                
                /**
                 * Sets (as xml) the "EXCHG_CODE" attribute
                 */
                public void xsetEXCHGCODE(org.apache.xmlbeans.XmlString exchgcode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(EXCHGCODE$24);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(EXCHGCODE$24);
                      }
                      target.set(exchgcode);
                    }
                }
                
                /**
                 * Unsets the "EXCHG_CODE" attribute
                 */
                public void unsetEXCHGCODE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(EXCHGCODE$24);
                    }
                }
                
                /**
                 * Gets the "CTRCT_END_DATE" attribute
                 */
                public java.lang.String getCTRCTENDDATE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CTRCTENDDATE$26);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CTRCT_END_DATE" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCTRCTENDDATE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CTRCTENDDATE$26);
                      return target;
                    }
                }
                
                /**
                 * True if has "CTRCT_END_DATE" attribute
                 */
                public boolean isSetCTRCTENDDATE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CTRCTENDDATE$26) != null;
                    }
                }
                
                /**
                 * Sets the "CTRCT_END_DATE" attribute
                 */
                public void setCTRCTENDDATE(java.lang.String ctrctenddate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CTRCTENDDATE$26);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CTRCTENDDATE$26);
                      }
                      target.setStringValue(ctrctenddate);
                    }
                }
                
                /**
                 * Sets (as xml) the "CTRCT_END_DATE" attribute
                 */
                public void xsetCTRCTENDDATE(org.apache.xmlbeans.XmlString ctrctenddate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CTRCTENDDATE$26);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CTRCTENDDATE$26);
                      }
                      target.set(ctrctenddate);
                    }
                }
                
                /**
                 * Unsets the "CTRCT_END_DATE" attribute
                 */
                public void unsetCTRCTENDDATE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CTRCTENDDATE$26);
                    }
                }
                
                /**
                 * Gets the "CUS_NUM" attribute
                 */
                public java.lang.String getCUSNUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUSNUM$28);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CUS_NUM" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUSNUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUSNUM$28);
                      return target;
                    }
                }
                
                /**
                 * True if has "CUS_NUM" attribute
                 */
                public boolean isSetCUSNUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUSNUM$28) != null;
                    }
                }
                
                /**
                 * Sets the "CUS_NUM" attribute
                 */
                public void setCUSNUM(java.lang.String cusnum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUSNUM$28);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUSNUM$28);
                      }
                      target.setStringValue(cusnum);
                    }
                }
                
                /**
                 * Sets (as xml) the "CUS_NUM" attribute
                 */
                public void xsetCUSNUM(org.apache.xmlbeans.XmlString cusnum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUSNUM$28);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUSNUM$28);
                      }
                      target.set(cusnum);
                    }
                }
                
                /**
                 * Unsets the "CUS_NUM" attribute
                 */
                public void unsetCUSNUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUSNUM$28);
                    }
                }
                
                /**
                 * Gets the "ACCT_NUM" attribute
                 */
                public java.lang.String getACCTNUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ACCTNUM$30);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "ACCT_NUM" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetACCTNUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ACCTNUM$30);
                      return target;
                    }
                }
                
                /**
                 * True if has "ACCT_NUM" attribute
                 */
                public boolean isSetACCTNUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(ACCTNUM$30) != null;
                    }
                }
                
                /**
                 * Sets the "ACCT_NUM" attribute
                 */
                public void setACCTNUM(java.lang.String acctnum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ACCTNUM$30);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ACCTNUM$30);
                      }
                      target.setStringValue(acctnum);
                    }
                }
                
                /**
                 * Sets (as xml) the "ACCT_NUM" attribute
                 */
                public void xsetACCTNUM(org.apache.xmlbeans.XmlString acctnum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ACCTNUM$30);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(ACCTNUM$30);
                      }
                      target.set(acctnum);
                    }
                }
                
                /**
                 * Unsets the "ACCT_NUM" attribute
                 */
                public void unsetACCTNUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(ACCTNUM$30);
                    }
                }
                
                /**
                 * Gets the "CUS_NAME" attribute
                 */
                public java.lang.String getCUSNAME()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUSNAME$32);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CUS_NAME" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUSNAME()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUSNAME$32);
                      return target;
                    }
                }
                
                /**
                 * True if has "CUS_NAME" attribute
                 */
                public boolean isSetCUSNAME()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUSNAME$32) != null;
                    }
                }
                
                /**
                 * Sets the "CUS_NAME" attribute
                 */
                public void setCUSNAME(java.lang.String cusname)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUSNAME$32);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUSNAME$32);
                      }
                      target.setStringValue(cusname);
                    }
                }
                
                /**
                 * Sets (as xml) the "CUS_NAME" attribute
                 */
                public void xsetCUSNAME(org.apache.xmlbeans.XmlString cusname)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUSNAME$32);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUSNAME$32);
                      }
                      target.set(cusname);
                    }
                }
                
                /**
                 * Unsets the "CUS_NAME" attribute
                 */
                public void unsetCUSNAME()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUSNAME$32);
                    }
                }
                
                /**
                 * Gets the "CUS_STATUS" attribute
                 */
                public java.lang.String getCUSSTATUS()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUSSTATUS$34);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CUS_STATUS" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUSSTATUS()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUSSTATUS$34);
                      return target;
                    }
                }
                
                /**
                 * True if has "CUS_STATUS" attribute
                 */
                public boolean isSetCUSSTATUS()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUSSTATUS$34) != null;
                    }
                }
                
                /**
                 * Sets the "CUS_STATUS" attribute
                 */
                public void setCUSSTATUS(java.lang.String cusstatus)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUSSTATUS$34);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUSSTATUS$34);
                      }
                      target.setStringValue(cusstatus);
                    }
                }
                
                /**
                 * Sets (as xml) the "CUS_STATUS" attribute
                 */
                public void xsetCUSSTATUS(org.apache.xmlbeans.XmlString cusstatus)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUSSTATUS$34);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUSSTATUS$34);
                      }
                      target.set(cusstatus);
                    }
                }
                
                /**
                 * Unsets the "CUS_STATUS" attribute
                 */
                public void unsetCUSSTATUS()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUSSTATUS$34);
                    }
                }
                
                /**
                 * Gets the "LEAS_CODE" attribute
                 */
                public java.lang.String getLEASCODE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LEASCODE$36);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "LEAS_CODE" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetLEASCODE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(LEASCODE$36);
                      return target;
                    }
                }
                
                /**
                 * True if has "LEAS_CODE" attribute
                 */
                public boolean isSetLEASCODE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(LEASCODE$36) != null;
                    }
                }
                
                /**
                 * Sets the "LEAS_CODE" attribute
                 */
                public void setLEASCODE(java.lang.String leascode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LEASCODE$36);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(LEASCODE$36);
                      }
                      target.setStringValue(leascode);
                    }
                }
                
                /**
                 * Sets (as xml) the "LEAS_CODE" attribute
                 */
                public void xsetLEASCODE(org.apache.xmlbeans.XmlString leascode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(LEASCODE$36);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(LEASCODE$36);
                      }
                      target.set(leascode);
                    }
                }
                
                /**
                 * Unsets the "LEAS_CODE" attribute
                 */
                public void unsetLEASCODE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(LEASCODE$36);
                    }
                }
                
                /**
                 * Gets the "LPS_MEMSHIP_NUM" attribute
                 */
                public java.lang.String getLPSMEMSHIPNUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LPSMEMSHIPNUM$38);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "LPS_MEMSHIP_NUM" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetLPSMEMSHIPNUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(LPSMEMSHIPNUM$38);
                      return target;
                    }
                }
                
                /**
                 * True if has "LPS_MEMSHIP_NUM" attribute
                 */
                public boolean isSetLPSMEMSHIPNUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(LPSMEMSHIPNUM$38) != null;
                    }
                }
                
                /**
                 * Sets the "LPS_MEMSHIP_NUM" attribute
                 */
                public void setLPSMEMSHIPNUM(java.lang.String lpsmemshipnum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LPSMEMSHIPNUM$38);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(LPSMEMSHIPNUM$38);
                      }
                      target.setStringValue(lpsmemshipnum);
                    }
                }
                
                /**
                 * Sets (as xml) the "LPS_MEMSHIP_NUM" attribute
                 */
                public void xsetLPSMEMSHIPNUM(org.apache.xmlbeans.XmlString lpsmemshipnum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(LPSMEMSHIPNUM$38);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(LPSMEMSHIPNUM$38);
                      }
                      target.set(lpsmemshipnum);
                    }
                }
                
                /**
                 * Unsets the "LPS_MEMSHIP_NUM" attribute
                 */
                public void unsetLPSMEMSHIPNUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(LPSMEMSHIPNUM$38);
                    }
                }
                
                /**
                 * Gets the "USER_NAME" attribute
                 */
                public java.lang.String getUSERNAME()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(USERNAME$40);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "USER_NAME" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetUSERNAME()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(USERNAME$40);
                      return target;
                    }
                }
                
                /**
                 * True if has "USER_NAME" attribute
                 */
                public boolean isSetUSERNAME()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(USERNAME$40) != null;
                    }
                }
                
                /**
                 * Sets the "USER_NAME" attribute
                 */
                public void setUSERNAME(java.lang.String username)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(USERNAME$40);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(USERNAME$40);
                      }
                      target.setStringValue(username);
                    }
                }
                
                /**
                 * Sets (as xml) the "USER_NAME" attribute
                 */
                public void xsetUSERNAME(org.apache.xmlbeans.XmlString username)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(USERNAME$40);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(USERNAME$40);
                      }
                      target.set(username);
                    }
                }
                
                /**
                 * Unsets the "USER_NAME" attribute
                 */
                public void unsetUSERNAME()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(USERNAME$40);
                    }
                }
                
                /**
                 * Gets the "CONTACT_PERSON" attribute
                 */
                public java.lang.String getCONTACTPERSON()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CONTACTPERSON$42);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CONTACT_PERSON" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCONTACTPERSON()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CONTACTPERSON$42);
                      return target;
                    }
                }
                
                /**
                 * True if has "CONTACT_PERSON" attribute
                 */
                public boolean isSetCONTACTPERSON()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CONTACTPERSON$42) != null;
                    }
                }
                
                /**
                 * Sets the "CONTACT_PERSON" attribute
                 */
                public void setCONTACTPERSON(java.lang.String contactperson)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CONTACTPERSON$42);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CONTACTPERSON$42);
                      }
                      target.setStringValue(contactperson);
                    }
                }
                
                /**
                 * Sets (as xml) the "CONTACT_PERSON" attribute
                 */
                public void xsetCONTACTPERSON(org.apache.xmlbeans.XmlString contactperson)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CONTACTPERSON$42);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CONTACTPERSON$42);
                      }
                      target.set(contactperson);
                    }
                }
                
                /**
                 * Unsets the "CONTACT_PERSON" attribute
                 */
                public void unsetCONTACTPERSON()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CONTACTPERSON$42);
                    }
                }
                
                /**
                 * Gets the "CONTACT_TEL" attribute
                 */
                public java.lang.String getCONTACTTEL()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CONTACTTEL$44);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CONTACT_TEL" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCONTACTTEL()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CONTACTTEL$44);
                      return target;
                    }
                }
                
                /**
                 * True if has "CONTACT_TEL" attribute
                 */
                public boolean isSetCONTACTTEL()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CONTACTTEL$44) != null;
                    }
                }
                
                /**
                 * Sets the "CONTACT_TEL" attribute
                 */
                public void setCONTACTTEL(java.lang.String contacttel)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CONTACTTEL$44);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CONTACTTEL$44);
                      }
                      target.setStringValue(contacttel);
                    }
                }
                
                /**
                 * Sets (as xml) the "CONTACT_TEL" attribute
                 */
                public void xsetCONTACTTEL(org.apache.xmlbeans.XmlString contacttel)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CONTACTTEL$44);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CONTACTTEL$44);
                      }
                      target.set(contacttel);
                    }
                }
                
                /**
                 * Unsets the "CONTACT_TEL" attribute
                 */
                public void unsetCONTACTTEL()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CONTACTTEL$44);
                    }
                }
                
                /**
                 * Gets the "CONTACT_FAX" attribute
                 */
                public java.lang.String getCONTACTFAX()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CONTACTFAX$46);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CONTACT_FAX" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCONTACTFAX()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CONTACTFAX$46);
                      return target;
                    }
                }
                
                /**
                 * True if has "CONTACT_FAX" attribute
                 */
                public boolean isSetCONTACTFAX()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CONTACTFAX$46) != null;
                    }
                }
                
                /**
                 * Sets the "CONTACT_FAX" attribute
                 */
                public void setCONTACTFAX(java.lang.String contactfax)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CONTACTFAX$46);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CONTACTFAX$46);
                      }
                      target.setStringValue(contactfax);
                    }
                }
                
                /**
                 * Sets (as xml) the "CONTACT_FAX" attribute
                 */
                public void xsetCONTACTFAX(org.apache.xmlbeans.XmlString contactfax)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CONTACTFAX$46);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CONTACTFAX$46);
                      }
                      target.set(contactfax);
                    }
                }
                
                /**
                 * Unsets the "CONTACT_FAX" attribute
                 */
                public void unsetCONTACTFAX()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CONTACTFAX$46);
                    }
                }
                
                /**
                 * Gets the "CUS_REFERENCE" attribute
                 */
                public java.lang.String getCUSREFERENCE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUSREFERENCE$48);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CUS_REFERENCE" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUSREFERENCE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUSREFERENCE$48);
                      return target;
                    }
                }
                
                /**
                 * True if has "CUS_REFERENCE" attribute
                 */
                public boolean isSetCUSREFERENCE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUSREFERENCE$48) != null;
                    }
                }
                
                /**
                 * Sets the "CUS_REFERENCE" attribute
                 */
                public void setCUSREFERENCE(java.lang.String cusreference)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUSREFERENCE$48);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUSREFERENCE$48);
                      }
                      target.setStringValue(cusreference);
                    }
                }
                
                /**
                 * Sets (as xml) the "CUS_REFERENCE" attribute
                 */
                public void xsetCUSREFERENCE(org.apache.xmlbeans.XmlString cusreference)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUSREFERENCE$48);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUSREFERENCE$48);
                      }
                      target.set(cusreference);
                    }
                }
                
                /**
                 * Unsets the "CUS_REFERENCE" attribute
                 */
                public void unsetCUSREFERENCE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUSREFERENCE$48);
                    }
                }
                
                /**
                 * Gets the "CUS_PO" attribute
                 */
                public java.lang.String getCUSPO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUSPO$50);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CUS_PO" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUSPO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUSPO$50);
                      return target;
                    }
                }
                
                /**
                 * True if has "CUS_PO" attribute
                 */
                public boolean isSetCUSPO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUSPO$50) != null;
                    }
                }
                
                /**
                 * Sets the "CUS_PO" attribute
                 */
                public void setCUSPO(java.lang.String cuspo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUSPO$50);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUSPO$50);
                      }
                      target.setStringValue(cuspo);
                    }
                }
                
                /**
                 * Sets (as xml) the "CUS_PO" attribute
                 */
                public void xsetCUSPO(org.apache.xmlbeans.XmlString cuspo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUSPO$50);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUSPO$50);
                      }
                      target.set(cuspo);
                    }
                }
                
                /**
                 * Unsets the "CUS_PO" attribute
                 */
                public void unsetCUSPO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUSPO$50);
                    }
                }
                
                /**
                 * Gets the "CORR_FLT" attribute
                 */
                public java.lang.String getCORRFLT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRFLT$52);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CORR_FLT" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCORRFLT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRFLT$52);
                      return target;
                    }
                }
                
                /**
                 * True if has "CORR_FLT" attribute
                 */
                public boolean isSetCORRFLT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CORRFLT$52) != null;
                    }
                }
                
                /**
                 * Sets the "CORR_FLT" attribute
                 */
                public void setCORRFLT(java.lang.String corrflt)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRFLT$52);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CORRFLT$52);
                      }
                      target.setStringValue(corrflt);
                    }
                }
                
                /**
                 * Sets (as xml) the "CORR_FLT" attribute
                 */
                public void xsetCORRFLT(org.apache.xmlbeans.XmlString corrflt)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRFLT$52);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CORRFLT$52);
                      }
                      target.set(corrflt);
                    }
                }
                
                /**
                 * Unsets the "CORR_FLT" attribute
                 */
                public void unsetCORRFLT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CORRFLT$52);
                    }
                }
                
                /**
                 * Gets the "CORR_FLOOR" attribute
                 */
                public java.lang.String getCORRFLOOR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRFLOOR$54);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CORR_FLOOR" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCORRFLOOR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRFLOOR$54);
                      return target;
                    }
                }
                
                /**
                 * True if has "CORR_FLOOR" attribute
                 */
                public boolean isSetCORRFLOOR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CORRFLOOR$54) != null;
                    }
                }
                
                /**
                 * Sets the "CORR_FLOOR" attribute
                 */
                public void setCORRFLOOR(java.lang.String corrfloor)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRFLOOR$54);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CORRFLOOR$54);
                      }
                      target.setStringValue(corrfloor);
                    }
                }
                
                /**
                 * Sets (as xml) the "CORR_FLOOR" attribute
                 */
                public void xsetCORRFLOOR(org.apache.xmlbeans.XmlString corrfloor)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRFLOOR$54);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CORRFLOOR$54);
                      }
                      target.set(corrfloor);
                    }
                }
                
                /**
                 * Unsets the "CORR_FLOOR" attribute
                 */
                public void unsetCORRFLOOR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CORRFLOOR$54);
                    }
                }
                
                /**
                 * Gets the "CORR_BLDG" attribute
                 */
                public java.lang.String getCORRBLDG()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRBLDG$56);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CORR_BLDG" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCORRBLDG()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRBLDG$56);
                      return target;
                    }
                }
                
                /**
                 * True if has "CORR_BLDG" attribute
                 */
                public boolean isSetCORRBLDG()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CORRBLDG$56) != null;
                    }
                }
                
                /**
                 * Sets the "CORR_BLDG" attribute
                 */
                public void setCORRBLDG(java.lang.String corrbldg)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRBLDG$56);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CORRBLDG$56);
                      }
                      target.setStringValue(corrbldg);
                    }
                }
                
                /**
                 * Sets (as xml) the "CORR_BLDG" attribute
                 */
                public void xsetCORRBLDG(org.apache.xmlbeans.XmlString corrbldg)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRBLDG$56);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CORRBLDG$56);
                      }
                      target.set(corrbldg);
                    }
                }
                
                /**
                 * Unsets the "CORR_BLDG" attribute
                 */
                public void unsetCORRBLDG()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CORRBLDG$56);
                    }
                }
                
                /**
                 * Gets the "CORR_PO_BOX" attribute
                 */
                public java.lang.String getCORRPOBOX()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRPOBOX$58);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CORR_PO_BOX" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCORRPOBOX()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRPOBOX$58);
                      return target;
                    }
                }
                
                /**
                 * True if has "CORR_PO_BOX" attribute
                 */
                public boolean isSetCORRPOBOX()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CORRPOBOX$58) != null;
                    }
                }
                
                /**
                 * Sets the "CORR_PO_BOX" attribute
                 */
                public void setCORRPOBOX(java.lang.String corrpobox)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRPOBOX$58);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CORRPOBOX$58);
                      }
                      target.setStringValue(corrpobox);
                    }
                }
                
                /**
                 * Sets (as xml) the "CORR_PO_BOX" attribute
                 */
                public void xsetCORRPOBOX(org.apache.xmlbeans.XmlString corrpobox)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRPOBOX$58);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CORRPOBOX$58);
                      }
                      target.set(corrpobox);
                    }
                }
                
                /**
                 * Unsets the "CORR_PO_BOX" attribute
                 */
                public void unsetCORRPOBOX()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CORRPOBOX$58);
                    }
                }
                
                /**
                 * Gets the "CORR_ST_NUM" attribute
                 */
                public java.lang.String getCORRSTNUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRSTNUM$60);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CORR_ST_NUM" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCORRSTNUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRSTNUM$60);
                      return target;
                    }
                }
                
                /**
                 * True if has "CORR_ST_NUM" attribute
                 */
                public boolean isSetCORRSTNUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CORRSTNUM$60) != null;
                    }
                }
                
                /**
                 * Sets the "CORR_ST_NUM" attribute
                 */
                public void setCORRSTNUM(java.lang.String corrstnum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRSTNUM$60);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CORRSTNUM$60);
                      }
                      target.setStringValue(corrstnum);
                    }
                }
                
                /**
                 * Sets (as xml) the "CORR_ST_NUM" attribute
                 */
                public void xsetCORRSTNUM(org.apache.xmlbeans.XmlString corrstnum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRSTNUM$60);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CORRSTNUM$60);
                      }
                      target.set(corrstnum);
                    }
                }
                
                /**
                 * Unsets the "CORR_ST_NUM" attribute
                 */
                public void unsetCORRSTNUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CORRSTNUM$60);
                    }
                }
                
                /**
                 * Gets the "CORR_LAND_NUM" attribute
                 */
                public java.lang.String getCORRLANDNUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRLANDNUM$62);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CORR_LAND_NUM" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCORRLANDNUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRLANDNUM$62);
                      return target;
                    }
                }
                
                /**
                 * True if has "CORR_LAND_NUM" attribute
                 */
                public boolean isSetCORRLANDNUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CORRLANDNUM$62) != null;
                    }
                }
                
                /**
                 * Sets the "CORR_LAND_NUM" attribute
                 */
                public void setCORRLANDNUM(java.lang.String corrlandnum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRLANDNUM$62);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CORRLANDNUM$62);
                      }
                      target.setStringValue(corrlandnum);
                    }
                }
                
                /**
                 * Sets (as xml) the "CORR_LAND_NUM" attribute
                 */
                public void xsetCORRLANDNUM(org.apache.xmlbeans.XmlString corrlandnum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRLANDNUM$62);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CORRLANDNUM$62);
                      }
                      target.set(corrlandnum);
                    }
                }
                
                /**
                 * Unsets the "CORR_LAND_NUM" attribute
                 */
                public void unsetCORRLANDNUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CORRLANDNUM$62);
                    }
                }
                
                /**
                 * Gets the "CORR_ST_NAME" attribute
                 */
                public java.lang.String getCORRSTNAME()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRSTNAME$64);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CORR_ST_NAME" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCORRSTNAME()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRSTNAME$64);
                      return target;
                    }
                }
                
                /**
                 * True if has "CORR_ST_NAME" attribute
                 */
                public boolean isSetCORRSTNAME()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CORRSTNAME$64) != null;
                    }
                }
                
                /**
                 * Sets the "CORR_ST_NAME" attribute
                 */
                public void setCORRSTNAME(java.lang.String corrstname)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRSTNAME$64);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CORRSTNAME$64);
                      }
                      target.setStringValue(corrstname);
                    }
                }
                
                /**
                 * Sets (as xml) the "CORR_ST_NAME" attribute
                 */
                public void xsetCORRSTNAME(org.apache.xmlbeans.XmlString corrstname)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRSTNAME$64);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CORRSTNAME$64);
                      }
                      target.set(corrstname);
                    }
                }
                
                /**
                 * Unsets the "CORR_ST_NAME" attribute
                 */
                public void unsetCORRSTNAME()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CORRSTNAME$64);
                    }
                }
                
                /**
                 * Gets the "CORR_ST_CATG" attribute
                 */
                public java.lang.String getCORRSTCATG()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRSTCATG$66);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CORR_ST_CATG" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCORRSTCATG()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRSTCATG$66);
                      return target;
                    }
                }
                
                /**
                 * True if has "CORR_ST_CATG" attribute
                 */
                public boolean isSetCORRSTCATG()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CORRSTCATG$66) != null;
                    }
                }
                
                /**
                 * Sets the "CORR_ST_CATG" attribute
                 */
                public void setCORRSTCATG(java.lang.String corrstcatg)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRSTCATG$66);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CORRSTCATG$66);
                      }
                      target.setStringValue(corrstcatg);
                    }
                }
                
                /**
                 * Sets (as xml) the "CORR_ST_CATG" attribute
                 */
                public void xsetCORRSTCATG(org.apache.xmlbeans.XmlString corrstcatg)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRSTCATG$66);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CORRSTCATG$66);
                      }
                      target.set(corrstcatg);
                    }
                }
                
                /**
                 * Unsets the "CORR_ST_CATG" attribute
                 */
                public void unsetCORRSTCATG()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CORRSTCATG$66);
                    }
                }
                
                /**
                 * Gets the "CORR_SECT" attribute
                 */
                public java.lang.String getCORRSECT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRSECT$68);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CORR_SECT" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCORRSECT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRSECT$68);
                      return target;
                    }
                }
                
                /**
                 * True if has "CORR_SECT" attribute
                 */
                public boolean isSetCORRSECT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CORRSECT$68) != null;
                    }
                }
                
                /**
                 * Sets the "CORR_SECT" attribute
                 */
                public void setCORRSECT(java.lang.String corrsect)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRSECT$68);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CORRSECT$68);
                      }
                      target.setStringValue(corrsect);
                    }
                }
                
                /**
                 * Sets (as xml) the "CORR_SECT" attribute
                 */
                public void xsetCORRSECT(org.apache.xmlbeans.XmlString corrsect)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRSECT$68);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CORRSECT$68);
                      }
                      target.set(corrsect);
                    }
                }
                
                /**
                 * Unsets the "CORR_SECT" attribute
                 */
                public void unsetCORRSECT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CORRSECT$68);
                    }
                }
                
                /**
                 * Gets the "CORR_DISTR" attribute
                 */
                public java.lang.String getCORRDISTR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRDISTR$70);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CORR_DISTR" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCORRDISTR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRDISTR$70);
                      return target;
                    }
                }
                
                /**
                 * True if has "CORR_DISTR" attribute
                 */
                public boolean isSetCORRDISTR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CORRDISTR$70) != null;
                    }
                }
                
                /**
                 * Sets the "CORR_DISTR" attribute
                 */
                public void setCORRDISTR(java.lang.String corrdistr)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRDISTR$70);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CORRDISTR$70);
                      }
                      target.setStringValue(corrdistr);
                    }
                }
                
                /**
                 * Sets (as xml) the "CORR_DISTR" attribute
                 */
                public void xsetCORRDISTR(org.apache.xmlbeans.XmlString corrdistr)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRDISTR$70);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CORRDISTR$70);
                      }
                      target.set(corrdistr);
                    }
                }
                
                /**
                 * Unsets the "CORR_DISTR" attribute
                 */
                public void unsetCORRDISTR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CORRDISTR$70);
                    }
                }
                
                /**
                 * Gets the "CORR_AREA" attribute
                 */
                public java.lang.String getCORRAREA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRAREA$72);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CORR_AREA" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCORRAREA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRAREA$72);
                      return target;
                    }
                }
                
                /**
                 * True if has "CORR_AREA" attribute
                 */
                public boolean isSetCORRAREA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CORRAREA$72) != null;
                    }
                }
                
                /**
                 * Sets the "CORR_AREA" attribute
                 */
                public void setCORRAREA(java.lang.String corrarea)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRAREA$72);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CORRAREA$72);
                      }
                      target.setStringValue(corrarea);
                    }
                }
                
                /**
                 * Sets (as xml) the "CORR_AREA" attribute
                 */
                public void xsetCORRAREA(org.apache.xmlbeans.XmlString corrarea)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRAREA$72);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CORRAREA$72);
                      }
                      target.set(corrarea);
                    }
                }
                
                /**
                 * Unsets the "CORR_AREA" attribute
                 */
                public void unsetCORRAREA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CORRAREA$72);
                    }
                }
                
                /**
                 * Gets the "CORR_POST_REGN" attribute
                 */
                public java.lang.String getCORRPOSTREGN()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRPOSTREGN$74);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CORR_POST_REGN" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCORRPOSTREGN()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRPOSTREGN$74);
                      return target;
                    }
                }
                
                /**
                 * True if has "CORR_POST_REGN" attribute
                 */
                public boolean isSetCORRPOSTREGN()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CORRPOSTREGN$74) != null;
                    }
                }
                
                /**
                 * Sets the "CORR_POST_REGN" attribute
                 */
                public void setCORRPOSTREGN(java.lang.String corrpostregn)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRPOSTREGN$74);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CORRPOSTREGN$74);
                      }
                      target.setStringValue(corrpostregn);
                    }
                }
                
                /**
                 * Sets (as xml) the "CORR_POST_REGN" attribute
                 */
                public void xsetCORRPOSTREGN(org.apache.xmlbeans.XmlString corrpostregn)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRPOSTREGN$74);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CORRPOSTREGN$74);
                      }
                      target.set(corrpostregn);
                    }
                }
                
                /**
                 * Unsets the "CORR_POST_REGN" attribute
                 */
                public void unsetCORRPOSTREGN()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CORRPOSTREGN$74);
                    }
                }
                
                /**
                 * Gets the "CORR_POST_CODE" attribute
                 */
                public java.lang.String getCORRPOSTCODE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRPOSTCODE$76);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CORR_POST_CODE" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCORRPOSTCODE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRPOSTCODE$76);
                      return target;
                    }
                }
                
                /**
                 * True if has "CORR_POST_CODE" attribute
                 */
                public boolean isSetCORRPOSTCODE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CORRPOSTCODE$76) != null;
                    }
                }
                
                /**
                 * Sets the "CORR_POST_CODE" attribute
                 */
                public void setCORRPOSTCODE(java.lang.String corrpostcode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRPOSTCODE$76);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CORRPOSTCODE$76);
                      }
                      target.setStringValue(corrpostcode);
                    }
                }
                
                /**
                 * Sets (as xml) the "CORR_POST_CODE" attribute
                 */
                public void xsetCORRPOSTCODE(org.apache.xmlbeans.XmlString corrpostcode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRPOSTCODE$76);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CORRPOSTCODE$76);
                      }
                      target.set(corrpostcode);
                    }
                }
                
                /**
                 * Unsets the "CORR_POST_CODE" attribute
                 */
                public void unsetCORRPOSTCODE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CORRPOSTCODE$76);
                    }
                }
                
                /**
                 * Gets the "CORR_COUNTRY" attribute
                 */
                public java.lang.String getCORRCOUNTRY()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRCOUNTRY$78);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CORR_COUNTRY" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCORRCOUNTRY()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRCOUNTRY$78);
                      return target;
                    }
                }
                
                /**
                 * True if has "CORR_COUNTRY" attribute
                 */
                public boolean isSetCORRCOUNTRY()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CORRCOUNTRY$78) != null;
                    }
                }
                
                /**
                 * Sets the "CORR_COUNTRY" attribute
                 */
                public void setCORRCOUNTRY(java.lang.String corrcountry)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRCOUNTRY$78);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CORRCOUNTRY$78);
                      }
                      target.setStringValue(corrcountry);
                    }
                }
                
                /**
                 * Sets (as xml) the "CORR_COUNTRY" attribute
                 */
                public void xsetCORRCOUNTRY(org.apache.xmlbeans.XmlString corrcountry)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRCOUNTRY$78);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CORRCOUNTRY$78);
                      }
                      target.set(corrcountry);
                    }
                }
                
                /**
                 * Unsets the "CORR_COUNTRY" attribute
                 */
                public void unsetCORRCOUNTRY()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CORRCOUNTRY$78);
                    }
                }
                
                /**
                 * Gets the "CORR_ATTN" attribute
                 */
                public java.lang.String getCORRATTN()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRATTN$80);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CORR_ATTN" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCORRATTN()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRATTN$80);
                      return target;
                    }
                }
                
                /**
                 * True if has "CORR_ATTN" attribute
                 */
                public boolean isSetCORRATTN()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CORRATTN$80) != null;
                    }
                }
                
                /**
                 * Sets the "CORR_ATTN" attribute
                 */
                public void setCORRATTN(java.lang.String corrattn)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRATTN$80);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CORRATTN$80);
                      }
                      target.setStringValue(corrattn);
                    }
                }
                
                /**
                 * Sets (as xml) the "CORR_ATTN" attribute
                 */
                public void xsetCORRATTN(org.apache.xmlbeans.XmlString corrattn)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRATTN$80);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CORRATTN$80);
                      }
                      target.set(corrattn);
                    }
                }
                
                /**
                 * Unsets the "CORR_ATTN" attribute
                 */
                public void unsetCORRATTN()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CORRATTN$80);
                    }
                }
                
                /**
                 * Gets the "CORR_CO" attribute
                 */
                public java.lang.String getCORRCO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRCO$82);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CORR_CO" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCORRCO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRCO$82);
                      return target;
                    }
                }
                
                /**
                 * True if has "CORR_CO" attribute
                 */
                public boolean isSetCORRCO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CORRCO$82) != null;
                    }
                }
                
                /**
                 * Sets the "CORR_CO" attribute
                 */
                public void setCORRCO(java.lang.String corrco)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CORRCO$82);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CORRCO$82);
                      }
                      target.setStringValue(corrco);
                    }
                }
                
                /**
                 * Sets (as xml) the "CORR_CO" attribute
                 */
                public void xsetCORRCO(org.apache.xmlbeans.XmlString corrco)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CORRCO$82);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CORRCO$82);
                      }
                      target.set(corrco);
                    }
                }
                
                /**
                 * Unsets the "CORR_CO" attribute
                 */
                public void unsetCORRCO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CORRCO$82);
                    }
                }
                
                /**
                 * Gets the "INSTALL_FLT_A" attribute
                 */
                public java.lang.String getINSTALLFLTA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLFLTA$84);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_FLT_A" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLFLTA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLFLTA$84);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_FLT_A" attribute
                 */
                public boolean isSetINSTALLFLTA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLFLTA$84) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_FLT_A" attribute
                 */
                public void setINSTALLFLTA(java.lang.String installflta)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLFLTA$84);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLFLTA$84);
                      }
                      target.setStringValue(installflta);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_FLT_A" attribute
                 */
                public void xsetINSTALLFLTA(org.apache.xmlbeans.XmlString installflta)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLFLTA$84);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLFLTA$84);
                      }
                      target.set(installflta);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_FLT_A" attribute
                 */
                public void unsetINSTALLFLTA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLFLTA$84);
                    }
                }
                
                /**
                 * Gets the "INSTALL_FLOOR_A" attribute
                 */
                public java.lang.String getINSTALLFLOORA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLFLOORA$86);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_FLOOR_A" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLFLOORA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLFLOORA$86);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_FLOOR_A" attribute
                 */
                public boolean isSetINSTALLFLOORA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLFLOORA$86) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_FLOOR_A" attribute
                 */
                public void setINSTALLFLOORA(java.lang.String installfloora)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLFLOORA$86);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLFLOORA$86);
                      }
                      target.setStringValue(installfloora);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_FLOOR_A" attribute
                 */
                public void xsetINSTALLFLOORA(org.apache.xmlbeans.XmlString installfloora)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLFLOORA$86);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLFLOORA$86);
                      }
                      target.set(installfloora);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_FLOOR_A" attribute
                 */
                public void unsetINSTALLFLOORA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLFLOORA$86);
                    }
                }
                
                /**
                 * Gets the "INSTALL_BLDG_A" attribute
                 */
                public java.lang.String getINSTALLBLDGA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLBLDGA$88);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_BLDG_A" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLBLDGA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLBLDGA$88);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_BLDG_A" attribute
                 */
                public boolean isSetINSTALLBLDGA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLBLDGA$88) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_BLDG_A" attribute
                 */
                public void setINSTALLBLDGA(java.lang.String installbldga)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLBLDGA$88);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLBLDGA$88);
                      }
                      target.setStringValue(installbldga);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_BLDG_A" attribute
                 */
                public void xsetINSTALLBLDGA(org.apache.xmlbeans.XmlString installbldga)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLBLDGA$88);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLBLDGA$88);
                      }
                      target.set(installbldga);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_BLDG_A" attribute
                 */
                public void unsetINSTALLBLDGA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLBLDGA$88);
                    }
                }
                
                /**
                 * Gets the "INSTALL_PO_BOX_A" attribute
                 */
                public java.lang.String getINSTALLPOBOXA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLPOBOXA$90);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_PO_BOX_A" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLPOBOXA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLPOBOXA$90);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_PO_BOX_A" attribute
                 */
                public boolean isSetINSTALLPOBOXA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLPOBOXA$90) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_PO_BOX_A" attribute
                 */
                public void setINSTALLPOBOXA(java.lang.String installpoboxa)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLPOBOXA$90);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLPOBOXA$90);
                      }
                      target.setStringValue(installpoboxa);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_PO_BOX_A" attribute
                 */
                public void xsetINSTALLPOBOXA(org.apache.xmlbeans.XmlString installpoboxa)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLPOBOXA$90);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLPOBOXA$90);
                      }
                      target.set(installpoboxa);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_PO_BOX_A" attribute
                 */
                public void unsetINSTALLPOBOXA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLPOBOXA$90);
                    }
                }
                
                /**
                 * Gets the "INSTALL_ST_NUM_A" attribute
                 */
                public java.lang.String getINSTALLSTNUMA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLSTNUMA$92);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_ST_NUM_A" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLSTNUMA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLSTNUMA$92);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_ST_NUM_A" attribute
                 */
                public boolean isSetINSTALLSTNUMA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLSTNUMA$92) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_ST_NUM_A" attribute
                 */
                public void setINSTALLSTNUMA(java.lang.String installstnuma)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLSTNUMA$92);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLSTNUMA$92);
                      }
                      target.setStringValue(installstnuma);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_ST_NUM_A" attribute
                 */
                public void xsetINSTALLSTNUMA(org.apache.xmlbeans.XmlString installstnuma)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLSTNUMA$92);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLSTNUMA$92);
                      }
                      target.set(installstnuma);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_ST_NUM_A" attribute
                 */
                public void unsetINSTALLSTNUMA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLSTNUMA$92);
                    }
                }
                
                /**
                 * Gets the "INSTALL_LAND_NUM_A" attribute
                 */
                public java.lang.String getINSTALLLANDNUMA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLLANDNUMA$94);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_LAND_NUM_A" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLLANDNUMA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLLANDNUMA$94);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_LAND_NUM_A" attribute
                 */
                public boolean isSetINSTALLLANDNUMA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLLANDNUMA$94) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_LAND_NUM_A" attribute
                 */
                public void setINSTALLLANDNUMA(java.lang.String installlandnuma)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLLANDNUMA$94);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLLANDNUMA$94);
                      }
                      target.setStringValue(installlandnuma);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_LAND_NUM_A" attribute
                 */
                public void xsetINSTALLLANDNUMA(org.apache.xmlbeans.XmlString installlandnuma)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLLANDNUMA$94);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLLANDNUMA$94);
                      }
                      target.set(installlandnuma);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_LAND_NUM_A" attribute
                 */
                public void unsetINSTALLLANDNUMA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLLANDNUMA$94);
                    }
                }
                
                /**
                 * Gets the "INSTALL_ST_NAME_A" attribute
                 */
                public java.lang.String getINSTALLSTNAMEA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLSTNAMEA$96);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_ST_NAME_A" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLSTNAMEA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLSTNAMEA$96);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_ST_NAME_A" attribute
                 */
                public boolean isSetINSTALLSTNAMEA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLSTNAMEA$96) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_ST_NAME_A" attribute
                 */
                public void setINSTALLSTNAMEA(java.lang.String installstnamea)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLSTNAMEA$96);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLSTNAMEA$96);
                      }
                      target.setStringValue(installstnamea);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_ST_NAME_A" attribute
                 */
                public void xsetINSTALLSTNAMEA(org.apache.xmlbeans.XmlString installstnamea)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLSTNAMEA$96);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLSTNAMEA$96);
                      }
                      target.set(installstnamea);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_ST_NAME_A" attribute
                 */
                public void unsetINSTALLSTNAMEA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLSTNAMEA$96);
                    }
                }
                
                /**
                 * Gets the "INSTALL_ST_CATG_A" attribute
                 */
                public java.lang.String getINSTALLSTCATGA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLSTCATGA$98);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_ST_CATG_A" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLSTCATGA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLSTCATGA$98);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_ST_CATG_A" attribute
                 */
                public boolean isSetINSTALLSTCATGA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLSTCATGA$98) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_ST_CATG_A" attribute
                 */
                public void setINSTALLSTCATGA(java.lang.String installstcatga)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLSTCATGA$98);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLSTCATGA$98);
                      }
                      target.setStringValue(installstcatga);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_ST_CATG_A" attribute
                 */
                public void xsetINSTALLSTCATGA(org.apache.xmlbeans.XmlString installstcatga)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLSTCATGA$98);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLSTCATGA$98);
                      }
                      target.set(installstcatga);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_ST_CATG_A" attribute
                 */
                public void unsetINSTALLSTCATGA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLSTCATGA$98);
                    }
                }
                
                /**
                 * Gets the "INSTALL_SECT_A" attribute
                 */
                public java.lang.String getINSTALLSECTA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLSECTA$100);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_SECT_A" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLSECTA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLSECTA$100);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_SECT_A" attribute
                 */
                public boolean isSetINSTALLSECTA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLSECTA$100) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_SECT_A" attribute
                 */
                public void setINSTALLSECTA(java.lang.String installsecta)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLSECTA$100);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLSECTA$100);
                      }
                      target.setStringValue(installsecta);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_SECT_A" attribute
                 */
                public void xsetINSTALLSECTA(org.apache.xmlbeans.XmlString installsecta)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLSECTA$100);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLSECTA$100);
                      }
                      target.set(installsecta);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_SECT_A" attribute
                 */
                public void unsetINSTALLSECTA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLSECTA$100);
                    }
                }
                
                /**
                 * Gets the "INSTALL_DISTR_A" attribute
                 */
                public java.lang.String getINSTALLDISTRA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLDISTRA$102);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_DISTR_A" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLDISTRA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLDISTRA$102);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_DISTR_A" attribute
                 */
                public boolean isSetINSTALLDISTRA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLDISTRA$102) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_DISTR_A" attribute
                 */
                public void setINSTALLDISTRA(java.lang.String installdistra)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLDISTRA$102);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLDISTRA$102);
                      }
                      target.setStringValue(installdistra);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_DISTR_A" attribute
                 */
                public void xsetINSTALLDISTRA(org.apache.xmlbeans.XmlString installdistra)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLDISTRA$102);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLDISTRA$102);
                      }
                      target.set(installdistra);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_DISTR_A" attribute
                 */
                public void unsetINSTALLDISTRA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLDISTRA$102);
                    }
                }
                
                /**
                 * Gets the "INSTALL_AREA_A" attribute
                 */
                public java.lang.String getINSTALLAREAA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLAREAA$104);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_AREA_A" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLAREAA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLAREAA$104);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_AREA_A" attribute
                 */
                public boolean isSetINSTALLAREAA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLAREAA$104) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_AREA_A" attribute
                 */
                public void setINSTALLAREAA(java.lang.String installareaa)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLAREAA$104);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLAREAA$104);
                      }
                      target.setStringValue(installareaa);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_AREA_A" attribute
                 */
                public void xsetINSTALLAREAA(org.apache.xmlbeans.XmlString installareaa)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLAREAA$104);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLAREAA$104);
                      }
                      target.set(installareaa);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_AREA_A" attribute
                 */
                public void unsetINSTALLAREAA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLAREAA$104);
                    }
                }
                
                /**
                 * Gets the "INSTALL_POST_REGN_A" attribute
                 */
                public java.lang.String getINSTALLPOSTREGNA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLPOSTREGNA$106);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_POST_REGN_A" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLPOSTREGNA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLPOSTREGNA$106);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_POST_REGN_A" attribute
                 */
                public boolean isSetINSTALLPOSTREGNA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLPOSTREGNA$106) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_POST_REGN_A" attribute
                 */
                public void setINSTALLPOSTREGNA(java.lang.String installpostregna)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLPOSTREGNA$106);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLPOSTREGNA$106);
                      }
                      target.setStringValue(installpostregna);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_POST_REGN_A" attribute
                 */
                public void xsetINSTALLPOSTREGNA(org.apache.xmlbeans.XmlString installpostregna)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLPOSTREGNA$106);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLPOSTREGNA$106);
                      }
                      target.set(installpostregna);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_POST_REGN_A" attribute
                 */
                public void unsetINSTALLPOSTREGNA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLPOSTREGNA$106);
                    }
                }
                
                /**
                 * Gets the "INSTALL_POST_CODE_A" attribute
                 */
                public java.lang.String getINSTALLPOSTCODEA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLPOSTCODEA$108);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_POST_CODE_A" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLPOSTCODEA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLPOSTCODEA$108);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_POST_CODE_A" attribute
                 */
                public boolean isSetINSTALLPOSTCODEA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLPOSTCODEA$108) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_POST_CODE_A" attribute
                 */
                public void setINSTALLPOSTCODEA(java.lang.String installpostcodea)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLPOSTCODEA$108);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLPOSTCODEA$108);
                      }
                      target.setStringValue(installpostcodea);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_POST_CODE_A" attribute
                 */
                public void xsetINSTALLPOSTCODEA(org.apache.xmlbeans.XmlString installpostcodea)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLPOSTCODEA$108);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLPOSTCODEA$108);
                      }
                      target.set(installpostcodea);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_POST_CODE_A" attribute
                 */
                public void unsetINSTALLPOSTCODEA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLPOSTCODEA$108);
                    }
                }
                
                /**
                 * Gets the "INSTALL_COUNTRY_A" attribute
                 */
                public java.lang.String getINSTALLCOUNTRYA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLCOUNTRYA$110);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_COUNTRY_A" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLCOUNTRYA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLCOUNTRYA$110);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_COUNTRY_A" attribute
                 */
                public boolean isSetINSTALLCOUNTRYA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLCOUNTRYA$110) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_COUNTRY_A" attribute
                 */
                public void setINSTALLCOUNTRYA(java.lang.String installcountrya)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLCOUNTRYA$110);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLCOUNTRYA$110);
                      }
                      target.setStringValue(installcountrya);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_COUNTRY_A" attribute
                 */
                public void xsetINSTALLCOUNTRYA(org.apache.xmlbeans.XmlString installcountrya)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLCOUNTRYA$110);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLCOUNTRYA$110);
                      }
                      target.set(installcountrya);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_COUNTRY_A" attribute
                 */
                public void unsetINSTALLCOUNTRYA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLCOUNTRYA$110);
                    }
                }
                
                /**
                 * Gets the "INSTALL_FLT_B" attribute
                 */
                public java.lang.String getINSTALLFLTB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLFLTB$112);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_FLT_B" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLFLTB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLFLTB$112);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_FLT_B" attribute
                 */
                public boolean isSetINSTALLFLTB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLFLTB$112) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_FLT_B" attribute
                 */
                public void setINSTALLFLTB(java.lang.String installfltb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLFLTB$112);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLFLTB$112);
                      }
                      target.setStringValue(installfltb);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_FLT_B" attribute
                 */
                public void xsetINSTALLFLTB(org.apache.xmlbeans.XmlString installfltb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLFLTB$112);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLFLTB$112);
                      }
                      target.set(installfltb);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_FLT_B" attribute
                 */
                public void unsetINSTALLFLTB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLFLTB$112);
                    }
                }
                
                /**
                 * Gets the "INSTALL_FLOOR_B" attribute
                 */
                public java.lang.String getINSTALLFLOORB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLFLOORB$114);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_FLOOR_B" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLFLOORB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLFLOORB$114);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_FLOOR_B" attribute
                 */
                public boolean isSetINSTALLFLOORB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLFLOORB$114) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_FLOOR_B" attribute
                 */
                public void setINSTALLFLOORB(java.lang.String installfloorb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLFLOORB$114);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLFLOORB$114);
                      }
                      target.setStringValue(installfloorb);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_FLOOR_B" attribute
                 */
                public void xsetINSTALLFLOORB(org.apache.xmlbeans.XmlString installfloorb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLFLOORB$114);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLFLOORB$114);
                      }
                      target.set(installfloorb);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_FLOOR_B" attribute
                 */
                public void unsetINSTALLFLOORB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLFLOORB$114);
                    }
                }
                
                /**
                 * Gets the "INSTALL_BLDG_B" attribute
                 */
                public java.lang.String getINSTALLBLDGB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLBLDGB$116);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_BLDG_B" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLBLDGB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLBLDGB$116);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_BLDG_B" attribute
                 */
                public boolean isSetINSTALLBLDGB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLBLDGB$116) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_BLDG_B" attribute
                 */
                public void setINSTALLBLDGB(java.lang.String installbldgb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLBLDGB$116);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLBLDGB$116);
                      }
                      target.setStringValue(installbldgb);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_BLDG_B" attribute
                 */
                public void xsetINSTALLBLDGB(org.apache.xmlbeans.XmlString installbldgb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLBLDGB$116);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLBLDGB$116);
                      }
                      target.set(installbldgb);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_BLDG_B" attribute
                 */
                public void unsetINSTALLBLDGB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLBLDGB$116);
                    }
                }
                
                /**
                 * Gets the "INSTALL_PO_BOX_B" attribute
                 */
                public java.lang.String getINSTALLPOBOXB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLPOBOXB$118);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_PO_BOX_B" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLPOBOXB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLPOBOXB$118);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_PO_BOX_B" attribute
                 */
                public boolean isSetINSTALLPOBOXB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLPOBOXB$118) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_PO_BOX_B" attribute
                 */
                public void setINSTALLPOBOXB(java.lang.String installpoboxb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLPOBOXB$118);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLPOBOXB$118);
                      }
                      target.setStringValue(installpoboxb);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_PO_BOX_B" attribute
                 */
                public void xsetINSTALLPOBOXB(org.apache.xmlbeans.XmlString installpoboxb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLPOBOXB$118);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLPOBOXB$118);
                      }
                      target.set(installpoboxb);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_PO_BOX_B" attribute
                 */
                public void unsetINSTALLPOBOXB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLPOBOXB$118);
                    }
                }
                
                /**
                 * Gets the "INSTALL_ST_NUM_B" attribute
                 */
                public java.lang.String getINSTALLSTNUMB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLSTNUMB$120);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_ST_NUM_B" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLSTNUMB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLSTNUMB$120);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_ST_NUM_B" attribute
                 */
                public boolean isSetINSTALLSTNUMB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLSTNUMB$120) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_ST_NUM_B" attribute
                 */
                public void setINSTALLSTNUMB(java.lang.String installstnumb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLSTNUMB$120);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLSTNUMB$120);
                      }
                      target.setStringValue(installstnumb);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_ST_NUM_B" attribute
                 */
                public void xsetINSTALLSTNUMB(org.apache.xmlbeans.XmlString installstnumb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLSTNUMB$120);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLSTNUMB$120);
                      }
                      target.set(installstnumb);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_ST_NUM_B" attribute
                 */
                public void unsetINSTALLSTNUMB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLSTNUMB$120);
                    }
                }
                
                /**
                 * Gets the "INSTALL_LAND_NUM_B" attribute
                 */
                public java.lang.String getINSTALLLANDNUMB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLLANDNUMB$122);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_LAND_NUM_B" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLLANDNUMB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLLANDNUMB$122);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_LAND_NUM_B" attribute
                 */
                public boolean isSetINSTALLLANDNUMB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLLANDNUMB$122) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_LAND_NUM_B" attribute
                 */
                public void setINSTALLLANDNUMB(java.lang.String installlandnumb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLLANDNUMB$122);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLLANDNUMB$122);
                      }
                      target.setStringValue(installlandnumb);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_LAND_NUM_B" attribute
                 */
                public void xsetINSTALLLANDNUMB(org.apache.xmlbeans.XmlString installlandnumb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLLANDNUMB$122);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLLANDNUMB$122);
                      }
                      target.set(installlandnumb);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_LAND_NUM_B" attribute
                 */
                public void unsetINSTALLLANDNUMB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLLANDNUMB$122);
                    }
                }
                
                /**
                 * Gets the "INSTALL_ST_NAME_B" attribute
                 */
                public java.lang.String getINSTALLSTNAMEB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLSTNAMEB$124);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_ST_NAME_B" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLSTNAMEB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLSTNAMEB$124);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_ST_NAME_B" attribute
                 */
                public boolean isSetINSTALLSTNAMEB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLSTNAMEB$124) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_ST_NAME_B" attribute
                 */
                public void setINSTALLSTNAMEB(java.lang.String installstnameb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLSTNAMEB$124);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLSTNAMEB$124);
                      }
                      target.setStringValue(installstnameb);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_ST_NAME_B" attribute
                 */
                public void xsetINSTALLSTNAMEB(org.apache.xmlbeans.XmlString installstnameb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLSTNAMEB$124);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLSTNAMEB$124);
                      }
                      target.set(installstnameb);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_ST_NAME_B" attribute
                 */
                public void unsetINSTALLSTNAMEB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLSTNAMEB$124);
                    }
                }
                
                /**
                 * Gets the "INSTALL_ST_CATG_B" attribute
                 */
                public java.lang.String getINSTALLSTCATGB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLSTCATGB$126);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_ST_CATG_B" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLSTCATGB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLSTCATGB$126);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_ST_CATG_B" attribute
                 */
                public boolean isSetINSTALLSTCATGB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLSTCATGB$126) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_ST_CATG_B" attribute
                 */
                public void setINSTALLSTCATGB(java.lang.String installstcatgb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLSTCATGB$126);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLSTCATGB$126);
                      }
                      target.setStringValue(installstcatgb);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_ST_CATG_B" attribute
                 */
                public void xsetINSTALLSTCATGB(org.apache.xmlbeans.XmlString installstcatgb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLSTCATGB$126);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLSTCATGB$126);
                      }
                      target.set(installstcatgb);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_ST_CATG_B" attribute
                 */
                public void unsetINSTALLSTCATGB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLSTCATGB$126);
                    }
                }
                
                /**
                 * Gets the "INSTALL_SECT_B" attribute
                 */
                public java.lang.String getINSTALLSECTB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLSECTB$128);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_SECT_B" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLSECTB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLSECTB$128);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_SECT_B" attribute
                 */
                public boolean isSetINSTALLSECTB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLSECTB$128) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_SECT_B" attribute
                 */
                public void setINSTALLSECTB(java.lang.String installsectb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLSECTB$128);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLSECTB$128);
                      }
                      target.setStringValue(installsectb);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_SECT_B" attribute
                 */
                public void xsetINSTALLSECTB(org.apache.xmlbeans.XmlString installsectb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLSECTB$128);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLSECTB$128);
                      }
                      target.set(installsectb);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_SECT_B" attribute
                 */
                public void unsetINSTALLSECTB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLSECTB$128);
                    }
                }
                
                /**
                 * Gets the "INSTALL_DISTR_B" attribute
                 */
                public java.lang.String getINSTALLDISTRB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLDISTRB$130);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_DISTR_B" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLDISTRB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLDISTRB$130);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_DISTR_B" attribute
                 */
                public boolean isSetINSTALLDISTRB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLDISTRB$130) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_DISTR_B" attribute
                 */
                public void setINSTALLDISTRB(java.lang.String installdistrb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLDISTRB$130);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLDISTRB$130);
                      }
                      target.setStringValue(installdistrb);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_DISTR_B" attribute
                 */
                public void xsetINSTALLDISTRB(org.apache.xmlbeans.XmlString installdistrb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLDISTRB$130);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLDISTRB$130);
                      }
                      target.set(installdistrb);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_DISTR_B" attribute
                 */
                public void unsetINSTALLDISTRB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLDISTRB$130);
                    }
                }
                
                /**
                 * Gets the "INSTALL_AREA_B" attribute
                 */
                public java.lang.String getINSTALLAREAB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLAREAB$132);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_AREA_B" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLAREAB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLAREAB$132);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_AREA_B" attribute
                 */
                public boolean isSetINSTALLAREAB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLAREAB$132) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_AREA_B" attribute
                 */
                public void setINSTALLAREAB(java.lang.String installareab)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLAREAB$132);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLAREAB$132);
                      }
                      target.setStringValue(installareab);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_AREA_B" attribute
                 */
                public void xsetINSTALLAREAB(org.apache.xmlbeans.XmlString installareab)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLAREAB$132);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLAREAB$132);
                      }
                      target.set(installareab);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_AREA_B" attribute
                 */
                public void unsetINSTALLAREAB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLAREAB$132);
                    }
                }
                
                /**
                 * Gets the "INSTALL_POST_REGN_B" attribute
                 */
                public java.lang.String getINSTALLPOSTREGNB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLPOSTREGNB$134);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_POST_REGN_B" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLPOSTREGNB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLPOSTREGNB$134);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_POST_REGN_B" attribute
                 */
                public boolean isSetINSTALLPOSTREGNB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLPOSTREGNB$134) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_POST_REGN_B" attribute
                 */
                public void setINSTALLPOSTREGNB(java.lang.String installpostregnb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLPOSTREGNB$134);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLPOSTREGNB$134);
                      }
                      target.setStringValue(installpostregnb);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_POST_REGN_B" attribute
                 */
                public void xsetINSTALLPOSTREGNB(org.apache.xmlbeans.XmlString installpostregnb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLPOSTREGNB$134);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLPOSTREGNB$134);
                      }
                      target.set(installpostregnb);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_POST_REGN_B" attribute
                 */
                public void unsetINSTALLPOSTREGNB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLPOSTREGNB$134);
                    }
                }
                
                /**
                 * Gets the "INSTALL_POST_CODE_B" attribute
                 */
                public java.lang.String getINSTALLPOSTCODEB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLPOSTCODEB$136);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_POST_CODE_B" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLPOSTCODEB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLPOSTCODEB$136);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_POST_CODE_B" attribute
                 */
                public boolean isSetINSTALLPOSTCODEB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLPOSTCODEB$136) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_POST_CODE_B" attribute
                 */
                public void setINSTALLPOSTCODEB(java.lang.String installpostcodeb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLPOSTCODEB$136);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLPOSTCODEB$136);
                      }
                      target.setStringValue(installpostcodeb);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_POST_CODE_B" attribute
                 */
                public void xsetINSTALLPOSTCODEB(org.apache.xmlbeans.XmlString installpostcodeb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLPOSTCODEB$136);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLPOSTCODEB$136);
                      }
                      target.set(installpostcodeb);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_POST_CODE_B" attribute
                 */
                public void unsetINSTALLPOSTCODEB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLPOSTCODEB$136);
                    }
                }
                
                /**
                 * Gets the "INSTALL_COUNTRY_B" attribute
                 */
                public java.lang.String getINSTALLCOUNTRYB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLCOUNTRYB$138);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "INSTALL_COUNTRY_B" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetINSTALLCOUNTRYB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLCOUNTRYB$138);
                      return target;
                    }
                }
                
                /**
                 * True if has "INSTALL_COUNTRY_B" attribute
                 */
                public boolean isSetINSTALLCOUNTRYB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INSTALLCOUNTRYB$138) != null;
                    }
                }
                
                /**
                 * Sets the "INSTALL_COUNTRY_B" attribute
                 */
                public void setINSTALLCOUNTRYB(java.lang.String installcountryb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INSTALLCOUNTRYB$138);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INSTALLCOUNTRYB$138);
                      }
                      target.setStringValue(installcountryb);
                    }
                }
                
                /**
                 * Sets (as xml) the "INSTALL_COUNTRY_B" attribute
                 */
                public void xsetINSTALLCOUNTRYB(org.apache.xmlbeans.XmlString installcountryb)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INSTALLCOUNTRYB$138);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INSTALLCOUNTRYB$138);
                      }
                      target.set(installcountryb);
                    }
                }
                
                /**
                 * Unsets the "INSTALL_COUNTRY_B" attribute
                 */
                public void unsetINSTALLCOUNTRYB()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INSTALLCOUNTRYB$138);
                    }
                }
                
                /**
                 * Gets the "PROGRAM_CODE" attribute
                 */
                public java.lang.String getPROGRAMCODE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROGRAMCODE$140);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "PROGRAM_CODE" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetPROGRAMCODE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROGRAMCODE$140);
                      return target;
                    }
                }
                
                /**
                 * True if has "PROGRAM_CODE" attribute
                 */
                public boolean isSetPROGRAMCODE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(PROGRAMCODE$140) != null;
                    }
                }
                
                /**
                 * Sets the "PROGRAM_CODE" attribute
                 */
                public void setPROGRAMCODE(java.lang.String programcode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROGRAMCODE$140);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROGRAMCODE$140);
                      }
                      target.setStringValue(programcode);
                    }
                }
                
                /**
                 * Sets (as xml) the "PROGRAM_CODE" attribute
                 */
                public void xsetPROGRAMCODE(org.apache.xmlbeans.XmlString programcode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROGRAMCODE$140);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PROGRAMCODE$140);
                      }
                      target.set(programcode);
                    }
                }
                
                /**
                 * Unsets the "PROGRAM_CODE" attribute
                 */
                public void unsetPROGRAMCODE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(PROGRAMCODE$140);
                    }
                }
                
                /**
                 * Gets the "DELV_NAME" attribute
                 */
                public java.lang.String getDELVNAME()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DELVNAME$142);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "DELV_NAME" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetDELVNAME()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DELVNAME$142);
                      return target;
                    }
                }
                
                /**
                 * True if has "DELV_NAME" attribute
                 */
                public boolean isSetDELVNAME()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(DELVNAME$142) != null;
                    }
                }
                
                /**
                 * Sets the "DELV_NAME" attribute
                 */
                public void setDELVNAME(java.lang.String delvname)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DELVNAME$142);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DELVNAME$142);
                      }
                      target.setStringValue(delvname);
                    }
                }
                
                /**
                 * Sets (as xml) the "DELV_NAME" attribute
                 */
                public void xsetDELVNAME(org.apache.xmlbeans.XmlString delvname)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DELVNAME$142);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(DELVNAME$142);
                      }
                      target.set(delvname);
                    }
                }
                
                /**
                 * Unsets the "DELV_NAME" attribute
                 */
                public void unsetDELVNAME()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(DELVNAME$142);
                    }
                }
                
                /**
                 * Gets the "DELV_ADDR_1" attribute
                 */
                public java.lang.String getDELVADDR1()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DELVADDR1$144);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "DELV_ADDR_1" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetDELVADDR1()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DELVADDR1$144);
                      return target;
                    }
                }
                
                /**
                 * True if has "DELV_ADDR_1" attribute
                 */
                public boolean isSetDELVADDR1()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(DELVADDR1$144) != null;
                    }
                }
                
                /**
                 * Sets the "DELV_ADDR_1" attribute
                 */
                public void setDELVADDR1(java.lang.String delvaddr1)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DELVADDR1$144);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DELVADDR1$144);
                      }
                      target.setStringValue(delvaddr1);
                    }
                }
                
                /**
                 * Sets (as xml) the "DELV_ADDR_1" attribute
                 */
                public void xsetDELVADDR1(org.apache.xmlbeans.XmlString delvaddr1)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DELVADDR1$144);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(DELVADDR1$144);
                      }
                      target.set(delvaddr1);
                    }
                }
                
                /**
                 * Unsets the "DELV_ADDR_1" attribute
                 */
                public void unsetDELVADDR1()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(DELVADDR1$144);
                    }
                }
                
                /**
                 * Gets the "DELV_ADDR_2" attribute
                 */
                public java.lang.String getDELVADDR2()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DELVADDR2$146);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "DELV_ADDR_2" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetDELVADDR2()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DELVADDR2$146);
                      return target;
                    }
                }
                
                /**
                 * True if has "DELV_ADDR_2" attribute
                 */
                public boolean isSetDELVADDR2()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(DELVADDR2$146) != null;
                    }
                }
                
                /**
                 * Sets the "DELV_ADDR_2" attribute
                 */
                public void setDELVADDR2(java.lang.String delvaddr2)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DELVADDR2$146);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DELVADDR2$146);
                      }
                      target.setStringValue(delvaddr2);
                    }
                }
                
                /**
                 * Sets (as xml) the "DELV_ADDR_2" attribute
                 */
                public void xsetDELVADDR2(org.apache.xmlbeans.XmlString delvaddr2)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DELVADDR2$146);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(DELVADDR2$146);
                      }
                      target.set(delvaddr2);
                    }
                }
                
                /**
                 * Unsets the "DELV_ADDR_2" attribute
                 */
                public void unsetDELVADDR2()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(DELVADDR2$146);
                    }
                }
                
                /**
                 * Gets the "DELV_ADDR_3" attribute
                 */
                public java.lang.String getDELVADDR3()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DELVADDR3$148);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "DELV_ADDR_3" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetDELVADDR3()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DELVADDR3$148);
                      return target;
                    }
                }
                
                /**
                 * True if has "DELV_ADDR_3" attribute
                 */
                public boolean isSetDELVADDR3()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(DELVADDR3$148) != null;
                    }
                }
                
                /**
                 * Sets the "DELV_ADDR_3" attribute
                 */
                public void setDELVADDR3(java.lang.String delvaddr3)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DELVADDR3$148);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DELVADDR3$148);
                      }
                      target.setStringValue(delvaddr3);
                    }
                }
                
                /**
                 * Sets (as xml) the "DELV_ADDR_3" attribute
                 */
                public void xsetDELVADDR3(org.apache.xmlbeans.XmlString delvaddr3)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DELVADDR3$148);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(DELVADDR3$148);
                      }
                      target.set(delvaddr3);
                    }
                }
                
                /**
                 * Unsets the "DELV_ADDR_3" attribute
                 */
                public void unsetDELVADDR3()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(DELVADDR3$148);
                    }
                }
                
                /**
                 * Gets the "STAFF_TEL" attribute
                 */
                public java.lang.String getSTAFFTEL()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STAFFTEL$150);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "STAFF_TEL" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetSTAFFTEL()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STAFFTEL$150);
                      return target;
                    }
                }
                
                /**
                 * True if has "STAFF_TEL" attribute
                 */
                public boolean isSetSTAFFTEL()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(STAFFTEL$150) != null;
                    }
                }
                
                /**
                 * Sets the "STAFF_TEL" attribute
                 */
                public void setSTAFFTEL(java.lang.String stafftel)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STAFFTEL$150);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(STAFFTEL$150);
                      }
                      target.setStringValue(stafftel);
                    }
                }
                
                /**
                 * Sets (as xml) the "STAFF_TEL" attribute
                 */
                public void xsetSTAFFTEL(org.apache.xmlbeans.XmlString stafftel)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STAFFTEL$150);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(STAFFTEL$150);
                      }
                      target.set(stafftel);
                    }
                }
                
                /**
                 * Unsets the "STAFF_TEL" attribute
                 */
                public void unsetSTAFFTEL()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(STAFFTEL$150);
                    }
                }
            }
        }
    }
}
