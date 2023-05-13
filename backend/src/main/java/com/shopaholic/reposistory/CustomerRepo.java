package com.shopaholic.reposistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.shopaholic.entity.Categories;
import com.shopaholic.entity.Customer;

@EnableJpaRepositories
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
	
	
}
