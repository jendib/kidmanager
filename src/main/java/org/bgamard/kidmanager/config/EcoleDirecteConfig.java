package org.bgamard.kidmanager.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "kidmanager.ecoledirecte")
public interface EcoleDirecteConfig {
    String username();
    String password();
    String cn();
    String cv();

}
