package com.shopaholic.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopaholic.entity.BestSell;
import com.shopaholic.entity.OrderDetail;
import com.shopaholic.reposistory.OrderDetailRepo;



@Service
public class OrderDetailServiceImpl implements OrderDetailService {
	
	@Autowired
	OrderDetailRepo orderDetailRepo;
	
	@Override
	public List<OrderDetail> getAllOrderDetail() {
		return orderDetailRepo.findAll();
	}

	@Override
	public List<OrderDetail> create(List<OrderDetail> order) {
		return orderDetailRepo.saveAll(order);
		
	}

	
	@Override
	public List<OrderDetail> getOrderDetailByOrderNo(int orderNumber) {
		return orderDetailRepo.findByOrderNumber(orderNumber);
	}


	
	@Override
	public Integer[] getTotalSaleByCategory() {
		return orderDetailRepo.getTotalSaleByCategory();
	}


	
	@Override
	public List<BestSell> getBestSells() {
		return orderDetailRepo.getBestSell();
	}


	
}
