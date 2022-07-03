package Model;

import Client.Client;
import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.Configurations;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.Solution;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.spec.ECField;
import java.sql.ClientInfoStatus;
import java.util.*;


import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import Server.*;
import Client.*;

public class MyModel extends Observable implements IModel {
    private Maze maze;
    private Solution solution = null;
    private int playerRow;
    private int playerColumn;
    private Server generateMaze;
    private Server solveMaze;
    private Logger logFile = LogManager.getLogger();

    public MyModel()
    {
        ServerStrategyGenerateMaze serverStrategyGM = new ServerStrategyGenerateMaze();
        ServerStrategySolveSearchProblem serverStrategySP = new ServerStrategySolveSearchProblem();
        generateMaze = new Server(5400, 1000, serverStrategyGM);
        solveMaze = new Server(5401, 1000, serverStrategySP);

        generateMaze.start();
        solveMaze.start();
    }


    @Override
    public int[][] getMaze() {
        return this.maze.getMazeArray();
    }

    @Override
    public int getPlayerRow() {
        return this.playerRow;
    }

    @Override
    public int getPlayerCol() {
        return this.playerColumn;
    }

    @Override
    public ArrayList<AState> getSolution() {
        ArrayList<AState> sol = solution.getSolutionPath();
        return sol;
    }

