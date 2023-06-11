package WebProject.WebProject.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import WebProject.WebProject.entity.Cart;
import WebProject.WebProject.repository.CartRepository;
import WebProject.WebProject.service.CartService;

@Service
public class CartServicempl implements CartService{

	@Autowired
	CartRepository cartRepository;
	/**
	 *
	 */
	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		cartRepository.deleteById(id);
	}
	@Override
	public List<Cart> GetAllCartByUser_id(String user_id) {
		// TODO Auto-generated method stub
		return cartRepository.findAllByUser_id(user_id);
	}
	@Override
	public void saveCart(Cart cart) {
		// TODO Auto-generated method stub
		cartRepository.save(cart);
	}
}
