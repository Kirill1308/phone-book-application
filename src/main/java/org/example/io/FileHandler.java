package org.example.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.contact.Contact;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    private static final Logger logger = LogManager.getLogger(FileHandler.class);
    private static final String DIRECTORY_PATH = "src/main/resources/contacts";
    private static final String FILE_PATH = "src/main/resources/contacts/";
    private final Gson gson;

    public FileHandler() {
        this.gson = new Gson();
    }

    public void writeFile(Contact contact) {
        logger.info("Started writing to file for contact: {}", contact.getName());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(contact);

        try (FileWriter writer = new FileWriter(FILE_PATH + contact.getName() + ".json")) {
            writer.write(json);
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            logger.error("Error writing file: {}", e.getMessage());
        }
    }

    public File[] getFilesFromDirectory() {
        logger.info("Getting files from directory: {}", DIRECTORY_PATH);

        File folder = new File(DIRECTORY_PATH);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null) {
            System.out.println("No contacts found in the directory.");
        }
        return listOfFiles;
    }

    public Contact readContactFromFile(File file) {
        logger.info("Reading contact from file: {}", file.getName());

        try (FileReader fileReader = new FileReader(file)) {
            return gson.fromJson(fileReader, Contact.class);
        } catch (JsonSyntaxException e) {
            logger.error("Error parsing JSON while trying to read the contact. Detail: {}", e.getMessage(), e);
        } catch (IOException e) {
            logger.error("An error occurred while trying to read the contact. Detail: {}", e.getMessage(), e);
        }

        return null;
    }

    public void displayContactNames(File[] files) {
        logger.info("Displaying contact names.");

        for (File file : files) {
            if (file.isFile()) {
                logger.info("File name: {}", file.getName());
                Contact contact = readContactFromFile(file);
                System.out.println(contact.getName());
            }
        }
    }
}