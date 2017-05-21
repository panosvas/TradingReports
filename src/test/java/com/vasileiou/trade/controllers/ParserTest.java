package com.vasileiou.trade.controllers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.vasileiou.trade.controllers.Parser;
import com.vasileiou.trade.enums.Status;
import com.vasileiou.trade.models.Instruction;
import com.vasileiou.trade.models.ValidatedData;

/**
 * Various test cases for the parser
 * 
 * @author Panagiotis Vasileiou
 *
 */
public class ParserTest {
	
	/**
	 * Given null or empty instructions when parsing then it should return null
	 */
	@Test
	public void givenNullorEmptyWhenParsingThenIsNull()
    {
        Assert.assertNull(Parser.parseInstructions(null));
        Assert.assertNull(Parser.parseInstructions(new ArrayList<Instruction>()));
    }
	
	/**
	 * Given non empty instructions of size one with null entity when parsing 
	 * then it should return zero and missing property status
	 */
	@Test
	public void givenNullEntityWhenParsingThenZeroSize()
    {
		List<Instruction> instructions = new ArrayList<>();
    	Instruction instruction1 = new Instruction(null, "B", new Double(0.22), "EUR", "18 May 2017", "20 May 2017", 100L, new Double(20));
    	instructions.add(instruction1);
    	
    	ValidatedData validatedData = Parser.parseInstructions(instructions);
    	Assert.assertEquals(validatedData.getInstructionsPerDate().size(), 0);
    	Assert.assertEquals(validatedData.getIncomingEntitiesMax().size(), 0);
    	Assert.assertEquals(validatedData.getOutgoingEntitiesMax().size(), 0);
    	Assert.assertEquals(instruction1.getStatus(), Status.MISSING_PROPERTY);
    }
	
	/**
	 * Given non empty instructions of size one with null buy or sell when parsing 
	 * then it should return zero and missing property status
	 */
	@Test
	public void givenOneInstructionWithNullBuyOrSellWhenParsingThenZeroSize()
    {
		List<Instruction> instructions = new ArrayList<>();
    	Instruction instruction1 = new Instruction("entity1", null, new Double(0.22), "EUR", "18 May 2017", "20 May 2017", 100L, new Double(20));
    	instructions.add(instruction1);
    	
    	ValidatedData validatedData = Parser.parseInstructions(instructions);
    	Assert.assertEquals(validatedData.getInstructionsPerDate().size(), 0);
    	Assert.assertEquals(validatedData.getIncomingEntitiesMax().size(), 0);
    	Assert.assertEquals(validatedData.getOutgoingEntitiesMax().size(), 0);
    	Assert.assertEquals(instruction1.getStatus(), Status.MISSING_PROPERTY);
    }

	/**
	 * Given non empty instructions of size one with invalid buy or sell when parsing 
	 * then it should return zero and missing property status
	 */
	@Test
	public void givenOneInstructionWithInvalidBuyOrSellWhenParsingThenZeroSize()
    {
		List<Instruction> instructions = new ArrayList<>();
    	Instruction instruction1 = new Instruction("entity1", "P", new Double(0.22), "EUR", "18 May 2017", "20 May 2017", 100L, new Double(20));
    	instructions.add(instruction1);
    	
    	ValidatedData validatedData = Parser.parseInstructions(instructions);
    	Assert.assertEquals(validatedData.getInstructionsPerDate().size(), 0);
    	Assert.assertEquals(validatedData.getIncomingEntitiesMax().size(), 0);
    	Assert.assertEquals(validatedData.getOutgoingEntitiesMax().size(), 0);
    	Assert.assertEquals(instruction1.getStatus(), Status.MISSING_PROPERTY);
    }
	
	/**
	 * Given non empty instructions of size one with null agreed foreign exchange when parsing 
	 * then it should return zero and missing property status
	 */
	@Test
	public void givenOneInstructionWithNullAgreedFxWhenParsingThenZeroSize()
    {
		List<Instruction> instructions = new ArrayList<>();
    	Instruction instruction1 = new Instruction("entity1", "B", null, "EUR", "18 May 2017", "20 May 2017", 100L, new Double(20));
    	instructions.add(instruction1);
    	
    	ValidatedData validatedData = Parser.parseInstructions(instructions);
    	Assert.assertEquals(validatedData.getInstructionsPerDate().size(), 0);
    	Assert.assertEquals(validatedData.getIncomingEntitiesMax().size(), 0);
    	Assert.assertEquals(validatedData.getOutgoingEntitiesMax().size(), 0);
    	Assert.assertEquals(instruction1.getStatus(), Status.MISSING_PROPERTY);
    }
	
