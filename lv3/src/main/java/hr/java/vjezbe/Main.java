package hr.java.vjezbe;

import java.time.format.DateTimeFormatter;

public class Main {
	public static void main(String[] args) {
		String kljuc = "4z3g99";
		var auto = new Automobil(kljuc);
		
		try {
			auto.otkljucaj(kljuc);
			System.out.printf("Auto je otključan ključem %s.%n", kljuc);
			String drugiKljuc = "gt4i3f";
			auto.otkljucaj(drugiKljuc);
			System.out.printf("Auto je otključan ključem %s.%n", drugiKljuc);
		} catch (NePodudaraSeKljucException e) {
			System.out.println(e.getMessage());
		}

		try {
			System.out.printf("Temperatura motora: %d%n", auto.trenutacnaTemperatura());
		} catch (PrevisokaTemperaturaException e) {
			System.out.printf("Previsoka temperatura: %s%n", e.getMessage());
		}
		
		System.out.println("Događaji:");
		
		for (var d : auto.getBlackBox().getDogadjaji()) {
			System.out.printf("%s (%d): %s%n", d.getVrijemeNastanka().format(DateTimeFormatter.ISO_DATE_TIME), d.getId(), d.getNaziv());
		}
	}
}
