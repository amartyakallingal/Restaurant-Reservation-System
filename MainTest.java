

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void tests() throws Exception {
        String inputFilePath = "tests/inputs/updated_test_cases.txt";
        String expectedOutputPath = "tests/outputs/updated_test_resultzs_AFTER_CODING.txt";

        String input = new String(Files.readAllBytes(Paths.get(inputFilePath)));
        String expectedOutput = new String(Files.readAllBytes(Paths.get(expectedOutputPath)));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);


        Main.main(new String[] {inputFilePath});



        System.setOut(originalOut);

        String actualOut = outputStream.toString().trim();

        assertEquals(expectedOutput.trim(), actualOut);
    }
}