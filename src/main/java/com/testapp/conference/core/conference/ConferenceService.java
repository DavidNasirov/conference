package com.testapp.conference.core.conference;

import com.testapp.conference.core.conference.exception.ConferenceNotChangeException;
import com.testapp.conference.core.conference.model.Conference;
import com.testapp.conference.core.conference.model.ConferenceStatus;
import com.testapp.conference.core.conference.port.output.ConferenceRepository;
import com.testapp.conference.core.conference.port.input.ConferenceUseCase;
import com.testapp.conference.core.participant.port.input.ParticipantUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConferenceService implements ConferenceUseCase {

    private final ConferenceRepository conferenceRepository;
    private final ParticipantUseCase participantUseCase;

    @Override
    @Transactional
    public Conference createConference(Conference conference) {
        conference.setConferenceStatus(ConferenceStatus.AVAILABLE);
        return conferenceRepository.save(conference);
    }

    @Override
    public List<Conference> getAllConference() {
        return conferenceRepository.findAll();
    }

    @Override
    public Conference getConferenceById(Long id) {
        return conferenceRepository.getReferenceById(id);
    }

    @Override
    @Transactional
    public void deleteConferenceById(Long id) {
        conferenceRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void cancelConferenceById(Long id) {
        conferenceRepository.cancelConference(ConferenceStatus.CANCELED, id);
    }

    @Override
    public Boolean checkAvailability(Long id) {
        Optional<Conference> conference = conferenceRepository.findById(id);
        if (conference.get().getConferenceStatus() == ConferenceStatus.CANCELED) {
            throw new ConferenceNotChangeException(ConferenceStatus.CANCELED);
        }
        if (participantUseCase.checkMaxCountOfParticipantInConference(id)) {
            throw new ConferenceNotChangeException(ConferenceStatus.FULL);
        }
        return true;
    }
}
