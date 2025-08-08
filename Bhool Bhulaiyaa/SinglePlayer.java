import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

public class SinglePlayer extends JFrame {
    private static final int R = 20; 
    private static final int C = 30;
    private static final int CELL = 25;
    private static final int TIME = 30;

    private JPanel mpanel;
    private JLabel tlabel;
    private int startcol, startrow;
    private int[][] maze;
    private int endrow;
    private int endcol;
    private boolean move;
    private javax.swing.Timer timer;
    private int lefttime;
    private boolean timerStarted; 
    private Image backgroundImage; 
    public SinglePlayer() {
        
        setSize(C * CELL + 15, R * CELL + 160);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        backgroundImage = new ImageIcon("C:\\coding\\Bhool Bhulaiyaa\\3.jpg").getImage(); 

        mpanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
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

        generateButton.addActionListener(e -> {
            generateMaze();
            mpanel.repaint();
            mpanel.requestFocusInWindow();
        });

        backButton.addActionListener(e -> {
            if (timer != null) {
                timer.stop();
            }
            move = false;

            dispose();
            new MainMenu();
        });

        tlabel = new JLabel("Time left: " + TIME + " seconds");
        tlabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Bhool Bhulaiyaa", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Algerian", Font.BOLD, 50));
        titleLabel.setForeground(Color.BLACK);
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(tlabel, BorderLayout.SOUTH);

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
        startrow = 0;
        startcol = 1;
        endrow = R - 1;
        endcol = C - 1;
        move = true;
        timerStarted = false;
        generateBinaryTreeMaze();
        if (timer != null) {
            timer.stop();
        }
        lefttime = TIME;
        tlabel.setText("Time left: " + lefttime + " seconds");
        mpanel.requestFocusInWindow();
    }

    private void startTimer() {
        timer = new javax.swing.Timer(1000, e -> {
            lefttime--;
            if (lefttime <= 0) {
                timer.stop();
                move = false;
                JOptionPane.showMessageDialog(SinglePlayer.this, "Time's up! Try again.");
                resetMaze();
                mpanel.repaint();
            }
            tlabel.setText("Time left: " + lefttime + " seconds");
        });
        timer.start();
    }

    private void resetMaze() {
        startrow = 0;
        startcol = 1;
        move = true;
        timerStarted = false;
        lefttime = TIME;
        tlabel.setText("Time left: " + lefttime + " seconds");
        if (timer != null) {
            timer.stop();
        }
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
                    } 
                    else {
                        maze[row][col - 1] = 0;
                    }
                    if (row > 1 && col > 1 && random.nextDouble() < 0.25) {
                        if (up) {
                            maze[row][col - 1] = 0;
                        } else {
                            maze[row - 1][col] = 0;
                        }
                    }
                }
            }
        }
    }

    private void drawMaze(Graphics g) {
        if (maze == null) return;

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

        g.setColor(Color.RED);
        g.fillRect(endcol * CELL, endrow * CELL, CELL, CELL);

        g.setColor(Color.BLUE);
        g.fillRect(startcol * CELL, startrow * CELL, CELL, CELL);

        g.setColor(Color.GREEN);
        g.fillRect(1 * CELL, 0 * CELL, CELL, CELL);
    }

    private void setKeyBindings(JPanel panel) {
        InputMap i = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap a = panel.getActionMap();

        i.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "moveUp");
        i.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveDown");
        i.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLeft");
        i.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRight");

        a.put("moveUp", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                movePlayer(-1, 0);
            }
        });
        a.put("moveDown", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                movePlayer(1, 0);
            }
        });
        a.put("moveLeft", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                movePlayer(0, -1);
            }
        });
        a.put("moveRight", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                movePlayer(0, 1);
            }
        });
    }

    private void movePlayer(int rowChange, int colChange) {
        if (!move) return;

        if (!timerStarted) {
            timerStarted = true;
            startTimer();
        }

        int newRow = startrow + rowChange;
        int newCol = startcol + colChange;

        if (isValidMove(newRow, newCol)) {
            startrow = newRow;
            startcol = newCol;
            mpanel.repaint();

            if (startrow == endrow && startcol == endcol) {
                timer.stop();
                move = false;
                JOptionPane.showMessageDialog(this, "Congratulations You reached the end point");
                resetMaze();
                generateMaze();
            }
        }
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < R && col >= 0 && col < C && maze[row][col] == 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SinglePlayer::new);
    }
}
