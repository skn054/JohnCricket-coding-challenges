package org.example.utility;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * read json from file and convert it into string.
 */
public class ResourceUtils {

    private ResourceUtils() {

    }

    /***
     *
     *
     * @param resourcePath - the path to the file to read
     * @return the content of the file as string
     * @throws IOException if an I/O error occurs, such as the file not being found
     */
    public static String readFromResource(String resourcePath) throws IOException {
        // Get the resource as an InputStream from the ClassLoader.
        // This is the key part that finds the file in `src/main/resources`.

        InputStream inputStream = ResourceUtils.class.getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }
        // Use a try-with-resources block to ensure the stream is automatically closed.
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            // Read all lines from the reader and join them into a single string.
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
}
