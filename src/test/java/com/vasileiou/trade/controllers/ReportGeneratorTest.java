package com.vasileiou.trade.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.vasileiou.trade.common.Constants;
import com.vasileiou.trade.common.FetcherImpl;
import com.vasileiou.trade.enums.BuyOrSell;
import com.vasileiou.trade.interfaces.ReportGenerator;
import com.vasileiou.trade.models.Instruction;
import com.vasileiou.trade.models.ValidatedData;

/**
 * Various test cases for the report generator
 * 
 * @author Panagiotis Vasileiou
 *
 */
public class ReportGeneratorTest {
	
	ReportGenerator reportGenerator;
	
	/**
	 * Initialize the report generator
	 */
	@Before
	public void initializeReportGenerator () {
		reportGenerator = new ReportGeneratorImpl();
	}
	
	/**
	 * Given null or empty instructions when generating daily report
	 * then no instructions available constant will be displayed
	 */
	@Test
	public void givenNullOrEmptyInstructionsWhenGeneratingDailyReportThenNothingToShow()
    {
		Assert.assertEquals(reportGenerator.generateDailyReport(null, BuyOrSell.BUY).toString(), 
				Constants.NO_INSTRUCTIONS_AVAILABLE);
		Assert.assertEquals(reportGenerator.generateDailyReport(new TreeMap<>(), BuyOrSell.BUY).toString(), 
				Constants.NO_INSTRUCTIONS_AVAILABLE);
    }
	
	/**
	 * Given null buy or sell when generating daily report
	 * then buy or sell missing constant will be displayed
	 */
	@Test
	public void givenNullBuyOrSellWhenGeneratingDailyReportThenBuyOrSellMissing()
    {
		List<Instruction> instructions = new ArrayList<>();
    	Instruction instruction1 = new Instruction("entity1", "B", new Double(0.22), 
    			"EUR", "18 May 2017", "20 May 2017", 100L, new Double(20));
    	
    	instructions.add(instruction1);
    	ValidatedData validatedData = Parser.parseInstructions(instructions);
    	
		Assert.assertEquals(reportGenerator.generateDailyReport(validatedData.getInstructionsPerDate(), null).toString(), 
				Constants.BUY_OR_SELL_IS_MISSING);
    }
	
	/**
	 * Given valid outgoing instructions when generating daily report
	 * then proper string will be returned
	 */
	@Test
	public void givenValidOutgoingWhenGeneratingDailyReportThenShowReport()
    {
		List<Instruction> instructions = new ArrayList<>();
		Instruction instruction1 = new Instruction("entity1", "B", new Double(0.22), "EUR", "18 May 2017", "20 May 2017", 100L, new Double(20));
		Instruction instruction2 = new Instruction("entity2", "B", new Double(0.24), "AED", "19 May 2017", "24 May 2017", 100L, new Double(17));
		Instruction instruction3 = new Instruction("entity3", "B", new Double(0.24), "EUR", "19 May 2017", "24 May 2017", 100L, new Double(19));
		
		instructions.add(instruction1);
		instructions.add(instruction2);
		instructions.add(instruction3);
		
		String expectedOutput = Constants.DAILY_OUTGOING_OUTPUT_HEADER + Constants.DAILY_REPORT_HEADERS
				+ "22 May 2017 -> 440.0$\n"
				+ "23 May 2017 -> 0.0$\n"
				+ "24 May 2017 -> 864.0$\n";
		
		ValidatedData validatedData = Parser.parseInstructions(instructions);
		Assert.assertEquals(reportGenerator.generateDailyReport(validatedData.getInstructionsPerDate(), BuyOrSell.BUY).toString(), 
				expectedOutput);
		
    }
	
