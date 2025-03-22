package com.learning.blogappapis.service.Impl;

import com.learning.blogappapis.service.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Override
    public String uploadFile(String path, MultipartFile multipartFile) throws IOException {
        //File Name
        String originalFilename = multipartFile.getOriginalFilename();

        //Generate Random FileName to differentiate
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String formattedDateTime = currentDateTime.format(formatter);

        //extracting the file extension ex: .jpg
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

        //Create final fileName combining date and Name
        String finalFileName = formattedDateTime.concat(fileExtension);


        // Define a path to save file
        Path destinationPath = Path.of(path, finalFileName);

        // Create parent directory if it doesn't exist
        File parentDirectory = destinationPath.getParent().toFile();
        if (!parentDirectory.exists()) {
            parentDirectory.mkdirs();  // Create the necessary directories
        }

        Files.copy(multipartFile.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

        //Files.copy(multipartFile.getInputStream(), Path.of(filePath));

        return finalFileName;
    }

    @Override
    public InputStream getResourceAsStream(String path,String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream is = new FileInputStream(fullPath);
        //dblogic to get the file from db path
        return is;
    }
}
