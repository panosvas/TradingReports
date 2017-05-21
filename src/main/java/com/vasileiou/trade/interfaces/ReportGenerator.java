package com.vasileiou.trade.interfaces;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;

import com.vasileiou.trade.enums.BuyOrSell;
import com.vasileiou.trade.models.Instruction;

/**
 * The interface for trading reports generator
 * 
 * @author Panagiotis Vasileiou
 *
 */
public interface ReportGenerator {
	
	/**
	 * Generates the daily trading reports for both buy or sell instructions
	 * 
	 * @param existingDates the {@link SortedMap} with the sorted dates and their lists of instructions
	 * @param buyOrSell the {@link BuyOrSell} object defining the type of instruction
	 */
	StringBuilder generateDailyReport(SortedMap<Date, List<Instruction> > existingDates, BuyOrSell buyOrSell);
	
	/**
	 * Generates the ranking trading reports for both buy or sell instructions
	 * 
	 * @param maximumAmounts the {@link HashMap} of entity and maximum amount pairs
	 * @param buyOrSell buyOrSell the {@link BuyOrSell} object defining the type of instruction
	 */
	StringBuilder generateRanking(HashMap<String, Double> maximumAmounts, BuyOrSell buyOrSell);

}
