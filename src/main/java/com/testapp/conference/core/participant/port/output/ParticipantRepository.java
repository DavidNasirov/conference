package com.testapp.conference.core.participant.port.output;

import com.testapp.conference.core.conference.model.Conference;
import com.testapp.conference.core.participant.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    Integer countAllByConference(Optional<Conference> conferenceId);

    @Modifying
    @Query("UPDATE participants SET conference.conference_id = :conference_id WHERE participant_id = :participantId")
    void updateConferenceId(Long conference_id, Long participantId);

}
