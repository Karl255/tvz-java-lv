package hr.java.vjezbe.glavna.controllers;

import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.glavna.VjezbeApplication;
import hr.java.vjezbe.glavna.fxutil.FXUtil;
import hr.java.vjezbe.iznimke.DataSourceException;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class PredmetiPretragaController {
	@FXML
	private TextField sifraUnos;
	@FXML
	private TextField nazivUnos;
	@FXML
	private TextField brojEctsBodovaUnos;
	@FXML
	private TextField nositeljUnos;
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
	private AtomicBoolean processRunning = new AtomicBoolean(false);

	public void initialize() {
		sifraColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSifra()));
		nazivColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNaziv()));
		brojEctsBodovaColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getBrojEctsBodova()).asObject());
		nositeljColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNositelj().toString()));
		brojStudenataColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getStudenti().size()).asObject());

		try {
			sviPredmeti = VjezbeApplication.getDataSource().readPredmeti();
		} catch (DataSourceException e) {
			FXUtil.showDataSourceErrorAlert(e.getMessage());
		}

		new Thread(() -> loadItemsSlowly(sviPredmeti)).start();
	}

	public void pretrazi() {
		if (processRunning.get()) {
			showProcessRunningAlert();
			return;
		}
		
		var filtrirano = sviPredmeti.stream()
			.filter(p -> p.getSifra().contains(sifraUnos.getText()))
			.filter(p -> p.getNaziv().contains(nazivUnos.getText()))
			.filter(p -> FXUtil.tryFilterByIntegerInput(p.getBrojEctsBodova(), brojEctsBodovaUnos.getText()))
			.filter(predmet -> predmet.getNositelj().toString().contains(nositeljUnos.getText()))
			.filter(p -> FXUtil.tryFilterByIntegerInput(p.getStudenti().size(), brojStudenataUnos.getText()))
			.toList();

		new Thread(() -> loadItemsSlowly(filtrirano)).start();
	}
	
	private void loadItemsSlowly(List<Predmet> predmeti) {
		processRunning.set(true);
		var rng = new Random();
		int initialWait = rng.nextInt(10, 20) * 1000;
		System.out.printf("initial wait: %dms%n", initialWait);

		try {
			Thread.sleep(initialWait);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		var itemsList = FXCollections.observableList(new ArrayList<Predmet>());
		predmetTableView.setItems(itemsList);
		
		for (int i = 0; i < predmeti.size(); i++) {
			int waitPerItems = rng.nextInt(1, 10) * 1000;
			System.out.printf("additional wait: %dms%n", waitPerItems);

			try {
				Thread.sleep(waitPerItems);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			
			itemsList.add(predmeti.get(i));
		}
		processRunning.set(false);
		Platform.runLater(this::showProcessCompleteAlert);
	}
	
	@FXML
	private void izbrisi() {
		if (processRunning.get()) {
			showProcessRunningAlert();
			return;
		}
		
		new Thread(() -> {
			processRunning.set(true);
			
			var rng = new Random();
			var items = predmetTableView.getItems();
			
			while (items.size() > 0) {
				int waitPerItems = rng.nextInt(1, 10) * 1000;
				System.out.printf("additional wait: %dms%n", waitPerItems);

				try {
					Thread.sleep(waitPerItems);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				
				items.remove(0);
			}
			
			processRunning.set(false);
			Platform.runLater(this::showProcessCompleteAlert);
		}).start();
	}

	private static void showProcessRunningAlert() {
		var alert = new Alert(Alert.AlertType.ERROR, "Another process is already running");
		alert.show();
	}

	private void showProcessCompleteAlert() {
		var alert = new Alert(Alert.AlertType.INFORMATION, "Process finished!");
		alert.show();
	}
}
