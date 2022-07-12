package com.testapp.conference.core.participant.model;

import com.testapp.conference.core.conference.model.Conference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "participants")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long participant_id;

    @NotNull
    @NotBlank(message = "First name cannot be blank")
    @Size(min = 4, max = 255, message = "Minimum firstName length: 4 characters")
    private String first_name;

    @NotNull
    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 4, max = 255, message = "Minimum lastName length: 4 characters")
    private String last_name;

    @ManyToOne
    @JoinColumn(name = "conference_id", referencedColumnName = "conference_id")
    @Null
    private Conference conference;


}

