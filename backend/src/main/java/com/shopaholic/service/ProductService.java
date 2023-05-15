package com.shopaholic.service;

import java.util.List;

import com.shopaholic.entity.Categories;
import com.shopaholic.entity.Product;



public interface ProductService {
	
	public Product createProduct(Product product);
	
	public Product updateProduct(int id, Product product);
	
	public Integer updateProductStock(Integer pid, Integer qty);
	
	public List<Product> getAllProduct();
	
	public Product getById(int id);
	
	public List<Product> search(String str);
	
	public List<Product> getByCategory(Categories category);

	public boolean deleteProduct(int id);

}
