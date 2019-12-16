/*
 * An XML document type.
 * Localname: sales_h
 * Namespace: 
 * Java type: noNamespace.SalesHDocument
 *
 * Automatically generated - do not modify.
 */
package noNamespace.impl;
/**
 * A document containing one sales_h(@) element.
 *
 * This is a complex type.
 */
public class SalesHDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.SalesHDocument
{
    private static final long serialVersionUID = 1L;
    
    public SalesHDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SALESH$0 = 
        new javax.xml.namespace.QName("", "sales_h");
    
    
    /**
     * Gets the "sales_h" element
     */
    public noNamespace.SalesHDocument.SalesH getSalesH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            noNamespace.SalesHDocument.SalesH target = null;
            target = (noNamespace.SalesHDocument.SalesH)get_store().find_element_user(SALESH$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "sales_h" element
     */
    public void setSalesH(noNamespace.SalesHDocument.SalesH salesH)
    {
        generatedSetterHelperImpl(salesH, SALESH$0, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }
    
    /**
     * Appends and returns a new empty "sales_h" element
     */
    public noNamespace.SalesHDocument.SalesH addNewSalesH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            noNamespace.SalesHDocument.SalesH target = null;
            target = (noNamespace.SalesHDocument.SalesH)get_store().add_element_user(SALESH$0);
            return target;
        }
    }
    /**
     * An XML sales_h(@).
     *
     * This is a complex type.
     */
    public static class SalesHImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.SalesHDocument.SalesH
    {
        private static final long serialVersionUID = 1L;
        
