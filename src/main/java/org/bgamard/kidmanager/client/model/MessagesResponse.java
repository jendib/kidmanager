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
