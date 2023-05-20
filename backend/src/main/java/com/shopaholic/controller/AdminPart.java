package com.shopaholic.controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.apache.naming.java.javaURLContextFactory;
import org.hibernate.type.LocalDateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shopaholic.entity.BestSell;
import com.shopaholic.entity.Categories;
import com.shopaholic.entity.OrderDetail;
import com.shopaholic.entity.Orders;
import com.shopaholic.entity.Product;
import com.shopaholic.service.CategoryService;
import com.shopaholic.service.OrderDetailService;
import com.shopaholic.service.OrderService;
import com.shopaholic.service.ProductService;
import com.shopaholic.service.StorageService;

@RestController
public class AdminPart {
	
	int totalStock=0;
	int todayOrder=0;

	@Autowired
	ProductService productService;

	@Autowired
	StorageService storageService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderDetailService orderDetailService;
	
	//----------------Category------------------
	@GetMapping("/category/getAll")
	public List<Categories> getAllCategory() {
		return categoryService.getAll();
	}
	
	@GetMapping("/category/getById")
	public Categories getCategoryById(@RequestParam int id) {
		return categoryService.get(id);
	}
	
	@GetMapping("/category/getInstock")
	public List<Integer> getInstockByCategory() {
		return categoryService.getStockByCategory();
	}
	

	@PostMapping("/category/create")
	public Categories createCategory(@RequestBody Categories category) {
		return categoryService.create(category);
	}
	
	@PutMapping("/category/update/{id}")
	public ResponseEntity<Categories> updateCategory(
			@PathVariable int id, @Valid @RequestBody Categories category
	) {
		Categories toupadateCategory = categoryService.update(id, category);
		if (toupadateCategory == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(toupadateCategory);
	}
	
	
	//----------------Product-------------------
	
	
	
	@GetMapping("/product/searchProduct")
	public ResponseEntity<?> searchProduct(@RequestParam String pname) {
		return ResponseEntity.ok(productService.search(pname));
	}


	@PostMapping("/product/create")
	public ResponseEntity<?> createProduct(@Valid @RequestBody Product product) {
		if (!storageService.check(product.getProductPath())) {
			return ResponseEntity.badRequest().body("Image is invalid");
		}
		return ResponseEntity.ok(productService.createProduct(product));
	}

	@PostMapping("/file/create")
	public ResponseEntity<String> createFile(
			@RequestParam("file") MultipartFile file,
			@RequestParam("fileType") String fileType
	) {
		String filePath = storageService.create(file, fileType);
		if (filePath == null) {
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok(filePath);
	}

	

	@PutMapping("/product/update/{pid}")
	public ResponseEntity<Product> updateProduct(
			@PathVariable int pid, @Valid @RequestBody Product product
	) {
		Product toupdateProduct = productService.updateProduct(pid, product);
		if (toupdateProduct == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(toupdateProduct);
	}

	@PutMapping("/file/update")
	public ResponseEntity<String> updateFile(
			@RequestParam("file") MultipartFile file,
			@RequestParam("fileType") String fileType,
			@RequestParam("filePath") String filePath
	) {
		String newFilePath = storageService.update(file, fileType, filePath);
		if (newFilePath == null) {
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok(newFilePath);
	}
	
	@DeleteMapping(value = "/product/delete/{pid}")
	public ResponseEntity<?> deleteProduct(@PathVariable int pid) {
		Product product = productService.getById(pid);
		if (product == null) {
			return ResponseEntity.notFound().build();
		}

		// Delete Movie
		boolean isDeleted = productService.deleteProduct(pid);
		if (!isDeleted) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
		storageService.delete(product.getProductPath());
		return ResponseEntity.ok().build();
	}
	

	//---------------Order Detail---------------------
	@GetMapping("/order/getAllDetail")
	public List<OrderDetail> getAllOrderDetail() {
		List<OrderDetail> orderDetails=orderDetailService.getAllOrderDetail();
		return orderDetails;
	}
	
	
	@GetMapping("/order/getTotalSaleByCategory")
	public Integer[] getTotalSaleByCategory() {
		return orderDetailService.getTotalSaleByCategory();
	}
	

	@PostMapping("/order/findByOrderNo/{order_number}")
	public List<OrderDetail> findByOrderNo(@PathVariable("order_number") int order_number) {
		 return orderDetailService.getOrderDetailByOrderNo(order_number);
	}
	
	@GetMapping("/order/getBestSell")
	public List<BestSell> getBestSell() {
		return orderDetailService.getBestSells();
	}
	
	//-----------------------Order------------
	@GetMapping("/order/getAll")
	public List<Orders> getAllOrder() {
		List<Orders> orders=orderService.getAllOrders();
		return orders;
	}
	
	@GetMapping("/order/getCount")
	public int getOrderCount() {
		return orderService.countTotalOrder();
	}
	
	@GetMapping("/order/getShippedCount")
	public int getShippedCount() {
		return orderService.countShippedOrder();
	}
	@GetMapping("/order/getPendingCount")
	public int getPendingCount() {
		return orderService.countUnshippedOrder();
	}
	
	@GetMapping("/order/getTodayOrder")
	public int getToday() {
		return orderService.countTodayOrder(LocalDate.now());
	}
	
	@PostMapping("/order/finished/{order_number}")
	public void updateStatus(@PathVariable("order_number") int order_number) {
		LocalDate date=java.time.LocalDate.now();
		 orderService.updateStatus(order_number,date);
	}
	
	@GetMapping("/order/getByStatus/{status}")
	public List<Orders> getOrdersByStatus(@PathVariable("status") String status) {
		String sts="";
		if(status.equals("Finished"))
			sts="finished";
		else if(status.equals("Pending"))
			sts="pending";
		return orderService.getOrderByStatus(sts);
	}
	
	
	@GetMapping("/order/getOneOrderList/{order_number}")
	public Orders getOneOrder(@PathVariable("order_number") int order_number) {
		return orderService.getOneOrder(order_number);
	}
	
	@GetMapping("/order/getTotalSaleByMonth")
	public Integer[] getTotalSaleByMonth() {
		return orderService.getTotalSaleByMonth();
	}
	
	@GetMapping("/order/getTotalSale")
	public Integer getTotalSale() {
		return orderService.getTotalSale();
	}
	
	
}

