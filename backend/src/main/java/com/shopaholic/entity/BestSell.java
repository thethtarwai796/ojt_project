package com.shopaholic.entity;

public class BestSell {
	
	private String pname;
	private long total;
	public BestSell(String pname,long total) {
		this.pname=pname;
		this.total=total;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}

}
