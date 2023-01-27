package hr.java.vjezbe.glavna.controllers;

import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.glavna.VjezbeApplication;
import hr.java.vjezbe.glavna.fxutil.FXUtil;
import hr.java.vjezbe.iznimke.DataSourceException;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Objects;

public class ProfesoriPretragaController {
	@FXML
	private TextField sifraUnos;
	@FXML
	private TextField imeUnos;
	@FXML
	private TextField prezimeUnos;
	@FXML
	private TextField titulaUnos;

	@FXML
	private TableView<Profesor> profesorTableView;
	@FXML
	private TableColumn<Profesor, String> sifraColumn;
	@FXML
	private TableColumn<Profesor, String> imeColumn;
	@FXML
	private TableColumn<Profesor, String> prezimeColumn;
	@FXML
	private TableColumn<Profesor, String> titulaColumn;

	@FXML private Button izbrisiButton;
	@FXML private Button izmjeniButton;
	
	private List<Profesor> sviProfesori;

	public void initialize() {
		sifraColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSifra()));
		imeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIme()));
		prezimeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPrezime()));
		titulaColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitula()));

		profesorTableView.getSelectionModel().selectedItemProperty().addListener(this::onSelect);
		
		loadData();
	}
	
	private void onSelect(Observable obs, Profesor old, Profesor profesor) {
		if (profesor != null) {
			izbrisiButton.setDisable(false);
			izmjeniButton.setDisable(false);
			
			sifraUnos.setText(profesor.getSifra());
			imeUnos.setText(profesor.getIme());
			prezimeUnos.setText(profesor.getPrezime());
			titulaUnos.setText(profesor.getTitula());
		}
	}
	
	private void loadData() {
		try {
			sviProfesori = VjezbeApplication.getDataSource().readProfesori();
		} catch (DataSourceException e) {
			FXUtil.showDataSourceErrorAlert(e.getMessage());
		}
		
		profesorTableView.setItems(FXCollections.observableList(sviProfesori));
	}

	@FXML
	private void pretrazi() {
		var filtrirano = sviProfesori.stream()
			.filter(p -> p.getSifra().contains(sifraUnos.getText()))
			.filter(p -> p.getIme().contains(imeUnos.getText()))
			.filter(p -> p.getPrezime().contains(prezimeUnos.getText()))
			.filter(p -> p.getTitula().contains(titulaUnos.getText()))
			.toList();

		profesorTableView.setItems(FXCollections.observableList(filtrirano));
	}
	
	@FXML
	private void izbrisi() {
		try {
			var selected = profesorTableView.getSelectionModel().getSelectedItem();
			VjezbeApplication.getDataSource().deleteProfesor(selected.getId());
			loadData();
			
			sifraUnos.clear();
			imeUnos.clear();
			prezimeUnos.clear();
			titulaUnos.clear();
		} catch (DataSourceException e) {
			FXUtil.showDataSourceErrorAlert("Ovaj profesor je nostielj nekog predmeta te se zbog toga ne može izbrisati iz baze!");
		}
	}
	
	@FXML
	private void izmjeni() {
		try {
			var selected = profesorTableView.getSelectionModel().getSelectedItem();
			
			var novi = new Profesor(
				selected.getId(),
				sifraUnos.getText(),
				imeUnos.getText(),
				prezimeUnos.getText(),
				titulaUnos.getText()
			);
			
			VjezbeApplication.getDataSource().updateProfesor(novi);
			loadData();
			profesorTableView.getSelectionModel().select(novi);
		} catch (DataSourceException e) {
			FXUtil.showDataSourceErrorAlert(e.getMessage());
		}
	}
}
