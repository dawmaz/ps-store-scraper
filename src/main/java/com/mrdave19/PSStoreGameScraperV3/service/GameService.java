package com.mrdave19.PSStoreGameScraperV3.service;

import java.util.List;

import com.mrdave19.PSStoreGameScraperV3.entity.PSStoreGame;

public interface GameService {
	
	public  List<PSStoreGame> getGames(String url, Integer gamesPerPgae);
	public List<Integer> getParameters (String url);
	


}
