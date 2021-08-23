package com.sud.laundry.presentation.order.model.orderDetails;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class OrderDetailsItem {

    @SerializedName("payment_zipgroup")
    private String paymentZipgroup;

    @SerializedName("firstname")
    private String firstname;

    @SerializedName("qr_path")
    private String qr_path;

    @SerializedName("shipping_code")
    private String shippingCode;

    @SerializedName("shipping_city")
    private String shippingCity;

    @SerializedName("mobile_no")
    private String mobileNo;

    @SerializedName("shipping_address_1")
    private String shippingAddress1;

    @SerializedName("del_status")
    private int delStatus;

    @SerializedName("shipping_address_2")
    private String shippingAddress2;

    @SerializedName("tracking")
    private String tracking;

    @SerializedName("order_status")
    private String orderStatus;
    @SerializedName("shipping_state")
    private String shipping_state;

    @SerializedName("total")
    private String total;

    @SerializedName("forwarded_ip")
    private String forwardedIp;

    @SerializedName("shipping_zipgroup")
    private String shippingZipgroup;

    @SerializedName("payment_country")
    private String paymentCountry;

    @SerializedName("shipping_method")
    private String shippingMethod;

    @SerializedName("payment_zipgroup_id")
    private int paymentZipgroupId;

    @SerializedName("payment_postcode")
    private String paymentPostcode;

    @SerializedName("courier_id")
    private int courierId;

    @SerializedName("shipping_firstname")
    private String shippingFirstname;

    @SerializedName("mobile_optional")
    private String mobileOptional;

    @SerializedName("payment_city")
    private String paymentCity;

    @SerializedName("shipping_postcode")
    private String shippingPostcode;

    @SerializedName("email")
    private String email;

    @SerializedName("payment_firstname")
    private String paymentFirstname;

    @SerializedName("payment_method")
    private String paymentMethod;

    @SerializedName("shipping_lastname")
    private String shippingLastname;

    @SerializedName("user_agent")
    private String userAgent;

    @SerializedName("payment_company")
    private String paymentCompany;

    @SerializedName("payment_code")
    private String paymentCode;

    @SerializedName("awbno")
    private String awbno;

    @SerializedName("shipping_company")
    private String shippingCompany;

    @SerializedName("invoice_prefix")
    private String invoicePrefix;

    @SerializedName("shipping_zipgroup_id")
    private int shippingZipgroupId;

    @SerializedName("payment_status")
    private int paymentStatus;

    @SerializedName("payment_address_2")
    private String paymentAddress2;

    @SerializedName("payment_address_1")
    private String paymentAddress1;

    @SerializedName("date_modified")
    private String dateModified;

    @SerializedName("proDetailsList")
    private List<ProDetailsListItem> proDetailsList;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("comment")
    private String comment;

    @SerializedName("marketing_id")
    private int marketingId;

    @SerializedName("created_date")
    private String createdDate;

    @SerializedName("order_id")
    private int orderId;

    @SerializedName("shipping_country")
    private String shippingCountry;

    @SerializedName("user_group_id")
    private int userGroupId;

    @SerializedName("tracking_name")
    private String trackingName;

    @SerializedName("payment_status_name")
    private String paymentStatusName;


    public String getPaymentZipgroup() {
        return paymentZipgroup;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getShippingAddress1() {
        return shippingAddress1;
    }

    public int getDelStatus() {
        return delStatus;
    }

    public String getShippingAddress2() {
        return shippingAddress2;
    }

    public String getTracking() {
        return tracking;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getTotal() {
        return total;
    }

    public String getForwardedIp() {
        return forwardedIp;
    }

    public String getShippingZipgroup() {
        return shippingZipgroup;
    }

    public String getPaymentCountry() {
        return paymentCountry;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public int getPaymentZipgroupId() {
        return paymentZipgroupId;
    }

    public String getPaymentPostcode() {
        return paymentPostcode;
    }

    public int getCourierId() {
        return courierId;
    }

    public String getShippingFirstname() {
        return shippingFirstname;
    }

    public String getMobileOptional() {
        return mobileOptional;
    }

    public String getPaymentCity() {
        return paymentCity;
    }

    public String getShippingPostcode() {
        return shippingPostcode;
    }

    public String getEmail() {
        return email;
    }

    public String getPaymentFirstname() {
        return paymentFirstname;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getShippingLastname() {
        return shippingLastname;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getPaymentCompany() {
        return paymentCompany;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public String getAwbno() {
        return awbno;
    }

    public String getShippingCompany() {
        return shippingCompany;
    }

    public String getInvoicePrefix() {
        return invoicePrefix;
    }

    public int getShippingZipgroupId() {
        return shippingZipgroupId;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public String getPaymentAddress2() {
        return paymentAddress2;
    }

    public String getPaymentAddress1() {
        return paymentAddress1;
    }

    public String getDateModified() {
        return dateModified;
    }

    public List<ProDetailsListItem> getProDetailsList() {
        return proDetailsList;
    }

    public int getUserId() {
        return userId;
    }

    public String getComment() {
        return comment;
    }

    public int getMarketingId() {
        return marketingId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getShippingCountry() {
        return shippingCountry;
    }

    public int getUserGroupId() {
        return userGroupId;
    }

    public String getShipping_state() {
        return shipping_state;
    }

    public String getTrackingName() {
        return trackingName;
    }

    public String getPaymentStatusName() {
        return paymentStatusName;
    }

    public String getQr_path() {
        return qr_path;
    }
}