package com.testapp.conference.core.conference.port.input;

import com.testapp.conference.core.conference.model.Conference;

import java.util.List;

public interface ConferenceUseCase {

    Conference createConference(Conference conference);

    List<Conference> getAllConference();

    Conference getConferenceById(Long id);

    void deleteConferenceById(Long id);

    void cancelConferenceById(Long id);

    Boolean checkAvailability(Long idConference);
}
