package com.fractallabs.assignment;


import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.time.Clock;
import java.time.Instant;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import static com.fractallabs.assignment.ScannerConts.ONE_HR_TIMER;

public class TwitterScanner {
    public TwitterScanner(){}

    public static class TSValue {
        private final Instant timestamp;
        private final double val;

        public TSValue(Instant timestamp, double val) {
            this.timestamp = timestamp;
            this.val = val;
        }

        public Instant getTimestamp() {
            return timestamp;
        }

        public double getVal() {
            return val;
        }
    }

    public TwitterScanner(String companyName){
        ScannerConts.COMPANY = companyName;
    }

    public static Clock          defaultClock = Clock.systemDefaultZone();
    public static Stack<TSValue> tsValueStack = new Stack<>();

    private ConfigurationBuilder getConfigurationBuilder(){
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setDebugEnabled(false)
		  .setOAuthConsumerKey(ScannerConts.oAuthKey)
		  .setOAuthConsumerSecret(ScannerConts.oAuthSecret)
		  .setOAuthAccessToken(ScannerConts.oAuthToken)
		  .setOAuthAccessTokenSecret(ScannerConts.oAuthTokenSecret);
		return configurationBuilder;
    }


    public void run() {
        TwitterStreamFactory twitterStreamFactory = new TwitterStreamFactory(getConfigurationBuilder().build());
        TwitterStream twitterStream = twitterStreamFactory.getInstance();
        twitterStream.addListener(new CompanyStatusListener());
        twitterStream.sample();
    }

    private void storeValue(TSValue ts) {
        Utils.showOutPut(ScannerConts.COMPANY + " currently have " + ScannerConts.COMPANY_COUNT + " tweets for the  hour");
        if(!tsValueStack.empty()){
            String changeStr = Utils.calculatePercentChange(ts,tsValueStack.peek());
            Utils.showOutPut(changeStr);
        }
        tsValueStack.push(ts);
        ScannerConts.COMPANY_COUNT = 0;
    }


    public static void main(String... args) {
        TwitterScanner twitterScanner = new TwitterScanner("FaceBook");
        Utils.showOutPut("start ");
        twitterScanner.run();
        twitterScanner.startTweetTimer();
    }

    public void  startTweetTimer() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run () {
                TSValue ts = new TSValue(Instant.now(defaultClock), ScannerConts.COMPANY_COUNT);
                storeValue(ts);
            }
        }, 0,ONE_HR_TIMER);
    }
}