package ViewModel;

import Model.IModel;
import Model.MovementDirection;
import View.MyViewController;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;

public class MyViewModel extends Observable implements Observer {
    private IModel model;

    public MyViewModel(IModel model) {
        this.model = model;
        this.model.assignObserver(this); //Observe the Model for it's changes
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }

    public int[][] getMaze(){
        return model.getMaze();
    }

    public int getPlayerRow(){
        return model.getPlayerRow();
    }

    public int getPlayerCol(){
        return model.getPlayerCol();
    }

    public Queue<Integer[]> getSolution()
    {
        ArrayList<AState> solution = model.getSolution();
        Queue<Integer[]> solToRet = new LinkedList<>();

        for(AState state : solution)
        {
            Integer[] positionToAdd = new Integer[2];
            MazeState mazeState = (MazeState) state;
            positionToAdd[0] = mazeState.getMazeState().getRowIndex();
            positionToAdd[1] = mazeState.getMazeState().getColumnIndex();
            solToRet.add(positionToAdd);
        }
        return solToRet;
    }

    public void generateMaze(int rows, int cols){
        model.generateMaze(rows, cols);
    }

    public void movePlayer(KeyEvent keyEvent){
        MovementDirection directionToMove;
        switch (keyEvent.getCode())
        {
            //Directions
            case NUMPAD8 -> directionToMove = MovementDirection.UP;
            case NUMPAD2 -> directionToMove = MovementDirection.DOWN;
            case NUMPAD4 -> directionToMove = MovementDirection.LEFT;
            case NUMPAD6 -> directionToMove = MovementDirection.RIGHT;

            //Diagonals
            case NUMPAD1 -> directionToMove = MovementDirection.DOWNLEFT;
            case NUMPAD3 -> directionToMove = MovementDirection.DOWNRIGHT;
            case NUMPAD7 -> directionToMove = MovementDirection.UPLEFT;
            case NUMPAD9 -> directionToMove = MovementDirection.UPRIGHT;
            default -> {
                // no need to move the player...
                return;
            }
        }
        model.updatePlayerLocation(directionToMove);
    }

    public void movePlayerToDirection(MovementDirection directionToMove)
    {
        model.updatePlayerLocation(directionToMove);
    }

    public void loadMaze(File fileToLoad)
    {
        model.loadMaze(fileToLoad);
    }

    public void saveMaze(File fileToSave)
    {
        model.saveMaze(fileToSave);
    }

    public void solveMaze(){
        model.solveMaze();
    }

    public Integer[] getStartPosition()

    {
        return model.getStartPosition();
    }

    public Integer[] getGoalPosition()
    {
        return model.getGoalPosition();
    }

    public void stopServers()
    {
        model.stopServers();
    }

    public void assignObserver(Observer observer) {
        this.addObserver(observer);
    }
}
