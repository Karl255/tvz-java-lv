package hr.java.vjezbe.glavna.controllers;

import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.glavna.VjezbeApplication;
import hr.java.vjezbe.glavna.fxutil.FXUtil;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PredmetiPretragaController {
	@FXML
	private TextField sifraUnos;
	@FXML
	private TextField nazivUnos;
	@FXML
	private TextField brojEctsBodovaUnos;
	@FXML
	private ChoiceBox<Profesor> nositeljUnos;
	@FXML
	private TextField brojStudenataUnos;

	@FXML
	private TableView<Predmet> predmetTableView;
	@FXML
	private TableColumn<Predmet, String> sifraColumn;
	@FXML
	private TableColumn<Predmet, String> nazivColumn;
	@FXML
	private TableColumn<Predmet, Integer> brojEctsBodovaColumn;
	@FXML
	private TableColumn<Predmet, String> nositeljColumn;
	@FXML
	private TableColumn<Predmet, Integer> brojStudenataColumn;

	private List<Predmet> sviPredmeti;

	private Predmet selectedPredmet;
	
	@FXML private ImageView imageView;
	private ArrayList<String> imagePaths = new ArrayList<>();
	private int shownImageIndex = -1;
	
	public void initialize() {
		var dataSource = VjezbeApplication.getDataSource();
		
		sifraColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSifra()));
		nazivColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNaziv()));
		brojEctsBodovaColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getBrojEctsBodova()).asObject());
		nositeljColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNositelj().toString()));
		brojStudenataColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getStudenti().size()).asObject());

		nositeljUnos.setItems(FXCollections.observableList(dataSource.readProfesori()));
		
		predmetTableView.getSelectionModel().selectedItemProperty().addListener(this::selectFromTable);
		loadTable();
	}
	
	private void loadTable() {
		sviPredmeti = VjezbeApplication.getDataSource().readPredmeti();
		predmetTableView.setItems(FXCollections.observableList(sviPredmeti));
	}

	public void pretrazi() {
		var filtrirano = sviPredmeti.stream()
			.filter(p -> p.getSifra().contains(sifraUnos.getText()))
			.filter(p -> p.getNaziv().contains(nazivUnos.getText()))
			.filter(p -> FXUtil.tryFilterByIntegerInput(p.getBrojEctsBodova(), brojEctsBodovaUnos.getText()))
			.filter(predmet -> predmet.getNositelj().equals(nositeljUnos.getValue()))
			.filter(p -> FXUtil.tryFilterByIntegerInput(p.getStudenti().size(), brojStudenataUnos.getText()))
			.toList();

		predmetTableView.setItems(FXCollections.observableList(filtrirano));
	}
	
	private void selectFromTable(Object obs, Predmet old, Predmet predmet) {
		selectedPredmet = predmet;
		
		sifraUnos.setText(predmet != null ? predmet.getSifra() : "");
		nazivUnos.setText(predmet != null ? predmet.getNaziv() : "");
		brojEctsBodovaUnos.setText(predmet != null ? predmet.getBrojEctsBodova() + "" : "");
		
		if (predmet != null) {
			nositeljUnos.setValue(predmet.getNositelj());
		} else {
			nositeljUnos.getSelectionModel().clearSelection();
		}
		
		brojStudenataUnos.setText(predmet != null ? predmet.getStudenti().size() + "" : "");
	}
	
	@FXML
	private void delete() {
		var alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm deletion", ButtonType.YES, ButtonType.CANCEL);
		alert.setTitle("Confirm deletion");
		
		var clicked = alert.showAndWait();
		
		if (clicked.isPresent()) {
			if (clicked.get().equals(ButtonType.YES)) {
				VjezbeApplication.getDataSource().deletePredmet(selectedPredmet);
			}
		}
	}
	
	@FXML
	private void update() {
		var alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm update", ButtonType.YES, ButtonType.CANCEL);
		alert.setTitle("Confirm update");

		var clicked = alert.showAndWait();

		if (clicked.isPresent()) {
			if (clicked.get().equals(ButtonType.YES)) {
				var updated = new Predmet(
					-1,
					sifraUnos.getText(),
					nazivUnos.getText(),
					Integer.parseInt(brojEctsBodovaUnos.getText()),
					nositeljUnos.getValue(),
					selectedPredmet.getStudenti()
				);
				
				VjezbeApplication.getDataSource().updatePredmet(selectedPredmet, updated);
				loadTable();
			}
		}
	}
	
	@FXML
	private void addImage() {
		var filePicker = new FileChooser();
		filePicker.setTitle("Add image");
		File selected = filePicker.showOpenDialog(VjezbeApplication.getAppStage());
		
		if (selected != null) {
			imagePaths.add(selected.getAbsolutePath());
			System.out.println("image added");
			shownImageIndex = imagePaths.size() - 1;
			showImage();
		}
	}
	
	@FXML
	private void previousImage() {
		shownImageIndex--;
		
		if (shownImageIndex < 0) {
			shownImageIndex = imagePaths.size() - 1;
		}
		
		showImage();
	}
	
	@FXML
	private void nextImage() {
		shownImageIndex++;
		
		if (shownImageIndex >= imagePaths.size()) {
			shownImageIndex = imagePaths.size() > 0 ? 0 : -1;
		}
		
		showImage();
	}
	
	private void showImage() {
		if (shownImageIndex >= 0) {
			try (var is = new FileInputStream(imagePaths.get(shownImageIndex))) {
				imageView.setImage(new Image(is));
				
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}
	}
}
