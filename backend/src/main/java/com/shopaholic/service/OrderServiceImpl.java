package com.shopaholic.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopaholic.entity.BestSell;
import com.shopaholic.entity.OrderDetail;
import com.shopaholic.entity.OrderList;
import com.shopaholic.reposistory.OrderRepo;



@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderRepo orderRepo;

//	@Autowired
//	CustomerRepo customerRepo;

	@Override
	public int countTotalOrder() {
		return orderRepo.countTotalOrder();
	}


//	

	@Override
	public int countShippedOrder() {
		// TODO Auto-generated method stub
		return orderRepo.countShippedOrder();
	}

	@Override
	public int countUnshippedOrder() {
		// TODO Auto-generated method stub
		return orderRepo.countPendingOrder();
	}

	@Override
	public List<OrderDetail> getAllOrderDetail() {
		return orderRepo.findAll();
	}

	@Override
	public List<OrderDetail> create(List<OrderDetail> order) {
		return orderRepo.saveAll(order);
	}

	@Override
	public void updateStatus(int order_number,LocalDate date) {
		 orderRepo.updateStatus(order_number,date);
	}

	@Override
	public List<OrderList> getOrderByStatus(String status) {
		return orderRepo.findByStatus(status);
	}

	@Override
	public List<OrderList> getAllOrders() {
		return orderRepo.getAllOrderList();
	}

	@Override
	public List<OrderDetail> getOrderDetailByOrderNo(int orderNumber) {
		return orderRepo.findByOrderNumber(orderNumber);
	}


	@Override
	public Integer getTotalSale() {
		return orderRepo.getTotalSale();
	}
	
	@Override
	public Integer[] getTotalSaleByCategory() {
		return orderRepo.getTotalSaleByCategory();
	}


	@Override
	public Integer[] getTotalSaleByMonth() {
			return orderRepo.getTotalSaleByMonth();
	}


	@Override
	public List<BestSell> getBestSells() {
		return orderRepo.getBestSell();
	}


	@Override
	public OrderList getOneOrder(int order_number) {
		return orderRepo.getOneOrderList(order_number);
	}


	

}
