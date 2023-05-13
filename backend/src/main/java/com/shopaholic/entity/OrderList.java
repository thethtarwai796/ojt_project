package com.shopaholic.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderList {
	
	
	private int orderNumber;
	private int cid;
	private String name;
	private String status;
	private long total;
	private LocalDate date;
	
	
	
	public OrderList(int orderNumber, int cid,String name,String status, long total, LocalDate date) {
		this.orderNumber = orderNumber;
		this.cid=cid;
		this.name = name;
		this.status = status;

		this.total = total;
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	

}
