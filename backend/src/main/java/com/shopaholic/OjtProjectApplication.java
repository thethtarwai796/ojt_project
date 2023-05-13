package com.shopaholic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import com.shopaholic.service.CategoryService;
import com.shopaholic.service.ProductService;
import com.shopaholic.service.StorageService;

@SpringBootApplication
public class OjtProjectApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(OjtProjectApplication.class, args);
	}
	
	@Autowired
	StorageService storageService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryService categoryService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String ddlMode;
	
	@Override
	public void run(String... args) throws Exception {}
}
