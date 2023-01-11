package hr.java.vjezbe.podaci;

import hr.java.vjezbe.entitet.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class IspitDatotekaDatasource implements Datasource<Ispit, Map<Long, Ispit>> {
	private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("d.M.yyyy. H:mm");
	
	private Path filePath;
	private Map<Long, Predmet> predmeti;
	private Map<Long, Student> studenti;

	public IspitDatotekaDatasource(Path filePath, Map<Long, Predmet> predmeti, Map<Long, Student> studenti) {
		this.filePath = filePath;
		this.predmeti = predmeti;
		this.studenti = studenti;
	}

	@Override
	public void spremiPodatke(List<Ispit> data) {
		/*
		StringBuilder output = new StringBuilder();
		
		for (var ispit : data) {
			
			output
				.append(ispit.getId()).append("\n")
				.append(ispit.getPredmet().getId())
				.append(ispit.getStudent().getId())
				.append(ispit.getOcjena().getNumerickaVrijednost())
				.append(ispit.getDatumIVrijeme().format(DATE_TIME_FORMAT))
				.append(ispit.getDvorana().naziv())
				.append(ispit.getDvorana().zgrada());
		}

		try {
			Files.writeString(filePath, output.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		*/
		
		try (var out = new ObjectOutputStream(new FileOutputStream(filePath.toString()))) {
			out.writeObject(data);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<Long, Ispit> dohvatiPodatke() {
		HashMap<Long, Ispit> ispiti = new HashMap<>();

		try (Scanner scanner = new Scanner(filePath)) {
			while (scanner.hasNextLine()) {
				long id = scanner.nextLong();
				scanner.nextLine();

				var predmet = predmeti.get(scanner.nextLong());
				scanner.nextLine();

				var student = studenti.get(scanner.nextLong());
				scanner.nextLine();

				var ocjena = Ocjena.parseOcjena(scanner.nextInt());
				scanner.nextLine();

				var datumIVrijeme = LocalDateTime.parse(scanner.nextLine(), DATE_TIME_FORMAT);
				var zgrada = scanner.nextLine();
				var dvorana = scanner.nextLine();

				var ispit = new Ispit(id, predmet, student, ocjena, datumIVrijeme, new Dvorana(zgrada, dvorana));
				ispiti.put(ispit.getId(), ispit);
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(String.format("File not found: %s%n", filePath));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return ispiti;
	}
}
