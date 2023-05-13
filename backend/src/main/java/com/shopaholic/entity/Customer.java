package com.shopaholic.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

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
public class Customer implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cid;

	@Column(length = 30, nullable = false)
	@NotBlank(message = "Required")
	private String cname;

	@Column(length = 100, nullable = false)
	@NotBlank(message = "Required")
	private String email;
	
	@Column(length = 100, nullable = false)
	@NotBlank(message = "Required")
	private String password;
	
	@Column(length = 100, nullable = false)
	@NotBlank(message = "Required")
	private String phone;
	
	@Column(length = 100, nullable = false)
	@NotBlank(message = "Required")
	private String address;
	
	@Column(nullable = false)
	private int coin;
	
	@Column(length = 100)
	private String role;
	
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getCid() {
		return cid;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCname() {
		return cname;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
    public void setPassword(String password) {
		this.password = password;
	}
    public String getPassword() {
		return password;
	}
    public void setPhone(String phone) {
		this.phone = phone;
	}
    public String getPhone() {
		return phone;
	}
    public void setAddress(String address) {
		this.address = address;
	}
    public String getAddress() {
		return address;
	}
    public void setCoin(int coin) {
		this.coin = coin;
	}
    public int getCoin() {
		return coin;
	}
	

	

}