
package com.businessbook.phonebook.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.businessbook.phonebook.model.Contact;
import com.businessbook.phonebook.service.ContactService;

import io.dropwizard.jersey.errors.ErrorMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Path("/contact")
@Component
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Api(value = "Contacts")
public class ContactResource {

    @Autowired
    private ContactService contactService;

    @POST
    @ApiOperation(value = "Add contact data to the DataBase")
    public Response createContact(Contact contact) {
        contactService.addContact(contact);
        return Response.ok(contact).build();
    }

    @PUT
    @Path("/{id}")
    @ApiOperation(value = "Update contact data in the DataBase")
    public Response updateContact(Contact contact, @PathParam("id") Long contactId) {
        contactService.editContact(contact, contactId);
        return Response.ok(contact).build();
    }

    @DELETE
    @Path("/{id}")
    @ApiOperation(value = "Delete contact defined by its ID", notes = "Id musn't be 0 or null")
    public Response deleteContact(
            @ApiParam(value = "ID of the contact that need to be deleted", required = true) @PathParam("id") Long contactId) {
        contactService.deleteContact(contactId);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Get a single contact data", notes = "Id musn't be 0 or null")
    public Response getContact(
            @ApiParam(value = "ID of the contact that need to be shown", required = true) @PathParam("id") Long contactId) {
        Contact contact = contactService.findContact(contactId);
        if (contact != null) {
            return Response.ok(contact).build();
        }
        return notFoundResponse(contactId);
    }

    @GET
    public List<Contact> getAllContacts() {
        return contactService.findAll();
    }

    private Response notFoundResponse(Long contactId) {
        Response.Status status = Response.Status.NOT_FOUND;
        return Response.status(status)
                .entity(new ErrorMessage(status.getStatusCode(), "Contact wasn't found with id " + contactId))
                .type(APPLICATION_JSON).build();
    }
}
