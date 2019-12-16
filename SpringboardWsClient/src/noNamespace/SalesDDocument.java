/*
 * An XML document type.
 * Localname: sales_d
 * Namespace: 
 * Java type: noNamespace.SalesDDocument
 *
 * Automatically generated - do not modify.
 */
package noNamespace;


/**
 * A document containing one sales_d(@) element.
 *
 * This is a complex type.
 */
public interface SalesDDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(SalesDDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sB4E55FC8BB30103C8188DD20EA8A2886").resolveHandle("salesd6f58doctype");
    
    /**
     * Gets the "sales_d" element
     */
    noNamespace.SalesDDocument.SalesD getSalesD();
    
    /**
     * Sets the "sales_d" element
     */
    void setSalesD(noNamespace.SalesDDocument.SalesD salesD);
    
    /**
     * Appends and returns a new empty "sales_d" element
     */
    noNamespace.SalesDDocument.SalesD addNewSalesD();
    
    /**
     * An XML sales_d(@).
     *
     * This is a complex type.
     */
    public interface SalesD extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(SalesD.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sB4E55FC8BB30103C8188DD20EA8A2886").resolveHandle("salesdd985elemtype");
        
        /**
         * Gets the "ROWDATA" element
         */
        noNamespace.SalesDDocument.SalesD.ROWDATA getROWDATA();
        
        /**
         * Sets the "ROWDATA" element
         */
        void setROWDATA(noNamespace.SalesDDocument.SalesD.ROWDATA rowdata);
        
        /**
         * Appends and returns a new empty "ROWDATA" element
         */
        noNamespace.SalesDDocument.SalesD.ROWDATA addNewROWDATA();
        
        /**
         * An XML ROWDATA(@).
         *
         * This is a complex type.
         */
        public interface ROWDATA extends org.apache.xmlbeans.XmlObject
        {
            public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
                org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ROWDATA.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sB4E55FC8BB30103C8188DD20EA8A2886").resolveHandle("rowdataee65elemtype");
            
            /**
             * Gets array of all "ROW" elements
             */
            noNamespace.SalesDDocument.SalesD.ROWDATA.ROW[] getROWArray();
            
            /**
             * Gets ith "ROW" element
             */
            noNamespace.SalesDDocument.SalesD.ROWDATA.ROW getROWArray(int i);
            
            /**
             * Returns number of "ROW" element
             */
            int sizeOfROWArray();
            
            /**
             * Sets array of all "ROW" element
             */
            void setROWArray(noNamespace.SalesDDocument.SalesD.ROWDATA.ROW[] rowArray);
            
            /**
             * Sets ith "ROW" element
             */
            void setROWArray(int i, noNamespace.SalesDDocument.SalesD.ROWDATA.ROW row);
            
            /**
             * Inserts and returns a new empty value (as xml) as the ith "ROW" element
             */
            noNamespace.SalesDDocument.SalesD.ROWDATA.ROW insertNewROW(int i);
            
            /**
             * Appends and returns a new empty value (as xml) as the last "ROW" element
             */
            noNamespace.SalesDDocument.SalesD.ROWDATA.ROW addNewROW();
            
            /**
             * Removes the ith "ROW" element
             */
            void removeROW(int i);
            
            /**
             * An XML ROW(@).
             *
             * This is an atomic type that is a restriction of noNamespace.SalesDDocument$SalesD$ROWDATA$ROW.
             */
            public interface ROW extends org.apache.xmlbeans.XmlString
            {
                public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
                    org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ROW.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sB4E55FC8BB30103C8188DD20EA8A2886").resolveHandle("row721belemtype");
                
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
                 * Gets the "SKU" attribute
                 */
                java.lang.String getSKU();
                
                /**
                 * Gets (as xml) the "SKU" attribute
                 */
                org.apache.xmlbeans.XmlString xgetSKU();
                
                /**
                 * True if has "SKU" attribute
                 */
                boolean isSetSKU();
                
                /**
                 * Sets the "SKU" attribute
                 */
                void setSKU(java.lang.String sku);
                
                /**
                 * Sets (as xml) the "SKU" attribute
                 */
                void xsetSKU(org.apache.xmlbeans.XmlString sku);
                
                /**
                 * Unsets the "SKU" attribute
                 */
                void unsetSKU();
                
                /**
                 * Gets the "ProdDesc" attribute
                 */
                java.lang.String getProdDesc();
                
                /**
                 * Gets (as xml) the "ProdDesc" attribute
                 */
                org.apache.xmlbeans.XmlString xgetProdDesc();
                
                /**
                 * True if has "ProdDesc" attribute
                 */
                boolean isSetProdDesc();
                
                /**
                 * Sets the "ProdDesc" attribute
                 */
                void setProdDesc(java.lang.String prodDesc);
                
                /**
                 * Sets (as xml) the "ProdDesc" attribute
                 */
                void xsetProdDesc(org.apache.xmlbeans.XmlString prodDesc);
                
                /**
                 * Unsets the "ProdDesc" attribute
                 */
                void unsetProdDesc();
                
                /**
                 * Gets the "Qty" attribute
                 */
                short getQty();
                
                /**
                 * Gets (as xml) the "Qty" attribute
                 */
                org.apache.xmlbeans.XmlShort xgetQty();
                
                /**
                 * True if has "Qty" attribute
                 */
                boolean isSetQty();
                
                /**
                 * Sets the "Qty" attribute
                 */
                void setQty(short qty);
                
                /**
                 * Sets (as xml) the "Qty" attribute
                 */
                void xsetQty(org.apache.xmlbeans.XmlShort qty);
                
                /**
                 * Unsets the "Qty" attribute
                 */
                void unsetQty();
                
                /**
                 * Gets the "Org_Price" attribute
                 */
                short getOrgPrice();
                
                /**
                 * Gets (as xml) the "Org_Price" attribute
                 */
                org.apache.xmlbeans.XmlShort xgetOrgPrice();
                
                /**
                 * True if has "Org_Price" attribute
                 */
                boolean isSetOrgPrice();
                
                /**
                 * Sets the "Org_Price" attribute
                 */
                void setOrgPrice(short orgPrice);
                
                /**
                 * Sets (as xml) the "Org_Price" attribute
                 */
                void xsetOrgPrice(org.apache.xmlbeans.XmlShort orgPrice);
                
                /**
                 * Unsets the "Org_Price" attribute
                 */
                void unsetOrgPrice();
                
                /**
                 * Gets the "Unit_Price" attribute
                 */
                short getUnitPrice();
                
                /**
                 * Gets (as xml) the "Unit_Price" attribute
                 */
                org.apache.xmlbeans.XmlShort xgetUnitPrice();
                
                /**
                 * True if has "Unit_Price" attribute
                 */
                boolean isSetUnitPrice();
                
                /**
                 * Sets the "Unit_Price" attribute
                 */
                void setUnitPrice(short unitPrice);
                
                /**
                 * Sets (as xml) the "Unit_Price" attribute
                 */
                void xsetUnitPrice(org.apache.xmlbeans.XmlShort unitPrice);
                
                /**
                 * Unsets the "Unit_Price" attribute
                 */
                void unsetUnitPrice();
                
                /**
                 * Gets the "Net_Price" attribute
                 */
                short getNetPrice();
                
                /**
                 * Gets (as xml) the "Net_Price" attribute
                 */
                org.apache.xmlbeans.XmlShort xgetNetPrice();
                
                /**
                 * True if has "Net_Price" attribute
                 */
                boolean isSetNetPrice();
                
                /**
                 * Sets the "Net_Price" attribute
                 */
                void setNetPrice(short netPrice);
                
                /**
                 * Sets (as xml) the "Net_Price" attribute
                 */
                void xsetNetPrice(org.apache.xmlbeans.XmlShort netPrice);
                
                /**
                 * Unsets the "Net_Price" attribute
                 */
                void unsetNetPrice();
                
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
                 * Gets the "Unit_Amount" attribute
                 */
                short getUnitAmount();
                
                /**
                 * Gets (as xml) the "Unit_Amount" attribute
                 */
                org.apache.xmlbeans.XmlShort xgetUnitAmount();
                
                /**
                 * True if has "Unit_Amount" attribute
                 */
                boolean isSetUnitAmount();
                
                /**
                 * Sets the "Unit_Amount" attribute
                 */
                void setUnitAmount(short unitAmount);
                
                /**
                 * Sets (as xml) the "Unit_Amount" attribute
                 */
                void xsetUnitAmount(org.apache.xmlbeans.XmlShort unitAmount);
                
                /**
                 * Unsets the "Unit_Amount" attribute
                 */
                void unsetUnitAmount();
                
                /**
                 * Gets the "Net_Amount" attribute
                 */
                short getNetAmount();
                
                /**
                 * Gets (as xml) the "Net_Amount" attribute
                 */
                org.apache.xmlbeans.XmlShort xgetNetAmount();
                
                /**
                 * True if has "Net_Amount" attribute
                 */
                boolean isSetNetAmount();
                
                /**
                 * Sets the "Net_Amount" attribute
                 */
                void setNetAmount(short netAmount);
                
                /**
                 * Sets (as xml) the "Net_Amount" attribute
                 */
                void xsetNetAmount(org.apache.xmlbeans.XmlShort netAmount);
                
                /**
                 * Unsets the "Net_Amount" attribute
                 */
                void unsetNetAmount();
                
                /**
                 * Gets the "Total_Qty" attribute
                 */
                short getTotalQty();
                
                /**
                 * Gets (as xml) the "Total_Qty" attribute
                 */
                org.apache.xmlbeans.XmlShort xgetTotalQty();
                
                /**
                 * True if has "Total_Qty" attribute
                 */
                boolean isSetTotalQty();
                
                /**
                 * Sets the "Total_Qty" attribute
                 */
                void setTotalQty(short totalQty);
                
                /**
                 * Sets (as xml) the "Total_Qty" attribute
                 */
                void xsetTotalQty(org.apache.xmlbeans.XmlShort totalQty);
                
                /**
                 * Unsets the "Total_Qty" attribute
                 */
                void unsetTotalQty();
                
                /**
                 * Gets the "Discount_Price" attribute
                 */
                short getDiscountPrice();
                
                /**
                 * Gets (as xml) the "Discount_Price" attribute
                 */
                org.apache.xmlbeans.XmlShort xgetDiscountPrice();
                
                /**
                 * True if has "Discount_Price" attribute
                 */
                boolean isSetDiscountPrice();
                
                /**
                 * Sets the "Discount_Price" attribute
                 */
                void setDiscountPrice(short discountPrice);
                
                /**
                 * Sets (as xml) the "Discount_Price" attribute
                 */
                void xsetDiscountPrice(org.apache.xmlbeans.XmlShort discountPrice);
                
                /**
                 * Unsets the "Discount_Price" attribute
                 */
                void unsetDiscountPrice();
                
                /**
                 * Gets the "ID_ReasonCode" attribute
                 */
                java.lang.String getIDReasonCode();
                
                /**
                 * Gets (as xml) the "ID_ReasonCode" attribute
                 */
                org.apache.xmlbeans.XmlString xgetIDReasonCode();
                
                /**
                 * True if has "ID_ReasonCode" attribute
                 */
                boolean isSetIDReasonCode();
                
                /**
                 * Sets the "ID_ReasonCode" attribute
                 */
                void setIDReasonCode(java.lang.String idReasonCode);
                
                /**
                 * Sets (as xml) the "ID_ReasonCode" attribute
                 */
                void xsetIDReasonCode(org.apache.xmlbeans.XmlString idReasonCode);
                
                /**
                 * Unsets the "ID_ReasonCode" attribute
                 */
                void unsetIDReasonCode();
                
                /**
                 * Gets the "Additional1" attribute
                 */
                java.lang.String getAdditional1();
                
                /**
                 * Gets (as xml) the "Additional1" attribute
                 */
                org.apache.xmlbeans.XmlString xgetAdditional1();
                
                /**
                 * True if has "Additional1" attribute
                 */
                boolean isSetAdditional1();
                
                /**
                 * Sets the "Additional1" attribute
                 */
                void setAdditional1(java.lang.String additional1);
                
                /**
                 * Sets (as xml) the "Additional1" attribute
                 */
                void xsetAdditional1(org.apache.xmlbeans.XmlString additional1);
                
                /**
                 * Unsets the "Additional1" attribute
                 */
                void unsetAdditional1();
                
                /**
                 * Gets the "Additional2" attribute
                 */
                java.lang.String getAdditional2();
                
                /**
                 * Gets (as xml) the "Additional2" attribute
                 */
                org.apache.xmlbeans.XmlString xgetAdditional2();
                
                /**
                 * True if has "Additional2" attribute
                 */
                boolean isSetAdditional2();
                
                /**
                 * Sets the "Additional2" attribute
                 */
                void setAdditional2(java.lang.String additional2);
                
                /**
                 * Sets (as xml) the "Additional2" attribute
                 */
                void xsetAdditional2(org.apache.xmlbeans.XmlString additional2);
                
                /**
                 * Unsets the "Additional2" attribute
                 */
                void unsetAdditional2();
                
                /**
                 * Gets the "Additional3" attribute
                 */
                java.lang.String getAdditional3();
                
                /**
                 * Gets (as xml) the "Additional3" attribute
                 */
                org.apache.xmlbeans.XmlString xgetAdditional3();
                
                /**
                 * True if has "Additional3" attribute
                 */
                boolean isSetAdditional3();
                
                /**
                 * Sets the "Additional3" attribute
                 */
                void setAdditional3(java.lang.String additional3);
                
                /**
                 * Sets (as xml) the "Additional3" attribute
                 */
                void xsetAdditional3(org.apache.xmlbeans.XmlString additional3);
                
                /**
                 * Unsets the "Additional3" attribute
                 */
                void unsetAdditional3();
                
                /**
                 * Gets the "ChgClass" attribute
                 */
                java.lang.String getChgClass();
                
                /**
                 * Gets (as xml) the "ChgClass" attribute
                 */
                org.apache.xmlbeans.XmlString xgetChgClass();
                
                /**
                 * True if has "ChgClass" attribute
                 */
                boolean isSetChgClass();
                
                /**
                 * Sets the "ChgClass" attribute
                 */
                void setChgClass(java.lang.String chgClass);
                
                /**
                 * Sets (as xml) the "ChgClass" attribute
                 */
                void xsetChgClass(org.apache.xmlbeans.XmlString chgClass);
                
                /**
                 * Unsets the "ChgClass" attribute
                 */
                void unsetChgClass();
                
                /**
                 * Gets the "IsBOM" attribute
                 */
                java.lang.String getIsBOM();
                
                /**
                 * Gets (as xml) the "IsBOM" attribute
                 */
                org.apache.xmlbeans.XmlString xgetIsBOM();
                
                /**
                 * True if has "IsBOM" attribute
                 */
                boolean isSetIsBOM();
                
                /**
                 * Sets the "IsBOM" attribute
                 */
                void setIsBOM(java.lang.String isBOM);
                
                /**
                 * Sets (as xml) the "IsBOM" attribute
                 */
                void xsetIsBOM(org.apache.xmlbeans.XmlString isBOM);
                
                /**
                 * Unsets the "IsBOM" attribute
                 */
                void unsetIsBOM();
                
                /**
                 * Gets the "MNPNeed" attribute
                 */
                java.lang.String getMNPNeed();
                
                /**
                 * Gets (as xml) the "MNPNeed" attribute
                 */
                org.apache.xmlbeans.XmlString xgetMNPNeed();
                
                /**
                 * True if has "MNPNeed" attribute
                 */
                boolean isSetMNPNeed();
                
                /**
                 * Sets the "MNPNeed" attribute
                 */
                void setMNPNeed(java.lang.String mnpNeed);
                
                /**
                 * Sets (as xml) the "MNPNeed" attribute
                 */
                void xsetMNPNeed(org.apache.xmlbeans.XmlString mnpNeed);
                
                /**
                 * Unsets the "MNPNeed" attribute
                 */
                void unsetMNPNeed();
                
                /**
                 * Gets the "CNPNeed" attribute
                 */
                java.lang.String getCNPNeed();
                
                /**
                 * Gets (as xml) the "CNPNeed" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCNPNeed();
                
                /**
                 * True if has "CNPNeed" attribute
                 */
                boolean isSetCNPNeed();
                
                /**
                 * Sets the "CNPNeed" attribute
                 */
                void setCNPNeed(java.lang.String cnpNeed);
                
                /**
                 * Sets (as xml) the "CNPNeed" attribute
                 */
                void xsetCNPNeed(org.apache.xmlbeans.XmlString cnpNeed);
                
                /**
                 * Unsets the "CNPNeed" attribute
                 */
                void unsetCNPNeed();
                
                /**
                 * Gets the "ChargeType" attribute
                 */
                java.lang.String getChargeType();
                
                /**
                 * Gets (as xml) the "ChargeType" attribute
                 */
                org.apache.xmlbeans.XmlString xgetChargeType();
                
                /**
                 * True if has "ChargeType" attribute
                 */
                boolean isSetChargeType();
                
                /**
                 * Sets the "ChargeType" attribute
                 */
                void setChargeType(java.lang.String chargeType);
                
                /**
                 * Sets (as xml) the "ChargeType" attribute
                 */
                void xsetChargeType(org.apache.xmlbeans.XmlString chargeType);
                
                /**
                 * Unsets the "ChargeType" attribute
                 */
                void unsetChargeType();
                
                /**
                 * Gets the "SN_Num" attribute
                 */
                java.lang.String getSNNum();
                
                /**
                 * Gets (as xml) the "SN_Num" attribute
                 */
                org.apache.xmlbeans.XmlString xgetSNNum();
                
                /**
                 * True if has "SN_Num" attribute
                 */
                boolean isSetSNNum();
                
                /**
                 * Sets the "SN_Num" attribute
                 */
                void setSNNum(java.lang.String snNum);
                
                /**
                 * Sets (as xml) the "SN_Num" attribute
                 */
                void xsetSNNum(org.apache.xmlbeans.XmlString snNum);
                
                /**
                 * Unsets the "SN_Num" attribute
                 */
                void unsetSNNum();
                
                /**
                 * Gets the "StockType" attribute
                 */
                java.lang.String getStockType();
                
                /**
                 * Gets (as xml) the "StockType" attribute
                 */
                org.apache.xmlbeans.XmlString xgetStockType();
                
                /**
                 * True if has "StockType" attribute
                 */
                boolean isSetStockType();
                
                /**
                 * Sets the "StockType" attribute
                 */
                void setStockType(java.lang.String stockType);
                
                /**
                 * Sets (as xml) the "StockType" attribute
                 */
                void xsetStockType(org.apache.xmlbeans.XmlString stockType);
                
                /**
                 * Unsets the "StockType" attribute
                 */
                void unsetStockType();
                
                /**
                 * Gets the "Collected" attribute
                 */
                java.lang.String getCollected();
                
                /**
                 * Gets (as xml) the "Collected" attribute
                 */
                org.apache.xmlbeans.XmlString xgetCollected();
                
                /**
                 * True if has "Collected" attribute
                 */
                boolean isSetCollected();
                
                /**
                 * Sets the "Collected" attribute
                 */
                void setCollected(java.lang.String collected);
                
                /**
                 * Sets (as xml) the "Collected" attribute
                 */
                void xsetCollected(org.apache.xmlbeans.XmlString collected);
                
                /**
                 * Unsets the "Collected" attribute
                 */
                void unsetCollected();
                
                /**
                 * Gets the "Pickup_Location" attribute
                 */
                java.lang.String getPickupLocation();
                
                /**
                 * Gets (as xml) the "Pickup_Location" attribute
                 */
                org.apache.xmlbeans.XmlString xgetPickupLocation();
                
                /**
                 * True if has "Pickup_Location" attribute
                 */
                boolean isSetPickupLocation();
                
                /**
                 * Sets the "Pickup_Location" attribute
                 */
                void setPickupLocation(java.lang.String pickupLocation);
                
                /**
                 * Sets (as xml) the "Pickup_Location" attribute
                 */
                void xsetPickupLocation(org.apache.xmlbeans.XmlString pickupLocation);
                
                /**
                 * Unsets the "Pickup_Location" attribute
                 */
                void unsetPickupLocation();
                
                /**
                 * Gets the "DeptCode" attribute
                 */
                java.lang.String getDeptCode();
                
                /**
                 * Gets (as xml) the "DeptCode" attribute
                 */
                org.apache.xmlbeans.XmlString xgetDeptCode();
                
                /**
                 * True if has "DeptCode" attribute
                 */
                boolean isSetDeptCode();
                
                /**
                 * Sets the "DeptCode" attribute
                 */
                void setDeptCode(java.lang.String deptCode);
                
                /**
                 * Sets (as xml) the "DeptCode" attribute
                 */
                void xsetDeptCode(org.apache.xmlbeans.XmlString deptCode);
                
                /**
                 * Unsets the "DeptCode" attribute
                 */
                void unsetDeptCode();
                
                /**
                 * Gets the "DISC_PRICE" attribute
                 */
                short getDISCPRICE();
                
                /**
                 * Gets (as xml) the "DISC_PRICE" attribute
                 */
                org.apache.xmlbeans.XmlShort xgetDISCPRICE();
                
                /**
                 * True if has "DISC_PRICE" attribute
                 */
                boolean isSetDISCPRICE();
                
                /**
                 * Sets the "DISC_PRICE" attribute
                 */
                void setDISCPRICE(short discprice);
                
                /**
                 * Sets (as xml) the "DISC_PRICE" attribute
                 */
                void xsetDISCPRICE(org.apache.xmlbeans.XmlShort discprice);
                
                /**
                 * Unsets the "DISC_PRICE" attribute
                 */
                void unsetDISCPRICE();
                
                /**
                 * Gets the "DISC_AMOUNT" attribute
                 */
                short getDISCAMOUNT();
                
                /**
                 * Gets (as xml) the "DISC_AMOUNT" attribute
                 */
                org.apache.xmlbeans.XmlShort xgetDISCAMOUNT();
                
                /**
                 * True if has "DISC_AMOUNT" attribute
                 */
                boolean isSetDISCAMOUNT();
                
                /**
                 * Sets the "DISC_AMOUNT" attribute
                 */
                void setDISCAMOUNT(short discamount);
                
                /**
                 * Sets (as xml) the "DISC_AMOUNT" attribute
                 */
                void xsetDISCAMOUNT(org.apache.xmlbeans.XmlShort discamount);
                
                /**
                 * Unsets the "DISC_AMOUNT" attribute
                 */
                void unsetDISCAMOUNT();
                
                /**
                 * Gets the "IMEI" attribute
                 */
                java.lang.String getIMEI();
                
                /**
                 * Gets (as xml) the "IMEI" attribute
                 */
                org.apache.xmlbeans.XmlString xgetIMEI();
                
                /**
                 * True if has "IMEI" attribute
                 */
                boolean isSetIMEI();
                
                /**
                 * Sets the "IMEI" attribute
                 */
                void setIMEI(java.lang.String imei);
                
                /**
                 * Sets (as xml) the "IMEI" attribute
                 */
                void xsetIMEI(org.apache.xmlbeans.XmlString imei);
                
                /**
                 * Unsets the "IMEI" attribute
                 */
                void unsetIMEI();
                
                /**
                 * Gets the "Add_Charge" attribute
                 */
                short getAddCharge();
                
                /**
                 * Gets (as xml) the "Add_Charge" attribute
                 */
                org.apache.xmlbeans.XmlShort xgetAddCharge();
                
                /**
                 * True if has "Add_Charge" attribute
                 */
                boolean isSetAddCharge();
                
                /**
                 * Sets the "Add_Charge" attribute
                 */
                void setAddCharge(short addCharge);
                
                /**
                 * Sets (as xml) the "Add_Charge" attribute
                 */
                void xsetAddCharge(org.apache.xmlbeans.XmlShort addCharge);
                
                /**
                 * Unsets the "Add_Charge" attribute
                 */
                void unsetAddCharge();
                
                /**
                 * Gets the "Disc_Type" attribute
                 */
                java.lang.String getDiscType();
                
                /**
                 * Gets (as xml) the "Disc_Type" attribute
                 */
                org.apache.xmlbeans.XmlString xgetDiscType();
                
                /**
                 * True if has "Disc_Type" attribute
                 */
                boolean isSetDiscType();
                
                /**
                 * Sets the "Disc_Type" attribute
                 */
                void setDiscType(java.lang.String discType);
                
                /**
                 * Sets (as xml) the "Disc_Type" attribute
                 */
                void xsetDiscType(org.apache.xmlbeans.XmlString discType);
                
                /**
                 * Unsets the "Disc_Type" attribute
                 */
                void unsetDiscType();
                
                /**
                 * Gets the "Is_coupon_sku" attribute
                 */
                java.lang.String getIsCouponSku();
                
                /**
                 * Gets (as xml) the "Is_coupon_sku" attribute
                 */
                org.apache.xmlbeans.XmlString xgetIsCouponSku();
                
                /**
                 * True if has "Is_coupon_sku" attribute
                 */
                boolean isSetIsCouponSku();
                
                /**
                 * Sets the "Is_coupon_sku" attribute
                 */
                void setIsCouponSku(java.lang.String isCouponSku);
                
                /**
                 * Sets (as xml) the "Is_coupon_sku" attribute
                 */
                void xsetIsCouponSku(org.apache.xmlbeans.XmlString isCouponSku);
                
                /**
                 * Unsets the "Is_coupon_sku" attribute
                 */
                void unsetIsCouponSku();
                
                /**
                 * Gets the "Is_buyback_sku" attribute
                 */
                java.lang.String getIsBuybackSku();
                
                /**
                 * Gets (as xml) the "Is_buyback_sku" attribute
                 */
                org.apache.xmlbeans.XmlString xgetIsBuybackSku();
                
                /**
                 * True if has "Is_buyback_sku" attribute
                 */
                boolean isSetIsBuybackSku();
                
                /**
                 * Sets the "Is_buyback_sku" attribute
                 */
                void setIsBuybackSku(java.lang.String isBuybackSku);
                
                /**
                 * Sets (as xml) the "Is_buyback_sku" attribute
                 */
                void xsetIsBuybackSku(org.apache.xmlbeans.XmlString isBuybackSku);
                
                /**
                 * Unsets the "Is_buyback_sku" attribute
                 */
                void unsetIsBuybackSku();
                
                /**
                 * Gets the "Pickup_Store_Type" attribute
                 */
                java.lang.String getPickupStoreType();
                
                /**
                 * Gets (as xml) the "Pickup_Store_Type" attribute
                 */
                org.apache.xmlbeans.XmlString xgetPickupStoreType();
                
                /**
                 * True if has "Pickup_Store_Type" attribute
                 */
                boolean isSetPickupStoreType();
                
                /**
                 * Sets the "Pickup_Store_Type" attribute
                 */
                void setPickupStoreType(java.lang.String pickupStoreType);
                
                /**
                 * Sets (as xml) the "Pickup_Store_Type" attribute
                 */
                void xsetPickupStoreType(org.apache.xmlbeans.XmlString pickupStoreType);
                
                /**
                 * Unsets the "Pickup_Store_Type" attribute
                 */
                void unsetPickupStoreType();
                
                /**
                 * Gets the "Pickup_Store_Name" attribute
                 */
                java.lang.String getPickupStoreName();
                
                /**
                 * Gets (as xml) the "Pickup_Store_Name" attribute
                 */
                org.apache.xmlbeans.XmlString xgetPickupStoreName();
                
                /**
                 * True if has "Pickup_Store_Name" attribute
                 */
                boolean isSetPickupStoreName();
                
                /**
                 * Sets the "Pickup_Store_Name" attribute
                 */
                void setPickupStoreName(java.lang.String pickupStoreName);
                
                /**
                 * Sets (as xml) the "Pickup_Store_Name" attribute
                 */
                void xsetPickupStoreName(org.apache.xmlbeans.XmlString pickupStoreName);
                
                /**
                 * Unsets the "Pickup_Store_Name" attribute
                 */
                void unsetPickupStoreName();
                
                /**
                 * Gets the "Pickup_Store_tel" attribute
                 */
                java.lang.String getPickupStoreTel();
                
                /**
                 * Gets (as xml) the "Pickup_Store_tel" attribute
                 */
                org.apache.xmlbeans.XmlString xgetPickupStoreTel();
                
                /**
                 * True if has "Pickup_Store_tel" attribute
                 */
                boolean isSetPickupStoreTel();
                
                /**
                 * Sets the "Pickup_Store_tel" attribute
                 */
                void setPickupStoreTel(java.lang.String pickupStoreTel);
                
                /**
                 * Sets (as xml) the "Pickup_Store_tel" attribute
                 */
                void xsetPickupStoreTel(org.apache.xmlbeans.XmlString pickupStoreTel);
                
                /**
                 * Unsets the "Pickup_Store_tel" attribute
                 */
                void unsetPickupStoreTel();
                
                /**
                 * Gets the "Pickup_Store_Begin_Hours" attribute
                 */
                java.lang.String getPickupStoreBeginHours();
                
                /**
                 * Gets (as xml) the "Pickup_Store_Begin_Hours" attribute
                 */
                org.apache.xmlbeans.XmlString xgetPickupStoreBeginHours();
                
                /**
                 * True if has "Pickup_Store_Begin_Hours" attribute
                 */
                boolean isSetPickupStoreBeginHours();
                
                /**
                 * Sets the "Pickup_Store_Begin_Hours" attribute
                 */
                void setPickupStoreBeginHours(java.lang.String pickupStoreBeginHours);
                
                /**
                 * Sets (as xml) the "Pickup_Store_Begin_Hours" attribute
                 */
                void xsetPickupStoreBeginHours(org.apache.xmlbeans.XmlString pickupStoreBeginHours);
                
                /**
                 * Unsets the "Pickup_Store_Begin_Hours" attribute
                 */
                void unsetPickupStoreBeginHours();
                
                /**
                 * Gets the "Pickup_Store_End_Hours" attribute
                 */
                java.lang.String getPickupStoreEndHours();
                
                /**
                 * Gets (as xml) the "Pickup_Store_End_Hours" attribute
                 */
                org.apache.xmlbeans.XmlString xgetPickupStoreEndHours();
                
                /**
                 * True if has "Pickup_Store_End_Hours" attribute
                 */
                boolean isSetPickupStoreEndHours();
                
                /**
                 * Sets the "Pickup_Store_End_Hours" attribute
                 */
                void setPickupStoreEndHours(java.lang.String pickupStoreEndHours);
                
                /**
                 * Sets (as xml) the "Pickup_Store_End_Hours" attribute
                 */
                void xsetPickupStoreEndHours(org.apache.xmlbeans.XmlString pickupStoreEndHours);
                
                /**
                 * Unsets the "Pickup_Store_End_Hours" attribute
                 */
                void unsetPickupStoreEndHours();
                
                /**
                 * Gets the "Pickup_Store_Addr_1" attribute
                 */
                java.lang.String getPickupStoreAddr1();
                
                /**
                 * Gets (as xml) the "Pickup_Store_Addr_1" attribute
                 */
                org.apache.xmlbeans.XmlString xgetPickupStoreAddr1();
                
                /**
                 * True if has "Pickup_Store_Addr_1" attribute
                 */
                boolean isSetPickupStoreAddr1();
                
                /**
                 * Sets the "Pickup_Store_Addr_1" attribute
                 */
                void setPickupStoreAddr1(java.lang.String pickupStoreAddr1);
                
                /**
                 * Sets (as xml) the "Pickup_Store_Addr_1" attribute
                 */
                void xsetPickupStoreAddr1(org.apache.xmlbeans.XmlString pickupStoreAddr1);
                
                /**
                 * Unsets the "Pickup_Store_Addr_1" attribute
                 */
                void unsetPickupStoreAddr1();
                
                /**
                 * Gets the "Pickup_Store_Addr_2" attribute
                 */
                java.lang.String getPickupStoreAddr2();
                
                /**
                 * Gets (as xml) the "Pickup_Store_Addr_2" attribute
                 */
                org.apache.xmlbeans.XmlString xgetPickupStoreAddr2();
                
                /**
                 * True if has "Pickup_Store_Addr_2" attribute
                 */
                boolean isSetPickupStoreAddr2();
                
                /**
                 * Sets the "Pickup_Store_Addr_2" attribute
                 */
                void setPickupStoreAddr2(java.lang.String pickupStoreAddr2);
                
                /**
                 * Sets (as xml) the "Pickup_Store_Addr_2" attribute
                 */
                void xsetPickupStoreAddr2(org.apache.xmlbeans.XmlString pickupStoreAddr2);
                
                /**
                 * Unsets the "Pickup_Store_Addr_2" attribute
                 */
                void unsetPickupStoreAddr2();
                
                /**
                 * Gets the "Pickup_Store_Addr_3" attribute
                 */
                java.lang.String getPickupStoreAddr3();
                
                /**
                 * Gets (as xml) the "Pickup_Store_Addr_3" attribute
                 */
                org.apache.xmlbeans.XmlString xgetPickupStoreAddr3();
                
                /**
                 * True if has "Pickup_Store_Addr_3" attribute
                 */
                boolean isSetPickupStoreAddr3();
                
                /**
                 * Sets the "Pickup_Store_Addr_3" attribute
                 */
                void setPickupStoreAddr3(java.lang.String pickupStoreAddr3);
                
                /**
                 * Sets (as xml) the "Pickup_Store_Addr_3" attribute
                 */
                void xsetPickupStoreAddr3(org.apache.xmlbeans.XmlString pickupStoreAddr3);
                
                /**
                 * Unsets the "Pickup_Store_Addr_3" attribute
                 */
                void unsetPickupStoreAddr3();
                
                /**
                 * Gets the "lbl_sno" attribute
                 */
                java.lang.String getLblSno();
                
                /**
                 * Gets (as xml) the "lbl_sno" attribute
                 */
                org.apache.xmlbeans.XmlString xgetLblSno();
                
                /**
                 * True if has "lbl_sno" attribute
                 */
                boolean isSetLblSno();
                
                /**
                 * Sets the "lbl_sno" attribute
                 */
                void setLblSno(java.lang.String lblSno);
                
                /**
                 * Sets (as xml) the "lbl_sno" attribute
                 */
                void xsetLblSno(org.apache.xmlbeans.XmlString lblSno);
                
                /**
                 * Unsets the "lbl_sno" attribute
                 */
                void unsetLblSno();
                
                /**
                 * Gets the "warranty" attribute
                 */
                java.lang.String getWarranty();
                
                /**
                 * Gets (as xml) the "warranty" attribute
                 */
                org.apache.xmlbeans.XmlString xgetWarranty();
                
                /**
                 * True if has "warranty" attribute
                 */
                boolean isSetWarranty();
                
                /**
                 * Sets the "warranty" attribute
                 */
                void setWarranty(java.lang.String warranty);
                
                /**
                 * Sets (as xml) the "warranty" attribute
                 */
                void xsetWarranty(org.apache.xmlbeans.XmlString warranty);
                
                /**
                 * Unsets the "warranty" attribute
                 */
                void unsetWarranty();
                
                /**
                 * Gets the "Fault_Code" attribute
                 */
                java.lang.String getFaultCode();
                
                /**
                 * Gets (as xml) the "Fault_Code" attribute
                 */
                org.apache.xmlbeans.XmlString xgetFaultCode();
                
                /**
                 * True if has "Fault_Code" attribute
                 */
                boolean isSetFaultCode();
                
                /**
                 * Sets the "Fault_Code" attribute
                 */
                void setFaultCode(java.lang.String faultCode);
                
                /**
                 * Sets (as xml) the "Fault_Code" attribute
                 */
                void xsetFaultCode(org.apache.xmlbeans.XmlString faultCode);
                
                /**
                 * Unsets the "Fault_Code" attribute
                 */
                void unsetFaultCode();
                
                /**
                 * Gets the "Fault_Desc" attribute
                 */
                java.lang.String getFaultDesc();
                
                /**
                 * Gets (as xml) the "Fault_Desc" attribute
                 */
                org.apache.xmlbeans.XmlString xgetFaultDesc();
                
                /**
                 * True if has "Fault_Desc" attribute
                 */
                boolean isSetFaultDesc();
                
                /**
                 * Sets the "Fault_Desc" attribute
                 */
                void setFaultDesc(java.lang.String faultDesc);
                
                /**
                 * Sets (as xml) the "Fault_Desc" attribute
                 */
                void xsetFaultDesc(org.apache.xmlbeans.XmlString faultDesc);
                
                /**
                 * Unsets the "Fault_Desc" attribute
                 */
                void unsetFaultDesc();
                
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
                 * Gets the "ActValue" attribute
                 */
                short getActValue();
                
                /**
                 * Gets (as xml) the "ActValue" attribute
                 */
                org.apache.xmlbeans.XmlShort xgetActValue();
                
                /**
                 * True if has "ActValue" attribute
                 */
                boolean isSetActValue();
                
                /**
                 * Sets the "ActValue" attribute
                 */
                void setActValue(short actValue);
                
                /**
                 * Sets (as xml) the "ActValue" attribute
                 */
                void xsetActValue(org.apache.xmlbeans.XmlShort actValue);
                
                /**
                 * Unsets the "ActValue" attribute
                 */
                void unsetActValue();
                
                /**
                 * Gets the "ActAmount" attribute
                 */
                short getActAmount();
                
                /**
                 * Gets (as xml) the "ActAmount" attribute
                 */
                org.apache.xmlbeans.XmlShort xgetActAmount();
                
                /**
                 * True if has "ActAmount" attribute
                 */
                boolean isSetActAmount();
                
                /**
                 * Sets the "ActAmount" attribute
                 */
                void setActAmount(short actAmount);
                
                /**
                 * Sets (as xml) the "ActAmount" attribute
                 */
                void xsetActAmount(org.apache.xmlbeans.XmlShort actAmount);
                
                /**
                 * Unsets the "ActAmount" attribute
                 */
                void unsetActAmount();
                
                /**
                 * Gets the "StockStatus" attribute
                 */
                java.lang.String getStockStatus();
                
                /**
                 * Gets (as xml) the "StockStatus" attribute
                 */
                org.apache.xmlbeans.XmlString xgetStockStatus();
                
                /**
                 * True if has "StockStatus" attribute
                 */
                boolean isSetStockStatus();
                
                /**
                 * Sets the "StockStatus" attribute
                 */
                void setStockStatus(java.lang.String stockStatus);
                
                /**
                 * Sets (as xml) the "StockStatus" attribute
                 */
                void xsetStockStatus(org.apache.xmlbeans.XmlString stockStatus);
                
                /**
                 * Unsets the "StockStatus" attribute
                 */
                void unsetStockStatus();
                
                /**
                 * Gets the "Sub_ID" attribute
                 */
                java.lang.String getSubID();
                
                /**
                 * Gets (as xml) the "Sub_ID" attribute
                 */
                org.apache.xmlbeans.XmlString xgetSubID();
                
                /**
                 * True if has "Sub_ID" attribute
                 */
                boolean isSetSubID();
                
                /**
                 * Sets the "Sub_ID" attribute
                 */
                void setSubID(java.lang.String subID);
                
                /**
                 * Sets (as xml) the "Sub_ID" attribute
                 */
                void xsetSubID(org.apache.xmlbeans.XmlString subID);
                
                /**
                 * Unsets the "Sub_ID" attribute
                 */
                void unsetSubID();
                
                /**
                 * Gets the "Pickup_Staff" attribute
                 */
                java.lang.String getPickupStaff();
                
                /**
                 * Gets (as xml) the "Pickup_Staff" attribute
                 */
                org.apache.xmlbeans.XmlString xgetPickupStaff();
                
                /**
                 * True if has "Pickup_Staff" attribute
                 */
                boolean isSetPickupStaff();
                
                /**
                 * Sets the "Pickup_Staff" attribute
                 */
                void setPickupStaff(java.lang.String pickupStaff);
                
                /**
                 * Sets (as xml) the "Pickup_Staff" attribute
                 */
                void xsetPickupStaff(org.apache.xmlbeans.XmlString pickupStaff);
                
                /**
                 * Unsets the "Pickup_Staff" attribute
                 */
                void unsetPickupStaff();
                
                /**
                 * Gets the "Pickup_Date" attribute
                 */
                java.lang.String getPickupDate();
                
                /**
                 * Gets (as xml) the "Pickup_Date" attribute
                 */
                org.apache.xmlbeans.XmlString xgetPickupDate();
                
                /**
                 * True if has "Pickup_Date" attribute
                 */
                boolean isSetPickupDate();
                
                /**
                 * Sets the "Pickup_Date" attribute
                 */
                void setPickupDate(java.lang.String pickupDate);
                
                /**
                 * Sets (as xml) the "Pickup_Date" attribute
                 */
                void xsetPickupDate(org.apache.xmlbeans.XmlString pickupDate);
                
                /**
                 * Unsets the "Pickup_Date" attribute
                 */
                void unsetPickupDate();
                
                /**
                 * A factory class with static methods for creating instances
                 * of this type.
                 */
                
                public static final class Factory
                {
                    public static noNamespace.SalesDDocument.SalesD.ROWDATA.ROW newInstance() {
                      return (noNamespace.SalesDDocument.SalesD.ROWDATA.ROW) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
                    
                    public static noNamespace.SalesDDocument.SalesD.ROWDATA.ROW newInstance(org.apache.xmlbeans.XmlOptions options) {
                      return (noNamespace.SalesDDocument.SalesD.ROWDATA.ROW) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
                    
                    private Factory() { } // No instance of this class allowed
                }
            }
            
            /**
             * A factory class with static methods for creating instances
             * of this type.
             */
            
            public static final class Factory
            {
                public static noNamespace.SalesDDocument.SalesD.ROWDATA newInstance() {
                  return (noNamespace.SalesDDocument.SalesD.ROWDATA) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
                
                public static noNamespace.SalesDDocument.SalesD.ROWDATA newInstance(org.apache.xmlbeans.XmlOptions options) {
                  return (noNamespace.SalesDDocument.SalesD.ROWDATA) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
                
                private Factory() { } // No instance of this class allowed
            }
        }
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static noNamespace.SalesDDocument.SalesD newInstance() {
              return (noNamespace.SalesDDocument.SalesD) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static noNamespace.SalesDDocument.SalesD newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (noNamespace.SalesDDocument.SalesD) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static noNamespace.SalesDDocument newInstance() {
          return (noNamespace.SalesDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static noNamespace.SalesDDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (noNamespace.SalesDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static noNamespace.SalesDDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static noNamespace.SalesDDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static noNamespace.SalesDDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static noNamespace.SalesDDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static noNamespace.SalesDDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static noNamespace.SalesDDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static noNamespace.SalesDDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static noNamespace.SalesDDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static noNamespace.SalesDDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static noNamespace.SalesDDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.SalesDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static noNamespace.SalesDDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static noNamespace.SalesDDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static noNamespace.SalesDDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static noNamespace.SalesDDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.SalesDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static noNamespace.SalesDDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (noNamespace.SalesDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static noNamespace.SalesDDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (noNamespace.SalesDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
