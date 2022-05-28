package com.mrdave19.PSStoreGameScraperV3.entity;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="revinfo")
public class Rev {
	@Id
	@Column(name="rev")
	private int rev;
	@Column(name="revtstmp")
	private BigInteger revtstmp;
	
	@OneToMany(mappedBy = "rev")
	private List<AuditLog> auditLog;
	
	

	public List<AuditLog> getAuditLog() {
		return auditLog;
	}
	public void setAuditLog(List<AuditLog> auditLog) {
		this.auditLog = auditLog;
	}
	public int getRev() {
		return rev;
	}
	public void setRev(int rev) {
		this.rev = rev;
	}
	public BigInteger getRevtstmp() {
		return revtstmp;
	}
	public void setRevtstmp(BigInteger revtstmp) {
		this.revtstmp = revtstmp;
	}


	
	
	
}
