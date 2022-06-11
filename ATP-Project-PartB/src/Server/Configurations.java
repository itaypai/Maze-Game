package Server;

import java.io.InputStream;
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
     * @return
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
     * @return
     */
    public String getMazeGeneratingAlgorithm()
    {
        try {

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    public String getMazeSearchingAlgorithm()
    {
        try {

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}
