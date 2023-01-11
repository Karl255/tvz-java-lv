package hr.java.vjezbe.glavna.controllers;

import hr.java.vjezbe.data.Data;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ProlaznostController {
	@FXML
	private BarChart<String, Double> chart;
	
	public void initialize() {
		XYChart.Series<String, Double> data = new XYChart.Series<>();
		
		for (var prolaznost : izracunajProlaznost().entrySet()) {
			data.getData().add(new XYChart.Data<>(prolaznost.))
		}
		
		//chart.getData().add()
	}
	
	public Map<String, Double> izracunajProlaznost() {
		var ispiti = Data.getIspiti();
		HashMap<String, Integer> brojIspita = new HashMap<>(); 
		HashMap<String, Double> prolaznosti = new HashMap<>();
		
		for (var ispit : ispiti) {
			String predmet = ispit.getPredmet().getNaziv();
			if (!brojIspita.containsKey(predmet)) {
				brojIspita.put(predmet, 0);
				prolaznosti.put(predmet, 0.0);
			}
			
			brojIspita.put(predmet, brojIspita.get(predmet) + 1);
			prolaznosti.put(predmet, prolaznosti.get(predmet) + (ispit.getOcjena().getNumerickaVrijednost() > 1 ? 1 : 0));
		}
		
		for (var predmet : prolaznosti.keySet()) {
			prolaznosti.put(predmet, prolaznosti.get(predmet) / brojIspita.get(predmet));
		}
		
		return prolaznosti;
	}
}
