package com.shopaholic.service;

import java.util.List;
import com.shopaholic.entity.Customer;

public interface CustomerService {

	

	public List<Customer> getAll();

	public Customer get(int id);

	public Customer create(Customer user);

	public Customer update(int id, Customer user);

	public boolean delete(int id);

	public Customer checkLoginUser(String gmail, String password);
	
	public void updatePwd(int id,String newPwd);

	
	

}
