package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;

/**
 * The ServerStrategyGenerateMaze class is a class that implements the IServerStrategy interface.
 * Gets the desired maze sizes from the client.
 * The server generates a maze according to these sizes, compresses it using MyCompressor
 * and sends back to the client the maze created.
 */
public class ServerStrategyGenerateMaze implements IServerStrategy{
    /**
     * @param inFromClient, reading the client's request.
     * @param outToClient, writing to the client the results of the requested strategy.
     */
    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient)
    {
        try{
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            OutputStream compressedData = new MyCompressorOutputStream(new FileOutputStream("newTmpMaze.maze"));
            int[] dimensionsSize = (int[]) fromClient.readObject();
            String mazeGeneratingAlgName = Configurations.getInstance().getMazeGeneratingAlgorithm();
            IMazeGenerator mazeGen;

            if (mazeGeneratingAlgName.equalsIgnoreCase("MyMazeGenerator")){
                mazeGen = new MyMazeGenerator();
            }
            else if (mazeGeneratingAlgName.equalsIgnoreCase("SimpleMazeGenerator")){
                mazeGen = new SimpleMazeGenerator();
            }
            else{
                mazeGen = new EmptyMazeGenerator();
            }

            Maze newMaze = mazeGen.generate(dimensionsSize[0], dimensionsSize[1]);
            byte[] byteArrayMaze = newMaze.toByteArray();
            compressedData.write(byteArrayMaze);
            byte[] compressedMaze = new byte[((dimensionsSize[0] * dimensionsSize[1]) / 8) + 12];

            InputStream in = new FileInputStream("newTmpMaze.maze");
            in.read(compressedMaze);
            toClient.writeObject(compressedMaze);
            toClient.flush();
            //compressedData.flush();

            fromClient.close();
            compressedData.close();
            toClient.close();
            in.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
