package utils;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

public class Decode {

	public static String encode(String key, String data) {
	    try {
	        byte[] keySect = "nqQiVSgDMy809JoPF6OzP5OdBUB550Y4".getBytes();
	        HmacUtils hm256 = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, keySect);
	        //hm256 object can be used again and again
	        String hmac = hm256.hmacHex(data);
	        return hmac;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }	    
	    return null;
	}
}
/*
 * một phương thức tĩnh encode nhận hai đối số kiểu chuỗi (String), key và data.
 * Phương thức này sử dụng thư viện org.apache.commons.codec.digest để tính toán
 * HMAC SHA-256 của data sử dụng key như là khóa bí mật. Giá trị HMAC tính toán
 * được được trả về dưới dạng chuỗi Hexadecimal. Nếu có lỗi trong quá trình tính
 * toán, phương thức sẽ trả về null và in ra stacktrace của lỗi.
 */