package org.bgamard.kidmanager.config;

import io.smallrye.config.ConfigMapping;

import java.util.List;

@ConfigMapping(prefix = "kidmanager")
public interface Config {
    EcoleDirecteConfig ecoledirecte();
    List<String> parentEmails();
}
