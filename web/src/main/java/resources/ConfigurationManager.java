package resources;

import java.util.ResourceBundle;

/**
 * Created by Yauhen Malchanau on 16.03.2017.
 */
public class ConfigurationManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
    private ConfigurationManager() {}
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
