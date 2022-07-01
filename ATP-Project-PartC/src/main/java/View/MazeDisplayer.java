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

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
    private int goalPosRow;
    private int goalPosCol;

    public int getPlayerRow() {
        return this.playerRow;
    }

    public int getPlayerCol() {
        return this.playerCol;
    }

    public void setPlayerPosition(int newRow, int newCol) {
        this.playerRow = newRow;
        this.playerCol = newCol;
        this.draw();
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
        this.draw();
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
            goalPositionImage = new Image(this.getImageFileNameGoalPos());
        }
        catch (Exception e){
            System.out.println("There is no image of goal position");
        double xAxis = this.goalPosRow * cellWidth;
        double yAxis = this.goalPosCol * cellHeight;

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
            double canvasWidth = getWidth();
            int rows = maze.length;
            int cols = maze[0].length;

            double cellHeight = canvasHeight / rows;
            double cellWidth = canvasWidth / cols;

            GraphicsContext graphicsContext = getGraphicsContext2D();
            //clear the canvas:
            graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);

            drawMazeWalls(graphicsContext, cellHeight, cellWidth, rows, cols);
            if(solution != null) {
                drawMazeSolution(graphicsContext, cellHeight, cellWidth);
            }
            drawPlayerInMaze(graphicsContext, cellHeight, cellWidth);
        }
    }

    private void drawMazeSolution(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {

    }

    private void drawMazeWalls(GraphicsContext graphicsContext, double cellHeight, double cellWidth, int rows, int cols)
    {
        Image mazeWallImage = null;
        double xAxis = 0;
        double yAxis = 0;
        try{
            mazeWallImage = new Image(this.getImageFileNameWall());
        }
        catch (Exception e){
            System.out.println("There is no image of maze wall");
        }

        graphicsContext.setFill(Color.RED);
        for (int i=0; i < rows; i++)
        {
            for(int j=0; j < cols; j++)
            {
                //If the cell is equal to 1 it should be wall
                if (this.maze[i][j] == 1)
                {
                    xAxis = j * cellWidth;
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

    private void drawPlayerInMaze(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        double x = getPlayerCol() * cellWidth;
        double y = getPlayerRow() * cellHeight;
        graphicsContext.setFill(Color.GREEN);

        Image playerImage = null;
        try {
            playerImage = new Image(new FileInputStream(getImageFileNamePlayer()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no player image file");
        }
        if(playerImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(playerImage, x, y, cellWidth, cellHeight);
    }

}

