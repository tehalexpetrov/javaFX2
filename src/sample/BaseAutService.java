package sample;

import java.util.ArrayList;
import java.util.List;

public class BaseAutService implements AutService {

    private List<Entry> entries;

    public BaseAutService(){
        entries = new ArrayList<>();
        entries.add(new Entry("log1","pass1", "user1"));
        entries.add(new Entry("log2","pass2", "user2"));
        entries.add(new Entry("log3","pass3", "user3"));
    }

    private class Entry{
        private  String login;
        private String password;
        private String nick;

        public Entry(String login, String password, String nick) {
            this.login = login;
            this.password = password;
            this.nick = nick;
        }
    }


    @Override
    public void start() {
        System.out.println("Сервис авторизации запущен");
    }

    @Override
    public void stop() {
        System.out.println("Сервис авторизации остановлен");
    }

    @Override
    public String getNickByLoginAndPass(String login, String password) {
        for (Entry entry : entries) {
            if (login.equals(entry.login) && password.equals(entry.password)){
                return entry.nick;
            }
        }
        return null;
    }
}
