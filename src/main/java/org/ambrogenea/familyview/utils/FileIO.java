package org.ambrogenea.familyview.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class FileIO {

    public static ArrayList<String> FileToLines(String path) throws IOException {
        InputStream is = new FileInputStream(path);
        BufferedReader buf = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String line = buf.readLine();
        ArrayList<String> lines = new ArrayList<>();

        while (line != null) {
            lines.add(line);
            line = buf.readLine();
        }

        return lines;
    }

    public static void saveFile(String content, String filePath) throws IOException {
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(content);
        }
    }

    public static void saveStreamToFile(InputStream initialStream, String filePath) throws IOException {
        byte[] buffer = new byte[initialStream.available()];
        initialStream.read(buffer);

        File targetFile = new File(filePath);
        OutputStream outStream = new FileOutputStream(targetFile);
        outStream.write(buffer);
    }

    public static File loadFileFromResources(String fileName) {
        String filePath;
        URL fileURL = ClassLoader.getSystemResource(fileName);

        if (fileURL != null) {
            filePath = fileURL.getPath();
            return new File(filePath);
        }
        System.out.println("File " + fileName + " cannot be open or does not exist in resources.");
        return null;
    }

    public static Properties loadProperties(String absolutePath) {
        Properties propertyFile = new Properties();

        try (FileInputStream fis = new FileInputStream(absolutePath)) {
            propertyFile.load(fis);
        } catch (IOException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return propertyFile;
    }
}
