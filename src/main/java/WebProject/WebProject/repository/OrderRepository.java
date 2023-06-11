package WebProject.WebProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import WebProject.WebProject.entity.Order;
import WebProject.WebProject.entity.Product;

public interface OrderRepository extends JpaRepository<Order,Integer>{
	List<Order> findAllByUser_id(String user_id);

	Order findById(int id);
	
	@Query(value="Select * From `order` o ORDER BY o.id DESC LIMIT 5;",nativeQuery = true)
	List<Order> findTop5RecentOrder();
	
	@Query(value="Select distinct o.user_id From `order` o ORDER BY o.id DESC LIMIT 5;",nativeQuery = true)
	List<String> findTop5RecentCustomer();
	
	Page<Order> findAll(Pageable pageable);

	void deleteById(int id);
	
	
	@Query(value="select * from `order` o where o.payment_method = ?1",nativeQuery = true)
	List<Order> findAllByPayment_Method(String payment_Method);
	
	@Query(value="Select * From `order` o where o.payment_method = ?1 ORDER BY o.id DESC LIMIT 5;",nativeQuery = true)
	List<Order> findTop5OrderByPaymentMethod(String payment_method);
	
}
