package com.vasileiou.trade.models;

import java.io.Serializable;
import java.util.Date;

import com.vasileiou.trade.enums.BuyOrSell;
import com.vasileiou.trade.enums.Status;

/**
 * The POJO representation of instruction
 * 
 * @author Panagiotis Vasileiou
 *
 */
public class Instruction implements Serializable {

	private static final long serialVersionUID = -796910202953940564L;
	
	// Mandatory Input Fields
	private String entity;
	private String buyOrSellStringFlag;
	private Double agreedFx;
	private String currency;
	private String instructionDate;
	private String settlementDate;
	private Long units;
	private Double pricePerUnit;
	
	// Generated Fields
	private Double amountOfTrade;
	private BuyOrSell buyOrSell;
	private Date formattedInstructionDate;
	private Date formattedSettlementDate;
	private Date initialFormattedSettlementDate;
	private Status status;
	
	public Instruction() {
		super();
	}
	
	public Instruction(String entity, String buyOrSellStringFlag, Double agreedFx, String currency,
			String instructionDate, String settlementDate, Long units, Double pricePerUnit) {
		
		this();
		
		this.entity = entity;
		this.buyOrSellStringFlag = buyOrSellStringFlag;
		this.agreedFx = agreedFx;
		this.currency = currency;
		this.instructionDate = instructionDate;
		this.settlementDate = settlementDate;
		this.units = units;
		this.pricePerUnit = pricePerUnit;
	}
	
	public String getEntity() {
		return entity;
	}
	
	public void setEntity(String entity) {
		this.entity = entity;
	}
	
	public String getBuyOrSellStringFlag() {
		return buyOrSellStringFlag;
	}

	public void setBuyOrSellStringFlag(String buyOrSellStringFlag) {
		this.buyOrSellStringFlag = buyOrSellStringFlag;
	}

	public BuyOrSell getBuyOrSell() {
		return buyOrSell;
	}
	
	public void setBuyOrSell(BuyOrSell buyOrSell) {
		this.buyOrSell = buyOrSell;
	}
	
	public Double getAgreedFx() {
		return agreedFx;
	}
	
	public void setAgreedFx(Double agreedFx) {
		this.agreedFx = agreedFx;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getInstructionDate() {
		return instructionDate;
	}
	
	public void setInstructionDate(String instructionDate) {
		this.instructionDate = instructionDate;
	}
	
	public String getSettlementDate() {
		return settlementDate;
	}
	
	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}
	
	public Long getUnits() {
		return units;
	}
	
	public void setUnits(Long units) {
		this.units = units;
	}
	
	public Double getPricePerUnit() {
		return pricePerUnit;
	}
	
	public void setPricePerUnit(Double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public Double getAmountOfTrade() {
		return amountOfTrade;
	}

	public void setAmountOfTrade(Double amountOfTrade) {
		this.amountOfTrade = amountOfTrade;
	}

	public Date getFormattedInstructionDate() {
		return formattedInstructionDate;
	}

	public void setFormattedInstructionDate(Date formattedInstructionDate) {
		this.formattedInstructionDate = formattedInstructionDate;
	}

	public Date getFormattedSettlementDate() {
		return formattedSettlementDate;
	}

	public void setFormattedSettlementDate(Date formattedSettlementDate) {
		this.formattedSettlementDate = formattedSettlementDate;
	}

	public Date getInitialFormattedSettlementDate() {
		return initialFormattedSettlementDate;
	}

	public void setInitialFormattedSettlementDate(Date initialFormattedSettlementDate) {
		this.initialFormattedSettlementDate = initialFormattedSettlementDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
