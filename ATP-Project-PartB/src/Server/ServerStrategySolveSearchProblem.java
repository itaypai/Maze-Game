package Server;

import algorithms.mazeGenerators.Maze;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;

/**
 * The ServerStrategySolveSearchProblem class is a class that implements the IServerStrategy interface.
 * The server receives from the client a Maze object representing a maze.
 * The server solves it and returns to the client a Solution object that holds the solution of the maze.
 */
public class ServerStrategySolveSearchProblem implements IServerStrategy {
    private String tempDirectoryPath;

    /**
     * Constructor
     */
    public ServerStrategySolveSearchProblem()
    {
        //A temporary folder that stores the mazes that the server solves.
        tempDirectoryPath = System.getProperty("java.io.tmpdir");
    }

    /**
     * @param inFromClient, reading the client's request.
     * @param outToClient, writing to the client the results of the requested strategy.
     */
    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            Maze clientMaze = (Maze) fromClient.readObject();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}




















