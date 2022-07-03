package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class newGameSettingsView extends AView{
    public void newGameSettings(ActionEvent event) throws IOException
    {
        Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/newGameSettingsView.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene2 = new Scene(root, 1000, 800);
        primaryStage.setScene(scene2);
        primaryStage.setFullScreen(true);
        primaryStage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
