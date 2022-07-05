package com.testapp.conference.service.impl;

import com.testapp.conference.exception.ConferenceNotChangeException;
import com.testapp.conference.model.Conference;
import com.testapp.conference.model.ConferenceStatus;
import com.testapp.conference.repository.ConferenceRepository;
import com.testapp.conference.service.ConferenceService;
import com.testapp.conference.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConferenceServiceImpl implements ConferenceService {

    private final ConferenceRepository conferenceRepository;
    private final ParticipantService participantService;

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
        if (participantService.checkMaxCountOfParticipantInConference(id)) {
            throw new ConferenceNotChangeException(ConferenceStatus.FULL);
        }
        return true;
    }
}
