import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SolveMaze {
    private int[][] dataArray;
    private int rows;
    private int cols;

    private int x_finish;
    private int y_finish;
    private int x_start;
    private int y_start;

    private int[][] shortestPath;

    private static final int[] dx = {0, 1, 0, -1};
    private static final int[] dy = {1, 0, -1, 0};

    private boolean finished = false;

    // metoda ładująca labirynt na podstawie pliku tekstowego do 2D tablicy intów, przyjmująca jako argument nazwę pliku txt
    private void loadMaze(String filePath) {
        ArrayList<int[]> data = new ArrayList<int[]>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\t");
                int[] numbers_row = new int[values.length];
                int i = 0;
                for (String s : values) {
                    int value_num = switch (s) {
                        case "W" -> -1;
                        case "C" -> 0;
                        case "S" -> 1;
                        case "F" -> -2;
                        default -> -12; // error
                    };
                    numbers_row[i] = value_num;
                    i++;
                }
                data.add(numbers_row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        dataArray = new int[data.size()][];
        data.toArray(dataArray);

        rows = data.size();
        cols = dataArray[0].length;

        shortestPath = new int[rows][cols];
    }

    private void findStartCoordinates() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (dataArray[i][j] == 1) {
                    x_start = i;
                    y_start = j;
                    return;
                }
            }
        }
    }

    public void printMaze() {
        System.out.println("Printing the maze:");
        for (int[] row : dataArray) {
            for (int value : row) {
                System.out.print(value + "\t");
            }
            System.out.println();
        }
    }

    public void printShortestPath() {
        System.out.println("Printing the shortest path:");

        for (int[] row : shortestPath) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    private void numberAdjacent(int i, int j, int n) {

        if (dataArray[i][j] == -2) {
            dataArray[i][j] = n;
            finished = true;
            x_finish = i;
            y_finish = j;
            return;
        }
        dataArray[i][j] = n;

        for (int k = 0; k < 4; k++) {
            int next_i = i + dx[k];
            int next_j = j + dy[k];

            if (next_i >= 0 && next_i < rows && next_j >= 0 && next_j < cols && (dataArray[next_i][next_j] == 0 || dataArray[next_i][next_j]==-2)&& !finished) {
                numberAdjacent(next_i, next_j, n + 1);
            }
        }
    }

    public void makePaths() {
        findStartCoordinates();
        numberAdjacent(x_start, y_start, 1);

        if (finished) {
            printMaze();
            backtrack(x_finish, y_finish);
            printShortestPath();
        } else {
            System.out.println("No path found to the finish.");
        }
    }

    public void backtrack(int i, int j) {

        while (dataArray[i][j] != 1) {
            shortestPath[i][j] = 1;

            for (int k = 0; k < 4; k++) {
                int next_i = i + dx[k];
                int next_j = j + dy[k];

                if (next_i >= 0 && next_i < rows && next_j >= 0 && next_j < cols && dataArray[next_i][next_j] == dataArray[i][j] - 1) {
                    i = next_i;
                    j = next_j;
                    break;
                }
            }
        }
        shortestPath[x_start][y_start] = 1;
        shortestPath[x_finish][y_finish] = -2;
    }

    public static void main(String[] args) {
        SolveMaze solver = new SolveMaze();
        solver.loadMaze("maze_txt.txt");
        System.out.println("Maze after loading from the text file: ");
        solver.printMaze();
        solver.makePaths();
    }
}
