package com.mrdave19.PSStoreGameScraperV3.entity;

import java.beans.ConstructorProperties;
import java.math.BigInteger;

public class PSStoreGameWebExtended {

	private String id;
	private String name;
	private String device;
	private String type;
	private String price;
	private BigInteger minDate;
	private BigInteger maxDate;
	private Double artPrice;
	private Double priceChange;
	private BigInteger revtstmp;
	
	@ConstructorProperties({"id","name","device","type","price","maxDate","artPrice","priceChange"})
	public PSStoreGameWebExtended(String id, String name, String device, String type, String price, BigInteger maxDate,
			Double artPrice, Double priceChange) {
		this.id = id;
		this.name = name;
		this.device = device;
		this.type = type;
		this.price = price;
		this.maxDate = maxDate;
		this.artPrice = artPrice;
		this.priceChange = priceChange;
	}





	public PSStoreGameWebExtended(String id, String name, String device, String type, String price, BigInteger minDate,
			BigInteger maxDate, Double artPrice) {
		this.id = id;
		this.name = name;
		this.device = device;
		this.type = type;
		this.price = price;
		this.minDate = minDate;
		this.maxDate = maxDate;
		this.artPrice = artPrice;
	}
	
	
	
	public PSStoreGameWebExtended(String id, String name, String device, String type, String price,
			BigInteger revtstmp) {
		this.id = id;
		this.name = name;
		this.device = device;
		this.type = type;
		this.price = price;
		this.revtstmp = revtstmp;
	}


	
	

	public Double getPriceChange() {
		return priceChange;
	}


	public void setPriceChange(Double priceChange) {
		this.priceChange = priceChange;
	}


	public BigInteger getRevtstmp() {
		return revtstmp;
	}



	public void setRevtstmp(BigInteger revtstmp) {
		this.revtstmp = revtstmp;
	}



	public BigInteger getMinDate() {
		return minDate;
	}
	public void setMinDate(BigInteger minDate) {
		this.minDate = minDate;
	}
	public Double getArtPrice() {
		return artPrice;
	}
	public void setArtPrice(Double artPrice) {
		this.artPrice = artPrice;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public BigInteger getMaxDate() {
		return maxDate;
	}
	public void setMaxDate(BigInteger maxDate) {
		this.maxDate = maxDate;
	}





	@Override
	public String toString() {
		return "PSStoreGameWebExtended [id=" + id + ", name=" + name + ", device=" + device + ", type=" + type
				+ ", price=" + price + ", minDate=" + minDate + ", maxDate=" + maxDate + ", artPrice=" + artPrice
				+ ", priceChange=" + priceChange + ", revtstmp=" + revtstmp + "]";
	}



	

}
