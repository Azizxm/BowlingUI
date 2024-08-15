import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BowlingGameUI extends JFrame {
    private CardLayout cardLayout;  // Layout manager for switching between different screens
    private JPanel mainPanel;  // Main panel that holds all screens (cards)
    private JTextField[] playerNameFields;  // Text fields for entering player names
    private JLabel[] scoreLabels;  // Labels to display scores for each player
    private int currentPlayerIndex = 0;  // Index to track the current player's turn
    private int[][] frames = new int[4][10];  // Array to store the scores for each player and frame
    private int[] scores = new int[4];  // Array to store the total scores for each player

    // Constructor to set up the UI
    public BowlingGameUI() {
        setTitle("Bowling Game");  // Set the title of the window
        setSize(500, 500);  // Set the size of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Exit the application when the window is closed

        cardLayout = new CardLayout();  // Initialize the CardLayout
        mainPanel = new JPanel(cardLayout);  // Create the main panel with CardLayout

        // Add different screens (cards) to the main panel
        mainPanel.add(createWelcomeScreen(), "WelcomeScreen");
        mainPanel.add(createPlayerSetupScreen(), "PlayerSetupScreen");
        mainPanel.add(createGameScreen(), "GameScreen");
        mainPanel.add(createInstructionsScreen(), "InstructionsScreen");
        mainPanel.add(createSettingsScreen(), "SettingsScreen");

        add(mainPanel);  // Add the main panel to the frame
        cardLayout.show(mainPanel, "WelcomeScreen");  // Show the Welcome screen initially
    }

    // Method to create the Welcome Screen
    private JPanel createWelcomeScreen() {
        JPanel panel = new JPanel(new GridBagLayout());  // Use GridBagLayout for centered components
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Bowling Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));  // Set font for the title label
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        // Button to start the game, navigates to the Player Setup Screen
        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(e -> cardLayout.show(mainPanel, "PlayerSetupScreen"));
        gbc.gridy = 1;
        panel.add(startGameButton, gbc);

        // Button to view instructions, navigates to the Instructions Screen
        JButton instructionsButton = new JButton("Instructions");
        instructionsButton.addActionListener(e -> cardLayout.show(mainPanel, "InstructionsScreen"));
        gbc.gridy = 2;
        panel.add(instructionsButton, gbc);

        // Button to adjust settings, navigates to the Settings Screen
        JButton settingsButton = new JButton("Settings");
        settingsButton.addActionListener(e -> cardLayout.show(mainPanel, "SettingsScreen"));
        gbc.gridy = 3;
        panel.add(settingsButton, gbc);

        // Button to exit the game
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        gbc.gridy = 4;
        panel.add(exitButton, gbc);

        return panel;  // Return the completed Welcome Screen panel
    }

    // Method to create the Player Setup Screen
    private JPanel createPlayerSetupScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  // Use vertical box layout for components

        JLabel titleLabel = new JLabel("Player Setup");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));  // Set font for the title label
        panel.add(titleLabel);

        JLabel instructionLabel = new JLabel("Enter Player Names (1-4):");
        panel.add(instructionLabel);

        // Text fields for entering up to 4 player names
        playerNameFields = new JTextField[4];
        for (int i = 0; i < 4; i++) {
            playerNameFields[i] = new JTextField(20);
            panel.add(playerNameFields[i]);
        }

        // Button to start the game, navigates to the Game Screen
        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(e -> startGame());
        panel.add(startGameButton);

        return panel;  // Return the completed Player Setup Screen panel
    }

    // Method to create the Game Screen
    private JPanel createGameScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  // Use vertical box layout for components

        JLabel titleLabel = new JLabel("Bowling Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));  // Set font for the title label
        panel.add(titleLabel);

        // Panel to display the scoreboard
        JPanel scoreboardPanel = new JPanel(new GridLayout(4, 1));  // GridLayout for displaying player scores
        scoreLabels = new JLabel[4];
        for (int i = 0; i < 4; i++) {
            scoreLabels[i] = new JLabel();  // Create labels for each player's score
            scoreboardPanel.add(scoreLabels[i]);
        }
        panel.add(scoreboardPanel);

        // Button to simulate rolling the ball
        JButton rollButton = new JButton("Roll Ball");
        rollButton.addActionListener(e -> rollBall());
        panel.add(rollButton);

        // Button to reset the current frame
        JButton resetButton = new JButton("Reset Frame");
        resetButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Frame reset!"));
        panel.add(resetButton);

        return panel;  // Return the completed Game Screen panel
    }

    // Method to create the Instructions Screen
    private JPanel createInstructionsScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  // Use vertical box layout for components

        JLabel titleLabel = new JLabel("How to Play");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));  // Set font for the title label
        panel.add(titleLabel);

        // Text area to display the instructions
        JTextArea instructionsTextArea = new JTextArea(10, 30);
        instructionsTextArea.setText("1. The game consists of 10 frames.\n" +
                "2. Each player rolls the ball twice per frame, trying to knock down all 10 pins.\n" +
                "3. A strike is when you knock down all 10 pins on the first roll.\n" +
                "4. A spare is when you knock down all 10 pins with both rolls.\n" +
                "5. The player with the highest score after 10 frames wins.");
        instructionsTextArea.setWrapStyleWord(true);
        instructionsTextArea.setLineWrap(true);
        instructionsTextArea.setEditable(false);  // Make the text area non-editable
        panel.add(instructionsTextArea);

        // Button to go back to the Welcome Screen
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "WelcomeScreen"));
        panel.add(backButton);

        return panel;  // Return the completed Instructions Screen panel
    }

    // Method to create the Settings Screen
    private JPanel createSettingsScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  // Use vertical box layout for components

        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));  // Set font for the title label
        panel.add(titleLabel);

        // Label and combo box for selecting difficulty level
        JLabel difficultyLabel = new JLabel("Difficulty Level:");
        panel.add(difficultyLabel);

        String[] difficultyLevels = {"Easy", "Medium", "Hard"};
        JComboBox<String> difficultyComboBox = new JComboBox<>(difficultyLevels);
        panel.add(difficultyComboBox);

        // Check box for enabling or disabling sound
        JCheckBox soundCheckBox = new JCheckBox("Enable Sound");
        soundCheckBox.setSelected(true);  // Enable sound by default
        panel.add(soundCheckBox);

        // Button to save settings and go back to the Welcome Screen
        JButton saveButton = new JButton("Save Settings");
        saveButton.addActionListener(e -> cardLayout.show(mainPanel, "WelcomeScreen"));
        panel.add(saveButton);

        return panel;  // Return the completed Settings Screen panel
    }

    // Method to start the game
    private void startGame() {
        for (int i = 0; i < playerNameFields.length; i++) {
            if (!playerNameFields[i].getText().isEmpty()) {
                scoreLabels[i].setText(playerNameFields[i].getText() + ": 0");  // Initialize player score to 0
            }
        }
        cardLayout.show(mainPanel, "GameScreen");  // Show the Game Screen
    }

    // Method to simulate rolling the ball
    private void rollBall() {
        String currentPlayer = scoreLabels[currentPlayerIndex].getText().split(":")[0];  // Get the current player's name
        JOptionPane.showMessageDialog(this, currentPlayer + " is rolling...");

        // Simulate rolling a random score (this would be more complex in a full game)
        int pinsKnockedDown = (int) (Math.random() * 11);  // Random number between 0 and 10
        frames[currentPlayerIndex][0] += pinsKnockedDown;  // Update the score in the first frame
        scores[currentPlayerIndex] += pinsKnockedDown;  // Update the total score

        // Update the score label for the current player
        scoreLabels[currentPlayerIndex].setText(currentPlayer + ": " + scores[currentPlayerIndex]);

        // Move to the next player
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BowlingGameUI gameUI = new BowlingGameUI();
            gameUI.setVisible(true);  // Show the UI
        });
    }
}