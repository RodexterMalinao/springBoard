/*
 * An XML document type.
 * Localname: sales_com
 * Namespace: 
 * Java type: noNamespace.SalesComDocument
 *
 * Automatically generated - do not modify.
 */
package noNamespace.impl;
/**
 * A document containing one sales_com(@) element.
 *
 * This is a complex type.
 */
public class SalesComDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.SalesComDocument
{
    private static final long serialVersionUID = 1L;
    
    public SalesComDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SALESCOM$0 = 
        new javax.xml.namespace.QName("", "sales_com");
    
    
    /**
     * Gets the "sales_com" element
     */
    public noNamespace.SalesComDocument.SalesCom getSalesCom()
    {
        synchronized (monitor())
        {
            check_orphaned();
            noNamespace.SalesComDocument.SalesCom target = null;
            target = (noNamespace.SalesComDocument.SalesCom)get_store().find_element_user(SALESCOM$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "sales_com" element
     */
    public void setSalesCom(noNamespace.SalesComDocument.SalesCom salesCom)
    {
        generatedSetterHelperImpl(salesCom, SALESCOM$0, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }
    
    /**
     * Appends and returns a new empty "sales_com" element
     */
    public noNamespace.SalesComDocument.SalesCom addNewSalesCom()
    {
        synchronized (monitor())
        {
            check_orphaned();
            noNamespace.SalesComDocument.SalesCom target = null;
            target = (noNamespace.SalesComDocument.SalesCom)get_store().add_element_user(SALESCOM$0);
            return target;
        }
    }
    /**
     * An XML sales_com(@).
     *
     * This is a complex type.
     */
    public static class SalesComImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.SalesComDocument.SalesCom
    {
        private static final long serialVersionUID = 1L;
        
        public SalesComImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName ROWDATA$0 = 
            new javax.xml.namespace.QName("", "ROWDATA");
        
        
        /**
         * Gets the "ROWDATA" element
         */
        public noNamespace.SalesComDocument.SalesCom.ROWDATA getROWDATA()
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.SalesComDocument.SalesCom.ROWDATA target = null;
                target = (noNamespace.SalesComDocument.SalesCom.ROWDATA)get_store().find_element_user(ROWDATA$0, 0);
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
        public void setROWDATA(noNamespace.SalesComDocument.SalesCom.ROWDATA rowdata)
        {
            generatedSetterHelperImpl(rowdata, ROWDATA$0, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
        }
        
        /**
         * Appends and returns a new empty "ROWDATA" element
         */
        public noNamespace.SalesComDocument.SalesCom.ROWDATA addNewROWDATA()
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.SalesComDocument.SalesCom.ROWDATA target = null;
                target = (noNamespace.SalesComDocument.SalesCom.ROWDATA)get_store().add_element_user(ROWDATA$0);
                return target;
            }
        }
        /**
         * An XML ROWDATA(@).
         *
         * This is a complex type.
         */
        public static class ROWDATAImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.SalesComDocument.SalesCom.ROWDATA
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
            public noNamespace.SalesComDocument.SalesCom.ROWDATA.ROW getROW()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    noNamespace.SalesComDocument.SalesCom.ROWDATA.ROW target = null;
                    target = (noNamespace.SalesComDocument.SalesCom.ROWDATA.ROW)get_store().find_element_user(ROW$0, 0);
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
            public void setROW(noNamespace.SalesComDocument.SalesCom.ROWDATA.ROW row)
            {
                generatedSetterHelperImpl(row, ROW$0, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
            }
            
            /**
             * Appends and returns a new empty "ROW" element
             */
            public noNamespace.SalesComDocument.SalesCom.ROWDATA.ROW addNewROW()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    noNamespace.SalesComDocument.SalesCom.ROWDATA.ROW target = null;
                    target = (noNamespace.SalesComDocument.SalesCom.ROWDATA.ROW)get_store().add_element_user(ROW$0);
                    return target;
                }
            }
            /**
             * An XML ROW(@).
             *
             * This is an atomic type that is a restriction of noNamespace.SalesComDocument$SalesCom$ROWDATA$ROW.
             */
            public static class ROWImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements noNamespace.SalesComDocument.SalesCom.ROWDATA.ROW
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
                private static final javax.xml.namespace.QName SALESMANID$12 = 
                    new javax.xml.namespace.QName("", "Salesman_ID");
                private static final javax.xml.namespace.QName SALESMANNAME$14 = 
                    new javax.xml.namespace.QName("", "Salesman_Name");
                private static final javax.xml.namespace.QName CMR$16 = 
                    new javax.xml.namespace.QName("", "CMR");
                private static final javax.xml.namespace.QName UM$18 = 
                    new javax.xml.namespace.QName("", "UM");
                private static final javax.xml.namespace.QName TITLE$20 = 
                    new javax.xml.namespace.QName("", "Title");
                private static final javax.xml.namespace.QName TEAMNAME$22 = 
                    new javax.xml.namespace.QName("", "Team_Name");
                private static final javax.xml.namespace.QName DEP$24 = 
                    new javax.xml.namespace.QName("", "Dep");
                private static final javax.xml.namespace.QName DEPNAME$26 = 
                    new javax.xml.namespace.QName("", "Dep_Name");
                private static final javax.xml.namespace.QName COMPERCENT$28 = 
                    new javax.xml.namespace.QName("", "Com_Percent");
                private static final javax.xml.namespace.QName SEQNUM$30 = 
                    new javax.xml.namespace.QName("", "Seq_Num");
                private static final javax.xml.namespace.QName TEAMCODE$32 = 
                    new javax.xml.namespace.QName("", "Team_Code");
                private static final javax.xml.namespace.QName SALESMANEXT$34 = 
                    new javax.xml.namespace.QName("", "Salesman_Ext");
                private static final javax.xml.namespace.QName CHANNEL$36 = 
                    new javax.xml.namespace.QName("", "Channel");
                private static final javax.xml.namespace.QName SALESMANCODE$38 = 
                    new javax.xml.namespace.QName("", "Salesman_Code");
                
                
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
                 * Gets the "Salesman_ID" attribute
                 */
                public java.lang.String getSalesmanID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SALESMANID$12);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Salesman_ID" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetSalesmanID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SALESMANID$12);
                      return target;
                    }
                }
                
