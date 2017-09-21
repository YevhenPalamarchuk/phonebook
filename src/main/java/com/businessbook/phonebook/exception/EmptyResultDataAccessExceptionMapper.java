
package com.businessbook.phonebook.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;

public class EmptyResultDataAccessExceptionMapper implements ExceptionMapper<EmptyResultDataAccessException> {

    @Override
    public Response toResponse(EmptyResultDataAccessException exception) {

        return Response.status(HttpStatus.NOT_FOUND.value()).entity(exception.getMessage() + " - wrong id number in the request!").type(MediaType.TEXT_PLAIN).build();
    }
}
