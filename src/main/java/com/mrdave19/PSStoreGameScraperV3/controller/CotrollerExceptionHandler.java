package com.mrdave19.PSStoreGameScraperV3.controller;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CotrollerExceptionHandler {
	
	@ExceptionHandler(ConversionFailedException.class)
	public ModelAndView handleNumericException(ConversionFailedException exc) {
		ModelMap model = new ModelMap();
		model.addAttribute("error", "illegaltype");
		return new ModelAndView("redirect:/playstation-store-games/",model);
	}
	
	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	public ModelAndView handleNumericException(InvalidDataAccessApiUsageException exc) {
		ModelMap model = new ModelMap();
		model.addAttribute("error", "illegalargument");
		return new ModelAndView("redirect:/playstation-store-games/",model);
	}
	
	

}
