package View;
//import Model.MazeGenerator;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MyViewController extends AView implements IView, Initializable, Observer {
    private MyViewModel viewModel;
    private TextField mazeRowsTextField;
    private TextField mazeColsTextField;
    private MazeDisplayer mazeDisplayer;
    private Label playerRow;
    private Label playerCol;
    private StringProperty updateCharacterRow = new SimpleStringProperty();
    private StringProperty updateCharacterCol = new SimpleStringProperty();
    public static MediaPlayer myMazeMedia;
    public MenuItem newGame;
    public MenuItem loadGame;
    public MenuItem saveGame;

    public void setViewModel(MyViewModel viewModel)
    {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);
    }

    @Override
    public void exitProgram(ActionEvent event) {
        Platform.exit();
    }

    public void setUpdateCharacterRow(String newCharacterRow)
    {
        this.updateCharacterRow.set(updateCharacterRow + "");
    }

    public void setUpdateCharacterCol(String newCharacterCol) {
        this.updateCharacterCol.set(updateCharacterCol + "");
    }

    public void setMazeRowsTextField(TextField mazeRowsTextField)
    {
        this.mazeRowsTextField = mazeRowsTextField;
    }

    public void setMazeColsTextField(TextField mazeColsTextField)
    {
        this.mazeColsTextField = mazeColsTextField;
    }

    public TextField getMazeRowsTextField()
    {
        return mazeRowsTextField;
    }

    public TextField getMazeColsTextField()
    {
        return mazeColsTextField;
    }

    public static void playMusic(String songString)
    {
        MyViewController.myMazeMedia.setMute(true);
        String currSong = new File(songString).toURI().toString();
        Media currWindowMusic = new Media(currSong);
        MyViewController.myMazeMedia = new MediaPlayer(currWindowMusic);
        MyViewController.myMazeMedia.setAutoPlay(true);
        MyViewController.myMazeMedia.setCycleCount(MediaPlayer.INDEFINITE);
        MyViewController.myMazeMedia.play();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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


    @Override
    public void update(Observable o, Object arg) {

    }
}
