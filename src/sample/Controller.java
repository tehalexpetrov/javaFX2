package sample;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;


public class Controller {
    @FXML
    private TextArea MessageTo;

    @FXML
    private TextField message;

    @FXML
    void sendTo() {
        String text = message.getText();
//        MessageTo.setText(String.valueOf(text));
        MessageTo.appendText(text + "\n");
        message.clear();
    }

    @FXML
    void clearTo() {
        message.clear();
        MessageTo.clear();
    }

}


