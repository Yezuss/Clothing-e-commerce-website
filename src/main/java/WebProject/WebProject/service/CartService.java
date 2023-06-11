package WebProject.WebProject.service;

import java.util.List;

import WebProject.WebProject.entity.Cart;

public interface CartService {
	
	void deleteById(int id);
	
	List<Cart> GetAllCartByUser_id(String user_id);
	
	void saveCart(Cart cart);
	
}
