package com.testapp.conference.controller;

import com.testapp.conference.model.Participant;
import com.testapp.conference.service.ParticipantService;
import io.swagger.annotations.*;
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
@Api(tags = "participants")
@RequestMapping("/api/v1/participant")
public class ParticipantController {

    private final ParticipantService participantService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER_ROLE','ADMIN_ROLE')")
    @ApiOperation(value = "Get all participants")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
    })
    public ResponseEntity<List<Participant>> getAllParticipants() {
        return ResponseEntity.status(HttpStatus.OK).body(participantService.getAllParticipant());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER_ROLE','ADMIN_ROLE')")
    @ApiOperation(value = "Get participant by id")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
    })
    public ResponseEntity<Optional<Participant>> getParticipant(@ApiParam("id") @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(participantService.getParticipantById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER_ROLE','ADMIN_ROLE')")
    @ApiOperation(value = "Create participant")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Participant is already in use")
    })
    public ResponseEntity<Participant> createParticipant(@ApiParam("Participant") @RequestBody @Valid Participant participant) {
        return ResponseEntity.status(HttpStatus.OK).body(participantService.createParticipant(participant));
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('USER_ROLE','ADMIN_ROLE')")
    @ApiOperation(value = "Create participant")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
    })
    public ResponseEntity<String> deleteParticipant(@ApiParam("participantId") @RequestParam Long participantId) {
        participantService.deleteParticipantById(participantId);
        return ResponseEntity.status(HttpStatus.OK).body("SUCCESS");
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE')")
    @ApiOperation(value = "Add or remove participant to conference")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
    })
    public ResponseEntity<String> addOrRemoveParticipantToConference(@ApiParam("participantId") @RequestParam Long participantId,
                                                                     @ApiParam("conferenceId")@RequestParam(required = false) Long conferenceId) {
        participantService.addOrRemoveParticipantToConference(conferenceId, participantId);
        return ResponseEntity.status(HttpStatus.OK).body("SUCCESS");
    }

}
