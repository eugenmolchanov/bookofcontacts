package util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Yauhen Malchanau on 13.10.2017.
 */
public class Convertion {
    public static Map<String, String> convertResourceBundleToMap(ResourceBundle resource) {
        Map<String, String> map = new HashMap<>();

        Enumeration<String> keys = resource.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            map.put(key, resource.getString(key));
        }

        return map;
    }
}
