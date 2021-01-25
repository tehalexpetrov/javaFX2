package sample;

import java.io.IOException;

public interface ServerService {
    void openConnection() throws IOException;
    void closeConnection() throws IOException;
    void sendMessage(String message) throws IOException;
    String readMessages() throws IOException;
}
