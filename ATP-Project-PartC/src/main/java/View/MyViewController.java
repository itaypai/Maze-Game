package View;

import Model.IModel;
import Model.MovementDirection;
import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MyViewController extends AView implements IView, Initializable, Observer {
    private MyViewModel viewModel;
    private TextField mazeRowsTextField;
    private TextField mazeColsTextField;
    @FXML
    public MazeDisplayer mazeDisplayer;
    private Label playerRow;
    private Label playerCol;
    public static int rows;
    public static int cols;
    private StringProperty updateCharacterRow = new SimpleStringProperty();
    private StringProperty updateCharacterCol = new SimpleStringProperty();
    public static String characterPath;
    public static File loadedFile;
    public static MediaPlayer myMazeMedia;
    public MenuItem newGame;
    public MenuItem loadGame;
    public MenuItem saveGame;
    @FXML
    public RadioButton musicOnRadioButton;
    @FXML
    public RadioButton musicOffRadioButton;
    public Button solveMazeButton;
    double prevXAxis = -1;
    double prevYAxis = -1;
    private boolean ctrlKeyPressed = false;
    @FXML
    BorderPane myMazeGameView;


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
        Image backGroundImage = null;
        String backGroundImagePath = "./src/main/resources/images/mazeViewBackground.jpg";
        try {
            FileInputStream backImageInputStream = new FileInputStream(backGroundImagePath);
            backGroundImage = new Image(backImageInputStream);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true,true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(backGroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        myMazeGameView.setBackground(background);
        this.startNewGame();

    }



    @Override
    public void update(Observable o, Object arg) {
        String update = (String) arg;
        switch (update)
        {
            case "The new requested maze created" -> generateNewMaze();

            case "The maze solved" -> showMazeSolution();

            case "The player moved" -> PlayerMoved();

            case "The player finished the maze" -> playerFinished();

            default -> System.out.println("update: " + update + "Not implemented");
        }
    }

    public void closeRequestsToServers(MouseEvent mouseEvent)
    {
        Stage primaryStage = (Stage) solveMazeButton.getScene().getWindow();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                exitGame(new ActionEvent());
            }
        });
    }

    public void exitGame(ActionEvent event) {
        this.viewModel.stopServers();
        super.exitProgram(event);
    }

    private void showMazeSolution()
    {
        mazeDisplayer.drawMazeSolution(viewModel.getSolution());

    }

    private void PlayerMoved()
    {
        int playerRow = viewModel.getPlayerRow();
        int playerCol = viewModel.getPlayerCol();
        mazeDisplayer.setPlayerPosition(playerRow, playerCol);
    }

    private void playerFinished()
    {
        mazeDisplayer.setFinish(true);
        this.playMusic("./src/main/resources/music/endGame.mp3");
        try{
            showImageInformation("./src/main/resources/images/wellDone.jpg", "Success",650, 400);
            backToSettingsWindow(new ActionEvent());
        }
        catch (Exception e){

        }
    }

    private void generateNewMaze()
    {
        Integer[] goalPosition = viewModel.getGoalPosition();
        mazeDisplayer.setGoalPosRow(goalPosition[0]);
        mazeDisplayer.setGoalPosCol(goalPosition[1]);
        int playerRow = viewModel.getPlayerRow();
        int playerCol = viewModel.getPlayerCol();
        mazeDisplayer.setPlayerPosition(playerRow, playerCol);
        mazeDisplayer.drawMaze(viewModel.getMaze());
    }

    public void backToSettingsWindow(ActionEvent event) throws IOException {
        myMazeMedia.setMute(true);
        Stage primaryStage = (Stage) solveMazeButton.getScene().getWindow();;
        Parent root = FXMLLoader.load(getClass().getResource("/newGameSettingsView.fxml"));
        Scene settingsScene = new Scene(root, 700, 575);
        //primaryStage.setMaximized(false);
        primaryStage.setScene(settingsScene);
        //primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public void solveMaze(ActionEvent actionEvent) {
        this.viewModel.solveMaze();
        mazeDisplayer.requestFocus();
    }

    public void selectionMusicOn(ActionEvent event)
    {
        if (musicOffRadioButton.isSelected())
            musicOffRadioButton.setSelected(false);
        musicOnRadioButton.setSelected(true);
        myMazeMedia.play();
        mazeDisplayer.requestFocus();
    }

    public void selectionMusicOff(ActionEvent event)
    {
        if (musicOnRadioButton.isSelected())
            musicOnRadioButton.setSelected(false);
        musicOffRadioButton.setSelected(true);
        myMazeMedia.pause();
        mazeDisplayer.requestFocus();
    }


    public void startNewGame() {
        mazeDisplayer.setScaleY(1.0);
        mazeDisplayer.setScaleX(1.0);
        mazeDisplayer.setTranslateY(0);
        mazeDisplayer.setTranslateX(0);

        IModel model = new MyModel();
        this.viewModel = new MyViewModel(model);
        viewModel.assignObserver(this);
        mazeDisplayer.setImageFileNamePlayer(characterPath);

        String mazeWallPath = "./src/main/resources/images/wall.jpg";
        mazeDisplayer.setImageFileNameWall(mazeWallPath);

        String mazeGoalPosImagePath = "./src/main/resources/images/rings.png";
        mazeDisplayer.setImageFileNameGoalPos(mazeGoalPosImagePath);

        //mazeDisplayer.finish();
        if(loadedFile != null){
            viewModel.loadMaze(loadedFile);
            loadedFile = null;
        }
        else{
            viewModel.generateMaze(rows, cols);
        }
        if (musicOffRadioButton.isSelected())
            return;
        MyViewController.myMazeMedia.setOnEndOfMedia(()->{});
        this.playMusic("./src/main/resources/music/duringGame.mp3");
    }



    public void loadMaze(ActionEvent event) {
        File file = this.loadFileDialog(event);
        if (file != null)
        {
            mazeDisplayer.setFinish(false);
            viewModel.loadMaze(file);
            if(musicOffRadioButton.isSelected())
                return;
            MyViewController.myMazeMedia.setOnEndOfMedia(()->{});
            playMusic("./src/main/resources/music/duringGame.mp3");
        }
    }

    public void saveMaze(ActionEvent event) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Maze Objects", "*.maze"));
        File f = fileChooser.showSaveDialog(stage);
        viewModel.saveMaze(f);

    }

    public void clickOnMaze(MouseEvent mouseEvent)
    {
        mazeDisplayer.requestFocus();
    }

    public void moveMaze(MouseEvent mouseEvent) {

    }

    public void keyPressed(KeyEvent keyEvent)
    {
        if(keyEvent.getCode() == KeyCode.CONTROL)
        {
            this.ctrlKeyPressed = true;
            return;
        }

        this.viewModel.movePlayer(keyEvent);

        if (keyEvent.isControlDown()){
            if(mazeDisplayer.getScaleY()==1 && mazeDisplayer.getScaleX()==1) {
                mazeDisplayer.setTranslateY(0);
                mazeDisplayer.setTranslateX(0);
                return;
            }
            switch (keyEvent.getCode()){
                case UP -> {
                    if(mazeDisplayer.getHeight()>50){
                        mazeDisplayer.setTranslateY(mazeDisplayer.getTranslateY()+20);

                    }
                }
                case DOWN -> {
                    if(true){
                        mazeDisplayer.setTranslateY(mazeDisplayer.getTranslateY()-20);
                    }
                }
                case LEFT -> {
                    if(true){
                        mazeDisplayer.setTranslateX(mazeDisplayer.getTranslateX()-20);
                    }
                }
                case RIGHT -> {
                    if(mazeDisplayer.getWidth()>200){
                        mazeDisplayer.setTranslateX(mazeDisplayer.getTranslateX()+20);
                    }
                }
            }
        }
        else{
            switch (keyEvent.getCode()){
                case UP -> {
                    viewModel.movePlayerToDirection(MovementDirection.UP);
                    return;
                }
                case DOWN -> {
                    viewModel.movePlayerToDirection(MovementDirection.DOWN);
                    return;
                }
                case LEFT -> {
                    viewModel.movePlayerToDirection(MovementDirection.LEFT);
                    return;
                }
                case RIGHT -> {
                    viewModel.movePlayerToDirection(MovementDirection.RIGHT);
                    return;
                }
                case Q -> {
                    viewModel.movePlayerToDirection(MovementDirection.UPLEFT);
                    return;
                }
                case W -> {
                    viewModel.movePlayerToDirection(MovementDirection.UPRIGHT);
                    return;
                }
                case A -> {
                    viewModel.movePlayerToDirection(MovementDirection.DOWNLEFT);
                    return;
                }
                case S -> {
                    viewModel.movePlayerToDirection(MovementDirection.DOWNRIGHT);
                    return;
                }
            }
        }
        keyEvent.consume();

    }

    public void releaseCtrlKey(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.CONTROL){
            this.ctrlKeyPressed = false;
        }
    }

    public void mouseToHand(MouseEvent mouseEvent)
    {
        Scene scene = (Scene) solveMazeButton.getScene();
        scene.setCursor(Cursor.HAND);
    }

    public void turnsMouseArrow(MouseEvent mouseEvent)
    {
        Scene scene = (Scene) solveMazeButton.getScene();
        scene.setCursor(Cursor.DEFAULT);
    }

    public void zoomInOut(ScrollEvent scrollEvent)
    {
        if(ctrlKeyPressed == false){
            return;
        }
        if(scrollEvent.getDeltaY() > 0){
            mazeDisplayer.setScaleY(mazeDisplayer.getScaleY() * 1.05);
            mazeDisplayer.setScaleX(mazeDisplayer.getScaleX() * 1.05);
        }
        else
        {
            if(mazeDisplayer.getScaleY() * 0.95 <= 1 || mazeDisplayer.getScaleX() * 0.95 <= 1){
                mazeDisplayer.setScaleY(1);
                mazeDisplayer.setScaleX(1);
                return;
            }
            mazeDisplayer.setScaleY(mazeDisplayer.getScaleY()*0.95);
            mazeDisplayer.setScaleX(mazeDisplayer.getScaleX()*0.95);
        }
    }

    public void DragMouse(MouseEvent mouseEvent)
    {
        if(prevXAxis == -1)
            prevXAxis = mouseEvent.getX();
        if(prevYAxis == -1)
            prevYAxis = mouseEvent.getY();
        if (mouseEvent.getY() < prevYAxis - 10) {
            if (mazeDisplayer.legalMove(mazeDisplayer.getPlayerRow() - 1, mazeDisplayer.getPlayerCol())) {
                viewModel.movePlayerToDirection(MovementDirection.UP);
                return;
            }
        }
        if (mouseEvent.getY() > this.prevYAxis + 10) {
            if (mazeDisplayer.legalMove(mazeDisplayer.getPlayerRow() + 1, mazeDisplayer.getPlayerCol())) {
                viewModel.movePlayerToDirection(MovementDirection.DOWN);
                return;
            }
        }
        if (mouseEvent.getX() > this.prevXAxis + 10) {
            if(mazeDisplayer.legalMove(mazeDisplayer.getPlayerRow(), mazeDisplayer.getPlayerCol()+1)) {
                viewModel.movePlayerToDirection(MovementDirection.RIGHT);
                return;
            }
        }
        if (mouseEvent.getX() < prevXAxis - 10) {
            if(mazeDisplayer.legalMove(mazeDisplayer.getPlayerRow(), mazeDisplayer.getPlayerCol()-1)) {
                viewModel.movePlayerToDirection(MovementDirection.LEFT);

            }
        }
    }

}

