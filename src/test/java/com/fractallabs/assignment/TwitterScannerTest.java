package com.fractallabs.assignment;


import org.junit.Before;
import org.junit.Test;
import twitter4j.conf.ConfigurationBuilder;
import java.lang.reflect.Method;
import java.time.Instant;
import com.fractallabs.assignment.TwitterScanner.TSValue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TwitterScannerTest {

    TwitterScanner twitterScanner;
    Class cls;
    Object obj;


    @Before
    public void setUp() throws Exception{
        cls = Class.forName("com.fractallabs.assignment.TwitterScanner");
        obj = cls.getDeclaredConstructor().newInstance();
        twitterScanner = new TwitterScanner("Facebook");
    }

    private static Method getPrivateStoreValueMethod() throws NoSuchMethodException {
        Class[] param = new Class[1];
        param[0] = TSValue.class;
        Method storeValueMethod = TwitterScanner.class.getDeclaredMethod("storeValue", param);
        storeValueMethod.setAccessible(true);
        return storeValueMethod;
    }

    private static Method getPrivateConfigurationBuilderMethod() throws NoSuchMethodException {
        Class noparams[] = {};
        Method getConfigurationBuilderMethod = TwitterScanner.class.getDeclaredMethod("getConfigurationBuilder",noparams);
        getConfigurationBuilderMethod.setAccessible(true);
        return getConfigurationBuilderMethod;
    }


    @Test
    public void test_getConfigurationBuilder()throws Exception {
        ConfigurationBuilder configurationBuilder = (ConfigurationBuilder)
                getPrivateConfigurationBuilderMethod().invoke(obj,null);
        assertTrue(!configurationBuilder.build().getOAuthAccessToken().isEmpty());
    }

    @Test
    public void test_StoreValue() throws Exception {
        TSValue value = new TSValue(Instant.now(), 5);
        getPrivateStoreValueMethod().invoke(obj,value);
        assertEquals(1,TwitterScanner.tsValueStack.size());
    }
}