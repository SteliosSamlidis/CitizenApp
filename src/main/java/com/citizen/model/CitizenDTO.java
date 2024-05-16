package com.citizen.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CitizenDTO {

    private long id;

    private String firstName;

    private String lastName;

    private String taxId;

    private String identificationId;

    private String address;

    private String gender;

    private String dateOfBirth;

}

