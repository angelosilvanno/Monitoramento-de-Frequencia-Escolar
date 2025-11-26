package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvReader {

    private static Properties props = new Properties();
    private static boolean loaded = false;

    public static String get(String key) {
        if (!loaded) {
            try (FileInputStream fis = new FileInputStream(".env")) {
                props.load(fis);
                loaded = true;
            } catch (IOException e) {
                System.out.println("Arquivo .env não encontrado!");
            }
        }
        return props.getProperty(key);
    }
}
