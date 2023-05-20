package com.shopaholic.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shopaholic.entity.Orders;
import com.shopaholic.entity.OrderDetail;
import com.shopaholic.entity.Product;
import com.shopaholic.reposistory.OrderRepo;
import com.shopaholic.reposistory.ProductRepo;
import com.shopaholic.service.CategoryService;
import com.shopaholic.service.OrderDetailService;
import com.shopaholic.service.OrderService;
import com.shopaholic.service.ProductService;
import com.shopaholic.service.StorageService;

@RestController
public class OrderController {
	
	@Autowired
	ProductService productService;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderDetailService orderDetailService;
	
	
	public static int orderNumber=33;
	
	//-----------------------Order Create-----------
	
	@PostMapping("/order/updateStock")
	public ResponseEntity<?> updateStock(@Valid @RequestBody List<OrderDetail> orderList) {
		for(OrderDetail orderDetail:orderList) {
			productService.updateProductStock(orderDetail.getProduct().getPid(),orderDetail.getQty()) ;
			} 
		return ResponseEntity.ok().build();
	}
	@PostMapping("/order/createDetail")
	public ResponseEntity<?> createOrderDetail(@Valid @RequestBody List<OrderDetail> orderList) {
		return ResponseEntity.ok().body(orderDetailService.create(orderList));
			}
	
	@PostMapping("/order/create")
	public ResponseEntity<?> createOrder(@Valid @RequestBody Orders order) {
		return ResponseEntity.ok().body(orderService.create(order));
			}
	
	
	

	
}
