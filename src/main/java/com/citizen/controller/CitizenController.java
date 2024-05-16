package com.citizen.controller;

import com.citizen.model.CitizenDTO;
import com.citizen.exceptions.CitizenNotFoundException;
import com.citizen.exceptions.InvalidDataException;
import com.citizen.service.ICitizenService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@Path("/citizens")
public class CitizenController {

    private final ICitizenService citizenService;

    @Autowired
    public CitizenController(ICitizenService citizenService) {
        this.citizenService = citizenService;
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCitizen(@PathParam("id") Long id, CitizenDTO citizenDTO) {
        try {
            citizenService.updateCitizen(id, citizenDTO);
            return Response.ok("Citizen updated successfully.").build();
        } catch (CitizenNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("No citizen found with the specified ID.").build();
        } catch (InvalidDataException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCitizens() {
        List<CitizenDTO> citizens = citizenService.getAllCitizens();
        return Response.ok(citizens).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCitizen(CitizenDTO citizenDTO) {
        try {
            citizenService.addCitizen(citizenDTO);
            return Response.ok("Citizen added successfully.").build();
        } catch (InvalidDataException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCitizen(@PathParam("id") Long id) {
        try {
            citizenService.deleteCitizen(id);
            return Response.ok("Citizen deleted successfully.").build();
        } catch (CitizenNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("No citizen found with the specified ID.").build();
        }
    }

}

