package com.mrdave19.PSStoreGameScraperV3.dao;

import java.util.List;
import java.util.Optional;

import com.mrdave19.PSStoreGameScraperV3.entity.PSStoreGameWebExtended;

public interface PSStoreGameWebExtendedDAO {

// get all games front page
	public List<PSStoreGameWebExtended> getGamesFrontPage(int currentPage, int pageSize, String orderBy,
			Optional<String> gameName);

//get new games
	public List<PSStoreGameWebExtended> getNewGames(int currentPage, int pageSize, String orderBy, long limit,
			Optional<String> gameName);

//get all games top-discounted
	public List<PSStoreGameWebExtended> getTopDiscountedGames(int currentPage, int pageSize, String orderby, long limit,
			Optional<String> gameName);

//get history
	public List<PSStoreGameWebExtended> getGameHistory(String id);

//All Games home page
	public Long getTotalNumberOfGames(Optional<String> game);

//records size for top discounts
	public Integer getTotalNumberOfDiscuntedGames(long limit, Optional<String> gameName);

//records for new games
	public Long getTotalNumberOfNewGames(Long days, Optional<String> gameName);

}
