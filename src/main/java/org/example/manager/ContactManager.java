package org.example.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.actions.ContactService;
import org.example.io.FileHandler;
import org.example.printer.ConsoleViewProvider;

public class ContactManager implements ContactService {
    private static final Logger logger = LogManager.getLogger(ContactManager.class);
    private final FileHandler fileHandler = new FileHandler();
    private final ConsoleViewProvider provider;

    public ContactManager(ConsoleViewProvider provider) {
        this.provider = provider;
    }

    public void processUserAction(String action) {
        logger.info("Processing user action: {}", action);
        try {
            switch (Integer.parseInt(action)) {
                case 1 -> createContact(provider.askName(), provider.askPhone(), provider.askEmail());
                case 2 -> editContact();
                case 3 -> searchContact();
                case 4 -> viewAllContacts();
                case 5 -> deleteContact();
                default -> System.out.println(action + " is not provided.");
            }
        } catch (NumberFormatException e) {
            logger.error("Invalid input: {}. Please enter a valid integer.", action, e);
            System.out.println("Invalid input. Please enter a valid integer.");
        }
    }

    @Override
    public void createContact(String name, String phone, String email) {
        ContactCreator creator = new ContactCreator(fileHandler);
        creator.createContactAndWriteToFile(name, phone, email);
    }

    @Override
    public void editContact() {
        ContactEditor editor = new ContactEditor(fileHandler, provider, this);
        editor.edit();
    }

    @Override
    public void searchContact() {
        ContactSearcher searcher = new ContactSearcher(fileHandler);
        String contactNameToFind = provider.askName();
        searcher.searchContactByName(contactNameToFind);
    }

    @Override
    public void viewAllContacts() {
        ContactsViewer viewer = new ContactsViewer(fileHandler);
        viewer.viewAll();
    }

    @Override
    public void deleteContact() {
        ContactDeleter deleter = new ContactDeleter(fileHandler, provider);
        deleter.delete();
    }
}