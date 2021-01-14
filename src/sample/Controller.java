package sample;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class Controller {
    @FXML
    private TextArea MessageTo;

    @FXML
    private TextField message;

    @FXML
    void sendTo() {
        String text = message.getText();
        message.clear();

    }
}
