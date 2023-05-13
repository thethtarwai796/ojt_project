package com.shopaholic.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString 
public class Product implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int pid;

	@Column(length = 30, nullable = false)
	private String pname;
	
	@Column(length = 30, nullable = false)
	private String color;
	
	@Column(nullable = false)
	private int stockQty;
	
	@Column(nullable = false)
	private int price;
	
	@Column(length = 200)
	private String productPath;
	
	@Column(nullable = false)
	private int discount;
	
	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
	private Categories category;

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getStockQty() {
		return stockQty;
	}

	public void setStockQty(int stockQty) {
		this.stockQty = stockQty;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getProductPath() {
		return productPath;
	}

	public void setProductPath(String productPath) {
		this.productPath = productPath;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public Categories getCategory() {
		return category;
	}

	public void setCategory(Categories category) {
		this.category = category;
	}

	
	
	
	
	
	

}
