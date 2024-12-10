package org.bgamard.kidmanager.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bgamard.kidmanager.service.UpdateService;

@Path("/update")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UpdateResource {
    @Inject
    UpdateService updateService;

    @GET
    @Path("force")
    public Response forceUpdate() {
        updateService.updateEcoleDirecte();
        return Response.ok().build();
    }
}
