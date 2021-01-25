package sample;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketServerService implements ServerService {
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private boolean isConnected = false;
    private final String login = "log1";
    private final String password = "pass1";

    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public void openConnection() throws IOException {
        socket = new Socket("localhost", MyServer.PORT);
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        AuthMessage authMessage = new AuthMessage();
        authMessage.setLogin(login);
        authMessage.setPassword(password);
        dataOutputStream.writeUTF(new Gson().toJson(authMessage));
        authMessage = new Gson().fromJson(dataInputStream.readUTF(), AuthMessage.class);
        if (authMessage.isAuthentificated()){
           isConnected = true;
        }
    }

    @Override
    public void closeConnection() throws IOException {
        dataOutputStream.close();
        dataInputStream.close();
        socket.close();
    }

    @Override
    public void sendMessage(String message) throws IOException {
        Message msg = new Message();
        msg.setMessage(message);
        dataOutputStream.writeUTF(new Gson().toJson(msg));

    }

    @Override
    public String readMessages() throws IOException {
       return new Gson().fromJson(dataInputStream.readUTF(), Message.class).getMessage();
    }
}
