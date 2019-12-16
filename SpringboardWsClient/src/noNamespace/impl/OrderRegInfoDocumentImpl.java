/*
 * An XML document type.
 * Localname: OrderRegInfo
 * Namespace: 
 * Java type: noNamespace.OrderRegInfoDocument
 *
 * Automatically generated - do not modify.
 */
package noNamespace.impl;
/**
 * A document containing one OrderRegInfo(@) element.
 *
 * This is a complex type.
 */
public class OrderRegInfoDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.OrderRegInfoDocument
{
    private static final long serialVersionUID = 1L;
    
    public OrderRegInfoDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ORDERREGINFO$0 = 
        new javax.xml.namespace.QName("", "OrderRegInfo");
    
    
    /**
     * Gets the "OrderRegInfo" element
     */
    public noNamespace.OrderRegInfoDocument.OrderRegInfo getOrderRegInfo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            noNamespace.OrderRegInfoDocument.OrderRegInfo target = null;
            target = (noNamespace.OrderRegInfoDocument.OrderRegInfo)get_store().find_element_user(ORDERREGINFO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "OrderRegInfo" element
     */
    public void setOrderRegInfo(noNamespace.OrderRegInfoDocument.OrderRegInfo orderRegInfo)
    {
        generatedSetterHelperImpl(orderRegInfo, ORDERREGINFO$0, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }
    
    /**
     * Appends and returns a new empty "OrderRegInfo" element
     */
    public noNamespace.OrderRegInfoDocument.OrderRegInfo addNewOrderRegInfo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            noNamespace.OrderRegInfoDocument.OrderRegInfo target = null;
            target = (noNamespace.OrderRegInfoDocument.OrderRegInfo)get_store().add_element_user(ORDERREGINFO$0);
            return target;
        }
    }
    /**
     * An XML OrderRegInfo(@).
     *
     * This is a complex type.
     */
    public static class OrderRegInfoImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.OrderRegInfoDocument.OrderRegInfo
    {
        private static final long serialVersionUID = 1L;
        
        public OrderRegInfoImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName ORDERREG$0 = 
            new javax.xml.namespace.QName("", "orderReg");
        private static final javax.xml.namespace.QName CUSTOMER$2 = 
            new javax.xml.namespace.QName("", "customer");
        private static final javax.xml.namespace.QName ADDRESS$4 = 
            new javax.xml.namespace.QName("", "Address");
        private static final javax.xml.namespace.QName ORDERREGDTLS$6 = 
            new javax.xml.namespace.QName("", "orderRegDtls");
        private static final javax.xml.namespace.QName ORDERREGTENDERS$8 = 
            new javax.xml.namespace.QName("", "orderRegTenders");
        
        
        /**
         * Gets the "orderReg" element
         */
        public noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderReg getOrderReg()
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderReg target = null;
                target = (noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderReg)get_store().find_element_user(ORDERREG$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Sets the "orderReg" element
         */
        public void setOrderReg(noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderReg orderReg)
        {
            generatedSetterHelperImpl(orderReg, ORDERREG$0, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
        }
        
        /**
         * Appends and returns a new empty "orderReg" element
         */
        public noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderReg addNewOrderReg()
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderReg target = null;
                target = (noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderReg)get_store().add_element_user(ORDERREG$0);
                return target;
            }
        }
        
        /**
         * Gets the "customer" element
         */
        public noNamespace.OrderRegInfoDocument.OrderRegInfo.Customer getCustomer()
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.OrderRegInfoDocument.OrderRegInfo.Customer target = null;
                target = (noNamespace.OrderRegInfoDocument.OrderRegInfo.Customer)get_store().find_element_user(CUSTOMER$2, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Sets the "customer" element
         */
        public void setCustomer(noNamespace.OrderRegInfoDocument.OrderRegInfo.Customer customer)
        {
            generatedSetterHelperImpl(customer, CUSTOMER$2, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
        }
        
        /**
         * Appends and returns a new empty "customer" element
         */
        public noNamespace.OrderRegInfoDocument.OrderRegInfo.Customer addNewCustomer()
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.OrderRegInfoDocument.OrderRegInfo.Customer target = null;
                target = (noNamespace.OrderRegInfoDocument.OrderRegInfo.Customer)get_store().add_element_user(CUSTOMER$2);
                return target;
            }
        }
        
        /**
         * Gets the "Address" element
         */
        public noNamespace.OrderRegInfoDocument.OrderRegInfo.Address getAddress()
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.OrderRegInfoDocument.OrderRegInfo.Address target = null;
                target = (noNamespace.OrderRegInfoDocument.OrderRegInfo.Address)get_store().find_element_user(ADDRESS$4, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Sets the "Address" element
         */
        public void setAddress(noNamespace.OrderRegInfoDocument.OrderRegInfo.Address address)
        {
            generatedSetterHelperImpl(address, ADDRESS$4, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
        }
        
        /**
         * Appends and returns a new empty "Address" element
         */
        public noNamespace.OrderRegInfoDocument.OrderRegInfo.Address addNewAddress()
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.OrderRegInfoDocument.OrderRegInfo.Address target = null;
                target = (noNamespace.OrderRegInfoDocument.OrderRegInfo.Address)get_store().add_element_user(ADDRESS$4);
                return target;
            }
        }
        
        /**
         * Gets the "orderRegDtls" element
         */
        public noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls getOrderRegDtls()
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls target = null;
                target = (noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls)get_store().find_element_user(ORDERREGDTLS$6, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Sets the "orderRegDtls" element
         */
        public void setOrderRegDtls(noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls orderRegDtls)
        {
            generatedSetterHelperImpl(orderRegDtls, ORDERREGDTLS$6, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
        }
        
        /**
         * Appends and returns a new empty "orderRegDtls" element
         */
        public noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls addNewOrderRegDtls()
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls target = null;
                target = (noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls)get_store().add_element_user(ORDERREGDTLS$6);
                return target;
            }
        }
        
        /**
         * Gets the "orderRegTenders" element
         */
        public noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders getOrderRegTenders()
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders target = null;
                target = (noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders)get_store().find_element_user(ORDERREGTENDERS$8, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Sets the "orderRegTenders" element
         */
        public void setOrderRegTenders(noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders orderRegTenders)
        {
            generatedSetterHelperImpl(orderRegTenders, ORDERREGTENDERS$8, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
        }
        
        /**
         * Appends and returns a new empty "orderRegTenders" element
         */
        public noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders addNewOrderRegTenders()
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders target = null;
                target = (noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders)get_store().add_element_user(ORDERREGTENDERS$8);
                return target;
            }
        }
        /**
         * An XML orderReg(@).
         *
         * This is a complex type.
         */
        public static class OrderRegImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderReg
        {
            private static final long serialVersionUID = 1L;
            
            public OrderRegImpl(org.apache.xmlbeans.SchemaType sType)
            {
                super(sType);
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
                new javax.xml.namespace.QName("", "Channel");
            private static final javax.xml.namespace.QName SALESMANCODE$22 = 
                new javax.xml.namespace.QName("", "Salesman_Code");
            private static final javax.xml.namespace.QName SMAPPROVEDBY$24 = 
                new javax.xml.namespace.QName("", "SM_ApprovedBy");
            private static final javax.xml.namespace.QName QUNUM$26 = 
                new javax.xml.namespace.QName("", "QU_Num");
            private static final javax.xml.namespace.QName SMNUM$28 = 
                new javax.xml.namespace.QName("", "SM_Num");
            private static final javax.xml.namespace.QName TOTALAMT$30 = 
                new javax.xml.namespace.QName("", "TotalAmt");
            private static final javax.xml.namespace.QName SINEED$32 = 
                new javax.xml.namespace.QName("", "SI_Need");
            private static final javax.xml.namespace.QName SMCREATEDATE$34 = 
                new javax.xml.namespace.QName("", "SM_CreateDate");
            private static final javax.xml.namespace.QName MODIFYBY$36 = 
                new javax.xml.namespace.QName("", "Modifyby");
            private static final javax.xml.namespace.QName MODIFYDATE$38 = 
                new javax.xml.namespace.QName("", "ModifyDate");
            private static final javax.xml.namespace.QName CONSOLDATE$40 = 
                new javax.xml.namespace.QName("", "ConsolDate");
            private static final javax.xml.namespace.QName SETTLEMENTDATE$42 = 
                new javax.xml.namespace.QName("", "SettlementDate");
            private static final javax.xml.namespace.QName SETTLELOGDATE$44 = 
                new javax.xml.namespace.QName("", "Settle_Log_Date");
            private static final javax.xml.namespace.QName SETTLEMENTSTAFFID$46 = 
                new javax.xml.namespace.QName("", "Settlement_Staff_ID");
            private static final javax.xml.namespace.QName STACHGDATE$48 = 
                new javax.xml.namespace.QName("", "StaChgDate");
            private static final javax.xml.namespace.QName STACHGBY$50 = 
                new javax.xml.namespace.QName("", "StaChgBy");
            private static final javax.xml.namespace.QName PAYSETTLELOGDATE$52 = 
                new javax.xml.namespace.QName("", "Pay_Settle_LogDate");
            private static final javax.xml.namespace.QName PAYSETTLEPHYDATE$54 = 
                new javax.xml.namespace.QName("", "Pay_Settle_PhyDate");
            private static final javax.xml.namespace.QName DELIVERDATE$56 = 
                new javax.xml.namespace.QName("", "Deliver_Date");
            private static final javax.xml.namespace.QName STATUS$58 = 
                new javax.xml.namespace.QName("", "Status");
            private static final javax.xml.namespace.QName CPEFLAG$60 = 
                new javax.xml.namespace.QName("", "CPEFLAG");
            private static final javax.xml.namespace.QName TRANSDISCOUNT$62 = 
                new javax.xml.namespace.QName("", "Trans_Discount");
            private static final javax.xml.namespace.QName TRANSREASON$64 = 
                new javax.xml.namespace.QName("", "Trans_Reason");
            private static final javax.xml.namespace.QName CPEWORKNUM$66 = 
                new javax.xml.namespace.QName("", "CPE_Work_Num");
            private static final javax.xml.namespace.QName CONVERTED$68 = 
                new javax.xml.namespace.QName("", "Converted");
            private static final javax.xml.namespace.QName ORGQUNUM$70 = 
                new javax.xml.namespace.QName("", "Org_QU_Num");
            private static final javax.xml.namespace.QName REFSMNO$72 = 
                new javax.xml.namespace.QName("", "Ref_SM_No");
            private static final javax.xml.namespace.QName INVALIDATEDFLAG$74 = 
                new javax.xml.namespace.QName("", "Invalidated_Flag");
            private static final javax.xml.namespace.QName DOWNLOAD$76 = 
                new javax.xml.namespace.QName("", "Download");
            private static final javax.xml.namespace.QName PRINTTEMPLATE$78 = 
                new javax.xml.namespace.QName("", "Print_Template");
            private static final javax.xml.namespace.QName STORETYPE$80 = 
                new javax.xml.namespace.QName("", "Store_Type");
            private static final javax.xml.namespace.QName STORENAME$82 = 
                new javax.xml.namespace.QName("", "Store_Name");
            private static final javax.xml.namespace.QName STORETEL$84 = 
                new javax.xml.namespace.QName("", "Store_Tel");
            private static final javax.xml.namespace.QName STOREBEGINHOURS$86 = 
                new javax.xml.namespace.QName("", "Store_Begin_Hours");
            private static final javax.xml.namespace.QName STOREENDHOURS$88 = 
                new javax.xml.namespace.QName("", "Store_End_Hours");
            private static final javax.xml.namespace.QName STOREADDR1$90 = 
                new javax.xml.namespace.QName("", "Store_Addr_1");
            private static final javax.xml.namespace.QName STOREADDR2$92 = 
                new javax.xml.namespace.QName("", "Store_Addr_2");
            private static final javax.xml.namespace.QName STOREADDR3$94 = 
                new javax.xml.namespace.QName("", "Store_Addr_3");
            private static final javax.xml.namespace.QName SALESMANNAME$96 = 
                new javax.xml.namespace.QName("", "Salesman_Name");
            private static final javax.xml.namespace.QName SETTLESTAFFNAME$98 = 
                new javax.xml.namespace.QName("", "Settle_Staff_Name");
            private static final javax.xml.namespace.QName CMR$100 = 
                new javax.xml.namespace.QName("", "CMR");
            private static final javax.xml.namespace.QName UM$102 = 
                new javax.xml.namespace.QName("", "UM");
            private static final javax.xml.namespace.QName TITLE$104 = 
                new javax.xml.namespace.QName("", "Title");
            private static final javax.xml.namespace.QName TEAMNAME$106 = 
                new javax.xml.namespace.QName("", "Team_Name");
            private static final javax.xml.namespace.QName DEP$108 = 
                new javax.xml.namespace.QName("", "Dep");
            private static final javax.xml.namespace.QName DEPNAME$110 = 
                new javax.xml.namespace.QName("", "Dep_Name");
            private static final javax.xml.namespace.QName COLLTIME$112 = 
                new javax.xml.namespace.QName("", "Coll_Time");
            private static final javax.xml.namespace.QName IMSACNO$114 = 
                new javax.xml.namespace.QName("", "IMS_Acno");
            private static final javax.xml.namespace.QName COLLDATE$116 = 
                new javax.xml.namespace.QName("", "Coll_Date");
            private static final javax.xml.namespace.QName PAIDAMT$118 = 
                new javax.xml.namespace.QName("", "Paid_Amt");
            private static final javax.xml.namespace.QName REFSMTYPE$120 = 
                new javax.xml.namespace.QName("", "Ref_SM_Type");
            private static final javax.xml.namespace.QName TXNTIMEPOSBOM$122 = 
                new javax.xml.namespace.QName("", "Txn_Time_POS_BOM");
            private static final javax.xml.namespace.QName TXNDATEPOSBOM$124 = 
                new javax.xml.namespace.QName("", "Txn_Date_POS_BOM");
            private static final javax.xml.namespace.QName REQNO$126 = 
                new javax.xml.namespace.QName("", "Req_No");
            private static final javax.xml.namespace.QName CUSTOMERID$128 = 
                new javax.xml.namespace.QName("", "Customer_ID");
            private static final javax.xml.namespace.QName ACCOUNTCODE$130 = 
                new javax.xml.namespace.QName("", "Account_Code");
            private static final javax.xml.namespace.QName SLID$132 = 
                new javax.xml.namespace.QName("", "SL_ID");
            private static final javax.xml.namespace.QName PRINTSM$134 = 
                new javax.xml.namespace.QName("", "Print_SM");
            private static final javax.xml.namespace.QName GENSMPDF$136 = 
                new javax.xml.namespace.QName("", "Gen_SM_Pdf");
            
            
            /**
             * Gets the "Store_ID" element
             */
            public java.lang.String getStoreID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STOREID$0, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Store_ID" element
             */
            public org.apache.xmlbeans.XmlString xgetStoreID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STOREID$0, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Store_ID" element
             */
            public void setStoreID(java.lang.String storeID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STOREID$0, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STOREID$0);
                    }
                    target.setStringValue(storeID);
                }
            }
            
