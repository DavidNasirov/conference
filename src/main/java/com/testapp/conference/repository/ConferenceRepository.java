package com.testapp.conference.repository;

import com.testapp.conference.model.Conference;
import com.testapp.conference.model.ConferenceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {

    @Modifying
    @Query("update conferences set conferenceStatus = :conferenceStatus where conference_id = :conferenceId")
    void cancelConference(ConferenceStatus conferenceStatus, Long conferenceId);
}
