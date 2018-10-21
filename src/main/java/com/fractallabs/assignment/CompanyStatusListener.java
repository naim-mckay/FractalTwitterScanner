package com.fractallabs.assignment;

import twitter4j.*;

public class CompanyStatusListener implements StatusListener {
    @Override
    public void onStatus(Status status) {
        if (status.getText().toLowerCase().contains(ScannerConts.COMPANY.toLowerCase())){
            ScannerConts.COMPANY_COUNT++;
        }
    }
    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
    @Override
    public void onScrubGeo(long userId, long upToStatusId) {}
    @Override
    public void onStallWarning(StallWarning warning) {}
    @Override
    public void onException(Exception ex) {}
}
