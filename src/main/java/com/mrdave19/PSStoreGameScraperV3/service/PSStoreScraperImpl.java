package com.mrdave19.PSStoreGameScraperV3.service;

import java.util.LinkedList;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrdave19.PSStoreGameScraperV3.dao.PSStoreGameDAO;
import com.mrdave19.PSStoreGameScraperV3.entity.PSStoreGame;

@Service
public class PSStoreScraperImpl implements PSStoreScraper{
	
	@Autowired
	private PSStoreGameDAO dao;
	
	@Autowired
	private GameService imp;
	
	
	//@PostConstruct
	public void downloadAll() {
		
		//Check parameters: number of games per page, total games
		List<Integer> parameters = imp.getParameters("https://store.playstation.com/en-us/grid/STORE-MSF77008-ALLGAMES/1");
		Integer gamesPerPage = parameters.get(0);
		Integer totalGames = parameters.get(1);
		
		//Calculate number of requested Iterations to cover all games
		//use ceil value
		Integer iterations = (totalGames+gamesPerPage-1)/gamesPerPage;
		
		
		//Download all games and save them into db
		//List<PSStoreGame> tempList;
		List<PSStoreGame> list = new LinkedList<>();
		
		
		//for(int i=1;i<=iterations;i++) {
		//System.out.println("Iteration: " + i);
		//IntStream.rangeClosed(1, 10).parallel()
		//	.forEach(a->list.addAll(imp.getGames("https://store.playstation.com/en-us/grid/STORE-MSF77008-ALLGAMES/" +a + "?direction=asc&sort=price", gamesPerPage)));	
		IntStream.rangeClosed(1, iterations).parallel()
		.forEach(new IntConsumer() {
			
			@Override
			public void accept(int value) {
				System.out.println("Iteration: " + value);
				list.addAll(imp.getGames("https://store.playstation.com/en-us/grid/STORE-MSF77008-ALLGAMES/" +value + "?direction=asc&sort=price", gamesPerPage));
		
			}
		});
		
		dao.save(list);
	}



}
