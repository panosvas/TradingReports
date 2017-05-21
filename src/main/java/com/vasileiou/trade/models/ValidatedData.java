package com.vasileiou.trade.models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;

/**
 * The POJO representation of valid, parsed data
 * 
 * @author Panagiotis Vasileiou
 *
 */
public class ValidatedData implements Serializable {

	private static final long serialVersionUID = 1677658592337878254L;
	
	// Holds the list of instructions per date
	SortedMap<Date, List<Instruction> > instructionsPerDate;
	
	// Holds the entity - max amount value pair for incoming
	HashMap<String, Double> incomingEntitiesMax;
	
	// Holds the entity - max amount value pair for outgoing
	HashMap<String, Double> outgoingEntitiesMax;
	
	public ValidatedData() {
		super();
	}

	public SortedMap<Date, List<Instruction>> getInstructionsPerDate() {
		return instructionsPerDate;
	}

	public void setInstructionsPerDate(SortedMap<Date, List<Instruction>> instructionsPerDate) {
		this.instructionsPerDate = instructionsPerDate;
	}

	public HashMap<String, Double> getIncomingEntitiesMax() {
		return incomingEntitiesMax;
	}

	public void setIncomingEntitiesMax(HashMap<String, Double> incomingEntitiesMax) {
		this.incomingEntitiesMax = incomingEntitiesMax;
	}

	public HashMap<String, Double> getOutgoingEntitiesMax() {
		return outgoingEntitiesMax;
	}

	public void setOutgoingEntitiesMax(HashMap<String, Double> outgoingEntitiesMax) {
		this.outgoingEntitiesMax = outgoingEntitiesMax;
	}

}
