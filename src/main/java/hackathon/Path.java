package hackathon;

import java.util.*;
import java.util.stream.Collectors;

import static hackathon.Cell.getMeDirection;

public class Path {
    private final Map<Integer, Cell> freeCells;
    private final int cost;
    private final Direction inputDirection;
    private final Cell position;
    private final List<Direction> outputDirections;

    public Path(Direction inputDirection, Cell position) {
        this.freeCells = new HashMap<>();
        this.cost = 1;
        this.inputDirection = inputDirection;
        this.position = position;
        this.outputDirections = Arrays.asList(inputDirection);
    }

    public Path(Step step) {
        Path oldPath = step.getPath();
        this.freeCells = new HashMap<>(oldPath.freeCells);
        this.position = step.getCell();
        this.inputDirection = getMeDirection(oldPath.position, this.position);
        this.outputDirections = getOutPutDirections();

        boolean extraCost = false;
        if (oldPath.position.getType().equals(MazeType.EMPTY) && positionIsNotInFreeCells(oldPath.position)) {
            Cell cellToAddPath = oldPath.position.copy(getNewMazeType(oldPath.inputDirection, this.inputDirection), oldPath.inputDirection);
            this.freeCells.put(cellToAddPath.getCantorHash(), cellToAddPath);
            if (!cellToAddPath.getType().equals(MazeType.EMPTY)) {
                extraCost = true;
            }
        }
        this.cost = oldPath.cost + (extraCost ? 6 : 1);
    }

    public int costOfMove(Cell possiblePosition) {
        Direction possibleDirection = getMeDirection(this.position, possiblePosition);
        if (!this.outputDirections.contains(possibleDirection)) {
            return -1;
        }
        if (positionIsNotInFreeCells(this.position) &&
                !this.inputDirection.equals(possibleDirection) &&
                this.position.getType().equals(MazeType.EMPTY)) {
            return 6;
        }
        return 1;
    }

    public List<Cell> getModifiedFreeCells() {
        return this.freeCells.values().stream()
                .filter(cell -> MazeType.RIGHT_TO_DOWN.equals(cell.getType()) || MazeType.RIGHT_TO_UP.equals(cell.getType()))
                .collect(Collectors.toList());
    }

    public Direction getInputDirection() {
        return inputDirection;
    }

    public Cell getPosition() {
        return position;
    }

    public int getCost() {
        return cost;
    }

    private boolean positionIsNotInFreeCells(Cell position) {
        return !this.freeCells.containsKey(position.getCantorHash());
    }

    private List<Direction> getOutPutDirections() {
        List<Direction> directions = new ArrayList<>();
        Cell positionInFreeCells = this.freeCells.get(this.position.getCantorHash());
        if (this.position.getType().equals(MazeType.EMPTY) && positionInFreeCells == null) {
            if (this.inputDirection.equals(Direction.RIGHT)) directions = Arrays.asList(Direction.RIGHT, Direction.UP, Direction.DOWN);
            else if (this.inputDirection.equals(Direction.LEFT)) directions = Arrays.asList(Direction.LEFT, Direction.UP, Direction.DOWN);
            else if (this.inputDirection.equals(Direction.UP)) directions = Arrays.asList(Direction.RIGHT, Direction.UP, Direction.LEFT);
            else if (this.inputDirection.equals(Direction.DOWN)) directions = Arrays.asList(Direction.RIGHT, Direction.LEFT, Direction.DOWN);
        } else if (this.position.getType().equals(MazeType.EMPTY) && positionInFreeCells != null) {
            directions.add(getMeRightDirection(positionInFreeCells));
        } else  {
            directions.add(getMeRightDirection(this.position));
        }
        return directions;
    }

    private MazeType getNewMazeType(Direction oldDirection, Direction newDirection) {
        MazeType result = MazeType.EMPTY;

        if (oldDirection.equals(Direction.RIGHT) && newDirection.equals(Direction.DOWN) || oldDirection.equals(Direction.DOWN) && newDirection.equals(Direction.RIGHT)) result = MazeType.RIGHT_TO_DOWN;
        else if (oldDirection.equals(Direction.RIGHT) && newDirection.equals(Direction.UP) || oldDirection.equals(Direction.UP) && newDirection.equals(Direction.RIGHT)) result = MazeType.RIGHT_TO_UP;
        else if (oldDirection.equals(Direction.LEFT) && newDirection.equals(Direction.DOWN) || oldDirection.equals(Direction.DOWN) && newDirection.equals(Direction.LEFT)) result = MazeType.RIGHT_TO_UP;
        else if (oldDirection.equals(Direction.LEFT) && newDirection.equals(Direction.UP) || oldDirection.equals(Direction.UP) && newDirection.equals(Direction.LEFT)) result = MazeType.RIGHT_TO_DOWN;

        return result;
    }


    private Direction getMeRightDirection(Cell cell) {
        Direction result = this.inputDirection;

        if (MazeType.RIGHT_TO_DOWN.equals(cell.getType()) || MazeType.RIGHT_TO_DOWN_FIX.equals(cell.getType())) {
            if (this.inputDirection.equals(Direction.RIGHT)) result = Direction.DOWN;
            else if (this.inputDirection.equals(Direction.DOWN)) result = Direction.RIGHT;
            else if (this.inputDirection.equals(Direction.LEFT)) result = Direction.UP;
            else if (this.inputDirection.equals(Direction.UP)) result = Direction.LEFT;
        }
        else if (MazeType.RIGHT_TO_UP.equals(cell.getType()) || MazeType.RIGHT_TO_UP_FIX.equals(cell.getType())) {
            if (this.inputDirection.equals(Direction.RIGHT)) result = Direction.UP;
            else if (this.inputDirection.equals(Direction.UP)) result = Direction.RIGHT;
            else if (this.inputDirection.equals(Direction.LEFT)) result = Direction.DOWN;
            else if (this.inputDirection.equals(Direction.DOWN)) result = Direction.LEFT;
        }
        return result;
    }


}
