package com.vasileiou.trade.common;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import com.vasileiou.trade.common.Utils;

/**
 * Various test cases for the utilities
 * 
 * @author Panagiotis Vasileiou
 *
 */
public class UtilsTest
{

	/**
	 * When giving Friday with a common currency then it should be a working day
	 */
    @Test
    public void givenCommonCurrencyWhenFridayThenIsWorkingDay()
    {
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.DAY_OF_MONTH, 19);
    	calendar.set(Calendar.MONTH, Calendar.MAY);
    	calendar.set(Calendar.YEAR, 2017);
    	
        Assert.assertTrue(Utils.isWorkingDay("EUR", calendar.getTime()));
    }
    
    /**
	 * When giving Friday with a non common currency then it should not be a working day
	 */
    @Test
    public void givenExceptionCurrencyWhenFridayThenIsNotWorkingDay()
    {
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.DAY_OF_MONTH, 19);
    	calendar.set(Calendar.MONTH, Calendar.MAY);
    	calendar.set(Calendar.YEAR, 2017);
    	
        Assert.assertFalse(Utils.isWorkingDay("AED", calendar.getTime()));
    }
    
    /**
	 * When giving Sunday with a common currency then it should not be a working day
	 */
    @Test
    public void givenCommonCurrencyWhenSundayThenIsNotWorkingDay()
    {
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.DAY_OF_MONTH, 21);
    	calendar.set(Calendar.MONTH, Calendar.MAY);
    	calendar.set(Calendar.YEAR, 2017);
    	
        Assert.assertFalse(Utils.isWorkingDay("EUR", calendar.getTime()));
    }
    
    /**
	 * When giving Sunday with a non common currency then it should be a working day
	 */
    @Test
    public void givenExceptionCurrencyWhenSundayThenIsWorkingDay()
    {
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.DAY_OF_MONTH, 21);
    	calendar.set(Calendar.MONTH, Calendar.MAY);
    	calendar.set(Calendar.YEAR, 2017);
    	
        Assert.assertTrue(Utils.isWorkingDay("SAR", calendar.getTime()));
    }
    
    /**
     * If null is given to date conversion then it should return null
     */
    @Test
    public void givenNullWhenConvertDateThenNull()
    {
    	Assert.assertNull(Utils.convertStringToDate(null));
    }
    
    /**
     * If invalid date format is given to date conversion then it should return null
     */
    @Test
    public void givenInvalidWhenConvertDateThenNull()
    {
    	Assert.assertNull(Utils.convertStringToDate("22 June , 2011"));
    }
    
    /**
     * If valid date is given to date conversion it should convert it successfully
     */
    @Test
    public void givenValidWhenConvertDateThenShouldConvertIt()
    {
    	String date = "Fri May 19 00:00:00 EEST 2017";
    	
    	Assert.assertEquals(Utils.convertStringToDate("19 May 2017").toString(), date);
    }
    
    /**
     * If null is given during date correction it should return null
     */
    @Test
    public void givenNullDateWhenCorrectingDateThenShouldBeNull()
    {
    	Assert.assertNull(Utils.dateCorrection("", null));
    }
    
    /**
     * Given a common currency when it is Sunday, the correction should return Monday
     */
    @Test
    public void givenCommonCurrencyWhenSundayThenMonday()
    {
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.DAY_OF_MONTH, 21);
    	calendar.set(Calendar.MONTH, Calendar.MAY);
    	calendar.set(Calendar.YEAR, 2017);
    	calendar.set(Calendar.HOUR_OF_DAY, 0);
    	calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
    	
    	Assert.assertEquals(Utils.dateCorrection("EUR", calendar.getTime()).toString(), "Mon May 22 00:00:00 EEST 2017");
    }
    
    /**
     * Given a non common currency when it is Sunday, then it should return Sunday
     */
    @Test
    public void givenExceptionCurrencyWhenSundayThenSunday()
    {
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.DAY_OF_MONTH, 21);
    	calendar.set(Calendar.MONTH, Calendar.MAY);
    	calendar.set(Calendar.YEAR, 2017);
    	calendar.set(Calendar.HOUR_OF_DAY, 0);
    	calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
    	
    	Assert.assertEquals(Utils.dateCorrection("SAR", calendar.getTime()).toString(), "Sun May 21 00:00:00 EEST 2017");
    }
    
    /**
     * Given non common currency when it is Friday, then it should return Sunday
     */
    @Test
    public void givenExceptionCurrencyWhenFridayThenSunday()
    {
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.DAY_OF_MONTH, 19);
    	calendar.set(Calendar.MONTH, Calendar.MAY);
    	calendar.set(Calendar.YEAR, 2017);
    	calendar.set(Calendar.HOUR_OF_DAY, 0);
    	calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
    	
    	Assert.assertEquals(Utils.dateCorrection("SAR", calendar.getTime()).toString(), "Sun May 21 00:00:00 EEST 2017");
    }
    
    /**
     * If at least one input is zero then it should return zero
     */
    @Test
    public void givenOneZeroWhenCalculatingAmountThenZero()
    {
    	Assert.assertEquals(Utils.calculateAmountOfTrade(new Double(1), 0L, new Double(1)), new Double(0));
    	Assert.assertEquals(Utils.calculateAmountOfTrade(new Double(0), 1L, new Double(1)), new Double(0));
    	Assert.assertEquals(Utils.calculateAmountOfTrade(new Double(1), 1L, new Double(0)), new Double(0));
    }
    
    /**
     * If valid input is given, then it should return the multiplication of them
     */
    @Test
    public void givenNonZeroParamsWhenCalculatingAmountThenGreaterThanZero()
    {
    	Assert.assertEquals(Utils.calculateAmountOfTrade(new Double(150.5), 450L, new Double(0.22)), new Double(14899.5));
    }
}
