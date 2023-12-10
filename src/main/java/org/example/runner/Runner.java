package org.example.runner;

import org.example.manager.ContactManager;
import org.example.printer.ConsoleViewProvider;

public class Runner {
    private final ConsoleViewProvider provider;
    private final ContactManager manager;

    public Runner(ConsoleViewProvider provider, ContactManager manager) {
        this.manager = manager;
        this.provider = provider;
    }

    public void run() {
        do {
            provider.printAvailableActions();
            String action = provider.askAction();
            manager.processUserAction(action);

            System.out.println();
        } while (provider.askUserToContinue());
    }
}
