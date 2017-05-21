package com.vasileiou.trade.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.stream.Collectors;

import com.vasileiou.trade.common.Constants;
import com.vasileiou.trade.enums.BuyOrSell;
import com.vasileiou.trade.interfaces.ReportGenerator;
import com.vasileiou.trade.models.Instruction;

/**
 * A {@link ReportGenerator} implementation for generating trading reports
 * 
 * @author Panagiotis Vasileiou
 *
 */
public class ReportGeneratorImpl implements ReportGenerator {
	
	// Default constructor
	public ReportGeneratorImpl() {
		super();
	}

	@Override
	public StringBuilder generateDailyReport(SortedMap<Date, List<Instruction> > existingDates, BuyOrSell buyOrSell) {
		
		// If there are no instructions then exit with the appropriate message
		if (existingDates == null || existingDates.size() == 0)
			return new StringBuilder(Constants.NO_INSTRUCTIONS_AVAILABLE);
		
		// If buy or sell is not specified then exit with the appropriate message
		if (buyOrSell == null)
			return new StringBuilder(Constants.BUY_OR_SELL_IS_MISSING);
		
		StringBuilder dailyOuput = new StringBuilder(buyOrSell == BuyOrSell.SELL 
																? Constants.DAILY_INCOMING_OUTPUT_HEADER
																: Constants.DAILY_OUTGOING_OUTPUT_HEADER);
				
		Calendar calendar = Calendar.getInstance();
		Date firstDate = existingDates.firstKey();
		Date lastDate = existingDates.lastKey();
		calendar.setTime(firstDate);
		
		DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
		
		dailyOuput.append(Constants.DAILY_REPORT_HEADERS);
				
		List<Instruction> dailyInstructions;
		
		// Iterate over all the dates without excluding the empty dates in the report 
		while(!calendar.getTime().after(lastDate)) {
			
			dailyInstructions = existingDates.get(calendar.getTime());
			double totalDailyIncoming = 0;
			
			// Sum up all the instruction amounts for that day
			if (dailyInstructions != null && dailyInstructions.size() > 0) {
				for (Instruction instruction : dailyInstructions) {
					if (instruction.getBuyOrSell() == buyOrSell) {
						totalDailyIncoming += instruction.getAmountOfTrade();
					}
				}
			}
			
			// Update the output and then increment the date
			dailyOuput.append(dateFormat.format(calendar.getTime())).append(" -> ")
						.append(totalDailyIncoming).append("$").append("\n");
			
			calendar.add(Calendar.DATE, 1);
		}
	
		return dailyOuput;
	}

	@Override
	public StringBuilder generateRanking(HashMap<String, Double> maximumAmounts, BuyOrSell buyOrSell) {
		
		// If there are no instructions then exit with the appropriate message
		if (maximumAmounts == null || maximumAmounts.size() == 0)
			return new StringBuilder(Constants.NO_INSTRUCTIONS_AVAILABLE);
		
		// If buy or sell is not specified then exit with the appropriate message
		if (buyOrSell == null)
			return new StringBuilder(Constants.BUY_OR_SELL_IS_MISSING);
		
		StringBuilder rankingOutput = new StringBuilder(buyOrSell == BuyOrSell.SELL 
				? Constants.INCOMING_RANKING_HEADER
				: Constants.OUTGOING_RANKING_HEADER);
		
		rankingOutput.append(Constants.RANKING_REPORT_HEADERS);
		
		// Sort the entities based on the value in descending order, using Java 8 streams
		LinkedHashMap<String, Double> rankingMap = maximumAmounts.entrySet()
		        .stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
		        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		
		// Iterate over the sorted map and update the output with the ranking
		long rank = 0;
		for (Map.Entry<String, Double> entityEntry : rankingMap.entrySet()) {
		    String entity = entityEntry.getKey();
		    Double amount = entityEntry.getValue();
		    
		    rankingOutput.append(++rank).append(". ").append(entity).append(" -> " + amount).append("$").append("\n");
		}
		
		return rankingOutput;
		
	}

}
