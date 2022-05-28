package com.mrdave19.PSStoreGameScraperV3.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mrdave19.PSStoreGameScraperV3.entity.PSStoreGame;
import com.mrdave19.PSStoreGameScraperV3.utils.ScrapeModel;

@Repository
public class GameServiceImpl implements GameService {

	@Autowired
	ScrapeModel model;
	
	private static Element missingPriceElement = new Element("noPrice").appendText("No price found");

	@Override
	public List<PSStoreGame> getGames(String url,Integer gamesPerPage) {

		Document doc = model.scrapeData(url);

		List<PSStoreGame> list = new LinkedList<>();

		// Get list of unique IDs
		Elements links = doc.select("a[href*='product']");
		LinkedHashSet<String> hashIds = new LinkedHashSet <>();
		for(Element el:links) {
			hashIds.add(el.attr("abs:href").substring(44));
		}
		LinkedList<String> ids = new LinkedList<>(hashIds);
		
		// Get list of names
		Elements name = doc.select("div.grid-cell__title");

		// Get list of devices
		Elements device = doc.select("div.grid-cell__left-detail.grid-cell__left-detail--detail-1");

		// Get list of types
		Elements type = doc.select("div.grid-cell__left-detail.grid-cell__left-detail--detail-2");

		// Get list of prices
		Elements price = doc.select(".price-display__price,.price-display__price--is-plus-exclusive,.grid-cell__ineligible-reason");
		
			// some games are missing prices - they appear at at end of the list
			// add customized message for such cases
		
			Integer tempSize=price.size();
			if(tempSize<gamesPerPage) {
				for (int i=0; i<gamesPerPage-tempSize;i++) {
					price.add(missingPriceElement);
				}
			}
		
	
		// Create Game and add to the list
		
		//System.out.println("Sanity check: ids size: " + ids.size() + " namesSize = " + name.size()+ " devices size = " +device.size() + " types size = " +type.size()+" prices size = " + price.size());

		for (int i = 0; i < name.size(); i++) {
			list.add(new PSStoreGame(ids.get(i),name.get(i).text(), device.get(i).text(), type.get(i).text(), price.get(i).text()));
		}

		return list;
	}

	@Override
	public List<Integer> getParameters(String url) {
		
		Document doc = model.scrapeData(url);
		List<Integer> list = new ArrayList<>();
		
		//find how many games per page, add to list as first parameter; expected format '1-30'
		//expected value = 30;
		String gamePerPage = doc.select(".range").text();
		Integer cut = gamePerPage.indexOf("-");
		
		list.add(Integer.parseInt(gamePerPage.substring(cut+1)));

		//find total number of games, expected format: '1-30 of 8001 Matches'
		//expected value = 8001
		
		String gamesTotal = doc.select("div.grid-header__left").text();
		Integer of = gamesTotal.indexOf("of ");
		Integer matches = gamesTotal.indexOf(" Matches");
		
		list.add(Integer.parseInt(gamesTotal.substring(of+3, matches)));
		
		return list;
	}

}
