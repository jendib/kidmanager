package org.bgamard.kidmanager.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class EcoleDirecteFilter implements ClientRequestFilter {
    @Inject
    ObjectMapper objectMapper;

    @Override
    public void filter(ClientRequestContext requestContext) throws JsonProcessingException {
        requestContext.getHeaders().putSingle(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:133.0) Gecko/20100101 Firefox/133.0");
        requestContext.getHeaders().putSingle("Origin", "https://www.ecoledirecte.com");
        requestContext.setEntity("data=" + objectMapper.writeValueAsString(requestContext.getEntity()));
    }
}