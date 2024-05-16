package com.citizen.service.impl;

import com.citizen.domain.Citizen;
import com.citizen.exceptions.CitizenNotFoundException;
import com.citizen.model.CitizenDTO;
import com.citizen.repository.CitizenRepository;
import com.citizen.service.ICitizenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CitizenServiceTest {

    @Mock
    CitizenRepository citizenRepository;

    ICitizenService citizenService;

    @BeforeEach
    void setUp() {
        citizenService = new CitizenService(citizenRepository);
    }

    @Test
    void updateCitizen_WhenRequestedIdDoesNotExist_CitizenNotFoundExceptionGetsThrown() {
        long citizenId = 1L;
        CitizenDTO citizenDTO = new CitizenDTO();
        when(citizenRepository.findById(citizenId)).thenReturn(Optional.empty());
        assertThrows(CitizenNotFoundException.class, () -> citizenService.updateCitizen(citizenId, citizenDTO));
    }

    @Test
    void testUpdateCitizen_WhenCitizenExists_ShouldUpdateSuccessfully() {
        // Arrange
        Long id = 1L;
        CitizenDTO citizenDTO = new CitizenDTO();
        citizenDTO.setFirstName("John");
        citizenDTO.setLastName("Doe");
        citizenDTO.setTaxId("123456");
        citizenDTO.setIdentificationId("ID123");
        citizenDTO.setAddress("123 Main St");
        citizenDTO.setGender("Male");
        citizenDTO.setDateOfBirth("01/01/1990");

        Citizen citizen = new Citizen();
        citizen.setId(id);
        citizen.setFirstName("Jane");
        citizen.setLastName("Doe");
        citizen.setTaxId("654321");
        citizen.setIdentificationId("ID456");
        citizen.setAddress("456 Elm St");
        citizen.setGender("Female");
        citizen.setDateOfBirth(LocalDateTime.of(1980, 12, 31, 0, 0));

        when(citizenRepository.findById(id)).thenReturn(Optional.of(citizen));

        // Act
        citizenService.updateCitizen(id, citizenDTO);

        // Assert
        ArgumentCaptor<Citizen> argument = ArgumentCaptor.forClass(Citizen.class);
        verify(citizenRepository).save(argument.capture());
        Citizen updatedCitizen = argument.getValue();
        assertEquals(id, updatedCitizen.getId());
        assertEquals("John", updatedCitizen.getFirstName());
        assertEquals("Doe", updatedCitizen.getLastName());
        assertEquals("123456", updatedCitizen.getTaxId());
        assertEquals("ID123", updatedCitizen.getIdentificationId());
        assertEquals("123 Main St", updatedCitizen.getAddress());
        assertEquals("Male", updatedCitizen.getGender());
        assertEquals(LocalDateTime.of(1990, 1, 1, 0, 0), updatedCitizen.getDateOfBirth());
    }

    @Test
    void testUpdateCitizen_WhenCitizenDoesNotExist_ShouldThrowException() {
        // Arrange
        Long id = 1L;
        CitizenDTO citizenDTO = new CitizenDTO();

        when(citizenRepository.findById(id)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(CitizenNotFoundException.class, () -> citizenService.updateCitizen(id, citizenDTO));
    }

    @Test
    void testGetAllCitizens_ShouldReturnAllCitizens() {
        // Arrange
        List<Citizen> citizens = new ArrayList<>();
        Citizen citizen1 = new Citizen();
        citizen1.setId(1L);
        citizen1.setFirstName("John");
        citizen1.setLastName("Doe");
        citizen1.setTaxId("123456");
        citizen1.setIdentificationId("ID123");
        citizen1.setAddress("123 Main St");
        citizen1.setGender("Male");
        citizen1.setDateOfBirth(LocalDateTime.of(1990, 1, 1, 0, 0));

        Citizen citizen2 = new Citizen();
        citizen2.setId(2L);
        citizen2.setFirstName("Jane");
        citizen2.setLastName("Doe");
        citizen2.setTaxId("654321");
        citizen2.setIdentificationId("ID456");
        citizen2.setAddress("456 Elm St");
        citizen2.setGender("Female");
        citizen2.setDateOfBirth(LocalDateTime.of(1980, 12, 31, 0, 0));

        citizens.add(citizen1);
        citizens.add(citizen2);

        when(citizenRepository.findAll()).thenReturn(citizens);

        // Act
        List<CitizenDTO> citizenDTOs = citizenService.getAllCitizens();

        // Assert
        assertEquals(2, citizenDTOs.size());
        assertEquals(1L, citizenDTOs.get(0).getId());
        assertEquals("John", citizenDTOs.get(0).getFirstName());
        assertEquals("Doe", citizenDTOs.get(0).getLastName());
        assertEquals("123456", citizenDTOs.get(0).getTaxId());
        assertEquals("ID123", citizenDTOs.get(0).getIdentificationId());
        assertEquals("123 Main St", citizenDTOs.get(0).getAddress());
        assertEquals("Male", citizenDTOs.get(0).getGender());
        assertEquals("01/01/1990", citizenDTOs.get(0).getDateOfBirth());

        assertEquals(2L, citizenDTOs.get(1).getId());
        assertEquals("Jane", citizenDTOs.get(1).getFirstName());
        assertEquals("Doe", citizenDTOs.get(1).getLastName());
        assertEquals("654321", citizenDTOs.get(1).getTaxId());
        assertEquals("ID456", citizenDTOs.get(1).getIdentificationId());
        assertEquals("456 Elm St", citizenDTOs.get(1).getAddress());
        assertEquals("Female", citizenDTOs.get(1).getGender());
        assertEquals("31/12/1980", citizenDTOs.get(1).getDateOfBirth());
    }

    @Test
    void testAddCitizen_ShouldAddCitizenSuccessfully() {
        // Arrange
        CitizenDTO citizenDTO = new CitizenDTO();
        citizenDTO.setFirstName("John");
        citizenDTO.setLastName("Doe");
        citizenDTO.setTaxId("123456");
        citizenDTO.setIdentificationId("ID123");
        citizenDTO.setAddress("123 Main St");
        citizenDTO.setGender("Male");
        citizenDTO.setDateOfBirth("01/01/1990");

        // Act
        citizenService.addCitizen(citizenDTO);

        // Assert
        ArgumentCaptor<Citizen> argument = ArgumentCaptor.forClass(Citizen.class);
        verify(citizenRepository).save(argument.capture());
        Citizen capturedCitizen = argument.getValue();
        assertEquals("John", capturedCitizen.getFirstName());
        assertEquals("Doe", capturedCitizen.getLastName());
        assertEquals("123456", capturedCitizen.getTaxId());
        assertEquals("ID123", capturedCitizen.getIdentificationId());
        assertEquals("123 Main St", capturedCitizen.getAddress());
        assertEquals("Male", capturedCitizen.getGender());
        assertEquals(LocalDateTime.of(1990, 1, 1, 0, 0), capturedCitizen.getDateOfBirth());
    }

    @Test
    void testDeleteCitizen_WhenCitizenExists_ShouldDeleteSuccessfully() {
        // Arrange
        Long id = 1L;
        Citizen citizen = new Citizen();
        citizen.setId(id);
        citizen.setFirstName("John");
        citizen.setLastName("Doe");
        citizen.setTaxId("123456");
        citizen.setIdentificationId("ID123");
        citizen.setAddress("123 Main St");
        citizen.setGender("Male");
        citizen.setDateOfBirth(LocalDateTime.of(1990, 1, 1, 0, 0));

        when(citizenRepository.findById(id)).thenReturn(Optional.of(citizen));

        // Act
        citizenService.deleteCitizen(id);

        // Assert
        verify(citizenRepository).delete(citizen);
    }

    @Test
    void testDeleteCitizen_WhenCitizenDoesNotExist_ShouldThrowException() {
        // Arrange
        Long id = 1L;

        when(citizenRepository.findById(id)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(CitizenNotFoundException.class, () -> citizenService.deleteCitizen(id));
    }

}