package com.mrdave19.PSStoreGameScraperV3.utils;

import java.util.ArrayList;
import java.util.List;

import org.jooq.SortField;
import org.jooq.impl.DSL;

import com.mrdave19.PSStoreGameScraperV3.entity.jooq.games.tables.Psstoregamess;

public enum SortingMethodJooq {
	
	

	
	NAMEASC{
		@Override
		public List<SortField<?>> apply(Psstoregamess games) {
			final List<SortField<?>> list = new ArrayList<>();
			list.add(games.NAME.asc());
			return list;
		}
	},
	
	
	 NAMEDESC{
		@Override
		public List<SortField<?>> apply(Psstoregamess games) {
			final List<SortField<?>> list = new ArrayList<>();
			list.add(games.NAME.desc());
			return list;
		}
		
	}, 
	 PRICEASC{

		@Override
		public List<SortField<?>> apply(Psstoregamess games) {
			final List<SortField<?>> list = new ArrayList<>();
			list.add(DSL.field("artPrice").asc());
			list.add(games.NAME.asc());
			return list;
		}
		
	}, 
	 PRICEDESC{
		@Override
		public List<SortField<?>> apply(Psstoregamess games) {
			final List<SortField<?>> list = new ArrayList<>();
			list.add(DSL.field("artPrice").desc());
			list.add(games.NAME.asc());
			return list;
		}

		
	}, 
	 
	 CHANGEASC{

		@Override
		public List<SortField<?>> apply(Psstoregamess games) {
			final List<SortField<?>> list = new ArrayList<>();
			list.add(DSL.field("maxDate").asc());
			list.add(games.NAME.asc());
			return list;
		}
		
	}, 
	 CHANGEDESC{
		@Override
		public List<SortField<?>> apply(Psstoregamess games) {
			final List<SortField<?>> list = new ArrayList<>();
			list.add(DSL.field("maxDate").desc());
			list.add(games.NAME.asc());
			return list;
		}
		
	},
	 
	 PRICECHANGEASC{

		@Override
		public List<SortField<?>> apply(Psstoregamess games) {
			final List<SortField<?>> list = new ArrayList<>();
			list.add(DSL.field("priceChange").asc());
			list.add(games.NAME.asc());
			return list;
		}


		
	},
	 
	 PRICECHANGEDESC{

		@Override
		public List<SortField<?>> apply(Psstoregamess games) {
			final List<SortField<?>> list = new ArrayList<>();
			list.add(DSL.field("priceChange").desc());
			list.add(games.NAME.asc());
			return list;
		}


		
	};
	
	
	public abstract List<SortField<?>> apply(Psstoregamess games);
	
	

}
