package hr.java.vjezbe.glavna.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class PodatciController {
	@FXML
	private TextField filenameInput;
	
	@FXML
	private Label counterLabel;
	private static int deletedCounter = 0;
	
	public void initialize() {
		updateCounterLabel();
	}
	
	private void updateCounterLabel() {
		counterLabel.setText(Integer.toString(deletedCounter));
	}
	
	@FXML
	public void obrisi() {
		try {
			Files.delete(Path.of(filenameInput.getText()));
			deletedCounter++;
			updateCounterLabel();
		} catch (NoSuchFileException e) {
			var alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("File not found!");
			alert.setContentText(e.getMessage());
			alert.show();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
