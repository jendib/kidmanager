package org.bgamard.kidmanager.client;

import io.quarkus.rest.client.reactive.ClientQueryParam;
import io.quarkus.rest.client.reactive.ComputedParamContext;
import io.quarkus.rest.client.reactive.NotBody;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bgamard.kidmanager.client.model.LoginRequest;
import org.bgamard.kidmanager.client.model.LoginResponse;
import org.bgamard.kidmanager.client.model.MessageResponse;
import org.bgamard.kidmanager.client.model.MessagesResponse;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "ecoledirecte-api")
@ApplicationScoped
@RegisterProvider(EcoleDirecteFilter.class)
@Path("/v3")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public interface EcoleDirecteClient {
    @GET
    @Path("login.awp")
    @ClientQueryParam(name = "gtk", value = "1")
    @ClientQueryParam(name = "v", value = "4.77.5")
    Response gtk();
    
    @POST
    @Path("login.awp")
    @ClientQueryParam(name = "v", value = "4.77.5")
    @ClientHeaderParam(name = "Cookie", value = "{computeGtk}")
    LoginResponse login(LoginRequest loginRequest,
                        @NotBody String name,
                        @HeaderParam("X-GTK") String gtk);
    
    default String computeGtk(ComputedParamContext computedParamContext) {
        String name = (String) computedParamContext.methodParameters()
                .get(1)
                .value();
        String gtk = (String) computedParamContext.methodParameters()
                .get(2)
                .value();
        return "GTK=" + gtk + "; " + name + "=" + gtk;
    }

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

    @POST
    @Path("familles/{id}/messages/{messageId}.awp")
    @ClientQueryParam(name = "verbe", value = "get")
    @ClientQueryParam(name = "mode", value = "destinataire")
    MessageResponse message(@HeaderParam("X-Token") String token,
                            @PathParam("id") int id,
                            @PathParam("messageId") int messageId);
}
