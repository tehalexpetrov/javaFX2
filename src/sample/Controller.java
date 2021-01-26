package sample;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.client.ServerService;
import sample.server.Message;


public class Controller {

    private ServerService serverService;

    @FXML
    private TextArea messageTo;

    @FXML
    private TextField message;

    @FXML
    void sendTo() {
        String text = message.getText();
        messageTo.appendText(text + "\n");
        message.clear();
    }

    @FXML
    void clearTo() {
        message.clear();
        messageTo.clear();
    }

}


