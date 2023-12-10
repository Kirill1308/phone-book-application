package org.example.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.io.FileHandler;

import java.io.File;

public class ContactsViewer {
    private static final Logger logger = LogManager.getLogger(ContactsViewer.class);
    private final FileHandler fileHandler;

    public ContactsViewer(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public void viewAll() {
        logger.info("Viewing all contacts");
        File[] listOfFiles = fileHandler.getFilesFromDirectory();
        if (listOfFiles == null) {
            return;
        }
        fileHandler.displayContactNames(listOfFiles);
    }
}
