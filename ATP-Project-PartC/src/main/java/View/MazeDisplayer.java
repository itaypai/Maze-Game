package View;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Queue;

public class MazeDisplayer extends Canvas{
    private int[][] maze;
    private Solution solution;
    // player position:
    private int playerRow = 0;
    private int playerCol = 0;
    // wall and player images:
    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();
    StringProperty imageFileNameGoalPos = new SimpleStringProperty();
    StringProperty imageFileNameSolutionPath = new SimpleStringProperty();
    private boolean isFinish = false;
    private int goalPosRow;
    private int goalPosCol;

    public int getPlayerRow() {
        return this.playerRow;
    }

    public int getPlayerCol() {
        return this.playerCol;
    }

    public void setPlayerPosition(int newRow, int newCol)
    {
        this.playerRow = newRow;
        this.playerCol = newCol;
        this.draw();
    }

    public void setSolution(Solution solution)
    {
        this.solution = solution;
        this.draw();
    }

    public void setFinish(boolean bool)
    {
        this.isFinish = bool;
        if (isFinish == true){
            draw();
        }
    }

    public String getImageFileNameWall() {
        return this.imageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }

    public String getImageFileNamePlayer() {
        return this.imageFileNamePlayer.get();
    }

    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }

    public String getImageFileNameGoalPos() {
        return this.imageFileNameGoalPos.get();
    }

    public void setImageFileNameGoalPos(String imageFileNameGoal) {
        this.imageFileNameGoalPos.set(imageFileNameGoal);
    }

    public String getImageFileNameSolutionPath() {
        return this.imageFileNameSolutionPath.get();
    }

    public void setImageFileNameSolutionPath(String imageFileNameSolutionPath) {
        this.imageFileNameSolutionPath.set(imageFileNameSolutionPath);
    }

    public void setGoalPosRow(int goalPosRow)
    {
        this.goalPosRow = goalPosRow;
    }

    public void setGoalPosCol(int goalPosCol)
    {
        this.goalPosCol = goalPosCol;
    }

    public boolean finish()
    {
        if (this.playerRow == this.goalPosRow && this.playerCol == this.goalPosCol)
        {
            return true;
        }
        else{
            return false;
        }
    }


    public void drawMazeGoalPosition(GraphicsContext graphicsContext, double cellHeight, double cellWidth)
    {
        Image goalPositionImage = null;
        try{
            goalPositionImage = new Image(new FileInputStream(this.getImageFileNameGoalPos()));
        }
        catch (FileNotFoundException e) {
            System.out.println("There is no image of goal position");
        }
        double xAxis = this.goalPosCol * cellWidth+100;
        double yAxis = this.goalPosRow * cellHeight;

        graphicsContext.setFill(Color.YELLOWGREEN);
        if (!this.finish()){
            this.setImageFileNameGoalPos("./src/main/resources/images/rings.png");
        }
        else{
            this.setImageFileNameGoalPos("./src/main/resources/images/treasureAfterFinish.png");
        }

        if (goalPositionImage == null){
            graphicsContext.fillRect(xAxis, yAxis, cellWidth, cellHeight);
        }
        else{
            graphicsContext.drawImage(goalPositionImage, xAxis, yAxis, cellWidth, cellHeight);
        }

    }

    public void drawMaze(int[][] mazeToDraw)
    {
        this.maze = mazeToDraw;
        this.draw();
    }

    private void draw()
    {
        if(maze != null)
        {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth() - 200;
            int rows = maze.length;
            int cols = maze[0].length;

            double cellHeight = canvasHeight / rows;
            double cellWidth = canvasWidth / cols;

            GraphicsContext graphicsContext = getGraphicsContext2D();
            //clear the canvas:
            graphicsContext.clearRect(0, 0, canvasWidth + 100, canvasHeight);

            drawMazeWalls(graphicsContext, cellHeight, cellWidth, rows, cols);
            drawMazeGoalPosition(graphicsContext, cellHeight, cellWidth);
            drawPlayerInMaze(graphicsContext, cellHeight, cellWidth);
        }
    }

    public void drawMazeSolution(Queue<Integer[]> solutionPath)
    {
        Integer posFirstValue;
        Integer posSecondValue;
        double canvasHeight = getHeight();
        double canvasWidth = getWidth() - 200;
        int numOfRows = maze.length;
        int numOfCols = maze[0].length;
        double cellHeight = canvasHeight / numOfRows;
        double cellWidth = canvasWidth / numOfCols;

        GraphicsContext graphicsContext = getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);
        Image solutionPathImage = null;
        try {
            solutionPathImage = new Image(new FileInputStream("./src/main/resources/images/dollar.png"));
        } catch (FileNotFoundException e) {
            System.out.println("There is no solution path image");
        }

        for(Integer[] position: solutionPath){
            posFirstValue = position[0];
            posSecondValue = position[1];
            if(posFirstValue == playerRow && posSecondValue == playerCol){
                continue;
            }
            double xAxis = posSecondValue * cellWidth+100;
            double yAxis = posFirstValue * cellHeight;
            graphicsContext.clearRect(xAxis,yAxis,cellWidth,cellHeight);
            graphicsContext.drawImage(solutionPathImage, xAxis, yAxis, cellWidth, cellHeight);

        }

        this.drawPlayerInMaze(graphicsContext, cellHeight, cellWidth);
        this.drawMazeWalls(graphicsContext, cellHeight, cellWidth, numOfRows, numOfCols);
        this.drawMazeGoalPosition(graphicsContext, cellHeight, cellWidth);
    }


        /*double xAxis;
        double yAxis;
        Integer posFirstValue;
        Integer posSecondValue;
        double canvasHeight = getHeight();
        double canvasWidth = getWidth();
        int numOfRows = maze.length;
        int numOfCols = maze[0].length;
        double cellHeight = canvasHeight / numOfRows;
        double cellWidth = canvasWidth / numOfCols;

        GraphicsContext graphicsContext = getGraphicsContext2D();
        Image solutionPathImage = null;
        try{
            solutionPathImage = new Image(new FileInputStream(this.getImageFileNameSolutionPath()));
        }
        catch (FileNotFoundException e){
            System.out.println("There is no solution path image");
        }

        for (Integer[] position: solutionPath)
        {
            posFirstValue = position[0];
            posSecondValue = position[1];
            if (this.playerRow == posFirstValue && this.playerCol == posSecondValue){
                continue;
            }
            else{
                xAxis = posFirstValue * cellWidth;
                yAxis = posSecondValue * cellHeight;
                graphicsContext.drawImage(solutionPathImage, xAxis, yAxis, cellWidth, cellHeight);
            }
        }

        this.drawPlayerInMaze(graphicsContext, cellHeight, cellWidth);
        this.drawMazeWalls(graphicsContext, cellHeight, cellWidth, numOfRows, numOfCols);
        this.drawMazeGoalPosition(graphicsContext, cellHeight, cellWidth);
    */

    private void drawMazeWalls(GraphicsContext graphicsContext, double cellHeight, double cellWidth, int rows, int cols)
    {
        Image mazeWallImage = null;
        double xAxis = 0;
        double yAxis = 0;
        try{
            mazeWallImage = new Image(new FileInputStream(this.getImageFileNameWall()));
        }
        catch (FileNotFoundException e){
            System.out.println("There is no image of maze wall");
        }

        graphicsContext.setFill(Color.RED);
        for (int i=0; i < rows; i++)
        {
            for(int j=0; j < cols; j++)
            {
                //If the cell is equal to 1 the cell should be a wall
                if (this.maze[i][j] == 1)
                {
                    xAxis = j * cellWidth+100;
                    yAxis = i * cellHeight;

                    if (mazeWallImage == null){
                        graphicsContext.fillRect(xAxis, yAxis, cellWidth, cellHeight);
                    }
                    else{
                        graphicsContext.drawImage(mazeWallImage, xAxis, yAxis, cellWidth, cellHeight);
                    }
                }
            }
        }
    }
    public boolean legalMove(int row, int col){
        if (row < 0 || col < 0 || row > maze.length - 1 || col > maze[0].length - 1)
            return false;
        int legalMove = maze[row][col];
        if (legalMove == 0){
            return true;
        }
        return false;
    }

    private void drawPlayerInMaze(GraphicsContext graphicsContext, double cellHeight, double cellWidth)
    {
        Image playerImage = null;
        double xAxis = getPlayerCol() * cellWidth+100;
        double yAxis = getPlayerRow() * cellHeight;
        graphicsContext.setFill(Color.GREEN);

        try
        {
            playerImage = new Image(new FileInputStream(this.getImageFileNamePlayer()));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("There is no player image");
        }
        if(playerImage == null) {
            graphicsContext.fillRect(xAxis, yAxis, cellWidth, cellHeight);
        }
        else{
            graphicsContext.drawImage(playerImage, xAxis, yAxis, cellWidth, cellHeight);
        }
    }

}

