package com.shopaholic.service;

import java.util.List;

import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopaholic.entity.Customer;
import com.shopaholic.reposistory.CustomerRepo;


@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepo customerRepo;

//	@Autowired
//	PasswordEncoder pwEncoder;

	@Override
	public Customer create(Customer user) {
		// Check IF user with same gmail exists
		Customer tempGmailCheckUser = customerRepo.findByEmail(user.getEmail());
		if (tempGmailCheckUser != null) {
			return null;
		}
		// Create User
		
		//user.setPassword(pwEncoder.encode(user.getPassword()));
		return customerRepo.save(user);
	}

	@Override
	public Customer get(int id) {
		return customerRepo.findById(id).orElse(null);
	}

	@Override
	public List<Customer> getAll() {
		return customerRepo.findAll();
	}

	@Override
	public Customer update(int id, Customer user) {
		Customer toUpdateUser = this.get(id);
		if (toUpdateUser == null) {
			return null;
		}
		toUpdateUser.setCname(user.getCname());
		return customerRepo.save(toUpdateUser);
	}

	@Override
	public boolean delete(int id) {
		Customer user = this.get(id);
		if (user == null) {
			return false;
		}
		customerRepo.deleteById(id);
		return true;
	}

	@Override
	public Customer checkLoginUser(String email, String password) {
		Customer user = customerRepo.findByEmail(email);
		if (user == null) {
			return null;
		}
//		if (!pwEncoder.matches(password, user.getPassword())) {
//			return null;
//		}
		if(!password.contentEquals(user.getPassword())) {
			return null;
		}
		return customerRepo.save(user);
	}

	
	@Override
	public void updatePwd(int id, String newPwd) {

		Customer toUpdateUserPwd = get(id);
		if (toUpdateUserPwd != null) {
			//toUpdateUserPwd.setPassword(pwEncoder.encode(newPwd));
			customerRepo.save(toUpdateUserPwd);
			System.out.println("pwd updated");
		}

	}

	

}

