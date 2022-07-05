package com.testapp.conference.repository;

import com.testapp.conference.model.Conference;
import com.testapp.conference.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

//    @Modifying
//    @Query("SELECT COUNT (participants) FROM participants where conference.conference_id = :conferenceId")
    Integer countAllByConference(Optional<Conference> conferenceId);

    @Modifying
    @Query("UPDATE participants SET conference.conference_id = :conference_id WHERE participant_id = :participantId")
//    @Query("UPDATE participants p SET p.conference = :#{#conference.conference_id} WHERE p.participant_id = :participantId")
    void updateConferenceId(Long conference_id, Long participantId);

}
