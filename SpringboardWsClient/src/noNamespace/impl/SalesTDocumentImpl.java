/*
 * An XML document type.
 * Localname: sales_t
 * Namespace: 
 * Java type: noNamespace.SalesTDocument
 *
 * Automatically generated - do not modify.
 */
package noNamespace.impl;
/**
 * A document containing one sales_t(@) element.
 *
 * This is a complex type.
 */
public class SalesTDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.SalesTDocument
{
    private static final long serialVersionUID = 1L;
    
    public SalesTDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SALEST$0 = 
        new javax.xml.namespace.QName("", "sales_t");
    
    
    /**
     * Gets the "sales_t" element
     */
    public noNamespace.SalesTDocument.SalesT getSalesT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            noNamespace.SalesTDocument.SalesT target = null;
            target = (noNamespace.SalesTDocument.SalesT)get_store().find_element_user(SALEST$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "sales_t" element
     */
    public void setSalesT(noNamespace.SalesTDocument.SalesT salesT)
    {
        generatedSetterHelperImpl(salesT, SALEST$0, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }
    
    /**
     * Appends and returns a new empty "sales_t" element
     */
    public noNamespace.SalesTDocument.SalesT addNewSalesT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            noNamespace.SalesTDocument.SalesT target = null;
            target = (noNamespace.SalesTDocument.SalesT)get_store().add_element_user(SALEST$0);
            return target;
        }
    }
    /**
     * An XML sales_t(@).
     *
     * This is a complex type.
     */
    public static class SalesTImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.SalesTDocument.SalesT
    {
        private static final long serialVersionUID = 1L;
        
