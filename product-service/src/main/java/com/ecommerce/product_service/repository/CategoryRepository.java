package com.ecommerce.product_service.repository;

import com.ecommerce.product_service.event.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByParentId(Long parentId);
    boolean existsByName(String name);
}
