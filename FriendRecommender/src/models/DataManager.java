package models;

import java.io.*;
import java.util.*;

public class DataManager {
    private Map<String, User> users;
    private String dataDirectory;

    public DataManager(String dataDirectory) {
        this.dataDirectory = dataDirectory;
        users = new HashMap<>();
        loadAllUsers();
    }

    private void loadAllUsers() {
        File dir = new File(dataDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
            return;
        }
        for (File file : Objects.requireNonNull(dir.listFiles((d, name) -> name.endsWith(".ser")))) {
            try {
                User user = User.loadFromFile(file.getAbsolutePath());
                users.put(user.getUsername(), user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean registerUser(String username, String password) {
        if (users.containsKey(username)) {
            return false; 
        }
        User newUser = new User(username, password);
        users.put(username, newUser);
        saveUser(newUser);
        return true;
    }

    public Optional<User> loginUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.checkPassword(password)) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public void saveUser(User user) {
        try {
            user.saveToFile(dataDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public void saveAll() {
        for (User user : users.values()) {
            saveUser(user);
        }
    }

    
    public List<String> suggestFriends(User user) {
        List<String> suggestions = new ArrayList<>();
        for (String username : users.keySet()) {
            if (!username.equals(user.getUsername()) && !user.getFriends().contains(username)) {
                suggestions.add(username);
            }
        }
        Collections.shuffle(suggestions);
        return suggestions.subList(0, Math.min(5, suggestions.size()));
    }
}
