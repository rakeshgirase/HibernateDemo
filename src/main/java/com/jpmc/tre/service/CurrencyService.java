package com.jpmc.tre.service;

import java.time.DayOfWeek;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.jpmc.tre.exception.ApplicationException;
import com.jpmc.tre.logging.Logger;
import com.jpmc.tre.model.Currency;
import com.jpmc.tre.util.PropertyService;


public class CurrencyService {
	
	private Map<String, Currency> cache = new HashMap<>();
	private final String SEPARATOR = ",";
	private String CURRENCY_WITH_WEEKENDS_SEPARATOR = ";";
	
	private static class SingletonCurrencyService{
        private static final CurrencyService INSTANCE = new CurrencyService();
    }
	
	public static CurrencyService getInstance(){
		return SingletonCurrencyService.INSTANCE;
	}
	
	private CurrencyService() {
		loadCurrencies();
	}

	private void loadCurrencies() {
		populateCache(loadRegularCurrencies());
		populateCache(loadCurrenciesWithDifferentWeekends());
		Logger.info("Loaded %s currencies from properties file.", cache.size());
	}
	
	private void populateCache(Collection<Currency> currencies) {
		currencies.forEach(ccy->cache.put(ccy.getCode(), ccy));				
	}

	private Collection<Currency> loadCurrenciesWithDifferentWeekends() {
		String customWeekendCurrencies = PropertyService.getInstance().getProperty("currency.with.custom.weekend");
		String[] currenciesWithWeekends = customWeekendCurrencies.split(CURRENCY_WITH_WEEKENDS_SEPARATOR);
		Collection<Currency> currenciesWithDifferentWeekends = new HashSet<>();
		for (String currencyWithWeekend : currenciesWithWeekends) {
			String[] ccyWithWeekends = currencyWithWeekend.split(SEPARATOR);
			String code = null;
			Collection<DayOfWeek> weekends = new HashSet<>();
			boolean isCurrency = true;
			for (String string : ccyWithWeekends) {				
				if(isCurrency){
					code = string;
					isCurrency = false;
				}else{
					weekends.add(DayOfWeek.valueOf(string));
				}
			}
			currenciesWithDifferentWeekends.add(new Currency(code, weekends));
		}		
		return currenciesWithDifferentWeekends ;
	}

	private Collection<Currency> loadRegularCurrencies() {
		Collection<Currency> regularCurrencies = new HashSet<>();
		String concatenatedCurrencies = PropertyService.getInstance().getProperty("currency.with.regular.weekend");
		String[] codes = concatenatedCurrencies.split(SEPARATOR);		
		for (String code : codes) {
			try {				
				regularCurrencies.add(new Currency(code));
			} catch (ApplicationException e) {
				Logger.warn("Invalid currency configured in properties file: " + code);
			}
		}
		return regularCurrencies;
	}

	public Currency getCurrency(String code) throws ApplicationException{
		if(cache.containsKey(code)){
			return cache.get(code);
		}
		throw new ApplicationException("Invalid Currency code: " + code);
	}	
}