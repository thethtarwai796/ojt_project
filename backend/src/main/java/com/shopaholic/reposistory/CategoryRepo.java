package com.shopaholic.reposistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.shopaholic.entity.Categories;


@EnableJpaRepositories
public interface CategoryRepo extends JpaRepository<Categories, Integer> {
	
	@Query(value="select sum(stock_Qty) from product group by category_id",nativeQuery = true)
	public List<Integer> getStockByCategory();

}