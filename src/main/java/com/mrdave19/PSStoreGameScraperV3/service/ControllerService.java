package com.mrdave19.PSStoreGameScraperV3.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface ControllerService {

	public long getTotalPages(int pageSize, Optional<String> gameName);
	public void updatePageModel(Model model, int currentPage, long totalPages,HttpServletRequest request);
	public void updateGameModel(Model model,int currentPage,int pageSize,String orderbyParam,Optional<String> gameName);
	public long getTotalPagesOfNewGames(int pageSize, Optional<String> gameName, long limitParam);
	public void updateNewGameModel(Model model,int currentPage,int pageSize,String orderbyParam,Optional<String> gameName, long limitParam);
	public long getTotalPagesOfDiscountedGames(int pageSize, Optional<String> gameName, long limitParam);
	public void updateTopDiscountsGameModel(Model model,int currentPage,int pageSize,String orderbyParam,Optional<String> gameName, long limitParam);
	public void updateHistoryGameModel(Model model,String id);
}

