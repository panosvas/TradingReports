package com.vasileiou.trade.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Definition of all needed utilities
 * 
 * @author Panagiotis Vasileiou
 *
 */
public class Utils {

	/**
	 * Based on currency and date, checks if it falls into a working day
	 * 
	 * @param currency {@link String} for defining the instruction currency
	 * @param date {@link Date} for the settlement date
	 * @return true if it is a working date and false otherwise
	 */
	public static boolean isWorkingDay(String currency, Date date) {
		
		// Get the appropriate weekend based on the given currency
		List<Integer> weekend = Constants.DIFFERENT_WEEK_CURRENCIES.contains(currency)
				? Constants.CALENDAR_EXCEPTION_WEEKEND
				: Constants.CALENDAR_COMMON_WEEKEND;
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		
		if (weekend.contains(dayOfWeek)) {
			return false;
		} else {
			return true;
		}
		
	}
	
	/**
	 * Converts a given string in a predefined format (see {@link Constants}) to {@link Date}
	 * 
	 * @param dateContent {@link String} variable defining the date
	 * @return a {@link Date} object which is the conversion of the given string
	 */
	public static Date convertStringToDate(String dateContent) {
		
		// Return if given date is null
		if (dateContent == null)
			return null;
		
		DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
		try {
			return dateFormat.parse(dateContent);
		} catch (ParseException e) {
			Printer.print(e.getMessage());
			return null;
		}
		
	}
	
	/**
	 * In case of a day falling into weekend then this function gets the next working day
	 * 
	 * @param currency {@link String} currency
	 * @param initialDate {@link Date} representing the date needing correction
	 * @return the {@link Date} corrected object
	 */
	public static Date dateCorrection(String currency, Date initialDate) {
		
		// Return if given date is null
		if (initialDate == null)
			return null;
		
		// Get the proper weekend based on currency
		List<Integer> weekend = Constants.DIFFERENT_WEEK_CURRENCIES.contains(currency)
				? Constants.CALENDAR_EXCEPTION_WEEKEND
				: Constants.CALENDAR_COMMON_WEEKEND;
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(initialDate);
		
		// Incrementally add one day till fall into a working day
		while (!isWorkingDay(calendar.get(Calendar.DAY_OF_WEEK), weekend)) {
			calendar.add(Calendar.DATE, 1);
		}
		
		return calendar.getTime();
		
	}
	
	/**
	 * Simple check for a day of week if it is a working day
	 * 
	 * @param dayOfWeek {@link Integer} day of week, starting from Sunday (0)
	 * @param weekend the {@link List} of weekend
	 * @return true if it is a working day and false otherwise
	 */
	private static boolean isWorkingDay(Integer dayOfWeek, List<Integer> weekend) {
		
		// If day falls into a weekend then it is not a working day
		if (weekend.contains(dayOfWeek)) {
			return false;
		} else {
			return true;
		}
		
	}
	
	/**
	 * Calculates the total amount of instruction. It is applied on valid instructions
	 * so, there are no field null checks.
	 * 
	 * @param pricePerUnit {@link Double} the price per unit
	 * @param units {@link Long} the number of units
	 * @param agreedFx {@link Double} the agreed foreign exchange rate
	 * @return {@link Double} with the total amount of instruction
	 */
	public static Double calculateAmountOfTrade(Double pricePerUnit, Long units, Double agreedFx) {
		
		/*It is null safe to perform the multiplication as the Parser keeps only valid data
		So, it is not necessary to perform null check at every instruction*/
		return pricePerUnit * units * agreedFx;
		
	}

	/**
	 * Print out the footer for declaring the end of reporting
	 */
	public static void printReportsFooter() {
		
		Printer.print(Constants.REPORT_FOOTER);
        Printer.print(Constants.REPORT_GENERATION_FINISHED);
		
	}
	
}
