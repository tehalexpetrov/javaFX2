package sample;

public interface AutService {
    void start();
    void stop();
    String getNickByLoginAndPass(String login, String password);
}