	/**
	 * Given non empty instructions of size one with null currency when parsing 
	 * then it should return zero and missing property status
	 */
	@Test
	public void givenOneInstructionWithNullCurrencyWhenParsingThenZeroSize()
    {
		List<Instruction> instructions = new ArrayList<>();
    	Instruction instruction1 = new Instruction("entity1", "B", new Double(0.22), null, "18 May 2017", "20 May 2017", 100L, new Double(20));
    	instructions.add(instruction1);
    	
    	ValidatedData validatedData = Parser.parseInstructions(instructions);
    	Assert.assertEquals(validatedData.getInstructionsPerDate().size(), 0);
    	Assert.assertEquals(validatedData.getIncomingEntitiesMax().size(), 0);
    	Assert.assertEquals(validatedData.getOutgoingEntitiesMax().size(), 0);
    	Assert.assertEquals(instruction1.getStatus(), Status.MISSING_PROPERTY);
    }
	
	/**
	 * Given non empty instructions of size one with null instruction date when parsing 
	 * then it should return zero and missing property status
	 */
	@Test
	public void givenOneInstructionWithNullInstrDateWhenParsingThenZeroSize()
    {
		List<Instruction> instructions = new ArrayList<>();
    	Instruction instruction1 = new Instruction("entity1", "B", new Double(0.22), "EUR", null, "20 May 2017", 100L, new Double(20));
    	instructions.add(instruction1);
    	
    	ValidatedData validatedData = Parser.parseInstructions(instructions);
    	Assert.assertEquals(validatedData.getInstructionsPerDate().size(), 0);
    	Assert.assertEquals(validatedData.getIncomingEntitiesMax().size(), 0);
    	Assert.assertEquals(validatedData.getOutgoingEntitiesMax().size(), 0);
    	Assert.assertEquals(instruction1.getStatus(), Status.MISSING_PROPERTY);
    }
	
	/**
	 * Given non empty instructions of size one with null settlement date when parsing 
	 * then it should return zero and missing property status
	 */
	@Test
	public void givenOneInstructionWithNullSettlDateWhenParsingThenZeroSize()
    {
		List<Instruction> instructions = new ArrayList<>();
    	Instruction instruction1 = new Instruction("entity1", "B", new Double(0.22), "EUR", "18 May 2017", null, 100L, new Double(20));
    	instructions.add(instruction1);
    	
    	ValidatedData validatedData = Parser.parseInstructions(instructions);
    	Assert.assertEquals(validatedData.getInstructionsPerDate().size(), 0);
    	Assert.assertEquals(validatedData.getIncomingEntitiesMax().size(), 0);
    	Assert.assertEquals(validatedData.getOutgoingEntitiesMax().size(), 0);
    	Assert.assertEquals(instruction1.getStatus(), Status.MISSING_PROPERTY);
    }
	
	/**
	 * Given non empty instructions of size one with null units when parsing 
	 * then it should return zero and missing property status
	 */
	@Test
	public void givenOneInstructionWithNullUnitsWhenParsingThenZeroSize()
    {
		List<Instruction> instructions = new ArrayList<>();
    	Instruction instruction1 = new Instruction("entity1", "B", new Double(0.22), "EUR", "18 May 2017", "20 May 2017", null, new Double(20));
    	instructions.add(instruction1);
    	
    	ValidatedData validatedData = Parser.parseInstructions(instructions);
    	Assert.assertEquals(validatedData.getInstructionsPerDate().size(), 0);
    	Assert.assertEquals(validatedData.getIncomingEntitiesMax().size(), 0);
    	Assert.assertEquals(validatedData.getOutgoingEntitiesMax().size(), 0);
    	Assert.assertEquals(instruction1.getStatus(), Status.MISSING_PROPERTY);
    }
	
	/**
	 * Given non empty instructions of size one with null price per unit when parsing 
	 * then it should return zero and missing property status
	 */
	@Test
	public void givenOneInstructionWithNullPricePerUnitWhenParsingThenZeroSize()
    {
		List<Instruction> instructions = new ArrayList<>();
    	Instruction instruction1 = new Instruction("entity1", "B", new Double(0.22), "EUR", "18 May 2017", "20 May 2017", 100L, null);
    	instructions.add(instruction1);
    	
    	ValidatedData validatedData = Parser.parseInstructions(instructions);
    	Assert.assertEquals(validatedData.getInstructionsPerDate().size(), 0);
    	Assert.assertEquals(validatedData.getIncomingEntitiesMax().size(), 0);
    	Assert.assertEquals(validatedData.getOutgoingEntitiesMax().size(), 0);
    	Assert.assertEquals(instruction1.getStatus(), Status.MISSING_PROPERTY);
    }
	
