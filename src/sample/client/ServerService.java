package sample.client;

import sample.server.Message;

public interface ServerService {

    void openConnection();
    void closeConnection();

    void sendMessage(String message);
    Message readMessages();

}
