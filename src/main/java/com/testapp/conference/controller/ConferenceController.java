package com.testapp.conference.controller;

import com.testapp.conference.model.Conference;
import com.testapp.conference.service.ConferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/conference")
public class ConferenceController {

    private final ConferenceService conferenceService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','USER_ROLE')")
    public ResponseEntity<List<Conference>> getAllConferences() {
        return ResponseEntity.status(HttpStatus.OK).body(conferenceService.getAllConference());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE')")
    public ResponseEntity<Conference> createConference(@RequestBody @Valid Conference conference) {
        return ResponseEntity.status(HttpStatus.OK).body(conferenceService.createConference(conference));
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE')")
    public ResponseEntity<String> deleteConference(@RequestParam Long id) {
        conferenceService.deleteConferenceById(id);
        return ResponseEntity.status(HttpStatus.OK).body("SUCCESS");
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE')")
    public ResponseEntity<String> cancelConference(@RequestParam Long id) {
        conferenceService.cancelConferenceById(id);
        return ResponseEntity.status(HttpStatus.OK).body("SUCCESS");
    }

    @GetMapping("/{idConference}")
    @PreAuthorize("hasAnyAuthority('USER_ROLE','ADMIN_ROLE')")
    public ResponseEntity<Boolean> checkAvailability(@PathVariable Long idConference) {
        return ResponseEntity.status(HttpStatus.OK).body(conferenceService.checkAvailability(idConference));
    }

}
