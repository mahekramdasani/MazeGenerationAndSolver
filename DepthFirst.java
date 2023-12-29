import java.util.List;

public class DepthFirst {
    
    public static boolean searchPath(int[][] maze, int x, int y, List<Integer> path) {
        int rows = maze.length;
        int cols = maze[0].length;

        if (x < 0 || y < 0 || x >= cols || y >= rows) {
            return false;  // Out of bounds
        }

        if (maze[y][x] == 9) {
            path.add(x);
            path.add(y);
            return true;
        }

        if (maze[y][x] == 0) {
            maze[y][x] = 2;

            int[] dx = {-1, 1, 0, 0};
            int[] dy = {0, 0, -1, 1};

            for (int i = 0; i < 4; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];

                if (searchPath(maze, newX, newY, path)) {
                    path.add(x);
                    path.add(y);
                    return true;
                }
            }
        }

        return false;
    }
}
