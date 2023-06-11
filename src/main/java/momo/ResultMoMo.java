package momo;

public class ResultMoMo {
	public String t;
	public String partnerCode;
	public String requestId;
	public String deeplink;
	public String deeplinkMiniApp;
	public String orderId;
	public String amount;
	public String responseTime;
	public String message;
	public String resultCode;
	public String payUrl;

	public ResultMoMo() {
		super();
	}

	public ResultMoMo(String t, String deeplink, String deeplinkMiniApp, String partnerCode, String orderId,
			String requestId, String amount, String responseTime, String message, String resultCode, String payUrl) {
		super();
		this.t = t;
		this.deeplink = deeplink;
		this.deeplinkMiniApp = deeplinkMiniApp;
		this.partnerCode = partnerCode;
		this.orderId = orderId;
		this.requestId = requestId;
		this.amount = amount;
		this.responseTime = responseTime;
		this.message = message;
		this.resultCode = resultCode;
		this.payUrl = payUrl;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getDeeplink() {
		return deeplink;
	}

	public void setDeeplink(String deeplink) {
		this.deeplink = deeplink;
	}

	public String getDeeplinkMiniApp() {
		return deeplinkMiniApp;
	}

	public void setDeeplinkMiniApp(String deeplinkMiniApp) {
		this.deeplinkMiniApp = deeplinkMiniApp;
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

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}

}
