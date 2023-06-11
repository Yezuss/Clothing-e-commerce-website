package utils;

public class Constant {
	
	public static final  String accessKey = "M8brj9K6E22vXoDB";	
	public static final String serectkey = "nqQiVSgDMy809JoPF6OzP5OdBUB550Y4";
	public static final String orderType = "momo_wallet";
	public static final String requestType="captureWallet";
	public static final String payType="webApp";
	public static final String redirectUrl="http://localhost:8080/paywithmomo";
	public static final String ipnUrl="http://localhost:8080/paywithmomo";
	public static final String Url="https://test-payment.momo.vn/v2/gateway/api/create";
	public static final String IDMOMO="MOMO5RGX20191128";
}
/*- `accesskey`: khóa truy cập để gửi yêu cầu đến MoMo API.
- `serectkey`: khóa bí mật để mã hóa chữ ký trước khi gửi yêu cầu đến MoMo.
- `ordertype`: kiểu đơn hàng, ở đây là ví MoMo.
- `requesttype`: kiểu yêu cầu, ở đây là thu tiền.
- `paytype`: kiểu thanh toán, ở đây là webapp.
- `redirecturl`: đường dẫn để chuyển hướng sau khi thanh toán thành công.
- `ipnurl`: đường dẫn để MoMo gửi kết quả thanh toán thông qua Instant Payment Notification (IPN).
- `url`: đường dẫn để gửi yêu cầu tới MoMo API.
- `idmomo`: Id của MoMo.*/