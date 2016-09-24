package com.adm.web.helpers;

import com.adm.web.controllers.SessionController;
import java.io.File;
import org.primefaces.model.UploadedFile;

import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 * Created by Albert Lovers on 21/09/16.
 */
@ManagedBean(name = "fileUploadHelper", eager = true)
@ViewScoped
public class FileUploadHelper implements Serializable {

	private UploadedFile file;

	// Kijkt of de directories bestaan. Stelt het anders maakt het aan
	@PostConstruct
	public void init() {

		File dir = new File(SessionController.getBasePath() + "JEE/artikel/");
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void upload() {
		if (file != null) {
			FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);

			try {
				Files.write(Paths.get(String.format("%snewFile.%s", SessionController.getBasePath(), file.getContentType().split("/")[1])), file.getContents(), StandardOpenOption.CREATE_NEW);
			} catch (IOException ex) {
				Logger.getLogger(FileUploadHelper.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public static void hernoemEnVerplaatsAfbeelding(long id, String locatie) {
		File file = new File(SessionController.getBasePath());
		String[] rightFile = null;
		for (String temp : file.list()) {
			rightFile = temp.split("\\.");
			if (rightFile[0].equals("newFile")) {
				break;
			}
		}

		try {
			Files.move(Paths.get(String.format("%s%s.%s", SessionController.getBasePath(), rightFile[0], rightFile[1])), Paths.get(String.format("%sJEE/%s/%s.%s", SessionController.getBasePath(), locatie, id, rightFile[1])), REPLACE_EXISTING);
		} catch (IOException ex) {
			Logger.getLogger(FileUploadHelper.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
