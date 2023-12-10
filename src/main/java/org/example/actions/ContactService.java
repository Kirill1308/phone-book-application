package org.example.actions;

public interface ContactService {
    void createContact(String name, String phoneNumber, String email);

    void editContact();

    void searchContact();

    void viewAllContacts();

    void deleteContact();

}
