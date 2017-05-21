package com.vasileiou.trade.interfaces;

import java.util.Calendar;
import java.util.List;

import com.vasileiou.trade.models.Instruction;

/**
 * The interface for instructions fetcher
 * 
 * @author Panagiotis Vasileiou
 *
 */
public interface Fetcher {
	
	/**
	 * Retrieves the instructions
	 * 
	 * @return a {@link List} of {@link Instruction} objects
	 */
	List<Instruction> getInstructionsFromClients();

	/**
	 * Generate random instructions for demonstration purposes
	 * 
	 * @param numberOfInstructions for the desired total number of generated instructions
	 * @param numberOfEntities the total number of different entities
	 * @param dateStart the {@link Calendar} representing the starting date
	 * @param dateEnd the {@link Calendar} representing the end date
	 * @return a {@link List} of {@link Instruction} objects
	 */
	List<Instruction> getRandomInstructions(int numberOfInstructions, int numberOfEntities, 
			Calendar dateStart, Calendar dateEnd);
}
