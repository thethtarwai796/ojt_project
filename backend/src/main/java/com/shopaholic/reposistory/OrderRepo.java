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
import com.shopaholic.entity.OrderDetail;
import com.shopaholic.entity.OrderList;



@EnableJpaRepositories
public interface OrderRepo extends JpaRepository<OrderDetail, Integer>{
	
	@Query("select new com.shopaholic.entity.OrderList(orderNumber,customer.cid,customer.cname,status,sum(qty*product.price) as total,orderDate) from OrderDetail where status=:status group by orderNumber")
	public List<OrderList> findByStatus(String status);
	
	@Query(value="select count(distinct order_number) from order_detail",nativeQuery = true)
		public int countTotalOrder();
	
	@Query(value="select count(distinct order_number) from order_detail where status='finished'",nativeQuery = true)
	public int countShippedOrder();
	
	@Query(value="select count(distinct order_number) from order_detail where status='pending'",nativeQuery = true)
	public int countPendingOrder();
	
//	@Query(value="select count(distinct order_number) from order_detail where order_date=:currentDate",nativeQuery = true)
//	public int countTodayOrder(@Param("currentDate")LocalDate current);
	
//	@Query(value="select count(distinct order_number) from order_detail where order_date='2023-03-28' and status='pending'",nativeQuery = true)
//	public int countTodayOrder(LocalDate current);
	
	
	@Query("select new com.shopaholic.entity.OrderList(orderNumber,customer.cid,customer.cname,status,sum(qty*product.price) as total,orderDate) from OrderDetail group by orderNumber")
	public List<OrderList> getAllOrderList();
	
	@Query("select new com.shopaholic.entity.OrderList(orderNumber,customer.cid,customer.cname,status,sum(qty*product.price) as total,orderDate) from OrderDetail where orderNumber=:order_number group by orderNumber")
	public OrderList getOneOrderList(int order_number);
	
	public List<OrderDetail> findByOrderNumber(int orderNumer);
	
	@Modifying(clearAutomatically=true)
	@Transactional
	@Query("update OrderDetail o set o.status='finished',o.finishDate=:date where o.orderNumber=:orderNo")
	public void updateStatus(int orderNo,LocalDate date);
	
	
	@Query(value="select sum(qty*p.price) as total from order_detail o,product p,categories c where status='finished' and o.pid=p.pid and c.id=p.category_id group by c.id",nativeQuery = true)
	public Integer[] getTotalSaleByCategory();
	
	@Query(value="select sum(qty*p.price) from order_detail o,product p where p.pid=o.pid and finish_date>='2023-01-01' group by EXTRACT(month from finish_date) order by EXTRACT(month from finish_date) ",nativeQuery = true)
	public Integer[] getTotalSaleByMonth();
	
	@Query(value="select sum(qty*p.price) as total from order_detail o,product p where status='finished' and o.pid=p.pid",nativeQuery = true)
	public Integer getTotalSale();
	
	@Query("select new com.shopaholic.entity.BestSell(product.pname,sum(qty) as total) from OrderDetail where status='finished' group by product.pname order by total desc")
	public List<BestSell> getBestSell();
}


