package com.scaler.folderfilenameextractor.service;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileService {

    // Read the root directory from the application.properties file
    @Value("${file.root.path}")
    private String rootDirectory;

    // Method to traverse directories and collect folder names and their files, excluding the root folder
    public Map<String, List<String>> listFilesInFolders() throws IOException {
        Map<String, List<String>> folderFileMap = new HashMap<>();

        // Get the root path and exclude it from processing
        Path rootPath = Paths.get(rootDirectory);

        // Traverse the directory tree
        Files.walk(rootPath)
                .filter(Files::isDirectory) // Filter to get only directories
                .filter(folder -> !folder.equals(rootPath)) // Exclude the root folder itself
                .forEach(folder -> {
                    try {
                        // Get all files in this folder
                        List<String> fileNames = new ArrayList<>();
                        try (DirectoryStream<Path> stream = Files.newDirectoryStream(folder)) {
                            for (Path path : stream) {
                                if (Files.isRegularFile(path)) {
                                    fileNames.add(path.getFileName().toString());
                                }
                            }
                        }
                        // Add the folder and its files to the map (using only the folder name)
                        folderFileMap.put(folder.getFileName().toString(), fileNames);
                    } catch (IOException e) {
                        e.printStackTrace(); // Handle exceptions
                    }
                });

        return folderFileMap;
    }
}
