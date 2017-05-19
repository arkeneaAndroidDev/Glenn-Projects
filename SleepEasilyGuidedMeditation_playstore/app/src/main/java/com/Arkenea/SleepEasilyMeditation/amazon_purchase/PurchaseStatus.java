package com.Arkenea.SleepEasilyMeditation.amazon_purchase;

public interface PurchaseStatus {
    
    public void onPurchaseState(boolean level2ProductAvailable,boolean level3ProductAvailable,boolean level4ProductAvailable,
            boolean level2Purchased,boolean level3Purchased,boolean level4Purchased);
    public void onPurchaseSuccess(String sku);

}
