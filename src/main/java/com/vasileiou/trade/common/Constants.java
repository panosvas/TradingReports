package com.vasileiou.trade.common;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Declaration for all program constants
 * 
 * @author Panagiotis Vasileiou
 *
 */
public class Constants {
	
	// Problem Input based on Requirements and Specifications
	public static final List<String> DIFFERENT_WEEK_CURRENCIES = Arrays.asList("AED", "SAR");
	public static final List<Integer> CALENDAR_COMMON_WEEKEND = Arrays.asList(Calendar.SATURDAY, Calendar.SUNDAY);
	public static final List<Integer> CALENDAR_EXCEPTION_WEEKEND = Arrays.asList(Calendar.FRIDAY, Calendar.SATURDAY);
	public static final String DATE_FORMAT = "dd MMM yyyy";
	public static final String INPUT_BUY_FLAG = "B";
	public static final String INPUT_SELL_FLAG = "S";
	
	// Printer output Constants
	public static final String DAILY_INCOMING_OUTPUT_HEADER = "+++++++++++++++ INCOMING DAILY REPORT +++++++++++++++";
	public static final String DAILY_OUTGOING_OUTPUT_HEADER = "+++++++++++++++ OUTGOING DAILY REPORT +++++++++++++++";
	public static final String INCOMING_RANKING_HEADER = "++++++++++++++ INCOMING RANKING REPORT ++++++++++++++";
	public static final String OUTGOING_RANKING_HEADER = "++++++++++++++ OUTGOING RANKING REPORT ++++++++++++++";
	public static final String REPORT_FOOTER = "=====================================================";
	public static final String NO_INSTRUCTIONS_AVAILABLE = "There are no instructions to display output";
	public static final String REPORT_GENERATION_FINISHED = "\nTrade Report Generation Finished!";
	public static final String NOTHING_TO_SHOW = "Nothing to show...\n";
	public static final String BUY_OR_SELL_IS_MISSING = "Buy or Sell is missing...\n";
	public static final String DAILY_REPORT_HEADERS = "\n\nDate -> Amount\n-----------------------\n";
	public static final String RANKING_REPORT_HEADERS = "\n\nRank. Entity -> Amount\n-----------------------\n";

}
