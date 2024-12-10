package org.bgamard.kidmanager.client.model;

import java.util.List;

public class MessagesResponse extends EcoleDirecteResponse {
    public Data data;

    public static class Data {
        public Messages messages;

        @Override
        public String toString() {
            return "Data{" +
                    "messages=" + messages +
                    '}';
        }
    }

    public static class Messages {
        public List<Message> received;

        @Override
        public String toString() {
            return "Messages{" +
                    "received=" + received +
                    '}';
        }
    }

    public static class Message {
        public int id;
        public String subject;
        public String date;
        public From from;

        @Override
        public String toString() {
            return "Message{" +
                    "id=" + id +
                    ", subject='" + subject + '\'' +
                    ", date='" + date + '\'' +
                    ", from=" + from +
                    '}';
        }
    }

    public static class From {
        public String nom;
        public String prenom;

        @Override
        public String toString() {
            return "From{" +
                    "nom='" + nom + '\'' +
                    ", prenom='" + prenom + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MessagesResponse{" +
                "data=" + data +
                ", code=" + code +
                ", token='" + token + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
