package resources;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Евгений Молчанов on 16.03.2017.
 */
public class MessageManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", Locale.getDefault());
    private MessageManager() {}
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
