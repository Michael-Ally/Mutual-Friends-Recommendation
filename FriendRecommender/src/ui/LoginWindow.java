package ui;

import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;
import java.util.Optional;
import models.DataManager;
import models.User;

public class LoginWindow extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private final DataManager dataManager;
    private Image backgroundImage;

    public LoginWindow(DataManager dataManager) {
        this.dataManager = dataManager;

        setTitle("Login - Flash");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        backgroundImage = new ImageIcon(System.getProperty("user.home") + "/Desktop/FriendRecommender copy/src/image/flashbg.jpeg").getImage();


        

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());

        JPanel loginPanel = new RoundedPanel(30);
        loginPanel.setLayout(new GridLayout(7, 1, 10, 10));
        loginPanel.setBackground(new Color(255, 255, 255, 230));
        loginPanel.setPreferredSize(new Dimension(320, 320));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Account Details", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.DARK_GRAY);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton loginButton = new JButton("Log In");
        loginButton.setBackground(new Color(66, 133, 244));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton registerButton = new JButton("Register");
        registerButton.setForeground(new Color(66, 133, 244));
        registerButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        registerButton.setFocusPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setBorderPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> handleRegister());

        loginPanel.add(titleLabel);
        loginPanel.add(new JLabel("Username"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password"));
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(registerButton);

        backgroundPanel.add(loginPanel);
        add(backgroundPanel);
        setVisible(true);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        Optional<User> user = dataManager.loginUser(username, password);
        if (user.isPresent()) {
            dispose();
            new DashboardWindow(dataManager, user.get());
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials");
        }
    }

    private void handleRegister() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        boolean success = dataManager.registerUser(username, password);
        if (success) {
            JOptionPane.showMessageDialog(this, "Registered! Please log in.");
        } else {
            JOptionPane.showMessageDialog(this, "Username already exists!");
        }
    }

    
    class RoundedPanel extends JPanel {
        private final int cornerRadius;

        public RoundedPanel(int radius) {
            super();
            this.cornerRadius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        }
    }
}
