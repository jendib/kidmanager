package org.bgamard.kidmanager.client.model;

import java.util.List;

public class LoginRequest {
    public String identifiant;
    public String motdepasse;
    public boolean isRelogin = false;
    public String uuid = "";
    public List<Fa> fa;

    public static class Fa {
        public String cn;
        public String cv;
    }
}
