package com.citizen.service;

import com.citizen.model.CitizenDTO;

import java.util.List;

public interface ICitizenService {
    void updateCitizen(Long id, CitizenDTO citizenDTO);

    List<CitizenDTO> getAllCitizens();

    void addCitizen(CitizenDTO citizenDTO);

    void deleteCitizen(Long id);
}
