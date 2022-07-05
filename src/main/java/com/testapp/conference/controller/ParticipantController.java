package com.testapp.conference.controller;

import com.testapp.conference.model.Participant;
import com.testapp.conference.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/participant")
public class ParticipantController {

    private final ParticipantService participantService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER_ROLE','ADMIN_ROLE')")
    public ResponseEntity<List<Participant>> getAllParticipants() {
        return ResponseEntity.status(HttpStatus.OK).body(participantService.getAllParticipant());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER_ROLE','ADMIN_ROLE')")
    public ResponseEntity<Optional<Participant>> getParticipant(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(participantService.getParticipantById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER_ROLE','ADMIN_ROLE')")
    public ResponseEntity<Participant> createParticipant(@RequestBody @Valid Participant participant) {
        return ResponseEntity.status(HttpStatus.OK).body(participantService.createParticipant(participant));
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('USER_ROLE','ADMIN_ROLE')")
    public ResponseEntity<String> deleteParticipant(@RequestParam Long participantId) {
        participantService.deleteParticipantById(participantId);
        return ResponseEntity.status(HttpStatus.OK).body("SUCCESS");
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE')")
    public ResponseEntity<String> addOrRemoveParticipantToConference(@RequestParam Long participantId, @RequestParam(required = false) Long conferenceId) {
        participantService.addOrRemoveParticipantToConference(conferenceId, participantId);
        return ResponseEntity.status(HttpStatus.OK).body("SUCCESS");
    }

}
