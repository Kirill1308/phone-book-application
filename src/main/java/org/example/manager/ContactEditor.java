package org.example.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.contact.Contact;
import org.example.io.FileHandler;
import org.example.printer.ConsoleViewProvider;

import java.io.File;
import java.io.IOException;

public class ContactEditor {
    private static final Logger logger = LogManager.getLogger(ContactEditor.class);
    private final FileHandler fileHandler;
    private final ConsoleViewProvider provider;
    private final ContactManager manager;
    private final ObjectMapper objectMapper;

    public ContactEditor(FileHandler fileHandler, ConsoleViewProvider provider, ContactManager manager) {
        this.fileHandler = fileHandler;
        this.provider = provider;
        this.manager = manager;
        this.objectMapper = new ObjectMapper();
    }

    public void edit() {
        logger.info("Editing contact");
        File[] listOfFiles = fileHandler.getFilesFromDirectory();
        if (listOfFiles == null) {
            return;
        }

        Contact contact = provider.askContactToEdit();
        ContactSearcher searcher = new ContactSearcher(fileHandler);
        if (!searcher.searchContactByName(contact.getName())) {
            System.out.println("Contact with the specified name does not exist.");
            return;
        }
        String fieldToEdit = provider.askField();
        editContactInFiles(listOfFiles, contact, fieldToEdit);
    }

    private void editContactInFiles(File[] files, Contact contactToEdit, String fieldToEdit) {
        logger.info("Editing contact in files with detail {}: {}", fieldToEdit, contactToEdit);
        for (File file : files) {
            if (file.isFile()) {
                try {
                    Contact contact = objectMapper.readValue(file, Contact.class);
                    switch (fieldToEdit) {
                        case "name" -> editContactName(file, contact, contactToEdit);
                        case "phone" -> editContactPhone(contact);
                        case "email" -> editContactEmail(contact);
                    }

                    saveEditedContactToFile(file, contact, fieldToEdit);
                } catch (IOException e) {
                    logger.error("An error occurred while editing the contact. Detail: {}", e.getMessage(), e);
                    System.out.println("An error occurred while editing the contact: " + e.getMessage());
                }
            }
        }
    }

    private void editContactName(File file, Contact contact, Contact contactToEdit) throws IOException {
        if (contact.getName().equals(contactToEdit.getName())) {
            executeFileDeletion(file, contact, contactToEdit);
        }
    }

    private void editContactPhone(Contact contact) {
        contact.setPhone(provider.askPhone());
    }

    private void editContactEmail(Contact contact) {
        contact.setEmail(provider.askEmail());
    }

    private void saveEditedContactToFile(File file, Contact contact, String fieldToEdit) throws IOException {
        if (!fieldToEdit.equals("name")) {
            objectMapper.writeValue(file, contact);
        }
    }

    public void executeFileDeletion(File file, Contact contact, Contact contactToEdit) {
        logger.info("Executing file deletion: {}", contact);
        if (contact.getName().equals(contactToEdit.getName()) && file.delete()) {
            System.out.println("Enter the new name.");
            String newName = provider.askName();

            logger.info("Executing file creation with the same params except name: {}", newName);
            manager.createContact(newName, contact.getPhone(), contact.getEmail());
        } else {
            System.out.println("Unable to delete the contact.");
        }
    }
}