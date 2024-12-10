package org.bgamard.kidmanager.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "kidmanager")
public interface Config {
    EcoleDirecteConfig ecoledirecte();
}
