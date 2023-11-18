package hackathon;

import java.util.*;
import java.util.stream.Collectors;

public class Solver {

    private Solver() {
    }

    public static String solveThis(Maze maze) {

        Path initPath = new Path(maze.getInitDirection(), maze.getEntry());
        List<Cell> initPossibleCells = maze.getNonWalls(initPath.getPosition());

        Set<Cell> cellWithDirections = new HashSet<>();

        Map<Integer, List<Step>> stepMap = new HashMap<>();
        stepMap.put(2, initPossibleCells.stream()
                .map(cell -> new Step(initPath, cell, initPath.costOfMove(cell)))
                .filter(step -> step.getCost() != -1)
                .collect(Collectors.toList()));

        boolean exitFound = false;
        int counter = 1;
        Optional<Path> winner = Optional.empty();

        while (!stepMap.isEmpty() && !exitFound) {
            List<Step> currentSteps = stepMap.remove(counter);
            if (currentSteps != null) {
                List<Path> newPaths = currentSteps.stream()
                        .filter(step -> isNotAlreadyVisited(cellWithDirections, step))
                        .map(step -> {
                                    Path newPath = new Path(step);
                                    cellWithDirections.add(step.getPath().getPosition().copy(newPath.getInputDirection()));
                                    return newPath;
                                }
                        )
                        .collect(Collectors.toList());

                Optional<Path> checkingWinners = newPaths.stream().filter(path -> path.getPosition().getType().equals(MazeType.EXIT)).findFirst();
                if (checkingWinners.isPresent()) {
                    exitFound = true;
                    winner = checkingWinners;
                    continue;
                }

                for (Path newPath : newPaths) {
                    List<Cell> possibleCells = maze.getNonWalls(newPath.getPosition());
                    possibleCells.stream()
                            .map(cell -> {
                                int cost = newPath.costOfMove(cell);
                                return cost != -1 ? new Step(newPath, cell, cost) : null;
                            })
                            .filter(Objects::nonNull)
                            .forEach(step -> {
                                int totalCost = step.getPath().getCost() + step.getCost();
                                List<Step> stepList = stepMap.get(totalCost);
                                if (stepList != null) {
                                    stepList.add(step);
                                } else {
                                    List<Step> newStepList = new ArrayList<>();
                                    newStepList.add(step);
                                    stepMap.put(totalCost, newStepList);
                                }
                            });
                }
            }

            counter++;
        }

        if (exitFound) {
            Path winnerPath = winner.get();
            List<Cell> modifiedCells = winnerPath.getModifiedFreeCells();
            modifiedCells.forEach(c -> maze.getMaze()[c.getX()][c.getY()] = c.getType());
            return maze.writeYourPlan();
        }

        return "-1";
    }

    private static boolean isNotAlreadyVisited(Set<Cell> cellWithDirections, Step step) {
        Cell currentPosition = step.getPath().getPosition();
        return !cellWithDirections.contains(currentPosition.copy(Cell.getMeDirection(currentPosition, step.getCell())));
    }

}
