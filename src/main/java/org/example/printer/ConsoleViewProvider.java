package org.example.printer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.contact.Contact;
import org.example.io.FileHandler;
import org.example.manager.ContactSearcher;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleViewProvider {
    private static final Logger logger = LogManager.getLogger(ConsoleViewProvider.class);
    private final Scanner scanner;

    public ConsoleViewProvider() {
        this.scanner = new Scanner(System.in);
    }

    public void printAvailableActions() {
        logger.info("Printing available actions for the user.");
        System.out.println("""
                Select an option:
                1. Create a new contact
                2. Edit a contact
                3. Search for a contact
                4. View all contacts
                5. Delete contact""");
    }

    public String askAction() {
        logger.info("Provider asks user to enter the action.");
        System.out.print("Enter: ");
        while (!scanner.hasNextInt()) {
            logger.warn("Invalid input received. User should enter a number.");
            System.out.println("That's not a valid option, Enter a number!");
            System.out.print("Enter: ");
            scanner.next();
        }

        String action = String.valueOf(scanner.nextInt());
        logger.info("Received action: " + action);

        return action;
    }

    public String askName() {
        logger.info("Asking for name.");
        System.out.print("Name: ");
        String name = scanner.next();
        while (name.length() < 3 || name.length() > 50) {
            logger.warn("Incorrect length of the name entered.");
            System.out.println("Name should be between 3 and 50 characters.");
            System.out.print("Name: ");
            name = scanner.next();
        }
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        logger.info("Received name: " + name);

        return name;
    }

    public String askPhone() {
        logger.info("Asking for phone number.");
        System.out.print("Phone: +38 ");
        String phone = scanner.next();
        while (!Pattern.matches("^\\d{10}$", phone)) {
            logger.warn("Invalid phone number entered.");
            System.out.println("Phone number should consist of 10 digits.");
            System.out.print("Phone: +38  ");
            logger.info("Asking for phone number.");
            phone = scanner.next();
        }
        logger.info("Received phone number: " + phone);

        return phone;
    }

    public String askEmail() {
        logger.info("Asking for email.");
        System.out.print("Email: ");
        String email = scanner.next();
        while (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email)) {
            logger.warn("Invalid email entered.");
            System.out.println("Enter valid email.");
            System.out.print("Email: ");
            logger.info("Asking for email.");
            email = scanner.next();
        }
        logger.info("Received email: " + email);

        return email;
    }

    public Contact askContactToEdit() {
        logger.info("Asking user to enter the name of the contact to be edited.");
        System.out.println("Enter the name of the contact you want to edit: ");

        return new Contact(askName(), "", "");
    }

    public String askField() {
        logger.info("Asking user which field they would like to edit.");
        System.out.print("Which field you want to edit in the contact? (name/phone/email): ");

        String field = scanner.next();
        while (!Pattern.matches("^(name|phone|email)$", field)) {
            logger.warn("Invalid field entered.");
            System.out.println("Invalid field specified. Please specify 'name', 'phone', or 'email'.");
            System.out.print("Which field you want to edit in the contact? (name/phone/email): ");
            field = scanner.next();
        }
        logger.info("Received field to edit: " + field);

        return field;
    }

    public boolean askUserToContinue() {
        logger.info("Asking user if they want to continue.");
        System.out.print("Do you want to continue? (y/n):");
        String userInput = getUserInput();

        return userInput.equals("y");
    }

    private String getUserInput() {
        String userInput = scanner.next().trim().toLowerCase();
        logger.info("User input received: " + userInput);
        return userInput;
    }

    public Contact askContactToDelete() {
        logger.info("Asking user to enter the name of the contact to be deleted.");
        System.out.println("Enter the name of the contact you want to delete: ");
        String contactName = askName();

        ContactSearcher searcher = new ContactSearcher(new FileHandler());
        if (!searcher.searchContactByName(contactName)) {
            logger.warn("No contact found with the given name.");
            System.out.println("No contact found with the given name.");
        }
        return new Contact(contactName, "", "");
    }
}