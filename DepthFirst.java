import java.util.List;

public class DepthFirst {
    
    public static boolean searchPath(int[][] maze, int x, int y, List<Integer> path) {
        int rows = maze.length;
        int cols = maze[0].length;
    
        // Check if the current position (x, y) is out of bounds
        if (x < 0 || y < 0 || x >= cols || y >= rows) {
            return false;  // Out of bounds
        }
    
        // Check if the current position is the destination (value 9 in the maze)
        if (maze[y][x] == 9) {
            path.add(x);
            path.add(y);
            return true;
        }
    
        // Check if the current position is an open path (value 0 in the maze)
        if (maze[y][x] == 0) {
            // Mark the current position as visited (value 2 in the maze)
            maze[y][x] = 2;
    
            // Define possible moves: left, right, up, down
            int[] dx = {-1, 1, 0, 0};
            int[] dy = {0, 0, -1, 1};
    
            // Explore all possible moves
            for (int i = 0; i < 4; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];
    
                // Recursively search for a path from the new position
                if (searchPath(maze, newX, newY, path)) {
                    // If a path is found, add the current position to the path list
                    path.add(x);
                    path.add(y);
                    return true;
                }
            }
        }
    
        // No path found from this position
        return false;
    }
}
