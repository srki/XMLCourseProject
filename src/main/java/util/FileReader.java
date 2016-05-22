package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author - Srđan Milaković
 */
public class FileReader {
    private String readFile(String path) {
        URL url = FileReader.class.getClassLoader().getResource(path);

        if (url == null) {
            return null;
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            StringBuilder result = new StringBuilder("");
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                result.append(line).append("\n");
            }
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
