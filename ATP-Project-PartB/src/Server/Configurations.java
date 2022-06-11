package Server;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Configurations class is a class that allows us to set and get all the settings we need in the program.
 * This class is a singleton class which allows the existence of only one instance of it.
 * By using it the server knows which generate maze algorithm to use, which solution search algorithm to use
 * and how many threads can work simultaneously.
 */
public class Configurations {
    private static Configurations instanceOfConfigurations = null;
    static Configurations getInstance()
    {
        if (instanceOfConfigurations == null) {
            instanceOfConfigurations = new Configurations();
        }
        return instanceOfConfigurations;
    }

    private Properties prop;

    /**
     * Constructor
     */
    private Configurations()
    {
        this.prop = new Properties();
    }

    /**
     * @return the number of threads the thread pool will use according to the settings in config.properties.
     */
    public int getThreadPoolSize()
    {
        try {
            InputStream inputStream = Configurations.class.getClassLoader().getResourceAsStream("config.properties");
            this.prop.load(inputStream);
            inputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        String threadPoolSize = this.prop.getProperty("threadPoolSize");
        return Integer.parseInt(threadPoolSize);
    }

    /**
     * @return the name of the maze generating algorithm that the server will use to generate maze
     * according to the settings in config.properties.
     */
    public String getMazeGeneratingAlgorithm()
    {
        try {
            InputStream inputStream = Configurations.class.getClassLoader().getResourceAsStream("config.properties");
            this.prop.load(inputStream);
            inputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        String mazeGeneratingAlgorithm = this.prop.getProperty("mazeGeneratingAlgorithm");
        return mazeGeneratingAlgorithm;
    }

    /**
     * @return the name of the maze searching algorithm that the server will use to find the solution of the maze
     * according to the settings in config.properties.
     */
    public String getMazeSearchingAlgorithm()
    {
        try {
            InputStream inputStream = Configurations.class.getClassLoader().getResourceAsStream("config.properties");
            this.prop.load(inputStream);
            inputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        String mazeSearchingAlgorithm = this.prop.getProperty("mazeSearchingAlgorithm");
        return mazeSearchingAlgorithm;
    }

    // Setters for the settings from the config.properties file
    public void setThreadPoolSize(String numOfThreads)
    {
        if (Integer.parseInt(numOfThreads) > 0){
            try {
                OutputStream outStream = new FileOutputStream("resources\\config.properties");
                this.prop.setProperty("threadPoolSize", numOfThreads);
                this.prop.store(outStream, null);
                outStream.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void setMazeGeneratingAlgorithm(String mazeGeneratingAlgorithm)
    {
        if (mazeGeneratingAlgorithm.equals("MyMazeGenerator") || mazeGeneratingAlgorithm.equals("SimpleMazeGenerator") ||
                mazeGeneratingAlgorithm.equals("EmptyMazeGenerator")){
            try {
                OutputStream outStream = new FileOutputStream("resources\\config.properties");
                this.prop.setProperty("mazeGeneratingAlgorithm", mazeGeneratingAlgorithm);
                this.prop.store(outStream, null);
                outStream.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void setMazeSearchingAlgorithm(String mazeSearchingAlgorithm)
    {
        if (mazeSearchingAlgorithm.equals("BestFirstSearch") || mazeSearchingAlgorithm.equals("BreadthFirstSearch") ||
                mazeSearchingAlgorithm.equals("DepthFirstSearch")){
            try {
                OutputStream outStream = new FileOutputStream("resources\\config.properties");
                this.prop.setProperty("mazeSearchingAlgorithm", mazeSearchingAlgorithm);
                this.prop.store(outStream, null);
                outStream.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
