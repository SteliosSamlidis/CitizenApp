package com.citizen.provider;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Catches exceptions in the execute of code behind a REST endpoint and returns an error response.
 *
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(final Exception e) {
        return Response.serverError().entity(e.getMessage()).build();
    }
}
