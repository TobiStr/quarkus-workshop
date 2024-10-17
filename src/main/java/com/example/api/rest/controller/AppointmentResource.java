package com.example.api.rest.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/appointment")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AppointmentResource {
    @GET
    @Path("/{id}")
    public void getAppointment(
        @PathParam("id") String id) {
        //TODO: Implement
    }
}
