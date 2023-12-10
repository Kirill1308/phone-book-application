package org.example.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.contact.Contact;
import org.example.io.FileHandler;

import java.io.File;

public class ContactSearcher {
    private static final Logger logger = LogManager.getLogger(ContactSearcher.class);
    private final FileHandler fileHandler;

    public ContactSearcher(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public boolean searchContactByName(String name) {
        logger.info("Searching contact by name: {}", name);
        File[] contactFiles = fileHandler.getFilesFromDirectory();
        if (contactFiles == null) {
            return false;
        }

        for (File file : contactFiles) {
            if (file.isFile()) {
                Contact contact = fileHandler.readContactFromFile(file);
                if (contact != null && contact.getName().equals(name)) {
                    logger.info("Contact is found: {}", contact.getName());
                    System.out.println(contact);
                    return true;
                }
            }
        }
        logger.info("Contact is not found: {}", name);
        return false;
    }
}
