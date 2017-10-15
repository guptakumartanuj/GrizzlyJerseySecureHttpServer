package com.secure.server.main;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/secureGrizzly")
public class HealthResource {

    @Path("/health")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response selfHealthCheck() {

        String response = "OK";

        return Response.status(200).entity(response).build();
    }

}
