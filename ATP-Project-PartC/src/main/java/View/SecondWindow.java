package View;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SecondWindow extends AView{
    @FXML
    CheckBox selectHappyEfi;
    @FXML
    CheckBox selectAngryEfi;

    @FXML
    TextField textFieldRow;
    @FXML
    TextField textFieldCol;
    @FXML
    Label rowColInputError;

    String characterPath = "";

    public void initialize(URL url, ResourceBundle resourceBundle) {
        textFieldRow.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                rowColInputError.setText("");

            }
        });

        textFieldCol.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                rowColInputError.setText("");

            }
        });
    }

    public void selectionHappyEfi(ActionEvent event) {
        if (this.selectAngryEfi.isSelected())
            this.selectAngryEfi.setSelected(false);
        selectHappyEfi.setSelected(true);
        characterPath = "./resources/images/happyEfi.png";
    }

    public void selectionAngryEfi(ActionEvent event) {
        if (this.selectHappyEfi.isSelected())
            this.selectHappyEfi.setSelected(false);
        selectAngryEfi.setSelected(true);
        characterPath = "./resources/images/angryEfi.png";
    }


    public void startAccordingConfig(ActionEvent event)throws Exception
    {
        int numOfRows;
        int numOfCols;
        try {
            numOfRows = Integer.parseInt(textFieldRow.getText());
            numOfCols = Integer.parseInt(textFieldCol.getText());
            if (numOfRows < 2 || numOfCols < 2)
            {
                rowColInputError.setText("Please enter valid numbers and larger than 1!");
                return;
            }
        }
        catch (Exception e){
            rowColInputError.setText("Please enter valid numbers!");
            return;
        }

        if (this.textFieldRow.getText().equals("") || this.textFieldCol.getText().equals(""))
        {
            rowColInputError.setText("The fields of rows and columns must contain a value");
            return;
        }

        MyViewController.rows = numOfRows;
        MyViewController.cols = numOfCols;
        setMyViewScene(event);

    }

    private void setMyViewScene(ActionEvent event)throws Exception
    {
        MyViewController.characterPath = this.characterPath;

        Parent thirdRoot = FXMLLoader.load(getClass().getResource("/MyView.fxml"));
        Scene myViewScene = new Scene(thirdRoot, 1000, 800);

        Stage primaryStage = (Stage) selectHappyEfi.getScene().getWindow();
        primaryStage.setMaximized(false);
        primaryStage.setScene(myViewScene);
        primaryStage.setMaximized(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.show();
    }

    public void loadMaze(ActionEvent event) throws Exception {
        File file = this.loadFileDialog(event);
        if (file != null)
        {
            MyViewController.loadedFile = file;
            setMyViewScene(event);
        }
    }

    public void setGenerator(ActionEvent event)
    {
        String mazeGenAlgorithm = "";
        ChoiceDialog<String> firstChoiceDialog = new ChoiceDialog<String>("MyMazeGenerator");
        firstChoiceDialog.setHeaderText("Select maze generating algorithm");
        firstChoiceDialog.setTitle("Choose Maze Generator");
        ObservableList<String> listOfItems = firstChoiceDialog.getItems();
        listOfItems.add("SimpleMazeGenerator");
        listOfItems.add("EmptyMazeGenerator");
        listOfItems.add("MyMazeGenerator");

        Button button = new Button();
        button.setText("Select Maze Generator");
        Optional<String> res = firstChoiceDialog.showAndWait();
        if(res.isPresent()){
            mazeGenAlgorithm = firstChoiceDialog.getSelectedItem();
        }
        if(mazeGenAlgorithm != ""){
            Server.Configurations.getInstance().setMazeGeneratingAlgorithm(mazeGenAlgorithm);
        }
    }


    public void setSolvingAlgorithm(ActionEvent event)
    {
        String mazeSearchAlgorithm = "";
        ChoiceDialog<String> secondChoiceDialog = new ChoiceDialog<String>("BreadthFirstSearch");
        secondChoiceDialog.setHeaderText("Select maze solving algorithm");
        secondChoiceDialog.setTitle("Choose solving Algorithm");
        ObservableList<String> listOfItems2 = secondChoiceDialog.getItems();
        listOfItems2.add("DepthFirstSearch");
        listOfItems2.add("BestFirstSearch");
        listOfItems2.add("BreadthFirstSearch");

        Button secondButton = new Button();
        secondButton.setText("Select");
        Optional<String> res2 = secondChoiceDialog.showAndWait();
        if (res2.isPresent())
        {
            mazeSearchAlgorithm = secondChoiceDialog.getSelectedItem();
        }
        if (mazeSearchAlgorithm != "")
        {
            Server.Configurations.getInstance().setMazeSearchingAlgorithm(mazeSearchAlgorithm);
        }

    }

    public void setThreadsNum(ActionEvent event)
    {
        String numOfThreads = "";
        TextInputDialog threadsDialog = new TextInputDialog("4");
        threadsDialog.setTitle("Choose the number of threads");
        Optional<String> res3 = threadsDialog.showAndWait();

        if (res3.isPresent())
        {
            numOfThreads = threadsDialog.getResult();
        }
        if (numOfThreads != "")
        {
            try{
                int threadsNum = Integer.parseInt(numOfThreads);
                if (threadsNum <= 0 || threadsNum > 20)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid input of amount");
                    alert.showAndWait();
                }
                else{
                    Server.Configurations.getInstance().setThreadPoolSize(numOfThreads);
                }
            }
            catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input, the input should be valid valid number between 1 and 20");
            alert.showAndWait();
            }
        }

    }

}
