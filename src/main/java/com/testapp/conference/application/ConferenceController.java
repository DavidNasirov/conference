package com.testapp.conference.application;

import com.testapp.conference.core.conference.model.Conference;
import com.testapp.conference.core.conference.port.input.ConferenceUseCase;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "conferences")
@RequestMapping("/api/v1/conference")
public class ConferenceController {

    private final ConferenceUseCase conferenceUseCase;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','USER_ROLE')")
    @ApiOperation(value = "Get all conferences")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
    })
    public ResponseEntity<List<Conference>> getAllConferences() {
        return ResponseEntity.status(HttpStatus.OK).body(conferenceUseCase.getAllConference());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE')")
    @ApiOperation(value = "Create conference")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Conference is already in use")
    })
    public ResponseEntity<Conference> createConference(@ApiParam("Conference") @RequestBody @Valid Conference conference) {
        return ResponseEntity.status(HttpStatus.OK).body(conferenceUseCase.createConference(conference));
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE')")
    @ApiOperation(value = "Delete conference")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
    })
    public ResponseEntity<String> deleteConference(@ApiParam("id") @RequestParam Long id) {
        conferenceUseCase.deleteConferenceById(id);
        return ResponseEntity.status(HttpStatus.OK).body("SUCCESS");
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE')")
    @ApiOperation(value = "Cancel conference")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
    })
    public ResponseEntity<String> cancelConference(@ApiParam("id") @RequestParam Long id) {
        conferenceUseCase.cancelConferenceById(id);
        return ResponseEntity.status(HttpStatus.OK).body("SUCCESS");
    }

    @GetMapping("/{idConference}")
    @PreAuthorize("hasAnyAuthority('USER_ROLE','ADMIN_ROLE')")
    @ApiOperation(value = "Check availability conference")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
    })
    public ResponseEntity<Boolean> checkAvailability(@ApiParam("id") @PathVariable Long idConference) {
        return ResponseEntity.status(HttpStatus.OK).body(conferenceUseCase.checkAvailability(idConference));
    }

}
