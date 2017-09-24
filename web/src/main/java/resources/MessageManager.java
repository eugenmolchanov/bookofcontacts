package resources;

import java.util.ResourceBundle;

/**
 * Created by Yauhen Malchanau on 14.09.2017.
 */
public class MessageManager {
    public static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");
    private MessageManager() {}
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
