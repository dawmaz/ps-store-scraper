package com.mrdave19.PSStoreGameScraperV3.dao;

import java.util.List;

import com.mrdave19.PSStoreGameScraperV3.entity.PSStoreGame;

public interface PSStoreGameDAO {

	public void save(PSStoreGame psGame);
	public void save(List<PSStoreGame> list);

}
