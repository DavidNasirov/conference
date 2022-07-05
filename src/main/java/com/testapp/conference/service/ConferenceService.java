package com.testapp.conference.service;

import com.testapp.conference.model.Conference;

import java.util.List;

public interface ConferenceService {

    Conference createConference(Conference conference);

    List<Conference> getAllConference();

    Conference getConferenceById(Long id);

    void deleteConferenceById(Long id);

    void cancelConferenceById(Long id);

    Boolean checkAvailability(Long idConference);
}
