package sample.client;

import com.google.gson.Gson;
import sample.server.AuthMessage;
import sample.server.Message;
import sample.server.MyServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketServerService implements ServerService {

    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private boolean isConnected = false;
    private final String login = "ivan";
    private final String password = "password";

    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public void openConnection() {
        try {
            socket = new Socket("localhost", MyServer.PORT);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            AuthMessage authMessage = new AuthMessage();
            authMessage.setLogin(login);
            authMessage.setPassword(password);
            dataOutputStream.writeUTF(new Gson().toJson(authMessage));

            authMessage = new Gson().fromJson(dataInputStream.readUTF(), AuthMessage.class);
            if (authMessage.isAuthenticated()) {
                isConnected = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnection() {
        try {
            dataOutputStream.close();
            dataInputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String message) {
        Message msg = new Message();
        msg.setMessage(message);

        try {
            dataOutputStream.writeUTF(new Gson().toJson(msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Message readMessages() {
        try {
            return new Gson().fromJson(dataInputStream.readUTF(), Message.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new Message();
        }
    }
}
