package com.mrdave19.PSStoreGameScraperV3.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "psstoregamess_audit_log")
public class AuditLog {

	@EmbeddedId
	AuditEmbededId auditId;
	
	@Column(name = "name")
	private String name;
	@Column(name = "device")
	private String device;
	@Column(name = "type")
	private String type;
	@Column(name = "price")
	private String price;
	
	@MapsId("rev")
	@ManyToOne
	@JoinColumn(name = "rev", referencedColumnName = "rev")
	private Rev rev;
	
	@Column(name = "revtype")
	private Integer revtype;


	public AuditEmbededId getAuditId() {
		return auditId;
	}

	public void setAuditId(AuditEmbededId auditId) {
		this.auditId = auditId;
	}

	public Rev getRev() {
		return rev;
	}

	public void setRev(Rev rev) {
		this.rev = rev;
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



	public Integer getRevtype() {
		return revtype;
	}

	public void setRevtype(Integer revtype) {
		this.revtype = revtype;
	}

	@Override
	public String toString() {
		return "AuditLog [name=" + name + ", device=" + device + ", type=" + type + ", price=" + price + ", rev=" + rev
				+ ", revtype=" + revtype + "]";
	}





}
