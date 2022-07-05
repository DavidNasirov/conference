package com.testapp.conference.exception;

import com.testapp.conference.model.ConferenceStatus;

public class ConferenceNotChangeException extends RuntimeException {

    public ConferenceNotChangeException(ConferenceStatus conferenceStatus) {
        super(conferenceStatus.toString());
    }
}
