package WebProject.WebProject.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data // lombok giúp generate các hàm constructor, get, set v.v.
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "count", nullable = false)
	private int count;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Product product;
}
