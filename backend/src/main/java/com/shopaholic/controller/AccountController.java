package com.shopaholic.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopaholic.entity.ChangePwd;
import com.shopaholic.entity.Customer;
import com.shopaholic.entity.LoginReq;
import com.shopaholic.service.CustomerService;

@RestController
public class AccountController {

	@Autowired
	CustomerService customerService;

	@Autowired
	PasswordEncoder pwEncoder;

	@PostMapping("/login")
	public ResponseEntity<Customer> login(
			@Valid @RequestBody LoginReq lognReq
	) {
		Customer user = customerService
				.checkLoginUser(lognReq.getGmail(), lognReq.getPassword());
		if (user == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok().body(user);
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody Customer user) {
		Customer createdUser = customerService.create(user);
		if (createdUser == null) {
			return ResponseEntity.badRequest()
					.body("User with same gmail already exists!");
		}
		return ResponseEntity.ok().body(createdUser);
	}

	// @GetMapping("/logout")
	// public Boolean logout(HttpSession session) throws IOException {
	// session.invalidate();
	// return true;
	// }

	@GetMapping("/profile")
	public ResponseEntity<Customer> getProfile(@RequestParam int id) {
		Customer user = customerService.get(id);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(user);
	}

	@PostMapping("/changePwd")
	public ResponseEntity<Object> changePassword(
			@Valid @RequestBody ChangePwd changePwd
	) throws IOException {
		Customer user = customerService.get(changePwd.getOri_id());
		if (user == null) {
			return new ResponseEntity<Object>(
					new Exception("Something wrong"), HttpStatus.CONFLICT
			);
		}
		if (!changePwd.getCon_new_pwd().equals(changePwd.getNew_pwd())) {
			return new ResponseEntity<Object>(
					new Exception("Confirm Password does not match"),
					HttpStatus.CONFLICT
			);
		}
		if (!pwEncoder
				.matches(changePwd.getCurrent_pwd(), user.getPassword())) {
			return new ResponseEntity<Object>(
					new Exception("Current Password does not match"),
					HttpStatus.CONFLICT
			);
		}
		customerService.updatePwd(user.getCid(), changePwd.getNew_pwd());
		return ResponseEntity.ok().body(user);
	}

	@PutMapping("/profile/update")
	public ResponseEntity<Customer> updateProfile(@Valid @RequestBody Customer user) {
		if (user.getCid() <= 0) {
			return ResponseEntity.badRequest().build();
		}
		Customer updatedUser = customerService.update(user.getCid(), user);
		if (updatedUser == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(updatedUser);
	}

}
