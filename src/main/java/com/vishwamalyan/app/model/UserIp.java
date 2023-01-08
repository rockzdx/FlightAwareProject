package com.vishwamalyan.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserIp {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String no;
	private String depature;
	private String arrival;
	private String date;
	private String Cnt;
	private String Country;
	private String Locale;
	private String Origin;
	private String Dest;
	private String Currency;
	private String inDate;
	private String outDate;
	private String Curr;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getDepature() {
		return depature;
	}

	public void setDepature(String depature) {
		this.depature = depature;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getCurr() {
		return Curr;
	}

	public void setCurr(String curr) {
		Curr = curr;
	}

	public String getCnt() {
		return Cnt;
	}

	public void setCnt(String cnt) {
		Cnt = cnt;
	}

	public String getOrigin() {
		return Origin;
	}

	public void setOrigin(String origin) {
		Origin = origin;
	}

	public String getDest() {
		return Dest;
	}

	public void setDest(String dest) {
		Dest = dest;
	}

	public String getLocale() {
		return Locale;
	}

	public void setLocale(String locale) {
		Locale = locale;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public String getInDate() {
		return inDate;
	}

	public void setInDate(String inDate) {
		this.inDate = inDate;
	}

	public String getOutDate() {
		return outDate;
	}

	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}

}
