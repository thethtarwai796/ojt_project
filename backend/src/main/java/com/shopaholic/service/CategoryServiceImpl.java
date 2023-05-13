package com.shopaholic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopaholic.entity.Categories;
import com.shopaholic.entity.Product;
import com.shopaholic.reposistory.CategoryRepo;


@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepo categroyRepo;

	@Override
	public List<Categories> getAll() {
		return categroyRepo.findAll();
	}

	@Override
	public Categories get(int id) {
		return categroyRepo.findById(id).orElse(null);
	}

	@Override
	public Categories create(Categories category) {
		// Category Create
		return categroyRepo.save(category);
	}

	@Override
	public List<Integer> getStockByCategory() {
		return categroyRepo.getStockByCategory();
	}

	@Override
	public Categories update(int id, Categories category) {
		Categories toUpdateCategory=this.get(id);
		if(toUpdateCategory==null)
			return null;
		toUpdateCategory.setName(category.getName());
		
		
		categroyRepo.save(toUpdateCategory);
		return toUpdateCategory;
	}

	

}

