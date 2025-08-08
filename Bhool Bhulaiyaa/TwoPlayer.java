import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

public class TwoPlayer extends JFrame {
    private static final int R = 20;
    private static final int C = 30;
    private static final int CELL = 25;
    private JPanel mpanel;
    private JLabel tlabel;
    private int startcol1, startrow1;
    private int startcol2, startrow2;
    private int[][] maze;
    private int endrow;
    private int endcol;
    private boolean move;
    private Image backgroundImage;

    public TwoPlayer() {
        setSize(C * CELL + 15, R * CELL + 140);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        backgroundImage = new ImageIcon("C:\\coding\\Bhool Bhulaiyaa\\3.jpg").getImage();
        mpanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
                drawMaze(g);
            }
        };

        mpanel.setPreferredSize(new Dimension(C * CELL, R * CELL));
        setKeyBindings(mpanel);

        JPanel controlPanel = new JPanel();
        JButton generateButton = new JButton("Generate Maze");
        JButton backButton = new JButton("Back");
        generateButton.setBackground(Color.GREEN);
        backButton.setBackground(Color.red);
        controlPanel.add(generateButton);
        controlPanel.add(backButton);
        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateMaze();
                mpanel.repaint();
                mpanel.requestFocusInWindow();
            }
        });
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainMenu();
            }
        });

        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Bhool Bhulaiyaa", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Algerian", Font.BOLD, 50));
        titleLabel.setForeground(Color.BLACK);
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(mpanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        add(mainPanel);
        generateMaze();
        setVisible(true);
    }

    private void generateMaze() {
        maze = new int[R][C];
        startrow1 = 0;
        startcol1 = 1;
        startrow2 = 0;
        startcol2 = 1;
        endrow = R - 1;
        endcol = C - 1;
        move = true;
        generateBinaryTreeMaze();
        mpanel.requestFocusInWindow();
    }
    private void generateBinaryTreeMaze() {
        for (int[] row : maze) {
            Arrays.fill(row, 1);
        }
        Random random = new Random();
        for (int row = 0; row < R; row++) {
            for (int col = 0; col < C; col++) {
                if (row % 2 == 1 && col % 2 == 1) {
                    maze[row][col] = 0;
                    boolean up = random.nextBoolean();

                    if (row == 1) {
                        up = false;
                    }
                    if (col == 1) {
                        up = true;
                    }
                    if (up) {
                        maze[row - 1][col] = 0;
                    } else {
                        maze[row][col - 1] = 0;
                    }

                    if (row > 1 && col > 1 && random.nextDouble() < 0.25) {
                        if (up) {
                            maze[row][col - 1] = 0;
                        } else {
                            maze[row - 1][col] = 0;
                        }}}}}}

    private void drawMaze(Graphics g) {
        if (maze == null)
            return;
            if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, C * CELL, R * CELL, this);
            }

        for (int row = 0; row < R; row++) {
            for (int col = 0; col < C; col++) {
                if (maze[row][col] == 1) {
                    g.setColor(new Color(0, 0, 0, 150));
                } else {
                    g.setColor(new Color(255, 255, 255, 150));
                }
                g.fillRect(col * CELL, row * CELL, CELL, CELL);
                g.setColor(Color.GRAY);
                g.drawRect(col * CELL, row * CELL, CELL, CELL);
            }
        }
        int endSize = CELL;
        g.setColor(Color.RED);
        g.fillRect(endcol * CELL + (CELL - endSize) / 2, endrow * CELL + (CELL - endSize) / 2, endSize, endSize);
        int playerSize = CELL;
        g.setColor(Color.BLUE);
        g.fillRect(startcol1 * CELL + (CELL - playerSize) / 2, startrow1 * CELL + (CELL - playerSize) / 2, playerSize, playerSize);
        g.setColor(Color.MAGENTA);
        g.fillRect(startcol2 * CELL + (CELL - playerSize) / 2, startrow2 * CELL + (CELL - playerSize) / 2, playerSize, playerSize);
        int startSize = CELL;
        g.setColor(Color.GREEN);
        g.fillRect(1 * CELL + (CELL - startSize) / 2, 0 * CELL + (CELL - startSize) / 2, startSize, startSize);
        g.setColor(Color.BLACK);
    }

    private void setKeyBindings(JPanel panel) {
        InputMap i = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap a = panel.getActionMap();

        i.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "moveUp1");
        i.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveDown1");
        i.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLeft1");
        i.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRight1");

        a.put("moveUp1", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                movePlayer(1, -1, 0);
            }
        });
        a.put("moveDown1", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                movePlayer(1, 1, 0);
            }
        });
        a.put("moveLeft1", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                movePlayer(1, 0, -1);
            }
        });
        a.put("moveRight1", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                movePlayer(1, 0, 1);
            }
        });
        i.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "moveUp2");
        i.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "moveDown2");
        i.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "moveLeft2");
        i.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "moveRight2");

        a.put("moveUp2", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                movePlayer(2, -1, 0);
            }
        });
        a.put("moveDown2", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                movePlayer(2, 1, 0);
            }
        });
        a.put("moveLeft2", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                movePlayer(2, 0, -1);
            }
        });
        a.put("moveRight2", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                movePlayer(2, 0, 1);
            }
        });
    }

    private void movePlayer(int player, int rowChange, int colChange) {
        if (!move)
            return;

        int currentRow, currentCol;

        if (player == 1) {
            currentRow = startrow1;
            currentCol = startcol1;
        } else {
            currentRow = startrow2;
            currentCol = startcol2;
        }

        int newRow = currentRow + rowChange;
        int newCol = currentCol + colChange;

        if (isValidMove(newRow, newCol)) {
            if (player == 1) {
                startrow1 = newRow;
                startcol1 = newCol;
            } else {
                startrow2 = newRow;
                startcol2 = newCol;
            }

            mpanel.repaint();

            if (startrow1 == endrow && startcol1 == endcol) {
                move = false;
                JOptionPane.showMessageDialog(TwoPlayer.this, "Player Blue reached the end point! Game Over.");
                generateMaze();
            } else if (startrow2 == endrow && startcol2 == endcol) {
                move = false;
                JOptionPane.showMessageDialog(TwoPlayer.this, "Player Magenta reached the end point! Game Over.");
                generateMaze();
            }
        }
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < R && col >= 0 && col < C && maze[row][col] == 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TwoPlayer();
        });
    }
}
