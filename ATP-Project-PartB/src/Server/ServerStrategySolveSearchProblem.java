package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;
import algorithms.search.*;

import java.io.*;
import java.util.ArrayList;

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
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            Maze clientMaze = (Maze) fromClient.readObject();

            // A string that represents the path of the file we will create to the solution of the maze we received from the client.
            String currFilePath = this.tempDirectoryPath + clientMaze.hashCode();
            // Initialize a new file with the path given.
            File currNewFile = new File(currFilePath);
            Solution clientMazeSolution;
            FileInputStream newFileInputStream = new FileInputStream(currNewFile);
            ObjectInputStream clientMazeSolInput = new ObjectInputStream(newFileInputStream);

            // Check if there is already a solution file for the given maze.
            // If so do not need to solve again, but just pull it out and return to the client.
            if(currNewFile.exists()) {
                ArrayList<AState> solution = (ArrayList<AState>) clientMazeSolInput.readObject();
                clientMazeSolution = new Solution(solution);
                newFileInputStream.close();
                clientMazeSolInput.close();
            }
            // A new maze that has not been solved before.
            else{
                String mazeSearchingAlgName = Configurations.getInstance().getMazeSearchingAlgorithm();
                ISearchable newSearchableMaze = new SearchableMaze(clientMaze);
                ISearchingAlgorithm searchingAlgorithm;

                // Check by definition which algorithm we will use to solve this maze.
                if (mazeSearchingAlgName.equalsIgnoreCase("BestFirstSearch")){
                    searchingAlgorithm = new BestFirstSearch();
                }
                else if (mazeSearchingAlgName.equalsIgnoreCase("BreadthFirstSearch")){
                    searchingAlgorithm = new BreadthFirstSearch();
                }
                else{
                    searchingAlgorithm = new DepthFirstSearch();
                }

                clientMazeSolution = searchingAlgorithm.solve(newSearchableMaze);
                FileOutputStream newFileOutStream = new FileOutputStream(currNewFile);
                ObjectOutputStream mazeSolOutStream = new ObjectOutputStream(newFileOutStream);
                mazeSolOutStream.writeObject(clientMazeSolution.getSolutionPath());
                mazeSolOutStream.flush();
                newFileOutStream.close();
                mazeSolOutStream.close();
            }

            // After we have a solution to the maze we will send it back to the client.
            toClient.writeObject(clientMazeSolution);
            toClient.flush();
            toClient.close();
            fromClient.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}




















