package com.mrdave19.PSStoreGameScraperV3.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.mrdave19.PSStoreGameScraperV3.dao.PSStoreGameWebExtendedDAO;
import com.mrdave19.PSStoreGameScraperV3.entity.PSStoreGameWebExtended;
import com.mrdave19.PSStoreGameScraperV3.utils.CommonUtils;

@Service
public class ControllerServiceImpl implements ControllerService {

	@Autowired
	PSStoreGameWebExtendedDAO psStoreGameWebExtendedDAO;

	@Override
	public long getTotalPages(int pageSize,Optional<String> gameName) {
		
		Long totalNumberOfGames = psStoreGameWebExtendedDAO.getTotalNumberOfGames(gameName);
		long totalPages = (pageSize + totalNumberOfGames - 1) / pageSize;
		
		return totalPages;
	}

	@Override
	public void updatePageModel(Model model, int currentPage, long totalPages, HttpServletRequest request) {

		List<Integer> pages = IntStream.rangeClosed(currentPage - 3, currentPage + 3).boxed()
				.filter(i -> i > 0 && i <= totalPages).collect(Collectors.toList());

		model.addAttribute("pages", pages);
		model.addAttribute("currentpage", currentPage);
		model.addAttribute("totalpages", totalPages);
		model.addAttribute("parameters", request.getQueryString());
		
	}

	@Override
	public void updateGameModel(Model model, int currentPage, int pageSize, String orderbyParam,
			Optional<String> gameName) {
		
		List<PSStoreGameWebExtended> list = psStoreGameWebExtendedDAO.getGamesFrontPage(currentPage, pageSize, orderbyParam, gameName);

		List<String> minDate = new ArrayList<>();
		List<String> maxDate = new ArrayList<>();
		List<String> truncName = new ArrayList<>();

	
		datesAndNamesReadableFormat(list, minDate, maxDate, truncName);
		addGamesToModel(model, list, minDate, maxDate, truncName);

		
	}

	private void addGamesToModel(Model model, List<PSStoreGameWebExtended> list, List<String> minDate,
			List<String> maxDate, List<String> truncName) {
		model.addAttribute("games", list);
		model.addAttribute("minDate", minDate);
		model.addAttribute("maxDate", maxDate);
		model.addAttribute("truncName", truncName);
	}
	
	private void datesAndNamesReadableFormat(List<PSStoreGameWebExtended> list, List<String> minDate, List<String> maxDate,
			List<String> truncName) {
		for (PSStoreGameWebExtended game : list) {
			minDate.add(new SimpleDateFormat("yyyy/MM/dd").format(game.getMinDate().longValue()));
			maxDate.add(new SimpleDateFormat("yyyy/MM/dd").format(game.getMaxDate().longValue()));
			truncName.add(CommonUtils.truncString(game.getName(), 27));

		}
	}

	@Override
	public long getTotalPagesOfNewGames(int pageSize, Optional<String> gameName, long limitParam) {

		Long totalNumberOfGames = psStoreGameWebExtendedDAO.getTotalNumberOfNewGames(limitParam, gameName);
		long totalPages = (pageSize + totalNumberOfGames - 1) / pageSize;
		
		return totalPages;
	}

	@Override
	public void updateNewGameModel(Model model, int currentPage, int pageSize, String orderbyParam,
			Optional<String> gameName, long limitParam) {
		
		List<PSStoreGameWebExtended> list = psStoreGameWebExtendedDAO.getNewGames(currentPage, pageSize, orderbyParam, limitParam, gameName);

		List<String> minDate = new ArrayList<>();
		List<String> maxDate = new ArrayList<>();
		List<String> truncName = new ArrayList<>();

		datesAndNamesReadableFormat(list, minDate, maxDate, truncName);
		addGamesToModel(model, list, minDate, maxDate, truncName);
		
	}

	@Override
	public long getTotalPagesOfDiscountedGames(int pageSize, Optional<String> gameName, long limitParam) {
		Integer totalNumberOfGames = psStoreGameWebExtendedDAO.getTotalNumberOfDiscuntedGames(limitParam, gameName);
		long totalPages = (pageSize + totalNumberOfGames - 1) / pageSize;
		
		return totalPages;
	}

	@Override
	public void updateTopDiscountsGameModel(Model model, int currentPage, int pageSize, String orderbyParam,
			Optional<String> gameName, long limitParam) {
		
		DecimalFormat format = new DecimalFormat("$#,##0.00;-$#.##0.00");

		List<PSStoreGameWebExtended> list = psStoreGameWebExtendedDAO.getTopDiscountedGames(currentPage, pageSize, orderbyParam, limitParam, gameName);

		List<String> maxDate = new ArrayList<>();
		List<String> truncName = new ArrayList<>();
		List<String> priceChangeFormatted = new ArrayList<>();
		
		for (PSStoreGameWebExtended game : list) {
			
			maxDate.add(new SimpleDateFormat("yyyy/MM/dd").format(game.getMaxDate().longValue()));
			truncName.add(CommonUtils.truncString(game.getName(), 27));
			priceChangeFormatted.add(format.format(game.getPriceChange()).replace(",", "."));
		}

		model.addAttribute("games", list);
		model.addAttribute("maxDate", maxDate);
		model.addAttribute("truncName", truncName);
		model.addAttribute("priceChangeFormatted", priceChangeFormatted);
		
		
	}

	@Override
	public void updateHistoryGameModel(Model model, String id) {
		
		List<PSStoreGameWebExtended> gameHistory = psStoreGameWebExtendedDAO.getGameHistory(id);
		List<List<String>> description = new ArrayList<>();
		List<String> dates = new ArrayList<>();

		for (int i = 0; i < gameHistory.size(); i++) {
			if (i == 0) {
				description.add(Arrays.asList("Game added to the repository"));
			} else {
				description.add(
						CommonUtils.compareSequentObjects(gameHistory.get(i - 1), gameHistory.get(i), Arrays.asList("revtstmp")));
			}

			dates.add(new SimpleDateFormat("yyyy/MM/dd").format(gameHistory.get(i).getRevtstmp().longValue()));
		}

		model.addAttribute("gameHistory", gameHistory);
		model.addAttribute("description", description);
		model.addAttribute("dates", dates);
		
	}
	
	




	
	
	
}
