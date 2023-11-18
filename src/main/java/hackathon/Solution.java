package hackathon;


public class Solution {

    public static void main(String[] args) {
        Maze maze = new Maze(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2]);
        System.out.println(Solver.solveThis(maze));
    }
}