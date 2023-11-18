package hackathon;

public class Step {
    private final Path path;
    private final Cell cell;
    private final int cost;

    public Step(Path path, Cell cell, int cost) {
        this.path = path;
        this.cell = cell;
        this.cost = cost;
    }

    public Path getPath() {
        return path;
    }

    public Cell getCell() {
        return cell;
    }

    public int getCost() {
        return cost;
    }

}
