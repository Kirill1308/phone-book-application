package org.example.contact;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contact {
    private String name;
    private String phone;
    private String email;

    public Contact() {
    }

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public String toString() {
        return "name = '" + name + '\'' +
                ", phone = '" + phone + '\'' +
                ", email = '" + email + '\'';
    }
}
