package org.bgamard.kidmanager.client;

import io.quarkus.rest.client.reactive.ClientQueryParam;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.bgamard.kidmanager.client.model.LoginRequest;
import org.bgamard.kidmanager.client.model.LoginResponse;
import org.bgamard.kidmanager.client.model.MessagesResponse;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "ecoledirecte-api")
@ApplicationScoped
@RegisterProvider(EcoleDirecteFilter.class)
@Path("/v3")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public interface EcoleDirecteClient {
    @POST
    @Path("login.awp")
    LoginResponse login(LoginRequest loginRequest);

    @POST
    @Path("familles/{id}/messages.awp")
    @ClientQueryParam(name = "force", value = "false")
    @ClientQueryParam(name = "typeRecuperation", value = "received")
    @ClientQueryParam(name = "idClasseur", value = "0")
    @ClientQueryParam(name = "orderBy", value = "date")
    @ClientQueryParam(name = "order", value = "desc")
    @ClientQueryParam(name = "page", value = "0")
    @ClientQueryParam(name = "itemsPerPage", value = "100")
    @ClientQueryParam(name = "verbe", value = "get")
    @ClientQueryParam(name = "query", value = "")
    @ClientQueryParam(name = "onlyRead", value = "")
    MessagesResponse messages(@HeaderParam("X-Token") String token, @PathParam("id") int id);
}