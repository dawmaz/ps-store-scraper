package com.mrdave19.PSStoreGameScraperV3.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mrdave19.PSStoreGameScraperV3.service.ControllerService;
import com.mrdave19.PSStoreGameScraperV3.service.PSStoreScraper;

@Controller
@RequestMapping("/playstation-store-games")
public class HomeController {

	@Autowired
	ControllerService controllerService;
	
	@Autowired
	PSStoreScraper scraper;
	
	@GetMapping("/")
	public String home(HttpServletRequest request, Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("order") Optional<String> order, @RequestParam("gamename") Optional<String> gameName) {
		

		int currentPage = page.orElse(1);
		String orderbyParam = order.orElse("nameAsc");
		final int pageSize = 30;
				
		long totalPages =controllerService.getTotalPages(pageSize, gameName);
		
		controllerService.updatePageModel(model, currentPage, totalPages, request);
		controllerService.updateGameModel(model, currentPage, pageSize, orderbyParam, gameName);
		
		return "home";
	}

	@GetMapping("/add-games")
	public String addGames() {
		scraper.downloadAll();
		return "download";
	}

	@GetMapping("/new-games")
	public String newGames(HttpServletRequest request, Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("order") Optional<String> order, @RequestParam("limit") Optional<Long> limit,
			@RequestParam("gamename") Optional<String> gameName) {

		int currentPage = page.orElse(1);
		String orderbyParam = order.orElse("createDesc");
		Long limitParam = limit.orElse(7L);
		final int pageSize = 30;
		
		long totalPages = controllerService.getTotalPagesOfNewGames(pageSize, gameName, limitParam);
		controllerService.updatePageModel(model, currentPage, totalPages, request);
		controllerService.updateNewGameModel(model, currentPage, pageSize, orderbyParam, gameName, limitParam);
		
		return "newgames";
	}

	@GetMapping("/top-discounts")
	public String topDiscounts(HttpServletRequest request, Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("order") Optional<String> order, @RequestParam("limit") Optional<Long> limit,
			@RequestParam("gamename") Optional<String> gameName) {


		int currentPage = page.orElse(1);
		String orderbyParam = order.orElse("pricechangeasc");
		Long limitParam = limit.orElse(7L);
		final int pageSize = 30;
		
		long totalPages = controllerService.getTotalPagesOfDiscountedGames(pageSize, gameName, limitParam);
		controllerService.updatePageModel(model, currentPage, totalPages, request);
		controllerService.updateTopDiscountsGameModel(model, currentPage, pageSize, orderbyParam, gameName, limitParam);
		
		return "topdiscounts";
	}

	@GetMapping("/history")

	public String getHistory(@RequestParam("id") String id, Model model) {
		
		controllerService.updateHistoryGameModel(model, id);

		return "history";
	}
	

}
