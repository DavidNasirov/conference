package com.testapp.conference.application;

import com.testapp.conference.core.participant.model.Participant;
import com.testapp.conference.core.participant.port.input.ParticipantUseCase;
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

    private final ParticipantUseCase participantUseCase;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER_ROLE','ADMIN_ROLE')")
    @ApiOperation(value = "Get all participants")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
    })
    public ResponseEntity<List<Participant>> getAllParticipants() {
        return ResponseEntity.status(HttpStatus.OK).body(participantUseCase.getAllParticipant());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER_ROLE','ADMIN_ROLE')")
    @ApiOperation(value = "Get participant by id")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
    })
    public ResponseEntity<Optional<Participant>> getParticipant(@ApiParam("id") @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(participantUseCase.getParticipantById(id));
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
        return ResponseEntity.status(HttpStatus.OK).body(participantUseCase.createParticipant(participant));
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('USER_ROLE','ADMIN_ROLE')")
    @ApiOperation(value = "Create participant")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
    })
    public ResponseEntity<String> deleteParticipant(@ApiParam("participantId") @RequestParam Long participantId) {
        participantUseCase.deleteParticipantById(participantId);
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
        participantUseCase.addOrRemoveParticipantToConference(conferenceId, participantId);
        return ResponseEntity.status(HttpStatus.OK).body("SUCCESS");
    }

}