            /**
             * Sets (as xml) the "Store_ID" element
             */
            public void xsetStoreID(org.apache.xmlbeans.XmlString storeID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STOREID$0, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STOREID$0);
                    }
                    target.set(storeID);
                }
            }
            
            /**
             * Gets the "Register_ID" element
             */
            public java.lang.String getRegisterID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REGISTERID$2, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Register_ID" element
             */
            public org.apache.xmlbeans.XmlString xgetRegisterID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REGISTERID$2, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Register_ID" element
             */
            public void setRegisterID(java.lang.String registerID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REGISTERID$2, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(REGISTERID$2);
                    }
                    target.setStringValue(registerID);
                }
            }
            
            /**
             * Sets (as xml) the "Register_ID" element
             */
            public void xsetRegisterID(org.apache.xmlbeans.XmlString registerID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REGISTERID$2, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REGISTERID$2);
                    }
                    target.set(registerID);
                }
            }
            
            /**
             * Gets the "Trans_Num" element
             */
            public java.lang.String getTransNum()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRANSNUM$4, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Trans_Num" element
             */
            public org.apache.xmlbeans.XmlString xgetTransNum()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRANSNUM$4, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Trans_Num" element
             */
            public void setTransNum(java.lang.String transNum)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRANSNUM$4, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TRANSNUM$4);
                    }
                    target.setStringValue(transNum);
                }
            }
            
            /**
             * Sets (as xml) the "Trans_Num" element
             */
            public void xsetTransNum(org.apache.xmlbeans.XmlString transNum)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRANSNUM$4, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TRANSNUM$4);
                    }
                    target.set(transNum);
                }
            }
            
            /**
             * Gets the "Bus_Date" element
             */
            public java.lang.String getBusDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BUSDATE$6, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Bus_Date" element
             */
            public org.apache.xmlbeans.XmlString xgetBusDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BUSDATE$6, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Bus_Date" element
             */
            public void setBusDate(java.lang.String busDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BUSDATE$6, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BUSDATE$6);
                    }
                    target.setStringValue(busDate);
                }
            }
            
            /**
             * Sets (as xml) the "Bus_Date" element
             */
            public void xsetBusDate(org.apache.xmlbeans.XmlString busDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BUSDATE$6, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BUSDATE$6);
                    }
                    target.set(busDate);
                }
            }
            
            /**
             * Gets the "Txn_Date" element
             */
            public java.lang.String getTxnDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TXNDATE$8, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Txn_Date" element
             */
            public org.apache.xmlbeans.XmlString xgetTxnDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TXNDATE$8, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Txn_Date" element
             */
            public void setTxnDate(java.lang.String txnDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TXNDATE$8, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TXNDATE$8);
                    }
                    target.setStringValue(txnDate);
                }
            }
            
            /**
             * Sets (as xml) the "Txn_Date" element
             */
            public void xsetTxnDate(org.apache.xmlbeans.XmlString txnDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TXNDATE$8, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TXNDATE$8);
                    }
                    target.set(txnDate);
                }
            }
            
            /**
             * Gets the "SM_Type" element
             */
            public int getSMType()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SMTYPE$10, 0);
                    if (target == null)
                    {
                      return 0;
                    }
                    return target.getIntValue();
                }
            }
            
            /**
             * Gets (as xml) the "SM_Type" element
             */
            public org.apache.xmlbeans.XmlInt xgetSMType()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlInt target = null;
                    target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SMTYPE$10, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "SM_Type" element
             */
            public void setSMType(int smType)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SMTYPE$10, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SMTYPE$10);
                    }
                    target.setIntValue(smType);
                }
            }
            
            /**
             * Sets (as xml) the "SM_Type" element
             */
            public void xsetSMType(org.apache.xmlbeans.XmlInt smType)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlInt target = null;
                    target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SMTYPE$10, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(SMTYPE$10);
                    }
                    target.set(smType);
                }
            }
            
            /**
             * Gets the "Cashier_ID" element
             */
            public java.lang.String getCashierID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CASHIERID$12, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Cashier_ID" element
             */
            public org.apache.xmlbeans.XmlString xgetCashierID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CASHIERID$12, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Cashier_ID" element
             */
            public void setCashierID(java.lang.String cashierID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CASHIERID$12, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CASHIERID$12);
                    }
                    target.setStringValue(cashierID);
                }
            }
            
            /**
             * Sets (as xml) the "Cashier_ID" element
             */
            public void xsetCashierID(org.apache.xmlbeans.XmlString cashierID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CASHIERID$12, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CASHIERID$12);
                    }
                    target.set(cashierID);
                }
            }
            
            /**
             * Gets the "SalesMan_ID" element
             */
            public java.lang.String getSalesManID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SALESMANID$14, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "SalesMan_ID" element
             */
            public org.apache.xmlbeans.XmlString xgetSalesManID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SALESMANID$14, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "SalesMan_ID" element
             */
            public void setSalesManID(java.lang.String salesManID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SALESMANID$14, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SALESMANID$14);
                    }
                    target.setStringValue(salesManID);
                }
            }
            
            /**
             * Sets (as xml) the "SalesMan_ID" element
             */
            public void xsetSalesManID(org.apache.xmlbeans.XmlString salesManID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SALESMANID$14, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SALESMANID$14);
                    }
                    target.set(salesManID);
                }
            }
            
            /**
             * Gets the "Salesman_Ext" element
             */
            public java.lang.String getSalesmanExt()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SALESMANEXT$16, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Salesman_Ext" element
             */
            public org.apache.xmlbeans.XmlString xgetSalesmanExt()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SALESMANEXT$16, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Salesman_Ext" element
             */
            public void setSalesmanExt(java.lang.String salesmanExt)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SALESMANEXT$16, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SALESMANEXT$16);
                    }
                    target.setStringValue(salesmanExt);
                }
            }
            
            /**
             * Sets (as xml) the "Salesman_Ext" element
             */
            public void xsetSalesmanExt(org.apache.xmlbeans.XmlString salesmanExt)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SALESMANEXT$16, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SALESMANEXT$16);
                    }
                    target.set(salesmanExt);
                }
            }
            
            /**
             * Gets the "Team_Code" element
             */
            public java.lang.String getTeamCode()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TEAMCODE$18, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Team_Code" element
             */
            public org.apache.xmlbeans.XmlString xgetTeamCode()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TEAMCODE$18, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Team_Code" element
             */
            public void setTeamCode(java.lang.String teamCode)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TEAMCODE$18, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TEAMCODE$18);
                    }
                    target.setStringValue(teamCode);
                }
            }
            
            /**
             * Sets (as xml) the "Team_Code" element
             */
            public void xsetTeamCode(org.apache.xmlbeans.XmlString teamCode)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TEAMCODE$18, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TEAMCODE$18);
                    }
                    target.set(teamCode);
                }
            }
            
            /**
             * Gets the "Channel" element
             */
            public java.lang.String getChannel()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CHANNEL$20, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Channel" element
             */
            public org.apache.xmlbeans.XmlString xgetChannel()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CHANNEL$20, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Channel" element
             */
            public void setChannel(java.lang.String channel)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CHANNEL$20, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CHANNEL$20);
                    }
                    target.setStringValue(channel);
                }
            }
            
            /**
             * Sets (as xml) the "Channel" element
             */
            public void xsetChannel(org.apache.xmlbeans.XmlString channel)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CHANNEL$20, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CHANNEL$20);
                    }
                    target.set(channel);
                }
            }
            
            /**
             * Gets the "Salesman_Code" element
             */
            public java.lang.String getSalesmanCode()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SALESMANCODE$22, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Salesman_Code" element
             */
            public org.apache.xmlbeans.XmlString xgetSalesmanCode()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SALESMANCODE$22, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Salesman_Code" element
             */
            public void setSalesmanCode(java.lang.String salesmanCode)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SALESMANCODE$22, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SALESMANCODE$22);
                    }
                    target.setStringValue(salesmanCode);
                }
            }
            
            /**
             * Sets (as xml) the "Salesman_Code" element
             */
            public void xsetSalesmanCode(org.apache.xmlbeans.XmlString salesmanCode)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SALESMANCODE$22, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SALESMANCODE$22);
                    }
                    target.set(salesmanCode);
                }
            }
            
            /**
             * Gets the "SM_ApprovedBy" element
             */
            public java.lang.String getSMApprovedBy()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SMAPPROVEDBY$24, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "SM_ApprovedBy" element
             */
            public org.apache.xmlbeans.XmlString xgetSMApprovedBy()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SMAPPROVEDBY$24, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "SM_ApprovedBy" element
             */
            public void setSMApprovedBy(java.lang.String smApprovedBy)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SMAPPROVEDBY$24, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SMAPPROVEDBY$24);
                    }
                    target.setStringValue(smApprovedBy);
                }
            }
            
            /**
             * Sets (as xml) the "SM_ApprovedBy" element
             */
            public void xsetSMApprovedBy(org.apache.xmlbeans.XmlString smApprovedBy)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SMAPPROVEDBY$24, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SMAPPROVEDBY$24);
                    }
                    target.set(smApprovedBy);
                }
            }
            
            /**
             * Gets the "QU_Num" element
             */
            public java.lang.String getQUNum()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(QUNUM$26, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "QU_Num" element
             */
            public org.apache.xmlbeans.XmlString xgetQUNum()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(QUNUM$26, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "QU_Num" element
             */
            public void setQUNum(java.lang.String quNum)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(QUNUM$26, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(QUNUM$26);
                    }
                    target.setStringValue(quNum);
                }
            }
            
            /**
             * Sets (as xml) the "QU_Num" element
             */
            public void xsetQUNum(org.apache.xmlbeans.XmlString quNum)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(QUNUM$26, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(QUNUM$26);
                    }
                    target.set(quNum);
                }
            }
            
            /**
             * Gets the "SM_Num" element
             */
            public java.lang.String getSMNum()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SMNUM$28, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "SM_Num" element
             */
            public org.apache.xmlbeans.XmlString xgetSMNum()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SMNUM$28, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "SM_Num" element
             */
            public void setSMNum(java.lang.String smNum)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SMNUM$28, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SMNUM$28);
                    }
                    target.setStringValue(smNum);
                }
            }
            
            /**
             * Sets (as xml) the "SM_Num" element
             */
            public void xsetSMNum(org.apache.xmlbeans.XmlString smNum)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SMNUM$28, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SMNUM$28);
                    }
                    target.set(smNum);
                }
            }
            
            /**
             * Gets the "TotalAmt" element
             */
            public double getTotalAmt()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TOTALAMT$30, 0);
                    if (target == null)
                    {
                      return 0.0;
                    }
                    return target.getDoubleValue();
                }
            }
            
            /**
             * Gets (as xml) the "TotalAmt" element
             */
            public org.apache.xmlbeans.XmlDouble xgetTotalAmt()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlDouble target = null;
                    target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(TOTALAMT$30, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "TotalAmt" element
             */
            public void setTotalAmt(double totalAmt)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TOTALAMT$30, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TOTALAMT$30);
                    }
                    target.setDoubleValue(totalAmt);
                }
            }
            
            /**
             * Sets (as xml) the "TotalAmt" element
             */
            public void xsetTotalAmt(org.apache.xmlbeans.XmlDouble totalAmt)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlDouble target = null;
                    target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(TOTALAMT$30, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(TOTALAMT$30);
                    }
                    target.set(totalAmt);
                }
            }
            
            /**
             * Gets the "SI_Need" element
             */
            public int getSINeed()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SINEED$32, 0);
                    if (target == null)
                    {
                      return 0;
                    }
                    return target.getIntValue();
                }
            }
            
            /**
             * Gets (as xml) the "SI_Need" element
             */
            public org.apache.xmlbeans.XmlInt xgetSINeed()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlInt target = null;
                    target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SINEED$32, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "SI_Need" element
             */
            public void setSINeed(int siNeed)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SINEED$32, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SINEED$32);
                    }
                    target.setIntValue(siNeed);
                }
            }
            
            /**
             * Sets (as xml) the "SI_Need" element
             */
            public void xsetSINeed(org.apache.xmlbeans.XmlInt siNeed)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlInt target = null;
                    target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SINEED$32, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(SINEED$32);
                    }
                    target.set(siNeed);
                }
            }
            
            /**
             * Gets the "SM_CreateDate" element
             */
            public java.lang.String getSMCreateDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SMCREATEDATE$34, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "SM_CreateDate" element
             */
            public org.apache.xmlbeans.XmlString xgetSMCreateDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SMCREATEDATE$34, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "SM_CreateDate" element
             */
            public void setSMCreateDate(java.lang.String smCreateDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SMCREATEDATE$34, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SMCREATEDATE$34);
                    }
                    target.setStringValue(smCreateDate);
                }
            }
            
            /**
             * Sets (as xml) the "SM_CreateDate" element
             */
            public void xsetSMCreateDate(org.apache.xmlbeans.XmlString smCreateDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SMCREATEDATE$34, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SMCREATEDATE$34);
                    }
                    target.set(smCreateDate);
                }
            }
            
            /**
             * Gets the "Modifyby" element
             */
            public java.lang.String getModifyby()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MODIFYBY$36, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Modifyby" element
             */
            public org.apache.xmlbeans.XmlString xgetModifyby()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MODIFYBY$36, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Modifyby" element
             */
            public void setModifyby(java.lang.String modifyby)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MODIFYBY$36, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MODIFYBY$36);
                    }
                    target.setStringValue(modifyby);
                }
            }
            
            /**
             * Sets (as xml) the "Modifyby" element
             */
            public void xsetModifyby(org.apache.xmlbeans.XmlString modifyby)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MODIFYBY$36, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MODIFYBY$36);
                    }
                    target.set(modifyby);
                }
            }
            
            /**
             * Gets the "ModifyDate" element
             */
            public java.lang.String getModifyDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MODIFYDATE$38, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "ModifyDate" element
             */
            public org.apache.xmlbeans.XmlString xgetModifyDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MODIFYDATE$38, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "ModifyDate" element
             */
            public void setModifyDate(java.lang.String modifyDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MODIFYDATE$38, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MODIFYDATE$38);
                    }
                    target.setStringValue(modifyDate);
                }
            }
            
            /**
             * Sets (as xml) the "ModifyDate" element
             */
            public void xsetModifyDate(org.apache.xmlbeans.XmlString modifyDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MODIFYDATE$38, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MODIFYDATE$38);
                    }
                    target.set(modifyDate);
                }
            }
            
            /**
             * Gets the "ConsolDate" element
             */
            public java.lang.String getConsolDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CONSOLDATE$40, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "ConsolDate" element
             */
            public org.apache.xmlbeans.XmlString xgetConsolDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CONSOLDATE$40, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "ConsolDate" element
             */
            public void setConsolDate(java.lang.String consolDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CONSOLDATE$40, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CONSOLDATE$40);
                    }
                    target.setStringValue(consolDate);
                }
            }
            
            /**
             * Sets (as xml) the "ConsolDate" element
             */
            public void xsetConsolDate(org.apache.xmlbeans.XmlString consolDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CONSOLDATE$40, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CONSOLDATE$40);
                    }
                    target.set(consolDate);
                }
            }
            
            /**
             * Gets the "SettlementDate" element
             */
            public java.lang.String getSettlementDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SETTLEMENTDATE$42, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "SettlementDate" element
             */
            public org.apache.xmlbeans.XmlString xgetSettlementDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SETTLEMENTDATE$42, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "SettlementDate" element
             */
            public void setSettlementDate(java.lang.String settlementDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SETTLEMENTDATE$42, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SETTLEMENTDATE$42);
                    }
                    target.setStringValue(settlementDate);
                }
            }
            
            /**
             * Sets (as xml) the "SettlementDate" element
             */
            public void xsetSettlementDate(org.apache.xmlbeans.XmlString settlementDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SETTLEMENTDATE$42, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SETTLEMENTDATE$42);
                    }
                    target.set(settlementDate);
                }
            }
            
            /**
             * Gets the "Settle_Log_Date" element
             */
            public java.lang.String getSettleLogDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SETTLELOGDATE$44, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Settle_Log_Date" element
             */
            public org.apache.xmlbeans.XmlString xgetSettleLogDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SETTLELOGDATE$44, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Settle_Log_Date" element
             */
            public void setSettleLogDate(java.lang.String settleLogDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SETTLELOGDATE$44, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SETTLELOGDATE$44);
                    }
                    target.setStringValue(settleLogDate);
                }
            }
            
            /**
             * Sets (as xml) the "Settle_Log_Date" element
             */
            public void xsetSettleLogDate(org.apache.xmlbeans.XmlString settleLogDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SETTLELOGDATE$44, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SETTLELOGDATE$44);
                    }
                    target.set(settleLogDate);
                }
            }
            
            /**
             * Gets the "Settlement_Staff_ID" element
             */
            public java.lang.String getSettlementStaffID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SETTLEMENTSTAFFID$46, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Settlement_Staff_ID" element
             */
            public org.apache.xmlbeans.XmlString xgetSettlementStaffID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SETTLEMENTSTAFFID$46, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Settlement_Staff_ID" element
             */
            public void setSettlementStaffID(java.lang.String settlementStaffID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SETTLEMENTSTAFFID$46, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SETTLEMENTSTAFFID$46);
                    }
                    target.setStringValue(settlementStaffID);
                }
            }
            
            /**
             * Sets (as xml) the "Settlement_Staff_ID" element
             */
            public void xsetSettlementStaffID(org.apache.xmlbeans.XmlString settlementStaffID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SETTLEMENTSTAFFID$46, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SETTLEMENTSTAFFID$46);
                    }
                    target.set(settlementStaffID);
                }
            }
            
            /**
             * Gets the "StaChgDate" element
             */
            public java.lang.String getStaChgDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STACHGDATE$48, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "StaChgDate" element
             */
            public org.apache.xmlbeans.XmlString xgetStaChgDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STACHGDATE$48, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "StaChgDate" element
             */
            public void setStaChgDate(java.lang.String staChgDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STACHGDATE$48, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STACHGDATE$48);
                    }
                    target.setStringValue(staChgDate);
                }
            }
            
            /**
             * Sets (as xml) the "StaChgDate" element
             */
            public void xsetStaChgDate(org.apache.xmlbeans.XmlString staChgDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STACHGDATE$48, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STACHGDATE$48);
                    }
                    target.set(staChgDate);
                }
            }
            
            /**
             * Gets the "StaChgBy" element
             */
            public java.lang.String getStaChgBy()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STACHGBY$50, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "StaChgBy" element
             */
            public org.apache.xmlbeans.XmlString xgetStaChgBy()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STACHGBY$50, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "StaChgBy" element
             */
            public void setStaChgBy(java.lang.String staChgBy)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STACHGBY$50, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STACHGBY$50);
                    }
                    target.setStringValue(staChgBy);
                }
            }
            
            /**
             * Sets (as xml) the "StaChgBy" element
             */
            public void xsetStaChgBy(org.apache.xmlbeans.XmlString staChgBy)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STACHGBY$50, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STACHGBY$50);
                    }
                    target.set(staChgBy);
                }
            }
            
            /**
             * Gets the "Pay_Settle_LogDate" element
             */
            public java.lang.String getPaySettleLogDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAYSETTLELOGDATE$52, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Pay_Settle_LogDate" element
             */
            public org.apache.xmlbeans.XmlString xgetPaySettleLogDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAYSETTLELOGDATE$52, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Pay_Settle_LogDate" element
             */
            public void setPaySettleLogDate(java.lang.String paySettleLogDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAYSETTLELOGDATE$52, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PAYSETTLELOGDATE$52);
                    }
                    target.setStringValue(paySettleLogDate);
                }
            }
            
            /**
             * Sets (as xml) the "Pay_Settle_LogDate" element
             */
            public void xsetPaySettleLogDate(org.apache.xmlbeans.XmlString paySettleLogDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAYSETTLELOGDATE$52, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PAYSETTLELOGDATE$52);
                    }
                    target.set(paySettleLogDate);
                }
            }
            
            /**
             * Gets the "Pay_Settle_PhyDate" element
             */
            public java.lang.String getPaySettlePhyDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAYSETTLEPHYDATE$54, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Pay_Settle_PhyDate" element
             */
            public org.apache.xmlbeans.XmlString xgetPaySettlePhyDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAYSETTLEPHYDATE$54, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Pay_Settle_PhyDate" element
             */
            public void setPaySettlePhyDate(java.lang.String paySettlePhyDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAYSETTLEPHYDATE$54, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PAYSETTLEPHYDATE$54);
                    }
                    target.setStringValue(paySettlePhyDate);
                }
            }
            
            /**
             * Sets (as xml) the "Pay_Settle_PhyDate" element
             */
            public void xsetPaySettlePhyDate(org.apache.xmlbeans.XmlString paySettlePhyDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAYSETTLEPHYDATE$54, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PAYSETTLEPHYDATE$54);
                    }
                    target.set(paySettlePhyDate);
                }
            }
            
            /**
             * Gets the "Deliver_Date" element
             */
            public java.lang.String getDeliverDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DELIVERDATE$56, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Deliver_Date" element
             */
            public org.apache.xmlbeans.XmlString xgetDeliverDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DELIVERDATE$56, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Deliver_Date" element
             */
            public void setDeliverDate(java.lang.String deliverDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DELIVERDATE$56, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DELIVERDATE$56);
                    }
                    target.setStringValue(deliverDate);
                }
            }
            
            /**
             * Sets (as xml) the "Deliver_Date" element
             */
            public void xsetDeliverDate(org.apache.xmlbeans.XmlString deliverDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DELIVERDATE$56, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DELIVERDATE$56);
                    }
                    target.set(deliverDate);
                }
            }
            
            /**
             * Gets the "Status" element
             */
            public int getStatus()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATUS$58, 0);
                    if (target == null)
                    {
                      return 0;
                    }
                    return target.getIntValue();
                }
            }
            
            /**
             * Gets (as xml) the "Status" element
             */
            public org.apache.xmlbeans.XmlInt xgetStatus()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlInt target = null;
                    target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(STATUS$58, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Status" element
             */
            public void setStatus(int status)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATUS$58, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STATUS$58);
                    }
                    target.setIntValue(status);
                }
            }
            
            /**
             * Sets (as xml) the "Status" element
             */
            public void xsetStatus(org.apache.xmlbeans.XmlInt status)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlInt target = null;
                    target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(STATUS$58, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(STATUS$58);
                    }
                    target.set(status);
                }
            }
            
            /**
             * Gets the "CPEFLAG" element
             */
            public java.lang.String getCPEFLAG()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CPEFLAG$60, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CPEFLAG" element
             */
            public org.apache.xmlbeans.XmlString xgetCPEFLAG()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CPEFLAG$60, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CPEFLAG" element
             */
            public void setCPEFLAG(java.lang.String cpeflag)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CPEFLAG$60, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CPEFLAG$60);
                    }
                    target.setStringValue(cpeflag);
                }
            }
            
            /**
             * Sets (as xml) the "CPEFLAG" element
             */
            public void xsetCPEFLAG(org.apache.xmlbeans.XmlString cpeflag)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CPEFLAG$60, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CPEFLAG$60);
                    }
                    target.set(cpeflag);
                }
            }
            
            /**
             * Gets the "Trans_Discount" element
             */
            public double getTransDiscount()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRANSDISCOUNT$62, 0);
                    if (target == null)
                    {
                      return 0.0;
                    }
                    return target.getDoubleValue();
                }
            }
            
            /**
             * Gets (as xml) the "Trans_Discount" element
             */
            public org.apache.xmlbeans.XmlDouble xgetTransDiscount()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlDouble target = null;
                    target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(TRANSDISCOUNT$62, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Trans_Discount" element
             */
            public void setTransDiscount(double transDiscount)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRANSDISCOUNT$62, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TRANSDISCOUNT$62);
                    }
                    target.setDoubleValue(transDiscount);
                }
            }
            
            /**
             * Sets (as xml) the "Trans_Discount" element
             */
            public void xsetTransDiscount(org.apache.xmlbeans.XmlDouble transDiscount)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlDouble target = null;
                    target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(TRANSDISCOUNT$62, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(TRANSDISCOUNT$62);
                    }
                    target.set(transDiscount);
                }
            }
            
            /**
             * Gets the "Trans_Reason" element
             */
            public java.lang.String getTransReason()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRANSREASON$64, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Trans_Reason" element
             */
            public org.apache.xmlbeans.XmlString xgetTransReason()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRANSREASON$64, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Trans_Reason" element
             */
            public void setTransReason(java.lang.String transReason)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRANSREASON$64, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TRANSREASON$64);
                    }
                    target.setStringValue(transReason);
                }
            }
            
            /**
             * Sets (as xml) the "Trans_Reason" element
             */
            public void xsetTransReason(org.apache.xmlbeans.XmlString transReason)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRANSREASON$64, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TRANSREASON$64);
                    }
                    target.set(transReason);
                }
            }
            
            /**
             * Gets the "CPE_Work_Num" element
             */
            public java.lang.String getCPEWorkNum()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CPEWORKNUM$66, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CPE_Work_Num" element
             */
            public org.apache.xmlbeans.XmlString xgetCPEWorkNum()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CPEWORKNUM$66, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CPE_Work_Num" element
             */
            public void setCPEWorkNum(java.lang.String cpeWorkNum)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CPEWORKNUM$66, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CPEWORKNUM$66);
                    }
                    target.setStringValue(cpeWorkNum);
                }
            }
            
            /**
             * Sets (as xml) the "CPE_Work_Num" element
             */
            public void xsetCPEWorkNum(org.apache.xmlbeans.XmlString cpeWorkNum)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CPEWORKNUM$66, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CPEWORKNUM$66);
                    }
                    target.set(cpeWorkNum);
                }
            }
            
            /**
             * Gets the "Converted" element
             */
            public int getConverted()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CONVERTED$68, 0);
                    if (target == null)
                    {
                      return 0;
                    }
                    return target.getIntValue();
                }
            }
            
            /**
             * Gets (as xml) the "Converted" element
             */
            public org.apache.xmlbeans.XmlInt xgetConverted()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlInt target = null;
                    target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(CONVERTED$68, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Converted" element
             */
            public void setConverted(int converted)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CONVERTED$68, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CONVERTED$68);
                    }
                    target.setIntValue(converted);
                }
            }
            
            /**
             * Sets (as xml) the "Converted" element
             */
            public void xsetConverted(org.apache.xmlbeans.XmlInt converted)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlInt target = null;
                    target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(CONVERTED$68, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(CONVERTED$68);
                    }
                    target.set(converted);
                }
            }
            
            /**
             * Gets the "Org_QU_Num" element
             */
            public java.lang.String getOrgQUNum()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ORGQUNUM$70, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Org_QU_Num" element
             */
            public org.apache.xmlbeans.XmlString xgetOrgQUNum()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ORGQUNUM$70, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Org_QU_Num" element
             */
            public void setOrgQUNum(java.lang.String orgQUNum)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ORGQUNUM$70, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ORGQUNUM$70);
                    }
                    target.setStringValue(orgQUNum);
                }
            }
            
            /**
             * Sets (as xml) the "Org_QU_Num" element
             */
            public void xsetOrgQUNum(org.apache.xmlbeans.XmlString orgQUNum)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ORGQUNUM$70, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ORGQUNUM$70);
                    }
                    target.set(orgQUNum);
                }
            }
            
            /**
             * Gets the "Ref_SM_No" element
             */
            public java.lang.String getRefSMNo()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REFSMNO$72, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Ref_SM_No" element
             */
            public org.apache.xmlbeans.XmlString xgetRefSMNo()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REFSMNO$72, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Ref_SM_No" element
             */
            public void setRefSMNo(java.lang.String refSMNo)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REFSMNO$72, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(REFSMNO$72);
                    }
                    target.setStringValue(refSMNo);
                }
            }
            
            /**
             * Sets (as xml) the "Ref_SM_No" element
             */
            public void xsetRefSMNo(org.apache.xmlbeans.XmlString refSMNo)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REFSMNO$72, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REFSMNO$72);
                    }
                    target.set(refSMNo);
                }
            }
            
            /**
             * Gets the "Invalidated_Flag" element
             */
            public int getInvalidatedFlag()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INVALIDATEDFLAG$74, 0);
                    if (target == null)
                    {
                      return 0;
                    }
                    return target.getIntValue();
                }
            }
            
            /**
             * Gets (as xml) the "Invalidated_Flag" element
             */
            public org.apache.xmlbeans.XmlInt xgetInvalidatedFlag()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlInt target = null;
                    target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(INVALIDATEDFLAG$74, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Invalidated_Flag" element
             */
            public void setInvalidatedFlag(int invalidatedFlag)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INVALIDATEDFLAG$74, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INVALIDATEDFLAG$74);
                    }
                    target.setIntValue(invalidatedFlag);
                }
            }
            
            /**
             * Sets (as xml) the "Invalidated_Flag" element
             */
            public void xsetInvalidatedFlag(org.apache.xmlbeans.XmlInt invalidatedFlag)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlInt target = null;
                    target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(INVALIDATEDFLAG$74, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(INVALIDATEDFLAG$74);
                    }
                    target.set(invalidatedFlag);
                }
            }
            
            /**
             * Gets the "Download" element
             */
            public int getDownload()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DOWNLOAD$76, 0);
                    if (target == null)
                    {
                      return 0;
                    }
                    return target.getIntValue();
                }
            }
            
            /**
             * Gets (as xml) the "Download" element
             */
            public org.apache.xmlbeans.XmlInt xgetDownload()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlInt target = null;
                    target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DOWNLOAD$76, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Download" element
             */
            public void setDownload(int download)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DOWNLOAD$76, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DOWNLOAD$76);
                    }
                    target.setIntValue(download);
                }
            }
            
            /**
             * Sets (as xml) the "Download" element
             */
            public void xsetDownload(org.apache.xmlbeans.XmlInt download)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlInt target = null;
                    target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DOWNLOAD$76, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(DOWNLOAD$76);
                    }
                    target.set(download);
                }
            }
            
            /**
             * Gets the "Print_Template" element
             */
            public java.lang.String getPrintTemplate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PRINTTEMPLATE$78, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Print_Template" element
             */
            public org.apache.xmlbeans.XmlString xgetPrintTemplate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PRINTTEMPLATE$78, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Print_Template" element
             */
            public void setPrintTemplate(java.lang.String printTemplate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PRINTTEMPLATE$78, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PRINTTEMPLATE$78);
                    }
                    target.setStringValue(printTemplate);
                }
            }
            
            /**
             * Sets (as xml) the "Print_Template" element
             */
            public void xsetPrintTemplate(org.apache.xmlbeans.XmlString printTemplate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PRINTTEMPLATE$78, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PRINTTEMPLATE$78);
                    }
                    target.set(printTemplate);
                }
            }
            
            /**
             * Gets the "Store_Type" element
             */
            public int getStoreType()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STORETYPE$80, 0);
                    if (target == null)
                    {
                      return 0;
                    }
                    return target.getIntValue();
                }
            }
            
            /**
             * Gets (as xml) the "Store_Type" element
             */
            public org.apache.xmlbeans.XmlInt xgetStoreType()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlInt target = null;
                    target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(STORETYPE$80, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Store_Type" element
             */
            public void setStoreType(int storeType)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STORETYPE$80, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STORETYPE$80);
                    }
                    target.setIntValue(storeType);
                }
            }
            
            /**
             * Sets (as xml) the "Store_Type" element
             */
            public void xsetStoreType(org.apache.xmlbeans.XmlInt storeType)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlInt target = null;
                    target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(STORETYPE$80, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(STORETYPE$80);
                    }
                    target.set(storeType);
                }
            }
            
            /**
             * Gets the "Store_Name" element
             */
            public java.lang.String getStoreName()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STORENAME$82, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Store_Name" element
             */
            public org.apache.xmlbeans.XmlString xgetStoreName()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STORENAME$82, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Store_Name" element
             */
            public void setStoreName(java.lang.String storeName)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STORENAME$82, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STORENAME$82);
                    }
                    target.setStringValue(storeName);
                }
            }
            
            /**
             * Sets (as xml) the "Store_Name" element
             */
            public void xsetStoreName(org.apache.xmlbeans.XmlString storeName)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STORENAME$82, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STORENAME$82);
                    }
                    target.set(storeName);
                }
            }
            
            /**
             * Gets the "Store_Tel" element
             */
            public java.lang.String getStoreTel()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STORETEL$84, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Store_Tel" element
             */
            public org.apache.xmlbeans.XmlString xgetStoreTel()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STORETEL$84, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Store_Tel" element
             */
            public void setStoreTel(java.lang.String storeTel)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STORETEL$84, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STORETEL$84);
                    }
                    target.setStringValue(storeTel);
                }
            }
            
            /**
             * Sets (as xml) the "Store_Tel" element
             */
            public void xsetStoreTel(org.apache.xmlbeans.XmlString storeTel)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STORETEL$84, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STORETEL$84);
                    }
                    target.set(storeTel);
                }
            }
            
            /**
             * Gets the "Store_Begin_Hours" element
             */
            public java.lang.String getStoreBeginHours()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STOREBEGINHOURS$86, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Store_Begin_Hours" element
             */
            public org.apache.xmlbeans.XmlString xgetStoreBeginHours()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STOREBEGINHOURS$86, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Store_Begin_Hours" element
             */
            public void setStoreBeginHours(java.lang.String storeBeginHours)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STOREBEGINHOURS$86, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STOREBEGINHOURS$86);
                    }
                    target.setStringValue(storeBeginHours);
                }
            }
            
            /**
             * Sets (as xml) the "Store_Begin_Hours" element
             */
            public void xsetStoreBeginHours(org.apache.xmlbeans.XmlString storeBeginHours)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STOREBEGINHOURS$86, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STOREBEGINHOURS$86);
                    }
                    target.set(storeBeginHours);
                }
            }
            
            /**
             * Gets the "Store_End_Hours" element
             */
            public java.lang.String getStoreEndHours()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STOREENDHOURS$88, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Store_End_Hours" element
             */
            public org.apache.xmlbeans.XmlString xgetStoreEndHours()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STOREENDHOURS$88, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Store_End_Hours" element
             */
            public void setStoreEndHours(java.lang.String storeEndHours)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STOREENDHOURS$88, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STOREENDHOURS$88);
                    }
                    target.setStringValue(storeEndHours);
                }
            }
            
            /**
             * Sets (as xml) the "Store_End_Hours" element
             */
            public void xsetStoreEndHours(org.apache.xmlbeans.XmlString storeEndHours)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STOREENDHOURS$88, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STOREENDHOURS$88);
                    }
                    target.set(storeEndHours);
                }
            }
            
            /**
             * Gets the "Store_Addr_1" element
             */
            public java.lang.String getStoreAddr1()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STOREADDR1$90, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Store_Addr_1" element
             */
            public org.apache.xmlbeans.XmlString xgetStoreAddr1()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STOREADDR1$90, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Store_Addr_1" element
             */
            public void setStoreAddr1(java.lang.String storeAddr1)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STOREADDR1$90, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STOREADDR1$90);
                    }
                    target.setStringValue(storeAddr1);
                }
            }
            
            /**
             * Sets (as xml) the "Store_Addr_1" element
             */
            public void xsetStoreAddr1(org.apache.xmlbeans.XmlString storeAddr1)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STOREADDR1$90, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STOREADDR1$90);
                    }
                    target.set(storeAddr1);
                }
            }
            
            /**
             * Gets the "Store_Addr_2" element
             */
            public java.lang.String getStoreAddr2()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STOREADDR2$92, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Store_Addr_2" element
             */
            public org.apache.xmlbeans.XmlString xgetStoreAddr2()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STOREADDR2$92, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Store_Addr_2" element
             */
            public void setStoreAddr2(java.lang.String storeAddr2)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STOREADDR2$92, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STOREADDR2$92);
                    }
                    target.setStringValue(storeAddr2);
                }
            }
            
            /**
             * Sets (as xml) the "Store_Addr_2" element
             */
            public void xsetStoreAddr2(org.apache.xmlbeans.XmlString storeAddr2)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STOREADDR2$92, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STOREADDR2$92);
                    }
                    target.set(storeAddr2);
                }
            }
            
            /**
             * Gets the "Store_Addr_3" element
             */
            public java.lang.String getStoreAddr3()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STOREADDR3$94, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Store_Addr_3" element
             */
            public org.apache.xmlbeans.XmlString xgetStoreAddr3()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STOREADDR3$94, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Store_Addr_3" element
             */
            public void setStoreAddr3(java.lang.String storeAddr3)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STOREADDR3$94, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STOREADDR3$94);
                    }
                    target.setStringValue(storeAddr3);
                }
            }
            
            /**
             * Sets (as xml) the "Store_Addr_3" element
             */
            public void xsetStoreAddr3(org.apache.xmlbeans.XmlString storeAddr3)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STOREADDR3$94, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STOREADDR3$94);
                    }
                    target.set(storeAddr3);
                }
            }
            
            /**
             * Gets the "Salesman_Name" element
             */
            public java.lang.String getSalesmanName()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SALESMANNAME$96, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Salesman_Name" element
             */
            public org.apache.xmlbeans.XmlString xgetSalesmanName()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SALESMANNAME$96, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Salesman_Name" element
             */
            public void setSalesmanName(java.lang.String salesmanName)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SALESMANNAME$96, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SALESMANNAME$96);
                    }
                    target.setStringValue(salesmanName);
                }
            }
            
            /**
             * Sets (as xml) the "Salesman_Name" element
             */
            public void xsetSalesmanName(org.apache.xmlbeans.XmlString salesmanName)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SALESMANNAME$96, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SALESMANNAME$96);
                    }
                    target.set(salesmanName);
                }
            }
            
            /**
             * Gets the "Settle_Staff_Name" element
             */
            public java.lang.String getSettleStaffName()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SETTLESTAFFNAME$98, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Settle_Staff_Name" element
             */
            public org.apache.xmlbeans.XmlString xgetSettleStaffName()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SETTLESTAFFNAME$98, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Settle_Staff_Name" element
             */
            public void setSettleStaffName(java.lang.String settleStaffName)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SETTLESTAFFNAME$98, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SETTLESTAFFNAME$98);
                    }
                    target.setStringValue(settleStaffName);
                }
            }
            
            /**
             * Sets (as xml) the "Settle_Staff_Name" element
             */
            public void xsetSettleStaffName(org.apache.xmlbeans.XmlString settleStaffName)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SETTLESTAFFNAME$98, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SETTLESTAFFNAME$98);
                    }
                    target.set(settleStaffName);
                }
            }
            
            /**
             * Gets the "CMR" element
             */
            public java.lang.String getCMR()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CMR$100, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CMR" element
             */
            public org.apache.xmlbeans.XmlString xgetCMR()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CMR$100, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CMR" element
             */
            public void setCMR(java.lang.String cmr)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CMR$100, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CMR$100);
                    }
                    target.setStringValue(cmr);
                }
            }
            
            /**
             * Sets (as xml) the "CMR" element
             */
            public void xsetCMR(org.apache.xmlbeans.XmlString cmr)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CMR$100, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CMR$100);
                    }
                    target.set(cmr);
                }
            }
            
            /**
             * Gets the "UM" element
             */
            public java.lang.String getUM()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(UM$102, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "UM" element
             */
            public org.apache.xmlbeans.XmlString xgetUM()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(UM$102, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "UM" element
             */
            public void setUM(java.lang.String um)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(UM$102, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(UM$102);
                    }
                    target.setStringValue(um);
                }
            }
            
            /**
             * Sets (as xml) the "UM" element
             */
            public void xsetUM(org.apache.xmlbeans.XmlString um)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(UM$102, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(UM$102);
                    }
                    target.set(um);
                }
            }
            
            /**
             * Gets the "Title" element
             */
            public java.lang.String getTitle()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TITLE$104, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Title" element
             */
            public org.apache.xmlbeans.XmlString xgetTitle()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TITLE$104, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Title" element
             */
            public void setTitle(java.lang.String title)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TITLE$104, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TITLE$104);
                    }
                    target.setStringValue(title);
                }
            }
            
            /**
             * Sets (as xml) the "Title" element
             */
            public void xsetTitle(org.apache.xmlbeans.XmlString title)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TITLE$104, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TITLE$104);
                    }
                    target.set(title);
                }
            }
            
            /**
             * Gets the "Team_Name" element
             */
            public java.lang.String getTeamName()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TEAMNAME$106, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Team_Name" element
             */
            public org.apache.xmlbeans.XmlString xgetTeamName()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TEAMNAME$106, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Team_Name" element
             */
            public void setTeamName(java.lang.String teamName)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TEAMNAME$106, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TEAMNAME$106);
                    }
                    target.setStringValue(teamName);
                }
            }
            
            /**
             * Sets (as xml) the "Team_Name" element
             */
            public void xsetTeamName(org.apache.xmlbeans.XmlString teamName)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TEAMNAME$106, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TEAMNAME$106);
                    }
                    target.set(teamName);
                }
            }
            
            /**
             * Gets the "Dep" element
             */
            public java.lang.String getDep()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEP$108, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Dep" element
             */
            public org.apache.xmlbeans.XmlString xgetDep()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEP$108, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Dep" element
             */
            public void setDep(java.lang.String dep)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEP$108, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEP$108);
                    }
                    target.setStringValue(dep);
                }
            }
            
            /**
             * Sets (as xml) the "Dep" element
             */
            public void xsetDep(org.apache.xmlbeans.XmlString dep)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEP$108, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEP$108);
                    }
                    target.set(dep);
                }
            }
            
            /**
             * Gets the "Dep_Name" element
             */
            public java.lang.String getDepName()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEPNAME$110, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Dep_Name" element
             */
            public org.apache.xmlbeans.XmlString xgetDepName()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEPNAME$110, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Dep_Name" element
             */
            public void setDepName(java.lang.String depName)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEPNAME$110, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEPNAME$110);
                    }
                    target.setStringValue(depName);
                }
            }
            
            /**
             * Sets (as xml) the "Dep_Name" element
             */
            public void xsetDepName(org.apache.xmlbeans.XmlString depName)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEPNAME$110, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEPNAME$110);
                    }
                    target.set(depName);
                }
            }
            
            /**
             * Gets the "Coll_Time" element
             */
            public java.lang.String getCollTime()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COLLTIME$112, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Coll_Time" element
             */
            public org.apache.xmlbeans.XmlString xgetCollTime()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COLLTIME$112, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Coll_Time" element
             */
            public void setCollTime(java.lang.String collTime)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COLLTIME$112, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COLLTIME$112);
                    }
                    target.setStringValue(collTime);
                }
            }
            
            /**
             * Sets (as xml) the "Coll_Time" element
             */
            public void xsetCollTime(org.apache.xmlbeans.XmlString collTime)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COLLTIME$112, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COLLTIME$112);
                    }
                    target.set(collTime);
                }
            }
            
            /**
             * Gets the "IMS_Acno" element
             */
            public java.lang.String getIMSAcno()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMSACNO$114, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "IMS_Acno" element
             */
            public org.apache.xmlbeans.XmlString xgetIMSAcno()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IMSACNO$114, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "IMS_Acno" element
             */
            public void setIMSAcno(java.lang.String imsAcno)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMSACNO$114, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMSACNO$114);
                    }
                    target.setStringValue(imsAcno);
                }
            }
            
            /**
             * Sets (as xml) the "IMS_Acno" element
             */
            public void xsetIMSAcno(org.apache.xmlbeans.XmlString imsAcno)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IMSACNO$114, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(IMSACNO$114);
                    }
                    target.set(imsAcno);
                }
            }
            
            /**
             * Gets the "Coll_Date" element
             */
            public java.lang.String getCollDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COLLDATE$116, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Coll_Date" element
             */
            public org.apache.xmlbeans.XmlString xgetCollDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COLLDATE$116, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Coll_Date" element
             */
            public void setCollDate(java.lang.String collDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COLLDATE$116, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COLLDATE$116);
                    }
                    target.setStringValue(collDate);
                }
            }
            
            /**
             * Sets (as xml) the "Coll_Date" element
             */
            public void xsetCollDate(org.apache.xmlbeans.XmlString collDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COLLDATE$116, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COLLDATE$116);
                    }
                    target.set(collDate);
                }
            }
            
            /**
             * Gets the "Paid_Amt" element
             */
            public double getPaidAmt()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAIDAMT$118, 0);
                    if (target == null)
                    {
                      return 0.0;
                    }
                    return target.getDoubleValue();
                }
            }
            
            /**
             * Gets (as xml) the "Paid_Amt" element
             */
            public org.apache.xmlbeans.XmlDouble xgetPaidAmt()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlDouble target = null;
                    target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(PAIDAMT$118, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Paid_Amt" element
             */
            public void setPaidAmt(double paidAmt)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAIDAMT$118, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PAIDAMT$118);
                    }
                    target.setDoubleValue(paidAmt);
                }
            }
            
            /**
             * Sets (as xml) the "Paid_Amt" element
             */
            public void xsetPaidAmt(org.apache.xmlbeans.XmlDouble paidAmt)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlDouble target = null;
                    target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(PAIDAMT$118, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(PAIDAMT$118);
                    }
                    target.set(paidAmt);
                }
            }
            
            /**
             * Gets the "Ref_SM_Type" element
             */
            public int getRefSMType()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REFSMTYPE$120, 0);
                    if (target == null)
                    {
                      return 0;
                    }
                    return target.getIntValue();
                }
            }
            
            /**
             * Gets (as xml) the "Ref_SM_Type" element
             */
            public org.apache.xmlbeans.XmlInt xgetRefSMType()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlInt target = null;
                    target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(REFSMTYPE$120, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Ref_SM_Type" element
             */
            public void setRefSMType(int refSMType)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REFSMTYPE$120, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(REFSMTYPE$120);
                    }
                    target.setIntValue(refSMType);
                }
            }
            
            /**
             * Sets (as xml) the "Ref_SM_Type" element
             */
            public void xsetRefSMType(org.apache.xmlbeans.XmlInt refSMType)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlInt target = null;
                    target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(REFSMTYPE$120, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(REFSMTYPE$120);
                    }
                    target.set(refSMType);
                }
            }
            
            /**
             * Gets the "Txn_Time_POS_BOM" element
             */
            public java.lang.String getTxnTimePOSBOM()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TXNTIMEPOSBOM$122, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Txn_Time_POS_BOM" element
             */
            public org.apache.xmlbeans.XmlString xgetTxnTimePOSBOM()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TXNTIMEPOSBOM$122, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Txn_Time_POS_BOM" element
             */
            public void setTxnTimePOSBOM(java.lang.String txnTimePOSBOM)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TXNTIMEPOSBOM$122, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TXNTIMEPOSBOM$122);
                    }
                    target.setStringValue(txnTimePOSBOM);
                }
            }
            
            /**
             * Sets (as xml) the "Txn_Time_POS_BOM" element
             */
            public void xsetTxnTimePOSBOM(org.apache.xmlbeans.XmlString txnTimePOSBOM)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TXNTIMEPOSBOM$122, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TXNTIMEPOSBOM$122);
                    }
                    target.set(txnTimePOSBOM);
                }
            }
            
            /**
             * Gets the "Txn_Date_POS_BOM" element
             */
            public java.lang.String getTxnDatePOSBOM()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TXNDATEPOSBOM$124, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Txn_Date_POS_BOM" element
             */
            public org.apache.xmlbeans.XmlString xgetTxnDatePOSBOM()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TXNDATEPOSBOM$124, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Txn_Date_POS_BOM" element
             */
            public void setTxnDatePOSBOM(java.lang.String txnDatePOSBOM)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TXNDATEPOSBOM$124, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TXNDATEPOSBOM$124);
                    }
                    target.setStringValue(txnDatePOSBOM);
                }
            }
            
            /**
             * Sets (as xml) the "Txn_Date_POS_BOM" element
             */
            public void xsetTxnDatePOSBOM(org.apache.xmlbeans.XmlString txnDatePOSBOM)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TXNDATEPOSBOM$124, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TXNDATEPOSBOM$124);
                    }
                    target.set(txnDatePOSBOM);
                }
            }
            
            /**
             * Gets the "Req_No" element
             */
            public java.lang.String getReqNo()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REQNO$126, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Req_No" element
             */
            public org.apache.xmlbeans.XmlString xgetReqNo()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REQNO$126, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Req_No" element
             */
            public void setReqNo(java.lang.String reqNo)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REQNO$126, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(REQNO$126);
                    }
                    target.setStringValue(reqNo);
                }
            }
            
            /**
             * Sets (as xml) the "Req_No" element
             */
            public void xsetReqNo(org.apache.xmlbeans.XmlString reqNo)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REQNO$126, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REQNO$126);
                    }
                    target.set(reqNo);
                }
            }
            
            /**
             * Gets the "Customer_ID" element
             */
            public java.lang.String getCustomerID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUSTOMERID$128, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Customer_ID" element
             */
            public org.apache.xmlbeans.XmlString xgetCustomerID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUSTOMERID$128, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Customer_ID" element
             */
            public void setCustomerID(java.lang.String customerID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUSTOMERID$128, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUSTOMERID$128);
                    }
                    target.setStringValue(customerID);
                }
            }
            
            /**
             * Sets (as xml) the "Customer_ID" element
             */
            public void xsetCustomerID(org.apache.xmlbeans.XmlString customerID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUSTOMERID$128, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUSTOMERID$128);
                    }
                    target.set(customerID);
                }
            }
            
            /**
             * Gets the "Account_Code" element
             */
            public java.lang.String getAccountCode()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ACCOUNTCODE$130, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Account_Code" element
             */
            public org.apache.xmlbeans.XmlString xgetAccountCode()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ACCOUNTCODE$130, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Account_Code" element
             */
            public void setAccountCode(java.lang.String accountCode)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ACCOUNTCODE$130, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ACCOUNTCODE$130);
                    }
                    target.setStringValue(accountCode);
                }
            }
            
            /**
             * Sets (as xml) the "Account_Code" element
             */
            public void xsetAccountCode(org.apache.xmlbeans.XmlString accountCode)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ACCOUNTCODE$130, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ACCOUNTCODE$130);
                    }
                    target.set(accountCode);
                }
            }
            
            /**
             * Gets the "SL_ID" element
             */
            public java.lang.String getSLID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SLID$132, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "SL_ID" element
             */
            public org.apache.xmlbeans.XmlString xgetSLID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SLID$132, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "SL_ID" element
             */
            public void setSLID(java.lang.String slid)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SLID$132, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SLID$132);
                    }
                    target.setStringValue(slid);
                }
            }
            
            /**
             * Sets (as xml) the "SL_ID" element
             */
            public void xsetSLID(org.apache.xmlbeans.XmlString slid)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SLID$132, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SLID$132);
                    }
                    target.set(slid);
                }
            }
            
            /**
             * Gets the "Print_SM" element
             */
            public java.lang.String getPrintSM()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PRINTSM$134, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Print_SM" element
             */
            public org.apache.xmlbeans.XmlString xgetPrintSM()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PRINTSM$134, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Print_SM" element
             */
            public void setPrintSM(java.lang.String printSM)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PRINTSM$134, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PRINTSM$134);
                    }
                    target.setStringValue(printSM);
                }
            }
            
            /**
             * Sets (as xml) the "Print_SM" element
             */
            public void xsetPrintSM(org.apache.xmlbeans.XmlString printSM)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PRINTSM$134, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PRINTSM$134);
                    }
                    target.set(printSM);
                }
            }
            
            /**
             * Gets the "Gen_SM_Pdf" element
             */
            public java.lang.String getGenSMPdf()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GENSMPDF$136, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Gen_SM_Pdf" element
             */
            public org.apache.xmlbeans.XmlString xgetGenSMPdf()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(GENSMPDF$136, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Gen_SM_Pdf" element
             */
            public void setGenSMPdf(java.lang.String genSMPdf)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GENSMPDF$136, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(GENSMPDF$136);
                    }
                    target.setStringValue(genSMPdf);
                }
            }
            
            /**
             * Sets (as xml) the "Gen_SM_Pdf" element
             */
            public void xsetGenSMPdf(org.apache.xmlbeans.XmlString genSMPdf)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(GENSMPDF$136, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(GENSMPDF$136);
                    }
                    target.set(genSMPdf);
                }
            }
        }
        /**
         * An XML customer(@).
         *
         * This is a complex type.
         */
        public static class CustomerImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.OrderRegInfoDocument.OrderRegInfo.Customer
        {
            private static final long serialVersionUID = 1L;
            
            public CustomerImpl(org.apache.xmlbeans.SchemaType sType)
            {
                super(sType);
            }
            
            private static final javax.xml.namespace.QName CUTITLE$0 = 
                new javax.xml.namespace.QName("", "CU_Title");
            private static final javax.xml.namespace.QName CUNAME$2 = 
                new javax.xml.namespace.QName("", "CU_Name");
            private static final javax.xml.namespace.QName CUCCCNO$4 = 
                new javax.xml.namespace.QName("", "CU_CCCNO");
            private static final javax.xml.namespace.QName CUTEL$6 = 
                new javax.xml.namespace.QName("", "CU_Tel");
            private static final javax.xml.namespace.QName CUPRIME$8 = 
                new javax.xml.namespace.QName("", "CU_Prime");
            private static final javax.xml.namespace.QName CUFAXNO$10 = 
                new javax.xml.namespace.QName("", "CU_FaxNo");
            private static final javax.xml.namespace.QName CUMEMTYPE$12 = 
                new javax.xml.namespace.QName("", "CU_MemType");
            private static final javax.xml.namespace.QName CUCONTPER$14 = 
                new javax.xml.namespace.QName("", "CU_ContPer");
            private static final javax.xml.namespace.QName CUMEMBERNO$16 = 
                new javax.xml.namespace.QName("", "CU_MemberNo");
            private static final javax.xml.namespace.QName CUINTERCOM$18 = 
                new javax.xml.namespace.QName("", "CU_InterCom");
            private static final javax.xml.namespace.QName CUIDBUSTYPE$20 = 
                new javax.xml.namespace.QName("", "CU_IDBusType");
            private static final javax.xml.namespace.QName CUIDBUSNO$22 = 
                new javax.xml.namespace.QName("", "CU_IDBusNo");
            private static final javax.xml.namespace.QName CUREMARK$24 = 
                new javax.xml.namespace.QName("", "CU_Remark");
            private static final javax.xml.namespace.QName CUINTREMARKS$26 = 
                new javax.xml.namespace.QName("", "CU_Int_Remarks");
            private static final javax.xml.namespace.QName CUREQUESTDATE$28 = 
                new javax.xml.namespace.QName("", "CU_Request_Date");
            private static final javax.xml.namespace.QName CUREQUESTTIME$30 = 
                new javax.xml.namespace.QName("", "CU_Request_Time");
            private static final javax.xml.namespace.QName CUPOSBADDEBT$32 = 
                new javax.xml.namespace.QName("", "CU_PosBadDebt");
            private static final javax.xml.namespace.QName CUEMAIL$34 = 
                new javax.xml.namespace.QName("", "CU_Email");
            
            
            /**
             * Gets the "CU_Title" element
             */
            public java.lang.String getCUTitle()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUTITLE$0, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_Title" element
             */
            public org.apache.xmlbeans.XmlString xgetCUTitle()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUTITLE$0, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_Title" element
             */
            public void setCUTitle(java.lang.String cuTitle)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUTITLE$0, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUTITLE$0);
                    }
                    target.setStringValue(cuTitle);
                }
            }
            
            /**
             * Sets (as xml) the "CU_Title" element
             */
            public void xsetCUTitle(org.apache.xmlbeans.XmlString cuTitle)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUTITLE$0, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUTITLE$0);
                    }
                    target.set(cuTitle);
                }
            }
            
            /**
             * Gets the "CU_Name" element
             */
            public java.lang.String getCUName()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUNAME$2, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_Name" element
             */
            public org.apache.xmlbeans.XmlString xgetCUName()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUNAME$2, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_Name" element
             */
            public void setCUName(java.lang.String cuName)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUNAME$2, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUNAME$2);
                    }
                    target.setStringValue(cuName);
                }
            }
            
            /**
             * Sets (as xml) the "CU_Name" element
             */
            public void xsetCUName(org.apache.xmlbeans.XmlString cuName)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUNAME$2, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUNAME$2);
                    }
                    target.set(cuName);
                }
            }
            
            /**
             * Gets the "CU_CCCNO" element
             */
            public java.lang.String getCUCCCNO()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCCCNO$4, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_CCCNO" element
             */
            public org.apache.xmlbeans.XmlString xgetCUCCCNO()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCCCNO$4, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_CCCNO" element
             */
            public void setCUCCCNO(java.lang.String cucccno)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCCCNO$4, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUCCCNO$4);
                    }
                    target.setStringValue(cucccno);
                }
            }
            
            /**
             * Sets (as xml) the "CU_CCCNO" element
             */
            public void xsetCUCCCNO(org.apache.xmlbeans.XmlString cucccno)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCCCNO$4, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUCCCNO$4);
                    }
                    target.set(cucccno);
                }
            }
            
            /**
             * Gets the "CU_Tel" element
             */
            public java.lang.String getCUTel()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUTEL$6, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_Tel" element
             */
            public org.apache.xmlbeans.XmlString xgetCUTel()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUTEL$6, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_Tel" element
             */
            public void setCUTel(java.lang.String cuTel)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUTEL$6, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUTEL$6);
                    }
                    target.setStringValue(cuTel);
                }
            }
            
            /**
             * Sets (as xml) the "CU_Tel" element
             */
            public void xsetCUTel(org.apache.xmlbeans.XmlString cuTel)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUTEL$6, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUTEL$6);
                    }
                    target.set(cuTel);
                }
            }
            
            /**
             * Gets the "CU_Prime" element
             */
            public java.lang.String getCUPrime()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUPRIME$8, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_Prime" element
             */
            public org.apache.xmlbeans.XmlString xgetCUPrime()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUPRIME$8, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_Prime" element
             */
            public void setCUPrime(java.lang.String cuPrime)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUPRIME$8, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUPRIME$8);
                    }
                    target.setStringValue(cuPrime);
                }
            }
            
            /**
             * Sets (as xml) the "CU_Prime" element
             */
            public void xsetCUPrime(org.apache.xmlbeans.XmlString cuPrime)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUPRIME$8, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUPRIME$8);
                    }
                    target.set(cuPrime);
                }
            }
            
            /**
             * Gets the "CU_FaxNo" element
             */
            public java.lang.String getCUFaxNo()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUFAXNO$10, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_FaxNo" element
             */
            public org.apache.xmlbeans.XmlString xgetCUFaxNo()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUFAXNO$10, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_FaxNo" element
             */
            public void setCUFaxNo(java.lang.String cuFaxNo)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUFAXNO$10, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUFAXNO$10);
                    }
                    target.setStringValue(cuFaxNo);
                }
            }
            
            /**
             * Sets (as xml) the "CU_FaxNo" element
             */
            public void xsetCUFaxNo(org.apache.xmlbeans.XmlString cuFaxNo)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUFAXNO$10, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUFAXNO$10);
                    }
                    target.set(cuFaxNo);
                }
            }
            
            /**
             * Gets the "CU_MemType" element
             */
            public java.lang.String getCUMemType()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUMEMTYPE$12, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_MemType" element
             */
            public org.apache.xmlbeans.XmlString xgetCUMemType()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUMEMTYPE$12, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_MemType" element
             */
            public void setCUMemType(java.lang.String cuMemType)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUMEMTYPE$12, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUMEMTYPE$12);
                    }
                    target.setStringValue(cuMemType);
                }
            }
            
            /**
             * Sets (as xml) the "CU_MemType" element
             */
            public void xsetCUMemType(org.apache.xmlbeans.XmlString cuMemType)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUMEMTYPE$12, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUMEMTYPE$12);
                    }
                    target.set(cuMemType);
                }
            }
            
            /**
             * Gets the "CU_ContPer" element
             */
            public java.lang.String getCUContPer()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCONTPER$14, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_ContPer" element
             */
            public org.apache.xmlbeans.XmlString xgetCUContPer()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCONTPER$14, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_ContPer" element
             */
            public void setCUContPer(java.lang.String cuContPer)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCONTPER$14, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUCONTPER$14);
                    }
                    target.setStringValue(cuContPer);
                }
            }
            
            /**
             * Sets (as xml) the "CU_ContPer" element
             */
            public void xsetCUContPer(org.apache.xmlbeans.XmlString cuContPer)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCONTPER$14, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUCONTPER$14);
                    }
                    target.set(cuContPer);
                }
            }
            
            /**
             * Gets the "CU_MemberNo" element
             */
            public java.lang.String getCUMemberNo()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUMEMBERNO$16, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_MemberNo" element
             */
            public org.apache.xmlbeans.XmlString xgetCUMemberNo()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUMEMBERNO$16, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_MemberNo" element
             */
            public void setCUMemberNo(java.lang.String cuMemberNo)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUMEMBERNO$16, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUMEMBERNO$16);
                    }
                    target.setStringValue(cuMemberNo);
                }
            }
            
            /**
             * Sets (as xml) the "CU_MemberNo" element
             */
            public void xsetCUMemberNo(org.apache.xmlbeans.XmlString cuMemberNo)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUMEMBERNO$16, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUMEMBERNO$16);
                    }
                    target.set(cuMemberNo);
                }
            }
            
            /**
             * Gets the "CU_InterCom" element
             */
            public boolean getCUInterCom()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUINTERCOM$18, 0);
                    if (target == null)
                    {
                      return false;
                    }
                    return target.getBooleanValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_InterCom" element
             */
            public org.apache.xmlbeans.XmlBoolean xgetCUInterCom()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlBoolean target = null;
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(CUINTERCOM$18, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_InterCom" element
             */
            public void setCUInterCom(boolean cuInterCom)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUINTERCOM$18, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUINTERCOM$18);
                    }
                    target.setBooleanValue(cuInterCom);
                }
            }
            
            /**
             * Sets (as xml) the "CU_InterCom" element
             */
            public void xsetCUInterCom(org.apache.xmlbeans.XmlBoolean cuInterCom)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlBoolean target = null;
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(CUINTERCOM$18, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(CUINTERCOM$18);
                    }
                    target.set(cuInterCom);
                }
            }
            
            /**
             * Gets the "CU_IDBusType" element
             */
            public java.lang.String getCUIDBusType()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUIDBUSTYPE$20, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_IDBusType" element
             */
            public org.apache.xmlbeans.XmlString xgetCUIDBusType()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUIDBUSTYPE$20, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_IDBusType" element
             */
            public void setCUIDBusType(java.lang.String cuidBusType)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUIDBUSTYPE$20, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUIDBUSTYPE$20);
                    }
                    target.setStringValue(cuidBusType);
                }
            }
            
            /**
             * Sets (as xml) the "CU_IDBusType" element
             */
            public void xsetCUIDBusType(org.apache.xmlbeans.XmlString cuidBusType)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUIDBUSTYPE$20, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUIDBUSTYPE$20);
                    }
                    target.set(cuidBusType);
                }
            }
            
            /**
             * Gets the "CU_IDBusNo" element
             */
            public java.lang.String getCUIDBusNo()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUIDBUSNO$22, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_IDBusNo" element
             */
            public org.apache.xmlbeans.XmlString xgetCUIDBusNo()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUIDBUSNO$22, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_IDBusNo" element
             */
            public void setCUIDBusNo(java.lang.String cuidBusNo)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUIDBUSNO$22, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUIDBUSNO$22);
                    }
                    target.setStringValue(cuidBusNo);
                }
            }
            
            /**
             * Sets (as xml) the "CU_IDBusNo" element
             */
            public void xsetCUIDBusNo(org.apache.xmlbeans.XmlString cuidBusNo)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUIDBUSNO$22, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUIDBUSNO$22);
                    }
                    target.set(cuidBusNo);
                }
            }
            
            /**
             * Gets the "CU_Remark" element
             */
            public java.lang.String getCURemark()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUREMARK$24, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_Remark" element
             */
            public org.apache.xmlbeans.XmlString xgetCURemark()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUREMARK$24, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_Remark" element
             */
            public void setCURemark(java.lang.String cuRemark)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUREMARK$24, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUREMARK$24);
                    }
                    target.setStringValue(cuRemark);
                }
            }
            
            /**
             * Sets (as xml) the "CU_Remark" element
             */
            public void xsetCURemark(org.apache.xmlbeans.XmlString cuRemark)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUREMARK$24, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUREMARK$24);
                    }
                    target.set(cuRemark);
                }
            }
            
            /**
             * Gets the "CU_Int_Remarks" element
             */
            public java.lang.String getCUIntRemarks()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUINTREMARKS$26, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_Int_Remarks" element
             */
            public org.apache.xmlbeans.XmlString xgetCUIntRemarks()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUINTREMARKS$26, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_Int_Remarks" element
             */
            public void setCUIntRemarks(java.lang.String cuIntRemarks)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUINTREMARKS$26, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUINTREMARKS$26);
                    }
                    target.setStringValue(cuIntRemarks);
                }
            }
            
            /**
             * Sets (as xml) the "CU_Int_Remarks" element
             */
            public void xsetCUIntRemarks(org.apache.xmlbeans.XmlString cuIntRemarks)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUINTREMARKS$26, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUINTREMARKS$26);
                    }
                    target.set(cuIntRemarks);
                }
            }
            
            /**
             * Gets the "CU_Request_Date" element
             */
            public java.lang.String getCURequestDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUREQUESTDATE$28, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_Request_Date" element
             */
            public org.apache.xmlbeans.XmlString xgetCURequestDate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUREQUESTDATE$28, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_Request_Date" element
             */
            public void setCURequestDate(java.lang.String cuRequestDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUREQUESTDATE$28, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUREQUESTDATE$28);
                    }
                    target.setStringValue(cuRequestDate);
                }
            }
            
            /**
             * Sets (as xml) the "CU_Request_Date" element
             */
            public void xsetCURequestDate(org.apache.xmlbeans.XmlString cuRequestDate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUREQUESTDATE$28, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUREQUESTDATE$28);
                    }
                    target.set(cuRequestDate);
                }
            }
            
            /**
             * Gets the "CU_Request_Time" element
             */
            public java.lang.String getCURequestTime()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUREQUESTTIME$30, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_Request_Time" element
             */
            public org.apache.xmlbeans.XmlString xgetCURequestTime()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUREQUESTTIME$30, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_Request_Time" element
             */
            public void setCURequestTime(java.lang.String cuRequestTime)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUREQUESTTIME$30, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUREQUESTTIME$30);
                    }
                    target.setStringValue(cuRequestTime);
                }
            }
            
            /**
             * Sets (as xml) the "CU_Request_Time" element
             */
            public void xsetCURequestTime(org.apache.xmlbeans.XmlString cuRequestTime)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUREQUESTTIME$30, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUREQUESTTIME$30);
                    }
                    target.set(cuRequestTime);
                }
            }
            
            /**
             * Gets the "CU_PosBadDebt" element
             */
            public boolean getCUPosBadDebt()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUPOSBADDEBT$32, 0);
                    if (target == null)
                    {
                      return false;
                    }
                    return target.getBooleanValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_PosBadDebt" element
             */
            public org.apache.xmlbeans.XmlBoolean xgetCUPosBadDebt()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlBoolean target = null;
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(CUPOSBADDEBT$32, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_PosBadDebt" element
             */
            public void setCUPosBadDebt(boolean cuPosBadDebt)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUPOSBADDEBT$32, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUPOSBADDEBT$32);
                    }
                    target.setBooleanValue(cuPosBadDebt);
                }
            }
            
            /**
             * Sets (as xml) the "CU_PosBadDebt" element
             */
            public void xsetCUPosBadDebt(org.apache.xmlbeans.XmlBoolean cuPosBadDebt)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlBoolean target = null;
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(CUPOSBADDEBT$32, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(CUPOSBADDEBT$32);
                    }
                    target.set(cuPosBadDebt);
                }
            }
            
            /**
             * Gets the "CU_Email" element
             */
            public java.lang.String getCUEmail()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUEMAIL$34, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_Email" element
             */
            public org.apache.xmlbeans.XmlString xgetCUEmail()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUEMAIL$34, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_Email" element
             */
            public void setCUEmail(java.lang.String cuEmail)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUEMAIL$34, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUEMAIL$34);
                    }
                    target.setStringValue(cuEmail);
                }
            }
            
            /**
             * Sets (as xml) the "CU_Email" element
             */
            public void xsetCUEmail(org.apache.xmlbeans.XmlString cuEmail)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUEMAIL$34, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUEMAIL$34);
                    }
                    target.set(cuEmail);
                }
            }
        }
        /**
         * An XML Address(@).
         *
         * This is a complex type.
         */
        public static class AddressImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.OrderRegInfoDocument.OrderRegInfo.Address
        {
            private static final long serialVersionUID = 1L;
            
            public AddressImpl(org.apache.xmlbeans.SchemaType sType)
            {
                super(sType);
            }
            
            private static final javax.xml.namespace.QName CUCFLATUNIT$0 = 
                new javax.xml.namespace.QName("", "CU_CFLATUNIT");
            private static final javax.xml.namespace.QName CUCFLOOR$2 = 
                new javax.xml.namespace.QName("", "CU_CFLOOR");
            private static final javax.xml.namespace.QName CUCBLDG$4 = 
                new javax.xml.namespace.QName("", "CU_CBLDG");
            private static final javax.xml.namespace.QName CUCPOBOX$6 = 
                new javax.xml.namespace.QName("", "CU_CPOBOX");
            private static final javax.xml.namespace.QName CUCHSESTNO$8 = 
                new javax.xml.namespace.QName("", "CU_CHSESTNO");
            private static final javax.xml.namespace.QName CUCLOTLDNO$10 = 
                new javax.xml.namespace.QName("", "CU_CLOTLDNO");
            private static final javax.xml.namespace.QName CUCSTNAME$12 = 
                new javax.xml.namespace.QName("", "CU_CSTNAME");
            private static final javax.xml.namespace.QName CUCSTCAT$14 = 
                new javax.xml.namespace.QName("", "CU_CSTCAT");
            private static final javax.xml.namespace.QName CUCSECT$16 = 
                new javax.xml.namespace.QName("", "CU_CSECT");
            private static final javax.xml.namespace.QName CUCDISTR$18 = 
                new javax.xml.namespace.QName("", "CU_CDISTR");
            private static final javax.xml.namespace.QName CUCAREA$20 = 
                new javax.xml.namespace.QName("", "CU_CAREA");
            private static final javax.xml.namespace.QName CUCPOSTREGN$22 = 
                new javax.xml.namespace.QName("", "CU_CPOSTREGN");
            private static final javax.xml.namespace.QName CUCPOSTCD$24 = 
                new javax.xml.namespace.QName("", "CU_CPOSTCD");
            private static final javax.xml.namespace.QName CUCCOUNTRY$26 = 
                new javax.xml.namespace.QName("", "CU_CCOUNTRY");
            private static final javax.xml.namespace.QName CUCATTETO$28 = 
                new javax.xml.namespace.QName("", "CU_CATTETO");
            private static final javax.xml.namespace.QName CUCCO$30 = 
                new javax.xml.namespace.QName("", "CU_CCO");
            private static final javax.xml.namespace.QName CUIFLATUNIT$32 = 
                new javax.xml.namespace.QName("", "CU_IFLATUNIT");
            private static final javax.xml.namespace.QName CUIFLOOR$34 = 
                new javax.xml.namespace.QName("", "CU_IFLOOR");
            private static final javax.xml.namespace.QName CUIBLDG$36 = 
                new javax.xml.namespace.QName("", "CU_IBLDG");
            private static final javax.xml.namespace.QName CUIPOBOX$38 = 
                new javax.xml.namespace.QName("", "CU_IPOBOX");
            private static final javax.xml.namespace.QName CUIHSESTNO$40 = 
                new javax.xml.namespace.QName("", "CU_IHSESTNO");
            private static final javax.xml.namespace.QName CUILOTLDNO$42 = 
                new javax.xml.namespace.QName("", "CU_ILOTLDNO");
            private static final javax.xml.namespace.QName CUISTNAME$44 = 
                new javax.xml.namespace.QName("", "CU_ISTNAME");
            private static final javax.xml.namespace.QName CUISTCAT$46 = 
                new javax.xml.namespace.QName("", "CU_ISTCAT");
            private static final javax.xml.namespace.QName CUISECT$48 = 
                new javax.xml.namespace.QName("", "CU_ISECT");
            private static final javax.xml.namespace.QName CUIDISTR$50 = 
                new javax.xml.namespace.QName("", "CU_IDISTR");
            private static final javax.xml.namespace.QName CUIAREA$52 = 
                new javax.xml.namespace.QName("", "CU_IAREA");
            private static final javax.xml.namespace.QName CUIPOSTREGN$54 = 
                new javax.xml.namespace.QName("", "CU_IPOSTREGN");
            private static final javax.xml.namespace.QName CUIPOSTCD$56 = 
                new javax.xml.namespace.QName("", "CU_IPOSTCD");
            private static final javax.xml.namespace.QName CUICOUNTRY$58 = 
                new javax.xml.namespace.QName("", "CU_ICOUNTRY");
            
            
            /**
             * Gets the "CU_CFLATUNIT" element
             */
            public java.lang.String getCUCFLATUNIT()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCFLATUNIT$0, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_CFLATUNIT" element
             */
            public org.apache.xmlbeans.XmlString xgetCUCFLATUNIT()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCFLATUNIT$0, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_CFLATUNIT" element
             */
            public void setCUCFLATUNIT(java.lang.String cucflatunit)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCFLATUNIT$0, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUCFLATUNIT$0);
                    }
                    target.setStringValue(cucflatunit);
                }
            }
            
            /**
             * Sets (as xml) the "CU_CFLATUNIT" element
             */
            public void xsetCUCFLATUNIT(org.apache.xmlbeans.XmlString cucflatunit)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCFLATUNIT$0, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUCFLATUNIT$0);
                    }
                    target.set(cucflatunit);
                }
            }
            
            /**
             * Gets the "CU_CFLOOR" element
             */
            public java.lang.String getCUCFLOOR()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCFLOOR$2, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_CFLOOR" element
             */
            public org.apache.xmlbeans.XmlString xgetCUCFLOOR()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCFLOOR$2, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_CFLOOR" element
             */
            public void setCUCFLOOR(java.lang.String cucfloor)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCFLOOR$2, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUCFLOOR$2);
                    }
                    target.setStringValue(cucfloor);
                }
            }
            
            /**
             * Sets (as xml) the "CU_CFLOOR" element
             */
            public void xsetCUCFLOOR(org.apache.xmlbeans.XmlString cucfloor)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCFLOOR$2, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUCFLOOR$2);
                    }
                    target.set(cucfloor);
                }
            }
            
            /**
             * Gets the "CU_CBLDG" element
             */
            public java.lang.String getCUCBLDG()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCBLDG$4, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_CBLDG" element
             */
            public org.apache.xmlbeans.XmlString xgetCUCBLDG()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCBLDG$4, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_CBLDG" element
             */
            public void setCUCBLDG(java.lang.String cucbldg)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCBLDG$4, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUCBLDG$4);
                    }
                    target.setStringValue(cucbldg);
                }
            }
            
            /**
             * Sets (as xml) the "CU_CBLDG" element
             */
            public void xsetCUCBLDG(org.apache.xmlbeans.XmlString cucbldg)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCBLDG$4, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUCBLDG$4);
                    }
                    target.set(cucbldg);
                }
            }
            
            /**
             * Gets the "CU_CPOBOX" element
             */
            public java.lang.String getCUCPOBOX()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCPOBOX$6, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_CPOBOX" element
             */
            public org.apache.xmlbeans.XmlString xgetCUCPOBOX()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCPOBOX$6, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_CPOBOX" element
             */
            public void setCUCPOBOX(java.lang.String cucpobox)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCPOBOX$6, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUCPOBOX$6);
                    }
                    target.setStringValue(cucpobox);
                }
            }
            
            /**
             * Sets (as xml) the "CU_CPOBOX" element
             */
            public void xsetCUCPOBOX(org.apache.xmlbeans.XmlString cucpobox)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCPOBOX$6, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUCPOBOX$6);
                    }
                    target.set(cucpobox);
                }
            }
            
            /**
             * Gets the "CU_CHSESTNO" element
             */
            public java.lang.String getCUCHSESTNO()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCHSESTNO$8, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_CHSESTNO" element
             */
            public org.apache.xmlbeans.XmlString xgetCUCHSESTNO()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCHSESTNO$8, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_CHSESTNO" element
             */
            public void setCUCHSESTNO(java.lang.String cuchsestno)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCHSESTNO$8, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUCHSESTNO$8);
                    }
                    target.setStringValue(cuchsestno);
                }
            }
            
            /**
             * Sets (as xml) the "CU_CHSESTNO" element
             */
            public void xsetCUCHSESTNO(org.apache.xmlbeans.XmlString cuchsestno)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCHSESTNO$8, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUCHSESTNO$8);
                    }
                    target.set(cuchsestno);
                }
            }
            
            /**
             * Gets the "CU_CLOTLDNO" element
             */
            public java.lang.String getCUCLOTLDNO()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCLOTLDNO$10, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_CLOTLDNO" element
             */
            public org.apache.xmlbeans.XmlString xgetCUCLOTLDNO()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCLOTLDNO$10, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_CLOTLDNO" element
             */
            public void setCUCLOTLDNO(java.lang.String cuclotldno)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCLOTLDNO$10, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUCLOTLDNO$10);
                    }
                    target.setStringValue(cuclotldno);
                }
            }
            
            /**
             * Sets (as xml) the "CU_CLOTLDNO" element
             */
            public void xsetCUCLOTLDNO(org.apache.xmlbeans.XmlString cuclotldno)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCLOTLDNO$10, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUCLOTLDNO$10);
                    }
                    target.set(cuclotldno);
                }
            }
            
            /**
             * Gets the "CU_CSTNAME" element
             */
            public java.lang.String getCUCSTNAME()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCSTNAME$12, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_CSTNAME" element
             */
            public org.apache.xmlbeans.XmlString xgetCUCSTNAME()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCSTNAME$12, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_CSTNAME" element
             */
            public void setCUCSTNAME(java.lang.String cucstname)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCSTNAME$12, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUCSTNAME$12);
                    }
                    target.setStringValue(cucstname);
                }
            }
            
            /**
             * Sets (as xml) the "CU_CSTNAME" element
             */
            public void xsetCUCSTNAME(org.apache.xmlbeans.XmlString cucstname)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCSTNAME$12, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUCSTNAME$12);
                    }
                    target.set(cucstname);
                }
            }
            
            /**
             * Gets the "CU_CSTCAT" element
             */
            public java.lang.String getCUCSTCAT()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCSTCAT$14, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_CSTCAT" element
             */
            public org.apache.xmlbeans.XmlString xgetCUCSTCAT()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCSTCAT$14, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_CSTCAT" element
             */
            public void setCUCSTCAT(java.lang.String cucstcat)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCSTCAT$14, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUCSTCAT$14);
                    }
                    target.setStringValue(cucstcat);
                }
            }
            
            /**
             * Sets (as xml) the "CU_CSTCAT" element
             */
            public void xsetCUCSTCAT(org.apache.xmlbeans.XmlString cucstcat)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCSTCAT$14, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUCSTCAT$14);
                    }
                    target.set(cucstcat);
                }
            }
            
            /**
             * Gets the "CU_CSECT" element
             */
            public java.lang.String getCUCSECT()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCSECT$16, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_CSECT" element
             */
            public org.apache.xmlbeans.XmlString xgetCUCSECT()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCSECT$16, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_CSECT" element
             */
            public void setCUCSECT(java.lang.String cucsect)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCSECT$16, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUCSECT$16);
                    }
                    target.setStringValue(cucsect);
                }
            }
            
            /**
             * Sets (as xml) the "CU_CSECT" element
             */
            public void xsetCUCSECT(org.apache.xmlbeans.XmlString cucsect)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCSECT$16, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUCSECT$16);
                    }
                    target.set(cucsect);
                }
            }
            
            /**
             * Gets the "CU_CDISTR" element
             */
            public java.lang.String getCUCDISTR()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCDISTR$18, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_CDISTR" element
             */
            public org.apache.xmlbeans.XmlString xgetCUCDISTR()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCDISTR$18, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_CDISTR" element
             */
            public void setCUCDISTR(java.lang.String cucdistr)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCDISTR$18, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUCDISTR$18);
                    }
                    target.setStringValue(cucdistr);
                }
            }
            
            /**
             * Sets (as xml) the "CU_CDISTR" element
             */
            public void xsetCUCDISTR(org.apache.xmlbeans.XmlString cucdistr)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCDISTR$18, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUCDISTR$18);
                    }
                    target.set(cucdistr);
                }
            }
            
            /**
             * Gets the "CU_CAREA" element
             */
            public java.lang.String getCUCAREA()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCAREA$20, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_CAREA" element
             */
            public org.apache.xmlbeans.XmlString xgetCUCAREA()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCAREA$20, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_CAREA" element
             */
            public void setCUCAREA(java.lang.String cucarea)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCAREA$20, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUCAREA$20);
                    }
                    target.setStringValue(cucarea);
                }
            }
            
            /**
             * Sets (as xml) the "CU_CAREA" element
             */
            public void xsetCUCAREA(org.apache.xmlbeans.XmlString cucarea)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCAREA$20, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUCAREA$20);
                    }
                    target.set(cucarea);
                }
            }
            
            /**
             * Gets the "CU_CPOSTREGN" element
             */
            public java.lang.String getCUCPOSTREGN()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCPOSTREGN$22, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_CPOSTREGN" element
             */
            public org.apache.xmlbeans.XmlString xgetCUCPOSTREGN()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCPOSTREGN$22, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_CPOSTREGN" element
             */
            public void setCUCPOSTREGN(java.lang.String cucpostregn)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCPOSTREGN$22, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUCPOSTREGN$22);
                    }
                    target.setStringValue(cucpostregn);
                }
            }
            
            /**
             * Sets (as xml) the "CU_CPOSTREGN" element
             */
            public void xsetCUCPOSTREGN(org.apache.xmlbeans.XmlString cucpostregn)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCPOSTREGN$22, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUCPOSTREGN$22);
                    }
                    target.set(cucpostregn);
                }
            }
            
            /**
             * Gets the "CU_CPOSTCD" element
             */
            public java.lang.String getCUCPOSTCD()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCPOSTCD$24, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_CPOSTCD" element
             */
            public org.apache.xmlbeans.XmlString xgetCUCPOSTCD()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCPOSTCD$24, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_CPOSTCD" element
             */
            public void setCUCPOSTCD(java.lang.String cucpostcd)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCPOSTCD$24, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUCPOSTCD$24);
                    }
                    target.setStringValue(cucpostcd);
                }
            }
            
            /**
             * Sets (as xml) the "CU_CPOSTCD" element
             */
            public void xsetCUCPOSTCD(org.apache.xmlbeans.XmlString cucpostcd)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCPOSTCD$24, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUCPOSTCD$24);
                    }
                    target.set(cucpostcd);
                }
            }
            
            /**
             * Gets the "CU_CCOUNTRY" element
             */
            public java.lang.String getCUCCOUNTRY()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCCOUNTRY$26, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_CCOUNTRY" element
             */
            public org.apache.xmlbeans.XmlString xgetCUCCOUNTRY()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCCOUNTRY$26, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_CCOUNTRY" element
             */
            public void setCUCCOUNTRY(java.lang.String cuccountry)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCCOUNTRY$26, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUCCOUNTRY$26);
                    }
                    target.setStringValue(cuccountry);
                }
            }
            
            /**
             * Sets (as xml) the "CU_CCOUNTRY" element
             */
            public void xsetCUCCOUNTRY(org.apache.xmlbeans.XmlString cuccountry)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCCOUNTRY$26, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUCCOUNTRY$26);
                    }
                    target.set(cuccountry);
                }
            }
            
            /**
             * Gets the "CU_CATTETO" element
             */
            public java.lang.String getCUCATTETO()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCATTETO$28, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_CATTETO" element
             */
            public org.apache.xmlbeans.XmlString xgetCUCATTETO()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCATTETO$28, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_CATTETO" element
             */
            public void setCUCATTETO(java.lang.String cucatteto)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCATTETO$28, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUCATTETO$28);
                    }
                    target.setStringValue(cucatteto);
                }
            }
            
            /**
             * Sets (as xml) the "CU_CATTETO" element
             */
            public void xsetCUCATTETO(org.apache.xmlbeans.XmlString cucatteto)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCATTETO$28, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUCATTETO$28);
                    }
                    target.set(cucatteto);
                }
            }
            
            /**
             * Gets the "CU_CCO" element
             */
            public java.lang.String getCUCCO()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCCO$30, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_CCO" element
             */
            public org.apache.xmlbeans.XmlString xgetCUCCO()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCCO$30, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_CCO" element
             */
            public void setCUCCO(java.lang.String cucco)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUCCO$30, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUCCO$30);
                    }
                    target.setStringValue(cucco);
                }
            }
            
            /**
             * Sets (as xml) the "CU_CCO" element
             */
            public void xsetCUCCO(org.apache.xmlbeans.XmlString cucco)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUCCO$30, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUCCO$30);
                    }
                    target.set(cucco);
                }
            }
            
            /**
             * Gets the "CU_IFLATUNIT" element
             */
            public java.lang.String getCUIFLATUNIT()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUIFLATUNIT$32, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_IFLATUNIT" element
             */
            public org.apache.xmlbeans.XmlString xgetCUIFLATUNIT()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUIFLATUNIT$32, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_IFLATUNIT" element
             */
            public void setCUIFLATUNIT(java.lang.String cuiflatunit)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUIFLATUNIT$32, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUIFLATUNIT$32);
                    }
                    target.setStringValue(cuiflatunit);
                }
            }
            
            /**
             * Sets (as xml) the "CU_IFLATUNIT" element
             */
            public void xsetCUIFLATUNIT(org.apache.xmlbeans.XmlString cuiflatunit)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUIFLATUNIT$32, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUIFLATUNIT$32);
                    }
                    target.set(cuiflatunit);
                }
            }
            
            /**
             * Gets the "CU_IFLOOR" element
             */
            public java.lang.String getCUIFLOOR()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUIFLOOR$34, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_IFLOOR" element
             */
            public org.apache.xmlbeans.XmlString xgetCUIFLOOR()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUIFLOOR$34, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_IFLOOR" element
             */
            public void setCUIFLOOR(java.lang.String cuifloor)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUIFLOOR$34, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUIFLOOR$34);
                    }
                    target.setStringValue(cuifloor);
                }
            }
            
            /**
             * Sets (as xml) the "CU_IFLOOR" element
             */
            public void xsetCUIFLOOR(org.apache.xmlbeans.XmlString cuifloor)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUIFLOOR$34, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUIFLOOR$34);
                    }
                    target.set(cuifloor);
                }
            }
            
            /**
             * Gets the "CU_IBLDG" element
             */
            public java.lang.String getCUIBLDG()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUIBLDG$36, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_IBLDG" element
             */
            public org.apache.xmlbeans.XmlString xgetCUIBLDG()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUIBLDG$36, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_IBLDG" element
             */
            public void setCUIBLDG(java.lang.String cuibldg)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUIBLDG$36, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUIBLDG$36);
                    }
                    target.setStringValue(cuibldg);
                }
            }
            
            /**
             * Sets (as xml) the "CU_IBLDG" element
             */
            public void xsetCUIBLDG(org.apache.xmlbeans.XmlString cuibldg)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUIBLDG$36, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUIBLDG$36);
                    }
                    target.set(cuibldg);
                }
            }
            
            /**
             * Gets the "CU_IPOBOX" element
             */
            public java.lang.String getCUIPOBOX()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUIPOBOX$38, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_IPOBOX" element
             */
            public org.apache.xmlbeans.XmlString xgetCUIPOBOX()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUIPOBOX$38, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_IPOBOX" element
             */
            public void setCUIPOBOX(java.lang.String cuipobox)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUIPOBOX$38, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUIPOBOX$38);
                    }
                    target.setStringValue(cuipobox);
                }
            }
            
            /**
             * Sets (as xml) the "CU_IPOBOX" element
             */
            public void xsetCUIPOBOX(org.apache.xmlbeans.XmlString cuipobox)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUIPOBOX$38, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUIPOBOX$38);
                    }
                    target.set(cuipobox);
                }
            }
            
            /**
             * Gets the "CU_IHSESTNO" element
             */
            public java.lang.String getCUIHSESTNO()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUIHSESTNO$40, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_IHSESTNO" element
             */
            public org.apache.xmlbeans.XmlString xgetCUIHSESTNO()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUIHSESTNO$40, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_IHSESTNO" element
             */
            public void setCUIHSESTNO(java.lang.String cuihsestno)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUIHSESTNO$40, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUIHSESTNO$40);
                    }
                    target.setStringValue(cuihsestno);
                }
            }
            
            /**
             * Sets (as xml) the "CU_IHSESTNO" element
             */
            public void xsetCUIHSESTNO(org.apache.xmlbeans.XmlString cuihsestno)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUIHSESTNO$40, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUIHSESTNO$40);
                    }
                    target.set(cuihsestno);
                }
            }
            
            /**
             * Gets the "CU_ILOTLDNO" element
             */
            public java.lang.String getCUILOTLDNO()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUILOTLDNO$42, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_ILOTLDNO" element
             */
            public org.apache.xmlbeans.XmlString xgetCUILOTLDNO()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUILOTLDNO$42, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_ILOTLDNO" element
             */
            public void setCUILOTLDNO(java.lang.String cuilotldno)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUILOTLDNO$42, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUILOTLDNO$42);
                    }
                    target.setStringValue(cuilotldno);
                }
            }
            
            /**
             * Sets (as xml) the "CU_ILOTLDNO" element
             */
            public void xsetCUILOTLDNO(org.apache.xmlbeans.XmlString cuilotldno)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUILOTLDNO$42, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUILOTLDNO$42);
                    }
                    target.set(cuilotldno);
                }
            }
            
            /**
             * Gets the "CU_ISTNAME" element
             */
            public java.lang.String getCUISTNAME()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUISTNAME$44, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_ISTNAME" element
             */
            public org.apache.xmlbeans.XmlString xgetCUISTNAME()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUISTNAME$44, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_ISTNAME" element
             */
            public void setCUISTNAME(java.lang.String cuistname)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUISTNAME$44, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUISTNAME$44);
                    }
                    target.setStringValue(cuistname);
                }
            }
            
            /**
             * Sets (as xml) the "CU_ISTNAME" element
             */
            public void xsetCUISTNAME(org.apache.xmlbeans.XmlString cuistname)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUISTNAME$44, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUISTNAME$44);
                    }
                    target.set(cuistname);
                }
            }
            
            /**
             * Gets the "CU_ISTCAT" element
             */
            public java.lang.String getCUISTCAT()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUISTCAT$46, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_ISTCAT" element
             */
            public org.apache.xmlbeans.XmlString xgetCUISTCAT()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUISTCAT$46, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_ISTCAT" element
             */
            public void setCUISTCAT(java.lang.String cuistcat)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUISTCAT$46, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUISTCAT$46);
                    }
                    target.setStringValue(cuistcat);
                }
            }
            
            /**
             * Sets (as xml) the "CU_ISTCAT" element
             */
            public void xsetCUISTCAT(org.apache.xmlbeans.XmlString cuistcat)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUISTCAT$46, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUISTCAT$46);
                    }
                    target.set(cuistcat);
                }
            }
            
            /**
             * Gets the "CU_ISECT" element
             */
            public java.lang.String getCUISECT()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUISECT$48, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_ISECT" element
             */
            public org.apache.xmlbeans.XmlString xgetCUISECT()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUISECT$48, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_ISECT" element
             */
            public void setCUISECT(java.lang.String cuisect)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUISECT$48, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUISECT$48);
                    }
                    target.setStringValue(cuisect);
                }
            }
            
            /**
             * Sets (as xml) the "CU_ISECT" element
             */
            public void xsetCUISECT(org.apache.xmlbeans.XmlString cuisect)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUISECT$48, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUISECT$48);
                    }
                    target.set(cuisect);
                }
            }
            
            /**
             * Gets the "CU_IDISTR" element
             */
            public java.lang.String getCUIDISTR()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUIDISTR$50, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_IDISTR" element
             */
            public org.apache.xmlbeans.XmlString xgetCUIDISTR()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUIDISTR$50, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_IDISTR" element
             */
            public void setCUIDISTR(java.lang.String cuidistr)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUIDISTR$50, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUIDISTR$50);
                    }
                    target.setStringValue(cuidistr);
                }
            }
            
            /**
             * Sets (as xml) the "CU_IDISTR" element
             */
            public void xsetCUIDISTR(org.apache.xmlbeans.XmlString cuidistr)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUIDISTR$50, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUIDISTR$50);
                    }
                    target.set(cuidistr);
                }
            }
            
            /**
             * Gets the "CU_IAREA" element
             */
            public java.lang.String getCUIAREA()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUIAREA$52, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_IAREA" element
             */
            public org.apache.xmlbeans.XmlString xgetCUIAREA()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUIAREA$52, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_IAREA" element
             */
            public void setCUIAREA(java.lang.String cuiarea)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUIAREA$52, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUIAREA$52);
                    }
                    target.setStringValue(cuiarea);
                }
            }
            
            /**
             * Sets (as xml) the "CU_IAREA" element
             */
            public void xsetCUIAREA(org.apache.xmlbeans.XmlString cuiarea)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUIAREA$52, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUIAREA$52);
                    }
                    target.set(cuiarea);
                }
            }
            
            /**
             * Gets the "CU_IPOSTREGN" element
             */
            public java.lang.String getCUIPOSTREGN()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUIPOSTREGN$54, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_IPOSTREGN" element
             */
            public org.apache.xmlbeans.XmlString xgetCUIPOSTREGN()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUIPOSTREGN$54, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_IPOSTREGN" element
             */
            public void setCUIPOSTREGN(java.lang.String cuipostregn)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUIPOSTREGN$54, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUIPOSTREGN$54);
                    }
                    target.setStringValue(cuipostregn);
                }
            }
            
            /**
             * Sets (as xml) the "CU_IPOSTREGN" element
             */
            public void xsetCUIPOSTREGN(org.apache.xmlbeans.XmlString cuipostregn)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUIPOSTREGN$54, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUIPOSTREGN$54);
                    }
                    target.set(cuipostregn);
                }
            }
            
            /**
             * Gets the "CU_IPOSTCD" element
             */
            public java.lang.String getCUIPOSTCD()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUIPOSTCD$56, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_IPOSTCD" element
             */
            public org.apache.xmlbeans.XmlString xgetCUIPOSTCD()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUIPOSTCD$56, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_IPOSTCD" element
             */
            public void setCUIPOSTCD(java.lang.String cuipostcd)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUIPOSTCD$56, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUIPOSTCD$56);
                    }
                    target.setStringValue(cuipostcd);
                }
            }
            
            /**
             * Sets (as xml) the "CU_IPOSTCD" element
             */
            public void xsetCUIPOSTCD(org.apache.xmlbeans.XmlString cuipostcd)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUIPOSTCD$56, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUIPOSTCD$56);
                    }
                    target.set(cuipostcd);
                }
            }
            
            /**
             * Gets the "CU_ICOUNTRY" element
             */
            public java.lang.String getCUICOUNTRY()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUICOUNTRY$58, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "CU_ICOUNTRY" element
             */
            public org.apache.xmlbeans.XmlString xgetCUICOUNTRY()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUICOUNTRY$58, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "CU_ICOUNTRY" element
             */
            public void setCUICOUNTRY(java.lang.String cuicountry)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUICOUNTRY$58, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUICOUNTRY$58);
                    }
                    target.setStringValue(cuicountry);
                }
            }
            
            /**
             * Sets (as xml) the "CU_ICOUNTRY" element
             */
            public void xsetCUICOUNTRY(org.apache.xmlbeans.XmlString cuicountry)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUICOUNTRY$58, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUICOUNTRY$58);
                    }
                    target.set(cuicountry);
                }
            }
        }
        /**
         * An XML orderRegDtls(@).
         *
         * This is a complex type.
         */
        public static class OrderRegDtlsImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls
        {
            private static final long serialVersionUID = 1L;
            
            public OrderRegDtlsImpl(org.apache.xmlbeans.SchemaType sType)
            {
                super(sType);
            }
            
            private static final javax.xml.namespace.QName ORDERREGINFODTL$0 = 
                new javax.xml.namespace.QName("", "OrderRegInfoDtl");
            
            
            /**
             * Gets array of all "OrderRegInfoDtl" elements
             */
            public noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls.OrderRegInfoDtl[] getOrderRegInfoDtlArray()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    java.util.List targetList = new java.util.ArrayList();
                    get_store().find_all_element_users(ORDERREGINFODTL$0, targetList);
                    noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls.OrderRegInfoDtl[] result = new noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls.OrderRegInfoDtl[targetList.size()];
                    targetList.toArray(result);
                    return result;
                }
            }
            
            /**
             * Gets ith "OrderRegInfoDtl" element
             */
            public noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls.OrderRegInfoDtl getOrderRegInfoDtlArray(int i)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls.OrderRegInfoDtl target = null;
                    target = (noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls.OrderRegInfoDtl)get_store().find_element_user(ORDERREGINFODTL$0, i);
                    if (target == null)
                    {
                      throw new IndexOutOfBoundsException();
                    }
                    return target;
                }
            }
            
            /**
             * Returns number of "OrderRegInfoDtl" element
             */
            public int sizeOfOrderRegInfoDtlArray()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(ORDERREGINFODTL$0);
                }
            }
            
            /**
             * Sets array of all "OrderRegInfoDtl" element  WARNING: This method is not atomicaly synchronized.
             */
            public void setOrderRegInfoDtlArray(noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls.OrderRegInfoDtl[] orderRegInfoDtlArray)
            {
                check_orphaned();
                arraySetterHelper(orderRegInfoDtlArray, ORDERREGINFODTL$0);
            }
            
            /**
             * Sets ith "OrderRegInfoDtl" element
             */
            public void setOrderRegInfoDtlArray(int i, noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls.OrderRegInfoDtl orderRegInfoDtl)
            {
                generatedSetterHelperImpl(orderRegInfoDtl, ORDERREGINFODTL$0, i, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_ARRAYITEM);
            }
            
            /**
             * Inserts and returns a new empty value (as xml) as the ith "OrderRegInfoDtl" element
             */
            public noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls.OrderRegInfoDtl insertNewOrderRegInfoDtl(int i)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls.OrderRegInfoDtl target = null;
                    target = (noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls.OrderRegInfoDtl)get_store().insert_element_user(ORDERREGINFODTL$0, i);
                    return target;
                }
            }
            
            /**
             * Appends and returns a new empty value (as xml) as the last "OrderRegInfoDtl" element
             */
            public noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls.OrderRegInfoDtl addNewOrderRegInfoDtl()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls.OrderRegInfoDtl target = null;
                    target = (noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls.OrderRegInfoDtl)get_store().add_element_user(ORDERREGINFODTL$0);
                    return target;
                }
            }
            
            /**
             * Removes the ith "OrderRegInfoDtl" element
             */
            public void removeOrderRegInfoDtl(int i)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(ORDERREGINFODTL$0, i);
                }
            }
            /**
             * An XML OrderRegInfoDtl(@).
             *
             * This is a complex type.
             */
            public static class OrderRegInfoDtlImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegDtls.OrderRegInfoDtl
            {
                private static final long serialVersionUID = 1L;
                
                public OrderRegInfoDtlImpl(org.apache.xmlbeans.SchemaType sType)
                {
                    super(sType);
                }
                
                private static final javax.xml.namespace.QName STORECODE$0 = 
                    new javax.xml.namespace.QName("", "Store_Code");
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
                private static final javax.xml.namespace.QName SEQNUM$12 = 
                    new javax.xml.namespace.QName("", "Seq_Num");
                private static final javax.xml.namespace.QName BARCODE$14 = 
                    new javax.xml.namespace.QName("", "Barcode");
                private static final javax.xml.namespace.QName SKU$16 = 
                    new javax.xml.namespace.QName("", "SKU");
                private static final javax.xml.namespace.QName PRODDESC$18 = 
                    new javax.xml.namespace.QName("", "ProdDesc");
                private static final javax.xml.namespace.QName QTY$20 = 
                    new javax.xml.namespace.QName("", "Qty");
                private static final javax.xml.namespace.QName ORGPRICE$22 = 
                    new javax.xml.namespace.QName("", "Org_Price");
                private static final javax.xml.namespace.QName UNITPRICE$24 = 
                    new javax.xml.namespace.QName("", "Unit_Price");
                private static final javax.xml.namespace.QName NETPRICE$26 = 
                    new javax.xml.namespace.QName("", "Net_Price");
                private static final javax.xml.namespace.QName ORGAMOUNT$28 = 
                    new javax.xml.namespace.QName("", "Org_Amount");
                private static final javax.xml.namespace.QName UNITAMOUNT$30 = 
                    new javax.xml.namespace.QName("", "Unit_Amount");
                private static final javax.xml.namespace.QName NETAMOUNT$32 = 
                    new javax.xml.namespace.QName("", "Net_Amount");
                private static final javax.xml.namespace.QName TOTALQTY$34 = 
                    new javax.xml.namespace.QName("", "Total_Qty");
                private static final javax.xml.namespace.QName DISCOUNTPRICE$36 = 
                    new javax.xml.namespace.QName("", "Discount_Price");
                private static final javax.xml.namespace.QName POVALUE$38 = 
                    new javax.xml.namespace.QName("", "PO_Value");
                private static final javax.xml.namespace.QName POREASONCODE$40 = 
                    new javax.xml.namespace.QName("", "PO_ReasonCode");
                private static final javax.xml.namespace.QName IDTYPE$42 = 
                    new javax.xml.namespace.QName("", "ID_Type");
                private static final javax.xml.namespace.QName IDREASONCODE$44 = 
                    new javax.xml.namespace.QName("", "ID_ReasonCode");
                private static final javax.xml.namespace.QName IDVALUE$46 = 
                    new javax.xml.namespace.QName("", "ID_Value");
                private static final javax.xml.namespace.QName MNMID$48 = 
                    new javax.xml.namespace.QName("", "MNM_ID");
                private static final javax.xml.namespace.QName ADDITIONAL1$50 = 
                    new javax.xml.namespace.QName("", "Additional1");
                private static final javax.xml.namespace.QName ADDITIONAL2$52 = 
                    new javax.xml.namespace.QName("", "Additional2");
                private static final javax.xml.namespace.QName ADDITIONAL3$54 = 
                    new javax.xml.namespace.QName("", "Additional3");
                private static final javax.xml.namespace.QName ADDTYPE$56 = 
                    new javax.xml.namespace.QName("", "AddType");
                private static final javax.xml.namespace.QName CHGCLASS$58 = 
                    new javax.xml.namespace.QName("", "ChgClass");
                private static final javax.xml.namespace.QName MODIFIER$60 = 
                    new javax.xml.namespace.QName("", "Modifier");
                private static final javax.xml.namespace.QName ISBOM$62 = 
                    new javax.xml.namespace.QName("", "IsBOM");
                private static final javax.xml.namespace.QName MNPNEED$64 = 
                    new javax.xml.namespace.QName("", "MNPNeed");
                private static final javax.xml.namespace.QName CNPNEED$66 = 
                    new javax.xml.namespace.QName("", "CNPNeed");
                private static final javax.xml.namespace.QName CHARGETYPE$68 = 
                    new javax.xml.namespace.QName("", "ChargeType");
                private static final javax.xml.namespace.QName SNNEED$70 = 
                    new javax.xml.namespace.QName("", "SN_Need");
                private static final javax.xml.namespace.QName SNNUM$72 = 
                    new javax.xml.namespace.QName("", "SN_Num");
                private static final javax.xml.namespace.QName STOCKTYPE$74 = 
                    new javax.xml.namespace.QName("", "StockType");
                private static final javax.xml.namespace.QName COLLECTED$76 = 
                    new javax.xml.namespace.QName("", "Collected");
                private static final javax.xml.namespace.QName PICKUPLOCATION$78 = 
                    new javax.xml.namespace.QName("", "Pickup_Location");
                private static final javax.xml.namespace.QName PICKUPSTAFF$80 = 
                    new javax.xml.namespace.QName("", "Pickup_Staff");
                private static final javax.xml.namespace.QName PICKUPDATE$82 = 
                    new javax.xml.namespace.QName("", "Pickup_Date");
                private static final javax.xml.namespace.QName DEPTCODE$84 = 
                    new javax.xml.namespace.QName("", "DeptCode");
                private static final javax.xml.namespace.QName UPLOADEDFIN$86 = 
                    new javax.xml.namespace.QName("", "Uploaded_FIN");
                private static final javax.xml.namespace.QName LISRESERVEID$88 = 
                    new javax.xml.namespace.QName("", "LIS_Reserve_ID");
                private static final javax.xml.namespace.QName DISCPRICE$90 = 
                    new javax.xml.namespace.QName("", "DISC_PRICE");
                private static final javax.xml.namespace.QName DISCAMOUNT$92 = 
                    new javax.xml.namespace.QName("", "DISC_AMOUNT");
                private static final javax.xml.namespace.QName IMEI$94 = 
                    new javax.xml.namespace.QName("", "IMEI");
                private static final javax.xml.namespace.QName ADDCHARGE$96 = 
                    new javax.xml.namespace.QName("", "Add_Charge");
                private static final javax.xml.namespace.QName DISCTYPE$98 = 
                    new javax.xml.namespace.QName("", "Disc_Type");
                private static final javax.xml.namespace.QName ISCOUPONSKU$100 = 
                    new javax.xml.namespace.QName("", "Is_coupon_SKU");
                private static final javax.xml.namespace.QName ISBUYBACKSKU$102 = 
                    new javax.xml.namespace.QName("", "Is_buyback_SKU");
                private static final javax.xml.namespace.QName LBLSNO$104 = 
                    new javax.xml.namespace.QName("", "Lbl_Sno");
                private static final javax.xml.namespace.QName WARRANTY$106 = 
                    new javax.xml.namespace.QName("", "Warranty");
                private static final javax.xml.namespace.QName PICKUPSTORETYPE$108 = 
                    new javax.xml.namespace.QName("", "Pickup_Store_Type");
                private static final javax.xml.namespace.QName PICKUPSTORENAME$110 = 
                    new javax.xml.namespace.QName("", "Pickup_Store_Name");
                private static final javax.xml.namespace.QName PICKUPSTORETEL$112 = 
                    new javax.xml.namespace.QName("", "Pickup_Store_Tel");
                private static final javax.xml.namespace.QName PICKUPSTOREBEGINHOURS$114 = 
                    new javax.xml.namespace.QName("", "Pickup_Store_begin_hours");
                private static final javax.xml.namespace.QName PICKUPSTOREENDHOURS$116 = 
                    new javax.xml.namespace.QName("", "Pickup_Store_End_Hours");
                private static final javax.xml.namespace.QName PICKUPSTOREADDR1$118 = 
                    new javax.xml.namespace.QName("", "Pickup_Store_Addr_1");
                private static final javax.xml.namespace.QName PICKUPSTOREADDR2$120 = 
                    new javax.xml.namespace.QName("", "Pickup_Store_Addr_2");
                private static final javax.xml.namespace.QName PICKUPSTOREADDR3$122 = 
                    new javax.xml.namespace.QName("", "Pickup_Store_Addr_3");
                private static final javax.xml.namespace.QName FAULTCODE$124 = 
                    new javax.xml.namespace.QName("", "Fault_Code");
                private static final javax.xml.namespace.QName FAULTDESC$126 = 
                    new javax.xml.namespace.QName("", "Fault_Desc");
                private static final javax.xml.namespace.QName RECORDTYPE$128 = 
                    new javax.xml.namespace.QName("", "Record_Type");
                private static final javax.xml.namespace.QName REFGUID$130 = 
                    new javax.xml.namespace.QName("", "Ref_GUID");
                private static final javax.xml.namespace.QName ACTAMOUNT$132 = 
                    new javax.xml.namespace.QName("", "ActAmount");
                private static final javax.xml.namespace.QName ACTVALUE$134 = 
                    new javax.xml.namespace.QName("", "ActValue");
                private static final javax.xml.namespace.QName TXNTIMEPOSBOM$136 = 
                    new javax.xml.namespace.QName("", "Txn_Time_POS_BOM");
                private static final javax.xml.namespace.QName TXNDATEPOSBOM$138 = 
                    new javax.xml.namespace.QName("", "Txn_Date_POS_BOM");
                private static final javax.xml.namespace.QName REQNO$140 = 
                    new javax.xml.namespace.QName("", "Req_No");
                private static final javax.xml.namespace.QName REFNO$142 = 
                    new javax.xml.namespace.QName("", "Ref_No");
                private static final javax.xml.namespace.QName MSISDN$144 = 
                    new javax.xml.namespace.QName("", "MSISDN");
                private static final javax.xml.namespace.QName SIMNO$146 = 
                    new javax.xml.namespace.QName("", "SIM_No");
                
                
                /**
                 * Gets the "Store_Code" element
                 */
                public java.lang.String getStoreCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STORECODE$0, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Store_Code" element
                 */
                public org.apache.xmlbeans.XmlString xgetStoreCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STORECODE$0, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Store_Code" element
                 */
                public void setStoreCode(java.lang.String storeCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STORECODE$0, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STORECODE$0);
                      }
                      target.setStringValue(storeCode);
                    }
                }
                
                /**
                 * Sets (as xml) the "Store_Code" element
                 */
                public void xsetStoreCode(org.apache.xmlbeans.XmlString storeCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STORECODE$0, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STORECODE$0);
                      }
                      target.set(storeCode);
                    }
                }
                
                /**
                 * Gets the "Register_ID" element
                 */
                public java.lang.String getRegisterID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REGISTERID$2, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Register_ID" element
                 */
                public org.apache.xmlbeans.XmlString xgetRegisterID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REGISTERID$2, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Register_ID" element
                 */
                public void setRegisterID(java.lang.String registerID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REGISTERID$2, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(REGISTERID$2);
                      }
                      target.setStringValue(registerID);
                    }
                }
                
                /**
                 * Sets (as xml) the "Register_ID" element
                 */
                public void xsetRegisterID(org.apache.xmlbeans.XmlString registerID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REGISTERID$2, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REGISTERID$2);
                      }
                      target.set(registerID);
                    }
                }
                
                /**
                 * Gets the "Trans_Num" element
                 */
                public java.lang.String getTransNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRANSNUM$4, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Trans_Num" element
                 */
                public org.apache.xmlbeans.XmlString xgetTransNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRANSNUM$4, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Trans_Num" element
                 */
                public void setTransNum(java.lang.String transNum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRANSNUM$4, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TRANSNUM$4);
                      }
                      target.setStringValue(transNum);
                    }
                }
                
                /**
                 * Sets (as xml) the "Trans_Num" element
                 */
                public void xsetTransNum(org.apache.xmlbeans.XmlString transNum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRANSNUM$4, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TRANSNUM$4);
                      }
                      target.set(transNum);
                    }
                }
                
                /**
                 * Gets the "Bus_Date" element
                 */
                public java.lang.String getBusDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BUSDATE$6, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Bus_Date" element
                 */
                public org.apache.xmlbeans.XmlString xgetBusDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BUSDATE$6, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Bus_Date" element
                 */
                public void setBusDate(java.lang.String busDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BUSDATE$6, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BUSDATE$6);
                      }
                      target.setStringValue(busDate);
                    }
                }
                
                /**
                 * Sets (as xml) the "Bus_Date" element
                 */
                public void xsetBusDate(org.apache.xmlbeans.XmlString busDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BUSDATE$6, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BUSDATE$6);
                      }
                      target.set(busDate);
                    }
                }
                
                /**
                 * Gets the "Txn_Date" element
                 */
                public java.lang.String getTxnDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TXNDATE$8, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Txn_Date" element
                 */
                public org.apache.xmlbeans.XmlString xgetTxnDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TXNDATE$8, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Txn_Date" element
                 */
                public void setTxnDate(java.lang.String txnDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TXNDATE$8, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TXNDATE$8);
                      }
                      target.setStringValue(txnDate);
                    }
                }
                
                /**
                 * Sets (as xml) the "Txn_Date" element
                 */
                public void xsetTxnDate(org.apache.xmlbeans.XmlString txnDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TXNDATE$8, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TXNDATE$8);
                      }
                      target.set(txnDate);
                    }
                }
                
                /**
                 * Gets the "SM_Type" element
                 */
                public int getSMType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SMTYPE$10, 0);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getIntValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "SM_Type" element
                 */
                public org.apache.xmlbeans.XmlInt xgetSMType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SMTYPE$10, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "SM_Type" element
                 */
                public void setSMType(int smType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SMTYPE$10, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SMTYPE$10);
                      }
                      target.setIntValue(smType);
                    }
                }
                
                /**
                 * Sets (as xml) the "SM_Type" element
                 */
                public void xsetSMType(org.apache.xmlbeans.XmlInt smType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SMTYPE$10, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(SMTYPE$10);
                      }
                      target.set(smType);
                    }
                }
                
                /**
                 * Gets the "Seq_Num" element
                 */
                public java.lang.String getSeqNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SEQNUM$12, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Seq_Num" element
                 */
                public org.apache.xmlbeans.XmlString xgetSeqNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SEQNUM$12, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Seq_Num" element
                 */
                public void setSeqNum(java.lang.String seqNum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SEQNUM$12, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SEQNUM$12);
                      }
                      target.setStringValue(seqNum);
                    }
                }
                
                /**
                 * Sets (as xml) the "Seq_Num" element
                 */
                public void xsetSeqNum(org.apache.xmlbeans.XmlString seqNum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SEQNUM$12, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SEQNUM$12);
                      }
                      target.set(seqNum);
                    }
                }
                
                /**
                 * Gets the "Barcode" element
                 */
                public java.lang.String getBarcode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BARCODE$14, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Barcode" element
                 */
                public org.apache.xmlbeans.XmlString xgetBarcode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BARCODE$14, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Barcode" element
                 */
                public void setBarcode(java.lang.String barcode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BARCODE$14, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BARCODE$14);
                      }
                      target.setStringValue(barcode);
                    }
                }
                
                /**
                 * Sets (as xml) the "Barcode" element
                 */
                public void xsetBarcode(org.apache.xmlbeans.XmlString barcode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BARCODE$14, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BARCODE$14);
                      }
                      target.set(barcode);
                    }
                }
                
                /**
                 * Gets the "SKU" element
                 */
                public java.lang.String getSKU()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SKU$16, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "SKU" element
                 */
                public org.apache.xmlbeans.XmlString xgetSKU()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SKU$16, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "SKU" element
                 */
                public void setSKU(java.lang.String sku)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SKU$16, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SKU$16);
                      }
                      target.setStringValue(sku);
                    }
                }
                
                /**
                 * Sets (as xml) the "SKU" element
                 */
                public void xsetSKU(org.apache.xmlbeans.XmlString sku)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SKU$16, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SKU$16);
                      }
                      target.set(sku);
                    }
                }
                
                /**
                 * Gets the "ProdDesc" element
                 */
                public java.lang.String getProdDesc()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PRODDESC$18, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "ProdDesc" element
                 */
                public org.apache.xmlbeans.XmlString xgetProdDesc()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PRODDESC$18, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "ProdDesc" element
                 */
                public void setProdDesc(java.lang.String prodDesc)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PRODDESC$18, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PRODDESC$18);
                      }
                      target.setStringValue(prodDesc);
                    }
                }
                
                /**
                 * Sets (as xml) the "ProdDesc" element
                 */
                public void xsetProdDesc(org.apache.xmlbeans.XmlString prodDesc)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PRODDESC$18, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PRODDESC$18);
                      }
                      target.set(prodDesc);
                    }
                }
                
                /**
                 * Gets the "Qty" element
                 */
                public int getQty()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(QTY$20, 0);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getIntValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Qty" element
                 */
                public org.apache.xmlbeans.XmlInt xgetQty()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(QTY$20, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Qty" element
                 */
                public void setQty(int qty)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(QTY$20, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(QTY$20);
                      }
                      target.setIntValue(qty);
                    }
                }
                
                /**
                 * Sets (as xml) the "Qty" element
                 */
                public void xsetQty(org.apache.xmlbeans.XmlInt qty)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(QTY$20, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(QTY$20);
                      }
                      target.set(qty);
                    }
                }
                
                /**
                 * Gets the "Org_Price" element
                 */
                public double getOrgPrice()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ORGPRICE$22, 0);
                      if (target == null)
                      {
                        return 0.0;
                      }
                      return target.getDoubleValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Org_Price" element
                 */
                public org.apache.xmlbeans.XmlDouble xgetOrgPrice()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ORGPRICE$22, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Org_Price" element
                 */
                public void setOrgPrice(double orgPrice)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ORGPRICE$22, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ORGPRICE$22);
                      }
                      target.setDoubleValue(orgPrice);
                    }
                }
                
                /**
                 * Sets (as xml) the "Org_Price" element
                 */
                public void xsetOrgPrice(org.apache.xmlbeans.XmlDouble orgPrice)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ORGPRICE$22, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(ORGPRICE$22);
                      }
                      target.set(orgPrice);
                    }
                }
                
                /**
                 * Gets the "Unit_Price" element
                 */
                public double getUnitPrice()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(UNITPRICE$24, 0);
                      if (target == null)
                      {
                        return 0.0;
                      }
                      return target.getDoubleValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Unit_Price" element
                 */
                public org.apache.xmlbeans.XmlDouble xgetUnitPrice()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(UNITPRICE$24, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Unit_Price" element
                 */
                public void setUnitPrice(double unitPrice)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(UNITPRICE$24, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(UNITPRICE$24);
                      }
                      target.setDoubleValue(unitPrice);
                    }
                }
                
                /**
                 * Sets (as xml) the "Unit_Price" element
                 */
                public void xsetUnitPrice(org.apache.xmlbeans.XmlDouble unitPrice)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(UNITPRICE$24, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(UNITPRICE$24);
                      }
                      target.set(unitPrice);
                    }
                }
                
                /**
                 * Gets the "Net_Price" element
                 */
                public double getNetPrice()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NETPRICE$26, 0);
                      if (target == null)
                      {
                        return 0.0;
                      }
                      return target.getDoubleValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Net_Price" element
                 */
                public org.apache.xmlbeans.XmlDouble xgetNetPrice()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(NETPRICE$26, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Net_Price" element
                 */
                public void setNetPrice(double netPrice)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NETPRICE$26, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NETPRICE$26);
                      }
                      target.setDoubleValue(netPrice);
                    }
                }
                
                /**
                 * Sets (as xml) the "Net_Price" element
                 */
                public void xsetNetPrice(org.apache.xmlbeans.XmlDouble netPrice)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(NETPRICE$26, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(NETPRICE$26);
                      }
                      target.set(netPrice);
                    }
                }
                
                /**
                 * Gets the "Org_Amount" element
                 */
                public double getOrgAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ORGAMOUNT$28, 0);
                      if (target == null)
                      {
                        return 0.0;
                      }
                      return target.getDoubleValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Org_Amount" element
                 */
                public org.apache.xmlbeans.XmlDouble xgetOrgAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ORGAMOUNT$28, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Org_Amount" element
                 */
                public void setOrgAmount(double orgAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ORGAMOUNT$28, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ORGAMOUNT$28);
                      }
                      target.setDoubleValue(orgAmount);
                    }
                }
                
                /**
                 * Sets (as xml) the "Org_Amount" element
                 */
                public void xsetOrgAmount(org.apache.xmlbeans.XmlDouble orgAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ORGAMOUNT$28, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(ORGAMOUNT$28);
                      }
                      target.set(orgAmount);
                    }
                }
                
                /**
                 * Gets the "Unit_Amount" element
                 */
                public double getUnitAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(UNITAMOUNT$30, 0);
                      if (target == null)
                      {
                        return 0.0;
                      }
                      return target.getDoubleValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Unit_Amount" element
                 */
                public org.apache.xmlbeans.XmlDouble xgetUnitAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(UNITAMOUNT$30, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Unit_Amount" element
                 */
                public void setUnitAmount(double unitAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(UNITAMOUNT$30, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(UNITAMOUNT$30);
                      }
                      target.setDoubleValue(unitAmount);
                    }
                }
                
                /**
                 * Sets (as xml) the "Unit_Amount" element
                 */
                public void xsetUnitAmount(org.apache.xmlbeans.XmlDouble unitAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(UNITAMOUNT$30, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(UNITAMOUNT$30);
                      }
                      target.set(unitAmount);
                    }
                }
                
                /**
                 * Gets the "Net_Amount" element
                 */
                public double getNetAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NETAMOUNT$32, 0);
                      if (target == null)
                      {
                        return 0.0;
                      }
                      return target.getDoubleValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Net_Amount" element
                 */
                public org.apache.xmlbeans.XmlDouble xgetNetAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(NETAMOUNT$32, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Net_Amount" element
                 */
                public void setNetAmount(double netAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NETAMOUNT$32, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NETAMOUNT$32);
                      }
                      target.setDoubleValue(netAmount);
                    }
                }
                
                /**
                 * Sets (as xml) the "Net_Amount" element
                 */
                public void xsetNetAmount(org.apache.xmlbeans.XmlDouble netAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(NETAMOUNT$32, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(NETAMOUNT$32);
                      }
                      target.set(netAmount);
                    }
                }
                
                /**
                 * Gets the "Total_Qty" element
                 */
                public int getTotalQty()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TOTALQTY$34, 0);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getIntValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Total_Qty" element
                 */
                public org.apache.xmlbeans.XmlInt xgetTotalQty()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(TOTALQTY$34, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Total_Qty" element
                 */
                public void setTotalQty(int totalQty)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TOTALQTY$34, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TOTALQTY$34);
                      }
                      target.setIntValue(totalQty);
                    }
                }
                
                /**
                 * Sets (as xml) the "Total_Qty" element
                 */
                public void xsetTotalQty(org.apache.xmlbeans.XmlInt totalQty)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(TOTALQTY$34, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(TOTALQTY$34);
                      }
                      target.set(totalQty);
                    }
                }
                
                /**
                 * Gets the "Discount_Price" element
                 */
                public boolean getDiscountPrice()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DISCOUNTPRICE$36, 0);
                      if (target == null)
                      {
                        return false;
                      }
                      return target.getBooleanValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Discount_Price" element
                 */
                public org.apache.xmlbeans.XmlBoolean xgetDiscountPrice()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlBoolean target = null;
                      target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(DISCOUNTPRICE$36, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Discount_Price" element
                 */
                public void setDiscountPrice(boolean discountPrice)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DISCOUNTPRICE$36, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DISCOUNTPRICE$36);
                      }
                      target.setBooleanValue(discountPrice);
                    }
                }
                
                /**
                 * Sets (as xml) the "Discount_Price" element
                 */
                public void xsetDiscountPrice(org.apache.xmlbeans.XmlBoolean discountPrice)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlBoolean target = null;
                      target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(DISCOUNTPRICE$36, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(DISCOUNTPRICE$36);
                      }
                      target.set(discountPrice);
                    }
                }
                
                /**
                 * Gets the "PO_Value" element
                 */
                public double getPOValue()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(POVALUE$38, 0);
                      if (target == null)
                      {
                        return 0.0;
                      }
                      return target.getDoubleValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "PO_Value" element
                 */
                public org.apache.xmlbeans.XmlDouble xgetPOValue()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(POVALUE$38, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "PO_Value" element
                 */
                public void setPOValue(double poValue)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(POVALUE$38, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(POVALUE$38);
                      }
                      target.setDoubleValue(poValue);
                    }
                }
                
                /**
                 * Sets (as xml) the "PO_Value" element
                 */
                public void xsetPOValue(org.apache.xmlbeans.XmlDouble poValue)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(POVALUE$38, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(POVALUE$38);
                      }
                      target.set(poValue);
                    }
                }
                
                /**
                 * Gets the "PO_ReasonCode" element
                 */
                public java.lang.String getPOReasonCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(POREASONCODE$40, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "PO_ReasonCode" element
                 */
                public org.apache.xmlbeans.XmlString xgetPOReasonCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(POREASONCODE$40, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "PO_ReasonCode" element
                 */
                public void setPOReasonCode(java.lang.String poReasonCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(POREASONCODE$40, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(POREASONCODE$40);
                      }
                      target.setStringValue(poReasonCode);
                    }
                }
                
                /**
                 * Sets (as xml) the "PO_ReasonCode" element
                 */
                public void xsetPOReasonCode(org.apache.xmlbeans.XmlString poReasonCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(POREASONCODE$40, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(POREASONCODE$40);
                      }
                      target.set(poReasonCode);
                    }
                }
                
                /**
                 * Gets the "ID_Type" element
                 */
                public int getIDType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDTYPE$42, 0);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getIntValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "ID_Type" element
                 */
                public org.apache.xmlbeans.XmlInt xgetIDType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(IDTYPE$42, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "ID_Type" element
                 */
                public void setIDType(int idType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDTYPE$42, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDTYPE$42);
                      }
                      target.setIntValue(idType);
                    }
                }
                
                /**
                 * Sets (as xml) the "ID_Type" element
                 */
                public void xsetIDType(org.apache.xmlbeans.XmlInt idType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(IDTYPE$42, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(IDTYPE$42);
                      }
                      target.set(idType);
                    }
                }
                
                /**
                 * Gets the "ID_ReasonCode" element
                 */
                public java.lang.String getIDReasonCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDREASONCODE$44, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "ID_ReasonCode" element
                 */
                public org.apache.xmlbeans.XmlString xgetIDReasonCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDREASONCODE$44, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "ID_ReasonCode" element
                 */
                public void setIDReasonCode(java.lang.String idReasonCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDREASONCODE$44, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDREASONCODE$44);
                      }
                      target.setStringValue(idReasonCode);
                    }
                }
                
                /**
                 * Sets (as xml) the "ID_ReasonCode" element
                 */
                public void xsetIDReasonCode(org.apache.xmlbeans.XmlString idReasonCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDREASONCODE$44, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(IDREASONCODE$44);
                      }
                      target.set(idReasonCode);
                    }
                }
                
                /**
                 * Gets the "ID_Value" element
                 */
                public double getIDValue()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDVALUE$46, 0);
                      if (target == null)
                      {
                        return 0.0;
                      }
                      return target.getDoubleValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "ID_Value" element
                 */
                public org.apache.xmlbeans.XmlDouble xgetIDValue()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IDVALUE$46, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "ID_Value" element
                 */
                public void setIDValue(double idValue)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDVALUE$46, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDVALUE$46);
                      }
                      target.setDoubleValue(idValue);
                    }
                }
                
                /**
                 * Sets (as xml) the "ID_Value" element
                 */
                public void xsetIDValue(org.apache.xmlbeans.XmlDouble idValue)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IDVALUE$46, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IDVALUE$46);
                      }
                      target.set(idValue);
                    }
                }
                
                /**
                 * Gets the "MNM_ID" element
                 */
                public java.lang.String getMNMID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MNMID$48, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "MNM_ID" element
                 */
                public org.apache.xmlbeans.XmlString xgetMNMID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MNMID$48, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "MNM_ID" element
                 */
                public void setMNMID(java.lang.String mnmid)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MNMID$48, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MNMID$48);
                      }
                      target.setStringValue(mnmid);
                    }
                }
                
                /**
                 * Sets (as xml) the "MNM_ID" element
                 */
                public void xsetMNMID(org.apache.xmlbeans.XmlString mnmid)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MNMID$48, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MNMID$48);
                      }
                      target.set(mnmid);
                    }
                }
                
                /**
                 * Gets the "Additional1" element
                 */
                public java.lang.String getAdditional1()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADDITIONAL1$50, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Additional1" element
                 */
                public org.apache.xmlbeans.XmlString xgetAdditional1()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADDITIONAL1$50, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Additional1" element
                 */
                public void setAdditional1(java.lang.String additional1)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADDITIONAL1$50, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ADDITIONAL1$50);
                      }
                      target.setStringValue(additional1);
                    }
                }
                
                /**
                 * Sets (as xml) the "Additional1" element
                 */
                public void xsetAdditional1(org.apache.xmlbeans.XmlString additional1)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADDITIONAL1$50, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ADDITIONAL1$50);
                      }
                      target.set(additional1);
                    }
                }
                
                /**
                 * Gets the "Additional2" element
                 */
                public java.lang.String getAdditional2()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADDITIONAL2$52, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Additional2" element
                 */
                public org.apache.xmlbeans.XmlString xgetAdditional2()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADDITIONAL2$52, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Additional2" element
                 */
                public void setAdditional2(java.lang.String additional2)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADDITIONAL2$52, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ADDITIONAL2$52);
                      }
                      target.setStringValue(additional2);
                    }
                }
                
                /**
                 * Sets (as xml) the "Additional2" element
                 */
                public void xsetAdditional2(org.apache.xmlbeans.XmlString additional2)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADDITIONAL2$52, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ADDITIONAL2$52);
                      }
                      target.set(additional2);
                    }
                }
                
                /**
                 * Gets the "Additional3" element
                 */
                public java.lang.String getAdditional3()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADDITIONAL3$54, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Additional3" element
                 */
                public org.apache.xmlbeans.XmlString xgetAdditional3()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADDITIONAL3$54, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Additional3" element
                 */
                public void setAdditional3(java.lang.String additional3)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADDITIONAL3$54, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ADDITIONAL3$54);
                      }
                      target.setStringValue(additional3);
                    }
                }
                
                /**
                 * Sets (as xml) the "Additional3" element
                 */
                public void xsetAdditional3(org.apache.xmlbeans.XmlString additional3)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADDITIONAL3$54, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ADDITIONAL3$54);
                      }
                      target.set(additional3);
                    }
                }
                
                /**
                 * Gets the "AddType" element
                 */
                public int getAddType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADDTYPE$56, 0);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getIntValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "AddType" element
                 */
                public org.apache.xmlbeans.XmlInt xgetAddType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(ADDTYPE$56, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "AddType" element
                 */
                public void setAddType(int addType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADDTYPE$56, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ADDTYPE$56);
                      }
                      target.setIntValue(addType);
                    }
                }
                
                /**
                 * Sets (as xml) the "AddType" element
                 */
                public void xsetAddType(org.apache.xmlbeans.XmlInt addType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(ADDTYPE$56, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(ADDTYPE$56);
                      }
                      target.set(addType);
                    }
                }
                
                /**
                 * Gets the "ChgClass" element
                 */
                public java.lang.String getChgClass()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CHGCLASS$58, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "ChgClass" element
                 */
                public org.apache.xmlbeans.XmlString xgetChgClass()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CHGCLASS$58, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "ChgClass" element
                 */
                public void setChgClass(java.lang.String chgClass)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CHGCLASS$58, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CHGCLASS$58);
                      }
                      target.setStringValue(chgClass);
                    }
                }
                
                /**
                 * Sets (as xml) the "ChgClass" element
                 */
                public void xsetChgClass(org.apache.xmlbeans.XmlString chgClass)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CHGCLASS$58, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CHGCLASS$58);
                      }
                      target.set(chgClass);
                    }
                }
                
                /**
                 * Gets the "Modifier" element
                 */
                public java.lang.String getModifier()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MODIFIER$60, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Modifier" element
                 */
                public org.apache.xmlbeans.XmlString xgetModifier()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MODIFIER$60, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Modifier" element
                 */
                public void setModifier(java.lang.String modifier)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MODIFIER$60, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MODIFIER$60);
                      }
                      target.setStringValue(modifier);
                    }
                }
                
                /**
                 * Sets (as xml) the "Modifier" element
                 */
                public void xsetModifier(org.apache.xmlbeans.XmlString modifier)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MODIFIER$60, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MODIFIER$60);
                      }
                      target.set(modifier);
                    }
                }
                
                /**
                 * Gets the "IsBOM" element
                 */
                public boolean getIsBOM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISBOM$62, 0);
                      if (target == null)
                      {
                        return false;
                      }
                      return target.getBooleanValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "IsBOM" element
                 */
                public org.apache.xmlbeans.XmlBoolean xgetIsBOM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlBoolean target = null;
                      target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ISBOM$62, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "IsBOM" element
                 */
                public void setIsBOM(boolean isBOM)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISBOM$62, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ISBOM$62);
                      }
                      target.setBooleanValue(isBOM);
                    }
                }
                
                /**
                 * Sets (as xml) the "IsBOM" element
                 */
                public void xsetIsBOM(org.apache.xmlbeans.XmlBoolean isBOM)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlBoolean target = null;
                      target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ISBOM$62, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(ISBOM$62);
                      }
                      target.set(isBOM);
                    }
                }
                
                /**
                 * Gets the "MNPNeed" element
                 */
                public boolean getMNPNeed()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MNPNEED$64, 0);
                      if (target == null)
                      {
                        return false;
                      }
                      return target.getBooleanValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "MNPNeed" element
                 */
                public org.apache.xmlbeans.XmlBoolean xgetMNPNeed()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlBoolean target = null;
                      target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(MNPNEED$64, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "MNPNeed" element
                 */
                public void setMNPNeed(boolean mnpNeed)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MNPNEED$64, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MNPNEED$64);
                      }
                      target.setBooleanValue(mnpNeed);
                    }
                }
                
                /**
                 * Sets (as xml) the "MNPNeed" element
                 */
                public void xsetMNPNeed(org.apache.xmlbeans.XmlBoolean mnpNeed)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlBoolean target = null;
                      target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(MNPNEED$64, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(MNPNEED$64);
                      }
                      target.set(mnpNeed);
                    }
                }
                
                /**
                 * Gets the "CNPNeed" element
                 */
                public boolean getCNPNeed()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CNPNEED$66, 0);
                      if (target == null)
                      {
                        return false;
                      }
                      return target.getBooleanValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CNPNeed" element
                 */
                public org.apache.xmlbeans.XmlBoolean xgetCNPNeed()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlBoolean target = null;
                      target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(CNPNEED$66, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "CNPNeed" element
                 */
                public void setCNPNeed(boolean cnpNeed)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CNPNEED$66, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CNPNEED$66);
                      }
                      target.setBooleanValue(cnpNeed);
                    }
                }
                
                /**
                 * Sets (as xml) the "CNPNeed" element
                 */
                public void xsetCNPNeed(org.apache.xmlbeans.XmlBoolean cnpNeed)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlBoolean target = null;
                      target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(CNPNEED$66, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(CNPNEED$66);
                      }
                      target.set(cnpNeed);
                    }
                }
                
                /**
                 * Gets the "ChargeType" element
                 */
                public int getChargeType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CHARGETYPE$68, 0);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getIntValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "ChargeType" element
                 */
                public org.apache.xmlbeans.XmlInt xgetChargeType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(CHARGETYPE$68, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "ChargeType" element
                 */
                public void setChargeType(int chargeType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CHARGETYPE$68, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CHARGETYPE$68);
                      }
                      target.setIntValue(chargeType);
                    }
                }
                
                /**
                 * Sets (as xml) the "ChargeType" element
                 */
                public void xsetChargeType(org.apache.xmlbeans.XmlInt chargeType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(CHARGETYPE$68, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(CHARGETYPE$68);
                      }
                      target.set(chargeType);
                    }
                }
                
                /**
                 * Gets the "SN_Need" element
                 */
                public int getSNNeed()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SNNEED$70, 0);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getIntValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "SN_Need" element
                 */
                public org.apache.xmlbeans.XmlInt xgetSNNeed()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SNNEED$70, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "SN_Need" element
                 */
                public void setSNNeed(int snNeed)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SNNEED$70, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SNNEED$70);
                      }
                      target.setIntValue(snNeed);
                    }
                }
                
                /**
                 * Sets (as xml) the "SN_Need" element
                 */
                public void xsetSNNeed(org.apache.xmlbeans.XmlInt snNeed)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SNNEED$70, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(SNNEED$70);
                      }
                      target.set(snNeed);
                    }
                }
                
                /**
                 * Gets the "SN_Num" element
                 */
                public java.lang.String getSNNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SNNUM$72, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "SN_Num" element
                 */
                public org.apache.xmlbeans.XmlString xgetSNNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SNNUM$72, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "SN_Num" element
                 */
                public void setSNNum(java.lang.String snNum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SNNUM$72, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SNNUM$72);
                      }
                      target.setStringValue(snNum);
                    }
                }
                
                /**
                 * Sets (as xml) the "SN_Num" element
                 */
                public void xsetSNNum(org.apache.xmlbeans.XmlString snNum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SNNUM$72, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SNNUM$72);
                      }
                      target.set(snNum);
                    }
                }
                
                /**
                 * Gets the "StockType" element
                 */
                public java.lang.String getStockType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STOCKTYPE$74, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "StockType" element
                 */
                public org.apache.xmlbeans.XmlString xgetStockType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STOCKTYPE$74, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "StockType" element
                 */
                public void setStockType(java.lang.String stockType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STOCKTYPE$74, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STOCKTYPE$74);
                      }
                      target.setStringValue(stockType);
                    }
                }
                
                /**
                 * Sets (as xml) the "StockType" element
                 */
                public void xsetStockType(org.apache.xmlbeans.XmlString stockType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STOCKTYPE$74, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STOCKTYPE$74);
                      }
                      target.set(stockType);
                    }
                }
                
                /**
                 * Gets the "Collected" element
                 */
                public int getCollected()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COLLECTED$76, 0);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getIntValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Collected" element
                 */
                public org.apache.xmlbeans.XmlInt xgetCollected()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(COLLECTED$76, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Collected" element
                 */
                public void setCollected(int collected)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COLLECTED$76, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COLLECTED$76);
                      }
                      target.setIntValue(collected);
                    }
                }
                
                /**
                 * Sets (as xml) the "Collected" element
                 */
                public void xsetCollected(org.apache.xmlbeans.XmlInt collected)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(COLLECTED$76, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(COLLECTED$76);
                      }
                      target.set(collected);
                    }
                }
                
                /**
                 * Gets the "Pickup_Location" element
                 */
                public java.lang.String getPickupLocation()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PICKUPLOCATION$78, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pickup_Location" element
                 */
                public org.apache.xmlbeans.XmlString xgetPickupLocation()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PICKUPLOCATION$78, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Pickup_Location" element
                 */
                public void setPickupLocation(java.lang.String pickupLocation)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PICKUPLOCATION$78, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PICKUPLOCATION$78);
                      }
                      target.setStringValue(pickupLocation);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pickup_Location" element
                 */
                public void xsetPickupLocation(org.apache.xmlbeans.XmlString pickupLocation)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PICKUPLOCATION$78, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PICKUPLOCATION$78);
                      }
                      target.set(pickupLocation);
                    }
                }
                
                /**
                 * Gets the "Pickup_Staff" element
                 */
                public java.lang.String getPickupStaff()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PICKUPSTAFF$80, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pickup_Staff" element
                 */
                public org.apache.xmlbeans.XmlString xgetPickupStaff()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PICKUPSTAFF$80, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Pickup_Staff" element
                 */
                public void setPickupStaff(java.lang.String pickupStaff)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PICKUPSTAFF$80, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PICKUPSTAFF$80);
                      }
                      target.setStringValue(pickupStaff);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pickup_Staff" element
                 */
                public void xsetPickupStaff(org.apache.xmlbeans.XmlString pickupStaff)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PICKUPSTAFF$80, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PICKUPSTAFF$80);
                      }
                      target.set(pickupStaff);
                    }
                }
                
                /**
                 * Gets the "Pickup_Date" element
                 */
                public java.lang.String getPickupDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PICKUPDATE$82, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pickup_Date" element
                 */
                public org.apache.xmlbeans.XmlString xgetPickupDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PICKUPDATE$82, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Pickup_Date" element
                 */
                public void setPickupDate(java.lang.String pickupDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PICKUPDATE$82, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PICKUPDATE$82);
                      }
                      target.setStringValue(pickupDate);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pickup_Date" element
                 */
                public void xsetPickupDate(org.apache.xmlbeans.XmlString pickupDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PICKUPDATE$82, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PICKUPDATE$82);
                      }
                      target.set(pickupDate);
                    }
                }
                
                /**
                 * Gets the "DeptCode" element
                 */
                public java.lang.String getDeptCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEPTCODE$84, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "DeptCode" element
                 */
                public org.apache.xmlbeans.XmlString xgetDeptCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEPTCODE$84, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "DeptCode" element
                 */
                public void setDeptCode(java.lang.String deptCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEPTCODE$84, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEPTCODE$84);
                      }
                      target.setStringValue(deptCode);
                    }
                }
                
                /**
                 * Sets (as xml) the "DeptCode" element
                 */
                public void xsetDeptCode(org.apache.xmlbeans.XmlString deptCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEPTCODE$84, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEPTCODE$84);
                      }
                      target.set(deptCode);
                    }
                }
                
                /**
                 * Gets the "Uploaded_FIN" element
                 */
                public boolean getUploadedFIN()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(UPLOADEDFIN$86, 0);
                      if (target == null)
                      {
                        return false;
                      }
                      return target.getBooleanValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Uploaded_FIN" element
                 */
                public org.apache.xmlbeans.XmlBoolean xgetUploadedFIN()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlBoolean target = null;
                      target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(UPLOADEDFIN$86, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Uploaded_FIN" element
                 */
                public void setUploadedFIN(boolean uploadedFIN)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(UPLOADEDFIN$86, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(UPLOADEDFIN$86);
                      }
                      target.setBooleanValue(uploadedFIN);
                    }
                }
                
                /**
                 * Sets (as xml) the "Uploaded_FIN" element
                 */
                public void xsetUploadedFIN(org.apache.xmlbeans.XmlBoolean uploadedFIN)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlBoolean target = null;
                      target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(UPLOADEDFIN$86, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(UPLOADEDFIN$86);
                      }
                      target.set(uploadedFIN);
                    }
                }
                
                /**
                 * Gets the "LIS_Reserve_ID" element
                 */
                public java.lang.String getLISReserveID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LISRESERVEID$88, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "LIS_Reserve_ID" element
                 */
                public org.apache.xmlbeans.XmlString xgetLISReserveID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LISRESERVEID$88, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "LIS_Reserve_ID" element
                 */
                public void setLISReserveID(java.lang.String lisReserveID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LISRESERVEID$88, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LISRESERVEID$88);
                      }
                      target.setStringValue(lisReserveID);
                    }
                }
                
                /**
                 * Sets (as xml) the "LIS_Reserve_ID" element
                 */
                public void xsetLISReserveID(org.apache.xmlbeans.XmlString lisReserveID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LISRESERVEID$88, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LISRESERVEID$88);
                      }
                      target.set(lisReserveID);
                    }
                }
                
                /**
                 * Gets the "DISC_PRICE" element
                 */
                public double getDISCPRICE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DISCPRICE$90, 0);
                      if (target == null)
                      {
                        return 0.0;
                      }
                      return target.getDoubleValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "DISC_PRICE" element
                 */
                public org.apache.xmlbeans.XmlDouble xgetDISCPRICE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(DISCPRICE$90, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "DISC_PRICE" element
                 */
                public void setDISCPRICE(double discprice)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DISCPRICE$90, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DISCPRICE$90);
                      }
                      target.setDoubleValue(discprice);
                    }
                }
                
                /**
                 * Sets (as xml) the "DISC_PRICE" element
                 */
                public void xsetDISCPRICE(org.apache.xmlbeans.XmlDouble discprice)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(DISCPRICE$90, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(DISCPRICE$90);
                      }
                      target.set(discprice);
                    }
                }
                
                /**
                 * Gets the "DISC_AMOUNT" element
                 */
                public double getDISCAMOUNT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DISCAMOUNT$92, 0);
                      if (target == null)
                      {
                        return 0.0;
                      }
                      return target.getDoubleValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "DISC_AMOUNT" element
                 */
                public org.apache.xmlbeans.XmlDouble xgetDISCAMOUNT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(DISCAMOUNT$92, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "DISC_AMOUNT" element
                 */
                public void setDISCAMOUNT(double discamount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DISCAMOUNT$92, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DISCAMOUNT$92);
                      }
                      target.setDoubleValue(discamount);
                    }
                }
                
                /**
                 * Sets (as xml) the "DISC_AMOUNT" element
                 */
                public void xsetDISCAMOUNT(org.apache.xmlbeans.XmlDouble discamount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(DISCAMOUNT$92, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(DISCAMOUNT$92);
                      }
                      target.set(discamount);
                    }
                }
                
                /**
                 * Gets the "IMEI" element
                 */
                public java.lang.String getIMEI()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMEI$94, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "IMEI" element
                 */
                public org.apache.xmlbeans.XmlString xgetIMEI()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IMEI$94, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "IMEI" element
                 */
                public void setIMEI(java.lang.String imei)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMEI$94, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMEI$94);
                      }
                      target.setStringValue(imei);
                    }
                }
                
                /**
                 * Sets (as xml) the "IMEI" element
                 */
                public void xsetIMEI(org.apache.xmlbeans.XmlString imei)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IMEI$94, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(IMEI$94);
                      }
                      target.set(imei);
                    }
                }
                
                /**
                 * Gets the "Add_Charge" element
                 */
                public double getAddCharge()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADDCHARGE$96, 0);
                      if (target == null)
                      {
                        return 0.0;
                      }
                      return target.getDoubleValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Add_Charge" element
                 */
                public org.apache.xmlbeans.XmlDouble xgetAddCharge()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ADDCHARGE$96, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Add_Charge" element
                 */
                public void setAddCharge(double addCharge)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADDCHARGE$96, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ADDCHARGE$96);
                      }
                      target.setDoubleValue(addCharge);
                    }
                }
                
                /**
                 * Sets (as xml) the "Add_Charge" element
                 */
                public void xsetAddCharge(org.apache.xmlbeans.XmlDouble addCharge)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ADDCHARGE$96, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(ADDCHARGE$96);
                      }
                      target.set(addCharge);
                    }
                }
                
                /**
                 * Gets the "Disc_Type" element
                 */
                public int getDiscType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DISCTYPE$98, 0);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getIntValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Disc_Type" element
                 */
                public org.apache.xmlbeans.XmlInt xgetDiscType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DISCTYPE$98, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Disc_Type" element
                 */
                public void setDiscType(int discType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DISCTYPE$98, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DISCTYPE$98);
                      }
                      target.setIntValue(discType);
                    }
                }
                
                /**
                 * Sets (as xml) the "Disc_Type" element
                 */
                public void xsetDiscType(org.apache.xmlbeans.XmlInt discType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DISCTYPE$98, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(DISCTYPE$98);
                      }
                      target.set(discType);
                    }
                }
                
                /**
                 * Gets the "Is_coupon_SKU" element
                 */
                public boolean getIsCouponSKU()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISCOUPONSKU$100, 0);
                      if (target == null)
                      {
                        return false;
                      }
                      return target.getBooleanValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Is_coupon_SKU" element
                 */
                public org.apache.xmlbeans.XmlBoolean xgetIsCouponSKU()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlBoolean target = null;
                      target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ISCOUPONSKU$100, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Is_coupon_SKU" element
                 */
                public void setIsCouponSKU(boolean isCouponSKU)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISCOUPONSKU$100, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ISCOUPONSKU$100);
                      }
                      target.setBooleanValue(isCouponSKU);
                    }
                }
                
                /**
                 * Sets (as xml) the "Is_coupon_SKU" element
                 */
                public void xsetIsCouponSKU(org.apache.xmlbeans.XmlBoolean isCouponSKU)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlBoolean target = null;
                      target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ISCOUPONSKU$100, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(ISCOUPONSKU$100);
                      }
                      target.set(isCouponSKU);
                    }
                }
                
                /**
                 * Gets the "Is_buyback_SKU" element
                 */
                public boolean getIsBuybackSKU()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISBUYBACKSKU$102, 0);
                      if (target == null)
                      {
                        return false;
                      }
                      return target.getBooleanValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Is_buyback_SKU" element
                 */
                public org.apache.xmlbeans.XmlBoolean xgetIsBuybackSKU()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlBoolean target = null;
                      target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ISBUYBACKSKU$102, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Is_buyback_SKU" element
                 */
                public void setIsBuybackSKU(boolean isBuybackSKU)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISBUYBACKSKU$102, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ISBUYBACKSKU$102);
                      }
                      target.setBooleanValue(isBuybackSKU);
                    }
                }
                
                /**
                 * Sets (as xml) the "Is_buyback_SKU" element
                 */
                public void xsetIsBuybackSKU(org.apache.xmlbeans.XmlBoolean isBuybackSKU)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlBoolean target = null;
                      target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ISBUYBACKSKU$102, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(ISBUYBACKSKU$102);
                      }
                      target.set(isBuybackSKU);
                    }
                }
                
                /**
                 * Gets the "Lbl_Sno" element
                 */
                public java.lang.String getLblSno()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LBLSNO$104, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Lbl_Sno" element
                 */
                public org.apache.xmlbeans.XmlString xgetLblSno()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LBLSNO$104, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Lbl_Sno" element
                 */
                public void setLblSno(java.lang.String lblSno)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LBLSNO$104, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LBLSNO$104);
                      }
                      target.setStringValue(lblSno);
                    }
                }
                
                /**
                 * Sets (as xml) the "Lbl_Sno" element
                 */
                public void xsetLblSno(org.apache.xmlbeans.XmlString lblSno)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LBLSNO$104, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LBLSNO$104);
                      }
                      target.set(lblSno);
                    }
                }
                
                /**
                 * Gets the "Warranty" element
                 */
                public boolean getWarranty()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WARRANTY$106, 0);
                      if (target == null)
                      {
                        return false;
                      }
                      return target.getBooleanValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Warranty" element
                 */
                public org.apache.xmlbeans.XmlBoolean xgetWarranty()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlBoolean target = null;
                      target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(WARRANTY$106, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Warranty" element
                 */
                public void setWarranty(boolean warranty)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WARRANTY$106, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(WARRANTY$106);
                      }
                      target.setBooleanValue(warranty);
                    }
                }
                
                /**
                 * Sets (as xml) the "Warranty" element
                 */
                public void xsetWarranty(org.apache.xmlbeans.XmlBoolean warranty)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlBoolean target = null;
                      target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(WARRANTY$106, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(WARRANTY$106);
                      }
                      target.set(warranty);
                    }
                }
                
                /**
                 * Gets the "Pickup_Store_Type" element
                 */
                public int getPickupStoreType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PICKUPSTORETYPE$108, 0);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getIntValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pickup_Store_Type" element
                 */
                public org.apache.xmlbeans.XmlInt xgetPickupStoreType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(PICKUPSTORETYPE$108, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Pickup_Store_Type" element
                 */
                public void setPickupStoreType(int pickupStoreType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PICKUPSTORETYPE$108, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PICKUPSTORETYPE$108);
                      }
                      target.setIntValue(pickupStoreType);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pickup_Store_Type" element
                 */
                public void xsetPickupStoreType(org.apache.xmlbeans.XmlInt pickupStoreType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(PICKUPSTORETYPE$108, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(PICKUPSTORETYPE$108);
                      }
                      target.set(pickupStoreType);
                    }
                }
                
                /**
                 * Gets the "Pickup_Store_Name" element
                 */
                public java.lang.String getPickupStoreName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PICKUPSTORENAME$110, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pickup_Store_Name" element
                 */
                public org.apache.xmlbeans.XmlString xgetPickupStoreName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PICKUPSTORENAME$110, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Pickup_Store_Name" element
                 */
                public void setPickupStoreName(java.lang.String pickupStoreName)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PICKUPSTORENAME$110, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PICKUPSTORENAME$110);
                      }
                      target.setStringValue(pickupStoreName);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pickup_Store_Name" element
                 */
                public void xsetPickupStoreName(org.apache.xmlbeans.XmlString pickupStoreName)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PICKUPSTORENAME$110, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PICKUPSTORENAME$110);
                      }
                      target.set(pickupStoreName);
                    }
                }
                
                /**
                 * Gets the "Pickup_Store_Tel" element
                 */
                public java.lang.String getPickupStoreTel()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PICKUPSTORETEL$112, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pickup_Store_Tel" element
                 */
                public org.apache.xmlbeans.XmlString xgetPickupStoreTel()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PICKUPSTORETEL$112, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Pickup_Store_Tel" element
                 */
                public void setPickupStoreTel(java.lang.String pickupStoreTel)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PICKUPSTORETEL$112, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PICKUPSTORETEL$112);
                      }
                      target.setStringValue(pickupStoreTel);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pickup_Store_Tel" element
                 */
                public void xsetPickupStoreTel(org.apache.xmlbeans.XmlString pickupStoreTel)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PICKUPSTORETEL$112, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PICKUPSTORETEL$112);
                      }
                      target.set(pickupStoreTel);
                    }
                }
                
                /**
                 * Gets the "Pickup_Store_begin_hours" element
                 */
                public java.lang.String getPickupStoreBeginHours()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PICKUPSTOREBEGINHOURS$114, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pickup_Store_begin_hours" element
                 */
                public org.apache.xmlbeans.XmlString xgetPickupStoreBeginHours()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PICKUPSTOREBEGINHOURS$114, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Pickup_Store_begin_hours" element
                 */
                public void setPickupStoreBeginHours(java.lang.String pickupStoreBeginHours)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PICKUPSTOREBEGINHOURS$114, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PICKUPSTOREBEGINHOURS$114);
                      }
                      target.setStringValue(pickupStoreBeginHours);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pickup_Store_begin_hours" element
                 */
                public void xsetPickupStoreBeginHours(org.apache.xmlbeans.XmlString pickupStoreBeginHours)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PICKUPSTOREBEGINHOURS$114, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PICKUPSTOREBEGINHOURS$114);
                      }
                      target.set(pickupStoreBeginHours);
                    }
                }
                
                /**
                 * Gets the "Pickup_Store_End_Hours" element
                 */
                public java.lang.String getPickupStoreEndHours()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PICKUPSTOREENDHOURS$116, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pickup_Store_End_Hours" element
                 */
                public org.apache.xmlbeans.XmlString xgetPickupStoreEndHours()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PICKUPSTOREENDHOURS$116, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Pickup_Store_End_Hours" element
                 */
                public void setPickupStoreEndHours(java.lang.String pickupStoreEndHours)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PICKUPSTOREENDHOURS$116, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PICKUPSTOREENDHOURS$116);
                      }
                      target.setStringValue(pickupStoreEndHours);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pickup_Store_End_Hours" element
                 */
                public void xsetPickupStoreEndHours(org.apache.xmlbeans.XmlString pickupStoreEndHours)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PICKUPSTOREENDHOURS$116, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PICKUPSTOREENDHOURS$116);
                      }
                      target.set(pickupStoreEndHours);
                    }
                }
                
                /**
                 * Gets the "Pickup_Store_Addr_1" element
                 */
                public java.lang.String getPickupStoreAddr1()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PICKUPSTOREADDR1$118, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pickup_Store_Addr_1" element
                 */
                public org.apache.xmlbeans.XmlString xgetPickupStoreAddr1()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PICKUPSTOREADDR1$118, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Pickup_Store_Addr_1" element
                 */
                public void setPickupStoreAddr1(java.lang.String pickupStoreAddr1)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PICKUPSTOREADDR1$118, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PICKUPSTOREADDR1$118);
                      }
                      target.setStringValue(pickupStoreAddr1);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pickup_Store_Addr_1" element
                 */
                public void xsetPickupStoreAddr1(org.apache.xmlbeans.XmlString pickupStoreAddr1)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PICKUPSTOREADDR1$118, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PICKUPSTOREADDR1$118);
                      }
                      target.set(pickupStoreAddr1);
                    }
                }
                
                /**
                 * Gets the "Pickup_Store_Addr_2" element
                 */
                public java.lang.String getPickupStoreAddr2()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PICKUPSTOREADDR2$120, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pickup_Store_Addr_2" element
                 */
                public org.apache.xmlbeans.XmlString xgetPickupStoreAddr2()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PICKUPSTOREADDR2$120, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Pickup_Store_Addr_2" element
                 */
                public void setPickupStoreAddr2(java.lang.String pickupStoreAddr2)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PICKUPSTOREADDR2$120, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PICKUPSTOREADDR2$120);
                      }
                      target.setStringValue(pickupStoreAddr2);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pickup_Store_Addr_2" element
                 */
                public void xsetPickupStoreAddr2(org.apache.xmlbeans.XmlString pickupStoreAddr2)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PICKUPSTOREADDR2$120, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PICKUPSTOREADDR2$120);
                      }
                      target.set(pickupStoreAddr2);
                    }
                }
                
                /**
                 * Gets the "Pickup_Store_Addr_3" element
                 */
                public java.lang.String getPickupStoreAddr3()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PICKUPSTOREADDR3$122, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pickup_Store_Addr_3" element
                 */
                public org.apache.xmlbeans.XmlString xgetPickupStoreAddr3()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PICKUPSTOREADDR3$122, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Pickup_Store_Addr_3" element
                 */
                public void setPickupStoreAddr3(java.lang.String pickupStoreAddr3)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PICKUPSTOREADDR3$122, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PICKUPSTOREADDR3$122);
                      }
                      target.setStringValue(pickupStoreAddr3);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pickup_Store_Addr_3" element
                 */
                public void xsetPickupStoreAddr3(org.apache.xmlbeans.XmlString pickupStoreAddr3)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PICKUPSTOREADDR3$122, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PICKUPSTOREADDR3$122);
                      }
                      target.set(pickupStoreAddr3);
                    }
                }
                
                /**
                 * Gets the "Fault_Code" element
                 */
                public java.lang.String getFaultCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAULTCODE$124, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Fault_Code" element
                 */
                public org.apache.xmlbeans.XmlString xgetFaultCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAULTCODE$124, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Fault_Code" element
                 */
                public void setFaultCode(java.lang.String faultCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAULTCODE$124, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FAULTCODE$124);
                      }
                      target.setStringValue(faultCode);
                    }
                }
                
                /**
                 * Sets (as xml) the "Fault_Code" element
                 */
                public void xsetFaultCode(org.apache.xmlbeans.XmlString faultCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAULTCODE$124, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FAULTCODE$124);
                      }
                      target.set(faultCode);
                    }
                }
                
                /**
                 * Gets the "Fault_Desc" element
                 */
                public java.lang.String getFaultDesc()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAULTDESC$126, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Fault_Desc" element
                 */
                public org.apache.xmlbeans.XmlString xgetFaultDesc()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAULTDESC$126, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Fault_Desc" element
                 */
                public void setFaultDesc(java.lang.String faultDesc)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAULTDESC$126, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FAULTDESC$126);
                      }
                      target.setStringValue(faultDesc);
                    }
                }
                
                /**
                 * Sets (as xml) the "Fault_Desc" element
                 */
                public void xsetFaultDesc(org.apache.xmlbeans.XmlString faultDesc)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAULTDESC$126, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FAULTDESC$126);
                      }
                      target.set(faultDesc);
                    }
                }
                
                /**
                 * Gets the "Record_Type" element
                 */
                public int getRecordType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RECORDTYPE$128, 0);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getIntValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Record_Type" element
                 */
                public org.apache.xmlbeans.XmlInt xgetRecordType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(RECORDTYPE$128, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Record_Type" element
                 */
                public void setRecordType(int recordType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RECORDTYPE$128, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RECORDTYPE$128);
                      }
                      target.setIntValue(recordType);
                    }
                }
                
                /**
                 * Sets (as xml) the "Record_Type" element
                 */
                public void xsetRecordType(org.apache.xmlbeans.XmlInt recordType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(RECORDTYPE$128, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(RECORDTYPE$128);
                      }
                      target.set(recordType);
                    }
                }
                
                /**
                 * Gets the "Ref_GUID" element
                 */
                public java.lang.String getRefGUID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REFGUID$130, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Ref_GUID" element
                 */
                public org.apache.xmlbeans.XmlString xgetRefGUID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REFGUID$130, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Ref_GUID" element
                 */
                public void setRefGUID(java.lang.String refGUID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REFGUID$130, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(REFGUID$130);
                      }
                      target.setStringValue(refGUID);
                    }
                }
                
                /**
                 * Sets (as xml) the "Ref_GUID" element
                 */
                public void xsetRefGUID(org.apache.xmlbeans.XmlString refGUID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REFGUID$130, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REFGUID$130);
                      }
                      target.set(refGUID);
                    }
                }
                
                /**
                 * Gets the "ActAmount" element
                 */
                public double getActAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ACTAMOUNT$132, 0);
                      if (target == null)
                      {
                        return 0.0;
                      }
                      return target.getDoubleValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "ActAmount" element
                 */
                public org.apache.xmlbeans.XmlDouble xgetActAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ACTAMOUNT$132, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "ActAmount" element
                 */
                public void setActAmount(double actAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ACTAMOUNT$132, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ACTAMOUNT$132);
                      }
                      target.setDoubleValue(actAmount);
                    }
                }
                
                /**
                 * Sets (as xml) the "ActAmount" element
                 */
                public void xsetActAmount(org.apache.xmlbeans.XmlDouble actAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ACTAMOUNT$132, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(ACTAMOUNT$132);
                      }
                      target.set(actAmount);
                    }
                }
                
                /**
                 * Gets the "ActValue" element
                 */
                public double getActValue()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ACTVALUE$134, 0);
                      if (target == null)
                      {
                        return 0.0;
                      }
                      return target.getDoubleValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "ActValue" element
                 */
                public org.apache.xmlbeans.XmlDouble xgetActValue()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ACTVALUE$134, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "ActValue" element
                 */
                public void setActValue(double actValue)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ACTVALUE$134, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ACTVALUE$134);
                      }
                      target.setDoubleValue(actValue);
                    }
                }
                
                /**
                 * Sets (as xml) the "ActValue" element
                 */
                public void xsetActValue(org.apache.xmlbeans.XmlDouble actValue)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ACTVALUE$134, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(ACTVALUE$134);
                      }
                      target.set(actValue);
                    }
                }
                
                /**
                 * Gets the "Txn_Time_POS_BOM" element
                 */
                public java.lang.String getTxnTimePOSBOM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TXNTIMEPOSBOM$136, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Txn_Time_POS_BOM" element
                 */
                public org.apache.xmlbeans.XmlString xgetTxnTimePOSBOM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TXNTIMEPOSBOM$136, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Txn_Time_POS_BOM" element
                 */
                public void setTxnTimePOSBOM(java.lang.String txnTimePOSBOM)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TXNTIMEPOSBOM$136, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TXNTIMEPOSBOM$136);
                      }
                      target.setStringValue(txnTimePOSBOM);
                    }
                }
                
                /**
                 * Sets (as xml) the "Txn_Time_POS_BOM" element
                 */
                public void xsetTxnTimePOSBOM(org.apache.xmlbeans.XmlString txnTimePOSBOM)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TXNTIMEPOSBOM$136, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TXNTIMEPOSBOM$136);
                      }
                      target.set(txnTimePOSBOM);
                    }
                }
                
                /**
                 * Gets the "Txn_Date_POS_BOM" element
                 */
                public java.lang.String getTxnDatePOSBOM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TXNDATEPOSBOM$138, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Txn_Date_POS_BOM" element
                 */
                public org.apache.xmlbeans.XmlString xgetTxnDatePOSBOM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TXNDATEPOSBOM$138, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Txn_Date_POS_BOM" element
                 */
                public void setTxnDatePOSBOM(java.lang.String txnDatePOSBOM)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TXNDATEPOSBOM$138, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TXNDATEPOSBOM$138);
                      }
                      target.setStringValue(txnDatePOSBOM);
                    }
                }
                
                /**
                 * Sets (as xml) the "Txn_Date_POS_BOM" element
                 */
                public void xsetTxnDatePOSBOM(org.apache.xmlbeans.XmlString txnDatePOSBOM)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TXNDATEPOSBOM$138, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TXNDATEPOSBOM$138);
                      }
                      target.set(txnDatePOSBOM);
                    }
                }
                
                /**
                 * Gets the "Req_No" element
                 */
                public java.lang.String getReqNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REQNO$140, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Req_No" element
                 */
                public org.apache.xmlbeans.XmlString xgetReqNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REQNO$140, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Req_No" element
                 */
                public void setReqNo(java.lang.String reqNo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REQNO$140, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(REQNO$140);
                      }
                      target.setStringValue(reqNo);
                    }
                }
                
                /**
                 * Sets (as xml) the "Req_No" element
                 */
                public void xsetReqNo(org.apache.xmlbeans.XmlString reqNo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REQNO$140, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REQNO$140);
                      }
                      target.set(reqNo);
                    }
                }
                
                /**
                 * Gets the "Ref_No" element
                 */
                public java.lang.String getRefNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REFNO$142, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Ref_No" element
                 */
                public org.apache.xmlbeans.XmlString xgetRefNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REFNO$142, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Ref_No" element
                 */
                public void setRefNo(java.lang.String refNo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REFNO$142, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(REFNO$142);
                      }
                      target.setStringValue(refNo);
                    }
                }
                
                /**
                 * Sets (as xml) the "Ref_No" element
                 */
                public void xsetRefNo(org.apache.xmlbeans.XmlString refNo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REFNO$142, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REFNO$142);
                      }
                      target.set(refNo);
                    }
                }
                
                /**
                 * Gets the "MSISDN" element
                 */
                public java.lang.String getMSISDN()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MSISDN$144, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "MSISDN" element
                 */
                public org.apache.xmlbeans.XmlString xgetMSISDN()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MSISDN$144, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "MSISDN" element
                 */
                public void setMSISDN(java.lang.String msisdn)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MSISDN$144, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MSISDN$144);
                      }
                      target.setStringValue(msisdn);
                    }
                }
                
                /**
                 * Sets (as xml) the "MSISDN" element
                 */
                public void xsetMSISDN(org.apache.xmlbeans.XmlString msisdn)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MSISDN$144, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MSISDN$144);
                      }
                      target.set(msisdn);
                    }
                }
                
                /**
                 * Gets the "SIM_No" element
                 */
                public java.lang.String getSIMNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SIMNO$146, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "SIM_No" element
                 */
                public org.apache.xmlbeans.XmlString xgetSIMNo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SIMNO$146, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "SIM_No" element
                 */
                public void setSIMNo(java.lang.String simNo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SIMNO$146, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SIMNO$146);
                      }
                      target.setStringValue(simNo);
                    }
                }
                
                /**
                 * Sets (as xml) the "SIM_No" element
                 */
                public void xsetSIMNo(org.apache.xmlbeans.XmlString simNo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SIMNO$146, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SIMNO$146);
                      }
                      target.set(simNo);
                    }
                }
            }
        }
        /**
         * An XML orderRegTenders(@).
         *
         * This is a complex type.
         */
        public static class OrderRegTendersImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders
        {
            private static final long serialVersionUID = 1L;
            
            public OrderRegTendersImpl(org.apache.xmlbeans.SchemaType sType)
            {
                super(sType);
            }
            
            private static final javax.xml.namespace.QName ORDERREGINFOTENDER$0 = 
                new javax.xml.namespace.QName("", "OrderRegInfoTender");
            
            
            /**
             * Gets array of all "OrderRegInfoTender" elements
             */
            public noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders.OrderRegInfoTender[] getOrderRegInfoTenderArray()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    java.util.List targetList = new java.util.ArrayList();
                    get_store().find_all_element_users(ORDERREGINFOTENDER$0, targetList);
                    noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders.OrderRegInfoTender[] result = new noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders.OrderRegInfoTender[targetList.size()];
                    targetList.toArray(result);
                    return result;
                }
            }
            
            /**
             * Gets ith "OrderRegInfoTender" element
             */
            public noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders.OrderRegInfoTender getOrderRegInfoTenderArray(int i)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders.OrderRegInfoTender target = null;
                    target = (noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders.OrderRegInfoTender)get_store().find_element_user(ORDERREGINFOTENDER$0, i);
                    if (target == null)
                    {
                      throw new IndexOutOfBoundsException();
                    }
                    return target;
                }
            }
            
            /**
             * Returns number of "OrderRegInfoTender" element
             */
            public int sizeOfOrderRegInfoTenderArray()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(ORDERREGINFOTENDER$0);
                }
            }
            
            /**
             * Sets array of all "OrderRegInfoTender" element  WARNING: This method is not atomicaly synchronized.
             */
            public void setOrderRegInfoTenderArray(noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders.OrderRegInfoTender[] orderRegInfoTenderArray)
            {
                check_orphaned();
                arraySetterHelper(orderRegInfoTenderArray, ORDERREGINFOTENDER$0);
            }
            
            /**
             * Sets ith "OrderRegInfoTender" element
             */
            public void setOrderRegInfoTenderArray(int i, noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders.OrderRegInfoTender orderRegInfoTender)
            {
                generatedSetterHelperImpl(orderRegInfoTender, ORDERREGINFOTENDER$0, i, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_ARRAYITEM);
            }
            
            /**
             * Inserts and returns a new empty value (as xml) as the ith "OrderRegInfoTender" element
             */
            public noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders.OrderRegInfoTender insertNewOrderRegInfoTender(int i)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders.OrderRegInfoTender target = null;
                    target = (noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders.OrderRegInfoTender)get_store().insert_element_user(ORDERREGINFOTENDER$0, i);
                    return target;
                }
            }
            
            /**
             * Appends and returns a new empty value (as xml) as the last "OrderRegInfoTender" element
             */
            public noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders.OrderRegInfoTender addNewOrderRegInfoTender()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders.OrderRegInfoTender target = null;
                    target = (noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders.OrderRegInfoTender)get_store().add_element_user(ORDERREGINFOTENDER$0);
                    return target;
                }
            }
            
            /**
             * Removes the ith "OrderRegInfoTender" element
             */
            public void removeOrderRegInfoTender(int i)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(ORDERREGINFOTENDER$0, i);
                }
            }
            /**
             * An XML OrderRegInfoTender(@).
             *
             * This is a complex type.
             */
            public static class OrderRegInfoTenderImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.OrderRegInfoDocument.OrderRegInfo.OrderRegTenders.OrderRegInfoTender
            {
                private static final long serialVersionUID = 1L;
                
                public OrderRegInfoTenderImpl(org.apache.xmlbeans.SchemaType sType)
                {
                    super(sType);
                }
                
                private static final javax.xml.namespace.QName STORECODE$0 = 
                    new javax.xml.namespace.QName("", "Store_Code");
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
                private static final javax.xml.namespace.QName SEQNUM$12 = 
                    new javax.xml.namespace.QName("", "Seq_Num");
                private static final javax.xml.namespace.QName TENDERID$14 = 
                    new javax.xml.namespace.QName("", "Tender_ID");
                private static final javax.xml.namespace.QName TENDERTYPE$16 = 
                    new javax.xml.namespace.QName("", "Tender_Type");
                private static final javax.xml.namespace.QName TENDERAMOUNT$18 = 
                    new javax.xml.namespace.QName("", "Tender_Amount");
                private static final javax.xml.namespace.QName LOCALAMOUNT$20 = 
                    new javax.xml.namespace.QName("", "Local_Amount");
                private static final javax.xml.namespace.QName TENDERDESC$22 = 
                    new javax.xml.namespace.QName("", "Tender_Desc");
                private static final javax.xml.namespace.QName ADDITIONAL$24 = 
                    new javax.xml.namespace.QName("", "Additional");
                private static final javax.xml.namespace.QName ORGAMOUNT$26 = 
                    new javax.xml.namespace.QName("", "Org_Amount");
                private static final javax.xml.namespace.QName PAYMENTDIFF$28 = 
                    new javax.xml.namespace.QName("", "Payment_Diff");
                private static final javax.xml.namespace.QName PAYCONFIRMDATE$30 = 
                    new javax.xml.namespace.QName("", "Pay_Confirm_Date");
                private static final javax.xml.namespace.QName DEPOSIT$32 = 
                    new javax.xml.namespace.QName("", "Deposit");
                private static final javax.xml.namespace.QName RECORDTYPE$34 = 
                    new javax.xml.namespace.QName("", "Record_Type");
                private static final javax.xml.namespace.QName EODFLAG$36 = 
                    new javax.xml.namespace.QName("", "EOD_Flag");
                private static final javax.xml.namespace.QName INPUTTYPE$38 = 
                    new javax.xml.namespace.QName("", "Input_Type");
                private static final javax.xml.namespace.QName EXCHANGERATE$40 = 
                    new javax.xml.namespace.QName("", "Exchange_Rate");
                private static final javax.xml.namespace.QName PAYMENTTYPE$42 = 
                    new javax.xml.namespace.QName("", "Payment_Type");
                private static final javax.xml.namespace.QName STATUS$44 = 
                    new javax.xml.namespace.QName("", "Status");
                
                
                /**
                 * Gets the "Store_Code" element
                 */
                public java.lang.String getStoreCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STORECODE$0, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Store_Code" element
                 */
                public org.apache.xmlbeans.XmlString xgetStoreCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STORECODE$0, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Store_Code" element
                 */
                public void setStoreCode(java.lang.String storeCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STORECODE$0, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STORECODE$0);
                      }
                      target.setStringValue(storeCode);
                    }
                }
                
                /**
                 * Sets (as xml) the "Store_Code" element
                 */
                public void xsetStoreCode(org.apache.xmlbeans.XmlString storeCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STORECODE$0, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STORECODE$0);
                      }
                      target.set(storeCode);
                    }
                }
                
                /**
                 * Gets the "Register_ID" element
                 */
                public java.lang.String getRegisterID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REGISTERID$2, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Register_ID" element
                 */
                public org.apache.xmlbeans.XmlString xgetRegisterID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REGISTERID$2, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Register_ID" element
                 */
                public void setRegisterID(java.lang.String registerID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REGISTERID$2, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(REGISTERID$2);
                      }
                      target.setStringValue(registerID);
                    }
                }
                
                /**
                 * Sets (as xml) the "Register_ID" element
                 */
                public void xsetRegisterID(org.apache.xmlbeans.XmlString registerID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REGISTERID$2, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REGISTERID$2);
                      }
                      target.set(registerID);
                    }
                }
                
                /**
                 * Gets the "Trans_Num" element
                 */
                public java.lang.String getTransNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRANSNUM$4, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Trans_Num" element
                 */
                public org.apache.xmlbeans.XmlString xgetTransNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRANSNUM$4, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Trans_Num" element
                 */
                public void setTransNum(java.lang.String transNum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRANSNUM$4, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TRANSNUM$4);
                      }
                      target.setStringValue(transNum);
                    }
                }
                
                /**
                 * Sets (as xml) the "Trans_Num" element
                 */
                public void xsetTransNum(org.apache.xmlbeans.XmlString transNum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRANSNUM$4, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TRANSNUM$4);
                      }
                      target.set(transNum);
                    }
                }
                
                /**
                 * Gets the "Bus_Date" element
                 */
                public java.lang.String getBusDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BUSDATE$6, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Bus_Date" element
                 */
                public org.apache.xmlbeans.XmlString xgetBusDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BUSDATE$6, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Bus_Date" element
                 */
                public void setBusDate(java.lang.String busDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BUSDATE$6, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BUSDATE$6);
                      }
                      target.setStringValue(busDate);
                    }
                }
                
                /**
                 * Sets (as xml) the "Bus_Date" element
                 */
                public void xsetBusDate(org.apache.xmlbeans.XmlString busDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BUSDATE$6, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BUSDATE$6);
                      }
                      target.set(busDate);
                    }
                }
                
                /**
                 * Gets the "Txn_Date" element
                 */
                public java.lang.String getTxnDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TXNDATE$8, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Txn_Date" element
                 */
                public org.apache.xmlbeans.XmlString xgetTxnDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TXNDATE$8, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Txn_Date" element
                 */
                public void setTxnDate(java.lang.String txnDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TXNDATE$8, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TXNDATE$8);
                      }
                      target.setStringValue(txnDate);
                    }
                }
                
                /**
                 * Sets (as xml) the "Txn_Date" element
                 */
                public void xsetTxnDate(org.apache.xmlbeans.XmlString txnDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TXNDATE$8, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TXNDATE$8);
                      }
                      target.set(txnDate);
                    }
                }
                
                /**
                 * Gets the "SM_Type" element
                 */
                public int getSMType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SMTYPE$10, 0);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getIntValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "SM_Type" element
                 */
                public org.apache.xmlbeans.XmlInt xgetSMType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SMTYPE$10, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "SM_Type" element
                 */
                public void setSMType(int smType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SMTYPE$10, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SMTYPE$10);
                      }
                      target.setIntValue(smType);
                    }
                }
                
                /**
                 * Sets (as xml) the "SM_Type" element
                 */
                public void xsetSMType(org.apache.xmlbeans.XmlInt smType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SMTYPE$10, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(SMTYPE$10);
                      }
                      target.set(smType);
                    }
                }
                
                /**
                 * Gets the "Seq_Num" element
                 */
                public java.lang.String getSeqNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SEQNUM$12, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Seq_Num" element
                 */
                public org.apache.xmlbeans.XmlString xgetSeqNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SEQNUM$12, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Seq_Num" element
                 */
                public void setSeqNum(java.lang.String seqNum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SEQNUM$12, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SEQNUM$12);
                      }
                      target.setStringValue(seqNum);
                    }
                }
                
                /**
                 * Sets (as xml) the "Seq_Num" element
                 */
                public void xsetSeqNum(org.apache.xmlbeans.XmlString seqNum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SEQNUM$12, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SEQNUM$12);
                      }
                      target.set(seqNum);
                    }
                }
                
                /**
                 * Gets the "Tender_ID" element
                 */
                public java.lang.String getTenderID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TENDERID$14, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Tender_ID" element
                 */
                public org.apache.xmlbeans.XmlString xgetTenderID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TENDERID$14, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Tender_ID" element
                 */
                public void setTenderID(java.lang.String tenderID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TENDERID$14, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TENDERID$14);
                      }
                      target.setStringValue(tenderID);
                    }
                }
                
                /**
                 * Sets (as xml) the "Tender_ID" element
                 */
                public void xsetTenderID(org.apache.xmlbeans.XmlString tenderID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TENDERID$14, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TENDERID$14);
                      }
                      target.set(tenderID);
                    }
                }
                
                /**
                 * Gets the "Tender_Type" element
                 */
                public java.lang.String getTenderType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TENDERTYPE$16, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Tender_Type" element
                 */
                public org.apache.xmlbeans.XmlString xgetTenderType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TENDERTYPE$16, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Tender_Type" element
                 */
                public void setTenderType(java.lang.String tenderType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TENDERTYPE$16, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TENDERTYPE$16);
                      }
                      target.setStringValue(tenderType);
                    }
                }
                
                /**
                 * Sets (as xml) the "Tender_Type" element
                 */
                public void xsetTenderType(org.apache.xmlbeans.XmlString tenderType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TENDERTYPE$16, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TENDERTYPE$16);
                      }
                      target.set(tenderType);
                    }
                }
                
                /**
                 * Gets the "Tender_Amount" element
                 */
                public double getTenderAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TENDERAMOUNT$18, 0);
                      if (target == null)
                      {
                        return 0.0;
                      }
                      return target.getDoubleValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Tender_Amount" element
                 */
                public org.apache.xmlbeans.XmlDouble xgetTenderAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(TENDERAMOUNT$18, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Tender_Amount" element
                 */
                public void setTenderAmount(double tenderAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TENDERAMOUNT$18, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TENDERAMOUNT$18);
                      }
                      target.setDoubleValue(tenderAmount);
                    }
                }
                
                /**
                 * Sets (as xml) the "Tender_Amount" element
                 */
                public void xsetTenderAmount(org.apache.xmlbeans.XmlDouble tenderAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(TENDERAMOUNT$18, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(TENDERAMOUNT$18);
                      }
                      target.set(tenderAmount);
                    }
                }
                
                /**
                 * Gets the "Local_Amount" element
                 */
                public double getLocalAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOCALAMOUNT$20, 0);
                      if (target == null)
                      {
                        return 0.0;
                      }
                      return target.getDoubleValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Local_Amount" element
                 */
                public org.apache.xmlbeans.XmlDouble xgetLocalAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(LOCALAMOUNT$20, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Local_Amount" element
                 */
                public void setLocalAmount(double localAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOCALAMOUNT$20, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LOCALAMOUNT$20);
                      }
                      target.setDoubleValue(localAmount);
                    }
                }
                
                /**
                 * Sets (as xml) the "Local_Amount" element
                 */
                public void xsetLocalAmount(org.apache.xmlbeans.XmlDouble localAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(LOCALAMOUNT$20, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(LOCALAMOUNT$20);
                      }
                      target.set(localAmount);
                    }
                }
                
                /**
                 * Gets the "Tender_Desc" element
                 */
                public java.lang.String getTenderDesc()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TENDERDESC$22, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Tender_Desc" element
                 */
                public org.apache.xmlbeans.XmlString xgetTenderDesc()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TENDERDESC$22, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Tender_Desc" element
                 */
                public void setTenderDesc(java.lang.String tenderDesc)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TENDERDESC$22, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TENDERDESC$22);
                      }
                      target.setStringValue(tenderDesc);
                    }
                }
                
                /**
                 * Sets (as xml) the "Tender_Desc" element
                 */
                public void xsetTenderDesc(org.apache.xmlbeans.XmlString tenderDesc)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TENDERDESC$22, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TENDERDESC$22);
                      }
                      target.set(tenderDesc);
                    }
                }
                
                /**
                 * Gets the "Additional" element
                 */
                public java.lang.String getAdditional()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADDITIONAL$24, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Additional" element
                 */
                public org.apache.xmlbeans.XmlString xgetAdditional()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADDITIONAL$24, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Additional" element
                 */
                public void setAdditional(java.lang.String additional)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADDITIONAL$24, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ADDITIONAL$24);
                      }
                      target.setStringValue(additional);
                    }
                }
                
                /**
                 * Sets (as xml) the "Additional" element
                 */
                public void xsetAdditional(org.apache.xmlbeans.XmlString additional)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADDITIONAL$24, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ADDITIONAL$24);
                      }
                      target.set(additional);
                    }
                }
                
                /**
                 * Gets the "Org_Amount" element
                 */
                public double getOrgAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ORGAMOUNT$26, 0);
                      if (target == null)
                      {
                        return 0.0;
                      }
                      return target.getDoubleValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Org_Amount" element
                 */
                public org.apache.xmlbeans.XmlDouble xgetOrgAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ORGAMOUNT$26, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Org_Amount" element
                 */
                public void setOrgAmount(double orgAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ORGAMOUNT$26, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ORGAMOUNT$26);
                      }
                      target.setDoubleValue(orgAmount);
                    }
                }
                
                /**
                 * Sets (as xml) the "Org_Amount" element
                 */
                public void xsetOrgAmount(org.apache.xmlbeans.XmlDouble orgAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ORGAMOUNT$26, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(ORGAMOUNT$26);
                      }
                      target.set(orgAmount);
                    }
                }
                
                /**
                 * Gets the "Payment_Diff" element
                 */
                public double getPaymentDiff()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAYMENTDIFF$28, 0);
                      if (target == null)
                      {
                        return 0.0;
                      }
                      return target.getDoubleValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Payment_Diff" element
                 */
                public org.apache.xmlbeans.XmlDouble xgetPaymentDiff()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(PAYMENTDIFF$28, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Payment_Diff" element
                 */
                public void setPaymentDiff(double paymentDiff)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAYMENTDIFF$28, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PAYMENTDIFF$28);
                      }
                      target.setDoubleValue(paymentDiff);
                    }
                }
                
                /**
                 * Sets (as xml) the "Payment_Diff" element
                 */
                public void xsetPaymentDiff(org.apache.xmlbeans.XmlDouble paymentDiff)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(PAYMENTDIFF$28, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(PAYMENTDIFF$28);
                      }
                      target.set(paymentDiff);
                    }
                }
                
                /**
                 * Gets the "Pay_Confirm_Date" element
                 */
                public java.lang.String getPayConfirmDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAYCONFIRMDATE$30, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pay_Confirm_Date" element
                 */
                public org.apache.xmlbeans.XmlString xgetPayConfirmDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAYCONFIRMDATE$30, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Pay_Confirm_Date" element
                 */
                public void setPayConfirmDate(java.lang.String payConfirmDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAYCONFIRMDATE$30, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PAYCONFIRMDATE$30);
                      }
                      target.setStringValue(payConfirmDate);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pay_Confirm_Date" element
                 */
                public void xsetPayConfirmDate(org.apache.xmlbeans.XmlString payConfirmDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAYCONFIRMDATE$30, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PAYCONFIRMDATE$30);
                      }
                      target.set(payConfirmDate);
                    }
                }
                
                /**
                 * Gets the "Deposit" element
                 */
                public boolean getDeposit()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEPOSIT$32, 0);
                      if (target == null)
                      {
                        return false;
                      }
                      return target.getBooleanValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Deposit" element
                 */
                public org.apache.xmlbeans.XmlBoolean xgetDeposit()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlBoolean target = null;
                      target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(DEPOSIT$32, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Deposit" element
                 */
                public void setDeposit(boolean deposit)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEPOSIT$32, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEPOSIT$32);
                      }
                      target.setBooleanValue(deposit);
                    }
                }
                
                /**
                 * Sets (as xml) the "Deposit" element
                 */
                public void xsetDeposit(org.apache.xmlbeans.XmlBoolean deposit)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlBoolean target = null;
                      target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(DEPOSIT$32, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(DEPOSIT$32);
                      }
                      target.set(deposit);
                    }
                }
                
                /**
                 * Gets the "Record_Type" element
                 */
                public int getRecordType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RECORDTYPE$34, 0);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getIntValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Record_Type" element
                 */
                public org.apache.xmlbeans.XmlInt xgetRecordType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(RECORDTYPE$34, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Record_Type" element
                 */
                public void setRecordType(int recordType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RECORDTYPE$34, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RECORDTYPE$34);
                      }
                      target.setIntValue(recordType);
                    }
                }
                
                /**
                 * Sets (as xml) the "Record_Type" element
                 */
                public void xsetRecordType(org.apache.xmlbeans.XmlInt recordType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(RECORDTYPE$34, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(RECORDTYPE$34);
                      }
                      target.set(recordType);
                    }
                }
                
                /**
                 * Gets the "EOD_Flag" element
                 */
                public int getEODFlag()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EODFLAG$36, 0);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getIntValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "EOD_Flag" element
                 */
                public org.apache.xmlbeans.XmlInt xgetEODFlag()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(EODFLAG$36, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "EOD_Flag" element
                 */
                public void setEODFlag(int eodFlag)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EODFLAG$36, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EODFLAG$36);
                      }
                      target.setIntValue(eodFlag);
                    }
                }
                
                /**
                 * Sets (as xml) the "EOD_Flag" element
                 */
                public void xsetEODFlag(org.apache.xmlbeans.XmlInt eodFlag)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(EODFLAG$36, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(EODFLAG$36);
                      }
                      target.set(eodFlag);
                    }
                }
                
                /**
                 * Gets the "Input_Type" element
                 */
                public java.lang.String getInputType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INPUTTYPE$38, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Input_Type" element
                 */
                public org.apache.xmlbeans.XmlString xgetInputType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INPUTTYPE$38, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Input_Type" element
                 */
                public void setInputType(java.lang.String inputType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INPUTTYPE$38, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INPUTTYPE$38);
                      }
                      target.setStringValue(inputType);
                    }
                }
                
                /**
                 * Sets (as xml) the "Input_Type" element
                 */
                public void xsetInputType(org.apache.xmlbeans.XmlString inputType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INPUTTYPE$38, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INPUTTYPE$38);
                      }
                      target.set(inputType);
                    }
                }
                
                /**
                 * Gets the "Exchange_Rate" element
                 */
                public double getExchangeRate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXCHANGERATE$40, 0);
                      if (target == null)
                      {
                        return 0.0;
                      }
                      return target.getDoubleValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Exchange_Rate" element
                 */
                public org.apache.xmlbeans.XmlDouble xgetExchangeRate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(EXCHANGERATE$40, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Exchange_Rate" element
                 */
                public void setExchangeRate(double exchangeRate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXCHANGERATE$40, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EXCHANGERATE$40);
                      }
                      target.setDoubleValue(exchangeRate);
                    }
                }
                
                /**
                 * Sets (as xml) the "Exchange_Rate" element
                 */
                public void xsetExchangeRate(org.apache.xmlbeans.XmlDouble exchangeRate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDouble target = null;
                      target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(EXCHANGERATE$40, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(EXCHANGERATE$40);
                      }
                      target.set(exchangeRate);
                    }
                }
                
                /**
                 * Gets the "Payment_Type" element
                 */
                public int getPaymentType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAYMENTTYPE$42, 0);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getIntValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Payment_Type" element
                 */
                public org.apache.xmlbeans.XmlInt xgetPaymentType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(PAYMENTTYPE$42, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Payment_Type" element
                 */
                public void setPaymentType(int paymentType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAYMENTTYPE$42, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PAYMENTTYPE$42);
                      }
                      target.setIntValue(paymentType);
                    }
                }
                
                /**
                 * Sets (as xml) the "Payment_Type" element
                 */
                public void xsetPaymentType(org.apache.xmlbeans.XmlInt paymentType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(PAYMENTTYPE$42, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(PAYMENTTYPE$42);
                      }
                      target.set(paymentType);
                    }
                }
                
                /**
                 * Gets the "Status" element
                 */
                public int getStatus()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATUS$44, 0);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getIntValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Status" element
                 */
                public org.apache.xmlbeans.XmlInt xgetStatus()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(STATUS$44, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Status" element
                 */
                public void setStatus(int status)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATUS$44, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STATUS$44);
                      }
                      target.setIntValue(status);
                    }
                }
                
                /**
                 * Sets (as xml) the "Status" element
                 */
                public void xsetStatus(org.apache.xmlbeans.XmlInt status)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlInt target = null;
                      target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(STATUS$44, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(STATUS$44);
                      }
                      target.set(status);
                    }
                }
            }
        }
    }
}
