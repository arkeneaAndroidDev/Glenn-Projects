package com.Arkenea.SleepEasilyMeditation.amazon_purchase;

import com.Arkenea.SleepEasilyMeditatation.Constants;


/**
 * 
 * MySku enum contains all In App Purchase products definition that the sample
 * app will use. The product definition includes two properties: "SKU" and
 * "Available Marketplace".
 * 
 */
public enum MySku {

    // The only entitlement product used in this sample app
    
    LEVEL2(Constants.ITEM_SKU_AMAZON_FIRST, "GB"),
    
    LEVEL3(Constants.ITEM_SKU_AMAZON_SECOND, "GB"),
    
    LEVEL4(Constants.ITEM_SKU_AMAZON_THIRD, "GB");
    
    private final String sku;
    private final String availableMarkpetplace;

    /**
     * Returns the MySku object from the specified Sku and marketplace value.
     * 
     * @param sku
     * @param marketplace
     * @return
     */
    public static MySku fromSku(final String sku, final String marketplace) {
        if (LEVEL2.getSku().equals(sku) ) {
            return LEVEL2;
        }
        if(LEVEL3.getSku().equals(sku) ){
            return LEVEL3;
        }
        if(LEVEL4.getSku().equals(sku) ){
            return LEVEL4;
        }
        return null;
    }

    /**
     * Returns the Sku string of the MySku object
     * 
     * @return
     */
    public String getSku() {
        return this.sku;
    }

    /**
     * Returns the Available Marketplace of the MySku object
     * 
     * @return
     */
    public String getAvailableMarketplace() {
        return this.availableMarkpetplace;
    }

    private MySku(final String sku, final String availableMarkpetplace) {
        this.sku = sku;
        this.availableMarkpetplace = availableMarkpetplace;
    }

}
