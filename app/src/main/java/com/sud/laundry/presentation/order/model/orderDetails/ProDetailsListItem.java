package com.sud.laundry.presentation.order.model.orderDetails;

import com.google.gson.annotations.SerializedName;

public class ProDetailsListItem{

	@SerializedName("reward")
	private int reward;

	@SerializedName("Suppliers_id")
	private int suppliersId;

	@SerializedName("order_product_id")
	private int orderProductId;

	@SerializedName("quantity")
	private int quantity;

	@SerializedName("discount")
	private float discount;

	@SerializedName("Product_Name")
	private String productName;

	@SerializedName("description")
	private String description;

	@SerializedName("tax")
	private float tax;

	@SerializedName("tax_rate")
	private float taxRate;

	@SerializedName("Category_id")
	private int categoryId;

	@SerializedName("total")
	private float total;

	@SerializedName("model_name")
	private String modelName;

	@SerializedName("img_url")
	private String imgUrl;

	@SerializedName("price")
	private float price;

	@SerializedName("product_id")
	private int productId;

	@SerializedName("name")
	private String name;

	@SerializedName("model")
	private String model;

	@SerializedName("order_id")
	private int orderId;

	@SerializedName("weight")
	private String weight;

	public String getWeight() {
		return weight;
	}

	public int getReward(){
		return reward;
	}

	public int getSuppliersId(){
		return suppliersId;
	}

	public int getOrderProductId(){
		return orderProductId;
	}

	public int getQuantity(){
		return quantity;
	}

	public float getDiscount(){
		return discount;
	}

	public String getProductName(){
		return productName;
	}

	public String getDescription(){
		return description;
	}

	public float getTax(){
		return tax;
	}

	public float getTaxRate(){
		return taxRate;
	}

	public int getCategoryId(){
		return categoryId;
	}

	public float getTotal(){
		return total;
	}

	public String getModelName(){
		return modelName;
	}

	public String getImgUrl(){
		return imgUrl;
	}

	public float getPrice(){
		return price;
	}

	public int getProductId(){
		return productId;
	}

	public String getName(){
		return name;
	}

	public String getModel(){
		return model;
	}

	public int getOrderId(){
		return orderId;
	}
}