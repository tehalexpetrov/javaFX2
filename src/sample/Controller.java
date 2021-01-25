package sample;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;


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


