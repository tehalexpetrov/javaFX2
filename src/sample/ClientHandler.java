package sample;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Socket socket;
    private MyServer myServer;
    private DataInputStream in;
    private DataOutputStream out;
    private String nick;


    public ClientHandler(MyServer myServer, Socket socket) {
        try {
            this.myServer = myServer;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    authentication();
                    readMessage(); //читаем сообщения от клиентов
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        closeConection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeConection() throws IOException {
        myServer.unsubcribe(this);
        Message message = new Message();
        message.setMessage(nick + " вышел из чата");
        myServer.broadcastMessage(message);
        in.close();
        out.close();
        socket.close();
    }

    private void authentication() throws IOException {
        while (true){
            AuthMessage message = new Gson().fromJson(in.readUTF(), AuthMessage.class);
            String nick = myServer.getAutService().getNickByLoginAndPass(message.getLogin(), message.getPassword());
            if (nick != null && myServer.isNickBusy(nick)){
                message.setAuthentificated(true);
                out.writeUTF(new Gson().toJson(message));
                Message broadcastMsg = new Message();
                broadcastMsg.setMessage(nick + " вошел в чат");
                myServer.broadcastMessage(broadcastMsg);
                myServer.subcribe(this);
                this.nick = nick;
            }
        }
    }

    private void readMessage() throws IOException {
        while (true){
            Message message = new Gson().fromJson(in.readUTF(), Message.class);
            message.setNick(nick);
            System.out.println(message);
            if ("/end".equals(message.getMessage())){
                return;
            }
            myServer.broadcastMessage(message);
        }
    }

    public void sendMessage(Message message) throws IOException {
        out.writeUTF(new Gson().toJson(message));
    }

    public String getNick() {
        return nick;
    }
}
