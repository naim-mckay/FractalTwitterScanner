package com.fractallabs.assignment;


import com.fractallabs.assignment.TwitterScanner.TSValue;
import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.assertEquals;


public class UtilsTest {

    Utils utils;

    @Test
    public void test_calculatePercentChange_withNegativeChange() {
        String expected = "There's been a decrease of 75%";
        TSValue newTSValue = new TSValue(Instant.now(),1);
        TSValue oldTSValue = new TSValue(Instant.now(),4);
        String resultStr = utils.calculatePercentChange (newTSValue,oldTSValue);
        assertEquals(expected.trim(),resultStr.trim());
    }
    @Test
    public void test_calculatePercentChange_withPositiveChange() {
        String expected = "There's been a increase of 100%";
        TSValue newTSValue = new TSValue(Instant.now(),2);
        TSValue oldTSValue = new TSValue(Instant.now(),1);
        String resultStr = utils.calculatePercentChange (newTSValue,oldTSValue);
        assertEquals(expected.trim(),resultStr.trim());
    }
    @Test
    public void test_calculatePercentChange_withNoChange() {
        String expected = "There's been no change since the last check";
        TSValue newTSValue = new TSValue(Instant.now(),4);
        TSValue oldTSValue = new TSValue(Instant.now(),4);
        String resultStr = utils.calculatePercentChange (newTSValue,oldTSValue);
        assertEquals(expected.trim(),resultStr.trim());
    }

    @Test
    public void test_calculatePercentChange_withLeadingZero() {
        String expected = "There's been a decrease of 400%";
        TSValue newTSValue = new TSValue(Instant.now(),0);
        TSValue oldTSValue = new TSValue(Instant.now(),4);
        String resultStr = utils.calculatePercentChange (newTSValue,oldTSValue);
        assertEquals(expected.trim(),resultStr.trim());
    }

    @Test
    public void test_calculatePercentChange_withTrailingZero() {
        String expected = "There's been a increase of 300%";
        TSValue newTSValue = new TSValue(Instant.now(),3);
        TSValue oldTSValue = new TSValue(Instant.now(),0);
        String resultStr = utils.calculatePercentChange (newTSValue,oldTSValue);
        assertEquals(expected.trim(),resultStr.trim());
    }
}
