package com.Arkenea.SleepEasilyMeditatation;

public class purchaseitemclass {
	int icon_id;
	String product_id;
	String product_app_name;
	String Product_app_ammount;
	String Product_app_description;
	public purchaseitemclass(int icon_id, String product_id, String product_app_name,
			String product_app_ammount, String product_app_description) {
		super();
		this.icon_id = icon_id;
		this.product_id = product_id;
		this.product_app_name = product_app_name;
		Product_app_ammount = product_app_ammount;
		Product_app_description = product_app_description;
	}
	public int getIcon_id() {
		return icon_id;
	}
	public void setIcon_id(int icon_id) {
		this.icon_id = icon_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getProduct_app_name() {
		return product_app_name;
	}
	public void setProduct_app_name(String product_app_name) {
		this.product_app_name = product_app_name;
	}
	public String getProduct_app_ammount() {
		return Product_app_ammount;
	}
	public void setProduct_app_ammount(String product_app_ammount) {
		Product_app_ammount = product_app_ammount;
	}
	public String getProduct_app_description() {
		return Product_app_description;
	}
	public void setProduct_app_description(String product_app_description) {
		Product_app_description = product_app_description;
	}

}
