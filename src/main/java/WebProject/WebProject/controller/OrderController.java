package WebProject.WebProject.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.fasterxml.jackson.databind.ObjectMapper;

import WebProject.WebProject.entity.Cart;
import WebProject.WebProject.entity.Order;
import WebProject.WebProject.entity.Order_Item;
import WebProject.WebProject.entity.Product;
import WebProject.WebProject.entity.User;
import WebProject.WebProject.service.CartService;
import WebProject.WebProject.service.OrderService;
import WebProject.WebProject.service.Order_ItemService;
import WebProject.WebProject.service.ProductService;
import momo.MomoModel;
import momo.ResultMoMo;
import utils.Constant;
import utils.Decode;

@Controller
public class OrderController {

	@Autowired
	CartService cartService;

	@Autowired
	ProductService productService;

	@Autowired
	Order_ItemService order_ItemService;

	@Autowired
	OrderService orderService;

	@Autowired
	HttpSession session;

	@GetMapping("checkout")
	public String CheckOutView(Model model) {
		User user = (User) session.getAttribute("acc");
		if (user == null) {
			session.setAttribute("AddToCartErr", "Vui lòng đăng nhập trước khi thực hiện thao tác!");
			return "redirect:/home";
		} else {
			List<Cart> Cart = cartService.GetAllCartByUser_id(user.getId());
			if (!Cart.isEmpty()) {
				String a = session.getAttribute("Total").toString();
				int Total = Integer.parseInt(a);
				System.out.println(Total);
				model.addAttribute("Total", a);
				@SuppressWarnings("unchecked")
				List<Cart> listCart = (List<Cart>) session.getAttribute("listCart");
				model.addAttribute("listCart", listCart);
				return "checkout";
			}
			else {
				session.setAttribute("CartIsEmpty", "CartIsEmpty");
				return "redirect:/cart";
			}
		}
	}