    @Override
    public void generateMaze(int rows, int cols) {
        logFile.info("Connecting to server following a user who wants to start a new maze...");

        try{
            Client newClient = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inputStream, OutputStream outputStream) {
                    try{
                        logFile.info("Client number - " + InetAddress.getLocalHost() + " requested a maze of " + rows + " rows and " +
                                cols + " columns.");

                    }
                    catch (UnknownHostException e){
                        e.printStackTrace();
                    }
                    generateMazeClientS(inputStream, outputStream, rows, cols);
                }
            });

            newClient.communicateWithServer();
            int characterRow = this.maze.getStartPosition().getRowIndex();
            int characterCol = this.maze.getStartPosition().getColumnIndex();
            this.setPlayerLocation(characterRow, characterCol);
            setChanged();
            notifyObservers("The new requested maze created.");
            logFile.info("The new requested maze created.");
        }
        catch (Exception e){
            logFile.info("The creation of the new maze failed.");
        }

    }

    private void generateMazeClientS(InputStream inputFromServer, OutputStream outputToServer, int rows, int columns)
    {
        try{
            int[] mazeSizes = new int[2];
            mazeSizes[0] = rows;
            mazeSizes[1] = columns;
            ObjectOutputStream toServer = new ObjectOutputStream(outputToServer);
            toServer.flush();
            toServer.writeObject(mazeSizes);
            toServer.flush();

            ObjectInputStream fromServer = new ObjectInputStream(inputFromServer);
            byte[] compressesMaze =  (byte[])fromServer.readObject();
            ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(compressesMaze);
            InputStream inputStream = new MyDecompressorInputStream(byteArrayIS);
            byte[] decompressedMaze = new byte[maze.toByteArray().length];
            inputStream.read(decompressedMaze);

            this.maze = new Maze(decompressedMaze);
        }
        catch (Exception e){
            logFile.info("Connecting to generating server failed.");
        }
    }

    private void setPlayerLocation(int row, int col)
    {
        int newPositionValue = this.maze.getMazeArray()[row][col];
        if (newPositionValue == 1){
            return;
        }
        else{
            this.playerRow = row;
            this.playerColumn = col;
            if (this.playerRow == this.maze.getGoalPosition().getRowIndex() &&
                    this.playerColumn == this.maze.getGoalPosition().getColumnIndex()){
                setChanged();
                notifyObservers("The player finished the maze");
                logFile.info("The player finished the maze");
            }
            else{
                setChanged();
                notifyObservers("The player moved");
            }
        }
    }

    @Override
    public void updatePlayerLocation(MovementDirection direction) {
        switch(direction){
            case UP -> {
                if (this.playerRow > 0) {
                    setPlayerLocation(this.playerRow - 1, this.playerColumn);
                }
            }

            case DOWN -> {
                if (playerRow < this.maze.getNumOfRows() - 1) {
                    setPlayerLocation(this.playerRow + 1, this.playerColumn);
                }
            }

            case LEFT -> {
                if (this.playerColumn > 0) {
                    setPlayerLocation(this.playerRow, this.playerColumn - 1);
                }
            }

            case RIGHT -> {
                if (this.playerColumn < this.maze.getNumOfCols() - 1) {
                    setPlayerLocation(this.playerRow, this.playerColumn + 1);
                }
            }

            case UPLEFT -> {
                if (this.playerRow > 0 && this.playerColumn > 0) {
                    setPlayerLocation(this.playerRow - 1, this.playerColumn -1);
                }
            }

            case UPRIGHT -> {
                if (this.playerRow > 0 && this.playerColumn < this.maze.getNumOfCols() - 1) {
                    setPlayerLocation(this.playerRow - 1, this.playerColumn + 1);
                }
            }

            case DOWNLEFT -> {
                if (this.playerRow < this.maze.getNumOfRows() - 1 && this.playerColumn > 0) {
                    setPlayerLocation(this.playerRow + 1, this.playerColumn - 1);
                }
            }

            case DOWNRIGHT -> {

                if (this.playerRow < this.maze.getNumOfRows() - 1 && this.playerColumn < this.maze.getNumOfCols() - 1) {
                    setPlayerLocation(this.playerRow + 1, this.playerColumn + 1);
                }
            }
        }

    }

    @Override
    public void stopServers() {
        this.generateMaze.stop();
        this.solveMaze.stop();
    }

    @Override
    public void solveMaze()
    {
        logFile.info("Connecting to maze solver server following a user who requested a solution to the maze...");

        try{
            Client newClient = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inputStream, OutputStream outputStream) {
                    try{
                        logFile.info("Client number - " + InetAddress.getLocalHost() + " requested a solution for his maze.");
                    }
                    catch (UnknownHostException e){
                        e.printStackTrace();
                    }
                    solveMazeClientS(inputStream, outputStream);
                }
            });
            newClient.communicateWithServer();
            //logFile.info("Maze solved");
        }
        catch (Exception e){
            logFile.info("Connecting to solving maze server failed.");
        }
    }

    private void solveMazeClientS(InputStream inputFromServer, OutputStream outputToServer)
    {
        try{
            MyCompressorOutputStream myCompressorOS = new MyCompressorOutputStream(outputToServer);
            ObjectOutputStream toServer = new ObjectOutputStream(myCompressorOS);

            Position goalPosition = this.maze.getGoalPosition();
            Position currentPosition = new Position(this.playerRow, this.playerColumn);
            Maze updatedMaze = new Maze(currentPosition, goalPosition, this.maze.getMazeArray());
            toServer.writeObject(updatedMaze);
            toServer.flush();

            ObjectInputStream fromServer = new ObjectInputStream(inputFromServer);
            this.solution = (Solution) fromServer.readObject();
            setChanged();
            notifyObservers("The maze solved");
            logFile.info("The maze solved");

        }
        catch (Exception e){
            logFile.info("Failed to search for solution");
        }
    }

    @Override
    public void loadMaze(File fileToLoad) {
        logFile.info("The user requested to save the current maze, the load menu opened");
        try {
            FileInputStream fileIS = new FileInputStream(fileToLoad);
            MyDecompressorInputStream myDecompressorIS = new MyDecompressorInputStream(fileIS);
            ObjectInputStream decompressedObject = new ObjectInputStream(myDecompressorIS);
            this.maze = (Maze) decompressedObject.readObject();

            int startRow = this.maze.getStartPosition().getRowIndex();
            int startCol = this.maze.getStartPosition().getColumnIndex();
            this.setPlayerLocation(startRow, startCol);

            setChanged();
            notifyObservers("The maze was successfully loaded");
            logFile.info("The maze was successfully loaded");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void saveMaze(File fileToSave) {
        logFile.info("The user wants to save the current maze");
        try {
            FileOutputStream fileOS = new FileOutputStream(fileToSave);
            MyCompressorOutputStream myCompressorOS = new MyCompressorOutputStream(fileOS);
            ObjectOutputStream output = new ObjectOutputStream(myCompressorOS);
            output.writeObject(this.maze);
            output.flush();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Integer[] getStartPosition() {
        Integer[] startPosition = new Integer[2];
        int rowIndex = this.maze.getStartPosition().getRowIndex();
        int colIndex = this.maze.getStartPosition().getColumnIndex();
        startPosition[0] = rowIndex;
        startPosition[1] = colIndex;
        return startPosition;
    }

    @Override
    public Integer[] getGoalPosition() {
        Integer[] goalPosition = new Integer[2];
        int rowIndex = this.maze.getGoalPosition().getRowIndex();
        int colIndex = this.maze.getGoalPosition().getColumnIndex();
        goalPosition[0] = rowIndex;
        goalPosition[1] = colIndex;
        return goalPosition;
    }

    @Override
    public void assignObserver(Observer observer) {
        this.addObserver(observer);
    }
}