	/**
	 * Given valid incoming instructions when generating daily report
	 * then proper string will be returned
	 */
	@Test
	public void givenValidIncomingWhenGeneratingDailyReportThenShowReport()
    {
		List<Instruction> instructions = new ArrayList<>();
		Instruction instruction1 = new Instruction("entity1", "S", new Double(0.22), "EUR", "18 May 2017", "20 May 2017", 100L, new Double(20));
		Instruction instruction2 = new Instruction("entity2", "S", new Double(0.24), "AED", "19 May 2017", "24 May 2017", 100L, new Double(17));
		Instruction instruction3 = new Instruction("entity3", "S", new Double(0.24), "EUR", "19 May 2017", "24 May 2017", 100L, new Double(19));
		
		instructions.add(instruction1);
		instructions.add(instruction2);
		instructions.add(instruction3);
		
		String expectedOutput = Constants.DAILY_INCOMING_OUTPUT_HEADER + Constants.DAILY_REPORT_HEADERS
				+ "22 May 2017 -> 440.0$\n"
				+ "23 May 2017 -> 0.0$\n"
				+ "24 May 2017 -> 864.0$\n";
		
		ValidatedData validatedData = Parser.parseInstructions(instructions);
		Assert.assertEquals(reportGenerator.generateDailyReport(validatedData.getInstructionsPerDate(), BuyOrSell.SELL).toString(), 
				expectedOutput);
		
    }
	
	/**
	 * Given a randomly generated large input of instructions when generating daily report for outgoing
	 * for one week then Saturday should be empty as for all currencies it is included in weekend
	 * and no settlement takes place
	 */
	@Test
	public void givenLargeOutgoingWhenGeneratingDailyReportOneWeekThenSaturdayZero()
    {
		Calendar calendarStart = Calendar.getInstance();
    	calendarStart.set(Calendar.DAY_OF_MONTH, 22);
    	calendarStart.set(Calendar.MONTH, Calendar.MAY);
    	calendarStart.set(Calendar.YEAR, 2017);
    	
    	Calendar calendarEnd = Calendar.getInstance();
    	calendarEnd.set(Calendar.DAY_OF_MONTH, 29);
    	calendarEnd.set(Calendar.MONTH, Calendar.MAY);
    	calendarEnd.set(Calendar.YEAR, 2017);
    	
    	List<Instruction> instructions = new FetcherImpl().getRandomInstructions(100, 50, calendarStart, calendarEnd);
    	ValidatedData validatedData = Parser.parseInstructions(instructions);
    	
    	String expectedContainingString = "27 May 2017 -> 0.0$";
    	
    	Assert.assertThat(reportGenerator.generateDailyReport(validatedData.getInstructionsPerDate(), BuyOrSell.BUY).toString(),
    			CoreMatchers.containsString(expectedContainingString));
    }
	
	/**
	 * Given a randomly generated large input of instructions when generating daily report for incoming
	 * for one week then Saturday should be empty as for all currencies it is included in weekend
	 * and no settlement takes place
	 */
	@Test
	public void givenLargeIncomingWhenGeneratingDailyReportOneWeekThenSaturdayZero()
    {
		Calendar calendarStart = Calendar.getInstance();
    	calendarStart.set(Calendar.DAY_OF_MONTH, 22);
    	calendarStart.set(Calendar.MONTH, Calendar.MAY);
    	calendarStart.set(Calendar.YEAR, 2017);
    	
    	Calendar calendarEnd = Calendar.getInstance();
    	calendarEnd.set(Calendar.DAY_OF_MONTH, 29);
    	calendarEnd.set(Calendar.MONTH, Calendar.MAY);
    	calendarEnd.set(Calendar.YEAR, 2017);
    	
    	List<Instruction> instructions = new FetcherImpl().getRandomInstructions(100, 50, calendarStart, calendarEnd);
    	ValidatedData validatedData = Parser.parseInstructions(instructions);
    	
    	String expectedContainingString = "27 May 2017 -> 0.0$";
    	
    	Assert.assertThat(reportGenerator.generateDailyReport(validatedData.getInstructionsPerDate(), BuyOrSell.SELL).toString(),
    			CoreMatchers.containsString(expectedContainingString));
    }
	