        public SalesTImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName ROWDATA$0 = 
            new javax.xml.namespace.QName("", "ROWDATA");
        
        
        /**
         * Gets the "ROWDATA" element
         */
        public noNamespace.SalesTDocument.SalesT.ROWDATA getROWDATA()
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.SalesTDocument.SalesT.ROWDATA target = null;
                target = (noNamespace.SalesTDocument.SalesT.ROWDATA)get_store().find_element_user(ROWDATA$0, 0);
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
        public void setROWDATA(noNamespace.SalesTDocument.SalesT.ROWDATA rowdata)
        {
            generatedSetterHelperImpl(rowdata, ROWDATA$0, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
        }
        
        /**
         * Appends and returns a new empty "ROWDATA" element
         */
        public noNamespace.SalesTDocument.SalesT.ROWDATA addNewROWDATA()
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.SalesTDocument.SalesT.ROWDATA target = null;
                target = (noNamespace.SalesTDocument.SalesT.ROWDATA)get_store().add_element_user(ROWDATA$0);
                return target;
            }
        }
        /**
         * An XML ROWDATA(@).
         *
         * This is a complex type.
         */
        public static class ROWDATAImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.SalesTDocument.SalesT.ROWDATA
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
            public noNamespace.SalesTDocument.SalesT.ROWDATA.ROW[] getROWArray()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    java.util.List targetList = new java.util.ArrayList();
                    get_store().find_all_element_users(ROW$0, targetList);
                    noNamespace.SalesTDocument.SalesT.ROWDATA.ROW[] result = new noNamespace.SalesTDocument.SalesT.ROWDATA.ROW[targetList.size()];
                    targetList.toArray(result);
                    return result;
                }
            }
            
            /**
             * Gets ith "ROW" element
             */
            public noNamespace.SalesTDocument.SalesT.ROWDATA.ROW getROWArray(int i)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    noNamespace.SalesTDocument.SalesT.ROWDATA.ROW target = null;
                    target = (noNamespace.SalesTDocument.SalesT.ROWDATA.ROW)get_store().find_element_user(ROW$0, i);
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
            public void setROWArray(noNamespace.SalesTDocument.SalesT.ROWDATA.ROW[] rowArray)
            {
                check_orphaned();
                arraySetterHelper(rowArray, ROW$0);
            }
            
            /**
             * Sets ith "ROW" element
             */
            public void setROWArray(int i, noNamespace.SalesTDocument.SalesT.ROWDATA.ROW row)
            {
                generatedSetterHelperImpl(row, ROW$0, i, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_ARRAYITEM);
            }
            
            /**
             * Inserts and returns a new empty value (as xml) as the ith "ROW" element
             */
            public noNamespace.SalesTDocument.SalesT.ROWDATA.ROW insertNewROW(int i)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    noNamespace.SalesTDocument.SalesT.ROWDATA.ROW target = null;
                    target = (noNamespace.SalesTDocument.SalesT.ROWDATA.ROW)get_store().insert_element_user(ROW$0, i);
                    return target;
                }
            }
            
            /**
             * Appends and returns a new empty value (as xml) as the last "ROW" element
             */
            public noNamespace.SalesTDocument.SalesT.ROWDATA.ROW addNewROW()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    noNamespace.SalesTDocument.SalesT.ROWDATA.ROW target = null;
                    target = (noNamespace.SalesTDocument.SalesT.ROWDATA.ROW)get_store().add_element_user(ROW$0);
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
             * This is an atomic type that is a restriction of noNamespace.SalesTDocument$SalesT$ROWDATA$ROW.
             */
            public static class ROWImpl extends org.apache.xmlbeans.impl.values.JavaStringHolderEx implements noNamespace.SalesTDocument.SalesT.ROWDATA.ROW
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
                private static final javax.xml.namespace.QName TENDERID$14 = 
                    new javax.xml.namespace.QName("", "Tender_ID");
                private static final javax.xml.namespace.QName TENDERAMOUNT$16 = 
                    new javax.xml.namespace.QName("", "Tender_Amount");
                private static final javax.xml.namespace.QName LOCALAMOUNT$18 = 
                    new javax.xml.namespace.QName("", "Local_Amount");
                private static final javax.xml.namespace.QName TENDERDESC$20 = 
                    new javax.xml.namespace.QName("", "Tender_Desc");
                private static final javax.xml.namespace.QName EXCHANGERATE$22 = 
                    new javax.xml.namespace.QName("", "Exchange_Rate");
                private static final javax.xml.namespace.QName PAYMENTTYPE$24 = 
                    new javax.xml.namespace.QName("", "Payment_Type");
                private static final javax.xml.namespace.QName ADDITIONAL$26 = 
                    new javax.xml.namespace.QName("", "Additional");
                private static final javax.xml.namespace.QName TENDERTYPE$28 = 
                    new javax.xml.namespace.QName("", "Tender_Type");
                private static final javax.xml.namespace.QName STATUS$30 = 
                    new javax.xml.namespace.QName("", "Status");
                private static final javax.xml.namespace.QName ORGAMOUNT$32 = 
                    new javax.xml.namespace.QName("", "Org_Amount");
                private static final javax.xml.namespace.QName PAYCONFIRMDATE$34 = 
                    new javax.xml.namespace.QName("", "Pay_Confirm_Date");
                private static final javax.xml.namespace.QName DEPOSIT$36 = 
                    new javax.xml.namespace.QName("", "Deposit");
                private static final javax.xml.namespace.QName RECORDTYPE$38 = 
                    new javax.xml.namespace.QName("", "Record_Type");
                private static final javax.xml.namespace.QName EODFLAG$40 = 
                    new javax.xml.namespace.QName("", "EOD_Flag");
                private static final javax.xml.namespace.QName INPUTTYPE$42 = 
                    new javax.xml.namespace.QName("", "Input_Type");
                
                
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
                 * Gets the "Tender_ID" attribute
                 */
                public java.lang.String getTenderID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TENDERID$14);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Tender_ID" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetTenderID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TENDERID$14);
                      return target;
                    }
                }
                
                /**
                 * True if has "Tender_ID" attribute
                 */
                public boolean isSetTenderID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(TENDERID$14) != null;
                    }
                }
                
                /**
                 * Sets the "Tender_ID" attribute
                 */
                public void setTenderID(java.lang.String tenderID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TENDERID$14);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TENDERID$14);
                      }
                      target.setStringValue(tenderID);
                    }
                }
                
                /**
                 * Sets (as xml) the "Tender_ID" attribute
                 */
                public void xsetTenderID(org.apache.xmlbeans.XmlString tenderID)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TENDERID$14);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(TENDERID$14);
                      }
                      target.set(tenderID);
                    }
                }
                
                /**
                 * Unsets the "Tender_ID" attribute
                 */
                public void unsetTenderID()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(TENDERID$14);
                    }
                }
                
                /**
                 * Gets the "Tender_Amount" attribute
                 */
                public short getTenderAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TENDERAMOUNT$16);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getShortValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Tender_Amount" attribute
                 */
                public org.apache.xmlbeans.XmlShort xgetTenderAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(TENDERAMOUNT$16);
                      return target;
                    }
                }
                
                /**
                 * True if has "Tender_Amount" attribute
                 */
                public boolean isSetTenderAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(TENDERAMOUNT$16) != null;
                    }
                }
                
                /**
                 * Sets the "Tender_Amount" attribute
                 */
                public void setTenderAmount(short tenderAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TENDERAMOUNT$16);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TENDERAMOUNT$16);
                      }
                      target.setShortValue(tenderAmount);
                    }
                }
                
                /**
                 * Sets (as xml) the "Tender_Amount" attribute
                 */
                public void xsetTenderAmount(org.apache.xmlbeans.XmlShort tenderAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(TENDERAMOUNT$16);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlShort)get_store().add_attribute_user(TENDERAMOUNT$16);
                      }
                      target.set(tenderAmount);
                    }
                }
                
                /**
                 * Unsets the "Tender_Amount" attribute
                 */
                public void unsetTenderAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(TENDERAMOUNT$16);
                    }
                }
                
                /**
                 * Gets the "Local_Amount" attribute
                 */
                public short getLocalAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LOCALAMOUNT$18);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getShortValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Local_Amount" attribute
                 */
                public org.apache.xmlbeans.XmlShort xgetLocalAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(LOCALAMOUNT$18);
                      return target;
                    }
                }
                
                /**
                 * True if has "Local_Amount" attribute
                 */
                public boolean isSetLocalAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(LOCALAMOUNT$18) != null;
                    }
                }
                
                /**
                 * Sets the "Local_Amount" attribute
                 */
                public void setLocalAmount(short localAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LOCALAMOUNT$18);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(LOCALAMOUNT$18);
                      }
                      target.setShortValue(localAmount);
                    }
                }
                
                /**
                 * Sets (as xml) the "Local_Amount" attribute
                 */
                public void xsetLocalAmount(org.apache.xmlbeans.XmlShort localAmount)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(LOCALAMOUNT$18);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlShort)get_store().add_attribute_user(LOCALAMOUNT$18);
                      }
                      target.set(localAmount);
                    }
                }
                
                /**
                 * Unsets the "Local_Amount" attribute
                 */
                public void unsetLocalAmount()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(LOCALAMOUNT$18);
                    }
                }
                
                /**
                 * Gets the "Tender_Desc" attribute
                 */
                public java.lang.String getTenderDesc()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TENDERDESC$20);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Tender_Desc" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetTenderDesc()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TENDERDESC$20);
                      return target;
                    }
                }
                
                /**
                 * True if has "Tender_Desc" attribute
                 */
                public boolean isSetTenderDesc()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(TENDERDESC$20) != null;
                    }
                }
                
                /**
                 * Sets the "Tender_Desc" attribute
                 */
                public void setTenderDesc(java.lang.String tenderDesc)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TENDERDESC$20);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TENDERDESC$20);
                      }
                      target.setStringValue(tenderDesc);
                    }
                }
                
                /**
                 * Sets (as xml) the "Tender_Desc" attribute
                 */
                public void xsetTenderDesc(org.apache.xmlbeans.XmlString tenderDesc)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TENDERDESC$20);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(TENDERDESC$20);
                      }
                      target.set(tenderDesc);
                    }
                }
                
                /**
                 * Unsets the "Tender_Desc" attribute
                 */
                public void unsetTenderDesc()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(TENDERDESC$20);
                    }
                }
                
                /**
                 * Gets the "Exchange_Rate" attribute
                 */
                public short getExchangeRate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(EXCHANGERATE$22);
                      if (target == null)
                      {
                        return 0;
                      }
                      return target.getShortValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Exchange_Rate" attribute
                 */
                public org.apache.xmlbeans.XmlShort xgetExchangeRate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(EXCHANGERATE$22);
                      return target;
                    }
                }
                
                /**
                 * True if has "Exchange_Rate" attribute
                 */
                public boolean isSetExchangeRate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(EXCHANGERATE$22) != null;
                    }
                }
                
                /**
                 * Sets the "Exchange_Rate" attribute
                 */
                public void setExchangeRate(short exchangeRate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(EXCHANGERATE$22);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(EXCHANGERATE$22);
                      }
                      target.setShortValue(exchangeRate);
                    }
                }
                
                /**
                 * Sets (as xml) the "Exchange_Rate" attribute
                 */
                public void xsetExchangeRate(org.apache.xmlbeans.XmlShort exchangeRate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlShort target = null;
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(EXCHANGERATE$22);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlShort)get_store().add_attribute_user(EXCHANGERATE$22);
                      }
                      target.set(exchangeRate);
                    }
                }
                
                /**
                 * Unsets the "Exchange_Rate" attribute
                 */
                public void unsetExchangeRate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(EXCHANGERATE$22);
                    }
                }
                
                /**
                 * Gets the "Payment_Type" attribute
                 */
                public java.lang.String getPaymentType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PAYMENTTYPE$24);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Payment_Type" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetPaymentType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PAYMENTTYPE$24);
                      return target;
                    }
                }
                
                /**
                 * True if has "Payment_Type" attribute
                 */
                public boolean isSetPaymentType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(PAYMENTTYPE$24) != null;
                    }
                }
                
                /**
                 * Sets the "Payment_Type" attribute
                 */
                public void setPaymentType(java.lang.String paymentType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PAYMENTTYPE$24);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PAYMENTTYPE$24);
                      }
                      target.setStringValue(paymentType);
                    }
                }
                
                /**
                 * Sets (as xml) the "Payment_Type" attribute
                 */
                public void xsetPaymentType(org.apache.xmlbeans.XmlString paymentType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PAYMENTTYPE$24);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PAYMENTTYPE$24);
                      }
                      target.set(paymentType);
                    }
                }
                
                /**
                 * Unsets the "Payment_Type" attribute
                 */
                public void unsetPaymentType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(PAYMENTTYPE$24);
                    }
                }
                
                /**
                 * Gets the "Additional" attribute
                 */
                public java.lang.String getAdditional()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ADDITIONAL$26);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Additional" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetAdditional()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ADDITIONAL$26);
                      return target;
                    }
                }
                
                /**
                 * True if has "Additional" attribute
                 */
                public boolean isSetAdditional()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(ADDITIONAL$26) != null;
                    }
                }
                
                /**
                 * Sets the "Additional" attribute
                 */
                public void setAdditional(java.lang.String additional)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ADDITIONAL$26);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ADDITIONAL$26);
                      }
                      target.setStringValue(additional);
                    }
                }
                
                /**
                 * Sets (as xml) the "Additional" attribute
                 */
                public void xsetAdditional(org.apache.xmlbeans.XmlString additional)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ADDITIONAL$26);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(ADDITIONAL$26);
                      }
                      target.set(additional);
                    }
                }
                
                /**
                 * Unsets the "Additional" attribute
                 */
                public void unsetAdditional()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(ADDITIONAL$26);
                    }
                }
                
                /**
                 * Gets the "Tender_Type" attribute
                 */
                public java.lang.String getTenderType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TENDERTYPE$28);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Tender_Type" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetTenderType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TENDERTYPE$28);
                      return target;
                    }
                }
                
                /**
                 * True if has "Tender_Type" attribute
                 */
                public boolean isSetTenderType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(TENDERTYPE$28) != null;
                    }
                }
                
                /**
                 * Sets the "Tender_Type" attribute
                 */
                public void setTenderType(java.lang.String tenderType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TENDERTYPE$28);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TENDERTYPE$28);
                      }
                      target.setStringValue(tenderType);
                    }
                }
                
                /**
                 * Sets (as xml) the "Tender_Type" attribute
                 */
                public void xsetTenderType(org.apache.xmlbeans.XmlString tenderType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TENDERTYPE$28);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(TENDERTYPE$28);
                      }
                      target.set(tenderType);
                    }
                }
                
                /**
                 * Unsets the "Tender_Type" attribute
                 */
                public void unsetTenderType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(TENDERTYPE$28);
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STATUS$30);
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
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STATUS$30);
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
                      return get_store().find_attribute_user(STATUS$30) != null;
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STATUS$30);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(STATUS$30);
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
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(STATUS$30);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(STATUS$30);
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
                      get_store().remove_attribute(STATUS$30);
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ORGAMOUNT$32);
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
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(ORGAMOUNT$32);
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
                      return get_store().find_attribute_user(ORGAMOUNT$32) != null;
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ORGAMOUNT$32);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ORGAMOUNT$32);
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
                      target = (org.apache.xmlbeans.XmlShort)get_store().find_attribute_user(ORGAMOUNT$32);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlShort)get_store().add_attribute_user(ORGAMOUNT$32);
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
                      get_store().remove_attribute(ORGAMOUNT$32);
                    }
                }
                
                /**
                 * Gets the "Pay_Confirm_Date" attribute
                 */
                public java.lang.String getPayConfirmDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PAYCONFIRMDATE$34);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Pay_Confirm_Date" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetPayConfirmDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PAYCONFIRMDATE$34);
                      return target;
                    }
                }
                
                /**
                 * True if has "Pay_Confirm_Date" attribute
                 */
                public boolean isSetPayConfirmDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(PAYCONFIRMDATE$34) != null;
                    }
                }
                
                /**
                 * Sets the "Pay_Confirm_Date" attribute
                 */
                public void setPayConfirmDate(java.lang.String payConfirmDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PAYCONFIRMDATE$34);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PAYCONFIRMDATE$34);
                      }
                      target.setStringValue(payConfirmDate);
                    }
                }
                
                /**
                 * Sets (as xml) the "Pay_Confirm_Date" attribute
                 */
                public void xsetPayConfirmDate(org.apache.xmlbeans.XmlString payConfirmDate)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PAYCONFIRMDATE$34);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PAYCONFIRMDATE$34);
                      }
                      target.set(payConfirmDate);
                    }
                }
                
                /**
                 * Unsets the "Pay_Confirm_Date" attribute
                 */
                public void unsetPayConfirmDate()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(PAYCONFIRMDATE$34);
                    }
                }
                
                /**
                 * Gets the "Deposit" attribute
                 */
                public java.lang.String getDeposit()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DEPOSIT$36);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Deposit" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetDeposit()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DEPOSIT$36);
                      return target;
                    }
                }
                
                /**
                 * True if has "Deposit" attribute
                 */
                public boolean isSetDeposit()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(DEPOSIT$36) != null;
                    }
                }
                
                /**
                 * Sets the "Deposit" attribute
                 */
                public void setDeposit(java.lang.String deposit)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DEPOSIT$36);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DEPOSIT$36);
                      }
                      target.setStringValue(deposit);
                    }
                }
                
                /**
                 * Sets (as xml) the "Deposit" attribute
                 */
                public void xsetDeposit(org.apache.xmlbeans.XmlString deposit)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(DEPOSIT$36);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(DEPOSIT$36);
                      }
                      target.set(deposit);
                    }
                }
                
                /**
                 * Unsets the "Deposit" attribute
                 */
                public void unsetDeposit()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(DEPOSIT$36);
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(RECORDTYPE$38);
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
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(RECORDTYPE$38);
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
                      return get_store().find_attribute_user(RECORDTYPE$38) != null;
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
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(RECORDTYPE$38);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(RECORDTYPE$38);
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
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(RECORDTYPE$38);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(RECORDTYPE$38);
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
                      get_store().remove_attribute(RECORDTYPE$38);
                    }
                }
                
                /**
                 * Gets the "EOD_Flag" attribute
                 */
                public java.lang.String getEODFlag()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(EODFLAG$40);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "EOD_Flag" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetEODFlag()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(EODFLAG$40);
                      return target;
                    }
                }
                
                /**
                 * True if has "EOD_Flag" attribute
                 */
                public boolean isSetEODFlag()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(EODFLAG$40) != null;
                    }
                }
                
                /**
                 * Sets the "EOD_Flag" attribute
                 */
                public void setEODFlag(java.lang.String eodFlag)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(EODFLAG$40);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(EODFLAG$40);
                      }
                      target.setStringValue(eodFlag);
                    }
                }
                
                /**
                 * Sets (as xml) the "EOD_Flag" attribute
                 */
                public void xsetEODFlag(org.apache.xmlbeans.XmlString eodFlag)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(EODFLAG$40);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(EODFLAG$40);
                      }
                      target.set(eodFlag);
                    }
                }
                
                /**
                 * Unsets the "EOD_Flag" attribute
                 */
                public void unsetEODFlag()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(EODFLAG$40);
                    }
                }
                
                /**
                 * Gets the "Input_Type" attribute
                 */
                public java.lang.String getInputType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INPUTTYPE$42);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Input_Type" attribute
                 */
                public org.apache.xmlbeans.XmlString xgetInputType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INPUTTYPE$42);
                      return target;
                    }
                }
                
                /**
                 * True if has "Input_Type" attribute
                 */
                public boolean isSetInputType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().find_attribute_user(INPUTTYPE$42) != null;
                    }
                }
                
                /**
                 * Sets the "Input_Type" attribute
                 */
                public void setInputType(java.lang.String inputType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(INPUTTYPE$42);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(INPUTTYPE$42);
                      }
                      target.setStringValue(inputType);
                    }
                }
                
                /**
                 * Sets (as xml) the "Input_Type" attribute
                 */
                public void xsetInputType(org.apache.xmlbeans.XmlString inputType)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(INPUTTYPE$42);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(INPUTTYPE$42);
                      }
                      target.set(inputType);
                    }
                }
                
                /**
                 * Unsets the "Input_Type" attribute
                 */
                public void unsetInputType()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_attribute(INPUTTYPE$42);
                    }
                }
            }
        }
    }
}
