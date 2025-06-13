package fr.mdbs.thetreasuremap;

import fr.mdbs.thetreasuremap.application.service.game.GameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.stream.Collectors;

@SpringBootTest
public class TheTreasureMapApplicationTests {

    @Autowired
    private GameService gameService;

    @Test
    public void should_readFile() {
        // Given
        String resourcePath = "/input/input.txt";

        // When
        InputStream inputStream = getClass().getResourceAsStream(resourcePath);

        // Then
        Assertions.assertNotNull(inputStream);
        try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
            String content = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            Assertions.assertNotNull(content);
            Assertions.assertFalse(content.isEmpty());
        }
    }

    @Test
    public void should_processMainScenario() throws IOException {
        // Given
        String expected, actual;
        String fileName = "input.txt";
        String inputResourcePath = "/input/" + fileName;
        String outputResourcePath = "/output/output.txt";
        String contentType = "multipart/form-data";
        MockMultipartFile inputFile = new MockMultipartFile(
                fileName,
                fileName,
                contentType,
                getClass().getResourceAsStream(inputResourcePath)
        );

        // When
        ByteArrayResource outputFile = gameService.process(inputFile);

        // Then
        try (InputStream is = getClass().getResourceAsStream(outputResourcePath)) {
            Assertions.assertNotNull(is);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                expected = reader.lines().collect(Collectors.joining("\n"));
            }
        }
        actual = new String(outputFile.getByteArray(), StandardCharsets.UTF_8);

        Assertions.assertEquals(expected, actual);
    }

}
