package org.bgamard.kidmanager.client.model;

public class Message {
    public int id;
    public String subject;
    public String date;
    public String content;
    public From from;

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
            return "Message{" +
                    "id=" + id +
                    ", subject='" + subject + '\'' +
                    ", date='" + date + '\'' +
                    ", from=" + from +
                    '}';
        }
    }