package org.bgamard.kidmanager.service;

import io.quarkus.logging.Log;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.bgamard.kidmanager.client.EcoleDirecteClient;
import org.bgamard.kidmanager.client.model.LoginRequest;
import org.bgamard.kidmanager.client.model.LoginResponse;
import org.bgamard.kidmanager.client.model.MessagesResponse;
import org.bgamard.kidmanager.config.Config;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
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

    private List<Integer> lastMessageIds = List.of();

    void onStart(@Observes StartupEvent ev) {
        log.info("Fetching EcoleDirecte current messages");
        var login = login();
        var messages = ecoleDirecteClient.messages(login.token, getId(login));
        log.info("Last message: {}", messages.data.messages.received.getFirst().subject);
        saveLastMessageIds(messages);
        log.info("Update service started");
    }

    private void saveLastMessageIds(MessagesResponse messages) {
        lastMessageIds = messages.data.messages.received.stream()
                .map(message -> message.id)
                .toList();
    }

    @Scheduled(cron = "${ecoledirecte.update.cron:off}", identity = "update-job")
    public void updateEcoleDirecte() {
        Log.info("Starting ecoledirecte.com update");

        var login = login();
        int id = getId(login);
        var messages = ecoleDirecteClient.messages(login.token, id);

        var newMessageIds = getNewMessageIds(messages);
        saveLastMessageIds(messages);

        for (int messageId : newMessageIds) {
            var message = ecoleDirecteClient.message(login.token, id, messageId);
            String htmlContent = new String(Base64.getMimeDecoder().decode(message.data.content), StandardCharsets.UTF_8);
            for (String email : config.parentEmails()) {
                mailer.send(Mail.withHtml(email,
                        "EcoleDirecte - " + message.data.from.prenom + " " + message.data.from.nom + " - " + message.data.subject,
                        htmlContent));
            }
        }

        Log.info("ecoledirecte.com update done");
    }

    private List<Integer> getNewMessageIds(MessagesResponse messages) {
        return messages.data.messages.received.stream()
                .map(message -> message.id)
                .filter(id -> !lastMessageIds.contains(id))
                .toList();
    }

    private LoginResponse login() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.identifiant = config.ecoledirecte().username();
        loginRequest.motdepasse = config.ecoledirecte().password();
        LoginRequest.Fa fa = new LoginRequest.Fa();
        fa.cn = config.ecoledirecte().cn();
        fa.cv = config.ecoledirecte().cv();
        loginRequest.fa = List.of(fa);
        return ecoleDirecteClient.login(loginRequest);
    }

    private int getId(LoginResponse loginResponse) {
        return loginResponse.data.accounts.getFirst().id;
    }
}
