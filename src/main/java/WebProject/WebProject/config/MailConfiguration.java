package WebProject.WebProject.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration 
{
	@Autowired
	private Environment environment;
	
	@Bean
	public JavaMailSender getMailSender()
	{
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost(environment.getProperty("spring.mail.host"));
		javaMailSender.setPort(Integer.valueOf(environment.getProperty("spring.mail.port")));
		javaMailSender.setUsername(environment.getProperty("spring.mail.username"));
		javaMailSender.setPassword(environment.getProperty("spring.mail.password"));
		
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.starttls.enable", "true");
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.transport.protocol", "smtp");
		javaMailProperties.put("mail.debug", "true");
		javaMailProperties.put("mail.smtp.ssl.trust", "*");
		
		javaMailSender.setJavaMailProperties(javaMailProperties);
		return javaMailSender;
	}
}/*
	 * Đoạn code này định nghĩa một lớp `MailConfiguration` với một phương thức
	 * 
	 * `getMailSender` trả về một đối tượng `JavaMailSender` được cấu hình để gửi
	 * email.
	 * 
	 * Phương thức `getMailSender` sử dụng `@Autowired` để tiêm môi trường
	 *  Spring
	 * (`Environment`) vào lớp.
	 * 
	 * Đối tượng `JavaMailSenderImpl` được tạo ra và cấu hình với các giá trị được
	 * lấy từ môi trường Spring thông qua phương thức `getProperty()`. Cụ thể là
	 * `host`, `port`, `username`, và `password` được định nghĩa trong các tệp cấu
	 * hình.
	 * 
	 * Sau đó, một đối tượng `Properties` được tạo ra để cấu hình các thuộc tính
	 * khác cho gửi email, chẳng hạn như `starttls.enable`, `auth`,
	 * `transport.protocol`, `debug`, và `smtp.ssl.trust`.
	 * 
	 * Các thuộc tính này được thiết lập bằng cách sử dụng phương thức `put()` của
	 * đối tượng `Properties`.
	 * 
	 * Cuối cùng, các thuộc tính được đặt được đặt trên đối tượng
	 * `JavaMailSenderImpl` và trả về cho phương thức `getMailSender`.
	 */