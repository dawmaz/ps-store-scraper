package com.mrdave19.PSStoreGameScraperV3.entity;

import java.math.BigInteger;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Rev.class)
public abstract class Rev_ {

	public static volatile SingularAttribute<Rev, Integer> rev;
	public static volatile ListAttribute<Rev, AuditLog> auditLog;
	public static volatile SingularAttribute<Rev, BigInteger> revtstmp;

	public static final String REV = "rev";
	public static final String AUDIT_LOG = "auditLog";
	public static final String REVTSTMP = "revtstmp";

}

