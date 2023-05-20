package com.shopaholic.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopaholic.entity.Orders;
import com.shopaholic.reposistory.OrderRepo;



@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderRepo orderRepo;

	@Override
	public int countTotalOrder() {
		return orderRepo.countTotalOrder();
	}

	@Override
	public int countShippedOrder() {
		return orderRepo.countShippedOrder();
	}

	@Override
	public int countUnshippedOrder() {
		// TODO Auto-generated method stub
		return orderRepo.countPendingOrder();
	}
	
	@Override
	public int countTodayOrder(LocalDate current) {
		return orderRepo.countTodayOrder(current);
	}

	@Override
	public Orders create(Orders order) {
		return orderRepo.save(order);
		
	}

	@Override
	public void updateStatus(int order_number,LocalDate date) {
		 orderRepo.updateStatus(order_number,date);
	}

	@Override
	public List<Orders> getOrderByStatus(String status) {
		return orderRepo.findByStatus(status);
	}

	@Override
	public List<Orders> getAllOrders() {
		return orderRepo.findAll();
	}


	@Override
	public Integer getTotalSale() {
		return orderRepo.getTotalSale();
	}


	@Override
	public Integer[] getTotalSaleByMonth() {
			return orderRepo.getTotalSaleByMonth();
	}


	@Override
	public Orders getOneOrder(int order_number) {
		return orderRepo.findById(order_number).orElse(null);
	}


	@Override
	public int getOrderNumber() {
		return orderRepo.getOrderNumber();
	}


	

}
