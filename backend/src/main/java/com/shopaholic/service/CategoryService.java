package com.shopaholic.service;

import java.util.List;

import com.shopaholic.entity.Categories;



public interface CategoryService {

	public List<Categories> getAll();

	public Categories get(int id);

	public Categories create(Categories category);

	public List<Integer> getStockByCategory();
	
	public Categories update(int id,Categories category);

}
