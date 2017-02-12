package com.jpmc.tre.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import com.jpmc.tre.exception.ApplicationException;

public class Currency {

	private String code;
	private Collection<DayOfWeek> weekOff = new HashSet<>();

	public Currency(String code) throws ApplicationException {		
		this(code, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
	}

	private boolean isValid(String code) throws ApplicationException {
		if(code!=null && code.trim().length()!=3){
			throw new ApplicationException("Invalid Currency Code: " + code);
		}
		return true;
	}

	public Currency(String code, DayOfWeek... weekOffs) throws ApplicationException {
		if(isValid(code)){
			this.code = code;
			this.weekOff.addAll(Arrays.asList(weekOffs));
		}
	}
	
	public Currency(String code, Collection<DayOfWeek> weekOffs) {
		this.code = code;
		this.weekOff.addAll(weekOffs);
	}
	
	public boolean isWorkingDay(LocalDate date){
		return !isHoliday(date);
	}
	
	public String getCode() {
		return code;
	}

	public boolean isHoliday(LocalDate date) {
		return weekOff.contains(date.getDayOfWeek());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Currency other = (Currency) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Currency [code=" + code + "]";
	}	
}
