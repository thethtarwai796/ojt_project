package com.shopaholic.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.shopaholic.entity.BestSell;
import com.shopaholic.entity.OrderDetail;


public interface OrderDetailService {
	
	public List<OrderDetail> getAllOrderDetail();
		
	public Integer[] getTotalSaleByCategory();
			
	public List<OrderDetail> create(List<OrderDetail> order);
					
	public List<OrderDetail> getOrderDetailByOrderNo(int orderNumber);
	
	public List<BestSell> getBestSells();

}
