package com.example.cohort_9_java_14478_manahil.dto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({
        "id",
        "firstName",
        "lastName",
        "email",
        "phoneNumber",
        "company",
        "jobTitle"
})

@Getter
@Setter
public class ContactDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String company;

    private String jobTitle;
}