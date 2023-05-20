package com.shopaholic.reposistory;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.shopaholic.entity.BestSell;
import com.shopaholic.entity.Orders;
import com.shopaholic.entity.OrderDetail;


@EnableJpaRepositories
public interface OrderRepo extends JpaRepository<Orders, Integer>{
	

	@Query(value="select max(order_number) from orders",nativeQuery = true)
		public int getOrderNumber();
	
	@Query(value="select * from orders where status=:status",nativeQuery = true)
	public List<Orders> findByStatus(String status);
	
	@Query(value="select count(order_number) from orders",nativeQuery = true)
		public int countTotalOrder();
	
	@Query(value="select count(order_number) from orders where status='finished'",nativeQuery = true)
	public int countShippedOrder();
	
	@Query(value="select count(order_number) from orders where status='pending'",nativeQuery = true)
	public int countPendingOrder();
	

	@Query(value="select count(order_number) from orders where order_date=:current",nativeQuery = true)
	public int countTodayOrder(LocalDate current);
	
	
	@Modifying(clearAutomatically=true)
	@Transactional
	@Query("update Orders o set o.status='finished',o.finishDate=:date where o.orderNumber=:orderNo")
	public void updateStatus(int orderNo,LocalDate date);
	
	@Query(value="select sum(total) from orders o where order_date>='2023-01-01' group by EXTRACT(month from order_date) order by EXTRACT(month from order_date) ",nativeQuery = true)
	public Integer[] getTotalSaleByMonth();
	
	@Query(value="select sum(total) as total from orders o",nativeQuery = true)
	public Integer getTotalSale();
	
}


