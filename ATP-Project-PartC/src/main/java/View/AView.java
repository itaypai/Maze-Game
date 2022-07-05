package View;

import Server.Configurations;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.event.ActionEvent;
import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public abstract class AView implements IView, Initializable {
    @Override
    public void exitProgram(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void showImageInformation(String imagePath, String imageTitle, double width, double height) throws FileNotFoundException
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(imageTitle);
        alert.setHeaderText("");
        alert.getDialogPane().setPrefSize(width, height);

        Image image = null;
        try {
            FileInputStream fileIS = new FileInputStream(imagePath);
            image = new Image(fileIS);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

        BackgroundRepeat backgroundRepeat = BackgroundRepeat.NO_REPEAT;
        BackgroundRepeat backgroundRepeat2 = BackgroundRepeat.NO_REPEAT;
        BackgroundPosition backgroundPosition = BackgroundPosition.CENTER;
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, backgroundRepeat, backgroundRepeat2, backgroundPosition, backgroundSize);
        Background background = new Background(backgroundImage);


        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setMaxHeight(height);
        dialogPane.setMaxWidth(width);
        dialogPane.setBackground(background);

        alert.showAndWait();

        //option - add image view


    }

    public void showConfigurations(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Properties");
        alert.setHeaderText("");
        File file = new File("./src/main/resources/config.properties");

        InputStream inputStream = new FileInputStream(file);
        Properties prop =new Properties();
        prop.load(inputStream);
        inputStream.close();

        String configurationsString = "\n\n\t" +     "Number of threads:                   " + Configurations.getInstance().getThreadPoolSize() +
                                                "\n\t" + "Maze Generate Algorithm:       " + Configurations.getInstance().getMazeGeneratingAlgorithm() +
                                                "\n\t" + "Maze Searching Algorithm:      " + Configurations.getInstance().getMazeSearchingAlgorithm();
        Text text = new Text();
        text.setText(configurationsString);
        text.setFont(Font.font("Serif", FontWeight.EXTRA_BOLD, 40));
        alert.setContentText(text.getText());
        alert.showAndWait();

    }

    public File loadFileDialog(ActionEvent event)
    {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open loaded File");
        FileChooser.ExtensionFilter fileChooserEF = new FileChooser.ExtensionFilter("Maze Objects Files", "*.maze");
        fileChooser.getExtensionFilters().addAll(fileChooserEF);
        File fileToRet = fileChooser.showOpenDialog(stage);

        return fileToRet;
    }

    public void aboutAlgorithmsMenu(ActionEvent event)
    {
        AppInformation.aboutAlgorithms();
    }


    public void aboutMeMenu(ActionEvent event)
    {
        AppInformation.aboutMe();
    }

    public void storyBehindMenu(ActionEvent event)
    {
        AppInformation.storyBehind();
    }

    public void instructionsMenu(ActionEvent event)
    {
        AppInformation.gameInstructions();
    }

}













