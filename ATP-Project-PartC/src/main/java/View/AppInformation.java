package View;

import javafx.scene.control.Alert;

public class AppInformation {
    public static void gameInstructions()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("The Goal:"+ "\n" +
                "Bring the character to the ring at the end of the maze and make Efrat happy." +
                "\n" + "\n" +
                "To zoom in and zoom out, press the ctrl key on the keyboard and the mouse scroller" + "\n" + "at the same time and select the zoom you want." +
                "\n" + "\n" +
                "Displacement of the character on the screen will be done using the existing keyboard arrows" +
                "\n" +
                "There is another option to move the character using the following number buttons:" + "\n" +
                "Directions:                Diagonals:" + "\n" +
                "2- down                    1- down left" + "\n" +
                "4- left                    3- down right" + "\n" +
                "6- right                   7- up left" + "\n" +
                "8- up                      9- up right" +
                "\n" + "\n" +
                "If you do not know how to continue, you can click on the Help me button on the screen" + "\n" +
                "and the solution to the current maze will be displayed on the maze."
                );
        alert.show();
    }

    public static void storyBehind()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Hi everybody," + "\n" +
            "My name is Itay and my girlfriend Efrat has a message for you:" + "\n" +
            "\"I have a dream to get married and I need your help to get through the maze and get the ring I'm waiting for.\"");
        alert.show();
    }

    public static void aboutMe()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Hello my name is Itay Paikin and I am the friend of the cute character you see inside the maze." + "\n" +
            "I am a third year student in the department of Software and Information Systems Engineering at Ben Gurion University." + "\n" +
            "This maze application was created as part of the Advanced Topics in Programming course.");
        alert.show();
    }

    public static void aboutAlgorithms()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Maze Generators Algorithms:" + "\n" +
                "EmptyMazeGenerator - creates an empty maze without walls and randomly selects the start and goal positions." + "\n" +
                "SimpleMazeGenerator - creates a simple random maze with a path from the starting position to the goal position." + "\n" +
                "MyMazeAlgorithm - Uses a DFS algorithm to create the maze." + "\n" +
                "Solving Algorithms:" + "\n" +
                "In order to solve the maze, one of the following three search algorithms is used -" + "\n" +
                "Breadth First Search / Depth First Search / Best First Search.");
        alert.show();
    }

}
