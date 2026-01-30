package clinic.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader()
                .getResourceAsStream("src/main/config.properties")) {
            if (input != null) {
                props.load(input);
            } else {
                System.out.println("config.properties not found, using defaults");
                props.setProperty("admin.username", "admin");
                props.setProperty("admin.password", "secret123");
            }
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