        public SalesHImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName ROWDATA$0 = 
            new javax.xml.namespace.QName("", "ROWDATA");
        
        
        /**
         * Gets the "ROWDATA" element
         */
        public noNamespace.SalesHDocument.SalesH.ROWDATA getROWDATA()
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.SalesHDocument.SalesH.ROWDATA target = null;
                target = (noNamespace.SalesHDocument.SalesH.ROWDATA)get_store().find_element_user(ROWDATA$0, 0);
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
        public void setROWDATA(noNamespace.SalesHDocument.SalesH.ROWDATA rowdata)
        {
            generatedSetterHelperImpl(rowdata, ROWDATA$0, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
        }
        
        /**
         * Appends and returns a new empty "ROWDATA" element
         */
        public noNamespace.SalesHDocument.SalesH.ROWDATA addNewROWDATA()
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.SalesHDocument.SalesH.ROWDATA target = null;
                target = (noNamespace.SalesHDocument.SalesH.ROWDATA)get_store().add_element_user(ROWDATA$0);
                return target;
            }
        }
        /**
         * An XML ROWDATA(@).
         *
         * This is a complex type.
         */
        public static class ROWDATAImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.SalesHDocument.SalesH.ROWDATA
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
            public noNamespace.SalesHDocument.SalesH.ROWDATA.ROW getROW()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    noNamespace.SalesHDocument.SalesH.ROWDATA.ROW target = null;
                    target = (noNamespace.SalesHDocument.SalesH.ROWDATA.ROW)get_store().find_element_user(ROW$0, 0);
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
            public void setROW(noNamespace.SalesHDocument.SalesH.ROWDATA.ROW row)
            {
                generatedSetterHelperImpl(row, ROW$0, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
            }
            
            /**
             * Appends and returns a new empty "ROW" element
             */
            public noNamespace.SalesHDocument.SalesH.ROWDATA.ROW addNewROW()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    noNamespace.SalesHDocument.SalesH.ROWDATA.ROW target = null;
                    target = (noNamespace.SalesHDocument.SalesH.ROWDATA.ROW)get_store().add_element_user(ROW$0);
                    return target;
                }
            }
            /**
             * An XML ROW(@).
             *
             * This is an atomic type that is a restriction of noNamespace.SalesHDocument$SalesH$ROWDATA$ROW.
             */
            public static class ROWImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements noNamespace.SalesHDocument.SalesH.ROWDATA.ROW
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
                private static final javax.xml.namespace.QName CASHIERID$12 = 
                    new javax.xml.namespace.QName("", "Cashier_ID");
                private static final javax.xml.namespace.QName SALESMANID$14 = 
                    new javax.xml.namespace.QName("", "SalesMan_ID");
                private static final javax.xml.namespace.QName SALESMANEXT$16 = 
                    new javax.xml.namespace.QName("", "Salesman_Ext");
                private static final javax.xml.namespace.QName TEAMCODE$18 = 
                    new javax.xml.namespace.QName("", "Team_Code");
                private static final javax.xml.namespace.QName CHANNEL$20 = 
                    new javax.xml.namespace.QName("", "channel");
                private static final javax.xml.namespace.QName SALESMANCODE$22 = 
                    new javax.xml.namespace.QName("", "salesman_code");
                private static final javax.xml.namespace.QName QUNUM$24 = 
                    new javax.xml.namespace.QName("", "QU_Num");
                private static final javax.xml.namespace.QName SMNUM$26 = 
                    new javax.xml.namespace.QName("", "SM_Num");
                private static final javax.xml.namespace.QName TOTALAMT$28 = 
                    new javax.xml.namespace.QName("", "TotalAmt");
                private static final javax.xml.namespace.QName SINEED$30 = 
                    new javax.xml.namespace.QName("", "SI_Need");
                private static final javax.xml.namespace.QName SMCREATEDATE$32 = 
                    new javax.xml.namespace.QName("", "SM_CreateDate");
                private static final javax.xml.namespace.QName MODIFYBY$34 = 
                    new javax.xml.namespace.QName("", "Modifyby");
                private static final javax.xml.namespace.QName MODIFYDATE$36 = 
                    new javax.xml.namespace.QName("", "ModifyDate");
                private static final javax.xml.namespace.QName CONSOLDATE$38 = 
                    new javax.xml.namespace.QName("", "ConsolDate");
                private static final javax.xml.namespace.QName SETTLEMENTDATE$40 = 
                    new javax.xml.namespace.QName("", "SettlementDate");
                private static final javax.xml.namespace.QName SETTLELOGDATE$42 = 
                    new javax.xml.namespace.QName("", "Settle_Log_Date");
                private static final javax.xml.namespace.QName SETTLEMENTSTAFFID$44 = 
                    new javax.xml.namespace.QName("", "Settlement_Staff_ID");
                private static final javax.xml.namespace.QName STACHGDATE$46 = 
                    new javax.xml.namespace.QName("", "StaChgDate");
                private static final javax.xml.namespace.QName STACHGBY$48 = 
                    new javax.xml.namespace.QName("", "StaChgBy");
                private static final javax.xml.namespace.QName PAYSETTLEPHYDATE$50 = 
                    new javax.xml.namespace.QName("", "Pay_Settle_PhyDate");
                private static final javax.xml.namespace.QName PAYSETTLELOGDATE$52 = 
                    new javax.xml.namespace.QName("", "Pay_Settle_LogDate");
                private static final javax.xml.namespace.QName STATUS$54 = 
                    new javax.xml.namespace.QName("", "Status");
                private static final javax.xml.namespace.QName CUTITLE$56 = 
                    new javax.xml.namespace.QName("", "CU_Title");
                private static final javax.xml.namespace.QName CUNAME$58 = 
                    new javax.xml.namespace.QName("", "CU_Name");
                private static final javax.xml.namespace.QName CUCCCNO$60 = 
                    new javax.xml.namespace.QName("", "CU_CCCNO");
                private static final javax.xml.namespace.QName CUTEL$62 = 
                    new javax.xml.namespace.QName("", "CU_Tel");
                private static final javax.xml.namespace.QName CUPRIME$64 = 
                    new javax.xml.namespace.QName("", "CU_Prime");
                private static final javax.xml.namespace.QName CUFAXNO$66 = 
                    new javax.xml.namespace.QName("", "CU_FaxNo");
                private static final javax.xml.namespace.QName CUMEMTYPE$68 = 
                    new javax.xml.namespace.QName("", "CU_MemType");
                private static final javax.xml.namespace.QName CUCONTPER$70 = 
                    new javax.xml.namespace.QName("", "CU_ContPer");
                private static final javax.xml.namespace.QName CUMEMBERNO$72 = 
                    new javax.xml.namespace.QName("", "CU_MemberNo");
                private static final javax.xml.namespace.QName CUINTERCOM$74 = 
                    new javax.xml.namespace.QName("", "CU_InterCom");
                private static final javax.xml.namespace.QName CUIDBUSTYPE$76 = 
                    new javax.xml.namespace.QName("", "CU_IDBusType");
                private static final javax.xml.namespace.QName CUIDBUSNO$78 = 
                    new javax.xml.namespace.QName("", "CU_IDBusNo");
                private static final javax.xml.namespace.QName CUREMARK$80 = 
                    new javax.xml.namespace.QName("", "CU_Remark");
                private static final javax.xml.namespace.QName CUREQUESTDATE$82 = 
                    new javax.xml.namespace.QName("", "CU_Request_Date");
                private static final javax.xml.namespace.QName CUREQUESTTIME$84 = 
                    new javax.xml.namespace.QName("", "CU_Request_Time");
                private static final javax.xml.namespace.QName CUPOSBADDEBT$86 = 
                    new javax.xml.namespace.QName("", "CU_PosBadDebt");
                private static final javax.xml.namespace.QName CUCFLATUNIT$88 = 
                    new javax.xml.namespace.QName("", "CU_CFLATUNIT");
                private static final javax.xml.namespace.QName CUCFLOOR$90 = 
                    new javax.xml.namespace.QName("", "CU_CFLOOR");
                private static final javax.xml.namespace.QName CUCBLDG$92 = 
                    new javax.xml.namespace.QName("", "CU_CBLDG");
                private static final javax.xml.namespace.QName CUCPOBOX$94 = 
                    new javax.xml.namespace.QName("", "CU_CPOBOX");
                private static final javax.xml.namespace.QName CUCHSESTNO$96 = 
                    new javax.xml.namespace.QName("", "CU_CHSESTNO");
                private static final javax.xml.namespace.QName CUCLOTLDNO$98 = 
                    new javax.xml.namespace.QName("", "CU_CLOTLDNO");
                private static final javax.xml.namespace.QName CUCSTNAME$100 = 
                    new javax.xml.namespace.QName("", "CU_CSTNAME");
                private static final javax.xml.namespace.QName CUCSTCAT$102 = 
                    new javax.xml.namespace.QName("", "CU_CSTCAT");
                private static final javax.xml.namespace.QName CUCSECT$104 = 
                    new javax.xml.namespace.QName("", "CU_CSECT");
                private static final javax.xml.namespace.QName CUCDISTR$106 = 
                    new javax.xml.namespace.QName("", "CU_CDISTR");
                private static final javax.xml.namespace.QName CUCAREA$108 = 
                    new javax.xml.namespace.QName("", "CU_CAREA");
                private static final javax.xml.namespace.QName CUCPOSTREGN$110 = 
                    new javax.xml.namespace.QName("", "CU_CPOSTREGN");
                private static final javax.xml.namespace.QName CUCPOSTCD$112 = 
                    new javax.xml.namespace.QName("", "CU_CPOSTCD");
                private static final javax.xml.namespace.QName CUCCOUNTRY$114 = 
                    new javax.xml.namespace.QName("", "CU_CCOUNTRY");
                private static final javax.xml.namespace.QName CUCATTETO$116 = 
                    new javax.xml.namespace.QName("", "CU_CATTETO");
                private static final javax.xml.namespace.QName CUCCO$118 = 
                    new javax.xml.namespace.QName("", "CU_CCO");
                private static final javax.xml.namespace.QName CUIFLATUNIT$120 = 
                    new javax.xml.namespace.QName("", "CU_IFLATUNIT");
                private static final javax.xml.namespace.QName CUIFLOOR$122 = 
                    new javax.xml.namespace.QName("", "CU_IFLOOR");
                private static final javax.xml.namespace.QName CUIBLDG$124 = 
                    new javax.xml.namespace.QName("", "CU_IBLDG");
                private static final javax.xml.namespace.QName CUIPOBOX$126 = 
                    new javax.xml.namespace.QName("", "CU_IPOBOX");
                private static final javax.xml.namespace.QName CUIHSESTNO$128 = 
                    new javax.xml.namespace.QName("", "CU_IHSESTNO");
                private static final javax.xml.namespace.QName CUILOTLDNO$130 = 
                    new javax.xml.namespace.QName("", "CU_ILOTLDNO");
                private static final javax.xml.namespace.QName CUISTNAME$132 = 
                    new javax.xml.namespace.QName("", "CU_ISTNAME");
                private static final javax.xml.namespace.QName CUISTCAT$134 = 
                    new javax.xml.namespace.QName("", "CU_ISTCAT");
                private static final javax.xml.namespace.QName CUISECT$136 = 
                    new javax.xml.namespace.QName("", "CU_ISECT");
                private static final javax.xml.namespace.QName CUIDISTR$138 = 
                    new javax.xml.namespace.QName("", "CU_IDISTR");
                private static final javax.xml.namespace.QName CUIAREA$140 = 
                    new javax.xml.namespace.QName("", "CU_IAREA");
                private static final javax.xml.namespace.QName CUIPOSTREGN$142 = 
                    new javax.xml.namespace.QName("", "CU_IPOSTREGN");
                private static final javax.xml.namespace.QName CUIPOSTCD$144 = 
                    new javax.xml.namespace.QName("", "CU_IPOSTCD");
                private static final javax.xml.namespace.QName CUICOUNTRY$146 = 
                    new javax.xml.namespace.QName("", "CU_ICOUNTRY");
                private static final javax.xml.namespace.QName CPEFLAG$148 = 
                    new javax.xml.namespace.QName("", "CPEFLAG");
                private static final javax.xml.namespace.QName TRANSDISCOUNT$150 = 
                    new javax.xml.namespace.QName("", "Trans_Discount");
                private static final javax.xml.namespace.QName TRANSREASON$152 = 
                    new javax.xml.namespace.QName("", "Trans_Reason");
                private static final javax.xml.namespace.QName CPEWORKNUM$154 = 
                    new javax.xml.namespace.QName("", "CPE_Work_Num");
                private static final javax.xml.namespace.QName CONVERTED$156 = 
                    new javax.xml.namespace.QName("", "Converted");
                private static final javax.xml.namespace.QName ORGQUNUM$158 = 
                    new javax.xml.namespace.QName("", "Org_QU_Num");
                private static final javax.xml.namespace.QName REFSMNO$160 = 
                    new javax.xml.namespace.QName("", "Ref_SM_No");
                private static final javax.xml.namespace.QName INVALIDATEDFLAG$162 = 
                    new javax.xml.namespace.QName("", "Invalidated_Flag");
                private static final javax.xml.namespace.QName EXCHANGEFLAG$164 = 
                    new javax.xml.namespace.QName("", "ExchangeFlag");
                private static final javax.xml.namespace.QName DOWNLOAD$166 = 
                    new javax.xml.namespace.QName("", "Download");
                private static final javax.xml.namespace.QName CUEMAIL$168 = 
                    new javax.xml.namespace.QName("", "CU_Email");
                private static final javax.xml.namespace.QName STORETYPE$170 = 
                    new javax.xml.namespace.QName("", "Store_Type");
                private static final javax.xml.namespace.QName STORENAME$172 = 
                    new javax.xml.namespace.QName("", "Store_Name");
                private static final javax.xml.namespace.QName STORETEL$174 = 
                    new javax.xml.namespace.QName("", "Store_Tel");
                private static final javax.xml.namespace.QName STOREBEGINHOURS$176 = 
                    new javax.xml.namespace.QName("", "Store_Begin_Hours");
                private static final javax.xml.namespace.QName STOREENDHOURS$178 = 
                    new javax.xml.namespace.QName("", "Store_End_Hours");
                private static final javax.xml.namespace.QName STOREADDR1$180 = 
                    new javax.xml.namespace.QName("", "Store_Addr_1");
                private static final javax.xml.namespace.QName STOREADDR2$182 = 
                    new javax.xml.namespace.QName("", "Store_Addr_2");
                private static final javax.xml.namespace.QName STOREADDR3$184 = 
                    new javax.xml.namespace.QName("", "Store_Addr_3");
                private static final javax.xml.namespace.QName SALESMANNAME$186 = 
                    new javax.xml.namespace.QName("", "Salesman_Name");
                private static final javax.xml.namespace.QName SETTLESTAFFNAME$188 = 
                    new javax.xml.namespace.QName("", "Settle_Staff_Name");
                private static final javax.xml.namespace.QName CMR$190 = 
                    new javax.xml.namespace.QName("", "CMR");
                private static final javax.xml.namespace.QName UM$192 = 
                    new javax.xml.namespace.QName("", "UM");
                private static final javax.xml.namespace.QName TITLE$194 = 
                    new javax.xml.namespace.QName("", "Title");
                private static final javax.xml.namespace.QName TEAMNAME$196 = 
                    new javax.xml.namespace.QName("", "Team_Name");
                private static final javax.xml.namespace.QName DEP$198 = 
                    new javax.xml.namespace.QName("", "Dep");
                private static final javax.xml.namespace.QName DEPNAME$200 = 
                    new javax.xml.namespace.QName("", "Dep_Name");
                private static final javax.xml.namespace.QName COLLTIME$202 = 
                    new javax.xml.namespace.QName("", "coll_time");
                private static final javax.xml.namespace.QName IMSACNO$204 = 
                    new javax.xml.namespace.QName("", "IMS_Acno");
                private static final javax.xml.namespace.QName PAIDAMT$206 = 
                    new javax.xml.namespace.QName("", "Paid_Amt");
                private static final javax.xml.namespace.QName TXNDATEPOSBOM$208 = 
                    new javax.xml.namespace.QName("", "Txn_Date_POS_BOM");
                private static final javax.xml.namespace.QName TXNTIMEPOSBOM$210 = 
                    new javax.xml.namespace.QName("", "Txn_Time_POS_BOM");
                private static final javax.xml.namespace.QName OCID$212 = 
                    new javax.xml.namespace.QName("", "OCID");
                private static final javax.xml.namespace.QName REQUESTID$214 = 
                    new javax.xml.namespace.QName("", "Request_ID");
                private static final javax.xml.namespace.QName CUSTOMERID$216 = 
                    new javax.xml.namespace.QName("", "Customer_ID");
                private static final javax.xml.namespace.QName ACCOUNTCODE$218 = 
                    new javax.xml.namespace.QName("", "Account_Code");
                private static final javax.xml.namespace.QName SSAGREENO$220 = 
                    new javax.xml.namespace.QName("", "SSAgreeNo");
                private static final javax.xml.namespace.QName CUMOBILENO$222 = 
                    new javax.xml.namespace.QName("", "CU_MobileNo");
                private static final javax.xml.namespace.QName MOBILENOTYPE$224 = 
                    new javax.xml.namespace.QName("", "MobileNoType");
                private static final javax.xml.namespace.QName MOBILENO$226 = 
                    new javax.xml.namespace.QName("", "MobileNo");
                
                
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
                 * Gets the "Cashier_ID" attribute
                 */
                public java.lang.String getCashierID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CASHIERID$12);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Cashier_ID" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCashierID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CASHIERID$12);
                      return target;
                    }
                }
                
                /**
                 * True if has "Cashier_ID" attribute
                 */
                public boolean isSetCashierID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CASHIERID$12) != null;
                    }
                }
                
                /**
                 * Sets the "Cashier_ID" attribute
                 */
                public void setCashierID(java.lang.String cashierID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CASHIERID$12);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CASHIERID$12);
                      }
                      target.setStringValue(cashierID);
                    }
                }
                
                /**
                 * Sets (as xml) the "Cashier_ID" attribute
                 */
                public void xsetCashierID(org.apache.xmlbeans.XmlString cashierID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CASHIERID$12);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CASHIERID$12);
                      }
                      target.set(cashierID);
                    }
                }
                
                /**
                 * Unsets the "Cashier_ID" attribute
                 */
                public void unsetCashierID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CASHIERID$12);
                    }
                }
                
                /**
                 * Gets the "SalesMan_ID" attribute
                 */
                public java.lang.String getSalesManID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SALESMANID$14);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "SalesMan_ID" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetSalesManID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SALESMANID$14);
                      return target;
                    }
                }
                
                /**
                 * True if has "SalesMan_ID" attribute
                 */
                public boolean isSetSalesManID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(SALESMANID$14) != null;
                    }
                }
                
                /**
                 * Sets the "SalesMan_ID" attribute
                 */
                public void setSalesManID(java.lang.String salesManID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SALESMANID$14);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SALESMANID$14);
                      }
                      target.setStringValue(salesManID);
                    }
                }
                
                /**
                 * Sets (as xml) the "SalesMan_ID" attribute
                 */
                public void xsetSalesManID(org.apache.xmlbeans.XmlString salesManID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SALESMANID$14);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SALESMANID$14);
                      }
                      target.set(salesManID);
                    }
                }
                
                /**
                 * Unsets the "SalesMan_ID" attribute
                 */
                public void unsetSalesManID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(SALESMANID$14);
                    }
                }
                
                /**
                 * Gets the "Salesman_Ext" attribute
                 */
                public java.lang.String getSalesmanExt()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SALESMANEXT$16);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Salesman_Ext" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetSalesmanExt()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SALESMANEXT$16);
                      return target;
                    }
                }
                
                /**
                 * True if has "Salesman_Ext" attribute
                 */
                public boolean isSetSalesmanExt()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(SALESMANEXT$16) != null;
                    }
                }
                
                /**
                 * Sets the "Salesman_Ext" attribute
                 */
                public void setSalesmanExt(java.lang.String salesmanExt)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SALESMANEXT$16);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SALESMANEXT$16);
                      }
                      target.setStringValue(salesmanExt);
                    }
                }
                
                /**
                 * Sets (as xml) the "Salesman_Ext" attribute
                 */
                public void xsetSalesmanExt(org.apache.xmlbeans.XmlString salesmanExt)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SALESMANEXT$16);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SALESMANEXT$16);
                      }
                      target.set(salesmanExt);
                    }
                }
                
                /**
                 * Unsets the "Salesman_Ext" attribute
                 */
                public void unsetSalesmanExt()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(SALESMANEXT$16);
                    }
                }
                
                /**
                 * Gets the "Team_Code" attribute
                 */
                public java.lang.String getTeamCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TEAMCODE$18);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Team_Code" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetTeamCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TEAMCODE$18);
                      return target;
                    }
                }
                
                /**
                 * True if has "Team_Code" attribute
                 */
                public boolean isSetTeamCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(TEAMCODE$18) != null;
                    }
                }
                
                /**
                 * Sets the "Team_Code" attribute
                 */
                public void setTeamCode(java.lang.String teamCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TEAMCODE$18);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TEAMCODE$18);
                      }
                      target.setStringValue(teamCode);
                    }
                }
                
                /**
                 * Sets (as xml) the "Team_Code" attribute
                 */
                public void xsetTeamCode(org.apache.xmlbeans.XmlString teamCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TEAMCODE$18);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(TEAMCODE$18);
                      }
                      target.set(teamCode);
                    }
                }
                
                /**
                 * Unsets the "Team_Code" attribute
                 */
                public void unsetTeamCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(TEAMCODE$18);
                    }
                }
                
                /**
                 * Gets the "channel" attribute
                 */
                public java.lang.String getChannel()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CHANNEL$20);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "channel" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetChannel()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CHANNEL$20);
                      return target;
                    }
                }
                
                /**
                 * True if has "channel" attribute
                 */
                public boolean isSetChannel()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CHANNEL$20) != null;
                    }
                }
                
                /**
                 * Sets the "channel" attribute
                 */
                public void setChannel(java.lang.String channel)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CHANNEL$20);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CHANNEL$20);
                      }
                      target.setStringValue(channel);
                    }
                }
                
                /**
                 * Sets (as xml) the "channel" attribute
                 */
                public void xsetChannel(org.apache.xmlbeans.XmlString channel)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CHANNEL$20);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CHANNEL$20);
                      }
                      target.set(channel);
                    }
                }
                
                /**
                 * Unsets the "channel" attribute
                 */
                public void unsetChannel()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CHANNEL$20);
                    }
                }
                
                /**
                 * Gets the "salesman_code" attribute
                 */
                public java.lang.String getSalesmanCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SALESMANCODE$22);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "salesman_code" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetSalesmanCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SALESMANCODE$22);
                      return target;
                    }
                }
                
                /**
                 * True if has "salesman_code" attribute
                 */
                public boolean isSetSalesmanCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(SALESMANCODE$22) != null;
                    }
                }
                
                /**
                 * Sets the "salesman_code" attribute
                 */
                public void setSalesmanCode(java.lang.String salesmanCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SALESMANCODE$22);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SALESMANCODE$22);
                      }
                      target.setStringValue(salesmanCode);
                    }
                }
                
                /**
                 * Sets (as xml) the "salesman_code" attribute
                 */
                public void xsetSalesmanCode(org.apache.xmlbeans.XmlString salesmanCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SALESMANCODE$22);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SALESMANCODE$22);
                      }
                      target.set(salesmanCode);
                    }
                }
                
                /**
                 * Unsets the "salesman_code" attribute
                 */
                public void unsetSalesmanCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(SALESMANCODE$22);
                    }
                }
                
                /**
                 * Gets the "QU_Num" attribute
                 */
                public java.lang.String getQUNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(QUNUM$24);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "QU_Num" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetQUNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(QUNUM$24);
                      return target;
                    }
                }
                
                /**
                 * True if has "QU_Num" attribute
                 */
                public boolean isSetQUNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(QUNUM$24) != null;
                    }
                }
                
                /**
                 * Sets the "QU_Num" attribute
                 */
                public void setQUNum(java.lang.String quNum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(QUNUM$24);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(QUNUM$24);
                      }
                      target.setStringValue(quNum);
                    }
                }
                
                /**
                 * Sets (as xml) the "QU_Num" attribute
                 */
                public void xsetQUNum(org.apache.xmlbeans.XmlString quNum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(QUNUM$24);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(QUNUM$24);
                      }
                      target.set(quNum);
                    }
                }
                
                /**
                 * Unsets the "QU_Num" attribute
                 */
                public void unsetQUNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(QUNUM$24);
                    }
                }
                
                /**
                 * Gets the "SM_Num" attribute
                 */
                public java.lang.String getSMNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SMNUM$26);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "SM_Num" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetSMNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SMNUM$26);
                      return target;
                    }
                }
                
                /**
                 * True if has "SM_Num" attribute
                 */
                public boolean isSetSMNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(SMNUM$26) != null;
                    }
                }
                
                /**
                 * Sets the "SM_Num" attribute
                 */
                public void setSMNum(java.lang.String smNum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SMNUM$26);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SMNUM$26);
                      }
                      target.setStringValue(smNum);
                    }
                }
                
                /**
                 * Sets (as xml) the "SM_Num" attribute
                 */
                public void xsetSMNum(org.apache.xmlbeans.XmlString smNum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SMNUM$26);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SMNUM$26);
                      }
                      target.set(smNum);
                    }
                }
                
                /**
                 * Unsets the "SM_Num" attribute
                 */
                public void unsetSMNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(SMNUM$26);
                    }
                }
                
                /**
                 * Gets the "TotalAmt" attribute
                 */
                public short getTotalAmt()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TOTALAMT$28);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getShortValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "TotalAmt" attribute
                 */
                public org.apache.xmlbeans.XmlShort xgetTotalAmt()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(TOTALAMT$28);
                      return target;
                    }
                }
                
                /**
                 * True if has "TotalAmt" attribute
                 */
                public boolean isSetTotalAmt()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(TOTALAMT$28) != null;
                    }
                }
                
                /**
                 * Sets the "TotalAmt" attribute
                 */
                public void setTotalAmt(short totalAmt)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TOTALAMT$28);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TOTALAMT$28);
                      }
                      target.setShortValue(totalAmt);
                    }
                }
                
                /**
                 * Sets (as xml) the "TotalAmt" attribute
                 */
                public void xsetTotalAmt(org.apache.xmlbeans.XmlShort totalAmt)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(TOTALAMT$28);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlShort)get_store().add_attribute_user(TOTALAMT$28);
                      }
                      target.set(totalAmt);
                    }
                }
                
                /**
                 * Unsets the "TotalAmt" attribute
                 */
                public void unsetTotalAmt()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(TOTALAMT$28);
                    }
                }
                
                /**
                 * Gets the "SI_Need" attribute
                 */
                public java.lang.String getSINeed()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SINEED$30);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "SI_Need" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetSINeed()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SINEED$30);
                      return target;
                    }
                }
                
                /**
                 * True if has "SI_Need" attribute
                 */
                public boolean isSetSINeed()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(SINEED$30) != null;
                    }
                }
                
                /**
                 * Sets the "SI_Need" attribute
                 */
                public void setSINeed(java.lang.String siNeed)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SINEED$30);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SINEED$30);
                      }
                      target.setStringValue(siNeed);
                    }
                }
                
                /**
                 * Sets (as xml) the "SI_Need" attribute
                 */
                public void xsetSINeed(org.apache.xmlbeans.XmlString siNeed)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SINEED$30);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SINEED$30);
                      }
                      target.set(siNeed);
                    }
                }
                
                /**
                 * Unsets the "SI_Need" attribute
                 */
                public void unsetSINeed()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(SINEED$30);
                    }
                }
                
                /**
                 * Gets the "SM_CreateDate" attribute
                 */
                public java.lang.String getSMCreateDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SMCREATEDATE$32);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "SM_CreateDate" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetSMCreateDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SMCREATEDATE$32);
                      return target;
                    }
                }
                
                /**
                 * True if has "SM_CreateDate" attribute
                 */
                public boolean isSetSMCreateDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(SMCREATEDATE$32) != null;
                    }
                }
                
                /**
                 * Sets the "SM_CreateDate" attribute
                 */
                public void setSMCreateDate(java.lang.String smCreateDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SMCREATEDATE$32);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SMCREATEDATE$32);
                      }
                      target.setStringValue(smCreateDate);
                    }
                }
                
                /**
                 * Sets (as xml) the "SM_CreateDate" attribute
                 */
                public void xsetSMCreateDate(org.apache.xmlbeans.XmlString smCreateDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SMCREATEDATE$32);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SMCREATEDATE$32);
                      }
                      target.set(smCreateDate);
                    }
                }
                
                /**
                 * Unsets the "SM_CreateDate" attribute
                 */
                public void unsetSMCreateDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(SMCREATEDATE$32);
                    }
                }
                
                /**
                 * Gets the "Modifyby" attribute
                 */
                public java.lang.String getModifyby()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MODIFYBY$34);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Modifyby" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetModifyby()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(MODIFYBY$34);
                      return target;
                    }
                }
                
                /**
                 * True if has "Modifyby" attribute
                 */
                public boolean isSetModifyby()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(MODIFYBY$34) != null;
                    }
                }
                
                /**
                 * Sets the "Modifyby" attribute
                 */
                public void setModifyby(java.lang.String modifyby)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MODIFYBY$34);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(MODIFYBY$34);
                      }
                      target.setStringValue(modifyby);
                    }
                }
                
                /**
                 * Sets (as xml) the "Modifyby" attribute
                 */
                public void xsetModifyby(org.apache.xmlbeans.XmlString modifyby)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(MODIFYBY$34);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(MODIFYBY$34);
                      }
                      target.set(modifyby);
                    }
                }
                
                /**
                 * Unsets the "Modifyby" attribute
                 */
                public void unsetModifyby()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(MODIFYBY$34);
                    }
                }
                
                /**
                 * Gets the "ModifyDate" attribute
                 */
                public java.lang.String getModifyDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MODIFYDATE$36);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "ModifyDate" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetModifyDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(MODIFYDATE$36);
                      return target;
                    }
                }
                
                /**
                 * True if has "ModifyDate" attribute
                 */
                public boolean isSetModifyDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(MODIFYDATE$36) != null;
                    }
                }
                
                /**
                 * Sets the "ModifyDate" attribute
                 */
                public void setModifyDate(java.lang.String modifyDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MODIFYDATE$36);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(MODIFYDATE$36);
                      }
                      target.setStringValue(modifyDate);
                    }
                }
                
                /**
                 * Sets (as xml) the "ModifyDate" attribute
                 */
                public void xsetModifyDate(org.apache.xmlbeans.XmlString modifyDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(MODIFYDATE$36);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(MODIFYDATE$36);
                      }
                      target.set(modifyDate);
                    }
                }
                
                /**
                 * Unsets the "ModifyDate" attribute
                 */
                public void unsetModifyDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(MODIFYDATE$36);
                    }
                }
                
                /**
                 * Gets the "ConsolDate" attribute
                 */
                public java.lang.String getConsolDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CONSOLDATE$38);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "ConsolDate" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetConsolDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CONSOLDATE$38);
                      return target;
                    }
                }
                
                /**
                 * True if has "ConsolDate" attribute
                 */
                public boolean isSetConsolDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CONSOLDATE$38) != null;
                    }
                }
                
                /**
                 * Sets the "ConsolDate" attribute
                 */
                public void setConsolDate(java.lang.String consolDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CONSOLDATE$38);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CONSOLDATE$38);
                      }
                      target.setStringValue(consolDate);
                    }
                }
                
                /**
                 * Sets (as xml) the "ConsolDate" attribute
                 */
                public void xsetConsolDate(org.apache.xmlbeans.XmlString consolDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CONSOLDATE$38);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CONSOLDATE$38);
                      }
                      target.set(consolDate);
                    }
                }
                
                /**
                 * Unsets the "ConsolDate" attribute
                 */
                public void unsetConsolDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CONSOLDATE$38);
                    }
                }
                
                /**
                 * Gets the "SettlementDate" attribute
                 */
                public java.lang.String getSettlementDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SETTLEMENTDATE$40);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "SettlementDate" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetSettlementDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SETTLEMENTDATE$40);
                      return target;
                    }
                }
                
                /**
                 * True if has "SettlementDate" attribute
                 */
                public boolean isSetSettlementDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(SETTLEMENTDATE$40) != null;
                    }
                }
                
                /**
                 * Sets the "SettlementDate" attribute
                 */
                public void setSettlementDate(java.lang.String settlementDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SETTLEMENTDATE$40);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SETTLEMENTDATE$40);
                      }
                      target.setStringValue(settlementDate);
                    }
                }
                
                /**
                 * Sets (as xml) the "SettlementDate" attribute
                 */
                public void xsetSettlementDate(org.apache.xmlbeans.XmlString settlementDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SETTLEMENTDATE$40);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SETTLEMENTDATE$40);
                      }
                      target.set(settlementDate);
                    }
                }
                
                /**
                 * Unsets the "SettlementDate" attribute
                 */
                public void unsetSettlementDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(SETTLEMENTDATE$40);
                    }
                }
                
                /**
                 * Gets the "Settle_Log_Date" attribute
                 */
                public java.lang.String getSettleLogDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SETTLELOGDATE$42);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Settle_Log_Date" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetSettleLogDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SETTLELOGDATE$42);
                      return target;
                    }
                }
                
                /**
                 * True if has "Settle_Log_Date" attribute
                 */
                public boolean isSetSettleLogDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(SETTLELOGDATE$42) != null;
                    }
                }
                
                /**
                 * Sets the "Settle_Log_Date" attribute
                 */
                public void setSettleLogDate(java.lang.String settleLogDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SETTLELOGDATE$42);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SETTLELOGDATE$42);
                      }
                      target.setStringValue(settleLogDate);
                    }
                }
                
                /**
                 * Sets (as xml) the "Settle_Log_Date" attribute
                 */
                public void xsetSettleLogDate(org.apache.xmlbeans.XmlString settleLogDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SETTLELOGDATE$42);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SETTLELOGDATE$42);
                      }
                      target.set(settleLogDate);
                    }
                }
                
                /**
                 * Unsets the "Settle_Log_Date" attribute
                 */
                public void unsetSettleLogDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(SETTLELOGDATE$42);
                    }
                }
                
                /**
                 * Gets the "Settlement_Staff_ID" attribute
                 */
                public java.lang.String getSettlementStaffID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SETTLEMENTSTAFFID$44);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Settlement_Staff_ID" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetSettlementStaffID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SETTLEMENTSTAFFID$44);
                      return target;
                    }
                }
                
                /**
                 * True if has "Settlement_Staff_ID" attribute
                 */
                public boolean isSetSettlementStaffID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(SETTLEMENTSTAFFID$44) != null;
                    }
                }
                
                /**
                 * Sets the "Settlement_Staff_ID" attribute
                 */
                public void setSettlementStaffID(java.lang.String settlementStaffID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SETTLEMENTSTAFFID$44);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SETTLEMENTSTAFFID$44);
                      }
                      target.setStringValue(settlementStaffID);
                    }
                }
                
                /**
                 * Sets (as xml) the "Settlement_Staff_ID" attribute
                 */
                public void xsetSettlementStaffID(org.apache.xmlbeans.XmlString settlementStaffID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SETTLEMENTSTAFFID$44);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SETTLEMENTSTAFFID$44);
                      }
                      target.set(settlementStaffID);
                    }
                }
                
                /**
                 * Unsets the "Settlement_Staff_ID" attribute
                 */
                public void unsetSettlementStaffID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(SETTLEMENTSTAFFID$44);
                    }
                }
                
                /**
                 * Gets the "StaChgDate" attribute
                 */
                public java.lang.String getStaChgDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STACHGDATE$46);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "StaChgDate" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetStaChgDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STACHGDATE$46);
                      return target;
                    }
                }
                
                /**
                 * True if has "StaChgDate" attribute
                 */
                public boolean isSetStaChgDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(STACHGDATE$46) != null;
                    }
                }
                
                /**
                 * Sets the "StaChgDate" attribute
                 */
                public void setStaChgDate(java.lang.String staChgDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STACHGDATE$46);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(STACHGDATE$46);
                      }
                      target.setStringValue(staChgDate);
                    }
                }
                
                /**
                 * Sets (as xml) the "StaChgDate" attribute
                 */
                public void xsetStaChgDate(org.apache.xmlbeans.XmlString staChgDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STACHGDATE$46);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(STACHGDATE$46);
                      }
                      target.set(staChgDate);
                    }
                }
                
                /**
                 * Unsets the "StaChgDate" attribute
                 */
                public void unsetStaChgDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(STACHGDATE$46);
                    }
                }
                
                /**
                 * Gets the "StaChgBy" attribute
                 */
                public java.lang.String getStaChgBy()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STACHGBY$48);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "StaChgBy" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetStaChgBy()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STACHGBY$48);
                      return target;
                    }
                }
                
                /**
                 * True if has "StaChgBy" attribute
                 */
                public boolean isSetStaChgBy()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(STACHGBY$48) != null;
                    }
                }
                
                /**
                 * Sets the "StaChgBy" attribute
                 */
                public void setStaChgBy(java.lang.String staChgBy)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STACHGBY$48);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(STACHGBY$48);
                      }
                      target.setStringValue(staChgBy);
                    }
                }
                
                /**
                 * Sets (as xml) the "StaChgBy" attribute
                 */
                public void xsetStaChgBy(org.apache.xmlbeans.XmlString staChgBy)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STACHGBY$48);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(STACHGBY$48);
                      }
                      target.set(staChgBy);
                    }
                }
                
                /**
                 * Unsets the "StaChgBy" attribute
                 */
                public void unsetStaChgBy()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(STACHGBY$48);
                    }
                }
                
                /**
                 * Gets the "Pay_Settle_PhyDate" attribute
                 */
                public java.lang.String getPaySettlePhyDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PAYSETTLEPHYDATE$50);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pay_Settle_PhyDate" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetPaySettlePhyDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PAYSETTLEPHYDATE$50);
                      return target;
                    }
                }
                
                /**
                 * True if has "Pay_Settle_PhyDate" attribute
                 */
                public boolean isSetPaySettlePhyDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(PAYSETTLEPHYDATE$50) != null;
                    }
                }
                
                /**
                 * Sets the "Pay_Settle_PhyDate" attribute
                 */
                public void setPaySettlePhyDate(java.lang.String paySettlePhyDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PAYSETTLEPHYDATE$50);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PAYSETTLEPHYDATE$50);
                      }
                      target.setStringValue(paySettlePhyDate);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pay_Settle_PhyDate" attribute
                 */
                public void xsetPaySettlePhyDate(org.apache.xmlbeans.XmlString paySettlePhyDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PAYSETTLEPHYDATE$50);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PAYSETTLEPHYDATE$50);
                      }
                      target.set(paySettlePhyDate);
                    }
                }
                
                /**
                 * Unsets the "Pay_Settle_PhyDate" attribute
                 */
                public void unsetPaySettlePhyDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(PAYSETTLEPHYDATE$50);
                    }
                }
                
                /**
                 * Gets the "Pay_Settle_LogDate" attribute
                 */
                public java.lang.String getPaySettleLogDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PAYSETTLELOGDATE$52);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pay_Settle_LogDate" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetPaySettleLogDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PAYSETTLELOGDATE$52);
                      return target;
                    }
                }
                
                /**
                 * True if has "Pay_Settle_LogDate" attribute
                 */
                public boolean isSetPaySettleLogDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(PAYSETTLELOGDATE$52) != null;
                    }
                }
                
                /**
                 * Sets the "Pay_Settle_LogDate" attribute
                 */
                public void setPaySettleLogDate(java.lang.String paySettleLogDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PAYSETTLELOGDATE$52);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PAYSETTLELOGDATE$52);
                      }
                      target.setStringValue(paySettleLogDate);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pay_Settle_LogDate" attribute
                 */
                public void xsetPaySettleLogDate(org.apache.xmlbeans.XmlString paySettleLogDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PAYSETTLELOGDATE$52);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PAYSETTLELOGDATE$52);
                      }
                      target.set(paySettleLogDate);
                    }
                }
                
                /**
                 * Unsets the "Pay_Settle_LogDate" attribute
                 */
                public void unsetPaySettleLogDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(PAYSETTLELOGDATE$52);
                    }
                }
                
                /**
                 * Gets the "Status" attribute
                 */
                public java.lang.String getStatus()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STATUS$54);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Status" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetStatus()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STATUS$54);
                      return target;
                    }
                }
                
                /**
                 * True if has "Status" attribute
                 */
                public boolean isSetStatus()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(STATUS$54) != null;
                    }
                }
                
                /**
                 * Sets the "Status" attribute
                 */
                public void setStatus(java.lang.String status)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STATUS$54);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(STATUS$54);
                      }
                      target.setStringValue(status);
                    }
                }
                
                /**
                 * Sets (as xml) the "Status" attribute
                 */
                public void xsetStatus(org.apache.xmlbeans.XmlString status)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STATUS$54);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(STATUS$54);
                      }
                      target.set(status);
                    }
                }
                
                /**
                 * Unsets the "Status" attribute
                 */
                public void unsetStatus()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(STATUS$54);
                    }
                }
                
                /**
                 * Gets the "CU_Title" attribute
                 */
                public java.lang.String getCUTitle()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUTITLE$56);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_Title" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUTitle()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUTITLE$56);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_Title" attribute
                 */
                public boolean isSetCUTitle()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUTITLE$56) != null;
                    }
                }
                
                /**
                 * Sets the "CU_Title" attribute
                 */
                public void setCUTitle(java.lang.String cuTitle)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUTITLE$56);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUTITLE$56);
                      }
                      target.setStringValue(cuTitle);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_Title" attribute
                 */
                public void xsetCUTitle(org.apache.xmlbeans.XmlString cuTitle)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUTITLE$56);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUTITLE$56);
                      }
                      target.set(cuTitle);
                    }
                }
                
                /**
                 * Unsets the "CU_Title" attribute
                 */
                public void unsetCUTitle()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUTITLE$56);
                    }
                }
                
                /**
                 * Gets the "CU_Name" attribute
                 */
                public java.lang.String getCUName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUNAME$58);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_Name" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUNAME$58);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_Name" attribute
                 */
                public boolean isSetCUName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUNAME$58) != null;
                    }
                }
                
                /**
                 * Sets the "CU_Name" attribute
                 */
                public void setCUName(java.lang.String cuName)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUNAME$58);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUNAME$58);
                      }
                      target.setStringValue(cuName);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_Name" attribute
                 */
                public void xsetCUName(org.apache.xmlbeans.XmlString cuName)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUNAME$58);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUNAME$58);
                      }
                      target.set(cuName);
                    }
                }
                
                /**
                 * Unsets the "CU_Name" attribute
                 */
                public void unsetCUName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUNAME$58);
                    }
                }
                
                /**
                 * Gets the "CU_CCCNO" attribute
                 */
                public java.lang.String getCUCCCNO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCCCNO$60);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_CCCNO" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUCCCNO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCCCNO$60);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_CCCNO" attribute
                 */
                public boolean isSetCUCCCNO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUCCCNO$60) != null;
                    }
                }
                
                /**
                 * Sets the "CU_CCCNO" attribute
                 */
                public void setCUCCCNO(java.lang.String cucccno)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCCCNO$60);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUCCCNO$60);
                      }
                      target.setStringValue(cucccno);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_CCCNO" attribute
                 */
                public void xsetCUCCCNO(org.apache.xmlbeans.XmlString cucccno)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCCCNO$60);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUCCCNO$60);
                      }
                      target.set(cucccno);
                    }
                }
                
                /**
                 * Unsets the "CU_CCCNO" attribute
                 */
                public void unsetCUCCCNO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUCCCNO$60);
                    }
                }
                
                /**
                 * Gets the "CU_Tel" attribute
                 */
                public java.lang.String getCUTel()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUTEL$62);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_Tel" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUTel()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUTEL$62);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_Tel" attribute
                 */
                public boolean isSetCUTel()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUTEL$62) != null;
                    }
                }
                
                /**
                 * Sets the "CU_Tel" attribute
                 */
                public void setCUTel(java.lang.String cuTel)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUTEL$62);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUTEL$62);
                      }
                      target.setStringValue(cuTel);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_Tel" attribute
                 */
                public void xsetCUTel(org.apache.xmlbeans.XmlString cuTel)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUTEL$62);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUTEL$62);
                      }
                      target.set(cuTel);
                    }
                }
                
                /**
                 * Unsets the "CU_Tel" attribute
                 */
                public void unsetCUTel()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUTEL$62);
                    }
                }
                
                /**
                 * Gets the "CU_Prime" attribute
                 */
                public java.lang.String getCUPrime()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUPRIME$64);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_Prime" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUPrime()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUPRIME$64);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_Prime" attribute
                 */
                public boolean isSetCUPrime()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUPRIME$64) != null;
                    }
                }
                
                /**
                 * Sets the "CU_Prime" attribute
                 */
                public void setCUPrime(java.lang.String cuPrime)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUPRIME$64);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUPRIME$64);
                      }
                      target.setStringValue(cuPrime);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_Prime" attribute
                 */
                public void xsetCUPrime(org.apache.xmlbeans.XmlString cuPrime)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUPRIME$64);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUPRIME$64);
                      }
                      target.set(cuPrime);
                    }
                }
                
                /**
                 * Unsets the "CU_Prime" attribute
                 */
                public void unsetCUPrime()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUPRIME$64);
                    }
                }
                
                /**
                 * Gets the "CU_FaxNo" attribute
                 */
                public java.lang.String getCUFaxNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUFAXNO$66);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_FaxNo" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUFaxNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUFAXNO$66);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_FaxNo" attribute
                 */
                public boolean isSetCUFaxNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUFAXNO$66) != null;
                    }
                }
                
                /**
                 * Sets the "CU_FaxNo" attribute
                 */
                public void setCUFaxNo(java.lang.String cuFaxNo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUFAXNO$66);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUFAXNO$66);
                      }
                      target.setStringValue(cuFaxNo);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_FaxNo" attribute
                 */
                public void xsetCUFaxNo(org.apache.xmlbeans.XmlString cuFaxNo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUFAXNO$66);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUFAXNO$66);
                      }
                      target.set(cuFaxNo);
                    }
                }
                
                /**
                 * Unsets the "CU_FaxNo" attribute
                 */
                public void unsetCUFaxNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUFAXNO$66);
                    }
                }
                
                /**
                 * Gets the "CU_MemType" attribute
                 */
                public java.lang.String getCUMemType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUMEMTYPE$68);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_MemType" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUMemType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUMEMTYPE$68);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_MemType" attribute
                 */
                public boolean isSetCUMemType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUMEMTYPE$68) != null;
                    }
                }
                
                /**
                 * Sets the "CU_MemType" attribute
                 */
                public void setCUMemType(java.lang.String cuMemType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUMEMTYPE$68);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUMEMTYPE$68);
                      }
                      target.setStringValue(cuMemType);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_MemType" attribute
                 */
                public void xsetCUMemType(org.apache.xmlbeans.XmlString cuMemType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUMEMTYPE$68);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUMEMTYPE$68);
                      }
                      target.set(cuMemType);
                    }
                }
                
                /**
                 * Unsets the "CU_MemType" attribute
                 */
                public void unsetCUMemType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUMEMTYPE$68);
                    }
                }
                
                /**
                 * Gets the "CU_ContPer" attribute
                 */
                public java.lang.String getCUContPer()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCONTPER$70);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_ContPer" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUContPer()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCONTPER$70);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_ContPer" attribute
                 */
                public boolean isSetCUContPer()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUCONTPER$70) != null;
                    }
                }
                
                /**
                 * Sets the "CU_ContPer" attribute
                 */
                public void setCUContPer(java.lang.String cuContPer)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCONTPER$70);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUCONTPER$70);
                      }
                      target.setStringValue(cuContPer);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_ContPer" attribute
                 */
                public void xsetCUContPer(org.apache.xmlbeans.XmlString cuContPer)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCONTPER$70);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUCONTPER$70);
                      }
                      target.set(cuContPer);
                    }
                }
                
                /**
                 * Unsets the "CU_ContPer" attribute
                 */
                public void unsetCUContPer()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUCONTPER$70);
                    }
                }
                
                /**
                 * Gets the "CU_MemberNo" attribute
                 */
                public java.lang.String getCUMemberNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUMEMBERNO$72);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_MemberNo" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUMemberNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUMEMBERNO$72);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_MemberNo" attribute
                 */
                public boolean isSetCUMemberNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUMEMBERNO$72) != null;
                    }
                }
                
                /**
                 * Sets the "CU_MemberNo" attribute
                 */
                public void setCUMemberNo(java.lang.String cuMemberNo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUMEMBERNO$72);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUMEMBERNO$72);
                      }
                      target.setStringValue(cuMemberNo);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_MemberNo" attribute
                 */
                public void xsetCUMemberNo(org.apache.xmlbeans.XmlString cuMemberNo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUMEMBERNO$72);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUMEMBERNO$72);
                      }
                      target.set(cuMemberNo);
                    }
                }
                
                /**
                 * Unsets the "CU_MemberNo" attribute
                 */
                public void unsetCUMemberNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUMEMBERNO$72);
                    }
                }
                
                /**
                 * Gets the "CU_InterCom" attribute
                 */
                public java.lang.String getCUInterCom()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUINTERCOM$74);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_InterCom" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUInterCom()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUINTERCOM$74);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_InterCom" attribute
                 */
                public boolean isSetCUInterCom()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUINTERCOM$74) != null;
                    }
                }
                
                /**
                 * Sets the "CU_InterCom" attribute
                 */
                public void setCUInterCom(java.lang.String cuInterCom)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUINTERCOM$74);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUINTERCOM$74);
                      }
                      target.setStringValue(cuInterCom);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_InterCom" attribute
                 */
                public void xsetCUInterCom(org.apache.xmlbeans.XmlString cuInterCom)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUINTERCOM$74);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUINTERCOM$74);
                      }
                      target.set(cuInterCom);
                    }
                }
                
                /**
                 * Unsets the "CU_InterCom" attribute
                 */
                public void unsetCUInterCom()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUINTERCOM$74);
                    }
                }
                
                /**
                 * Gets the "CU_IDBusType" attribute
                 */
                public java.lang.String getCUIDBusType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUIDBUSTYPE$76);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_IDBusType" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUIDBusType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUIDBUSTYPE$76);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_IDBusType" attribute
                 */
                public boolean isSetCUIDBusType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUIDBUSTYPE$76) != null;
                    }
                }
                
                /**
                 * Sets the "CU_IDBusType" attribute
                 */
                public void setCUIDBusType(java.lang.String cuidBusType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUIDBUSTYPE$76);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUIDBUSTYPE$76);
                      }
                      target.setStringValue(cuidBusType);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_IDBusType" attribute
                 */
                public void xsetCUIDBusType(org.apache.xmlbeans.XmlString cuidBusType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUIDBUSTYPE$76);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUIDBUSTYPE$76);
                      }
                      target.set(cuidBusType);
                    }
                }
                
                /**
                 * Unsets the "CU_IDBusType" attribute
                 */
                public void unsetCUIDBusType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUIDBUSTYPE$76);
                    }
                }
                
                /**
                 * Gets the "CU_IDBusNo" attribute
                 */
                public java.lang.String getCUIDBusNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUIDBUSNO$78);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_IDBusNo" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUIDBusNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUIDBUSNO$78);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_IDBusNo" attribute
                 */
                public boolean isSetCUIDBusNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUIDBUSNO$78) != null;
                    }
                }
                
                /**
                 * Sets the "CU_IDBusNo" attribute
                 */
                public void setCUIDBusNo(java.lang.String cuidBusNo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUIDBUSNO$78);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUIDBUSNO$78);
                      }
                      target.setStringValue(cuidBusNo);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_IDBusNo" attribute
                 */
                public void xsetCUIDBusNo(org.apache.xmlbeans.XmlString cuidBusNo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUIDBUSNO$78);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUIDBUSNO$78);
                      }
                      target.set(cuidBusNo);
                    }
                }
                
                /**
                 * Unsets the "CU_IDBusNo" attribute
                 */
                public void unsetCUIDBusNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUIDBUSNO$78);
                    }
                }
                
                /**
                 * Gets the "CU_Remark" attribute
                 */
                public java.lang.String getCURemark()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUREMARK$80);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_Remark" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCURemark()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUREMARK$80);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_Remark" attribute
                 */
                public boolean isSetCURemark()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUREMARK$80) != null;
                    }
                }
                
                /**
                 * Sets the "CU_Remark" attribute
                 */
                public void setCURemark(java.lang.String cuRemark)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUREMARK$80);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUREMARK$80);
                      }
                      target.setStringValue(cuRemark);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_Remark" attribute
                 */
                public void xsetCURemark(org.apache.xmlbeans.XmlString cuRemark)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUREMARK$80);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUREMARK$80);
                      }
                      target.set(cuRemark);
                    }
                }
                
                /**
                 * Unsets the "CU_Remark" attribute
                 */
                public void unsetCURemark()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUREMARK$80);
                    }
                }
                
                /**
                 * Gets the "CU_Request_Date" attribute
                 */
                public java.lang.String getCURequestDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUREQUESTDATE$82);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_Request_Date" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCURequestDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUREQUESTDATE$82);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_Request_Date" attribute
                 */
                public boolean isSetCURequestDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUREQUESTDATE$82) != null;
                    }
                }
                
                /**
                 * Sets the "CU_Request_Date" attribute
                 */
                public void setCURequestDate(java.lang.String cuRequestDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUREQUESTDATE$82);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUREQUESTDATE$82);
                      }
                      target.setStringValue(cuRequestDate);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_Request_Date" attribute
                 */
                public void xsetCURequestDate(org.apache.xmlbeans.XmlString cuRequestDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUREQUESTDATE$82);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUREQUESTDATE$82);
                      }
                      target.set(cuRequestDate);
                    }
                }
                
                /**
                 * Unsets the "CU_Request_Date" attribute
                 */
                public void unsetCURequestDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUREQUESTDATE$82);
                    }
                }
                
                /**
                 * Gets the "CU_Request_Time" attribute
                 */
                public java.lang.String getCURequestTime()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUREQUESTTIME$84);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_Request_Time" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCURequestTime()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUREQUESTTIME$84);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_Request_Time" attribute
                 */
                public boolean isSetCURequestTime()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUREQUESTTIME$84) != null;
                    }
                }
                
                /**
                 * Sets the "CU_Request_Time" attribute
                 */
                public void setCURequestTime(java.lang.String cuRequestTime)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUREQUESTTIME$84);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUREQUESTTIME$84);
                      }
                      target.setStringValue(cuRequestTime);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_Request_Time" attribute
                 */
                public void xsetCURequestTime(org.apache.xmlbeans.XmlString cuRequestTime)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUREQUESTTIME$84);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUREQUESTTIME$84);
                      }
                      target.set(cuRequestTime);
                    }
                }
                
                /**
                 * Unsets the "CU_Request_Time" attribute
                 */
                public void unsetCURequestTime()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUREQUESTTIME$84);
                    }
                }
                
                /**
                 * Gets the "CU_PosBadDebt" attribute
                 */
                public java.lang.String getCUPosBadDebt()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUPOSBADDEBT$86);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_PosBadDebt" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUPosBadDebt()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUPOSBADDEBT$86);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_PosBadDebt" attribute
                 */
                public boolean isSetCUPosBadDebt()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUPOSBADDEBT$86) != null;
                    }
                }
                
                /**
                 * Sets the "CU_PosBadDebt" attribute
                 */
                public void setCUPosBadDebt(java.lang.String cuPosBadDebt)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUPOSBADDEBT$86);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUPOSBADDEBT$86);
                      }
                      target.setStringValue(cuPosBadDebt);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_PosBadDebt" attribute
                 */
                public void xsetCUPosBadDebt(org.apache.xmlbeans.XmlString cuPosBadDebt)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUPOSBADDEBT$86);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUPOSBADDEBT$86);
                      }
                      target.set(cuPosBadDebt);
                    }
                }
                
                /**
                 * Unsets the "CU_PosBadDebt" attribute
                 */
                public void unsetCUPosBadDebt()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUPOSBADDEBT$86);
                    }
                }
                
                /**
                 * Gets the "CU_CFLATUNIT" attribute
                 */
                public java.lang.String getCUCFLATUNIT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCFLATUNIT$88);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_CFLATUNIT" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUCFLATUNIT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCFLATUNIT$88);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_CFLATUNIT" attribute
                 */
                public boolean isSetCUCFLATUNIT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUCFLATUNIT$88) != null;
                    }
                }
                
                /**
                 * Sets the "CU_CFLATUNIT" attribute
                 */
                public void setCUCFLATUNIT(java.lang.String cucflatunit)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCFLATUNIT$88);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUCFLATUNIT$88);
                      }
                      target.setStringValue(cucflatunit);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_CFLATUNIT" attribute
                 */
                public void xsetCUCFLATUNIT(org.apache.xmlbeans.XmlString cucflatunit)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCFLATUNIT$88);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUCFLATUNIT$88);
                      }
                      target.set(cucflatunit);
                    }
                }
                
                /**
                 * Unsets the "CU_CFLATUNIT" attribute
                 */
                public void unsetCUCFLATUNIT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUCFLATUNIT$88);
                    }
                }
                
                /**
                 * Gets the "CU_CFLOOR" attribute
                 */
                public java.lang.String getCUCFLOOR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCFLOOR$90);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_CFLOOR" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUCFLOOR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCFLOOR$90);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_CFLOOR" attribute
                 */
                public boolean isSetCUCFLOOR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUCFLOOR$90) != null;
                    }
                }
                
                /**
                 * Sets the "CU_CFLOOR" attribute
                 */
                public void setCUCFLOOR(java.lang.String cucfloor)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCFLOOR$90);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUCFLOOR$90);
                      }
                      target.setStringValue(cucfloor);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_CFLOOR" attribute
                 */
                public void xsetCUCFLOOR(org.apache.xmlbeans.XmlString cucfloor)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCFLOOR$90);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUCFLOOR$90);
                      }
                      target.set(cucfloor);
                    }
                }
                
                /**
                 * Unsets the "CU_CFLOOR" attribute
                 */
                public void unsetCUCFLOOR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUCFLOOR$90);
                    }
                }
                
                /**
                 * Gets the "CU_CBLDG" attribute
                 */
                public java.lang.String getCUCBLDG()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCBLDG$92);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_CBLDG" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUCBLDG()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCBLDG$92);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_CBLDG" attribute
                 */
                public boolean isSetCUCBLDG()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUCBLDG$92) != null;
                    }
                }
                
                /**
                 * Sets the "CU_CBLDG" attribute
                 */
                public void setCUCBLDG(java.lang.String cucbldg)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCBLDG$92);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUCBLDG$92);
                      }
                      target.setStringValue(cucbldg);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_CBLDG" attribute
                 */
                public void xsetCUCBLDG(org.apache.xmlbeans.XmlString cucbldg)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCBLDG$92);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUCBLDG$92);
                      }
                      target.set(cucbldg);
                    }
                }
                
                /**
                 * Unsets the "CU_CBLDG" attribute
                 */
                public void unsetCUCBLDG()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUCBLDG$92);
                    }
                }
                
                /**
                 * Gets the "CU_CPOBOX" attribute
                 */
                public java.lang.String getCUCPOBOX()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCPOBOX$94);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_CPOBOX" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUCPOBOX()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCPOBOX$94);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_CPOBOX" attribute
                 */
                public boolean isSetCUCPOBOX()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUCPOBOX$94) != null;
                    }
                }
                
                /**
                 * Sets the "CU_CPOBOX" attribute
                 */
                public void setCUCPOBOX(java.lang.String cucpobox)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCPOBOX$94);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUCPOBOX$94);
                      }
                      target.setStringValue(cucpobox);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_CPOBOX" attribute
                 */
                public void xsetCUCPOBOX(org.apache.xmlbeans.XmlString cucpobox)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCPOBOX$94);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUCPOBOX$94);
                      }
                      target.set(cucpobox);
                    }
                }
                
                /**
                 * Unsets the "CU_CPOBOX" attribute
                 */
                public void unsetCUCPOBOX()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUCPOBOX$94);
                    }
                }
                
                /**
                 * Gets the "CU_CHSESTNO" attribute
                 */
                public java.lang.String getCUCHSESTNO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCHSESTNO$96);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_CHSESTNO" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUCHSESTNO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCHSESTNO$96);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_CHSESTNO" attribute
                 */
                public boolean isSetCUCHSESTNO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUCHSESTNO$96) != null;
                    }
                }
                
                /**
                 * Sets the "CU_CHSESTNO" attribute
                 */
                public void setCUCHSESTNO(java.lang.String cuchsestno)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCHSESTNO$96);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUCHSESTNO$96);
                      }
                      target.setStringValue(cuchsestno);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_CHSESTNO" attribute
                 */
                public void xsetCUCHSESTNO(org.apache.xmlbeans.XmlString cuchsestno)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCHSESTNO$96);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUCHSESTNO$96);
                      }
                      target.set(cuchsestno);
                    }
                }
                
                /**
                 * Unsets the "CU_CHSESTNO" attribute
                 */
                public void unsetCUCHSESTNO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUCHSESTNO$96);
                    }
                }
                
                /**
                 * Gets the "CU_CLOTLDNO" attribute
                 */
                public java.lang.String getCUCLOTLDNO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCLOTLDNO$98);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_CLOTLDNO" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUCLOTLDNO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCLOTLDNO$98);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_CLOTLDNO" attribute
                 */
                public boolean isSetCUCLOTLDNO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUCLOTLDNO$98) != null;
                    }
                }
                
                /**
                 * Sets the "CU_CLOTLDNO" attribute
                 */
                public void setCUCLOTLDNO(java.lang.String cuclotldno)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCLOTLDNO$98);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUCLOTLDNO$98);
                      }
                      target.setStringValue(cuclotldno);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_CLOTLDNO" attribute
                 */
                public void xsetCUCLOTLDNO(org.apache.xmlbeans.XmlString cuclotldno)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCLOTLDNO$98);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUCLOTLDNO$98);
                      }
                      target.set(cuclotldno);
                    }
                }
                
                /**
                 * Unsets the "CU_CLOTLDNO" attribute
                 */
                public void unsetCUCLOTLDNO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUCLOTLDNO$98);
                    }
                }
                
                /**
                 * Gets the "CU_CSTNAME" attribute
                 */
                public java.lang.String getCUCSTNAME()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCSTNAME$100);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_CSTNAME" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUCSTNAME()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCSTNAME$100);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_CSTNAME" attribute
                 */
                public boolean isSetCUCSTNAME()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUCSTNAME$100) != null;
                    }
                }
                
                /**
                 * Sets the "CU_CSTNAME" attribute
                 */
                public void setCUCSTNAME(java.lang.String cucstname)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCSTNAME$100);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUCSTNAME$100);
                      }
                      target.setStringValue(cucstname);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_CSTNAME" attribute
                 */
                public void xsetCUCSTNAME(org.apache.xmlbeans.XmlString cucstname)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCSTNAME$100);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUCSTNAME$100);
                      }
                      target.set(cucstname);
                    }
                }
                
                /**
                 * Unsets the "CU_CSTNAME" attribute
                 */
                public void unsetCUCSTNAME()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUCSTNAME$100);
                    }
                }
                
                /**
                 * Gets the "CU_CSTCAT" attribute
                 */
                public java.lang.String getCUCSTCAT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCSTCAT$102);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_CSTCAT" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUCSTCAT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCSTCAT$102);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_CSTCAT" attribute
                 */
                public boolean isSetCUCSTCAT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUCSTCAT$102) != null;
                    }
                }
                
                /**
                 * Sets the "CU_CSTCAT" attribute
                 */
                public void setCUCSTCAT(java.lang.String cucstcat)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCSTCAT$102);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUCSTCAT$102);
                      }
                      target.setStringValue(cucstcat);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_CSTCAT" attribute
                 */
                public void xsetCUCSTCAT(org.apache.xmlbeans.XmlString cucstcat)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCSTCAT$102);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUCSTCAT$102);
                      }
                      target.set(cucstcat);
                    }
                }
                
                /**
                 * Unsets the "CU_CSTCAT" attribute
                 */
                public void unsetCUCSTCAT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUCSTCAT$102);
                    }
                }
                
                /**
                 * Gets the "CU_CSECT" attribute
                 */
                public java.lang.String getCUCSECT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCSECT$104);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_CSECT" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUCSECT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCSECT$104);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_CSECT" attribute
                 */
                public boolean isSetCUCSECT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUCSECT$104) != null;
                    }
                }
                
                /**
                 * Sets the "CU_CSECT" attribute
                 */
                public void setCUCSECT(java.lang.String cucsect)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCSECT$104);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUCSECT$104);
                      }
                      target.setStringValue(cucsect);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_CSECT" attribute
                 */
                public void xsetCUCSECT(org.apache.xmlbeans.XmlString cucsect)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCSECT$104);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUCSECT$104);
                      }
                      target.set(cucsect);
                    }
                }
                
                /**
                 * Unsets the "CU_CSECT" attribute
                 */
                public void unsetCUCSECT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUCSECT$104);
                    }
                }
                
                /**
                 * Gets the "CU_CDISTR" attribute
                 */
                public java.lang.String getCUCDISTR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCDISTR$106);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_CDISTR" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUCDISTR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCDISTR$106);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_CDISTR" attribute
                 */
                public boolean isSetCUCDISTR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUCDISTR$106) != null;
                    }
                }
                
                /**
                 * Sets the "CU_CDISTR" attribute
                 */
                public void setCUCDISTR(java.lang.String cucdistr)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCDISTR$106);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUCDISTR$106);
                      }
                      target.setStringValue(cucdistr);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_CDISTR" attribute
                 */
                public void xsetCUCDISTR(org.apache.xmlbeans.XmlString cucdistr)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCDISTR$106);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUCDISTR$106);
                      }
                      target.set(cucdistr);
                    }
                }
                
                /**
                 * Unsets the "CU_CDISTR" attribute
                 */
                public void unsetCUCDISTR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUCDISTR$106);
                    }
                }
                
                /**
                 * Gets the "CU_CAREA" attribute
                 */
                public java.lang.String getCUCAREA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCAREA$108);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_CAREA" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUCAREA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCAREA$108);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_CAREA" attribute
                 */
                public boolean isSetCUCAREA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUCAREA$108) != null;
                    }
                }
                
                /**
                 * Sets the "CU_CAREA" attribute
                 */
                public void setCUCAREA(java.lang.String cucarea)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCAREA$108);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUCAREA$108);
                      }
                      target.setStringValue(cucarea);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_CAREA" attribute
                 */
                public void xsetCUCAREA(org.apache.xmlbeans.XmlString cucarea)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCAREA$108);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUCAREA$108);
                      }
                      target.set(cucarea);
                    }
                }
                
                /**
                 * Unsets the "CU_CAREA" attribute
                 */
                public void unsetCUCAREA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUCAREA$108);
                    }
                }
                
                /**
                 * Gets the "CU_CPOSTREGN" attribute
                 */
                public java.lang.String getCUCPOSTREGN()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCPOSTREGN$110);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_CPOSTREGN" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUCPOSTREGN()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCPOSTREGN$110);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_CPOSTREGN" attribute
                 */
                public boolean isSetCUCPOSTREGN()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUCPOSTREGN$110) != null;
                    }
                }
                
                /**
                 * Sets the "CU_CPOSTREGN" attribute
                 */
                public void setCUCPOSTREGN(java.lang.String cucpostregn)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCPOSTREGN$110);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUCPOSTREGN$110);
                      }
                      target.setStringValue(cucpostregn);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_CPOSTREGN" attribute
                 */
                public void xsetCUCPOSTREGN(org.apache.xmlbeans.XmlString cucpostregn)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCPOSTREGN$110);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUCPOSTREGN$110);
                      }
                      target.set(cucpostregn);
                    }
                }
                
                /**
                 * Unsets the "CU_CPOSTREGN" attribute
                 */
                public void unsetCUCPOSTREGN()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUCPOSTREGN$110);
                    }
                }
                
                /**
                 * Gets the "CU_CPOSTCD" attribute
                 */
                public java.lang.String getCUCPOSTCD()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCPOSTCD$112);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_CPOSTCD" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUCPOSTCD()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCPOSTCD$112);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_CPOSTCD" attribute
                 */
                public boolean isSetCUCPOSTCD()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUCPOSTCD$112) != null;
                    }
                }
                
                /**
                 * Sets the "CU_CPOSTCD" attribute
                 */
                public void setCUCPOSTCD(java.lang.String cucpostcd)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCPOSTCD$112);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUCPOSTCD$112);
                      }
                      target.setStringValue(cucpostcd);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_CPOSTCD" attribute
                 */
                public void xsetCUCPOSTCD(org.apache.xmlbeans.XmlString cucpostcd)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCPOSTCD$112);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUCPOSTCD$112);
                      }
                      target.set(cucpostcd);
                    }
                }
                
                /**
                 * Unsets the "CU_CPOSTCD" attribute
                 */
                public void unsetCUCPOSTCD()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUCPOSTCD$112);
                    }
                }
                
                /**
                 * Gets the "CU_CCOUNTRY" attribute
                 */
                public java.lang.String getCUCCOUNTRY()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCCOUNTRY$114);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_CCOUNTRY" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUCCOUNTRY()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCCOUNTRY$114);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_CCOUNTRY" attribute
                 */
                public boolean isSetCUCCOUNTRY()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUCCOUNTRY$114) != null;
                    }
                }
                
                /**
                 * Sets the "CU_CCOUNTRY" attribute
                 */
                public void setCUCCOUNTRY(java.lang.String cuccountry)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCCOUNTRY$114);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUCCOUNTRY$114);
                      }
                      target.setStringValue(cuccountry);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_CCOUNTRY" attribute
                 */
                public void xsetCUCCOUNTRY(org.apache.xmlbeans.XmlString cuccountry)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCCOUNTRY$114);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUCCOUNTRY$114);
                      }
                      target.set(cuccountry);
                    }
                }
                
                /**
                 * Unsets the "CU_CCOUNTRY" attribute
                 */
                public void unsetCUCCOUNTRY()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUCCOUNTRY$114);
                    }
                }
                
                /**
                 * Gets the "CU_CATTETO" attribute
                 */
                public java.lang.String getCUCATTETO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCATTETO$116);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_CATTETO" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUCATTETO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCATTETO$116);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_CATTETO" attribute
                 */
                public boolean isSetCUCATTETO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUCATTETO$116) != null;
                    }
                }
                
                /**
                 * Sets the "CU_CATTETO" attribute
                 */
                public void setCUCATTETO(java.lang.String cucatteto)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCATTETO$116);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUCATTETO$116);
                      }
                      target.setStringValue(cucatteto);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_CATTETO" attribute
                 */
                public void xsetCUCATTETO(org.apache.xmlbeans.XmlString cucatteto)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCATTETO$116);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUCATTETO$116);
                      }
                      target.set(cucatteto);
                    }
                }
                
                /**
                 * Unsets the "CU_CATTETO" attribute
                 */
                public void unsetCUCATTETO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUCATTETO$116);
                    }
                }
                
                /**
                 * Gets the "CU_CCO" attribute
                 */
                public java.lang.String getCUCCO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCCO$118);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_CCO" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUCCO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCCO$118);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_CCO" attribute
                 */
                public boolean isSetCUCCO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUCCO$118) != null;
                    }
                }
                
                /**
                 * Sets the "CU_CCO" attribute
                 */
                public void setCUCCO(java.lang.String cucco)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUCCO$118);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUCCO$118);
                      }
                      target.setStringValue(cucco);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_CCO" attribute
                 */
                public void xsetCUCCO(org.apache.xmlbeans.XmlString cucco)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUCCO$118);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUCCO$118);
                      }
                      target.set(cucco);
                    }
                }
                
                /**
                 * Unsets the "CU_CCO" attribute
                 */
                public void unsetCUCCO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUCCO$118);
                    }
                }
                
                /**
                 * Gets the "CU_IFLATUNIT" attribute
                 */
                public java.lang.String getCUIFLATUNIT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUIFLATUNIT$120);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_IFLATUNIT" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUIFLATUNIT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUIFLATUNIT$120);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_IFLATUNIT" attribute
                 */
                public boolean isSetCUIFLATUNIT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUIFLATUNIT$120) != null;
                    }
                }
                
                /**
                 * Sets the "CU_IFLATUNIT" attribute
                 */
                public void setCUIFLATUNIT(java.lang.String cuiflatunit)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUIFLATUNIT$120);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUIFLATUNIT$120);
                      }
                      target.setStringValue(cuiflatunit);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_IFLATUNIT" attribute
                 */
                public void xsetCUIFLATUNIT(org.apache.xmlbeans.XmlString cuiflatunit)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUIFLATUNIT$120);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUIFLATUNIT$120);
                      }
                      target.set(cuiflatunit);
                    }
                }
                
                /**
                 * Unsets the "CU_IFLATUNIT" attribute
                 */
                public void unsetCUIFLATUNIT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUIFLATUNIT$120);
                    }
                }
                
                /**
                 * Gets the "CU_IFLOOR" attribute
                 */
                public java.lang.String getCUIFLOOR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUIFLOOR$122);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_IFLOOR" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUIFLOOR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUIFLOOR$122);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_IFLOOR" attribute
                 */
                public boolean isSetCUIFLOOR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUIFLOOR$122) != null;
                    }
                }
                
                /**
                 * Sets the "CU_IFLOOR" attribute
                 */
                public void setCUIFLOOR(java.lang.String cuifloor)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUIFLOOR$122);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUIFLOOR$122);
                      }
                      target.setStringValue(cuifloor);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_IFLOOR" attribute
                 */
                public void xsetCUIFLOOR(org.apache.xmlbeans.XmlString cuifloor)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUIFLOOR$122);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUIFLOOR$122);
                      }
                      target.set(cuifloor);
                    }
                }
                
                /**
                 * Unsets the "CU_IFLOOR" attribute
                 */
                public void unsetCUIFLOOR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUIFLOOR$122);
                    }
                }
                
                /**
                 * Gets the "CU_IBLDG" attribute
                 */
                public java.lang.String getCUIBLDG()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUIBLDG$124);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_IBLDG" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUIBLDG()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUIBLDG$124);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_IBLDG" attribute
                 */
                public boolean isSetCUIBLDG()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUIBLDG$124) != null;
                    }
                }
                
                /**
                 * Sets the "CU_IBLDG" attribute
                 */
                public void setCUIBLDG(java.lang.String cuibldg)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUIBLDG$124);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUIBLDG$124);
                      }
                      target.setStringValue(cuibldg);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_IBLDG" attribute
                 */
                public void xsetCUIBLDG(org.apache.xmlbeans.XmlString cuibldg)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUIBLDG$124);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUIBLDG$124);
                      }
                      target.set(cuibldg);
                    }
                }
                
                /**
                 * Unsets the "CU_IBLDG" attribute
                 */
                public void unsetCUIBLDG()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUIBLDG$124);
                    }
                }
                
                /**
                 * Gets the "CU_IPOBOX" attribute
                 */
                public java.lang.String getCUIPOBOX()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUIPOBOX$126);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_IPOBOX" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUIPOBOX()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUIPOBOX$126);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_IPOBOX" attribute
                 */
                public boolean isSetCUIPOBOX()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUIPOBOX$126) != null;
                    }
                }
                
                /**
                 * Sets the "CU_IPOBOX" attribute
                 */
                public void setCUIPOBOX(java.lang.String cuipobox)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUIPOBOX$126);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUIPOBOX$126);
                      }
                      target.setStringValue(cuipobox);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_IPOBOX" attribute
                 */
                public void xsetCUIPOBOX(org.apache.xmlbeans.XmlString cuipobox)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUIPOBOX$126);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUIPOBOX$126);
                      }
                      target.set(cuipobox);
                    }
                }
                
                /**
                 * Unsets the "CU_IPOBOX" attribute
                 */
                public void unsetCUIPOBOX()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUIPOBOX$126);
                    }
                }
                
                /**
                 * Gets the "CU_IHSESTNO" attribute
                 */
                public java.lang.String getCUIHSESTNO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUIHSESTNO$128);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_IHSESTNO" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUIHSESTNO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUIHSESTNO$128);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_IHSESTNO" attribute
                 */
                public boolean isSetCUIHSESTNO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUIHSESTNO$128) != null;
                    }
                }
                
                /**
                 * Sets the "CU_IHSESTNO" attribute
                 */
                public void setCUIHSESTNO(java.lang.String cuihsestno)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUIHSESTNO$128);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUIHSESTNO$128);
                      }
                      target.setStringValue(cuihsestno);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_IHSESTNO" attribute
                 */
                public void xsetCUIHSESTNO(org.apache.xmlbeans.XmlString cuihsestno)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUIHSESTNO$128);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUIHSESTNO$128);
                      }
                      target.set(cuihsestno);
                    }
                }
                
                /**
                 * Unsets the "CU_IHSESTNO" attribute
                 */
                public void unsetCUIHSESTNO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUIHSESTNO$128);
                    }
                }
                
                /**
                 * Gets the "CU_ILOTLDNO" attribute
                 */
                public java.lang.String getCUILOTLDNO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUILOTLDNO$130);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_ILOTLDNO" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUILOTLDNO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUILOTLDNO$130);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_ILOTLDNO" attribute
                 */
                public boolean isSetCUILOTLDNO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUILOTLDNO$130) != null;
                    }
                }
                
                /**
                 * Sets the "CU_ILOTLDNO" attribute
                 */
                public void setCUILOTLDNO(java.lang.String cuilotldno)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUILOTLDNO$130);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUILOTLDNO$130);
                      }
                      target.setStringValue(cuilotldno);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_ILOTLDNO" attribute
                 */
                public void xsetCUILOTLDNO(org.apache.xmlbeans.XmlString cuilotldno)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUILOTLDNO$130);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUILOTLDNO$130);
                      }
                      target.set(cuilotldno);
                    }
                }
                
                /**
                 * Unsets the "CU_ILOTLDNO" attribute
                 */
                public void unsetCUILOTLDNO()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUILOTLDNO$130);
                    }
                }
                
                /**
                 * Gets the "CU_ISTNAME" attribute
                 */
                public java.lang.String getCUISTNAME()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUISTNAME$132);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_ISTNAME" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUISTNAME()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUISTNAME$132);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_ISTNAME" attribute
                 */
                public boolean isSetCUISTNAME()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUISTNAME$132) != null;
                    }
                }
                
                /**
                 * Sets the "CU_ISTNAME" attribute
                 */
                public void setCUISTNAME(java.lang.String cuistname)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUISTNAME$132);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUISTNAME$132);
                      }
                      target.setStringValue(cuistname);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_ISTNAME" attribute
                 */
                public void xsetCUISTNAME(org.apache.xmlbeans.XmlString cuistname)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUISTNAME$132);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUISTNAME$132);
                      }
                      target.set(cuistname);
                    }
                }
                
                /**
                 * Unsets the "CU_ISTNAME" attribute
                 */
                public void unsetCUISTNAME()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUISTNAME$132);
                    }
                }
                
                /**
                 * Gets the "CU_ISTCAT" attribute
                 */
                public java.lang.String getCUISTCAT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUISTCAT$134);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_ISTCAT" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUISTCAT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUISTCAT$134);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_ISTCAT" attribute
                 */
                public boolean isSetCUISTCAT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUISTCAT$134) != null;
                    }
                }
                
                /**
                 * Sets the "CU_ISTCAT" attribute
                 */
                public void setCUISTCAT(java.lang.String cuistcat)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUISTCAT$134);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUISTCAT$134);
                      }
                      target.setStringValue(cuistcat);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_ISTCAT" attribute
                 */
                public void xsetCUISTCAT(org.apache.xmlbeans.XmlString cuistcat)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUISTCAT$134);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUISTCAT$134);
                      }
                      target.set(cuistcat);
                    }
                }
                
                /**
                 * Unsets the "CU_ISTCAT" attribute
                 */
                public void unsetCUISTCAT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUISTCAT$134);
                    }
                }
                
                /**
                 * Gets the "CU_ISECT" attribute
                 */
                public java.lang.String getCUISECT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUISECT$136);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_ISECT" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUISECT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUISECT$136);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_ISECT" attribute
                 */
                public boolean isSetCUISECT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUISECT$136) != null;
                    }
                }
                
                /**
                 * Sets the "CU_ISECT" attribute
                 */
                public void setCUISECT(java.lang.String cuisect)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUISECT$136);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUISECT$136);
                      }
                      target.setStringValue(cuisect);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_ISECT" attribute
                 */
                public void xsetCUISECT(org.apache.xmlbeans.XmlString cuisect)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUISECT$136);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUISECT$136);
                      }
                      target.set(cuisect);
                    }
                }
                
                /**
                 * Unsets the "CU_ISECT" attribute
                 */
                public void unsetCUISECT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUISECT$136);
                    }
                }
                
                /**
                 * Gets the "CU_IDISTR" attribute
                 */
                public java.lang.String getCUIDISTR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUIDISTR$138);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_IDISTR" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUIDISTR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUIDISTR$138);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_IDISTR" attribute
                 */
                public boolean isSetCUIDISTR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUIDISTR$138) != null;
                    }
                }
                
                /**
                 * Sets the "CU_IDISTR" attribute
                 */
                public void setCUIDISTR(java.lang.String cuidistr)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUIDISTR$138);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUIDISTR$138);
                      }
                      target.setStringValue(cuidistr);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_IDISTR" attribute
                 */
                public void xsetCUIDISTR(org.apache.xmlbeans.XmlString cuidistr)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUIDISTR$138);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUIDISTR$138);
                      }
                      target.set(cuidistr);
                    }
                }
                
                /**
                 * Unsets the "CU_IDISTR" attribute
                 */
                public void unsetCUIDISTR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUIDISTR$138);
                    }
                }
                
                /**
                 * Gets the "CU_IAREA" attribute
                 */
                public java.lang.String getCUIAREA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUIAREA$140);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_IAREA" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUIAREA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUIAREA$140);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_IAREA" attribute
                 */
                public boolean isSetCUIAREA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUIAREA$140) != null;
                    }
                }
                
                /**
                 * Sets the "CU_IAREA" attribute
                 */
                public void setCUIAREA(java.lang.String cuiarea)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUIAREA$140);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUIAREA$140);
                      }
                      target.setStringValue(cuiarea);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_IAREA" attribute
                 */
                public void xsetCUIAREA(org.apache.xmlbeans.XmlString cuiarea)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUIAREA$140);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUIAREA$140);
                      }
                      target.set(cuiarea);
                    }
                }
                
                /**
                 * Unsets the "CU_IAREA" attribute
                 */
                public void unsetCUIAREA()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUIAREA$140);
                    }
                }
                
                /**
                 * Gets the "CU_IPOSTREGN" attribute
                 */
                public java.lang.String getCUIPOSTREGN()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUIPOSTREGN$142);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_IPOSTREGN" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUIPOSTREGN()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUIPOSTREGN$142);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_IPOSTREGN" attribute
                 */
                public boolean isSetCUIPOSTREGN()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUIPOSTREGN$142) != null;
                    }
                }
                
                /**
                 * Sets the "CU_IPOSTREGN" attribute
                 */
                public void setCUIPOSTREGN(java.lang.String cuipostregn)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUIPOSTREGN$142);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUIPOSTREGN$142);
                      }
                      target.setStringValue(cuipostregn);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_IPOSTREGN" attribute
                 */
                public void xsetCUIPOSTREGN(org.apache.xmlbeans.XmlString cuipostregn)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUIPOSTREGN$142);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUIPOSTREGN$142);
                      }
                      target.set(cuipostregn);
                    }
                }
                
                /**
                 * Unsets the "CU_IPOSTREGN" attribute
                 */
                public void unsetCUIPOSTREGN()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUIPOSTREGN$142);
                    }
                }
                
                /**
                 * Gets the "CU_IPOSTCD" attribute
                 */
                public java.lang.String getCUIPOSTCD()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUIPOSTCD$144);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_IPOSTCD" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUIPOSTCD()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUIPOSTCD$144);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_IPOSTCD" attribute
                 */
                public boolean isSetCUIPOSTCD()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUIPOSTCD$144) != null;
                    }
                }
                
                /**
                 * Sets the "CU_IPOSTCD" attribute
                 */
                public void setCUIPOSTCD(java.lang.String cuipostcd)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUIPOSTCD$144);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUIPOSTCD$144);
                      }
                      target.setStringValue(cuipostcd);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_IPOSTCD" attribute
                 */
                public void xsetCUIPOSTCD(org.apache.xmlbeans.XmlString cuipostcd)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUIPOSTCD$144);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUIPOSTCD$144);
                      }
                      target.set(cuipostcd);
                    }
                }
                
                /**
                 * Unsets the "CU_IPOSTCD" attribute
                 */
                public void unsetCUIPOSTCD()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUIPOSTCD$144);
                    }
                }
                
                /**
                 * Gets the "CU_ICOUNTRY" attribute
                 */
                public java.lang.String getCUICOUNTRY()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUICOUNTRY$146);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_ICOUNTRY" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUICOUNTRY()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUICOUNTRY$146);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_ICOUNTRY" attribute
                 */
                public boolean isSetCUICOUNTRY()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUICOUNTRY$146) != null;
                    }
                }
                
                /**
                 * Sets the "CU_ICOUNTRY" attribute
                 */
                public void setCUICOUNTRY(java.lang.String cuicountry)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUICOUNTRY$146);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUICOUNTRY$146);
                      }
                      target.setStringValue(cuicountry);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_ICOUNTRY" attribute
                 */
                public void xsetCUICOUNTRY(org.apache.xmlbeans.XmlString cuicountry)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUICOUNTRY$146);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUICOUNTRY$146);
                      }
                      target.set(cuicountry);
                    }
                }
                
                /**
                 * Unsets the "CU_ICOUNTRY" attribute
                 */
                public void unsetCUICOUNTRY()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUICOUNTRY$146);
                    }
                }
                
                /**
                 * Gets the "CPEFLAG" attribute
                 */
                public java.lang.String getCPEFLAG()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CPEFLAG$148);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CPEFLAG" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCPEFLAG()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CPEFLAG$148);
                      return target;
                    }
                }
                
                /**
                 * True if has "CPEFLAG" attribute
                 */
                public boolean isSetCPEFLAG()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CPEFLAG$148) != null;
                    }
                }
                
                /**
                 * Sets the "CPEFLAG" attribute
                 */
                public void setCPEFLAG(java.lang.String cpeflag)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CPEFLAG$148);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CPEFLAG$148);
                      }
                      target.setStringValue(cpeflag);
                    }
                }
                
                /**
                 * Sets (as xml) the "CPEFLAG" attribute
                 */
                public void xsetCPEFLAG(org.apache.xmlbeans.XmlString cpeflag)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CPEFLAG$148);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CPEFLAG$148);
                      }
                      target.set(cpeflag);
                    }
                }
                
                /**
                 * Unsets the "CPEFLAG" attribute
                 */
                public void unsetCPEFLAG()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CPEFLAG$148);
                    }
                }
                
                /**
                 * Gets the "Trans_Discount" attribute
                 */
                public java.lang.String getTransDiscount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TRANSDISCOUNT$150);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Trans_Discount" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetTransDiscount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TRANSDISCOUNT$150);
                      return target;
                    }
                }
                
                /**
                 * True if has "Trans_Discount" attribute
                 */
                public boolean isSetTransDiscount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(TRANSDISCOUNT$150) != null;
                    }
                }
                
                /**
                 * Sets the "Trans_Discount" attribute
                 */
                public void setTransDiscount(java.lang.String transDiscount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TRANSDISCOUNT$150);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TRANSDISCOUNT$150);
                      }
                      target.setStringValue(transDiscount);
                    }
                }
                
                /**
                 * Sets (as xml) the "Trans_Discount" attribute
                 */
                public void xsetTransDiscount(org.apache.xmlbeans.XmlString transDiscount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TRANSDISCOUNT$150);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(TRANSDISCOUNT$150);
                      }
                      target.set(transDiscount);
                    }
                }
                
                /**
                 * Unsets the "Trans_Discount" attribute
                 */
                public void unsetTransDiscount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(TRANSDISCOUNT$150);
                    }
                }
                
                /**
                 * Gets the "Trans_Reason" attribute
                 */
                public java.lang.String getTransReason()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TRANSREASON$152);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Trans_Reason" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetTransReason()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TRANSREASON$152);
                      return target;
                    }
                }
                
                /**
                 * True if has "Trans_Reason" attribute
                 */
                public boolean isSetTransReason()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(TRANSREASON$152) != null;
                    }
                }
                
                /**
                 * Sets the "Trans_Reason" attribute
                 */
                public void setTransReason(java.lang.String transReason)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TRANSREASON$152);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TRANSREASON$152);
                      }
                      target.setStringValue(transReason);
                    }
                }
                
                /**
                 * Sets (as xml) the "Trans_Reason" attribute
                 */
                public void xsetTransReason(org.apache.xmlbeans.XmlString transReason)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TRANSREASON$152);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(TRANSREASON$152);
                      }
                      target.set(transReason);
                    }
                }
                
                /**
                 * Unsets the "Trans_Reason" attribute
                 */
                public void unsetTransReason()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(TRANSREASON$152);
                    }
                }
                
                /**
                 * Gets the "CPE_Work_Num" attribute
                 */
                public java.lang.String getCPEWorkNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CPEWORKNUM$154);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CPE_Work_Num" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCPEWorkNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CPEWORKNUM$154);
                      return target;
                    }
                }
                
                /**
                 * True if has "CPE_Work_Num" attribute
                 */
                public boolean isSetCPEWorkNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CPEWORKNUM$154) != null;
                    }
                }
                
                /**
                 * Sets the "CPE_Work_Num" attribute
                 */
                public void setCPEWorkNum(java.lang.String cpeWorkNum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CPEWORKNUM$154);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CPEWORKNUM$154);
                      }
                      target.setStringValue(cpeWorkNum);
                    }
                }
                
                /**
                 * Sets (as xml) the "CPE_Work_Num" attribute
                 */
                public void xsetCPEWorkNum(org.apache.xmlbeans.XmlString cpeWorkNum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CPEWORKNUM$154);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CPEWORKNUM$154);
                      }
                      target.set(cpeWorkNum);
                    }
                }
                
                /**
                 * Unsets the "CPE_Work_Num" attribute
                 */
                public void unsetCPEWorkNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CPEWORKNUM$154);
                    }
                }
                
                /**
                 * Gets the "Converted" attribute
                 */
                public java.lang.String getConverted()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CONVERTED$156);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Converted" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetConverted()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CONVERTED$156);
                      return target;
                    }
                }
                
                /**
                 * True if has "Converted" attribute
                 */
                public boolean isSetConverted()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CONVERTED$156) != null;
                    }
                }
                
                /**
                 * Sets the "Converted" attribute
                 */
                public void setConverted(java.lang.String converted)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CONVERTED$156);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CONVERTED$156);
                      }
                      target.setStringValue(converted);
                    }
                }
                
                /**
                 * Sets (as xml) the "Converted" attribute
                 */
                public void xsetConverted(org.apache.xmlbeans.XmlString converted)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CONVERTED$156);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CONVERTED$156);
                      }
                      target.set(converted);
                    }
                }
                
                /**
                 * Unsets the "Converted" attribute
                 */
                public void unsetConverted()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CONVERTED$156);
                    }
                }
                
                /**
                 * Gets the "Org_QU_Num" attribute
                 */
                public java.lang.String getOrgQUNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ORGQUNUM$158);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Org_QU_Num" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetOrgQUNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ORGQUNUM$158);
                      return target;
                    }
                }
                
                /**
                 * True if has "Org_QU_Num" attribute
                 */
                public boolean isSetOrgQUNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(ORGQUNUM$158) != null;
                    }
                }
                
                /**
                 * Sets the "Org_QU_Num" attribute
                 */
                public void setOrgQUNum(java.lang.String orgQUNum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ORGQUNUM$158);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ORGQUNUM$158);
                      }
                      target.setStringValue(orgQUNum);
                    }
                }
                
                /**
                 * Sets (as xml) the "Org_QU_Num" attribute
                 */
                public void xsetOrgQUNum(org.apache.xmlbeans.XmlString orgQUNum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ORGQUNUM$158);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(ORGQUNUM$158);
                      }
                      target.set(orgQUNum);
                    }
                }
                
                /**
                 * Unsets the "Org_QU_Num" attribute
                 */
                public void unsetOrgQUNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(ORGQUNUM$158);
                    }
                }
                
                /**
                 * Gets the "Ref_SM_No" attribute
                 */
                public java.lang.String getRefSMNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(REFSMNO$160);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Ref_SM_No" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetRefSMNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(REFSMNO$160);
                      return target;
                    }
                }
                
                /**
                 * True if has "Ref_SM_No" attribute
                 */
                public boolean isSetRefSMNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(REFSMNO$160) != null;
                    }
                }
                
                /**
                 * Sets the "Ref_SM_No" attribute
                 */
                public void setRefSMNo(java.lang.String refSMNo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(REFSMNO$160);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(REFSMNO$160);
                      }
                      target.setStringValue(refSMNo);
                    }
                }
                
                /**
                 * Sets (as xml) the "Ref_SM_No" attribute
                 */
                public void xsetRefSMNo(org.apache.xmlbeans.XmlString refSMNo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(REFSMNO$160);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(REFSMNO$160);
                      }
                      target.set(refSMNo);
                    }
                }
                
                /**
                 * Unsets the "Ref_SM_No" attribute
                 */
                public void unsetRefSMNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(REFSMNO$160);
                    }
                }
                
                /**
                 * Gets the "Invalidated_Flag" attribute
                 */
                public java.lang.String getInvalidatedFlag()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INVALIDATEDFLAG$162);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Invalidated_Flag" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetInvalidatedFlag()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INVALIDATEDFLAG$162);
                      return target;
                    }
                }
                
                /**
                 * True if has "Invalidated_Flag" attribute
                 */
                public boolean isSetInvalidatedFlag()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INVALIDATEDFLAG$162) != null;
                    }
                }
                
                /**
                 * Sets the "Invalidated_Flag" attribute
                 */
                public void setInvalidatedFlag(java.lang.String invalidatedFlag)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INVALIDATEDFLAG$162);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INVALIDATEDFLAG$162);
                      }
                      target.setStringValue(invalidatedFlag);
                    }
                }
                
                /**
                 * Sets (as xml) the "Invalidated_Flag" attribute
                 */
                public void xsetInvalidatedFlag(org.apache.xmlbeans.XmlString invalidatedFlag)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INVALIDATEDFLAG$162);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INVALIDATEDFLAG$162);
                      }
                      target.set(invalidatedFlag);
                    }
                }
                
                /**
                 * Unsets the "Invalidated_Flag" attribute
                 */
                public void unsetInvalidatedFlag()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INVALIDATEDFLAG$162);
                    }
                }
                
                /**
                 * Gets the "ExchangeFlag" attribute
                 */
                public java.lang.String getExchangeFlag()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(EXCHANGEFLAG$164);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "ExchangeFlag" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetExchangeFlag()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(EXCHANGEFLAG$164);
                      return target;
                    }
                }
                
                /**
                 * True if has "ExchangeFlag" attribute
                 */
                public boolean isSetExchangeFlag()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(EXCHANGEFLAG$164) != null;
                    }
                }
                
                /**
                 * Sets the "ExchangeFlag" attribute
                 */
                public void setExchangeFlag(java.lang.String exchangeFlag)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(EXCHANGEFLAG$164);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(EXCHANGEFLAG$164);
                      }
                      target.setStringValue(exchangeFlag);
                    }
                }
                
                /**
                 * Sets (as xml) the "ExchangeFlag" attribute
                 */
                public void xsetExchangeFlag(org.apache.xmlbeans.XmlString exchangeFlag)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(EXCHANGEFLAG$164);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(EXCHANGEFLAG$164);
                      }
                      target.set(exchangeFlag);
                    }
                }
                
                /**
                 * Unsets the "ExchangeFlag" attribute
                 */
                public void unsetExchangeFlag()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(EXCHANGEFLAG$164);
                    }
                }
                
                /**
                 * Gets the "Download" attribute
                 */
                public java.lang.String getDownload()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DOWNLOAD$166);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Download" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetDownload()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DOWNLOAD$166);
                      return target;
                    }
                }
                
                /**
                 * True if has "Download" attribute
                 */
                public boolean isSetDownload()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(DOWNLOAD$166) != null;
                    }
                }
                
                /**
                 * Sets the "Download" attribute
                 */
                public void setDownload(java.lang.String download)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DOWNLOAD$166);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DOWNLOAD$166);
                      }
                      target.setStringValue(download);
                    }
                }
                
                /**
                 * Sets (as xml) the "Download" attribute
                 */
                public void xsetDownload(org.apache.xmlbeans.XmlString download)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DOWNLOAD$166);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(DOWNLOAD$166);
                      }
                      target.set(download);
                    }
                }
                
                /**
                 * Unsets the "Download" attribute
                 */
                public void unsetDownload()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(DOWNLOAD$166);
                    }
                }
                
                /**
                 * Gets the "CU_Email" attribute
                 */
                public java.lang.String getCUEmail()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUEMAIL$168);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_Email" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUEmail()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUEMAIL$168);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_Email" attribute
                 */
                public boolean isSetCUEmail()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUEMAIL$168) != null;
                    }
                }
                
                /**
                 * Sets the "CU_Email" attribute
                 */
                public void setCUEmail(java.lang.String cuEmail)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUEMAIL$168);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUEMAIL$168);
                      }
                      target.setStringValue(cuEmail);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_Email" attribute
                 */
                public void xsetCUEmail(org.apache.xmlbeans.XmlString cuEmail)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUEMAIL$168);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUEMAIL$168);
                      }
                      target.set(cuEmail);
                    }
                }
                
                /**
                 * Unsets the "CU_Email" attribute
                 */
                public void unsetCUEmail()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUEMAIL$168);
                    }
                }
                
                /**
                 * Gets the "Store_Type" attribute
                 */
                public java.lang.String getStoreType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STORETYPE$170);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Store_Type" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetStoreType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STORETYPE$170);
                      return target;
                    }
                }
                
                /**
                 * True if has "Store_Type" attribute
                 */
                public boolean isSetStoreType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(STORETYPE$170) != null;
                    }
                }
                
                /**
                 * Sets the "Store_Type" attribute
                 */
                public void setStoreType(java.lang.String storeType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STORETYPE$170);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(STORETYPE$170);
                      }
                      target.setStringValue(storeType);
                    }
                }
                
                /**
                 * Sets (as xml) the "Store_Type" attribute
                 */
                public void xsetStoreType(org.apache.xmlbeans.XmlString storeType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STORETYPE$170);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(STORETYPE$170);
                      }
                      target.set(storeType);
                    }
                }
                
                /**
                 * Unsets the "Store_Type" attribute
                 */
                public void unsetStoreType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(STORETYPE$170);
                    }
                }
                
                /**
                 * Gets the "Store_Name" attribute
                 */
                public java.lang.String getStoreName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STORENAME$172);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Store_Name" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetStoreName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STORENAME$172);
                      return target;
                    }
                }
                
                /**
                 * True if has "Store_Name" attribute
                 */
                public boolean isSetStoreName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(STORENAME$172) != null;
                    }
                }
                
                /**
                 * Sets the "Store_Name" attribute
                 */
                public void setStoreName(java.lang.String storeName)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STORENAME$172);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(STORENAME$172);
                      }
                      target.setStringValue(storeName);
                    }
                }
                
                /**
                 * Sets (as xml) the "Store_Name" attribute
                 */
                public void xsetStoreName(org.apache.xmlbeans.XmlString storeName)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STORENAME$172);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(STORENAME$172);
                      }
                      target.set(storeName);
                    }
                }
                
                /**
                 * Unsets the "Store_Name" attribute
                 */
                public void unsetStoreName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(STORENAME$172);
                    }
                }
                
                /**
                 * Gets the "Store_Tel" attribute
                 */
                public java.lang.String getStoreTel()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STORETEL$174);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Store_Tel" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetStoreTel()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STORETEL$174);
                      return target;
                    }
                }
                
                /**
                 * True if has "Store_Tel" attribute
                 */
                public boolean isSetStoreTel()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(STORETEL$174) != null;
                    }
                }
                
                /**
                 * Sets the "Store_Tel" attribute
                 */
                public void setStoreTel(java.lang.String storeTel)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STORETEL$174);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(STORETEL$174);
                      }
                      target.setStringValue(storeTel);
                    }
                }
                
                /**
                 * Sets (as xml) the "Store_Tel" attribute
                 */
                public void xsetStoreTel(org.apache.xmlbeans.XmlString storeTel)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STORETEL$174);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(STORETEL$174);
                      }
                      target.set(storeTel);
                    }
                }
                
                /**
                 * Unsets the "Store_Tel" attribute
                 */
                public void unsetStoreTel()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(STORETEL$174);
                    }
                }
                
                /**
                 * Gets the "Store_Begin_Hours" attribute
                 */
                public java.lang.String getStoreBeginHours()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STOREBEGINHOURS$176);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Store_Begin_Hours" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetStoreBeginHours()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STOREBEGINHOURS$176);
                      return target;
                    }
                }
                
                /**
                 * True if has "Store_Begin_Hours" attribute
                 */
                public boolean isSetStoreBeginHours()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(STOREBEGINHOURS$176) != null;
                    }
                }
                
                /**
                 * Sets the "Store_Begin_Hours" attribute
                 */
                public void setStoreBeginHours(java.lang.String storeBeginHours)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STOREBEGINHOURS$176);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(STOREBEGINHOURS$176);
                      }
                      target.setStringValue(storeBeginHours);
                    }
                }
                
                /**
                 * Sets (as xml) the "Store_Begin_Hours" attribute
                 */
                public void xsetStoreBeginHours(org.apache.xmlbeans.XmlString storeBeginHours)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STOREBEGINHOURS$176);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(STOREBEGINHOURS$176);
                      }
                      target.set(storeBeginHours);
                    }
                }
                
                /**
                 * Unsets the "Store_Begin_Hours" attribute
                 */
                public void unsetStoreBeginHours()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(STOREBEGINHOURS$176);
                    }
                }
                
                /**
                 * Gets the "Store_End_Hours" attribute
                 */
                public java.lang.String getStoreEndHours()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STOREENDHOURS$178);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Store_End_Hours" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetStoreEndHours()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STOREENDHOURS$178);
                      return target;
                    }
                }
                
                /**
                 * True if has "Store_End_Hours" attribute
                 */
                public boolean isSetStoreEndHours()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(STOREENDHOURS$178) != null;
                    }
                }
                
                /**
                 * Sets the "Store_End_Hours" attribute
                 */
                public void setStoreEndHours(java.lang.String storeEndHours)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STOREENDHOURS$178);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(STOREENDHOURS$178);
                      }
                      target.setStringValue(storeEndHours);
                    }
                }
                
                /**
                 * Sets (as xml) the "Store_End_Hours" attribute
                 */
                public void xsetStoreEndHours(org.apache.xmlbeans.XmlString storeEndHours)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STOREENDHOURS$178);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(STOREENDHOURS$178);
                      }
                      target.set(storeEndHours);
                    }
                }
                
                /**
                 * Unsets the "Store_End_Hours" attribute
                 */
                public void unsetStoreEndHours()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(STOREENDHOURS$178);
                    }
                }
                
                /**
                 * Gets the "Store_Addr_1" attribute
                 */
                public java.lang.String getStoreAddr1()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STOREADDR1$180);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Store_Addr_1" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetStoreAddr1()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STOREADDR1$180);
                      return target;
                    }
                }
                
                /**
                 * True if has "Store_Addr_1" attribute
                 */
                public boolean isSetStoreAddr1()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(STOREADDR1$180) != null;
                    }
                }
                
                /**
                 * Sets the "Store_Addr_1" attribute
                 */
                public void setStoreAddr1(java.lang.String storeAddr1)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STOREADDR1$180);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(STOREADDR1$180);
                      }
                      target.setStringValue(storeAddr1);
                    }
                }
                
                /**
                 * Sets (as xml) the "Store_Addr_1" attribute
                 */
                public void xsetStoreAddr1(org.apache.xmlbeans.XmlString storeAddr1)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STOREADDR1$180);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(STOREADDR1$180);
                      }
                      target.set(storeAddr1);
                    }
                }
                
                /**
                 * Unsets the "Store_Addr_1" attribute
                 */
                public void unsetStoreAddr1()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(STOREADDR1$180);
                    }
                }
                
                /**
                 * Gets the "Store_Addr_2" attribute
                 */
                public java.lang.String getStoreAddr2()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STOREADDR2$182);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Store_Addr_2" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetStoreAddr2()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STOREADDR2$182);
                      return target;
                    }
                }
                
                /**
                 * True if has "Store_Addr_2" attribute
                 */
                public boolean isSetStoreAddr2()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(STOREADDR2$182) != null;
                    }
                }
                
                /**
                 * Sets the "Store_Addr_2" attribute
                 */
                public void setStoreAddr2(java.lang.String storeAddr2)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STOREADDR2$182);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(STOREADDR2$182);
                      }
                      target.setStringValue(storeAddr2);
                    }
                }
                
                /**
                 * Sets (as xml) the "Store_Addr_2" attribute
                 */
                public void xsetStoreAddr2(org.apache.xmlbeans.XmlString storeAddr2)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STOREADDR2$182);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(STOREADDR2$182);
                      }
                      target.set(storeAddr2);
                    }
                }
                
                /**
                 * Unsets the "Store_Addr_2" attribute
                 */
                public void unsetStoreAddr2()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(STOREADDR2$182);
                    }
                }
                
                /**
                 * Gets the "Store_Addr_3" attribute
                 */
                public java.lang.String getStoreAddr3()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STOREADDR3$184);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Store_Addr_3" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetStoreAddr3()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STOREADDR3$184);
                      return target;
                    }
                }
                
                /**
                 * True if has "Store_Addr_3" attribute
                 */
                public boolean isSetStoreAddr3()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(STOREADDR3$184) != null;
                    }
                }
                
                /**
                 * Sets the "Store_Addr_3" attribute
                 */
                public void setStoreAddr3(java.lang.String storeAddr3)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STOREADDR3$184);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(STOREADDR3$184);
                      }
                      target.setStringValue(storeAddr3);
                    }
                }
                
                /**
                 * Sets (as xml) the "Store_Addr_3" attribute
                 */
                public void xsetStoreAddr3(org.apache.xmlbeans.XmlString storeAddr3)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STOREADDR3$184);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(STOREADDR3$184);
                      }
                      target.set(storeAddr3);
                    }
                }
                
                /**
                 * Unsets the "Store_Addr_3" attribute
                 */
                public void unsetStoreAddr3()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(STOREADDR3$184);
                    }
                }
                
                /**
                 * Gets the "Salesman_Name" attribute
                 */
                public java.lang.String getSalesmanName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SALESMANNAME$186);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Salesman_Name" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetSalesmanName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SALESMANNAME$186);
                      return target;
                    }
                }
                
                /**
                 * True if has "Salesman_Name" attribute
                 */
                public boolean isSetSalesmanName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(SALESMANNAME$186) != null;
                    }
                }
                
                /**
                 * Sets the "Salesman_Name" attribute
                 */
                public void setSalesmanName(java.lang.String salesmanName)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SALESMANNAME$186);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SALESMANNAME$186);
                      }
                      target.setStringValue(salesmanName);
                    }
                }
                
                /**
                 * Sets (as xml) the "Salesman_Name" attribute
                 */
                public void xsetSalesmanName(org.apache.xmlbeans.XmlString salesmanName)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SALESMANNAME$186);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SALESMANNAME$186);
                      }
                      target.set(salesmanName);
                    }
                }
                
                /**
                 * Unsets the "Salesman_Name" attribute
                 */
                public void unsetSalesmanName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(SALESMANNAME$186);
                    }
                }
                
                /**
                 * Gets the "Settle_Staff_Name" attribute
                 */
                public java.lang.String getSettleStaffName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SETTLESTAFFNAME$188);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Settle_Staff_Name" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetSettleStaffName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SETTLESTAFFNAME$188);
                      return target;
                    }
                }
                
                /**
                 * True if has "Settle_Staff_Name" attribute
                 */
                public boolean isSetSettleStaffName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(SETTLESTAFFNAME$188) != null;
                    }
                }
                
                /**
                 * Sets the "Settle_Staff_Name" attribute
                 */
                public void setSettleStaffName(java.lang.String settleStaffName)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SETTLESTAFFNAME$188);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SETTLESTAFFNAME$188);
                      }
                      target.setStringValue(settleStaffName);
                    }
                }
                
                /**
                 * Sets (as xml) the "Settle_Staff_Name" attribute
                 */
                public void xsetSettleStaffName(org.apache.xmlbeans.XmlString settleStaffName)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SETTLESTAFFNAME$188);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SETTLESTAFFNAME$188);
                      }
                      target.set(settleStaffName);
                    }
                }
                
                /**
                 * Unsets the "Settle_Staff_Name" attribute
                 */
                public void unsetSettleStaffName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(SETTLESTAFFNAME$188);
                    }
                }
                
                /**
                 * Gets the "CMR" attribute
                 */
                public java.lang.String getCMR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CMR$190);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CMR" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCMR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CMR$190);
                      return target;
                    }
                }
                
                /**
                 * True if has "CMR" attribute
                 */
                public boolean isSetCMR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CMR$190) != null;
                    }
                }
                
                /**
                 * Sets the "CMR" attribute
                 */
                public void setCMR(java.lang.String cmr)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CMR$190);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CMR$190);
                      }
                      target.setStringValue(cmr);
                    }
                }
                
                /**
                 * Sets (as xml) the "CMR" attribute
                 */
                public void xsetCMR(org.apache.xmlbeans.XmlString cmr)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CMR$190);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CMR$190);
                      }
                      target.set(cmr);
                    }
                }
                
                /**
                 * Unsets the "CMR" attribute
                 */
                public void unsetCMR()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CMR$190);
                    }
                }
                
                /**
                 * Gets the "UM" attribute
                 */
                public java.lang.String getUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(UM$192);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "UM" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(UM$192);
                      return target;
                    }
                }
                
                /**
                 * True if has "UM" attribute
                 */
                public boolean isSetUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(UM$192) != null;
                    }
                }
                
                /**
                 * Sets the "UM" attribute
                 */
                public void setUM(java.lang.String um)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(UM$192);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(UM$192);
                      }
                      target.setStringValue(um);
                    }
                }
                
                /**
                 * Sets (as xml) the "UM" attribute
                 */
                public void xsetUM(org.apache.xmlbeans.XmlString um)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(UM$192);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(UM$192);
                      }
                      target.set(um);
                    }
                }
                
                /**
                 * Unsets the "UM" attribute
                 */
                public void unsetUM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(UM$192);
                    }
                }
                
                /**
                 * Gets the "Title" attribute
                 */
                public java.lang.String getTitle()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TITLE$194);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Title" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetTitle()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TITLE$194);
                      return target;
                    }
                }
                
                /**
                 * True if has "Title" attribute
                 */
                public boolean isSetTitle()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(TITLE$194) != null;
                    }
                }
                
                /**
                 * Sets the "Title" attribute
                 */
                public void setTitle(java.lang.String title)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TITLE$194);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TITLE$194);
                      }
                      target.setStringValue(title);
                    }
                }
                
                /**
                 * Sets (as xml) the "Title" attribute
                 */
                public void xsetTitle(org.apache.xmlbeans.XmlString title)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TITLE$194);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(TITLE$194);
                      }
                      target.set(title);
                    }
                }
                
                /**
                 * Unsets the "Title" attribute
                 */
                public void unsetTitle()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(TITLE$194);
                    }
                }
                
                /**
                 * Gets the "Team_Name" attribute
                 */
                public java.lang.String getTeamName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TEAMNAME$196);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Team_Name" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetTeamName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TEAMNAME$196);
                      return target;
                    }
                }
                
                /**
                 * True if has "Team_Name" attribute
                 */
                public boolean isSetTeamName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(TEAMNAME$196) != null;
                    }
                }
                
                /**
                 * Sets the "Team_Name" attribute
                 */
                public void setTeamName(java.lang.String teamName)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TEAMNAME$196);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TEAMNAME$196);
                      }
                      target.setStringValue(teamName);
                    }
                }
                
                /**
                 * Sets (as xml) the "Team_Name" attribute
                 */
                public void xsetTeamName(org.apache.xmlbeans.XmlString teamName)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TEAMNAME$196);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(TEAMNAME$196);
                      }
                      target.set(teamName);
                    }
                }
                
                /**
                 * Unsets the "Team_Name" attribute
                 */
                public void unsetTeamName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(TEAMNAME$196);
                    }
                }
                
                /**
                 * Gets the "Dep" attribute
                 */
                public java.lang.String getDep()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DEP$198);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Dep" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetDep()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DEP$198);
                      return target;
                    }
                }
                
                /**
                 * True if has "Dep" attribute
                 */
                public boolean isSetDep()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(DEP$198) != null;
                    }
                }
                
                /**
                 * Sets the "Dep" attribute
                 */
                public void setDep(java.lang.String dep)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DEP$198);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DEP$198);
                      }
                      target.setStringValue(dep);
                    }
                }
                
                /**
                 * Sets (as xml) the "Dep" attribute
                 */
                public void xsetDep(org.apache.xmlbeans.XmlString dep)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DEP$198);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(DEP$198);
                      }
                      target.set(dep);
                    }
                }
                
                /**
                 * Unsets the "Dep" attribute
                 */
                public void unsetDep()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(DEP$198);
                    }
                }
                
                /**
                 * Gets the "Dep_Name" attribute
                 */
                public java.lang.String getDepName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DEPNAME$200);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Dep_Name" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetDepName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DEPNAME$200);
                      return target;
                    }
                }
                
                /**
                 * True if has "Dep_Name" attribute
                 */
                public boolean isSetDepName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(DEPNAME$200) != null;
                    }
                }
                
                /**
                 * Sets the "Dep_Name" attribute
                 */
                public void setDepName(java.lang.String depName)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DEPNAME$200);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DEPNAME$200);
                      }
                      target.setStringValue(depName);
                    }
                }
                
                /**
                 * Sets (as xml) the "Dep_Name" attribute
                 */
                public void xsetDepName(org.apache.xmlbeans.XmlString depName)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DEPNAME$200);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(DEPNAME$200);
                      }
                      target.set(depName);
                    }
                }
                
                /**
                 * Unsets the "Dep_Name" attribute
                 */
                public void unsetDepName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(DEPNAME$200);
                    }
                }
                
                /**
                 * Gets the "coll_time" attribute
                 */
                public java.lang.String getCollTime()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(COLLTIME$202);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "coll_time" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCollTime()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(COLLTIME$202);
                      return target;
                    }
                }
                
                /**
                 * True if has "coll_time" attribute
                 */
                public boolean isSetCollTime()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(COLLTIME$202) != null;
                    }
                }
                
                /**
                 * Sets the "coll_time" attribute
                 */
                public void setCollTime(java.lang.String collTime)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(COLLTIME$202);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(COLLTIME$202);
                      }
                      target.setStringValue(collTime);
                    }
                }
                
                /**
                 * Sets (as xml) the "coll_time" attribute
                 */
                public void xsetCollTime(org.apache.xmlbeans.XmlString collTime)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(COLLTIME$202);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(COLLTIME$202);
                      }
                      target.set(collTime);
                    }
                }
                
                /**
                 * Unsets the "coll_time" attribute
                 */
                public void unsetCollTime()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(COLLTIME$202);
                    }
                }
                
                /**
                 * Gets the "IMS_Acno" attribute
                 */
                public java.lang.String getIMSAcno()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMSACNO$204);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "IMS_Acno" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetIMSAcno()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(IMSACNO$204);
                      return target;
                    }
                }
                
                /**
                 * True if has "IMS_Acno" attribute
                 */
                public boolean isSetIMSAcno()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(IMSACNO$204) != null;
                    }
                }
                
                /**
                 * Sets the "IMS_Acno" attribute
                 */
                public void setIMSAcno(java.lang.String imsAcno)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMSACNO$204);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMSACNO$204);
                      }
                      target.setStringValue(imsAcno);
                    }
                }
                
                /**
                 * Sets (as xml) the "IMS_Acno" attribute
                 */
                public void xsetIMSAcno(org.apache.xmlbeans.XmlString imsAcno)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(IMSACNO$204);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(IMSACNO$204);
                      }
                      target.set(imsAcno);
                    }
                }
                
                /**
                 * Unsets the "IMS_Acno" attribute
                 */
                public void unsetIMSAcno()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(IMSACNO$204);
                    }
                }
                
                /**
                 * Gets the "Paid_Amt" attribute
                 */
                public short getPaidAmt()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PAIDAMT$206);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getShortValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Paid_Amt" attribute
                 */
                public org.apache.xmlbeans.XmlShort xgetPaidAmt()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(PAIDAMT$206);
                      return target;
                    }
                }
                
                /**
                 * True if has "Paid_Amt" attribute
                 */
                public boolean isSetPaidAmt()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(PAIDAMT$206) != null;
                    }
                }
                
                /**
                 * Sets the "Paid_Amt" attribute
                 */
                public void setPaidAmt(short paidAmt)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PAIDAMT$206);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PAIDAMT$206);
                      }
                      target.setShortValue(paidAmt);
                    }
                }
                
                /**
                 * Sets (as xml) the "Paid_Amt" attribute
                 */
                public void xsetPaidAmt(org.apache.xmlbeans.XmlShort paidAmt)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(PAIDAMT$206);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlShort)get_store().add_attribute_user(PAIDAMT$206);
                      }
                      target.set(paidAmt);
                    }
                }
                
                /**
                 * Unsets the "Paid_Amt" attribute
                 */
                public void unsetPaidAmt()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(PAIDAMT$206);
                    }
                }
                
                /**
                 * Gets the "Txn_Date_POS_BOM" attribute
                 */
                public java.lang.String getTxnDatePOSBOM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TXNDATEPOSBOM$208);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Txn_Date_POS_BOM" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetTxnDatePOSBOM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TXNDATEPOSBOM$208);
                      return target;
                    }
                }
                
                /**
                 * True if has "Txn_Date_POS_BOM" attribute
                 */
                public boolean isSetTxnDatePOSBOM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(TXNDATEPOSBOM$208) != null;
                    }
                }
                
                /**
                 * Sets the "Txn_Date_POS_BOM" attribute
                 */
                public void setTxnDatePOSBOM(java.lang.String txnDatePOSBOM)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TXNDATEPOSBOM$208);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TXNDATEPOSBOM$208);
                      }
                      target.setStringValue(txnDatePOSBOM);
                    }
                }
                
                /**
                 * Sets (as xml) the "Txn_Date_POS_BOM" attribute
                 */
                public void xsetTxnDatePOSBOM(org.apache.xmlbeans.XmlString txnDatePOSBOM)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TXNDATEPOSBOM$208);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(TXNDATEPOSBOM$208);
                      }
                      target.set(txnDatePOSBOM);
                    }
                }
                
                /**
                 * Unsets the "Txn_Date_POS_BOM" attribute
                 */
                public void unsetTxnDatePOSBOM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(TXNDATEPOSBOM$208);
                    }
                }
                
                /**
                 * Gets the "Txn_Time_POS_BOM" attribute
                 */
                public java.lang.String getTxnTimePOSBOM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TXNTIMEPOSBOM$210);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Txn_Time_POS_BOM" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetTxnTimePOSBOM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TXNTIMEPOSBOM$210);
                      return target;
                    }
                }
                
                /**
                 * True if has "Txn_Time_POS_BOM" attribute
                 */
                public boolean isSetTxnTimePOSBOM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(TXNTIMEPOSBOM$210) != null;
                    }
                }
                
                /**
                 * Sets the "Txn_Time_POS_BOM" attribute
                 */
                public void setTxnTimePOSBOM(java.lang.String txnTimePOSBOM)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TXNTIMEPOSBOM$210);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TXNTIMEPOSBOM$210);
                      }
                      target.setStringValue(txnTimePOSBOM);
                    }
                }
                
                /**
                 * Sets (as xml) the "Txn_Time_POS_BOM" attribute
                 */
                public void xsetTxnTimePOSBOM(org.apache.xmlbeans.XmlString txnTimePOSBOM)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TXNTIMEPOSBOM$210);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(TXNTIMEPOSBOM$210);
                      }
                      target.set(txnTimePOSBOM);
                    }
                }
                
                /**
                 * Unsets the "Txn_Time_POS_BOM" attribute
                 */
                public void unsetTxnTimePOSBOM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(TXNTIMEPOSBOM$210);
                    }
                }
                
                /**
                 * Gets the "OCID" attribute
                 */
                public java.lang.String getOCID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OCID$212);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "OCID" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetOCID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(OCID$212);
                      return target;
                    }
                }
                
                /**
                 * True if has "OCID" attribute
                 */
                public boolean isSetOCID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(OCID$212) != null;
                    }
                }
                
                /**
                 * Sets the "OCID" attribute
                 */
                public void setOCID(java.lang.String ocid)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OCID$212);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(OCID$212);
                      }
                      target.setStringValue(ocid);
                    }
                }
                
                /**
                 * Sets (as xml) the "OCID" attribute
                 */
                public void xsetOCID(org.apache.xmlbeans.XmlString ocid)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(OCID$212);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(OCID$212);
                      }
                      target.set(ocid);
                    }
                }
                
                /**
                 * Unsets the "OCID" attribute
                 */
                public void unsetOCID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(OCID$212);
                    }
                }
                
                /**
                 * Gets the "Request_ID" attribute
                 */
                public java.lang.String getRequestID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(REQUESTID$214);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Request_ID" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetRequestID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(REQUESTID$214);
                      return target;
                    }
                }
                
                /**
                 * True if has "Request_ID" attribute
                 */
                public boolean isSetRequestID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(REQUESTID$214) != null;
                    }
                }
                
                /**
                 * Sets the "Request_ID" attribute
                 */
                public void setRequestID(java.lang.String requestID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(REQUESTID$214);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(REQUESTID$214);
                      }
                      target.setStringValue(requestID);
                    }
                }
                
                /**
                 * Sets (as xml) the "Request_ID" attribute
                 */
                public void xsetRequestID(org.apache.xmlbeans.XmlString requestID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(REQUESTID$214);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(REQUESTID$214);
                      }
                      target.set(requestID);
                    }
                }
                
                /**
                 * Unsets the "Request_ID" attribute
                 */
                public void unsetRequestID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(REQUESTID$214);
                    }
                }
                
                /**
                 * Gets the "Customer_ID" attribute
                 */
                public java.lang.String getCustomerID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUSTOMERID$216);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Customer_ID" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCustomerID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUSTOMERID$216);
                      return target;
                    }
                }
                
                /**
                 * True if has "Customer_ID" attribute
                 */
                public boolean isSetCustomerID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUSTOMERID$216) != null;
                    }
                }
                
                /**
                 * Sets the "Customer_ID" attribute
                 */
                public void setCustomerID(java.lang.String customerID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUSTOMERID$216);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUSTOMERID$216);
                      }
                      target.setStringValue(customerID);
                    }
                }
                
                /**
                 * Sets (as xml) the "Customer_ID" attribute
                 */
                public void xsetCustomerID(org.apache.xmlbeans.XmlString customerID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUSTOMERID$216);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUSTOMERID$216);
                      }
                      target.set(customerID);
                    }
                }
                
                /**
                 * Unsets the "Customer_ID" attribute
                 */
                public void unsetCustomerID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUSTOMERID$216);
                    }
                }
                
                /**
                 * Gets the "Account_Code" attribute
                 */
                public java.lang.String getAccountCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ACCOUNTCODE$218);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Account_Code" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetAccountCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ACCOUNTCODE$218);
                      return target;
                    }
                }
                
                /**
                 * True if has "Account_Code" attribute
                 */
                public boolean isSetAccountCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(ACCOUNTCODE$218) != null;
                    }
                }
                
                /**
                 * Sets the "Account_Code" attribute
                 */
                public void setAccountCode(java.lang.String accountCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ACCOUNTCODE$218);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ACCOUNTCODE$218);
                      }
                      target.setStringValue(accountCode);
                    }
                }
                
                /**
                 * Sets (as xml) the "Account_Code" attribute
                 */
                public void xsetAccountCode(org.apache.xmlbeans.XmlString accountCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ACCOUNTCODE$218);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(ACCOUNTCODE$218);
                      }
                      target.set(accountCode);
                    }
                }
                
                /**
                 * Unsets the "Account_Code" attribute
                 */
                public void unsetAccountCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(ACCOUNTCODE$218);
                    }
                }
                
                /**
                 * Gets the "SSAgreeNo" attribute
                 */
                public java.lang.String getSSAgreeNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SSAGREENO$220);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "SSAgreeNo" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetSSAgreeNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SSAGREENO$220);
                      return target;
                    }
                }
                
                /**
                 * True if has "SSAgreeNo" attribute
                 */
                public boolean isSetSSAgreeNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(SSAGREENO$220) != null;
                    }
                }
                
                /**
                 * Sets the "SSAgreeNo" attribute
                 */
                public void setSSAgreeNo(java.lang.String ssAgreeNo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SSAGREENO$220);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SSAGREENO$220);
                      }
                      target.setStringValue(ssAgreeNo);
                    }
                }
                
                /**
                 * Sets (as xml) the "SSAgreeNo" attribute
                 */
                public void xsetSSAgreeNo(org.apache.xmlbeans.XmlString ssAgreeNo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SSAGREENO$220);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SSAGREENO$220);
                      }
                      target.set(ssAgreeNo);
                    }
                }
                
                /**
                 * Unsets the "SSAgreeNo" attribute
                 */
                public void unsetSSAgreeNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(SSAGREENO$220);
                    }
                }
                
                /**
                 * Gets the "CU_MobileNo" attribute
                 */
                public java.lang.String getCUMobileNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUMOBILENO$222);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CU_MobileNo" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCUMobileNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUMOBILENO$222);
                      return target;
                    }
                }
                
                /**
                 * True if has "CU_MobileNo" attribute
                 */
                public boolean isSetCUMobileNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CUMOBILENO$222) != null;
                    }
                }
                
                /**
                 * Sets the "CU_MobileNo" attribute
                 */
                public void setCUMobileNo(java.lang.String cuMobileNo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CUMOBILENO$222);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CUMOBILENO$222);
                      }
                      target.setStringValue(cuMobileNo);
                    }
                }
                
                /**
                 * Sets (as xml) the "CU_MobileNo" attribute
                 */
                public void xsetCUMobileNo(org.apache.xmlbeans.XmlString cuMobileNo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CUMOBILENO$222);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CUMOBILENO$222);
                      }
                      target.set(cuMobileNo);
                    }
                }
                
                /**
                 * Unsets the "CU_MobileNo" attribute
                 */
                public void unsetCUMobileNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CUMOBILENO$222);
                    }
                }
                
                /**
                 * Gets the "MobileNoType" attribute
                 */
                public java.lang.String getMobileNoType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MOBILENOTYPE$224);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "MobileNoType" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetMobileNoType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(MOBILENOTYPE$224);
                      return target;
                    }
                }
                
                /**
                 * True if has "MobileNoType" attribute
                 */
                public boolean isSetMobileNoType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(MOBILENOTYPE$224) != null;
                    }
                }
                
                /**
                 * Sets the "MobileNoType" attribute
                 */
                public void setMobileNoType(java.lang.String mobileNoType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MOBILENOTYPE$224);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(MOBILENOTYPE$224);
                      }
                      target.setStringValue(mobileNoType);
                    }
                }
                
                /**
                 * Sets (as xml) the "MobileNoType" attribute
                 */
                public void xsetMobileNoType(org.apache.xmlbeans.XmlString mobileNoType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(MOBILENOTYPE$224);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(MOBILENOTYPE$224);
                      }
                      target.set(mobileNoType);
                    }
                }
                
                /**
                 * Unsets the "MobileNoType" attribute
                 */
                public void unsetMobileNoType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(MOBILENOTYPE$224);
                    }
                }
                
                /**
                 * Gets the "MobileNo" attribute
                 */
                public java.lang.String getMobileNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MOBILENO$226);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "MobileNo" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetMobileNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(MOBILENO$226);
                      return target;
                    }
                }
                
                /**
                 * True if has "MobileNo" attribute
                 */
                public boolean isSetMobileNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(MOBILENO$226) != null;
                    }
                }
                
                /**
                 * Sets the "MobileNo" attribute
                 */
                public void setMobileNo(java.lang.String mobileNo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MOBILENO$226);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(MOBILENO$226);
                      }
                      target.setStringValue(mobileNo);
                    }
                }
                
                /**
                 * Sets (as xml) the "MobileNo" attribute
                 */
                public void xsetMobileNo(org.apache.xmlbeans.XmlString mobileNo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(MOBILENO$226);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(MOBILENO$226);
                      }
                      target.set(mobileNo);
                    }
                }
                
                /**
                 * Unsets the "MobileNo" attribute
                 */
                public void unsetMobileNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(MOBILENO$226);
                    }
                }
            }
        }
    }
}
