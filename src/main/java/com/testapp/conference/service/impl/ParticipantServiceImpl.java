package com.testapp.conference.service.impl;

import com.testapp.conference.model.Participant;
import com.testapp.conference.repository.ConferenceRepository;
import com.testapp.conference.repository.ParticipantRepository;
import com.testapp.conference.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

    private final static Integer MAX_NUMBER_PARTICIPANT = 10;

    private final ParticipantRepository participantRepository;
    private final ConferenceRepository conferenceRepository;

    @Override
    @Transactional
    public Participant createParticipant(Participant participant) {
        return participantRepository.save(participant);
    }

    @Override
    public List<Participant> getAllParticipant() {
        return participantRepository.findAll();
    }

    @Override
    public Optional<Participant> getParticipantById(Long id) {
        return participantRepository.findById(id);
    }

    @Override
    @Transactional
    public void addOrRemoveParticipantToConference(Long conferenceId, Long participantId) {
        if (conferenceId == null) {
            participantRepository.updateConferenceId(null, participantId);
        } else {
            participantRepository.updateConferenceId(conferenceId, participantId);
        }
    }

    @Override
    @Transactional
    public void deleteParticipantById(Long id) {
        participantRepository.deleteById(id);
    }

    @Override
    public Boolean checkMaxCountOfParticipantInConference(Long conferenceId) {
        return participantRepository.countAllByConference(conferenceRepository.findById(conferenceId)) > MAX_NUMBER_PARTICIPANT;
    }
}