	/**
	 * Given null or empty instructions when generating ranking report
	 * then no instructions available constant will be displayed
	 */
	@Test
	public void givenNullOrEmptyInstructionsWhenGeneratingRankingReportThenNothingToShow()
    {
		Assert.assertEquals(reportGenerator.generateRanking(null, BuyOrSell.BUY).toString(), 
				Constants.NO_INSTRUCTIONS_AVAILABLE);
		Assert.assertEquals(reportGenerator.generateRanking(new HashMap<>(), BuyOrSell.BUY).toString(), 
				Constants.NO_INSTRUCTIONS_AVAILABLE);
    }
	
	/**
	 * Given null buy or sell when generating ranking report
	 * then buy or sell missing constant will be displayed
	 */
	@Test
	public void givenNullBuyOrSellWhenGeneratingRankingReportThenNothingToShow()
    {
		List<Instruction> instructions = new ArrayList<>();
    	Instruction instruction1 = new Instruction("entity1", "B", new Double(0.22), 
    			"EUR", "18 May 2017", "20 May 2017", 100L, new Double(20));
    	
    	instructions.add(instruction1);
    	ValidatedData validatedData = Parser.parseInstructions(instructions);
    	
		Assert.assertEquals(reportGenerator.generateRanking(validatedData.getOutgoingEntitiesMax(), null).toString(), 
				Constants.BUY_OR_SELL_IS_MISSING);
    }
	
	/**
	 * Given valid outgoing instructions when generating ranking report
	 * then proper string will be returned
	 */
	@Test
	public void givenValidOutgoingWhenGeneratingRankingReportThenShowReport()
    {
		List<Instruction> instructions = new ArrayList<>();
		Instruction instruction1 = new Instruction("entity1", "B", new Double(0.22), "EUR", "18 May 2017", "20 May 2017", 100L, new Double(20));
		Instruction instruction2 = new Instruction("entity2", "B", new Double(0.24), "AED", "19 May 2017", "24 May 2017", 100L, new Double(17));
		Instruction instruction3 = new Instruction("entity3", "B", new Double(0.24), "EUR", "19 May 2017", "24 May 2017", 100L, new Double(19));
		
		instructions.add(instruction1);
		instructions.add(instruction2);
		instructions.add(instruction3);
		
		String expectedOutput = Constants.OUTGOING_RANKING_HEADER + Constants.RANKING_REPORT_HEADERS
				+ "1. entity3 -> 456.0$\n"
				+ "2. entity1 -> 440.0$\n" 
				+ "3. entity2 -> 408.0$\n";

		ValidatedData validatedData = Parser.parseInstructions(instructions);

		Assert.assertEquals(reportGenerator.generateRanking(validatedData.getOutgoingEntitiesMax(), BuyOrSell.BUY).toString(), 
				expectedOutput);
		
    }
	
	/**
	 * Given valid incoming instructions when generating ranking report
	 * then proper string will be returned
	 */
	@Test
	public void givenValidIncomingWhenGeneratingRankingReportThenShowReport()
    {
		List<Instruction> instructions = new ArrayList<>();
		Instruction instruction1 = new Instruction("entity1", "S", new Double(0.22), "EUR", "18 May 2017", "20 May 2017", 100L, new Double(20));
		Instruction instruction2 = new Instruction("entity2", "S", new Double(0.24), "AED", "19 May 2017", "24 May 2017", 100L, new Double(17));
		Instruction instruction3 = new Instruction("entity3", "S", new Double(0.24), "EUR", "19 May 2017", "24 May 2017", 100L, new Double(19));
		
		instructions.add(instruction1);
		instructions.add(instruction2);
		instructions.add(instruction3);
		
		String expectedOutput = Constants.INCOMING_RANKING_HEADER + Constants.RANKING_REPORT_HEADERS
				+ "1. entity3 -> 456.0$\n"
				+ "2. entity1 -> 440.0$\n" 
				+ "3. entity2 -> 408.0$\n";

		ValidatedData validatedData = Parser.parseInstructions(instructions);

		Assert.assertEquals(reportGenerator.generateRanking(validatedData.getIncomingEntitiesMax(), BuyOrSell.SELL).toString(), 
				expectedOutput);
		
    }
}
