package com.jpmc.tre.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.jpmc.tre.exception.ApplicationException;

public class CurrencyTest {

	private Currency currency;

	@Before
	public void setup() throws ApplicationException {
		currency = new Currency("USD");
	}

	@Test(expected=ApplicationException.class)	
	public void testCurrencyCodeMoreThanThreeChar() throws ApplicationException {
		currency = new Currency("ABCD");
	}
	
	@Test(expected=ApplicationException.class)	
	public void testCurrencyCodeLessThanThreeChar() throws ApplicationException {
		currency = new Currency("AB");
	}
	
	@Test
	public void testIsWorkingDay() {
		assertFalse(currency.isWorkingDay(LocalDate.parse("2017-02-12")));
		assertTrue(currency.isWorkingDay(LocalDate.parse("2017-02-13")));
	}

	@Test
	public void testIsHoliday() {
		assertTrue(currency.isHoliday(LocalDate.parse("2017-02-12")));
		assertFalse(currency.isHoliday(LocalDate.parse("2017-02-13")));
	}

}
