package path.search.file_support;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Class to read and save properties files.
 *
 * @version 1.0 6/24/2019.
 */
public class PropertyHandler {
    public Properties read(String path) {
        Properties properties = new Properties();
        try(FileInputStream in = new FileInputStream(path)) {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public void write(Properties properties, String path){
        try(FileOutputStream out = new FileOutputStream(path)) {
            properties.store(out, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
