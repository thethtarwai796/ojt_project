package com.shopaholic.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shopaholic.entity.Categories;
import com.shopaholic.entity.Product;
import com.shopaholic.service.CategoryService;
import com.shopaholic.service.ProductService;
import com.shopaholic.service.StorageService;


@RestController
public class CategoryController {
	int totalStock;

	@Autowired
	ProductService productService;

	@Autowired
	StorageService storageService;

	@Autowired
	CategoryService categoryServie;

	
	@GetMapping("/product/getAll")
	public List<Product> getAllProduct() {
		List<Product> products=productService.getAllProduct();
		for(Product p:products)
			totalStock+=p.getStockQty();
		return products;
	}
	@GetMapping("/product/getTotalStock")
	public int getTotalStock() {
		return totalStock;
	}
	@GetMapping("/product/getByCategory/{category_id}")
	public ResponseEntity<?> getProductByCategory(
			@PathVariable("category_id") int categoryID
	) {
		Categories category = categoryServie.get(categoryID);
		if (category == null) {
			return ResponseEntity.badRequest().body("Category ID is invalid");
		}
		List<Product> productList = productService.getByCategory(category);
		return ResponseEntity.ok().body(productList);
	}

	@GetMapping("/product/getById/{pid}")
	public ResponseEntity<Product> getProductById(
			@PathVariable("pid") int pid
	) {
		Product product = productService.getById(pid);
		if (product == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(product);
	}

	@GetMapping("/media/{fileType}/{fileName}")
	public ResponseEntity<?> getPoster(
			@PathVariable("fileType") String fileType,
			@PathVariable("fileName") String fileName
	) throws IOException {
		MediaType contentType = MediaType.IMAGE_PNG;
		switch (fileType) {
			case "mp4" :
				contentType = MediaType.APPLICATION_OCTET_STREAM;
				break;
			case "jpg" :
				contentType = MediaType.IMAGE_JPEG;
				break;
			case "png" :
				contentType = MediaType.IMAGE_PNG;
				break;
			default :
				return ResponseEntity.badRequest()
						.body("Unsupported File Type");
		}
		byte[] fileBytes = storageService.load(fileName);
		if (fileBytes == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().contentType(contentType).body(fileBytes);
	}

}
