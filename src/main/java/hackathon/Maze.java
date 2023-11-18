package hackathon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Maze {
    private final int width;
    private final int height;
    private final MazeType[][] maze;
    private Cell entry;

    public Maze(int width, int height, String mazePlan) {
        this.width = width;
        this.height = height;
        this.maze = new MazeType[height][width];
        fillUpMaze(width, mazePlan);
    }

    public MazeType[][] getMaze() {
        return maze;
    }

    public Cell getEntry() {
        return entry;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Direction getInitDirection() {
        Direction result;
        if (this.getEntry().getX() == 0) result = Direction.DOWN;
        else if (this.getEntry().getY() == 0) result = Direction.RIGHT;
        else if (this.getEntry().getX() == this.getHeight() - 1) result = Direction.UP;
        else result = Direction.LEFT;
        return result;
    }

    private void fillUpMaze(int width, String mazePlan) {
        int row = 0;
        int column = 0;
        for (char cell : mazePlan.toCharArray()) {
            maze[row][column] = MazeType.getType(cell);
            if (cell == '2') this.entry = new Cell(row, column, MazeType.ENTRY);
            column++;
            if (column == width) {
                column = 0;
                row++;
            }
        }
    }

    public void showMaze() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(this.maze[i][j].ordinal());
            }
            System.out.println();
        }
    }

    public List<Cell> getNonWalls(Cell position) {
        int x;
        int y;

        List<Cell> result = new ArrayList<>();

        x = position.getX() - 1;
        y = position.getY();
        if (isInTheRange(x, this.getHeight()) && isInTheRange(y, this.getWidth()) && !this.maze[x][y].equals(MazeType.WALL)) {
            result.add(new Cell(x, y, this.maze[x][y]));
        }

        x = position.getX() + 1;
        if (isInTheRange(x, this.getHeight()) && isInTheRange(y, this.getWidth()) && !this.maze[x][y].equals(MazeType.WALL)) {
            result.add(new Cell(x, y, this.maze[x][y]));
        }

        x = position.getX();
        y = position.getY() - 1;
        if (isInTheRange(x, this.getHeight()) && isInTheRange(y, this.getWidth()) && !this.maze[x][y].equals(MazeType.WALL)) {
            result.add(new Cell(x, y, this.maze[x][y]));
        }

        y = position.getY() + 1;
        if (isInTheRange(x, this.getHeight()) && isInTheRange(y, this.getWidth()) && !this.maze[x][y].equals(MazeType.WALL)) {
            result.add(new Cell(x, y, this.maze[x][y]));
        }

        return result;
    }

    private boolean isInTheRange(int coordinate, int limit) {
        return coordinate >= 0 && coordinate < limit;
    }

    public String writeYourPlan() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.getHeight(); i++) {
            for (int j = 0; j < this.getWidth(); j++) {
                sb.append(this.maze[i][j].ordinal());
            }
        }
        return sb.toString();
    }
}
