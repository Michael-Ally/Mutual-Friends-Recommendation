package ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import models.User;
import models.DataManager;

public class DashboardWindow extends JFrame {
    private User user;
    private DataManager dataManager;

    private JPanel friendsListPanel;
    private JPanel suggestionsListPanel;

    public DashboardWindow(DataManager dataManager, User user) {
        this.user = user;
        this.dataManager = dataManager;

        setTitle("Welcome, " + user.getUsername());
        //setTitle("ACCOUNT HOLDER - " );
        setSize(600, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Friends Recommendation System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginWindow(dataManager);
        });
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(logoutBtn, BorderLayout.EAST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        
        friendsListPanel = new JPanel();
        friendsListPanel.setLayout(new BoxLayout(friendsListPanel, BoxLayout.Y_AXIS));
        JScrollPane friendsScroll = new JScrollPane(friendsListPanel);
        friendsScroll.setBorder(BorderFactory.createTitledBorder("Your Friends"));

        
        suggestionsListPanel = new JPanel();
        suggestionsListPanel.setLayout(new BoxLayout(suggestionsListPanel, BoxLayout.Y_AXIS));
        JScrollPane suggestionsScroll = new JScrollPane(suggestionsListPanel);
        suggestionsScroll.setBorder(BorderFactory.createTitledBorder("Suggestions"));

        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, friendsScroll, suggestionsScroll);
        splitPane.setResizeWeight(0.5);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        JTextField searchField = new JTextField();
        JButton searchBtn = new JButton("Add Friend");
        searchBtn.addActionListener(e -> {
            String target = searchField.getText().trim();
            if (!target.isEmpty()) {
                if (!target.equals(user.getUsername()) && dataManager.getUser(target) != null) {
                    if (!user.getFriends().contains(target)) {
                        user.addFriend(target);
                        refreshFriends();
                        refreshSuggestions();
                        searchField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, target + " is already your friend.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "User not found.");
                }
            }
        });
        searchPanel.add(new JLabel("Add new friend:"), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchBtn, BorderLayout.EAST);
        mainPanel.add(searchPanel, BorderLayout.SOUTH);

        add(mainPanel);

        refreshFriends();
        refreshSuggestions();

        setVisible(true);
    }

    private void refreshFriends() {
        friendsListPanel.removeAll();

        for (String friend : user.getFriends()) {
            JPanel item = createFriendItem(friend);
            friendsListPanel.add(item);
        }

        friendsListPanel.revalidate();
        friendsListPanel.repaint();
    }

    private JPanel createFriendItem(String friend) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35)); 

        JLabel nameLabel = new JLabel(friend);
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JButton blockBtn = new JButton("Block");
        blockBtn.setPreferredSize(new Dimension(70, 25));
        blockBtn.setBackground(Color.RED);
        blockBtn.setForeground(Color.BLUE);
        blockBtn.setFocusPainted(false);

        blockBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Block " + friend + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                user.removeFriend(friend);
                refreshFriends();
                refreshSuggestions();
            }
        });

        panel.add(nameLabel, BorderLayout.CENTER);
        panel.add(blockBtn, BorderLayout.EAST);


        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        return panel;
    }

    private void refreshSuggestions() {
        suggestionsListPanel.removeAll();

        List<String> suggestions = dataManager.suggestFriends(user);
        for (String suggestion : suggestions) {
            JPanel item = createSuggestionItem(suggestion);
            suggestionsListPanel.add(item);
        }

        suggestionsListPanel.revalidate();
        suggestionsListPanel.repaint();
    }

    private JPanel createSuggestionItem(String name) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));  

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JButton addBtn = new JButton("Add Friend");
        addBtn.setPreferredSize(new Dimension(100, 25));
        addBtn.setBackground(new Color(135, 206, 235));
        addBtn.setForeground(Color.BLUE);
        addBtn.setFocusPainted(false);

        addBtn.addActionListener(e -> {
            if (!user.getFriends().contains(name)) {
                user.addFriend(name);
                refreshFriends();
                refreshSuggestions();
                JOptionPane.showMessageDialog(this, name + " added to your friends list.");
            }
        });

        panel.add(nameLabel, BorderLayout.CENTER);
        panel.add(addBtn, BorderLayout.EAST);

        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        return panel;
    }
}
