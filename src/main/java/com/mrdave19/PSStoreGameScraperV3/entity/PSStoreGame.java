package com.mrdave19.PSStoreGameScraperV3.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name="psstoregamess")
public class PSStoreGame{
	

	@Id
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name="id")
	private String id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="device")
	private String device;
	
	@Column(name="type")
	private String type;
	
	@Column(name="price")
	private String price;
	
	//@Temporal(TemporalType.DATE)

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public PSStoreGame(String id, String name, String device, String type, String price) {
		this.id = id;
		this.name = name;
		this.device = device;
		this.type = type;
		this.price = price;
	}
	
	public PSStoreGame() {
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
	
	
	
	@Override
	public String toString() {
		return "PSStoreGame [id=" + id + ", name=" + name + ", device=" + device + ", type=" + type + ", price=" + price
				+ "]";
	}
	

}
