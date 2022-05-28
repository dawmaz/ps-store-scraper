package com.mrdave19.PSStoreGameScraperV3.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AuditEmbededId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name="id")
	private String id;
	
	@Column(name="rev")
	private int rev;

	public AuditEmbededId(String id, int rev) {
		this.id = id;
		this.rev = rev;
	}
	
	

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public int getRev() {
		return rev;
	}



	public void setRev(int rev) {
		this.rev = rev;
	}



	public AuditEmbededId() {
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + rev;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuditEmbededId other = (AuditEmbededId) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (rev != other.rev)
			return false;
		return true;
	}
	
	

}
