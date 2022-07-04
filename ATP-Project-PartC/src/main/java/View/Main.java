package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

import java.io.File;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/StartMazeWindow.fxml"));
        Parent root = fxmlLoader.load();
        //Parent root = FXMLLoader.load(getClass().getResource("/StartMazeWindow.fxml"));
        primaryStage.setTitle("The Maze");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
        primaryStage.setFullScreen(true);

        String startSong = new File("./src/main/resources/music/beforeStart.mp3").toURI().toString();
        Media startWindowMusic = new Media(startSong);
        MyViewController.myMazeMedia = new MediaPlayer(startWindowMusic);
        MyViewController.myMazeMedia.setAutoPlay(true);
        MyViewController.myMazeMedia.setCycleCount(MediaPlayer.INDEFINITE);
        MyViewController.myMazeMedia.setOnEndOfMedia(() -> {
            MyViewController.myMazeMedia.setMute(true);
            Parent secondRoot = null;
            try {
                //secondRoot = FXMLLoader.load(getClass().getResource("/newGameSettingsView.fxml"));
                FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/newGameSettingsView.fxml"));
                secondRoot = fxmlLoader2.load();

            } catch (IOException e) {
                e.printStackTrace();
            }

            Scene mySecondScene = new Scene(secondRoot, 1000, 800);
            primaryStage.setScene(mySecondScene);
            primaryStage.setTitle("The Maze");
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            primaryStage.setX(bounds.getMinX());
            primaryStage.setY(bounds.getMinY());
            primaryStage.setWidth(bounds.getWidth());
            primaryStage.setHeight(bounds.getHeight());
            primaryStage.show();
        });

        MyViewController.myMazeMedia.play();
    }


    public static void main(String[] args)
    {
        launch(args);
    }

}
