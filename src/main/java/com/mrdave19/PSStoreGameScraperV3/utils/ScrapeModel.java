package com.mrdave19.PSStoreGameScraperV3.utils;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class ScrapeModel {
	
	public Document scrapeData(String url) {

		Document doc = null;
		try {
			doc = Jsoup.connect(url).userAgent(
					"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2") //
					.header("Content-Language", "en-US").timeout(90*1000).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return doc;
	}

}
