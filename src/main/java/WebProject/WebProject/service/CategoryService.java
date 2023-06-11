package WebProject.WebProject.service;

import java.util.List;

import WebProject.WebProject.entity.Category;

public interface CategoryService {
	
	Category saveCategory(Category category);

	Category getCategoryById(int id);

	Category updateCategory(Category category);
	
	List<Category> findAll();

	void deleteCategoryId(int id);
}
