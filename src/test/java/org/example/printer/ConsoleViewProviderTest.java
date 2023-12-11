package org.example.printer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleViewProviderTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final ConsoleViewProvider provider = Mockito.spy(new ConsoleViewProvider());

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));

    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void testPrintAvailableActions() {
        String expectedOutput = """
                Select an option:
                1. Create a new contact
                2. Edit a contact
                3. Search for a contact
                4. View all contacts
                5. Delete contact""";
        provider.printAvailableActions();
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    void testAskName() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        String input = "John\n";
        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());

        System.setIn(inContent);
        System.setOut(new PrintStream(outContent));

        ConsoleViewProvider provider = new ConsoleViewProvider();
        String resultName = provider.askName();

        System.setOut(System.out);

        assertEquals("John", resultName);

        String expectedOutput = "Name: ";
        assertEquals(expectedOutput, outContent.toString());
    }
}