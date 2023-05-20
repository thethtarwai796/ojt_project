package com.shopaholic.reposistory;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.shopaholic.entity.BestSell;
import com.shopaholic.entity.OrderDetail;

@EnableJpaRepositories
public interface OrderDetailRepo extends JpaRepository<OrderDetail, Integer>{
	
	@Query(value="select * from order_detail od,orders o where od.order_number=o.order_number and od.order_number=:orderNumber",nativeQuery = true)
	public List<OrderDetail> findByOrderNumber(@Param("orderNumber")int orderNumer);
	
	@Query(value="select sum(qty*p.price) as total from order_detail o,product p,categories c where o.pid=p.pid and c.id=p.category_id group by c.id",nativeQuery = true)
	public Integer[] getTotalSaleByCategory();
	
	
	@Query("select new com.shopaholic.entity.BestSell(product.pname,sum(qty) as total) from OrderDetail group by product.pname order by total desc")
	public List<BestSell> getBestSell();
}