	/**
	 * Given non empty instructions of size one with invalid instruction date when parsing 
	 * then it should return zero and wrong date format status
	 */
	@Test
	public void givenOneInstructionWithInvalidInstrDateWhenParsingThenZeroSize()
    {
		List<Instruction> instructions = new ArrayList<>();
    	Instruction instruction1 = new Instruction("entity1", "B", new Double(0.22), "EUR", "18, May, 2017", "20 May 2017", 100L, new Double(20));
    	instructions.add(instruction1);
    	
    	ValidatedData validatedData = Parser.parseInstructions(instructions);
    	Assert.assertEquals(validatedData.getInstructionsPerDate().size(), 0);
    	Assert.assertEquals(validatedData.getIncomingEntitiesMax().size(), 0);
    	Assert.assertEquals(validatedData.getOutgoingEntitiesMax().size(), 0);
    	Assert.assertEquals(instruction1.getStatus(), Status.WRONG_DATE_FORMAT);
    }
	
	/**
	 * Given non empty instructions of size one with invalid settlement date when parsing 
	 * then it should return zero and wrong date format status
	 */
	@Test
	public void givenOneInstructionWithInvalidSettlDateWhenParsingThenZeroSize()
    {
		List<Instruction> instructions = new ArrayList<>();
    	Instruction instruction1 = new Instruction("entity1", "B", new Double(0.22), "EUR", "18 May 2017", "20/5/2017", 100L, new Double(20));
    	instructions.add(instruction1);
    	
    	ValidatedData validatedData = Parser.parseInstructions(instructions);
    	Assert.assertEquals(validatedData.getInstructionsPerDate().size(), 0);
    	Assert.assertEquals(validatedData.getIncomingEntitiesMax().size(), 0);
    	Assert.assertEquals(validatedData.getOutgoingEntitiesMax().size(), 0);
    	Assert.assertEquals(instruction1.getStatus(), Status.WRONG_DATE_FORMAT);
    }
	
	/**
	 * Given non empty instructions of size one with settlement date before instruction date when parsing 
	 * then it should return zero and invalid settlement date status
	 */
	@Test
	public void givenOneInstructionWithSettlDateBeforeInstrWhenParsingThenZeroSize()
    {
		List<Instruction> instructions = new ArrayList<>();
    	Instruction instruction1 = new Instruction("entity1", "B", new Double(0.22), "EUR", "18 May 2017", "17 May 2017", 100L, new Double(20));
    	instructions.add(instruction1);
    	
    	ValidatedData validatedData = Parser.parseInstructions(instructions);
    	Assert.assertEquals(validatedData.getInstructionsPerDate().size(), 0);
    	Assert.assertEquals(validatedData.getIncomingEntitiesMax().size(), 0);
    	Assert.assertEquals(validatedData.getOutgoingEntitiesMax().size(), 0);
    	Assert.assertEquals(instruction1.getStatus(), Status.INVALID_SETTLEMENT_DATE);
    }
	
	/**
	 * Given valid buy instructions of size one when parsing then it should return size one existing dates
	 * and outgoing ranking as long as valid status 
	 */
	@Test
	public void givenOneOutgoingInstructionWithValidDataWhenParsingThenReturnValid()
    {
		List<Instruction> instructions = new ArrayList<>();
    	Instruction instruction1 = new Instruction("entity1", "B", new Double(0.22), "EUR", "18 May 2017", "20 May 2017", 100L, new Double(20));
    	instructions.add(instruction1);
    	
    	ValidatedData validatedData = Parser.parseInstructions(instructions);
    	Assert.assertEquals(validatedData.getInstructionsPerDate().size(), 1);
    	Assert.assertEquals(validatedData.getIncomingEntitiesMax().size(), 0);
    	Assert.assertEquals(validatedData.getOutgoingEntitiesMax().size(), 1);
    	Assert.assertEquals(instruction1.getStatus(), Status.VALID);
    }
	
	/**
	 * Given valid sell instructions of size one when parsing then it should return size one existing dates
	 * and incoming ranking as long as valid status 
	 */
	@Test
	public void givenOneIncomingInstructionWithValidDataWhenParsingThenReturnValid()
    {
		List<Instruction> instructions = new ArrayList<>();
    	Instruction instruction1 = new Instruction("entity1", "S", new Double(0.22), "EUR", "18 May 2017", "20 May 2017", 100L, new Double(20));
    	instructions.add(instruction1);
    	
    	ValidatedData validatedData = Parser.parseInstructions(instructions);
    	Assert.assertEquals(validatedData.getInstructionsPerDate().size(), 1);
    	Assert.assertEquals(validatedData.getIncomingEntitiesMax().size(), 1);
    	Assert.assertEquals(validatedData.getOutgoingEntitiesMax().size(), 0);
    	Assert.assertEquals(instruction1.getStatus(), Status.VALID);
    }
}
