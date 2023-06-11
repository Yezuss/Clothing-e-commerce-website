package WebProject.WebProject;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cloudinary.Cloudinary;


@SpringBootApplication
public class WebProjectApplication implements CommandLineRunner{
	@Value("${cloudinary.cloud_name}")
	private String cloudName;

	@Value("${cloudinary.api_key}")
	private String apiKey;

	@Value("${cloudinary.api_secret}")
	private String apiSecret;
	
	public static void main(String[] args) {
		SpringApplication.run(WebProjectApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
	}
	@Bean
	public Cloudinary cloudinaryConfig() {
		Cloudinary cloudinary = null;
		Map<String, String> config = new HashMap<String, String>();
		config.put("cloud_name", cloudName);
		config.put("api_key", apiKey);
		config.put("api_secret", apiSecret);
		cloudinary = new Cloudinary(config);
		return cloudinary;
	}
}
/*
 * Lớp này là một ứng dụng Spring Boot (@SpringBootApplication). Nó triển khai
 * CommandLineRunner, nhưng không có bất kỳ mã thực thi nào trong phương thức
 * run của nó.
 * 
 * Đoạn code cũng định nghĩa một Bean Spring là cloudinaryConfig để cấu hình thư
 * viện Cloudinary. Cloudinary là một dịch vụ lưu trữ và quản lý hình ảnh trực
 * tuyến. Phương thức này tạo ra một đối tượng Cloudinary được cấu hình bằng
 * cách sử dụng các giá trị của cloudName, apiKey và apiSecret được định nghĩa ở
 * trên. Đối tượng Cloudinary này được trả về để được sử dụng trong toàn bộ ứng
 * dụng Spring Boot.
 */

