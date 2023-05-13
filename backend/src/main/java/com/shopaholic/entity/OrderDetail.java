package com.shopaholic.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;


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
public class OrderDetail implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private int did;
	
	@Column(nullable = false)
	private int orderNumber;
	
	@ManyToOne(targetEntity = Customer.class,cascade = CascadeType.ALL)
	@JoinColumn(name = "cid", referencedColumnName = "cid")
	@NotNull(message = "Required")
	private Customer customer;

	@ManyToOne(targetEntity = Product.class,cascade = CascadeType.ALL)
	@JoinColumn(name = "pid", referencedColumnName = "pid")
	@NotNull(message = "Required")
	private Product product;
	
	@Column(nullable = false)
	private int qty;

	
	@Column(nullable = false)
	@ColumnDefault("CURRENT_TIMESTAMP")
	private LocalDate orderDate;
	
	@Column(nullable = true)
	private LocalDate finishDate;
	
	@Column(length = 10, nullable = false)
	@NotBlank(message = "Required")
	private String status;
	

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public void setFinishDate(LocalDate finishDate) {
		this.finishDate = finishDate;
	}
	public LocalDate getFinishDate() {
		return finishDate;
	}
	

	


}

