package org.example.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.contact.Contact;
import org.example.io.FileHandler;

public class ContactCreator {
    private static final Logger logger = LogManager.getLogger(ContactCreator.class);

    private final FileHandler fileHandler;

    public ContactCreator(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public void createContactAndWriteToFile(String name, String phone, String email) {
        logger.info("Creating contact: {}, {}, {}", name, phone, email);

        Contact contact = new Contact(name, phone, email);
        fileHandler.writeFile(contact);
        System.out.println(name + " was successfully created.");
    }
}
