package com.scaler.folderfilenameextractor.controller;

import com.scaler.folderfilenameextractor.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    // Endpoint to get folder names and the files inside them using a POST request
    @GetMapping("/list")
    public Map<String, List<String>> getFolderFiles() throws IOException {
        return fileService.listFilesInFolders();
    }
}