	@PostMapping("checkout")
	public String CheckOut(@ModelAttribute("fullname") String fullname, @ModelAttribute("country") String country,
			@ModelAttribute("address") String address, @ModelAttribute("phone") String phone,
			@ModelAttribute("email") String email, @ModelAttribute("note") String note,
			@RequestParam(value = "payOndelivery", defaultValue = "false") boolean payOndelivery,
			@RequestParam(value = "payWithMomo", defaultValue = "false") boolean payWithMomo, Model model,
			HttpServletResponse resp) throws Exception {

		long millis = System.currentTimeMillis();
		Date booking_date = new java.sql.Date(millis);
		@SuppressWarnings("unchecked")
		List<Cart> listCart = (List<Cart>) session.getAttribute("listCart");
		User user = (User) session.getAttribute("acc");
		String a = session.getAttribute("Total").toString();
		int Total = Integer.parseInt(a);
		String status = "Pending";
		String payment_method = null;
		if (payOndelivery == true) {
			payment_method = "Payment on delivery";
		} else {
			payment_method = "Payment with momo";
		}
		Order newOrder = new Order();
		newOrder.setTotal(Total);
		newOrder.setAddress(address);
		newOrder.setBooking_Date(booking_date);
		newOrder.setCountry(country);
		newOrder.setEmail(email);
		newOrder.setFullname(fullname);
		newOrder.setNote(note);
		newOrder.setPayment_Method(payment_method);
		newOrder.setPhone(phone);
		newOrder.setStatus(status);
		newOrder.setUser(user);
		if (payment_method == "Payment with momo") {
			session.setAttribute("newOrder", newOrder);
			ObjectMapper mapper = new ObjectMapper();
			int code = (int) Math.floor(((Math.random() * 89999999) + 10000000));
			String orderId = Integer.toString(code);
			MomoModel jsonRequest = new MomoModel();
			jsonRequest.setPartnerCode(Constant.IDMOMO);
			jsonRequest.setOrderId(orderId);
			jsonRequest.setStoreId(orderId);
			jsonRequest.setRedirectUrl(Constant.redirectUrl);
			jsonRequest.setIpnUrl(Constant.ipnUrl);
			jsonRequest.setAmount(String.valueOf(Total));
			jsonRequest.setOrderInfo("Thanh toán Male Fashion.");
			jsonRequest.setRequestId(orderId);
			jsonRequest.setOrderType(Constant.orderType);
			jsonRequest.setRequestType(Constant.requestType);
			jsonRequest.setTransId("1");
			jsonRequest.setResultCode("200");
			jsonRequest.setMessage("");
			jsonRequest.setPayType(Constant.payType);
			jsonRequest.setResponseTime("300000");
			jsonRequest.setExtraData("");
			String decode = "accessKey=" + Constant.accessKey + "&amount=" + jsonRequest.amount + "&extraData="
					+ jsonRequest.extraData + "&ipnUrl=" + Constant.ipnUrl + "&orderId=" + orderId + "&orderInfo="
					+ jsonRequest.orderInfo + "&partnerCode=" + jsonRequest.getPartnerCode() + "&redirectUrl="
					+ Constant.redirectUrl + "&requestId=" + jsonRequest.getRequestId() + "&requestType="
					+ Constant.requestType;
			String signature = Decode.encode(Constant.serectkey, decode);
			jsonRequest.setSignature(signature);
			String json = mapper.writeValueAsString(jsonRequest);
			HttpClient client = HttpClient.newHttpClient();
			ResultMoMo res = new ResultMoMo();

			try {
				HttpRequest request = HttpRequest.newBuilder().uri(new URI(Constant.Url))
						.POST(HttpRequest.BodyPublishers.ofString(json)).headers("Content-Type", "application/json")
						.build();
				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
				res = mapper.readValue(response.body(), ResultMoMo.class);
			} catch (InterruptedException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (res == null) {
				session.setAttribute("error_momo", "Thanh toán thất bại");
				return "redirect:/home";
			} else {
//					return "redirect:/shop";
//				resp.sendRedirect(res.payUrl);
				return "redirect:" + res.payUrl;
			}
		} else {
			orderService.saveOrder(newOrder);
			List<Order> listOrder = orderService.getAllOrderByUser_Id(user.getId());
			newOrder = listOrder.get(listOrder.size() - 1);
			for (Cart y : listCart) {
				Product product = y.getProduct();
				product.setQuantity(product.getQuantity() - y.getCount());
				product.setSold(product.getSold() + y.getCount());
				productService.saveProduct(product);
				Order_Item newOrder_Item = new Order_Item();
				newOrder_Item.setCount(y.getCount());
				newOrder_Item.setOrder(newOrder);
				newOrder_Item.setProduct(y.getProduct());
				order_ItemService.saveOrder_Item(newOrder_Item);
				cartService.deleteById(y.getId());
			}
			listOrder = orderService.getAllOrderByUser_Id(user.getId());
			newOrder = listOrder.get(listOrder.size() - 1);
			session.setAttribute("order", newOrder);
			return "redirect:/invoice";
		}
	}

	@GetMapping("paywithmomo")
	public String PayWithMomoGet(@ModelAttribute("message") String message, Model model) {
		if (!message.equals("Successful.")) {
			session.setAttribute("error_momo", "Thanh toán không thành công!");
			return "redirect:/home";
		} else {
			@SuppressWarnings("unchecked")
			List<Cart> listCart = (List<Cart>) session.getAttribute("listCart");
			User user = (User) session.getAttribute("acc");
			Order newOrder = (Order) session.getAttribute("newOrder");
			orderService.saveOrder(newOrder);
			List<Order> listOrder = orderService.getAllOrderByUser_Id(user.getId());
			newOrder = listOrder.get(listOrder.size() - 1);
			for (Cart y : listCart) {
				Product product = y.getProduct();
				product.setQuantity(product.getQuantity() - y.getCount());
				product.setSold(product.getSold() + y.getCount());
				productService.saveProduct(product);
				Order_Item newOrder_Item = new Order_Item();
				newOrder_Item.setCount(y.getCount());
				newOrder_Item.setOrder(newOrder);
				newOrder_Item.setProduct(y.getProduct());
				order_ItemService.saveOrder_Item(newOrder_Item);
				cartService.deleteById(y.getId());
			}
			listOrder = orderService.getAllOrderByUser_Id(user.getId());
			newOrder = listOrder.get(listOrder.size() - 1);
			session.setAttribute("order", newOrder);
			System.out.println(newOrder);
			return "redirect:/invoice";
		}
	}

	@GetMapping("invoice")
	public String Invoice(Model model) {
		Order order = (Order) session.getAttribute("order");
		String invoiceView = (String) session.getAttribute("invoiceView");
		session.setAttribute("invoiceView", null);
		List<Order_Item> listOrder_Item = order_ItemService.getAllByOrder_Id(order.getId());
		model.addAttribute("invoiceView", invoiceView);
		model.addAttribute("listOrder_Item", listOrder_Item);
		model.addAttribute("order", order);
		return "invoice";
	}

	@GetMapping("/invoice/{id}")
	public String InvoiceView(@PathVariable int id, Model model, HttpServletRequest request) {
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		Order order = orderService.findById(id);
		session.setAttribute("order", order);
		session.setAttribute("invoiceView", "view");
		return "redirect:/invoice";
	}

	@GetMapping("/myhistory")
	public String Myhistory(Model model, HttpServletRequest request) {
		String referer = request.getHeader("Referer");
		User user = (User) session.getAttribute("acc");
		if (user == null) {
			return "redirect:" + referer;
		} else {
			List<Order> listOrder = orderService.getAllOrderByUser_Id(user.getId());
			Collections.reverse(listOrder);
			model.addAttribute("listOrder", listOrder);
			System.out.println(listOrder);
			for (Order y : listOrder) {
				System.out.println(y.getOrder_Item());
			}
		}
		return "myhistory";
	}
}
