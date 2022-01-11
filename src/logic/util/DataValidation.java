package logic.util;

import java.time.LocalDate;
import java.time.Month;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// @author Danilo D'Amico

public class DataValidation {
	
	public static LocalDate setDefaultDate() {
		return LocalDate.of(1970, Month.JANUARY, 1);
	}
	
	public static boolean isNatural(int number) {
		boolean bool = false;
		if(number >= 0)
			bool = true;
		
		return bool;
	}
	
	public static boolean isNotPastDate(LocalDate date) {
		boolean bool = false;
		if(!date.isBefore(LocalDate.now()))
			bool = true;
		
		return bool;
		
	}
	
	public static boolean isCorrectEmail(String email) {
		boolean bool = false;
		String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		
		if(matcher.matches())
			bool = true;
		
		return bool;
	}

}