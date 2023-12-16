package org.example.servlet;

import org.example.io.FileHandler;
import org.example.manager.ContactCreator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ContactCreatorServlet")
public class ContactCreatorServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        FileHandler fileHandler = new FileHandler();

        ContactCreator contactCreator = new ContactCreator(fileHandler);
        contactCreator.createContactAndWriteToFile(name, phone, email);

        response.sendRedirect("/");
    }
}


