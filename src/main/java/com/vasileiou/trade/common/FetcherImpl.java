package com.vasileiou.trade.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.vasileiou.trade.interfaces.Fetcher;
import com.vasileiou.trade.models.Instruction;

/**
 * A {@link Fetcher} implementation for getting mock instructions
 * 
 * @author Panagiotis Vasileiou
 *
 */
public class FetcherImpl implements Fetcher {
	
	// Auto Thread Safe Singleton Pattern
	private static class SingletonHolder {
        static final FetcherImpl INSTANCE = new FetcherImpl();
    }

    public static FetcherImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }
    
    // Default constructor
    public FetcherImpl() {
    	
    }
	
    @Override
	public List<Instruction> getInstructionsFromClients() {
		
		List<Instruction> instructions = new ArrayList<>();
    	Instruction instruction1 = new Instruction("foo", "B", new Double(0.22), "EUR", "18 May 2017", "20 May 2017", 100L, new Double(20));
    	Instruction instruction2 = new Instruction("bar", "B", new Double(0.24), "EUR", "18 May 2017", "24 May 2017", 100L, new Double(20));
    	Instruction instruction3 = new Instruction("bar", "S", new Double(0.24), "EUR", "18 May 2017", "24 May 2017", 100L, new Double(9));
    	
    	instructions.add(instruction1);
    	instructions.add(instruction2);
    	instructions.add(instruction3);
    	
    	return instructions;
	}
    
    @Override
    public List<Instruction> getRandomInstructions(int numberOfInstructions, int numberOfEntities, 
    		Calendar dateStart, Calendar dateEnd) {
    	
    	List<Instruction> instructions = new ArrayList<>();
    	
    	if (dateStart == null || dateEnd == null || dateEnd.getTime().before(dateStart.getTime()))
    		return new ArrayList<>();
    	
    	List<String> availableCurrencies = Arrays.asList("EUR", "GBP", "AED", "INR", "CAD", "AUD", "CNY", "USD", "SAR");
    	List<String> availableDates = new ArrayList<>();
    	
    	// Increment by one to get all date window range
    	DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
    	while(!dateStart.getTime().after(dateEnd.getTime())) {
    		availableDates.add(dateFormat.format(dateStart.getTime()));
    		dateStart.add(Calendar.DATE, 1);
    	}
    	
    	Random random = new Random();
    	
    	// Generate the desired number of random instructions based on some random number assumptions for the fields
    	for (int instructionCounter = 0; instructionCounter < numberOfInstructions; instructionCounter++) {
    		
    		Instruction instruction = new Instruction();
    		instruction.setEntity("Entity-" + String.valueOf(random.nextInt(numberOfEntities - 1 + 1) + 1));
    		instruction.setBuyOrSellStringFlag(random.nextBoolean() ? "B" : "S");
    		instruction.setAgreedFx(Double.parseDouble(String.format("%." + 2 + "f", random.nextDouble())) + 0.2);
    		instruction.setCurrency(availableCurrencies.get(random.nextInt(8 - 0 + 1) + 0));
    		
    		int instructionDateIndex = random.nextInt(availableDates.size() - 1 - 0 + 1) + 0;
    		int settlementDateIndex = random.nextInt(availableDates.size() - 1 - instructionDateIndex + 1)
    				+ instructionDateIndex;
    		
    		instruction.setInstructionDate(availableDates.get(instructionDateIndex));
    		instruction.setSettlementDate(availableDates.get(settlementDateIndex));

    		instruction.setUnits(new Long(random.nextInt(1000 - 2 + 1) + 2));
    		instruction.setPricePerUnit(new Double(random.nextInt(200 - 5 + 1) + 5 
    				+ Double.parseDouble(String.format("%." + 2 + "f", random.nextDouble()))));
    		
    		instructions.add(instruction);
    	}
    	
    	return instructions;
    }

}
