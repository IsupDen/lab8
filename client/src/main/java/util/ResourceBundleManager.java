package util;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleManager {

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles.locale", new Locale("en"));

    public static ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public static void setResourceBundle(ResourceBundle resourceBundle) {
        ResourceBundleManager.resourceBundle = resourceBundle;
    }
}
