package com.testapp.conference.core.conference.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "conferences")
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conference_id;

    @NotNull
    @NotBlank(message = "Conference name cannot be blank")
    @Size(min = 4, max = 255, message = "Minimum conference name length: 4 characters")
    private String conference_name;

    private ConferenceStatus conferenceStatus;
}
