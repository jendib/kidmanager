package org.bgamard.kidmanager.service;

import io.quarkus.logging.Log;
import io.quarkus.mailer.Mailer;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bgamard.kidmanager.client.EcoleDirecteClient;
import org.bgamard.kidmanager.client.model.LoginRequest;
import org.bgamard.kidmanager.client.model.MessagesResponse;
import org.bgamard.kidmanager.config.Config;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApplicationScoped
public class UpdateService {
    private static final Logger log = LoggerFactory.getLogger(UpdateService.class);
    @Inject
    @RestClient
    EcoleDirecteClient ecoleDirecteClient;

    @Inject
    Mailer mailer;

    @Inject
    Config config;

    @Scheduled(cron = "${ecoledirecte.update.cron:off}", identity = "update-job")
    public void updateEcoleDirecte() {
        Log.info("Starting ecoledirecte.com update");

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.identifiant = config.ecoledirecte().username();
        loginRequest.motdepasse = config.ecoledirecte().password();
        LoginRequest.Fa fa = new LoginRequest.Fa();
        fa.cn = config.ecoledirecte().cn();
        fa.cv = config.ecoledirecte().cv();
        loginRequest.fa = List.of(fa);

        var response = ecoleDirecteClient.login(loginRequest);
        var messages = ecoleDirecteClient.messages(response.token, response.data.accounts.getFirst().id);
        for (MessagesResponse.Message message : messages.data.messages.received) {
            System.out.println(message.subject);
        }

        Log.info("ecoledirecte.com update done");
    }
}
