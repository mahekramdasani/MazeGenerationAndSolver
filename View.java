import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class View extends JFrame {

    private MazePanel mazePanel;
    private int[][] maze;
    private List<Integer> path;
    private int pathIndex;
    private Timer timer;
    private boolean dfsFound;
    private int rows, cols;
    private JLabel noPathLabel;

    public View(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        setTitle("Simple Maze Solver");
        setSize(cols * 30 + 100, rows * 30 + 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        maze = new int[rows][cols];
        path = new ArrayList<>();

        // Initialize flag
        dfsFound = false;

        // Initialize maze and perform DFS search
        generateRandomMaze();
        dfsFound = DepthFirst.searchPath(maze, 1, 1, path);

        // Set up the timer only if DFS path is found
        if (dfsFound) {
            pathIndex = path.size() - 2;

            // Delay starting the timer to ensure it happens after the frame is visible
            SwingUtilities.invokeLater(() -> {
                startTimer();
            });
        } else {
            // Display "No DFS path found!" message if needed
            noPathLabel = new JLabel("No DFS path found!");
            noPathLabel.setForeground(Color.RED);
            noPathLabel.setBounds(25, rows * 30 + 50, 200, 30);
            add(noPathLabel, "North");
            revalidate();
        }

        // Add "Generate Maze" button
        JButton generateButton = new JButton("Generate Maze");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regenerateMaze();
                // revalidate();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(generateButton);
        add(buttonPanel, "South");

        mazePanel = new MazePanel();
        add(mazePanel);
    }

    private void generateRandomMaze() {
        Random random = new Random();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                maze[row][col] = (random.nextInt(3) == 0) ? 1 : 0;
            }
        }

        // Set the starting and target nodes
        maze[1][1] = 0; // Start
        maze[rows - 2][cols - 2] = 9; // Target
    }

    private void regenerateMaze() {
        SwingUtilities.invokeLater(() -> {
            if (noPathLabel != null) {
                remove(noPathLabel);
            }
    
            generateRandomMaze();
            path.clear();
            dfsFound = DepthFirst.searchPath(maze, 1, 1, path);
    
            if (!dfsFound) {
                noPathLabel = new JLabel("No DFS path found!");
                noPathLabel.setForeground(Color.RED);
                noPathLabel.setBounds(25, rows * 30 + 50, 200, 30);
                add(noPathLabel, "North");
                revalidate();
            } else {
                if (noPathLabel != null) {
                    remove(noPathLabel);
                    revalidate();
                }
    
                // Reset pathIndex to the initial value
                pathIndex = path.size() - 2;
    
                // Stop the previous timer
                if (timer != null) {
                    timer.cancel();
                }
    
                // Start the timer after regenerating the maze
                startTimer();
            }
            mazePanel.repaint();
        });
    }
    

    private void startTimer() {
        if (timer != null) {
            timer.cancel(); // Cancel the previous timer if it exists
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!update()) {
                    // If the target is reached or no DFS path, cancel the timer
                    timer.cancel();
                }
                mazePanel.repaint();
            }
        }, 100, 500);
    }

    private boolean update() {
        pathIndex -= 2;
        if (pathIndex < 0) {
            pathIndex = 0;
        }

        boolean targetReached = pathIndex <= 0 && !path.isEmpty() && path.size() >= 2 && maze[path.get(0)][path.get(1)] == 9;
        return dfsFound && !targetReached;
    }

    private class MazePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.translate(50, 50);

            // draw the maze
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    Color color;
                    switch (maze[row][col]) {
                        case 1:
                            color = Color.BLACK;
                            break;
                        case 9:
                            color = Color.RED;
                            break;
                        default:
                            color = Color.WHITE;
                    }

                    g.setColor(color);
                    g.fillRect(30 * col, 30 * row, 30, 30);
                    g.setColor(Color.BLACK);
                    g.drawRect(30 * col, 30 * row, 30, 30);
                }
            }

            g.setColor(Color.YELLOW); // Start Node
            g.fillRect(30, 30, 30, 30);
            g.setColor(Color.BLACK);
            g.drawRect(30, 30, 30, 30);

            // draw the path list
            if (!path.isEmpty()) {
                for (int p = 0; p < path.size(); p += 2) {
                    if (p+1 < path.size()) { // Ensure there are enough elements in the path list
                        int pathX = path.get(p);
                        int pathY = path.get(p + 1);
                        g.setColor(Color.GREEN);
                        g.fillRect(pathX * 30, pathY * 30, 30, 30);
                    }
                }
            }

            // draw the ball on path
            if (!path.isEmpty() && pathIndex < path.size()) {
                int pathX = path.get(pathIndex);
                int pathY = path.get(pathIndex + 1);
                g.setColor(Color.RED);
                g.fillOval(pathX * 30, pathY * 30, 30, 30);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(cols * 30 + 100, rows * 30 + 100);
        }
    }

    @Override
    protected void processKeyEvent(KeyEvent ke) {
        if (ke.getID() != KeyEvent.KEY_PRESSED) {
            return;
        }
        if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            pathIndex -= 2;
            if (pathIndex < 0) {
                pathIndex = 0;
            }
        } else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
            pathIndex += 2;
            if (pathIndex > path.size() - 2) {
                pathIndex = path.size() - 2;
            }
        }
        mazePanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int rows = Integer.parseInt(JOptionPane.showInputDialog("Enter number of rows:"));
            int cols = Integer.parseInt(JOptionPane.showInputDialog("Enter number of columns:"));
            View view = new View(rows, cols);
            view.setVisible(true);
        });
    }
}
