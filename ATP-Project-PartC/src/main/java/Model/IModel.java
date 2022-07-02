package Model;

import ViewModel.MyViewModel;
import algorithms.search.AState;
import algorithms.search.Solution;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Observer;

public interface IModel {
    int[][] getMaze();

    int getPlayerRow();

    int getPlayerCol();

    ArrayList<AState> getSolution();

    void generateMaze(int rows, int cols);

    void updatePlayerLocation(MovementDirection direction);

    void stopServers();

    void solveMaze();

    void loadMaze(File fileToLoad);

    void saveMaze(File fileToSave);

    Integer[] getStartPosition();

    Integer[] getGoalPosition();

    void assignObserver(Observer observer);


}
