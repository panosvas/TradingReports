package com.vasileiou.trade.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.vasileiou.trade.common.Constants;
import com.vasileiou.trade.common.Utils;
import com.vasileiou.trade.enums.BuyOrSell;
import com.vasileiou.trade.enums.Status;
import com.vasileiou.trade.models.Instruction;
import com.vasileiou.trade.models.ValidatedData;

/**
 * The implementation of parsing instruction methods
 * 
 * @author Panagiotis Vasileiou
 *
 */
public class Parser {
	
	/**
	 * This method holds the core logic for parsing and extracting the desired information from retrieved instructions
	 * in order to generate the valid data to proceed with reports generation
	 * 
	 * @param instructions a {@link List} of {@link Instruction} objects to be parsed
	 * @return the {@link ValidatedData} object with the analyzed data
	 */
	public static ValidatedData parseInstructions(List<Instruction> instructions) {
		
		// If nothing is given then return null
		if (instructions == null || instructions.size() == 0)
			return null;
		
		// Initialize maps for dates with lists and incoming - outgoing ranking
		SortedMap<Date, List<Instruction> > instructionsPerDate = new TreeMap<Date, List<Instruction>>();
		HashMap<String, Double> incomingEntitiesMax = new HashMap<>();
		HashMap<String, Double> outgoingEntitiesMax = new HashMap<>();
		
		// Parse every given instruction
		for (Instruction instruction : instructions) {
			
			// Do not proceed if at least one of the mandatory fields is missing
			if (instruction.getEntity() == null || instruction.getAgreedFx() == null
					|| instruction.getBuyOrSellStringFlag() == null || instruction.getCurrency() == null
					|| instruction.getInstructionDate() == null || instruction.getSettlementDate() == null
					|| instruction.getUnits() == null || instruction.getPricePerUnit() == null
					|| (!instruction.getBuyOrSellStringFlag().equalsIgnoreCase(Constants.INPUT_BUY_FLAG)
							&& !instruction.getBuyOrSellStringFlag().equalsIgnoreCase(Constants.INPUT_SELL_FLAG))) {
				
				instruction.setStatus(Status.MISSING_PROPERTY);
				
			} else {
				
				// Convert Buy or Sell given string input to the appropriate enumeration
				// It is known from previous condition that it is either -B- or -S- so, proceeding in the following
				// way is safe
				if (instruction.getBuyOrSellStringFlag().equalsIgnoreCase(Constants.INPUT_BUY_FLAG)) {
					instruction.setBuyOrSell(BuyOrSell.BUY);
				} else {
					instruction.setBuyOrSell(BuyOrSell.SELL);
				}
				
				// Convert the string date to a real date object and validate it
				instruction.setFormattedInstructionDate(Utils.convertStringToDate(instruction.getInstructionDate()));
				instruction.setFormattedSettlementDate(Utils.convertStringToDate(instruction.getSettlementDate()));
				
				if (instruction.getFormattedInstructionDate() == null 
						|| instruction.getFormattedSettlementDate() == null) {
					
					instruction.setStatus(Status.WRONG_DATE_FORMAT);
					
				} else if (isSettlementBeforeInstruction(instruction.getFormattedSettlementDate(), 
						instruction.getFormattedInstructionDate())) {
					
					// Do not allow a settlement date before the instruction date
					instruction.setStatus(Status.INVALID_SETTLEMENT_DATE);
					
				} else {
					
					// If the day is not a working day, updated to the next working day, based on the currency
					if (!Utils.isWorkingDay(instruction.getCurrency(), instruction.getFormattedSettlementDate())) {
						Date updatedDate = Utils.dateCorrection(instruction.getCurrency(),
																instruction.getFormattedSettlementDate());
						
						if (updatedDate != null) {
							instruction.setInitialFormattedSettlementDate(instruction.getFormattedSettlementDate());
							instruction.setFormattedSettlementDate(updatedDate);
							
							instruction.setStatus(Status.VALID);
						} else {
							instruction.setStatus(Status.INVALID_SETTLEMENT_DATE);
						}
						
					} else {
						instruction.setStatus(Status.VALID);
					}
				}
				
				// It is possible that we will need it for valid and non valid, for reporting purposes of failed instructions
				instruction.setAmountOfTrade(Utils.calculateAmountOfTrade(instruction.getPricePerUnit(),
						instruction.getUnits(), instruction.getAgreedFx()));
				
				// In case of valid instruction proceed with updating the maps accordingly
				if (instruction.getStatus() == Status.VALID) {
					
					// Update the dates map for the daily reporting
					if (instructionsPerDate.containsKey(instruction.getFormattedSettlementDate())) {
						instructionsPerDate.get(instruction.getFormattedSettlementDate()).add(instruction);
					} else {
						List<Instruction> instructionsToDate = new ArrayList<>();
						instructionsToDate.add(instruction);
						instructionsPerDate.put(instruction.getFormattedSettlementDate(), instructionsToDate);
					}
					
					// Update the outgoing and incoming ranking maps with only the maximum amount per entity per buy or sell
					if (instruction.getBuyOrSell() == BuyOrSell.BUY) {
						if (outgoingEntitiesMax.containsKey(instruction.getEntity())) {
							if (instruction.getAmountOfTrade() > outgoingEntitiesMax.get(instruction.getEntity())) {
								outgoingEntitiesMax.put(instruction.getEntity(), instruction.getAmountOfTrade());
							}
						} else {
							outgoingEntitiesMax.put(instruction.getEntity(), instruction.getAmountOfTrade());
						}
					} else {
						if (incomingEntitiesMax.containsKey(instruction.getEntity())) {
							if (instruction.getAmountOfTrade() > incomingEntitiesMax.get(instruction.getEntity())) {
								incomingEntitiesMax.put(instruction.getEntity(), instruction.getAmountOfTrade());
							}
						} else {
							incomingEntitiesMax.put(instruction.getEntity(), instruction.getAmountOfTrade());
						}
					}
				}
			}
		}
		
		// Store the data to the appropriate object
		ValidatedData validatedData = new ValidatedData();
		validatedData.setInstructionsPerDate(instructionsPerDate);
		validatedData.setOutgoingEntitiesMax(outgoingEntitiesMax);
		validatedData.setIncomingEntitiesMax(incomingEntitiesMax);
		
		return validatedData;
		
	}
	
	/**
	 * Checks if settlement date is before the instruction date, which is invalid
	 * 
	 * @param settlementDate the formatted {@link Date} representing the settlement date
	 * @param instructionDate the formatted {@link Date} representing the instruction date
	 * @return true if settlement is before instruction date and false otherwise
	 */
	private static boolean isSettlementBeforeInstruction(Date settlementDate, Date instructionDate) {
		
		return settlementDate.before(instructionDate) ? true : false;
		
	}

}
