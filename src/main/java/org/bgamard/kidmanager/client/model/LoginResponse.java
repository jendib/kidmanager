package org.bgamard.kidmanager.client.model;

import java.util.List;

public class LoginResponse extends EcoleDirecteResponse {
    public Data data;

    public static class Data {
        public List<Account> accounts;

        @Override
        public String toString() {
            return "Data{" +
                    "accounts=" + accounts +
                    '}';
        }
    }

    public static class Account {
        public int id;

        @Override
        public String toString() {
            return "Account{" +
                    "id=" + id +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "data=" + data +
                ", code=" + code +
                ", token='" + token + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
