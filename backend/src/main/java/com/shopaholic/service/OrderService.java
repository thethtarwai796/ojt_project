package com.shopaholic.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.shopaholic.entity.BestSell;
import com.shopaholic.entity.OrderDetail;
import com.shopaholic.entity.OrderList;


public interface OrderService {
	
	public int countTotalOrder();
	
	public int countShippedOrder();

	public int countUnshippedOrder();
	
	public List<OrderDetail> getAllOrderDetail();
	
	public Integer getTotalSale();
	
	public Integer[] getTotalSaleByCategory();
	
	public Integer[] getTotalSaleByMonth();
	
	public List<OrderDetail> create(List<OrderDetail> order);
	
	public void updateStatus(int order_number,LocalDate date);
	

	public List<OrderList> getOrderByStatus(String status);
	
	public List<OrderList> getAllOrders();
	
	public OrderList getOneOrder(int order_number);
	
	public List<OrderDetail> getOrderDetailByOrderNo(int orderNumber);
	
	public List<BestSell> getBestSells();

}
