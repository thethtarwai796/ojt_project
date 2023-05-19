package com.shopaholic.reposistory;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.shopaholic.entity.Customer;

@EnableJpaRepositories
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
	
	public Customer findByEmail(String email);

	public Customer findByCname(String cname);
}
