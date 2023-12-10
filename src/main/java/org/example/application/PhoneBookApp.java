
package org.example.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.manager.ContactManager;
import org.example.printer.ConsoleViewProvider;
import org.example.runner.Runner;

public class PhoneBookApp {
    private static final Logger logger = LogManager.getLogger(PhoneBookApp.class);

    public static void main(String[] args) {
        logger.info("Program is started...");
        ConsoleViewProvider provider = new ConsoleViewProvider();
        ContactManager manager = new ContactManager(provider);

        Runner runner = new Runner(provider, manager);
        runner.run();
        logger.info("Program is ended.");
    }
}