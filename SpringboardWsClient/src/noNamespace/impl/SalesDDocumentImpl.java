/*
 * An XML document type.
 * Localname: sales_d
 * Namespace: 
 * Java type: noNamespace.SalesDDocument
 *
 * Automatically generated - do not modify.
 */
package noNamespace.impl;
/**
 * A document containing one sales_d(@) element.
 *
 * This is a complex type.
 */
public class SalesDDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.SalesDDocument
{
    private static final long serialVersionUID = 1L;
    
    public SalesDDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SALESD$0 = 
        new javax.xml.namespace.QName("", "sales_d");
    
    
    /**
     * Gets the "sales_d" element
     */
    public noNamespace.SalesDDocument.SalesD getSalesD()
    {
        synchronized (monitor())
        {
            check_orphaned();
            noNamespace.SalesDDocument.SalesD target = null;
            target = (noNamespace.SalesDDocument.SalesD)get_store().find_element_user(SALESD$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "sales_d" element
     */
    public void setSalesD(noNamespace.SalesDDocument.SalesD salesD)
    {
        generatedSetterHelperImpl(salesD, SALESD$0, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }
    
    /**
     * Appends and returns a new empty "sales_d" element
     */
    public noNamespace.SalesDDocument.SalesD addNewSalesD()
    {
        synchronized (monitor())
        {
            check_orphaned();
            noNamespace.SalesDDocument.SalesD target = null;
            target = (noNamespace.SalesDDocument.SalesD)get_store().add_element_user(SALESD$0);
            return target;
        }
    }
    /**
     * An XML sales_d(@).
     *
     * This is a complex type.
     */
    public static class SalesDImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.SalesDDocument.SalesD
    {
        private static final long serialVersionUID = 1L;
        
        public SalesDImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName ROWDATA$0 = 
            new javax.xml.namespace.QName("", "ROWDATA");
        
        
        /**
         * Gets the "ROWDATA" element
         */
        public noNamespace.SalesDDocument.SalesD.ROWDATA getROWDATA()
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.SalesDDocument.SalesD.ROWDATA target = null;
                target = (noNamespace.SalesDDocument.SalesD.ROWDATA)get_store().find_element_user(ROWDATA$0, 0);
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
        public void setROWDATA(noNamespace.SalesDDocument.SalesD.ROWDATA rowdata)
        {
            generatedSetterHelperImpl(rowdata, ROWDATA$0, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
        }
        
        /**
         * Appends and returns a new empty "ROWDATA" element
         */
        public noNamespace.SalesDDocument.SalesD.ROWDATA addNewROWDATA()
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.SalesDDocument.SalesD.ROWDATA target = null;
                target = (noNamespace.SalesDDocument.SalesD.ROWDATA)get_store().add_element_user(ROWDATA$0);
                return target;
            }
        }
        /**
         * An XML ROWDATA(@).
         *
         * This is a complex type.
         */
        public static class ROWDATAImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.SalesDDocument.SalesD.ROWDATA
        {
            private static final long serialVersionUID = 1L;
            
            public ROWDATAImpl(org.apache.xmlbeans.SchemaType sType)
            {
                super(sType);
            }
            
            private static final javax.xml.namespace.QName ROW$0 = 
                new javax.xml.namespace.QName("", "ROW");
            
            
            /**
             * Gets array of all "ROW" elements
             */
            public noNamespace.SalesDDocument.SalesD.ROWDATA.ROW[] getROWArray()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    java.util.List targetList = new java.util.ArrayList();
                    get_store().find_all_element_users(ROW$0, targetList);
                    noNamespace.SalesDDocument.SalesD.ROWDATA.ROW[] result = new noNamespace.SalesDDocument.SalesD.ROWDATA.ROW[targetList.size()];
                    targetList.toArray(result);
                    return result;
                }
            }
            
            /**
             * Gets ith "ROW" element
             */
            public noNamespace.SalesDDocument.SalesD.ROWDATA.ROW getROWArray(int i)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    noNamespace.SalesDDocument.SalesD.ROWDATA.ROW target = null;
                    target = (noNamespace.SalesDDocument.SalesD.ROWDATA.ROW)get_store().find_element_user(ROW$0, i);
                    if (target == null)
                    {
                      throw new IndexOutOfBoundsException();
                    }
                    return target;
                }
            }
            
            /**
             * Returns number of "ROW" element
             */
            public int sizeOfROWArray()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(ROW$0);
                }
            }
            
            /**
             * Sets array of all "ROW" element  WARNING: This method is not atomicaly synchronized.
             */
            public void setROWArray(noNamespace.SalesDDocument.SalesD.ROWDATA.ROW[] rowArray)
            {
                check_orphaned();
                arraySetterHelper(rowArray, ROW$0);
            }
            
            /**
             * Sets ith "ROW" element
             */
            public void setROWArray(int i, noNamespace.SalesDDocument.SalesD.ROWDATA.ROW row)
            {
                generatedSetterHelperImpl(row, ROW$0, i, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_ARRAYITEM);
            }
            
            /**
             * Inserts and returns a new empty value (as xml) as the ith "ROW" element
             */
            public noNamespace.SalesDDocument.SalesD.ROWDATA.ROW insertNewROW(int i)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    noNamespace.SalesDDocument.SalesD.ROWDATA.ROW target = null;
                    target = (noNamespace.SalesDDocument.SalesD.ROWDATA.ROW)get_store().insert_element_user(ROW$0, i);
                    return target;
                }
            }
            
            /**
             * Appends and returns a new empty value (as xml) as the last "ROW" element
             */
            public noNamespace.SalesDDocument.SalesD.ROWDATA.ROW addNewROW()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    noNamespace.SalesDDocument.SalesD.ROWDATA.ROW target = null;
                    target = (noNamespace.SalesDDocument.SalesD.ROWDATA.ROW)get_store().add_element_user(ROW$0);
                    return target;
                }
            }
            
            /**
             * Removes the ith "ROW" element
             */
            public void removeROW(int i)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(ROW$0, i);
                }
            }
            /**
             * An XML ROW(@).
             *
             * This is an atomic type that is a restriction of noNamespace.SalesDDocument$SalesD$ROWDATA$ROW.
             */
            public static class ROWImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements noNamespace.SalesDDocument.SalesD.ROWDATA.ROW
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
                private static final javax.xml.namespace.QName SEQNUM$12 = 
                    new javax.xml.namespace.QName("", "Seq_Num");
                private static final javax.xml.namespace.QName SKU$14 = 
                    new javax.xml.namespace.QName("", "SKU");
                private static final javax.xml.namespace.QName PRODDESC$16 = 
                    new javax.xml.namespace.QName("", "ProdDesc");
                private static final javax.xml.namespace.QName QTY$18 = 
                    new javax.xml.namespace.QName("", "Qty");
                private static final javax.xml.namespace.QName ORGPRICE$20 = 
                    new javax.xml.namespace.QName("", "Org_Price");
                private static final javax.xml.namespace.QName UNITPRICE$22 = 
                    new javax.xml.namespace.QName("", "Unit_Price");
                private static final javax.xml.namespace.QName NETPRICE$24 = 
                    new javax.xml.namespace.QName("", "Net_Price");
                private static final javax.xml.namespace.QName ORGAMOUNT$26 = 
                    new javax.xml.namespace.QName("", "Org_Amount");
                private static final javax.xml.namespace.QName UNITAMOUNT$28 = 
                    new javax.xml.namespace.QName("", "Unit_Amount");
                private static final javax.xml.namespace.QName NETAMOUNT$30 = 
                    new javax.xml.namespace.QName("", "Net_Amount");
                private static final javax.xml.namespace.QName TOTALQTY$32 = 
                    new javax.xml.namespace.QName("", "Total_Qty");
                private static final javax.xml.namespace.QName DISCOUNTPRICE$34 = 
                    new javax.xml.namespace.QName("", "Discount_Price");
                private static final javax.xml.namespace.QName IDREASONCODE$36 = 
                    new javax.xml.namespace.QName("", "ID_ReasonCode");
                private static final javax.xml.namespace.QName ADDITIONAL1$38 = 
                    new javax.xml.namespace.QName("", "Additional1");
                private static final javax.xml.namespace.QName ADDITIONAL2$40 = 
                    new javax.xml.namespace.QName("", "Additional2");
                private static final javax.xml.namespace.QName ADDITIONAL3$42 = 
                    new javax.xml.namespace.QName("", "Additional3");
                private static final javax.xml.namespace.QName CHGCLASS$44 = 
                    new javax.xml.namespace.QName("", "ChgClass");
                private static final javax.xml.namespace.QName ISBOM$46 = 
                    new javax.xml.namespace.QName("", "IsBOM");
                private static final javax.xml.namespace.QName MNPNEED$48 = 
                    new javax.xml.namespace.QName("", "MNPNeed");
                private static final javax.xml.namespace.QName CNPNEED$50 = 
                    new javax.xml.namespace.QName("", "CNPNeed");
                private static final javax.xml.namespace.QName CHARGETYPE$52 = 
                    new javax.xml.namespace.QName("", "ChargeType");
                private static final javax.xml.namespace.QName SNNUM$54 = 
                    new javax.xml.namespace.QName("", "SN_Num");
                private static final javax.xml.namespace.QName STOCKTYPE$56 = 
                    new javax.xml.namespace.QName("", "StockType");
                private static final javax.xml.namespace.QName COLLECTED$58 = 
                    new javax.xml.namespace.QName("", "Collected");
                private static final javax.xml.namespace.QName PICKUPLOCATION$60 = 
                    new javax.xml.namespace.QName("", "Pickup_Location");
                private static final javax.xml.namespace.QName DEPTCODE$62 = 
                    new javax.xml.namespace.QName("", "DeptCode");
                private static final javax.xml.namespace.QName DISCPRICE$64 = 
                    new javax.xml.namespace.QName("", "DISC_PRICE");
                private static final javax.xml.namespace.QName DISCAMOUNT$66 = 
                    new javax.xml.namespace.QName("", "DISC_AMOUNT");
                private static final javax.xml.namespace.QName IMEI$68 = 
                    new javax.xml.namespace.QName("", "IMEI");
                private static final javax.xml.namespace.QName ADDCHARGE$70 = 
                    new javax.xml.namespace.QName("", "Add_Charge");
                private static final javax.xml.namespace.QName DISCTYPE$72 = 
                    new javax.xml.namespace.QName("", "Disc_Type");
                private static final javax.xml.namespace.QName ISCOUPONSKU$74 = 
                    new javax.xml.namespace.QName("", "Is_coupon_sku");
                private static final javax.xml.namespace.QName ISBUYBACKSKU$76 = 
                    new javax.xml.namespace.QName("", "Is_buyback_sku");
                private static final javax.xml.namespace.QName PICKUPSTORETYPE$78 = 
                    new javax.xml.namespace.QName("", "Pickup_Store_Type");
                private static final javax.xml.namespace.QName PICKUPSTORENAME$80 = 
                    new javax.xml.namespace.QName("", "Pickup_Store_Name");
                private static final javax.xml.namespace.QName PICKUPSTORETEL$82 = 
                    new javax.xml.namespace.QName("", "Pickup_Store_tel");
                private static final javax.xml.namespace.QName PICKUPSTOREBEGINHOURS$84 = 
                    new javax.xml.namespace.QName("", "Pickup_Store_Begin_Hours");
                private static final javax.xml.namespace.QName PICKUPSTOREENDHOURS$86 = 
                    new javax.xml.namespace.QName("", "Pickup_Store_End_Hours");
                private static final javax.xml.namespace.QName PICKUPSTOREADDR1$88 = 
                    new javax.xml.namespace.QName("", "Pickup_Store_Addr_1");
                private static final javax.xml.namespace.QName PICKUPSTOREADDR2$90 = 
                    new javax.xml.namespace.QName("", "Pickup_Store_Addr_2");
                private static final javax.xml.namespace.QName PICKUPSTOREADDR3$92 = 
                    new javax.xml.namespace.QName("", "Pickup_Store_Addr_3");
                private static final javax.xml.namespace.QName LBLSNO$94 = 
                    new javax.xml.namespace.QName("", "lbl_sno");
                private static final javax.xml.namespace.QName WARRANTY$96 = 
                    new javax.xml.namespace.QName("", "warranty");
                private static final javax.xml.namespace.QName FAULTCODE$98 = 
                    new javax.xml.namespace.QName("", "Fault_Code");
                private static final javax.xml.namespace.QName FAULTDESC$100 = 
                    new javax.xml.namespace.QName("", "Fault_Desc");
                private static final javax.xml.namespace.QName RECORDTYPE$102 = 
                    new javax.xml.namespace.QName("", "Record_Type");
                private static final javax.xml.namespace.QName ACTVALUE$104 = 
                    new javax.xml.namespace.QName("", "ActValue");
                private static final javax.xml.namespace.QName ACTAMOUNT$106 = 
                    new javax.xml.namespace.QName("", "ActAmount");
                private static final javax.xml.namespace.QName STOCKSTATUS$108 = 
                    new javax.xml.namespace.QName("", "StockStatus");
                private static final javax.xml.namespace.QName SUBID$110 = 
                    new javax.xml.namespace.QName("", "Sub_ID");
                private static final javax.xml.namespace.QName PICKUPSTAFF$112 = 
                    new javax.xml.namespace.QName("", "Pickup_Staff");
                private static final javax.xml.namespace.QName PICKUPDATE$114 = 
                    new javax.xml.namespace.QName("", "Pickup_Date");
                
                
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
                 * Gets the "Seq_Num" attribute
                 */
                public java.lang.String getSeqNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SEQNUM$12);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Seq_Num" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetSeqNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SEQNUM$12);
                      return target;
                    }
                }
                
                /**
                 * True if has "Seq_Num" attribute
                 */
                public boolean isSetSeqNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(SEQNUM$12) != null;
                    }
                }
                
                /**
                 * Sets the "Seq_Num" attribute
                 */
                public void setSeqNum(java.lang.String seqNum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SEQNUM$12);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SEQNUM$12);
                      }
                      target.setStringValue(seqNum);
                    }
                }
                
                /**
                 * Sets (as xml) the "Seq_Num" attribute
                 */
                public void xsetSeqNum(org.apache.xmlbeans.XmlString seqNum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SEQNUM$12);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SEQNUM$12);
                      }
                      target.set(seqNum);
                    }
                }
                
                /**
                 * Unsets the "Seq_Num" attribute
                 */
                public void unsetSeqNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(SEQNUM$12);
                    }
                }
                
                /**
                 * Gets the "SKU" attribute
                 */
                public java.lang.String getSKU()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SKU$14);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "SKU" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetSKU()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SKU$14);
                      return target;
                    }
                }
                
                /**
                 * True if has "SKU" attribute
                 */
                public boolean isSetSKU()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(SKU$14) != null;
                    }
                }
                
                /**
                 * Sets the "SKU" attribute
                 */
                public void setSKU(java.lang.String sku)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SKU$14);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SKU$14);
                      }
                      target.setStringValue(sku);
                    }
                }
                
                /**
                 * Sets (as xml) the "SKU" attribute
                 */
                public void xsetSKU(org.apache.xmlbeans.XmlString sku)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SKU$14);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SKU$14);
                      }
                      target.set(sku);
                    }
                }
                
                /**
                 * Unsets the "SKU" attribute
                 */
                public void unsetSKU()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(SKU$14);
                    }
                }
                
                /**
                 * Gets the "ProdDesc" attribute
                 */
                public java.lang.String getProdDesc()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PRODDESC$16);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "ProdDesc" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetProdDesc()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PRODDESC$16);
                      return target;
                    }
                }
                
                /**
                 * True if has "ProdDesc" attribute
                 */
                public boolean isSetProdDesc()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(PRODDESC$16) != null;
                    }
                }
                
                /**
                 * Sets the "ProdDesc" attribute
                 */
                public void setProdDesc(java.lang.String prodDesc)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PRODDESC$16);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PRODDESC$16);
                      }
                      target.setStringValue(prodDesc);
                    }
                }
                
                /**
                 * Sets (as xml) the "ProdDesc" attribute
                 */
                public void xsetProdDesc(org.apache.xmlbeans.XmlString prodDesc)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PRODDESC$16);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PRODDESC$16);
                      }
                      target.set(prodDesc);
                    }
                }
                
                /**
                 * Unsets the "ProdDesc" attribute
                 */
                public void unsetProdDesc()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(PRODDESC$16);
                    }
                }
                
                /**
                 * Gets the "Qty" attribute
                 */
                public short getQty()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(QTY$18);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getShortValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Qty" attribute
                 */
                public org.apache.xmlbeans.XmlShort xgetQty()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(QTY$18);
                      return target;
                    }
                }
                
                /**
                 * True if has "Qty" attribute
                 */
                public boolean isSetQty()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(QTY$18) != null;
                    }
                }
                
                /**
                 * Sets the "Qty" attribute
                 */
                public void setQty(short qty)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(QTY$18);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(QTY$18);
                      }
                      target.setShortValue(qty);
                    }
                }
                
                /**
                 * Sets (as xml) the "Qty" attribute
                 */
                public void xsetQty(org.apache.xmlbeans.XmlShort qty)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(QTY$18);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlShort)get_store().add_attribute_user(QTY$18);
                      }
                      target.set(qty);
                    }
                }
                
                /**
                 * Unsets the "Qty" attribute
                 */
                public void unsetQty()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(QTY$18);
                    }
                }
                
                /**
                 * Gets the "Org_Price" attribute
                 */
                public short getOrgPrice()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ORGPRICE$20);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getShortValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Org_Price" attribute
                 */
                public org.apache.xmlbeans.XmlShort xgetOrgPrice()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(ORGPRICE$20);
                      return target;
                    }
                }
                
                /**
                 * True if has "Org_Price" attribute
                 */
                public boolean isSetOrgPrice()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(ORGPRICE$20) != null;
                    }
                }
                
                /**
                 * Sets the "Org_Price" attribute
                 */
                public void setOrgPrice(short orgPrice)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ORGPRICE$20);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ORGPRICE$20);
                      }
                      target.setShortValue(orgPrice);
                    }
                }
                
                /**
                 * Sets (as xml) the "Org_Price" attribute
                 */
                public void xsetOrgPrice(org.apache.xmlbeans.XmlShort orgPrice)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(ORGPRICE$20);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlShort)get_store().add_attribute_user(ORGPRICE$20);
                      }
                      target.set(orgPrice);
                    }
                }
                
                /**
                 * Unsets the "Org_Price" attribute
                 */
                public void unsetOrgPrice()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(ORGPRICE$20);
                    }
                }
                
                /**
                 * Gets the "Unit_Price" attribute
                 */
                public short getUnitPrice()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(UNITPRICE$22);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getShortValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Unit_Price" attribute
                 */
                public org.apache.xmlbeans.XmlShort xgetUnitPrice()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(UNITPRICE$22);
                      return target;
                    }
                }
                
                /**
                 * True if has "Unit_Price" attribute
                 */
                public boolean isSetUnitPrice()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(UNITPRICE$22) != null;
                    }
                }
                
                /**
                 * Sets the "Unit_Price" attribute
                 */
                public void setUnitPrice(short unitPrice)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(UNITPRICE$22);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(UNITPRICE$22);
                      }
                      target.setShortValue(unitPrice);
                    }
                }
                
                /**
                 * Sets (as xml) the "Unit_Price" attribute
                 */
                public void xsetUnitPrice(org.apache.xmlbeans.XmlShort unitPrice)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(UNITPRICE$22);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlShort)get_store().add_attribute_user(UNITPRICE$22);
                      }
                      target.set(unitPrice);
                    }
                }
                
                /**
                 * Unsets the "Unit_Price" attribute
                 */
                public void unsetUnitPrice()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(UNITPRICE$22);
                    }
                }
                
                /**
                 * Gets the "Net_Price" attribute
                 */
                public short getNetPrice()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NETPRICE$24);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getShortValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Net_Price" attribute
                 */
                public org.apache.xmlbeans.XmlShort xgetNetPrice()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(NETPRICE$24);
                      return target;
                    }
                }
                
                /**
                 * True if has "Net_Price" attribute
                 */
                public boolean isSetNetPrice()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(NETPRICE$24) != null;
                    }
                }
                
                /**
                 * Sets the "Net_Price" attribute
                 */
                public void setNetPrice(short netPrice)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NETPRICE$24);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NETPRICE$24);
                      }
                      target.setShortValue(netPrice);
                    }
                }
                
                /**
                 * Sets (as xml) the "Net_Price" attribute
                 */
                public void xsetNetPrice(org.apache.xmlbeans.XmlShort netPrice)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(NETPRICE$24);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlShort)get_store().add_attribute_user(NETPRICE$24);
                      }
                      target.set(netPrice);
                    }
                }
                
                /**
                 * Unsets the "Net_Price" attribute
                 */
                public void unsetNetPrice()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(NETPRICE$24);
                    }
                }
                
                /**
                 * Gets the "Org_Amount" attribute
                 */
                public short getOrgAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ORGAMOUNT$26);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getShortValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Org_Amount" attribute
                 */
                public org.apache.xmlbeans.XmlShort xgetOrgAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(ORGAMOUNT$26);
                      return target;
                    }
                }
                
                /**
                 * True if has "Org_Amount" attribute
                 */
                public boolean isSetOrgAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(ORGAMOUNT$26) != null;
                    }
                }
                
                /**
                 * Sets the "Org_Amount" attribute
                 */
                public void setOrgAmount(short orgAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ORGAMOUNT$26);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ORGAMOUNT$26);
                      }
                      target.setShortValue(orgAmount);
                    }
                }
                
                /**
                 * Sets (as xml) the "Org_Amount" attribute
                 */
                public void xsetOrgAmount(org.apache.xmlbeans.XmlShort orgAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(ORGAMOUNT$26);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlShort)get_store().add_attribute_user(ORGAMOUNT$26);
                      }
                      target.set(orgAmount);
                    }
                }
                
                /**
                 * Unsets the "Org_Amount" attribute
                 */
                public void unsetOrgAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(ORGAMOUNT$26);
                    }
                }
                
                /**
                 * Gets the "Unit_Amount" attribute
                 */
                public short getUnitAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(UNITAMOUNT$28);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getShortValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Unit_Amount" attribute
                 */
                public org.apache.xmlbeans.XmlShort xgetUnitAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(UNITAMOUNT$28);
                      return target;
                    }
                }
                
                /**
                 * True if has "Unit_Amount" attribute
                 */
                public boolean isSetUnitAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(UNITAMOUNT$28) != null;
                    }
                }
                
                /**
                 * Sets the "Unit_Amount" attribute
                 */
                public void setUnitAmount(short unitAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(UNITAMOUNT$28);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(UNITAMOUNT$28);
                      }
                      target.setShortValue(unitAmount);
                    }
                }
                
                /**
                 * Sets (as xml) the "Unit_Amount" attribute
                 */
                public void xsetUnitAmount(org.apache.xmlbeans.XmlShort unitAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(UNITAMOUNT$28);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlShort)get_store().add_attribute_user(UNITAMOUNT$28);
                      }
                      target.set(unitAmount);
                    }
                }
                
                /**
                 * Unsets the "Unit_Amount" attribute
                 */
                public void unsetUnitAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(UNITAMOUNT$28);
                    }
                }
                
                /**
                 * Gets the "Net_Amount" attribute
                 */
                public short getNetAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NETAMOUNT$30);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getShortValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Net_Amount" attribute
                 */
                public org.apache.xmlbeans.XmlShort xgetNetAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(NETAMOUNT$30);
                      return target;
                    }
                }
                
                /**
                 * True if has "Net_Amount" attribute
                 */
                public boolean isSetNetAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(NETAMOUNT$30) != null;
                    }
                }
                
                /**
                 * Sets the "Net_Amount" attribute
                 */
                public void setNetAmount(short netAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NETAMOUNT$30);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NETAMOUNT$30);
                      }
                      target.setShortValue(netAmount);
                    }
                }
                
                /**
                 * Sets (as xml) the "Net_Amount" attribute
                 */
                public void xsetNetAmount(org.apache.xmlbeans.XmlShort netAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(NETAMOUNT$30);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlShort)get_store().add_attribute_user(NETAMOUNT$30);
                      }
                      target.set(netAmount);
                    }
                }
                
                /**
                 * Unsets the "Net_Amount" attribute
                 */
                public void unsetNetAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(NETAMOUNT$30);
                    }
                }
                
                /**
                 * Gets the "Total_Qty" attribute
                 */
                public short getTotalQty()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TOTALQTY$32);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getShortValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Total_Qty" attribute
                 */
                public org.apache.xmlbeans.XmlShort xgetTotalQty()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(TOTALQTY$32);
                      return target;
                    }
                }
                
                /**
                 * True if has "Total_Qty" attribute
                 */
                public boolean isSetTotalQty()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(TOTALQTY$32) != null;
                    }
                }
                
                /**
                 * Sets the "Total_Qty" attribute
                 */
                public void setTotalQty(short totalQty)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TOTALQTY$32);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TOTALQTY$32);
                      }
                      target.setShortValue(totalQty);
                    }
                }
                
                /**
                 * Sets (as xml) the "Total_Qty" attribute
                 */
                public void xsetTotalQty(org.apache.xmlbeans.XmlShort totalQty)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(TOTALQTY$32);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlShort)get_store().add_attribute_user(TOTALQTY$32);
                      }
                      target.set(totalQty);
                    }
                }
                
                /**
                 * Unsets the "Total_Qty" attribute
                 */
                public void unsetTotalQty()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(TOTALQTY$32);
                    }
                }
                
                /**
                 * Gets the "Discount_Price" attribute
                 */
                public short getDiscountPrice()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DISCOUNTPRICE$34);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getShortValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Discount_Price" attribute
                 */
                public org.apache.xmlbeans.XmlShort xgetDiscountPrice()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(DISCOUNTPRICE$34);
                      return target;
                    }
                }
                
                /**
                 * True if has "Discount_Price" attribute
                 */
                public boolean isSetDiscountPrice()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(DISCOUNTPRICE$34) != null;
                    }
                }
                
                /**
                 * Sets the "Discount_Price" attribute
                 */
                public void setDiscountPrice(short discountPrice)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DISCOUNTPRICE$34);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DISCOUNTPRICE$34);
                      }
                      target.setShortValue(discountPrice);
                    }
                }
                
                /**
                 * Sets (as xml) the "Discount_Price" attribute
                 */
                public void xsetDiscountPrice(org.apache.xmlbeans.XmlShort discountPrice)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(DISCOUNTPRICE$34);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlShort)get_store().add_attribute_user(DISCOUNTPRICE$34);
                      }
                      target.set(discountPrice);
                    }
                }
                
                /**
                 * Unsets the "Discount_Price" attribute
                 */
                public void unsetDiscountPrice()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(DISCOUNTPRICE$34);
                    }
                }
                
                /**
                 * Gets the "ID_ReasonCode" attribute
                 */
                public java.lang.String getIDReasonCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDREASONCODE$36);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "ID_ReasonCode" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetIDReasonCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(IDREASONCODE$36);
                      return target;
                    }
                }
                
                /**
                 * True if has "ID_ReasonCode" attribute
                 */
                public boolean isSetIDReasonCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(IDREASONCODE$36) != null;
                    }
                }
                
                /**
                 * Sets the "ID_ReasonCode" attribute
                 */
                public void setIDReasonCode(java.lang.String idReasonCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IDREASONCODE$36);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IDREASONCODE$36);
                      }
                      target.setStringValue(idReasonCode);
                    }
                }
                
                /**
                 * Sets (as xml) the "ID_ReasonCode" attribute
                 */
                public void xsetIDReasonCode(org.apache.xmlbeans.XmlString idReasonCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(IDREASONCODE$36);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(IDREASONCODE$36);
                      }
                      target.set(idReasonCode);
                    }
                }
                
                /**
                 * Unsets the "ID_ReasonCode" attribute
                 */
                public void unsetIDReasonCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(IDREASONCODE$36);
                    }
                }
                
                /**
                 * Gets the "Additional1" attribute
                 */
                public java.lang.String getAdditional1()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ADDITIONAL1$38);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Additional1" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetAdditional1()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ADDITIONAL1$38);
                      return target;
                    }
                }
                
                /**
                 * True if has "Additional1" attribute
                 */
                public boolean isSetAdditional1()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(ADDITIONAL1$38) != null;
                    }
                }
                
                /**
                 * Sets the "Additional1" attribute
                 */
                public void setAdditional1(java.lang.String additional1)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ADDITIONAL1$38);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ADDITIONAL1$38);
                      }
                      target.setStringValue(additional1);
                    }
                }
                
                /**
                 * Sets (as xml) the "Additional1" attribute
                 */
                public void xsetAdditional1(org.apache.xmlbeans.XmlString additional1)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ADDITIONAL1$38);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(ADDITIONAL1$38);
                      }
                      target.set(additional1);
                    }
                }
                
                /**
                 * Unsets the "Additional1" attribute
                 */
                public void unsetAdditional1()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(ADDITIONAL1$38);
                    }
                }
                
                /**
                 * Gets the "Additional2" attribute
                 */
                public java.lang.String getAdditional2()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ADDITIONAL2$40);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Additional2" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetAdditional2()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ADDITIONAL2$40);
                      return target;
                    }
                }
                
                /**
                 * True if has "Additional2" attribute
                 */
                public boolean isSetAdditional2()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(ADDITIONAL2$40) != null;
                    }
                }
                
                /**
                 * Sets the "Additional2" attribute
                 */
                public void setAdditional2(java.lang.String additional2)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ADDITIONAL2$40);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ADDITIONAL2$40);
                      }
                      target.setStringValue(additional2);
                    }
                }
                
                /**
                 * Sets (as xml) the "Additional2" attribute
                 */
                public void xsetAdditional2(org.apache.xmlbeans.XmlString additional2)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ADDITIONAL2$40);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(ADDITIONAL2$40);
                      }
                      target.set(additional2);
                    }
                }
                
                /**
                 * Unsets the "Additional2" attribute
                 */
                public void unsetAdditional2()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(ADDITIONAL2$40);
                    }
                }
                
                /**
                 * Gets the "Additional3" attribute
                 */
                public java.lang.String getAdditional3()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ADDITIONAL3$42);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Additional3" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetAdditional3()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ADDITIONAL3$42);
                      return target;
                    }
                }
                
                /**
                 * True if has "Additional3" attribute
                 */
                public boolean isSetAdditional3()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(ADDITIONAL3$42) != null;
                    }
                }
                
                /**
                 * Sets the "Additional3" attribute
                 */
                public void setAdditional3(java.lang.String additional3)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ADDITIONAL3$42);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ADDITIONAL3$42);
                      }
                      target.setStringValue(additional3);
                    }
                }
                
                /**
                 * Sets (as xml) the "Additional3" attribute
                 */
                public void xsetAdditional3(org.apache.xmlbeans.XmlString additional3)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ADDITIONAL3$42);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(ADDITIONAL3$42);
                      }
                      target.set(additional3);
                    }
                }
                
                /**
                 * Unsets the "Additional3" attribute
                 */
                public void unsetAdditional3()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(ADDITIONAL3$42);
                    }
                }
                
                /**
                 * Gets the "ChgClass" attribute
                 */
                public java.lang.String getChgClass()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CHGCLASS$44);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "ChgClass" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetChgClass()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CHGCLASS$44);
                      return target;
                    }
                }
                
                /**
                 * True if has "ChgClass" attribute
                 */
                public boolean isSetChgClass()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CHGCLASS$44) != null;
                    }
                }
                
                /**
                 * Sets the "ChgClass" attribute
                 */
                public void setChgClass(java.lang.String chgClass)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CHGCLASS$44);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CHGCLASS$44);
                      }
                      target.setStringValue(chgClass);
                    }
                }
                
                /**
                 * Sets (as xml) the "ChgClass" attribute
                 */
                public void xsetChgClass(org.apache.xmlbeans.XmlString chgClass)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CHGCLASS$44);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CHGCLASS$44);
                      }
                      target.set(chgClass);
                    }
                }
                
                /**
                 * Unsets the "ChgClass" attribute
                 */
                public void unsetChgClass()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CHGCLASS$44);
                    }
                }
                
                /**
                 * Gets the "IsBOM" attribute
                 */
                public java.lang.String getIsBOM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ISBOM$46);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "IsBOM" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetIsBOM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ISBOM$46);
                      return target;
                    }
                }
                
                /**
                 * True if has "IsBOM" attribute
                 */
                public boolean isSetIsBOM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(ISBOM$46) != null;
                    }
                }
                
                /**
                 * Sets the "IsBOM" attribute
                 */
                public void setIsBOM(java.lang.String isBOM)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ISBOM$46);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ISBOM$46);
                      }
                      target.setStringValue(isBOM);
                    }
                }
                
                /**
                 * Sets (as xml) the "IsBOM" attribute
                 */
                public void xsetIsBOM(org.apache.xmlbeans.XmlString isBOM)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ISBOM$46);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(ISBOM$46);
                      }
                      target.set(isBOM);
                    }
                }
                
                /**
                 * Unsets the "IsBOM" attribute
                 */
                public void unsetIsBOM()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(ISBOM$46);
                    }
                }
                
                /**
                 * Gets the "MNPNeed" attribute
                 */
                public java.lang.String getMNPNeed()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MNPNEED$48);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "MNPNeed" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetMNPNeed()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(MNPNEED$48);
                      return target;
                    }
                }
                
                /**
                 * True if has "MNPNeed" attribute
                 */
                public boolean isSetMNPNeed()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(MNPNEED$48) != null;
                    }
                }
                
                /**
                 * Sets the "MNPNeed" attribute
                 */
                public void setMNPNeed(java.lang.String mnpNeed)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MNPNEED$48);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(MNPNEED$48);
                      }
                      target.setStringValue(mnpNeed);
                    }
                }
                
                /**
                 * Sets (as xml) the "MNPNeed" attribute
                 */
                public void xsetMNPNeed(org.apache.xmlbeans.XmlString mnpNeed)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(MNPNEED$48);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(MNPNEED$48);
                      }
                      target.set(mnpNeed);
                    }
                }
                
                /**
                 * Unsets the "MNPNeed" attribute
                 */
                public void unsetMNPNeed()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(MNPNEED$48);
                    }
                }
                
                /**
                 * Gets the "CNPNeed" attribute
                 */
                public java.lang.String getCNPNeed()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CNPNEED$50);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "CNPNeed" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCNPNeed()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CNPNEED$50);
                      return target;
                    }
                }
                
                /**
                 * True if has "CNPNeed" attribute
                 */
                public boolean isSetCNPNeed()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CNPNEED$50) != null;
                    }
                }
                
                /**
                 * Sets the "CNPNeed" attribute
                 */
                public void setCNPNeed(java.lang.String cnpNeed)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CNPNEED$50);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CNPNEED$50);
                      }
                      target.setStringValue(cnpNeed);
                    }
                }
                
                /**
                 * Sets (as xml) the "CNPNeed" attribute
                 */
                public void xsetCNPNeed(org.apache.xmlbeans.XmlString cnpNeed)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CNPNEED$50);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CNPNEED$50);
                      }
                      target.set(cnpNeed);
                    }
                }
                
                /**
                 * Unsets the "CNPNeed" attribute
                 */
                public void unsetCNPNeed()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CNPNEED$50);
                    }
                }
                
                /**
                 * Gets the "ChargeType" attribute
                 */
                public java.lang.String getChargeType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CHARGETYPE$52);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "ChargeType" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetChargeType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CHARGETYPE$52);
                      return target;
                    }
                }
                
                /**
                 * True if has "ChargeType" attribute
                 */
                public boolean isSetChargeType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CHARGETYPE$52) != null;
                    }
                }
                
                /**
                 * Sets the "ChargeType" attribute
                 */
                public void setChargeType(java.lang.String chargeType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CHARGETYPE$52);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CHARGETYPE$52);
                      }
                      target.setStringValue(chargeType);
                    }
                }
                
                /**
                 * Sets (as xml) the "ChargeType" attribute
                 */
                public void xsetChargeType(org.apache.xmlbeans.XmlString chargeType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CHARGETYPE$52);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CHARGETYPE$52);
                      }
                      target.set(chargeType);
                    }
                }
                
                /**
                 * Unsets the "ChargeType" attribute
                 */
                public void unsetChargeType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CHARGETYPE$52);
                    }
                }
                
                /**
                 * Gets the "SN_Num" attribute
                 */
                public java.lang.String getSNNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SNNUM$54);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "SN_Num" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetSNNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SNNUM$54);
                      return target;
                    }
                }
                
                /**
                 * True if has "SN_Num" attribute
                 */
                public boolean isSetSNNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(SNNUM$54) != null;
                    }
                }
                
                /**
                 * Sets the "SN_Num" attribute
                 */
                public void setSNNum(java.lang.String snNum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SNNUM$54);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SNNUM$54);
                      }
                      target.setStringValue(snNum);
                    }
                }
                
                /**
                 * Sets (as xml) the "SN_Num" attribute
                 */
                public void xsetSNNum(org.apache.xmlbeans.XmlString snNum)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SNNUM$54);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SNNUM$54);
                      }
                      target.set(snNum);
                    }
                }
                
                /**
                 * Unsets the "SN_Num" attribute
                 */
                public void unsetSNNum()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(SNNUM$54);
                    }
                }
                
                /**
                 * Gets the "StockType" attribute
                 */
                public java.lang.String getStockType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STOCKTYPE$56);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "StockType" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetStockType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STOCKTYPE$56);
                      return target;
                    }
                }
                
                /**
                 * True if has "StockType" attribute
                 */
                public boolean isSetStockType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(STOCKTYPE$56) != null;
                    }
                }
                
                /**
                 * Sets the "StockType" attribute
                 */
                public void setStockType(java.lang.String stockType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STOCKTYPE$56);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(STOCKTYPE$56);
                      }
                      target.setStringValue(stockType);
                    }
                }
                
                /**
                 * Sets (as xml) the "StockType" attribute
                 */
                public void xsetStockType(org.apache.xmlbeans.XmlString stockType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STOCKTYPE$56);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(STOCKTYPE$56);
                      }
                      target.set(stockType);
                    }
                }
                
                /**
                 * Unsets the "StockType" attribute
                 */
                public void unsetStockType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(STOCKTYPE$56);
                    }
                }
                
                /**
                 * Gets the "Collected" attribute
                 */
                public java.lang.String getCollected()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(COLLECTED$58);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Collected" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetCollected()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(COLLECTED$58);
                      return target;
                    }
                }
                
                /**
                 * True if has "Collected" attribute
                 */
                public boolean isSetCollected()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(COLLECTED$58) != null;
                    }
                }
                
                /**
                 * Sets the "Collected" attribute
                 */
                public void setCollected(java.lang.String collected)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(COLLECTED$58);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(COLLECTED$58);
                      }
                      target.setStringValue(collected);
                    }
                }
                
                /**
                 * Sets (as xml) the "Collected" attribute
                 */
                public void xsetCollected(org.apache.xmlbeans.XmlString collected)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(COLLECTED$58);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(COLLECTED$58);
                      }
                      target.set(collected);
                    }
                }
                
                /**
                 * Unsets the "Collected" attribute
                 */
                public void unsetCollected()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(COLLECTED$58);
                    }
                }
                
                /**
                 * Gets the "Pickup_Location" attribute
                 */
                public java.lang.String getPickupLocation()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PICKUPLOCATION$60);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pickup_Location" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetPickupLocation()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PICKUPLOCATION$60);
                      return target;
                    }
                }
                
                /**
                 * True if has "Pickup_Location" attribute
                 */
                public boolean isSetPickupLocation()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(PICKUPLOCATION$60) != null;
                    }
                }
                
                /**
                 * Sets the "Pickup_Location" attribute
                 */
                public void setPickupLocation(java.lang.String pickupLocation)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PICKUPLOCATION$60);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PICKUPLOCATION$60);
                      }
                      target.setStringValue(pickupLocation);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pickup_Location" attribute
                 */
                public void xsetPickupLocation(org.apache.xmlbeans.XmlString pickupLocation)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PICKUPLOCATION$60);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PICKUPLOCATION$60);
                      }
                      target.set(pickupLocation);
                    }
                }
                
                /**
                 * Unsets the "Pickup_Location" attribute
                 */
                public void unsetPickupLocation()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(PICKUPLOCATION$60);
                    }
                }
                
                /**
                 * Gets the "DeptCode" attribute
                 */
                public java.lang.String getDeptCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DEPTCODE$62);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "DeptCode" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetDeptCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DEPTCODE$62);
                      return target;
                    }
                }
                
                /**
                 * True if has "DeptCode" attribute
                 */
                public boolean isSetDeptCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(DEPTCODE$62) != null;
                    }
                }
                
                /**
                 * Sets the "DeptCode" attribute
                 */
                public void setDeptCode(java.lang.String deptCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DEPTCODE$62);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DEPTCODE$62);
                      }
                      target.setStringValue(deptCode);
                    }
                }
                
                /**
                 * Sets (as xml) the "DeptCode" attribute
                 */
                public void xsetDeptCode(org.apache.xmlbeans.XmlString deptCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DEPTCODE$62);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(DEPTCODE$62);
                      }
                      target.set(deptCode);
                    }
                }
                
                /**
                 * Unsets the "DeptCode" attribute
                 */
                public void unsetDeptCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(DEPTCODE$62);
                    }
                }
                
                /**
                 * Gets the "DISC_PRICE" attribute
                 */
                public short getDISCPRICE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DISCPRICE$64);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getShortValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "DISC_PRICE" attribute
                 */
                public org.apache.xmlbeans.XmlShort xgetDISCPRICE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(DISCPRICE$64);
                      return target;
                    }
                }
                
                /**
                 * True if has "DISC_PRICE" attribute
                 */
                public boolean isSetDISCPRICE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(DISCPRICE$64) != null;
                    }
                }
                
                /**
                 * Sets the "DISC_PRICE" attribute
                 */
                public void setDISCPRICE(short discprice)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DISCPRICE$64);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DISCPRICE$64);
                      }
                      target.setShortValue(discprice);
                    }
                }
                
                /**
                 * Sets (as xml) the "DISC_PRICE" attribute
                 */
                public void xsetDISCPRICE(org.apache.xmlbeans.XmlShort discprice)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(DISCPRICE$64);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlShort)get_store().add_attribute_user(DISCPRICE$64);
                      }
                      target.set(discprice);
                    }
                }
                
                /**
                 * Unsets the "DISC_PRICE" attribute
                 */
                public void unsetDISCPRICE()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(DISCPRICE$64);
                    }
                }
                
                /**
                 * Gets the "DISC_AMOUNT" attribute
                 */
                public short getDISCAMOUNT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DISCAMOUNT$66);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getShortValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "DISC_AMOUNT" attribute
                 */
                public org.apache.xmlbeans.XmlShort xgetDISCAMOUNT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(DISCAMOUNT$66);
                      return target;
                    }
                }
                
                /**
                 * True if has "DISC_AMOUNT" attribute
                 */
                public boolean isSetDISCAMOUNT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(DISCAMOUNT$66) != null;
                    }
                }
                
                /**
                 * Sets the "DISC_AMOUNT" attribute
                 */
                public void setDISCAMOUNT(short discamount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DISCAMOUNT$66);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DISCAMOUNT$66);
                      }
                      target.setShortValue(discamount);
                    }
                }
                
                /**
                 * Sets (as xml) the "DISC_AMOUNT" attribute
                 */
                public void xsetDISCAMOUNT(org.apache.xmlbeans.XmlShort discamount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(DISCAMOUNT$66);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlShort)get_store().add_attribute_user(DISCAMOUNT$66);
                      }
                      target.set(discamount);
                    }
                }
                
                /**
                 * Unsets the "DISC_AMOUNT" attribute
                 */
                public void unsetDISCAMOUNT()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(DISCAMOUNT$66);
                    }
                }
                
                /**
                 * Gets the "IMEI" attribute
                 */
                public java.lang.String getIMEI()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMEI$68);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "IMEI" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetIMEI()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(IMEI$68);
                      return target;
                    }
                }
                
                /**
                 * True if has "IMEI" attribute
                 */
                public boolean isSetIMEI()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(IMEI$68) != null;
                    }
                }
                
                /**
                 * Sets the "IMEI" attribute
                 */
                public void setIMEI(java.lang.String imei)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(IMEI$68);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(IMEI$68);
                      }
                      target.setStringValue(imei);
                    }
                }
                
                /**
                 * Sets (as xml) the "IMEI" attribute
                 */
                public void xsetIMEI(org.apache.xmlbeans.XmlString imei)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(IMEI$68);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(IMEI$68);
                      }
                      target.set(imei);
                    }
                }
                
                /**
                 * Unsets the "IMEI" attribute
                 */
                public void unsetIMEI()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(IMEI$68);
                    }
                }
                
                /**
                 * Gets the "Add_Charge" attribute
                 */
                public short getAddCharge()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ADDCHARGE$70);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getShortValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Add_Charge" attribute
                 */
                public org.apache.xmlbeans.XmlShort xgetAddCharge()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(ADDCHARGE$70);
                      return target;
                    }
                }
                
                /**
                 * True if has "Add_Charge" attribute
                 */
                public boolean isSetAddCharge()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(ADDCHARGE$70) != null;
                    }
                }
                
                /**
                 * Sets the "Add_Charge" attribute
                 */
                public void setAddCharge(short addCharge)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ADDCHARGE$70);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ADDCHARGE$70);
                      }
                      target.setShortValue(addCharge);
                    }
                }
                
                /**
                 * Sets (as xml) the "Add_Charge" attribute
                 */
                public void xsetAddCharge(org.apache.xmlbeans.XmlShort addCharge)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(ADDCHARGE$70);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlShort)get_store().add_attribute_user(ADDCHARGE$70);
                      }
                      target.set(addCharge);
                    }
                }
                
                /**
                 * Unsets the "Add_Charge" attribute
                 */
                public void unsetAddCharge()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(ADDCHARGE$70);
                    }
                }
                
                /**
                 * Gets the "Disc_Type" attribute
                 */
                public java.lang.String getDiscType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DISCTYPE$72);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Disc_Type" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetDiscType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DISCTYPE$72);
                      return target;
                    }
                }
                
                /**
                 * True if has "Disc_Type" attribute
                 */
                public boolean isSetDiscType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(DISCTYPE$72) != null;
                    }
                }
                
                /**
                 * Sets the "Disc_Type" attribute
                 */
                public void setDiscType(java.lang.String discType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DISCTYPE$72);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DISCTYPE$72);
                      }
                      target.setStringValue(discType);
                    }
                }
                
                /**
                 * Sets (as xml) the "Disc_Type" attribute
                 */
                public void xsetDiscType(org.apache.xmlbeans.XmlString discType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DISCTYPE$72);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(DISCTYPE$72);
                      }
                      target.set(discType);
                    }
                }
                
                /**
                 * Unsets the "Disc_Type" attribute
                 */
                public void unsetDiscType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(DISCTYPE$72);
                    }
                }
                
                /**
                 * Gets the "Is_coupon_sku" attribute
                 */
                public java.lang.String getIsCouponSku()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ISCOUPONSKU$74);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Is_coupon_sku" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetIsCouponSku()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ISCOUPONSKU$74);
                      return target;
                    }
                }
                
                /**
                 * True if has "Is_coupon_sku" attribute
                 */
                public boolean isSetIsCouponSku()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(ISCOUPONSKU$74) != null;
                    }
                }
                
                /**
                 * Sets the "Is_coupon_sku" attribute
                 */
                public void setIsCouponSku(java.lang.String isCouponSku)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ISCOUPONSKU$74);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ISCOUPONSKU$74);
                      }
                      target.setStringValue(isCouponSku);
                    }
                }
                
                /**
                 * Sets (as xml) the "Is_coupon_sku" attribute
                 */
                public void xsetIsCouponSku(org.apache.xmlbeans.XmlString isCouponSku)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ISCOUPONSKU$74);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(ISCOUPONSKU$74);
                      }
                      target.set(isCouponSku);
                    }
                }
                
                /**
                 * Unsets the "Is_coupon_sku" attribute
                 */
                public void unsetIsCouponSku()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(ISCOUPONSKU$74);
                    }
                }
                
                /**
                 * Gets the "Is_buyback_sku" attribute
                 */
                public java.lang.String getIsBuybackSku()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ISBUYBACKSKU$76);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Is_buyback_sku" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetIsBuybackSku()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ISBUYBACKSKU$76);
                      return target;
                    }
                }
                
                /**
                 * True if has "Is_buyback_sku" attribute
                 */
                public boolean isSetIsBuybackSku()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(ISBUYBACKSKU$76) != null;
                    }
                }
                
                /**
                 * Sets the "Is_buyback_sku" attribute
                 */
                public void setIsBuybackSku(java.lang.String isBuybackSku)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ISBUYBACKSKU$76);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ISBUYBACKSKU$76);
                      }
                      target.setStringValue(isBuybackSku);
                    }
                }
                
                /**
                 * Sets (as xml) the "Is_buyback_sku" attribute
                 */
                public void xsetIsBuybackSku(org.apache.xmlbeans.XmlString isBuybackSku)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ISBUYBACKSKU$76);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(ISBUYBACKSKU$76);
                      }
                      target.set(isBuybackSku);
                    }
                }
                
                /**
                 * Unsets the "Is_buyback_sku" attribute
                 */
                public void unsetIsBuybackSku()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(ISBUYBACKSKU$76);
                    }
                }
                
                /**
                 * Gets the "Pickup_Store_Type" attribute
                 */
                public java.lang.String getPickupStoreType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PICKUPSTORETYPE$78);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pickup_Store_Type" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetPickupStoreType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PICKUPSTORETYPE$78);
                      return target;
                    }
                }
                
                /**
                 * True if has "Pickup_Store_Type" attribute
                 */
                public boolean isSetPickupStoreType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(PICKUPSTORETYPE$78) != null;
                    }
                }
                
                /**
                 * Sets the "Pickup_Store_Type" attribute
                 */
                public void setPickupStoreType(java.lang.String pickupStoreType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PICKUPSTORETYPE$78);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PICKUPSTORETYPE$78);
                      }
                      target.setStringValue(pickupStoreType);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pickup_Store_Type" attribute
                 */
                public void xsetPickupStoreType(org.apache.xmlbeans.XmlString pickupStoreType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PICKUPSTORETYPE$78);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PICKUPSTORETYPE$78);
                      }
                      target.set(pickupStoreType);
                    }
                }
                
                /**
                 * Unsets the "Pickup_Store_Type" attribute
                 */
                public void unsetPickupStoreType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(PICKUPSTORETYPE$78);
                    }
                }
                
                /**
                 * Gets the "Pickup_Store_Name" attribute
                 */
                public java.lang.String getPickupStoreName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PICKUPSTORENAME$80);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pickup_Store_Name" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetPickupStoreName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PICKUPSTORENAME$80);
                      return target;
                    }
                }
                
                /**
                 * True if has "Pickup_Store_Name" attribute
                 */
                public boolean isSetPickupStoreName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(PICKUPSTORENAME$80) != null;
                    }
                }
                
                /**
                 * Sets the "Pickup_Store_Name" attribute
                 */
                public void setPickupStoreName(java.lang.String pickupStoreName)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PICKUPSTORENAME$80);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PICKUPSTORENAME$80);
                      }
                      target.setStringValue(pickupStoreName);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pickup_Store_Name" attribute
                 */
                public void xsetPickupStoreName(org.apache.xmlbeans.XmlString pickupStoreName)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PICKUPSTORENAME$80);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PICKUPSTORENAME$80);
                      }
                      target.set(pickupStoreName);
                    }
                }
                
                /**
                 * Unsets the "Pickup_Store_Name" attribute
                 */
                public void unsetPickupStoreName()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(PICKUPSTORENAME$80);
                    }
                }
                
                /**
                 * Gets the "Pickup_Store_tel" attribute
                 */
                public java.lang.String getPickupStoreTel()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PICKUPSTORETEL$82);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pickup_Store_tel" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetPickupStoreTel()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PICKUPSTORETEL$82);
                      return target;
                    }
                }
                
                /**
                 * True if has "Pickup_Store_tel" attribute
                 */
                public boolean isSetPickupStoreTel()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(PICKUPSTORETEL$82) != null;
                    }
                }
                
                /**
                 * Sets the "Pickup_Store_tel" attribute
                 */
                public void setPickupStoreTel(java.lang.String pickupStoreTel)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PICKUPSTORETEL$82);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PICKUPSTORETEL$82);
                      }
                      target.setStringValue(pickupStoreTel);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pickup_Store_tel" attribute
                 */
                public void xsetPickupStoreTel(org.apache.xmlbeans.XmlString pickupStoreTel)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PICKUPSTORETEL$82);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PICKUPSTORETEL$82);
                      }
                      target.set(pickupStoreTel);
                    }
                }
                
                /**
                 * Unsets the "Pickup_Store_tel" attribute
                 */
                public void unsetPickupStoreTel()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(PICKUPSTORETEL$82);
                    }
                }
                
                /**
                 * Gets the "Pickup_Store_Begin_Hours" attribute
                 */
                public java.lang.String getPickupStoreBeginHours()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PICKUPSTOREBEGINHOURS$84);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pickup_Store_Begin_Hours" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetPickupStoreBeginHours()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PICKUPSTOREBEGINHOURS$84);
                      return target;
                    }
                }
                
                /**
                 * True if has "Pickup_Store_Begin_Hours" attribute
                 */
                public boolean isSetPickupStoreBeginHours()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(PICKUPSTOREBEGINHOURS$84) != null;
                    }
                }
                
                /**
                 * Sets the "Pickup_Store_Begin_Hours" attribute
                 */
                public void setPickupStoreBeginHours(java.lang.String pickupStoreBeginHours)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PICKUPSTOREBEGINHOURS$84);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PICKUPSTOREBEGINHOURS$84);
                      }
                      target.setStringValue(pickupStoreBeginHours);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pickup_Store_Begin_Hours" attribute
                 */
                public void xsetPickupStoreBeginHours(org.apache.xmlbeans.XmlString pickupStoreBeginHours)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PICKUPSTOREBEGINHOURS$84);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PICKUPSTOREBEGINHOURS$84);
                      }
                      target.set(pickupStoreBeginHours);
                    }
                }
                
                /**
                 * Unsets the "Pickup_Store_Begin_Hours" attribute
                 */
                public void unsetPickupStoreBeginHours()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(PICKUPSTOREBEGINHOURS$84);
                    }
                }
                
                /**
                 * Gets the "Pickup_Store_End_Hours" attribute
                 */
                public java.lang.String getPickupStoreEndHours()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PICKUPSTOREENDHOURS$86);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pickup_Store_End_Hours" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetPickupStoreEndHours()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PICKUPSTOREENDHOURS$86);
                      return target;
                    }
                }
                
                /**
                 * True if has "Pickup_Store_End_Hours" attribute
                 */
                public boolean isSetPickupStoreEndHours()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(PICKUPSTOREENDHOURS$86) != null;
                    }
                }
                
                /**
                 * Sets the "Pickup_Store_End_Hours" attribute
                 */
                public void setPickupStoreEndHours(java.lang.String pickupStoreEndHours)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PICKUPSTOREENDHOURS$86);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PICKUPSTOREENDHOURS$86);
                      }
                      target.setStringValue(pickupStoreEndHours);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pickup_Store_End_Hours" attribute
                 */
                public void xsetPickupStoreEndHours(org.apache.xmlbeans.XmlString pickupStoreEndHours)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PICKUPSTOREENDHOURS$86);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PICKUPSTOREENDHOURS$86);
                      }
                      target.set(pickupStoreEndHours);
                    }
                }
                
                /**
                 * Unsets the "Pickup_Store_End_Hours" attribute
                 */
                public void unsetPickupStoreEndHours()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(PICKUPSTOREENDHOURS$86);
                    }
                }
                
                /**
                 * Gets the "Pickup_Store_Addr_1" attribute
                 */
                public java.lang.String getPickupStoreAddr1()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PICKUPSTOREADDR1$88);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pickup_Store_Addr_1" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetPickupStoreAddr1()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PICKUPSTOREADDR1$88);
                      return target;
                    }
                }
                
                /**
                 * True if has "Pickup_Store_Addr_1" attribute
                 */
                public boolean isSetPickupStoreAddr1()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(PICKUPSTOREADDR1$88) != null;
                    }
                }
                
                /**
                 * Sets the "Pickup_Store_Addr_1" attribute
                 */
                public void setPickupStoreAddr1(java.lang.String pickupStoreAddr1)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PICKUPSTOREADDR1$88);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PICKUPSTOREADDR1$88);
                      }
                      target.setStringValue(pickupStoreAddr1);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pickup_Store_Addr_1" attribute
                 */
                public void xsetPickupStoreAddr1(org.apache.xmlbeans.XmlString pickupStoreAddr1)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PICKUPSTOREADDR1$88);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PICKUPSTOREADDR1$88);
                      }
                      target.set(pickupStoreAddr1);
                    }
                }
                
                /**
                 * Unsets the "Pickup_Store_Addr_1" attribute
                 */
                public void unsetPickupStoreAddr1()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(PICKUPSTOREADDR1$88);
                    }
                }
                
                /**
                 * Gets the "Pickup_Store_Addr_2" attribute
                 */
                public java.lang.String getPickupStoreAddr2()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PICKUPSTOREADDR2$90);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pickup_Store_Addr_2" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetPickupStoreAddr2()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PICKUPSTOREADDR2$90);
                      return target;
                    }
                }
                
                /**
                 * True if has "Pickup_Store_Addr_2" attribute
                 */
                public boolean isSetPickupStoreAddr2()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(PICKUPSTOREADDR2$90) != null;
                    }
                }
                
                /**
                 * Sets the "Pickup_Store_Addr_2" attribute
                 */
                public void setPickupStoreAddr2(java.lang.String pickupStoreAddr2)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PICKUPSTOREADDR2$90);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PICKUPSTOREADDR2$90);
                      }
                      target.setStringValue(pickupStoreAddr2);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pickup_Store_Addr_2" attribute
                 */
                public void xsetPickupStoreAddr2(org.apache.xmlbeans.XmlString pickupStoreAddr2)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PICKUPSTOREADDR2$90);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PICKUPSTOREADDR2$90);
                      }
                      target.set(pickupStoreAddr2);
                    }
                }
                
                /**
                 * Unsets the "Pickup_Store_Addr_2" attribute
                 */
                public void unsetPickupStoreAddr2()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(PICKUPSTOREADDR2$90);
                    }
                }
                
                /**
                 * Gets the "Pickup_Store_Addr_3" attribute
                 */
                public java.lang.String getPickupStoreAddr3()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PICKUPSTOREADDR3$92);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pickup_Store_Addr_3" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetPickupStoreAddr3()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PICKUPSTOREADDR3$92);
                      return target;
                    }
                }
                
                /**
                 * True if has "Pickup_Store_Addr_3" attribute
                 */
                public boolean isSetPickupStoreAddr3()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(PICKUPSTOREADDR3$92) != null;
                    }
                }
                
                /**
                 * Sets the "Pickup_Store_Addr_3" attribute
                 */
                public void setPickupStoreAddr3(java.lang.String pickupStoreAddr3)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PICKUPSTOREADDR3$92);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PICKUPSTOREADDR3$92);
                      }
                      target.setStringValue(pickupStoreAddr3);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pickup_Store_Addr_3" attribute
                 */
                public void xsetPickupStoreAddr3(org.apache.xmlbeans.XmlString pickupStoreAddr3)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PICKUPSTOREADDR3$92);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PICKUPSTOREADDR3$92);
                      }
                      target.set(pickupStoreAddr3);
                    }
                }
                
                /**
                 * Unsets the "Pickup_Store_Addr_3" attribute
                 */
                public void unsetPickupStoreAddr3()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(PICKUPSTOREADDR3$92);
                    }
                }
                
                /**
                 * Gets the "lbl_sno" attribute
                 */
                public java.lang.String getLblSno()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LBLSNO$94);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "lbl_sno" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetLblSno()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(LBLSNO$94);
                      return target;
                    }
                }
                
                /**
                 * True if has "lbl_sno" attribute
                 */
                public boolean isSetLblSno()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(LBLSNO$94) != null;
                    }
                }
                
                /**
                 * Sets the "lbl_sno" attribute
                 */
                public void setLblSno(java.lang.String lblSno)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LBLSNO$94);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(LBLSNO$94);
                      }
                      target.setStringValue(lblSno);
                    }
                }
                
                /**
                 * Sets (as xml) the "lbl_sno" attribute
                 */
                public void xsetLblSno(org.apache.xmlbeans.XmlString lblSno)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(LBLSNO$94);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(LBLSNO$94);
                      }
                      target.set(lblSno);
                    }
                }
                
                /**
                 * Unsets the "lbl_sno" attribute
                 */
                public void unsetLblSno()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(LBLSNO$94);
                    }
                }
                
                /**
                 * Gets the "warranty" attribute
                 */
                public java.lang.String getWarranty()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(WARRANTY$96);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "warranty" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetWarranty()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(WARRANTY$96);
                      return target;
                    }
                }
                
                /**
                 * True if has "warranty" attribute
                 */
                public boolean isSetWarranty()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(WARRANTY$96) != null;
                    }
                }
                
                /**
                 * Sets the "warranty" attribute
                 */
                public void setWarranty(java.lang.String warranty)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(WARRANTY$96);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(WARRANTY$96);
                      }
                      target.setStringValue(warranty);
                    }
                }
                
                /**
                 * Sets (as xml) the "warranty" attribute
                 */
                public void xsetWarranty(org.apache.xmlbeans.XmlString warranty)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(WARRANTY$96);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(WARRANTY$96);
                      }
                      target.set(warranty);
                    }
                }
                
                /**
                 * Unsets the "warranty" attribute
                 */
                public void unsetWarranty()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(WARRANTY$96);
                    }
                }
                
                /**
                 * Gets the "Fault_Code" attribute
                 */
                public java.lang.String getFaultCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FAULTCODE$98);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Fault_Code" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetFaultCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(FAULTCODE$98);
                      return target;
                    }
                }
                
                /**
                 * True if has "Fault_Code" attribute
                 */
                public boolean isSetFaultCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(FAULTCODE$98) != null;
                    }
                }
                
                /**
                 * Sets the "Fault_Code" attribute
                 */
                public void setFaultCode(java.lang.String faultCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FAULTCODE$98);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FAULTCODE$98);
                      }
                      target.setStringValue(faultCode);
                    }
                }
                
                /**
                 * Sets (as xml) the "Fault_Code" attribute
                 */
                public void xsetFaultCode(org.apache.xmlbeans.XmlString faultCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(FAULTCODE$98);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(FAULTCODE$98);
                      }
                      target.set(faultCode);
                    }
                }
                
                /**
                 * Unsets the "Fault_Code" attribute
                 */
                public void unsetFaultCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(FAULTCODE$98);
                    }
                }
                
                /**
                 * Gets the "Fault_Desc" attribute
                 */
                public java.lang.String getFaultDesc()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FAULTDESC$100);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Fault_Desc" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetFaultDesc()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(FAULTDESC$100);
                      return target;
                    }
                }
                
                /**
                 * True if has "Fault_Desc" attribute
                 */
                public boolean isSetFaultDesc()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(FAULTDESC$100) != null;
                    }
                }
                
                /**
                 * Sets the "Fault_Desc" attribute
                 */
                public void setFaultDesc(java.lang.String faultDesc)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FAULTDESC$100);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FAULTDESC$100);
                      }
                      target.setStringValue(faultDesc);
                    }
                }
                
                /**
                 * Sets (as xml) the "Fault_Desc" attribute
                 */
                public void xsetFaultDesc(org.apache.xmlbeans.XmlString faultDesc)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(FAULTDESC$100);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(FAULTDESC$100);
                      }
                      target.set(faultDesc);
                    }
                }
                
                /**
                 * Unsets the "Fault_Desc" attribute
                 */
                public void unsetFaultDesc()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(FAULTDESC$100);
                    }
                }
                
                /**
                 * Gets the "Record_Type" attribute
                 */
                public java.lang.String getRecordType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(RECORDTYPE$102);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Record_Type" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetRecordType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(RECORDTYPE$102);
                      return target;
                    }
                }
                
                /**
                 * True if has "Record_Type" attribute
                 */
                public boolean isSetRecordType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(RECORDTYPE$102) != null;
                    }
                }
                
                /**
                 * Sets the "Record_Type" attribute
                 */
                public void setRecordType(java.lang.String recordType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(RECORDTYPE$102);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(RECORDTYPE$102);
                      }
                      target.setStringValue(recordType);
                    }
                }
                
                /**
                 * Sets (as xml) the "Record_Type" attribute
                 */
                public void xsetRecordType(org.apache.xmlbeans.XmlString recordType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(RECORDTYPE$102);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(RECORDTYPE$102);
                      }
                      target.set(recordType);
                    }
                }
                
                /**
                 * Unsets the "Record_Type" attribute
                 */
                public void unsetRecordType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(RECORDTYPE$102);
                    }
                }
                
                /**
                 * Gets the "ActValue" attribute
                 */
                public short getActValue()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ACTVALUE$104);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getShortValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "ActValue" attribute
                 */
                public org.apache.xmlbeans.XmlShort xgetActValue()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(ACTVALUE$104);
                      return target;
                    }
                }
                
                /**
                 * True if has "ActValue" attribute
                 */
                public boolean isSetActValue()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(ACTVALUE$104) != null;
                    }
                }
                
                /**
                 * Sets the "ActValue" attribute
                 */
                public void setActValue(short actValue)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ACTVALUE$104);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ACTVALUE$104);
                      }
                      target.setShortValue(actValue);
                    }
                }
                
                /**
                 * Sets (as xml) the "ActValue" attribute
                 */
                public void xsetActValue(org.apache.xmlbeans.XmlShort actValue)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(ACTVALUE$104);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlShort)get_store().add_attribute_user(ACTVALUE$104);
                      }
                      target.set(actValue);
                    }
                }
                
                /**
                 * Unsets the "ActValue" attribute
                 */
                public void unsetActValue()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(ACTVALUE$104);
                    }
                }
                
                /**
                 * Gets the "ActAmount" attribute
                 */
                public short getActAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ACTAMOUNT$106);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getShortValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "ActAmount" attribute
                 */
                public org.apache.xmlbeans.XmlShort xgetActAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(ACTAMOUNT$106);
                      return target;
                    }
                }
                
                /**
                 * True if has "ActAmount" attribute
                 */
                public boolean isSetActAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(ACTAMOUNT$106) != null;
                    }
                }
                
                /**
                 * Sets the "ActAmount" attribute
                 */
                public void setActAmount(short actAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ACTAMOUNT$106);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ACTAMOUNT$106);
                      }
                      target.setShortValue(actAmount);
                    }
                }
                
                /**
                 * Sets (as xml) the "ActAmount" attribute
                 */
                public void xsetActAmount(org.apache.xmlbeans.XmlShort actAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(ACTAMOUNT$106);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlShort)get_store().add_attribute_user(ACTAMOUNT$106);
                      }
                      target.set(actAmount);
                    }
                }
                
                /**
                 * Unsets the "ActAmount" attribute
                 */
                public void unsetActAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(ACTAMOUNT$106);
                    }
                }
                
                /**
                 * Gets the "StockStatus" attribute
                 */
                public java.lang.String getStockStatus()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STOCKSTATUS$108);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "StockStatus" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetStockStatus()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STOCKSTATUS$108);
                      return target;
                    }
                }
                
                /**
                 * True if has "StockStatus" attribute
                 */
                public boolean isSetStockStatus()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(STOCKSTATUS$108) != null;
                    }
                }
                
                /**
                 * Sets the "StockStatus" attribute
                 */
                public void setStockStatus(java.lang.String stockStatus)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STOCKSTATUS$108);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(STOCKSTATUS$108);
                      }
                      target.setStringValue(stockStatus);
                    }
                }
                
                /**
                 * Sets (as xml) the "StockStatus" attribute
                 */
                public void xsetStockStatus(org.apache.xmlbeans.XmlString stockStatus)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STOCKSTATUS$108);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(STOCKSTATUS$108);
                      }
                      target.set(stockStatus);
                    }
                }
                
                /**
                 * Unsets the "StockStatus" attribute
                 */
                public void unsetStockStatus()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(STOCKSTATUS$108);
                    }
                }
                
                /**
                 * Gets the "Sub_ID" attribute
                 */
                public java.lang.String getSubID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SUBID$110);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Sub_ID" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetSubID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SUBID$110);
                      return target;
                    }
                }
                
                /**
                 * True if has "Sub_ID" attribute
                 */
                public boolean isSetSubID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(SUBID$110) != null;
                    }
                }
                
                /**
                 * Sets the "Sub_ID" attribute
                 */
                public void setSubID(java.lang.String subID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SUBID$110);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SUBID$110);
                      }
                      target.setStringValue(subID);
                    }
                }
                
                /**
                 * Sets (as xml) the "Sub_ID" attribute
                 */
                public void xsetSubID(org.apache.xmlbeans.XmlString subID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SUBID$110);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SUBID$110);
                      }
                      target.set(subID);
                    }
                }
                
                /**
                 * Unsets the "Sub_ID" attribute
                 */
                public void unsetSubID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(SUBID$110);
                    }
                }
                
                /**
                 * Gets the "Pickup_Staff" attribute
                 */
                public java.lang.String getPickupStaff()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PICKUPSTAFF$112);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pickup_Staff" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetPickupStaff()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PICKUPSTAFF$112);
                      return target;
                    }
                }
                
                /**
                 * True if has "Pickup_Staff" attribute
                 */
                public boolean isSetPickupStaff()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(PICKUPSTAFF$112) != null;
                    }
                }
                
                /**
                 * Sets the "Pickup_Staff" attribute
                 */
                public void setPickupStaff(java.lang.String pickupStaff)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PICKUPSTAFF$112);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PICKUPSTAFF$112);
                      }
                      target.setStringValue(pickupStaff);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pickup_Staff" attribute
                 */
                public void xsetPickupStaff(org.apache.xmlbeans.XmlString pickupStaff)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PICKUPSTAFF$112);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PICKUPSTAFF$112);
                      }
                      target.set(pickupStaff);
                    }
                }
                
                /**
                 * Unsets the "Pickup_Staff" attribute
                 */
                public void unsetPickupStaff()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(PICKUPSTAFF$112);
                    }
                }
                
                /**
                 * Gets the "Pickup_Date" attribute
                 */
                public java.lang.String getPickupDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PICKUPDATE$114);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pickup_Date" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetPickupDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PICKUPDATE$114);
                      return target;
                    }
                }
                
                /**
                 * True if has "Pickup_Date" attribute
                 */
                public boolean isSetPickupDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(PICKUPDATE$114) != null;
                    }
                }
                
                /**
                 * Sets the "Pickup_Date" attribute
                 */
                public void setPickupDate(java.lang.String pickupDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PICKUPDATE$114);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PICKUPDATE$114);
                      }
                      target.setStringValue(pickupDate);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pickup_Date" attribute
                 */
                public void xsetPickupDate(org.apache.xmlbeans.XmlString pickupDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PICKUPDATE$114);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PICKUPDATE$114);
                      }
                      target.set(pickupDate);
                    }
                }
                
                /**
                 * Unsets the "Pickup_Date" attribute
                 */
                public void unsetPickupDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(PICKUPDATE$114);
                    }
                }
            }
        }
    }
}
