package clinic.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private static Properties props = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("src/main/java/clinic/config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getAdminUsername() {
        return props.getProperty("admin.username");
    }

    public static String getAdminPassword() {
        return props.getProperty("admin.password");
    }
}

