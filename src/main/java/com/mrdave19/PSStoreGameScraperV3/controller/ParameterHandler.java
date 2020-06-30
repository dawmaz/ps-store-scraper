package com.mrdave19.PSStoreGameScraperV3.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
@Component
public class ParameterHandler {
	
	
	@Bean(name="produceNewParam")
	public NewParam produceParameter() {

		return new NewParam();
}
	
		
	class NewParam{
		public String getParams(String currentParams, String newParam) {
			if(currentParams==null) return "?" +newParam;
			String newParamName=newParam.substring(0, newParam.indexOf("=")-1);
			
			List<String> params =Arrays.asList(currentParams.split("&")).stream()
							.filter(i->!i.startsWith(newParamName)&& !i.startsWith("error"))
							.collect(Collectors.toList());
			params.add(newParam);

			return params.stream().collect(Collectors.joining("&","?",""));	
		}
		
	}
}