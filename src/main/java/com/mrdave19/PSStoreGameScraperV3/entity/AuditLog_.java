package com.mrdave19.PSStoreGameScraperV3.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AuditLog.class)
public abstract class AuditLog_ {

	public static volatile SingularAttribute<AuditLog, AuditEmbededId> auditId;
	public static volatile SingularAttribute<AuditLog, Rev> rev;
	public static volatile SingularAttribute<AuditLog, String> price;
	public static volatile SingularAttribute<AuditLog, String> name;
	public static volatile SingularAttribute<AuditLog, Integer> revtype;
	public static volatile SingularAttribute<AuditLog, String> type;
	public static volatile SingularAttribute<AuditLog, String> device;

	public static final String AUDIT_ID = "auditId";
	public static final String REV = "rev";
	public static final String PRICE = "price";
	public static final String NAME = "name";
	public static final String REVTYPE = "revtype";
	public static final String TYPE = "type";
	public static final String DEVICE = "device";

}

