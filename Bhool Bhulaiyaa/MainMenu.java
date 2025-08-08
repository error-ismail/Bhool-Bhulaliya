import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
public class MainMenu extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainContainer = new JPanel(cardLayout);
    private AudioPlayer audioPlayer = new AudioPlayer();
    private boolean isMusicPlaying = false;

    public MainMenu() {
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = createMainMenuPanel();
        JPanel modePanel = createModePanel();
        JPanel rulesPanel = createRulesPanel();
        JPanel aboutPanel = createAboutPanel();
            mainContainer.add(mainPanel,"MainMenu");
            mainContainer.add(modePanel,"ModeMenu");
            mainContainer.add(rulesPanel,"RulesPanel");
            mainContainer.add(aboutPanel,"AboutPanel");

        add(mainContainer);

        toggleMusic();
        setVisible(true);
    }

    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel() {
        protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon i=new ImageIcon("C:\\coding\\Bhool Bhulaiyaa\\1.jpg"); 
        g.drawImage(i.getImage(),0,0,getWidth(),getHeight(),this);
        }
        };
    panel.setLayout(new BorderLayout()); 
    JLabel t=new JLabel("Bhool Bhulaiyaa",JLabel.CENTER);
t.setFont(new Font("Algerian",Font.BOLD,50));
   t.setForeground(Color.WHITE);
     panel.add(t,BorderLayout.NORTH);
    t.setBorder(BorderFactory.createEmptyBorder(50,0,0,0));
    JPanel button=new JPanel(new GridBagLayout()); 
    button.setOpaque(false); 

    GridBagConstraints g=new GridBagConstraints();
    g.insets=new Insets(10,0,10,0); 
    g.gridx = 0; 
    g.fill = GridBagConstraints.NONE; 
    JButton modeButton=createCustomButton("Mode",new Color(44,160,80),150,50);
    JButton musicButton=createCustomButton("Music",new Color(44,160,80),150,50);
    JButton rulesButton=createCustomButton("Rules",new Color(44,160,80),150,50);
    JButton aboutButton=createCustomButton("About",new Color(44,160,80),150,50);

    g.gridy = 0; 
    button.add(modeButton, g);
    g.gridy++;
    button.add(musicButton, g);
    g.gridy++;
    button.add(rulesButton, g);
    g.gridy++; 
    button.add(aboutButton, g);
    modeButton.addActionListener(e->cardLayout.show(mainContainer,"ModeMenu"));
    musicButton.addActionListener(e->toggleMusic());
    rulesButton.addActionListener(e->cardLayout.show(mainContainer,"RulesPanel"));
    aboutButton.addActionListener(e->cardLayout.show(mainContainer,"AboutPanel"));
    panel.add(button,BorderLayout.CENTER);
    return panel;
}
    private JPanel createModePanel() {
        JPanel panel = new JPanel() {
        protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon i = new ImageIcon("C:\\coding\\Bhool Bhulaiyaa\\2.jpg");
        g.drawImage(i.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
        };
        panel.setLayout(new BorderLayout());
 
        JLabel label=new JLabel("Select Game Mode:",JLabel.CENTER);
        label.setFont(new Font("Algerian",Font.BOLD,40));
        label.setForeground(Color.white);
        JPanel titlePanel=new JPanel(new GridBagLayout());
        titlePanel.setOpaque(false); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets=new Insets(30,0,10,0); 
        gbc.gridx=0;  
        gbc.gridy=0; 
        gbc.anchor=GridBagConstraints.CENTER;  
        titlePanel.add(label, gbc); 

        panel.add(titlePanel,BorderLayout.NORTH);

        JPanel button=new JPanel(new GridBagLayout());
        button.setOpaque(false); 
    
        gbc.insets=new Insets(10,0,10,0); 
        gbc.gridx=0;
        gbc.fill=GridBagConstraints.NONE; 
    
        JButton singlePlayerButton=createCustomButton("Single Player",new Color(44,160,80),150,50);
        JButton twoPlayerButton=createCustomButton("Two Players",new Color(44,160,80),150,50);
        JButton backButton=createCustomButton("Back",new Color(255,0,0),150,50);
    
        gbc.gridy=0;  
        button.add(singlePlayerButton,gbc);
        gbc.gridy++; 
        button.add(twoPlayerButton,gbc);
        gbc.gridy++;
        button.add(backButton,gbc);
  
        panel.add(button,BorderLayout.CENTER);

        singlePlayerButton.addActionListener(e->
        {
            new SinglePlayer();
            dispose();
        }
    );
    
        twoPlayerButton.addActionListener(e->
        {
            new TwoPlayer();
            dispose();
        }
        );
        backButton.addActionListener(e->cardLayout.show(mainContainer,"MainMenu"));
        return panel;
    }
    private JPanel createRulesPanel() {
        JPanel panel=new JPanel(new BorderLayout());
        JLabel label=new JLabel("Game Rules:",JLabel.CENTER);
        label.setFont(new Font("Arial",Font.BOLD,30));
        label.setForeground(Color.BLACK);

        JTextArea rulesText = new JTextArea("""
            1. Start at the green point.
            2. Navigate through the maze to reach the red endpoint.
            3. For single player:
                3.1 Player Blue: Use arrow keys to move.
            4. For two player mode:
                4.1 Player Blue: Use arrow keys to move.
                4.2 Player Magenta: Use W/A/S/D keys to move.
            5. Reach the endpoint before the times is up
            """);
            rulesText.setFont(new Font("Arial",Font.PLAIN,20));
        rulesText.setEditable(false);
        rulesText.setLineWrap(true);
        rulesText.setWrapStyleWord(true);
        rulesText.setBackground(new Color(50,50,50));
        rulesText.setForeground(Color.WHITE);

        JButton backButton=createCustomButton("Back",new Color(255,0,0),200,40);
        backButton.addActionListener(e->cardLayout.show(mainContainer,"MainMenu"));

        panel.add(label,BorderLayout.NORTH);
        panel.add(new JScrollPane(rulesText),BorderLayout.CENTER);
        panel.add(backButton,BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createAboutPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("About the Game:", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(Color.BLACK);

        JTextArea aboutText = new JTextArea("""
            Game Name: "Bhool Bhulaiyaa"
            Developed by: Md Ismail Hossen Shohib
            Version: 1.0
            Enjoy "BHOOL BHULAIYAA" the maze game and competing with friends!
            """);
            aboutText.setFont(new Font("Arial",Font.PLAIN,20));
        aboutText.setEditable(false);
        aboutText.setLineWrap(true);
        aboutText.setWrapStyleWord(true);
        aboutText.setBackground(new Color(30,30,30));
        aboutText.setForeground(Color.WHITE);

        JButton backButton=createCustomButton("Back",new Color(255,0,0),200,40);
        backButton.addActionListener(e->cardLayout.show(mainContainer,"MainMenu"));

        panel.add(label,BorderLayout.NORTH);
        panel.add(new JScrollPane(aboutText),BorderLayout.CENTER);
        panel.add(backButton,BorderLayout.SOUTH);

        return panel;
    }
    private JButton createCustomButton(String text, Color bgColor, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(width, height));
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        }
        );
        return button;
    }
    private void toggleMusic() {
        if (isMusicPlaying) {
            audioPlayer.stopMusic();
            isMusicPlaying = false;
        } else {
            audioPlayer.playMusic("C:\\coding\\Bhool Bhulaiyaa\\bhool_bhulaiyya (1).wav");
            isMusicPlaying = true;
        }
    } public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
