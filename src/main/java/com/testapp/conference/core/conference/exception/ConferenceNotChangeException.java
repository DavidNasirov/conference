package com.testapp.conference.core.conference.exception;

import com.testapp.conference.core.conference.model.ConferenceStatus;

public class ConferenceNotChangeException extends RuntimeException {

    public ConferenceNotChangeException(ConferenceStatus conferenceStatus) {
        super(conferenceStatus.toString());
    }
}
