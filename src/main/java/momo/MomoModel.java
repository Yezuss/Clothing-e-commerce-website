package momo;

public class MomoModel {
 public String partnerCode;
 public String orderId ;
 public String storeId ;
 public String redirectUrl ;
 public String ipnUrl ;
 public String requestId ;
 public String requestType ;
 public String amount;
 public String partnerUserId;
 public String orderType;
 public String transId;
 public String resultCode;
 public String message;
 public String payType;
 public String responseTime; 
 public String orderInfo;
 public String extraData;
 public String  signature;
 
public MomoModel() {
	super();
}

public MomoModel(String partnerCode, String orderId, String storeId, String redirectUrl, String ipnUrl,
		String requestId, String requestType, String amount, String partnerUserId, String orderType, String transId,
		String resultCode, String message, String payType, String responseTime, String orderInfo, String extraData,
		String signature) {
	super();
	this.partnerCode = partnerCode;
	this.orderId = orderId;
	this.storeId = storeId;
	this.redirectUrl = redirectUrl;
	this.ipnUrl = ipnUrl;
	this.requestId = requestId;
	this.requestType = requestType;
	this.amount = amount;
	this.partnerUserId = partnerUserId;
	this.orderType = orderType;
	this.transId = transId;
	this.resultCode = resultCode;
	this.message = message;
	this.payType = payType;
	this.responseTime = responseTime;
	this.orderInfo = orderInfo;
	this.extraData = extraData;
	this.signature = signature;
}

public String getPartnerCode() {
	return partnerCode;
}

public void setPartnerCode(String partnerCode) {
	this.partnerCode = partnerCode;
}

public String getOrderId() {
	return orderId;
}

public void setOrderId(String orderId) {
	this.orderId = orderId;
}

public String getStoreId() {
	return storeId;
}

public void setStoreId(String storeId) {
	this.storeId = storeId;
}

public String getRedirectUrl() {
	return redirectUrl;
}

public void setRedirectUrl(String redirectUrl) {
	this.redirectUrl = redirectUrl;
}

public String getIpnUrl() {
	return ipnUrl;
}

public void setIpnUrl(String ipnUrl) {
	this.ipnUrl = ipnUrl;
}

public String getRequestId() {
	return requestId;
}

public void setRequestId(String requestId) {
	this.requestId = requestId;
}

public String getRequestType() {
	return requestType;
}

public void setRequestType(String requestType) {
	this.requestType = requestType;
}

public String getAmount() {
	return amount;
}

public void setAmount(String amount ) {
	this.amount = amount;
}

public String getPartnerUserId() {
	return partnerUserId;
}

public void setPartnerUserId(String partnerUserId) {
	this.partnerUserId = partnerUserId;
}

public String getOrderType() {
	return orderType;
}

public void setOrderType(String orderType) {
	this.orderType = orderType;
}

public String getTransId() {
	return transId;
}

public void setTransId(String transId) {
	this.transId = transId;
}

public String getResultCode() {
	return resultCode;
}

public void setResultCode(String resultCode) {
	this.resultCode = resultCode;
}

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}

public String getPayType() {
	return payType;
}

public void setPayType(String payType) {
	this.payType = payType;
}

public String getResponseTime() {
	return responseTime;
}

public void setResponseTime(String responseTime) {
	this.responseTime = responseTime;
}

public String getOrderInfo() {
	return orderInfo;
}

public void setOrderInfo(String orderInfo) {
	this.orderInfo = orderInfo;
}

public String getExtraData() {
	return extraData;
}

public void setExtraData(String extraData) {
	this.extraData = extraData;
}

public String getSignature() {
	return signature;
}

public void setSignature(String signature) {
	this.signature = signature;
}


}
