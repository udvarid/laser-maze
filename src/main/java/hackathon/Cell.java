package hackathon;

import java.util.Objects;

public class Cell {

    private final int x;
    private final int y;
    private final MazeType type;
    private Direction direction;

    public Cell(int x, int y, MazeType type, Direction direction) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.direction = direction;
    }

    public Cell(int x, int y, MazeType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }


    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = MazeType.EMPTY;
    }

    public static Direction getMeDirection(Cell oldCell, Cell newCell) {
        Direction response;
        if (oldCell.getX() < newCell.getX()) response = Direction.DOWN;
        else if (oldCell.getX() > newCell.getX()) response = Direction.UP;
        else if (oldCell.getY() < newCell.getY()) response = Direction.RIGHT;
        else response = Direction.LEFT;
        return response;
    }

    public int getCantorHash() {
        return (this.x + this.y) * (this.x + this.y + 1) / 2 + this.x;
    }

    public Cell copy(MazeType newType, Direction direction) {
        return new Cell(this.x, this.y, newType, direction);
    }

    public Cell copy(Direction direction) {
        return new Cell(this.x, this.y, this.type, direction);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public MazeType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y && direction == cell.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, direction);
    }
}
