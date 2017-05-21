package com.vasileiou.trade;

import java.util.Calendar;
import java.util.List;

import com.vasileiou.trade.common.Constants;
import com.vasileiou.trade.common.FetcherImpl;
import com.vasileiou.trade.common.Printer;
import com.vasileiou.trade.common.Utils;
import com.vasileiou.trade.controllers.ReportGeneratorImpl;
import com.vasileiou.trade.controllers.Parser;
import com.vasileiou.trade.enums.BuyOrSell;
import com.vasileiou.trade.interfaces.Fetcher;
import com.vasileiou.trade.interfaces.ReportGenerator;
import com.vasileiou.trade.models.Instruction;
import com.vasileiou.trade.models.ValidatedData;

/**
 * This this the entry point for Daily Trading Reports
 *
 * @author Panagiotis Vasileiou
 * 
 */
public class TradeReporting 
{
    public static void main( String[] args )
    {
    	ReportGenerator reportGenerator = new ReportGeneratorImpl();
    	Fetcher fetcher = FetcherImpl.getInstance();

    	// Get some mock instructions for a specific date window
    	Calendar calendarStart = Calendar.getInstance();
    	Calendar calendarEnd = Calendar.getInstance();
    	
    	calendarStart = Calendar.getInstance();
    	calendarStart.set(Calendar.DAY_OF_MONTH, 22);
    	calendarStart.set(Calendar.MONTH, Calendar.MAY);
    	calendarStart.set(Calendar.YEAR, 2017);
    	
    	calendarEnd = Calendar.getInstance();
    	calendarEnd.set(Calendar.DAY_OF_MONTH, 29);
    	calendarEnd.set(Calendar.MONTH, Calendar.MAY);
    	calendarEnd.set(Calendar.YEAR, 2017);

    	int numberOfInstructions = 100;
    	int numberOfDiffEntities = 50;
    	List<Instruction> instructions = fetcher.getRandomInstructions(numberOfInstructions, numberOfDiffEntities, 
    			calendarStart, calendarEnd);
    	
    	// Validate the data and extract all the needed information
    	ValidatedData validatedData = Parser.parseInstructions(instructions);
    	
    	if (validatedData != null) {
	    	// Run the daily reports
	    	StringBuilder dailyOutgoingOutput = reportGenerator.generateDailyReport(validatedData.getInstructionsPerDate(), 
	    			BuyOrSell.BUY);
	    	Printer.print(dailyOutgoingOutput.toString());
	    	
	    	StringBuilder dailyIncomingOutput = reportGenerator.generateDailyReport(validatedData.getInstructionsPerDate(), 
	    			BuyOrSell.SELL);
	    	Printer.print(dailyIncomingOutput.toString());
	    	
	    	// Run the ranking reports
	    	StringBuilder outgoingRankingOuput = reportGenerator.generateRanking(validatedData.getOutgoingEntitiesMax(), 
	    			BuyOrSell.BUY);
	    	Printer.print(outgoingRankingOuput.toString());
	    	
	    	StringBuilder incomingRankingOuput = reportGenerator.generateRanking(validatedData.getIncomingEntitiesMax(), 
	    			BuyOrSell.SELL);
	    	Printer.print(incomingRankingOuput.toString());
	    	
	    	// Finalize the report
	    	Utils.printReportsFooter();
	    	
    	} else {
    		Printer.print(Constants.NOTHING_TO_SHOW);
    	}
    }
    
}
