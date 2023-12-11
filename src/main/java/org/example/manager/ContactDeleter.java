package org.example.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.contact.Contact;
import org.example.io.FileHandler;
import org.example.printer.ConsoleViewProvider;

import java.io.File;

public class ContactDeleter {
    private static final Logger logger = LogManager.getLogger(ContactDeleter.class);
    private final FileHandler fileHandler;
    private final ConsoleViewProvider provider;

    public ContactDeleter(FileHandler fileHandler, ConsoleViewProvider provider) {
        this.fileHandler = fileHandler;
        this.provider = provider;
    }

    public void delete() {
        logger.info("Deleting contact");

        File[] listOfFiles = fileHandler.getFilesFromDirectory();
        if (listOfFiles == null) {
            return;
        }
        Contact contactToDelete = provider.askContactToDelete();
        deleteContactInFiles(listOfFiles, contactToDelete);
    }

    private void deleteContactInFiles(File[] files, Contact contactToDelete) {
        logger.info("Deleting contact in files: {}", contactToDelete);

        for (File file : files) {
            if (file.isFile()) {
                Contact contact = fileHandler.readContactFromFile(file);
                if (contact != null && contact.getName().equals(contactToDelete.getName())) {
                    executeFileDeletion(file, contact);
                    return;
                } else {
                    logger.warn("Contact is null in deleteContactInFiles.");
                }
            }
        }
    }

    private void executeFileDeletion(File file, Contact contactToDelete) {
        if (file.delete()) {
            logger.info("Contact '{}' has been successfully deleted", contactToDelete.getName());
            System.out.println(contactToDelete.getName() + " has been successfully deleted");
        } else {
            logger.error("Failed to delete contact '{}'", contactToDelete.getName());
            System.out.println("Failed to delete contact " + contactToDelete.getName());
        }
    }

}
