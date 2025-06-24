# Mutual Friend Recommender 💬🤝

**Mutual Friend Recommender** is a Java-based desktop web application that helps users discover **mutual friends** intelligently while supporting **multiple user accounts** and **basic security**.

## 🔍 Features

- 🔐 Secure login system
- 👥 Multiple user accounts
- 🤖 Intelligent mutual friend recommendations
- 💾 Data serialization for user data storage
- 🖥️ Clean Java Swing-based GUI

## 🛠️ Built With

- Java (OOP, Serialization)
- Java Swing (for UI)
- MVC Design Pattern

## 📁 Project Structure

```bash
src/
├── Main.java
├── logic/
│   └── FriendManager.java
├── models/
│   ├── DataManager.java
│   └── User.java
└── ui/
    ├── DashboardWindow.java
    └── LoginWindow.java
userdata/
├── <username>.ser (serialized user data)
