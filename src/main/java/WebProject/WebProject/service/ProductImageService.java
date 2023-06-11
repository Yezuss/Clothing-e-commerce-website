package WebProject.WebProject.service;

import WebProject.WebProject.entity.ProductImage;

public interface ProductImageService {

	void save(ProductImage productImage);

	void deleteById(int id);

}
