package hr.java.vjezbe.entitet;

import java.time.LocalDate;

public enum Generacija {
	X("X", LocalDate.of(2000, 11, 1), LocalDate.of(2012, 1, 2)),
	Y("Y", LocalDate.of(2013, 2, 2), LocalDate.of(2022, 2, 3)),
	Z("Z", LocalDate.of(2023, 1, 1), LocalDate.of(2030, 1, 1));
	
	private String opis;
	private LocalDate vrijediOd;
	private LocalDate vrijediDo;

	Generacija(String opis, LocalDate vrijediOd, LocalDate vrijediDo) {
		this.opis = opis;
		this.vrijediOd = vrijediOd;
		this.vrijediDo = vrijediDo;
	}

	public String getOpis() {
		return opis;
	}

	public LocalDate getVrijediOd() {
		return vrijediOd;
	}

	public LocalDate getVrijediDo() {
		return vrijediDo;
	}
	
	public boolean isWithin(LocalDate d) {
		return d.compareTo(vrijediOd) >= 0 && d.compareTo(vrijediDo) <= 0;
	}
	
	public static Generacija parse(String opis) {
		return switch (opis) {
			case "X" -> X;
			case "Y" -> Y;
			case "Z" -> Z;
			default -> throw new RuntimeException("Unreachable code");
		};
	}
}
