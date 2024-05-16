package com.citizen.service.impl;

import com.citizen.domain.Citizen;
import com.citizen.model.CitizenDTO;
import com.citizen.exceptions.CitizenNotFoundException;
import com.citizen.exceptions.InvalidDataException;
import com.citizen.service.ICitizenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.citizen.repository.CitizenRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CitizenService implements ICitizenService {

    private final CitizenRepository citizenRepository;

    @Autowired
    public CitizenService(CitizenRepository citizenRepository) {
        this.citizenRepository = citizenRepository;
    }

    @Override
    public void updateCitizen(Long id, CitizenDTO citizenDTO) {
        Citizen citizen = citizenRepository.findById(id)
                .orElseThrow(() -> new CitizenNotFoundException("No citizen found with the specified ID."));

        Citizen updatedCitizen = mapDTOToCitizen(citizenDTO);
        updatedCitizen.setId(citizen.getId()); // Ensure the ID remains the same
        citizenRepository.save(updatedCitizen);
    }

    private Citizen mapDTOToCitizen(CitizenDTO citizenDTO) {
        validateCitizenDTO(citizenDTO);

        Citizen citizen = new Citizen();
        citizen.setFirstName(citizenDTO.getFirstName());
        citizen.setLastName(citizenDTO.getLastName());
        citizen.setTaxId(citizenDTO.getTaxId());
        citizen.setIdentificationId(citizenDTO.getIdentificationId());
        citizen.setAddress(citizenDTO.getAddress());
        citizen.setGender(citizenDTO.getGender());
        citizen.setDateOfBirth(parseDateOfBirth(citizenDTO.getDateOfBirth()));

        return citizen;
    }

    private void validateCitizenDTO(CitizenDTO citizenDTO) throws InvalidDataException {
        if (citizenDTO.getFirstName() == null || citizenDTO.getLastName() == null ||
                citizenDTO.getTaxId() == null || citizenDTO.getIdentificationId() == null ||
                citizenDTO.getAddress() == null || citizenDTO.getGender() == null || citizenDTO.getDateOfBirth() == null) {
            throw new InvalidDataException("Required fields are missing.");
        }
    }


    @Override
    public List<CitizenDTO> getAllCitizens() {
        List<Citizen> citizens = citizenRepository.findAll();
        return citizens.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CitizenDTO convertToDTO(Citizen citizen) {
        CitizenDTO dto = new CitizenDTO();
        dto.setId(citizen.getId());
        dto.setFirstName(citizen.getFirstName());
        dto.setLastName(citizen.getLastName());
        dto.setTaxId(citizen.getTaxId());
        dto.setIdentificationId(citizen.getIdentificationId());
        dto.setAddress(citizen.getAddress());
        dto.setGender(citizen.getGender());
        dto.setDateOfBirth(formatDate(citizen.getDateOfBirth()));
        return dto;
    }

    private String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateTime.format(formatter);
    }

    @Override
    public void addCitizen(CitizenDTO citizenDTO) {
        Citizen citizen = mapDTOToCitizen(citizenDTO);
        citizenRepository.save(citizen);
    }

    @Override
    public void deleteCitizen(Long id) {
        Citizen citizen = citizenRepository.findById(id)
                .orElseThrow(() -> new CitizenNotFoundException("No citizen found with the specified ID."));
        citizenRepository.delete(citizen);
    }

    private LocalDateTime parseDateOfBirth(String dateOfBirthString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(dateOfBirthString, formatter);
            return date.atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid dateOfBirth format. Please use the format dd/MM/yyyy.", e);
        }
    }

}

