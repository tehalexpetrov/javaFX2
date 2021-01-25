package sample;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {
    public static  final int PORT = 8081;

    private List<ClientHandler> clients;
    private AutService autService;


    public MyServer() {
        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            while(true){
                autService = new BaseAutService();
                autService.start();
                clients = new ArrayList<>();
                System.out.println("Ожидаем подключения клиентов");
                Socket socket = serverSocket.accept();
                System.out.println("Клиент поключился");
                clients.add(new ClientHandler(this, socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            autService.stop();
        }
    }

    public  synchronized void broadcastMessage(Message message) throws IOException {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public synchronized boolean isNickBusy(String nick){
        for (ClientHandler client : clients) {
            if (nick.equals(client.getNick())) {
                return true;
            }
        }
        return false;
    }

    public AutService getAutService(){
        return autService;
    }

    public void subcribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public void unsubcribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }
}
