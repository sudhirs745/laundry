package com.sud.laundry.presentation.product.ProductModel.productList;

import com.google.gson.annotations.SerializedName;

public class ProductPriceItem {

	@SerializedName("original_price")
	private double originalPrice;

	@SerializedName("price")
	private double price;

	@SerializedName("product_id")
	private String productId;

	@SerializedName("name")
	private String name;

	@SerializedName("weight")
	private String weight;

	@SerializedName("id")
	private int id;

	@SerializedName("f_unit_name")
	private String fUnitName;

	@SerializedName("unit_id")
	private int unitId;

	public ProductPriceItem() {

	}

	public ProductPriceItem(double originalPrice, double price, String  productId, String name, String weight, int id, String fUnitName, int unitId) {
		this.originalPrice = originalPrice;
		this.price = price;
		this.productId = productId;
		this.name = name;
		this.weight = weight;
		this.id = id;
		this.fUnitName = fUnitName;
		this.unitId = unitId;
	}


	public double getOriginalPrice(){
		return originalPrice;
	}

	public double getPrice(){
		return price;
	}

	public String getProductId(){
		return productId;
	}

	public String getName(){
		return name;
	}

	public String getWeight(){
		return weight;
	}

	public int getId(){
		return id;
	}

	public String getFUnitName(){
		return fUnitName;
	}

	public int getUnitId(){
		return unitId;
	}
}