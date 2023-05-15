package com.shopaholic.reposistory;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;


import com.shopaholic.entity.Categories;
import com.shopaholic.entity.OrderList;
import com.shopaholic.entity.Product;

@EnableJpaRepositories
public interface ProductRepo extends JpaRepository<Product, Integer> {

	public List<Product> findByCategory(Categories category);
	
	
	@Query("SELECT p FROM Product p WHERE p.pname LIKE CONCAT('%',:pname,'%')")
	List<Product> searchProduct(@Param("pname") String pname);
	
	@Modifying(clearAutomatically=true)
	@Transactional
	@Query("update Product set stockQty=stockQty-(:qty) WHERE pid=:pid")
	Integer updateStock(Integer pid,Integer qty);
	
	
	
	
}
