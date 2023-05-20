package com.shopaholic.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.shopaholic.entity.BestSell;
import com.shopaholic.entity.Orders;
import com.shopaholic.entity.OrderDetail;


public interface OrderService {
	
	public int countTotalOrder();
	
	public int countShippedOrder();

	public int countUnshippedOrder();
	
	public int countTodayOrder(LocalDate current);
	
	public Integer getTotalSale();
		
	public Integer[] getTotalSaleByMonth();
	
	public int getOrderNumber();
	
	public Orders create(Orders order);
	
	public void updateStatus(int order_number,LocalDate date);
		
	public List<Orders> getAllOrders();
	
	public List<Orders> getOrderByStatus(String status);
	
	public Orders getOneOrder(int order_number);
		
}
