package com.mrdave19.PSStoreGameScraperV3.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.mrdave19.PSStoreGameScraperV3.entity.PSStoreGame;

public enum SortingMethod {

	
	NAMEASC{
		@Override
		public List<Order> apply(CriteriaBuilder criteriaBuilder, Root<PSStoreGame> psstoregameRoot,Path<String> name,Expression<BigInteger> minRevtstmp,Expression<BigInteger> maxRevtstmp,Expression<Object> artPrice){
			
			final List<Order> orderbyList = new ArrayList<>();
			orderbyList.add(criteriaBuilder.asc(name));
			return orderbyList;
		}
	},
	
	
	 NAMEDESC{

		@Override
		public List<Order> apply(CriteriaBuilder criteriaBuilder, Root<PSStoreGame> psstoregameRoot,Path<String> name,Expression<BigInteger> minRevtstmp,Expression<BigInteger> maxRevtstmp,Expression<Object> artPrice) {
			final List<Order> orderbyList = new ArrayList<>();
			orderbyList.add(criteriaBuilder.desc(name));
			return orderbyList;
		}
		
	}, 
	 PRICEASC{

		@Override
		public List<Order> apply(CriteriaBuilder criteriaBuilder, Root<PSStoreGame> psstoregameRoot,Path<String> name,Expression<BigInteger> minRevtstmp,Expression<BigInteger> maxRevtstmp,Expression<Object> artPrice) {
			final List<Order> orderbyList = new ArrayList<>();
			Order order1 = criteriaBuilder.asc(criteriaBuilder.selectCase().when(criteriaBuilder.isNotNull(artPrice), artPrice).otherwise(999));
			orderbyList.add(order1);
			orderbyList.add(criteriaBuilder.asc(name));
			return orderbyList;
		}
		
	}, 
	 PRICEDESC{

		@Override
		public List<Order> apply(CriteriaBuilder criteriaBuilder, Root<PSStoreGame> psstoregameRoot,Path<String> name,Expression<BigInteger> minRevtstmp,Expression<BigInteger> maxRevtstmp,Expression<Object> artPrice) {
			final List<Order> orderbyList = new ArrayList<>();
			Order order1 = criteriaBuilder.desc(criteriaBuilder.selectCase().when(criteriaBuilder.isNotNull(artPrice), artPrice).otherwise(artPrice));
			orderbyList.add(order1);
			orderbyList.add(criteriaBuilder.asc(name));
			return orderbyList;
		}
		
	}, 
	 CREATEASC{

		@Override
		public List<Order> apply(CriteriaBuilder criteriaBuilder, Root<PSStoreGame> psstoregameRoot,Path<String> name,Expression<BigInteger> minRevtstmp,Expression<BigInteger> maxRevtstmp,Expression<Object> artPrice) {
			final List<Order> orderbyList = new ArrayList<>();
			orderbyList.add(criteriaBuilder.asc(minRevtstmp));
			orderbyList.add(criteriaBuilder.asc(name));
			return orderbyList;
		}
		
	},
	 CREATEDESC{

		@Override
		public List<Order> apply(CriteriaBuilder criteriaBuilder, Root<PSStoreGame> psstoregameRoot,Path<String> name,Expression<BigInteger> minRevtstmp,Expression<BigInteger> maxRevtstmp,Expression<Object> artPrice) {
			final List<Order> orderbyList = new ArrayList<>();
			orderbyList.add(criteriaBuilder.desc(minRevtstmp));
			orderbyList.add(criteriaBuilder.asc(name));
			return orderbyList;
		}
		
	}, 
	 CHANGEASC{

		@Override
		public List<Order> apply(CriteriaBuilder criteriaBuilder, Root<PSStoreGame> psstoregameRoot,Path<String> name,Expression<BigInteger> minRevtstmp,Expression<BigInteger> maxRevtstmp,Expression<Object> artPrice) {
			final List<Order> orderbyList = new ArrayList<>();
			orderbyList.add(criteriaBuilder.asc(maxRevtstmp));
			orderbyList.add(criteriaBuilder.asc(name));
			return orderbyList;
		}
		
	}, 
	 CHANGEDESC{

		@Override
		public List<Order> apply(CriteriaBuilder criteriaBuilder, Root<PSStoreGame> psstoregameRoot,Path<String> name,Expression<BigInteger> minRevtstmp,Expression<BigInteger> maxRevtstmp,Expression<Object> artPrice) {
			final List<Order> orderbyList = new ArrayList<>();
			orderbyList.add(criteriaBuilder.desc(maxRevtstmp));
			orderbyList.add(criteriaBuilder.asc(name));
			return orderbyList;
		}
		
	};
	 
		
	
	
	public abstract List<Order> apply(CriteriaBuilder criteriaBuilder, Root<PSStoreGame> psstoregameRoot,Path<String> name,Expression<BigInteger> minRevtstmp,Expression<BigInteger> maxRevtstmp,Expression<Object> artPrice);
	
	

}
