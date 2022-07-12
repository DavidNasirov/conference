package com.testapp.conference.core.participant.port.input;

import com.testapp.conference.core.participant.model.Participant;

import java.util.List;
import java.util.Optional;

public interface ParticipantUseCase {

    Participant createParticipant(Participant conference);

    List<Participant> getAllParticipant();

    Optional<Participant> getParticipantById(Long id);

    void addOrRemoveParticipantToConference(Long conferenceId, Long participantId);

    void deleteParticipantById(Long id);

    Boolean checkMaxCountOfParticipantInConference(Long conferenceId);

}
