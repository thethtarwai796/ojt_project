package com.shopaholic.entity;

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
	
	@ManyToOne(targetEntity = Orders.class,cascade = CascadeType.ALL)
	@JoinColumn(name = "order_number", referencedColumnName = "orderNumber")
	@NotNull(message = "Required")
	private Orders order;
	
	@ManyToOne(targetEntity = Product.class,cascade = CascadeType.ALL)
	@JoinColumn(name = "pid", referencedColumnName = "pid")
	@NotNull(message = "Required")
	private Product product;
	
	@Column(nullable = false)
	private int qty;

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
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

	
	
}

