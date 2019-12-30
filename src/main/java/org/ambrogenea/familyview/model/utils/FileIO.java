package org.ambrogenea.familyview.model.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class FileIO {

    public static ArrayList<String> FileToLines(String path) throws IOException {
        InputStream is = new FileInputStream(path);
        BufferedReader buf = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String line = buf.readLine();
        ArrayList<String> lines = new ArrayList();

        while (line != null) {
            lines.add(line);
            line = buf.readLine();
        }

        return lines;
    }

    public static File loadFileFromResources(String fileName) {
        String filePath;
        URL fileURL = FileIO.class.getResource(fileName);

        if (fileURL != null) {
            filePath = fileURL.getPath();
            System.out.println("Image file path: " + filePath);
            return new File(filePath);
        }
        System.out.println("File " + fileName + " cannot be open or does not exist in resources.");
        return null;
    }
}
