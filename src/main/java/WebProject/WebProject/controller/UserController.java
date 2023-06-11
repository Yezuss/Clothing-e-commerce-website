package WebProject.WebProject.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import WebProject.WebProject.service.CloudinaryService;

import WebProject.WebProject.entity.Cart;
import WebProject.WebProject.entity.User;
import WebProject.WebProject.model.Mail;
import WebProject.WebProject.service.CartService;
import WebProject.WebProject.service.CookieService;
import WebProject.WebProject.service.MailService;
import WebProject.WebProject.service.UserService;

@Controller
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	CartService cartService;

	@Autowired
	MailService mailService;

	@Autowired
	CloudinaryService cloudinaryService;

	@Autowired
	HttpSession session;

	@Autowired
	CookieService cookie;

	@GetMapping("/signin")
	public String SigInView(Model model) throws Exception {
		Cookie login_name = cookie.read("login_name");
		Cookie pass = cookie.read("pass");
		if (login_name != null)
			model.addAttribute("login_name", login_name.getValue());
		if (pass != null) {
			String decodedValue = new String(Base64.getDecoder().decode(pass.getValue()));
			model.addAttribute("pass", decodedValue);
		}

		return "signin";
	}

	@GetMapping("/signup")
	public String SignUpView(Model model) {
		return "signup";
	}

	@GetMapping("/contact")
	public String ContactView(Model model) {
		return "contact";
	}

	@GetMapping("about")
	public String AboutView(Model model) {
		return "about";
	}

	@GetMapping("blog")
	public String BlogView(Model model) {
		return "blog";
	}

	@PostMapping("/signin")
	public String SignIn(@ModelAttribute("login-name") String loginname, @ModelAttribute("password") String password,
			@RequestParam(value = "remember", defaultValue = "false") boolean remember, Model model) throws Exception {
//		User user = userService.getUserById(loginname);
		User user = userService.findByIdAndRole(loginname, "user");
		if (user != null) {
			String decodedValue = new String(Base64.getDecoder().decode(user.getPassword()));
			if (decodedValue.equals(password)) {
				if (remember == true) {
					cookie.create("user_name", user.getId(), 3);
					cookie.create("login_name", user.getId(), 3);
					cookie.create("pass", user.getPassword(), 3);
					cookie.create("remember", "remember", 3);
				} else {
					cookie.create("login_name", user.getId(), 3);
					cookie.delete("pass");
				}
				session.setAttribute("acc", user);
				List<Cart> listCart = cartService.GetAllCartByUser_id(user.getId());
				session.setAttribute("countCart", listCart.size());
				return "redirect:/home";
			} else {
				model.addAttribute("errorLogin", "Tên đăng nhập hoặc mật khẩu không chính xác!");
				return "signin";
			}
		} else {
			model.addAttribute("errorLogin", "Tên đăng nhập hoặc mật khẩu không chính xác!");
			return "signin";
		}

	}

	@PostMapping("/signup")
	public String SignUp(@ModelAttribute("username") String id, @ModelAttribute("your_email") String email,
			@ModelAttribute("fullname") String fullname, @ModelAttribute("password") String password,
			@ModelAttribute("comfirm_password") String comfirm_password, Model model) throws Exception {

		User user = userService.findByIdAndRole(id, "user");

		if (user == null) {
			String encodedValue = Base64.getEncoder().encodeToString(password.getBytes());
			String avatar = "https://haycafe.vn/wp-content/uploads/2022/02/Avatar-trang-den.png";
			User newUser = new User(id, "default", "user", encodedValue, fullname, avatar, email, null, null, null);
			userService.saveUser(newUser);
			return "redirect:/signin";
		} else {
			model.addAttribute("errorSignUp", "Tài khoản đã tồn tại!");
			return "signup";
		}
	}

	@GetMapping("/signout")
	public String SignOut(Model model) {
		session.setAttribute("acc", null);
		cookie.delete("remember");
		return "redirect:/home";
	}

	@GetMapping("/myprofile")
	public String Myprofile(Model model, HttpServletRequest request) {
		User user = (User) session.getAttribute("acc");
		String referer = request.getHeader("Referer");
		String messageChangeProfile = (String) session.getAttribute("messageChangeProfile");
		model.addAttribute("messageChangeProfile", messageChangeProfile);
		session.setAttribute("messageChangeProfile", null);
		if (user == null) {
			return "redirect:" + referer;
		} else {
			String error_change_pass = (String) session.getAttribute("error_change_pass");
			String ChangePassSuccess = (String) session.getAttribute("ChangePassSuccess");
			model.addAttribute("error_change_pass", error_change_pass);
			model.addAttribute("ChangePassSuccess", ChangePassSuccess);
			session.setAttribute("error_change_pass", null);
			session.setAttribute("ChangePassSuccess", null);
			model.addAttribute("user", user);
			return "myprofile";
		}

	}

	@PostMapping("/changepassword")
	public String ChangePassword(Model model, @ModelAttribute("current_password") String current_password,
			@ModelAttribute("new_password") String new_password,
			@ModelAttribute("confirm_password") String confirm_password, HttpServletRequest request) {
		String referer = request.getHeader("Referer");
		User user = (User) session.getAttribute("acc");
		String decodedValue = new String(Base64.getDecoder().decode(user.getPassword()));
		if (!decodedValue.equals(current_password)) {
			session.setAttribute("error_change_pass", "Current Password not correct!");
			return "redirect:/myprofile";
		} else {
			if (!new_password.equals(confirm_password)) {
				session.setAttribute("error_change_pass", "Confirm New Password not valid!");
				return "redirect:/myprofile";
			} else {
				String encodedValue = Base64.getEncoder().encodeToString(new_password.getBytes());
				user.setPassword(encodedValue);
				userService.saveUser(user);
				session.setAttribute("acc", user);
			}
		}
		session.setAttribute("ChangePassSuccess", "ChangePassSuccess");
		return "redirect:" + referer;
	}

	@PostMapping("/changeProfile")
	public String ChangeProfile(Model model, @ModelAttribute("avatar") MultipartFile avatar,
			@ModelAttribute("fullname") String fullname, @ModelAttribute("phone") String phone,
			@ModelAttribute("email") String email) throws IOException {
		User user = (User) session.getAttribute("acc");
		if (user != null) {
			if (!avatar.isEmpty()) {
				String url = cloudinaryService.uploadFile(avatar);
				user.setAvatar(url);
			}
			user.setUser_Name(fullname);
			user.setEmail(email);
			user.setPhone_Number(phone);
			userService.saveUser(user);
			session.setAttribute("acc", user);
			session.setAttribute("messageChangeProfile", "Change Success.");
			return "redirect:/myprofile";
		} else {
			return "rediect:/home";
		}
	}

	@GetMapping("/forgot")
	public String forGotView(Model model) {
		String error_forgot = (String) session.getAttribute("error_forgot");
		model.addAttribute("error_forgot", error_forgot);
		session.setAttribute("error_forgot", null);
		model.addAttribute("forgot", "Forgot Password");
		return "signin";
	}

	@PostMapping("/forgot")
	public String forGotHandel(@ModelAttribute("login-name") String login_name, Model model) throws Exception {
		User user = userService.findByIdAndRole(login_name, "user");
		if (user == null) {
			session.setAttribute("error_forgot", "UserName is not correct!");
			return "redirect:/forgot";
		} else {
			session.setAttribute("userForgot", user);
			return "redirect:/code";
		}
	}

	@GetMapping("/code")
	public String codeView(Model model) throws Exception {
		User userForgot = (User) session.getAttribute("userForgot");
		String noSendEmail = (String) session.getAttribute("noSendEmail");
		if (noSendEmail == null) {
			int code = (int) Math.floor(((Math.random() * 899999) + 100000));
			Mail mail = new Mail();
			mail.setMailFrom("haovo1512@gmail.com");
			mail.setMailTo(userForgot.getEmail());
			mail.setMailSubject("For got Password");
			mail.setMailContent("Your code is: " + code);
			mailService.sendEmail(mail);
			System.out.println(code);
			session.setAttribute("code", code);
		}
		session.setAttribute("noSendEmail", null);
		String error_code = (String) session.getAttribute("error_code");
		model.addAttribute("error_code", error_code);
		session.setAttribute("error_code", null);
		model.addAttribute("forgot", "Forgot Password");
		model.addAttribute("sendcode", "sendcode");
		return "signin";
	}

	@PostMapping("/code")
	public String codeHandel(@ModelAttribute("code_input") int code_input, Model model) throws Exception {
		int code = (int) session.getAttribute("code");
		if (code == code_input) {
			session.setAttribute("code", null);
			return "redirect:/newpass";
		} else {
			session.setAttribute("noSendEmail", "noSendEmail");
			session.setAttribute("error_code", "Code is not correct!");
			return "redirect:/code";
		}

	}

	@GetMapping("/newpass")
	public String newPassView(Model model) {
		String error_newpass = (String) session.getAttribute("error_newpass");
		session.setAttribute("error_newpass", null);
		model.addAttribute("error_newpass", error_newpass);
		model.addAttribute("forgot", "Forgot Password");
		model.addAttribute("sendcode", "sendcode");
		model.addAttribute("changepass", "changepass");
		return "signin";
	}

	@PostMapping("newpass")
	public String newPassHandel(@ModelAttribute("new_pass") String new_pass,
			@ModelAttribute("confirm_new_pass") String confirm_new_pass, Model model) throws Exception {
		if (new_pass.equals(confirm_new_pass)) {
			String encodedValue = Base64.getEncoder().encodeToString(new_pass.getBytes());
			User userForgot = (User) session.getAttribute("userForgot");
			userForgot.setPassword(encodedValue);
			userService.saveUser(userForgot);
			return "redirect:/signin";
		} else {
			session.setAttribute("error_newpass", "Confirm New Password not valid!");
			return "redirect:/newpass";
		}

	}

//	@GetMapping("/signin-google")
//	public String SignInGoogle(@ModelAttribute("code") String code, Model model) throws Exception {
//		System.out.println("=========" + code);
//		String accessToken = getToken(code);
//		System.out.println(accessToken);
//		AccountGoogle accountGoogle = getUserInfo(accessToken);
//		System.out.println(accountGoogle);
//		return "redirect:/home";
//	}
}
