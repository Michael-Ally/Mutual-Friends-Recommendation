import models.DataManager;
import ui.LoginWindow;

public class FriendManager {
    public static void main(String[] args) {
        String dataDir = "userdata";
        DataManager dataManager = new DataManager(dataDir);
        new LoginWindow(dataManager);
        
    
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            dataManager.saveAll();
            System.out.println("All user data saved. Exiting...");
        }));
    }
}
