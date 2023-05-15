package com.shopaholic.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopaholic.entity.Categories;
import com.shopaholic.entity.Product;
import com.shopaholic.reposistory.ProductRepo;



@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepo productRepo;

	@Override
	public Product createProduct(Product product) {
		// TODO Auto-generated method stub
		return productRepo.save(product);
	}

	
	@Override
	public Product updateProduct(int id, Product product) {
		Product toUpdateProduct=this.getById(id);
		if(toUpdateProduct==null)
			return null;
		toUpdateProduct.setPname(product.getPname());
		toUpdateProduct.setColor(product.getColor());
		toUpdateProduct.setStockQty(product.getStockQty());
		toUpdateProduct.setPrice(product.getPrice());
		toUpdateProduct.setProductPath(product.getProductPath());
		toUpdateProduct.setDiscount(product.getDiscount());
		toUpdateProduct.setCategory(product.getCategory());
		productRepo.save(toUpdateProduct);
		return toUpdateProduct;
	}
	
	@Override
	public Integer updateProductStock(Integer pid, Integer qty) {
		return productRepo.updateStock(pid, qty);
	}

	@Override
	public List<Product> getAllProduct() {
		return productRepo.findAll();
	}

	@Override
	public Product getById(int pid) {
		return productRepo.findById(pid).orElse(null);
	}
	
	@Override
	public List<Product> getByCategory(Categories category) {
		return productRepo.findByCategory(category);
	}

	
	
	@Override
	public boolean deleteProduct(int id) {
		productRepo.deleteById(id);
		return true;
	}


	@Override
	public List<Product> search(String str) {
		
		return productRepo.searchProduct(str);
	}


	


	


	





	

	

}

