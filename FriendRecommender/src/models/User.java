package models;

import java.io.*;
import java.util.*;

public class User implements Serializable {
    private String username;
    private String password;
    private Set<String> friends;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.friends = new HashSet<>();
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public Set<String> getFriends() {
        return friends;
    }

    public void addFriend(String friendUsername) {
        friends.add(friendUsername);
    }

    public void removeFriend(String friendUsername) {
        friends.remove(friendUsername);
    }


    public void saveToFile(String directory) throws IOException {
        String filename = directory + "/" + username + ".ser";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        }
    }

   
    public static User loadFromFile(String filepath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            return (User) ois.readObject();
        }
    }
}
