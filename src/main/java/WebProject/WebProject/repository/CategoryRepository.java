package WebProject.WebProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import WebProject.WebProject.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Long> {
	
	Category getById(int id);

}