                /**
                 * True if has "Salesman_ID" attribute
                 */
                public boolean isSetSalesmanID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(SALESMANID$12) != null;
                    }
                }
                
                /**
                 * Sets the "Salesman_ID" attribute
                 */
                public void setSalesmanID(java.lang.String salesmanID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SALESMANID$12);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SALESMANID$12);
                      }
                      target.setStringValue(salesmanID);
                    }
                }
                
                /**
                 * Sets (as xml) the "Salesman_ID" attribute
                 */
                public void xsetSalesmanID(org.apache.xmlbeans.XmlString salesmanID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SALESMANID$12);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SALESMANID$12);
                      }
                      target.set(salesmanID);
                    }
                }
                
                /**
                 * Unsets the "Salesman_ID" attribute
                 */
                public void unsetSalesmanID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(SALESMANID$12);
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SALESMANNAME$14);
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
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SALESMANNAME$14);
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
                      return get_store().find_attribute_user(SALESMANNAME$14) != null;
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SALESMANNAME$14);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SALESMANNAME$14);
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
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SALESMANNAME$14);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SALESMANNAME$14);
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
                      get_store().remove_attribute(SALESMANNAME$14);
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CMR$16);
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
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CMR$16);
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
                      return get_store().find_attribute_user(CMR$16) != null;
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CMR$16);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CMR$16);
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
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CMR$16);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CMR$16);
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
                      get_store().remove_attribute(CMR$16);
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(UM$18);
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
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(UM$18);
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
                      return get_store().find_attribute_user(UM$18) != null;
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(UM$18);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(UM$18);
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
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(UM$18);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(UM$18);
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
                      get_store().remove_attribute(UM$18);
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TITLE$20);
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
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TITLE$20);
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
                      return get_store().find_attribute_user(TITLE$20) != null;
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TITLE$20);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TITLE$20);
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
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TITLE$20);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(TITLE$20);
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
                      get_store().remove_attribute(TITLE$20);
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TEAMNAME$22);
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
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TEAMNAME$22);
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
                      return get_store().find_attribute_user(TEAMNAME$22) != null;
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TEAMNAME$22);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TEAMNAME$22);
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
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TEAMNAME$22);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(TEAMNAME$22);
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
                      get_store().remove_attribute(TEAMNAME$22);
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DEP$24);
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
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DEP$24);
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
                      return get_store().find_attribute_user(DEP$24) != null;
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DEP$24);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DEP$24);
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
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DEP$24);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(DEP$24);
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
                      get_store().remove_attribute(DEP$24);
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DEPNAME$26);
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
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DEPNAME$26);
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
                      return get_store().find_attribute_user(DEPNAME$26) != null;
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DEPNAME$26);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DEPNAME$26);
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
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DEPNAME$26);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(DEPNAME$26);
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
                      get_store().remove_attribute(DEPNAME$26);
                    }
                }
                
                /**
                 * Gets the "Com_Percent" attribute
                 */
                public java.lang.String getComPercent()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(COMPERCENT$28);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Com_Percent" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetComPercent()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(COMPERCENT$28);
                      return target;
                    }
                }
                
                /**
                 * True if has "Com_Percent" attribute
                 */
                public boolean isSetComPercent()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(COMPERCENT$28) != null;
                    }
                }
                
                /**
                 * Sets the "Com_Percent" attribute
                 */
                public void setComPercent(java.lang.String comPercent)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(COMPERCENT$28);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(COMPERCENT$28);
                      }
                      target.setStringValue(comPercent);
                    }
                }
                
                /**
                 * Sets (as xml) the "Com_Percent" attribute
                 */
                public void xsetComPercent(org.apache.xmlbeans.XmlString comPercent)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(COMPERCENT$28);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(COMPERCENT$28);
                      }
                      target.set(comPercent);
                    }
                }
                
                /**
                 * Unsets the "Com_Percent" attribute
                 */
                public void unsetComPercent()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(COMPERCENT$28);
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SEQNUM$30);
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
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SEQNUM$30);
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
                      return get_store().find_attribute_user(SEQNUM$30) != null;
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SEQNUM$30);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SEQNUM$30);
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
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SEQNUM$30);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SEQNUM$30);
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
                      get_store().remove_attribute(SEQNUM$30);
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TEAMCODE$32);
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
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TEAMCODE$32);
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
                      return get_store().find_attribute_user(TEAMCODE$32) != null;
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TEAMCODE$32);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TEAMCODE$32);
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
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TEAMCODE$32);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(TEAMCODE$32);
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
                      get_store().remove_attribute(TEAMCODE$32);
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SALESMANEXT$34);
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
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SALESMANEXT$34);
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
                      return get_store().find_attribute_user(SALESMANEXT$34) != null;
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SALESMANEXT$34);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SALESMANEXT$34);
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
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SALESMANEXT$34);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SALESMANEXT$34);
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
                      get_store().remove_attribute(SALESMANEXT$34);
                    }
                }
                
                /**
                 * Gets the "Channel" attribute
                 */
                public java.lang.String getChannel()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CHANNEL$36);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Channel" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetChannel()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CHANNEL$36);
                      return target;
                    }
                }
                
                /**
                 * True if has "Channel" attribute
                 */
                public boolean isSetChannel()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(CHANNEL$36) != null;
                    }
                }
                
                /**
                 * Sets the "Channel" attribute
                 */
                public void setChannel(java.lang.String channel)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CHANNEL$36);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CHANNEL$36);
                      }
                      target.setStringValue(channel);
                    }
                }
                
                /**
                 * Sets (as xml) the "Channel" attribute
                 */
                public void xsetChannel(org.apache.xmlbeans.XmlString channel)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CHANNEL$36);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CHANNEL$36);
                      }
                      target.set(channel);
                    }
                }
                
                /**
                 * Unsets the "Channel" attribute
                 */
                public void unsetChannel()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(CHANNEL$36);
                    }
                }
                
                /**
                 * Gets the "Salesman_Code" attribute
                 */
                public java.lang.String getSalesmanCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SALESMANCODE$38);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Salesman_Code" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetSalesmanCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SALESMANCODE$38);
                      return target;
                    }
                }
                
                /**
                 * True if has "Salesman_Code" attribute
                 */
                public boolean isSetSalesmanCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(SALESMANCODE$38) != null;
                    }
                }
                
                /**
                 * Sets the "Salesman_Code" attribute
                 */
                public void setSalesmanCode(java.lang.String salesmanCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SALESMANCODE$38);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SALESMANCODE$38);
                      }
                      target.setStringValue(salesmanCode);
                    }
                }
                
                /**
                 * Sets (as xml) the "Salesman_Code" attribute
                 */
                public void xsetSalesmanCode(org.apache.xmlbeans.XmlString salesmanCode)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SALESMANCODE$38);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SALESMANCODE$38);
                      }
                      target.set(salesmanCode);
                    }
                }
                
                /**
                 * Unsets the "Salesman_Code" attribute
                 */
                public void unsetSalesmanCode()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(SALESMANCODE$38);
                    }
                }
            }
        }
    }
}
