package com.testapp.conference.service;

import com.testapp.conference.model.Participant;

import java.util.List;
import java.util.Optional;

public interface ParticipantService {

    Participant createParticipant(Participant conference);

    List<Participant> getAllParticipant();

    Optional<Participant> getParticipantById(Long id);

    void addOrRemoveParticipantToConference(Long conferenceId, Long participantId);

    void deleteParticipantById(Long id);

    Boolean checkMaxCountOfParticipantInConference(Long conferenceId);

}
