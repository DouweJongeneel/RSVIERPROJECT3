package com.adm.web.helpers;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Created by douwejongeneel on 15/09/16.
 */

@ManagedBean
@ViewScoped
public class FileUploadHelper {

    public void handleUpload(FileUploadEvent event) {
        UploadedFile uploadedFile = event.getFile();
        Path folder = Paths.get("/tmp/Harrie/ArtikelAfbeelding");
        String fileName = uploadedFile.getFileName();
        String contentType = uploadedFile.getContentType();

        try (InputStream input = uploadedFile.getInputstream()){

        Path file = Files.createTempFile(folder, fileName, "." + contentType);

        // Save File to directory
        Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Uploaded file succesfully saved in  " + file);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            // TODO: Handle IOException
        }

    }
}
